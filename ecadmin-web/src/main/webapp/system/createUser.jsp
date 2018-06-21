<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>新增用户</title>
	  <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath %>UI/plugins/bootstrapvalidator/bootstrapValidator.min.css" rel="stylesheet">
  </head>
  <body>
	<div class="wrapper wrapper-content animated fadeInUp" style="padding-left: 30px;padding-right: 30px;">
		<form id="form1" method="post" class="form-horizontal">
			<input id="user_id" name="user_id" type="hidden" value="${user.userId}">
			<div class="form-group">
				<label for="user_name" class="control-label"><span style="color: red;">*</span>姓名</label>
              	<input id="user_name" name="user_name" type="text" class="form-control" placeholder="请输入姓名" value="${user.userName}"/>
			</div>
			<div class="form-group">
				<input type="hidden" id="org_id" name="org_id" value="${user.orgId}">
				<label for="org_name" class="control-label"><span style="color: red;">*</span>部门</label>
              	<input id="org_name" name="org_name" type="text" class="form-control" placeholder="请选择部门" value="${user.orgName}" readonly="readonly" onclick="myOrgNameClick()"/>
              	<div id="orgTreeBox" style="display: none;position: absolute;box-shadow: 0 0 3px rgba(86,96,117,.3);border: medium none;background-color: #FFFFFF;border-radius: 0;z-index: 1001;
              	font-size: 12px !important;">
              		<%@ include file="orgRadioSelect.jsp" %>
              	</div>
			</div>
			<div class="form-group">
				<label for="user_login" class="control-label"><span style="color: red;">*</span>登陆名</label>
				<c:if test="${not empty user.userId}">
					<input id="user_login" name="user_login" type="text" class="form-control" placeholder="请输入登陆名" value="${user.userLogin}" readonly/>
				</c:if>
				<c:if test="${empty user.userId}">
              		<input id="user_login" name="user_login" type="text" class="form-control" placeholder="请输入登陆名" value="${user.userLogin}"/>
              	</c:if>
			</div>
			<c:if test="${empty user.userPwd}">
			<div class="form-group">
				<label for="user_pwd" class="control-label"><span style="color: red;">*</span>登陆密码</label>
              	<input id="user_pwd" name="user_pwd" type="password" class="form-control" placeholder="请输入密码"/>
			</div>
			</c:if>
			<div class="form-group">
				<label for="user_phone" class="control-label"><span style="color: red;">*</span>手机号</label>
              	<input id="user_phone" name="user_phone" type="text" class="form-control" placeholder="请输入手机号" value="${user.userPhone}"/>
			</div>
			<div class="form-group">
				<label for="user_email" class="control-label"><span style="color: red;">*</span>邮箱</label>
              	<input id="user_email" name="user_email" type="text" class="form-control" placeholder="请输入邮箱" value="${user.userEmail}"/>
			</div>
			<div class="form-group">
				<label for="user_sex" class="control-label">性别</label>
              	<select class="form-control" id="user_sex" name="user_sex">
              		<c:choose>
              			<c:when test="${user.userSex == 0}">
					    	<option value="0" selected="true">未知</option>
	              		</c:when>
	              		<c:otherwise>
	              			<option value="0">未知</option>
	              		</c:otherwise>
              		</c:choose>
              		<c:choose>
              			<c:when test="${user.userSex == 1}">
					    	<option value="1" selected="true">男</option>
	              		</c:when>
	              		<c:otherwise>
	              			<option value="1">男</option>
	              		</c:otherwise>
              		</c:choose>
              		<c:choose>
              			<c:when test="${user.userSex == 2}">
					    	<option value="2" selected="true">女</option>
	              		</c:when>
	              		<c:otherwise>
	              			<option value="2">女</option>
	              		</c:otherwise>
              		</c:choose>
				</select>
			</div>
			<div class="col-sm-12 col-lg-12 text-center">
				<button type="submit" class="btn btn-primary">提交</button>
				<button type="reset" class="btn btn-outline btn-default" onclick="resetForm();">重置</button>
			</div>
		</form>
	</div>
	<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
	<script src="<%=basePath%>UI/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
  </body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	initForm();
});

function hideOrgTree() {
	$("#orgTreeBox").css("display","none");
}

function showOrgTree() {
	$("#orgTreeBox").css("display","block");
}

function isOrgTreeShow() {
	return $("#orgTreeBox").css("display") == "block";
}

function myOrgNameClick() {
	if(isOrgTreeShow()){
		hideOrgTree();
	}else{
		showOrgTree();
	}
}

function resetForm(){
	$('#form1').data('bootstrapValidator').resetForm();
}

function afterOrgRadioSelected(treeId, treeNode) {
	$("#org_id").val(treeNode.id);
	$("#org_name").val(treeNode.orgNamePath);
	hideOrgTree();
}

function initForm(){
	// 表单验证初始化
	$("#form1").bootstrapValidator({
		excluded: ':disabled',
		message: '该项有错,请检查',
		submitHandler: function(validator,form,submitButton){
			var param = form.serializeArray();
			Base.ajax({
				url:Base.globvar.basePath + "system/user/userManager/saveUser",
				type:'post',
				data:param,
				success:function(data, status, xhr){
				}
			});
		},
        fields: {
        	user_name: {
        		validators: {
        			notEmpty: {
        				message:"用户名不能为空"
        			},
        			stringLength: {
                        max: 25,
                        message: "最多输入25个字"
                    }
        		}
        	},
        	user_login: {
        		validators: {
        			notEmpty: {
        				message:"登陆名不能为空"
        			},
        			stringLength: {
                        max: 25,
                        message: "最多输入25个字"
                    }
        		}
        	},
        	user_pwd: {
        		validators: {
        			notEmpty: {
        				message:"密码不能为空"
        			},
        			stringLength: {
                        max: 25,
                        message: "最多输入25个字"
                    }
        		}
        	},
        	user_phone: {
        		validators: {
        			notEmpty: {
        				message:"手机号不能为空"
        			},
        			phone: {
        				message: "请输入有效的手机号码"
        			}
        		}
        	},
        	user_email: {
        		validators: {
        			notEmpty: {
        				message:"邮箱不能为空"
        			},
        			emailAddress: {
                        message: "请输入有效的邮箱地址"
                    }
        		}
        	},
        	org_name: {
        		validators: {
        			notEmpty: {
        				message:"部门不能为空"
        			}
        		}
        	}
        }
	});
}
</script>