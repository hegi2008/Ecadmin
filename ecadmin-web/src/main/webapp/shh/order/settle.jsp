<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>结算列表</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid" style="height: 500px;">
    <ec:grid id="settles" title="结算列表" fit="true"  pageSize="10"  showRowno="true"   showHeaderContextMenu="true"
             dataurl="${basePath}shh/order/orderManager/getSettle?orderId=${orderId}">
        <ec:gridItem itemId="orderId" itemName="订单编号" width="230"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="patName"  itemName="患者名"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="idcard" itemName="身份证" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="patid" itemName="患者编号" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="cardno" itemName="就诊卡号" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="cardtype" itemName="卡类型"  showDetail="true" collection="CARDTYPE"></ec:gridItem>
        <ec:gridItem itemId="preregflag" itemName="是否预约(挂号)" showDetail="true" collection="PREREGFLAG"></ec:gridItem>
        <ec:gridItem itemId="regtype" itemName="挂号类别"  showDetail="true" collection="REGTYPE"></ec:gridItem>
        <ec:gridItem itemId="tscid" itemName="排班id" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="seqnum" itemName="号源序号" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="tscdate" itemName="号源日期"  width="200" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="daysection" itemName="时段" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="whetherded" itemName="是否扣院内账户" showDetail="true" collection="WHETHERDED" ></ec:gridItem>
        <ec:gridItem itemId="whetherset" itemName="是否自费结算" showDetail="true" collection="WHETHERSET"></ec:gridItem>
        <ec:gridItem itemId="hospitalcardno" itemName="院内账户卡号"  width="200" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="regid" itemName="挂号单唯一编号(挂号)"  width="200" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="deptid" itemName="科室编号(挂号)"  showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="deptname" itemName="科室名(挂号)" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="drid" itemName="医生编号(挂号)" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="drname" itemName="医生名(挂号)" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="regfee" itemName="挂号费(挂号)" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="discountsamt" itemName="医院优惠金额" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="personamt" itemName="自付金额" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="receiptno" itemName="单据(挂号)"  width="200"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="accountpayment" itemName="院内账户支付" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="accountbalance" itemName="院内账户余额" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="treatfee" itemName="诊疗费(挂号)" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="regamt" itemName="总金额" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="paymoney" itemName="医保支付金额" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="payway" itemName="支付方式" showDetail="true" collection="PAYWAY"></ec:gridItem>
        <ec:gridItem itemId="paychannel" itemName="结算渠道" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="paytradeno" itemName="支付流水号"  width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="regreceipt" itemName="收据号"  width="200" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="status" itemName="结算类别" showDetail="true" collection="SETTLE_STATUS"></ec:gridItem>
        <ec:gridItem itemId="receiptlist" itemName="单据列表(门诊)" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="chargetypelist" itemName="费用类型列表(门诊)"  width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="personamtlist" itemName="自付金额列表(门诊)"  width="200" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="pyckjh" itemName="配药窗口集合(门诊)"  width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="fyckjh" itemName="发药窗口集合(门诊)"  width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="sjhinfo" itemName="缴费单号合集(门诊)" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="accountdate" itemName="his结算时间"  width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="addTime" itemName="系统结算时间"  width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="lastUpdate" itemName="最后更新时间"  width="200" showDetail="true"></ec:gridItem>
    </ec:grid>
</div>
</body>
</html>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp" %>
<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>

