<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="quota_regist_save_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
			<!-- 隐藏域 --> 
				<input type="hidden" name="fQId" id="fQId" value="${bean.fQId}"/><!--主键id -->
				<input type="hidden" name="fCheckStauts" id="fCheckStauts" value="${bean.fCheckStauts}"/><!--审批状态 -->
				<input type="hidden" name="fSubName" id="fSubName" value="${bean.fSubName}"/><!--科目名称 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="收款录入" data-options="collapsed:false,collapsible:false"
					style="overflow:auto">
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;width:707px;padding-left: 3px;">
						<tr class="trbody">
									<td class="td1"><span class="style1">*</span>录入时间</td>
									<td class="td2">
										<input id="fOperateTime" name="fOperateTime"  class="easyui-datebox" readonly="readonly"  style="width: 200px;height: 30px" value="${bean.fOperateTime}" />
									</td>
									<td style="width: 0px"></td>
									<td class="td1" style="width: 102px"><span class="style1">*</span>政府支出经济分类</td>
									<td class="td2">
										<input id="fSubCode" name="fSubCode" class="easyui-combobox" readonly="readonly" data-options="validateOnCreate:false,editable:false,panelHeight:'auto',url:'${base}/quotaManage/lookupsJson?blanked=0&selected=${bean.fSubCode}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid',onSelect:onSelectSub"
										 required="required"  style="width: 200px;height: 30px" />
									</td>
								</tr>
								<tr>
									<td class="td1"><span class="style1">*</span>收款金额（元）</td>
									<td class="td2" colspan="4">
										<input id="fAmount" name="fAmount" class="easyui-numberbox" readonly="readonly" required="required"  style="width: 200px;height: 30px" value="${bean.fAmount}"/>
									</td>
								</tr>
							</table>
				</div>
			</div>
		</div>	
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="closeWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	</div>
</form>
	<script type="text/javascript">
	//保存
	function saveRegist(status) {	
		
		$('#fCheckStauts').val(status);
		//提交
		$('#quota_regist_save_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/quotaManage/save',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					closeWindow();
					$("#quota_regist_save_form").form("clear"); 
					$("#quota_regist_dg").datagrid("reload");
				} else {
					$.messager.alert('系统提示', data.info, 'error');
					closeWindow();
					$('#quota_regist_save_form').form('clear');
				}
			}
		}); 	
	}
	
	function onSelectSub(rec){
		$('#fSubName').val(rec.text);
	}
	
	</script>
</body>