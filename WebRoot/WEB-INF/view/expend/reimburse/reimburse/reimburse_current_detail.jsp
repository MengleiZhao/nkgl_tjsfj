<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<body>
<div  data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:755px;height: 491px;border: 1px solid #D9E3E7;margin-top: 10px">
			<div class="window-left-top-div">
				<form id="reimburse_save_form" style="height: 0px;" method="post"  enctype="multipart/form-data">
				<!-- 隐藏域 --> 
				<!-- 主键ID --><input type="hidden" name="rId" value="${bean.rId}" id="rId"/>
				<!-- 报销单号 --><input type="hidden" name="rCode" value="${bean.rCode}"/>
				<!-- 申请单流水号 --><input type="hidden" name="gCode"<c:if test="${operation=='add' }">value="${applyBean.gCode}"</c:if><c:if test="${operation=='edit' }">value="${bean.gCode}"</c:if> />
				<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="reimburseFlowStauts" />
				<!-- 报销状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="reimburseStauts" />
				<%-- <!-- 下环节处理人姓名 --><input type="hidden" name="userName2" value="${bean.userName2}" />
				<!-- 下环节处理人编码 --><input type="hidden" name="userId" value="${bean.userId}" /> --%>
				<input hidden="hidden" value="${bean.fuserId}" name="fuserId" id="fuserId"/>
				<!-- 下环节处理人名称 --><input type="hidden" id="input_userName2" name="userName2" value="${bean.userName2}"/>
				<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
				<!-- 报销类型 --><input type="hidden" id="reimburseTypeHi" value="${bean.type}" name="type"/>
				<!-- 报销金额 --><input type="hidden" id="reimburseAmount" name="amount" <c:if test="${operation=='edit' }">value="${bean.amount}"</c:if><c:if test="${operation=='add'}">value="${applyBean.amount}"</c:if> />
				<!-- 项目支出明细ID --><input type="hidden" id="proDetailId" name="proDetailId" <c:if test="${operation=='edit' }">value="${bean.proDetailId}"</c:if><c:if test="${operation=='add'}">value="${applyBean.proDetailId}"</c:if> />
				<!-- 指标ID --><input type="hidden" name="indexId" value="${bean.indexId}" id=""/>
				<!-- 预算指标名称 --><input type="hidden" id="indexName" name="indexName" value="${bean.indexName}" />
				<!-- 指标类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="indexType"/>
				<!-- 报销人 --><input type="hidden" name="userName" value="${bean.userName}" id="input_userName"/>
				<!-- 报销部门 --><input type="hidden" name="deptName" value="${bean.deptName}" id="input_deptName"/>
				<!-- 报销说明 --><input type="hidden" name="reimburseReason"  id="input_reimburseReason"/>
				<!-- 是否冲销借款 --><input type="hidden" name="withLoan" value="${bean.withLoan}" id="F_withLoan"/>
				<!-- 报销摘要 --><input type="hidden" name="gName" value="${bean.gName}" id="gName"/>
				<!-- 立项项目名称 --><input type="hidden" name="proName" value="${bean.proName}" id="proName"/>
				<div id="panelID" class="easyui-panel" data-options="closed:true">
					<!-- 报销时间 --><input class="easyui-datetimebox" name="reimburseReqTime" value="${bean.reimburseReqTime}" id="input_reimburseReqTime" />
					<input type="hidden" id="applyAmount" name="applyAmount" value="${applyBean.amount}" />
					<input type="hidden"  id="payeeAmount" name="payeeAmount"/><!-- 转账金额 -->
					<input type="hidden" id="hbank" name="bank" value="${payee.bank}"  /><!-- 开户行 -->
					<input type="hidden" id="hbankAccount" name="bankAccount" value="${payee.bankAccount}" /><!-- 银行账户 -->
					<input type="hidden" id="hidCard" name="idCard" value="${payee.idCard}" /><!-- 身份证号 -->
					<input type="hidden" id="hpayeeName" name="payeeName" value="${payee.payeeName}" /><!-- 收款人 -->
					<input type="hidden" id="arry" name="arry" value="" />
					<input id="input_jkdid" hidden="hidden" value="${bean.loan.lId}" name="loan.lId"/>
					<input id="input_jkdamonut" value="${bean.loan.lAmount}" hidden="hidden"  />
				</div>
				<!-- 冲销金额 -->
				<input id="cxAmounts" value="${bean.cxAmount}" name="cxAmount" hidden="hidden"  />
				<input type="hidden" id="json1" name="form1" />
				<input type="hidden" id="payerinfoJson" name="payerinfoJson" />
				<input type="hidden" id="mingxiJson" name="mingxi"/>
				<!-- 附件信息 -->
				<input type="text" id="files" name="files" hidden="hidden">
			</form>
				<div title="报销单" style="margin-bottom:35px;width: 737px" data-options="iconCls:'icon-xxlb'">
					<!-- 基本信息 -->
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
					<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
						<table class="window-table" style="margin-top: 10px" cellspacing="0" cellpadding="0">
							<tr class="trbody">
								<td style="width: 70px;"><span class="style1">*</span> 摘要</td>
								<td class="td2" colspan="4">
									<input style="width: 620px;height: 30px;" readonly="readonly" id="gNames" class="easyui-textbox" value="${bean.gName}" />
								</td>
							</tr>
							<tr class="trbody">
								<td style="width: 70px;"><span class="style1">*</span>项目名称</td>
								<td class="td2" colspan="4">
									<input style="width: 620px;height: 30px;" id="proName_show" readonly="readonly" class="easyui-combobox" value="${bean.proName}" 
									data-options="editable:false,panelHeight:'auto',
									url:'${base}/reimburse/getProNameData?selected=${bean.proName}',
									method:'POST',valueField:'text',textField:'text',editable:false,validType:'selectValid',"/>
								</td>
							</tr>
							<tr class="trbody">
								<td style="width: 70px;"><span class="style1">*</span>报销日期</td>
								<td class="td2" >
									<input class="easyui-datebox" id="reimburseReqTime" name="reimburseReqTime" readonly="readonly" value="${bean.reimburseReqTime}" style="width: 200px;height: 30px;margin-left: 10px; margin-bottom: 5px;" >
								</td>
								<td style="width:120px"></td>
								<td class="td1" style="width: 92px;"></td>
								<td class="td2" >
								</td>
							</tr>
							<tr style="height:5px;"></tr>
							<tr class="trbody" style="margin-top: 10px;">
								<td style="width: 70px;" ><p style="margin-top: 10px"> 报销说明</p></td>
								<td class="td2" colspan="4">
									<input style="width: 620px;height: 60px;margin-top: 5px;"id="commreason"  readonly="readonly"  class="easyui-textbox" data-options="multiline:true"
									value="${bean.reimburseReason}" required="required" />
							</tr>
							<tr class="trbody">
								<td style="width: 70px;"><span class="style1">*</span> 经办人</td>
								<td class="td2">
									<input class="easyui-textbox" value="${bean.userName}" readonly="readonly" style="width: 200px; height: 30px;" readonly="readonly" required="required" data-options="validType:'length[1,50]'"/>
								</td>
								<td style="width:120px"></td>
								<td class="td1" style="width: 92px;">部门名称</td>
								<td class="td2">
									<input class="easyui-textbox" value="${bean.deptName}" readonly="readonly" style="width: 200px; height: 30px;" required="required" data-options="validType:'length[1,50]'" readonly="readonly"/>
								</td>
							</tr>
						</table>
						</div>				
					</div>
					<!-- 费用明细 -->
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px;width: 695px">
						<div title="费用明细" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
							<div style="overflow:auto;">
								<!--  通用事项申请明细 -->
								<jsp:include page="reimburse_current_mingxi_detail.jsp" />
							</div>
							<div style="overflow:hidden;margin-top: 0px">
								<!-- 通用事项发票明细 -->
								<jsp:include page="mingxi_current_detail.jsp" />
							</div>
						</div>
					</div>
					
					<!-- 收款人信息 -->
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
						<div title="收款人信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;padding:0px;">
							<div id="" style="overflow-x:hidden;margin-top: 0px;">
								<jsp:include page="payee-info_detail.jsp" />	
							</div>
							<div id="" style="overflow-x:hidden;margin-top: 20px;">
								<jsp:include page="payee-info-external.jsp" />	
							</div>
						</div>
					</div>
					<!-- 附件信息 -->
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;width: 695px">
						<div title="附件信息" data-options="collapsed:false,collapsible:false" style="overflow:auto; ">		
							<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
								<tr class="trbody">
									<td style="width:60px;text-align: right;">附件:</td>
									<td colspan="4">
										<c:if test="${!empty attaList1}">
										<c:forEach items="${attaList1}" var="att">
											<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a><br>
										</c:forEach>
									</c:if>
									<c:if test="${empty attaList1}">
										<span style="color:#999999;">暂未上传附件</span>
									</c:if>
									</td>
								</tr>
							</table>			
						</div>
					</div>
					<!-- 审批记录报销 -->
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
						<div title="审批记录" data-options=" collapsible:false" style="overflow:auto;padding:0px;">
								<jsp:include page="../check/check_history.jsp" />
						</div>
					</div>

				</div>
			</div>
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=支出管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<div class="window-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../../check_system.jsp" />
		</div>
	</div>
