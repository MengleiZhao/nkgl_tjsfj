<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
<div class="win-div">
<form id="receive_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" style="width: 735px" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div title="采购验收"  id="cgysdiv" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-bottom:35px;">
				 	<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
				 		<!-- 第1个div -->
				 		<c:if test="${openType == 'add' || openType == 'edit' }">
					 		<div title="验收信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
						  		<jsp:include page="receive_edit.jsp" />
							</div>
						</c:if>
						<c:if test="${openType == 'detail' }">
							<div title="验收信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
						  		<jsp:include page="receive_detail.jsp" />
							</div>
						</c:if>
						<c:if test="${openType == 'check' }">
							<div title="验收信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
						  		<jsp:include page="receive_check.jsp" />
							</div>
						</c:if>
						
						<!-- 第2个div -->
						<c:if test="${openType != 'add' }">
							<div title="审批记录" data-options="iconCls:'icon-xxlb'" style="overflow-x:hidden;margin-top: 10px;">
								<jsp:include page="../../check_history.jsp" />												
							</div> 
						</c:if>
					</div>												
				</div>
			</div>
			
			<div class="win-left-bottom-div">
				<c:if test="${openType == 'add' || openType == 'edit' }">
					<a href="javascript:void(0)" onclick="saveReceive(0)">
						<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="saveReceive(1)">
						<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
				</c:if>
				
				<c:if test="${openType == 'check' }">	
					<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
						<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
						<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
				</c:if>
				
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		
		<div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
</div>
<script type="text/javascript">
	//加载tab页
	flashtab('yx-tab');
	
	//提交
	function saveReceive(checkStauts) {
		//设置审批状态
		$("#F_CheckStauts").val(checkStauts);
		//设置申请状态
		$("#F_Stauts").val(checkStauts);
		
		//附件的路径地址
		var s="";
		$(".fileUrl").each(function(){
			s=s+$(this).attr("id")+",";
		});
		$("#files").val(s);
		
		//提交
		$('#receive_form').form('submit', {
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
			url : base + '/cgreceive/save',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#receive_list').datagrid('reload');
					$('#indexdb').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
	
	//审核
	function check(checkResult) {
		//校验采购单是否有已经拟定通过的合同
		$.ajax({
			type: "post",
			url : base + '/cgreceive/checkContract?fpId='+'${bean.fpId}',
			success : function(data) {
				data = eval("(" + data + ")");
				if (data.success) {
					
				} else {
					$.messager.alert('系统提示', data.info, 'error');
					return
				}
			},
		});
		
		
	 	$('#receive_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/cgreceive/checkResult',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#receive_list').datagrid('reload');
					$('#indexdb').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});	
	}
</script>
</body>