<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<!-- <div class="window-title">审批记录</div> -->
<style>
	 .footer{
        width:100%;
        position:absolute;
        bottom:0;
		text-align: center;
    }

</style>
<!-- <div class="window-tab" style="margin-left: 0px">这个是去掉左移 -->

	<table id="history_records_tab" class="easyui-datagrid"  style="width:695px;height:auto"
	data-options="
	singleSelect:true,
	url: '${base}/payment/RepaymentHistoryRecordsPageList?lId=${loan.lId}',
	method: 'post'
	">
	<thead>
		<tr>
			<th data-options="field:'num',align:'center',resizable:false,sortable:true" width="20%">序号</th>
			<th data-options="field:'payTime',required:'required',align:'center',width:'26.5%',formatter: ChangeDateFormatIndex">还款时间</th>
			<th data-options="field:'payAmount',required:'required',align:'center',width:'25%'">还款/冲销金额（元）</th>
			<th data-options="field:'surplusPayAmount',required:'required',align:'left',width:'26%'">剩余还款金额（元）</th>
		</tr>
	</thead>
	</table>