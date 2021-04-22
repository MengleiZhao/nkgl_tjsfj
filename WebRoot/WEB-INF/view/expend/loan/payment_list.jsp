<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>



<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	
	<div class="list-top">
		<table class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search" class="queryth">
					<input id="searchTitle" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"/>
					<!-- &nbsp;&nbsp;借款单编号&nbsp;
					<input id="payment_list_code" style="width: 150px;height:25px;" class="easyui-textbox"/>
					
					&nbsp;&nbsp;借款单摘要&nbsp;
					<input id="payment_list_name" style="width: 150px;height:25px;" class="easyui-textbox"/> -->
					
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryPaym();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearPaym();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				
			
			</tr>
		</table>  
	</div>
	
	<div class="list-table">
		<table id="capitalTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/srcapital/srcapitalPage',
		method:'post',fit:true,pagination:true,singleSelect: true,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'lId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'lCode',align:'center',resizable:false" style="width: 20%">借款单编号</th>
					<th data-options="field:'loanPurpose',align:'center',resizable:false,formatter:formatCellTooltip" style="width: 15%">借款单摘要</th>
					<th data-options="field:'lAmount',align:'center',resizable:false,formatter:listToFixed" style="width: 10%">借款金额[元]</th>
					<th data-options="field:'reqTime',align:'center',resizable:false,formatter: ChangeDateFormat" style="width: 14%">申请时间</th>
					<th data-options="field:'estChargeTime',align:'center',resizable:false,formatter: ChangeDateFormat" style="width: 14%">预计冲账时间</th>
					<th data-options="field:'frepayStatus',align:'center',resizable:false,formatter:flowStautsSet" style="width: 10%">还款状态</th>
					<th data-options="field:'payflowStauts',align:'center',resizable:false,formatter:payFlowStautsSet" style="width: 10%">审批状态</th>
					<th data-options="field:'name',align:'center',resizable:false,formatter:CZ" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
function ChangeDateFormat(val) {
	var t, y, m, d, h, i, s;
    if(val==null){
  	  return "";
    }
    t = new Date(val)
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) ;
}
//设置还款状态
var c;
function flowStautsSet(val, row) {
		c = row.frepayStatus;
		if( c == 9){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已还款" + '</a>';
		}else{
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "待还款" + '</a>';
		}
		/* if (c == null || row.frepayStatus == -9) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "待还款" + '</a>';
		} else if (row.frepayStatus == -4){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "待还款 已撤回" + '</a>';
		}else if(row.frepayStatus == 0){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "待还款 暂存" + '</a>';
		} else if (c == -1) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已退回" + '</a>';
		} else if (c == 9) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已还款" + '</a>';
		} else {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待审定" + '</a>';
		} */
}
var cc;
function payFlowStautsSet(val , row){
	cc = row.payflowStauts;
	if( cc == 0){
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "暂存" + '</a>';
	}else if (cc == -1){
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已退回" + '</a>';
	}else if (cc == -4){
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "已撤回" + '</a>';
	}else if (cc == 9 ){
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已审批" + '</a>';
	}else if (cc == -9 && row.frepayStatus != 9){
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "未审批" + '</a>';
	}else if (cc == -9 && row.frepayStatus == 9){
		return '<a style="color:#666666;">&nbsp;&nbsp;' + "" + '</a>';
	}else{
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待审批" + '</a>';
	}
}

