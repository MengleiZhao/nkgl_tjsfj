<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<c:if test="${type!=1}">
<!-- <div class="window-title">费用明细</div> -->
</c:if>


<div class="window-tab" style="margin-left: 0px;padding-top: 10px">


	<table id="mingxi-zonghe-dg" class="easyui-datagrid" style="width:707px;height:auto;"
	data-options="
	toolbar: '#zongheys_Id',
	<c:if test="${!empty bean.gId}">
	url: '${base}/apply/mingxi?id=${bean.gId}',
	</c:if>
	<c:if test="${empty bean.gId}">
	url: '',
	</c:if>
	method: 'post',
	<c:if test="${empty detail}">
	onClickRow: onClickRow3,
	</c:if>
	onBeforeLoad: onLoadSuccessTran,
	striped : true,
	nowrap : false,
	rownumbers:false,
	scrollbarSize:0,
	singleSelect: true,
	">
	<thead>
		<tr>
			<th data-options="field:'cId',hidden:true"></th>
				<th data-options="field:'costDetail',required:'required',align:'center',width:170">费用名称</th>
				<th data-options="field:'standard',required:'required',align:'center',width:180">费用标准（元/人天）</th>
				<th data-options="field:'totalStandard',required:'required',align:'center',width:180">总额标准[元]</th>
				<c:if test="${empty detail}">
				<th data-options="field:'applySum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{onChange:addNum1,precision:2,groupSeparator:','}}">申请金额[元]</th>
				</c:if>
				<c:if test="${!empty detail}">
				<th data-options="field:'applySum',required:'required',align:'center',width:180,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}">申请金额[元]</th>
				</c:if>
		</tr>
	</thead>
	</table>
	<div id="zongheys_Id" style="height:30px;padding-top : 8px">
		<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">综合预算</a>
		<a style="float: left;">&nbsp;&nbsp;</a>
		<a style="float: left;color: #666666;font-size:12px;">申请金额：<span id="zongheysAmount"><fmt:formatNumber groupingUsed="true" value="${trainingBean.zongheMoney}" minFractionDigits="2" maxFractionDigits="2"/>[元]</span></a>
	</div>
</div>

<input type="hidden" id="zongheJson" name="zongheJson"/>
<script type="text/javascript">
function getZongheJson(){
	accept3();
	var rows = $('#mingxi-zonghe-dg').datagrid('getRows');
	var zongheJson = "";
	for (var i = 0; i < rows.length; i++) {
		zongheJson = zongheJson + JSON.stringify(rows[i]) + ",";
	}
	$('#zongheJson').val(zongheJson);
}

function onLoadSuccessTran(data){
	var rows = data.rows;
	if(rows == undefined || rows.length == 0){
		$('#mingxi-zonghe-dg').datagrid('appendRow', {
			costDetail: '住宿费'
		});
		$('#mingxi-zonghe-dg').datagrid('appendRow', {
			costDetail: '伙食费'
		});
		$('#mingxi-zonghe-dg').datagrid('appendRow', {
			costDetail: '场地、资料、交通费'
		});
		$('#mingxi-zonghe-dg').datagrid('appendRow', {
			costDetail: '其他费用'
		});
	}
	editIndex3 = undefined;
}
//接待人员表格添加删除，保存方法
var editIndex3 = undefined;
function endEditing3() {
	if (editIndex3 == undefined) {
		return true
	}
	if ($('#mingxi-zonghe-dg').datagrid('validateRow', editIndex3)) {
		$('#mingxi-zonghe-dg').datagrid('endEdit', editIndex3);
		editIndex3 = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow3(index) {
		if (editIndex3 != index) {
			if (endEditing3()) {
				$('#mingxi-zonghe-dg').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex3 = index;
				var row =$('#mingxi-zonghe-dg').datagrid('getRows')[index];
				var isHotel = $('input[name="isHotel"]:checked').val();
				var isFood = $('input[name="isFood"]:checked').val();
				if(row.costDetail=='住宿费'&&isHotel==0){
					var ed =$('#mingxi-zonghe-dg').datagrid('getEditor',{index:index,field:'applySum'});
					$(ed.target).numberbox('readonly', true);
				}
				if(row.costDetail=='伙食费'&&isFood==0){
					var ed =$('#mingxi-zonghe-dg').datagrid('getEditor',{index:index,field:'applySum'});
					$(ed.target).numberbox('readonly', true);
				}
			} else {
				$('#mingxi-zonghe-dg').datagrid('selectRow', editIndex3);
			}
		}
}
function accept3() {
	if (endEditing3()) {
		$('#mingxi-zonghe-dg').datagrid('acceptChanges');
	}
}

//计算申请金额
function addNum1(newValue,oldValue) {
		var row = $('#mingxi-zonghe-dg').datagrid('getSelected');//获得选择行
		var index=$('#mingxi-zonghe-dg').datagrid('getRowIndex',row);//获得选中行的行号
		var tr = $('#mingxi-zonghe-dg').datagrid('getEditors', index);
		var standar= parseFloat(row.totalStandard);//获得选中行的开支标准
		if(isNaN(standar)){
			standar=0;
		}
		/* if(parseFloat(newValue)>parseFloat(standar)){
			
			alert('申请金额不能大于开支标准，请核对！');
			tr[0].target.numberbox('setValue',0);
			newValue=0;
		} */
		
		var num = 0;
		var num1 = 0;
		var rows = $('#mingxi-zonghe-dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i!=index){
				if(rows[i].applySum!=""&&rows[i].applySum!=null){
					num += parseFloat(rows[i].applySum);
				}
			}
			num1 += parseFloat(rows[i].totalStandard);
		}
		if(newValue!=""&&newValue!=null) {
			num += parseFloat(newValue);
		}
		if(num1 < num){
			alert("申请金额不能大于总额标准！请重新填写！");
			tr[0].target.numberbox('setValue',0);
			num = num - parseFloat(newValue);
			newValue=0;
		}
		$('#zongheMoney').val(num.toFixed(2));
		$('#zongheysAmount').html(fomatMoney(num,2)+"[元]");
		addTotalAmount();
}

</script>
