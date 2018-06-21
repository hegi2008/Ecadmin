<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <title>登录</title>
	<%@ include file="head.inc.jsp"%>
    <style type="text/css">
        .login {
            background-color: #5c97bd;
        }

        .login a {
            color: #edf4f8 !important;
        }

        .login .logo {
            margin: 0 auto;
            margin-top: 100px;
            padding: 15px;
            text-align: center;
        }

        .login .content {
            width: 400px;
            margin: 40px auto 40px auto;
        }

        .login .form-title {
            margin-bottom: 20px;
        }

        .login .form-title {
            color: #edf4f8;
            font-size: 19px;
            font-weight: 400 !important;
        }

        .login .form-subtitle {
            color: #c9dce9;
            font-size: 17px;
            font-weight: 300 !important;
            padding-left: 10px;
        }

        .login .content h4 {
            color: #555;
        }

        .login .content .hint {
            color: #b7d1e2;
            padding: 0;
            font-size: 14px;
            margin: 15px 0 7px 0;
        }

        .login .content .login-form,
        .login .content .forget-form {
            padding: 0px;
            margin: 0px;
        }

        .login .content .form-control {
            border: none;
            background-color: #6ba3c8;
            border: 1px solid #6ba3c8;
            height: 43px;
            color: #d9ecf9;
        }

        .login .content .form-control:focus, .login .content .form-control:active {
            border: 1px solid #83b8db;
        }

        .login .content .form-control::-moz-placeholder {
            color: #d9ecf9;
            opacity: 1;
        }

        .login .content .form-control:-ms-input-placeholder {
            color: #d9ecf9;
        }

        .login .content .form-control::-webkit-input-placeholder {
            color: #d9ecf9;
        }

        .login .content select.form-control {
            padding-left: 9px;
            padding-right: 9px;
        }

        .login .content .forget-form {
            display: none;
        }

        .login .content .register-form {
            display: none;
        }

        .login .content .form-title {
            font-weight: 300;
            margin-bottom: 25px;
        }

        .login .content .form-actions {
            clear: both;
            border: 0px;
            padding: 0px 30px 25px 30px;
            margin-left: -30px;
            margin-right: -30px;
        }

        .form-actions .forget-password-block {
            padding-top: 7px;
        }

        .login-options {
            margin-top: 30px;
            padding-top: 20px;
            padding-bottom: 50px;
            border-top: 1px solid #69a0c4;
            border-bottom: 1px solid #69a0c4;
        }

        .login-options h4 {
            margin-top: 8px;
            font-weight: 600;
            font-size: 15px;
            color: #b7d1e2 !important;
        }

        .login .forget-password {
            font-size: 14px;
        }

        .login-options .social-icons {
            float: right;
            padding-top: 3px;
        }

        .login-options .social-icons li a {
            border-radius: 15px 15px 15px 15px !important;
            -moz-border-radius: 15px 15px 15px 15px !important;
            -webkit-border-radius: 15px 15px 15px 15px !important;
        }

        .login .content .forget-form .form-actions {
            border: 0;
            margin-bottom: 0;
            padding-bottom: 20px;
        }

        .login .content .register-form .form-actions {
            border: 0;
            margin-bottom: 0;
            padding-bottom: 0px;
        }

        .login .content .form-actions .checkbox {
            margin-top: 8px;
            display: inline-block;
        }

        .login .content .form-actions .btn {
            margin-top: 1px;
        }

        .login .btn {
            background-color: #5995bb;
            border: 1px solid #72a9cc;
            color: #8fc4e5;
            font-weight: 600;
            padding: 10px 25px !important;
        }

        .login .btn:hover {
            border: 1px solid #90bbd7;
            background-color: #5995bb;
            color: #8fc4e5;
        }

        .login .btn-default {
            background-color: #5995bb;
            border: 1px solid #72a9cc;
            color: #8fc4e5;
            font-weight: 600;
            padding: 10px 25px !important;
        }

        .login .btn-default:hover {
            border: 1px solid #90bbd7;
            background-color: #5995bb;
            color: #8fc4e5;
        }

        .login .content .forget-password {
            color: #d7eaf7;
            font-size: 15px;
        }

        .login .content .rememberme {
            margin-top: 8px;
        }

        .login .content .mt-checkbox {
            color: #c9dce9 !important;
        }

        .login .content .mt-checkbox > span:after {
            border-color: #c9dce9 !important;
        }

        .login .content .create-account {
            text-align: center;
            margin-top: 20px;
        }

        .login .content .create-account p a {
            font-weight: 300;
            font-size: 16px;
            color: #ffffff;
        }

        .login .content .create-account a {
            display: inline-block;
            margin-top: 5px;
        }

        /* footer copyright */
        .login .copyright {
            text-align: center;
            margin: 10px auto 30px 0;
            padding: 10px;
            color: #c9dce9;
            font-size: 13px;
        }

        @media (max-width: 1400px) {
            .login .logo {
                margin-top: 100px;
            }
        }

        @media (max-width: 480px) {
            /***
            Login page
            ***/
            .login .logo {
                margin-top: 30px;
                padding: 0px;
            }

            .login .content {
                width: 245px;
            }

            .login .content h3 {
                font-size: 22px;
            }

            .login .checkbox {
                font-size: 13px;
            }
        }

    </style>
