<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div  data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:755px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px">
			<div class="window-left-top-div">
				<div class="tab-wrapper" id="reimburse-meetinginfo-add">
					<ul class="tab-menu">
						<li class="active" onclick="onclickreimburset()">报销单</li>
						<li onclick="onclickdetail()" >申请单</li>
					</ul>
					<div class="tab-content">
						<div title="报销单" style="margin-bottom:35px;width: 737px" data-options="iconCls:'icon-xxlb'">
							<jsp:include page="reimburset_meetinginfo.jsp" />
						</div>
						<div title="申请单" style="overflow:auto;margin-bottom:35px;width: 737px" data-options="iconCls:'icon-xxlb'">
							<jsp:include page="apply_meetinginfo_detail.jsp" />
						</div>
					</div>
				</div>
			</div>
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveReimburse(0)">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveReimburse(1)">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=支出管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<c:if test="${bean.type!=1 }">
			<div class="window-right-div" data-options="region:'east',split:true">
				<jsp:include page="../../../check_system.jsp" />
			</div>
		</c:if>
	</div>
</div>
<script type="text/javascript">
flashtab('reimburse-meetinginfo-add');


//防止不停重新加载
var itineraryurlcount = 0;
function onclickreimburset(){
	if(itineraryurlcount>0){
		itineraryurlcount+=1;
		return false;
	}else {
		itineraryurlcount+=1;
		$('#payer_info_tab').datagrid('reload');
		return true;
	}
}
var detaiurlcount = 0;
function onclickdetail(){
	if(detaiurlcount>=1){
		detaiurlcount+=1;
		return false;
	}else {
		detaiurlcount+=1;
		$('#appli-detail-dg1').datagrid('reload'); 
		$('#dg_meet_plan1').datagrid('reload'); 
		$('#check-history-reim-apply-dg').datagrid('reload'); 
		return true;
	}
}


