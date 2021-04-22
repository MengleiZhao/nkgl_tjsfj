<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="window-tab" style="margin-left: 0px;padding-top: 10px;border-top: 1px solid rgba(217,227,231,1);">

	<table id="reimbursein_hoteltab" class="easyui-datagrid" style="width:695px;height:auto"
	data-options="
	singleSelect: true,
	toolbar: '#reimburse_hoteltool',
	url: '${base}/reimburse/hotelJson?rId=${bean.rId}',
	method: 'post',
	striped : true,
	nowrap : true,
	rownumbers:true,
	scrollbarSize:0,
	">
		<thead>
			<tr>
				<th data-options="field:'ffId',hidden:true"></th>
				<th data-options="field:'checkInTime',align:'center',editor:{type:'datebox', options:{editable:false}},formatter:ChangeDateFormat"width="18%">入住时间</th>
				<th data-options="field:'checkOUTTime',align:'center',editor:{type:'datebox',options:{editable:false}},formatter:ChangeDateFormat"width="18%">退房时间</th>
				<th data-options="field:'CityId',align:'center',editor:'textbox',hidden:true">城市id</th>
				<th data-options="field:'travelPersonnelId',align:'center',editor:'textbox',hidden:true">住宿人员id</th>
				<th data-options="field:'locationCity',align:'center',editor:{type:'textbox',options:{icons:[{iconCls:'icon-add',handler: function(e){
					var row = $('#reimbursein_hoteltab').datagrid('getSelected');
					var index = $('#reimbursein_hoteltab').datagrid('getRowIndex',row);
					chooseArea(index,4)
					<!-- $(e.data.target).textbox('setValue', 'Something added'); -->
				}}]}}" width="20.2%">所在城市</th>
				<th data-options="field:'travelPersonnel',align:'center',editor:{type:'textbox',options:{icons:[{iconCls:'icon-add',handler: function(e){
					var row = $('#reimbursein_hoteltab').datagrid('getSelected');
					var index = $('#reimbursein_hoteltab').datagrid('getRowIndex',row);
					chooseUser(index,4)
					}}]}}" width="32%">住宿人员</th>
				<th data-options="field:'applyAmount',align:'center',editor:{type:'numberbox',options:{precision:2,onChange:hotelAmounts}}" width="13%">报销金额</th>
			</tr>
		</thead>
	</table>
	<div id="reimburse_hoteltool" style="height:20px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">住宿费</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">原申请金额：<span id="rhotelAmount"><fmt:formatNumber groupingUsed="true" value="${applyBean.hotelAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">报销金额：<span id="rhotelAmount"><fmt:formatNumber groupingUsed="true" value="${bean.hotelAmount}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
		<%-- <a href="javascript:void(0)" onclick="removehotel()" id="rhotelRemoveId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
		<a style="float: right;">&nbsp;&nbsp;</a>
		<a href="javascript:void(0)" onclick="appendhotel()" id="rhotelAppendId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a> --%>
	</div>

</div>
<script type="text/javascript">
/* 
<!-- multiple:true,
valueField: 'value',
textField: 'label',
onShowPanel:function onShowPanel(){
		endEditingItinerary();
		userdata();
		userdatajson = eval('('+userdatajson+')');
		$(this).combotree('loadData',userdatajson);
},
onHidePanel:function onSelect(){
	var row = $('#reimbursein_hoteltab').datagrid('getSelected');
	var index = $('#reimbursein_hoteltab').datagrid('getRowIndex',row);
	var e = $('#reimbursein_hoteltab').datagrid('getEditor',{index:index,field:'travelPersonnelId'});
	$(e.target).textbox('setValue', row.value);
}, --> */


//伙食表格添加删除
var editIndex = undefined;
function endEditinghotel() {
	if (editIndex == undefined) {
		return true;
	}
	if ($('#reimbursein_hoteltab').datagrid('validateRow', editIndex)) {
		//下面三行，是在增加一行的时候，防止原来的一行的值变成code
		var tr = $('#reimbursein_hoteltab').datagrid('getEditors', editIndex);
		/* var text=tr[5].target.combotree('getText');
		if(text!='--请选择--'){
			tr[5].target.combotree('setValue',text);
		}
		var text1=tr[4].target.combotree('getText');
		if(text1!='--请选择--'){
			tr[4].target.combotree('setValue',text1);
		} */
		$('#reimbursein_hoteltab').datagrid('endEdit', editIndex);
		calcHotelCost();
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowhotel(index) {
	if(sign==1){
	if (editIndex != index) {
		if (endEditinghotel()) {
			$('#reimbursein_hoteltab').datagrid('selectRow', index).datagrid('beginEdit',index);
			editIndex = index;
		} else {
			$('#reimbursein_hoteltab').datagrid('selectRow', editIndex);
		}
	}
	}else{
		alert("不能编辑！");
		return false;
	}
}
function appendhotel() {
	if (endEditinghotel()) {
		$('#reimbursein_hoteltab').datagrid('appendRow', {});
		editIndex = $('#reimbursein_hoteltab').datagrid('getRows').length - 1;
		$('#reimbursein_hoteltab').datagrid('selectRow', editIndex).datagrid('beginEdit',editIndex);
	}
}
function removehotel() {
	if (editIndex == undefined) {
		return
	}
	$('#reimbursein_hoteltab').datagrid('cancelEdit', editIndex).datagrid('deleteRow',editIndex);
	editIndex = undefined;
	calcHotelCost();
}
function accepthotel() {
	if (endEditinghotel()) {
		$('#reimbursein_hoteltab').datagrid('acceptChanges');
		calcHotelCost();
	}
}
//获得json数据
function getHotelJson(){
	accepthotel();
	$('#reimbursein_hoteltab').datagrid('acceptChanges');
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
	var entities= '';
	for(var i = 0 ;i < rows.length;i++){
	 entities = entities  + JSON.stringify(rows[i]) + ',';  
	}
	$("#hotelJson").val(entities);
}	
function calcHotelCost(){
	//计算总额
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
	var HotelAmount=parseFloat(0.00);
	for(var i=0;i<rows.length;i++){
		var money = isNaN(parseFloat(rows[i].applyAmount))?0.00:parseFloat(rows[i].applyAmount);
		HotelAmount=HotelAmount+money;
	}
	$('#rhotelAmount').html(HotelAmount.toFixed(2)+'[元]');
}	
/* 	//重新计算开支标准
	function countkzbz() {
		accept();
		//获得接待人数（有几行就是接待多少人）
		var rownum = $('#reimbursein_hoteltab').datagrid('getRows').length;
		//修改明细表中的开支标准
		var rows = $('#appli-detail-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++) {
			var tr = $('#appli-detail-dg').datagrid('getEditors', i);
			//获得每一行的开支标准
			var kzbz=rows[i].standard;
			//设置开支标准
			onClickRow(i);
			tr[1].target.textbox('setValue', parseFloat(kzbz*rownum));
			accept();
		}
	} */
	
/* //计算天数
function setDays1(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
    var day1 = editors[6]; 
    var day2 = editors[7]; 
    startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    if(day1!=''&&day2!=''){
    	if(endday<startday){
    		alert("结束日期不能小于开始日期！");
    		editors[6].target.datebox('setValue', '');
    	}
    }
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumDays=setEditing(rows,i);
		}else{
			totalDays+=addNum(rows,i);
		}
		totalDays =fsumDays+totalDays;
		//$('#travelTotalDays').val(totalDays);
		//$('#hotelTotalDays').val(totalDays);
		calcHotelCost();
	}
	
}

function setDays2(newValue,oldValue) {
	var totalDays = 0;
	var fsumDays = 0;
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
    var day1 = editors[6]; 
    var day2 = editors[7]; 
    startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    if(day1!=''&&day2!=''){
    	if(endday<startday){
    		alert("结束日期不能小于开始日期！");
    		editors[7].target.datebox('setValue', '');
    	}
    }
	for(var i=0;i<rows.length;i++){
		if(i==index){
			fsumDays=setEditing(rows,i);
		}else{
			totalDays+=addNum(rows,i);
		}
		totalDays =fsumDays+totalDays;
		//$('#travelTotalDays').val(totalDays);
		//$('#hotelTotalDays').val(totalDays);
		calcHotelCost();
	}
	
}
//未编辑或者已经编辑完毕的行，计算天数
function addNum(rows,index){
	var totalDays=0;
	var startday=new Date(rows[index]['startTime']);
	var endday=new Date (rows[index]['endTime']);
	if(startday!="" && startday!=null && endday!="" && endday!=null){
		totalDays = (endday - startday) / 86400000 + 1;
	}
	return totalDays;
}
//对于正在编辑的行，计算天数
function setEditing(rows,index){
    var editors = $('#reimbursein_hoteltab').datagrid('getEditors', index);  
    var day1 = editors[6]; 
    var day2 = editors[7]; 
    var travelDays =editors[8];
    var hotelDays =editors[9];
	startday = new Date(day1.target.val());
    endday = new Date(day2.target.val());
    var totalDays = (endday - startday) / 86400000 + 1;
    travelDays.target.numberbox('setValue', totalDays);
    hotelDays.target.numberbox('setValue', totalDays-1);
    return totalDays;
}
 */
function reload(rec){
	var row = $('#reimbursein_hoteltab').datagrid('getSelected');
	var rindex = $('#reimbursein_hoteltab').datagrid('getRowIndex', row); 
	var ed = $('#reimbursein_hoteltab').datagrid('getEditor',{
		index:rindex,
		field : 'vehicleLevel'  
	});
	if(rec.code !='JTGJ06'){
		var url = base+'/vehicle/comboboxJson?selected=${travelBean.vehicle}&parentCode='+rec.code;
    	$(ed.target).combotree('reload', url);
	}/* else{
		$('#vehicleOther1').css('display','');
		$('#vehicleOther2').css('display','');
		$('#vehicleLevel1').css('display','none');
		$('#vehicleLevel2').css('display','none');
	}
	 $(ed.target).combobox('setValue', '2016');  */
}

//选择出差地区
function chooseArea(index,editType){
	var win = creatFirstWin('选择-出差地区', 640, 580, 'icon-search', '/hotelStandard/choose?index='+index+'&editType=4');
	win.window('open');
}
//选择人员
function chooseUser(index,editType){
	var win = creatFirstWin('选择-出差人员', 640, 580, 'icon-search', '/hotelStandard/chooseUser?index='+index+'&editType=4');
	win.window('open');
}


function hotelAmounts(newVal,oldVal){
	if(newVal==undefined || oldVal==undefined){
		return false;
	}
	var rows = $('#reimbursein_hoteltab').datagrid('getRows');
	var index=$('#reimbursein_hoteltab').datagrid('getRowIndex',$('#reimbursein_hoteltab').datagrid('getSelected'));
     var num1 = 0;
     for(var i=0;i<rows.length;i++){
		if(i==index){
			num1+=parseFloat(newVal);
		}else{
			num1+=addNumsHotel(rows,i);
		}
	}
		$("#rhotelAmount").html(num1.toFixed(2)+"[元]");
		$("#hotelAmount").val(num1.toFixed(2));
		var cityAmount = $("#cityAmount").val();
		var outsideAmount = $("#outsideAmount").val();
		var foodAmount = $("#foodAmount").val();
		if(cityAmount=='NaN'||cityAmount==''||cityAmount==undefined||cityAmount==null){
			cityAmount=0;
		}
		if(outsideAmount=='NaN'||outsideAmount==''||outsideAmount==undefined||outsideAmount==null){
			outsideAmount=0;
		}
		if(foodAmount=='NaN'||foodAmount==''||foodAmount==undefined||foodAmount==null){
			foodAmount=0;
		}
		$("#rapplyTotalAmount").html((parseFloat(foodAmount)+parseFloat(outsideAmount)+parseFloat(cityAmount)+parseFloat(num1)).toFixed(2)+"元");
		$("#applyAmount").val((parseFloat(foodAmount)+parseFloat(outsideAmount)+parseFloat(cityAmount)+parseFloat(num1)).toFixed(2));
}

function addNumsHotel(rows,index){
	var num=0;
	if(rows[index].applyAmount!=''&&rows[index].applyAmount!='NaN'&&rows[index].applyAmount!=undefined){
		num = parseFloat(rows[index].applyAmount);
	}else{
		num =0;
	}
	return num;
}
</script>