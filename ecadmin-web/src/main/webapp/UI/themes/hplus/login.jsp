<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<title>登录</title>
	<%@ include file="head.inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>UI/themes/hplus/css/login.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>UI/plugins/bootstrapswitch/bootstrap-switch.min.css">
</head>
<body>
	<div class="lock-content" style="background-image: url(<%=basePath%>UI/themes/hplus/img/1.jpg);"></div>
	<div class="user-content">
		<!-- <div id="logo">
			<img src="UI/img/default.jpg" alt="登录"
				class="img-rounded">
		</div> -->
		<div id="login-form" class="panel panel-default login-form">
			<div class="panel-body">
				<h3 class="login-form-title">登录到系统</h3>
				<br />
				<div class="input-group">
					<div class="input-group-addon">
						<i class="fa fa-user"></i>
					</div>
					<input id="j_username" name="j_username" type="text"
						class="form-control" placeholder="用户名"
						aria-describedby="basic-addon1">
				</div>
				<br />
				<div class="input-group">
					<div class="input-group-addon">
						<i class="fa fa-lock"></i>
					</div>
					<input id="j_password" name="j_password" type="password"
						class="form-control" placeholder="密码"
						aria-describedby="basic-addon1">
				</div>
				<br />
				<div class="input-group">
					<input id="code" name="code" type="text"
						class="form-control" placeholder="验证码"
						aria-describedby="basic-addon1">
					<div class="input-group-addon" style="padding: 0;border-width: 0;">
						<img alt="验证码" height="34px" src="images/kaptcha.jpg" title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha();">
					</div>
				</div>
				<br />
				<div class="switch" data-size="mini">
					<label style="font-weight: normal;color: #ccc;">
						<input id="remember-me" name="remember-me" type="checkbox" data-on-color="default"/>&nbsp;&nbsp;记住我
					</label>
				</div>
				<br />
				<button id="btn-login" type="button" onclick="login();"
					class="btn btn-primary btn-block">
					<span id="btn-login" class="glyphicon glyphicon-log-in"
						aria-hidden="true"></span> 登录
				</button>
				<br />
				<div class="input-group">
					<a id="forget-passwod" class="text-left" href="javascript:void(0);">修改密码？</a>
				</div>
			</div>
		</div>
		<div class="copyright">2015 &copy; Made By Hyman</div>
	</div>
	<div id="change-passwod-box" class="container"
		style="display:none;width:350px; height:280px; padding: 10px; overflow: hidden;">
		<form class="form-horizontal" action="#">
			<div class="form-group">
				<label for="_username" class="col-sm-3 control-label">登陆账号</label>
				<div class="col-sm-9">
					<input id="_username" type="text" class="form-control"
						placeholder="登陆账号" >
				</div>
			</div>
			<div class="form-group">
				<label for="old-password" class="col-sm-3 control-label">原密码</label>
				<div class="col-sm-9">
					<input id="old-password" type="password" class="form-control"
						placeholder="原密码" >
				</div>
			</div>
			<div class="form-group">
				<label for="new-password" class="col-sm-3 control-label">新密码</label>
				<div class="col-sm-9">
					<input id="new-password" type="password" class="form-control"
						placeholder="新密码" >
				</div>
			</div>
			<div class="form-group">
				<label for="rep-new-password" class="col-sm-3 control-label">确认新密码</label>
				<div class="col-sm-9">
					<input id="rep-new-password" type="password" class="form-control"
						placeholder="确认新密码" >
				</div>
			</div>
			<%-- <div class="form-group">
				<label for="code" class="col-sm-3 control-label">验证码</label>
				<div class="col-sm-5">
					<input id="code" type="text" class="form-control"
						placeholder="验证码" >
				</div>
				<div class="col-sm-4">
					<img id="codeimgPass" class="form-control" style="padding: 0;"
						onclick="javascript:refeshCodePass();"
						src="<%=path%>/CaptchaImgPass" />
				</div>
			</div> --%>
			<button id="btn-save-password" type="button"
				class="btn btn-block btn-primary">
				<i class="fa fa-check"></i> 提交
			</button>
		</form>
	</div>
	<script src="<%=basePath%>UI/js/jquery.hotkeys.js"></script>
    <script src="<%=basePath%>UI/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>UI/plugins/layer/layer.min.js"></script>
	<script src="<%=basePath%>UI/plugins/bootstrapswitch/bootstrap-switch.min.js"></script>
	<script src="<%=basePath%>UI/js/api.base.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		<%
			String login_error_msg = (String)request.getAttribute("login_error_msg");
			if(login_error_msg != null){
				out.println("layer.msg(\'"+login_error_msg+"\',{icon:2});");
			}
		%>
		//refreshCaptcha();
		beginSlide();
		$(document).bind('keydown','return',function(event){
			login();
			return false;
		});	
		var username = Base.cookie.get("HYusername");
		var password = Base.cookie.get("HYpassword");
		if (username && password) {
			$("#j_username").val(username);
			$("#j_password").val(password);
			$("#remember-me").bootstrapSwitch({"size": "mini", "state": true});	
		} else {
			$("#remember-me").bootstrapSwitch({"size": "mini", "state": false});
		}
		
		$("#forget-passwod").click(function() { // 弹出修改密码的对话框
			var username = $("#j_username").val();
			if(username) {
				$("#_username").val(username);
			}
			$("#old-password").val("");
			$("#new-password").val("");
			$("#rep-new-password").val("");
			layer.open({
			    type: 1,
			    shade: false,
			    title: '修改密码', //不显示标题
			    content: $("#change-passwod-box"), //捕获的元素
			    cancel: function(index){
			        layer.close(index);
			        this.content.hide();
			    }
			});
		});
		
		$("#btn-save-password").click(function() { // 保存新密码
			var username = $.trim($("#_username").val());
			if(!username || "" == username) {
				Base.alert({message: "请输入用户名", type: "warning"});
				return false;
			}
			
			var oldPass = $.trim($("#old-password").val());
			if(!oldPass || "" == oldPass) {
				Base.alert({message: "请输入旧密码", type: "warning"});
				return false;
			}
			
			var newPass = $.trim($("#new-password").val());
			if(!newPass || "" == newPass) {
				Base.alert({message: "请输入新密码", type: "warning"});
				return false;
			}
			
			var rpassword = $.trim($("#rep-new-password").val());
			if(!rpassword || "" == rpassword) {
				Base.alert({message: "请确认新密码", type: "warning"});
				return false;
			}
			
			/* var code = $.trim($("#code").val());
			if(!code || "" == code) {
				Base.alert({message: "请输入验证码", type: "warning"});
				return false;
			}
			 */
		    if(checkPass(newPass)<2){
		      Base.alert({message: "密码过于简单，请重设密码。", type: "warning"});
		      $("#new-password").val("");
		      $("#rep-new-password").val("");
		      return false;
		    }
			if($.trim(newPass)!=$.trim(rpassword)){
				$("#rep-new-password").val("");
				Base.alert({message: "密码不一致，请重新确认。", type: "warning"});
				return false;
			}
		   	
			Base.ajax({
				url : Base.globvar.basePath + "changeUserPassword",
				data : {"userLogin": username,"userPwd_old": oldPass, "userPwd": newPass},
				type : 'post',
				success : function(data){
					if(!data.error) {
						$("#j_password").val("");
						setTimeout(function(){
							$("#change-passwod-box").window("close");
						}, 2000);
					}
				}
			});
			return false;
		});
	});
	
	function checkPass(s){     
        if(s.length < 0){     
           return 0;     
        }     
        var ls = 10;     
        if(s.match(/([a-z])+/)){     
           ls++;     
         }     
        if(s.match(/([0-9])+/)){     
           ls++;       
        }     
        if(s.match(/([A-Z])+/)){     
           ls++;     
        }     
        if(s.match(/[^a-zA-Z0-9]+/)){     
           ls++;     
        }     
        return ls;     
    }
	
	function beginSlide(){
		var imgUrl = [], i = 0;
		imgUrl[0] = '<%=basePath%>UI/themes/hplus/img/2.jpg';
		imgUrl[1] = '<%=basePath%>UI/themes/hplus/img/3.jpg';
		imgUrl[2] = '<%=basePath%>UI/themes/hplus/img/4.jpg';
		imgUrl[3] = '<%=basePath%>UI/themes/hplus/img/1.jpg';
		setInterval(changeBgImg, 8000);
		function changeBgImg() {
			var time = i % imgUrl.length;
			$(".lock-content").css("background-image",
					"url(" + imgUrl[time] + ")");
			i++;
		}
	}
	
	function login() {
		var username = Base.trimAll($("#j_username").val());
		var password =  Base.trimAll($("#j_password").val());
		var code = Base.trimAll($("#code").val());
		if(username == ""){
			Base.tips("请输入登陆账号","#j_username");
			return;
		}
		if(password == ""){
			Base.tips("请输入登陆密码","#j_password");
			return;
		}
		/* if(code == ""){
			Base.tips("请输入验证码","#code");
			return;
		} */
		var rememberMe = false;
		if ($("#remember-me").bootstrapSwitch("state")) { // 记住密码
			Base.cookie.add("HYusername", escape(username), 168); // 记住7天
			Base.cookie.add("HYpassword", escape(password), 168);
			rememberMe = true;
		} else {
			Base.cookie.remove("HYusername");
			Base.cookie.remove("HYpassword");
		}
		Base.ajax({
			url : "doLogin",
			data : {"username": username,"password": password,"rememberMe":rememberMe,"code":code},
			type : 'post',
			dataType : 'json',
			success : function(data,textStatus){
				if(data.error){
					if(data.error_msg.indexOf("验证码") < 0){
						Base.cookie.remove("HYusername");
						Base.cookie.remove("HYpassword");
						//$("#j_username").val("");
						$("#j_password").val("");
					}
				}else{
					window.location.href = "index";
				}
			}
		});
	}
   	if(top != window.self){
		top.location.href=window.location.href;
	}
   	
   	// 刷新验证码
   	function refreshCaptcha(){
   		$("#img_captcha").attr("src","images/kaptcha.jpg?r="+new Date().getTime());
   	}
	</script>
</body>
</html>