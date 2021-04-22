<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>



<body >
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
		<table class="topTable" style="width: 100%;font-size: 12px;height: 40px;"  cellpadding="0" cellspacing="0">
			<tr id="directly_cashier_top">
				<td class="top-table-search" class="queryth">
					&nbsp;&nbsp;报销单编号&nbsp;
					<input id="directly_cashier_list_top_code" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销部门&nbsp;
					<input id="directly_cashier_list_top_deptName" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销时间&nbsp;
					<input id="directly_cashier_list_top_reqTime1" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateBegin[directly_cashier_list_top_reqTime2]"/>
					&nbsp;-&nbsp;
					<input id="directly_cashier_list_top_reqTime2" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateEnd[directly_cashier_list_top_reqTime1]"/>
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryDirectlyCashier();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearDirectlyCashier();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
			
			<tr id="reimburse_cashier_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					&nbsp;&nbsp;报销单编号&nbsp;
					<input id="reimburse_cashier_list_top_code" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销部门&nbsp;
					<input id="reimburse_cashier_list_top_deptName" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销时间&nbsp;
					<input id="reimburse_cashier_list_top_reqTime1" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateBegin[reimburse_cashier_list_top_reqTime2]"/>
					&nbsp;-&nbsp;
					<input id="reimburse_cashier_list_top_reqTime2" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateEnd[reimburse_cashier_list_top_reqTime1]"/>
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryReimburseCashier();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearReimburseCashier();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
			
			<tr id="loan_cashier_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					&nbsp;&nbsp;借款单编号&nbsp;
					<input id="loan_cashier_list_top_lCode" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;借款金额&nbsp;
					<input id="loan_cashier_list_top_lAmount1" style="width: 150px;height:25px;" class="easyui-numberbox"/>
					&nbsp;-&nbsp;
					<input id="loan_cashier_list_top_lAmount2" style="width: 150px;height:25px;" class="easyui-numberbox"/>
					
					&nbsp;&nbsp;所属部门&nbsp;
					<input id="loan_cashier_list_top_deptName" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryLoanCashier();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearLoanCashier();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
			
			
			<tr id="contract_enforcing_cashier_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					&nbsp;&nbsp;合同编号&nbsp;
					<input id="contract_enforcing_cashier_list_top_fcCode" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;合同名称&nbsp;
					<input id="contract_enforcing_cashier_list_top_fcTitle" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryContractEnforcingCashier();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearContractEnforcingCashier();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>   
	</div>
	<div class="list-table-tab">
		<div class="tab-wrapper" id="cashier-tab">
			<ul class="tab-menu">
				<li class="active" onclick="directlyCashierTopClick();">直接报销单</li>
			    <li onclick="reimburseCashierTopClick();">申请报销单</li>
			    <li onclick="loanCashierTopClick();">借款申请单</li>
			    <!-- <li onclick="purchaseCashierTopClick();">采购支付单</li> -->
			    <li onclick="contractCashierTopClick();">合同报销单</li>
			</ul>
				
				
			<div class="tab-content">
				<div style="height: 440px">
					<table id="directlyReimbCashierTab" class="easyui-datagrid"
					data-options="collapsible:true,url:'${base}/cashier/reimbursePage?reimburseType=0',
					method:'post',fit:true,pagination:true,singleSelect: true,
					selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
						<thead>
							<tr>
								<th data-options="field:'drId',hidden:true"></th>
								<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
								<th data-options="field:'drCode',align:'left',resizable:false,sortable:true" style="width: 15%">报销单编号</th>
								<th data-options="field:'type',align:'left',formatter:typeSet" style="width: 10%">报销类型</th>
								<th data-options="field:'summary',align:'left'" style="width: 15%">报销摘要</th>
								<th data-options="field:'deptName',align:'left',resizable:false,sortable:true" style="width: 10%">报销部门</th>
								<th data-options="field:'userName',align:'left',resizable:false,sortable:true" style="width: 10%">报销请人</th>
								<th data-options="field:'reqTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 7%">报销时间</th>
								<th data-options="field:'amount',align:'left',resizable:false,sortable:true" style="width: 10%">报销总金额</th>
								<th data-options="field:'cashierType',align:'left',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 8%">付款状态</th>
								<th data-options="field:'zc',align:'left',resizable:false,sortable:true,formatter:CZ1" style="width: 10%">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div style="height: 440px">
					<table id="reimburseCashierTab" class="easyui-datagrid"
					data-options="collapsible:true,url:'${base}/cashier/reimbursePage?reimburseType=1',
					method:'post',fit:true,pagination:true,singleSelect: true,
					selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
						<thead>
							<tr>
								<th data-options="field:'rId',hidden:true"></th>
								<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
								<th data-options="field:'rCode',align:'left',resizable:false,sortable:true" style="width: 15%">报销单编号</th>
								<th data-options="field:'type',align:'left',formatter:typeSet" style="width: 10%">报销类型</th>
								<th data-options="field:'gName',align:'left'" style="width: 15%">报销摘要</th>
								<th data-options="field:'deptName',align:'left',resizable:false,sortable:true" style="width: 10%">报销部门</th>
								<th data-options="field:'userName',align:'left',resizable:false,sortable:true" style="width: 10%">报销请人</th>
								<th data-options="field:'reimburseReqTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 10%">报销时间</th>
								<th data-options="field:'amount',align:'left',resizable:false,sortable:true" style="width: 10%">报销总金额</th>
								<th data-options="field:'cashierType',align:'left',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 7%">付款状态</th>
								<th data-options="field:'zc',align:'left',resizable:false,sortable:true,formatter:CZ2" style="width: 9%">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="height: 440px">
					<table id="loanCashierTab" class="easyui-datagrid"
					data-options="collapsible:true,url:'${base}/cashier/loanPage',
					method:'post',fit:true,pagination:true,singleSelect: true,
					selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
						<thead>
							<tr>
								<th data-options="field:'lId',hidden:true"></th>
								<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
								<th data-options="field:'lCode',align:'left',resizable:false,sortable:true" style="width: 10%">借款单编号</th>
								<th data-options="field:'loanPurpose',align:'left',resizable:false,sortable:true" style="width: 10%">用途</th>
								<th data-options="field:'userName',align:'left',resizable:false,sortable:true" style="width: 10%">借款人</th>
								<th data-options="field:'deptName',align:'left',resizable:false,sortable:true" style="width: 10%">所属部门</th>
								<th data-options="field:'reqTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 10%">申请时间</th>
								<th data-options="field:'estChargeTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 10%">预计冲账时间</th>
								<!-- <th data-options="field:'indexAmount',align:'left',resizable:false,sortable:true" style="width: 10%">可用预算金额</th> -->
								<th data-options="field:'lAmount',align:'left',resizable:false,sortable:true" style="width: 10%">借款金额</th>
								<th data-options="field:'cashierType',align:'left',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 10%">付款状态</th>
								<th data-options="field:'zc',align:'left',resizable:false,sortable:true,formatter:CZ5" style="width: 15%">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				<div style="height: 440px">
					<table id="contractReimbCashierTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/cashier/contractPage',
						method:'post',fit:true,pagination:true,singleSelect: true,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'fcId',hidden:true"></th>
									<th data-options="field:'payId',hidden:true"></th>
									<th data-options="field:'fPlanId',hidden:true"></th>
									<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
									<th data-options="field:'fcCode',align:'left',resizable:false,sortable:true" style="width: 15%">合同编号</th>
									<th data-options="field:'fcTitle',align:'left',resizable:false,sortable:true" style="width: 13%">合同名称</th>
									<th data-options="field:'fReceProperty',align:'left',resizable:false,sortable:true,formatter: paytype" style="width: 13%">付款名称</th>
									<th data-options="field:'fRecePlanTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 13%">计划付款时间</th>
									<th data-options="field:'fRecePlanAmount',align:'left',resizable:false,sortable:true" style="width: 13%">计划付款金额</th>
									<th data-options="field:'payStauts',align:'left',resizable:false,sortable:true,formatter:cashierTypeSet" style="width: 10%">付款状态</th>
									<th data-options="field:'cz',align:'left',resizable:false,sortable:true,formatter:CZ4" style="width: 15%">操作</th>
								</tr>
							</thead>
						</table>
				</div>
		
			</div>
		
		</div>
	</div>




