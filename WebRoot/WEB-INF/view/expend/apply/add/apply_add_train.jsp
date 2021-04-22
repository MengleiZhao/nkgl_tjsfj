<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>


<script type="text/javascript">
function ChangeDateFormat1(val) {
	var t, y, m, d, h, i, s;
    if(val==null){
  	  return "";
    }
    t = new Date(val);
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
}
var flag1=0;
var flag2=0;
//显示详细信息手风琴页面
$(document).ready(function() {
	
	if('${bean.gId}'==''){
		flag1=0;
		flag2=0;
	}else{
		flag1=1;
		flag2=1;
		$("#addId").hide();
		$("#removeId").hide();
		$("#appendId").hide();
		$("#editId").show();
		$("#rph").show();
		$("#addId1").hide();
		$("#removeId1").hide();
		$("#appendId1").hide();
		$("#editId1").show();
	}
	//设置时间
	if($("#applyReqTime").val()==""||$("#applyReqTime").val()==null){
		var date = new Date();
		date=ChangeDateFormat1(date);
		$("#req_time").html(date);
		$("#applyReqTime").val(date);
	} else {
		var date = $("#applyReqTime").val();
		date=ChangeDateFormat1(date);
		$("#req_time").html(date);
	}
	
	//设置支出申请扩展信息
	var h = $("#applyTypeHi").val();
	if (h != "") {
		$('#applyType').val(h);
	}
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

	var applyAmount = $("#applyAmount").val();
	if(applyAmount !=""){
		$('#applyAmount_span').html(fomatMoney(applyAmount,2)+" [元]");
	}
});


