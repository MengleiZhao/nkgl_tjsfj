<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<style type="text/css">
.pro_outcome_th {
	width: 210px;
	background: #c1e3f2;
	font-weight: bold;
	color: #333333;
	text-align: center;
}
#pro_outcome_table td{text-align: center;}
.pro_outcome_input{
	width: 210px;
	border: 0;
	background-color: #f6f6f6;
	vertical-align: middle;
}
.accordion .accordion-header {
	height: 20px;
	width: 910px;
}
textarea {
	height: 60px;
	resize:none;
	padding: 8px;
}
.viewShowtd{
	margin-left: 10px;
}
/* .ystable{border:1px solid #999999;} */
</style>

<body>
<form id="project_add_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" >
		<div class="easyui-accordion" data-options="" style="width:922px;margin-left: 20px">
			<div title="基本支出信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
				<table cellpadding="0" cellspacing="0" border="1px" style="width: 900px">
					<tr class="trbody">
				   		<td class="td1-ys"><span class="style_must">*</span>&nbsp;基本支出编号</td>
				     	<td class="td2"  colspan="4">
				     		<span class="viewShowtd">${bean.FProCode}</span>
				     		<!-- 项目id -->
							<input type="hidden" name="FProId" value="${bean.FProId }"/>
							<input type="text" id="spjlFile" name="spjlFiles" hidden="hidden" >
							<!-- 预算支出类型 -->
							<input type="hidden" id="project_add_FProOrBasic" onchange="FProOrBasicChange(${bean.FProOrBasic})" name="FProOrBasic" value="${bean.FProOrBasic}"/>
				     	</td>
				   	</tr>
					<tr class="trbody">
				   		<td class="td1-ys"><span class="style_must">*</span>&nbsp;基本支出名称</td>
				     	<td class="td2" colspan="4">
				     		<span class="viewShowtd">${bean.FProName}</span>
				     	</td>
				   	</tr>
				    <tr class="trbody">
				    	<td class="td1-ys"><span class="style_must">*</span>&nbsp;预算支出类型</td>
				     	<td class="td2">
							<span class="viewShowtd" id="project_add_FProOrBasic_show"> </span>
				     	</td>
				     	<td class="td1-ys">部门</td>
				     	<td class="td2" colspan="2">
				     		<span class="viewShowtd">${sbbm }</span>
				     	</td>
				    </tr>
				      <tr class="trbody">
				    	<td class="td1-ys"><span class="style_must">*</span>&nbsp;基本支出预算金额</td>
				     	<td class="td2">
				     		<span class="viewShowtd" id="pro_add_FProBudgetAmount">${bean.FProBudgetAmount}万元</span>
				     	</td>
				    	<td class="td1-ys">大写金额</td>
				     	<td class="td2" >
				     		<span style="color: red" class="viewShowtd" id="pro_add_UP_FProBudgetAmount">${bean.provideAmount1}</span>
				     	</td>
				    </tr>
				    <tr class="trbody">
				     	<td class="td1-ys">&nbsp;&nbsp;资金来源</td>
				     	<td class="td2" >
		     				<span class="viewShowtd">${fundsSourceText}</span>
				     	</td>
				     	<td class="td1-ys">&nbsp;&nbsp;开始执行年份</td>
		     			<td class="td2">
		     				<span class="viewShowtd">${bean.planStartYear}年</span>
		     			</td> 
				    </tr>
				    <tr class="trbody">
						<td class="td1-ys"><span class="style_must">*</span>&nbsp;基本支出申报部门</td>
		     			<td class="td2">
		     				<div ondblclick="">
		     					<span class="viewShowtd">${sbbm }</span>
		     				</div>
		     			</td>
						<td class="td1-ys"><span class="style_must">*</span>&nbsp;基本支出申报人</td>
		   				<td class="td2">
		   					<span class="viewShowtd">${sbr }</span>
		   				</td>
					</tr>
				</table>
			</div>
			<div title="基本支出明细" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
				<table id="pro_outcome_table" style="margin-left: 0px" cellpadding="1" cellspacing="1">
					<tr>
						<th class="pro_outcome_th" style="width: 30px">序号</th>
						<th class="pro_outcome_th">活动</th>
						<th class="pro_outcome_th">经济分类科目</th>
						<th class="pro_outcome_th">支出金额（元）</th>
						<th class="pro_outcome_th">摘要</th>
						<!-- <th class="pro_outcome_th">子活动</th> -->
						<!-- <th class="pro_outcome_th">对子活动的描述</th> -->
					</tr>
					<c:forEach var="i" items="${expDetailList}" varStatus="status">
					<tr>
						<td><textarea disabled="disabled" class="pro_outcome_input" style="width:30px">${status.index+1}</textarea></td>
						<td><textarea disabled="disabled" class="pro_outcome_input">${i.activity}</textarea></td>
						<td><textarea disabled="disabled" class="pro_outcome_input">${i.subName}</textarea></td>
						<td><textarea disabled="disabled" class="pro_outcome_input"><fmt:formatNumber type="number" value="${i.outAmount}" maxFractionDigits="2" /></textarea></td>
						<td><textarea disabled="disabled" class="pro_outcome_input">${i.actDesc}</textarea></td>
						<%-- <td><textarea disabled="disabled" class="pro_outcome_input">${i.sonActivity}</textarea></td> --%>
						<%-- <td><textarea disabled="disabled" class="pro_outcome_input">${i.sonActDesc}</textarea></td> --%>
					</tr>
					</c:forEach>
				</table>			
			</div>
			<div title="审批记录" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
				<table id="check-history-dg" class="easyui-datagrid"  style="width:900px;height:auto"
					data-options="
					<c:if test="${empty foCode}">
					url: '',
					</c:if>
					<c:if test="${!empty foCode}">
					url: '${base}/wflow/history?fpId=${fpId}&foCode=${foCode}',
					</c:if>
					method: 'post'
					">
					<thead>
						<tr>
							<th data-options="field:'fproCode',required:'required',align:'center',width:'16%',formatter: proCode">项目编号</th>
							<th data-options="field:'fproName',required:'required',align:'center',width:'15%',formatter: proName">项目名称</th>
							<th data-options="field:'fproHead',required:'required',align:'center',width:'8%',formatter: proHead">项目负责人</th>
							<th data-options="field:'fproAppliDepart',required:'required',align:'center',width:'10%',formatter: proDepart">申报部门</th>
							<th data-options="field:'fuserName',required:'required',align:'center',width:'8%'">审批人</th>
							<th data-options="field:'fcheckResult',required:'required',align:'center',width:'8%',formatter: YJ">审批结果</th>
							<th data-options="field:'fcheckTime',required:'required',align:'center',width:'10%',formatter: ChangeDateFormat">审批时间</th>
							<th data-options="field:'fcheckRemake',required:'required',align:'center',width:'15%'">审批意见</th>
							<th data-options="field:'filesPid',align:'center',resizable:false,sortable:true,formatter:filesshow" width="10%">审批附件</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>    	
	</div>
