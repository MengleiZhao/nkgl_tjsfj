<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>



<body >
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr id="directly_check_top">
				<td class="top-table-search" class="queryth">
				<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					<%-- <a id='zk' href="#" onclick="zk()">
						<img style="vertical-align:bottom;text-align:left;"  src="${base}/resource-modality/${themenurl}/button/zk.png" >
					</a>
					<a id='sq' hidden="hidden" href="#" onclick="sq()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/sq.png" >
					</a> --%>
					&nbsp;&nbsp;
					<a href="#" onclick="queryDirectlyCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearDirectlyCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				<!-- <div id='othersearchtd' class="top-table-search-td" hidden="hidden" style="margin-bottom: 10px;">
					&nbsp;&nbsp;报销单编号&nbsp;
					<input id="directly_check_list_top_code" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销部门&nbsp;
					<input id="directly_check_list_top_deptName" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销时间&nbsp;
					<input id="directly_check_list_top_reqTime1" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateBegin[directly_check_list_top_reqTime2]"/>
					&nbsp;-&nbsp;
					<input id="directly_check_list_top_reqTime2" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateEnd[directly_check_list_top_reqTime1]"/>
				</div> -->
				</td>
			</tr>
		
			<tr id="reimburse_check_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<input id="searchDataReim" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					<%-- <a id='zk' href="#" onclick="zk()">
						<img style="vertical-align:bottom;text-align:left;"  src="${base}/resource-modality/${themenurl}/button/zk.png" >
					</a>
					<a id='sq' hidden="hidden" href="#" onclick="sq()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/sq.png" >
					</a> --%>
					&nbsp;&nbsp;
					<a href="#" onclick="queryReimburseCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearReimburseCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
			<!-- 	<div id='othersearchtd' class="top-table-search-td" hidden="hidden" style="margin-bottom: 10px;">
					&nbsp;&nbsp;报销单编号&nbsp;
					<input id="reimburse_check_list_top_code" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销部门&nbsp;
					<input id="reimburse_check_list_top_deptName" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;报销时间&nbsp;
					<input id="reimburse_check_list_top_reqTime1" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateBegin[reimburse_check_list_top_reqTime2]"/>
					&nbsp;-&nbsp;
					<input id="reimburse_check_list_top_reqTime2" style="width: 150px;height:25px;" class="easyui-datebox" editable="false" validType="dateEnd[reimburse_check_list_top_reqTime1]"/>
				</div> -->
				</td>
			</tr>
			
			<tr id="contract_enforcing_check_top" style="display: none;">
				<td class="top-table-search" class="queryth">
				 <input id="searchDataContractReim" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
				 	<%-- <a id='zk' href="#" onclick="zk()">
						<img style="vertical-align:bottom;text-align:left;"  src="${base}/resource-modality/${themenurl}/button/zk.png" >
					</a>
					<a id='sq' hidden="hidden" href="#" onclick="sq()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/sq.png" >
					</a> --%>
					&nbsp;&nbsp;
					<a href="#" onclick="queryContractEnforcingCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearContractEnforcingCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				<!-- <div id='othersearchtd' class="top-table-search-td" hidden="hidden" style="margin-bottom: 10px;">
					&nbsp;&nbsp;合同编号&nbsp;
					<input id="contract_enforcing_list_top_fcCode" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;合同名称&nbsp;
					<input id="contract_enforcing_list_top_fcTitle" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;
				</div> -->
				</td>
			</tr>
			
			<tr id="goldPay_enforcing_check_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<!-- &nbsp;&nbsp;合同编号&nbsp;
					<input id="goldPay_enforcing_list_top_fcCode" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;合同名称&nbsp;
					<input id="goldPay_enforcing_list_top_fcTitle" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp; -->
					<input id="searchDataGoldPayReim" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="#" onclick="queryGoldPayEnforcingCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearGoldPayEnforcingCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
			<!--往来款报销-->
			<tr id="current_ledger_top" style="display: none;">
				<td class="top-table-search" class="queryth">
					<!-- &nbsp;&nbsp;合同编号&nbsp;
					<input id="goldPay_enforcing_list_top_fcCode" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;合同名称&nbsp;
					<input id="goldPay_enforcing_list_top_fcTitle" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp; -->
					<input id="searchDataGoldPayReim" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="#" onclick="queryGoldPayEnforcingCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearGoldPayEnforcingCheck();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
			
		</table>   
	</div>
	<div class="list-table-tab">
		<div class="tab-wrapper" id="rc-tab">
			<ul class="tab-menu">
				<li class="active" onclick="directlyTopClick()">直接报销</li>
			    <li onclick="reimburseTopClick()">申请报销</li>
			    <li onclick="contractTopClick()">合同报销</li>
			    <li onclick="currentLedgerTopClick()">往来款报销</li>
			    <li onclick="goldPayTopClick()">保证金报销</li>
			</ul>
				
			<div class="tab-content">
				<div style="height: 440px">
					<table id="directlyReimbCheckTab" class="easyui-datagrid" 
						data-options="collapsible:true,url:'${base}/reimburseCheck/reimbursePage?reimburseType=0',
						method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'drId',hidden:true"></th>
									<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
									<th data-options="field:'drCode',align:'center',resizable:false,sortable:true" style="width: 20%">报销单编号</th>
									<!-- <th data-options="field:'type',align:'center',formatter:typeSet" style="width: 15%">报销类型</th>
									<th data-options="field:'deptName',align:'center',resizable:false,sortable:true" style="width: 15%">报销部门</th>
									<th data-options="field:'userName',align:'center',resizable:false,sortable:true" style="width: 10%">报销请人</th> -->
									<th data-options="field:'summary',align:'center',resizable:false,sortable:true" style="width: 15%">摘要</th>
									<th data-options="field:'amount',align:'center',resizable:false,sortable:true" style="width: 8%">报销金额</th>
									<th data-options="field:'reqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 20%">报销时间</th>
									<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 15%">审批状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ1" style="width: 10%">操作</th>
								</tr>
							</thead>
						</table>
				</div>
				
				<div style="height: 440px">
					<table id="reimburseCheckTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/reimburseCheck/reimbursePage?reimburseType=1',
						method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'rId',hidden:true"></th>
									<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
									<th data-options="field:'rCode',align:'center',resizable:false,sortable:true" style="width: 18%">报销单编号</th>
									<th data-options="field:'type',align:'center',formatter:typeSet" style="width: 15%">报销类型</th>
									<th data-options="field:'deptName',align:'center',resizable:false,sortable:true" style="width: 15%">报销部门</th>
									<th data-options="field:'userName',align:'center',resizable:false,sortable:true" style="width: 10%">报销请人</th>
									<th data-options="field:'reimburseReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 15%">报销时间</th>
									<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 10%">审批状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ2" style="width: 10%">操作</th>
								</tr>
							</thead>
						</table>
				</div>
				<div style="height: 440px">
					<table id="contractReimbCheckTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/reimburseCheck/reimbursePage?reimburseType=1&type=8',
						method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'fcId',hidden:true"></th>
									<th data-options="field:'number',align:'center'" width="5%">序号</th>
									<th data-options="field:'rCode',align:'center'" width="15%">报销单号</th>
									<th data-options="field:'contCode',align:'center'" width="15%">合同编号</th>
									<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="20%">合同名称</th>
									<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="15%">付款金额(元)</th>
									<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 15%">审批状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ3" style="width: 15%">操作</th>
								</tr>
							</thead>
					</table>
				</div>
				<div style="height: 440px">
					<table id="current_ledger_dg" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/reimburseCheck/reimbursePage?reimburseType=1&type=9',
						method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'rId',hidden:true"></th>
									<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
									<th data-options="field:'rCode',align:'center',resizable:false,sortable:true" style="width: 18%">报销单编号</th>
									<th data-options="field:'type',align:'center',formatter:typeSet" style="width: 15%">报销类型</th>
									<th data-options="field:'deptName',align:'center',resizable:false,sortable:true" style="width: 15%">报销部门</th>
									<th data-options="field:'userName',align:'center',resizable:false,sortable:true" style="width: 10%">报销请人</th>
									<th data-options="field:'reimburseReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 15%">报销时间</th>
									<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 10%">审批状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ2" style="width: 10%">操作</th>
								</tr>
							</thead>
						</table>
				</div>
				<div style="height: 440px">
					<table id="goldPayReimbCheckTab" class="easyui-datagrid"
						data-options="collapsible:true,url:'${base}/reimburseCheck/reimbursePage?reimburseType=1&type=11',
						method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
						selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
							<thead>
								<tr>
									<th data-options="field:'rId',hidden:true"></th>
									<th data-options="field:'num',align:'center'" width="5%">序号</th>
									<th data-options="field:'rCode',align:'center'" width="15%">报销单号</th>
									<th data-options="field:'contCode',align:'center'" width="15%">合同编号</th>
									<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="20%">合同名称</th>
									<th data-options="field:'amount',align:'center',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}" width="15%">付款金额(元)</th>
									<th data-options="field:'flowStauts',align:'center',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 15%">审批状态</th>
									<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ4" style="width: 15%">操作</th>
								</tr>
							</thead>
					</table>
				</div>
			</div>
		
		</div>
	</div>
	
	
	
	
	<%-- <div class="list-table">
		<div title="直接报销">
		<div style="height: 440px">
			<table id="directlyReimbCheckTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimburseCheck/reimbursePage?reimburseType=0',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'drId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'drCode',align:'left',resizable:false,sortable:true" style="width: 15%">报销单编号</th>
						<th data-options="field:'type',align:'left',formatter:typeSet" style="width: 13%">报销类型</th>
						<th data-options="field:'deptName',align:'left',resizable:false,sortable:true" style="width: 13%">报销部门</th>
						<th data-options="field:'userName',align:'left',resizable:false,sortable:true" style="width: 13%">报销请人</th>
						<th data-options="field:'reqTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 13%">报销时间</th>
						<th data-options="field:'flowStauts',align:'left',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 13%">审批状态</th>
						<th data-options="field:'name',align:'left',resizable:false,sortable:true,formatter:CZ1" style="width: 15%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		</div>
		
		<div title="申请报销">
		<div style="height: 440px">
			<table id="reimburseCheckTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimburseCheck/reimbursePage?reimburseType=1',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'rId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'rCode',align:'left',resizable:false,sortable:true" style="width: 15%">报销单编号</th>
						<th data-options="field:'type',align:'left',formatter:typeSet" style="width: 13%">报销类型</th>
						<th data-options="field:'deptName',align:'left',resizable:false,sortable:true" style="width: 13%">报销部门</th>
						<th data-options="field:'userName',align:'left',resizable:false,sortable:true" style="width: 13%">报销请人</th>
						<th data-options="field:'reimburseReqTime',align:'left',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 13%">报销时间</th>
						<th data-options="field:'flowStauts',align:'left',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 13%">审批状态</th>
						<th data-options="field:'name',align:'left',resizable:false,sortable:true,formatter:CZ2" style="width: 15%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		</div>
		
		<div title="合同报销">
		<div style="height: 440px">
			<table id="contractReimbCheckTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/reimburseCheck/reimbursePage?reimburseType=2',
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
						<th data-options="field:'flowStauts',align:'left',resizable:false,sortable:true,formatter:flowStautsSet" style="width: 13%">审批状态</th>
						<th data-options="field:'cz',align:'left',resizable:false,sortable:true,formatter:CZ3" style="width: 15%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		</div>
		
	</div> --%>