//操作栏创建
/* function CZ(val, row) {
	//申请列表
	if(${menuType}=='1'){
		if(c =="0" || c =="-1" || c =="-4") {
			//修改、删除、查看
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="detailPaym(' + row.id + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a>'+ '</td><td style="width: 25px">'+
			'<a href="#" onclick="editPaym(' + row.id + ')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
			'</a>' + '</td><td style="width: 25px">'+
			'<a href="#" onclick="deletePayment(' + row.id + ')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
			'</a></td></tr></table>';
		} else if(c =="9"){
			//查看
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="detailPaym(' + row.id + ')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			'</a>'+ '</td><td style="width: 25px">'+
			'</a></td></tr></table>';
		}else{
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			'<a href="#" onclick="detailPaym(' + row.id + ')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			'</a>'+ '</td><td style="width: 25px">'+
			'</a></td><td style="width: 25px">'+
			'<a href="#" onclick="reCall(\'dg_payment_list\',' + row.id + ',\'/payment/paymentReCall\')" class="easyui-linkbutton">'+
			'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
			'</a>'+ '</td><td style="width: 25px">'+
			'</a></td></tr></table>';
		}
	} */
	
	function CZ(val,row) {
		if(row.frepayStatus == 9 && row.payflowStauts != -9){
			return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		   		   '<a href="#" onclick="detailHK(' + row.lId + ',0)" class="easyui-linkbutton">'+
			   	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				   '</a>'+ '</td></tr></table>';
		}else if(row.frepayStatus == 9 && row.payflowStauts == -9){
			return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
	   		   '<a href="#" onclick="detailCX(' + row.lId + ',0)" class="easyui-linkbutton">'+
		   	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   '</a>'+ '</td></tr></table>';
		}else{
			if(row.payflowStauts == 0 || row.payflowStauts == -1 || row.payflowStauts == -4){
				return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="detailHK(' + row.lId + ' ,0)" class="easyui-linkbutton">'+
			   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   		'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="editLoan(' + row.lId + ' ,1)" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
					'</a></td></tr></table>';
			}else if(row.payflowStauts == -9){
				return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="editLoan(' + row.lId + ',0)" class="easyui-linkbutton">'+
			   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   		'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="addPayment(' + row.lId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/repayment1.png">'+
					'</a></td></tr></table>';
			}else if(row.payflowStauts == 9){
				return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		   		   '<a href="#" onclick="detailHK(' + row.lId + ',0)" class="easyui-linkbutton">'+
			   	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				   '</a>'+ '</td></tr></table>';
			}else{
				return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="detailHK(' + row.lId + ',0)" class="easyui-linkbutton">'+
			   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   		'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="reCall(\'capitalTab\',' + row.lId + ',\'/payment/paymentReCall\')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
					'</a></td></tr></table>';
			}
		}
		/*  if(row.frepayStatus == null || row.frepayStatus == -9) {//待还款的可以查看和还款
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="editLoan(' + row.lId + ',0)" class="easyui-linkbutton">'+
			   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   		'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="addPayment(' + row.lId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/repayment1.png">'+
					'</a></td></tr></table>';
		}else if(row.frepayStatus == 0 || row.frepayStatus == -1 || row.frepayStatus == -4 ){//暂存和退回和撤回  可以查看修改
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="detailHK(' + row.lId + ' ,0)" class="easyui-linkbutton">'+
			   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   		'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="editLoan(' + row.lId + ' ,1)" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
					'</a></td></tr></table>';
		}else if(row.frepayStatus  == 1) {
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="detailHK(' + row.lId + ',0)" class="easyui-linkbutton">'+
			   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   		'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="reCall(\'capitalTab\',' + row.lId + ',\'/payment/paymentReCall\')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
					'</a></td></tr></table>';
		}else{//待审定和已还款的只能查看
			return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		   		   '<a href="#" onclick="detailHK(' + row.lId + ',0)" class="easyui-linkbutton">'+
			   	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				   '</a>'+ '</td></tr></table>';
		}		 */
	}
	
	
	/* //审批列表
	if (${menuType}=='2') {
		return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
		'<a href="#" onclick="checkPayment(' + row.id + ',1)" class="easyui-linkbutton">'+
		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/check1.png">'+
		'</a></td></tr></table>';
	}
} */
function exportHtml(id) {
	window.open(base+"/exportApplyAndReim/paymentExprot?id="+ id);
}
	 
//新增页面
function addPayment(id) {
	var win = creatFirstWin('还款登记申请', 1080, 580, 'icon-search', '/payment/add?id='+id);
	win.window('open');
}
//查看页面
function detailHK(id) {
	var win = creatFirstWin('还款登记申请', 1115, 600, 'icon-search', '/payment/detailHK?id='+id);
	win.window('open');
}
//冲销完查看页面
function detailCX(id) {
	var win = creatFirstWin('还款登记申请', 800, 600, 'icon-search', '/payment/detailHK?cxIndex=1&id='+id);
	win.window('open');
}
//删除
function deletePayment(id) {
	if (confirm("确认删除吗？")) {
		$.ajax({
			type : 'POST',
			url : '${base}/payment/delete/'+id,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#dg_payment_list').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}

function editPaym(id) {
	var win = creatFirstWin('还款登记-修改 ', 1115, 600, 'icon-search', "/payment/edit/"+ id);
	win.window('open');
}
function detailPaym(id) {
	var win = creatFirstWin('还款登记明细 ', 1115, 600, 'icon-search', "/payment/detail/"+ id);
	win.window('open');
}

function editLoan(id,type) {
	/*type为修改或查看1位修改，0位查看  */
	var title = null;
	if(type==1){
		var win = creatFirstWin('还款登记-修改 ', 1115, 600, 'icon-search', "/payment/edit/"+ id);
		win.window('open');
	}else {
		title = "借款明细";
		var win = creatWin(title, 1115, 600, 'icon-search', "/loan/edit?id="+ id +"&editType="+ type+"&indexType=1");
		win.window('open');
	}
}
//查询
function queryPaym() {
	$("#capitalTab").datagrid('load',{
		searchTitle:$("#searchTitle").textbox('getValue').trim(),
		/* lCode:$("#payment_list_code").textbox('getValue').trim(),
		loanPurpose:$("#payment_list_name").textbox('getValue').trim(), */
	});

}
//清除查询条件
function clearPaym() {
	$("#searchTitle").textbox('setValue',''),
	/* $("#payment_list_code").textbox('setValue',''),
	$("#payment_list_name").textbox('setValue',''), */
	$("#capitalTab").datagrid('load',{});
}

//审批页面跳转
function checkPayment(id){
	var win = creatWin('审批', 1115, 600, 'icon-search', "/payment/check/"+ id);
	win.window('open');
}


$("#loan_list_top_estChargeTime1").datebox({
    onSelect : function(beginDate){
        $('#loan_list_top_estChargeTime2').datebox().datebox('calendar').calendar({
            validator: function(date){
                return beginDate <= date;
            }
        });
    }
});
</script>
</body>