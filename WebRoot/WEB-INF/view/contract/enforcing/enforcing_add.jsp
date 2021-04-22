<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<body>
<div  data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:755px;height: 491px;border: 1px solid #D9E3E7;margin-top:20px">
			<div class="window-left-top-div">
				<form id="reimburse_save_form" style="height: 0px;" method="post"  enctype="multipart/form-data">
					<!-- 隐藏域 --> 
					<!-- 主键ID --><input type="hidden" name="rId" value="${bean.rId}" id="rId"/>
					<!-- 合同ID --><input type="hidden" id="f_payId" name="payId" value="${bean.payId}">
					<!-- 变更合同状态 --><input type="hidden" id="fsealedStatus" value="${upt.fsealedStatus}">
					<!-- 合同变更状态 --><input type="hidden" id="contractUpdateStatus" value="${contractUpdateStatus}">
					<!-- 变更合同ID --><input type="hidden" id="uptId">
					<!-- 报销单号 --><input type="hidden" id="f_rCode" name="rCode" value="${bean.rCode}"/>
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
					<input type="hidden" id="json1" name="form1" />
					<input type="hidden" id="payerinfoJson" name="payerinfoJson" />
					<!-- 附件信息 -->
					<input type="text" id="files" name="files" hidden="hidden">
					<!-- 合同待付款id --><input type="hidden" id="f_receivplanid" name="receivplanid"  value="${bean.receivplanid }">
					<!-- 验收单id --><input type="hidden" id="f_checkacceptid" name="checkacceptid"  value="${bean.checkacceptid }">
					<!-- 固定入账单id --><input hidden="hidden" id="f_checkFixedAssetid" name="checkFixedAssetid" value="${bean.checkFixedAssetid }">
					<!-- 无形入账单id --><input hidden="hidden" id="f_checkintangibleAssetid" name="checkintangibleAssetid" value="${bean.checkintangibleAssetid }">
					<!-- 验收单name --><input hidden="hidden" id="f_checkacceptname" name="checkacceptName" value="${bean.checkacceptName }">
					<!-- 固定入账单name --><input hidden="hidden" id="f_checkFixedAssetname" name="checkFixedName" value="${bean.checkFixedName }">
					<!-- 无形入账单name --><input hidden="hidden" id="f_checkintangibleAssetname" name="checkintangibleName" value="${bean.checkintangibleName}">
					<!-- 入库单name --><input hidden="hidden" id="f_checkWarehouseWarrant" name="checkWarehouseWarrant" value="${bean.checkWarehouseWarrant}">
					<!-- 入库单Id --><input hidden="hidden" id="f_checkWarehouseWarrantid" name="checkWarehouseWarrantid" value="${bean.checkWarehouseWarrantid}">
					<!-- 付款计划id --><input hidden="hidden" id="f_receivplanids" value="${receivplanids}" />
				</form>
				<div id="sqsqjbxx" style="overflow:auto;margin-top: 0px;">			
					<jsp:include page="enforcing_baseinfo_add.jsp" />
				</div>
			</div>
			<div class="window-left-bottom-div" >
				<a href="javascript:void(0)" onclick="saveReimburse(0)">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveReimburse(1)">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=支出管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<c:if test="${bean.type!=1 }">
			<div class="window-right-div" data-options="region:'east',split:true">
				<jsp:include page="../../check_system.jsp" />
			</div>
		</c:if>
	</div>
</div>

<script type="text/javascript">
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

