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
    <legend>登记详情</legend>
</fieldset>

<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">订单编号</label>
        <div class="layui-input-block">
            <input type="text" name="orderId" lay-verify="required" value="${reg.orderId}" readonly="true" autocomplete="off"  class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">预约登记号</label>
        <div class="layui-input-block">
            <input type="text" name="orderId" lay-verify="required" value="${reg.yydjh}" readonly="true" autocomplete="off"  class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <c:forEach var="status" items="${regstatus}">
                <c:if test="${status.codeValue == reg.status}">
                    <input type="radio" name="status" value="${status.codeValue}" title="${status.codeValueName}" disabled="true"  checked="true">
                </c:if>
                <c:if test="${status.codeValue != reg.status}">
                    <input type="radio" name="status" value="${status.codeValue}" title="${status.codeValueName}" disabled="true"   >
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">患者名</label>
            <div class="layui-input-inline">
                <input type="text" name="patName" lay-verify="required" value="${reg.patName}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">身份证</label>
            <div class="layui-input-inline">
                <input type="tel" name="idcard" lay-verify="required" value="${reg.idcard}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">患者编号</label>
            <div class="layui-input-inline">
                <input type="text" name="patid" lay-verify="required" value="${reg.patid}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">就诊卡号</label>
            <div class="layui-input-inline">
                <input type="text" name="cardno" lay-verify="required" value="${reg.cardno}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">号源日期</label>
            <div class="layui-input-inline">
                <input type="text" name="tscdate" lay-verify="required" value="${reg.tscdate}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">排班编号</label>
            <div class="layui-input-inline">
                <input type="text" name="tscid" lay-verify="required" value="${reg.tscid}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">号序</label>
            <div class="layui-input-inline">
                <input type="text" name="seqnum" lay-verify="required" value="${reg.seqnum}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">挂号费</label>
            <div class="layui-input-inline">
                <input type="text" name="regfee" lay-verify="required" value="${reg.regfee}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">诊疗费</label>
            <div class="layui-input-inline">
                <input type="text" name="treatfee" lay-verify="required" value="${reg.treatfee}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">费用总金额</label>
            <div class="layui-input-inline">
                <input type="text" name="regamt" lay-verify="required" value="${reg.regamt}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">科室编号</label>
            <div class="layui-input-inline">
                <input type="text" name="deptid" lay-verify="required" value="${reg.deptid}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">科室名</label>
            <div class="layui-input-inline">
                <input type="text" name="deptname" lay-verify="required" value="${reg.deptname}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">医生编号</label>
            <div class="layui-input-inline">
                <input type="text" name="drid" lay-verify="required" value="${reg.drid}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" name="drname" lay-verify="required" value="${reg.drname}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">职称</label>
            <div class="layui-input-inline">
                <input type="text" name="drtitle" lay-verify="required" value="${reg.drtitle}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">锁号时间(分)</label>
            <div class="layui-input-inline">
                <input type="text" name="minutes" lay-verify="required" value="${reg.minutes}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">登记时间</label>
            <div class="layui-input-inline">
                <input type="text" name="addTime" lay-verify="required" value="${reg.addTime}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">更新时间</label>
            <div class="layui-input-inline">
                <input type="text" name="lastUpdate" lay-verify="required" value="${reg.lastUpdate}" readonly="true" autocomplete="off" class="layui-input">
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
