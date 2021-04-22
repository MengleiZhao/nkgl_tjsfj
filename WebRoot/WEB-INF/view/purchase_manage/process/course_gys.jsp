<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;供应商名称</td>
		<td colspan="4">
			<input class="easyui-textbox" id="F_fwName"  name="fwName" readonly="readonly" style="width:555px;" data-options="" value="${fwbean.fwName}"/>
		</td>
	</tr>										
	
	<tr>
		<td class="td1">&nbsp;&nbsp;中标金额</td>
		<td class="td2">
			<input class="easyui-numberbox" id="F_fbidAmount" readonly="readonly" name="fbidAmount" data-options="icons: [{iconCls:'icon-yuan'}],precision:2" style="width: 200px" value="${br.fbidAmount}"/>
		</td>
		<td class="td4">
			<!-- 隐藏域 -->
			<input type="hidden" name="fwCode" id="F_fwCode" value="${fwbean.fwCode}"/><!--供应商编码  --> 
			<input type="hidden" name="fbId" id="F_fbId" value="${br.fbId}"/><!--过程登记的主键id  --> 
			<input type="hidden" name="fpId" id="F_fpId" value="${bean.fpId}"/><!--采购的主键id  -->
			<input type="hidden" name="fwId" id="F_fwId" value="${fwbean.fwId}"/><!--供应商的主键id  -->
			<input type="hidden" name="fbidStauts" id="F_fbidStauts" value="${bean.fbidStauts}"/><!--登记状态  -->
		</td>
		<td class="td1"><span class="style1">*</span>&nbsp;法人代表</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="F_fLegalRep"  name="fLegalRep" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${fwbean.fLegalRep}"/>
		</td>
	</tr>
	
	<tr>
		<td class="td1"><span class="style1">*</span>&nbsp;联系人</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="F_fwuserName"  name="fwuserName" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${fwbean.fwuserName}"/>
		</td>
		<td class="td4"></td>
		<td class="td1"><span class="style1">*</span>&nbsp;联系人电话</td>
		<td class="td2">
			<input class="easyui-textbox" type="text" id="F_fwTel"  name="fwTel" readonly="readonly" required="required" data-options="validType:'tel'" style="width: 200px" value="${fwbean.fwTel}"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1">&nbsp;&nbsp;办公地址</td>
		<td colspan="4">
			<input class="easyui-textbox" type="text" id="F_fwAddr"  name="fwAddr" readonly="readonly" data-options="validType:'length[1,200]'" style="width:555px;" value="${fwbean.fwAddr}"/>
		</td>
	</tr>
	
	<tr style="height: 78px;">
		<td class="td1" valign="top">&nbsp;&nbsp;备注</td>
		<td colspan="4">
			<input class="easyui-textbox" type="text" id="F_ftendFax"  name="ftendFax" readonly="readonly"  data-options="validType:'length[1,200]',multiline:true"   style="width:555px;height:70px;" value="${br.ftendFax}"/>
		</td>
	</tr>
	
	<%-- <tr class="trbody">
		<td class="td1">&nbsp;&nbsp;项目需求书</td>
		<td colspan="4">
		<c:if test="${!empty brAttac}">
		<c:forEach items="${brAttac}" var="att">
			<a href='${base}/attachment/download/${att.id}' target="blank" onclick="js_method()" style="color: #666666;font-weight: bold;">${att.originalName}</a></br>
		</c:forEach>
		</c:if>
		<c:if test="${empty brAttac}">
			<span style="color:#999999">暂未上传附件</span>
		</c:if>
	</tr> --%>
	
</table>