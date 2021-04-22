<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
	<form>
		<table style="width: 100%">
			<tr>
				<th colspan="2" style="text-align: center;">该报销单已超出事前申请额度！</th>
			</tr>
			<tr>
				<td style="width: 200px;text-align: right;">报销标准：</td>
				<td>${standard }</td>
			</tr>
			<tr>
				<td style="width: 200px;text-align: right;">报销总金额：</td>
				<td>${amount}</td>
			</tr>
			<tr>
				<td style="width: 200px;text-align: right;">报销超额：</td>
				<td>${applyAmount}</td>
			</tr>
			<tr style="position:absolute;bottom: 10px">
				<td style="width: 200px;text-align: right;">
					<a href="javascript:void(0)" onclick="reimburseCheck(${sts})">
						<img src="${base}/resource-modality/${themenurl}/button/jxmrbx1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				<td>
					<a href="javascript:void(0)" onclick="closeFirstWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</form>
	<script>
//保存
function reimburseCheck(flowStauts) {
	// 在后台反序列话成明细Json的对象集合
	/* accept();
	var rows = $('#rmxdg').datagrid('getRows');
	var mingxi = "";
	for (var i = 0; i < rows.length; i++) {
		mingxi = mingxi + JSON.stringify(rows[i]) + ",";
	}
	$('#rMingxiJson').val(mingxi);
	
	// 在后台反序列话成发票Json的对象集合
	accept2();
	var rows2 = $('#drfpdg').datagrid('getRows');
	var fapiao = "";
	for (var i = 0; i < rows2.length; i++) {
		fapiao = fapiao + JSON.stringify(rows2[i]) + ",";
	}
	$('#rfpJson').val(fapiao); 
	
	
	//附件的路径地址
	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#files").val(s);


	//设置审批状态
	$('#reimburseFlowStauts').val(flowStauts);
	//设置报销状态
	$('#reimburseStauts').val(flowStauts);*/

	
	var h = $("#reimburseTypeHi").textbox().textbox('getValue');
	
	//提交
	$('#reimburse_save_form').form('submit', {
		onSubmit : function() {
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				//如果校验通过，则进行下一步
				$.messager.progress();
			}else{
				//校验不通过，就打开第一个校验失败的手风琴
				openAccordion();
			}
			return flag;
		},
		url : base + '/reimburse/save',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				$('#reimburse_save_form').form('clear');
				$('#reimburseTab'+h).datagrid('reload');
				$('#indexdb').datagrid('reload');
				closeFirstWindow();
				closeWindow();
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}	
</script>