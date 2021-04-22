<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<script type="text/javascript">
//显示详细信息手风琴页面
$(document).ready(function() {
	
});

//保存
function saveRegisterAffirm(flowStauts){
	var realityDate = $('#realityDate').datebox('getValue');
	if(realityDate==''){
		alert('请填写实际来款日期！');
		return false;
	}
	//提交
	$('#register_affirm_form').form('submit', {
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
		url : base + '/registerAffirm/saveRegisterAffirm',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				$('#accountsAffirmTab').datagrid('reload');
				$('#indexdb').datagrid('reload');
				closeWindow();
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}

function addPro(){
	var win=creatFirstWin('选择项目',860,580,'icon-search','/accountsCurrent/proList'); 
	win.window('open');
}

function addCon(){
	var win=creatFirstWin('选择合同',860,580,'icon-search','/accountsRegister/registerConList'); 
	win.window('open');
}
</script>
<form id="register_affirm_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;"">
			<div class="win-left-top-div">
				<!-- 隐藏域 --> 
				<!-- 主键 ID --><input type="hidden" id="fMSId" name="fMSId" value="${bean.fMSId}" />
				<!-- 主键 ID --><input type="hidden" id="fAcaId" name="fAcaId" value="${bean.fAcaId}" />
				<!-- 合同 ID --><input type="hidden" id="conId" name="conId" value="${bean.conId}" />
				<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="applyflowStauts" />
				<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="applyStauts" />
				<!-- 申请状态 --><input type="hidden" name="rStauts" value="${bean.rStauts}" id="rStauts" />
				<!-- 申请状态 --><input type="hidden" name="aStauts" value="${bean.aStauts}" id="aStauts" />
				<!-- 申请状态 --><input type="hidden" name="userId" value="${bean.userId}" id="userId" />
				<!-- 申请状态 --><input type="hidden" name="deptId" value="${bean.deptId}" id="deptId" />
				<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
				<!-- 流程id  --><input type="hidden" id="flowId"  value="${fpId}"/>
				<!--历史审批节点  --><input type="hidden" id="historyNodes"  value="${historyNodes}"/>
				<!-- 下一级审批节点  --><input type="hidden" id="nextKey"  value="${bean.nCode}"/>
				<!-- 基本信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="基本信息" data-options="collapsed:false,collapsible:false"style="overflow:auto;">
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
						<tr class="trbody">
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 登记单号</td>
							<td class="td2" >
								<input class="easyui-textbox" id="registerCode" name="registerCode" readonly="readonly" value="${bean.registerCode}" style="width: 247px;height: 30px;margin-left: 10px " >
							</td>
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 登记时间</td>
							<td class="td2" >
								<input class="easyui-datebox" id="reqTime" name="reqTime" readonly="readonly" value="${bean.reqTime}" style="width: 247px;height: 30px;margin-left: 10px " >
							</td>
						</tr>
						<tr class="trbody">
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 登记部门</td>
							<td class="td2" >
								<input class="easyui-textbox" id="deptName" name="deptName" readonly="readonly" value="${bean.deptName}" style="width: 247px;height: 30px;margin-left: 10px " >
							</td>
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 登记人</td>
							<td class="td2" >
								<input class="easyui-textbox" id="userName" name="userName" readonly="readonly" value="${bean.userName}" style="width: 247px;height: 30px;margin-left: 10px " >
							</td>
						</tr>
						<tr class="trbody">
							<td class="td1"><span class="style1">*</span> 来款项目名称</td>
							<td colspan="3">
								<input class="easyui-textbox" style="width: 590px;height: 30px; " readonly="readonly" value="${bean.proName}" id="proName" name="proName" required="required" data-options="prompt:'请选择来款项目名称'"/>
								<input value="${bean.proCode}" id="proCode" name="proCode" hidden="hidden"/>
							</td>
						</tr>
						<tr class="trbody">
							<td class="td1" style="width: 70px;"><span class="style1">*</span> 实际来款日期</td>
							<td class="td2" >
								<input class="easyui-datebox" id="realityDate" name="realityDate" value="${bean.realityDate}" style="width: 247px;height: 30px;margin-left: 10px " data-options="editable:false" >
							</td>
							<td class="td1" style="width: 70px;"></td>
							<td class="td2" ></td>
						</tr>
					</table>
				</div>				
				</div>
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;height: 200px">
				<div title="来款明细" data-options="collapsed:false,collapsible:false"style="overflow:auto;">
					<div style="overflow:auto;margin-top: 20px;">
						<jsp:include page="accounts_register_lkmx.jsp" />
					</div>
					<table style="margin-top: 20px">
						<tr>
							<td class="td1" style="width:55px;text-align: right">
								附件
							</td>
							<td colspan="3" id="tdf">
								<c:forEach items="${attaList}" var="att">
									<div style="margin-top: 5px;">
									<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
									</div>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				</div>
				<c:if test="${operation!='add' }">
				<!-- 审批记录 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;height: 200px">
					<div title="审批记录" data-options="collapsed:false,collapsible:false"style="overflow:auto;">
						<c:if test="${type!=1 }">
							<jsp:include page="../../../check_history.jsp" />
						</c:if>												
					</div>
				</div>
				</c:if>
			</div>
			<div class="window-left-bottom-div" style="margin-top: 55px;">
				<a href="javascript:void(0)" onclick="saveRegisterAffirm(0)">
				<img src="${base}/resource-modality/${themenurl}/button/queren1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=支出管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<div class="window-right-div" style="width:254px;height:490px">
			<jsp:include page="../../../check_system.jsp" />
		</div>
	</div>
</form>
