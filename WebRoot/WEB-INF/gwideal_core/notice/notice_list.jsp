<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
					<tr>
						<td class="top-table-td1">公告标题</td>
						<td	class="top-table-td2">
							<input type="text" style="width: 150px;height:25px;"  size="15" class="easyui-textbox" maxlength="10" id="notice_noticeTitle"/>
						</td>
						
						<td class="top-table-td1">发布人</td>
						<td	class="top-table-td2">
							<input type="text" style="width: 150px;height:25px;"  size="15" class="easyui-textbox" maxlength="10" id="notice_releaseUser"/>
						</td>
						
						<td style="width: 30px;"></td>				
						<td style="width: 26px;">
							<a href="#" onclick="queryNotice();">
								<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>
						</td>				
						<td style="width: 8px;"></td>			
						<td style="width: 26px;">
							<a href="#" onclick="clearNotice();">
								<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>
						</td>								
						<td align="right" style="padding-right: 10px">
							<a href="#" onclick="addNotice()">
								<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/xz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="list-table">
			<table id="noticeTab" class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/notice/noticePage',
			method:'post',fit:true,pagination:true,toolbar:'#noticeHelpTab',singleSelect: true,
			selectOnCheck: true,checkOnSelect: true,remoteSort:true,nowrap:false,pageSize:10">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'noticeId',hidden:true"></th>
						<th data-options="field:'num',align:'center',resizable:false,sortable:true" style="width: 5%">序号</th>
						<th data-options="field:'noticeTitle',align:'left',resizable:false,sortable:true" style="width: 30%">公告标题</th>
						<th data-options="field:'noticeSubtitle',align:'left',resizable:false,sortable:true" style="width: 29%">副标题</th>
						<th data-options="field:'releaseUser',align:'center',resizable:false,sortable:true" style="width: 10%">发布人</th>
						<th data-options="field:'releaseTime',align:'center',resizable:false,sortable:true,formatter: ChangeDateFormat" style="width: 15%">发布时间</th>
						<th data-options="field:'operation',align:'center',formatter:oper_notice" style="width: 10%">操作</th>
					</tr>
				</thead>
			</table>	
    	</div>
    </div>
<script type="text/javascript">
//操作
function oper_notice(value, row, index){
	var btn = "<table><tr style='width: 105px; height:20px'>";
	
	//修改
	var btn1 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='editNotice("+row.noticeId+")'>"
	+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/update1.png'>"
	+"</a></td>";
	
	//删除
	var btn2 = "<td style='width:25px'><a href='javascript:void(0)' style='color:blue' onclick='deleteNotice("+row.noticeId+")'>"
	+ "<img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/delete1.png'>"
	+"</a></td>";
	btn = btn + btn1 + btn2;
	btn = btn + "</tr></table>";
	return btn;
}
//时间格式化
function ChangeDateFormat(val) {
    var t, y, m, d, h, i, s;
    if(val==null){
  	  return "";
    }
    t = new Date(val);
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + ' ' + (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i);
}

//新增窗口弹出
function addNotice(){
	var win = parent.creatFirstWin('公告-新增', 780,580, 'icon-search', "/notice/add");
	win.window('open');
}

//删除
function deleteNotice(id){
	if(confirm("确认删除吗？")){
		$.ajax({ 
			type: 'POST', 
			url: '${base}/notice/delete/'+id,
			dataType: 'json',  
			success: function(data){ 
				if(data.success){
					$.messager.alert('系统提示',data.info,'info');
					$('#noticeTab').datagrid('reload');
				}else{
					$.messager.alert('系统提示',data.info,'error');
				}
			} 
		}); 
	}
}

//修改页面跳转
function editNotice(id){
	var win = parent.creatFirstWin('修改公告-修改', 780,580, 'icon-search', "/notice/edit/"+id);
	win.window('open');
}

//查询
function queryNotice(){ 
	$('#noticeTab').datagrid('load',{ 
		noticeTitle:$('#notice_noticeTitle').textbox('getValue'),
		releaseUser:$('#notice_releaseUser').textbox('getValue')
	} ); 
}
//清除
function clearNotice(){
	$('#notice_noticeTitle').textbox('setValue','');
	$('#notice_releaseUser').textbox('setValue','');
	queryNotice();
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
</script>
  </body>
</html>
