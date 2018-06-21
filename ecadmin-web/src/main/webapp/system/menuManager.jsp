<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>菜单管理</title>
	  <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
	  <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
    <style type="text/css">
    	.datagrid-cell{line-height: 1;}
    </style>
  </head>
  <body class="gray-bg">
  	<div class="wrapper wrapper-content animated fadeInRight">
  		<div class="row">
  			<div class="col-sm-12">
  				<div class="ibox">
	  				<div class="ibox-title">
	                    <h5>菜单列表&nbsp;&nbsp;<small>菜单图标请参考：<a href="http://fontawesome.io/icons/" target="_blank">http://fontawesome.io/icons/</a>。填名称即可。</small></h5>
	                    <!-- <div class="ibox-tools">
	                        <a class="collapse-link">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
	                    </div> -->
	                </div>
	                <div class="ibox-content">
	                	<div id="menu_toolbar" style="padding:5px;">
							<button type="button" class="btn btn-outline btn-default btn-sm" onclick="$('#menu').treegrid('expandAll');"><i class="fa fa-toggle-down"></i>&nbsp;&nbsp;展开</button>
							<button type="button" class="btn btn-outline btn-default btn-sm" onclick="$('#menu').treegrid('collapseAll');"><i class="fa fa-toggle-down"></i>&nbsp;&nbsp;收起</button>
							<button type="button" class="btn btn-outline btn-default btn-sm" onclick="queryGrid()"><i class="fa fa-refresh"></i>&nbsp;&nbsp;刷新</button>
							<button type="button" class="btn btn-outline btn-default btn-sm" onclick="createMenu()"><i class="fa fa-plus-square"></i>&nbsp;&nbsp;新增根菜单</button>
							<button type="button" class="btn btn-outline btn-default btn-sm" onclick="cancelEdit()"><i class="fa fa-undo"></i>&nbsp;&nbsp;取消编辑</button>
							<button type="button" class="btn btn-outline btn-primary btn-sm" onclick="saveEdit()"><i class="fa fa-save"></i>&nbsp;&nbsp;保存新增/编辑</button>
						</div>
						<div style="height: 535px;">
							<table id="menu" class="easyui-treegrid" data-options="url:'${basePath}system/menu/menuManager/queryMenuList',fit:true,idField:'menu_id',treeField:'menu_name'
							,rownumbers:true,toolbar:'#menu_toolbar',animate:true,singleSelect:true">   
							    <thead>   
							        <tr>
							        	<!-- <th data-options="field:'ck',checkbox:true"></th> -->
							        	<th data-options="field:'menu_id',hidden:true"></th>
							        	<th data-options="field:'_parentId',hidden:true"></th>
							            <th data-options="field:'menu_name',align:'left',halign:'center',editor:'text'" width="29%">菜单名称</th>   
							            <th data-options="field:'menu_icon',align:'center',halign:'center',formatter:optionsi,editor:'text'" width="10%">菜单图标</th>
							            <th data-options="field:'menu_url',align:'left',halign:'center',editor:'text'" width="30%">菜单路劲</th>
							            <th data-options="field:'menu_create_time',align:'center',halign:'center',formatter:CommonFormatter.format,editor:'datetimebox'" width="15%">创建时间</th>
							            <th data-options="field:'options',align:'center',halign:'center',formatter:optionsf" width="15%">操作</th>
							        </tr>   
							    </thead>   
							</table>
						</div>
	                </div>
  				</div>
  			</div>
  		</div>
  	</div>
	<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
	<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
  </body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	
});

function queryGrid() {
	$('#menu').treegrid('reload');
}

var mid = -1;
var editingId;
// 添加跟节点
function createMenu() {
	if(!mid){
		mid = -1;
	}else{
		mid --;
	}
	$('#menu').treegrid('append',{
		data: [{menu_id:mid,_parentId:0,menu_name:'',menu_create_time:moment().format('YYYY-MM-DD HH:mm:ss')}]
	});
	if(editingId != undefined){
		cancelEdit();
	}
	editingId = mid;
	$('#menu').treegrid('beginEdit', editingId);
}

function optionsi(value,data,index){
	if(value){
		if(value.indexOf("fa") > 0){
			return '<i class="fa '+value+'"></i>';
		}else{
			return '<i class="fa fa-'+value+'"></i>';
		}
	}
}

function optionsf(value,data,index){
	var arr = [];
	if(data._parentId == '0'){
		arr.push('<a class="btn btn-warning btn-xs" onclick="addMenu('+data.menu_id+')">新增下级</a>');
	}
	arr.push('<a class="btn btn-primary btn-xs" onclick="editMenu('+data.menu_id+')">编辑菜单</a>');
	arr.push('<a class="btn btn-danger btn-xs" onclick="deleteMenu('+data.menu_id+')">删除菜单</a>');
	return arr.join(' ');
}

function deleteMenu(menu_id){
	if(parseInt(menu_id) < 0){
		$('#menu').treegrid('remove',menu_id);
		return;
	}
	Base.confirm("确认删除该菜单及其所有子菜单吗?",function(yes){
		if(yes){
			Base.ajax({
				url:Base.globvar.basePath + "system/menu/menuManager/deleteMenu",
				type:'post',
				data:{menu_id:menu_id},
				success:function(data, status, xhr){
					$('#menu').treegrid('remove',menu_id);
				}
			});
		}
	});
}

function addMenu(menu_id){
	if(!mid){
		mid = -1;
	}else{
		mid --;
	}
	$('#menu').treegrid('append',{
		parent: menu_id,
		data: [{menu_id:mid,_parentId:menu_id,menu_name:'',menu_create_time:moment().format('YYYY-MM-DD HH:mm:ss')}]
	});
	if(editingId != undefined){
		cancelEdit();
	}
	editingId = mid;
	$('#menu').treegrid('beginEdit', editingId);
}

function editMenu(menu_id){
    if (editingId != undefined){
        return;
    }
    editingId = menu_id
    $('#menu').treegrid('beginEdit', editingId);
}

function saveEdit(){
    if (editingId != undefined){
        var t = $('#menu');
        t.treegrid('endEdit', editingId);
        var node = t.treegrid('find',editingId);
        var param = Base.jsonToStr(node);
		Base.ajax({
			url:Base.globvar.basePath + "system/menu/menuManager/saveMenu",
			type:'post',
			data:{node:param},
			success:function(data, status, xhr){
				queryGrid();
			}
		});
        editingId = undefined;
    }
}

function cancelEdit(){
    if (editingId != undefined){
        $('#menu').treegrid('cancelEdit', editingId);
        editingId = undefined;
    }
}
</script>