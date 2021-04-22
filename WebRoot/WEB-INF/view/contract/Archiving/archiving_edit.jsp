<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="uptInfo" action="${base}/Change/Save" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<input type="hidden" name="fcId" id="upt_fcId" value="${bean.fcId}"/>
	    		<input type="hidden" name="fFlowStauts" id="upt_fFlowStauts" value="${bean.fFlowStauts}"/>
	    		<input type="hidden" name="fContStauts" id="upt_fContStauts" value="${bean.fContStauts}"/>
	    		<input type="hidden" name="fUserName" id="upt_fUserName" value="${bean.fUserName}"/>
	    		<input type="hidden" name="fUserCode" id="upt_fUserCode" value="${bean.fUserCode}"/>
	    		<input type="hidden" name="fOperatorId" id="F_fOperatorId" value="${bean.fOperatorId}"/>
				<div>
					<c:if test="${openType=='Adetail'||openType=='Aedit'||openType=='detail'||openType=='Gdetail'||openType=='Edetail'}">
						<div class="easyui-accordion" data-options="multiple:true" style="width:662px;margin-left: 20px;">
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
											<input class="easyui-datebox" class="dfinput" id="F_fSignTime" required="required" <c:if test="${openType=='Adetail'}">readonly="readonly"</c:if> name="fSignTime"  data-options="validType:'length[1,20]',editable:false,onHidePanel:beforeTime" style="width: 200px" value="${bean.fSignTime}"/>
										</td>
										<td class="td4">&nbsp;</td>
										<td class="td1"></td>
										<td class="td2">
										</td >
									</tr>
									<tr class="trbody">
										<td class="td1" style="width:55px;text-align: right"><span class="style1">*</span>
											已盖章合同文本
											<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'htgdfj','zcgl01')" hidden="hidden">
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
													<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
													</c:if>
													</div>
												</c:if>
											</c:forEach>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</c:if>
					<div title="合同信息" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						<div class="easyui-accordion" style="width: 662px;margin-left: 20px;margin-right: 20px">
							<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
								<%@ include file="../base/contract-formulation-base2.jsp" %>
							</div>	
							<div title="签约方信息" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
								<%@ include file="../base/contract-formulation-sign-base-detail.jsp" %>
							</div>
							<div id="divTwo">
								<div class="easyui-accordion" style="width: 660px;overflow:hidden;">
								<div id="select_cgconf_plan_detail" hidden="hidden" title="采购清单" data-options="collapsed:false,collapsible:false" style="overflow:hidden;margin-top: 10px;">
									<%@ include file="../base/cgconf_plan_mingxi_detail.jsp" %>
								</div>
								</div>
							</div>
							<div id="divOne">
								<div class="easyui-accordion" style="width: 660px;overflow:hidden;">
								<div title="付款计划" style="overflow:hidden;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
									<%@ include file="../base/contract-filing-edit-plan-detail.jsp" %>
								</div>
								</div>
							</div>
							<div title="审批记录" style="overflow:auto;margin-top: 10px;" data-options="collapsed:false,collapsible:false">
								<jsp:include page="../../check_history.jsp" />												
							</div>
						</div>
					</div>
				</div>
	    		</div>
			</div>
			<div class="win-left-bottom-div">
				<c:if test="${openType=='Aadd'||openType=='Aedit'}">
					<a href="javascript:void(0)" onclick="ArchivingEditForm();">
						<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
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
function beforeTime(){
	var startday = Date.parse(new Date($("#F_fSignTime").datebox('getValue')));
	var maxTime = Date.parse(new Date($("#F_fContStartTime").datebox('getValue')));
	if(!isNaN(startday)){
    	if(startday>maxTime){
	    	alert("所选时间不在日程范围内请重新选择！");
    		$("#F_fSignTime").datebox("setValue","");
    	}
    	
    } 
}
$(function(){
	if('${signInfo.fIsOfficialUser}'==0){
		fIsOfficialUserNo();
	}else if('${signInfo.fIsOfficialUser}'==1){
		fIsOfficialUserYes();
	}
});
flashtab('contract-archiving-edit');	
$('#F_fcType').combobox({  
    onChange:function(newValue,oldValue){  
	var sel2=$('#F_fcType').combobox('getValue');
	if(sel2=="HTFL-01"){
		$('#cg1').show();
		$('#cg2').show();
		$('#divOne').show();
		$('#divTwo').show();
		//$('#cg2').hide();
		//$('#F_fPurchNo').next(".textbox").show();
	}else{
		$('#cg1').hide();
		$('#cg2').hide();
		$('#divOne').hide();
		$('#divTwo').hide();
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
		$(".fileUrl").each(function(){
			s=s+$(this).attr("id")+",";
		});
	    if(s==''){
			alert("请上传归档合同！");
			return false;
		}
		//var plan = getPlan();
		//console.log($('#upt_fFileSrc1').val());
    	$('#uptInfo').form('submit', {
				onSubmit:function(){ 
					//param.planJson=plan;
					flag=$(this).form('enableValidation').form('validate');
					if(flag){
						$.messager.progress();
					}
					return flag;
				}, 
				url:'${base}/Archiving/Save?type=0',
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
						$('#ArchivingcontractTab').datagrid('reload'); 
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
	</script>
</body>