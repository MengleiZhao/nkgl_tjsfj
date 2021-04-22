<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
<div class="window-div">
<form id="cg_ask_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div title="项目基本信息"  id="cgysdiv" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-bottom:35px;">
				 	<div class="easyui-accordion" data-options="" id="" style="width:722px;margin-left: 20px">
				 		<div title="项目基本信息" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
					  		<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;采购项目编号</td>
									<td class="td2">
										<a <c:if test="${operation == 'add' }"> onclick="chooseCgList()" </c:if>>
											<input class="easyui-textbox" type="text" id="F_fpCode"  name="fpCode" readonly="readonly"  required="required" data-options="prompt:'选择采购项目'"  style="width: 200px" value="${bean.fpCode}"/>
										</a>
									</td>
									<td class="td4">
									</td>
									<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
									<td class="td2">
										<input class="easyui-textbox" id="F_fpName" name="fpName" readonly="readonly" required="required" data-options="prompt:'系统自动生成'" style="width: 200px;" value="${bean.fpName}"/>
									</td>
								</tr>
								
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;采购类型</td>
									<td class="td2">
										<input class="easyui-textbox" type="text" id="F_fpMethod" name="fpMethod" readonly="readonly" required="required" data-options="prompt:'系统自动生成'" style="width: 200px" value="${bean.fpMethod}"/>
									</td>
									<td class="td4">
									<!--主键id -->		<input type="hidden" id="F_QId" name="fqId" value="${bean.fqId}"/>								
									<!--采购的主键id -->	<input type="hidden" id="F_PId" name="fpId" value="${bean.fpId}"/>								
									<!-- 质疑发起状态 -->	<input type="hidden" id="F_fAskStauts" name="fAskStauts" value="${bean.fAskStauts}"/>
									
									</td>
									<td class="td1"><span class="style1">*</span>&nbsp;采购方式</td>
									<td class="td2">
										<input class="easyui-textbox" id="F_fpPype" name="fpPype" readonly="readonly" required="required"  data-options="prompt:'系统自动生成'" style="width: 200px;" value="${bean.fpPype}"/>
									</td>
								</tr>
								
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
									<td class="td2">
										<input class="easyui-textbox " id="F_fDeptName" name="fDeptName" required="required" readonly="readonly" data-options="prompt:'系统自动生成'"  style="width: 200px" value="${bean.fDeptName}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
									<td class="td2">
										<input class="easyui-textbox" id="F_fUserName" name="fUserName" readonly="readonly" required="required" data-options="prompt:'系统自动生成'" style="width: 200px;" value="${bean.fUserName}"/>
									</td>
								</tr>
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;中标金额</td>
									<td class="td2">
										<input class="easyui-numberbox " data-options="formatter:function(value,row,index){return fomatMoney(value,2);}" id="F_fbidAmount" name="fbidAmount" readonly="readonly" required="required" data-options="prompt:'系统自动生成'"  style="width: 200px" value="${bean.fbidAmount}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;中标商名称</td>
									<td class="td2">
										<input class="easyui-textbox" id="F_fOrgName" name="fOrgName" readonly="readonly" data-options="prompt:'系统自动生成'" required="required" style="width: 200px;" value="${bean.fOrgName}"/>
									</td>
								</tr>
								<c:if test="${operation != 'detail' }">
								<tr class="trbody">
									<td class="td1">
										<span class="style1">*</span>&nbsp;上传质疑函
										<input type="file" multiple="multiple" id="fcgzy" onchange="upladFile(this,'zyh','cggl01')" hidden="hidden">
										<input type="text" id="files" name="files" hidden="hidden">
									</td>
									<td colspan="4" id="tdf">
										<a onclick="$('#fcgzy').click()" style="font-weight: bold;" href="#">
											<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
										</a>
										<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
											<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
											</div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
										</div>
										<c:forEach items="${attaList}" var="att">
											<c:if test="${att.serviceType=='zyh' }">
												<div style="margin-top: 5px;">
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
													&nbsp;&nbsp;&nbsp;&nbsp;
													<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
													&nbsp;&nbsp;&nbsp;&nbsp;
													<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
												</div>
											</c:if>
										</c:forEach>
									</td>
								</tr>
								</c:if>
								<c:if test="${operation == 'detail' }">
								<tr class="trbody">
									<td class="td1">
										<span class="style1">*</span>&nbsp;质疑函
									</td>
									<td colspan="4" id="tdf">
										<c:forEach items="${attaList}" var="att">
											<c:if test="${att.serviceType=='zyh' }">
												<div style="margin-top: 10px;">
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												</div>
											</c:if>
										</c:forEach>
									</td>
								</tr>
								</c:if>	
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;情况简述</td>
									<td colspan="4">
										<input class="easyui-textbox " id="F_fRemark" name="fRemark" <c:if test="${operation == 'detail' }"> readonly="readonly"</c:if> required="required" data-options="multiline:true"  style="width: 550px;height:100px" value="${bean.fRemark}"/>
									</td>
								</tr>
							</table>
						</div>
					</div>												
				</div>
			</div>
			
			<div class="window-left-bottom-div">
			<c:if test="${operation != 'detail' }">
				<a href="javascript:void(0)" onclick="saveAsk(0);">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveAsk(1)">
					<img src="${base}/resource-modality/${themenurl}/button/tijiao1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
			</c:if>	
				<a href="javascript:void(0)" onclick="closeFirstWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
</form>
</div>
<script type="text/javascript">
	//加载tab页
	flashtab('yx-tab');
	//提交
	function saveAsk(status) {
		$("#F_fAskStauts").val(status);
		//附件的路径地址
		var s="";
		$(".fileUrl").each(function(){
			s=s+$(this).attr("id")+",";
		});
		if(s==''){
			alert('请上传质疑函');
			return false;
		}
		$("#files").val(s);
		//提交
		$('#cg_ask_form').form('submit', {
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
			url : base + '/cgAsk/saveAsk',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#cg_ask_Tab').datagrid('reload');
					$('#ask_detail_Tab').datagrid('reload');
					closeFirstWindow();
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
	
	
	//选择采购项目
	function chooseCgList(){
		var win = creatSecondWin('选择质疑项目', 1070, 580, 'icon-search', '/cgAsk/chooseCgList');
		win.window('open');
	}
</script>
</body>