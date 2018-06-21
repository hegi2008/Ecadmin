<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
	  <title>角色管理</title>
	  <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
	  <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
	  <style type="text/css">
		  .datagrid-cell {
			  overflow: visible;
		  }
	  </style>
  </head>
  <body class="gray-bg">
  	<div class="wrapper wrapper-content animated fadeInRight">
  		<div class="row">
  			<div class="col-sm-12">
  				<div class="ibox">
	  				<div class="ibox-title">
	                    <h5>角色列表</h5>
	                    <!-- <div class="ibox-tools">
	                        <a class="collapse-link">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
	                    </div> -->
	                </div>
	                <div class="ibox-content">
	                	<form id="form1" class="form-inline m-b" role="form">
	                		<div class="form-group">
			  	  				<label for="role_name" class="sr-only">角色名字</label>
				  				<input id="role_name" type="text" name="role_name" class="form-control" onchange="queryGrid()" placeholder="角色名称">
							</div>
							<div class="form-group">
					  	  		<label for="role_type" class="sr-only">角色类型</label>
						  		<select class="form-control" id="role_type" name="role_type" onChange="queryGrid()">
									<option value="">请选择角色类型</option>
									<c:forEach var="type" items="${role_type}">
									    <option value="${type.codeValue}">${type.codeValueName}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
						  	  	<label for="user_lock" class="sr-only">是否有效</label>
								<div class="radio m-l m-r-xs">
				                    <label>
				                        <input type="radio" name="role_status" value="1" onchange="queryGrid()" checked="checked"/>有效
				                    </label>
				                </div>
				                <div class="radio m-l m-r-xs">
				                    <label>
				                        <input type="radio" name="role_status" value="0" onchange="queryGrid()"/>无效
				                    </label>
				                </div>
							</div>
						</form>
						<div style="height: 460px;">
							<ec:grid id="role" hasComplicateHeader="true" showRowno="true" queryParams="{role_status:$('input[name=\"role_status\"]:checked').val()}" fitColumns="true" fit="true" dataurl="${basePath}system/role/roleManager/queryRoleList">
								<ec:gridToolbar>
									<button type="button" class="btn btn-primary btn-sm" onclick="createRole()"><i class="fa fa-plus-square"></i>&nbsp;&nbsp;新增角色</button>
									<button type="button" class="btn btn-outline btn-default btn-sm" onclick="queryGrid()"><i class="fa fa-refresh"></i>&nbsp;&nbsp;刷新</button>
									<button type="button" class="btn btn-outline btn-default btn-sm" onclick="resetQuery()"><i class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询</button>
								</ec:gridToolbar>
								<ec:gridItem itemId="" itemName="基本信息，菜单权限" colspan="6"/>
								<ec:gridItem itemId="" itemName="数据权限" colspan="6"/>
								<ec:gridItem itemId="role_id" itemName="角色Id" hidden="true"/>
								<ec:gridItem itemId="role_creater" itemName="角色创建人" hidden="true"/>
								<ec:gridItem itemId="role_name" itemName="角色名称" editorType="text"/>
								<ec:gridItem itemId="role_string" itemName="角色验证字符" editorType="text"/>
								<ec:gridItem itemId="role_type" itemName="角色类型" editorType="combobox" collection="ROLE_TYPE"/>
								<ec:gridItem itemId="role_creater_name" itemName="角色创建人"></ec:gridItem>
								<ec:gridItem itemId="role_create_time" itemName="角色创建时间" formatter="CommonFormatter.format"/>
								<ec:gridItem itemId="options" itemName="操作" formatter="optionsf" align="center" width="80"/>
								<ec:gridItem itemId="optionsCreate" itemName="增" formatter="optionsCreate" align="center" width="40"/>
								<ec:gridItem itemId="optionsDelete" itemName="删" formatter="optionsDelete" align="center" width="40"/>
								<ec:gridItem itemId="optionsQuery" itemName="查" formatter="optionsQuery" align="center" width="40"/>
								<ec:gridItem itemId="optionsUpdate" itemName="改" formatter="optionsUpdate" align="center" width="40"/>
							</ec:grid>
						</div>
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
	//Base.acceptDatagridChanges("role");
});

//查询
function queryGrid() {
	Base.loadGridData("role","form1",null);
}
//重置查询
function resetQuery() {
	$('#form1')[0].reset();
	queryGrid();
}

function optionsCreate(value,data,index) {
	return '<i class="fa fa-edit" style="color:#1AB394;font-size:16px;" onclick="editDataAuthority('+data.role_id+',\'create\',\''+data.role_name+'\')"></i>';
}

function optionsDelete(value,data,index) {
	return '<i class="fa fa-edit" style="color:#1AB394;font-size:16px;" onclick="editDataAuthority('+data.role_id+',\'delete\',\''+data.role_name+'\')"></i>';
}

