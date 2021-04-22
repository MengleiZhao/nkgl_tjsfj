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
					<a href="${base}/quotaManage/download">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/mbxz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				
					<a href="#"  onclick="imput()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daoru1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a> 
					<a href="#" onclick="addQuotaRegist()">
						<img src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
			</tr>
			
		</table>
	</div>




	<div style="margin: 0 10px 0 10px;height: 445px;">	
		<table id="quota_regist_dg" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/quotaManage/quotaPage',
		method:'post',fit:true,pagination:true,singleSelect: true,
		selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
			<thead>
				<tr>
					<th data-options="field:'fQId',hidden:true"></th>
					<th data-options="field:'num',align:'center'" style="width: 5%">序号</th>
					<th data-options="field:'fSubName',align:'center'" style="width: 15%">政府支出经济分类</th>
					<th data-options="field:'fAmount',align:'center',formatter:listToFixed" style="width: 10%">金额[元]</th>
					<th data-options="field:'fOperateUser',align:'center'" style="width: 12%">录入人员</th>
					<th data-options="field:'fOperateTime',align:'center',formatter: ChangeDateFormat" style="width: 12%">录入日期</th>
					<th data-options="field:'fCheckStauts',align:'center',formatter:flowStautsSet" style="width: 7%">复核状态</th>
					<th data-options="field:'fCheckUser',align:'center'" style="width: 12%">复核人</th>
					<th data-options="field:'fChcekTime',align:'center',formatter: ChangeDateFormat" style="width: 12%">复核日期</th>
					<th data-options="field:'name',align:'center',formatter:CZ" style="width: 15%">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
	<script type="text/javascript">
	
		
		//点击查询
		function queryRegist() {
			$('#quota_regist_dg').datagrid('load', {
				searchData : $('#searchData').textbox('getValue'),
			});
		}

		//清除查询条件
		function clearRegistTable() {
			$("#searchData").textbox('setValue','');
			queryRegist();
		}
	
		//设置审批状态
		var c;
		function flowStautsSet(val, row) {
			c = val;
			if (val == 0) {
				return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/zc.png">&nbsp;&nbsp;' + "暂存" + '</a>';
			} else if (val == -1) {
				return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已驳回" + '</a>';
			} else if (val == -4) {
				return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/yth.png">&nbsp;&nbsp;' + "已撤回" + '</a>';
			} else if (val == 9) {
				return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/ytg.png">&nbsp;&nbsp;' + "已复核" + '</a>';
			} else {
				return '<a style="color:#666666;"><img src="'+base+'/resource-modality/${themenurl}/dsp.png">&nbsp;&nbsp;' + "待复核" + '</a>';
			}
		}
		
		//操作栏创建
		function CZ(val,row) {		
			 if(c == 0 || c == -1| c == -4) {
				 return 	'<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
					'<a href="#" onclick="rgeistDetail(' + row.fQId + ')" class="easyui-linkbutton">'+
			   		'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   		'</a>'+ '</td><td style="width: 25px">'+
					'<a href="#" onclick="rgeistEdit(' + row.fQId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/update1.png">'+
					'</a>' + '</td><td style="width: 25px">'+
					'<a href="#" onclick="deleteApply(' + row.fQId + ')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/delete1.png">'+
					'</a></td></tr></table>';
			}else if(c == 1){
				return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
				   '<a href="#" onclick="rgeistDetail(' + row.fQId + ')" class="easyui-linkbutton">'+
				   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				   '</a></td><td style="width: 25px">'+
					'<a href="#" onclick="reCall(\'quota_regist_dg\',' + row.fQId + ',\'/quotaManage/recall\')" class="easyui-linkbutton">'+
					'<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/reCall1.png">'+
					'</a></td></tr></table>';
			}else{
				return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			   		   '<a href="#" onclick="rgeistDetail(' + row.fQId + ')" class="easyui-linkbutton">'+
				   	   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
				       '</a></td></tr></table>';
			}		
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

		 //查看
		 function rgeistDetail(id) {
			var win = creatWin(' 查看', 790, 580, 'icon-search',"/quotaManage/detail?id=" + id);
			win.window('open');
		} 
		//修改
		function rgeistEdit(id) {
			var win = creatWin(' 修改', 790, 580, 'icon-search',"/quotaManage/edit?id=" + id);
			win.window('open');
	   	}
		
		 //新增页面
		function addQuotaRegist() {
			var win = creatWin('新增', 790,580, 'icon-search', '/quotaManage/add');
			win.window('open');
		}
	
		
		//删除
		function deleteApply(id) {
			if (confirm("确认删除吗？")) {
				$.ajax({
					type : 'POST',
					url : '${base}/quotaManage/delete?id=' + id,
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$.messager.alert('系统提示', data.info, 'info');
							$('#quota_regist_dg').datagrid('reload');
						} else {
							$.messager.alert('系统提示', data.info, 'error');
						}
					}
				});
			}
		}
	

		//导入
		function imput() {
			var win = creatWin('导入', 500, 200, 'icon-search', '/quotaManage/imput');
			win.window('open');
		}

		
	</script>
</body>
</html>

