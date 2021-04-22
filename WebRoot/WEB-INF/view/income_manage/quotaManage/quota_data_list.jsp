<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">
	<div class="list-div">
		<div style="height: 10px;background-color:#f0f5f7 "></div>





		<div style="margin: 0 10px 0 10px;height: 505px;">
			<table id="quota_data_dg" class="easyui-datagrid"
				data-options="collapsible:true,url:'${base}/quotaManage/dataListJson',
		method:'post',fit:true,pagination:false,singleSelect: true,onLoadSuccess:freezeRowData,
		selectOnCheck: false,checkOnSelect:false,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
				<thead>
					<tr>
						<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
						<th data-options="field:'fSubName',align:'center'"
							style="width: 25%">政府支出经济分类</th>
						<th data-options="field:'skAmount',align:'center',formatter:listToFixed"
							style="width: 24%">收款额度[元]</th>
						<th data-options="field:'bxAmount',align:'center',formatter:listToFixed"
							style="width: 24%">报销额度[元]</th>
						<th data-options="field:'kyAmount',align:'center',formatter:getKyAmount"
							style="width: 23%">可用额度[元]</th>
					</tr>
				</thead>
			</table>
		</div>



	</div>
	<script type="text/javascript">
	function getKyAmount(index,row){
		var kyAmount = parseFloat(row.skAmount)- parseFloat(row.bxAmount);
		return listToFixed(kyAmount);
	}
	
	function freezeRowData(){
		$('#quota_data_dg').datagrid('freezeRow',0);
	}
	</script>
</body>
</html>

