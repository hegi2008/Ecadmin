<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>订单列表</title>
    <%@ include file="/UI/themes/hplus/head.inc.jsp" %>
    <link href="<%=basePath%>UI/plugins/easyui/easyui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>UI/plugins/bootstrap-daterangepicker/daterangepicker.min.css" type="text/css">
</head>
<body>
<div class="container-fluid ec-form">
    <form id="form1" method="post" class="form-horizontal">
        <div class="row">
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="orderId" class="control-label">订单编号</label>
                    <input id="orderId" type="text" name="orderId" class="form-control" placeholder="请输入订单编号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="payAccount" class="control-label">账户id</label>
                    <input id="payAccount" type="text" name="payAccount" class="form-control" placeholder="请输入账户id">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="patName" class="control-label">患者姓名</label>
                    <input id="patName" type="text" name="patName" class="form-control" placeholder="请输入患者名">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="idcard" class="control-label">身份证号</label>
                    <input id="idcard" type="text" name="idcard" class="form-control" placeholder="请输入身份证号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="receiveAccount" class="control-label">医疗机构编号</label>
                    <input id="receiveAccount" type="text" name="receiveAccount" class="form-control" placeholder="请输入医疗机构编号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="agencyName" class="control-label">医疗机构名称</label>
                    <input id="agencyName" type="text" name="agencyName" class="form-control" placeholder="请输入医疗机构名称">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="paytradeno" class="control-label">交易流水号</label>
                    <input id="paytradeno" type="text" name="paytradeno" class="form-control" placeholder="请输入交易流水号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="cardno" class="control-label">患者卡号</label>
                    <input id="cardno" type="text" name="cardno" class="form-control" placeholder="请输入交易流水号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label class="control-label">添加日期</label>
                    <div class="input-group date">
                        <input id="add_time_value" name="add_time_value" class="form-control" type="text">
                        <span class="input-group-addon">
					    	<span class="glyphicon glyphicon-calendar"></span>
					    </span>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class=""> <!-- 业务类型 1 挂号 2 门诊 3住院 -->
                    <label class="control-label">业务类型</label>
                    <select class="form-control" name="consumeType">
                        <option value="">--全部--</option>
                        <option value="1">挂号</option>
                        <option value="2">门诊</option>
                        <option value="3">住院</option>
                    </select>
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label class="control-label">支付状态</label>
                    <select class="form-control" name="payResult">
                        <option value="">--全部--</option>
                        <option value="01">待支付</option>
                        <option value="02">支付成功</option>
                        <option value="03">支付失败</option>
                        <option value="04">退费</option>
                    </select>
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label class="control-label">his状态</label>
                    <select class="form-control" name="hisResult">
                        <option value="">--全部--</option>
                        <option value="01">初始状态</option>
                        <option value="02">处理成功</option>
                        <option value="03">处理失败</option>
                        <option value="04">退费</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="container-fluid" style="height: 500px;">
    <ec:grid id="orders" title="订单列表" fit="true"  pageSize="10"  showRowno="true" singleSelect="false" showck="true" showHeaderContextMenu="true"  pagination="true"
             dataurl="${basePath}shh/order/orderManager/queryOrderList">
        <ec:gridToolbar>
            <button type="button" class="btn  btn-primary btn-sm" onclick="queryGrid()"><i
                    class="fa fa-undo"></i>&nbsp;&nbsp;查询
            </button>
            <button type="button" class="btn  btn-primary btn-sm" onclick="resetQuery()"><i
                    class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询
            </button>
        </ec:gridToolbar>
        <ec:gridItem itemId="orderId" itemName="订单编号" width="200"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="patName"  itemName="患者名"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="idcard" itemName="身份证" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="payAccount" itemName="账户id" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="patid" itemName="患者编号" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="cardno" itemName="就诊卡号" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="agencyNum" itemName="机构编号" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="agencyName" itemName="机构名" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="tradeBalance" itemName="订单金额"></ec:gridItem>
        <ec:gridItem itemId="consumeType" itemName="业务类型" showDetail="true" collection="YWLX"></ec:gridItem>
        <ec:gridItem itemId="payResult" itemName="支付状态" showDetail="true" collection="PAY_RESULT"></ec:gridItem>
        <ec:gridItem itemId="hisResult" itemName="his状态" showDetail="true" collection="HISRESULT"></ec:gridItem>
        <ec:gridItem itemId="paytradeno" itemName="流水号" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="tradeDesc" itemName="交易描述" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="paychannel" itemName="订单来源" showDetail="true" collection="PAYCHANNEL"></ec:gridItem>
        <ec:gridItem itemId="payway" itemName="支付方式" showDetail="true" collection="PAYWAY"></ec:gridItem>
        <ec:gridItem itemId="payType" itemName="支付类型" showDetail="true" collection="PAY_TYPE"></ec:gridItem>
        <ec:gridItem itemId="orderAddTime" itemName="订单生成时间" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="finishTime" itemName="订单完成时间" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="options" itemName="操作" formatter="optionsf" ></ec:gridItem>
    </ec:grid>
