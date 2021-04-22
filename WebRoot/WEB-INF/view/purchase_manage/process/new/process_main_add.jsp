<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
<div class="window-div">
<form id="process_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div"  style="width:750px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:false">
			<div class="win-left-top-div">
				<div class="tab-wrapper" id="yx-tab">
					<!-- <ul class="tab-menu">
						<li class="active">过程登记</li>
					    <li onclick="onclickdetailProcess()">采购申请</li>
					</ul> -->
					
					<div class="tab-content">
						<div title="过程登记" style="margin-bottom:35px;" data-options="collapsed:false,collapsible:false">
							<div class="easyui-accordion" data-options="" id="" style="width:698px;margin-left: 20px">
								<!-- 第1个div -->
									<div title="采购信息"  id="gysxxdiv1"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="process_edit.jsp" />
									</div>
									<%-- <div title="采购邀请"  id="gysxxdiv2"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="process_invite.jsp" />
									</div> --%>
									<div title="竞争过程"  id="gysxxdiv3"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="process_course.jsp" />
									</div>
									<div title="采购结果"  id="gysxxdiv4"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="purchasing_results.jsp" />
									</div>
									<c:if test="${openType=='edit'}">
									<div title="审批记录" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;">
											<jsp:include page="../../../../view/check_history.jsp" />
									</div>
									</c:if>
							</div>
						</div>
					
<%-- 						<div title="采购申请" style="margin-bottom:35px;" data-options="">
							<div class="easyui-accordion" data-options="" id="" style="width:698px;margin-left: 20px">
								<!-- 第3个div -->
								<div title="采购信息" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
									<jsp:include page="../../purchase/cgjh_msg.jsp" />
								</div>
								<!-- 第4个div -->
								<div title="采购清单" id="CGdetail" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
	 							  	<jsp:include page="../../purchase/select_cgconf_plan_mingxi_detail.jsp" />												
								</div>
								<c:if test="${bean.amount>=50000 }">
									<div title="党组会会议号" id="" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
										<table cellpadding="0" cellspacing="0" class="ourtable" style="">
											<tr>
												<td>
													<input class="easyui-textbox"  name="fDZHCode" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fDZHCode}"/>
												</td>
											</tr>
										</table>
									</div>
								</c:if>
								<div title="采购方式确认" id="cgqddiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
									<table cellpadding="0" cellspacing="0" class="ourtable">
										<tr class="trbody">
											<td class="td1"><span class="style1">*</span>&nbsp;采购类型</td>
											<td class="td2">
												<input class="easyui-combobox" readonly="readonly" id="fpMethod" name="fpMethod" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PURCHASE_METHOD&selected=${bean.fpMethod}',method:'get',valueField:'code',textField:'text',editable:false,
													onSelect: function(rec){
													    var url = '${base}/lookup/lookupsJson?parentCode='+rec.code+'&selected=${bean.fpPype}';
													    $('#fpPype').combobox('reload', url);
												    }
												"/>
											</td>
											<td class="td4"></td>
											<td class="td1"><span class="style1">*</span>&nbsp;采购方式</td>
											<td class="td2">
												<input class="easyui-combobox" id="fpPype" name="fpPype" readonly="readonly" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=${bean.fpMethod}&selected=${bean.fpPype}',method:'get',valueField:'code',textField:'text',editable:false"/>
											</td>
										</tr>
										<c:if test="${bean.fIsImp=='1'}">
										<tr class="trbody" id="fhyzgbmyjfTr" >
											<td class="td1">
												<span class="style1">*</span>&nbsp;行业主管部门意见
											</td>
											<td colspan="4" id="hyzgbmyjtdf">
												<c:forEach items="${attac}" var="att">
													<c:if test="${att.serviceType=='hyzgbmyj'}">
														<div style="margin-top: 10px;">
															<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
														</div>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										<tr class="trbody" id="fczbmyjTr">
											<td class="td1">
												<span class="style1">*</span>&nbsp;财政部门意见
											</td>
											<td colspan="4" id="czbmyjtdf">
												<c:forEach items="${attac}" var="att">
													<c:if test="${att.serviceType=='czbmyj' }">
														<div style="margin-top: 10px;">
															<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
														</div>
													</c:if>
												</c:forEach>
											</td>
										</tr>
										</c:if>
									</table>
								</div>
							</div>
						</div>
 --%>					</div>
				</div>
			</div>	
			
			<div class="window-left-bottom-div">
					<a href="javascript:void(0)" onclick="saveProcess(0)">
						<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="saveProcess(1)">
						<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
				
				<c:if test="${openType == 'check' }">
					<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
						<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
						<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
				</c:if>
				
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		
		<div class="window-right-div" style="width:254px;height: 491px">
			<jsp:include page="../../../check_system.jsp" />
		</div>
