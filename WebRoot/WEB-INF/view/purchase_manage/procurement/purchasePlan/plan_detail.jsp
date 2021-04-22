<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="window-div">
<form id="purchase_plan_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" style="width:715px;margin-left: 20px">
					<div  title="完善信息" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;代理机构</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" readonly="readonly" name="agency" style="width: 200px" value="${bean.agency}"/>
								</td>
								<td class="td4">
									<!-- 隐藏域 -->
									<input type="hidden" name="fId"  value="${bean.fId}"/>
									<input type="hidden" name="nCode" value="${bean.nCode}"/>
									<input type="hidden" name="flowStauts" id="flowStauts" value="${bean.flowStauts}"/><!--审批状态  -->
									<input type="hidden" name="stauts" value="${bean.stauts}"/><!--删除状态  -->
									<input type="hidden" name="fUser" value="${bean.fUser}"/><!-- 申请人ID -->
									<input type="hidden" name="fDeptId" value="${bean.fDeptId}"/><!-- 申请处室ID -->
									<input type="hidden" name="fDeptName" value="${bean.fDeptName}"/><!-- 申请处室 -->
									<input type="hidden" id="nextKey" value="${bean.nCode}"/><!-- 下一级审批节点  -->
									<input type="hidden" id="historyNodes" value="${historyNodes}"/><!--历史审批节点  -->
									<input type="hidden" name="fPurchasePlanCode" value="${bean.fPurchasePlanCode}"/>
									<input type="hidden" name="fPurchaseCode" value="${purchaseApplyBasic.fpCode}"/>
									<input type="hidden" name="fPurchaseWay" value="${purchaseApplyBasic.fpPype}"/>
									<input type="hidden" name="projectLeaderId" value="${bean.projectLeaderId}"/>
									<input type="hidden" name="projectLeaderName" value="${bean.projectLeaderName}"/>
									<input type="hidden" name="authorizedName" value="${purchaseApplyBasic.authorized}"/>
									<input type="hidden" id="processLeaderName" name="processLeaderName"/>
								</td>
								<td class="td1"></td>
								<td class="td2">
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;采购流程负责人</td>
								<td class="td2">
									<input class="easyui-combobox" readonly="readonly" id="processLeaderCombo" name="processLeaderId" data-options="editable:false,panelHeight:'auto',url:'${base}/purchasePlan/getUserByRole?selected=${bean.processLeaderId}',method:'POST',valueField:'id',textField:'text',editable:false,validType:'selectValid'" style="width: 200px" />
								</td>
								<td class="td4"></td>
								<td class="td1"></td>
								<td class="td2">
								</td>
							</tr>
						</table>
					</div>
					
					<div  title="基本信息" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" readonly="readonly" name="fPurchaseName" style="width: 200px" value="${purchaseApplyBasic.fpName}"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;预算金额</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" readonly="readonly" id="f_amount" name="amount" style="width: 200px" value="${purchaseApplyBasic.amount}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" readonly="readonly" name="fUserName" style="width: 200px" value="${bean.fUserName}"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请时间</td>
								<td class="td2">
									<input class="easyui-datebox" class="dfinput" name="fReqTime" readonly="readonly" style="width: 200px;" value="${bean.fReqTime}"/>
								</td>
							</tr>
						</table>
					</div>
					
					<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<jsp:include page="../../../check_history.jsp" />												
					</div> 
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="window-right-div" id="check_system_div" style="margin: 20px 20px 30px 0px;" data-options="region:'east',split:true">
			<jsp:include page="../../../check_system.jsp" />
		</div>
</form>
</div>
<script type="text/javascript">
	
	$(document).ready(function() {
		$("#processLeaderCombo").combobox({
			onSelect: function (rec) {
				$('#processLeaderName').val(rec.text);
			}
		});
		//申请金额
		var f_amount = $("#f_amount").val();
		if(f_amount !=""){
			$('#f_amount').val(fomatMoney(f_amount,2));
		}
	});
	 
</script>
</body>