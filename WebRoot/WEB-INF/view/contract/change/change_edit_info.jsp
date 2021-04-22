<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="easyui-accordion" data-options="multiple:true"
	style="width:705px;margin-left: 20px;">
	<div title="基本信息" data-options="collapsed:false,collapsible:false"
		style="overflow:auto;">
		<table cellpadding="0" cellspacing="0" class="ourtable">
			<tr class="trbody">
				<td class="td1">&nbsp;变更合同编号</td>
				<td colspan="4"><input class="easyui-textbox"
					id="upt_fContCode_edit" name="fContCode" readonly="readonly"
					data-options="validType:'length[1,32]'"  <c:if test="${uptOpenType == 'Cdetail'}">style="width: 555px"</c:if> <c:if test="${uptOpenType != 'Cdetail'}">style="width: 505px"</c:if>
					value="${Upt.fContCode}" /> <c:if test="${uptOpenType == 'Cadd'}"> 
						<input type="button" value="刷新" onclick="getFContCode()" />
					</c:if></td>
			</tr>
			<tr class="trbody">
				<td class="td1"><span class="style1">*</span>&nbsp;变更合同名称</td>
				<td colspan="4"><input class="easyui-textbox"
					id="upt_fContName_edit"
					<c:if test="${uptOpenType == 'Cdetail'}">readonly="readonly"</c:if>
					name="fContName" required="required"
					data-options="validType:'length[1,100]'" style="width: 555px"
					value="${Upt.fContName}" /></td>
			</tr>
			<tr class="trbody">
				<td class="td1">&nbsp;原合同编号</td>
				<td colspan="4"><input class="easyui-textbox"
					id="upt_fContCodeOld_edit" name="fContCodeOld" readonly="readonly"
					data-options="validType:'length[1,32]'" style="width: 555px"
					value="${bean.fcCode}" /></td>
			</tr>
			<tr class="trbody">
				<td class="td1">&nbsp;原合同名称</td>
				<td colspan="4"><input class="easyui-textbox"
					id="upt_fContNameold_edit" name="fContNameold" readonly="readonly"
					data-options="validType:'length[1,100]'" style="width: 555px"
					value="${bean.fcTitle}" /></td>
			</tr>
			
			<c:if test="${bean.fcType=='HTFL-01'}"> 
			<tr class="trbody " >
				<td class="td1"><span class="style1">*</span>&nbsp;签约方名称</td>
				<td class="td2">
					<input class="easyui-combobox" id="upt_fContractor" value="${Upt.fContractor}" readonly="readonly" name="fContractor" data-options="validateOnCreate:false,editable:false,panelHeight:'auto',url:'${base}/Formulation/fContractorlookupsJson?parentCode=${bean.fPurchNo}&selected=${bean.fContractor}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'" style="width: 200px"/>
				</td>
				<td class="td4">&nbsp;</td>
				<td class="td1"><span class="style1">*</span>&nbsp;合同保证金</td>
				<td class="td2">
					<input class="easyui-numberbox" id="upt_fMarginAmount" <c:if test="${uptOpenType == 'Cdetail'||bean.fIncomeStauts=='1'}">readonly="readonly"</c:if> name="fMarginAmount"  required="required" data-options="validateOnCreate:false,icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${Upt.fMarginAmount}"/>
				</td>
			</tr>
			
			<tr class="trbody" >
				<td class="td1"><span class="style1">*</span>&nbsp;合同金额</td>
				<td class="td2">
					<input class="easyui-numberbox" id="upt_fcAmount" name="fcAmount" required="required" data-options="validateOnCreate:false,icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${Upt.fcAmount}" readonly="readonly"/>
				</td>
				<td class="td4">&nbsp;</td>
				<td class="td1"><span class="style1">*</span>&nbsp;大写金额</td>
				<td class="td2">
					<input class="easyui-textbox" id="upt_fcAmountMax" name="fcAmountMax" readonly="readonly" required="required" data-options="validateOnCreate:false,validType:'length[1,25]'" style="width: 200px" value="${Upt.fcAmountMax}"/>
				</td>
			</tr>
			</c:if>
			<c:if test="${bean.fcType!='HTFL-01'}"> 
			<tr class="trbody "  >
				<td class="td1"><span class="style1">*</span>&nbsp;签约方名称</td>
				<td class="td2">
					<input class="easyui-textbox" id="upt_fContractor"  required="required"  readonly="readonly" name="fContractor" data-options="validateOnCreate:false"  value="${Upt.fContractor}"
					 style="width: 200px"/>
				</td>
				<td class="td4">&nbsp;</td>
				<td class="td1"><span class="style1">*</span>&nbsp;合同保证金</td>
				<td class="td2">
					<input class="easyui-numberbox" id="upt_fMarginAmount"<c:if test="${bean.fIncomeStauts=='1'}"> readonly="readonly"</c:if> required="required" name="fMarginAmount" <c:if test="${uptOpenType == 'Cdetail'}">readonly="readonly"</c:if> data-options="validateOnCreate:false,icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${Upt.fMarginAmount}"/>
				</td>
			</tr>
			</c:if>
			<tr class="trbody">
				<td class="td1"><span class="style1">*</span>&nbsp;合同开始日期</td>
				<td class="td2">
					<input class="easyui-datebox" id="upt_fContStartTime" name="fContStartTime" required="required" <c:if test="${uptOpenType == 'Cdetail'}">readonly="readonly"</c:if> data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${Upt.fContStartTime}"/>
				</td>
				<td class="td4">&nbsp;</td>
				<td class="td1"><span class="style1">*</span>&nbsp;合同结束日期</td>
				<td class="td2">
					<input class="easyui-datebox" id="upt_fContEndTime" name="fContEndTime"  required="required" <c:if test="${uptOpenType == 'Cdetail'}">readonly="readonly"</c:if> data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${Upt.fContEndTime}"/>
				</td>
			</tr>
			<tr class="trbody">
				<td class="td1"><span class="style1">*</span>&nbsp;变更申请人</td>
				<td class="td2"><input class="easyui-textbox"
					id="upt_fOperator_edit" name="fOperator" readonly="readonly"
					data-options="validType:'length[1,25]'" style="width: 200px"
					value="${Upt.fOperator}" /> <input type="hidden"
					id="upt_fOperatorID_edit" name="fOperatorID"
					value="${Upt.fOperatorID}" /></td>
				<td class="td4">&nbsp;</td>
				<td class="td1"><span class="style1">*</span>&nbsp;变更申请部门</td>
				<td class="td2"><input class="easyui-textbox"
					id="upt_fDeptName_edit" name="fDeptName" readonly="readonly"
					data-options="validType:'length[1,20]',editable:false"
					style="width: 200px" value="${Upt.fDeptName}" /> <input
					type="hidden" id="upt_fDeptID_edit" name="fDeptID"
					value="${Upt.fDeptID}" /></td>
			</tr>
			<tr class="trbody">
				<td class="td1"><span class="style1">*</span>&nbsp;变更说明</td>
				<td colspan="4">
					<input class="easyui-textbox" required="required" type="text" id="Upt_fUptReason_edit" name="fUptReason" <c:if test="${uptOpenType == 'Cdetail'}">readonly="readonly"</c:if> data-options="validType:'length[1,200]',multiline:true"   style="width:555px;height:100px" value="${Upt.fUptReason}"/>
					<%-- <textarea name="fUptReason" id="Upt_fUptReason_edit"
						class="textbox-text" oninput="textareaNum(this,'textareaNum1')"
						autocomplete="off" style="width:555px;height:100px">${Upt.fUptReason}</textarea> --%>
				</td>
			</tr>
			<tr class="trbody">
				<td class="td1"><span class="style1">*</span>&nbsp;补充协议
				 <input type="file" multiple="multiple" id="fhtbg" onchange="upladFileParams(this,'htbg','htgl01','progressNumberhtbg','percenthtbg','tdhtbg','htbgfiles','progidhtbg')" hidden="hidden">
					<input type="text" id="htbgfiles" name="htbgfiles" hidden="hidden"></td>
				<td colspan="4" id="tdhtbg"><a onclick="$('#fhtbg').click()"
					style="font-weight: bold;" href="#" id="uptUploadBtn"> <img
						style="vertical-align:bottom"
						src="${base}/resource-modality/${themenurl}/button/shangchuan1.png"
						onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
					<div id="progidhtbg"
						style="background:#EFF5F7;width:300px;height:10px;margin-top:0px;display: none">
						<div id="progressNumberhtbg"
							style="background:#3AF960;width:0px;height:10px"></div>
						文件上传中...&nbsp;&nbsp;<font id="percenthtbg">0%</font>
					</div>
					</br> <c:forEach items="${changeAttaList}" var="att">
						<c:if test="${att.serviceType=='htbg' }">
							<div class="htbg" style="margin-top: 0px;margin-bottom: 10px;">
								<a href='${base}/attachment/download/${att.id}'
									style="color: #666666;font-weight: bold;">${att.originalName}</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<c:if test="${uptOpenType != 'Cdetail'}">
									<img style="vertical-align:bottom"
										src="${base}/resource-modality/${themenurl}/sccg.png">
								&nbsp;&nbsp;&nbsp;&nbsp;
								</c:if>
								<a id="${att.id}" class="fileUrl deleteFlag" href="#"
									style="color:red" onclick="deleteAttac(this)">删除</a>
							</div>
						</c:if>
					</c:forEach></td>
			</tr>
			<tr style="display: none">
				<td class="td1"><span class="style1">*</span>&nbsp;是否变更付款计划</td>
				<td><input type="radio" name="collectionPlan" value="1"  disabled>是
					<input type="radio" name="collectionPlan" value="0" checked disabled>否</td>
				<td class="td4">&nbsp;</td>
				<td class="td1"><span class="style1">*</span>&nbsp;是否变更采购清单</td>
				<td><input type="radio" name="shoppingList" value="1"  disabled>是
					<input type="radio" name="shoppingList" value="0" checked disabled>否</td>
			</tr>
		</table>
		<input type="text" id="uptOpenType" hidden="hidden"
			value="${uptOpenType}">
		<c:if test="${bean.fcType=='HTFL-01'}"> 	
		<div class="easyui-accordion" >
			<div  title="采购清单变更情况" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
			<%@ include file="../base/select_cgconf_plan_mingxi_edit.jsp"%>
			</div>
		</div>	
		<div class="easyui-accordion" >
			<div  title="付款计划变更情况" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
			<%@ include file="../base/contract-upt-edit-plan.jsp"%>
			</div>
		</div>	
		</c:if>
		<c:if test="${Upt.fcAmount>=200000.00 }">
		<div class="easyui-accordion" >
			<div title="党委会会议号" id="cgdwhdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
				<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 10px;">
					<tr>
						<td class="td1"><span class="style1">*</span>&nbsp;编号:</td>
						<td colspan="4">
							<input class="easyui-textbox" id="f_cgdwh" name="fDZHCode" style="width: 200px;" data-options="" value="${Upt.fDZHCode }" readonly="readonly"/>
						</td>
					</tr>
					<c:if test="${checkType != 'detail' }">
						<tr class="trbody">
							<td class="td1">
								&nbsp;<span class="style1">*</span>&nbsp;会议纪要上传:
								<input type="file" multiple="multiple" id="f1" onchange="upladFileHT(this,'htbgjy','htgl01')" hidden="hidden">
								<input type="text" id="htbgjyfiles" name="htbgjyfiles"  hidden="hidden">
							</td>
							<td colspan="4" id="tdf">
								<a onclick="$('#f1').click()" style="font-weight: bold;" href="#" id="button">
									<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
								</a>
								<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
							        <div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
							        </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
					    	    </div>
								<c:forEach items="${changeAttaList}" var="att">
									<c:if test="${att.serviceType=='htbgjy' }">
										<div style="margin-top: 10px;">
											<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
											&nbsp;&nbsp;&nbsp;&nbsp;
											<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<a id="${att.id}" class="HTfileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
										</div>
									</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if test="${checkType == 'detail' }">
						<tr class="trbody">
							<td class="td1">
								&nbsp;<span class="style1">*</span>&nbsp;会议纪要上传：
								<input type="file" multiple="multiple" id="f1" onchange="upladFile(this,'htbgjy','htgl01')" hidden="hidden">
								<input type="text" id="htbgjyfiles" name="htbgjyfiles"  hidden="hidden">
							</td>
							<td colspan="4" id="tdf">
								<c:forEach items="${changeAttaList}" var="att">
									<c:if test="${att.serviceType=='htbgjy' }">
										<div style="margin-top: 10px;">
											<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
											&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
		</c:if>
	</div>
	<c:if test="${uptOpenType != 'Cadd'}">
		<div title="审批记录" style="overflow:auto;margin-top: 10px;"
			data-options="collapsed:false,collapsible:false">
			<%@ include file="../../check_history.jsp"%>
		</div>
	</c:if>
