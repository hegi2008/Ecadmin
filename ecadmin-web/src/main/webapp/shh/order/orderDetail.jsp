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
    <legend>订单详情</legend>
</fieldset>

<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">订单编号</label>
        <div class="layui-input-block">
            <input type="text" name="orderId" lay-verify="required" value="${order.orderId}" readonly="true" autocomplete="off"  class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">流水号</label>
            <div class="layui-input-inline">
                <input type="text" name="paytradeno" lay-verify="required" value="${order.paytradeno}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">身份证</label>
            <div class="layui-input-inline">
                <input type="tel" name="idcard" lay-verify="required" value="${order.idcard}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">患者名</label>
            <div class="layui-input-inline">
                <input type="text" name="patName" lay-verify="required" value="${order.patName}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">就诊卡号</label>
            <div class="layui-input-inline">
                <input type="text" name="cardno" lay-verify="required" value="${order.cardno}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">生成时间</label>
            <div class="layui-input-inline">
                <input type="text" name="phone" lay-verify="required" value="${order.orderAddTime}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">完成时间</label>
            <div class="layui-input-inline">
                <input type="text" name="finishTime" lay-verify="required" value="${order.finishTime}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">账户id</label>
            <div class="layui-input-inline">
                <input type="text" name="phone" lay-verify="required" value="${order.payAccount}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">患者编号</label>
            <div class="layui-input-inline">
                <input type="text" name="patid" lay-verify="required" value="${order.patid}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">机构名</label>
            <div class="layui-input-inline">
                <input type="text" name="agencyName" lay-verify="required" value="${order.agencyName}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">机构编号</label>
            <div class="layui-input-inline">
                <input type="text" name="agencyNum" lay-verify="required" value="${order.agencyNum}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">订单金额</label>
            <div class="layui-input-inline">
                <input type="text" name="tradeBalance" lay-verify="required" value="${order.tradeBalance}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-inline">
                <input type="text" name="remarks" lay-verify="required" value="${order.remarks}" readonly="true" autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
        <label class="layui-form-label">业务类型</label>
        <div class="layui-input-block">
            <select name="consumeType" disabled="true" >
                <c:forEach var="ywlx" items="${ywlxs}">
                    <c:if test="${ywlx.codeValue == order.consumeType}">
                        <option value="${ywlx.codeValue}" selected="true">${ywlx.codeValueName}</option>
                    </c:if>
                    <c:if test="${type.codeValue != order.consumeType}">
                        <option value="${ywlx.codeValue}">${ywlx.codeValueName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        </div>
        <div class="layui-inline">
        <label class="layui-form-label">订单渠道</label>
        <div class="layui-input-block">
            <select name="paychannel" disabled="true">
                <c:forEach var="qd" items="${paychannels}">
                    <c:if test="${qd.codeValue == order.paychannel}">
                        <option value="${qd.codeValue}" selected="true">${qd.codeValueName}</option>
                    </c:if>
                    <c:if test="${qd.codeValue != order.paychannel}">
                        <option value="${qd.codeValue}">${qd.codeValueName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">支付状态</label>
            <div class="layui-input-block">
                <select name="payResult" disabled="true">
                    <c:forEach var="pay" items="${pay_results}">
                        <c:if test="${pay.codeValue == order.payResult}">
                            <option value="${pay.codeValue}" selected="true">${pay.codeValueName}</option>
                        </c:if>
                        <c:if test="${pay.codeValue != order.payResult}">
                            <option value="${pay.codeValue}">${pay.codeValueName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">his状态</label>
            <div class="layui-input-block">
                <select name="hisResult" disabled="true">
                    <c:forEach var="his" items="${hisresults}">
                        <c:if test="${his.codeValue == order.hisResult}">
                            <option value="${his.codeValue}" selected="true">${his.codeValueName}</option>
                        </c:if>
                        <c:if test="${his.codeValue != order.hisResult}">
                            <option value="${his.codeValue}">${his.codeValueName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">支付方式</label>
            <div class="layui-input-block">
                <select name="payway" disabled="true">
                    <c:forEach var="pay" items="${payways}">
                        <c:if test="${pay.codeValue == order.payway}">
                            <option value="${pay.codeValue}" selected="true">${pay.codeValueName}</option>
                        </c:if>
                        <c:if test="${pay.codeValue != order.payway}">
                            <option value="${pay.codeValue}">${pay.codeValueName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">支付类型</label>
            <div class="layui-input-block">
                <select name="payType" disabled="true">
                    <c:forEach var="type" items="${pay_types}">
                        <c:if test="${type.codeValue == order.payType}">
                            <option value="${type.codeValue}" selected="true">${type.codeValueName}</option>
                        </c:if>
                        <c:if test="${type.codeValue != order.payType}">
                            <option value="${type.codeValue}">${type.codeValueName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">订单描述</label>
        <div class="layui-input-block">
            <textarea  class="layui-textarea" name="tradeDesc" readonly="true" >${order.tradeDesc}</textarea>
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
