<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div  data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
<form id="income-goldpay-update" action="${base}/Dispute/Save" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:755px;height: 491px;border: 1px solid #D9E3E7;margin-top: 10px;">
			<div class="win-left-top-div">
				<!-- 隐藏域 --> 
				<!-- 主键ID --><input type="hidden" name="fWarId" value="${goldPay.fWarId}" />
				<!-- 主键ID --><input type="hidden" name="fContId_GP" value="${goldPay.fContId_GP}" />
				<!-- 合同类型 --><input type="hidden" name="contType" value="${goldPay.contType}" />
				<!-- 基本信息 -->
				<div id="sqsqjbxx" style="overflow:auto;margin-top: 0px;">			
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
						<div title="保证金到账信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
							<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span> 保证金金额</td>
									<td class="td2" style="width: 281px;">
										<input class="easyui-numberbox" style="width: 200px; height: 30px;" id="fPayAmount" name="fPayAmount"
										data-options="icons:[{iconCls:'icon-yuan'}],precision:2" value="${goldPay.fPayAmount}" readonly="readonly" editable="false"/>
									</td>
									<td class="td1"><span class="style1">*</span> 保证金到账日期</td>
									<td class="td2">
										<input class="easyui-datebox" style="width: 200px; height: 30px;" id="fpayedDate" name="fpayedDate"
										data-options="" value="${goldPay.fpayedDate}" required="required" editable="false"/>
									</td>
								</tr>
							</table>
						</div>				
					</div>
				</div>
			</div>
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="GoldPaySaveForm()">
				<img src="${base}/resource-modality/${themenurl}/button/queren1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	</div>
</form>
</div>
<script type="text/javascript">
function GoldPaySaveForm(){
   	$('#income-goldpay-update').form('submit', {
		onSubmit:function(){ 
			flag=$(this).form('enableValidation').form('validate');
			if(flag){
				$.messager.progress();
			}
			return flag;
		}, 
		url:'${base}/GoldPay/incomeSave',
		type:'post',
		success:function(data){
			if(flag){
				$.messager.progress('close');
			}
			data=eval("("+data+")");
			if(data.success){
				$.messager.alert('系统提示', data.info, 'info');
				$('#income-goldpay-update').form('clear');
				$('#income-goldpay-dg').datagrid('reload'); 
				$('#income-goldpay-upt-dg').datagrid('reload'); 
				closeWindow();
			}else{
				$.messager.alert('系统提示', data.info, 'error');
				closeWindow();
			}
		} 
	});
}
</script>
</body>