</form>
</div>
<script type="text/javascript">

	//加载tab页
	flashtab('yx-tab');
	function onclickdetailProcess(){
		$.parser.parse("#CGdetail");
	}
	$(document).ready(function() {
		var fpPype = '${bean.fpPype}';
		if(fpPype=='三家比选'){
			/* $("#fujian01").html('<span class="style1">*</span>招标公告');
			$("#fujian02").html('<span class="style1">*</span>招标文件');
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#fujian04").html('<span class="style1">*</span>供应商投标书'); */
			$("#fujian04").html('<span class="style1" style="width:110px">*</span>成交证明文件：');
			$("#fujian05").html('<span class="style1" style="width:110px">*</span>供应商资质证明：');
		}else{
			$("#gysxxdiv2").remove();
			$("#gysxxdiv3").remove();
		}
		
	});
	
	
	function filesIdYZ(id){
		var s="";
		$(".fileUrl"+id).each(function(){
			s=s+$(this).attr("id")+",";
		});
		$("#files"+id).val(s);
		if(s==''){
			return true;
		}else{
			return false;
		}
	}
	//保存
	function saveProcess(checkStauts) {
		var fpPype = '${bean.fpPype}';
		/* if(fpPype=='GKZB'){
			var files01 = filesIdYZ('01');
			var files02 = filesIdYZ('02');
			var files03 = filesIdYZ('03');
			var files04 = filesIdYZ('04');
			var files05 = filesIdYZ('05');
			if(files01){
				alert("请上传招标公告文件!");
				return false;
			}
			if(files02){
				alert("请上传招标文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
			if(files04){
				alert("请上传供应商投标书文件!");
				return false;
			}
			if(files05){
				alert("请上传供应商资质证明文件!");
				return false;
			}
		}else if(fpPype=='YQZB'){
			var files01 = filesIdYZ('01');
			var files02 = filesIdYZ('02');
			var files03 = filesIdYZ('03');
			var files04 = filesIdYZ('04');
			var files05 = filesIdYZ('05');
			if(files01){
				alert("请上传招标公");
				return false;
			}
			if(files02){
				alert("请上传招标文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
			if(files04){
				alert("请上传供应商投标书文件!");
				return false;
			}
			if(files05){
				alert("请上传供应商资质证明文件!");
				return false;
			}
		}else if(fpPype=='JZXCS'){
			var files01 = filesIdYZ('01');
			var files02 = filesIdYZ('02');
			var files03 = filesIdYZ('03');
			var files04 = filesIdYZ('04');
			var files05 = filesIdYZ('05');
			if(files01){
				alert("请上传磋商邀请函文件!");
				return false;
			}
			if(files02){
				alert("请上传竞争性磋商文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
			if(files04){
				alert("请上传供应商投标书文件!");
				return false;
			}
			if(files05){
				alert("请上传供应商资质证明文件!");
				return false;
			}
		}else if(fpPype=='JZXTP'){
			var files01 = filesIdYZ('01');
			var files02 = filesIdYZ('02');
			var files03 = filesIdYZ('03');
			var files04 = filesIdYZ('04');
			var files05 = filesIdYZ('05');
			if(files01){
				alert("请上传谈判邀请函文件!");
				return false;
			}
			if(files02){
				alert("请上传竞争性谈判文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
			if(files04){
				alert("请上传供应商投标书文件!");
				return false;
			}
			if(files05){
				alert("请上传供应商资质证明文件!");
				return false;
			}
		}else if(fpPype=='DYLY'){
			var files01 = filesIdYZ('01');
			var files02 = filesIdYZ('02');
			var files03 = filesIdYZ('03');
			if(files01){
				alert("请上传单一来源采购公告文件!");
				return false;
			}
			if(files02){
				alert("请上传单一来源采购申请表文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
		}else if(fpPype=='XBJ'){
			var files01 = filesIdYZ('01');
			var files03 = filesIdYZ('03');
			var files04 = filesIdYZ('04');
			var files05 = filesIdYZ('05');
			if(files01){
				alert("请上传询价采购邀请函文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
			if(files04){
				alert("请上传供应商投标书文件!");
				return false;
			}
			if(files05){
				alert("请上传供应商资质证明文件!");
				return false;
			}
		}else if(fpPype=='XYGH'){
			var files01 = filesIdYZ('01');
			var files03 = filesIdYZ('03');
			if(files01){
				alert("请上传采购信息公告文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
		}else if(fpPype=='DDCG'){
			var files01 = filesIdYZ('01');
			var files03 = filesIdYZ('03');
			if(files01){
				alert("请上传定点采购公告文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
		}else if(fpPype=='ZXZB'){
			var files01 = filesIdYZ('01');
			var files02 = filesIdYZ('02');
			var files03 = filesIdYZ('03');
			var files04 = filesIdYZ('04');
			var files05 = filesIdYZ('05');
			if(files01){
				alert("请上传招标公告文件!");
				return false;
			}
			if(files02){
				alert("请上传招标文件!");
				return false;
			}
			if(files03){
				alert("请上传采购评价报告文件!");
				return false;
			}
			if(files04){
				alert("请上传供应商投标书文件!");
				return false;
			}
			if(files05){
				alert("请上传供应商资质证明文件!");
				return false;
			}
		}
		if(sign==0){
			alert("请先保存中标商名单!");
			return false;
		} */
		if(fpPype=='三家比选'){
			var files04 = filesIdYZ('04');
			var files05 = filesIdYZ('05');
			
			if(files04){
				alert("请上传成交证明文件!");
				return false;
			}
			if(files05){
				alert("请上传供应商资质证明文件!");
				return false;
			}
		}
		
		//设置审批状态
		$("#F_CheckStauts").val(checkStauts);
		//设置申请状态
		$("#F_Stauts").val(checkStauts);
		
		var rows = $('#purchasing_tab_id').datagrid('getRows');
		
		if(rows == ''){
			alert('请添加中标商!');
			return false;
		}else{
			var mingxi = "";
			var g = 0;
			for (var i = 0; i < rows.length; i++) {
				mingxi = mingxi + JSON.stringify(rows[i]) + ",";
				if(rows[i].fbidAmount=='' || isNaN(rows[i].fbidAmount) || rows[i].fbidAmount==0){
					alert('投标金额不能为空且大于0！');
					return false;
				}
				if(rows[i].fbiddingName == '' || rows[i].fbiddingName == null){
					alert('请填写供应商名称');
					return false;
				}
				if(rows[i].fbidStatus == '' || rows[i].fbidStatus == null){
					alert('请选择中标供应商');
					return false;
				}
				if(rows[i].fbidStatus == '是'){
					g++;
				}
			}
			if(g != 1){
				alert('中标供应商有且只能有一个');
				return false;
			}
		}
		$("#jsonList").val(mingxi);
		/* if(rows==''){
			alert('请添加中标商!');
			return false;
		}else{
			for (var g = 0; g < rows.length; g++) {
				if (endEditingPurRes(editIndexPurRes,tabId,editIndexPurRes)) {
					$('#purchasing_tab_pur_'+g).datagrid('acceptChanges');
				}
			}
			var mingxi = "";
			for (var i = 0; i < rows.length; i++) {
				mingxi = mingxi + JSON.stringify(rows[i]) + ",";
				if(rows[i].fbidAmount=='' || isNaN(rows[i].fbidAmount) || rows[i].fbidAmount==0){
					alert('中标金额不能为空且大于0！');
					return false;
				}
			}
			$("#jsonList").val(mingxi);
			/* var supJsonList = "";
			for (var j = 0; j < rows.length; j++) {
				var rowss = $('#purchasing_tab_pur_'+j).datagrid('getRows');
				for (var r = 0; r < rowss.length; r++) {
					supJsonList = supJsonList + JSON.stringify(rowss[r]) + ",";
				}
			}
			$("#supJsonList").val(supJsonList);
		} */
		if($("#addResultsId").is(":hidden") == false){
			alert("请先保存供应商名单！");
			return;
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
					$('#indexdb').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});	
	}
	
	//审批
	function check(checkResult) {
	 	$('#process_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/cgprocess/checkResult',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#process_list').datagrid('reload');
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