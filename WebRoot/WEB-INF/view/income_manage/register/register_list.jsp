<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="register_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">
					<%-- 登记单号&nbsp;
					<input id="register_code" name="" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;收入科目&nbsp;
					<input id="income_name" name="" style="width: 150px; height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;到账类型&nbsp;
					<input id="arrive_faccty" name="" style="width: 150px;height:25px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_TYPE',method:'POST',valueField:'code',textField:'text',editable:false"  class="easyui-combobox"></input>
					&nbsp;&nbsp;到账方式&nbsp;
					<input id="arrive_faccw" name="" style="width: 150px;height:25px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_METHOD',method:'POST',valueField:'code',textField:'text',editable:false" class="easyui-combobox"></input>
					 --%>
					<input id="searchData" name="searchData" data-options="prompt:'请输入查询内容'" style="width: 300px;height:25px;" class="easyui-textbox"></input>
					&nbsp;&nbsp;
					<a href="#" onclick="queryRegist();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearRegistTable();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				
				</td>
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="addRegist()">
						<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				<%-- <td class="top-table-td1">登记单号：</td>
				<td	class="top-table-td2">
					<input id="register_code" name="" style="width: 150px; height:25px;" class="easyui-textbox"></input>
				</td>

				<td class="top-table-td1">收入科目：</td>
				<td class="top-table-td2">
					<input id="income_name" name="" style="width: 150px; height:25px;" class="easyui-textbox"></input>
				</td>

				<!-- <td class="top-table-td1">到账项目：</td>
				<td class="top-table-td2">
					<input id="arrive_name" name="" style="width: 150px;height:25px;" class="easyui-textbox"></input>
				</td> -->
				
				<td style="width: 30px;"></td>				
				<td style="width: 26px;">
					<a href="#" onclick="queryRegist();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>				
				<td style="width: 8px;"></td>			
				<td style="width: 26px;">
					<a href="#" onclick="clearRegistTable();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>								
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="addRegist()">
						<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td> --%>
			</tr>
			
		</table>
	</div>



	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="registerTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/srregister/registerPage',
		method:'post',fit:true,pagination:true,singleSelect: true,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fincomeId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fincomeNum',align:'center'" style="width: 15%">登记单号</th>
					<th data-options="field:'indexName',align:'center',resizable:false,sortable:true" style="width: 10%">收入科目</th>
					<th data-options="field:'fregisterPerson',align:'center',resizable:false,sortable:true" style="width: 8%">登记人</th>
					<th data-options="field:'fregisterAmount',align:'right',resizable:false,sortable:true" style="width: 10%">收入金额[元]</th>
					<th data-options="field:'fregisterTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 10%">登记时间</th>
					<th data-options="field:'fregisterDepart',align:'center',resizable:false,sortable:true" style="width: 8%">登记部门</th>
					<th data-options="field:'faccty',align:'center',resizable:false,sortable:true" style="width: 8%">到账类型</th>
					<th data-options="field:'faccount',align:'center',resizable:false,sortable:true" style="width: 10%">开户行</th>
					<th data-options="field:'faccw',align:'center',resizable:false,sortable:true" style="width: 8%">到账方式</th>
					<th data-options="field:'name',align:'center',resizable:false,sortable:true,formatter:CZ" style="width: 10%">操作</th>
				</tr>
			</thead>
		</table>
	</div>



</div>
	<script type="text/javascript">
	
	//分页样式调整
	$(function(){
		var pager = $('#registerTab').datagrid().datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			layout:['sep','first','prev','links','next','last'/* ,'refresh' */]
		});
	});
	
		//点击查询
		function queryRegist() {
			//alert($('#register_code').val());
			var searchData="searchData";
			$('#registerTab').datagrid('load', {
				/* fincomeNum : $('#register_code').textbox('getValue'),
				indexName : $('#income_name').textbox('getValue'),
				faccty : $('#arrive_faccty').combobox('getValue'),
				faccw : $('#arrive_faccw').combobox('getValue'), */
				searchData:$("#"+searchData).textbox('getValue').trim(),
			});                            
		}

		 //新增页面
		function addRegist() {
			var win = creatWin('新增', 780,580, 'icon-search', '/srregister/add');
			win.window('open');
		}

		//操作栏创建
		function CZ(val,row) {		
				return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
						'<a href="#" onclick="detail(' + row.fincomeId + ')" class="easyui-linkbutton">'+
				   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				   		'</a>'+ '</td><td style="width: 25px">'+
						'<a href="#" onclick="CF_update(' + row.fincomeId + ')" class="easyui-linkbutton">'+
						'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
						'</a>' + '</td><td style="width: 25px">'+
						'<a href="#" onclick="CF_delete(' + row.fincomeId + ')" class="easyui-linkbutton">'+
						'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
						'</a></td></tr></table>';
			
		}
		function showA(obj){
			obj.src=base+'/resource-modality/${themenurl}/list/select1.png';
		}
		function showB(obj){
			obj.src=base+'/resource-modality/${themenurl}/select2.png';
		}
		function showC(obj){
			obj.src=base+'/resource-modality/${themenurl}/list/update1.png';
		}
		function showD(obj){
			obj.src=base+'/resource-modality/${themenurl}/update2.png';
		}
		function showE(obj){
			obj.src=base+'/resource-modality/${themenurl}/list/delete1.png';
		}
		function showF(obj){
			obj.src=base+'/resource-modality/${themenurl}/delete2.png';
		}

		 //查看
		 function detail(id) {
			var win = creatWin('查看', 740,580, 'icon-search',"/srregister/detail?id=" + id);
			win.window('open');
		} 
		//修改
		function CF_update(id) {
			var win = creatWin('修改', 740,580, 'icon-search',"/srregister/edit?id=" + id);
			win.window('open');
	   }
		
		 //删除
		function CF_delete(id) {
			if (confirm("确认删除吗？")) {
				$.ajax({
					type : 'POST',
					url : '${base}/srregister/delete?id='+id,
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$.messager.alert('系统提示', data.info, 'info');
							$('#registerTab').datagrid("reload");
						} else {
							$.messager.alert('系统提示', data.info, 'error');
						}
					}
				});
			}
		} 
	
		//清除查询条件
		function clearRegistTable() {
			/* $("#register_code").textbox('setValue','');
			$("#income_name").textbox('setValue','');
			$("#arrive_faccty").combobox('setValue','');
			$("#arrive_faccw").combobox('setValue',''); */
			var searchData="searchData";
			/* $("#"+gCode).textbox('setValue','');
			$("#"+gName).textbox('setValue','');
			$("#"+reqTime1).datebox('setValue','');
			$("#"+reqTime2).datebox('setValue',''); */
			$("#"+searchData).textbox('setValue','');
			queryRegist();
		}
		
	</script>
</body>
</html>

