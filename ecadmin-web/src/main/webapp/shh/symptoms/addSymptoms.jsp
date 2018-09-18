<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>新增症状</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="<%=basePath%>UI/plugins/kindeditor/themes/simple/simple.css"/>
    <link href="<%=basePath %>UI/plugins/bootstrapvalidator/bootstrapValidator.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>UI/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
</head>
<body>

<div class="container-fluid ec-form">
    <form id="form_inline" method="post" class="form-horizontal">
        <input type="hidden" name="symptoms_id" id="symptoms_id" value="${ae.symptoms_id }">
        <div class="row">
            <div class=" col-sm-6 col-lg-6 ">
                <label for="cate_id" class="control-label">部位</label>
                <input class="easyui-combotree"  id="cate_name" name="cate_name" placeholder="请选择部位" type="text" value="${ae.cate_id}"  data-options="url:'${basePath}shh/symptoms/symptomsManager/getBodys',method:'post',required:true" style="width:200px;">
                <input id="cate_id" name="cate_id" type="hidden" value="${ae.cate_id }" readonly="readonly"/>
            </div>
            <div class=" col-sm-6 col-lg-6">
                <label for="title" class="control-label">症状</label>
                <input id="title" name="title" type="text" value="${ae.title}" class="form-control"
                       placeholder="请输入症状"/>
            </div>
        </div>
        <div class="row">
            <div class=" col-sm-6 col-lg-6">
                <label for="recommend_deptid" class="control-label">推荐科室编号</label>
                <input id="recommend_deptid" type="text" class="form-control" value="${ae.recommend_deptid}" name="recommend_deptid"
                       placeholder="请输入科室编号"/>
            </div>
            <div class=" col-sm-6 col-lg-6">
                <label for="recommend_dept" class="control-label">推荐科室</label>
                <input id="recommend_dept" type="text" class="form-control" name="recommend_dept" value="${ae.recommend_dept}" placeholder="请输入科室名"/>
            </div>
        </div>
        <div class="row">
            <div class=" col-sm-6 col-lg-6">
                <label for="sort_order" class="control-label">序号</label>
                <input id="sort_order" type="text" class="form-control" name="sort_order" value="${ae.sort_order}"
                       placeholder="请输入排序号"/>
            </div>
            <div class=" col-sm-6 col-lg-6" >
                <label class="control-label">状态</label>
                <div class="row" style="margin-left: 15px;">
                    <div class="radio pull-left">
                        <label>
                            <input type="radio" name="status" value="1" checked/>启用
                        </label>
                    </div>
                    <div class="radio pull-left">
                        <label>
                            <input type="radio" name="status" value="2"/>禁用
                        </label>
                    </div>
                    <div class="radio pull-left">
                        <label>
                            <input type="radio" name="status" value="3"/>已删除
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="easyui-tabs" data-options="tabWidth:112" style="width:1300px;height:500px">
            <div title="简介" style="padding:10px">
                <textarea id="introduction" style="width: 100%; height: 500px; visibility: hidden;">${ae.introduction }</textarea>
            </div>
            <div title="治疗" style="padding:10px">
                <textarea id="treatment" style="width: 100%; height: 500px; visibility: hidden;">${ae.treatment }</textarea>
            </div>
            <div title="诊断" style="padding:10px">
                <textarea id="diagnosis" style="width: 100%; height: 500px; visibility: hidden;">${ae.diagnosis }</textarea>
            </div>
            <div title="病因" style="padding:10px">
                <textarea id="cause" style="width: 100%; height: 500px; visibility: hidden;">${ae.cause }</textarea>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12 col-lg-12 text-center">
                <input type="hidden" id="type_" value="${type}" status="${ae.status }">
                <button type="submit" id="submit_btn" class="btn btn-primary">提交</button>
                <button type="reset" class="btn btn-default">重置表单</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp" %>
<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
<script src="<%=basePath%>UI/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>UI/plugins/kindeditor/kindeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>UI/plugins/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>statics/js/symptoms/symptoms.js"></script>