</form>
<script type="text/javascript">
//页面打印
$(function(){
	window.print();
});

function validateProjectAdd(stepName){
	if(stepName=='项目基本信息'){
		return validateProjectAddBase();
	} else if(stepName=''){
		
	}
	return true;
}

function validateProjectAddBase(){
	/* //验证预算额不大于控制数
	var controlAmount = $('#pro_add_provideAmount1').numberbox('getValue');
	if(controlAmount!=''){
		var num1 = $('#pro_add_FProBudgetAmount').numberbox('getValue');
		if(num1>controlAmount){
			alert('请确保"项目预算金额"不大于"一下控制财拨数"金额！');
			return false;
		}
	} */
	//验证项目属性
	if ($('#project_add_FProName').textbox('getValue')==''){
		alert('请填写项目名称！');
		return false;
	} else if ($('#pro_add_FProBudgetAmount').numberbox('getValue')=='') {
		alert('请填写项目预算金额！');
		return false;
	}  else if ($("input[name='FProcurementYn']:checked").val()==undefined) {
		alert('请选择是否采购！');
		return false;
	} else if ($('#project_add_FProClass').combobox('getValue')=='') {
		alert('请选择项目类别！');
		return false;
	} else if($("input[name='FProcurementYn']:checked").val()==1 && isNaN(parseInt($('#input_cgje').numberbox('getValue'))) ){
		alert('请填写采购金额！');
		return false;
	}
	
	return true;
}

