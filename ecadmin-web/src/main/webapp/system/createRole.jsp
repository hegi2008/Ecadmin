<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>新增角色</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath %>UI/plugins/bootstrapvalidator/bootstrapValidator.min.css" rel="stylesheet">
  </head>
  <body>
	<div class="wrapper wrapper-content animated fadeInUp" style="padding-left: 30px;padding-right: 30px;">
		<form id="form1" method="post" class="form-horizontal">
			<input id="role_id" name="role_id" type="hidden" value="${role.role_id}">
			<div class="form-group">
				<label for="role_name" class="control-label"><span style="color: red;">*</span>角色名称</label>
              	<input id="role_name" name="role_name" type="text" class="form-control" placeholder="请输入角色名称" value="${role.role_name}"/>
			</div>
			<div class="form-group">
				<input type="hidden" id="org_id" name="org_id" value="${role.org_id}">
				<label for="org_name" class="control-label"><span style="color: red;">*</span>所属部门</label>
              	<input id="org_name" name="org_name" type="text" class="form-control" placeholder="请选择部门" value="${role.org_name}" readonly="readonly" onclick="myOrgNameClick()"/>
              	<div id="orgTreeBox" style="display: none;position: absolute;box-shadow: 0 0 3px rgba(86,96,117,.3);border: medium none;background-color: #FFFFFF;border-radius: 0;z-index: 1001;
              	font-size: 12px !important;">
              		<%@ include file="orgRadioSelect.jsp" %>
              	</div>
			</div>
			<div class="form-group">
				<label for="role_string" class="control-label"><span style="color: red;">*</span>角色字符串</label>
              	<input id="role_string" name="role_string" type="text" class="form-control" placeholder="请输入角色字符串" value="${role.role_string}"/>
			</div>
			<div class="form-group">
				<label for="role_type" class="control-label"><span style="color: red;">*</span>角色类型</label>
              	<select class="form-control" id="role_type" name="role_type">
              		<c:forEach var="type" items="${role_types}">
              			<c:if test="${type.codeValue == role.role_type}">
              				<option value="${type.codeValue}" selected="true">${type.codeValueName}</option>
              			</c:if>
              			<c:if test="${type.codeValue != role.role_type}">
					   		<option value="${type.codeValue}">${type.codeValueName}</option>
              			</c:if>
					</c:forEach>
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
				url:Base.globvar.basePath + "system/role/roleManager/saveRole",
				type:'post',
				data:param,
				success:function(data, status, xhr){
				}
			});
		},
        fields: {
        	role_name: {
        		validators: {
        			notEmpty: {
        				message:"角色名不能为空"
        			},
        			stringLength: {
                        max: 25,
                        message: "最多输入25个字"
                    }
        		}
        	},
        	org_name: {
        		validators: {
        			notEmpty: {
        				message:"请选择部门"
        			}
        		}
        	},
        	org_id: {
        		validators: {
        			notEmpty: {
        				message:"请选择部门"
        			}
        		}
        	},
        	role_string: {
        		validators: {
        			notEmpty: {
        				message:"角色字符串不能为空"
        			},
        			stringLength: {
                        max: 25,
                        message: "最多输入25个字"
                    }
        		}
        	},
        	role_type: {
        		validators: {
        			notEmpty: {
        				message:"请选择角色类型"
        			}
        		}
        	}
        }
	});
}
</script>