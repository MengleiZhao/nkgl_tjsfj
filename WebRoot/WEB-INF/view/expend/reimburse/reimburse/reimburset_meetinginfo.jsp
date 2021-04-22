<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
	<div title="会议事项调整" data-options="collapsed:false,collapsible:false"style="overflow:auto;width: 717px">
		<table class="window-table" style="margin-top: 3px;" cellspacing="0" cellpadding="0">
			<tr class="trbody">
				<td class="td1" style="width: 80px;"><span class="style1">*</span>是否存在调整</td>
				<td class="td2" colspan="4" >
					<input type="radio" value="1" onclick="radioyes()" name="fupdateStatus" id="box1" <c:if test="${bean.fupdateStatus=='1'}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;是
					&nbsp;&nbsp;
					<input type="radio" value="0" onclick="radiono()" name="fupdateStatus" id="box2" <c:if test="${bean.fupdateStatus=='0'}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;否
				</td>
				<!-- <td class="td4" style="width: 67px;"></td> -->
			</tr>
			<tr id="radiofupdate" <c:if test="${bean.fupdateStatus=='0'}"> hidden="hidden" </c:if> class="trbody">
				<td class="td1" style="width: 80px;"><span class="style1">*</span>会议调整说明</td>
				<td colspan="3" class="td2" >
					<textarea name="fupdateReason"  id="fupdateReason" class="textbox-text"
							oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
							style="width:595px;height:70px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:8px; margin-bottom:0px;">${bean.fupdateReason }</textarea>
				</td>
			</tr>
		</table>
	</div>				
</div>

<form id="reimburse_save_form" method="post"  enctype="multipart/form-data">
	<!-- 隐藏域 --> 
	<!-- 主键ID --><input type="hidden" name="rId" value="${bean.rId}" id="rId"/>
	<!-- 报销单号 --><input type="hidden" name="rCode" value="${bean.rCode}"/>
	<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="reimburseFlowStauts" />
	<!-- 报销状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="reimburseStauts" />
	<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
	<!-- 流程id  --><input type="hidden" id="flowId"  value="${fpId}"/>
	<!-- 下一级审批节点  --><input type="hidden" id="nextKey"  value="${bean.nCode}"/>
	<!-- 报销类型 --><input type="hidden" id="reimburseTypeHi" value="${bean.type}" name="type"/>
	<!-- 报销类型 --><input type="hidden" id="reimburseAmount" name="amount" value="${bean.amount}"/>
	<!-- 预算指标名称 --><input type="hidden" id="indexName" name="indexName" value="${bean.indexName}" />
	<!-- 指标ID --><input type="hidden" name="indexId" value="${applyBean.indexId}" id=""/>
	<!-- 指标类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="indexType"/>
	<!-- 报销人 --><input type="hidden" name="userName" value="${bean.userName}" id="input_userName"/>
	<!-- 报销部门 --><input type="hidden" name=deptName value="${bean.deptName}" id="input_deptName"/>
	<!-- 会议是否调整 --><input hidden="hidden" id="fupdateStatusid" name="fupdateStatus" value="${bean.fupdateStatus}" />
	<!-- 会议调整说明 --><input hidden="hidden" id="fupdateReasonid" name="fupdateReason" value="${bean.fupdateReason}" />
	
	<div id="panelID" class="easyui-panel" data-options="closed:true">
	<!-- 项目支出明细ID --><input type="hidden" name="proDetailId" value="${bean.proDetailId}" id="proDetailId"/>
	<input type="hidden" id="json1" name="form1" />
	<!-- 收款人json -->
	<input type="hidden" id="payerinfoJson" name="payerinfoJson"/>
	<input type="hidden" id="payerinfoJsonExt" name="payerinfoJsonExt"/>
	<!-- 转账金额 -->
	<input type="hidden" id="payeeAmount"/>
	<!-- 应转账金额转账金额 -->
	<input type="hidden" id="skAmount"/>
	<!-- 冲销金额 -->
	<input id="cxAmounts" value="${bean.cxAmount}" name="cxAmount" hidden="hidden"  />
	<input id="input_jkdid" hidden="hidden" value="${bean.loan.lId}" name="loan.lId"/>
	<input id="input_jkdamonut" value="${bean.loan.lAmount}" hidden="hidden"  />
	<input id="withLoan" value="${bean.withLoan}" hidden="hidden" name="withLoan" />
	
	<input type="hidden" id="applyAmount" name="applyAmount" value="${applyBean.amount}" />
	<input type="hidden" id="meetPlanJson" name="meetPlan" />
	<input type="hidden" id="mingxiJson" name="mingxi"/>
	<!-- 附件信息 -->
	<input type="text" id="files" name="files" hidden="hidden">
	<input type="text" id="bxtzfilesId" name="bxtzfiles" hidden="hidden">
	<input type="text" id="bxqdbfilesId" name="bxqdbfiles" hidden="hidden">
	<!-- 会议信息ID --><input type="hidden" name="mId" value="${reimbMeetingBean.mId}" id="mId"/>
	<input type="hidden" id="fWAHotel" name="fWAHotel" value="${reimbMeetingBean.fWAHotel}" />
	<input type="hidden" id="fWAFood" name="fWAFood" value="${reimbMeetingBean.fWAFood}" />
	</div>
			
