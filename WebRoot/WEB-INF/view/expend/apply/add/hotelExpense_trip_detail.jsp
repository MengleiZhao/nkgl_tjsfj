<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px;border-top: 1px solid rgba(217,227,231,1);">

	<table id="hoteltabApplyTrip" class="easyui-datagrid" style="width:707px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#hoteltoolTrip',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/hotelJson?gId=${bean.gId}&travelType=GWCX',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	striped : true,
	nowrap : true,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'ffId',hidden:true"></th>
				<th data-options="field:'checkInTime',width:140,align:'center',editor:{type:'datebox', options:{editable:false}},formatter:ChangeDateFormat" >入住时间</th>
				<th data-options="field:'checkOUTTime',width:140,align:'center',editor:{type:'datebox',options:{editable:false}},formatter:ChangeDateFormat" >退房时间</th>
				<th data-options="field:'locationCity',width:150,align:'center',
                        editor:{
                            type:'combobox',
                            options:{
                                valueField:'id',
                                textField:'text',
                                editable:false,
                                multiple:false,
                            }}">所在辖区</th>
					<th data-options="field:'travelPersonnel',width:130,align:'center',
                        editor:{
                            type:'combobox',
                            options:{
                                valueField:'id',
                                textField:'text',
                                editable:false,
                                multiple: true,
                            }}">住宿人员</th>
				<th data-options="field:'applyAmount',align:'center',width:120,editor:{type:'numberbox',options:{editable:true,precision:2}}">申请费用</th>
			</tr>
		</thead>
	</table>
	<div id="hoteltoolTrip" style="height:20px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">住宿费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
	</div>
		<a style="float: right;color: #666666;font-size:12px;">申请金额：<span id=""><fmt:formatNumber groupingUsed="true" value="${bean.hotelAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
</div>
<script type="text/javascript">
</script>