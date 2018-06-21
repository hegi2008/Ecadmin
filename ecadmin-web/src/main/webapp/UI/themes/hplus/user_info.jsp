<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>个人信息</title>
<%@ include file="head.inc.jsp" %>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
        <div class="row animated fadeInRight">
        	<div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>个人资料</h5>
                    </div>
                    <div>
                        <div class="ibox-content no-padding border-left-right">
                            <img alt="image" class="img-responsive" src="<%=basePath%>UI/themes/hplus/img/user.jpg">
                        </div>
                        <div class="ibox-content profile-content">
                            <h4><strong>${user.userName}</strong></h4>
							<div class="col-sm-6">
								<p><i class="fa fa-phone"></i> ${user.userPhone}</p>
							</div>
							<div class="col-sm-6">
								<p><i class="fa fa-envelope"></i> ${user.userEmail}</p>
							</div>
                            <h5>
                                   	关于我
                            </h5>
                            <p>
                                	会点前端技术，div+css啊，jQuery之类的，不是很精；热爱生活，热爱互联网，热爱新技术；有一个小的团队，在不断的寻求新的突破。
                            </p>
                            <!-- <div class="row m-t-lg">
                                <div class="col-sm-4">
                                    <span class="bar">5,3,9,6,5,9,7,3,5,2</span>
                                    <h5><strong>169</strong> 文章</h5>
                                </div>
                                <div class="col-sm-4">
                                    <span class="line">5,3,9,6,5,9,7,3,5,2</span>
                                    <h5><strong>28</strong> 关注</h5>
                                </div>
                                <div class="col-sm-4">
                                    <span class="bar">5,3,2,-1,-3,-2,2,3,5,2</span>
                                    <h5><strong>240</strong> 关注者</h5>
                                </div>
                            </div> -->
                            <div class="user-button">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <button type="button" class="btn btn-primary btn-sm btn-block" onclick="editUserInfo()"><i class="fa fa-edit"></i> 编辑信息</button>
                                    </div>
<!--                                     <div class="col-sm-6">
                                        <button type="button" class="btn btn-default btn-sm btn-block"><i class="fa fa-coffee"></i> 赞助</button>
                                    </div>
 -->                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<div id="userInfoBox" class="container" style="display:none;width:350px; height:280px; padding: 10px; overflow: hidden;">
		<form id="form-user-info">
		  <div class="form-group">
		    <label for="userName">姓名</label>
		    <input type="text" class="form-control" id="userName" name="userName" placeholder="姓名" value="${user.userName}" readonly="readonly">
		  </div>
		  <div class="form-group">
		    <label for="userPhone">联系电话</label>
		    <input type="text" class="form-control" id="userPhone" name="userPhone" placeholder="联系电话" value="${user.userPhone}">
		  </div>
		  <div class="form-group">
	  		<label>性别</label>
	  		<div class="radio">
	  			<c:set var="usex" scope="session" value="${user.userSex}" />
	  			<c:if test="${usex == '0'}">
	  				<label>
			      		<input name="userSex" type="radio" value="0" checked="checked">未知
			    	</label>
			    	<label>
			      		<input name="userSex" type="radio" value="1">男
			    	</label>
			    	<label>
			      		<input name="userSex" type="radio" value="2">女
			    	</label>
			    </c:if>
	  			<c:if test="${usex == '1'}">
	  				<label>
			      		<input name="userSex" type="radio" value="0">未知
			    	</label>
			    	<label>
			      		<input name="userSex" type="radio" value="1" checked="checked">男
			    	</label>
			    	<label>
			      		<input name="userSex" type="radio" value="2">女
			    	</label>
			    </c:if>
			    <c:if test="${usex == '2'}">
			    	<label>
			      		<input name="userSex" type="radio" value="0">未知
			    	</label>
			    	<label>    
			      		<input name="userSex" type="radio" value="1">男
			    	</label>
			    	<label>
			      		<input name="userSex" type="radio" value="2" checked="checked">女
			    	</label>
			    </c:if>
		    </div>
		  </div>
		  <div class="form-group text-center">
		  	<button type="button" class="btn btn-primary btn-sm btn-block" onclick="fnSave()"><i class="fa fa-save"></i> 保存</button>
		  </div>
		</form>
	</div>
	<%@ include file="basic.js.inc.jsp" %>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
});
//保存修改信息
function fnSave(form){
	if(!form){
		form = $("#form-user-info");
	}
	var array = form.serializeArray();
	var param = {};
	for ( var i in array) {
		param[array[i].name] = array[i].value;
	}
	var url=Base.globvar.basePath+"index/updateUserInfo";
	var opt = {
		type: "post",	
		url: url,
		data: param,
		success: function(data) {
			if(!data.error){
				setTimeout(function(){
					layer.closeAll();
				}, 3000);
			}
	    }
	};
	Base.ajax(opt);
}

function editUserInfo(){
	layer.open({
	    type: 1,
	    shade: false,
	    title: '修改密码', //不显示标题
	    content: $("#userInfoBox"), //捕获的元素
	    cancel: function(index){
	        layer.close(index);
	        this.content.hide();
	    }
	});
}
</script>
