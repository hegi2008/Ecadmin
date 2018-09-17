<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/UI/plugins/layui/css/layui.css"  media="all">
</head>
<body>


<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>就诊卡详情</legend>
</fieldset>

<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">账户编号</label>
        <div class="layui-input-block">
            <input type="text" name="outPlatformId" lay-verify="required" value="${card.outPlatformId}" readonly="true" autocomplete="off"  class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">身份证</label>
        <div class="layui-input-block">
            <input type="text" name="idcardNo" lay-verify="required" value="${card.idcardNo}" readonly="true" autocomplete="off"  class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">机构编号</label>
            <div class="layui-input-inline">
                <input type="text" name="agencyNum" lay-verify="required" value="${card.agencyNum}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">患者编号</label>
            <div class="layui-input-inline">
                <input type="text" name="patid" lay-verify="required" value="${card.patid}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">患者名</label>
            <div class="layui-input-inline">
                <input type="text" name="patName" lay-verify="required" value="${card.patName}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">就诊卡号</label>
            <div class="layui-input-inline">
                <input type="text" name="cardno" lay-verify="required" value="${card.cardno}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-inline">
                <input type="text" name="address" lay-verify="required" value="${card.address}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">电话</label>
            <div class="layui-input-inline">
                <input type="text" name="mobile" lay-verify="required" value="${card.mobile}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">绑定时间</label>
            <div class="layui-input-inline">
                <input type="text" name="bindTime" lay-verify="required" value="${card.bindTime}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">更新时间</label>
            <div class="layui-input-inline">
                <input type="text" name="lastUpdatetime" lay-verify="required" value="${card.lastUpdatetime}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否默认</label>
        <div class="layui-input-block">
            <c:forEach var="status" items="${ismrjzks}">
                <c:if test="${status.codeValue == card.relationship}">
                    <input type="radio" name="relationship" value="${status.codeValue}" title="${status.codeValueName}" disabled="true"  checked="true">
                </c:if>
                <c:if test="${status.codeValue != card.relationship}">
                    <input type="radio" name="relationship" value="${status.codeValue}" title="${status.codeValueName}" disabled="true"   >
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <c:forEach var="status" items="${cardstatus}">
                <c:if test="${status.codeValue == card.status}">
                    <input type="radio" name="status" value="${status.codeValue}" title="${status.codeValueName}" disabled="true"  checked="true">
                </c:if>
                <c:if test="${status.codeValue != card.status}">
                    <input type="radio" name="status" value="${status.codeValue}" title="${status.codeValueName}" disabled="true"   >
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
        <label class="layui-form-label">账户类型</label>
        <div class="layui-input-block">
            <select name="channel" disabled="true" >
                <c:forEach var="paychannel" items="${paychannels}">
                    <c:if test="${paychannel.codeValue == card.channel}">
                        <option value="${paychannel.codeValue}" selected="true">${paychannel.codeValueName}</option>
                    </c:if>
                    <c:if test="${paychannel.codeValue != card.channel}">
                        <option value="${paychannel.codeValue}">${paychannel.codeValueName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        </div>
        <div class="layui-inline">
        <label class="layui-form-label">卡类型</label>
        <div class="layui-input-block">
            <select name="type" disabled="true" >
                <c:forEach var="cardtype" items="${cardtypes}">
                    <c:if test="${cardtype.codeValue == card.type}">
                        <option value="${cardtype.codeValue}" selected="true">${cardtype.codeValueName}</option>
                    </c:if>
                    <c:if test="${cardtype.codeValue != card.type}">
                        <option value="${cardtype.codeValue}">${cardtype.codeValueName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        </div>
    </div>
</form>


<script src="${pageContext.request.contextPath}/UI/plugins/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form'], function(){
    });
</script>

</body>
</html>
