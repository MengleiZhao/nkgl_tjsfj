<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		<div class="list-top">
			<table class="top-table" cellpadding="0" cellspacing="0">
				<tr>
					<td class="top-table-search" class="queryth">
						&nbsp;&nbsp;姓名&nbsp;
						<input id="query_name_R" style="width: 150px;height:25px;" class="easyui-textbox"/>
						
						<!-- &nbsp;&nbsp;部门&nbsp;
						<input id="query_dept" style="width: 150px;height:25px;" class="easyui-textbox"/> -->
						
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="queryR();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						<a href="#" onclick="clearTableR();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
						<a href="#" onclick="saveuserR();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
			</table>           
		</div>
			<div class="list-table">
			<table id="choose_user_rdg"  class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/user/jsonPaginationTravel',
			method:'post',fit:true,pagination:false,singleSelect: false,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true" >
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'Id',hidden:true"></th>
						<th data-options="field:'accountNo',align:'center'" width="25%">账号</th>
						<th data-options="field:'name',align:'center'" width="25%">姓名</th>
						<th data-options="field:'departName',align:'center',resizable:false,sortable:true" width="23%">部门</th>
						<th data-options="field:'roleslevel',align:'center',formatter:rolesreturn,resizable:false,sortable:true" width="25%">级别</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	


<script type="text/javascript">

//查询
function queryR() {
	$('#choose_user_rdg').datagrid('load',{
		name:$('#query_name_R').textbox('getValue').trim(),
	//	gName:$("#query_dept").textbox('getValue').trim(),
	});
}

//清除查询条件
function clearTableR() {
	$('#query_name_R').textbox('setValue','');
	//$("#query_dept").textbox('setValue','');
	$('#choose_user_rdg').datagrid('load',{});
}

function rolesreturn(val,row){//1：市级，2：局级，3：其他人员
	if(1==val){
		return '局级';
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
function saveuserR(){
	var rowindex  = '${index}';
	var names = '';
	var ids = '';
	var levels = '';
	var rows = $('#choose_user_rdg').datagrid('getSelections');
	var ary = new Array();
	if(rows==''){
		alert('请选择人员！');
		return false;
	}else{
		for (var i = 0 ; i < rows.length ; i++) {
			names=names + rows[i].name+',';	
			ary.push(rows[i].roleslevel);	
			ids=ids + rows[i].id+',';
			levels=levels + rows[i].roleslevel+',';
		}
	}
	
	names = names.substring(0,names.length-1);
	ids = ids.substring(0,ids.length-1);
	var travelPersonnel = $('#reimburse_itinerary_tab_id').datagrid('getEditor',{
		index:rowindex,
		field:'travelAttendPeop'
	});
	$(travelPersonnel.target).textbox('setValue', names);
	//人员id
	var travelPersonnelId = $('#reimburse_itinerary_tab_id').datagrid('getEditor',{
		index:rowindex,
		field:'travelAttendPeopId'
	});
	$(travelPersonnelId.target).textbox('setValue', ids);
	//人员id
	var travelPersonnelLevel = $('#reimburse_itinerary_tab_id').datagrid('getEditor',{
		index:rowindex,
		field:'travelPersonnelLevel'
	});
	$(travelPersonnelLevel.target).textbox('setValue', levels);
	closeFirstWindow();
}
</script>
</body>
</html>