<!-- 基本信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
<div title="基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
	<table class="window-table" style="margin-top: 3px;width: 695px;padding-right: 5px;" cellspacing="0" cellpadding="0">
		<tr class="trbody">
			<td class="td1"><span class="style1">*</span>单据编号</td>
			<td colspan="3" class="td2" >
				<input style="width: 590px;height: 30px;margin-left: 10px" readonly="readonly" name="gCode" class="easyui-textbox"
				value="${bean.gCode}" data-options="required:true" readonly="readonly"/>
			</td>
		</tr>
		<tr class="trbody">
			<td class="td1"><span class="style1">*</span>摘要</td>
			<td colspan="3" class="td2" >
				<input style="width: 590px; height: 30px;margin-left: 10px" id="gName"  name="gName" class="easyui-textbox"
				value="${bean.gName}" />
			</td>
		</tr>
			<jsp:include page="reimburse_meetinginfo.jsp" />
			</table>
		</div>				
	</div>
</form>	

<!-- 调整后会议日程 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
	<div title="会议日程" data-options="collapsed:false,collapsible:false"style="overflow:auto;width: 717px">
		<div style="overflow:auto;margin-top: 0px;">
			<jsp:include page="reimburse_meeting_plan.jsp" />
		</div>
	</div>
</div>
<!-- 费用明细 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px">
	<div title="费用明细" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
		<div style="overflow:auto;">
		<div style="margin-top: 20px">
			<a style="margin-bottom: 5px;float: right;">原申请金额：<span style="color: #0000CD"  id="applyAmount_span"><fmt:formatNumber groupingUsed="true" value="${applyBean.amount}" minFractionDigits="2" maxFractionDigits="2"/></span></a>
		</div>
			<jsp:include page="reimburse-mingxi-meeting.jsp" />
			<a style="float: right;">报销金额总计：<span style="color: #D7414E"  id="reimbAmount_span"><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/></span>[元]</a>
		</div>
		<c:if test="${operation=='add'}">		
			<div style="overflow:hidden;margin-top: 0px">
				<!-- 发票明细 -->
				<jsp:include page="mingxi_meeting_invoice.jsp" />
			</div>
		</c:if>
		<c:if test="${operation=='edit'}">		
			<div style="overflow:hidden;margin-top: 0px">
				<!-- 发票明细 -->
				<jsp:include page="mingxi_meeting_invoice_edit.jsp" />
			</div>
		</c:if>
		
	</div>
</div>

