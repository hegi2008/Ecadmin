<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>账户管理</title>
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
	                    <h5>所有账户</h5>
	                </div>
	                <div class="ibox-content">
	                	<form id="form1" class="form-inline m-b" role="form">
	                		<div class="form-group">
						  	  	<label for="yad901" class="sr-only">账户ID</label>
							  	<input id="yad901" type="text" name="yad901" class="form-control" onchange="queryGrid()" placeholder="账户ID">
							</div>
							<div class="form-group">
								<label for="yad961" class="sr-only">渠道</label>
								<select class="form-control" id="yad961" name="yad961" onchange="queryGrid()" >
									<option value="">请选择渠道</option>
									<c:forEach var="type" items="${qd_type}">
										<option value="${type.codeValue}">${type.codeValueName}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
						  	  	<label for="create_time" class="sr-only">创建时间</label>
						  	  	<div class="input-group">
						  	  		<span class="input-group-addon">
								    	<span class="glyphicon glyphicon-calendar"></span>
								    </span>
							  		<input id="create_time" type="text" name="create_time" class="form-control" onchange="queryGrid()" placeholder="创建时间">
						  	  	</div>
							</div>
						</form>
						<div style="height: 400px;">
							<ec:grid id="account" title=""  fit="true" fitColumns="true" showRowno="true" pagination="true" dataurl="${basePath}shh/account/accountManager/queryAccountList"  showck="true" singleSelect="false">
								<ec:gridToolbar>
									<button type="button" class="btn btn-primary btn-sm" onclick="createAccout()"><i class="fa fa-plus-square"></i>&nbsp;&nbsp;新增账户</button>
									<button type="button" class="btn btn-outline btn-default btn-sm" onclick="$('#form1')[0].reset();queryGrid();"><i class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询</button>
								</ec:gridToolbar>
								<ec:gridItem itemId="yad901" itemName="账户ID"></ec:gridItem>
								<ec:gridItem itemId="yad961" itemName="渠道" collection="QD_TYPE"></ec:gridItem>
								<ec:gridItem itemId="des" itemName="账号描述"></ec:gridItem>
								<ec:gridItem itemId="create_time" itemName="创建时间" formatter="CommonFormatter.format"></ec:gridItem>
								<ec:gridItem itemId="update_time" itemName="最后更新时间" formatter="CommonFormatter.format"></ec:gridItem>
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
	initDateRange();
});

function initDateRange() {
	$('#create_time').daterangepicker({
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

function queryGrid() {
	Base.loadGridData("account","form1",null);
}

function createAccout() {
	var url = Base.globvar.basePath + "shh/account/accountManager/createAccount";
	top.Base.openIframe({
		title:'新增用户',
		href : url,
		width		: '400px',
		height		: '650px',
		afterClose : function(){
			Base.reloadGridData("account");
		}
	});
}

function optionsf(value,data,index){
	var arr = [];
	arr.push('<a class="btn btn-primary btn-xs" onclick="editAccount('+data.yad901+','+data.yad961+')">编辑</a>');
	arr.push('<a class="btn btn-warning btn-xs" onclick="getMoreBindInfo('+data.yad901+','+data.yad961+')">绑定详情</a>');
	return arr.join(' ');
}

function getMoreBindInfo(yad901,yad961) {
    var url = Base.globvar.basePath + "shh/account/accountManager/getMoreBindInfo?yad901="+yad901+"&yad961="+yad961;
    top.Base.openIframe({
        title:'新增用户',
        href : url,
        width		: '400px',
        height		: '650px',
        afterClose : function(){
            Base.reloadGridData("account");
        }
    });
}

function editAccount(yad901,yad961){
	var url = Base.globvar.basePath + "shh/account/accountManager/createAccount?yad901="+yad901+"&yad961="+yad961;
	top.Base.openIframe({
		title:'编辑用户信息',
		href : url,
		width		: '400px',
		height		: '600px',
		afterClose : function(){
			Base.reloadGridData("account");
		}
	});
}

</script>