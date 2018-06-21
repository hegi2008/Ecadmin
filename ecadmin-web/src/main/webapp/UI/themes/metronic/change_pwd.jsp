<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <title>登录</title>
	<%@ include file="head.inc.jsp"%>
    <%
        String username = request.getParameter("username");
        request.setAttribute("username",username);
    %>
</head>
<body>
<!-- 修改密码 开始 -->
<div style="padding:10px 20px;">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label for="_username" class="col-xs-3 control-label text-right">登陆账号</label>
            <div class="col-xs-9">
                <input id="_username" type="text" class="form-control "
                       placeholder="登陆账号" value="${username}">
            </div>
        </div>
        <div class="form-group">
            <label for="old-password" class="col-xs-3 control-label text-right">原密码</label>
            <div class="col-xs-9">
                <input id="old-password" type="password" class="form-control"
                       placeholder="原密码">
            </div>
        </div>
        <div class="form-group">
            <label for="new-password" class="col-xs-3 control-label text-right">新密码</label>
            <div class="col-xs-9">
                <input id="new-password" type="password" class="form-control"
                       placeholder="新密码">
            </div>
        </div>
        <div class="form-group">
            <label for="rep-new-password" class="col-xs-3 control-label text-right">新密码</label>
            <div class="col-xs-9">
                <input id="rep-new-password" type="password" class="form-control"
                       placeholder="确认新密码">
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
        <div class="text-center">
            <button id="btn-save-password" type="button" class="btn blue btn-block">提交</button>
        </div>
    </form>
</div>
<!-- 修改密码 结束 -->
    <%@ include file="basic.js.inc.jsp"%>
	<script type="text/javascript">
        $(document).ready(function() {
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
                /*if(checkPass(newPass)<2){
                    Base.alert({message: "密码过于简单，请重设密码。", type: "warning"});
                    $("#new-password").val("");
                    $("#rep-new-password").val("");
                    return false;
                }*/
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
	</script>
</body>
</html>