<!-- 预算信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 50px">
<div title="预算信息" data-options="collapsed:false,collapsible:false"style="overflow:auto;margin-left: 0px;">				
	<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;margin-left: 0px;;margin-bottom: 0px;width: 695px;">					
		<tr class="trbody">
			<td style="width: 60px;"><span class="style1">*</span> 预算指标</td>
			<td colspan="3" style="padding-right: 5px;">
				<input class="easyui-textbox" style="width: 630px;height: 30px;"
				 value="${bean.indexName}" id="F_fBudgetIndexName"
				 readonly="readonly" required="required"/>
			</td>
		</tr>
	</table>	
	<table class="window-table-readonly-zc" cellspacing="0" cellpadding="0" style="margin-left:0px;;margin-bottom: 0px;width: 695px;height: 50px;">
		<tr>
			<td class="window-table-td1"><p style=" color:#0000CD;">申请金额:</p></td>
			<td class="window-table-td2"><p id="applyAmount_span_two">${applyBean.amount}[元]</p></td>
			<td class="window-table-td1"><p style="color: red;" >报销金额:</p></td>
			<td class="window-table-td2"><p id="p_amount">${bean.amount}[元]</p></td>
		</tr>
		<tr>
			<td class="window-table-td1"><p>归还预算:</p></td>
			<td class="window-table-td2"><p id="ghAmount">[元]</p></td>
		</tr>
		<tr >
			<td class="window-table-td1"><p>是否冲销借款</p></td>
			<td class="window-table-td2">
				<input id="hotelstd_add_sfwj" name="withLoans" value="1"
					type="radio" onclick="selectCxjk(this)" <c:if test="${bean.withLoan==1 }">checked="checked"</c:if>/>是
				<input id="hotelstd_add_sfwj" name="withLoans" value="0"
					type="radio" onclick="selectCxjk(this)" <c:if test="${bean.withLoan!=1 }">checked="checked"</c:if>/>否
			</td>	
		</tr>
		<tbody id="jk" >
			<tr>
				<td class="window-table-td1"><p>借款单号:</p></td>
				<td class="window-table-td2">
					<a href="#" onclick="chooseJkd()">
						<input class="easyui-textbox" id="input_jkdbh" style="width: 250px;height: 30px;" data-options="prompt: '借款单选择' ,icons: [{iconCls:'icon-sousuo'}]"
						value="${bean.loan.lCode}" readonly="readonly" >
						
					</a>
				</td>
			</tr>
			<tr>
				<td class="window-table-td1"><p>本次冲销金额:</p></td>
				<td class="window-table-td2"><p id="cxAmount">[元]</p></td>
				<td class="window-table-td1"><p>剩余报销金额:</p></td>
				<td class="window-table-td2"><p id="syAmount">[元]</p></td>
			</tr>
		</tbody>
		</table>			
	</div>
</div>

<!-- 收款人信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
	<div title="收款人信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;padding:0px;">
		<div id="" style="overflow-x:hidden;margin-top: 0px;">
			<jsp:include page="payee-info.jsp" />	
		</div>
		<div id="" style="overflow-x:hidden;margin-top: 20px;">
			<jsp:include page="payee-info-external.jsp" />	
		</div>
	</div>
</div>

