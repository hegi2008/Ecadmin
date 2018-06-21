<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>角色用户管理</title>
	  <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
	  <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
  </head>
  <body class="gray-bg">
  	<input type="hidden" id="role_id" name="role_id" value="${role.roleId}">
  	<div class="wrapper wrapper-content animated fadeInRight">
  		<div class="row">
  			<div class="col-sm-12">
  				<div class="ibox">
	  				<div class="ibox-title">
	                    <h5>选择人员添加</h5>
	                    <div class="ibox-tools">
	                        <a class="collapse-link">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
	                    </div>
	                </div>
	                <div class="ibox-content">
	                	<form id="form1" class="form-inline m-b" role="form">
	                		<div class="form-group">
						  	  	<label for="user_name" class="sr-only">用户姓名</label>
							  	<input id="user_name" type="text" name="user_name" class="form-control" onchange="queryGrid()" placeholder="用户姓名">
							</div>
							<div class="form-group">
						  	  	<label for="user_phone" class="sr-only">手机号</label>
							  	<input id="user_phone" type="text" name="user_phone" class="form-control" onchange="queryGrid()" placeholder="手机号">
							</div>
							<div class="form-group">
						  	  	<label for="user_status" class="sr-only">是否有效</label>
								<div class="radio m-l m-r-xs">
				                    <label>
				                        <input type="radio" name="user_status" value="1" onchange="queryGrid()" checked="checked"/>有效
				                    </label>
				                </div>
				                <div class="radio m-l m-r-xs">
				                    <label>
				                        <input type="radio" name="user_status" value="female" onchange="queryGrid()"/>无效
				                    </label>
				                </div>
							</div>
							<div class="form-group">
						  	  	<label for="user_lock" class="sr-only">是否锁定</label>
								<div class="radio m-l m-r-xs">
				                    <label>
				                        <input type="radio" name="user_lock" value="1" onchange="queryGrid()"/>未锁定
				                    </label>
				                </div>
				                <div class="radio m-l m-r-xs">
				                    <label>
				                        <input type="radio" name="user_lock" value="0" onchange="queryGrid()"/>锁定
				                    </label>
				                </div>
							</div>
						</form>
						<div style="height: 280px;">
							<ec:grid id="user" title="" queryParams="{user_status:$('input[name=\"user_status\"]:checked').val()}" fit="true" fitColumns="true" showRowno="true" pagination="true" 
							dataurl="${basePath}system/role/roleManager/queryAllRoleUsers" showHeaderContextMenu="true"
							pageSize="5" pageList="[5,10,20]">
								<ec:gridToolbar>
									<button type="button" class="btn btn-outline btn-default btn-sm" onclick="resetQuery();"><i class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询</button>
								</ec:gridToolbar>
								<ec:gridItem itemId="user_id" itemName="user_id" hidden="true"></ec:gridItem>
								<ec:gridItem itemId="user_name" itemName="姓名"></ec:gridItem>
								<ec:gridItem itemId="user_login" itemName="登陆名"></ec:gridItem>
								<ec:gridItem itemId="user_phone" itemName="电话"></ec:gridItem>
								<ec:gridItem itemId="user_email" itemName="邮箱"></ec:gridItem>
								<ec:gridItem itemId="creater_name" itemName="创建人"></ec:gridItem>
								<ec:gridItem itemId="user_create_time" itemName="创建时间" formatter="CommonFormatter.format"></ec:gridItem>
								<ec:gridItem itemId="options" itemName="操作" formatter="optionsf" width="50"></ec:gridItem>
							</ec:grid>
						</div>
	                </div>
  				</div>
  			</div>
  		</div>
  		<div class="row">
  			<div class="col-sm-12">
  				<div class="ibox">
	  				<div class="ibox-title">
	                    <h5>已选择人员</h5>
	                    <div class="ibox-tools">
	                        <a class="collapse-link">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
	                    </div>
	                </div>
	                <div class="ibox-content" style="height: 280px;">
						<ec:grid id="roleUser" title="" fit="true" fitColumns="true" showRowno="true" pagination="true" 
						dataurl="${basePath}system/role/roleManager/queryRoleUsers"	showHeaderContextMenu="true">
							<ec:gridItem itemId="user_id" itemName="user_id" hidden="true"></ec:gridItem>
							<ec:gridItem itemId="user_name" itemName="姓名"></ec:gridItem>
							<ec:gridItem itemId="user_login" itemName="登陆名"></ec:gridItem>
							<ec:gridItem itemId="user_phone" itemName="电话"></ec:gridItem>
							<ec:gridItem itemId="user_email" itemName="邮箱"></ec:gridItem>
							<ec:gridItem itemId="creater_name" itemName="创建人"></ec:gridItem>
							<ec:gridItem itemId="user_create_time" itemName="创建时间" formatter="CommonFormatter.format"></ec:gridItem>
							<ec:gridItem itemId="options" itemName="操作" formatter="optionsr" width="50"></ec:gridItem>
						</ec:grid>
	                </div>
  				</div>
  			</div>
  		</div>
  	</div>
	<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
	<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
	<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
  </body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	queryGrid();
	queryRoleUserGrid();
});

//查询
function queryRoleUserGrid() {
	Base.loadGridData("roleUser",null,{
		role_id: $("#role_id").val()
	});
}

//查询
function queryGrid() {
	Base.loadGridData("user","form1",{
		role_id: $("#role_id").val()
	});
}
//重置查询
function resetQuery() {
	$('#form1')[0].reset();
	queryGrid();
}
//操作
function optionsf(value,data,index){
	var role_name = '${role.roleName}';
	var arr = [];
	arr.push('<a class="btn btn-primary btn-xs" onclick="addUserToRole('+data.user_id+')">添加</a>');
	return arr.join('');
}

//操作
function optionsr(value,data,index){
	var role_name = '${role.roleName}';
	var arr = [];
	arr.push('<a class="btn btn-danger btn-xs" onclick="removeUserFromRole('+data.user_id+')">删除</a>');
	return arr.join('');
}

function addUserToRole(user_id) {
	var role_id = $("#role_id").val();
	var param = {
		user_id : user_id,
		role_id : role_id,
		status : 1
	};
	Base.ajax({
		type:'post',
		url:Base.globvar.basePath + 'system/role/roleManager/updateRoleUser',
		data:param,
		success:function(data){
			queryRoleUserGrid();
			queryGrid();
		}
	});
}

function removeUserFromRole(user_id) {
	var role_id = $("#role_id").val();
	var param = {
		user_id : user_id,
		role_id : role_id,
		status : 0
	};
	Base.ajax({
		type:'post',
		url:Base.globvar.basePath + 'system/role/roleManager/updateRoleUser',
		data:param,
		success:function(data){
			if(!data.error){
				queryRoleUserGrid();
				queryGrid();
			}
		}
	});
}
</script>