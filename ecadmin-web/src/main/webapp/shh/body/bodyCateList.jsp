<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>身体部位管理</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid ec-form">
    <form id="form1" class="form-inline" role="form" style="margin-left:15px;">
        <div class="form-group" style="margin-left:30px;">
            <label for="cate_id_s">部位名称</label>
            <select class="form-control" id="cate_id_s" onchange="queryGrid();">
            </select>
        </div>
    </form>
</div>
<div class="container-fluid" style="height: 600px;margin-top:15px;">
    <ec:grid id="bodyCateGrid" title="分类信息" fit="true" fitColumns="true" showRowno="true" pagination="true"
             dataurl="${basePath}shh/body/bodyManager/queryCateList">
        <ec:gridToolbar>
            <button type="button" class="btn btn-primary btn-sm" onclick="addBodyCate()"><i
                    class="fa fa-plus-square"></i>&nbsp;&nbsp;新增部位
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="resetFrom()"><i
                    class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询
            </button>
        </ec:gridToolbar>
        <ec:gridItem itemId="cate_id" itemName="部位id" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="cate_name" itemName="部位名称"></ec:gridItem>
        <ec:gridItem itemId="parent_id" itemName="父类id" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="parent_name" itemName="父类部位名称" formatter="fnParentFormatter"></ec:gridItem>
        <ec:gridItem itemId="status" itemName="状态" formatter="fnStatusFormatter"></ec:gridItem>
        <ec:gridItem itemId="sort_order" itemName="排序"></ec:gridItem>
        <ec:gridItem itemId="cate_type" itemName="部位类型" collection="BODYTYPE"></ec:gridItem>
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
        var url = Base.globvar.basePath + "shh/body/bodyManager/getSelectList"
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
        Base.loadGridData("bodyCateGrid", "form1", param);
    }

    function fnParentFormatter(value, rowData, index) {
        if (value == null || value == "") {
            return "一级部位";
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

    //新增部位
    function addBodyCate() {
        var url = Base.globvar.basePath + "shh/body/bodyManager/toAddBodyCate";
        top.Base.openIframe({
            title: '新增部位',
            href: url,
            width: '600px',
            height: '400px',
            afterClose: function () {
                Base.reloadGridData("bodyCateGrid");
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
        var url = Base.globvar.basePath + "shh/body/bodyManager/toEditBodyCate?cate_id=" + cate_id;
        top.Base.openIframe({
            title: '部位编辑',
            href: url,
            width: '600px',
            height: '400px',
            afterClose: function () {
                Base.reloadGridData("bodyCateGrid");
            }
        });
    }

    function fnDel(cate_id) {
        Base.confirm("是否确认删除此部位？", function (flag) {
            if (!flag) {
                return false;
            }
            Base.ajax({
                type: 'post',
                url: Base.globvar.basePath + "shh/body/bodyManager/deleteBodyCate",
                data: {cate_id: cate_id},
                success: function (data) {
                    if (data.error === false) {
                        Base.alert("删除成功！");
                        queryGrid();
                    } else {
                        Base.alert("删除失败");
                        queryGrid();
                    }
                }
            });
        }, "warning")
    }

</script>
