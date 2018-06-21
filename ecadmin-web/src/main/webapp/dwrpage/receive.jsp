<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<title>接收dwr后端推送</title>
	<%@ include file="../UI/head.inc.jsp"%>
	<script type='text/javascript' src='<%=basePath %>dwr/engine.js'></script>  
  	<script type='text/javascript' src='<%=basePath %>dwr/util.js'></script>  
 	<script type="text/javascript" src="<%=basePath %>dwr/interface/MessagePush.js"></script>
</head>
<body>
	
    <script src="../UI/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="../UI/js/plugins/layer/layer.min.js"></script>
	<script src="../UI/js/api.base.js"></script>
	<script type="text/javascript">  
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true);
		//设置DWR调用服务出错时，不打印(alert)调试信息 
		dwr.engine.setErrorHandler(function() { 
		    // 
		});
        //通过该方法与后台交互，确保推送时能找到指定用户  
		function onPageLoad(){  
		    var userId = '111';  
		    MessagePush.onPageLoad(userId);  
		}  
		 //推送信息  
		function showMessage(autoMessage){
			alert(autoMessage);
		}
	</script>
</body>
</html>