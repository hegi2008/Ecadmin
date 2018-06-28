<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>文章分类管理</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid ec-form">
    <form id="form1" class="form-inline" role="form" style="margin-left:15px;">
        <div class="form-group" style="margin-left:30px;">
            <label for="cate_name">分类名称</label>
            <select class="form-control" id="cate_id_s" onchange="queryGrid();">
            </select>
        </div>
    </form>
</div>
<div class="container-fluid" style="height: 500px;margin-top:15px;">
    <ec:grid id="articleCateGrid" title="分类信息" fit="true" fitColumns="true" showRowno="true" pagination="true"
             dataurl="${basePath}shh/cate/cateManager/queryCateList">
        <ec:gridToolbar>
            <button type="button" class="btn btn-primary btn-sm" onclick="addArticleCate()"><i class="fa fa-plus-square"></i>&nbsp;&nbsp;新增分类</button>
            <button type="button" class="btn btn-outline btn-default btn-sm" onclick="resetFrom()"><i class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询</button>
        </ec:gridToolbar>
        <ec:gridItem itemId="cate_id" itemName="文章分类id" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="cate_name" itemName="分类名称"></ec:gridItem>
        <ec:gridItem itemId="parent_id" itemName="父类id" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="parent_name" itemName="父类名称" formatter="fnParentFormatter"></ec:gridItem>
        <ec:gridItem itemId="status" itemName="分类状态" formatter="fnStatusFormatter"></ec:gridItem>
        <ec:gridItem itemId="sort_order" itemName="排序"></ec:gridItem>
        <ec:gridItem itemId="cate_type" itemName="文章类型" collection="CATE_TYPE"></ec:gridItem>
        <ec:gridItem itemId="options" itemName="编辑/删除" formatter="optionsf" width="50"></ec:gridItem>
    </ec:grid>
</div>
</body>
</html>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp" %>
<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        initForm();
    });

    //初始化查询条件
    function initForm() {
        var url = Base.globvar.basePath + "shh/cate/cateManager/getSelectList"
        $.ajax({
            url: url,
            success: function (data) {
                var html = "<option value='-1'>全部</option>";
                for (var i = 0; i < data.length; i++) {
                    html += "<option value='" + data[i].cate_id + "'>"
                        + data[i].cate_name + "</option>"
                }
                $("#cate_id_s").empty().append(html);
            }
        });
    }

    //根据查询条件查询列表
    function queryGrid() {
        var param = {};
        if ($("#cate_id_s").val() != -1) {
            param = {"parent_id": $("#cate_id_s").val()};
        }
        Base.loadGridData("articleCateGrid", "form1", param);
    }

    function fnParentFormatter(value, rowData, index) {
        if (value == null || value == "") {
            return "顶级分类";
        } else {
            return value;
        }
    }

    function fnStatusFormatter(value, rowData, index) {
        if (value == 1) {
            return "启用";
        } else if (value == 0) {
            return "<span style='color: red;'>注销</span>";
        }
    }

    //新增文章分类
    function addArticleCate() {
        var url = Base.globvar.basePath + "shh/cate/cateManager/toAddArticleCate";
        top.Base.openIframe({
            title: '新增文章分类',
            href: url,
            width: '600px',
            height: '400px',
            afterClose: function () {
                Base.reloadGridData("articleCateGrid");
            }
        });
    };

    //重置表单
    function resetFrom() {
        $('#form1')[0].reset();
        queryGrid();
    }

    //初始化编辑和删除按钮
    function optionsf(value, rowData, index) {
        var cate_id = rowData.cate_id;
        var arr = [];
        arr.push('<i class="fa fa-edit" style="cursor:pointer;"  onclick="fnEdit(' + cate_id + ');"></i>');
        arr.push('<i class="fa fa-remove" style="cursor:pointer;"  onclick="fnDel(' + cate_id + ');"></i>');
        return arr.join(' ');
    }

    function fnEdit(cate_id) {
        var url = Base.globvar.basePath + "shh/cate/cateManager/toEditArticleCate?cate_id=" + cate_id;
        top.Base.openIframe({
            title: '文章分类编辑',
            href: url,
            width: '600px',
            height: '400px',
            afterClose: function () {
                Base.reloadGridData("articleCateGrid");
            }
        });
    }

    function fnDel(cate_id) {
        var yes = window.confirm("是否确认删除此文章分类?");
        if (yes) {
            var url = Base.globvar.basePath + "shh/cate/cateManager/deleteArticleCate.do?cate_id=" + cate_id;
            $.get(url, function (data) {
                Base.reloadGridData("articleCateGrid");
            });
        }
    }
</script>
