<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="purchasing_tab_id" class="easyui-datagrid" 
data-options="
singleSelect: true,
toolbar: '#purchasingToolbar',
rownumbers : true,
striped:true,
<c:if test="${!empty bean.fpId}">
url: '${base}/cgprocess/purchasing?id=${bean.fpId}',
</c:if>
method: 'post',
onClickRow: onClickRowPur
">
<thead>
	<tr>
		<th data-options="field:'fbId',hidden:true"></th>
		<th data-options="field:'fpId',hidden:true"></th>
		<th data-options="field:'fbiddingCode',hidden:true">招标编号</th>
		<th data-options="field:'fbiddingName',align:'center',editor:'textbox'" style="width: 27.5%"><span class="style1">*</span>供应商名称</th>
		<th data-options="field:'fbidAmount',align:'center',formatter:function(value,row,index){return fomatMoney(value,2);},editor:{type:'numberbox',options:{precision:2,editable:true}}" style="width: 25%"><span class="style1">*</span>投标金额(元)</th>
		<th data-options="field:'fgrade',align:'center',editor:'textbox'" style="width: 25%">评分</th>
		<th data-options="field:'fbidStatus',align:'center',editor:{type:'combobox',options:{valueField:'value',
						textField:'label',editable:false,data: [{label: '是',value: '是'},{label: '否',value: '否'}]
						}}" style="width:25%"><span class="style1">*</span>是否为中标供应商</th>
		<!-- <th data-options="field:'flegal',align:'center',editor:'textbox'" style="width: 15%">法定代表人</th>
		<th data-options="field:'flinkman',align:'center',editor:{type:'textbox'}" style="width: 20%">联系人</th>
		<th data-options="field:'fphone',align:'center',editor:{type:'textbox'}" style="width: 20%">联系方式</th> -->
	</tr>
</thead>
</table>
<div id="purchasingToolbar" style="height:30px">
	<a >供应商名单</a>
	<a href="javascript:void(0)" onclick="editResults()" id="editResultsId" hidden="hidden" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/xg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="addResults()" id="addResultsId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="removeitPur()" id="removeitPurId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="appendPur()" id="appendPurId" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
</div>

