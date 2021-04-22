<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="assetReturnAddEditForm" action="${base}/assetReturn/save"	method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
						<input type="hidden" name="fId_A" id="fId_A_Search" value="${bean.fId_A}" />
						<input type="hidden" name="fAssType" id="fAssType_Search" value="${bean.fAssType}" />
						<input type="hidden" name="fAssetType" id="fAssetType_Search" value="${bean.fAssetType}" />
						<input type="hidden" name="fAcpCode" id="fAcpCode_Search" value="${bean.fAcpCode}" />
						<input type="hidden" name="fReturnOperator" id="fReturnOperator_Search" value="${bean.fReturnOperator}" />
						<input type="hidden" name="fReturnOperatorId" id="fReturnOperatorId_Search" value="${bean.fReturnOperatorId}" />
						<input type="hidden" name="fOperatorId" id="fOperatorId_Search" value="${bean.fOperatorId}" />
						<input type="hidden" name="fDeptId" id="fDeptId_Search" value="${bean.fDeptId}" />
						<input type="hidden" name="fReqTime" id="fReqTime_Search" value="${bean.fReqTime}" />
						<input type="hidden" name="fAcceptStauts" id="fAcceptStauts_Search" value="${bean.fAcceptStauts}" />
						<input type="hidden" name="fReturnStauts" id="fReturnStauts_Search" value="${bean.fReturnStauts}" />
						<input type="hidden" name="fFlowStauts_A" id="fFlowStauts_A_Search" value="${bean.fFlowStauts_A}" />
						<input type="hidden" name="fNextUserName" id="fNextUserName_Search" value="${bean.fNextUserName}" />
						<input type="hidden" name="fNextUserCode" id="fNextUserCode_Search" value="${bean.fNextUserCode}" />
						<input type="hidden" name="fNextCode" id="fNextCode_Search" value="${bean.fNextCode}" />
						<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
							<div title="基本信息" data-options="collapsed:false,collapsible:false"style="overflow: auto; margin-top: 10px;">
								<table class="ourtable" cellpadding="0" cellspacing="0" style="margin-top: 3px;width:707px;">
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;交回单号</td>
										<td class="td2"><input id="fAssReturnCode"
											readonly="readonly" required="required"
											class="easyui-textbox" name="fAssReturnCode"
											data-options="prompt:'',validType:'length[1,40]'"
											value="${bean.fAssReturnCode}" style="width: 200px" /></td>
										<td class="td4">&nbsp;</td>
										<td class="td1"><span class="style1">*</span>&nbsp;预计交回时间</td>
										<td class="td2"><input class="easyui-datebox" readonly="readonly"
											class="dfinput" id="fReturnTime" name="fReturnTime" required="required"
											data-options="editable:false" style="width: 200px"
											value="${bean.fReturnTime}" /></td>
									</tr>
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
										<td class="td2"><input id="fOperator"
											class="easyui-textbox" readonly="readonly"
											required="required" name="fOperator"
											data-options="validType:'length[1,20]'" style="width: 200px"
											value="${bean.fOperator}" /></td>
										<td class="td4">&nbsp;</td>
										<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
										<td class="td2"><input id="fDeptName"
											class="easyui-textbox" readonly="readonly"
											required="required" name="fDeptName"
											data-options="prompt:' ',validType:'length[1,20]'"
											style="width: 200px" value="${bean.fDeptName}" /></td>
									</tr>
									<tr class="trbody">
										<td class="td1" valign="top"><p style="margin-top: 8px">
												<span class="style1">*</span>&nbsp;交回说明
											</p></td>
										<td colspan="4"><textarea name="fRemark" id="fRemark"
												class="textbox-text" required="required"
												oninput="textareaNum(this,'textareaNum1')"
												autocomplete="off" readonly
												style="width: 570px; height: 62px; resize: none">${bean.fRemark }</textarea>
										</td>
									</tr>
								</table>
							</div>

							<div title="交回清单"
								data-options="collapsed:false,collapsible:false"
								style="overflow: auto; margin-top: 10px;">
								<%@ include file="return_list_info_detail.jsp"%>
							</div>
							<c:if test="${checkinfo==1}">
								<div title="审批记录"
									data-options="collapsed:false,collapsible:false"
									style="overflow: auto; margin-top: 10px;">
									<jsp:include page="../../check_history.jsp" />
								</div>
							</c:if>
						</div>
					</div>

					<div class="window-left-bottom-div" style="margin-top: 55px;">
						<c:if test="${detailType=='accept'}">
							<a href="javascript:void(0)" onclick="return_Accept('${bean.fId_A}')">
								<img src="${base}/resource-modality/${themenurl}/button/sl1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>&nbsp;&nbsp;&nbsp;
						</c:if>
						<c:if test="${ledger=='detail'}">
							<a href="javascript:void(0)"onclick="closeWindow()"> 
								<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>
						</c:if>
						<c:if test="${ledger=='ledger'}">
							<a href="javascript:void(0)"onclick="closeFirstWindow()"> 
								<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>
						</c:if>
						&nbsp;&nbsp;&nbsp; 
						<a href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
							<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png"	onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</div>
				</div>
				<div class="window-right-div" style="width:254px;height: 591px">
					<jsp:include page="../../check_system.jsp" />
				</div>
			</div>
		</form>
	<script type="text/javascript">
	function return_Accept(id) {
		if(confirm('是否已将该信息录入财政资产系统?')){
			$.ajax({
				url:'${base}/assetReturn/accept/' + id,
				type:'POST',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						$("#asset_return_accept_dg").datagrid('reload');
						closeWindow()
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});
		}
	}
	</script>
</body>