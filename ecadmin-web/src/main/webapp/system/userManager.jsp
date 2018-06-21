<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>用户管理</title>
	  <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
	  <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
	  <link rel="stylesheet" href="<%=basePath%>UI/plugins/bootstrap-daterangepicker/daterangepicker.min.css" type="text/css">
  </head>
  <body class="gray-bg">
  	<div class="wrapper wrapper-content animated fadeInRight">
  		<div class="row">
  			<div class="col-sm-12">
  				<div class="ibox">
	  				<div class="ibox-title">
	                    <h5>所有用户</h5>
	                    <!-- <div class="ibox-tools">
	                        <a class="collapse-link">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
	                    </div> -->
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
						  	  	<label for="user_create_time" class="sr-only">创建时间</label>
						  	  	<div class="input-group">
						  	  		<span class="input-group-addon">
								    	<span class="glyphicon glyphicon-calendar"></span>
								    </span>
							  		<input id="user_create_time" type="text" name="user_create_time" class="form-control" onchange="queryGrid()" placeholder="创建时间">
						  	  	</div>
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
						<div style="height: 400px;">
							<ec:grid id="user" title="" queryParams="{user_status:$('input[name=\"user_status\"]:checked').val()}" fit="true" fitColumns="true" showRowno="true" pagination="true" dataurl="${basePath}system/user/userManager/queryUserList" >
								<ec:gridToolbar>
									<button type="button" class="btn btn-primary btn-sm" onclick="createUser()"><i class="fa fa-plus-square"></i>&nbsp;&nbsp;新增用户</button>
									<button type="button" class="btn btn-outline btn-default btn-sm" onclick="$('#form1')[0].reset();queryGrid();"><i class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询</button>
								</ec:gridToolbar>
								<ec:gridItem itemId="user_id" itemName="user_id" hidden="true"></ec:gridItem>
								<ec:gridItem itemId="user_name" itemName="姓名"></ec:gridItem>
								<ec:gridItem itemId="user_login" itemName="登陆名"></ec:gridItem>
								<ec:gridItem itemId="user_phone" itemName="电话"></ec:gridItem>
								<ec:gridItem itemId="user_email" itemName="邮箱"></ec:gridItem>
								<ec:gridItem itemId="creater_name" itemName="创建人"></ec:gridItem>
								<ec:gridItem itemId="user_create_time" itemName="创建时间" formatter="CommonFormatter.format"></ec:gridItem>
								<ec:gridItem itemId="options" itemName="操作" formatter="optionsf" width="140"></ec:gridItem>
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
	<script src="<%=basePath%>UI/plugins/bootstrap-daterangepicker/daterangepicker.min.js"></script>
  </body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	//setUserPager();
	initDateRange();
});

function initDateRange() {
	$('#user_create_time').daterangepicker({
		autoApply: true,
		autoUpdateInput:false,
		// dateLimit: {"days": 7}, //选择范围限制
		// drops: "up or down", // 向上还是向下展开
		// maxDate: //最大可选日期 支持moment
		// minDate: //最小可选日期 支持moment
		// singleDatePicker: true //选择单个日期模式
		ranges: {
	        "今天": [
	            moment(),
	            moment()
	        ],
	        "昨天": [
	            moment().subtract(1,'day'),
	            moment().subtract(1,'day')
	        ],
	        "最近七天": [
	            moment().subtract(6,'day'),
	            moment()
	        ],
	        "最近30天": [
	            moment().subtract(29,'day'),
	            moment()
	        ],
	        "当月": [
	            moment().startOf('month'),
	            moment().endOf('month')
	        ],
	        "上月": [
	            moment().subtract(1,'month').startOf('month'),
	            moment().subtract(1,'month').endOf('month')
	        ]
        }
	},function(start, end, label){
		// 这里是回调函数 此时触发input的值还没更改
	}).on('apply.daterangepicker',function(e,pk){
		$(this).val(pk.startDate.format(pk.locale.format) + pk.locale.separator + pk.endDate.format(pk.locale.format));
		queryGrid();
	});
}

function setUserPager() {
	var pager = $('#user').datagrid('getPager');
    pager.pagination({
        buttons:[{
            iconCls:'fa-commenting',
            handler:function(){
                alert('发送短信');
            }
        },{
            iconCls:'fa-envelope',
            handler:function(){
                alert('发送邮件');
            }
        }]
    });       
}

function queryGrid() {
	Base.loadGridData("user","form1",null);
}

function createUser() {
	var url = Base.globvar.basePath + "system/user/userManager/createUser";
	top.Base.openIframe({
		title:'新增用户',
		href : url,
		width		: '400px',
		height		: '650px',
		afterClose : function(){
			Base.reloadGridData("user");
		}
	});
}

function optionsf(value,data,index){
	var arr = [];
	arr.push('<a class="btn btn-primary btn-xs" onclick="editUser('+data.user_id+')">编辑</a>');
	
	if(data.user_lock == 1){
		arr.push('<a class="btn btn-primary btn-xs" onclick="lockUser('+data.user_id+',0)">锁定</a>');
	}else if(data.user_lock == 0){
		arr.push('<a class="btn btn-danger btn-xs" onclick="lockUser('+data.user_id+',1)">解锁</a>');
	}
	
	if(data.user_status == 1){
		arr.push('<a class="btn btn-primary btn-xs" onclick="disableUser('+data.user_id+',0)">注销</a>');
	}else if(data.user_status == 0){
		arr.push('<a class="btn btn-danger btn-xs" onclick="disableUser('+data.user_id+',1)">启用</a>');
	}
	arr.push('<a class="btn btn-warning btn-xs" onclick="resetPassword('+data.user_id+',1)">重置密码</a>');
	return arr.join(' ');
}

function resetPassword(user_id) {
	Base.confirm("重置后,用户密码将被重置为与登录账号一致,是否确认?",function(yes){
		if(yes){
			Base.ajax({
				url:Base.globvar.basePath + "system/user/userManager/updateResetPassword",
				type:'post',
				mask:false,
				data:{user_id:user_id},
				success:function(data, status, xhr){
					
				}
			});
		}
	});
}

function editUser(user_id){
	var url = Base.globvar.basePath + "system/user/userManager/createUser?user_id="+user_id;
	top.Base.openIframe({
		title:'编辑用户信息',
		href : url,
		width		: '400px',
		height		: '600px',
		afterClose : function(){
			Base.reloadGridData("user");
		}
	});
}

function lockUser(user_id,user_lock){
	var param = {
		user_id:user_id,
		user_lock:user_lock
	}
	Base.ajax({
		url:Base.globvar.basePath + "system/user/userManager/updateUser",
		type:'post',
		mask:false,
		data:param,
		success:function(data, status, xhr){
			Base.reloadGridData("user");
		}
	});
}

function disableUser(user_id,user_status){
	var param = {
		user_id:user_id,
		user_status:user_status
	}
	Base.ajax({
		url:Base.globvar.basePath + "system/user/userManager/updateUser",
		type:'post',
		mask:false,
		data:param,
		success:function(data, status, xhr){
			Base.reloadGridData("user");
		}
	});
}
</script>