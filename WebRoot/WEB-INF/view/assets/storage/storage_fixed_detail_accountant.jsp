<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<script type="text/javascript" src="/resource/custom/custom.js"></script>
<body>
<form id="saveAccountant" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
<div class="window-div">
		<div class="window-left-div" data-options="region:'west',split:true" style="width:695px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<input type="hidden" name="fId_S" id="S_fId_S" value="${bean.fId_S}"/>
				<input type="hidden" name="fReqTime" id="S_fReqTime" value="${bean.fReqTime}"/>
				<div class="easyui-accordion" style="width:662px;margin-left: 15px;">
					<div title="资产登记表" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<table class="ourtable" border="0" cellpadding="0" cellspacing="0">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;资产增加单单号</td>
								<td  colspan="4">
									<input id="S_fAssStorageCode" readonly="readonly" required="required" class="easyui-textbox"  name="fAssStorageCode" data-options="prompt:'',validType:'length[1,40]'" value="${bean.fAssStorageCode}" style="width: 555px"/> 
								</td>								
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;资产类别</td>
								<td class="td2">
									<input class="easyui-combobox" id="storage_fixed_fAssClass" name="fAssClass" readonly="readonly" data-options="url:'${base}/lookup/lookupsJson?parentCode=GDZCLB&selected=${bean.fAssClass}',method:'post',valueField:'code',textField:'text',editable:false" style="width: 200px;">
								</td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span>&nbsp;取得方式</td>
								<td class="td2">
									<input id="storage_fixed_fGainingMethod" name="fGainingMethod" readonly="readonly" data-options="url:'${base}/lookup/lookupsJson?parentCode=QDFS&selected=${bean.fGainingMethod}',method:'get',valueField:'code',textField:'text',editable:false" style="width: 200px;" value="${bean.fGainingMethod}" class="easyui-combobox"></input>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;验收单号</td>
								<td class="td2">
									<a id="queryAcpCodeId" href="#">
									<input class="easyui-textbox" id="S_facpCode" readonly="readonly" name="facpCode" style="width: 200px" value="${bean.facpCode}"/>
									</a>
								</td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span>&nbsp;登记人</td>
								<td class="td2">
									<input class="easyui-textbox" class="dfinput" readonly="readonly" required="required" id="S_fOperator" name="fOperator"  data-options="validType:'length[1,50]'" style="width: 200px" value="${bean.fOperator}"/>
								</td>
							</tr>
						</table>
					</div>
					<div title="资产详情表" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<%@ include file="storage_fixed_add_plan_accountant.jsp" %>
					</div>
				</div>			
			</div>
			
			<div class="window-left-bottom-div">
					<a href="javascript:void(0)" onclick="saveAccountant();">
						<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="returnAccountant();">
						<img src="${base}/resource-modality/${themenurl}/button/tuihui1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="closeWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				&nbsp;&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
</div>
</form>
	<script type="text/javascript">
	var values = false;
    function saveAccountant(){
    	var getPlans = getPlan2();
    	var rows = $('#fixed_add_plan2').datagrid('getRows');
    	for (var i = 0; i < rows.length; i++) {
    		if(rows[i].fFinancialCode==null|| rows[i].fFinancialDate==null||rows[i].fDepreciationStatusCode==null||rows[i].fValueTypeCode==null||rows[i].amount==null||rows[i].fAppropriationAmount==null||rows[i].fUnappropriationAmount==null){
    			alert('请完善资产详情表，第'+(i+1)+'行数据！');
    			return false;
    		}
    		if(rows[i].fFinancialCode==""|| rows[i].fFinancialDate==""||rows[i].fDepreciationStatusCode==""||rows[i].fValueTypeCode==""||rows[i].amount==""||rows[i].fAppropriationAmount==""||rows[i].fUnappropriationAmount==""){
    			alert('请完善资产详情表，第'+(i+1)+'行数据！');
    			return false;
    		}
    	}
    	if(!values){
    		alert('请完善资产价值信息！');
    		return false;
    	}
			$('#saveAccountant').form('submit', {
   				onSubmit: function(param){ 
   					param.planJson=getPlans;
   					flag=$(this).form('enableValidation').form('validate');
   					if(flag){
   						$.messager.progress();
   					}
   					return flag;
   				}, 
   				url:base+'/Storage/saveAccountant',
   				success:function(data){
   					if(flag){
   						$.messager.progress('close');
   					}
   					data=eval("("+data+")");
   					if(data.success){
   						$.messager.alert('系统提示', data.info, 'info');
   						closeWindow();
   						$('#saveAccountant').form('clear');
   						$("#storage_fixed_dg").datagrid('reload'); 
   					}else{
   						$.messager.alert('系统提示', data.info, 'error');
   					}
   				} 
   			});		
		}
    function returnAccountant(){
			$('#saveAccountant').form('submit', {
   				onSubmit: function(param){ 
   					flag=$(this).form('enableValidation').form('validate');
   					if(flag){
   						$.messager.progress();
   					}
   					return flag;
   				}, 
   				url:base+'/Storage/updateSts',
   				success:function(data){
   					if(flag){
   						$.messager.progress('close');
   					}
   					data=eval("("+data+")");
   					if(data.success){
   						$.messager.alert('系统提示', data.info, 'info');
   						closeWindow();
   						$('#saveAccountant').form('clear');
   						$("#storage_fixed_dg").datagrid('reload'); 
   					}else{
   						$.messager.alert('系统提示', data.info, 'error');
   					}
   				} 
   			});		
		}
    
	function onselectfAssClass(rec){
		if(rec.code!='GDZCLB-02'&&rec.code!='GDZCLB-08'){
			var fGainingMethods = $('#storage_fixed_fGainingMethods').combobox('getData');
			for (var i = fGainingMethods.length-1; i >= 0; i--) {
				if(fGainingMethods[i].code=='QDFS-07'){
					fGainingMethods.splice(i,7);
				}
			}
			$('#storage_fixed_fGainingMethods').combobox('loadData',fGainingMethods);
		}else {
			$('#storage_fixed_fGainingMethods').combobox('reload',base+'/lookup/lookupsJson?parentCode=QDFS');
		}
	}
	</script>
</body>