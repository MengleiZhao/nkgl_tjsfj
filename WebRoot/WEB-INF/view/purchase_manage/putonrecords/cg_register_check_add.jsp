.<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="window-div">
<form id="cgsq_register_check_form" name="example" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:715px;margin-left: 20px">
					<div title="上传信息" id="cgfjdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 10px;">
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;验收文件:
									<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'cgysba','cgysba01')" hidden="hidden">
									<input type="text" id="files" name="files"  hidden="hidden">
								</td>
								<td colspan="4" id="tdf">
									<a onclick="$('#f').click()" style="font-weight: bold;" href="#">
										<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
								        <div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
								        </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
						    	    </div>
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='cgysba' }">
											<div style="margin-top: 10px;">
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
						</table>
					</div>
					<div  title="基本信息" id="" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;项目名称:</td>
								<td class="td2">
									<input class="easyui-textbox" readonly="readonly" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fpName}"/>
								</td>
								<td class="td4">
									<input type="hidden" name="fpId"  value="${bean.fpId}"/>
									<input type="hidden" id="checkPutOnRecordsSts" name="checkPutOnRecordsSts"  value="${bean.checkPutOnRecordsSts}"/>
								</td>
								<td class="td1"><span class="style1">*</span>&nbsp;中标商名称:</td>
								<td class="td2">
									<input class="easyui-textbox" readonly="readonly" style="width: 200px;" value="${bean.fOrgName}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;投标金额:</td>
								<td class="td2">
									<input class="easyui-numberbox" data-options="formatter:function(value,row,index){return fomatMoney(value,2);}" style="width: 200px;" value="${bean.fbidAmount}" readonly="readonly"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请人:</td>
								<td class="td2">
									<input class="easyui-textbox" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fUserName}" readonly="readonly"/>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveCgFileApply(1)">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveCgFileApply(2)">
					<img src="${base}/resource-modality/${themenurl}/button/tijiao1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
</form>
</div>
<script type="text/javascript">
$(document).ready(function() {
	//申请金额
	var fbidAmount = $("#fbidAmount").val();
	if(fbidAmount !=""){
		$('#fbidAmount').val(fomatMoney(fbidAmount,2));
	};
});
	//保存
	function saveCgFileApply(type) {
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
		$("#checkPutOnRecordsSts").val(type);
		//提交
		$('#cgsq_register_check_form').form('submit', {
			onSubmit : function() {
				//获得校验结果
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					//如果校验通过，则进行下一步
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/putOnRecords/saveCheckSts',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
				    $('#cg_apply_register_Tab').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}

</script>
</body>