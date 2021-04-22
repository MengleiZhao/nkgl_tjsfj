<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
			<form id="reimburse_save_form" method="post"  enctype="multipart/form-data">
				<!-- 隐藏域 --> 
				<!-- 主键ID --><input type="hidden" name="rId" value="${bean.rId}" id="rId"/>
				<!-- 主键ID --><input type="hidden" name="jId" <c:if test="${operation=='edit' }"> value="${receptionBeanEdit.jId}"</c:if> id="jId"/>
				<!-- 报销单号 --><input type="hidden" name="rCode" <c:if test="${operation=='add' }">value="${applyBean.gCode}"</c:if><c:if test="${operation=='edit' }"> value="${bean.rCode}"</c:if>/>
				<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="reimburseFlowStauts" />
				<!-- 报销状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="reimburseStauts" />
				<%-- <!-- 下环节处理人姓名 --><input type="hidden" name="userName2" value="${bean.userName2}" />
				<!-- 下环节处理人编码 --><input type="hidden" name="userId" value="${bean.userId}" /> --%>
				<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
				<!-- 报销类型 --><input type="hidden" id="reimburseTypeHi" value="${bean.type}" name="type"/>
				<!-- 报销金额 --><input type="hidden" id="reimburseAmount" name="amount" <c:if test="${operation=='edit' }">value="${bean.amount}"</c:if><c:if test="${operation=='add'}">value="${applyBean.amount}"</c:if> />
				<!-- 指标ID --><input type="hidden" name="indexId" value="${applyBean.indexId}" id=""/>
				<!-- 预算指标名称 --><input type="hidden" id="indexName" name="indexName" value="${bean.indexName}" />
				<!-- 指标类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="indexType"/>
				<div id="panelID" class="easyui-panel" data-options="closed:true">
				<!-- 项目支出明细ID --><input type="hidden" name="proDetailId" <c:if test="${operation=='add' }"> value="${applyBean.proDetailId}"</c:if><c:if test="${operation=='edit' }"> value="${bean.proDetailId}"</c:if> id="proDetailId"/>
				<!-- 费用明细json和费用金额 -->
				<input type="hidden" id="receptionHotelJson" name="hotelJson" />
				<input type="hidden" id="receptionFoodJson" name="foodJson" />
				<input type="hidden" id="receptionOtherJson" name="otherJson" />
				<!-- 接待人员json -->
				<input type="hidden" id="recePeopJson" name="recePeop" />
				<!-- 主要行程json -->
				<input type="hidden" id="receptionStorkPlanJson" name="storkJson" />
				<!-- 收费 明细json -->
				<input type="hidden" id="receptionChargePlanJson" name="chargeJson" />
				
				<input type="hidden" id="costTraffic" name="costTraffic"  value="${receptionBeanEdit.costTraffic}"/>
				<input type="hidden" id="costRent" name="costRent"  value="${receptionBeanEdit.costRent}"/>
				<input type="hidden" id="costHotel" name="costHotel" value="${receptionBeanEdit.costHotel}"  />
				<input type="hidden" id="costFood" name="costFood" value="${receptionBeanEdit.costFood}"  />
				<input type="hidden" id="costOther" name="costOther" value="${receptionBeanEdit.costOther}"  />
				<input type="hidden" id="applyAmount" name="applyAmount" value="${applyBean.amount}" />
				<input id="input_jkdid" hidden="hidden" value="${bean.loan.lId}" name="loan.lId"/>
				<input id="input_jkdamonut" value="${bean.loan.lAmount}" hidden="hidden"  />
				<!-- 冲销金额 -->
				<input id="cxAmounts" value="${bean.cxAmount}" name="cxAmount" hidden="hidden"  />
				<input id="withLoan" value="${bean.withLoan}" name="withLoan" hidden="hidden"  />
				<!-- 转账金额 -->
				<input type="hidden" id="payeeAmount"/>
				<!-- 应转账金额转账金额 -->
				<input type="hidden" id="skAmount"/>
				<!-- 发票明细json -->
				<input type="hidden" id="json1" name="form1"/>
				<input type="hidden" id="json2" name="form2"/>
				<input type="hidden" id="json3" name="form3"/>
				<input type="hidden" id="json4" name="form4"/>
				<input type="hidden" id="json5" name="form5"/>
				<!-- 收款人json -->
				<input type="hidden" id="payerinfoJson" name="payerinfoJson"/>
				<input type="hidden" id="payerinfoJsonExt" name="payerinfoJsonExt"/>
				<!-- 附件信息 -->
				<input type="text" id="files" name="files" hidden="hidden">
				<!-- 接待天数 -->
				<input type="hidden" id="reDayNum"name="reDayNum" value="${receptionBean.reDayNum}" />
				</div>
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
					<div title="公务接待调整" data-options="collapsed:false,collapsible:false"style="overflow:auto;width: 717px">
						<table class="window-table" style="margin-top: 3px;" cellspacing="0" cellpadding="0">
							<tr class="trbody">
								<td class="td1"style="width: 80px;"><span class="style1">*</span>是否存在调整</td>
								<td class="td2" colspan="3" >
									<input type="radio" value="1" onclick="radioyes()" name="fupdateStatus" id="box3" <c:if test="${bean.fupdateStatus=='1'}">checked="checked" </c:if> style="vertical-align: middle;"/>&nbsp;&nbsp;是
									&nbsp;&nbsp;
									<input type="radio" value="0" onclick="radiono()" name="fupdateStatus" id="box4" <c:if test="${bean.fupdateStatus=='0'}">checked="checked" </c:if>  style="vertical-align: middle;"/>&nbsp;&nbsp;否
								</td>
								<!-- <td class="td4" style="width: 67px;"></td> -->
							</tr>
							<tr id="radiofupdate" hidden="hidden" class="trbody">
								<td class="td1"  style="width: 80px;"><span class="style1">*</span>调整说明</td>
								<td colspan="3" class="td2" >
									<textarea name="fupdateReason"  id="fupdateReason" class="textbox-text"
											oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
											style="width:595px;height:70px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:8px; margin-bottom:0px;">${bean.fupdateReason }</textarea>
								</td>
							</tr>
						</table>
					</div>				
				</div>
				<!-- 基本信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="基本信息" data-options="collapsible:false" style="overflow:auto;width: 707px">
					<table class="window-table" style="margin-top: 10px;width: 695px" cellspacing="0" cellpadding="0">
						<tr class="trbody">
							<td class="td1"><span class="style1">*</span> 单据编号</td>
							<td colspan="4">
								<input style="width: 587px;height: 30px;" name="gCode" class="easyui-textbox"
								<c:if test="${operation=='add' }">value="${applyBean.gCode}"</c:if><c:if test="${operation=='edit' }">value="${bean.gCode}"</c:if>
								 data-options="prompt: '事前申请选择' ,icons: [{iconCls:'icon-sousuo'}],required:true" readonly="readonly"/>
							</td>
						</tr>
						<tr class="trbody">
							<td class="td1"><span class="style1">*</span> 摘要</td>
							<td colspan="4">
								<input style="width: 587px; height: 30px;" name="gName" class="easyui-textbox"
								value="${bean.gName}" />
							</td>
						</tr>
							<!-- 公务接待信息 -->
							<jsp:include page="reception.jsp" />
						</table>
					</div>				
				</div>
			</form>		
			<!-- 接待对象 -->
			<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
				<div title="接待对象" data-options="collapsible:false" style="overflow:auto;">
					<div style="overflow:auto;margin-top: 0px">
						<!--  公务接待申请  接待对象 -->
						<jsp:include page="reception_people.jsp" />
					</div>
				</div>
			</div>
			<!-- 主要形成安排 -->
			<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
				<div title="主要行程安排" data-options="collapsible:false" style="overflow:auto;">
					<div style="overflow:auto;margin-top: 0px">
						<!--  公务接待申请  主要行程安排 -->
						<jsp:include page="reception_strok_plan.jsp" />
					</div>
				</div>
			</div>
			<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="费用明细" data-options="collapsible:false" style="overflow:auto;width: 717px">	
					<div style="overflow:hidden;margin-top: 0px">
						<!-- 费用明细 -->
						<jsp:include page="mingxi_reception.jsp" />
					</div>
					<div style="overflow:auto;margin-top: 10px;">
							<span style="float: right;">
								<span style="color: red;">报销金额总计： </span>
								<c:if test="${operation=='add'}">
								<span style="float: right;"  id="reimAmount_span" ><fmt:formatNumber groupingUsed="true" value="${applyBean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
								</c:if>
								<c:if test="${operation=='edit'}">
								<span style="float: right;"  id="reimAmount_span" ><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
								</c:if>
							</span>
						</div>
				</div>
			</div>
			<!-- 收费明细 -->
			<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
				<div title="收费明细" data-options="collapsible:false" style="overflow:auto;">
					<div style="overflow:auto;margin-top: 0px">
						<!--  公务接待申请  收费明细 -->
						<jsp:include page="reception_charge_plan.jsp" />
					</div>
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
						<td class="window-table-td2"><p id="applyAmount_span">${applyBean.amount}[元]</p></td>
						<td class="window-table-td1"><p style="color: red;" >报销金额:</p></td>
				
						<c:if test="${operation=='add'}">	
						<td class="window-table-td2"><p id="p_amount"><fmt:formatNumber groupingUsed="true" value="${applyBean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</p></td>
						</c:if>
						<c:if test="${operation=='edit'}">	
						<td class="window-table-td2"><p id="p_amount"><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</p></td>
						</c:if>
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
				
				<tbody id="jk">
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
								<td class="window-table-td1"><p>剩余应还:</p></td>
								<td class="window-table-td2"><p id="syAmount">[元]</p></td>
							</tr>
						</tbody>
					</table>			
				</div>
			</div>
				
				
				<!-- 收款人信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="收款人信息" data-options="collapsible:false" style="overflow:auto;width: 717px">
					<div id="" style="overflow-x:hidden;">
						<jsp:include page="payee-info.jsp" />	
					</div>
					<div id="" style="overflow-x:hidden;margin-top: 20px;">
						<jsp:include page="payee-info-external.jsp" />	
					</div>
					<input hidden="hidden" id="num2" name="payeeAmount" value="${payee.payeeAmount}" readonly="readonly" precision="2"/>
				</div>
				</div>
				
				<!-- 附件信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
				<div title="附件信息" data-options="collapsed:false,collapsible:false"
					style="overflow:auto;">		
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
						<tr>
							<td class="td1" style="width:81px;text-align: right"><span class="style1">*</span>公务接待方案:</td>
							<td colspan="3" id="gwjdfatdf">
								<c:forEach items="${attaList}" var="att">
								<c:if test="${att.serviceType=='gwjdfa' }">
									<div>
										<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
									</div>
								</c:if>
								</c:forEach>
							</td>
						</tr>	
						<tr>
							<td class="td1" style="width:65px;text-align: right"><span class="style1">*</span>公函:</td>
							<td colspan="3" id="gwjdghtdf">
								<c:forEach items="${attaList}" var="att">
								<c:if test="${att.serviceType=='gwjdgh' }">
									<div>
										<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
									</div>
								</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td style="width:75px;text-align: left"> 附件
								<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'bxsq','zcgl01')" hidden="hidden">
							</td>
							<td colspan="3" id="tdf">
								&nbsp;&nbsp;
								<a onclick="$('#f').click()" style="font-weight: bold;  " href="#">
									<img style="vertical-align:bottom;margin-bottom: 5px;" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
								</a>
								<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
									<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
									 </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
								</div>
								<c:forEach items="${attaList1}" var="att">
									<div style="margin-top: 5px;">
									<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<img style="margin-top: 5px;" src="${base}/resource-modality/${themenurl}/sccg.png">
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
									</div>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				</div>