//验证预算总额 = 采购金额 + 支出总额
function validateProMoney(){
	
	var num1 = $('#pro_add_FProBudgetAmount').numberbox('getValue');
	
	var num2 = 0;
	var cg=true;
	if($("input[name='FProcurementYn']:checked").val()==1){
		num2 = $('#input_cgje').numberbox('getValue');	
	}else{
		cg=false;
	}
	
	$('#project-add-outcome-treegrid').datagrid('acceptChanges');
	var num3 = updateRow_pro_total();
	
	if(parseInt(num1) != parseInt(num2) + parseInt(num3)){
		if(cg==true){
			alert("请确保 '项目预算金额' 等于 '采购金额' 和 '项目支出明细总额 ' 之和！");
		}else{
			alert("请确保 '项目预算金额' 等于 '项目支出明细总额 '！");
		}
		return false;
	}
	return true;
}

//保存 saveType,暂存zc，申报sb
function saveProject(saveType){
	if (validateProjectAddBase()==false) {
		return;
	} 
	//alert(2) */
	/* if (validateProMoney()==false) {
		return;
	} */
	setAccoFile();//立项依据附件
	setPlanFile();//实施方案附件
	//setAttaPath('file_pro_acco','path_pro_acco');//上传附件
	//setAttaPath('file_pro_plan','path_pro_plan');
	/* var outcome = getOutcome();//需保存的基本支出科目
	var plan = getPlan();//项目计划
	var longAim = getLongAim();
	var longIndex = getLongIndex();
	var yearAim = getYearAim();
	var yearIndex = getYearIndex(); */
	$('#project_add_form').form('submit', {
				onSubmit: function(param){
					param.saveType = saveType;
					/* param.outcomeJson = outcome;
					param.planJson = plan;
					param.longAimJson = longAim;
					param.longIndexJson = longIndex;
					param.yearAimJson = yearAim;
					param.yearIndexJson = yearIndex; */
				 	flag=$(this).form('enableValidation').form('validate');
					if(flag){
						$.messager.progress();
					}
					return flag; 
				}, 
				url:base+'/project/save', 
				success:function(data){
					if(flag){
						$.messager.progress('close');
					}
					data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示', data.info, 'info');
						closeWindow();
						$('#project_add_form').form('clear');
						$("#pro_dg").datagrid('reload'); 
					}else{
						$.messager.alert('系统提示', data.info, 'error');
						/* closeWindow();
						$('#project_add_form').form('clear'); */
					}
				} 
			});		
}
//寻找相关制度
function findSystemXmFile(id) {
	$.ajax({ 
		url: base+"/project/systemFind?id="+id, 
		success: function(data){
			data=data.replace('\"','');
			data=data.replace('\"','');
			window.open(data);
    }});
}

//校验不通过，就打开第一个校验失败的标签页
function openInvalidTab(tabid){
	//获取所有标签页
	var $wrapper = $('#'+tabid),
		$allTabs = $wrapper.find('.tab-content > div'),
		$tabMenu = $wrapper.find('.tab-menu li');
		
	for(var i=0;i<$allTabs.length;i++){
		var forflag = true;
		//获取标签页标记
		var datab = $allTabs[i].getAttribute('data-tab');
		var inputs = $('div[data-tab="'+datab+'"] input');
		
		$('div[data-tab="'+datab+'"] input').each(function() {
			//打开校验不通过的标签
			if($(this).hasClass("validatebox-invalid")){
				$('li[data-tab="'+datab+'"]').click();
				//打开该标签页
				/*$getWrapper = $(this).closest($wrapper);
				$getWrapper.find($allTabs).css('display','none');
				$getWrapper.find($allTabs).filter('[data-tab='+datab+']').css('display','block');*/
				//更改标签头
				/*var lili= $('li[data-tab="'+datab+'"]');
				$getWrapper.find($tabMenu).removeClass('active');
				$('li[data-tab="'+datab+'"]').addClass('active');*/
				
				forflag=false;
				return false;
			}
		});
		
		if(forflag==false){
			return forflag;
		}
		
	}
	//循环每个标签页
}

