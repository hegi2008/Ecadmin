<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>预约登记列表</title>
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
                    <label for="payAccount" class="control-label">患者编号</label>
                    <input id="payAccount" type="text" name="patid" class="form-control" placeholder="请输入患者编号">
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
                    <label for="receiveAccount" class="control-label">排班编号</label>
                    <input id="receiveAccount" type="text" name="tscid" class="form-control" placeholder="请输入排班编号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="agencyName" class="control-label">预约登记号</label>
                    <input id="agencyName" type="text" name="yydjh" class="form-control" placeholder="请输入医疗机构名称">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="paytradeno" class="control-label">科室名</label>
                    <input id="paytradeno" type="text" name="deptname" class="form-control" placeholder="请输入科室名">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="cardno" class="control-label">医生</label>
                    <input id="cardno" type="text" name="drname" class="form-control" placeholder="请输入医生">
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
                <div class="">
                    <label class="control-label">号源日期</label>
                    <div class="input-group date">
                        <input id="add_time_value_ts" name="add_time_value_ts" class="form-control" type="text">
                        <span class="input-group-addon">
					    	<span class="glyphicon glyphicon-calendar"></span>
					    </span>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class=""> <!-- 业务类型 1 登记 2 取消登记 -->
                    <label class="control-label">预约状态</label>
                    <select class="form-control" name="status">
                        <option value="">--全部--</option>
                        <option value="1">登记</option>
                        <option value="2">取消登记</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="container-fluid" style="height: 500px;">
    <ec:grid id="regs" title="登记列表" fit="true"   pageSize="10"  showRowno="true" singleSelect="false" showck="true" showHeaderContextMenu="true"  pagination="true"
             dataurl="${basePath}shh/reg/regManager/queryRegList">
        <ec:gridToolbar>
            <button type="button" class="btn  btn-primary btn-sm" onclick="queryGrid()"><i
                    class="fa fa-undo"></i>&nbsp;&nbsp;查询
            </button>
            <button type="button" class="btn  btn-primary btn-sm" onclick="resetQuery()"><i
                    class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询
            </button>
        </ec:gridToolbar>
        <ec:gridItem itemId="yydjh" itemName="预约登记号" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="orderId" itemName="订单编号" width="230"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="patName"  itemName="患者名"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="idcard" itemName="身份证" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="patid" itemName="患者编号" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="cardno" itemName="就诊卡号" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="tscid" itemName="排班编号" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="seqnum" itemName="号序" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="tscdate" itemName="号源日期" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="deptid" itemName="科室编号" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="deptname" itemName="科室名" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="drid" itemName="医生编号" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="drname" itemName="医生名"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="drtitle" itemName="职称" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="regfee" itemName="挂号费" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="treatfee" itemName="治疗费" showDetail="true" ></ec:gridItem>
        <ec:gridItem itemId="regamt" itemName="总费用" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="status" itemName="状态" showDetail="true" collection="REG_STATUS"></ec:gridItem>
        <ec:gridItem itemId="minutes" itemName="锁号时间" showDetail="true" hidden="true"></ec:gridItem>
        <ec:gridItem itemId="addTime" itemName="登记时间" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="lastUpdate" itemName="最后更新时间" width="200" showDetail="true"></ec:gridItem>
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
        selectDateForts();
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
    //选择日期
    function selectDateForts() {
        $('#add_time_value_ts').daterangepicker({
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
        Base.loadGridData("regs", "form1", null);
    }

    //重置查询
    function resetQuery() {
        $('#form1')[0].reset();
        queryGrid();
    }

    //操作按钮初始化
    function optionsf(value, data, index) {
        var arr = [];
        arr.push('<i class="fa fa-eye btn-primary" style="font-size: 16px;" title="登记详情" onclick="toDetail(\'' + data.orderId + '\')"></i>');
        arr.push('<i class="fa fa-search btn-warning" style="font-size: 16px;" title="对应订单" onclick="toOrder(\''+data.orderId+'\')"></i>');
        if(parseInt(data.minutes) >  60 && data.status == 1){

            arr.push('<i class="fa fa-remove btn-danger" style="font-size: 16px;" title="取消登记" onclick="cancel(\''+data.orderId+'\')"></i>');
        }
        return arr.join(' ');
    }

    //对应订单页面
    function toOrder(orderId) {
        Base.ajax({
            type: 'post',
            url: Base.globvar.basePath + "shh/order/orderManager/getOrderDetail",
            data: {orderId: orderId},
            success: function (data) {
                if (data.error === false) {
                    var url = Base.globvar.basePath + "shh/order/orderManager/toDetail?orderId="+orderId;
                    top.Base.openIframe({
                        href : url,
                        width		: '700px',
                        height		: '550px',
                        afterClose : function(){
                            Base.reloadGridData("orders");
                        }
                    });
                } else {
                    Base.alert("预约登记记录未匹配到订单(请关注是否自动释放号源 如在规定时间未自动释放请人工处理)!");
                }
            }
        });

    }

    //取消登记
    function cancel(orderId) {
        Base.confirm("取消登记将释放号源,请确认？", function (flag) {
            if (!flag) {
                return false;
            }
            Base.ajax({
                type: 'post',
                url: Base.globvar.basePath + "shh/reg/regManager/updateReg",
                data: {orderId: orderId},
                success: function (data) {
                    if (data.error === false) {
                        Base.alert("取消成功！");
                        queryGrid();
                    } else {
                        Base.alert("取消失败");
                    }
                }
            });
        }, "warning")
    }
    //详情页面
    function toDetail(orderId) {
        var url = Base.globvar.basePath + "shh/reg/regManager/toDetail?orderId="+orderId;
        top.Base.openIframe({
            href : url,
            width		: '700px',
            height		: '550px',
            afterClose : function(){
                Base.reloadGridData("orders");
            }
        });
    }
</script>
