<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>身体部位新增</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath %>UI/plugins/bootstrapvalidator/bootstrapValidator.min.css" rel="stylesheet">
    <style type="text/css">
    </style>
</head>
<body style="overflow:hidden;">
<div class="container-fluid" style="padding:40px 40px 0px 40px;">
    <form id="bodyCateForm" class="form-horizontal">
        <div class="form-group">
            <label for="parent_id" class="col-xs-3 col-sm-3">父类部位</label>
            <div class="col-xs-9 col-sm-9">
                <select class="form-control" id="parent_id" name="parent_id">
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="cate_name" class="col-xs-3 col-sm-3">部位名称</label>
            <div class="col-xs-9 col-sm-9">
                <input id="cate_name" name="cate_name" type="text" maxlength="30" class="form-control"
                       required="true"/>
            </div>
        </div>
        <div class="form-group">
            <label for="status" class="col-xs-3 col-sm-3">状态</label>
            <div class="col-xs-9 col-sm-9">
                <select class="form-control" id="status" name="status">
                    <option value="1" selected="selected">启用</option>
                    <option value="0">注销</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="sort_order" class="col-xs-3 col-sm-3">排序号</label>
            <div class="col-xs-9 col-sm-9">
                <input id="sort_order" name="sort_order" type="text" maxlength="100" class="form-control"
                       required="true"/>
            </div>
        </div>
        <div class="form-group">
            <label for="cate_type" class="col-xs-3 col-sm-3">部位类型</label>
            <div class="col-xs-9 col-sm-9">
                <select class="form-control" id="cate_type" name="cate_type">
                    <c:forEach items="${types }" var="type">
                        <option value="${type.codeValue }">${type.codeValueName }</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <span id="warning"
              style="display: none;line-height: 18px;width: 100%;height:18px;color:red;text-align: center;font-weight: bold"></span>
        <div class="form-group">
            <input type="button" id="bntSave" class="btn btn-primary col-xs-1.5 col-xs-offset-4"
                   onclick="saveBodyCate()" value="新增"/>
            <input type="button" id="btnBack" class="btn btn-default col-xs-1.5 col-xs-offset-1"
                   onclick="$('#bodyCateForm')[0].reset();" value="重置"/>
        </div>
    </form>
</div>
</body>
</html>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp" %>
<script src="<%=basePath%>UI/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        initSelect();
    });

    function initSelect() {
        var url = Base.globvar.basePath + "shh/body/bodyManager/getSelectList"
        $.ajax({
            url: url,
            success: function (data) {
                var html = "<option value='0'>一级部位</option>";
                for (var i = 0; i < data.length; i++) {
                    html += "<option value='" + data[i].cate_id + "'>"
                        + data[i].cate_name + "</option>"
                }
                $("#parent_id").empty().append(html);
            }
        });
    }

    function saveBodyCate() {
        var parent_id = $("#parent_id").val();
        var cate_name = $("#cate_name").val();
        var status = $("#status").val();
        var sort_order = $("#sort_order").val();
        var cate_type = $("#cate_type").val();
        if (cate_name == null || cate_name.length == 0) {
            $("#warning").text("身体部位名称不能为空").css("display", "block");
            $("#cate_name").focus();
            return;
        }
        if (sort_order == null || sort_order.length == 0 || sort_order > 255 || sort_order < 1) {
            $("#warning").text("排序号不能为空,最大值255").css("display", "block");
            $("#sort_order").focus();
            return;
        }
        var url = Base.globvar.basePath + "shh/body/bodyManager/saveBodyCate";
        $.post(url, $("#bodyCateForm").serializeArray(), function (data) {
            if (!data.error) {
                alert("新增身体部位成功,可继续新增");
                //$("#warning").text("新增身体部位成功,可继续新增").css("display", "block");
            } else {
                alert(data.error_msg);
                //$("#warning").text(data.error_msg).css("display", "block");
            }
        });
    }
</script>
