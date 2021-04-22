<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="win-div">
<form id="loan_save_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 541px;border: 1px solid #D9E3E7;margin-top: 20px;"">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="easyAcc" style="width:754px;margin-left: 20px">
					<div title="基本信息" id="jksqjbxx" data-options="collapsible:false" style="overflow:auto;margin-top: 10px;">
						<table class="ourtable" cellpadding="0" cellspacing="0">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span> 借款单编号</td>
								<td colspan="4">
									<input style="width: 558px;height: 30px;" name="gCode" class="easyui-textbox"
									value="${bean.lCode}" data-options="required:true" readonly="readonly"/>
								</td>
							</tr>
						<tr class="trbody">
								<td class="td1"><span class="style1">*</span>借款单摘要</td>
								<td colspan="4">
									<input style="width: 558px;"
									name="loanPurpose" class="easyui-textbox"
									value="${bean.loanPurpose}" data-options="required:true,validType:'length[1,50]'"/>
								</td>
							</tr>
							

							<tr class="trbody">
								<td class="td1"><span class="style1">*</span> 借款金额</td>
								<td class="td2">
									<input style="width: 200px;" id="lAmount" name="lAmount" class="easyui-numberbox" 
									value="${bean.lAmount}" precision="2" data-options="icons: [{iconCls:'icon-yuan'}],required:true,formatter:function(value,row,index){return fomatMoney(value,2);}"/>
								</td>
								<%-- <td class="td1"><span class="style1">*</span> 借款时间</td>
								<td class="td2">
									<input style="width: 200px;"
									id="loanReqTime" name="reqTime" class="easyui-datebox"
									value="${bean.reqTime}" required="required" editable="false"/>
								</td> --%>

								<td class="td4">
									<!-- 隐藏域 --> 
									<!-- 主键ID --><input type="hidden" name="lId" value="${bean.lId}" />
									<!-- 借款单编号 --><input type="hidden" name="lCode" value="${bean.lCode}" />
									<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="loanflowStauts"/> 
									<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="loanStauts" />
									<!-- 下环节处理人姓名 --><input type="hidden" name="userName2" value="${bean.userName2}"/>
									<!-- 下环节处理人编码 --><input type="hidden" name="userId" value="${bean.userId}" />
									<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
									<!-- 指标ID --><input type="hidden" name="indexId" value="${bean.indexId}" id="F_fBudgetIndexCode"/>
									<!-- 指标类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="F_indexType"/>
									<!-- 指标剩余金额 --><input type="hidden" name="indexAmount" value="${bean.indexAmount}" id="indexAmount"/>
									<!-- 剩余还款金额 --><input type="hidden" id="leastAmount" name="leastAmount" value="${bean.leastAmount }">
									<!-- 指标明细id --><input type="hidden" id="F_proDetailId" name="proDetailId" value="${bean.proDetailId}">
								<td class="td1"><span class="style1">*</span> 计划还款日期</td>

								<td class="td2">
									<input style="width: 200px;" id="estChargeTime"
									name="estChargeTime" class="easyui-datebox"
									value="${bean.estChargeTime}"  required="required" editable="false"/>
								</td>
							</tr>

							<%-- <tr class="trbody">
								<td class="td1"><span class="style1">*</span> 预算指标</td>
								<td class="td2">
									<a onclick="openIndex()" href="#">
									<input style="width: 200px;" id="F_fBudgetIndexName" name="indexName" class="easyui-textbox"
									value="${bean.indexName}" readonly="readonly"
									data-options="prompt: '选择指标' ,icons: [{iconCls:'icon-sousuo'}],required:true" />
									</a>
								</td>

								<td class="td4">

								<td class="td1"><span class="style1">*</span> 申请金额</td>
								<td class="td2">
									<input style="width: 200px;" id="lAmount" name="lAmount" class="easyui-numberbox" 
									value="${bean.lAmount}" precision="2" data-options="icons: [{iconCls:'icon-yuan'}],required:true,formatter:function(value,row,index){return fomatMoney(value,2);}"/>
								</td>
							</tr> --%>
							<%-- <tr class="trbody">
								<td class="td1"><span class="style1">*</span> 借款人</td>
								<td class="td2">
									<input style="width: 200px;" class="easyui-textbox"
									value="${bean.userName}" readonly="readonly" required="required" data-options="validType:'length[1,25]'"/>
								<td class="td4">

									
								</td>

								<td class="td1"><span class="style1">*</span> 所属部门</td>
								<td class="td2">
									<input  class="easyui-textbox" style="width: 200px;" value="${bean.deptName}" readonly="readonly" required="required" data-options="validType:'length[1,25]'"/>
								</td>
							</tr> --%>
							
							
							<tr style="height:5px;"></tr>
							
							<tr style="height: 70px;">
								<td class="td1" valign="top"><span class="style1">*</span> 借款事由</td>
								<td colspan="4">
									<%-- <input class="easyui-textbox"
									data-options="multiline:true,required:false,validType:'length[1,250]'" name="loanReason"
									style="width:555px;height:70px" value="${bean.loanReason}"> --%>
									<textarea name="loanReason"  id="loanReason" class="textbox-text" required="required" oninput="textareaNum(this,'textareaNum1')" autocomplete="off"   style="border-radius: 5px;border: 1px solid #D9E3E7;width:551px;height:70px;resize:none">${bean.loanReason}</textarea>
								</td>
							</tr>
							<!-- <tr>
								<td align="right" colspan="5">
								可输入剩余数：<span id="textareaNum1" class="250">250</span>
								</td>
							</tr> -->
						</table>
					</div>
					
					<div title="收款人信息" id="jksqjxrxx"  data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<input hidden="hidden" value="${payee.rId}" name="rId"/>
						<jsp:include page="../payee-info-loan.jsp" />
					</div>
					
					<div title="附件" id="jksqjxrxx"  data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<table>
							<tr>
								<td class="td1" style="width:55px;text-align: left"><!-- <span class="style1">*</span> -->
									附件
									<input type="file" multiple="multiple" id="f" onchange="upladFile(this,null,'zcgl01')" hidden="hidden">
									<input type="text" id="files" name="files" hidden="hidden">
								</td>
								<td colspan="3" id="tdf">
									&nbsp;&nbsp;
									<a onclick="$('#f').click()" style="font-weight: bold;  " href="#">
										<img style="vertical-align:bottom;margin-bottom: 5px;" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
										<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
										 </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
									</div>
									<c:forEach items="${loanAttaList}" var="att">
										<div style="margin-top: 5px;">
										<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<img style="margin-top: 5px;" src="${base}/resource-modality/${themenurl}/sccg.png">
										&nbsp;&nbsp;&nbsp;&nbsp;
										<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
										</div>
									</c:forEach>
								</td>
							</tr>
						</table>
					</div>
					
					<c:if test="${openType == 'edit'}">
						<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
							<jsp:include page="../../check_history.jsp" />												
						</div>
					</c:if>
				</div>
			</div>
			
			<div class="window-left-bottom-div" style="margin-top: 15px;">
				<a href="javascript:void(0)" onclick="saveLoan(0)">
				<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveLoan(1)">
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
	
		<div class="window-right-div" style="width:254px;height: 591px">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
