<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE HTML>
<html>
  <head>
	  <title>码表管理</title>
	  <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
	  <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
	  <link href="<%=basePath %>UI/plugins/bootstrapvalidator/bootstrapValidator.min.css" rel="stylesheet">
  </head>
  <body class="gray-bg">
  	<div class="wrapper wrapper-content animated fadeInRight">
  		<div class="row">
  			<div class="col-sm-12">
  				<div class="ibox">
	  				<div class="ibox-title">
	                    <h5>代码表列表</h5>
	                    <!-- <div class="ibox-tools">
	                        <a class="collapse-link">
                            	<i class="fa fa-chevron-up"></i>
                            </a>
	                    </div> -->
	                </div>
	                <div class="ibox-content">
	                	<form id="form1" class="form-inline m-b" role="form">
	                		<div class="form-group">
						  	  	<label for="codeName" class="sr-only">码表名称</label>
							  	<input id="codeName" type="text" name="codeName" class="form-control" onchange="queryGrid()" placeholder="码表名称">
							</div>
							<div class="form-group">
						  	  	<label for="codeString" class="sr-only">码表类别</label>
							  	<input id="codeString" type="text" name="codeString" class="form-control" onchange="queryGrid()" placeholder="码表类别">
							</div>
							<div class="form-group">
						  	  	<label for="codeStatus" class="sr-only">是否有效</label>
								<div class="radio m-l m-r-xs">
				                    <label>
				                        <input type="radio" name="codeStatus" value="1" onchange="queryGrid()"/>有效
				                    </label>
				                </div>
				                <div class="radio m-l m-r-xs">
				                    <label>
				                        <input type="radio" name="codeStatus" value="0" onchange="queryGrid()"/>无效
				                    </label>
				                </div>
							</div>
						</form>
						<div style="height: 610px;">
							<ec:grid id="code" title="" fit="true" fitColumns="true" showRowno="true" pagination="true" pageSize="20" dataurl="${basePath}system/appcode/appcodeManager/queryCodeList" 
							roweditable="true">
								<ec:gridToolbar>
									<button type="button" class="btn btn-primary btn-sm" onclick="createCode()"><i class="fa fa-plus-square"></i>&nbsp;&nbsp;新增码表</button>
									<button type="button" class="btn btn-primary btn-sm" onclick="refreshAllCode()"><i class="fa fa-refresh"></i>&nbsp;&nbsp;刷新所有码表</button>
									<button type="button" class="btn btn-outline btn-default btn-sm" onclick="$('#form1')[0].reset();queryGrid();"><i class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询</button>
								</ec:gridToolbar>
								<ec:gridItem itemId="codeName" itemName="名称" sortable="true" editorType="text"></ec:gridItem>
								<ec:gridItem itemId="codeString" itemName="类别" sortable="true" editorType="text"></ec:gridItem>
								<ec:gridItem itemId="codeValue" itemName="值"></ec:gridItem>
								<ec:gridItem itemId="codeValueName" itemName="描述" editorType="text"></ec:gridItem>
								<ec:gridItem itemId="codeType" itemName="类型" collection="CODE_TYPE" sortable="true" editorType="combobox"></ec:gridItem>
								<ec:gridItem itemId="codeCreateTime" itemName="创建时间" formatter="CommonFormatter.format" sortable="true"></ec:gridItem>
								<ec:gridItem itemId="options" itemName="操作" formatter="optionsf"></ec:gridItem>
							</ec:grid>
						</div>
	                </div>
  				</div>
  			</div>
  		</div>
  	</div>
  	<div id="change-passwod-box" class="container"
		style="width:350px; height:320px; padding: 10px; overflow: hidden;display: none;overflow-y: scroll;">
		<form id="codeForm" class="form-horizontal" action="#">
			<div class="form-group">
				<label for="codeName" class="col-sm-3 control-label"><span style="color: red;">*</span>名称</label>
				<div class="col-sm-9">
					<input id="codeName" name="codeName" type="text" class="form-control"
						placeholder="名称">
				</div>
			</div>
			<div class="form-group">
				<label for="codeString" class="col-sm-3 control-label"><span style="color: red;">*</span>类别</label>
				<div class="col-sm-9">
					<input id="codeString" name="codeString" type="text" class="form-control"
						placeholder="类别">
				</div>
			</div>
			<div class="form-group">
				<label for="codeValueName" class="col-sm-3 control-label"><span style="color: red;">*</span>描述</label>
				<div class="col-sm-9">
					<input id="codeValueName" name="codeValueName" type="text" class="form-control"
						placeholder="描述">
				</div>
			</div>
			<div class="form-group">
				<label for="codeValue" class="col-sm-3 control-label"><span style="color: red;">*</span>值</label>
				<div class="col-sm-9">
					<input id="codeValue" name="codeValue" type="text" class="form-control"
						placeholder="值">
				</div>
			</div>
			<div class="form-group">
	  	  		<label for="codeType" class="col-sm-3 control-label"><span style="color: red;">*</span>类型</label>
	  	  		<div class="col-sm-9">
	  	  			<select class="form-control" id="codeType" name="codeType">
						<c:forEach var="type" items="${code_type}">
						    <option value="${type.codeValue}">${type.codeValueName}</option>
						</c:forEach>
					</select>
	  	  		</div>
			</div>
			<div class="form-group text-center">
				<button id="btn-save-password" type="submit" class="btn btn-primary">提交</button>
				<button id="btn-save-password" type="button" class="btn btn-outline btn-default" onclick="resetCodeForm()">重填</button>
			</div>
		</form>
	</div>
	<%@ include file="/UI/themes/hplus/basic.js.inc.jsp" %>
    <script src="<%=basePath%>UI/plugins/bootstrapvalidator/bootstrapValidator.min.js"></script>
    <script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
    <script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
  </body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	initForm();
});


