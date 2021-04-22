<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<c:if test="${type!=1}">
<!-- <div class="window-title">费用明细</div> -->
</c:if>

<!-- 住宿费 -->
<div class="window-table" style="margin-bottom:10px;">
	<c:if test="${detail!='detail'}">
	<div style="float: left;">
	<span>费用名称：</span>
	<span style=" color:black;">住宿费</span>
	</div>
	</c:if>
	<table id="rec-hotel-dg_detail" class="easyui-datagrid" style="width:695px;height:auto;"
	data-options="
	url: '${base}/apply/receptionHotel?id=${applyBean.gId}',
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
			<th data-options="field:'hID',hidden:true"></th>
			<th data-options="field:'mark',hidden:true"></th>			
			<th data-options="field:'fName',required:'required',align:'center',width:105,editor:'textbox'">姓名</th>
			<th data-options="field:'position',required:'required',align:'center',width:200,editor:{
							editable:true,
							type:'combotree',
							filter: function(q, row){
									var opts = $(this).combotree('options');
									return row[opts.textField].indexOf(q) == 0;
								},
							options:{
								required:true,
								valueField:'code',
								textField:'text',
								method:'post',
								url:base+'/apply/lookupsJson?parentCode=ZWJBJD',
							}}">职务/级别</th>
			<th data-options="field:'fRoomType',required:'required',align:'center',width:124,editor:{
						type: 'combotree',
						filter: function(q, row){
									var opts = $(this).combotree('options');
									return row[opts.textField].indexOf(q) == 0;
								},options: {
						valueField:'code',
						textField:'text',
	                	prompt:'-请选择-',
	                	panelHeight:'atuo',
	                	editable: false,
	                	url:base+'/apply/lookupsJson?selected=SBJYX&parentCode=SBJYX',
	                	}}">住宿房型</th>
			<!-- <th data-options="field:'fCostStd',required:'required',align:'center',width:120,editor:{type:'numberbox',options:{onChange:addNum1,precision:2,groupSeparator:','}}">费用标准(元/天)</th> -->
			<th data-options="field:'fDays',required:'required',align:'center',width:120 ">住宿天数(天)</th>
			<th data-options="field:'fCostHotel',required:'required',align:'center',width:122 ">申请金额</th>
													
		</tr>
	</thead>
	</table>
	<div style="overflow:auto;margin-top: 10px;">
		<span style="float: right;color: #0000CD;">
			<span>小计金额： </span>
			<span style="float: right;"  id="costHotel_span" ><fmt:formatNumber groupingUsed="true" value="${receptionBean.costHotel}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
			<input type="hidden" id="costHotel" name="costHotel" value="${receptionBean.costHotel}"  />
		</span>
	</div>
</div>
<div style="height:10px;"></div>
<!-- 餐费 -->
<div class="window-table" style="margin-bottom:10px">
	<c:if test="${detail!='detail'}">
	<div style="float: left;">
	<span>费用名称：</span>
	<span style=" color:black;">餐费</span>
	
	</div>
	</c:if>
	<table id="rec-food-dg_detail" class="easyui-datagrid" style="width:695px;height:auto;"
	data-options="
	url: '${base}/apply/receptionFood?id=${applyBean.gId}',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
			<!-- <th data-options="field:'fId',hidden:true"></th>			
			<th data-options="field:'costDetail',required:'required',align:'center',width:100,editor:'textbox'">序号</th>
			<th data-options="field:'fFoodType',required:'required',align:'center',width:165">类别</th>
			<th data-options="field:'fCostStd',required:'required',align:'center',width:165">支出标准(元/人)</th>
			<th data-options="field:'fPersonNum',required:'required',align:'center',width:165">就餐人次</th>
			<th data-options="field:'fNum',required:'required',align:'center',width:115,editor:{type:'numberbox',options:{onChange:addNum2}}">次数(次)</th>
			<th data-options="field:'fCostFood',required:'required',align:'center',width:175">申请金额(元)</th> -->
			
			<th data-options="field:'fId',hidden:true"></th>			
			<!-- <th data-options="field:'costDetail',required:'required',align:'center',width:100,editor:'textbox'">序号</th> -->
			<th data-options="field:'foodTime',required:'required',align:'center',width:120,editor:{type:'datebox',options:{editable: false,onHidePanel:changeTimeCF}}">日期</th>
			<th data-options="field:'address',required:'required',align:'center',width:165,editor:{type:'textbox',options:{editable: true}}">地点</th>
			<th data-options="field:'fFoodType',required:'required',align:'center',width:100,editor:{type:'textbox',options:{editable: false}}">类别</th>
			<th data-options="field:'fPersonNum',required:'required',align:'center',width:90,editor:{type:'numberbox',options:{precision:0,onChange:addNum2,editable: true}}">接待人数</th>
			<th data-options="field:'attendPeopNum',required:'required',align:'center',width:90,editor:{type:'numberbox',options:{precision:0,onChange:attendPeopNumChange,editable: true}}">陪餐人数</th>
			<th data-options="field:'fCostFood',required:'required',align:'center',width:116,editor:{type:'numberbox',options:{onChange:addAmount2,editable: true,precision:2,groupSeparator:','}}">申请金额(元)</th>
		</tr>
	</thead>
	</table>
	<div style="overflow:auto;margin-top: 10px;">
		<span style="float: right;color: #0000CD;">
			<span>小计金额： </span>
			<span style="float: right;"  id="costFood_span" ><fmt:formatNumber groupingUsed="true" value="${receptionBean.costFood}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
			<input type="hidden" id="costFood" name="costFood" value="${receptionBean.costFood}"  />
		</span>
	</div>
