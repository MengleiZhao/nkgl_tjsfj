.<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="window-div">
<form id="cgsq_apply_form" name="example" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:715px;margin-left: 20px">
					<c:if test="${!empty returnReason }">
					<div  title="退回理由" id="" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<tr class="trbody">
								<td class="td1"></td>
								<td class="td2">
									<input class="easyui-textbox" readonly="readonly" style="width: 500px;height: 60px" value="${bean.returnReason}"/>
								</td>
							</tr>
						</table>
					</div>
					</c:if>
					<div title="上传信息" id="cgfjdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 10px;">
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;上传采购文件:
									<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'cgwjsc','cgwjsc01')" hidden="hidden">
									<input type="text" id="files" name="files"  hidden="hidden">
								</td>
								<td colspan="4" id="tdf">
									<c:if test="${fileUp=='edit'}">
										<a onclick="$('#f').click()" style="font-weight: bold;" href="#">
											<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
										</a>
										<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
									        <div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
									        </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
							    	    </div>
										<c:forEach items="${attac}" var="att">
											<c:if test="${att.serviceType=='cgwjsc'}">
												<div>
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
													&nbsp;&nbsp;&nbsp;&nbsp;
													<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
													&nbsp;&nbsp;&nbsp;&nbsp;
													<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
												</div>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${fileUp!='edit'}">
										<c:forEach items="${attac}" var="att">
											<c:if test="${att.serviceType=='cgwjsc'}">
												<div>
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												</div>
											</c:if>
										</c:forEach>
									</c:if>
								</td>
							</tr>
						</table>
					</div>
					<div  title="基本信息" id="" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称:</td>
								<td class="td2">
									<input class="easyui-textbox" readonly="readonly" style="width: 200px" value="${bean.fpName}"/>
								</td>
								<td class="td4">
									<input type="hidden" name="fpId"  value="${bean.fpId}"/>
									<input type="hidden" id="filesUploadSts" name="filesUploadSts"  value="${bean.filesUploadSts}"/>
								</td>
								<td class="td1"><span class="style1">*</span>&nbsp;预算金额:</td>
								<td class="td2">
									<input class="easyui-textbox" readonly="readonly" id="f_amount" style="width: 200px;" value="${bean.amount}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;项目负责人:</td>
								<td class="td2">
									<a href="#">
										<input class="easyui-textbox" style="width: 200px;" value="${bean.projectLeaderName}" readonly="readonly"/>
									</a>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;项目经办人:</td>
								<td class="td2">
									<input class="easyui-textbox" style="width: 200px" value="${bean.authorized}" readonly="readonly"/>
								</td>
							</tr>
						</table>
					</div>
					<!-- 审批记录 -->
					<div class="easyui-accordion" id="" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
								<jsp:include page="../purchase/cg_file_check_history.jsp" />
						</div>
					</div>
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<c:if test="${fileUp=='edit'}">
					<a href="javascript:void(0)" onclick="saveCgFileApply(1)">
						<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="saveCgFileApply(2)">
						<img src="${base}/resource-modality/${themenurl}/button/tijiao1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
				</c:if>
				<c:if test="${fileUp=='check'}">
					<a href="javascript:void(0)" onclick="openCgCheckWin('审批意见','9')">
						<img src="${base}/resource-modality/${themenurl}/button/queren1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="openCgCheckWin('审批意见','5')">
						<img src="${base}/resource-modality/${themenurl}/button/tuihui1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
					<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
					<!-- 审批附件 --><input type="hidden" name="spjlFile" id="spjlFile" value=""/>
					<!-- 流程id  --><input type="hidden" id="flowId"  value="-100"/>
				</c:if>
					<a href="javascript:void(0)" onclick="closeWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
			</div>
		</div>
</form>
</div>
<script type="text/javascript">
	//申请金额
	var f_amount = $("#f_amount").val();
	if(f_amount !=""){
		$('#f_amount').val(fomatMoney(f_amount,2));
		}	
	//保存
	function saveCgFileApply(type) {
		var fileUp = '${fileUp}';//页面类型
		if(fileUp=="edit"){
			//附件的路径地址
			var s="";
			$(".fileUrl").each(function(){
				s=s+$(this).attr("id")+",";
			});
			$("#files").val(s);
			if(s==''){
				alert('请上传采购文件!');
				return false;
			}
		}
		$("#filesUploadSts").val(type);
		//提交
		$('#cgsq_apply_form').form('submit', {
			onSubmit : function() {
				//获得校验结果
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					//如果校验通过，则进行下一步
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/cgsqsp/saveFileUp',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
				    $('#cg_apply_file_Tab').datagrid('reload');
				    $('#cg_apply_file_affirm_Tab').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
	//保存
	function check(type) {
		$("#filesUploadSts").val(type);
		//提交
		$('#cgsq_apply_form').form('submit', {
			onSubmit : function() {
				//获得校验结果
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					//如果校验通过，则进行下一步
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/cgsqsp/saveFileUp',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
				    $('#cg_apply_file_Tab').datagrid('reload');
				    $('#cg_apply_file_affirm_Tab').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}

</script>
</body>