</div>
<script type="text/javascript">
	$('#change-upt-datagr-div').show();
	$('#change-upt-cgconf-div').show();
	
	/* var rpSize = '${rpSize}';
	var crSize = '${crSize}';
	if (rpSize > 0) {
		$("input[name=collectionPlan]:eq(0)").prop("checked", 'checked');
		$('#change-upt-datagr-div').show();
	}
	if (rpSize == 0) {
		$("input[name=collectionPlan]:eq(1)").prop("checked", 'checked');
	}
	if (crSize > 0) {
		$("input[name=shoppingList]:eq(0)").prop("checked", 'checked');
		$('#change-upt-cgconf-div').show();
	}
	if (crSize == 0) {
		$("input[name=shoppingList]:eq(1)").prop("checked", 'checked');
	} */
	/* $('input[type=radio][name=collectionPlan]').change(function() {
		var myvalue = $(this).val();
		if (myvalue == 1) {
			$('#change-upt-datagr-div').show();
			$.parser.parse('#change-upt-datagr-div');
		} else {
			$('#change-upt-datagr-div').hide();
		}
	}); */
	/* $('input[type=radio][name=shoppingList]').change(function() {
		;
		var myvalue = $(this).val();
		if (myvalue == 1) {
			$('#change-upt-cgconf-div').show();
			$.parser.parse('#change-upt-cgconf-div');
		} else {
			$('#change-upt-cgconf-div').hide();

		}
	}); */
	//合同大写金额
	$('#upt_fcAmount').numberbox({
	    onChange:function(newValue,oldValue){
	    	$('#upt_fcAmountMax').textbox('setValue', convertCurrency(newValue));
	    }
	});
	function onClickCellPlan(index, field) {
		if (editIndex != index) {
			if (endEditingPlan()) {
				$('#change-upt-datagrid').datagrid('selectRow', index)
						.datagrid('beginEdit', index);
				var ed = $('#change-upt-datagrid').datagrid('getEditor', {
					index : index,
					field : field
				});
				if (ed) {
					($(ed.target).data('textbox') ? $(ed.target).textbox(
							'textbox') : $(ed.target)).focus();
				}
				editIndex = index;
			} else {
				setTimeout(function() {
					$('#change-upt-datagrid').datagrid('selectRow', editIndex);
				}, 0);
			}
		}
	}

	function getFContCode() {
		$.ajax({
			type : "POST",
			url : base + '/Change/getFContCode',
			success : function(data) {
				data = eval("(" + data + ")");
				if (data.success) {
					$('#upt_fContCode_edit').textbox("setValue", data.info);
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			},
			error : function(e) {
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	}

	function appendPlan1() {
		if (endEditingPlan()) {
			$('#change-upt-datagrid').datagrid('appendRow', {});
			editIndex = $('#change-upt-datagrid').datagrid('getRows').length - 1;
			$('#change-upt-datagrid').datagrid('selectRow', editIndex)
					.datagrid('beginEdit', editIndex);
		}
	}
	function removeitPlan1() {
		if (editIndex == undefined) {
			return
		}
		$('#change-upt-datagrid').datagrid('cancelEdit', editIndex).datagrid(
				'deleteRow', editIndex);
		editIndex = undefined;
	}
	var editIndex = undefined;
	function endEditingPlan() {
		if (editIndex == undefined) {
			return true
		}
		if ($('#change-upt-datagrid').datagrid('validateRow', editIndex)) {
			$('#change-upt-datagrid').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	
	
	
	function upladFileHT(obj,serviceType,pathNum,mark) {
		  var files = obj.files;
		  var size=files[0].size;
		  if(size==0){
			  alert("不允许上传空文件");
			  return false;
		  }
		  
		  var formData = new FormData();
		  //判断有没有选中附件信息
		  if(files==null || files=="null"|| files.length==0){
			  alert("请选择附件上传");
			  return false;
		  }
		  //把所有的附件都存入变量，准备传给后台
		  for(var i=0; i< files.length; i++){
		  	 formData.append("attFiles",files[i]);
		  } 
		  //初始化进度条为0
		  $("#percent").html(0 + '%');
		  $("#progressNumber").css("width",""+0+"px");
		  
		  $('.win-left-bottom-div').hide();
		  
		  // 接收上传文件的后台地址 
		  var url = base + "/attachment/uploadAtt?serviceType="+serviceType+"&pathNum="+pathNum;          
		  //1.创建XMLHttpRequest组建    
		  xmlHttpRequest = createXmlHttpRequest();  
		  //post请求
		  xmlHttpRequest.open("post", url, true);
		  //请求成功回调
		  xmlHttpRequest.onload = function (data) {
			  callbackHT(mark);
			  var resObj = JSON.parse(data.currentTarget.response);
			  if(resObj.success && 'zdsy'==serviceType){
				  //如果是制度索引文件上传
				  var fileName = resObj.info.split("@")[1];
				  var fileId = resObj.info.split("@")[0];
				  fileName = fileName.replace('.pdf','');
				  $('#cheter_add_fileName').textbox('setValue',fileName);
				  $('#F_attachmentId').val(fileId);
				  $('#systemcenter_add_uploadbtn').hide();
			  }
		  };
		  //调用线程监听上传进度
		  xmlHttpRequest.upload.addEventListener("progress", progressFunction, false);
		  //把文件数据发送出去
		  xmlHttpRequest.send(formData);
		  //清空value （作用：删除文件后再上传同一个文件，onchange方法不调用）
		  event.target.value=null;
		}
	
	
	function callbackHT(mark) {
	    //5。接收响应数据
	    //判断对象的状态是交互完成
	    if (xmlHttpRequest.readyState == 4) {
	        //判断http的交互是否成功
	        if (xmlHttpRequest.status == 200) {
	            //获取服务器段输出的纯文本数据
	            var responseText = xmlHttpRequest.responseText;
	            //文本数据转换成json
	            var jsonResponse = JSON.parse(responseText);
		    	 if(jsonResponse.success==true){
		    		    //获取controller 返回的对象信息
				        var datainfo = jsonResponse.info;
		    		    //如果上传了多个文件，返回的string就有多个，使用逗号分割，现在截取
		    		 	var infoArray = datainfo.split(',');	
		    		 	for(var i=0; i< infoArray.length; i++){
		    		 		var info = infoArray[i];
		    		 		var inf = info.split('@');
		    		 		var id = inf[0];//附件id
		    		 		var name = inf[1];//附件名称
					        $('#tdf').append(
				    			"<div style='margin-top: 5px;'>"+
				    				"<a href='"+base+"/attachment/download/"+id+"' style='color: #666666;font-weight: bold;'>"+name+"</a>"+
				    				"&nbsp;&nbsp;&nbsp;&nbsp;"+
				    				"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
				    				"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
				    				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+id+"' class='HTfileUrl' href='#' style='color:red' onclick='deleteAttac(this,\""+mark+"\")'>删除</a><div>"
				    		);
		    		 		
		    		 	}
		    		 	//放入附件id
		    			var s="";
		    			$(".HTfileUrl").each(function(){
		    				s=s+$(this).attr("id")+",";
		    			});
		    			$("#files").val(s);
		    			$("#progid").hide();
		    	} else {
		    		alert(jsonResponse.info);
		    		$("#progid").hide();
		    		$('#tdf').append(
	    				"<div style='margin-top: 5px;'>"+
	    					"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
	    					"&nbsp;&nbsp;&nbsp;&nbsp;"+
	    					"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
	    					"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
	    					"&nbsp;&nbsp;&nbsp;&nbsp;<a id='fail' class='HTfileUrl' href='#' style='color:red' onclick='deleteAttac(this)'>删除</a><div>"
	    			);
		    	}
	        } else {
	            alert("上传失败");
	        }
	        $('.win-left-bottom-div').show();
	    }
	}
</script>