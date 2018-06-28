<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>文章分类新增</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath %>UI/plugins/bootstrapvalidator/bootstrapValidator.min.css" rel="stylesheet">
    <style type="text/css">
    </style>
</head>
<body style="overflow:hidden;">
<div class="container-fluid" style="padding:40px 40px 0px 40px;">
    <form id="articleCateForm" class="form-horizontal">
        <input id="cate_id" name="cate_id" type="hidden" value="${articleCate.cate_id }"/>
        <div class="form-group">
            <label for="parent_id" class="col-xs-3 col-sm-3">文章分类父分类</label>
            <div class="col-xs-9 col-sm-9">
                <select class="form-control" id="parent_id" name="parent_id">
                    <option value="0">顶级分类</option>
                    <c:forEach items="${cates }" var="cate">
                        <c:if test="${articleCate.parent_id == cate.cate_id }">
                            <option selected="selected" value="${cate.cate_id }">${cate.cate_name }</option>
                        </c:if>
                        <c:if test="${articleCate.parent_id != cate.cate_id }">
                            <option value="${cate.cate_id }">${cate.cate_name }</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="cate_name" class="col-xs-3 col-sm-3">文章分类名称</label>
            <div class="col-xs-9 col-sm-9">
                <input id="cate_name" name="cate_name" value="${articleCate.cate_name }" type="text"
                       maxlength="30" class="form-control" required="true"/>
            </div>
        </div>
        <div class="form-group">
            <label for="status" class="col-xs-3 col-sm-3">状态</label>
            <div class="col-xs-9 col-sm-9">
                <select class="form-control" id="status" name="status">
                    <c:if test="${articleCate.status == 1 }">
                        <option value="1" selected="selected">启用</option>
                        <option value="0">注销</option>
                    </c:if>
                    <c:if test="${articleCate.status == 0 }">
                        <option value="1">启用</option>
                        <option value="0" selected="selected">注销</option>
                    </c:if>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="sort_order" class="col-xs-3 col-sm-3">排序号</label>
            <div class="col-xs-9 col-sm-9">
                <input id="sort_order" name="sort_order" type="text" value="${articleCate.sort_order }"
                       maxlength="100" class="form-control" required="true"/>
            </div>
        </div>
        <div class="form-group">
            <label for="cate_type" class="col-xs-3 col-sm-3">文章类型</label>
            <div class="col-xs-9 col-sm-9">
                <select class="form-control" id="cate_type" name="cate_type">
                    <c:forEach items="${types }" var="type">
                        <c:if test="${articleCate.cate_type == type.codeValue }">
                            <option selected="selected" value="${type.codeValue }">${type.codeValueName }</option>
                        </c:if>
                        <c:if test="${articleCate.cate_type != type.codeValue }">
                            <option value="${type.codeValue }">${type.codeValueName }</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
        <span id="warning"
              style="display: none;line-height: 18px;width: 100%;height:18px;color:red;text-align: center;font-weight: bold"></span>
        <div class="form-group">
            <input type="button" id="bntSave" class="btn btn-primary col-xs-1.5 col-xs-offset-4"
                   onclick="saveArticleCate()" value="保存"/>
            <input type="button" id="btnBack" class="btn btn-default col-xs-1.5 col-xs-offset-1" onclick="fnClose()"
                   value="取消"/>
        </div>
    </form>
</div>
</body>
</html>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp" %>
<script src="<%=basePath%>UI/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
<script type="text/javascript">
    function saveArticleCate() {
        var cate_id = $("#cate_id").val();
        var parent_id = $("#parent_id").val();
        var cate_name = $("#cate_name").val();
        var status = $("#status").val();
        var sort_order = $("#sort_order").val();
        var cate_type = $("#cate_type").val();
        if (cate_name == null || cate_name.length == 0) {
            $("#warning").text("文章分类名称不能为空").css("display", "block");
            $("#cate_name").focus();
            return;
        }
        if (sort_order == null || sort_order.length == 0 || sort_order > 255 || sort_order < 1) {
            $("#warning").text("排序号不能为空,最大值255").css("display", "block");
            $("#sort_order").focus();
            return;
        }
        var url = Base.globvar.basePath + "shh/cate/cateManager/updateArticleCate";
        $.post(url, $("#articleCateForm").serializeArray(), function (data) {
            if (!data.error) {
                $("#warning").text("修改文章分类成功").css("display", "block");
                setTimeout(function () {
                    parent.Base.closeIframe();
                }, 500);
            } else {
                $("#warning").text(data.error_msg).css("display", "block");
            }
        });
    }

    function fnClose() {
        parent.Base.closeIframe();
    }
</script>