<%-- <div id="winningDivId">
		<c:forEach items="${list}" var="list" varStatus="i">
		<div id="winningDivId_${i.index}" style="margin-top: 20px;">
		<table id="purchasing_tab_pur_${i.index}" class="easyui-datagrid"  data-options="
		toolbar: '#toolbar${i.index}',
		onClickRow: onClickRowPurRes,
		singleSelect:true,
		url: '${base}/cgprocess/purchasingMX?code=${list.fbiddingCode}',
		">
		<thead>
			<tr>
				<th data-options="field:'mRId',hidden:true"></th>
				<th data-options="field:'fpId',hidden:true"></th>
				<th data-options="field:'mainId',hidden:true"></th>
				<th data-options="field:'fbiddingCode',hidden:true"></th>
				
				<c:if test="${bean.fpItemsName!='PMMC-4' && bean.fpItemsName!='PMMC-5'}">
				<th data-options="field:'fmName',width:150,align:'center',editor:{type:'textbox',options:{editable:false,prompt: '请点击此处选择',icons:[{iconCls:'icon-add',handler: function(e){
						var id =e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.children[2].id;// e.target表示被点击的目标
					     var row = $('#'+id).datagrid('getSelected');
					     var index = $('#'+id).datagrid('getRowIndex',row);
					     selectCGQDDetail(index,id,row.fbiddingCode);
					     }}]}}" >商品名称</th>
				<th data-options="field:'fpNum',width:120,align:'center',editor:{type:'numberbox',options:{editable:true,onChange:function(newValue,oldValue){
							var row = $('#'+tabId).datagrid('getSelected');
						     var index = $('#'+tabId).datagrid('getRowIndex',row);
							dakaibianji(index,0,tabId,editIndexPurRes);
						     var fsignPrice = $('#'+tabId).datagrid('getEditor',{
						 		index:index,
						 		field : 'fsignPrice'  
						 	});
						     var famount = $('#'+tabId).datagrid('getEditor',{
						 		index:index,
						 		field : 'famount'  
						 	});
						     var fpNum = parseFloat(newValue);
						 	var fsignPrices = parseFloat(fsignPrice.target.numberbox('getValue'));
						 	if(isNaN(fpNum) || isNaN(fsignPrices)){
						 		return false;
						 	}
						    $(famount.target).numberbox('setValue', (fpNum*fsignPrices).toFixed(2));
						    
						    var money = 0.00;
							var rows = $('#'+tabId).datagrid('getRows');
							for (var i = 0; i < rows.length; i++) {
								if(i!=index){
									var famounts = parseFloat(rows[i].famount);
									if(isNaN(famounts)){
									 	famounts = 0;
									 }
									money = parseFloat(money) +parseFloat(famounts);
								}
							}
							var idSign = tabId.split('_');
							$('#grossId'+idSign[3]).html((parseFloat(money)+(fpNum*fsignPrices)).toFixed(2));
					}}}" >数量</th>				
				<th data-options="field:'fmModel',width:120,align:'center'" >单位</th>				
				<th data-options="field:'fWhetherImport',width:120,align:'center'" >是否进口</th>				
				<th data-options="field:'fsignPrice',width:120,align:'center',editor:{type:'numberbox',options:{precision:2,editable:true,onChange:function(newValue,oldValue){
							var row = $('#'+tabId).datagrid('getSelected');
						    var index = $('#'+tabId).datagrid('getRowIndex',row);
							dakaibianji(index,0,tabId,editIndexPurRes);
						    var fpNum = $('#'+tabId).datagrid('getEditor',{
						 		index:index,
						 		field : 'fpNum'  
						 	});
						     var famount = $('#'+tabId).datagrid('getEditor',{
						 		index:index,
						 		field : 'famount'  
						 	});
						     var fsignPrice = parseFloat(newValue);
						 	var fpNums = parseFloat(fpNum.target.numberbox('getValue'));
						 	if(isNaN(fpNums) || isNaN(fsignPrice)){
						 		return false;
						 	}
						    $(famount.target).numberbox('setValue', (fpNums*fsignPrice).toFixed(2));

							var money = 0.00;
							var rows = $('#'+tabId).datagrid('getRows');
							for (var i = 0; i < rows.length; i++) {
								if(i!=index){
									var famounts = parseFloat(rows[i].famount);
									if(isNaN(famounts)){
									 	famounts = 0;
									 }
									money = parseFloat(money) +parseFloat(famounts);
								}
							}
							var idSign = tabId.split('_');
							$('#grossId'+idSign[3]).html((parseFloat(money)+(fpNums*fsignPrice)).toFixed(2));
					}}}" >单价(元)</th>				
				<th data-options="field:'fBrand',width:120,align:'center',editor:'textbox'" >品牌</th>				
				<th data-options="field:'fmSpecif',width:120,align:'center',editor:'textbox'" >型号</th>		
				<th data-options="field:'famount',width:120,align:'center',editor:{type:'numberbox',options:{editable:false,precision:2}}" >中标金额(元)</th>	
				</c:if>
				<c:if test="${bean.fpItemsName=='PMMC-4' || bean.fpItemsName=='PMMC-5'}">	
				<th data-options="field:'fmName',width:385,align:'center',editor:{type:'textbox',options:{editable:false,prompt: '请点击此处选择',icons:[{iconCls:'icon-add',handler: function(e){
						var id =e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.children[2].id;// e.target表示被点击的目标
					     var row = $('#'+id).datagrid('getSelected');
					     var index = $('#'+id).datagrid('getRowIndex',row);
					     selectCGQDDetail(index,id,row.fbiddingCode);
					     }}]}}" >商品名称</th>	
				<th data-options="field:'famount',width:310,align:'center',editor:{type:'numberbox',options:{editable:true,precision:2,onChange:function(newValue,oldValue){
							var row = $('#'+tabId).datagrid('getSelected');
						    var index = $('#'+tabId).datagrid('getRowIndex',row);
							dakaibianji(index,0,tabId,editIndexPurRes);
							var money = 0.00;
							var rows = $('#'+tabId).datagrid('getRows');
							for (var i = 0; i < rows.length; i++) {
								if(i!=index){
									money = parseFloat(money) +parseFloat(rows[i].famount);
								}
							}
							var newVal = parseFloat(newValue);
							if(isNaN(newVal)){
								newVal=0;
							}
							var idSign = tabId.split('_');
							$('#grossId'+idSign[3]).html((parseFloat(money)+newVal).toFixed(2));
					}}}" >中标金额(元)</th>	
				</c:if>			
			</tr>
		</thead>
		</table> 
			 <div id="toolbar${i.index }">${list.fbiddingName}
			 </div>
			 <a style="float:right">小计金额：<span id="grossId${i.index }">${list.fbidAmount}</span>[元]
			 </a> 
		</div>
	
	</c:forEach>
