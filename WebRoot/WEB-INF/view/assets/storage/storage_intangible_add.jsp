<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<script type="text/javascript" src="/resource/custom/custom.js"></script>
<body>
<form id="StroageIntorageAddEditForm" action="${base}/Storage/saveIntangible" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
<div class="window-div">
		<div class="window-left-div" data-options="region:'west',split:true" style="width:695px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<input type="hidden" name="fId_S" id="S_fId_S" value="${bean.fId_S}"/>
				<input type="hidden" name="fAssType" id="S_fAssType" value="${bean.fAssType}"/>
				<input type="hidden" name="facpId" id="S_facpId" value="${bean.facpId}"/><!-- 验收单ID -->
		    	<input type="hidden" name="fReqTime" id="S_fReqTime" value="${bean.fReqTime}"/>
		    	<input type="hidden" name="fOperatorID" id="S_fOperatorID" value="${bean.fOperatorID}"/>
		    	<input type="hidden" name="fDeptID" id="S_fDeptID" value="${bean.fDeptID}"/>
		    	<input type="hidden" name="fAssStauts" id="S_fAssStauts" value="${bean.fAssStauts}"/>
		    	<input type="hidden" name="fFlowStatus" id="S_fFlowStatus" value="${bean.fFlowStatus}"/>
	    		<input type="hidden" name="fNextUserName" id="R_fNextUserName" value="${bean.fNextUserName}"/>
		    	<input type="hidden" name="fNextUserCode" id="R_fNextUserCode" value="${bean.fNextUserCode}"/>
		    	<input type="hidden" name="fNextCode" id="R_fNextCode" value="${bean.fNextCode}"/>
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
									<input id="storage_intangible_fGainingMethod" name="fGainingMethod"  data-options="url:'${base}/lookup/lookupsJson?parentCode=WXZCQDFS&selected=${bean.fGainingMethod}',method:'post',valueField:'code',textField:'text',editable:false,onSelect:onselectIntangibleMethod" style="width: 200px;" class="easyui-combobox"></input>
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
						<%@ include file="storage_intangible_add_plan.jsp" %>
					</div>
				</div>			
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="StroageIntorageAddEditForm();">
					<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
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
	var oldfGainingMethod ;
	(function(){
		$.ajax({  
	    	url: base + '/lookup/lookupsJson?parentCode=WXZCQDFS' ,  
	        type: 'post',  
	        cache: false,
	        processData: false,
	        contentType: false,
	        async: false,
	        dataType:'json',
		    success:function(data){
		    	oldfGainingMethod = data;
		    	
		    },
		     error:function(){
		     }
	    });
		
	})();
	
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
	    		$('#addIntorageButton').hide();
    		}else if(newValue=='QDFS-03'){
    			//如果是赠予的情况下显示其他字段
	    		$('#addIntorageButton').hide();
    		}else {
	    		$('#addIntorageButton').show();
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
    function StroageIntorageAddEditForm(){
	    	var fGainingMethod = $("#storage_intangible_fGainingMethod").combobox('getValue');
	    	if(fGainingMethod==''){
	    		alert('请选择取得方式！');
	    		return false;
	    	}
	    	if(fGainingMethod=='WXZCQDFS-02'){
	    		var facpCode = $("#S_facpCode").textbox('getValue');
	    		if(facpCode==''){
		    		alert('请选择验收单号！');
		    		return false;
	    		}
	    	}
	    	acceptPlanIntangible();
	    	var rows = $('#fixed_add_plan_intangible').datagrid('getRows');
	    	if(rows!=''){
	    		for (var i = 0; i < rows.length; i++) {
	        		if(rows[i].fFixedTypeId==''||rows[i].fFixedTypeId==null||rows[i].fAssName==''||rows[i].fAssName==null||rows[i].fAdminOfficial==''||rows[i].fActionDate==''){
	        			alert('请完善资产详情表，第'+(i+1)+'行数据！');
	        			return false;
	        		}
	        	}
	    	}else{
	    		alert('请填写资产详情数据！');
	    		return false;
	    	}
	    	
			$('#StroageIntorageAddEditForm').form('submit', {
   				onSubmit: function(param){ 
   					param.planJson=getPlanIntangible();
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
   						$('#StroageIntorageAddEditForm').form('clear');
   						$("#storage_intorage_dg").datagrid('reload'); 
   					}else{
   						$.messager.alert('系统提示', data.info, 'error');
   					}
   				} 
   			});		
		}
		
		function FWCode(){
			var win=creatFirstWin(' ',800,550,'icon-search','/Storage/fwCode');
			win.window('open');
		}
		function fProCode_DC(){
			var win=creatFirstWin(' ',800,550,'icon-search','/Formulation/fProCode');
			win.window('open');
		}
		function quota_DC(){
			//var node=$('#storage_fixed_dg').datagrid('getSelected');
			var win=creatFirstWin('选择-预算指标编号',750,550,'icon-add','/BudgetIndexMgr/contract_list');
			win.window('open');
		}
	var c1 =0;
	function upFile1() {
		c1 ++;
		var src = $('#a_fFileSrc1').val();
		$('#fi1').val(src);
		$('#S_a_f1').append("<div id='c1"+c1+"'><a href='#' style='color: #3f7f7f;font-weight: bold;'>"+src+"</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style='color: red;' href='#' onclick='deleteF1(c1"+c1+")'>删除</a></div>");
	} 
	function deleteF1(val){
		var child=document.getElementById(val.id);
		child.parentNode.removeChild(child);
	}
		var names = new Array();
		function streetChange(streetCode){
			$('#userStreetJwh').combobox('reload',"${base}/street/jwh?streetCode="+streetCode);
		}
		function departSelect(selectType){
			var win=creatSearchDataWin('选择-部门',590,400,'icon-add',"/depart/refUserDepart/"+selectType);
		    win.window('open');
		} 
		function departReturnSelect(ret){
			if (ret == "clear") {
		       $("#user_depart").html("");
			}

		    if (ret!="&&&&" && ret != undefined && ret != null && ret.indexOf("&&") != -1 && ret != "cancel") {
		    	var retArray = ret.split("&&");
		        var str= new Array(); 
		       	str=retArray[2].split(","); 
		       	for(var i=0;i<str.length-1 ;i++){
		       		if(null!=str[i]){
		       			var str1= new Array();
		       			str1=str[i].split(":");
		       			var div1 = $("#user_depart");
		       			if(!isUserTreeExist(str1[0]) && !isUserTreeExist(str1[1])){
							names.push(str1[0]);	
							div1.html("<div  style='float:left;margin-left:8px;margin-top:8px;' > "+
										"<input type='hidden' name='depart.id' id='departIds' value='"+str1[0]+"'/>"+
										"<span title='"+str1[1]+"'>"+((str1[1].length>8)?str1[1].substring(0,8)+"...":str1[1])+"</span>"+
										"<img src='${base}/resource/images/cancelDepart.jpg' style='cursor:pointer;margin-left:5px;height:15px;width:15px;' onclick = 'cancelUserDepart(this)' title='删除' id='"+str1[0]+"'/>"+
										"</div>");
						}
		       		}
		       	}
		    }
		}
		function isUserTreeExist(name){
			for(var i=0; i<names.length; i++){
				if(names[i] == name){
					return true;
				}
			}
			return false;
		}
		function deleteCF(){
			var row = $('#CS_dg').datagrid('getSelected');
			var selections = $('#storage_fixed_dg').datagrid('getSelections');
				if(confirm("确认删除吗？")){
					$.ajax({ 
						type: 'POST', 
						url: '${base}/Formulation/delete/'+row.fcId,
						dataType: 'json',  
						success: function(data){ 
							if(data.success){
								$.messager.alert('系统提示',data.info,'info');
								$("#storage_fixed_dg").datagrid('reload');
							}else{
								$.messager.alert('系统提示',data.info,'error');
							}
						} 
					}); 
				}
		}
		function cancelUserDepart(obj){
			var id=obj.id;
			if(names.length>0)
			{
				for(i=0; i<names.length; i++){
					if(names[i] == id){
						names.splice(i,1);
					}
				}
			}
			$(obj).parent().remove();
		}
		function checkMobileNo(){
			var mobile=$("#user_edit_mobileNo").textbox("getValue");
			var id=$("#user_edit_id").val();
			var tag="0";
			if(mobile!=''){
				 $.ajax({
		  			   type : "post",
		  			   url : "${base}/user/mobileCheck",
		  			   data:{"mobileNo":mobile,"id":id},
		  			   dataType: 'json',  
					   async: false,
		  			   success : function(data){
		  				   if(data.success){ 	
		  			   	     $.messager.alert('系统提示', "手机号在系统中已存在，请更换手机号！", 'info');
		  			   	     tag="1";
		  			   	   }else{
		  			   		 tag="0"; 
		  			   	   }  
		  			   }
		  		   })
			}
			 if(tag=="0"){
		    	return true; 
		     }else if(tag=="1"){
		    	return false; 
		     }
		}
		
		function onselectIntangibleMethod(record){
			if("WXZCQDFS-02"==record.code||"WXZCQDFS-09"==record.code){
				$('#S_facpCode').textbox('enable'); //可用
				$('#S_facpCode').textbox('readonly','false');
				$('#S_facpCode').textbox({prompt:'单击选择'}); 
				$("#queryAcpCodeId").attr("onclick","queryIntangibleAcpCode();");
				$("#removeitPlanIntangibleId").hide();
				$("#appendPlanIntangibleId").hide();
			}else{
				$('#S_facpCode').textbox('setValue','');
				$('#S_facpCode').textbox('disable'); //不可用
				$('#S_facpCode').textbox('readonly','true'); 
				$('#S_facpCode').textbox({prompt:'无需选择'}); 
				$("#queryAcpCodeId").removeAttr("onclick");
				$("#removeitPlanIntangibleId").show();
				$("#appendPlanIntangibleId").show();
			}
			acceptPlanIntangible();
			var rows = $('#fixed_add_plan_intangible').datagrid('getRows');
			for (var i = rows.length-1; i >= 0; i--) {
				$('#fixed_add_plan_intangible').datagrid('deleteRow',i);
			}
			editIndexPlanIntangible = undefined;
		}
		function queryIntangibleAcpCode(){
			acceptPlanIntangible();
			var win=creatFirstWin('选择-验收单',900,580,'icon-search',"/Storage/selectReceive");
		    win.window('open');
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