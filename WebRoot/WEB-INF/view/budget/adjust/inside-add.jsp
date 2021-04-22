<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
		
<body>
<div class="win-div">
<form id="inside_save_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 609px;">
		<div class="win-left-div" data-options="region:'west',split:true">
			<div class="win-left-top-div" style="height: 450px;">
				<div class="easyui-accordion" data-options="" id="easyAcc" style="width:662px;margin-left: 20px">
					<div title="指标调整信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<div style="height:30px">
							<span><strong>调增指标:</strong></span>
						</div>
						<div>
							<jsp:include page="inside/zbdr.jsp" />
						</div>
						</br>
						<div style="height:30px">
							<span><strong>调减指标:</strong></span>
						</div>
						<div>
							<jsp:include page="inside/zbdc.jsp" />
						</div>
						<div id="jbxx" style="margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable">
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;操作人</td>
	
									<td class="td2"><input style="width: 200px;" id=""
										name="opUser" class="easyui-textbox"
										value="${bean.opUser}" readonly="readonly"></input>
									</td>
									
									<td class="td4">
										<!-- 主键ID --><input type="hidden" name="inId" value="${bean.inId}" />
										<!-- 编码 --><input type="hidden" name="inCode" value="${bean.inCode}" />
										<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="insideflowStauts" />
										<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="insideStauts" />
									</td>
	
									<td class="td1"><span class="style1">*</span>&nbsp;调整时间</td>
									<td class="td2"><input style="width: 200px;"
										id="insideOpTime" name="opTime" class="easyui-datebox"
										value="${bean.opTime}" readonly="readonly"></input>
									</td>
								</tr>
								
								<%-- <tr class="trbody">
									<td class="td1">&nbsp;&nbsp;调减部门</td>
									<c:if test="${openType=='add' }">
										<td colspan="4">
											<input class="easyui-combobox" style="width: 555px;" id="inside_departId" name="insideDeptId" data-options="editable:false,panelHeight:'auto',
												url:'${base}/depart/chooseDepart',
												method:'POST',
												valueField:'code',
												textField:'text',
												onSelect: function(rec){
													$('#inside_departName').val(rec.text);
												}
												"/>
										</td>	
									</c:if>
									<c:if test="${openType=='edit' }">
										<td colspan="4">
											<input class="easyui-combobox" style="width: 555px;" id="inside_departId" name="insideDeptId" data-options="editable:false,panelHeight:'auto',
												url:'${base}/depart/chooseDepart?selected=${bean.insideDeptId }',
												method:'POST',
												valueField:'code',
												textField:'text',
												onSelect: function(rec){
													$('#inside_departName').val(rec.text);
												}
												"/>
										</td>
									</c:if>
									
									<td class="td4">
										<!-- 调减部门名称 --><input type="hidden" id="inside_departName" value="" />
									</td>
								</tr> --%>
								<tr style="height: 5px;">&nbsp;</tr>
								<tr style="height: 70px;">
									<td class="td1" valign="top">&nbsp;&nbsp;申请事由</td>
									<td colspan="4">
										<%-- <input class="easyui-textbox" data-options="multiline:true" name="appDesc" style="width:555px;height:60px" value="${bean.appDesc}"> --%>
										
										<textarea name="appDesc" class="textbox-text" oninput="textareaNum(this,'textareaNum1')"
										autocomplete="off"  style="width:555px;height:60px;resize:none">${bean.appDesc }</textarea>
									</td>
								</tr>
								<tr>
									<td align="right" colspan="5">
									可输入剩余数：<span id="textareaNum1" class="200">200</span>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			
		
			</div>
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveInside(0)">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveInside(1)">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=预算管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="win-right-div" id="check-system-table" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
</div>	
	
<script type="text/javascript">
$(document).ready(function() {
	//设值调整时间
	if ($("#insideOpTime").textbox().textbox('getValue') == "") {
		$("#insideOpTime").textbox().textbox('setValue', 'date');
	}
});

//保存
function saveInside(flowStauts) {
	var num = parseFloat($('#snum2').textbox('getValue'));
	var snum = parseFloat($('#snum3').textbox('getValue'));
	if(num==0||snum!=0){
		alert('请核对调入调出金额');
	} else {
		$('#zbdc').datagrid('acceptChanges');
		$('#zbdr').datagrid('acceptChanges');
		
		
		// 在后台反序列话成调入调出指标的Json对象集合
		var rows = $('#zbdc').datagrid('getRows');
		var j1 = "";
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].changeAmount==null||rows[i].changeAmount==''||rows[i].changeAmount==undefined||rows[i].changeAmount=='NaN'){
				alert('请填写完整指标调增金额');
				return false;
			}
			j1 = j1 + JSON.stringify(rows[i]) + ",";
		}
		$('#insideDcJson').val(j1);
		
		var rows = $('#zbdr').datagrid('getRows');
		var j2 = "";
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].changeAmount==null||rows[i].changeAmount==''||rows[i].changeAmount==undefined||rows[i].changeAmount=='NaN'){
				alert('请填写完整指标调减金额');
				return false;
			}
			j2 = j2 + JSON.stringify(rows[i]) + ",";
		}
		$('#insideDrJson').val(j2);
		
		//设置审批状态
		$('#insideflowStauts').val(flowStauts);
		//设置申请状态
		$('#insideStauts').val(flowStauts);

		//提交
		$('#inside_save_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/insideAdjust/save',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					closeWindow();
					$('#insideTab').datagrid('reload');
					$('#indexdb').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
					/* closeWindow();
					$('#inside_save_form').form('clear'); */
				}
			}
		});
	}
	
}

//刷新流程
$("#inside_departId").combobox({
	onChange:function(){
		//所有行
		var rows = $("#zbdc").datagrid("getRows");
		//行数为空，没有选择调减指标
		if(rows == ''){
			$('#inside_departId').combobox('setValue', '');
			alert("请先选择指标！");
			return;
		}
		
		//调减部门名称
		var insideDeptName = $("#inside_departName").val();
		//更新页面上的调减部门
		for(i = 0; i < rows.length; i++){
			$('#zbdc').datagrid('updateRow',{
				index: i,
				row: {
					deptName: insideDeptName
				}
			});
		}
		//调减部门id
		var insideDeptId = $("#inside_departId").val();
		$("#check-system-table").load("${base}/insideAdjust/refreshProcess?insideDeptId="+insideDeptId);
	}
});
</script>
</body>

