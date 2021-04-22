<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" class="queryth">
						<%-- &nbsp;&nbsp;姓名&nbsp;
						<input id="query_name" style="width: 150px;height:25px;" class="easyui-textbox"/>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="queryCF();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						<a href="#" onclick="clearTable();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a> --%>
						<a href="#" onclick="saveuser();" style="float: right;margin-right: 20px">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
			</table>           
		</div>
			<div class="list-table">
			<table id="choose_user_dg_s"  class="easyui-datagrid"
			data-options="collapsible:true,
			url: '${base}/cgconfplangl/mingxi?id=${fpId}',
			method:'post',
			fit:true,
			pagination:false,
			singleSelect: false,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'mainId',hidden:true"></th>
						<th data-options="field:'fpId',hidden:true"></th>
						<th data-options="field:'fIsImp',hidden:true"></th>
						<th data-options="field:'fpurName',align:'center'" width="20%">商品名称</th>
						<th data-options="field:'fnum',align:'center'" width="18%">数量</th>
						<th data-options="field:'fmeasureUnit',align:'center'" width="18%">单位</th>
						<th data-options="field:'funitPrice',align:'center'" width="18%">单价</th>
						<th data-options="field:'fsumMoney',align:'center'" width="25%">金额</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">

//查询
function queryCF() {
	$('#choose_user_dg_s').datagrid('load',{
		name:$('#query_name').textbox('getValue').trim(),
	//	gName:$("#query_dept").textbox('getValue').trim(),
	});
}

//清除查询条件
function clearTable() {
	$('#query_name').textbox('setValue','');
	//$("#query_dept").textbox('setValue','');
	$('#choose_user_dg_s').datagrid('load',{});
}

function rolesreturn(val,row){//1：市级，2：局级，3：其他人员
	if(1==val){
		return '市级';
	}else if(2==val){
		return '局级';
	}else if(3==val){
		return '其他人员';
	}
}

function isRepeat(arr) {

	  	var isTrue = true;   //作为是否一样的标识
	   for(var i = 0;i<arr.length;i++){
	  		if(arr.indexOf(arr[i])!=0){      //用indexOf来判断是否一样 
	  			isTrue = false;
	  			break;
	  		}
	  	}
	   return isTrue;; 
	}
function saveuser(){
	
	var rowindex  = '${index}';
	var fbiddingCode  = '${fbiddingCode}';
	var tabId  = '${tabId}';
	var tabId_index = tabId.split("_");
	var indexs = tabId_index[3];
	var rowSelect = $('#choose_user_dg_s').datagrid('getSelections');
	var rows = $('#'+tabId).datagrid('getRows');
	for (var i = rows.length-1; i >= 0; i--) {
		$('#'+tabId).datagrid('deleteRow',i);
		editIndexPurRes = undefined;
	}
	
		var fsumMoneys = 0.00;
	for (var j = 0; j < rowSelect.length; j++) {
		$('#'+tabId).datagrid('appendRow',{
			fbiddingCode:fbiddingCode,
			mainId: rowSelect[j].mainId,
			fmName: rowSelect[j].fpurName,
			fmModel: rowSelect[j].fmeasureUnit,
			fWhetherImport: rowSelect[j].fIsImp,
			fBrand: rowSelect[j].fBrand,
			fmSpecif: rowSelect[j].fmSpecif,
		});
		fsumMoneys =fsumMoneys+parseFloat(rowSelect[j].fsumMoney);
	}
	$("#grossId"+indexs).html(fsumMoneys);
	
	closeFirstWindow();
}
</script>
</body>
</html>