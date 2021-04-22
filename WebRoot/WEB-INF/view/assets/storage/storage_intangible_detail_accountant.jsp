<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<script type="text/javascript" src="/resource/custom/custom.js"></script>
<body>
<div class="win-div" style="">
<form id="saveAccountant" action="${base}/Storage/saveAccountant" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<input type="hidden" name="fId_S" id="S_fId_S" value="${bean.fId_S}"/>
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
									<input class="easyui-combobox" id="storage_intangible_fAssClass" name="fAssClass"  data-options="url:'${base}/lookup/lookupsJson?parentCode=WXZCLB&selected=${bean.fAssClass}',method:'post',valueField:'code',textField:'text',editable:false,onSelect:onselectIntangiblefAssClass" style="width: 200px;">
								</td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span>&nbsp;形成方式</td>
								<td class="td2">
									<input id="storage_intangible_fGainingMethod" name="fGainingMethod"  data-options="url:'${base}/lookup/lookupsJson?parentCode=WXZCQDFS&selected=${bean.fGainingMethod}',method:'get',valueField:'code',textField:'text',editable:false" style="width: 200px;" class="easyui-combobox"></input>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;验收单号</td>
								<td class="td2">
									<a id="queryAcpCodeId" href="#">
									<input class="easyui-textbox" id="S_facpCode" readonly="readonly" name="facpCode" style="width: 200px" value="${bean.facpCode}" />
									</a>
								</td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span>&nbsp;登记人</td>
								<td class="td2" colspan="4">
									<input class="easyui-textbox" class="dfinput" readonly="readonly" required="required" id="S_fOperator" name="fOperator"  data-options="validType:'length[1,50]'" style="width: 200px" value="${bean.fOperator}"/>
								</td>
							</tr>
						</table>
					</div>
					<div title="资产详情表" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<%@ include file="storage_intangible_add_plan_accountant.jsp" %>
					</div>
				</div>			
			</div>
			
			<div class="win-left-bottom-div">
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
</div>
	<script type="text/javascript">
	var values = false;
    function saveAccountant(){
	    	var getPlans = getPlan2();
	    	var rows = $('#intangible_add_plan2').datagrid('getRows');
	    	for (var i = 0; i < rows.length; i++) {
	    		if(rows[i].fFinancialCode==null|| rows[i].fFinancialDate==null||rows[i].fAmortizeStatusCode==null||rows[i].fValueTypeCode==null||rows[i].amount==null||rows[i].fAppropriationAmount==null||rows[i].fUnappropriationAmount==null){
	    			alert('请完善资产详情表，第'+(i+1)+'行数据！');
	    			return false;
	    		}
	    		if(rows[i].fFinancialCode==""|| rows[i].fFinancialDate==""||rows[i].fAmortizeStatusCode==""||rows[i].fValueTypeCode==""||rows[i].amount==""||rows[i].fAppropriationAmount==""||rows[i].fUnappropriationAmount==""){
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
	function onselectIntangiblefAssClass(rec){
		var fGainingMethods ;
		$.ajax({  
	    	url: base + '/lookup/lookupsJson?parentCode=WXZCQDFS' ,  
	        type: 'post',  
	        cache: false,
	        processData: false,
	        contentType: false,
	        async: false,
	        dataType:'json',
		    success:function(data){
		    	fGainingMethods = data;
		    },
		     error:function(){
		     }
	    });
		if(rec.code=='WXZCLB-06'){
			for (var i = fGainingMethods.length-1; i >= 0; i--) {
				if(fGainingMethods[i].code=='WXZCQDFS-09'||fGainingMethods[i].code=='WXZCQDFS-10'||
						fGainingMethods[i].code=='WXZCQDFS-11'||fGainingMethods[i].code=='WXZCQDFS-12'||
						fGainingMethods[i].code=='WXZCQDFS-13'||fGainingMethods[i].code=='WXZCQDFS-14'){
					fGainingMethods.splice(i,1);
				}
			}
			$('#storage_intangible_fGainingMethod').combobox('loadData',fGainingMethods);
		}else {
			for (var i = fGainingMethods.length-1; i >= 0; i--) {
				if(fGainingMethods[i].code=='WXZCQDFS-07'||
						fGainingMethods[i].code=='WXZCQDFS-06'||fGainingMethods[i].code=='WXZCQDFS-05'||
						fGainingMethods[i].code=='WXZCQDFS-04'||fGainingMethods[i].code=='WXZCQDFS-03'||
						fGainingMethods[i].code=='WXZCQDFS-02'||fGainingMethods[i].code=='WXZCQDFS-01'){
					fGainingMethods.splice(i,1);
				}
			}
			$('#storage_intangible_fGainingMethod').combobox('loadData',fGainingMethods);
		}
	}
	</script>
</body>