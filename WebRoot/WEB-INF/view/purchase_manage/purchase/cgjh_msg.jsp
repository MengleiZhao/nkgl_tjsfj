<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable">
	<!-- 表单样式参考 -->
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目编号</td>
		<td class="td2">
			<input class="easyui-textbox" id=" " readonly="readonly" style="width:200px;" value="${bean.fpCode}"/>
		</td>
		<td class="td4">
			<!-- 可用金额  --><input type="hidden" id="syAmount" value="${bean.syAmount}"/>
			<!-- 批复金额  --><input type="hidden" id="pfAmount" value="${bean.pfAmount}"/>
			<!-- 批复时间  --><input type="hidden" id="pfDate"  value="${bean.pfDate}"/>
		</td>
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
		<td class="td2">
			<input id=" " class="easyui-textbox" type="text" required="required" readonly="readonly" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fpName}"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id=" " readonly="readonly" required="required" name=" " data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fDeptName}"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
		<td class="td2">
			<input id=" " class="easyui-textbox" type="text" readonly="readonly" required="required" name=" " data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fUserName}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;品目名称</td>
		<td class="td2">
			<input class="easyui-combobox" id=" " name=" " readonly="readonly" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PMMC&selected=${bean.fpItemsName}',method:'get',valueField:'code',textField:'text',editable:false"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>&nbsp;申请时间</td>
		<td class="td2">
			<input class="easyui-datebox" class="dfinput" id=" " name=" " readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fReqTime}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;进口产品</td>
		<td class="td2" colspan="4">
			<input type="radio"  disabled="disabled" name="fIsImp" <c:if test="${bean.fIsImp=='1'}">checked="checked"</c:if> value="1">是</input>
			<input type="radio"  disabled="disabled" name="fIsImp" <c:if test="${bean.fIsImp!='1'}">checked="checked"</c:if> value="0">否</input>
		</td>
	</tr>
	<c:if test="${bean.fIsImp=='1'}">
	<tr class="trbody" id="jkzjyjTr">
		<td class="td1">
			&nbsp;&nbsp;进口产品专家意见
		</td>
		<td colspan="4" id="tdf0">
			<c:forEach items="${attac}" var="att0">
				<c:if test="${att0.serviceType=='JKCPZJYJ' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att0.id}' style="color: #666666;font-weight: bold;">${att0.originalName}</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
	</c:if>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;预算指标</td>
		<td colspan="4">
			<a  href="#">
			<input class="easyui-textbox" style="width: 555px;height: 30px;"
			name="indexName" value="${bean.indexName}" id="F_indexName"
			data-options="prompt: '选择指标' ,icons: [{iconCls:'icon-sousuo'}]" readonly="readonly" required="required"/>
			</a>
		</td>
	</tr>	
</table>
						
<table class="window-table-readonly" cellspacing="0" cellpadding="0" style="margin-left: 100px;width: 560px;">
	<tr>
		<td class="window-table-td1"><p>批复金额：&nbsp;</p></td>
		<td class="window-table-td2"><p id="p_pfAmount">${bean.pfAmount}元</p></td>
		
		<td class="window-table-td1"><p>批复时间：&nbsp;</p></td>
		<td class="window-table-td2"><p id="p_pfDate">${bean.pfDate}</p></td>
	</tr>
	
	<tr>
		<td class="window-table-td1"><p>使用部门：&nbsp;</p></td>
		<td class="window-table-td2"><p id="p_pfDepartName">${bean.pfDepartName}</p></td>
		
		<td class="window-table-td1"><p>可用余额：&nbsp;</p></td>
		<td class="window-table-td2"><p id="p_syAmount">${bean.syAmount}元</p></td>
	</tr>
	<tr>
		<td class="window-table-td1"><p>资金渠道：&nbsp;</p></td>
		<td class="window-table-td2"><p id="p_pfIndexType">${bean.pfIndexType}</p></td>
		
		<td class="window-table-td1"><p></p></td>
		<td class="window-table-td2">
			<p id="">
				
			</p>
		</td>
	</tr>
</table>
	
<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 20px;">
	<tr>
		<td class="td1"><span class="style1">*</span>&nbsp;预算价格依据</td>
		<td colspan="4">
			<input class="easyui-combobox" id="" name="" readonly="readonly" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=YSJGYJ&selected=${bean.budgetPriceBasis}',method:'get',valueField:'code',textField:'text',editable:false"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1">
			&nbsp;&nbsp;附件
		</td>
		<td colspan="4" id="tdf">
			<c:forEach items="${attac}" var="att">
				<c:if test="${att.serviceType=='cggl' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>
<script type="text/javascript">
	$(document).ready(function() {
		//批复金额
		var pfAmount = $("#pfAmount").val();
		if(pfAmount !=""){
			$('#p_pfAmount').html(fomatMoney(pfAmount,2)+" [元]");
		}
		//可用金额
		var syAmount = $("#syAmount").val();
		if(syAmount !=""){
			$('#p_syAmount').html(fomatMoney(syAmount,2)+" [元]");
		}
		//批复时间
		var pfDate = $("#pfDate").val();
		if(pfDate !=""){
			$('#p_pfDate').html(ChangeDateFormat(pfDate));
		}
	});
</script>