</head>
<body class="login">
    <!-- BEGIN LOGO -->
    <div class="logo">
        <a href="#">
            <img src="<%=basePath%>UI/themes/metronic/img/logo-big-white.png" style="height: 17px;" alt="" /> </a>
    </div>
    <!-- END LOGO -->
    <!-- BEGIN LOGIN -->
    <div class="content">
        <!-- BEGIN LOGIN FORM -->
        <form class="login-form" action="index.html" method="post">
            <div class="form-title">
                <span class="form-title">Welcome.</span>
                <span class="form-subtitle">Please login.</span>
            </div>
            <div class="form-group">
                <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                <label class="control-label visible-ie8 visible-ie9">Username</label>
                <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username" id="username"/>
            </div>
            <div class="form-group">
                <label class="control-label visible-ie8 visible-ie9">Password</label>
                <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="password" id="password"/>
            </div>
            <div class="form-group clearfix">
                <div class="col-sm-8 col-xs-8" style="padding: 0;">
                    <%--<label class="control-label visible-ie8 visible-ie9">ValidCode</label>--%>
                    <input id="code" name="code" type="text" class="form-control form-control-solid placeholder-no-fix" placeholder="ValidCode">
                </div>
                <div class="col-sm-4 col-xs-4">
                    <img alt="验证码" height="43px" src="<%=basePath%>images/kaptcha.jpg" title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha();">
                </div>
            </div>
            <div class="form-actions">
                <button type="button" onclick="login();" class="btn red btn-block uppercase">Login</button>
            </div>
            <div class="form-actions">
                <div class="pull-left">
                    <label class="rememberme mt-checkbox mt-checkbox-outline">
                        <input id="remember-me" type="checkbox" name="remember" value="1" /> Remember me
                        <span></span>
                    </label>
                </div>
                <div class="pull-right forget-password-block">
                    <a href="javascript:;" id="forget-passwod" class="change-password">change password?</a>
                </div>
            </div>
        </form>
        <!-- END LOGIN FORM -->
        <!-- 修改密码 开始 -->
        <div id="change-passwod-box" class="container"
             style="display:none;width:400px; height:280px; padding: 10px; overflow: hidden;background-color: #FFFFFF;">
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
        <!-- 修改密码 结束 -->
    </div>
    <div class="copyright"> 2014 © Metronic. Admin Dashboard Template. </div>
    <%@ include file="basic.js.inc.jsp"%>
    <script src="<%=basePath%>UI/js/jquery.hotkeys.js"></script>
	<script type="text/javascript">
        $(document).ready(function() {
            <%
                String login_error_msg = (String)request.getAttribute("login_error_msg");
                if(login_error_msg != null){
                    out.println("layer.msg(\'"+login_error_msg+"\',{icon:2});");
                }
            %>
            $(document).bind('keydown','return',function(event){
                login();
                return false;
            });
            var username = Base.cookie.get("HYusername");
            var password = Base.cookie.get("HYpassword");
            if (username && password) {
                $("#username").val(username);
                $("#password").val(password);
                $("#remember-me").prop("checked",true);
            } else {
                $("#remember-me").prop("checked",false);
            }

            $("#forget-passwod").click(function() { // 弹出修改密码的对话框
                var username = $("#username").val();
                layer.open({
                    type: 2,
                    title: '修改密码',
                    anim:0,
                    shade: 0.3,
                    maxmin: false, //开启最大化最小化按钮
                    area: ['400px','310px'],
                    content: Base.globvar.basePath+"UI/themes/metronic/change_pwd.jsp?username="+username
                });
            });
        });

        function login() {
            var username = Base.trimAll($("#username").val());
            var password =  Base.trimAll($("#password").val());
            var code = Base.trimAll($("#code").val());
            if(username == ""){
                Base.tips("请输入登陆账号","#username");
                return;
            }
            if(password == ""){
                Base.tips("请输入登陆密码","#password");
                return;
            }
            var rememberMe = false;
            if ($("#remember-me").prop("checked")) { // 记住密码
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
            $("#img_captcha").attr("src","<%=basePath%>images/kaptcha.jpg?r="+new Date().getTime());
        }
	</script>
</body>
</html>