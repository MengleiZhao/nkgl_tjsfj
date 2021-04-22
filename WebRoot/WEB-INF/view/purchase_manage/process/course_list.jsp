<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="cg_lendger_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
			<td class="top-table-search">采购批次编号&nbsp;
					<input id="ledger_fpCode" name="fpCode" style="width: 100px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;采购名称
					<input id="ledger_fpName" name="fpName" style="width: 100px; height:25px;" class="easyui-textbox"></input>
				<%-- &nbsp;&nbsp;采购方式&nbsp;
					<input id="ledger_fpMethodStr" name="fpMethod.code"  class="easyui-combobox" style="width: 150px; height:25px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PURCHASE_METHOD',method:'get',valueField:'code',textField:'text',editable:false" /> --%>
				<%-- &nbsp;&nbsp;组织形式
					<c:if test="${empty bean.fpId}">
						<input id="F_fOrgType" name="fOrgType.code"  class="easyui-combobox" style="width: 100px; height:25px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=CGORG_TYPE',method:'get',valueField:'code',textField:'text',editable:false" />
					</c:if>
					<c:if test="${!empty bean.fpId}">
						<input id="F_fOrgType" name="fOrgType.code"   class="easyui-combobox" style="width: 100px; height:25px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=CGORG_TYPE&selected=${bean.fOrgType.code}',method:'get',valueField:'code',textField:'text',editable:false"  />
					</c:if>
					&nbsp;&nbsp;采购方式
					<c:if test="${empty bean.fpId}">
					<input id="F_fpMethod" name="fpMethod.code"  class="easyui-combobox" style="width: 100px; height:25px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=FSCGFS',method:'get',valueField:'code',textField:'text',editable:false" />
					</c:if>
					<c:if test="${!empty bean.fOrgType.code && bean.fOrgType.code=='CGORG_TYPE_1'}">
						<input id="F_fpMethod" name="fpMethod.code"   class="easyui-combobox" style="width: 100px; height:25px;" 
						data-options="url:'${base}/lookup/lookupsJson?parentCode=JZCGFS&selected=${bean.fpMethod.code}',
						method:'get',valueField:'code',textField:'text',editable:false"  />
					</c:if>
					<c:if test="${!empty bean.fOrgType.code && bean.fOrgType.code=='CGORG_TYPE_2'}">
						<input id="F_fpMethod" name="fpMethod.code"   class="easyui-combobox" style="width: 100px; height:25px;" 
						data-options="url:'${base}/lookup/lookupsJson?parentCode=FSCGFS&selected=${bean.fpMethod.code}',
						method:'get',valueField:'code',textField:'text',editable:false"  />
					</c:if>	 --%>
					
					&nbsp;&nbsp;申报部门&nbsp;
					<input id="ledger_fDeptName" name="fDeptName" style="width: 100px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;<a href="#" onclick="queryCgLendgerApply();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearCgLendgerTable();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
		</table>
	</div>


	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="course_regist_div" class="easyui-datagrid" data-options="collapsible:true,url:'${base}/cgsqLedger/cgledgerPage',
		method:'post',fit:true,pagination:true,singleSelect: true,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fpId',hidden:true"></th>
					<th data-options="field:'number',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fpCode',align:'center'" style="width: 15%">采购批次编号</th>
					<th data-options="field:'fpName',align:'center',resizable:false,sortable:true" style="width: 11%">采购名称</th>
					<th
						data-options="field:'fpAmount',align:'right',resizable:false,sortable:true,formatter:function(value,row,index){return fomatMoney(value,2);}"
						style="width: 10%">采购金额[元]</th>
					<th
						data-options="field:'fDeptName',align:'center',resizable:false,sortable:true"
						style="width: 10%">申报部门</th>
					<th
						data-options="field:'fReqTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat"
						style="width: 10%">申报时间</th>
					<th
						data-options="field:'fUserName',align:'center',resizable:false,sortable:true"
						style="width: 10%">申请人</th>
					<!-- <th
						data-options="field:'fpMethod',align:'center',resizable:false,sortable:true"
						style="width: 10%">采购方式</th> -->
					<th
						data-options="field:'fCheckStauts',align:'center',resizable:false,sortable:true,formatter:formatPrice"
						style="width: 10%">审批状态</th>
					<th
						data-options="field:'fbidStauts',align:'center',resizable:false,sortable:true,formatter:forStatus"
						style="width: 10%">登记状态</th>
					<th
						data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ"
						style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>



