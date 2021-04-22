<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
<div class="win-div">
<form id="process_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" style="width: 705px" data-options="region:'west',split:false">
			<div class="win-left-top-div">
				<div class="tab-wrapper" id="yx-tab">
					<ul class="tab-menu">
						<li class="active">过程登记</li>
					    <li>采购申请</li>
					</ul>
					
					<div class="tab-content">
						<div title="过程登记" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false">
							<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
								<!-- 第1个div -->
									<div title="基本信息"  id="gysxxdiv"  data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<jsp:include page="process_edit.jsp" />
									</div>
									<div title="采购邀请"  id="gysxxdiv"  data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<jsp:include page="process_invite.jsp" />
									</div>
									<div title="竞争过程"  id="gysxxdiv"  data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<jsp:include page="process_course.jsp" />
									</div>
									<div title="采购结果"  id="gysxxdiv"  data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
										<jsp:include page="purchasing_results.jsp" />
									</div>
								
								<!-- 第2个div -->
								<c:if test="${openType != 'add' }">
									<div title="审批记录" data-options="iconCls:'icon-xxlb'" style="overflow-x:hidden;margin-top: 10px;">
										<jsp:include page="../../../check_history.jsp" />												
									</div> 
								</c:if>
							</div>
						</div>
					
						<div title="采购申请" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
								<!-- 第3个div -->
								<div title="采购信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
									<jsp:include page="../../purchase/cgjh_msg.jsp" />
								</div>
	
								<!-- 第4个div -->
								<div title="采购清单" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
	 							  	<jsp:include page="../../purchase/select_cgconf_plan_mingxi_detail.jsp" />												
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>	
			
			<div class="win-left-bottom-div">
					<a href="javascript:void(0)" onclick="saveProcess(1)">
						<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
				
				
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		
		<div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../../check_system.jsp" />
		</div>
	</div>
</form>
</div>
<script type="text/javascript">
	//加载tab页
	flashtab('yx-tab');
	function onclickdetailProcess(){
			$('#dg_detail').datagrid('reload');
			return true;
	}
	//保存
	function saveProcess(checkStauts) {
		if(sign==0){
			alert("请先保存中标商名单!");
			return false;
		}
		//设置审批状态
		$("#F_CheckStauts").val(checkStauts);
		//设置申请状态
		$("#F_Stauts").val(checkStauts);
		
		var rows = $('#purchasing_tab_id').datagrid('getRows');
		if(rows==''){
			alert('请添加中标商!');
			return false;
		}else{
			var mingxi = "";
			for (var i = 0; i < rows.length; i++) {
				mingxi = mingxi + JSON.stringify(rows[i]) + ",";
			}
			$("#jsonList").val(mingxi);
			var supJsonList = "";
			for (var j = 0; j < rows.length; j++) {
				var rowss = $('#purchasing_tab_pur_'+j).datagrid('getRows');
				for (var r = 0; r < rowss.length; r++) {
					supJsonList = supJsonList + JSON.stringify(rowss[r]) + ",";
				}
			}
			$("#supJsonList").val(supJsonList);
		}
			
		//提交
		$('#process_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}else{
					//校验不通过，就打开第一个校验失败的手风琴
					openAccordion();
				}
				return flag;
			},
			url : base + '/cgprocess/save',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#process_list').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});	
	}
</script>
</body>