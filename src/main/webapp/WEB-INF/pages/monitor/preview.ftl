<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Monitor Chart Preview</title>

    <link href="/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/codemirror/codemirror.css" rel="stylesheet">
    <link href="/static/css/codemirror/monokai.css" rel="stylesheet">

    <script src="/static/js/jquery/jquery-1.11.3.js"></script>
    <script src="/static/js/bootstrap/bootstrap.js"></script>
    <script src="/static/js/echarts/echarts-2.2.3.js"></script>
    <script src="/static/js/codemirror/codemirror.js"></script>
    <script src="/static/js/codemirror/javascript.js"></script>
</head>
<body>

<!-- header start -->
<header class="navbar navbar-static-top" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="/" class="navbar-brand">Monitor</a>
        </div>
        <nav class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/css/">CSS</a>
                </li>
                <li class="active">
                    <a href="/components/">Components</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="">Expo</a></li>
            </ul>
        </nav>
    </div>
</header>
<!-- header end -->

<!-- container start -->
<div class="container-fluid">
    <div class="row-fluid">
        <div id="sidebar-code" class="col-md-4">
            <div class="well sidebar-nav">
                <div class="nav-header"><a href="#" onclick="autoResize()" class="glyphicon glyphicon-resize-full" id ="icon-resize" ></a>option</div>
                    <textarea id="code" name="code">
option = {
    title : {
        text: '未来一周气温变化',
        subtext: '纯属虚构'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['最高气温','最低气温']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value} °C'
            }
        }
    ],
    series : [
        {
            name:'最高气温',
            type:'line',
            data:[11, 11, 15, 13, 12, 13, 10],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'最低气温',
            type:'line',
            data:[1, -2, 2, 5, 3, 2, 0],
            markPoint : {
                data : [
                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ]
            }
        }
    ]
};
                    </textarea>
            </div><!--/.well -->
        </div><!--/span-->
        <div id="graphic" class="col-md-8">
            <div id="main" class="main" style="height:400px"></div>
            <div>
                <button type="button" class="btn btn-sm btn-success" onclick="refresh(true)">刷 新</button>
                <span id='wrong-message' style="color:red"></span>
            </div>
        </div><!--/span-->
    </div><!--/row-->

</div><!--/.fluid-container-->
<!-- container end -->

<script src="/static/js/monitorpreview.js"></script>
</body>
</html>