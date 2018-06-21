<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>添加任务</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath %>UI/plugins/bootstrapvalidator/bootstrapValidator.min.css" rel="stylesheet">
</head>
<body>
<div class="wrapper wrapper-content animated fadeInUp" style="padding-left: 30px;padding-right: 30px;">
    <form id="form1" method="post" class="form-horizontal">
        <div class="form-group">
            <label for="job_name" class="control-label"><span style="color: red;">*</span>任务名称</label>
            <input id="job_name" name="job_name" type="text" class="form-control" placeholder="请输入任务名称"/>
        </div>
        <div class="form-group">
            <label for="cron_expression" class="control-label"><span style="color: red;">*</span>cron表达式(帮助:<a
                    href="http://cron.qqe2.com/" target="_blank">http://cron.qqe2.com/</a>)</label>
            <input id="cron_expression" name="cron_expression" type="text" class="form-control"
                   placeholder="请输入cron表达式"/>
        </div>
        <div class="form-group">
            <label for="job_class_name" class="control-label"><span
                    style="color: red;">*</span>执行类全名(如:com.xx.xx.)</label>
            <input id="job_class_name" name="job_class_name" type="text" class="form-control" placeholder="请输入执行类全名"/>
        </div>
        <div class="form-group">
            <label for="job_group" class="control-label">任务组名</label>
            <input id="job_group" name="job_group" type="text" class="form-control" placeholder="请输入任务组名"
                   value="EXTJWEB_JOBGROUP_NAME"/>
        </div>
        <div class="form-group">
            <label for="description" class="control-label">描述</label>
            <input id="description" name="description" type="text" class="form-control" placeholder="请输入描述"/>
        </div>
        <div class="col-sm-12 col-lg-12 text-center">
            <button type="submit" class="btn btn-primary">提交</button>
            <button type="reset" class="btn btn-outline btn-default" onclick="resetForm();">重置</button>
        </div>
    </form>
</div>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
<script src="<%=basePath%>UI/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
        initForm();
    });

    function resetForm() {
        $('#form1').data('bootstrapValidator').resetForm();
    }

    function initForm() {
        // 表单验证初始化
        $("#form1").bootstrapValidator({
            excluded: ':disabled',
            message: '该项有错,请检查',
            submitHandler: function (validator, form, submitButton) {
                var param = form.serializeArray();
                Base.ajax({
                    url: Base.globvar.basePath + "system/quartz/taskManager/add",
                    type: 'post',
                    data: param,
                    success: function (data, status, xhr) {
                    }
                });
            },
            fields: {
                job_name: {
                    validators: {
                        notEmpty: {
                            message: "任务名不能为空"
                        }
                    }
                }
                , cron: {
                    validators: {
                        notEmpty: {
                            message: "表达式不能为空"
                        }
                    }
                },
                job_class_name: {
                    validators: {
                        notEmpty: {
                            message: "执行类名不能为空"
                        }
                    }
                }
            }
        });
    }
</script>