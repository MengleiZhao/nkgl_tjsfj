<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<body>
<div  data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:755px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px">
			<div class="window-left-top-div">
				<div class="tab-wrapper" id="reimburse-traininfo-add">
					<ul class="tab-menu">
						<li class="active" onclick="onclickreimburset()">报销单</li>
						<li onclick="onclickdetail()" >申请单</li>
					</ul>
					<div class="tab-content">
						<div title="报销" style="margin-bottom:35px;width: 737px" data-options="iconCls:'icon-xxlb'">
							<jsp:include page="reimburset_traininfo.jsp" />
						</div>
						<div title="申请" style="overflow:auto;margin-bottom:35px;width: 737px" data-options="iconCls:'icon-xxlb'">
							<jsp:include page="apply_traininfo_detail.jsp" />
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
				<a href="javascript:void(0)" onclick="exportLecturer(${reimbTrainingBean.tId})">
					<img src="${base}/resource-modality/${themenurl}/button/daochu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
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
flashtab('reimburse-traininfo-add');
if('${bean.fupdateStatus}'==1){
	$("input[name='isFood1']").attr("disabled",false);
	$("input[name='isHotel1']").attr("disabled",false);
}
//防止不停重新加载
var itineraryurlcount = 0;
function onclickreimburset(){
	if(itineraryurlcount>0){
		itineraryurlcount+=1;
		return false;
	}else {
		itineraryurlcount+=1;
		/* $('#reimburse_itinerary_tab_id').datagrid('reload');
		$('#reimburse_outside_tab_id').datagrid('reload');
		$('#reimbursein_city_tab_id').datagrid('reload');
		$('#reimbursein_hoteltab').datagrid('reload');
		$('#reimbursein_foodtab').datagrid('reload'); */
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
		/* $('#tracel_itinerary_tab_id_detail').datagrid('reload');
		$('#outside_traffic_tab_id_detail').datagrid('reload');
		$('#in_city_tab_id_detail').datagrid('reload');
		$('#hoteltab_detail').datagrid('reload');*/
		$('#apply_dg_train_lecturer').datagrid('reload'); 
		$('#apply_dg_train_plan').datagrid('reload'); 
		$('#apply_mingxi-zonghe-dg').datagrid('reload'); 
		$('#apply_mingxi-lessons-dg').datagrid('reload'); 
		$('#apply_mingxi-hotel-dg').datagrid('reload'); 
		$('#apply_mingxi-food-dg').datagrid('reload');  
		$('#apply_mingxi-trafficCityToCity-dg').datagrid('reload');  
		$('#apply_mingxi-trafficInCity-dg').datagrid('reload');  
		$.parser.parse("#check-history-reim-apply-dg");
		$('#check-history-reim-apply-dg').datagrid('reload');
		return true;
	}
}
//冲销借款
function cx(){
	var num1=parseFloat($('#input_jkdamonut').val());
	var num2=parseFloat($('#reimburseAmount').val());
	var num3=parseFloat($('#applyAmount').val());
	if(isNaN(num1)&&!isNaN(num2)){
		 $('#skAccount').numberbox('setValue',num2);
	}
	if(!isNaN(num1)&&!isNaN(num2)){
		if(num2<num1){
			var num4=num1-num2;
			 $('#cxAmount').html(fomatMoney(num2,2)+" [元]");
			 $('#cxAmounts').val(num2.toFixed(2));
			 $('#syAmount').html(0.00+" [元]");
			 //$('#skAccount').numberbox('setValue',0);
		}else{
			$('#cxAmount').html(fomatMoney(num1,2)+" [元]");
			$('#cxAmounts').val(num1.toFixed(2));
			$('#syAmount').html((num2-num1).toFixed(2)+" [元]");
			//$('#skAccount').numberbox('setValue',(num2-num1).toFixed(2));
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
	if(cxjk==1){
	var num1=parseFloat(${bean.loan.leastAmount});
	if(!isNaN(num1)&&!isNaN(num2)){
		if(num2<num1){
			var num4=num1-num2;
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
		$('#trDayNum').numberbox("setValue", d);
	} else {
		$('#trDayNum').numberbox("setValue", "");
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
	if(applyAmount !=""){
		$('#applyAmount_span').html(fomatMoney(applyAmount,2)+" [元]");
	}

	var h = $("#reimburseTypeHi").val();
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
	if('${operation}'=='edit'){
		flag2=1;
		flag1=1;
	}
});

function selectCxjk(){
	var cxjk = $('input[name="withLoans"]:checked').val();
	if(cxjk==1){
		$('#jk').show();
		$('#withLoan').val(1);
	} else {
		$('#jk').hide();
		$('#withLoan').val(0);
		$('#input_jkdamonut').val(0);
		cx();
	}
}

//保存
function saveReimburse(flowStauts) {
	$('#fupdateReasonid').val($('#fupdateReason').val());//接待调整说明
	if($('#box1').is(":checked")){
		var aa = $('#fupdateReason').val();
		if(aa == '' || aa==null || aa==undefined){
			alert('请填写培训调整说明');
			return ;
		}
	}
	if(flag2==0){
		alert('请保存讲师信息');
		return ;
	}
	if(flag1==0){
		alert('请保存培训日程');
		return ;
	}
	
	$('#indexName').val($('#F_fBudgetIndexName').textbox('getValue'));//预算指标名称
	
	
	//获取列表json
	getTrainLecturerJson();
	getTrainPlanJson();
	getHotelJson();
	getFoodJson();
	getLessonJson();
	getZongheJson();
	getTrafficJson1();
	getTrafficJson2();
	
	var jsonStr1 = $("#form1").serializeJson();
	var jsonStr2 = $("#form2").serializeJson();
	var jsonStr3 = $("#form3").serializeJson();
	var jsonStr4 = $("#form4").serializeJson();
	var jsonStr5 = $("#form5").serializeJson();
	
	
	// 在后台反序列话成明细Json的对象集合
	
	$('#json1').val(jsonStr1);
	$('#json2').val(jsonStr2);
	$('#json3').val(jsonStr3);
	$('#json4').val(jsonStr4);
	$('#json5').val(jsonStr5);
	
	//附件的路径地址
	var s="";
	$(".bxtzfileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#bxtzfilesId").val(s);
	//签到表附件的路径地址
	var s1="";
	$(".bxqdbfileUrl").each(function(){
		s1=s1+$(this).attr("id")+",";
	});
	$("#bxqdbfilesId").val(s1);
	var nums=parseFloat($('#reimburseAmount').val());//报销金额
	var zongherowAmount = 0.00;
	var zongheapplyAmount = 0.00;
	var zongherows = $('#mingxi-zonghe-dg').datagrid('getRows');
	for (var i = 0; i < zongherows.length; i++) {
		zongherowAmount = parseFloat(zongherows[i].totalStandard) + parseFloat(zongherowAmount);
		zongheapplyAmount = parseFloat(zongherows[i].remibAmount) + parseFloat(zongheapplyAmount);
	}
	if(parseFloat(zongheapplyAmount.toFixed(2))>parseFloat(zongherowAmount.toFixed(2))){
		alert('综合预算报销总额不得超过费用标准总额！');
		return;
	}
	var num3=parseFloat($('#applyAmount').val());//申请金额
	var payeeAmount=parseFloat($('#payeeAmount').val());//转账金额
	var num1=parseFloat($('#cxAmounts').val());//转账金额
	var skAmount=(nums-num1).toFixed(2);//报销金额-冲销金额
	var applyAmount1 = (nums-num3).toFixed(2);
	if(isNaN(nums)){
		alert('报销金额不能为空！');
		return false;
	}
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
			var num1=parseFloat($('#input_jkdamonut').val());//借款单金额
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
	//设置审批状态
	$('#reimburseFlowStauts').val(flowStauts);
	//设置报销状态
	$('#reimburseStauts').val(flowStauts);
	var h = $("#reimburseTypeHi").val();
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
	$("#skAccount").numberbox().numberbox('setValue',num1+num2);
}	
</script>

</body>

