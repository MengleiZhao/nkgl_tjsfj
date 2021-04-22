<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="uptInfo" action="${base}/Change/Save" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<!-- 合同主键 -->		<input type="hidden" id="F_fcId" name="fcId" value="${bean.fcId}"/>
	    		<!-- 审批状态 -->		<input type="hidden" id="F_fUptFlowStauts" name="fUptFlowStauts" value="${Upt.fUptFlowStauts}"/>
	    		<!-- ID -->		    <input type="hidden" id="F_fId_U" name="fId_U" value="${Upt.fId_U}"/>
	    		<!-- 原合同ID -->     <input type="hidden" id="F_fContId_U" name="fContId_U" value="${Upt.fContId_U}"/>
	    		<!-- 变更单单号 -->     <input type="hidden" id="F_fUptCode" name="fUptCode" value="${Upt.fUptCode}"/>
	    		<!-- 变更单状态 -->     <input type="hidden" id="F_fUptStatus" name="fUptStatus" value="${Upt.fUptStatus}"/>
	    		<!-- 申请人id -->		<input type="hidden" id="F_fOperatorID" name="fOperatorID" value="${Upt.fOperatorID}"/>
	    		<!-- 品目名称 -->		<input type="hidden" id="F_fpItemsName" name="fpItemsName" value="${bean.fpItemsName}"/>
	    		<!-- 申请人 -->		<input type="hidden" id="F_fOperator" name="fOperator" value="${Upt.fOperator}"/>
	    		<!-- 申请部门id -->	<input type="hidden" id="F_fDeptID" name="fDeptID" value="${Upt.fDeptID}"/>
	    		<!-- 申请日期 -->	    <input type="hidden" id="F_fReqTime" name="fReqTime" value="${Upt.fReqTime}"/>
	    		<!-- 下环节处理人姓名 -->	<input type="hidden" id="F_fUserName" name="fUserName" value="${Upt.fUserName}"/>
	    		<!-- 下环节处理人编码 --> <input type="hidden" id="F_fUserCode" name="fUserCode" value="${Upt.fUserCode}"/>
	    		<!-- 下下节点节点编码 --> <input type="hidden" id="F_fNCode" name="fNCode" value="${Upt.fNCode}"/>
	    		<!-- 变更付款计划状态 --> <input type="hidden" id="F_fPlanChangeStatus" name="fPlanChangeStatus" value="${Upt.fPlanChangeStatus}"/>
				<!-- 变更采购清单状态 --> <input type="hidden" id="F_fShoppingChangeStatus" name="fShoppingChangeStatus" value="${Upt.fShoppingChangeStatus}"/>
				<div>
					<div title="" style="margin-bottom:35px;" data-options="">
						<c:if test="${openType=='Adetail'||openType=='Aedit'||openType=='detail'||openType=='Gdetail'||openType=='Edetail'}">
							<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
								<div title="归档信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
									<table cellpadding="0" cellspacing="0" class="ourtable">
										<%-- <tr class="trbody">
											<td class="td1"><span class="style1">*</span>&nbsp;归档位置</td>
											<td class="td2" colspan="4">
												<input class="easyui-textbox"<c:if test="${openType=='Aadd'||openType=='Aedit'}"> required="required" </c:if> id="a_fToPosition" name="fToPosition" <c:if test="${! empty archiving.fToPosition}">readonly="readonly"</c:if> required="required" data-options="validType:'length[1,200]',prompt:'请填写具体归档位置'" style="width: 555px;" value="${archiving.fToPosition}"/>
											</td>
										</tr> --%>
										<tr class="trbody">
											<td class="td1"><span class="style1">*</span>&nbsp;签订日期</td>
											<td class="td2">
												<input class="easyui-datebox" class="dfinput" <c:if test="${openType=='Adetail'}">readonly="readonly"</c:if> id="F_fUptdate" required="required" name="fUptdate"  data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${Upt.fUptdate}"/>
											</td>
											<td class="td4">&nbsp;</td>
											<td class="td1"></td>
											<td class="td2">
											</td >
										</tr>
										<tr class="trbody">
										<td class="td1" style="width:55px;text-align: right"><span class="style1">*</span>
											已盖章合同文本
											<input type="file" multiple="multiple" id="f" onchange="upladFileHTGD(this,'htgdfj','zcgl01')" hidden="hidden">
											<input type="text" id="files" name="files" hidden="hidden">
										</td>
										<td colspan="3" id="tdf">
											<c:if test="${openType=='Aedit'}">
											&nbsp;&nbsp;
											<a onclick="$('#f').click()" style="font-weight: bold;  " href="#">
												<img style="vertical-align:bottom;margin-bottom: 5px;" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
											</a>
											<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
												<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
												 </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
											</div>
											</c:if>
											<c:forEach items="${attaListArc}" var="att">
												<c:if test="${att.serviceType=='htgdfj'}">
													<div style="margin-top: 5px;">
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
													<c:if test="${openType=='Aedit'}">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<img style="margin-top: 5px;" src="${base}/resource-modality/${themenurl}/sccg.png">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<a id="${att.id}" class="fileUrl_htgdfj" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
													</c:if>
													</div>
												</c:if>
											</c:forEach>
										</td>
									</tr>
									</table>
								</div>
							</div>
							<div title="变更表" style="margin-bottom:35px;overflow:auto" data-options="collapsed:false,collapsible:false" >
								<%@ include file="../change/change_edit_info.jsp" %>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="win-left-bottom-div">
				<c:if test="${openType=='Aadd'||openType=='Aedit'}">
					<a href="javascript:void(0)" onclick="ArchivingEditForm();">
						<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;
				</c:if>
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=合同管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<%-- <div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div> --%>
	</div>