function optionsQuery(value,data,index) {
	return '<i class="fa fa-edit" style="color:#1AB394;font-size:16px;" onclick="editDataAuthority('+data.role_id+',\'query\',\''+data.role_name+'\')"></i>';
}

function optionsUpdate(value,data,index) {
	return '<i class="fa fa-edit" style="color:#1AB394;font-size:16px;" onclick="editDataAuthority('+data.role_id+',\'update\',\''+data.role_name+'\')"></i>';
}

function editDataAuthority(role_id,type,role_name) {
	var url = Base.globvar.basePath + "system/role/roleManager/editDataAuthority?role_id="+role_id+"&type="+type;
	var options = {
		title:'['+role_name+']('+type.toUpperCase()+')权限',
		href : url,
		width		: '1000px',
		height		: '700px',
		afterClose : function(){
			//Base.reloadGridData("role");
		}
	};
	top.Base.openIframe(options);
} 

//操作
function optionsf(value,data,index){
	var arr = [];
	/* arr.push('<a class="btn btn-primary btn-xs" onclick="managerRoleUser('+data.role_id+')">角色人员</a>');
	arr.push('<a class="btn btn-danger btn-xs" onclick="deleteRole('+data.role_id+')">删除角色</a>');
	arr.push('<a class="btn btn-primary btn-xs" onclick="authManage(\''+data.role_id+'\')">菜单权限</a>');
	arr.push('<a class="btn btn-info btn-xs" onclick="authList(\''+data.role_id+'\')">权限详情</a>'); */
	arr.push('<div class="dropdown">');
	arr.push('<button class="btn btn-white btn-xs dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作<span class="caret"></span></button>');
	arr.push(createDropdownUl(data.role_id));
	arr.push('</div>');
	return arr.join(' ');
}

function createDropdownUl(role_id){
	var arr = [];
	arr.push('<ul class="dropdown-menu animated fadeInRight m-t-xs">');
	arr.push('<li><a href="javascript:void(0)" onclick="managerRoleUser('+role_id+')">管理角色人员</a></li>');
	arr.push('<li><a href="javascript:void(0)" onclick="createRole('+role_id+')">编辑角色</a></li>');
	arr.push('<li><a href="javascript:void(0)" onclick="deleteRole('+role_id+')">删除角色</a></li>');
	arr.push('<li role="separator" class="divider"></li>')
	arr.push('<li><a href="javascript:void(0)" onclick="authManage('+role_id+')">菜单权限</a></li>');
	arr.push('<li><a href="javascript:void(0)" onclick="authList('+role_id+')">菜单权限详情</a></li>');
	arr.push('</ul>');
	return arr.join('');
}

function createRole(role_id) {
	var url = Base.globvar.basePath + "system/role/roleManager/createRole";
	var options = {
		title:'新增角色',
		href : url,
		width		: '400px',
		height		: '450px',
		afterClose : function(){
			Base.reloadGridData("role");
		}
	};
	
	if(role_id){
		url += "?role_id="+role_id;
		options.title = "编辑角色";
		options.href = url;
	}
	top.Base.openIframe(options);
}

function authManage(role_id){
	var url = Base.globvar.basePath + "system/role/roleManager/viewRoleAuthorityDetail?role_id="+role_id;
	top.Base.openIframe({
		title:'角色菜单授权',
		href : url,
		width		: '400px',
		height		: '600px',
		afterClose : function(){
			//Base.reloadGridData("user");
		}
	});
}

function authList(role_id){
	var url = Base.globvar.basePath + "system/role/roleManager/viewAuthorityList?role_id="+role_id;
	top.Base.openIframe({
		title:'角色菜单权限详情',
		href : url,
		width		: '1000px',
		height		: '600px',
		afterClose : function(){
			//Base.reloadGridData("user");
		}
	});
}

// 管理角色人员
function managerRoleUser(role_id){
	var url = Base.globvar.basePath + "system/role/roleManager/viewAllUsers?role_id="+role_id;
	top.Base.openIframe({
		title:'角色人员管理',
		href : url,
		width		: '1000px',
		height		: '650px',
		afterClose : function(){
			Base.reloadGridData("user");
		}
	});
}

//删除一行
function deleteRole(role_id){
	var row = Base.getDatagridSelected("role");
	var index = Base.getDatagridRowIndex("role",row);
	if(!role_id){
		Base.deleteDatagridRow("role",index);
		return;
	}
	Base.confirm("确认删除该角色吗?",function(yes){
		if(yes){
			Base.ajax({
				url:Base.globvar.basePath + "system/role/roleManager/deleteRole",
				type:'post',
				data:{role_id:role_id},
				success:function(data, status, xhr){
					if(!data.error){
						queryGrid();
					}
				}
			});
		}
	});
}
</script>