</div>


<script type="text/javascript">
//加载tab页
flashtab('cashier-tab');

function cashierTypeSet(val, row) {
	if (val == "0"||val == null) {
		return '<span >' + "未付款" + '</span>';
	}else if (val == "1") {
		return '<span >' + "已付款" + '</span>';
	}
}
//合同付款名称（什么阶段的款）
function paytype(val, row) {
	if (val == "FKXZ-01") {
		return '<span >' + "首款" + '</span>';
	} else if (val == "FKXZ-02") {
		return '<span >' + "阶段款" + '</span>';
	}else if (val == "FKXZ-03") {
		return '<span >' + "验收款" + '</span>';
	}else if (val == "FKXZ-04") {
		return '<span >' + "质保款" + '</span>';
	}
}

//设置申请事项
function typeSet(val, row) {
	if (val == 0) {
		return '<span>' + "直接报销" + '</span>';
	} else if (val == 1) {
		return '<span>' + "通用事项报销" + '</span>';
	} else if (val == 2) {
		return '<span>' + "会议报销" + '</span>';
	} else if (val == 3) {
		return '<span>' + "培训报销" + '</span>';
	} else if (val == 4) {
		return '<span>' + "差旅报销" + '</span>';
	} else if (val == 5) {
		return '<span>' + "公务接待报销" + '</span>';
	} else if (val == 9) {
		return '<span>' + "合同报销" + '</span>';
	} else if (val == 6) {
		return '<span>' + "公务用车报销" + '</span>';
	} else if (val == 7) {
		return '<span>' + "公务出国报销" + '</span>';
	}
}