</div> --%>
<input type="hidden" id="jsonList" name="jsonList"/>
<input type="hidden" id="supJsonList" name="supJsonList"/>
<script type="text/javascript">
var frId  ='${brBean.frId}';
var fpItemsNames = '${bean.fpItemsName}';
var sign =0;
var tabId ="";
if(frId !=''){
	frIdIsNotEmpty();
}

//明细表格添加删除，保存方法
var editIndexPur = undefined;
function endEditingPur() {
	if (editIndexPur == undefined) {
		return true;
	}
	if ($('#purchasing_tab_id').datagrid('validateRow', editIndexPur)) {
		var ed = $('#purchasing_tab_id').datagrid('getEditor', {
			index : editIndexPur,
			field : 'costDetail'
		});
		$('#purchasing_tab_id').datagrid('endEdit', editIndexPur);
		editIndexPur = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRowPur(index,id) {
	if(sign==0){
		if (editIndexPur != index) {
			if (endEditingPur()) {
				$('#purchasing_tab_id').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndexPur = index;
			} else {
				$('#purchasing_tab_id').datagrid('selectRow', editIndexPur);
			}
		}	
	}else{
		alert('请点击修改按钮!');
		return false;
	}
}

function appendPur() {//未配置采购类型不可添加采购清单
	 if (endEditingPur()) {
		 var date = Date.parse(new Date());
		 var x = 9999;
	     var y = 1000;
	     var rand = parseInt(Math.random() * (x - y + 1) + y);
			$('#purchasing_tab_id').datagrid('appendRow', {
				fbiddingCode:"ZBBH"+(date+rand)
			});
			editIndexPur = $('#purchasing_tab_id').datagrid('getRows').length - 1;
			$('#purchasing_tab_id').datagrid('selectRow', editIndexPur).datagrid('beginEdit',editIndexPur);
		} 
}
function removeitPur() {
	if (editIndexPur == undefined) {
		return;
	}
	$('#purchasing_tab_id').datagrid('cancelEdit', editIndexPur).datagrid('deleteRow',editIndexPur);
	editIndexPur = undefined;
	//修改申请总额
	//setfamount(0,0);
}

function acceptPur() {
	if (endEditingPur()) {
		$('#purchasing_tab_id').datagrid('acceptChanges');
	}
}

function addResults(){
	acceptPur();
	var rows = $('#purchasing_tab_id').datagrid('getRows');
	if(rows==''){
		alert('请添加中标商!');
		return false;
	}
	var amount = '${bean.amount}';
	for (var q = 0; q < rows.length; q++){
		var fbidAmounts =  parseFloat(rows[q].fbidAmount);
		if(isNaN(fbidAmounts)){
			fbidAmounts =0;
		}
		if(fbidAmounts>amount){
			alert('中标金额不能大于采购申请金额！');
			return false;
		}
	}
	
	/* var num = $("#winningDivId").children().length;
	var nums = rows.length;
	if(num==0){
		for (var i = 0; i < rows.length; i++) {
			$("#toolbar"+i).remove();
			$('#winningDivId').append('<div id="winningDivId_'+i+'" style="margin-top: 20px;"><table id="purchasing_tab_pur_'+i+'" data-options="onClickRow: onClickRowPurRes,singleSelect:true"></table>'
					+'<div id="toolbar'+i+'">'+rows[i].fbiddingName+'</div><a style="float:right">小计金额：<span id="grossId'+i+'"></span>[元]</a>'
					+'</div>');
			if(fpItemsNames=='PMMC-4' || fpItemsNames=='PMMC-5'){
				$('#purchasing_tab_pur_'+i).datagrid({
				    columns:[[
						{field:'mRId',hidden:true},
						{field:'fpId',hidden:true},
						{field:'mainId',hidden:true},
						{field:'fbiddingCode',hidden:true},
						{field:'fmName',title:'商品名称',width:350,align:'center',editor:{type:'textbox',options:{editable:false,prompt: '请点击此处选择',icons:[{iconCls:'icon-add',handler: function(e){
							var id =e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.children[2].id;// e.target表示被点击的目标
						     var row = $("#"+id).datagrid('getSelected');
						     var index = $("#"+id).datagrid('getRowIndex',row);
						     selectCGQDDetail(index,id,row.fbiddingCode);
						     }}]}}},
						{field:'famount',title:'中标金额(元)',width:310,align:'center',editor:{type:'numberbox',options:{editable:true,precision:2,onChange:function(newValue,oldValue){
							var row = $('#'+tabId).datagrid('getSelected');
						    var index = $('#'+tabId).datagrid('getRowIndex',row);
							dakaibianji(index,0,tabId,editIndexPurRes);
							var money = 0.00;
							var rows = $('#'+tabId).datagrid('getRows');
							for (var i = 0; i < rows.length; i++) {
								if(i!=index){
									money = parseFloat(money) +parseFloat(rows[i].famount);
								}
							}
							var newVal = parseFloat(newValue);
							if(isNaN(newVal)){
								newVal=0;
							}
							var idSign = tabId.split('_');
							$('#grossId'+idSign[3]).html((parseFloat(money)+newVal).toFixed(2));
					}}}},
				    ]],
				    toolbar: "#toolbar"+i
				});
				var rowss = $('#purchasing_tab_pur_'+i).datagrid('getRows');
				if(rowss==''){
					$('#purchasing_tab_pur_'+i).datagrid('appendRow',{
						fbiddingCode:rows[i].fbiddingCode,
						fpurName: '请点击此处选择',
						famount: 0.00
					});
				}
			}else{
				$('#purchasing_tab_pur_'+i).datagrid({
				    columns:[[
						{field:'mRId',hidden:true},
						{field:'fpId',hidden:true},
						{field:'mainId',hidden:true},
						{field:'fbiddingCode',hidden:true},
						{field:'fmName',title:'商品名称',width:150,align:'center',editor:{type:'textbox',options:{editable:false,prompt: '请点击此处选择',icons:[{iconCls:'icon-add',handler: function(e){
							var id =e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.children[2].id;// e.target表示被点击的目标
						     var row = $("#"+id).datagrid('getSelected');
						     var index = $("#"+id).datagrid('getRowIndex',row);
						     selectCGQDDetail(index,id,row.fbiddingCode);
						     }}]}}},
						{field:'fpNum',title:'数量',width:120,align:'center',editor:{type:'numberbox',options:{editable:true,onChange:function(newValue,oldValue){
							
								var row = $("#"+tabId).datagrid('getSelected');
							     var index = $("#"+tabId).datagrid('getRowIndex',row);
								dakaibianji(index,0,tabId,editIndexPurRes);
							     var fsignPrice = $('#'+tabId).datagrid('getEditor',{
							 		index:index,
							 		field : 'fsignPrice'  
							 	});
							     var famount = $('#'+tabId).datagrid('getEditor',{
							 		index:index,
							 		field : 'famount'  
							 	});
							     var fpNum = parseFloat(newValue);
							 	var fsignPrices = parseFloat(fsignPrice.target.numberbox('getValue'));
							 	if(isNaN(fpNum) || isNaN(fsignPrices)){
							 		return false;
							 	}
							    $(famount.target).numberbox('setValue', (fpNum*fsignPrices).toFixed(2));
							    
							    var money = 0.00;
								var rows = $('#'+tabId).datagrid('getRows');
								for (var i = 0; i < rows.length; i++) {
									if(i!=index){
										var famounts = parseFloat(rows[i].famount);
										if(isNaN(famounts)){
										 	famounts = 0;
										 }
										money = parseFloat(money) +parseFloat(famounts);
									}
								}
								var idSign = tabId.split("_");
								$("#grossId"+idSign[3]).html((parseFloat(money)+(fpNum*fsignPrices)).toFixed(2));
						}}}},
						{field:'fmModel',title:'单位',width:120,align:'center'},
						{field:'fWhetherImport',title:'是否进口',width:120,align:'center'},
						{field:'fsignPrice',title:'单价(元)',width:120,align:'center',editor:{type:'numberbox',options:{precision:2,editable:true,onChange:function(newValue,oldValue){
								var row = $("#"+tabId).datagrid('getSelected');
							    var index = $("#"+tabId).datagrid('getRowIndex',row);
								dakaibianji(index,0,tabId,editIndexPurRes);
							    var fpNum = $('#'+tabId).datagrid('getEditor',{
							 		index:index,
							 		field : 'fpNum'  
							 	});
							     var famount = $('#'+tabId).datagrid('getEditor',{
							 		index:index,
							 		field : 'famount'  
							 	});
							     var fsignPrice = parseFloat(newValue);
							 	var fpNums = parseFloat(fpNum.target.numberbox('getValue'));
							 	if(isNaN(fpNums) || isNaN(fsignPrice)){
							 		return false;
							 	}
							    $(famount.target).numberbox('setValue', (fpNums*fsignPrice).toFixed(2));
							    
								var money = 0.00;
								var rows = $('#'+tabId).datagrid('getRows');
								for (var i = 0; i < rows.length; i++) {
									if(i!=index){
										var famounts = parseFloat(rows[i].famount);
										if(isNaN(famounts)){
										 	famounts = 0;
										 }
										money = parseFloat(money) +parseFloat(famounts);
									}
								}
								var idSign = tabId.split("_");
								$("#grossId"+idSign[3]).html((parseFloat(money)+(fpNums*fsignPrice)).toFixed(2));
						}}}},
						{field:'fBrand',title:'品牌',width:120,align:'center',editor:'textbox'},
						{field:'fmSpecif',title:'型号',width:120,align:'center',editor:'textbox'},
						{field:'famount',title:'中标金额(元)',width:120,align:'center',editor:{type:'numberbox',options:{editable:false,precision:2}}},
				    ]],
				    toolbar: "#toolbar"+i
				});
				var rowss = $('#purchasing_tab_pur_'+i).datagrid('getRows');
				if(rowss==''){
					$('#purchasing_tab_pur_'+i).datagrid('appendRow',{
						fbiddingCode:rows[i].fbiddingCode,
						fpurName: '请点击此处选择',
						fpNum: 0,
						fmeasureUnit: '',
						fIsImp: '',
						fsignPrice: '',
						fBrand: '',
						fmSpecif: '',
						famount: 0.00
					});
				}
			}
			
		}
	}else{
		if(nums==num){
			for (var i = 0; i < rows.length; i++) {
				$("#toolbar"+i).html();
				$("#toolbar"+i).html(rows[i].fbiddingName);
				var rowss = $('#purchasing_tab_pur_'+i).datagrid('getRows');
				for (var r = 0; r < rowss.length; r++) {
					$('#purchasing_tab_pur_'+i).datagrid('updateRow',{
						index: r,
						row: {
							fbiddingCode:rows[i].fbiddingCode,
						}
					});
				}
			}
		}
		if(nums<num){
			for (var i =num-1; i >nums-1; i--) {
				$("#winningDivId_"+i).html();
				$("#winningDivId_"+i).remove();
			}
			for (var j = 0; j < rows.length; j++) {
				$("#toolbar"+j).html();
				$("#toolbar"+j).html(rows[j].fbiddingName);
				var rowss = $('#purchasing_tab_pur_'+j).datagrid('getRows');
				for (var r = 0; r < rowss.length; r++) {
					$('#purchasing_tab_pur_'+j).datagrid('updateRow',{
						index: r,
						row: {
							fbiddingCode:rows[j].fbiddingCode,
						}
					});
				}
			}
		}
		if(nums>num){
			for (var j = 0; j < num; j++) {
				$("#toolbar"+j).html();
				$("#toolbar"+j).html(rows[j].fbiddingName);
				var rowss = $('#purchasing_tab_pur_'+j).datagrid('getRows');
				for (var r = 0; r < rowss.length; r++) {
					$('#purchasing_tab_pur_'+j).datagrid('updateRow',{
						index: r,
						row: {
							fbiddingCode:rows[j].fbiddingCode,
						}
					});
				}
			}
			for (var i = num; i <nums; i++) {
				$("#toolbar"+i).remove();
				$('#winningDivId').append('<div id="winningDivId_'+i+'" style="margin-top: 20px;"><table id="purchasing_tab_pur_'+i+'" data-options="onClickRow: onClickRowPurRes,singleSelect:true"></table>'
						+'<div id="toolbar'+i+'">'+rows[i].fbiddingName+'</div><a style="margin-left: 20px;float:right">小计金额：<span id="grossId'+i+'"></span>[元]</a>'
						+'</div>');
				if(fpItemsNames=='PMMC-4' || fpItemsNames=='PMMC-5'){
					$('#purchasing_tab_pur_'+i).datagrid({
					    columns:[[
									{field:'mRId',hidden:true},
									{field:'fpId',hidden:true},
									{field:'mainId',hidden:true},
									{field:'fbiddingCode',hidden:true},
									{field:'fmName',title:'商品名称',width:350,align:'center',editor:{type:'textbox',options:{editable:false,prompt: '请点击此处选择',icons:[{iconCls:'icon-add',handler: function(e){
										var id =e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.children[2].id;// e.target表示被点击的目标
									     var row = $("#"+id).datagrid('getSelected');
									     var index = $("#"+id).datagrid('getRowIndex',row);
									     selectCGQDDetail(index,id,row.fbiddingCode);
									     }}]}}},
									{field:'famount',title:'中标金额(元)',width:310,align:'center',editor:{type:'numberbox',options:{editable:true,precision:2,onChange:function(newValue,oldValue){
										var row = $('#'+tabId).datagrid('getSelected');
									    var index = $('#'+tabId).datagrid('getRowIndex',row);
										dakaibianji(index,0,tabId,editIndexPurRes);
										var money = 0.00;
										var rows = $('#'+tabId).datagrid('getRows');
										for (var i = 0; i < rows.length; i++) {
											if(i!=index){
												var famounts = parseFloat(rows[i].famount);
												if(isNaN(famounts)){
												 	famounts = 0;
												 }
												money = parseFloat(money) +parseFloat(famounts);
											}
										}
										var newVal = parseFloat(newValue);
										if(isNaN(newVal)){
											newVal=0;
										}
										var idSign = tabId.split('_');
										$('#grossId'+idSign[3]).html((parseFloat(money)+newVal).toFixed(2));
								}}}},
							    ]],
					    toolbar: "#toolbar"+i
					});
					var rowss = $('#purchasing_tab_pur_'+i).datagrid('getRows');
					if(rowss==''){
						$('#purchasing_tab_pur_'+i).datagrid('appendRow',{
							fbiddingCode:rows[i].fbiddingCode,
							fpurName: '请点击此处选择',
							famount: 0.00
						});
					}
				}else{
					$('#purchasing_tab_pur_'+i).datagrid({
					    columns:[[
									{field:'mRId',hidden:true},
									{field:'fpId',hidden:true},
									{field:'mainId',hidden:true},
									{field:'fbiddingCode',hidden:true},
									{field:'fmName',title:'商品名称',width:150,align:'center',editor:{type:'textbox',options:{editable:false,prompt: '请点击此处选择',icons:[{iconCls:'icon-add',handler: function(e){
										var id =e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.children[2].id;// e.target表示被点击的目标
									     var row = $("#"+id).datagrid('getSelected');
									     var index = $("#"+id).datagrid('getRowIndex',row);
									     selectCGQDDetail(index,id,row.fbiddingCode);
									     }}]}}},
									{field:'fpNum',title:'数量',width:120,align:'center',editor:{type:'numberbox',options:{editable:true,onChange:function(newValue,oldValue){
										
											var row = $("#"+tabId).datagrid('getSelected');
										     var index = $("#"+tabId).datagrid('getRowIndex',row);
											dakaibianji(index,0,tabId,editIndexPurRes);
										     var fsignPrice = $('#'+tabId).datagrid('getEditor',{
										 		index:index,
										 		field : 'fsignPrice'  
										 	});
										     var famount = $('#'+tabId).datagrid('getEditor',{
										 		index:index,
										 		field : 'famount'  
										 	});
										     var fpNum = parseFloat(newValue);
										 	var fsignPrices = parseFloat(fsignPrice.target.numberbox('getValue'));
										 	if(isNaN(fpNum) || isNaN(fsignPrices)){
										 		return false;
										 	}
										    $(famount.target).numberbox('setValue', (fpNum*fsignPrices).toFixed(2));
										    
										    var money = 0.00;
											var rows = $('#'+tabId).datagrid('getRows');
											for (var i = 0; i < rows.length; i++) {
												if(i!=index){
													var famounts = parseFloat(rows[i].famount);
													if(isNaN(famounts)){
													 	famounts = 0;
													 }
													money = parseFloat(money) +parseFloat(famounts);
												}
											}
											var idSign = tabId.split("_");
											$("#grossId"+idSign[3]).html((parseFloat(money)+(fpNum*fsignPrices)).toFixed(2));
									}}}},
									{field:'fmModel',title:'单位',width:120,align:'center'},
									{field:'fWhetherImport',title:'是否进口',width:120,align:'center'},
									{field:'fsignPrice',title:'单价(元)',width:120,align:'center',editor:{type:'numberbox',options:{precision:2,editable:true,onChange:function(newValue,oldValue){
											var row = $("#"+tabId).datagrid('getSelected');
										    var index = $("#"+tabId).datagrid('getRowIndex',row);
											dakaibianji(index,0,tabId,editIndexPurRes);
										    var fpNum = $('#'+tabId).datagrid('getEditor',{
										 		index:index,
										 		field : 'fpNum'  
										 	});
										     var famount = $('#'+tabId).datagrid('getEditor',{
										 		index:index,
										 		field : 'famount'  
										 	});
										     var fsignPrice = parseFloat(newValue);
										 	var fpNums = parseFloat(fpNum.target.numberbox('getValue'));
										 	if(isNaN(fpNums) || isNaN(fsignPrice)){
										 		return false;
										 	}
										    $(famount.target).numberbox('setValue', (fpNums*fsignPrice).toFixed(2));
										    
											var money = 0.00;
											var rows = $('#'+tabId).datagrid('getRows');
											for (var i = 0; i < rows.length; i++) {
												if(i!=index){
													var famounts = parseFloat(rows[i].famount);
													if(isNaN(famounts)){
													 	famounts = 0;
													 }
													money = parseFloat(money) +parseFloat(famounts);
												}
											}
											var idSign = tabId.split("_");
											$("#grossId"+idSign[3]).html((parseFloat(money)+(fpNums*fsignPrice)).toFixed(2));
									}}}},
									{field:'fBrand',title:'品牌',width:120,align:'center',editor:'textbox'},
									{field:'fmSpecif',title:'型号',width:120,align:'center',editor:'textbox'},
									{field:'famount',title:'中标金额(元)',width:120,align:'center',editor:{type:'numberbox',options:{editable:false,precision:2}}},
							    ]],
					    toolbar: "#toolbar"+i
					});
					var rowss = $('#purchasing_tab_pur_'+i).datagrid('getRows');
					if(rowss==''){
						$('#purchasing_tab_pur_'+i).datagrid('appendRow',{
							fbiddingCode:rows[i].fbiddingCode,
							fpurName: '请点击此处选择',
							fpNum: 0,
							fmeasureUnit: '',
							fIsImp: '',
							fsignPrice: '',
							fBrand: '',
							fmSpecif: '',
							famount: 0.00
						});
					}
				}
			}
		}
		
	} */
	sign =1;
	$("#editResultsId").show();
	$("#addResultsId").hide();
	$("#removeitPurId").hide();
	$("#appendPurId").hide();
	editIndexPurRes = undefined;
}
function editResults(){
	$("#editResultsId").hide();
	$("#addResultsId").show();
	$("#removeitPurId").show();
	$("#appendPurId").show();
	var rows = $('#purchasing_tab_id').datagrid('getRows');
	for (var g = 0; g < rows.length; g++) {
	if (endEditingPurRes(editIndexPurRes,tabId,editIndexPurRes)) {
		$('#purchasing_tab_pur_'+g).datagrid('acceptChanges');
	}
	}
	sign =0;
}

function frIdIsNotEmpty(){
	sign =1;
	$("#editResultsId").show();
	$("#addResultsId").hide();
	$("#removeitPurId").hide();
	$("#appendPurId").hide();
}

//明细表格添加删除，保存方法
var editIndexPurRes = undefined;
function endEditingPurRes(index,id,oldIndex) {
	if (editIndexPurRes == undefined) {
		return true;
	}
	if ($('#'+id).datagrid('validateRow', oldIndex)) {
		$('#'+id).datagrid('endEdit', oldIndex);
		editIndexPurRes = undefined;
		return true;
	} else {
		return false;
	}
}

function appendPurRes() {//未配置采购类型不可添加采购清单
	 if (endEditingPurRes(index,id,oldIndex)) {
			$('#purchasing_tab_id').datagrid('appendRow', {});
			editIndexPurRes = $('#purchasing_tab_id').datagrid('getRows').length - 1;
			$('#purchasing_tab_id').datagrid('selectRow', editIndexPurRes).datagrid('beginEdit',editIndexPurRes);
		} 
}
function removeitPurRes(){
	if (editIndexPurRes == undefined) {
		return;
	}
	$('#purchasing_tab_id').datagrid('cancelEdit', editIndexPurRes).datagrid('deleteRow',editIndexPurRes);
	editIndexPurRes = undefined;
	//修改申请总额
	//setfamount(0,0);
}

function acceptPurRes() {
	if (endEditingPurRes(index,id,oldIndex)) {
		$('#'+id).datagrid('acceptChanges');
	}
}

function onClickRowPurRes(index,data) {
		var shortId = tabId;
		var indexs = editIndexPurRes;
		tabId = $(this).attr('id');
		if(tabId!=shortId){
			if (endEditingPurRes(index,shortId,indexs)) {
				$('#'+shortId).datagrid('acceptChanges');
			}
			editIndexPurRes=undefined;
			dakaibianji(index,0,tabId,index);
		}else{
			dakaibianji(index,0,tabId,editIndexPurRes);
		}
		
}
function dakaibianji(index,data,id,oldIndex){
	if (editIndexPurRes != index) {
		if (endEditingPurRes(index,id,oldIndex)) {
			$('#'+id).datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndexPurRes = index;
		} else {
			$('#'+id).datagrid('selectRow', editIndexPurRes);
		}
	}
}

//选择地址
function selectCGQDDetail(index,tabId,fbiddingCode) {
	var fpId = '${bean.fpId}';
	var win = creatFirstWin('选择-采购清单', 640, 580, 'icon-search', '/cgprocess/cgDetailedAccount?index='+index+'&tabId='+tabId+'&fpId='+fpId+'&fbiddingCode='+fbiddingCode);
	win.window('open');
}
</script>