</div>

<script type="text/javascript">
//防止不停重新加载
var itineraryurlcount = 0;
function onclickcommset(){
	if(itineraryurlcount>0){
		itineraryurlcount+=1;
		return false;
	}else {
		itineraryurlcount+=1;
		$('#reimburse_itinerary_tab_id').datagrid('reload');
		return true;
	}
}
var detaiurlcount = 0;
function onclickcommsetdeatil(){
	if(detaiurlcount>=1){
		detaiurlcount+=1;
		return false;
	}else {
		detaiurlcount+=1;
		$('#appli-detail-dg').datagrid('reload');
		$('#check-history-dg-comm').datagrid('reload');
		return true;
	}
}

function onSelect2(date) {
	endday2 = date;
	startday2 = new Date(startday2);
	var d = (endday2 - startday2) / 86400000 + 1;
	if (d > 0) {
		$('#duration').numberbox("setValue", d);
	} else {
		$('#duration').numberbox("setValue", "");
	}
}
//显示详细信息手风琴页面
$(document).ready(function() {
	//设置申请金额
	var applyAmount = $("#applyAmount").val();
	if(applyAmount !=""){
		$('#applyAmount_span').html(fomatMoney(applyAmount,2)+" [元]");
	}
	var h = $("#reimburseTypeHi").textbox().textbox('getValue');
	if (h != "") {
		$('#reimburseType').textbox().textbox('setValue', h);
		$('#reimburseType').textbox().attr('readonly', true);
	}
	zzAmount();
	$("#input_jkdbh").textbox({
		onChange: function(newValue, oldValue) {
		}
	});
});

