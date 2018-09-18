<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>症状列表</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>UI/plugins/bootstrap-daterangepicker/daterangepicker.min.css" type="text/css">
</head>
<body>
<div class="container-fluid ec-form">
    <form id="form1" method="post" class="form-horizontal">
        <div class="row">
            <div class=" col-sm-4 col-lg-4">
                <label class="control-label">症状部位</label>
                <ec:comboTree id="cate_id_tree" url="${basePath}shh/symptoms/symptomsManager/getBodys"
                              multiple="false" animate="true" lines="true" onSelect="afterEditFunc"></ec:comboTree>
                <input type="hidden" id="cate_id" name="cate_id"/>
            </div>
            <div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
                <div class="">
                    <label for="title" class="control-label">症状</label>
                    <input id="title" type="text" name="title" class="form-control" placeholder="请输入症状">
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
                <div class="">
                    <label for="recommend_dept" class="control-label">推荐科室</label>
                    <input id="recommend_dept" type="text" name="recommend_dept" class="form-control" placeholder="请输入推荐科室">
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
                <div class="">
                    <label class="control-label">添加日期</label>
                    <div class="input-group date">
                        <input id="add_time_value" name="add_time_value" class="form-control" type="text">
                        <span class="input-group-addon">
					    	<span class="glyphicon glyphicon-calendar"></span>
					    </span>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
                <div class="">
                    <label class="control-label">状态</label>
                    <select class="form-control" name="status">
                        <option value="">--全部--</option>
                        <option value="1">启用</option>
                        <option value="2">禁用</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="container-fluid" style="height: 550px;">
    <ec:grid id="symptoms" title="症状列表" fit="true" fitColumns="true" pageSize="10" showRowno="true" pagination="true"
             dataurl="${basePath}shh/symptoms/symptomsManager/symptomsList">
        <ec:gridToolbar>
            <button type="button" class="btn btn-primary  btn-sm" onclick="queryGrid()"><i
                    class="fa fa-undo"></i>&nbsp;&nbsp;查询
            </button>
            <button type="button" class="btn btn-primary  btn-sm" onclick="resetQuery()"><i
                    class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询
            </button>
            <button type="button" class="btn btn-primary btn-sm" onclick="fnOpenWindow('add')"><i
                    class="fa fa-plus-square"></i>&nbsp;&nbsp;新增症状
            </button>
        </ec:gridToolbar>
        <ec:gridItem itemId="symptoms_id" itemName="symptoms_id" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="cate_id" itemName="cate_id" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="title"  itemName="症状" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="cate_name" itemName="部位" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="recommend_deptid" width="200" itemName="推荐科室编号" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="recommend_dept" width="200" itemName="推荐科室" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="cate_type" itemName="部位类型" showDetail="true" collection="BODYTYPE"></ec:gridItem>
        <ec:gridItem itemId="sort_order" itemName="排序" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="publish_time" itemName="发布时间" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="add_time" itemName="添加时间" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="status" itemName="状态" align="center" halign="center"
                     formatter="formatterStatus"></ec:gridItem>
        <ec:gridItem itemId="options" itemName="操作" formatter="optionsf" ></ec:gridItem>
    </ec:grid>
</div>
</body>
</html>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp" %>
<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
<script src="<%=basePath%>UI/plugins/bootstrap-daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        main();
    });

    function main() {
        selectDate();
    }

    //选择日期
    function selectDate() {
        $('#add_time_value').daterangepicker({
            autoApply: true,
            autoUpdateInput: false,
            ranges: {
                "今天": [
                    moment(),
                    moment()
                ],
                "昨天": [
                    moment().subtract(1, 'day'),
                    moment().subtract(1, 'day')
                ],
                "最近七天": [
                    moment().subtract(6, 'day'),
                    moment()
                ],
                "最近30天": [
                    moment().subtract(29, 'day'),
                    moment()
                ],
                "当月": [
                    moment().startOf('month'),
                    moment().endOf('month')
                ],
                "上月": [
                    moment().subtract(1, 'month').startOf('month'),
                    moment().subtract(1, 'month').endOf('month')
                ]
            }
        }, function (start, end, label) {
            // 这里是回调函数
        }).on('apply.daterangepicker', function (e, pk) {
            $(this).val(pk.startDate.format(pk.locale.format) + pk.locale.separator + pk.endDate.format(pk.locale.format));
        });
    }

    //症状部位改变后的回掉
    function afterEditFunc(data) {
        var id = data.id;
        $("#cate_id").val(id);
    }

    //设置状态
    function formatterStatus(value) {
        if (value == 3) {
            return '<span style="color: #f03;">已删除</span>';
        } else if (value == 1) {
            return '<span style="color: #66f;">启用</span>';
        } else if (value == 2) {
            return '<span style="color: #090;">禁用</span>';
        } else {
            return '<span style="color: #f03;">无效</span>';
        }
    }

    //刷新表格
    function queryGrid() {
        Base.loadGridData("symptoms", "form1", null);
    }

    //重置查询
    function resetQuery() {
        $("#cate_id").val("");
        $('#form1')[0].reset();
        queryGrid();
    }

    function fnOpenWindow(type, id) {
        var url = Base.globvar.basePath + "shh/symptoms/symptomsManager/toSymptoms?";
        var title = "";
        type = Base.trimAll(type);
        if (type == 'add') {
            type = "type=add";
            title = "新增症状";
        }
        if (type == "edit") {
            type = "type=edit&symptoms_id=" + id;
            title = "编辑症状";
        }
        if (type == "preview") {
            type = "type=preview&symptoms_id=" + id;
            title = "预览文章";
        }

        url += type;
        top.Base.openIframe({
            title: title,
            href: url,
            width: '90%',
            height: '90%',
            afterClose: function () {
                Base.reloadGridData("symptoms");
                //queryGrid();
            }
        });
    }

    //操作
    function optionsf(value, data, index) {
        var arr = [];
        arr.push('<i class="fa fa-edit btn-info" style="font-size: 16px;" title="编辑症状" onclick="fnOpenWindow(\'edit\', ' + data.symptoms_id + ')"></i>');
        arr.push('<i class="fa fa-eye btn-primary" style="font-size: 16px;" title="预览症状" onclick="fnOpenWindow(\'preview\', ' + data.symptoms_id + ')"></i>');
        arr.push('<i class="fa fa-remove btn-danger" style="font-size: 16px;" title="删除症状" onclick="del(' + data.symptoms_id +  ')"></i>');
        return arr.join(' ');
    }

    //删除
    function del(id) {
        Base.confirm("确认删除症状？！", function (flag) {
            if (!flag) {
                return false;
            }
            Base.ajax({
                type: 'post',
                url: Base.globvar.basePath + "shh/symptoms/symptomsManager/updateSymptoms",
                data: {status: 3, symptoms_id: id},
                success: function (data) {
                    if (data.error === false) {
                        Base.alert("删除症状成功！");
                        Base.reloadGridData("symptoms");
                    } else {
                        Base.alert("删除症状失败…");
                    }
                }
            });
        }, "warning")
    }


</script>