</div>
<script type="text/javascript">
	//设置登记状态
	function forStatus(val){
		if(val==1){
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已登记" + '</a>';
		}
		return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待登记" + '</a>';
	}
	//显示搜索
	function spread() {
		$('#helpTr').css("display", "");
		$('#retract').css("display", "");
		$('#spread').css("display", "none");
	}
	//隐藏搜索
	function retract() {
		$('#helpTr').css("display", "none");
		$('#retract').css("display", "none");
		$('#spread').css("display", "");
	}
	
		//点击查询
		function queryCgLendgerApply() {
			$('#course_regist_div').datagrid('load', {
				fpCode:$('#ledger_fpCode').val(),
				fpName:$('#ledger_fpName').val(),
				forgtype:$('#F_fOrgType').val(),
				fDeptName:$('#ledger_fDeptName').val()
			});
		}
		//清除查询条件
		function clearCgLendgerTable() {
			$("#ledger_fpCode").textbox('setValue','');
			$("#ledger_fpName").textbox('setValue','');
			$("#F_fOrgType").combobox('setValue','');
			$("#ledger_fDeptName").textbox('setValue','');
			$('#course_regist_div').datagrid('load',{//清空以后，重新查一次
			});
		}
		
		/* //根据选择的组织形式，来请求采购方式
		$("#F_fOrgType").combobox({
			onChange: function (n,o) {
				if(n==""||n==null||n=="undefined"){
					 $('#F_fpMethod').combobox('setValues','');
				}
				if(n=="CGORG_TYPE_1"){	//集中采购
					 $('#F_fpMethod').combobox({
						    url:'${base}/lookup/lookupsJson?parentCode=JZCGFS&selected=${bean.fpMethod.code}',
						});
				}
				if(n=="CGORG_TYPE_2"){	//分散采购
					 $('#F_fpMethod').combobox({
						    url:'${base}/lookup/lookupsJson?parentCode=FSCGFS&selected=${bean.fpMethod.code}',
					});
				}
				
			}
		}); */
		
	//设置审批状态
	var c;
	function formatPrice(val, row) {
		c = val;
		if (val == 0) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">' + " 暂存" + '</a>';
		} else if (val == -1) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">' + " 已退回" + '</a>';
		} else if (val == 9) {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">' + " 已审批" + '</a>';
		} else {
			return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">' + " 待审批" + '</a>';
		}
	}

	//操作栏创建
	function CZ(val, row) {
		if (row.fbidStauts==0) {
			return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="cgsq_course_regist(' + row.fpId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/dengji1.png">'+
					'</a></td></tr></table>';
		}else{
			return  '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				'<a href="#" onclick="course_regist_detatil(' + row.fpId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				'</a></td><td style="width: 25px">'+
				'<a href="#" onclick="course_regist_update(' + row.fpId + ')" class="easyui-linkbutton">'+
				'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
				'</a>' + '</td></tr></table>';
		}
	}
	//查看
	function course_regist_detatil(id) {
		var win = creatSecondWin('采购过程明细', 720, 580, 'icon-search',"/cgprocess/detail?id=" + id);
		win.window('open');

	}
	//修改
	function course_regist_update(id) {
		var win = creatSecondWin('采购过程-修改', 720, 580, 'icon-search',"/cgprocess/edit?id=" + id);
		win.window('open');
   }
	//跳转登记页面
	function cgsq_course_regist(id) {
		var win = creatSecondWin('采购过程-登记', 720, 580, 'icon-search', "/cgprocess/add?id="+ id);
		win.window('open');
	}		
</script>
</body>
</html>

