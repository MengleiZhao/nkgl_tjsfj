<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
<div class="window-div">
<form id="receive_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div title="采购验收"  id="cgysdiv" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-bottom:35px;">
				 	<div class="easyui-accordion" data-options="" id="" style="width:722px;margin-left: 20px">
				 	<c:if test="${openType == 'add' || openType == 'edit' }">
				 		<!-- 第1个div -->
				 		<div title="验收信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
					  		<jsp:include page="receive_edit.jsp" />
						</div>
						<div title="验收明细" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
					  		<jsp:include page="../receive/accept_purchasing_results.jsp" />
						</div>
					</c:if>
				 	<c:if test="${openType == 'detail'||openType == 'check' }">
				 		<!-- 第1个div -->
				 		<div title="验收信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
					  		<jsp:include page="receive_detail.jsp" />
						</div>
						<div title="验收明细" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
					  		<jsp:include page="../receive/accept_purchasing_results_detail.jsp" />
						</div>
					</c:if>
						<!-- 第2个div -->
						<c:if test="${openType != 'add' }">
							<c:if test="${bean.fpItemsName=='PMMC-4' || bean.fpItemsName=='PMMC-5'}">
								<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
									<jsp:include page="../../check_history.jsp" />												
								</div> 
							</c:if>
						</c:if>
					</div>												
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<c:if test="${openType == 'add' || openType == 'edit' }">
					<c:if test="${bean.fpItemsName=='PMMC-4' || bean.fpItemsName=='PMMC-5'}">
						<a href="javascript:void(0)" onclick="saveReceive(0)">
							<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="saveReceive(1)">
							<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>&nbsp;&nbsp;
					</c:if>
					<c:if test="${bean.fpItemsName!='PMMC-4' && bean.fpItemsName!='PMMC-5'}">
						<a href="javascript:void(0)" onclick="saveReceive(9)">
							<img src="${base}/resource-modality/${themenurl}/button/ys1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						</a>&nbsp;&nbsp;
					</c:if>
				</c:if>
				
				<c:if test="${openType == 'check' }">	
					<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
						<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
						<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
				</c:if>
				<c:if test="${type=='BX'}">
				<a href="javascript:void(0)" onclick="closeSecondWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				</c:if>
				<c:if test="${empty type && empty checkFlag}">
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				</c:if>
				<c:if test="${empty type && checkFlag == 'index'}">
				<a href="javascript:void(0)" onclick="closeFirstWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				</c:if>
			</div>
		</div>
		<c:if test="${bean.fpItemsName=='PMMC-4' || bean.fpItemsName=='PMMC-5'}">
		<div class="window-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
		</c:if>
</form>
</div>
<script type="text/javascript">
	//加载tab页
	flashtab('yx-tab');
	//提交
	function saveReceive(checkStauts) {
		acceptYS();
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
		var rows = $('#ys_tab_id').datagrid('getRows');
		if(rows==''){
			alert('验收明细为空，不能提交！');
			return false;
		}else if(rows.fNowCheckedNum==''||rows.fNowCheckedNum==null||rows.fNowCheckedNum==undefined){
			alert('本次申请验收数量为空，不能提交！');
			return false;
		}else{
			var mingxi = "";
			for (var i = 0; i < rows.length; i++) {
				mingxi = mingxi + JSON.stringify(rows[i]) + ",";
			}
			$("#jsonList").val(mingxi);
		}
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
					closeFirstWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
	
	//审核
	function check(checkResult) {
		if('${bean.fpItemsName}'!='PMMC-4' && '${bean.fpItemsName}'!='PMMC-5'){
			var rows = $('#ys_tab_id').datagrid('getRows');
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].fNowCheckedNum==''||rows[i].fNowCheckedNum==null||rows[i].fNowCheckedNum==undefined){
					alert('请填写本次申请验收数量！');
					return false;
				}
			}
		}
		
		
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
					<c:if test="${empty type && empty checkFlag}">
					closeWindow();
					</c:if>
					<c:if test="${empty type && checkFlag == 'index'}">
					closeFirstWindow();
					</c:if>
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});	
	}
	var fpItemsName = '${bean.fpItemsName}';
	var openType = '${openType}';
	var sign = 0;
	function onSelectCode(r){
		if(sign == 0){
			sign+=1;
			if(openType=='add'){
				onSelectCode(r);
			}
		}else{
			$("#F_fcTitle").textbox('setValue',r.fcTitle);
			$("#F_FcId").val(r.fcId);
			var rows = $('#ys_tab_id').datagrid('getRows');
			for (var i = rows.length-1; i >= 0; i--) {
				$('#ys_tab_id').datagrid('deleteRow',i);
				editIndexYS = undefined;
			}
			$.ajax({
				type:'post',
				async:true,
				dataType:'json',
				url:base+'/Formulation/findAttacByFcId?fcId='+r.fcId,
				success:function (data){
					if(data[0]==false){
						alert('该合同正在进行变更，暂无法送审!');
						$('#F_fcCode').combobox('setValue','');
						$('#F_fcTitle').textbox('setValue','');
						return false;
					}
					for (var i = 0; i < data.length; i++) {
						if(fpItemsName=='PMMC-4'||fpItemsName=='PMMC-5'){
							$('#ys_tab_id').datagrid('appendRow',{
								acRId: data[i].acRId,
								cRId: data[i].cRId,
								fcId: data[i].fcId,
								fId_U: data[i].fId_U,
								fmName: data[i].fmName,
								fmatchRemark: data[i].fmatchRemark,
							});
						}else{
							$('#ys_tab_id').datagrid('appendRow',{
								acRId: data[i].acRId,
								cRId: data[i].cRId,
								fcId: data[i].fcId,
								fId_U: data[i].fId_U,
								fmName: data[i].fmName,
								fmModel: data[i].fmModel,
								fmSpecif: data[i].fmSpecif,
								fpNum: data[i].fpNum,
								fCheckedNum: data[i].fCheckedNum,
								fNowCheckedNum: data[i].fNowCheckedNum,
							});
						}
					}
				}
			});
		}
	}
</script>
</body>