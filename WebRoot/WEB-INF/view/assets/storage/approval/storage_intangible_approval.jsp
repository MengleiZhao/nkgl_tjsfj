<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="StroageFixedAddEditForm" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
<div class="window-div" style="">
		<div class="window-left-div" data-options="region:'west',split:true" style="width:695px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
								<input type="hidden" name="fId_S" id="fId_S" value="${bean.fId_S}"/>
			    				<input type="hidden" name=fuserName value="${bean.fNextUserName}" /><!--当前审批人姓名  -->
								<input type="hidden" name="fuserId" value="${bean.fNextUserCode}" /><!--当前审批人id  -->
								<input type="hidden" name="nCode" value="${bean.fNextCode}" /><!--当前审批节点  -->
								
			<!--当前申报人id -->	<input type="hidden" name="fOperatorID" value="${bean.fOperatorID}" />
				<!-- 审批结果 --> <input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
	    		<!-- 审批意见 --> <input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
				<!-- 审批附件 --> <input type="hidden" name="spjlFile" id="spjlFile" value=""/>
				<!-- 流程id  --> <input type="hidden" id="flowId"  value="${fpId}"/>
			<!-- 下一级审批节点  --><input type="hidden" id="nextKey"  value="${bean.fNextCode}"/>
			    
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
									<input class="easyui-combobox" id="storage_intangible_fAssClass" name="fAssClass" readonly="readonly" data-options="url:'${base}/lookup/lookupsJson?parentCode=WXZCLB&selected=${bean.fAssClass}',method:'post',valueField:'code',textField:'text',editable:false,onSelect:onselectIntangiblefAssClass" style="width: 200px;">
								</td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span>&nbsp;取得方式</td>
								<td class="td2">
									<input id="storage_intangible_fGainingMethod" name="fGainingMethod" readonly="readonly" data-options="url:'${base}/lookup/lookupsJson?parentCode=WXZCQDFS&selected=${bean.fGainingMethod}',method:'get',valueField:'code',textField:'text',editable:false" style="width: 200px;" value="${bean.fGainingMethod}" class="easyui-combobox"></input>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;验收单号</td>
								<td class="td2">
									<input class="easyui-textbox" id="S_facpCode" readonly="readonly" name="facpCode" style="width: 200px" value="${bean.facpCode}"/>
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
						<%@ include file="../storage_fixed_add_plan_detail.jsp" %>
					</div>
				</div>	
			
			
			</div>
			
			<div class="window-left-bottom-div">
				<c:if test="${openType=='add'||openType=='edit'}">
					<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
						<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
						<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;&nbsp;
				</c:if>
				<c:if test="${detailType=='detail'}">
					<a href="javascript:void(0)" onclick="closeWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</c:if>
				<c:if test="${detailType=='ledger'}">
					<a href="javascript:void(0)" onclick="closeFirstWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</c:if>
				&nbsp;&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
</div>
</form>
<script type="text/javascript">
  $('#F_fcType').combobox({  
      onChange:function(newValue,oldValue){  
  	var sel2=$('#F_fcType').combobox('getValue');
  	if(sel2!="1"){
  		$('#cg1').hide();
  		//$('#cg2').hide();
  		//$('#F_fPurchNo').next(".textbox").show();
}else{
  		$('#cg1').show();
  		//$('#cg2').show();
  		//$('#F_fPurchNo').next(".textbox").hide();
} 
      }
  }); 
//如果选择‘采购’主管方法会弹出页面，让你选择采购计划
  $('#S_fAcquisitionType').combobox({
  	onChange:function(newValue,oldValue){
  		if(newValue=='QDFS-02'){
  			var win = creatSearchDataWin('采购', 970, 590, 'icon-search','/Storage/cgd?type=ZCLX-02&fAcquisitionType='+newValue);
  			win.window('open');
  			//如果不是采购的方式不需要挂接采购单，这由操作人自己添加物品
  			$("#S_fAcquisitionType").combobox('setValue','QDFS-02');
   		$('#addFixedButton').hide();
  		}else if(newValue=='QDFS-03'){
  			//如果是赠予的情况下显示其他字段
   		$('#addFixedButton').hide();
  		}else {
   		$('#addFixedButton').show();
  		}
  	}
  });
//上传附件
function storagefixed_upFile(obj){
	//创建上传表单
	var files = obj.files;
	var formData = new FormData();
    for(var i=0; i< files.length; i++){
  	  formData.append("attFiles",files[i]);   
  	} 
    //ajax上传
	$.ajax({  
    	url: base + '/Storage/uploadAtt?serviceType=fixeddj' ,  
        type: 'post',  
        data: formData,  
        cache: false,
        processData: false,
        contentType: false,
        async: false,
        dataType:'json',
	    success:function(data){
	    	 if(data.success==true){
			        var datainfo = data.info;
	    		 	var infoArray = datainfo.split(',');	
	    		 	for(var i=0; i< infoArray.length; i++){
	    		 		var info = infoArray[i];
	    		 		var inf = info.split('@');
	    		 		var id = inf[0];//附件id
	    		 		var name = inf[1];//附件名称
				        $('#tdfgdzcdj').append(
			    			"<div style='margin-top: 10px;'>"+
			    				"<a href='"+base+"/attachment/download/"+id+"' style='color: #666666;font-weight: bold;'>"+name+"</a>"+
			    				"&nbsp;&nbsp;&nbsp;&nbsp;"+
			    				"<img style='vertical-align:middle' src='"+base+"/resource-modality/${themenurl}/sccg.png'>"+
			    				"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/${themenurl}/scsb.png'>"+
			    				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+id+"' class='fileUrl' href='#' style='color:red' onclick='deleteAttac(this)'>删除</a><div>"
			    		);
	    		 		
	    		 	}
	    		 	//放入附件id
	    			var s="";
	    			$(".fileUrl").each(function(){
	    				s=s+$(this).attr("id")+",";
	    			});
	    			$("#storageFiles").val(s);
		       	 	
	    	} else {
	    		alert(data.info);
	    		$('#tdfgdzcdj').append(
    				"<div style='margin-top: 10px;'>"+
    					"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
    					"&nbsp;&nbsp;&nbsp;&nbsp;"+
    					"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/${themenurl}/sccg.png'>"+
    					"<img style='vertical-align:middle' src='"+base+"/resource-modality/${themenurl}/scsb.png'>"+
    					"&nbsp;&nbsp;&nbsp;&nbsp;<a id='fail' class='fileUrl' href='#' style='color:red' onclick='deleteAttac(this)'>删除</a><div>"
    			);
	    	}
	     },
	     error:function(){
	    	 alert('上传失败！');
	     }
    });
}
function check(stauts){
	var fId=$('#fId_S').val();
	var r=$('#C_fremark').val();
	$('#StroageFixedAddEditForm').form('submit', {
			onSubmit: function(param){ 
				flag=$(this).form('enableValidation').form('validate');
				if(flag){
					$.messager.progress();
				}
				return flag;
			}, 
			url:'${base}/Storage/approvalStorage/'+stauts,
			data:{'fFlowStatus':stauts,'id':fId},
			success:function(data){
				if(flag){
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if(data.success){
					$.messager.alert('系统提示', data.info, 'info');
					closeWindow();
					$('#StroageFixedAddEditForm').form('clear');
					$("#storage_fixed_approval_dg").datagrid('reload'); 
					$("#indexdb").datagrid('reload'); 
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