<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
   
	<div data-options="region:'center'">
 <div class="list-table-tab" style="height: 100%; width:98%; margin-left: 1%; border: none;position: relative; ">
		<div class="tab-wrapper" id="quota-tab" >
			<ul class="tab-menu">
				<li class="active" onclick="reloadbasic()">项目支出</li>
		    	<li onclick="reloadpro()">项目冻结</li>
			</ul>
		  
			<div class="tab-content">
				<div style="height: 455px;margin-left: 1%;">
					<table class="easyui-datagrid" id="index-xdmx" 
		data-options="collapsible:true,url:'${base}/cockDetail/searchZCList?code=${bean.FProCode}',
		method:'post',fit:true,pagination:true,singleSelect: false,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns:true">
			<thead>
				<tr>
					<th data-options="field:'lId',hidden:true"></th>
					<th data-options="field:'url',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 6%">序号</th>
					<th data-options="field:'billsCode',align:'left'" style="width: 24%">单据编号</th>
					<th data-options="field:'fType',align:'center',formatter: zcmxYT" style="width: 12%">支出事项</th>
					<th data-options="field:'amount',align:'right',formatter:getPlus" style="width: 13%">支出金额(元)</th>
					<th data-options="field:'time',align:'center',formatter: zcsjFormat" style="width: 12%">经办时间</th>
					<th data-options="field:'username',align:'center'" style="width: 8%">经办人</th>
					<th data-options="field:'department',align:'center'" style="width: 17%">经办部门</th>
					<th data-options="field:'cz',align:'center',formatter:CZ" style="width: 9%">操作</th>
				</tr>
			</thead>
		</table>
				</div>
				
			   	<div style="height: 455px;margin-left: 1%;">
				    <table class="easyui-datagrid" id="index-xdmxs" 
		data-options="collapsible:true,url:'${base}/cockDetail/frozenAList?code=${budgetIndexMgr}',
		method:'post',fit:true,pagination:true,singleSelect: false,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns:true">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true"></th>
					<th data-options="field:'col5',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 6%">序号</th>
					<th data-options="field:'col1',align:'left'" style="width: 20%">编号</th>
					<th data-options="field:'col2',align:'center',formatter: zcmxYTS" style="width: 12%">冻结事项</th>
					<th data-options="field:'col3',align:'right'" style="width: 13%">冻结金额(元)</th>
					<th data-options="field:'col4',align:'center'" style="width: 20%">摘要</th>
					<th data-options="field:'col7',align:'center'" style="width: 8%">经办人</th>
					<th data-options="field:'col6',align:'center'" style="width: 17%">经办部门</th>
					<th data-options="field:'cz',align:'center',formatter:CZS" style="width: 5%">操作</th>
				</tr>
			</thead>
		</table>
				</div>
			</div>
		</div>
	</div>
  </div>
	<script type="text/javascript">
	//type为指标类型1位基本2为项目
	var type=1;
	function reloadbasic() {
		$('#index-xdmx').datagrid('reload');
		type=1;
		
		/* $("#quota_list_top1").show();
		$("#quota_list_top2").hide(); */
	}
	function reloadpro() {
		$('#index-xdmxs').datagrid('reload');
		type=2;
		
		/* $("#quota_list_top1").hide();
		$("#quota_list_top2").show(); */
	}
	flashtab("quota-tab");
	//操作栏创建
	function CZ(val, row) {
		return'<table style="margin:0 auto"><tr style="width: 75px;height:20px;"><td style="width: 25px;">'+
		   '<a href="#" onclick="editZCMX(\''+row.url+'\')" class="easyui-linkbutton">'+
		   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		   '</a></td></tr></table>';
	}
	function CZS(val, row) {
		return'<table style="margin:0 auto"><tr style="width: 75px;height:20px;"><td style="width: 25px;">'+
		   '<a href="#" onclick="editZCMXS(\''+row.id+'\',\''+row.col2+'\')" class="easyui-linkbutton">'+
		   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
		   '</a></td></tr></table>';
	}
	function zcmxYT(val, row) {
		//1、收入	2、还款	3、直接报销		4、申请报销		5、借款	6、采购支付		7、合同报销
		if(val==1) {
			return "收入";
		} else if(val==2) {
			return "还款";
		} else if(val==3) {
			return "直接报销";
		} else if(val==4) {
			return "申请报销";
		} else if(val==5) {
			return "借款";
		} else if(val==6) {
			return "采购支付";
		} else if(val==7) {
			return "合同报销";
		}
	}

	function editZCMX(url) {
		var type = url.substring(1,8);
		if(type=='ull') {
			alert("没有传入地址");
		} else if(type=='project'){
			var win = creatWin('查看', 1230,630, 'icon-search',url);
			win.window('open');
		}  else if(type=='Enforci') {
			var win = creatCockFirstWin('查看', 970, 580,'icon-search',url);
			win.window('open');
		}  else {
			var win = creatWin('查看', 960, 580, 'icon-search',url);
			win.window('open');
		}
	}
	function editZCMXS(id,type) {
		 if(type=='1' || type=='6'){
			var win = creatWin('查看', 770,630, 'icon-search',"/apply/edit?id="+id+"&editType=0&applyType="+type+"");
			win.window('open');
		}  else if(type=='HTFL-01') {
			var win = creatWin('查看', 970, 580,'icon-search',"/Enforcing/detail?id="+id+"&fPlanId=11");
			win.window('open');
		}  else  if(type=='10') {
			var win = creatWin('查看', 970, 580,'icon-search',"/loan/edit?editType=0&id="+id+"");
			win.window('open');
		}  else {
			var win = creatWin('查看', 1070, 580, 'icon-search',"/apply/edit?id="+id+"&editType=0&applyType="+type+"");
			win.window('open');
		}
	}
	function zcmxYTS(val, row) {
		//1-通用申请\r\n            2-会议申请\r\n            3-培训申请\r\n            4-差旅申请\r\n            5-接待申请
		if(val==0){
			return "直接报销";
		}else if(val==1) {
			return "通用申请";
		} else if(val==2) {
			return "会议申请";
		} else if(val==3) {
			return "培训申请";
		} else if(val==4) {
			return "差旅申请";
		} else if(val==5) {
			return "接待申请";
		} else if(val==6) {
			return "公务用车";
		} else if(val==7) {
			return "公务出国";
		}else if(val==10){
			return "借款申请";
		}else if(val=="HTFL-01"){
			return "合同申请";
		}
	}
	function add0(m){return m<10?'0'+m:m }
	//时间格式化
	function zcsjFormat(val) {
	    if(val==null){
	  	  return "";
	    }
	    var time = new Date(val);
	    var y = time.getFullYear();
	    var m = time.getMonth()+1;
	    var d = time.getDate();
	    var h = time.getHours();
	    var mm = time.getMinutes();
	    var s = time.getSeconds();
	   /*  return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s); */
	   return y+'-'+add0(m)+'-'+add0(d); 
	}
	//金额转为正数
	function getPlus(val){
		if(val != null && val != ""){
			return Math.abs(val.toFixed(2));
		} else {
			val=0;
			return  Math.abs(val.toFixed(2));
		}
	}
	</script>