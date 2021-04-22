<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="win-div">
<form id="confplan_check_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
																	<!-- 第一个div -->
								<div title="配置计划信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
									<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
										<tr class="trbody">
											<td class="td1"><span class="style1">*</span>单据编号</td>
											<td class="td2">
												<input class="easyui-textbox" id="F_flistNum"  name="flistNum"  style="width:200px;" readonly="readonly" data-options="validType:'length[1,50]'" value="${bean.flistNum}"/>
												<input type="hidden" name="fplId" id="F_fplId" value="${bean.fplId}"/><!--隐藏域  -->
												<input type="hidden" name="fcheckStauts" id="F_fcheckStauts" value="${bean.fcheckStauts}"/><!--配置申请的审批状态  -->
												<input type="hidden" name="fstauts" id="F_fstauts" value="${bean.fstauts}" /><!--数据的删除状态  -->
												<input type="hidden" name="fisChecked" id="F_fisChecked" value="${bean.fisChecked}" /><!--采购的选择状态  -->
												<input type="hidden" name=fuserName value="${bean.userName2}" /><!--当前审批人姓名  -->
												<input type="hidden" name="fuserId" value="${bean.userId}" /><!--当前审批人id  -->
												<input type="hidden" name="nCode" value="${bean.nCode}" /><!--当前审批节点  -->
												<input type="hidden" name="freqUserId" value="${bean.freqUserId}" /><!--申请人  -->
												<!-- 审批结果 -->
									    		<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
									    		<!-- 审批意见 -->
											    <input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
											    <!-- 审批附件 -->
											    <input type="hidden" name="spjlFile" id="spjlFile" value=""/>
											</td>
											<td class="td4"></td>
											<td class="td1"><span class="style1">*</span>申请部门</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_freqDept"  name="freqDept" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.freqDept}"/>
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>申请日期</td>
											<td class="td2">
												<input class="easyui-datebox" type="text" id="F_freqTime"  name="freqTime" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.freqTime}"/>
											</td>
											<td class="td4"></td>
											<td class="td1"><span class="style1">*</span>配置采购类型</td>
											<td class="td2">
												<select class="easyui-combobox" id="F_fprocurType" name="fprocurType" readonly="readonly" required="required" style="width: 200px;" data-options="editable:false,panelHeight:'auto'" validType="selectValueRequired['#test']">
													<!-- <option value="">--请选择--</option> -->
													<option value="A10" <c:if test="${bean.fprocurType=='A10'}">selected="selected"</c:if>>货物</option>
													<option value="A20" <c:if test="${bean.fprocurType=='A20'}">selected="selected"</c:if>>工程采购</option>
													<option value="A30" <c:if test="${bean.fprocurType=='A30'}">selected="selected"</c:if>>服务</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>申请部门联系人</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_freqLinkMen" readonly="readonly"  name="freqLinkMen"  required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.freqLinkMen}"/>
											</td>
											<td class="td4"></td>
											<td class="td1"><span class="style1">*</span>联系人电话</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_flinkTel" readonly="readonly"  name="flinkTel"  required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.flinkTel}"/>
											</td>
										</tr>
										<tr style="height: 70px;">
											<td class="td1" valign="top"><p style="margin-top: 8px"><span class="style1">*</span>配置申请内容</td>
											<td colspan="4">
												<input class="easyui-textbox" type="text" id="F_freqContent" readonly="readonly"  name="freqContent"  data-options="validType:'length[1,200]',multiline:true"   style="width:555px;height:70px;" value="${bean.freqContent}"/>
											</td>
										</tr>
										<tr style="height: 70px;">
											<td class="td1" valign="top"><p style="margin-top: 8px">备注</td>
											<td colspan="4">
												<input class="easyui-textbox" type="text" id="F_fremark"  readonly="readonly" name="fremark"  data-options="validType:'length[1,200]',multiline:true"   style="width:555px;height:70px;" value="${bean.fremark}"/>
											</td>
										</tr>
										<tr class="trbody">
											<td class="td1">附件</td>
											<td colspan="4">
											<c:if test="${!empty attac}">
											<c:forEach items="${attac}" var="att">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a></br>
											</c:forEach>
											</c:if>
											<c:if test="${empty attac}">
												<span style="color:#999999">暂未上传附件</span>
											</c:if>
										</tr>
									</table>
								</div>
												<!-- 第二个div -->
								<div title="配置采购商品清单" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
 							  		<jsp:include page="cgconf_plan_mingxi.jsp" />												
								</div>
														<!-- 第3个div -->
								<div title="审批记录" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
 							  		<jsp:include page="../../check_history.jsp" />												
								</div>

				</div>
			</div>
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
					<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
					<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
		
	</div>
</form>
</div>
	<script type="text/javascript">

	function deleteF(val) {
		var child = document.getElementById(val.id);
		child.parentNode.removeChild(child);
	}
	
	//审核
	function check(checkResult) {
	 	$('#confplan_check_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/cgconfplancheck/checkResult',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					closeWindow();
					$('#confplan_check_form').form('clear');
					$('#confplan_check_tab').datagrid('reload'); 
					$("#indexdb").datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
					closeWindow();
					$('#confplan_check_form').form('clear');
				}
			}
		});	 
	}
    
	
	//查看附件
	function findAttacFile(id) {
		$.ajax({ 
			url: base+"/cgsqsp/attacFind?id="+id, 
			success: function(data){
				data=data.replace('\"','');
				data=data.replace('\"','');
				window.open(data);
	    }});
	}
	
	
	
	 //寻找相关制度
	function findSystemFile(id) {
		$.ajax({ 
			url: base+"/cheter/systemFind?id="+id, 
			success: function(data){
				data=data.replace('\"','');
				data=data.replace('\"','');
				window.open(data);
	    }});
	} 
	
	
	
	</script>
</body>