</div>
</body>
</html>
<%@include file="/UI/themes/hplus/basic.js.inc.jsp" %>
<script src="<%=basePath%>UI/plugins/easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>UI/plugins/easyui/easyui.plugins.min.js"></script>
<script src="<%=basePath%>UI/plugins/bootstrap-daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        main();
    });

    function main() {
        selectDate();
    }

    //选择日期
    function selectDate() {
        $('#add_time_value').daterangepicker({
            autoApply: true,
            autoUpdateInput: false,
            ranges: {
                "今天": [
                    moment(),
                    moment()
                ],
                "昨天": [
                    moment().subtract(1, 'day'),
                    moment().subtract(1, 'day')
                ],
                "最近七天": [
                    moment().subtract(6, 'day'),
                    moment()
                ],
                "最近30天": [
                    moment().subtract(29, 'day'),
                    moment()
                ],
                "当月": [
                    moment().startOf('month'),
                    moment().endOf('month')
                ],
                "上月": [
                    moment().subtract(1, 'month').startOf('month'),
                    moment().subtract(1, 'month').endOf('month')
                ]
            }
        }, function (start, end, label) {
            // 这里是回调函数
        }).on('apply.daterangepicker', function (e, pk) {
            $(this).val(pk.startDate.format(pk.locale.format) + pk.locale.separator + pk.endDate.format(pk.locale.format));
        });
    }

    //刷新表格
    function queryGrid() {
        Base.loadGridData("orders", "form1", null);
    }

    //重置查询
    function resetQuery() {
        $('#form1')[0].reset();
        queryGrid();
    }

    //订单操作按钮初始化
    function optionsf(value, data, index) {
        var arr = [];
        arr.push('<i class="fa fa-eye btn-primary" style="font-size: 16px;" title="订单详情" onclick="toSettment(\'' + data.orderId + '\')"></i>');
        arr.push('<i class="fa fa-money btn-info" style="font-size: 16px;" title="结算详情" onclick="toSettment(\'' + data.orderId + '\')"></i>');
        arr.push('<i class="fa fa-edit btn-warning" style="font-size: 16px;" title="修改订单" onclick=""></i>');
        arr.push('<i class="fa fa-remove btn-danger" style="font-size: 16px;" title="退费" onclick=""></i>');
        return arr.join(' ');
    }
    //订单结算页面
    function toSettment(orderId) {
        var url = Base.globvar.basePath + "shh/order/orderManager/toSettle?orderId="+orderId;
        top.Base.openIframe({
            title:'结算详情',
            href : url,
            width		: '800px',
            height		: '550px',
            afterClose : function(){
                Base.reloadGridData("account");
            }
        });
    }

</script>