function onChangeCgjeInput(){
	var sfcg = 	$('input:radio[name="FProcurementYn"]:checked').val();
	/* if(sfcg==0){
		$('#th_cgje').hide();
		$('#td_cgje').hide();
	}else if(sfcg==1){
		$('#th_cgje').show();
		$('#td_cgje').show();
	} */
}
/* function onChangeSfcxxxm(){
	var sfcx = $('input:radio[name="FContinuousYn"]:checked').val();
	if(sfcx==0){
		$('#pro_add_FProRollingCycle').numberbox('setValue','1');
	}else if(sfcx==1){
		$('#pro_add_FProRollingCycle').numberbox('setValue','');
	}
} */


//设置附件信息
function setAccoFile(){
	var s="";
	$(".fileUrl1").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#files1").val(s);
}
function setPlanFile(){
	var s="";
	$(".fileUrl2").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#files2").val(s);
}
function FProOrBasicChange(newValue,oldValue){
	//隐藏立项依据和项目实施方案
	if(newValue==1){
	//项目支出
		$('.FProAccording').show();
		$('.FExplain').show();
		$('#project_firstLevel').show();
		$('#project_secondLevel').show();
	}
}


$(function(){ 
	$('#pro_add_UP_FProBudgetAmount').html(convertCurrency(${bean.FProBudgetAmount}*10000));
			var proOrBasic = '${bean.FProOrBasic}';
		   	if(proOrBasic==0){
		   	//基本支出
		   		$('.FProAccording').hide();
		   		$('.FExplain').hide();
		   		//$('#fProAccordingId').removeAttr('required');
		   		$('#project_add_FProOrBasic_show').html('基本支出');
		   	}else if(proOrBasic==1){
		   	//项目支出
		   		$('.FProAccording').show();
		   		$('.FExplain').show();
		   		$('#project_add_FProOrBasic_show').htnl('项目支出');
		   	}
});

//项目编号
function proCode(val,row){
	return '${bean.FProCode}';
}
//项目名称
function proName(val,row){
	return '${bean.FProName}';
}
//项目负责人
function proHead(val,row){
	return '${sbr}';
}
//申报部门
function proDepart(val,row){
	return '${bean.FProAppliDepart}';
}

function YJ(val) {
	if(val==1){
		return "通过";
	} else if(val==0){
		return "不通过";
	}
}

function filesshow (val,row){
	if (val=="" || val==null) {
		return '<table><tr style="width: 75px;height:20px"><td><a href="#" class="easyui-linkbutton">无附件' + '</a></td>'
		+'</table>';
	}else if (val!=null) {
	var data = val.split(',');
		if(data.length>0){
		return 	'<table><tr style="width: 75px;height:20px"><td><a href="#" onclick="accessory(\''+val+'\')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fj1.png">' + '</a></td>'
				+'</table>';
		}else{
			return 	'<table><tr style="width: 75px;height:20px"><td><a href="#" onclick="downloadFiles(\'' + data[0]+ '\')" class="easyui-linkbutton"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/fj1.png">' + '</a></td>'
			+'</table>';
		}
	}
}

function accessory(val){
	var win = creatFirstWin('审批附件', 500, 300, 'icon-search', "/project/historyAttaListJsp?id="+val);
	win.window('open');
	
}
function downloadFiles(id){
	if(id==null){
		alert("没有相关附件！");
		return;
	}
	//window.open(base+'/systemcentergl/viewPDF/'+fileName,'open');
	  window.open(base+'/attachment/download/'+id,'open');
	  
}
</script>
</body>