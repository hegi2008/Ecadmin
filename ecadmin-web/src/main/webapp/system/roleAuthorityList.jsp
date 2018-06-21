<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>角色权限详情</title>
		<%@ include file="/UI/themes/hplus/head.inc.jsp" %>
		<link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>UI/plugins/bootstrapswitch/bootstrap-switch.min.css">
	</head>
  <body class="gray-bg">
  	<input type="hidden" id="role_id" name="role_id" value="${role.roleId}">
  	<div class="wrapper wrapper-content animated fadeInRight" style="height: 100%;">
		<ec:grid id="roleList" title="" fit="true" fitColumns="true" showRowno="true" onLoadSuccess="fnRoleLoaded">
			<ec:gridItem itemId="role_id" itemName="role_id" hidden="true"></ec:gridItem>
			<ec:gridItem itemId="menu_id" itemName="menu_id" hidden="true"></ec:gridItem>
			<ec:gridItem itemId="menu_name" itemName="菜单名称"></ec:gridItem>
			<ec:gridItem itemId="menu_url" itemName="菜单路劲"></ec:gridItem>
			<ec:gridItem itemId="role_create" itemName="增" formatter="optionsc"></ec:gridItem>
			<ec:gridItem itemId="role_delete" itemName="删" formatter="optionsd"></ec:gridItem>
			<ec:gridItem itemId="role_query" itemName="查" formatter="optionsq"></ec:gridItem>
			<ec:gridItem itemId="role_update" itemName="改" formatter="optionsu"></ec:gridItem>
		</ec:grid>
  	</div>
	<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
	<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
	<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
	<script src="<%=basePath%>UI/plugins/bootstrapswitch/bootstrap-switch.min.js"></script>
  </body>
</html>
<script type="text/javascript">
	$(document).ready(function(){
		var list = '${roleList}';
		Base.setDatagridData("roleList",eval('('+list+')'));
	});
	
	function fnRoleLoaded(data){
		$("[name='my-checkbox']").bootstrapSwitch({"size": "mini", "offColor" : "warning", "onSwitchChange" : function(event, state) {
			event.preventDefault();
			var role_id =  $(this).attr("_role_id");
			var menu_id = $(this).attr("_menu_id");
			var field = $(this).attr("_field");
			var status;
			if(state){
				status = 1;
			}else{
				status = 0;
			}
			Base.ajax({
				url:"updateAuthorityOne",
				type:'post',
				data:{role_id:role_id,menu_id:menu_id,status:status,field:field},
				success:function(data, status, xhr){
					
				}
			});
		}});
	}
	
	function optionsc(value,data,index){
		var arr = "";
		var status = value;
		var role_id = data.role_id;
		var menu_id = data.menu_id;
		if(status == 0){
			arr = '<div class="switch"> <input type="checkbox" name="my-checkbox" _field="role_create" _menu_id='+menu_id+'  _role_id='+role_id+' _status="0"/></div>';
		} else {
			arr = '<div class="switch"> <input type="checkbox" name="my-checkbox" _field="role_create" _menu_id='+menu_id+'  _role_id='+role_id+' _status="1" checked /></div>';
		}
		return arr;
	}
	
	function optionsd(value,data,index){
		var arr = "";
		var status = value;
		var role_id = data.role_id;
		var menu_id = data.menu_id;
		if(status == 0){
			arr = '<div class="switch"> <input type="checkbox" name="my-checkbox" _field="role_delete" _menu_id='+menu_id+'  _role_id='+role_id+' _status="0"/></div>';
		} else {
			arr = '<div class="switch"> <input type="checkbox" name="my-checkbox" _field="role_delete" _menu_id='+menu_id+'  _role_id='+role_id+' _status="1" checked /></div>';
		}
		return arr;
	}
	function optionsq(value,data,index){
		var arr = "";
		var status = value;
		var role_id = data.role_id;
		var menu_id = data.menu_id;
		if(status == 0){
			arr = '<div class="switch"> <input type="checkbox" name="my-checkbox" _field="role_query" _menu_id='+menu_id+'  _role_id='+role_id+' _status="0"/></div>';
		} else {
			arr = '<div class="switch"> <input type="checkbox" name="my-checkbox" _field="role_query" _menu_id='+menu_id+'  _role_id='+role_id+' _status="1" checked /></div>';
		}
		return arr;
	}
	function optionsu(value,data,index){
		var arr = "";
		var status = value;
		var role_id = data.role_id;
		var menu_id = data.menu_id;
		if(status == 0){
			arr = '<div class="switch"> <input type="checkbox" name="my-checkbox" _field="role_update" _menu_id='+menu_id+'  _role_id='+role_id+' _status="0"/></div>';
		} else {
			arr = '<div class="switch"> <input type="checkbox" name="my-checkbox" _field="role_update" _menu_id='+menu_id+'  _role_id='+role_id+' _status="1" checked /></div>';
		}
		return arr;
	}
</script>