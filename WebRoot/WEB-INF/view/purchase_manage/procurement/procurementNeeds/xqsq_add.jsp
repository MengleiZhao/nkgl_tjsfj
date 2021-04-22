.<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<div class="window-div">
		<form id="xqsq_apply_form" name="example" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
			<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
				<div class="win-left-top-div">
					<div class="easyui-accordion" data-options="" id="cc" style="width:715px;margin-left: 20px">
						<!-- 隐藏域 --> 
						<input type="hidden" name="pId"  value="${needsBean.pId}"/><!-- 需求单id --> 
						<input type="hidden" name="pCode"  value="${needsBean.pCode}"/><!-- 需求单Code --> 
						<input type="hidden" name="cgId"  value="${bean.fpId}"/><!-- 采购单id -->
						<input type="hidden" name="cgName"  value="${bean.fpName}"/><!-- 采购项目名 -->
						<input type="hidden" name="cgDeptId"  value="${bean.fDeptId}"/><!-- 申请人处室id -->
						<input type="hidden" name="cgUserId"  value="${bean.fUser}"/><!-- 申请人id -->
						<input type="hidden" name="cgAmount"  value="${bean.amount}"/><!-- 采购金额 --> 
						<input type="hidden" name="cgType"  value="${bean.fpPype}"/><!-- 采购方式 -->
						<input type="hidden" name="flowStatus" id="F_flowStatus" value="${needsBean.flowStatus}"/><!--采购需求审批状态  -->
				    	<input type="hidden" name="status" id="F_status" value="${needsBean.status}"/><!--采购需求单状态  -->
						<!-- 附件隐藏域 --> 
						<div  title="申请信息" id="cgxxdiv" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable">
							
								<tr class="trbody">
									<td class="td1" style="width:60px;text-align: right"><span class="style1">*</span>上传需求书:
										<input type="file" multiple="multiple" id="xqs"
										onchange="upladFileMoreParams(this,'xqs','cggl09','xqsprogressNumber','xqspercent','xqstdf','xqsfiles','xqsprogid','xqsfileUrl')" hidden="hidden"> <input
										type="text" id="xqsfiles" name="xqsfiles" hidden="hidden"></td>
									<td colspan="3" id="xqstdf">&nbsp;&nbsp; <a onclick="$('#xqs').click()"
										style="font-weight: bold;  " href="#"> <img
											style="vertical-align:bottom;margin-bottom: 5px;"
											src="${base}/resource-modality/${themenurl}/button/shangchuan1.png"
											onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
									</a>
										<div id="xqsprogid"
											style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
											<div id="xqsprogressNumber" style="background:#3AF960;width:0px;height:10px"></div>
											文件上传中...&nbsp;&nbsp;<font id="xqspercent">0%</font>
										</div>
										<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='xqs' }">
											<div style="margin-top: 5px;">
												<a href='${base}/attachment/download/${att.id}'
													style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp; <img style="margin-top: 5px;"
													src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp; <a id="${att.id}" class="xqsfileUrl" href="#"
													style="color:red" onclick="deleteAttac(this)">删除</a>
											</div>
										</c:if>
										</c:forEach>
									</td>
								</tr>
								
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;授权代表:</td>
									<td class="td2" colspan="4">
										<input class="easyui-textbox" class="dfinput" id="F_authorized" name="authorized" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${needsBean.authorized}"/>
									</td>
								</tr>
								
								<tr class="trbody">
									<td class="td1" style="width:60px;text-align: right"><span class="style1">*</span>上传委托书：
										<input type="file" multiple="multiple" id="wts"
										onchange="upladFileMoreParams(this,'wts','cggl09','wtsprogressNumber','wtspercent','wtstdf','wtsfiles','wtsprogid','wtsfileUrl')" hidden="hidden"> <input
										type="text" id="wtsfiles" name="wtsfiles" hidden="hidden"></td>
									<td colspan="3" id="wtstdf">&nbsp;&nbsp; <a onclick="$('#wts').click()"
										style="font-weight: bold;  " href="#"> <img
											style="vertical-align:bottom;margin-bottom: 5px;"
											src="${base}/resource-modality/${themenurl}/button/shangchuan1.png"
											onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
									</a>
										<div id="bxtzprogid"
											style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
											<div id="wtsprogressNumber" style="background:#3AF960;width:0px;height:10px"></div>
											文件上传中...&nbsp;&nbsp;<font id="wtspercent">0%</font>
										</div>
										<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='wts' }">
											<div style="margin-top: 5px;">
												<a href='${base}/attachment/download/${att.id}'
													style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp; <img style="margin-top: 5px;"
													src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp; <a id="${att.id}" class="wtsfileUrl" href="#"
													style="color:red" onclick="deleteAttac(this)">删除</a>
											</div>
										</c:if>
										</c:forEach>
									</td>
								</tr>
								
							</table>
						</div>
						
						<div  title="基本信息" id="cgxxdiv" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable">
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;采购项目编号:</td>
									<td class="td2">
										<input class="easyui-textbox" class="dfinput" id="F_cgCode" name="cgCode" readonly="readonly" required="required" data-options="" style="width: 200px;" value="${bean.fpCode}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;采购类型:</td>
									<td class="td2">
										<input id="F_cgMethod" class="easyui-textbox" type="text" readonly="readonly" required="required" name="cgMethod" data-options="" style="width: 200px" value="${bean.fpMethod}"/>
									</td>
								</tr>
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;申请处室：</td>
									<td class="td2">
										<input class="easyui-textbox" class="dfinput" id="F_cgDeptName" name="cgDeptName" readonly="readonly" required="required" data-options="" style="width: 200px;" value="${bean.fDeptName}"/>
									</td>
									<td class="td4"></td>
									<td class="td1"><span class="style1">*</span>&nbsp;申请人:</td>
									<td class="td2">
										<input id="F_cgUserName" class="easyui-textbox" type="text" readonly="readonly" required="required" name="cgUserName" data-options="" style="width: 200px" value="${bean.fUserName}"/>
									</td>
								</tr>
							</table>
						</div>
						<c:if test="${openType=='edit' or openType=='detail'}">
							<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
								<jsp:include page="../../../check_history.jsp" />												
							</div> 
						</c:if>
					</div>
				</div>
				
				<div class="window-left-bottom-div">
					<a href="javascript:void(0)" onclick="saveNeedsApply(0)">
						<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="saveNeedsApply(1)">
						<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="closeWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</div>
				
			</div>
			
			<div class="window-right-div" id="check_system_div" style="margin: 20px 20px 30px 0px;" data-options="region:'east',split:true">
				<jsp:include page="../../../check_system.jsp" />
			</div>
			
		</form>
	</div>
	
	
	
	
<script type="text/javascript">
	function saveNeedsApply(saveType){
		//需求书的路径地址
		var s1="";
		$(".xqsfileUrl").each(function(){
			s1=s1+$(this).attr("id")+",";
		});
		$("#xqsfiles").val(s1);
		
		if(s1==""){
			alert("请上传需求书！");
			return;
		}
		
		//委托书的路径地址
		var s2="";
		$(".wtsfileUrl").each(function(){
			s2=s2+$(this).attr("id")+",";
		});
		$("#wtsfiles").val(s2);
		
		if(s2==""){
			alert("请上传委托书！");
			return;
		}
		
		//设置需求申请审批状态
		$('#F_flowStatus').val(saveType);
		//设置需求申请单状态
		$('#F_status').val(saveType);
		
		//提交
		$('#xqsq_apply_form').form('submit', {
			onSubmit : function() {
				//获得校验结果
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					//如果校验通过，则进行下一步
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/procurementNeeds/save',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#cgxq_apply_Tab').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
</script>
</body>