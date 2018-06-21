<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>任务管理</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>UI/plugins/bootstrap-daterangepicker/daterangepicker.min.css" type="text/css">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>所有任务</h5>
                </div>
                <div class="ibox-content">
                    <form id="form1" class="form-inline m-b" role="form">
                        <div class="form-group">
                            <label for="job_name" class="sr-only">任务名</label>
                            <input id="job_name" type="text" name="job_name" class="form-control" onchange="queryGrid()"
                                   placeholder="任务名">
                        </div>
                        <div class="form-group">
                            <label for="job_class_name" class="sr-only">任务类</label>
                            <input id="job_class_name" type="text" name="job_class_name" class="form-control" onchange="queryGrid()"
                                   placeholder="任务类">
                        </div>
                    </form>
                    <div style="height: 400px;">
                        <ec:grid id="user" title="" queryParams="{}" fit="true" fitColumns="true" showRowno="true"
                                 pagination="true" dataurl="${basePath}system/quartz/taskManager/list">
                            <ec:gridToolbar>
                                <button type="button" class="btn btn-primary btn-sm" onclick="createTask()"><i
                                        class="fa fa-plus-square"></i>&nbsp;&nbsp;添加任务
                                </button>
                                <button type="button" class="btn btn-outline btn-default btn-sm" onclick="queryGrid();">
                                    <i class="fa fa-search"></i>&nbsp;&nbsp;查询
                                </button>
                                <button type="button" class="btn btn-outline btn-default btn-sm"
                                        onclick="$('#form1')[0].reset();queryGrid();"><i class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询
                                </button>
                            </ec:gridToolbar>
                            <ec:gridItem itemId="sched_name" itemName="调度器名称"></ec:gridItem>
                            <ec:gridItem itemId="job_name" itemName="任务名"></ec:gridItem>
                            <ec:gridItem itemId="job_group" itemName="任务组名"></ec:gridItem>
                            <ec:gridItem itemId="description" itemName="描述"></ec:gridItem>
                            <ec:gridItem itemId="job_class_name" itemName="任务类"></ec:gridItem>
                            <ec:gridItem itemId="cron_expression" itemName="表达式"></ec:gridItem>
                            <%--<ec:gridItem itemId="trigger_name" itemName="触发器名"></ec:gridItem>--%>
                            <%--<ec:gridItem itemId="trigger_group" itemName="触发器组名"></ec:gridItem>--%>
                            <ec:gridItem itemId="trigger_state" itemName="触发器状态"></ec:gridItem>
                            <ec:gridItem itemId="options" itemName="操作" formatter="optionsf" width="160"></ec:gridItem>
                        </ec:grid>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
<script src="<%=basePath%>UI/plugins/bootstrap-daterangepicker/daterangepicker.min.js"></script>
</body>
</html>
<script type="text/javascript">
    function queryGrid() {
        Base.loadGridData("user", "form1", null);
    }
    function createTask() {
        var url = Base.globvar.basePath + "system/quartz/taskManager/toAdd";
        top.Base.openIframe({
            title: '添加任务',
            href: url,
            width: '600px',
            height: '600px',
            afterClose: function () {
                Base.reloadGridData("user");
            }
        });
    }
    function optionsf(value, data, index) {
        var arr = [];
        var stat = data.trigger_state
        if (stat == 'PAUSED') {
            var editTask = "editTask('" + data.sched_name + "','" + data.job_name + "','" + data.job_group + "')";
            var resumeTask = "resumeTask('" + data.job_name + "','" + data.trigger_group + "','" + data.job_group + "')";
            var removeTask = "removeTask('" + data.job_name + "','" + data.job_group + "')";
            arr.push('<a class="btn btn-primary btn-xs" onclick="' + editTask + '">修改cron</a>');
            arr.push('<a class="btn btn-info btn-xs" onclick="' + resumeTask + '">恢复启动</a>');
            arr.push('<a class="btn btn-danger btn-xs" onclick="' + removeTask + '">移除</a>');
        }
        else {
            var func = "pauseTask('" + data.job_name + "','" + data.trigger_group + "','" + data.job_group + "')";
            arr.push('<a class="btn btn-danger btn-xs" onclick="' + func + '">暂停任务</a>');
        }
        return arr.join(' ');
    }
    function editTask(sched_name, job_name, job_group) {
        var url = Base.globvar.basePath + "system/quartz/taskManager/toEdit?sched_name=" + sched_name + "&job_name=" + job_name + "&job_group=" + job_group;
        top.Base.openIframe({
            title: '修改cron表达式',
            href: url,
            width: '600px',
            height: '600px',
            afterClose: function () {
                Base.reloadGridData("user");
            }
        });
    }
    function pauseTask(job_name, trigger_group, job_group) {
        Base.confirm('确定要暂停此任务吗?',function(yes){
            if(yes){
                Base.ajax({
                    url: Base.globvar.basePath + "system/quartz/taskManager/pause",
                    type: 'post',
                    mask: false,
                    data: {"job_name": job_name, "trigger_group": trigger_group, "job_group": job_group},
                    success: function (data, status, xhr) {
//			Base.reloadGridData("user");
                        window.location.reload();
                    }
                });
            }
        })
    }
    function resumeTask(job_name, trigger_group, job_group) {
        Base.ajax({
            url: Base.globvar.basePath + "system/quartz/taskManager/resume",
            type: 'post',
            mask: false,
            data: {"job_name": job_name, "trigger_group": trigger_group, "job_group": job_group},
            success: function (data, status, xhr) {
//			Base.reloadGridData("user");
                window.location.reload();
            }
        });
    }
    function removeTask(job_name, job_group) {
        Base.confirm('移除之后不能恢复,确定要移除此任务吗？',function(yes){
            if(yes){
                Base.ajax({
                    url: Base.globvar.basePath + "system/quartz/taskManager/remove",
                    type: 'post',
                    mask: false,
                    data: {"job_name": job_name, "job_group": job_group},
                    success: function (data, status, xhr) {
                        Base.reloadGridData("user");
                    }
                });
            }
        })
    }

</script>