//冲销借款
function cx(){
	var num1=parseFloat($('#cxAmounts').val());
	var num2=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	var num6=parseFloat($('#input_jkdamonut').val());
	if(isNaN(num1)&&!isNaN(num2)){
	}
	if(!isNaN(num1)&&!isNaN(num2)){
		if(num2<num1){
			 $('#cxAmount').html(fomatMoney(num2,2)+" [元]");
			 $('#cxAmounts').val(num2.toFixed(2));
			 $('#syAmount').html(0.00+" [元]");
		}else{
			$('#cxAmount').html(fomatMoney(num1,2)+" [元]");
			$('#cxAmounts').val(num1.toFixed(2));
			$('#syAmount').html((num2-num1).toFixed(2)+" [元]");
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
	var num6=parseFloat($('#input_jkdamonut').val());
	if(cxjk==1){
	var num1=parseFloat($('#cxAmounts').val());
	if(!isNaN(num1)&&!isNaN(num2)){
		if(num2<num1){
			 $('#cxAmount').html(fomatMoney(num2,2)+" [元]");
			 $('#cxAmounts').val(num2.toFixed(2));
			 $('#syAmount').html(0.00+" [元]");
		}else{
			$('#cxAmount').html(fomatMoney(num1,2)+" [元]");
			$('#cxAmounts').val(num1.toFixed(2));
			$('#syAmount').html((num2-num1).toFixed(2)+" [元]");
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
		$('#duration').numberbox("setValue", 0);
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

	//设置申请金额
	var applyAmount = $("#applyAmount").val();
	var reimburseAmount = $("#reimburseAmount").val();
	if(applyAmount !=""){
		$('#applyAmount_span').html(fomatMoney(applyAmount,2)+" [元]");
		$('#applyAmount_span_two').html(fomatMoney(applyAmount,2)+" [元]");
	}
	if(reimburseAmount != ""){
		$('#p_amount').html(fomatMoney(reimburseAmount,2)+" [元]");
	}

	//zzAmount();
	$("#input_jkdbh").textbox({
		onChange: function(newValue, oldValue) {
			cx();
		}
	});
	setInterval(function () { refreshTable();},1500);
});

function refreshTable(){
	var updateStatus = $('input[name="fupdateStatus"]:checked').val();
	if(updateStatus==1){
		radioyes();
		updateradio=0;
	}else{
		updateradio=1;
	}
}

function selectCxjk(){
	var cxjk = $('input[name="withLoans"]:checked').val();
	if(cxjk==1){
		$('#jk').show();
		$('#withLoan').val(1);
	} else {
		$('#input_jkdamonut').val(0);
		$('#input_jkdbh').textbox('setValue','');
		$('#withLoan').val(0);
		$('#jk').hide();
		cx();
	}
}

//保存
function saveReimburse(flowStauts) {
	if($('#box1').is(":checked")){
		var aa = $('#fupdateReason').val();
		if(aa == '' || aa==null || aa==undefined){
			alert('请填写会议调整说明');
			return ;
		}
	}
	//会议日程
	acceptMeet();
	var rows2 = $('#dg_meet_plan_reimb').datagrid('getRows');
	var meetPLan = "";
	for (var i = 0; i < rows2.length; i++) {
		meetPLan = meetPLan + JSON.stringify(rows2[i]) + ",";
	}
	$('#meetPlanJson').val(meetPLan);
	//费用明细
	acceptMeetingReim();
	var rows = $('#reimb-meeting-mingxi').datagrid('getRows');
	var mingxiJson = "";
	for (var i = 0; i < rows.length; i++) {
		mingxiJson = mingxiJson + JSON.stringify(rows[i]) + ",";
	}
	$('#mingxiJson').val(mingxiJson);
	
	$('#indexName').val($('#F_fBudgetIndexName').textbox('getValue'));//预算指标名称
	var jsonStr1 = $("#form1").serializeJson();
	// 在后台反序列话成明细Json的对象集合
	$('#json1').val(jsonStr1);
	//通用附件的路径地址
	var s="";
	$(".bxtzfileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#bxtzfilesId").val(s);
	if(s==''){
		alert('请上传通知！');
		return false;
	}
	//签到表附件的路径地址
	var s1="";
	$(".bxqdbfileUrl").each(function(){
		s1=s1+$(this).attr("id")+",";
	});
	$("#bxqdbfilesId").val(s1);
	if(s1==''){
		alert('请上传签到表！');
		return false;
	}
	var nums=parseFloat($('#reimburseAmount').val());
	var meetAmount = 0.00;
	var meetapplyAmount = 0.00;
	var meetplanrows = $('#reimb-meeting-mingxi').datagrid('getRows');
	for (var i = 0; i < meetplanrows.length; i++) {
		meetAmount = parseFloat(meetplanrows[i].totalStandard) + parseFloat(meetAmount);
		meetapplyAmount = parseFloat(meetplanrows[i].applySum) + parseFloat(meetapplyAmount);
	}
	if(meetapplyAmount>meetAmount){
		alert('综合预算报销总额不得超过费用标准总额！');
		return;
	}
	
	var payeeAmount=parseFloat($('#payeeAmount').val());//转账金额
	if(isNaN(payeeAmount)){
		payeeAmount=0;
	}
	var num1 = parseFloat($('#cxAmounts').val());
	var skAmount=nums-num1;//报销金额-冲销金额cxAmounts
	var num3=parseFloat($('#applyAmount').val());
	if(isNaN(nums)){
		alert('请填写费用明细');
		return false;
	}
	$('#fupdateReasonid').val($('#fupdateReason').val());
	
	//下面几行判断是否有冲销借款
	var cxjk = $('input[name="withLoans"]:checked').val();
	
	if(cxjk==1){//如果有冲销借款的
		var lCode = $("#input_jkdbh").textbox('getValue');
		if(lCode==''){
			alert('请选择借款单！');
			return false;
		}else if(skAmount!=payeeAmount){
			var info = '报销金额：'+nums+'\n冲销金额：'+num1+'\n转账总金额：'+payeeAmount+'\n冲销后的报销金额和转账金额不一致,请核对后在提交！';
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
	
	getpayerinfoJson();
	getpayerinfoJsonExt();
	
	//设置审批状态
	$('#reimburseFlowStauts').val(flowStauts);
	//设置报销状态
	$('#reimburseStauts').val(flowStauts);
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
		var win=creatFirstWin(' ',360,280,'icon-search','/reimburse/overfulfil?standard='+num3+"&amount="+nums+"&applyAmount="+(nums-num3).toFixed(2)+"&sts="+flowStauts);
		win.window('open');
	}else{
		
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
					closeWindow();
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
}
</script>
</body>