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
	<div class="container">
		<button class="btn btn-default" onclick="sendMsg()">发送给全部</button>
		<button class="btn btn-default" onclick="send2user()">发送定向消息</button>
	</div>
    <script src="../UI/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="../UI/js/plugins/layer/layer.min.js"></script>
	<script src="../UI/js/api.base.js"></script>
	<script type="text/javascript">
	 	//这个方法用来启动该页面的ReverseAjax功能
		dwr.engine.setActiveReverseAjax(true);
		//设置在页面关闭时，通知服务端销毁会话
		dwr.engine.setNotifyServerOnPageUnload(true);
		//设置DWR调用服务出错时，不打印(alert)调试信息 
		dwr.engine.setErrorHandler(function() { 
		    // 
		});
		 //推送信息  
		function sendMsg(){
			var msg = "你收到了吗?";
			MessagePush.send2All(msg);
		}
		 
		function send2user() {
			var msg = "你收到了吗?";
			MessagePush.send2User("1",msg);
		}
	</script>
</body>
</html>