//保存
function saveApply(flowStauts) {
	
	if(flag2==0){
		alert('请保存讲师信息');
		return ;
	}
	if(flag1==0){
		alert('请保存培训日程');
		return ;
	}
	
	var trainPlanRows = $('#dg_train_plan').datagrid('getRows');
	if(trainPlanRows.length<=0){
		alert('请填写培训日程');
		return ;
	}
	var trainLecturerRows = $('#dg_train_lecturer').datagrid('getRows');
	if(trainLecturerRows.length<=0){
		alert('请填写讲师信息');
		return ;
	}
	
	if($('#applyAmount_span').html()=='&nbsp;'||parseInt($('#applyAmount_span').html())<=0){
		alert('请注意填写费用明细金额！');
		return ;
	}
	// 在后台反序列化成明细Json的对象集合
	var h = $("#applyTypeHi").textbox().textbox('getValue');
	var type = '${type}';
	
	//附件的路径地址
	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	if(s==''){
		alert('请上传培训请示');	
		return
	}
	$("#files").val(s);
	//获取列表json
	getTrainLecturerJson();
	getTrainPlanJson();
	getHotelJson();
	getFoodJson();
	getLessonJson();
	getZongheJson();
	getTrafficJson1();
	getTrafficJson2();
	//设置审批状态
	$('#applyflowStauts').val(flowStauts);
	//设置申请状态
	$('#applyStauts').val(flowStauts);

	if($('#applyAmount').val()==""||$('#applyAmount').val()=="0.00") {
		alert('请填写费用明细申请金额');
		return;
	}
	
	//校验
	//预算指标
	var budgetIndexName = $('#F_fBudgetIndexName').val();
	if(budgetIndexName == ''){
		alert('请选择预算指标！');
		return;
	}
	//申请金额
	var applyAmount = $('#applyAmount').val();
	if(applyAmount == '' || applyAmount == '0.00') {
		alert('请填写费用明细申请金额！');
		return;
	}
	var zongherowAmount = 0.00;
	var zongheapplyAmount = 0.00;
	var zongherows = $('#mingxi-zonghe-dg').datagrid('getRows');
	for (var i = 0; i < zongherows.length; i++) {
		zongherowAmount = parseFloat(zongherows[i].totalStandard) + parseFloat(zongherowAmount);
		zongheapplyAmount = parseFloat(zongherows[i].applySum) + parseFloat(zongheapplyAmount);
	}
	if(zongheapplyAmount>zongherowAmount){
		alert('综合预算总额不得超过费用标准总额！');
		return;
	}
	
	var syAmount = $('#syAmount').val();
	if(flowStauts==1&&(parseFloat(syAmount)<parseFloat(applyAmount))){
		alert('预算可用金额不足,请重新选择预算指标！');
		return;
	}
	$("input[name='isFood']").attr("disabled",false);
	$("input[name='isHotel']").attr("disabled",false);
	//提交
	$('#apply_save_form').form('submit', {
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
		url : base + '/apply/save',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			} 
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				$('#applyTab'+h).datagrid('reload');
				$('#indexdb').datagrid('reload');
				closeWindow();
				
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}

//打开指标选择页面
function openIndex() {
	//var win=creatFirstWin('选择指标',860,580,'icon-search','/quota/choiceIndex');
	var win=creatFirstWin('选择指标',860,580,'icon-search','/apply/choiceIndex?menuType=beforeApply&type=3'); 
	win.window('open');
}


//明细只加载一遍(在改变表格内容时如果有url他会优先加载url而不会加载手写的内容，所以只加载一遍会使表格不去请求url中的内容)
var mingxinum=1;
$("#appli-detail-dg").datagrid({
	onBeforeLoad: function () {
		if(mingxinum != 1) {
			return false;
		} else {
			mingxinum = mingxinum + 1;
			return true;
		}
	}
});

function addTotalAmount(){
	
	var totalAmount=0;
	var num1=parseFloat($('#zongheMoney').val())
	if(!isNaN(num1)){
		totalAmount=totalAmount+num1
	}
	var num2=parseFloat($('#lessonsMoney').val())
	if(!isNaN(num2)){
		totalAmount=totalAmount+num2
	}
	var num3=parseFloat($('#hotelMoney').val())
	if(!isNaN(num3)){
		totalAmount=totalAmount+num3
	}
	var num4=parseFloat($('#foodMoney').val())
	if(!isNaN(num4)){
		totalAmount=totalAmount+num4
	}
	var num5=parseFloat($('#longTrafficMoney').val())
	if(!isNaN(num5)){
		totalAmount=totalAmount+num5
	}
	var num6=parseFloat($('#transportMoney').val())
	if(!isNaN(num6)){
		totalAmount=totalAmount+num6
	}
	$('#applyAmount').val(totalAmount.toFixed(2));
	$('#applyAmount_span').html(fomatMoney(totalAmount,2)+"[元]");
}

</script>

<form id="apply_save_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<!-- 隐藏域 --> 
				<!-- 主键ID --><input type="hidden" name="gId" value="${bean.gId}" />
				<!-- 申请单流水号 --><input type="hidden" name="gCode" value="${bean.gCode}" />
				<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="applyflowStauts" />
				<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="applyStauts" />
				<%-- <!-- 下环节处理人姓名 --><input type="hidden" name="userName2" value="${bean.userName2}" />
				<!-- 下环节处理人编码 --><input type="hidden" name="userId" value="${bean.userId}" /> --%>
				<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
				<!-- 申请类型 --><input type="hidden" id="applyTypeHi" value="${bean.type}" />
				<!-- 指标ID --><input type="hidden" name="indexId" value="${bean.indexId}" id="F_fBudgetIndexCode"/>
				<!-- 项目支出明细ID --><input type="hidden" name="proDetailId" value="${bean.proDetailId}" id="F_proDetailId"/>
				<!-- 指标类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="F_indexType"/>
				<!-- 申请时间  --><input type="hidden" id="applyReqTime" name="reqTime" value="${bean.reqTime}"/>
				<!-- 申请事项  --><input type="hidden" id="applyType" name="type" value="${bean.type}"/>
				<!-- 申请总额  --><input type="hidden" id="applyAmount" name="amount" value="${bean.amount}"/>
				<!-- 可用金额  --><input type="hidden" id="syAmount" value="${bean.syAmount}"/>
				<!-- 批复金额  --><input type="hidden" id="pfAmount" value="${bean.pfAmount}"/>
				<!-- 批复时间  --><input type="hidden" id="pfDate"  value="${bean.pfDate}"/>
				<!-- 流程id  --><input type="hidden" id="flowId"  value="${fpId}"/>
				<!--历史审批节点  --><input type="hidden" id="historyNodes"  value="${historyNodes}"/>
				<!-- 下一级审批节点  --><input type="hidden" id="nextKey"  value="${bean.nCode}"/>
				<!-- 综合预算申请金额  --><input type="hidden" id="zongheMoney" name="zongheMoney"  value="${trainingBean.zongheMoney}"/>
				<!-- 讲课费申请金额  --><input type="hidden" id="lessonsMoney" name="lessonsMoney" value="${trainingBean.lessonsMoney}"/>
				<!-- 住宿费申请金额  --><input type="hidden" id="hotelMoney" name="hotelMoney" value="${trainingBean.hotelMoney}"/>
				<!-- 伙食费申请金额  --><input type="hidden" id="foodMoney"  name="foodMoney" value="${trainingBean.foodMoney}"/>
				<!-- 城市间交通费申请金额  --><input type="hidden" id="longTrafficMoney" name="longTrafficMoney"  value="${trainingBean.longTrafficMoney}"/>
				<!-- 市内交通费申请金额  --><input type="hidden" id="transportMoney" name="transportMoney" value="${trainingBean.transportMoney}"/>
				<!-- 基本信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="基本信息" data-options="collapsed:false,collapsible:false"
					style="overflow:auto">
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;width:707px;padding-left: 3px;">
						<tr class="trbody">
							<td class="td1"><span class="style1">*</span> 摘要</td>
							<td colspan="3">
								<c:if test="${operation=='add' }">
									<input class="easyui-textbox" style="width: 580px;height: 30px; " value="${draftAdd}" name="gName" required="required" data-options="validType:'length[1,50]'"/>
								</c:if>
								<c:if test="${operation!='add' }">
									<input class="easyui-textbox" style="width: 580px;height: 30px; " value="${bean.gName}" name="gName" required="required" data-options="validType:'length[1,50]'"/>
								</c:if>
							</td>
						</tr>
							<!-- 培训信息 -->
							<jsp:include page="training.jsp" />
					</table>
				</div>				
				</div>
				
				<!-- 讲师信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="讲师信息" data-options="collapsed:false,collapsible:false"
					style="overflow:auto;">
					<div style="overflow:auto;margin-top: 0px">
						<jsp:include page="train_lecturer.jsp" />
					</div>
				</div>
				</div>
				
				<!-- 培训日程 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
					<div title="培训日程" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;">
						<div style="overflow:auto;margin-top: 0px">
							<jsp:include page="train_plan.jsp" />
						</div>
					</div>
				</div>
				<!-- 费用明细 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 40px">
					<div title="费用明细" data-options="collapsible:false"
						style="overflow:auto">
							<!--  综合预算  明细 -->
						<div style="overflow:auto;margin-top: 20px">
							<jsp:include page="mingxi-train-zongheys.jsp" />
						</div>
							<!--  师资费-讲课费 -->
						<div style="overflow:auto;margin-top: 20px">
							<jsp:include page="mingxi-train-lessons.jsp" />
						</div>	
							<!--  师资费-住宿费 -->
						<div  style="overflow:auto;margin-top: 20px" >
							<jsp:include page="mingxi-train-hotel.jsp" />
						</div>	
							<!--  师资费-伙食费 -->
						<div  style="overflow:auto;margin-top: 20px" >
							<jsp:include page="mingxi-train-food.jsp" />
						</div>	
							<!--  师资费-城市间交通费 -->
						<div style="overflow:auto;margin-top: 20px">
							<jsp:include page="mingxi-train-trafficCityToCity.jsp" />
						</div>
							<!--  师资费-市内交通费 -->
						<div style="overflow:auto;margin-top: 20px">
							<jsp:include page="mingxi-train-trafficInCity.jsp" />
						</div>
							<div style="overflow:auto;margin-top: 10px;">
								<span style="float: right;">
									<span style="color: red;"  >申请金额总计： </span>
									<span style="float: right;"  id="applyAmount_span" >&nbsp;</span>
								</span>
							</div>
					</div>
				</div>
				
				<!-- 预算信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;margin-top: 0px">
				<div title="预算信息" data-options="collapsible:false" style="overflow:auto;margin-left: 0px;height: auto">				
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;margin-left: 0px;width:707px;">
						<tr class="trbody">
							<td style="width: 60px;float: left;"><span style="text-align: left;color: red">*</span> 预算指标</td>
							<td colspan="3"  style="padding-right: 5px;">
								<a onclick="openIndex()" href="#">
								<input class="easyui-textbox" style="width: 642px;height: 30px;"
								name="indexName" value="${bean.indexName}" id="F_fBudgetIndexName"
								data-options="prompt: '选择指标' ,icons: [{iconCls:'icon-sousuo'}]" readonly="readonly" required="required"/>
								</a>
							</td>
						</tr>
					</table>	
					<table class="window-table-readonly-zc" cellspacing="0" cellpadding="0" style="margin-left: 0px;width:707px;height: 50px;">
							<tr>
								<td class="window-table-td1" style="width: 128px"><p>批复金额：</p></td>
								<td class="window-table-td2"><p id="p_pfAmount">${bean.pfAmount}[元]</p></td>
								
								<td class="window-table-td1"><p>预算年度：</p></td>
								<td class="window-table-td2"><p id="p_pfDate">${bean.pfDate}</p></td>
							</tr>
							
							<tr>
								<td class="window-table-td1"><p>可用额度：</p></td>
								<td class="window-table-td2"><p id="p_syAmount">${bean.syAmount}[元]</p></td>
								
								<%-- <td class="window-table-td1"><p>累计支出:</p></td>
								<td class="window-table-td2"><p id="p_syAmount">${bean.syAmount}万元</p></td> --%>
							</tr>
					</table>				
				</div>
				</div>
				
				<!-- 附件信息 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="附件信息" data-options="collapsible:false"
					style="overflow:auto;">		
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;width:707px;">
						<tr>
							<td class="td1" style="width:60px;text-align: left"><span class="style1">*</span>
								培训请示
								<input type="file" multiple="multiple" id="f" onchange="upladFile(this,null,'zcgl01')" hidden="hidden">
								<input type="text" id="files" name="files" hidden="hidden">
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
								<c:forEach items="${attaList}" var="att">
									<c:if test="${att.serviceType=='null'}">
									<div style="margin-top: 5px;">
									<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<img style="margin-top: 5px;" src="${base}/resource-modality/${themenurl}/sccg.png">
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
									</div>
									</c:if>
								</c:forEach>
							</td>
						</tr>
						
						
					</table>
					
				</div>
				</div>
				<!-- 审批记录 -->
				<c:if test="${operation == 'edit'}">
					<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
						<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
							<!-- <div class="window-title"> 审批记录</div> -->
								<jsp:include page="check_history.jsp" />
						</div>
					</div>
				</c:if>
			</div>
			
			<div class="window-left-bottom-div" style="margin-top: 55px;">
				<a href="javascript:void(0)" onclick="saveApply(0)">
				<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveApply(1)">
				<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=支出管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<%-- <a href="javascript:void(0)" onclick="showFlowDesinger()">
				<img src="${base}/resource-modality/${themenurl}/button/CCLCT1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a> --%>
			</div>
			
		</div>
		<c:if test="${type!=1 }">
			<div class="window-right-div" style="width:254px;height: 591px">
				<jsp:include page="../../../check_system.jsp" />
			</div>
		</c:if>
	</div>
</form>