</div>
<script type="text/javascript">

/* function estChargeTime(){
	
	var loanReqTime = $('#loanReqTime').datebox('getValue');
	var estChargeTime = $('#estChargeTime').datebox('getValue');
	
	if(estChargeTime < loanReqTime){
		alert("预计冲账时间必须大于借款时间,请重新选择");
		$('#estChargeTime').datebox('setValue',null);
	}
	
} */

/* 
$('#estChargeTime').datebox({
	onChange: function(newValue, oldValue){
		
		var loanReqTime = $('#loanReqTime').datebox('getValue');
		estChargeTime = ChangeDateFormat(newValue);
		if(estChargeTime < loanReqTime && estChargeTime != ""){
			alert("预计冲账时间必须大于借款时间,请重新选择");
			$('#estChargeTime').datebox('setValue',null);
			newValue=null;
		}
	}
}); */

//保存
function saveLoan(flowStauts) {
	//设置审批状态
	$('#loanflowStauts').val(flowStauts);
	//设置申请状态
	$('#loanStauts').val(flowStauts);
	var lAmount=$('#lAmount').textbox('getValue'); //借款金额
	var indexAmount=$('#indexAmount').val(); //剩余金额
	if(parseFloat(lAmount)>parseFloat(indexAmount)){
		$.messager.alert('系统提示', "借款金额不能大于指标剩余金额", 'info');
		return false;
	}
	var loanReason = $('#loanReason').val();
	var lAmount = $('#lAmount').val();
	if(loanReason == ''){
		$.messager.alert('系统提示', "借款事由不能为空", 'info');
		return false;
	}
	if(lAmount <= 0){
		$.messager.alert('系统提示', "借款金额必须大于0", 'info');
		return false;
	}
	//提交
	$('#loan_save_form').form('submit', {
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
		url : base + '/loan/save',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				closeWindow();
				$('#loan_save_form').form('clear');
				$('#loanTab').datagrid('reload');
				$('#indexdb').datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}

//打开指标选择页面
function openIndex() {
	/* var win=creatFirstWin(' ',860,580,'icon-search','/quota/choiceIndex');
	win.window('open'); */
	var win=creatFirstWin('选择指标',860,580,'icon-search','/apply/choiceIndex?menuType=loanApply'); 
	win.window('open');
}
</script>
</body>