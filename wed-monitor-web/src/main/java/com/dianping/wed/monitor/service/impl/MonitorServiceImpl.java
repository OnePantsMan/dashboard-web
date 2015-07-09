package com.dianping.wed.monitor.service.impl;

import com.dianping.wed.monitor.common.util.StringTemplateUtil;
import com.dianping.wed.monitor.config.service.MonitorChartOptionService;
import com.dianping.wed.monitor.config.service.MonitorPageConfigService;
import com.dianping.wed.monitor.config.service.MonitorQueryTemplateService;
import com.dianping.wed.monitor.config.service.dto.MonitorChartOptionDTO;
import com.dianping.wed.monitor.config.service.dto.MonitorPageConfigDTO;
import com.dianping.wed.monitor.config.service.dto.MonitorQueryTemplateDTO;
import com.dianping.wed.monitor.data.enums.Datasource;
import com.dianping.wed.monitor.data.service.DataServiceFactory;
import com.dianping.wed.monitor.data.service.MonitorDataService;
import com.dianping.wed.monitor.data.service.dto.MonitorDataDTO;
import com.dianping.wed.monitor.data.service.dto.MonitorQueryDTO;
import com.dianping.wed.monitor.service.MonitorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author dan.shan
 * @since 2015-06-03 10:08
 */
public class MonitorServiceImpl implements MonitorService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);

    @Resource
    private MonitorQueryTemplateService monitorQueryTemplateService;
    @Resource
    private MonitorPageConfigService monitorPageConfigService;
    @Resource
    private MonitorChartOptionService monitorChartOptionService;

    @Override
    public MonitorDataDTO findDataByPageId(String pageId, Map<String, String> params) {
        Assert.isTrue(StringUtils.isNotBlank(pageId), "page id should not be blank.");

        MonitorQueryTemplateDTO queryTemplate = monitorQueryTemplateService.loadQueryTemplateByPageId(pageId);
        MonitorQueryDTO monitorQuery = this.renderMonitorQuery(queryTemplate, params);
        Datasource datasource = Datasource.valueOf(queryTemplate.getDatasource());

        MonitorDataService dataService = DataServiceFactory.getDataService(datasource);

        return dataService.findDataByQuery(monitorQuery);
    }

    @Override
    public MonitorPageConfigDTO loadPageConfigByPageId(String pageId) {
        Assert.isTrue(StringUtils.isNotBlank(pageId), "page id should not be blank.");
        return monitorPageConfigService.loadPageConfigByPageId(pageId);
    }

    @Override
    public String deletePageConfigByPageId(String pageId) {
        Assert.isTrue(StringUtils.isNotBlank(pageId), "page id should not be blank.");
        return monitorPageConfigService.deletePageConfigByPageId(pageId);
    }

    @Override
    public String updatePageConfigByPageId(MonitorPageConfigDTO pageConfig) {
        Assert.isTrue(StringUtils.isNotBlank(pageConfig.getPageId()), "page id should not be blank.");

        MonitorPageConfigDTO exists = monitorPageConfigService.loadPageConfigByPageId(pageConfig.getPageId());
        Assert.notNull(exists);
        exists.setPageName(pageConfig.getPageName());
        exists.setPageDesc(pageConfig.getPageDesc());

        return monitorPageConfigService.updatePageConfigByPageId(exists);
    }

    @Override
    public String addPageConfig(MonitorPageConfigDTO pageConfig) {
        return monitorPageConfigService.addPageConfig(pageConfig);
    }

    @Override
    public MonitorChartOptionDTO loadChartOptionByPageId(String pageId) {
        Assert.isTrue(StringUtils.isNotBlank(pageId), "page id should not be blank.");
        return monitorChartOptionService.loadChartOptionByPageId(pageId);

    }

    @Override
    public MonitorQueryTemplateDTO loadQueryTemplateByPageId(String pageId) {
        Assert.isTrue(StringUtils.isNotBlank(pageId), "page id should not be blank.");
        return monitorQueryTemplateService.loadQueryTemplateByPageId(pageId);
    }

    @Override
    public MonitorQueryDTO renderMonitorQuery(MonitorQueryTemplateDTO template, Map<String, String> params) {
        Assert.notNull(template, "query template should not be null.");
        Assert.isTrue(StringUtils.isNotBlank(template.getQuery()), "query.query should not be blank");

        MonitorQueryDTO query = new MonitorQueryDTO();
        query.setQuery(renderQuery(template.getQuery(), params));
        query.setDatasource(Datasource.valueOf(template.getDatasource()));
        query.setXAxis(template.getXAxis());

        return query;
    }

    @Override
    public List<MonitorPageConfigDTO> findPageConfigs() {
        return monitorPageConfigService.findPageConfigs();
    }

    private String renderQuery(String query, Map<String, String> params) {
        return StringTemplateUtil.replaceTemplateTag(query, params);
    }
}
