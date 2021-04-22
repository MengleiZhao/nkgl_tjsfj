<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search">合同编号&nbsp;
						<input id="c_fcCode" name="" onchange="CheckYou()" style="width: 150px;height:25px;" class="easyui-textbox"></input>
						&nbsp;&nbsp;合同名称&nbsp;
						<input id="c_fcTitle" name=""  style="width: 150px;height:25px;" class="easyui-textbox"></input>
						<a href="javascript:void(0)"  onclick="enforcing_choose_query();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')"></a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)"  onclick="enforcing_choose_clearTable();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu2.png')"onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/qingchu1.png')"></a>
					</td> 
				</tr>
			</table>           
		</div>
		<div class="list-table" style="height:420px">
			<table id="choose_enforcing_cont_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/Enforcing/contractList',
			method:'post',fit:true,pagination:true,singleSelect: true,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'fId_R',hidden:true"></th>
						<th data-options="field:'number',align:'center'" width="5%">序号</th>
						<th data-options="field:'fcCode',align:'center'" width="20%">合同编号</th>
						<th data-options="field:'fcTitle',align:'center',resizable:false,sortable:true" width="25%">合同名称</th>
						<th data-options="field:'fcAmount',align:'center',resizable:false,sortable:true" width="16%">合同金额(元)</th>
						<th data-options="field:'fContractor',align:'center',resizable:false,sortable:true" width="20%">供应商名称</th>
						<th data-options="field:'fSignTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" width="15%">合同签订日期</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="text-align: left;height: 20px">
			<span style="color:#ff6800">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;✧温馨提示：双击数据选择</span>
		</div>
	</div>
	


<script type="text/javascript">

$("#choose_enforcing_cont_dg").datagrid({
	 onDblClickRow:function(index, row){
		var row = $('#choose_enforcing_cont_dg').datagrid('getSelected');
		var selections = $('#choose_enforcing_cont_dg').datagrid('getSelections');
		if (row != null && selections.length == 1) {
			$.ajax({
		        type : "POST",
		        url : base+'/Enforcing/getReceivplanid',
		        async : 'false',
		        data : {
		        	payId: row.fcId
		        },
		        success : function(data) {
					$('#f_receivplanids').val(data);
		        },
		        error : function(e){
		            console.log(e.status);
		            console.log(e.responseText);
		        }
		    });
			
			$('#selectContId').val('');
			$('#selectUptId').val('');
			
			$.ajax({
			    type:'post',
			    async:false,
			    dataType:'json',
			    url:base+'/Formulation/isUnderReview?id='+row.fcId,
			    success:function (data){
			    	if(data.success){
			    		//清空表单
			    		$('#f_checkacceptid').val('');
			    		$('#checkacceptshow').tagbox().tagbox('setValues','');
			    		$('#f_checkFixedAssetid').val('');
			    		$('#checkFixedAssetshow').tagbox().tagbox('setValues','');
			    		$('#f_checkintangibleAssetid').val('');
			    		$('#checkintangibleAssetshow').tagbox().tagbox('setValues','');
			    		$('#checkaccept_tab').hide();
			    		$('#checkFixedAsset_tab').hide();
			    		$('#checkintangibleAsset_tab').hide();
			    		$('#checkWarehouseWarrant_tab').hide();
			    		
			    		$('#f_contCode').textbox('setValue',row.fcCode);
						$('#f_fcTitle').textbox('setValue',row.fcTitle);
						$('#f_payId').val(row.fcId);
						$('#contractUpdateStatus').val(row.fUpdateStatus);
						$('#uptId').val(data.info);
						
						var url = base+'/Change/getReceivPlan?id='+row.fcId;
						$('#enfircing_payplan_dg').datagrid('reload',url);
						var payerUrl = base+'/Enforcing/getSignInfoByCondition?fContId='+row.fcId;
						$('#payer_info_tab').datagrid('reload',payerUrl);
						closeSecondWindow();
			    	}else{
			    		$.messager.alert('系统提示', data.info, 'info');
			    	}
			    }
			});
		} else {
			$.messager.alert('系统提示', '请选择一条数据！', 'info');
		}
	}
});
function enforcing_choose_query(){  
	$('#choose_enforcing_cont_dg').datagrid('load',{ 
		fcCode:$('#c_fcCode').textbox('getValue'),
		fcTitle:$('#c_fcTitle').textbox('getValue'),
	} ); 
}
function enforcing_choose_clearTable(){  
	$('#c_fcCode').textbox('setValue',null);
	$('#c_fcTitle').textbox('setValue',null);
	enforcing_choose_query();
}

</script>
</body>
</html>

