<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<script type="text/javascript" src="/resource/custom/custom.js"></script>
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
										<td class="td2"><input class="easyui-datebox"
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
												autocomplete="off"
												style="width: 570px; height: 62px; resize: none">${bean.fRemark }</textarea>
										</td>
									</tr>
									<tr>
										<td align="right" colspan="5" style="padding-right: 0px;">
											可输入剩余数：<span id="textareaNum1" class="200"> <c:if
													test="${empty bean.fRemark}">200</c:if> <c:if
													test="${!empty bean.fRemark}">${200-bean.fRemark.length()}</c:if>
										</span>
										</td>
									</tr>
								</table>
							</div>

							<div title="交回清单"
								data-options="collapsed:false,collapsible:false"
								style="overflow: auto; margin-top: 10px;">
								<a href="javascript:void(0)" style="margin-left: 590px;" onclick="addReturnList();"> <img
									src="${base}/resource-modality/${themenurl}/button/tjzc1.png"
									onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
								</a>
								<a href="javascript:void(0)" onclick="deleteReturnList();"> <img
									src="${base}/resource-modality/${themenurl}/button/shanchu1.png"
									onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
								</a>
								<%@ include file="return_list_info.jsp"%>
							</div>
							<c:if test="${checkinfo==1}">
								<div title="审批记录"
									data-options="collapsed:false,collapsible:false"
									style="overflow-x: hidden;; margin-top: 10px;">
									<jsp:include page="../../check_history.jsp" />
								</div>
							</c:if>
						</div>
					</div>

					<div class="window-left-bottom-div" style="margin-top: 55px;">
						<a href="javascript:void(0)" onclick="assetReturnAddEditForm(0);">
							<img
							src="${base}/resource-modality/${themenurl}/button/zhanchun1.png"
							onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)"
							onclick="assetReturnAddEditForm(1)"> <img
							src="${base}/resource-modality/${themenurl}/button/songshen1.png"
							onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)"
							onclick="closeWindow()"> <img
							src="${base}/resource-modality/${themenurl}/button/guanbi1.png"
							onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a> &nbsp;&nbsp;&nbsp; <a
							href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
							<img
							src="${base}/resource-modality/${themenurl}/button/xgzd1.png"
							onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</div>
				</div>
				<div class="window-right-div" style="width:254px;height: 591px">
					<jsp:include page="../../check_system.jsp" />
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		function assetReturnAddEditForm(sts) {
			var fRemark = $('#fRemark').val();
			if (fRemark == '') {
				$.messager.alert('系统提示', '交回说明不能为空', 'info');
				return;
			}
			$("#fFlowStauts_A_Search").val(sts);
			var AssetReturnSelect = getAssetReturnSelect();
			if(AssetReturnSelect==''||AssetReturnSelect==null||AssetReturnSelect=='[]'){
				alert('请选择交回资产！');
				return false;
			}
			var rows = $('#return_list_info_dg').datagrid('getRows');
			var hash=[];
			 for(var i=0; i<rows.length-1; i++){
		          for(var j=i+1; j<rows.length; j++){
		            if(rows[j].fAssCode_AR === rows[i].fAssCode_AR){
		            	alert('交回资产不允许重复提交！');
						return false;
		            }
		         }
		      }
			  
			$('#assetReturnAddEditForm').form('submit', {
				onSubmit : function(param) {
					param.assetReturnJson = AssetReturnSelect;
					flag = $(this).form('enableValidation').form('validate');
					if (flag) {
						$.messager.progress();
					}
					return flag;
				},
				success : function(data) {
					if (flag) {
						$.messager.progress('close');
					}
					data = eval("(" + data + ")");
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						closeWindow();
						$('#assetReturnAddEditForm').form('clear');
						$("#asset_return_dg").datagrid('reload');
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});
			
		}

		function addReturnList() {
			var win = creatFirstWin('个人资产领用表', 900, 580, 'icon-search',
					"/assetReturn/toAssetReturnSelect");
			win.window('open');
		}
		function deleteReturnList() {
			/* if (editIndex == undefined){return}
			$('#return_list_info_dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex); */
			var row = $('#return_list_info_dg').datagrid('getSelected');
			var ind = $('#return_list_info_dg').datagrid('getRowIndex',row);
			var ind = $('#return_list_info_dg').datagrid('deleteRow',ind);
			/* editIndex = undefined; */
			/* if (confirm("确认删除吗？")) {
				$.ajax({
					type : 'POST',
					url : '${base}/assetReturnList/batchDelete/' + rowIndexs,
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$.messager.alert('系统提示', data.info, 'info');
							$("#return_list_info_dg").datagrid('reload');
						} else {
							$.messager.alert('系统提示', data.info, 'error');
						}
					}
				});
			} */
		}
		function getAssetReturnSelect(){
			//$('#change-upt-datagrid').datagrid('acceptChanges');
			var rows = $('#return_list_info_dg').datagrid('getRows');
			var entities= '';
			for(i = 0;i < rows.length;i++){
			 	entities = entities  + JSON.stringify(rows[i]) + ',';  
			}
			 entities = '[' + entities.substring(0,entities.length -1) + ']';
			 return entities;
		}
	</script>
</body>