<!-- 附件信息 -->
<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
<div title="附件信息" data-options="collapsed:false,collapsible:false"
	style="overflow:auto;">		
	<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
		<tr>
			<td class="td1" style="width:65px;text-align: right">
			<span class="style1">*</span> 会议请示:</td>
			<td colspan="3" id="tdf">
				<c:forEach items="${attaList}" var="att">
					<c:if test="${att.serviceType=='null'}">
						<div style="margin-top: 5px;">
							<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
					</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr>
		<td class="td1" style="width:65px;text-align: right"><span class="style1">*</span>通知:
			<input type="file" multiple="multiple" id="bxtz"
			onchange="upladFileMoreParams(this,'bxtz','zcgl01','bxtzprogressNumber','bxtzpercent','bxtztdf','bxtzfiles','bxtzprogid','bxtzfileUrl')" hidden="hidden"> <input
			type="text" id="bxtzfiles" name="bxtzfiles" hidden="hidden"></td>
		<td colspan="3" id="bxtztdf">&nbsp;&nbsp; <a onclick="$('#bxtz').click()"
			style="font-weight: bold;  " href="#"> <img
				style="vertical-align:bottom;margin-bottom: 5px;"
				src="${base}/resource-modality/${themenurl}/button/shangchuan1.png"
				onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
		</a>
			<div id="bxtzprogid"
				style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="bxtzprogressNumber"
					style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="bxtzpercent">0%</font>
			</div> <c:forEach items="${attaList1}" var="att">
			<c:if test="${att.serviceType=='bxtz' }">
				<div style="margin-top: 5px;">
					<a href='${base}/attachment/download/${att.id}'
						style="color: #666666;font-weight: bold;">${att.originalName}</a>
					&nbsp;&nbsp;&nbsp;&nbsp; <img style="margin-top: 5px;"
						src="${base}/resource-modality/${themenurl}/sccg.png">
					&nbsp;&nbsp;&nbsp;&nbsp; <a id="${att.id}" class="bxtzfileUrl" href="#"
						style="color:red" onclick="deleteAttac(this)">删除</a>
				</div>
			</c:if>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<td class="td1" style="width:65px;text-align: right"><span class="style1">*</span>签到表:
			<input type="file" multiple="multiple" id="bxqdb"
			onchange="upladFileMoreParams(this,'bxqdb','zcgl01','bxqdbprogressNumber','bxqdbpercent','bxqdbtdf','bxqdbfiles','bxqdbprogid','bxqdbfileUrl')" hidden="hidden"> <input
			type="text" id="bxqdbfiles" name="bxqdbfiles" hidden="hidden"></td>
		<td colspan="3" id="bxqdbtdf">&nbsp;&nbsp; <a onclick="$('#bxqdb').click()"
			style="font-weight: bold;  " href="#"> <img
				style="vertical-align:bottom;margin-bottom: 5px;"
				src="${base}/resource-modality/${themenurl}/button/shangchuan1.png"
				onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
		</a>
			<div id="bxqdbprogid"
				style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
				<div id="bxqdbprogressNumber"
					style="background:#3AF960;width:0px;height:10px"></div>
				文件上传中...&nbsp;&nbsp;<font id="bxqdbpercent">0%</font>
			</div> <c:forEach items="${attaList1}" var="att">
			<c:if test="${att.serviceType=='bxqdb' }">
				<div style="margin-top: 5px;">
					<a href='${base}/attachment/download/${att.id}'
						style="color: #666666;font-weight: bold;">${att.originalName}</a>
					&nbsp;&nbsp;&nbsp;&nbsp; <img style="margin-top: 5px;"
						src="${base}/resource-modality/${themenurl}/sccg.png">
					&nbsp;&nbsp;&nbsp;&nbsp; <a id="${att.id}" class="bxqdbfileUrl" href="#"
						style="color:red" onclick="deleteAttac(this)">删除</a>
				</div>
			</c:if>
			</c:forEach>
		</td>
	</tr>
	</table>