function queryGrid() {
	Base.loadGridData("code","form1",null);
}

function resetCodeForm(){
	$('#codeForm')[0].reset();
	$('#codeForm').data('bootstrapValidator').resetForm();
}

//新增一行
function createCode(){
	layer.open({
	    type: 1,
	    shade: false,
	    title: '新增码表', //不显示标题
	    content: $("#change-passwod-box"), //捕获的元素
	    cancel: function(index){
	    	resetCodeForm();
	        layer.close(index);
	        this.content.hide();
	    }
	});
}

function initForm(){
	// 表单验证初始化
	$("#codeForm").bootstrapValidator({
		excluded: ':disabled',
		message: '该项有错,请检查',
		submitHandler: function(validator,form,submitButton){
			var param = form.serializeArray();
		   	Base.ajax({
		   	 	url:Base.globvar.basePath + "system/appcode/appcodeManager/saveCode",
		   		type:'post',
		   		data:param,
		   		success:function(data, status, xhr){
		   			if(!data.error){
		   				Base.reloadGridData("code");
		   			}
		   		}
		   	});
		},
        fields: {
        	codeName: {
        		validators: {
        			notEmpty: {
        				message:"名称不能为空"
        			},
        			stringLength: {
                        max: 25,
                        message: "最多输入25个字"
                    }
        		}
        	},
        	codeString: {
        		validators: {
        			notEmpty: {
        				message:"类别不能为空"
        			},
        			stringLength: {
                        max: 15,
                        message: "最多输入15个字"
                    }
        		}
        	},
        	codeValueName: {
        		validators: {
        			notEmpty: {
        				message:"描述不能为空"
        			},
        			stringLength: {
                        max: 25,
                        message: "最多输入25个字"
                    }
        		}
        	},
        	codeValue: {
        		validators: {
        			notEmpty: {
        				message:"值不能为空"
        			},
                    stringLength: {
        			    max:6,
                        message:"值必须是正整数"
                    }
        		}
        	},
        	codeType: {
        		validators: {
        			notEmpty: {
        				message:"类型不能为空"
        			}
        		}
        	}
        }
	});
}

/**
 * 刷新内存中的码表 如果存在的话
 */
function refreshCode(codeString) {
	Base.ajax({
   	 	url:Base.globvar.basePath + "system/appcode/appcodeManager/updateCodeCache",
   		type:'post',
   		data:{codeString:codeString},
   		success:function(data, status, xhr){
   			if(!data.error){
   				Base.reloadGridData("code");
   			}
   		}
   	 });
}

/**
 * 刷新内存中的码表 如果存在的话
 */
function refreshAllCode() {
	Base.ajax({
   	 	url:Base.globvar.basePath + "system/appcode/appcodeManager/updateAllCodeCache",
   		type:'post',
   		data:null,
   		success:function(data, status, xhr){
   			if(!data.error){
   				queryGrid();
   			}
   		}
   	 });
}

//取消编辑
function cancelEdit(){
	Base.rejectDatagridChanges("code");
}

function optionsf(value,data,index){
	var arr = [];
	if(data.codeStatus == 1){
		arr.push('<a class="btn btn-primary btn-xs" onclick="saveCode()">保存</a>');
		arr.push('<a class="btn btn-primary btn-xs" onclick="refreshCode(\''+data.codeString+'\')">刷新</a>');
		arr.push('<a class="btn btn-danger btn-xs" onclick="deleteCode(\''+data.codeString+'\',\''+data.codeValue+'\')">删除</a>');
	}else{
		arr.push('<a class="btn btn-warning btn-xs" onclick="deleteCode(\''+data.codeString+'\',\''+data.codeValue+'\',1)">重新启用</a>');
	}
	return arr.join(' ');
}

function saveCode() {
	var row = Base.getDatagridSelected("code");
	if(row != null){
		var index = Base.getDatagridRowIndex("code",row);
		Base.acceptDatagridChanges("code");
		var row = Base.getDatagridRow("code",index);
	   	Base.ajax({
	   	 	url:Base.globvar.basePath + "system/appcode/appcodeManager/saveCode",
	   		type:'post',
	   		data:row,
	   		success:function(data, status, xhr){
	   			if(!data.error){
	   				Base.reloadGridData("code");
	   			}
	   		}
	   	 });
	}
}

function deleteCode (codeString,codeValue,reboot) {
	var data = {
		codeString:codeString,
		codeValue:codeValue
	}
	
	if(Base.isExist(reboot)){
		data.reboot = true;
	}
	
	Base.ajax({
   	 	url:Base.globvar.basePath + "system/appcode/appcodeManager/deleteCode",
   		type:'post',
   		data:data,
   		success:function(data, status, xhr){
   			if(!data.error){
   				Base.reloadGridData("code");
   			}
   		}
   	 });
}
</script>