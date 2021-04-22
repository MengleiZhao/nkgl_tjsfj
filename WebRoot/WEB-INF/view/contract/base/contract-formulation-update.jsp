<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
				
				<div class="easyui-accordion" style="width:662px;margin-left: 0px;">
					<div  data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top:10px;">	
								<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;合同编号</td>
										<td colspan="4">
											<input id="F_fcCode" class="easyui-textbox" readonly="readonly" type="text"  name="fcCode" data-options="validType:'length[1,32]'" style="width: 555px" value="${bean.fcCode}"/>
										</td >
									</tr>
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;合同名称</td>
										<td colspan="4">
											<input class="easyui-textbox" type="text"  id="F_fcTitle" name="fcTitle" readonly="readonly" required="required" data-options="validType:'length[1,50]'" style="width: 555px" value="${bean.fcTitle}"/>
										</td>
									</tr>
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;合同分类</td>
										<td class="td2">
											<input class="easyui-combobox" id="F_fcType" readonly="readonly" required="required" name="fcType" style="width: 200px" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=HTFL&selected=${bean.fcType}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'">
										</td>
										<td class="td4">&nbsp;</td>
										<td class="td1"><span class="style1">*</span>&nbsp;合同份数</td>
										<td class="td2">
											<input id="F_fcNum" class="easyui-numberbox" readonly="readonly" required="required" name="fcNum" data-options="validType:'length[1,2]',precision:0" style="width: 200px" value="${bean.fcNum}"/>
										</td>
									</tr>
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;合同金额小写</td>
										<td class="td2">
											<input class="easyui-numberbox" id="F_fcAmount" required="required" name="fcAmount" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fcAmount}"/>
										</td>
										<td class="td4">&nbsp;</td>
										<td class="td1"><span class="style1">*</span>&nbsp;合同金额大写</td>
										<td class="td2">
											<input id="F_fcAmountMax" class="easyui-textbox"  type="text" readonly="readonly" required="true" name="fcAmountMax" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fcAmountMax}"/>
										</td >
									</tr>
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;保证金金额</td>
										<td class="td2">
											<input id="F_fMarginAmount" class="easyui-numberbox" type="text" readonly="readonly" required="required" name="fMarginAmount" data-options="validType:'length[1,20]',icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fMarginAmount}"/>
										</td >
										<td class="td4">&nbsp;</td>
										<td class="td1"><span class="style1">*</span>&nbsp;质保期</td>
										<td class="td2">
											<input class="easyui-textbox" id="F_fWarrantyPeriod" readonly="readonly" required="required" name="fWarrantyPeriod"  data-options="icons: [{iconCls:'icon-yue'}],validType:'length[1,20]'" style="width: 200px" value="${bean.fWarrantyPeriod}"/>
										</td>
									</tr>
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;付款方式</td>
										<td class="td2">
											<input class="easyui-combobox" id="F_fPayType" readonly="readonly" required="required" name="fPayType.code" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=FKFS&selected=${bean.fPayType.code}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'" style="width: 200px" />
										</td>
										<td class="td4">&nbsp;</td>
										<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
										<td class="td2">
											<input id="F_fOperator" class="easyui-textbox" readonly="readonly" type="text" required="true" name="fOperator" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fOperator}"/>
										</td >
									</tr>
									<tr id="cg1" hidden="hidden">
										<td class="td1"><span class="style1">*</span>&nbsp;采购订单</td>
										<td  colspan="4">
											<input id="F_fPurchName" readonly="readonly" class="easyui-textbox" name="fPurchName" data-options="prompt:'单击打开选取采购订单',validType:'length[1,50]'" value="${bean.fPurchName}" style="width: 555px"/>
										</td>
									</tr>
									<tr id="cg2" hidden="hidden" class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;预算指标名称</td>
										<td  colspan="4">
											<input id="F_fBudgetIndexName" readonly="readonly" class="easyui-textbox" name="fBudgetIndexName" data-options="prompt:'单击打开选取指标',validType:'length[1,50]'" style="width: 555px" value="${bean.fBudgetIndexName}"/>
										</td >
									</tr>
									<tr id="cg3" hidden="hidden" class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;预算指标金额</td>
										<td  colspan="4">
											<input id="F_fAvailableAmount" readonly="readonly" class="easyui-numberbox" name="fAvailableAmount" style="width: 200px" data-options="icons: [{iconCls:'icon-yuan'}],precision:2,validType:'length[0,20]'" value="${bean.fAvailableAmount}"/>
											<input id="Fc_fBudgetIndexCode" hidden="hidden" type="text" maxlength="11" name="fBudgetIndexCode" style="width: 200px"  value="${bean.fBudgetIndexCode}"/>
											<input id="F_indexType" hidden="hidden" type="text" maxlength="2" name="indexType" style="width: 200px"  value="${bean.indexType}"/>
											<input id="F_proDetailId" hidden="hidden" type="text" name="proDetailId" style="width: 200px"  value="${bean.proDetailId}"/>
										</td >
									</tr>
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;合同开始时间</td>
										<td class="td2">
											<input id="F_fContStartTime" class="easyui-datebox" class="dfinput" readonly="readonly" required="required" name="fContStartTime" data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${bean.fContStartTime}"/>
										</td >
										<td class="td4">&nbsp;</td>
										<td class="td1"><span class="style1">*</span>&nbsp;合同结束时间</td>
										<td class="td2">
											<input class="easyui-datebox"  class="dfinput"  id="F_fContEndTime" readonly="readonly" required="required" name="fContEndTime"  data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${bean.fContEndTime}"/>
										</td>
									</tr>
									<tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;所属部门</td>
										<td class="td2">
											<input id="F_fDeptName" class="easyui-textbox" readonly="readonly" type="text"  name="fDeptName" data-options="validType:'length[1,50]'" style="width: 200px" value="${bean.fDeptName}"/>
										</td >
										<td class="td4">&nbsp;</td>
										<td class="td1"><span class="style1">*</span>&nbsp;签订日期</td>
										<td class="td2">
											<input class="easyui-datebox" class="dfinput"  id="F_fSignTime" readonly="readonly" required="required" name="fSignTime"  data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${bean.fSignTime}"/>
										</td>
									</tr>
									<%-- <tr class="trbody">
										<td class="td1"><span class="style1">*</span>&nbsp;签约方名称</td>
										<td colspan="4">
											<input class="easyui-textbox" type="text"  id="F_fContractor" name="fContractor" readonly="readonly" required="required" data-options="validType:'length[1,50]'" style="width: 555px" value="${bean.fContractor}"/>
										</td>
									</tr> --%>
									<tr class="trbody">
										<td class="td1">是否制式合同</td>
										<td class="td2">
											<input type="radio" name="standard" value="0" <c:if test="${bean.standard==0}">checked="checked"</c:if> />否
											<input type="radio" name="standard" value="1" <c:if test="${bean.standard==1}">checked="checked"</c:if> />是
										</td>
										<td class="td4">&nbsp;</td>
										<td class="td1"></td>
											<td class="td2">
											<%-- <input type="radio" name="fIsAuthor" value="0" <c:if test="${bean.fIsAuthor=='0'}">checked="checked"</c:if> />否
											<input type="radio" name="fIsAuthor" value="1" <c:if test="${bean.fIsAuthor=='1'}">checked="checked"</c:if> />是 --%>
										</td>
									</tr>
									<%-- <tr class="trbody">
										<td class="td1">
											&nbsp;&nbsp;附件
											<input type="file" multiple="multiple" id="f" onchange="upFile()" hidden="hidden">
											<input type="text" id="files" name="files" hidden="hidden">
										</td>
										<td colspan="4" id="tdf">
											<a onclick="$('#f').click()" style="font-weight: bold;" href="#">
												<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
											</a>
											<c:forEach items="${attac}" var="att">
												<div style="margin-top: 10px;">
													<a href='#' style="color: #666666;font-weight: bold;" >${att.fAttacName}</a>
													
													<img src="${base}/resource-modality/${themenurl}/sccg.png">
													
													<a id="${att.fAttacName}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
												</div>
											</c:forEach>
										</td>
									</tr> --%>
									<tr style="height: 70px;">
										<td class="td1" valign="top" >合同情况说明</td>
										<td colspan="4">
											<input class="easyui-textbox" type="text" readonly="readonly" id="CF_fRemark" name="fRemark" data-options="multiline:true,validType:'length[0,200]',validType:'invalid'"  style="width:555px;height:70px" value="${bean.fRemark}"/>
											<input type="text" id="CF_fFlowStauts" name="fFlowStauts" hidden="hidden" value="${bean.fFlowStauts}"/>
										</td>
									</tr>
									<tr class="trbody">
										<td class="td1">
											&nbsp;&nbsp;附件
											<input type="file" multiple="multiple" id="fhtnd" onchange="upladFile(this,'htnd','htgl01')" hidden="hidden">
											<input type="text" id="files" name="files" hidden="hidden">
										</td>
										<td colspan="4" id="tdf">
											<%-- <c:if test="${openType=='add'||openType=='edit'}">
											<a onclick="$('#fhtnd').click()" style="font-weight: bold;" href="#">
												<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
											</a>
											<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
												<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
											 	</div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
											</div>
											</c:if> --%>
											<c:forEach items="${formulationAttaList}" var="att">
												<c:if test="${att.serviceType=='htnd' }">
													<div>
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
													&nbsp;&nbsp;&nbsp;&nbsp;
													<%-- <c:if test="${openType=='add'||openType=='edit'}">
													<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
													&nbsp;&nbsp;&nbsp;&nbsp;
														<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
													</c:if> --%>
													</div>
												</c:if>
											</c:forEach>
										</td>
									</tr>
									<tr></tr>
									<tr></tr>
								</table>
							</div>
							<%-- <c:if test="${!empty check.fremark}"> 
								<div title="审批信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">	
									<table class="ourtable" border="0">
										<tr style="height: 70px;">
											<td class="td1" valign="top">审批意见</td>
											<td colspan="4">
												<input class="easyui-textbox"  value="${check.fremark}" type="text" id="C_fremark" name="fremark" data-options="multiline:true"  style="width:505px;height:70px" />
											</td>
										</tr>
									</table>
								</div>
							</c:if> --%>
							
							
							</div>
							


<script type="text/javascript">
$("#F_fContStartTime").datebox({
    onSelect : function(beginDate){
        $('#F_fContEndTime').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
</script>