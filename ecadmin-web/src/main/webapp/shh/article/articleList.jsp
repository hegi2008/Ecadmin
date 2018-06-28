<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>文章列表</title>
    <%@ include file="/ec/inc_base.jsp" %>
</head>
<body>
<div class="container-fluid ec-form">
    <form id="form1" method="post" class="form-horizontal">
        <div class="row">
            <div class="form-group col-sm-4 col-lg-4">
                <label class="control-label">文章类型</label>
                <ec:comboTree id="cate_id_tree" url="${basePath}admin/articleAdminAction!getCategorys.do"
                              multiple="false" animate="true" lines="true" onSelect="afterEditFunc"></ec:comboTree>
                <input type="hidden" id="cate_id" name="dto['cate_id']"/>
            </div>
            <div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
                <div class="form-group">
                    <label for="title" class="control-label">标题</label>
                    <input id="title" type="text" name="dto['title']" class="form-control" placeholder="请输入标题">
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
                <div class="form-group">
                    <label for="keywords" class="control-label">关键字</label>
                    <input id="keywords" type="text" name="dto['keywords']" class="form-control" placeholder="请输入关键字">
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
                <div class="form-group">
                    <label class="control-label">添加日期</label>
                    <div class="input-group date">
                        <input id="add_time_value" name="dto['add_time_value']" class="form-control" type="text">
                        <span class="input-group-addon">
					    	<span class="glyphicon glyphicon-calendar"></span>
					    </span>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-4 col-md-4 col-lg-4">
                <div class="form-group"> <!-- 0无效1审核中2通过审核3已删除 -->
                    <label class="control-label">审核状态</label>
                    <select class="form-control" name="dto['status']">
                        <option value="">--全部--</option>
                        <option value="4">无效</option>
                        <option value="1">审核中</option>
                        <option value="2">通过审核</option>
                        <option value="3">已删除</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="container-fluid" style="height: 500px;">
    <ec:grid id="goods" title="文章列表" fit="true" fitColumns="true" pageSize="10" showRowno="true" pagination="true"
             dataurl="${basePath}admin/articleAdminAction!artilceList.do">
        <ec:gridToolbar>
            <ec:linkbutton id="submit" text="查询" onclick="queryGrid()" icon="fa-search"></ec:linkbutton>
            <ec:linkbutton id="resetForm" text="重置查询" onclick="resetQuery()" icon="fa-refresh"></ec:linkbutton>
            <ec:linkbutton id="openw" text="新增文章" onclick="fnOpenWindow('add')" icon="fa-plus-square"></ec:linkbutton>
        </ec:gridToolbar>
        <ec:gridItem itemId="article_id" itemName="article_id" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="cate_id" itemName="cate_id" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="title" width="20%" itemName="文章标题" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="cate_name" itemName="文章类别"></ec:gridItem>
        <ec:gridItem itemId="keywords" itemName="关键字"></ec:gridItem>
        <ec:gridItem itemId="sort_order" itemName="文章排序号"></ec:gridItem>
        <ec:gridItem itemId="publish_time" itemName="发布时间" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="status" itemName="文章状态" align="center" halign="center"
                     formatter="formatterStatus"></ec:gridItem>
        <ec:gridItem itemId="options" itemName="操作" formatter="optionsf" width="50"></ec:gridItem>
    </ec:grid>
</div>
</body>
</html>
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

    //文章类型改变后的回掉
    function afterEditFunc(data) {
        var id = data.id;
        $("#cate_id").val(id);
    }

    //设置状态
    function formatterStatus(value, row, index) {
        if (value == 3) {
            return '<span style="color: #f03;">已删除</span>';
        } else if (value == 1) {
            return '<span style="color: #66f;">审核中</span>';
        } else if (value == 2) {
            return '<span style="color: #090;">已发布</span>';
        } else {
            return '<span style="color: #f03;">无效</span>';
        }
    }

    //刷新表格
    function queryGrid() {
        //var page = $(".pagination-num").val(); //{"dto['page']": page}
        Base.loadGridData("goods", "form1", null);
    }

    //重置查询
    function resetQuery() {
        $("#cate_id").val("");
        $('#form1')[0].reset();
    }

    function fnOpenWindow(type, id, status) {
        var url = Base.globvar.basePath + "admin/articleAdminAction!toArticle.do?";
        var title = "";
        type = Base.trimAll(type);
        if (type == 'add') {
            type = "type=add";
            title = "新增文章";
        }
        if (type == "edit") {
            type = "type=edit&article_id=" + id;
            title = "编辑文章";
        }
        if (type == "audit") {
            type = "type=audit&article_id=" + id;
            title = "审核文章";
        }
        if (type == "preview") {
            type = "type=preview&article_id=" + id;
            title = "预览文章";
        }

        url += type;
        top.Base.openIframe({
            title: title,
            href: url,
            width: '90%',
            height: '90%',
            afterClose: function () {
                Base.reloadGridData("goods");
                //queryGrid();
            }
        });
    }

    //文章操作
    function optionsf(value, data, index) {
        var status = data.status;
        var arr = [];
        var audit = "${audit}";
        if (audit === "1") {
            arr.push('<i class="fa fa-check" style="font-size: 16px;" title="审核文章" onclick="fnOpenWindow(\'audit\', ' + data.article_id + ', ' + status + ')"></i>');
        }
        arr.push('<i class="fa fa-edit" style="font-size: 16px;" title="编辑文章" onclick="fnOpenWindow(\'edit\', ' + data.article_id + ')"></i>');
        arr.push('<i class="fa fa-eye" style="font-size: 16px;" title="预览文章" onclick="fnOpenWindow(\'preview\', ' + data.article_id + ')"></i>');
        arr.push('<i class="fa fa-remove" style="font-size: 16px;" title="删除文章" onclick="delArticle(' + data.article_id + ', ' + status + ')"></i>');
        return arr.join(' ');
    }

    //删除文章
    function delArticle(id, status) {
        if (status == 3) {
            Base.alert("文章已删除！");
            return false;
        }
        Base.confirm("确认删除文章？！", function (flag) {
            if (!flag) {
                return false;
            }
            Base.ajax({
                type: 'post',
                url: Base.globvar.basePath + "admin/articleAdminAction!updateArticle.do",
                data: {status: 3, article_id: id},
                success: function (data) {
                    data = eval("(" + data + ")");
                    if (data.error === false) {
                        Base.alert("删除文章成功！");
                        Base.reloadGridData("goods");
                    } else {
                        Base.alert("删除文章失败…");
                    }
                }
            });
        }, "warning")
    }


</script>
<%@ include file="/ec/incfooter.jsp" %>