</form>
<script type="text/javascript">
$(function(){
	if ($('#uptOpenType').val() == 'Cdetail') {
		var c3 = $("#F_fPlanChangeStatus").val();
		var c4 = $("#F_fShoppingChangeStatus").val();
		if(c3 == '0'){
			$('#change-upt-datagr-div').hide();
		}else{
			$('#change-upt-datagr-div').show();
		}
		if(c4 == '0'){
			$('#change-upt-cgconf-div').hide();
		}else{
			$('#change-upt-cgconf-div').show();
		}
		/* $('#change-upt-datagr-div').show(); */
		$.parser.parse($('#change-upt-datagr-div').parent());
		/* $('#change-upt-cgconf-div').show(); */
		$.parser.parse($('#change-upt-cgconf-div').parent());
	}
	$('#Upt_fUptReason_edit').attr("readonly", "readonly");
	$('#uptUploadBtn').hide();
	$('.deleteFlag').hide();
	$(':radio').attr('disabled', true);

	function tabChange(){
		$('#filing-edit-plan-dg-detail').datagrid('reload');
		$('#contract_cgplan_dg_detail1').datagrid('reload');
	}
});

flashtab('contract-archiving-edit');	

$('#F_fcType').combobox({  
    onChange:function(newValue,oldValue){  
	var sel2=$('#F_fcType').combobox('getValue');
	if(sel2=="HTFL-01"){
		$('#cg1').show();
		$('#cg2').show();
		//$('#cg2').hide();
		//$('#F_fPurchNo').next(".textbox").show();
	}else{
		$('#cg1').hide();
		$('#cg2').hide();
		//$('#cg2').show();
		//$('#F_fPurchNo').next(".textbox").hide();
	} 
    }
}); 
function ArchivingEditForm(){
	
	var a = $(".file_U");
	var srcs;
    for(var i=0;i<a.length;i++){
		if(i==0){
			srcs=a[i].text;
		}else{
			srcs =srcs+','+a[i].text;
		}
		$('#upt_fi1').val(srcs);
    }   
 
   	 var s="";
		$(".fileUrl_htgdfj").each(function(){
			s=s+$(this).attr("id")+",";
		});
		console.log(s);
	    if(s==''){
			alert("请上传归档合同！");
			return false;
		} 
	//var plan = getPlan();
	//console.log($('#upt_fFileSrc1').val());
   	$('#uptInfo').form('submit', {
		onSubmit:function(){ 
			//param.planJson=plan;
			/* flag=$(this).form('enableValidation').form('validate');
			if(flag){
				$.messager.progress();
			}
			return flag; */
			flag=$(this).form('enableValidation').form('validate');
					if(flag){
						$.messager.progress();
					}
					return flag;
		}, 
		url:'${base}/Archiving/Save?type=1',
		type:'post',
		/* data:{'fFileSrc':$('#upt_fFileSrc').val()}, */
		success:function(data){
			if(flag){
						$.messager.progress('close');
					}
					data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示', data.info, 'info');
						$('#archiving_dg').form('clear');
						$('#ArchivingUpdateOrendingTab').datagrid('reload'); 
						closeWindow();
					}else{
						$.messager.alert('系统提示', data.info, 'error');
					}
		} 
	});		
}
function fPurchNo_DC(){
	//var node=$('#archiving_dg').datagrid('getSelected');
	var win=creatFirstWin('选择-采购订单号',750,550,'icon-add','/PurchaseApply/PurchNoList');
	win.window('open');
}
function quota_DC(){
	//var node=$('#archiving_dg').datagrid('getSelected');
	var win=creatFirstWin('选择-预算指标编号',750,550,'icon-add','/BudgetIndexMgr/contract_list');
	win.window('open');
}
var c =0;
function upt_upFile() {
	/* console.log(document.getElementById("upt_fFileSrc1"));
	var src = getFilePath();
	alert(getFilePath()); */
	c ++;
	var src = $('#upt_fFileSrc1').val();
	/* var srcs =src+','+$('#upt_fi1').val();
	$('#upt_fi1').val(srcs); */
	$('#upt__f1').append("<div id='c"+c+"'><a href='#' class='file_U' style='color: #3f7f7f;font-weight: bold;'>"+src+"</a><a style='color: red;' href='#' onclick='deleteF1(c"+c+")'>删除</a></div>");
} 
function deleteF1(val){
	var child=document.getElementById(val.id);
	child.parentNode.removeChild(child);
}