//保存
function saveReimburse(flowStauts) {
	/* var cxjk = $('input[name="withLoan"]:checked').val();
	$('#F_withLoan').val(cxjk); */
	getpayerinfoJson();
	commaccept();
	commjson();
	
	var reimburseAmount = 0.00;
	var rows = $('#rcurrent-edit-tab').datagrid('getRows');
	for(var i=0;i<rows.length;i++){
		if(rows[i].remibAmount!=""&&rows[i].remibAmount!=null){
			reimburseAmount =parseFloat(reimburseAmount) + parseFloat(rows[i].remibAmount);
		}
	}
	if(reimburseAmount<0.01){
		alert("请填写报销金额！");
		return false;
	}
	var payeeAmount = isNaN(parseFloat($("#payeeAmount").val()))?0.00: parseFloat($("#payeeAmount").val());
	var cxamount = parseFloat($('#cxAmounts').val());
	cxamount = isNaN(cxamount)?0.00:cxamount;
	if((parseFloat(payeeAmount)!=parseFloat(reimburseAmount))){
		alert('转账金额与报销金额不一致,请重新填写！');
		return false;
	}
	var payerinfoJson = $("#payerinfoJson").val();
	if((payerinfoJson==null||payerinfoJson=='')&&cxamount!=reimburseAmount){
		alert('请填写收款人信息');
		return false;
	}
	$("#gName").val($("#gNames").textbox('getValue'));
	$("#proName").val($("#proName_show").combobox('getValue'));
	var jsonStr1 = $("#form1").serializeJson();
	// 在后台反序列话成明细Json的对象集合
	$('#json1').val(jsonStr1);
	//$('#input_userName2').val($('#userName2').textbox('getValue'));//下级审批人名称
	$('#input_reimburseReason').val($('#commreason').textbox('getValue'));//报销说明
	var arry = new Array();
	var str ="";
	var j=${index};
	for(var i=0; i<=j-1; i++){
		str=$("#form_"+i).serializeJson();
		arry.push(str);
	}
	var arryjosn=JSON.stringify(arry);
	$("#arry").val(arryjosn);
	var nums=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	var applyAmount1 = (nums-num3).toFixed(2);
	//设置审批状态
	$('#reimburseFlowStauts').val(flowStauts);
	//设置报销状态
	$('#reimburseStauts').val(flowStauts);
	//附件的路径地址
	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	if(s==''){
		alert('请上传附件');	
		return
	}
	$("#files").val(s);
	if((parseFloat(nums)>parseFloat(num3))&&flowStauts==1){
		/* var rows = $('#rmxdg').datagrid('getRows');
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].standard!="据实列支"&&rows[i].standard!=null){
				if(parseFloat(rows[i].reimbSum)>parseFloat(rows[i].standard)){
					alert("报销金额不能超过支出标准！")
					return;
				}
			}
		} */
			var win=creatFirstWin(' ',360,280,'icon-search','/reimburse/overfulfil?standard='+num3+"&amount="+nums+"&applyAmount="+applyAmount1+"&sts="+flowStauts);
			win.window('open');
		}else{

			var h = $("#reimburseTypeHi").textbox().textbox('getValue');
			//提交
			$('#reimburse_save_form').form('submit', {
				onSubmit : function() {
					flag = $(this).form('enableValidation').form('validate');
					if (flag) {
						//如果校验通过，则进行下一步
						$.messager.progress();
					}else{
						//校验不通过，就打开第一个校验失败的手风琴
						openAccordion();
					}
					return flag;
				},
				url : base + '/reimburse/save',
				success : function(data) {
					if (flag) {
						$.messager.progress('close');
					}
					data = eval("(" + data + ")");
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$('#reimburse_save_form').form('clear');
						$('#current_dg').datagrid('reload');
						$('#indexdb').datagrid('reload');
						closeWindow();
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});
		}
}
//寻找相关制度
function findSystemFile(id) {
	$.ajax({ 
		url: base+"/cheter/systemFind?id="+id, 
		success: function(data){
			data=data.replace('\"','');
			data=data.replace('\"','');
			window.open(data);
    }});
}
//转账金额
function  zzAmount(){
	var num1=parseFloat($('#input_jkdamonut').val());
	var num2=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	if(isNaN(num1)){
		num1=0;
	}
	if(isNaN(num2)){
		num2=0;
	}
	if(isNaN(num3)){
		num3=0;
	}
	$("#skAccount").numberbox().numberbox('setValue',(num1+num2).toFixed(2));
}
function chooseUserid(){
	var win=creatSecondWin('选择归口部门',860,580,'icon-search','/user/chooseNextRole'); 
	win.window('open');
}

</script>
</body>