//设置审批状态
function flowStautsSet(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已审批" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待审批" + '</a>';
	}
}

//设置采购审批状态
function formatPrice(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	}
}
//设置付款申请状态
function formatPay(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
	} else if (val == -1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
	} else if (val == 9) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
	} else {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
	}
}
//设置验收状态
function formatRecive(val, row) {
	if (val == 0) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 未验收" + '</a>';
	} else if (val == 1) {
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已验收" + '</a>';
	}
}

//操作栏创建
function CZ1(val, row) {
	//直接报销
	if(row.cashierType==0){
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailReimburse(' + row.drId + ',0)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td>'+
		//审批操作
		'<td style="width: 25px">'+
		'<a href="#" onclick="cashierReimburse(' + row.drId + ',0)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fuqi1.png">'+
		'</a></td>'+
		
		'</tr></table>';
	}else {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailReimburse(' + row.drId + ',0)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td></tr></table>';
	}
}
function CZ2(val, row) {
	//申请报销
	if(row.register==0){
		//是否生成凭证（没生成）
		return 	'<table><tr style="width: 75px;height:20px">'+
		'<td style="width: 25px">'+
		'<a href="#" onclick="detail(' + row.rId + ','+row.type+')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td>'+
		'<td style="width: 25px">'+
		'<a href="#" onclick="pay_register(' + row.rId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fuqi1.png">'+
		'</a></td>'+
		'</tr></table>';
	}else if(row.register==1){
		//是否生成凭证（已生成）
		return 	'<table><tr style="width: 75px;height:20px">'+
		'<td style="width: 25px">'+
		'<a href="#" onclick="detail(' + row.rId + ','+row.type+')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td>'+
		'</td><td class="td4">'+
		'<a href="#" onclick="detail_register(\'' + row.gCode + '\')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/detailRegister1.png">'+
		'</a></td>'+
		
		'</tr></table>';
	}
}
function CZ4(val, row) {
	//合同报销
	if(row.payStauts==0){
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailContract(' + row.fcId + ',' + row.fPlanId + ','+row.payId+')" class="easyui-linkbutton">'+
		'<img onmouseovser="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td>'+
		
		'<td style="width: 25px">'+
		'<a href="#" onclick="cashierContract(' + row.fcId + ',' + row.fPlanId + ','+row.payId+')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fuqi1.png">'+
		'</a></td>'+
		
		'</tr></table>';
	}else if(row.payStauts==1){
		//凭证生成完成
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailContract(' + row.fcId + ',' + row.fPlanId + ','+row.payId+')" class="easyui-linkbutton">'+
		'<img onmouseovser="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		
	/* 	'<td style="width: 25px">'+
		'<a href="#" onclick="cashierContract(' + row.fcId + ',' + row.fPlanId + ','+row.payId+')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fuqi1.png">'+
		'</a></td>'+ */
		
		'</td><td class="td4">'+
		'<a href="#" onclick="detail_register(\'' + row.payCode + '\')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/detailRegister1.png">'+
		'</a></td>'+
		'</tr></table>';
	}
}
function CZ5(val, row) {
	//借款申请
	if(row.cashierType==0||row.cashierType==''||row.cashierType==null){
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailLoan(' + row.lId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td>'+
		'<td style="width: 25px">'+
		'<a href="#" onclick="cashierLoan(' + row.lId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fuqi1.png">'+
		'</a></td>'+
		'</tr></table>';
	}else {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="detailLoan(' + row.lId + ')" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		'</a></td>'+
		'</tr></table>';
		
	}
}
function showA(obj){
	obj.src=base+'/resource-modality/${themenurl}/list/select1.png';
}
function showB(obj){
	obj.src=base+'/resource-modality/${themenurl}/select2.png';
}

//跳转到付讫页面
function pay_register(id){
	var win = creatWin('付讫', 760, 310, 'icon-search', '/cashier/fundCheck/'+id+'?type=0');
	win.window('open');
}
//查看凭证库
function detail_register(code){
	var win = creatWin('凭证', 1040, 580, 'icon-search', '/Voucher/detailByBeanCode?code='+code);
	win.window('open');
}



/** 查看操作 **/

//跳转报销查看页面
function detailReimburse(id,reimburseType) {
	//0为直接报销1位申请报销2位采购支付3位合同支付
	var win = creatWin('出纳受理', 970, 580, 'icon-search', '/directlyReimburse/edit?id='+id+'&editType=0');
	win.window('open');
}
function detailReimburseOpen(id,reimburseType) {
	//0为直接报销1位申请报销2位采购支付3位合同支付
	var win = creatWin('出纳受理', 1070, 580, 'icon-search', '/directlyReimburse/edit?id='+id+'&editType=0');
	win.window('open');
}
//跳转申请报销查看页面
function detail(id,reimburseType) {
	
	if(reimburseType==1){
	var win = creatWin('出纳受理', 770, 580, 'icon-search', '/reimburse/edit?id='+id+'&editType=0');
	win.window('open');
	}else{
		var win = creatWin('出纳受理',1060, 580, 'icon-search', '/reimburse/edit?id='+id+'&editType=0');
		win.window('open');
	}
}
//跳转采购查看页面
function detailPurchase(id) {
	var win = creatWin('出纳受理', 970, 580, 'icon-search', '/audit/auditPurchase?id='+ id+'&cashier=1');
	win.window('open');
}
//跳转合同付款查看页面	
function detailContract(id,fPlanId,payId) {
	var win = creatWin('出纳受理', 970, 580, 'icon-search', '/Enforcing/detail?id='+id+'&fPlanId='+fPlanId+'&payId='+payId+'&cashier=1');
	win.window('open');
}
//跳转借款查看页面
function detailLoan(id) {
	var win = creatWin('出纳受理', 970, 580, 'icon-search', '/loan/edit?id='+ id+'&editType=0');
	win.window('open');
}

/** 付讫（原受理）操作 **/

//
function cashierReimburse(id,reimburseType) {
	//0为直接报销1位申请报销2位采购支付3位合同支付
	var win = creatWin('出纳受理', 705, 580, 'icon-search', '/audit/auditReimburse?id='+id+'&reimburseType='+reimburseType+'&cashier=1');
	win.window('open');
}
//
function cashierPurchase(id) {
	var win = creatWin('出纳受理', 705, 580, 'icon-search', '/audit/auditPurchase?id='+ id+'&cashier=1');
	win.window('open');
}
//  3位合同支付
function cashierContract(id,fPlanId,payId) {
	var win = creatWin('出纳受理', 705, 580, 'icon-search', '/audit/auditContract?id='+id+'&fPlanId='+fPlanId+'&payId='+payId+'&cashier=1');
	win.window('open');
}
//
function cashierLoan(id) {
	var win = creatWin('出纳受理', 705, 580, 'icon-search', '/loanCheck/check?id='+ id+'&cashier=1');
	win.window('open');
}

function directlyCashierTopClick(){
	$("#directlyReimbCashierTab").datagrid('reload');
	
	$("#directly_cashier_top").show();
	$("#reimburse_cashier_top").hide();
	$("#purchase_cashier_top").hide();
	$("#contract_enforcing_cashier_top").hide();
	$("#loan_cashier_top").hide();
}
function reimburseCashierTopClick(){
	$("#reimburseCashierTab").datagrid('reload');
	
	$("#directly_cashier_top").hide();
	$("#reimburse_cashier_top").show();
	$("#purchase_cashier_top").hide();
	$("#contract_enforcing_cashier_top").hide();
	$("#loan_cashier_top").hide();
}
function purchaseCashierTopClick(){
	$("#purchaseCashierTab").datagrid('reload');
	
	$("#directly_cashier_top").hide();
	$("#reimburse_cashier_top").hide();
	$("#purchase_cashier_top").show();
	$("#contract_enforcing_cashier_top").hide();
	$("#loan_cashier_top").hide();
}
function contractCashierTopClick(){
	$("#contractReimbCashierTab").datagrid('reload');
	
	$("#directly_cashier_top").hide();
	$("#reimburse_cashier_top").hide();
	$("#purchase_cashier_top").hide();
	$("#contract_enforcing_cashier_top").show();
	$("#loan_cashier_top").hide();
}
function loanCashierTopClick(){
	$("#loanCashierTab").datagrid('reload');
	
	$("#directly_cashier_top").hide();
	$("#reimburse_cashier_top").hide();
	$("#purchase_cashier_top").hide();
	$("#contract_enforcing_cashier_top").hide();
	$("#loan_cashier_top").show();
}

//直接报销查询
function queryDirectlyCashier() {
	var drCode="directly_cashier_list_top_code";
	var deptName="directly_cashier_list_top_deptName";
	var reqTime1="directly_cashier_list_top_reqTime1";
	var reqTime2="directly_cashier_list_top_reqTime2";
	
	
	$("#directlyReimbCashierTab").datagrid('load',{
		drCode:$("#"+drCode).textbox('getValue').trim(),
		reqTime1:$("#"+reqTime1).datebox('getValue').trim(),
		reqTime2:$("#"+reqTime2).datebox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(),
	});
}
//直接报销清除查询条件
function clearDirectlyCashier() {
	var drCode="directly_cashier_list_top_code";
	var deptName="directly_cashier_list_top_deptName";
	var reqTime1="directly_cashier_list_top_reqTime1";
	var reqTime2="directly_cashier_list_top_reqTime2";
	
	$("#"+drCode).textbox('setValue','');
	$("#"+reqTime1).datebox('setValue','');
	$("#"+reqTime2).datebox('setValue','');
	$("#"+deptName).textbox('setValue','');
	
	$("#directlyReimbCashierTab").datagrid('load',{});
}

//申请报销查询
function queryReimburseCashier() {
	var rCode="reimburse_cashier_list_top_code";
	var deptName="reimburse_cashier_list_top_deptName";
	var reqTime1="reimburse_cashier_list_top_reqTime1";
	var reqTime2="reimburse_cashier_list_top_reqTime2";
	
	
	$("#reimburseCashierTab").datagrid('load',{
		rCode:$("#"+rCode).textbox('getValue').trim(),
		reimburseReqTime1:$("#"+reqTime1).datebox('getValue').trim(),
		reimburseReqTime2:$("#"+reqTime2).datebox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(),
	});
}
//申请报销清除查询条件
function clearReimburseCashier() {
	var rCode="reimburse_cashier_list_top_code";
	var deptName="reimburse_cashier_list_top_deptName";
	var reqTime1="reimburse_cashier_list_top_reqTime1";
	var reqTime2="reimburse_cashier_list_top_reqTime2";
	
	$("#"+rCode).textbox('setValue','');
	$("#"+reqTime1).datebox('setValue','');
	$("#"+reqTime2).datebox('setValue','');
	$("#"+deptName).textbox('setValue','');
	
	$("#reimburseCashierTab").datagrid('load',{});
}

//采购支付查询
function queryPurchaseCashier() {
	var fpCode="purchase_cashier_list_top_code";
	var fpAmount1="purchase_cashier_list_top_fpAmount1";
	var fpAmount2="purchase_cashier_list_top_fpAmount2";
	var fpMethodStr="purchase_cashier_list_top_fpMethodStr";
	
	
	$("#purchaseCashierTab").datagrid('load',{
		fpCode:$("#"+fpCode).textbox('getValue').trim(),
		fpAmount1:$("#"+fpAmount1).numberbox('getValue').trim(),
		fpAmount2:$("#"+fpAmount2).numberbox('getValue').trim(),
		fpMethodStr:$("#"+fpMethodStr).textbox('getValue').trim(),
	});
}
//采购支付清除查询条件
function clearPurchaseCashier() {
	var fpCode="purchase_cashier_list_top_code";
	var fpAmount1="purchase_cashier_list_top_fpAmount1";
	var fpAmount2="purchase_cashier_list_top_fpAmount2";
	var fpMethodStr="purchase_cashier_list_top_fpMethodStr";
	
	$("#"+fpCode).textbox('setValue','');
	$("#"+fpAmount1).numberbox('setValue','');
	$("#"+fpAmount2).numberbox('setValue','');
	$("#"+fpMethodStr).textbox('setValue','');
	
	$("#purchaseCashierTab").datagrid('load',{});
}

//合同报销查询
function queryContractEnforcingCashier() {
	var fcCode="contract_enforcing_cashier_list_top_fcCode";
	var fcTitle="contract_enforcing_cashier_list_top_fcTitle";
	
	$("#contractReimbCashierTab").datagrid('load',{
		fcCode:$("#"+fcCode).textbox('getValue').trim(),
		fcTitle:$("#"+fcTitle).textbox('getValue').trim(),
	});
}

//合同报销清除查询条件
function clearContractEnforcingCashier() {
	var fcCode="contract_enforcing_cashier_list_top_fcCode";
	var fcTitle="contract_enforcing_cashier_list_top_fcTitle";
	
	$("#"+fcCode).textbox('setValue');
	$("#"+fcTitle).textbox('setValue');
	
	$("#contractReimbCashierTab").datagrid('load',{});
}

//借款申请查询
function queryLoanCashier() {
	var lCode="loan_cashier_list_top_lCode";
	var lAmount1="loan_cashier_list_top_lAmount1";
	var lAmount2="loan_cashier_list_top_lAmount2";
	var deptName="loan_cashier_list_top_deptName";
	
	
	$("#loanCashierTab").datagrid('load',{
		lCode:$("#"+lCode).textbox('getValue').trim(),
		lAmount1:$("#"+lAmount1).numberbox('getValue').trim(),
		lAmount2:$("#"+lAmount2).numberbox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(),
	});
}

//借款申请清除查询条件
function clearLoanCashier() {
	var lCode="loan_cashier_list_top_lCode";
	var lAmount1="loan_cashier_list_top_lAmount1";
	var lAmount2="loan_cashier_list_top_lAmount2";
	var deptName="loan_cashier_list_top_deptName";
	
	$("#"+lCode).textbox('setValue','');
	$("#"+lAmount1).numberbox('setValue','');
	$("#"+lAmount2).numberbox('setValue','');
	$("#"+deptName).textbox('setValue','');
	
	$("#loanCashierTab").datagrid('load',{});
}

$("#directly_cashier_list_top_reqTime1").datebox({
    onSelect : function(beginDate){
        $('#directly_cashier_list_top_reqTime2').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});

$("#reimburse_cashier_list_top_reqTime1").datebox({
    onSelect : function(beginDate){
        $('#reimburse_cashier_list_top_reqTime2').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
</script>
</body>
</html>

