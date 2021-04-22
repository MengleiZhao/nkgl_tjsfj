<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body style="background-color: #f0f5f7;text-align: center;">	
<div class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
		
	<div class="list-top">
		<table id="confplancombine_dg"  class="top-table" cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search">单据编号&nbsp;
					<input id="combine_flistNum" name="flistNum" style="width: 150px; height:25px;" class="easyui-textbox"></input>
				&nbsp;&nbsp;申请部门
					<input id="combine_freqDept" name="freqDept" style="width: 150px; height:25px;" class="easyui-textbox"></input>
				&nbsp;&nbsp;采购类型&nbsp;
					<select class="easyui-combobox" id="combine_fprocurType" name="fprocurType"  style="width: 150px; height:25px;" data-options="editable:false,panelHeight:'auto'">
						<option value="">--请选择--</option>
						<option value="A10" >货物</option>
						<option value="A20" >工程采购</option>
						<option value="A30" >服务</option>
					 </select>
					<a href="#" onclick="queryConfplanCombine();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					<a href="#" onclick="clearConfplancombineTable();">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>							
				<td align="right" style="padding-right: 10px">
					<a href="#" onclick="combineConfplan()">
						<img src="${base}/resource-modality/${themenurl}/button/combine1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						<input type="hidden" id="ids"/>
					</a>
				</td>
			</tr>
		</table>
	</div>
		
		<div style="margin: 0 10px 0 10px;height: 445px;">	
			<table id="confplancombine_tab" class="easyui-datagrid"
				data-options="collapsible:true,url:'${base}/cgconfplangl/confplancombinePageData',
			method:'post',fit:true,pagination:true,singleSelect: true,scrollbarSize:0,
			selectOnCheck: true,checkOnSelect:true,remoteSort:true,nowrap:false,striped: true,fitColumns: true">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:'true',name:'test' "></th>
						<th data-options="field:'fplId',hidden:true"></th>
						<th data-options="field:'num',align:'center'" width="5%">序号</th>
						<th data-options="field:'flistNum',align:'center'" width="13%">单据编号</th>
						<th data-options="field:'freqDept',align:'center'" width="25%">申请部门</th>
						<th data-options="field:'freqTime',align:'center',formatter:ChangeDateFormat" width="10%">申请日期</th>
						<th data-options="field:'fprocurType',align:'center',formatter:formatter_confleixing" width="10%">采购类型</th>
						<th data-options="field:'fbrandAndNum',align:'center'" width="20%">品牌(数量)</th>
						<th data-options="field:'combineState',align:'center'" width="12%">合并备注</th>
						<!-- <th data-options="field:'freqLinkMen',align:'left'" width="10%">申请部门联系人</th>
						<th data-options="field:'flinkTel',align:'left'" width="10%">联系电话</th>
						<th data-options="field:'freqContent',align:'left'" width="12%">配置申请内容</th>
						<th data-options="field:'fcheckStauts',align:'left',resizable:false,sortable:true,formatter:formatPrice" style="width: 10%">审批状态</th>
						 -->
						 <th data-options="field:'opera',align:'left',formatter:format_confplancombine" width="10%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	

</div>

	<script type="text/javascript">
	
	//分页样式调整
	$(document).ready(function() {
		var pager = $('#confplancombine_tab').datagrid().datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			layout:['sep','first','prev','links','next','last'/* ,'refresh' */]
		});	
	});
	
	$('#confplancombine_tab').datagrid({
		 onLoadSuccess: function () {   //隐藏表头的checkbox
            //$("#winner_tab").parent().find("div .datagrid-header-check").children("input[type=\"checkbox\"]").eq(0).attr("style", "display:none;");
            $(".datagrid-header-check").html("");
		 } 
	});
	
	function formatter_confleixing(val,row){
		if (val == "A10") {
			return "货物";
		} else if (val == "A20") {
			return "工程采购" ;
		} else if (val == "A30") {
			return "服务";
		} 
		
	}
	
	
	
		
	//点击查询
	function queryConfplanCombine() {
		$('#confplancombine_tab').datagrid('load',{
			flistNum : $('#combine_flistNum').textbox('getValue'),
			freqDept : $('#combine_freqDept').textbox('getValue'),
			fprocurType : $('#combine_fprocurType').textbox('getValue'),
		});
	}
	//清除查询条件
	function clearConfplancombineTable() {
		$("#combine_flistNum").textbox('setValue','');
		$("#combine_freqDept").textbox('setValue','');
		$("#combine_fprocurType").combobox('setValue','');
		$('#confplancombine_tab').datagrid('load',{//清空以后，重新查一次
		});
	}

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
	function format_confplancombine(val,row) {		
		return '<table><tr style="width: 75px;height:20px"><td style="width: 25px">'+
			   '<a href="#" onclick="confplancombine_detail(' + row.fplId + ')" class="easyui-linkbutton">'+
			   '<img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="'+base+'/resource-modality/${themenurl}/list/select1.png">'+
			   '</a></td></tr></table>';	
	}
	function showA(obj){
		obj.src=base+'/resource-modality/${themenurl}/list/select1.png';
	}
	function showB(obj){
		obj.src=base+'/resource-modality/${themenurl}/select2.png';
	}

	//查看页面
	 function confplancombine_detail(id) {
		var win = parent.creatWin(' ', 970, 580, 'icon-search',"/cgconfplangl/detail?id=" + id);
		win.window('open');
	} 
	
	//合并信息
	function combineConfplan() {
	     var len = $("input:checkbox:checked").length; 
	      if(len<2){
	    	  $.messager.alert('提示','请选择两行以上数据进行合并！');
	      }else{//判断要合并的数据是否是同意种类
	    	  var rows=$('#confplancombine_tab').datagrid('getSelections');
	      	  var arr=[];
	      	  var ids="";
	    	  for(var i=0;i<rows.length;i++){
	    			arr.push(rows[i].fprocurType);
	    			if(ids==""){
	    				ids=rows[i].fplId;
	    			}else{
	    				ids=ids+","+rows[i].fplId;
	    			}
	    	  }	    	  
	    	  var same=true;
	          for(var j=1,leng=arr.length;j<leng;j++){
	                 if(arr[j]!==arr[0]){
	                	 same=false;
	                }
	             }
	           if(same==true){
	        	   $('#ids').val(ids);
		          //判断选中的数据的采购清单是否完全相同   完全相同的合并
		   		 	$.ajax({
		   				type : 'POST',
		   				url : "${base}/cgconfplancombine/issame",
		   				data: {ids:ids},
		   				dataType : 'json',
		   				success : function(data) {
		   					$.messager.alert('系统提示',data);
		   					queryConfplanCombine();
		   					/* if (data=="1") {//清单完全相同
		   						parent.$.messager.progress('close');
		   						$.messager.alert('系统提示','合并成功。');
		   					} else if(data=="0"){//清单不相同
		   						parent.$.messager.progress('close');
		   						$.messager.alert('系统提示', '所选单据的采购清单不完全相同，请重新选择!').window({ width: 350, height: 160 });
		   					}else if(data=="-1"){
		   						parent.$.messager.progress('close');
		   						$.messager.alert('系统提示', '系统错误，请联系管理员!');
		   					} */
		   				}
		   			}); 
	           }else{
	        	   $.messager.alert('提示','所选单据的采购类型不同，请重新选择！');  
	           }
	    		
	    
	      }
	     
	}
	
	

	
	
	</script>
</body>