</div>
</div>
<script type="text/javascript">
var updateradio = 0;
var flag2 = 1;
function radiono(){
	var gId = '${applyBean.gId}';
	if(updateradio == 0){
		if(window.confirm('请确认是否重置调整数据！')){
			updateradio=0;
			refreshApply(2,gId);
		}else{
			$('#box1').prop("checked","checked");
		}
	}
}
function radioyes(){
	updateradio=0;
	$('#meetingNameId').textbox('readonly',false);
	$('#contentId').textbox('readonly',false);
	$('#meetingDateStart').datebox('readonly',false);
	$('#meetingDateEnd').datebox('readonly',false);
	$('#duration').numberbox('readonly',false);
	$('#rePeopNum').numberbox('readonly',false);
	$('#meetingType').combobox('readonly',false);
	$('#meetingPlaceId').textbox('readonly',false);
	$('#attendPeopleId').textbox('readonly',false);
	$('#attendNum').numberbox('readonly',false);
	$('#staffNum').numberbox('readonly',false);
	var input0 = $("#fWAHotelId").find("input[type='radio']");
	var input1 = $("#fWAFoodId").find("input[type='radio']");
	input0.removeAttr("disabled","");
	input1.removeAttr("disabled","");
	$("#fWAHotel").attr("disabled","disabled");
	$("#fWAFood").attr("disabled","disabled");
	$("#dmp1").css("display","block");
	$('#radiofupdate').show();
	$('#fupdateStatusid').val(1);
	flag2 = 0;
}
//接待人员表格添加删除，保存方法
var editIndex1 = undefined;
function endEditingR() {
	if (editIndex1 == undefined) {
		return true
	}
	if ($('#dg_meet_plan').datagrid('validateRow', editIndex1)) {
		var dmp = $('#dg_meet_plan').datagrid('getEditor', {
			index : editIndex1,
			field : 'costDetail'
		});
		$('#dg_meet_plan').datagrid('endEdit', editIndex1);
		editIndex1 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowR(index) {
	if (editIndex1 != index) {
		if (endEditingR()) {
			$('#dg_meet_plan').datagrid('selectRow', index).datagrid('beginEdit',
					index);
			editIndex1 = index;
		} else {
			$('#dg_meet_plan').datagrid('selectRow', editIndex1);
		}
	}
}
function appendR() {
	if (endEditingR()) {
		$('#dg_meet_plan').datagrid('appendRow', {});
		editIndex1 = $('#dg_meet_plan').datagrid('getRows').length - 1;
		$('#dg_meet_plan').datagrid('selectRow', editIndex1).datagrid('beginEdit',editIndex1);
	}
}
function removeitR() {
	if (editIndex1 == undefined) {
		return
	}
	$('#dg_meet_plan').datagrid('cancelEdit', editIndex1).datagrid('deleteRow',
			editIndex1);
	editIndex1 = undefined;
}
function acceptR() {
	if (endEditingR()) {
		$('#dg_meet_plan').datagrid('acceptChanges');
	}
}


function changeTime1(){
	var index=$('#dg_meet_plan').datagrid('getRowIndex',$('#dg_meet_plan').datagrid('getSelected'));
	var editors = $('#dg_meet_plan').datagrid('getEditors', index);  
	var endEditor = editors[1]; 
	var startday = Date.parse(new Date(editors[0].target.val()));
	var maxTime = Date.parse(new Date($("#meetingDateEnd").datetimebox('getValue')))+ (16 * 60 * 60 * 1000);
    var minTime = Date.parse(new Date($("#meetingDateStart").datetimebox('getValue')))- (8 * 60 * 60 * 1000);
	var endday = Date.parse(new Date(endEditor.target.val()));
	if(!isNaN(startday)){
    	if((startday>=minTime &&startday<=maxTime) ){
    		if(!isNaN(endday)){
	    		if(endday<startday){
	        		alert("结束日期不能小于开始日期！");
	        		editors[0].target.datetimebox('setValue', '');
	        	}
    		}
    	}else{
    		if(startday>maxTime || startday<minTime){
	    	alert("所选时间不在行程范围内请重新选择！");
    		editors[0].target.datetimebox('setValue', '');
    		}
    	}
    	
    } 
}


function changeTime2(){
	var index=$('#dg_meet_plan').datagrid('getRowIndex',$('#dg_meet_plan').datagrid('getSelected'));
	var editors = $('#dg_meet_plan').datagrid('getEditors', index);  
	var endEditor = editors[1]; 
	var startday = Date.parse(new Date(editors[0].target.val()));
	var maxTime = Date.parse(new Date($("#meetingDateEnd").datetimebox('getValue')))+ (16 * 60 * 60 * 1000);
    var minTime = Date.parse(new Date($("#meetingDateStart").datetimebox('getValue')))- (8 * 60 * 60 * 1000);
	var endday = Date.parse(new Date(endEditor.target.val()));
	if(!isNaN(endday)){
    	if((endday>=minTime &&endday<=maxTime) ){
    		if(!isNaN(startday)){
	    		if(endday<startday){
	        		alert("结束日期不能小于开始日期！");
	        		editors[1].target.datetimebox('setValue', '');
	        	}
    		}
    	}else{
    		if(endday>maxTime || endday<minTime){
	    	alert("所选时间不在行程范围内请重新选择！");
    		editors[1].target.datetimebox('setValue', '');
    		}
    	}
    	
    } 
}

</script>