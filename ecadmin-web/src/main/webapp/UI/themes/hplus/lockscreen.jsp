<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>锁屏</title>
    <%@ include file="head.inc.jsp" %>
</head>
<body class="gray-bg">

    <div class="lock-word animated fadeInDown">
    </div>
    <div class="middle-box text-center lockscreen animated fadeInDown">
        <div>
            <div class="m-b-md">
            	<c:if test="${!empty user.userHeaderPic}">
            		<img alt="image" class="img-circle circle-border" width="200px" height="200px" src="${imageUrl}${user.userHeaderPic}">
				</c:if>
				<c:if test="${empty user.userHeaderPic}">
					<img alt="image" class="img-circle circle-border" width="200px" height="200px" src="../UI/img/user.jpg">
				</c:if>
            </div>
            <h3>${user.userName}</h3>
            <p>您需要再次输入密码</p>
            <form class="m-t" role="form">
                <div class="form-group">
                    <input id="password" name="password" type="password" class="form-control" placeholder="******">
                </div>
                <button type="button" class="btn btn-primary block full-width" onclick="getUnlock()">登录到系统</button>
            </form>
        </div>
    </div>
	<script src="<%=basePath%>UI/js/jquery.hotkeys.js"></script>
	<script src="<%=basePath%>UI/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>UI/plugins/layer/layer.min.js"></script>
	<script src="<%=basePath%>UI/plugins/moment/moment.min.js"></script>
	<script src="<%=basePath%>UI/js/api.base.js"></script>
	<script>
		$(function(){
			$(document).bind('keydown','return',function(event){
				getUnlock();
				return false;
			});	
		});
		/*
		 * 解锁
		 */
		function getUnlock() {
			var url = Base.globvar.basePath + "index/unlockScreen";
			var password = $("#password").val();
			if (!password) {
				Base.tips("密码不能为空","#password");
				return ;
			}
			var param = {
				password : password
			};
			var options = {
				url : url,
				mask : true,
				data : param,
				success : function (data){
					if(!data.error){
						parent.layer.closeAll();
					}
				}
			};
			Base.ajax(options);
		}
	</script>
</body>
</html>