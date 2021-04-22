<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
  
<body>
<form id="inside_check_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
<div class="win-div">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="easyAcc" style="width:662px;margin-left: 20px">
					<div title="指标调整信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<div style="height:30px">
							<span><strong>调增指标:</strong></span>
						</div>
						<div>
							<jsp:include page="inside/zbdr.jsp" />
						</div>
						<div style="height:30px">
						<span><strong>调减指标:</strong></span>
						</div>
						<div>
							<jsp:include page="inside/zbdc.jsp" />
						</div>
						</br>
						
						<div style="margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable">
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;操作人</td>
	
									<td class="td2"><input style="width: 200px;" id=""
										name="opUser" class="easyui-textbox"
										value="${bean.opUser}" readonly="readonly"></input>
									</td>
									
									<td class="td4">
										<!-- 主键ID --><input type="hidden" name="inId" value="${bean.inId}" />
										<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="insideflowStauts" />
										<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="insideStauts" />
										<!-- 部门 --><input type="hidden" name="deptCode" value="${bean.deptCode}" />
										<!-- 审批附件 --><input type="hidden" name="spjlFile" id="spjlFile" value=""/>
										<!-- 审批结果 -->
							    		<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
							    		<!-- 审批意见 -->
									    <input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
									</td>
	
									<td class="td1"><span class="style1">*</span>&nbsp;调整时间</td>
									<td class="td2"><input style="width: 200px;"
										id="insideOpTime" name="opTime" class="easyui-datebox"
										value="${bean.opTime}" readonly="readonly"></input>
									</td>
								</tr>	
								
								<%-- <tr class="trbody">
									<td class="td1">&nbsp;&nbsp;调减部门</td>
									<td colspan="4">
										<input class="easyui-combobox" style="width: 555px;" id="inside_departId" name="insideDeptId" readonly="readonly" data-options="editable:false,panelHeight:'auto',
											url:'${base}/depart/chooseDepart?selected=${bean.insideDeptId }',
											method:'POST',
											valueField:'code',
											textField:'text',
											"/>
									</td>	
								</tr> --%>
								<tr style="height: 5px;">&nbsp;</tr>
								<tr style="height: 70px;">
									<td class="td1" valign="top"><p style="margin-top: 8px">&nbsp;&nbsp;申请事由</p></td>
									<td colspan="4">
										<input class="easyui-textbox" data-options="multiline:true" name="appDesc" style="width:555px;height:70px;" value="${bean.appDesc}" readonly="readonly">
									</td>
								</tr>
							</table>
						</div>
					</div>
					
				</div>
			
		
			</div>
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
					<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
					<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</div>	
</form>
<script type="text/javascript">	
//审批
function check() {
	$('#inside_check_form').form('submit', {
		onSubmit : function() {
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				$.messager.progress();
			}
			return flag;
		},
		url : base + '/insideCheck/checkResult',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				closeWindow();
				$('#outside_check_form').form('clear');
				$('#insideCheckTab').datagrid('reload'); 
				$("#indexdb").datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.info, 'error');
				closeWindow();
				$('#outside_check_form').form('clear');
			}
		}
	});
}
</script>
</body>