function upladFileHTGD(obj,serviceType,pathNum,mark) {
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
	  callback(mark);
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
	//监听进度线程，生成进度条
function progressFunction(evt) {
  if (evt.lengthComputable) {
	  //evt.loaded：文件上传的大小   evt.total：文件总的大小                    
      var percentComplete = Math.round((evt.loaded) * 100 / evt.total);    
      //加载进度条，同时显示信息          
      $("#progid").show();
      $("#percent").html(percentComplete + '%');
      //percentComplete*2  这个2是根据总像素大小来改变的，如果是300px就 *3
      $("#progressNumber").css("width",""+percentComplete*3+"px");   
  }
} 
//回调函数
function callback(mark) {
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
			    				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+id+"' class='fileUrl_htgdfj' href='#' style='color:red' onclick='deleteAttac(this,\""+mark+"\")'>删除</a><div>"
			    		);
	    		 		
	    		 	}
	    		 	//放入附件id
	    			var s="";
	    			$(".fileUrl_htgdfj").each(function(){
	    				s=s+$(this).attr("id")+",";
	    			});
	    			$("#files").val(s);
	    			$("#progid").hide();
	    	} else {
	    		alert(jsonResponse.info);
	    		$('#tdf').append(
    				"<div style='margin-top: 5px;'>"+
    					"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
    					"&nbsp;&nbsp;&nbsp;&nbsp;"+
    					"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
    					"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
    					"&nbsp;&nbsp;&nbsp;&nbsp;<a id='fail' class='fileUrl_htgdfj' href='#' style='color:red' onclick='deleteAttac(this)'>删除</a><div>"
    			);
	    		$("#progid").hide();
	    	}
        } else {
            alert("上传失败");
        }
        $('.win-left-bottom-div').show();
    }
}
</script>
</body>