//保存
function saveReimburse(flowStauts) {
	var fsealedStatus = $('#fsealedStatus').val();
	if (fsealedStatus == '0') {
		alert('该合同正在进行变更，暂时无法保存！');
		return false;
	}

	getpayerinfoJson();
	$('#f_rCode').val($('#f_rCodeshow').textbox('getValue'));//设置报销单
	$('#f_checkacceptname').val($('#checkacceptshow').tagbox('getValues'));//验收单
	$('#f_checkFixedAssetname').val($('#checkFixedAssetshow').tagbox('getValues'));//固定资产入账单
	$('#f_checkintangibleAssetname').val($('#checkintangibleAssetshow').tagbox('getValues'));//无形资产入账单
	var row =$('#enfircing_payplan_dg').datagrid('getSelections');
	// 获取被选中行的索引 index
	//var index=$('#enfircing_payplan_dg').datagrid('getRowIndex',row);
	var rows = $('#enfircing_payplan_dg').datagrid('getChecked');
	var indexs ;
	for (var i = 0; i < rows.length; i++) {
		if(indexs == null || indexs == undefined || indexs==""){
			indexs = rows[i].fPlanId+',';
		}else{
			indexs = indexs + rows[i].fPlanId+',';
		}
		if(rows[i].fReceProof=='FKPJ-1'){//验收单
			var checkaccept = $('#checkacceptshow').tagbox('getValues');
			if(checkaccept==''){
				alert('请选择验收单号！');
				return false;
			}
		}else if(rows[i].fReceProof=='FKPJ-3'){//固定资产入账单
			var checkFixedAsset = $('#checkFixedAssetshow').tagbox('getValues');
			if(checkFixedAsset==''){
				alert('请选择固定资产入账单！');
				return false;
			}
		}else if(rows[i].fReceProof=='FKPJ-4'){//无形资产入账单
			var checkintangibleAsset = $('#checkintangibleAssetshow').tagbox('getValues');
			if(checkintangibleAsset==''){
				alert('请选择无形资产入账单！');
				return false;
			}
		}
	}
	if(typeof(indexs) == "undefined"){
		alert('至少选择一条付款条件！');
		return false;
	}
	$('#f_receivplanid').val(indexs.substring(0,indexs.length -1));
	$('#reimburseAmount').val($('#f_amount').numberbox('getValue'));
	/* var reimburseAmount = $('#f_amount').numberbox('getValue');
	var payeeAmount = isNaN(parseFloat($("#payeeAmount").val()))?0.00: parseFloat($("#payeeAmount").val());
	if(parseFloat(reimburseAmount)!=(parseFloat(payeeAmount))){
		alert('付款金额不等于转账金额,请重新填写');
		return false;
	} */
	var payerinfoJson = $("#payerinfoJson").val();
	if((payerinfoJson==null||payerinfoJson=='')){
		alert('请填写收款人信息');
		return false;
	}
	var userStr = '\"payeeName\":\"\"';
	if(payerinfoJson.indexOf(userStr) != -1 ){
		alert('请选择收款人');
		return false;
	}
	//设置审批状态
	$('#reimburseFlowStauts').val(flowStauts);
	//设置报销状态
	$('#reimburseStauts').val(flowStauts);
	//附件的路径地址
	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	/* if(s==''){
		alert('请上传附件');	
		return
	} */
	$("#files").val(s);

	var jsonStr1 = $("#form1").serializeJson();
	
	var fapiaoFlag = true;
	$('#commfapiao').find('tbody').each(function(){
		if($($($(this).children()[0]).children()[0]).children()[1].value == '') {
			fapiaoFlag = false;
			return false;
		}
	});
	$('#contractfapiao').find('tbody').each(function(){
		if($($($(this).children()[0]).children()[0]).children()[1].value == '') {
			fapiaoFlag = false;
			return false;
		}
	});
	
	/* if (!fapiaoFlag) {
		alert('请上传发票！');
		return;
	} */
	
	// 在后台反序列话成明细Json的对象集合
	$('#json1').val(jsonStr1);
	
	var h = $("#reimburseTypeHi").textbox().textbox('getValue');
	
	/* var checkintangibleAssetid = $('#f_checkintangibleAssetid').val();
	var checkFixedAssetid = $('#f_checkFixedAssetid').val();
	var checkacceptid = $('#checkacceptid').val(); */
	
	
	//提交
	$('#reimburse_save_form').form('submit', {
		onSubmit : function(param) {
			/* param.checkintangibleAssetid = checkintangibleAssetid;
			param.checkFixedAssetid = checkFixedAssetid;
			param.checkacceptid = checkacceptid; */
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
				$('#enforcing_dg').datagrid('reload');
				closeWindow();
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
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


</script>
</body>

