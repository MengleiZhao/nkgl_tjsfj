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
												<input class="easyui-datebox" readonly="readonly" class="dfinput" id="F_fUptdate" required="required" name="fUptdate"  data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${Upt.fUptdate}"/>
											</td>
											<td class="td4">&nbsp;</td>
											<td class="td1"></td>
											<td class="td2">
											</td >
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
		}, 
		url:'${base}/Archiving/Save?type=1',
		type:'post',
		/* data:{'fFileSrc':$('#upt_fFileSrc').val()}, */
		success:function(data){
			/* if(flag){
				$.messager.progress('close');
			} */
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
if ($('#uptOpenType').val() == 'Cdetail') {
	$('#change-upt-datagr-div').show();
	$.parser.parse($('#change-upt-datagr-div').parent());
	$('#change-upt-cgconf-div').show();
	$.parser.parse($('#change-upt-cgconf-div').parent());
}

$('#upt_fContName_edit').attr("readonly", "readonly");
$('#Upt_fUptReason_edit').attr("readonly", "readonly");
$('#uptUploadBtn').hide();
$('.deleteFlag').hide();
$('#planFlag').hide();

function tabChange(){
	$('#filing-edit-plan-dg-detail').datagrid('reload');
	$('#contract_cgplan_dg_detail1').datagrid('reload');
}
</script>
</body>