</div>
<%-- <div style="height:10px;"></div>
<div class="window-table" style="margin-bottom:10px">
<table class="window-table-readonly" cellspacing="0" cellpadding="0" style="margin-left: 0px;width:695px;">
		<tr>
			<td class="window-table-td1" style="width:66px;"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:black">交通费</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input class="easyui-numberbox" value="${receptionBean.costTraffic}" data-options="icons: [{iconCls:'icon-yuan'}]" readonly="readonly"
				style="height:25px;width: 150px"/>
			</td>
		</tr>
</table>
</div> --%>

<div style="height:15px;"></div>
<div class="window-table" style="margin-bottom:10px">
<table class="window-table-readonly" cellspacing="0" cellpadding="0" style="margin-left: 0px;width: 695px;">
		<tr>
			<td class="window-table-td1" style="width:66px;"><p>费用名称：</p></td>
			<td class="window-table-td2">
				<p style=" color:black;">会议室租金</p>
			</td>
			
			<td class="window-table-td1"><p>申请金额：</p></td>
			<td class="window-table-td2">
				<input class="easyui-numberbox" value="${receptionBean.costRent}" data-options="icons: [{iconCls:'icon-yuan'}]" readonly="readonly" style="height:25px;width: 150px"/>
			</td>
		</tr>
</table>
</div>
<div style="height:15px;"></div>

<!-- 其他费用 -->
<div class="window-table">
	<c:if test="${detail!='detail'}">
	<div style="float: left;">
	<span>费用名称：</span>
	<span style=" color:black;">其他费用</span>
	</div>
	</c:if>
	<table id="rec-other-dg_detail" class="easyui-datagrid" style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	<c:if test="${!empty applyBean.gId}">
	url: '${base}/apply/receptionOther?id=${applyBean.gId}',
	</c:if>
	<c:if test="${empty applyBean.gId}">
	url: '',
	</c:if>
	method: 'post',
	striped : true,
	nowrap : false,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'oID',hidden:true"></th>
				<th data-options="field:'fCostName',required:'required',align:'center',width:195 ">费用名称</th>
				<th data-options="field:'fCost',required:'required',align:'center',width:191 ">支出金额[元]</th>
				<th data-options="field:'fRemark',width:283,align:'center' ">备注</th>
			</tr>
		</thead>
	</table>
	<div style="overflow:auto;margin-top: 10px;">
		<span style="float: right;color: #0000CD;">
			<span>小计金额： </span>
			<span style="float: right;"  id="costOther_span" ><fmt:formatNumber groupingUsed="true" value="${receptionBean.costOther}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span>
			<input type="hidden" id="costOther" name="costOther" value="${receptionBean.costOther}"  />
		</span>
	</div>
</div>
<script type="text/javascript">
//陪餐人数
function attendPeopNumChange(newValue,oldValue){
   	if(newValue==undefined || oldValue==undefined || newValue==''){
   		return false;
   	}
   	
   	var ed1 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'fPersonNum'});
   	var ed2 = $('#rec-food-dg').datagrid('getEditor', {index:0,field:'attendPeopNum'});
   
   	var unitFeteNum =parseInt($(ed1.target).numberbox('getValue'));//宴请人数
   	if($(ed1.target).numberbox('getValue') == '' || $(ed1.target).numberbox('getValue') == null){
   		alert('请先填写宴请人数');
  		$(ed2.target).numberbox('setValue','');
  		return false;
   	}else{
   		if(parseInt(unitFeteNum)<=10){
   	   		if(parseInt(newValue)>3){
   	   			alert('陪餐人数不能超过3人,请重新填写！');
   	   			$(ed2.target).numberbox('setValue','');
   	   			return false;
   	   		}
   	   	}else{
   	   		var unitFeteNumDivide = parseInt(unitFeteNum/3);
   	   		if(parseInt(newValue)>unitFeteNumDivide){
   	   			alert('陪餐人数不能超过宴请人1/3,请重新填写！');
   	   			$(ed2.target).numberbox('setValue','');
   	   			return false;
   	   		}
   	   	}
   	}
}
function changeTimeCF(){
	
	var index=$('#rec-food-dg').datagrid('getRowIndex',$('#rec-food-dg').datagrid('getSelected'));
	var editors = $('#rec-food-dg').datagrid('getEditors', index);  
	var endEditor = editors[1]; 
	var startday = Date.parse(new Date(editors[0].target.val()));
	var maxTime = Date.parse(new Date($("#reDateEnd").datebox('getValue')));
    var minTime = Date.parse(new Date($("#reDateStart").datebox('getValue')));
	var endday = Date.parse(new Date(endEditor.target.val()));
	if(!isNaN(startday)){
    	if((startday>=minTime &&startday<=maxTime) ){
    		if(!isNaN(endday)){
	    		if(endday<startday){
	        		alert("结束日期不能小于开始日期！");
	        		editors[0].target.datebox('setValue', '');
	        	}
    		}
    	}else{
    		if(startday>maxTime || startday<minTime){
	    	alert("所选时间不在日程范围内请重新选择！");
    		editors[0].target.datebox('setValue', '');
    		}
    	}
    	
    } 
}
</script>