<script type="text/javascript">

var updateradio = 0;
function radiono(){
	updateradio=1;
	var gId = '${applyBean.gId}';
	refreshApply(5,gId);
}

function radioyes(){
	updateradio=0;
	$('#receptionObject').textbox('readonly',false);
	$('#receptionLevel').combobox('readonly',false);
	$('#rePeopNum').numberbox('readonly',false);
	$('#reDateStart').datebox('readonly',false);
	$('#reDateEnd').datebox('readonly',false);
	$('#diningPlace').textbox('readonly',false);
	$('#fFeteTime').datebox('readonly',false);
	$('#unitFeteSite').textbox('readonly',false);
	$('#receptionContent').textbox('readonly',false);
	$('#unitFeteNum').numberbox('readonly',false);
	$('#attendPeopNum').numberbox('readonly',false);
	$('#box1').removeAttr("disabled");//去除input元素的disabled属性
	$('#box2').removeAttr("disabled");//去除input元素的disabled属性
	$('#receptionContent').removeAttr("disabled");//去除input元素的disabled属性
	$('#removeit2Id').show();
	$('#append2Id').show();
	$('#radiofupdate').show();
	$('#r_rpp').show();
	$('#r_rsp').show();
	//$('#r_rcp').show();
	$('#fupdateStatusid').val(1);
}
//冲销借款
function cx(){
	var num1=parseFloat($('#input_jkdamonut').val());
	var num2=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	if(isNaN(num1)&&!isNaN(num2)){
		 $('#skAmount').val('setValue',num2);
	}
	if(!isNaN(num1)&&!isNaN(num2)){
		if(num2<num1){
			var num4=num1-num2;
			 $('#cxAmount').html(fomatMoney(num2,2)+" [元]");
			 $('#cxAmounts').val(num2.toFixed(2));
			 $('#syAmount').html(fomatMoney(num4,2)+" [元]");
			 $('#skAmount').numberbox('setValue',0);
		}else{
			$('#cxAmount').html(fomatMoney(num1,2)+" [元]");
			$('#cxAmounts').val(num1.toFixed(2));
			$('#syAmount').html(fomatMoney(0,2)+" [元]");
			$('#skAmount').numberbox('setValue',(num2-num1).toFixed(2));
		}
	}
	if(!isNaN(num2)){
		if(num2<num3){
			var num5=num3-num2;
			$('#ghAmount').html(fomatMoney(num5,2)+" [元]");
		}else{
			$('#ghAmount').html(fomatMoney(0,2)+" [元]");
		}
	}
}