</div>



<script type="text/javascript">
//加载tab页
flashtab('rc-tab');

//付款名称（什么阶段的款）
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
	} else if (val == 8) {
		return '<span>' + "合同报销" + '</span>';
	} else if (val == 6) {
		return '<span>' + "公务用车报销" + '</span>';
	} else if (val == 7) {
		return '<span>' + "公务出国报销" + '</span>';
	} else if (val == 9) {
		return '<span>' + "往来款报销" + '</span>';
	}
}

//设置审批状态
var c;
function flowStautsSet(val, row) {
	c = val;
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

//操作栏创建
function CZ1(val, row) {
	return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	'<a href="#" onclick="checkReimburse(' + row.drId + ',0,null)" class="easyui-linkbutton">'+
	'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/check1.png">'+
	'</a></td></tr></table>';
}
function CZ2(val, row) {
	return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	'<a href="#" onclick="checkReimburse(' + row.rId + ',1,'+row.type+')" class="easyui-linkbutton">'+
	'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/check1.png">'+
	'</a></td></tr></table>';
}
function CZ3(val, row) {
	return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	'<a href="#" onclick="contractCheckReimburse(' + row.rId + ',2' + ')" class="easyui-linkbutton">'+
	'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/check1.png">'+
	'</a></td></tr></table>';
}
function CZ4(val, row) {
	return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	'<a href="#" onclick="goldPayCheckReimburse(' + row.rId + ',11' + ')" class="easyui-linkbutton">'+
	'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/check1.png">'+
	'</a></td></tr></table>';
}


//跳转直接报销和申请报销审批页面
function checkReimburse(id,reimburseType,type) {
	var win = null;
	/* if(type=='1'){
		win = creatWin('报销审批', 770, 580, 'icon-search', '/reimburseCheck/check?id='+id+'&reimburseType='+reimburseType);
	}else { */
		win = creatWin('报销审批', 1075, 580, 'icon-search', '/reimburseCheck/check?id='+id+'&reimburseType='+reimburseType);
	/* } */
	win.window('open');
}
//跳转合同报销审批页面
function contractCheckReimburse(id,reimburseType) {
	var win = creatWin('审批', 1115, 600, 'icon-search', '/reimburseCheck/check?id='+id+'&reimburseType='+reimburseType);
	win.window('open');
}
//跳转合同报销审批页面
function goldPayCheckReimburse(id,reimburseType) {
	var win = creatWin('审批', 1115, 600, 'icon-search', '/reimburseCheck/check?id='+id+'&reimburseType='+reimburseType);
	win.window('open');
}

function directlyTopClick(){
	$("#directlyReimbCheckTab").datagrid('reload');
	$("#current_ledger_top").hide();
	$("#directly_check_top").show();
	$("#contract_enforcing_check_top").hide();
	$("#reimburse_check_top").hide();
	$("#goldPay_enforcing_check_top").hide();
}
function reimburseTopClick(){
	$("#reimburseCheckTab").datagrid('reload');
	$("#current_ledger_top").hide();
	$("#directly_check_top").hide();
	$("#reimburse_check_top").show();
	$("#contract_enforcing_check_top").hide();
	$("#goldPay_enforcing_check_top").hide();
}
function contractTopClick(){
	$("#contractReimbCheckTab").datagrid('reload');
	$("#current_ledger_top").hide();
	$("#directly_check_top").hide();
	$("#reimburse_check_top").hide();
	$("#contract_enforcing_check_top").show();
	$("#goldPay_enforcing_check_top").hide();
}
function currentLedgerTopClick(){
	$("#current_ledger_dg").datagrid('reload');
	$("#current_ledger_top").hide();
	$("#directly_check_top").hide();
	$("#reimburse_check_top").hide();
	$("#contract_enforcing_check_top").hide();
	$("#goldPay_enforcing_check_top").hide();
	$("#current_ledger_top").show();
}
function goldPayTopClick(){
	$("#goldPayReimbCheckTab").datagrid('reload');
	$("#current_ledger_top").hide();
	$("#directly_check_top").hide();
	$("#reimburse_check_top").hide();
	$("#contract_enforcing_check_top").hide();
	$("#goldPay_enforcing_check_top").show();
}

//直接报销查询
function queryDirectlyCheck() {
	/* var drCode="directly_check_list_top_code";
	var deptName="directly_check_list_top_deptName";
	var reqTime1="directly_check_list_top_reqTime1";
	var reqTime2="directly_check_list_top_reqTime2"; */
	var searchData="searchData";
	$("#directlyReimbCheckTab").datagrid('load',{
		/* drCode:$("#"+drCode).textbox('getValue').trim(),
		reqTime1:$("#"+reqTime1).datebox('getValue').trim(),
		reqTime2:$("#"+reqTime2).datebox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(), */
		searchData:$("#"+searchData).textbox('getValue').trim(),
	});
}
//直接报销清除查询条件
function clearDirectlyCheck() {
	/* var drCode="directly_check_list_top_code";
	var deptName="directly_check_list_top_deptName";
	var reqTime1="directly_check_list_top_reqTime1";
	var reqTime2="directly_check_list_top_reqTime2"; */
	var searchData="searchData";
	/* $("#"+drCode).textbox('setValue','');
	$("#"+reqTime1).datebox('setValue','');
	$("#"+reqTime2).datebox('setValue','');
	$("#"+deptName).textbox('setValue',''); */
	$("#"+searchData).textbox('setValue','');
	$("#directlyReimbCheckTab").datagrid('load',{});
}

//申请报销查询
function queryReimburseCheck() {
	 var rCode="reimburse_check_list_top_code";
	var deptName="reimburse_check_list_top_deptName";
	var reqTime1="reimburse_check_list_top_reqTime1";
	var reqTime2="reimburse_check_list_top_reqTime2"; 
	$("#reimburseCheckTab").datagrid('load',{
		 rCode:$("#"+rCode).textbox('getValue').trim(),
		reimburseReqTime1:$("#"+reqTime1).datebox('getValue').trim(),
		reimburseReqTime2:$("#"+reqTime2).datebox('getValue').trim(),
		deptName:$("#"+deptName).textbox('getValue').trim(), 
		searchData:$("#searchDataReim").textbox('getValue').trim(),
	});
}
//申请报销清除查询条件
function clearReimburseCheck() {
	 var rCode="reimburse_check_list_top_code";
	var deptName="reimburse_check_list_top_deptName";
	var reqTime1="reimburse_check_list_top_reqTime1";
	var reqTime2="reimburse_check_list_top_reqTime2"; 
	
	 $("#"+rCode).textbox('setValue','');
	$("#"+reqTime1).datebox('setValue','');
	$("#"+reqTime2).datebox('setValue','');
	$("#"+deptName).textbox('setValue',''); 
	$("#searchDataReim").textbox('setValue','');
	
	$("#reimburseCheckTab").datagrid('load',{});
}

//合同报销查询
function queryContractEnforcingCheck() {
	/* var fcCode="contract_enforcing_list_top_fcCode";
	var fcTitle="contract_enforcing_list_top_fcTitle"; */
	
	$("#contractReimbCheckTab").datagrid('load',{
		/* fcCode:$("#"+fcCode).textbox('getValue').trim(),
		fcTitle:$("#"+fcTitle).textbox('getValue').trim(), */
		searchData:$("#searchDataContractReim").textbox('getValue').trim(),
	});
}

//合同报销清除查询条件
function clearContractEnforcingCheck() {
	/* var fcCode="contract_enforcing_list_top_fcCode";
	var fcTitle="contract_enforcing_list_top_fcTitle";
	
	$("#"+fcCode).textbox('setValue','');
	$("#"+fcTitle).textbox('setValue',''); */
	$("#searchDataContractReim").textbox('setValue','');
	$("#contractReimbCheckTab").datagrid('load',{});
}

//保证金报销查询
function queryGoldPayEnforcingCheck() {
	/* var fcCode="goldPay_enforcing_list_top_fcCode";
	var fcTitle="goldPay_enforcing_list_top_fcTitle"; */
	$("#goldPayReimbCheckTab").datagrid('load',{
		/* fcCode:$("#"+fcCode).textbox('getValue').trim(),
		fcTitle:$("#"+fcTitle).textbox('getValue').trim(), */
		searchData:$("#searchDataGoldPayReim").textbox('getValue').trim(),
	});
}

//保证金报销清除查询条件
function clearGoldPayEnforcingCheck() {
	/* var fcCode="goldPay_enforcing_list_top_fcCode";
	var fcTitle="goldPay_enforcing_list_top_fcTitle";
	
	$("#"+fcCode).textbox('setValue','');
	$("#"+fcTitle).textbox('setValue',''); */
	$("#searchDataGoldPayReim").textbox('setValue','');
	
	$("#goldPayReimbCheckTab").datagrid('load',{});
}


$("#directly_check_list_top_reqTime1").datebox({
    onSelect : function(beginDate){
        $('#directly_check_list_top_reqTime2').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});


$("#reimburse_check_list_top_reqTime1").datebox({
    onSelect : function(beginDate){
        $('#reimburse_check_list_top_reqTime2').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
</script>
</body>
</html>

