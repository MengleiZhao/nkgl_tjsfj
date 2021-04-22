<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同编号</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fcCode" name="fcCode" readonly="readonly"  data-options="validType:'length[1,32]'" style="width: 555px" value="${bean.fcCode}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同名称</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fcTitle" name="fcTitle" readonly="readonly"  data-options="validType:'length[1,50]'" style="width: 555px" value="${bean.fcTitle}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同分类</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fcType" name="fcType" readonly="readonly"  data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=HTFL&selected=${bean.fcType}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid',
			onSelect:function (rec){
				if(rec.code=='HTFL-01'){
					$('.fcType').show();
				}else {
					$('.fcType').hide();
				}
			}
			" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;合同份数</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fcNum" name="fcNum" readonly="readonly" data-options="validType:'length[1,2]',precision:0" style="width: 200px" value="${bean.fcNum}"/>
		</td>
	</tr>
	<tr class="trbody fcType" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;采购项目</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fPurchName" name="fPurchName" readonly="readonly" data-options="prompt:'单击打开选取采购订单',validType:'length[1,50]'" style="width: 555px" value="${bean.fPurchName}"/>
		</td>
	</tr>
	<tr class="trbody fcType" hidden="hidden">
		<td class="td1"><span class="style1">*</span>&nbsp;签约方名称</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fContractor" name="fContractor" readonly="readonly" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/fContractorlookupsJson?&selected=${bean.fContractor}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"></td>
		<td class="td2"></td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;合同金额</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fcAmount" name="fcAmount" readonly="readonly" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${bean.fcAmount}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;大写金额</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fcAmountMax" name="fcAmountMax" readonly="readonly"   data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fcAmountMax}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fOperator" name="fOperator" readonly="readonly"   data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fOperator}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
		<td class="td2">
			<input class="easyui-textbox" id="F_fDeptName" name="fDeptName" readonly="readonly"   data-options="validType:'length[1,20]',editable:false" style="width: 200px" value="${bean.fDeptName}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;付款方式</td>
		<td class="td2">
			<input class="easyui-combobox" id="F_fPayType" name="fPayType.code" readonly="readonly" data-options="editable:false,panelHeight:'auto',url:'${base}/Formulation/lookupsJson?parentCode=FKFS&selected=${bean.fPayType.code}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'" style="width: 200px"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1">&nbsp;&nbsp;</td>
		<td class="td2" >
			<input class="easyui-textbox" id="F_payTypeRemark" hidden="hidden" data-options="hidden:true" name="payTypeRemark" value="${bean.payTypeRemark }" style="width: 200px" />
		</td>
	</tr>
	<tr style="height: 70px;">
		<td class="td1" valign="top">合同情况说明</td>
		<td colspan="4">
			<textarea class="textbox-text" readonly="readonly" id="F_fRemark" name="fRemark" oninput="textareaNum(this,'textareaNum1')" autocomplete="off"  style="width:555px;height:70px">${bean.fRemark}</textarea>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1">
			&nbsp;&nbsp;合同文本
			<input type="file" multiple="multiple" id="fhtnd" onchange="upladFile(this,'htnd','htgl01')" hidden="hidden">
			<input type="text" id="files" name="files" hidden="hidden">
		</td>
		<td colspan="4" id="tdf">
			<a onclick="$('#fhtnd').click()" style="font-weight: bold;" href="#">
				<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
				<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
				</div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
			</div>
			<c:forEach items="${formulationAttaList}" var="att">
				<c:if test="${att.serviceType=='htnd' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>
<script type="text/javascript">
$('#F_fPayType').combobox({
	onChange:function(newValue,oldValue){
		if(newValue=='FKFS-04'){
			$("#F_payTypeRemark").next().show();
		}else {
			$("#F_payTypeRemark").next().hide();
		}
	},
	onSelect:function (record){
		if(record.text=="--请选择--"){
			$("#F_payTypeRemark").next().hide();
		}
	}
});
</script>