function jk(){
	
	var cxjk = $('input[name="withLoans"]:checked').val();
	var num2=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	var num6=parseFloat($('#cxAmounts').val());
	if(cxjk==1){
	var num1=parseFloat($('#input_jkdamonut').val());
	if(!isNaN(num1)&&!isNaN(num2)){
		if(num2<num1){
			var num4=num1-num2;
			 $('#cxAmount').html(fomatMoney(num2,2)+" [元]");
			 $('#cxAmounts').val(num2.toFixed(2));
			 $('#syAmount').html(fomatMoney(num4,2)+" [元]");
		}else{
			$('#cxAmount').html(fomatMoney(num1,2)+" [元]");
			$('#cxAmounts').val(num1.toFixed(2));
			$('#syAmount').html((num1-num6)+" [元]");
		}
	}
	}
	if(!isNaN(num2)){
		if(num2<num3){
			var num5=num3-num2;
			$('#ghAmount').html(fomatMoney(num5,2)+" [元]");
		}else{
			$('#ghAmount').html(fomatMoney(0,2)+" [元]");
		}
	}
}

function onSelect2(date) {
	endday2 = date;
	startday2 = new Date(startday2);
	var d = (endday2 - startday2) / 86400000 + 1;
	if (d > 0) {
		$('#duration').numberbox("setValue", d);
	} else {
		$('#duration').numberbox("setValue", "");
	}
}
//显示详细信息手风琴页面
$(document).ready(function() {
	jk();
	//是否显示 冲销借款信息
	var cxjk = $('input[name="withLoans"]:checked').val();
	if(cxjk==1){
		$('#jk').show();
	} else {
		$('#jk').hide();
		$('#input_jkdamonut').val(0);
	}
	//是否安排住宿
	var stayYN = $("#reimstayYN").val();
	if(stayYN=='1'){
    	$('#rec-hotel-div-reim').show();
	}else{
		$('#rec-hotel-div-reim').hide();
	}
	if('${bean.fupdateStatus}'=='1'){
		$('#r_rpp').show();
		$('#r_rsp').show();
		$('#r_rcp').show();
	}else{
		$('#r_rpp').hide();
		$('#r_rsp').hide();
		$('#r_rcp').hide();
	}
	//设置申请金额
	var applyAmount = $("#applyAmount").val();
	if(applyAmount !=""){
		$('#applyAmount_span').html(fomatMoney(applyAmount,2)+" [元]");
	}
	var sts = $('input[name="fupdateStatus"]:checked').val();
	if(sts==1){
		updateradio=0;
		$('#radiofupdate').show();
		$('#removeit2Id').show();
		$('#append2Id').show();
	} else {
		$('#radiofupdate').hide();
		$('#removeit2Id').hide();
		$('#append2Id').hide();
		updateradio=1;
	}
	var diningPlacePlan1 = '${receptionBeanEdit.diningPlacePlan1}';
	if(diningPlacePlan1=='1'){
		$('#tr1').show();
	}
	var diningPlacePlan2 = '${receptionBeanEdit.diningPlacePlan2}';
	if(diningPlacePlan2=='1'){
		$('#tr0').show();
	}
	
	var h = $("#reimburseTypeHi").textbox().textbox('getValue');
	if (h != "") {
		$('#reimburseType').textbox().textbox('setValue', h);
		$('#reimburseType').textbox().attr('readonly', true);
	}
	zzAmount();
	$("#input_jkdbh").textbox({
		onChange: function(newValue, oldValue) {
			cx();
		}
	});
	
});

