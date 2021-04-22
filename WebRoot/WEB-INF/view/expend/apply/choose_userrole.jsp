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
						<input id="query_name" style="width: 150px;height:25px;" class="easyui-textbox"/>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="queryCF();"><img src="${base}/resource-modality/${themenurl}/button/detail1.png" onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail2.png')"
								onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/detail1.png')">
						</a>
						<a href="#" onclick="clearTable();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
						<a href="#" onclick="saveuser();">
							<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>
					</td>
				</tr>
			</table>           
		</div>
			<div class="list-table">
			<table id="choose_user_dg_s"  class="easyui-datagrid"
			data-options="collapsible:true,url:'${base}/user/jsonPaginationTravel',
			method:'post',fit:true,pagination:false,
			 <%-- <c:if test="${tabId== 'tracel_itinerary_trip_tab_id'}">
			singleSelect: true,
			</c:if> 
			 <c:if test="${tabId == 'tracel_itinerary_trip_tab_id'}">
			singleSelect: false,
			</c:if>  --%>
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
function saveuser(){
	var rowindex  = '${index}';
	var tabId  = '${tabId}';
	var names = '';
	var ids = '';
	var levels = '';
	var rows = $('#choose_user_dg_s').datagrid('getSelections');
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
		$.ajax({
			type:'POST',
			url:base+'/user/checkRoleLevel',
			data:{'id':ids},
			dataType:'json',
			async:'false',
			success:function(data){
				if(!data.success){//没有越级
					alert('选择人员级别超过当前用户级别，请重新选择');
					return false;
				}else {
					if(tabId=='tracel_itinerary_tab_id' || tabId=='tracel_itinerary_trip_tab_id' || tabId=='tracel_itinerary_trip_reim_tab_id' ){
						names = names.substring(0,names.length-1);
						ids = ids.substring(0,ids.length-1);
						var travelPersonnel = $('#'+tabId).datagrid('getEditor',{
							index:rowindex,
							field:'travelAttendPeop'
						});
						$(travelPersonnel.target).textbox('setValue', names);
						//人员id
						var travelPersonnelId = $('#'+tabId).datagrid('getEditor',{
							index:rowindex,
							field:'travelAttendPeopId'
						});
						$(travelPersonnelId.target).textbox('setValue', ids);
						//人员id
						var travelPersonnelLevel = $('#'+tabId).datagrid('getEditor',{
							index:rowindex,
							field:'travelPersonnelLevel'
						});
						$(travelPersonnelLevel.target).textbox('setValue', levels);
					}else if(tabId=='outside_traffic_tab_id'){
						names = names.substring(0,names.length-1);
						ids = ids.substring(0,ids.length-1);
						var travelPersonnel = $('#outside_traffic_tab_id').datagrid('getEditor',{
							index:rowindex,
							field:'travelPersonnel'
						});
						$(travelPersonnel.target).textbox('setValue', names);
						//人员id
						var travelPersonnelId = $('#outside_traffic_tab_id').datagrid('getEditor',{
							index:rowindex,
							field:'travelPersonnelId'
						});
						$(travelPersonnelId.target).textbox('setValue', ids);
					}else if(tabId=='reimburse_outside_tab_id'){
						names = names.substring(0,names.length-1);
						ids = ids.substring(0,ids.length-1);
						var travelPersonnel = $('#reimburse_outside_tab_id').datagrid('getEditor',{
							index:rowindex,
							field:'travelPersonnel'
						});
						$(travelPersonnel.target).textbox('setValue', names);
						//人员id
						var travelPersonnelId = $('#reimburse_outside_tab_id').datagrid('getEditor',{
							index:rowindex,
							field:'travelPersonnelId'
						});
						$(travelPersonnelId.target).textbox('setValue', ids);
					}else if(tabId=='apply_abroadtab'){
						$('#fAbroadPeople').textbox('setValue', names);
					}
					
					closeFirstWindow();
				}
			}
		});
	}
		/* if(ary.length>1){
			if(!isRepeat(ary)){
				alert("所选用户级别不一致，无法保存！");
				return false;
			}
		} */
	
}
</script>
</body>
</html>