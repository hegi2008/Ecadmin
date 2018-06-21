<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>数据权限列表</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/iCheck/custom.css" rel="stylesheet">
  </head>
  <body class="gray-bg">
  	<input id="type" type="hidden" value="${type}">
  	<input id="role_id" type="hidden" value="${role_id}">
  	<div class="wrapper wrapper-content animated fadeInRight">
  		<div class="ibox">
  			<div class="ibox-title">
                <h5>系统相关</h5>
                <div class="ibox-tools">
                	<a href="javascript:void(0);" class="btn btn-primary btn-xs" onclick="submitForm('form1');">提交</a>
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-wrench"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="javascript:void(0);" onclick="checkAllAuthoritys('form1')">全部选中</a>
                        </li>
                        <li><a href="javascript:void(0);" onclick="uncheckAllAuthoritys('form1')">全部取消</a>
                        </li>
                    </ul>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
            	<form id="form1" class="form-horizontal" role="form">
            		<div class="form-group">
            			<c:forEach var="sys" items="${systems}">
            				<div class="col-sm-3">
	                            <div class="checkbox i-checks">
	                                <label>
	                                    <input type="checkbox" class="checkbox" id="${sys.table_name}" name="systems" value="${sys.table_name}"><i></i>${sys.table_comment}<%-- (${sys.table_name}) --%>
	                                </label>
	                            </div>
	                        </div>
            			</c:forEach>
                    </div>
            	</form>
            </div>
  		</div>
  		<div class="ibox">
  			<div class="ibox-title">
                <h5>业务相关</h5>
                <div class="ibox-tools">
                	<a href="javascript:void(0);" class="btn btn-primary btn-xs" onclick="submitForm('form2');">提交</a>
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-wrench"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="javascript:void(0);" onclick="checkAllAuthoritys('form2')">全部选中</a>
                        </li>
                        <li><a href="javascript:void(0);" onclick="uncheckAllAuthoritys('form2')">全部取消</a>
                        </li>
                    </ul>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
            	<form id="form2" class="form-horizontal" role="form">
            		<div class="form-group">
                        <div class="form-group">
	            			<c:forEach var="app" items="${apps}">
	            				<div class="col-sm-3">
		                            <div class="checkbox">
		                                <label>
		                                    <input type="checkbox" class="checkbox" id="${app.table_name}" name="apps" value="${app.table_name}">${app.table_comment}<%-- (${app.table_name}) --%>
		                                </label>
		                            </div>
		                        </div>
	            			</c:forEach>
	                    </div>
                    </div>
            	</form>
            </div>
  		</div>
  	</div>
	<%@include file="/UI/themes/hplus/basic.js.inc.jsp"%>
    <script src="<%=basePath%>UI/plugins/iCheck/icheck.min.js"></script>
  </body>
</html>
<script type="text/javascript">
//$("#id").iCheck('check,uncheck,disable,enable,destroy');
var authoritys = '${authoritys}';
$(document).ready(function(){
	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
	initAuthoritys();
});

function initAuthoritys() {
	if(Base.isExist(authoritys)){
		authoritys = eval('('+authoritys+')');
		var str = [];
		for(var i in authoritys){
			var authority = authoritys[i];
			str.push("#"+authority.table_name);
		}
		$(str.join(',')).iCheck('check');
	}
}

function submitForm(formid) {
	var param = $('#'+formid).serializeArray();
	param[param.length] = {
		name: 'type',
		value: $('#type').val()
	}
	param[param.length] = {
		name: 'role_id',
		value: $('#role_id').val()
	}
	Base.ajax({
		url:Base.globvar.basePath + "system/role/roleManager/saveRoleDataAuthority",
		type:'post',
		data:param,
		success:function(data, status, xhr){
		}
	});
}

function checkAllAuthoritys(formid) {
	$('#'+formid+' input').iCheck('check');
}

function uncheckAllAuthoritys(formid) {
	$('#'+formid+' input').iCheck('uncheck');
}
</script>