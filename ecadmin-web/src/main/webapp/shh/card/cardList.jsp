<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags/ectags" prefix="ec" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>就诊卡列表</title>
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
                    <label for="outPlatformId" class="control-label">账户id</label>
                    <input id="outPlatformId" type="text" name="outPlatformId" class="form-control" placeholder="请输入账户id">
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
                    <label for="patName" class="control-label">患者姓名</label>
                    <input id="patName" type="text" name="patName" class="form-control" placeholder="请输入患者名">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="idcardNo" class="control-label">身份证号</label>
                    <input id="idcardNo" type="text" name="idcardNo" class="form-control" placeholder="请输入身份证号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="agencyNum" class="control-label">医疗机构编号</label>
                    <input id="agencyNum" type="text" name="agencyNum" class="form-control" placeholder="请输入医疗机构编号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="patid" class="control-label">患者编号</label>
                    <input id="patid" type="text" name="patid" class="form-control" placeholder="请输入患者编号">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="mobile" class="control-label">电话</label>
                    <input id="mobile" type="text" name="mobile" class="form-control" placeholder="请输入电话">
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label for="address" class="control-label">地址</label>
                    <input id="address" type="text" name="address" class="form-control" placeholder="请输入地址">
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
                    <label class="control-label">账户类型</label>
                    <select class="form-control" name="channel">
                        <option value="">--全部--</option>
                        <option value="10">微信</option>
                        <option value="20">支付宝</option>
                    </select>
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label class="control-label">卡状态</label>
                    <select class="form-control" name="status">
                        <option value="">--全部--</option>
                        <option value="1">绑定</option>
                        <option value="2">解绑</option>
                        <option value="3">挂失</option>
                        <option value="4">删除</option>
                    </select>
                </div>
            </div>
            <div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
                <div class="">
                    <label class="control-label">卡类型</label>
                    <select class="form-control" name="type">
                        <option value="">--全部--</option>
                        <option value="1">就诊卡</option>
                        <option value="2">医保卡</option>
                        <option value="3">居民健康卡</option>
                        <option value="4">身份证</option>
                        <option value="5">病人唯一识别码</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="container-fluid" style="height: 500px;">
    <ec:grid id="cards" title="就诊卡列表" fit="true"  pageSize="10"  showRowno="true" singleSelect="false" showck="true" showHeaderContextMenu="true"  pagination="true"
             dataurl="${basePath}shh/card/cardManager/queryCardList">
        <ec:gridToolbar>
            <button type="button" class="btn  btn-primary btn-sm" onclick="queryGrid()"><i
                    class="fa fa-undo"></i>&nbsp;&nbsp;查询
            </button>
            <button type="button" class="btn  btn-primary btn-sm" onclick="resetQuery()"><i
                    class="fa fa-refresh"></i>&nbsp;&nbsp;重置查询
            </button>
        </ec:gridToolbar>
        <ec:gridItem itemId="outPlatformId" itemName="账户id" width="230"  showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="patName" itemName="患者名" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="idcardNo" itemName="身份证" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="patid" itemName="患者编号" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="cardno" itemName="卡号" width="200"></ec:gridItem>
        <ec:gridItem itemId="status" itemName="卡状态" showDetail="true" collection="CARDSTATUS"></ec:gridItem>
        <ec:gridItem itemId="type" itemName="卡类型" showDetail="true" collection="CARDTYPE"></ec:gridItem>
        <ec:gridItem itemId="relationship" itemName="是否默认" showDetail="true" collection="ISMRJZK"></ec:gridItem>
        <ec:gridItem itemId="channel"  itemName="账户类型"  showDetail="true" collection="PAYCHANNEL"></ec:gridItem>
        <ec:gridItem itemId="agencyNum" itemName="机构编号" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="mobile" itemName="手机号" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="address" itemName="地址" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="bindTime" itemName="初次绑定时间" width="200" showDetail="true"></ec:gridItem>
        <ec:gridItem itemId="lastUpdatetime" itemName="最后更新时间" width="200" showDetail="true"></ec:gridItem>
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
        Base.loadGridData("cards", "form1", null);
    }

    //重置查询
    function resetQuery() {
        $('#form1')[0].reset();
        queryGrid();
    }

    //操作按钮初始化
    function optionsf(value, data, index) {
        var arr = [];
        arr.push('<i class="fa fa-eye btn-primary" style="font-size: 16px;" title="卡详情" onclick="toDetail(\'' + data.outPlatformId + '\',\''+data.cardno+'\',\''+data.status+'\',\''+data.channel+'\',\''+data.agencyNum+'\')"></i>');
        arr.push('<i class="fa fa-edit btn-warning" style="font-size: 16px;" title="修改" onclick="toEdit(\'' + data.outPlatformId + '\',\''+data.cardno+'\',\''+data.status+'\',\''+data.channel+'\',\''+data.agencyNum+'\')"></i>');
        return arr.join(' ');
    }

    //详情页面
    function toDetail(outPlatformId,cardno,status,channel,agencyNum) {
        var url = Base.globvar.basePath + "shh/card/cardManager/toDetail?outPlatformId="+outPlatformId+"&cardno="+cardno+"&status="+status+"&channel="+channel+"&agencyNum="+agencyNum;
        top.Base.openIframe({
            href : url,
            width		: '700px',
            height		: '550px',
            afterClose : function(){
                Base.reloadGridData("cards");
            }
        });
    }
    //修改页面
    function toEdit(outPlatformId,cardno,status,channel,agencyNum) {
        var url = Base.globvar.basePath + "shh/card/cardManager/toEditDetail?outPlatformId="+outPlatformId+"&cardno="+cardno+"&status="+status+"&channel="+channel+"&agencyNum="+agencyNum;
        top.Base.openIframe({
            href : url,
            width		: '700px',
            height		: '550px',
            afterClose : function(){
                Base.reloadGridData("cards");
            }
        });
    }


</script>