function selectCxjk(){
	var cxjk = $('input[name="withLoans"]:checked').val();
	if(cxjk==1){
		$('#jk').show();
		$('#withLoan').val(1);
	} else {
		$('#jk').hide();
		$('#input_jkdamonut').val(0);
		$('#withLoan').val(0);
		cx();
	}
}

//保存
function saveReimburse(flowStauts) {
	var charge = $('#r_reception_people_plan').datagrid('getRows');
	 for (var i = 0; i < charge.length; i++) {
		if(charge[i].receptionPeopName != '' || charge[i].government != '' || charge[i].position != ''){
		if(charge[i].receptionPeopName == ''){
			alert('接待对象未填写完整！');
			return ;
		}
		if(charge[i].government == ''){
			alert('接待对象未填写完整！');
			return ;
		}
		if(charge[i].position == ''){
			alert('接待对象未填写完整！');
			return ;
		}
		}
	}
	 if($('#box3').is(":checked")){
			var aa = $('#fupdateReason').val();
			if(aa == '' || aa==null || aa==undefined){
				alert('请填写调整说明');
				return ;
			}
		}
	var strok = $('#r_reception_strok_plan').datagrid('getRows');
	 for (var i = 0; i < strok.length; i++) {
		if(strok[i].fPro != '' || strok[i].fTime != '' || strok[i].fAddress != ''){
		if(strok[i].fPro == ''){
			alert('主要行程安排未填写完整！');
			return ;
		}
		if(strok[i].fTime == ''){
			alert('主要行程安排未填写完整！');
			return ;
		}
		if(strok[i].fAddress == ''){
			alert('主要行程安排未填写完整！');
			return ;
		}
		}
	} 
	var hoteldg = $('#rec-hotel-dg-reim').datagrid('getRows');
	 for (var i = 0; i < hoteldg.length; i++) {
		if(hoteldg[i].fName != '' || hoteldg[i].position != '' || hoteldg[i].fRoomType != '' ||hoteldg[i].fDays != ''||hoteldg[i].fCostHotel != ''|| hoteldg[i].fCostHotel != null){
		if(hoteldg[i].fName == ''){
			alert('住宿费未填写完整！');
			return ;
		}
		if(hoteldg[i].position == ''){
			alert('住宿费未填写完整！');
			return ;
		}
		if(hoteldg[i].fRoomType == ''){
			alert('住宿费未填写完整！');
			return ;
		}
		if(hoteldg[i].fDays == ''){
			alert('住宿费未填写完整！');
			return ;
		}
		if(hoteldg[i].fCostHotel == '' || hoteldg[i].fCostHotel == null){
			alert('住宿费未填写完整！');
			return ;
		}
		}
	} 
	var otherdg = $('#rec-other-dg-reim').datagrid('getRows');
	for (var i = 0; i < otherdg.length; i++) {
	if(otherdg[i].fCostName != '' || otherdg[i].fCost != null || otherdg[i].fCost != '' || otherdg[i].fRemark != ''){
	if(otherdg[i].fCostName == ''){
		alert('其他费用未填写完整！');
		return ;
	}
	if(otherdg[i].fCost == null){
		alert('其他费用未填写完整！');
		return ;
	}
	if(otherdg[i].fRemark == '' || otherdg[i].fCost == ''){
		alert('其他费用未填写完整！');
		return ;
	}
	}
} 
	$('#indexName').val($('#F_fBudgetIndexName').textbox('getValue'));//预算指标名称
	$('#fupdateReasonid').val($('#fupdateReason').val());//接待调整说明
	if($('#receptionContent').val()==''){
		alert('请注意填写接待内容！');
		return ;
	}
	
	//附件的路径地址
	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#files").val(s);
	var nums=parseFloat($('#reimburseAmount').val());//报销金额
	var num3=parseFloat($('#applyAmount').val());//申请金额
	var payeeAmount=parseFloat($('#payeeAmount').val());//转账金额
	var cxAmount=parseFloat($('#cxAmounts').val());//冲销金额
	var skAmount=parseFloat((nums-cxAmount).toFixed(2));//报销金额-冲销金额
	var applyAmount1 = (nums-num3).toFixed(2);
	if(isNaN(payeeAmount) || payeeAmount=='' || payeeAmount==undefined){
		payeeAmount =0;
	}
	if(isNaN(skAmount) || skAmount=='' || skAmount==undefined){
		skAmount =0;
	}
	//下面几行判断是否有冲销借款
	var cxjk = $('input[name="withLoans"]:checked').val();
	if(cxjk==1){//如果有冲销借款的
		var lCode = $("#input_jkdbh").textbox('getValue');
		if(lCode==''){
			alert('请选择借款单！');
			return false;
		}else if(skAmount!=payeeAmount){
			var info = '报销金额：'+nums+'\n冲销金额：'+cxAmount+'\n转账总金额：'+payeeAmount+'\n冲销后的报销金额和转账金额不一致,请核对后在提交！';
			alert(info);
			return false;
		}
	}else if(cxjk==0){
		//没有冲销借款的
		if(nums!=payeeAmount){
			var info = '报销金额：'+nums+'\n转账总金额：'+payeeAmount+'\n报销金额和转账金额不一致,请核对后在提交！';
			alert(info);
			return false;
		}
	}
	//判断已上传发票的发票金额是否填写
	for(var i=0;i<$('input[name=fileId]').length;i++){
		if($('input[name=fileId]')[i].value != null && $('input[name=fileId]')[i].value != ''){
			if($('input[name=amount]')[i].value == null || $('input[name=amount]')[i].value == ''){
				alert("请填写已上传发票的金额");
				return false;
			}
		}
	}
	
	
	//设置审批状态
	$('#reimburseFlowStauts').val(flowStauts);
	var h = $('#reimburseTypeHi').val();
	/* 发票json */
	var jsonStr1 = $("#form2").serializeJson();
	var jsonStr2 = $("#formFood").serializeJson();
	var jsonStr3 = $("#form1").serializeJson();
	var jsonStr4 = $("#formCostRent").serializeJson();
	var jsonStr5 = $("#formOther").serializeJson();
	// 在后台反序列话成明细Json的对象集合
	$('#json1').val(jsonStr1);
	$('#json2').val(jsonStr2);
	$('#json3').val(jsonStr3);
	$('#json4').val(jsonStr4);
	$('#json5').val(jsonStr5);
	//设置报销状态
	$('#reimburseStauts').val(flowStauts);
	/* 以下是住宿费、餐费、其他费用的json */
	receptionHotelJson();
	receptionFoodJson();
	receptionOtherJson();
	
	receptionPeopleJson();
	receptionStrokJson();
	receptionStrokJson();
	//收款人json
	getpayerinfoJson();
	getpayerinfoJsonExt();
	if(parseFloat(nums)>parseFloat(num3)&&flowStauts==1){
		/* var rows = $('#rmxdg').datagrid('getRows');
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].standard!="据实列支"&&rows[i].standard!=null){
				if(parseFloat(rows[i].reimbSum)>parseFloat(rows[i].standard)){
					alert("报销金额不能超过支出标准！")
					return;
				}
			}
		} */
		var win=creatFirstWin(' ',360,280,'icon-search','/reimburse/overfulfil?standard='+num3+"&amount="+nums+"&applyAmount="+applyAmount1+"&sts="+flowStauts);
		win.window('open');
	}else{
		//提交
		$('#reimburse_save_form').form('submit', {
			onSubmit : function() {
				/* flag = $(this).form('enableValidation').form('validate');
				if (flag) { */
					$.messager.progress();
					//如果校验通过，则进行下一步
				/* }else{
					//校验不通过，就打开第一个校验失败的手风琴
					openAccordion();
				}
				return flag; */
			},
			url : base + '/reimburse/save',
			success : function(data) {
				/* if (flag) { */
					$.messager.progress('close');
				/* } */
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					closeWindow();
					$('#reimburse_save_form').form('clear');
					$('#reimburseTab'+h).datagrid('reload');
					$('#indexdb').datagrid('reload');
					
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
		}
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


//打开借款单选择页面
function chooseJkd(){
	var win = creatFirstWin('借款单选择', 840, 450, 'icon-search', '/loan/choose?menuType=fromBxsq');
	win.window('open');
	cx();
}
//转账金额
function  zzAmount(){
	var num1=parseFloat($('#input_jkdamonut').val());
	var num2=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	if(isNaN(num1)){
		num1=0;
	}
	if(isNaN(num2)){
		num2=0;
	}
	if(isNaN(num3)){
		num3=0;
	}
	$("#skAmount").numberbox().numberbox('setValue',num1+num2);
}	
</script>