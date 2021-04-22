<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<form id="ReceLowAddEditForm" action="${base}/Rece/save" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<input type="hidden" name="fId_R" id="R_fId_R" value="${bean.fId_R}"/>
		    	<input type="hidden" name="fFlowStauts_R" id="R_fFlowStauts_R" value="${bean.fFlowStauts_R}"/>
		    	<input type="hidden" name="fAssStauts" id="R_fAssStauts" value="${bean.fAssStauts}"/>
		    	<input type="hidden" name="fAcceptStatus" id="R_fAcceptStatus" value="${bean.fAcceptStatus}"/>
		    	<input type="hidden" name="fReceStatus" id="R_fReceStatus" value="${bean.fReceStatus}"/>
		    	<input type="hidden" name="fConfigStatus" id="R_fConfigStatus" value="${bean.fConfigStatus}"/>
		    	<input type="hidden" name="fAssType" id="R_fAssType" value="${bean.fAssType}"/>
		    	<input type="hidden" name="fReqDeptID" id="R_fReqDeptID" value="${bean.fReqDeptID}"/>
		    	<input type="hidden" name="fReqDept" id="R_fReqDept" value="${bean.fReqDept}"/>
		    	<input type="hidden" name="fReqUser" id="R_fReqUser" value="${bean.fReqUser}"/>
		    	<input type="hidden" name="fReqUserid" id="R_fReqUserid" value="${bean.fReqUserid}"/>
		    	<input type="hidden" name="fReceDeptID" id="R_fReceDeptID" value="${bean.fReceDeptID}"/>
		    	<input type="hidden" name="fReceUserid" id="R_fReceUserid" value="${bean.fReceUserid}"/>
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px;">
					<div title="领用单详情" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<table class="ourtable" cellpadding="0" cellspacing="0" style="margin-top: 3px;width:707px;">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;资产领用单号</td>
								<td class="td2">
									<input id="F_fAssReceCode" class="easyui-textbox" readonly="readonly" required="required" name="fAssReceCode" data-options="prompt:'系统自动生成'," value="${bean.fAssReceCode}" style="width: 200px;color: #f7f7f7;"/> 
								</td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span >&nbsp;预计领用时间</td>
								<td class="td2">
									<input class="easyui-datebox" class="dfinput"  id="R_fReceTime" name="fReceTime"  data-options="editable:false"style="width: 200px" value="${bean.fReceTime}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;领用人</td>
								<td class="td2">
									<input id="R_fReceUser"  class="easyui-textbox" readonly="readonly" required="required" name="fReceUser" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fReceUser}"/>
								</td>
								<td class="td4">&nbsp;</td>
								<td class="td1"><span class="style1">*</span>&nbsp;领用人部门</td>
								<td class="td2">
									<input id="R_fReceDept"  class="easyui-textbox" readonly="readonly" required="required" name="fReceDept" data-options="prompt:' ',validType:'length[1,20]'" style="width: 200px" value="${bean.fReceDept}"/>					
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1" valign="top"><p style="margin-top: 8px"><span class="style1">*</span>&nbsp;领用说明</p></td>
								<td  colspan="4">
									<%-- <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'" required="required"
									 id="R_fReceRemark" name="fReceRemark" style="width: 555px;height:62px" value="${bean.fReceRemark}"> --%>  
									<!-- <input type="text" id="S_fFlowStauts" name="fFlowStauts" hidden="hidden" value="0"/> -->
									<textarea name="fReceRemark"  id="R_fReceRemark"  class="textbox-text" oninput="textareaNum(this,'textareaNum1')"
									autocomplete="off"  style="width: 570px;height:62px;resize:none">${bean.fReceRemark }</textarea>
								</td>
							</tr>
							<tr>
								<td align="right" colspan="5" style="padding-right: 0px;">
									可输入剩余数：<span id="textareaNum1" class="200">
									<c:if test="${empty bean.fReceRemark}">200</c:if>
									<c:if test="${!empty bean.fReceRemark}">${200-bean.fReceRemark.length()}</c:if>
									</span>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;附件
									<input type="file" multiple="multiple" id="fly" onchange="upladFile(this,'lyflies','zcagl01')" hidden="hidden">
									<input type="text" id="files" name="receFlies" hidden="hidden">
								</td>
								<td colspan="4" id="tdf">
									<a onclick="$('#fly').click()" style="font-weight: bold;" href="#">
										<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
										<div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
								 		</div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
									 </div>
									<c:forEach items="${ReceFilesList}" var="att">
										<c:if test="${att.serviceType=='lyflies' }">
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
					<div title="申领清单" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
			        	<%@ include file="rece_base_add_plan.jsp" %>
					</div>
					<%-- <div title="配置明细" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
			        	<%@ include file="rece_base_config_plan.jsp" %>
					</div> --%>
					<c:if test="${checkinfo==1}">
						<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow:auto;">
							<jsp:include page="../../check_history.jsp" />												
						</div>
					</c:if>
				</div>			
			</div>
			<div class="window-left-bottom-div" style="margin-top: 55px;">
				<a href="javascript:void(0)" onclick="ReceLowAddEditForm();">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="ReceLowFormSS()">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<div class="window-right-div" style="width:254px;height: 591px">
			 <jsp:include page="../../check_system.jsp" /> 
		</div>
	</div>
</form>
<script type="text/javascript">
function fresult(val, row) {
	if (val == 1) {
		return '<span style="color:green;">' + " 通过" + '</span>';
	} else if (val == 0) {
		return '<span style="color:red;">' + " 未通过" + '</span>';
	}
}

$('#F_fcType').combobox({
	onChange : function(newValue, oldValue) {
		var sel2 = $('#F_fcType').combobox('getValue');
		if (sel2 != "1") {
			$('#cg1').hide();
			//$('#cg2').hide();
			//$('#F_fPurchNo').next(".textbox").show();
		} else {
			$('#cg1').show();
			//$('#cg2').show();
			//$('#F_fPurchNo').next(".textbox").hide();
		}
	}
});

function ReceLowAddEditForm() {
	/* //附件的路径地址
	var s="";
	$(".fileUrl").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#files").val(s); */
	$('#R_fFlowStauts_R').val("0");
	$('#ReceLowAddEditForm').form('submit', {
		onSubmit : function(param) {
			param.planJson = getRecePlan();
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				$.messager.progress();
			}
			return flag;
		},
		data : {
			'fFlowStauts_R' : '0'
		},
		/* url:base+'/demo/save',  */
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				closeWindow();
				$('#ReceLowAddEditForm').form('clear');
				$("#rece_low_dg").datagrid('reload');
				$("#rece_fixed_dg").datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}
function ReceLowFormSS() {
	var receTime = $('#R_fReceTime').datebox('getValue');
	if(receTime==''){
		alert('请选择预计领用时间！');
		return false;
	}
	var receRemark = $('#R_fReceRemark').val();
	if(receRemark==''){
		alert('请选择填写领用说明！');
		return false;
	}
	var recePlans =getRecePlan();
	if(recePlans==''||recePlans=='[]'){
		alert('请添加申领清单！');
		return false;
	}
	$("#R_fFlowStauts_R").val('1');
	$('#ReceLowAddEditForm').form('submit', {
		onSubmit : function(param) {
			param.planJson = recePlans;
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				$.messager.progress();
			}
			return flag;
		},
		//url:base+'/demo/save',
		data : {
			'fFlowStauts_R' : '1'
		},
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
				closeWindow();
				$('#ReceLowAddEditForm').form('clear');
				$("#rece_low_dg").datagrid('reload');
				$("#rece_fixed_dg").datagrid('reload');
				$('#indexdb').datagrid('reload');
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}

function fReceCode() {
	var win = creatFirstWin('原配置单号', 970, 580, 'icon-search',
			'/Rece/receCodeList');
	win.window('open');
}
function fProCode_DC() {
	var win = creatFirstWin(' ', 970, 580, 'icon-search',
			'/Formulation/fProCode');
	win.window('open');
}
function quota_DC() {
	//var node=$('#rece_low_dg').datagrid('getSelected');
	var win = creatFirstWin('选择-预算指标编号', 970, 580, 'icon-add',
			'/BudgetIndexMgr/contract_list');
	win.window('open');
}

function deleteF1(val) {
	var child = document.getElementById(val.id);
	child.parentNode.removeChild(child);
}
var names = new Array();
function streetChange(streetCode) {
	$('#userStreetJwh').combobox('reload',
			"${base}/street/jwh?streetCode=" + streetCode);
}
function departSelect(selectType) {
	var win = creatSearchDataWin('选择-部门', 590, 400, 'icon-add',
			"/depart/refUserDepart/" + selectType);
	win.window('open');
}
function departReturnSelect(ret) {
	if (ret == "clear") {
		$("#user_depart").html("");
	}

	if (ret != "&&&&" && ret != undefined && ret != null
			&& ret.indexOf("&&") != -1 && ret != "cancel") {
		var retArray = ret.split("&&");
		var str = new Array();
		str = retArray[2].split(",");
		for (var i = 0; i < str.length - 1; i++) {
			if (null != str[i]) {
				var str1 = new Array();
				str1 = str[i].split(":");
				var div1 = $("#user_depart");
				if (!isUserTreeExist(str1[0]) && !isUserTreeExist(str1[1])) {
					names.push(str1[0]);
					div1
							.html("<div  style='float:left;margin-left:8px;margin-top:8px;' > "
									+ "<input type='hidden' name='depart.id' id='departIds' value='"+str1[0]+"'/>"
									+ "<span title='"+str1[1]+"'>"
									+ ((str1[1].length > 8) ? str1[1]
											.substring(0, 8)
											+ "..." : str1[1])
									+ "</span>"
									+ "<img src='${base}/resource/images/cancelDepart.jpg' style='cursor:pointer;margin-left:5px;height:15px;width:15px;' onclick = 'cancelUserDepart(this)' title='删除' id='"
									+ str1[0] + "'/>" + "</div>");
				}
			}
		}
	}
}
function isUserTreeExist(name) {
	for (var i = 0; i < names.length; i++) {
		if (names[i] == name) {
			return true;
		}
	}
	return false;
}
function deleteCF() {
	var row = $('#CS_dg').datagrid('getSelected');
	var selections = $('#rece_low_dg').datagrid('getSelections');
	if (confirm("确认删除吗？")) {
		$.ajax({
			type : 'POST',
			url : '${base}/Formulation/delete/' + row.fcId,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$("#rece_low_dg").datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}
}
function cancelUserDepart(obj) {
	var id = obj.id;
	if (names.length > 0) {
		for (i = 0; i < names.length; i++) {
			if (names[i] == id) {
				names.splice(i, 1);
			}
		}
	}
	$(obj).parent().remove();
}
function checkMobileNo() {
	var mobile = $("#user_edit_mobileNo").textbox("getValue");
	var id = $("#user_edit_id").val();
	var tag = "0";
	if (mobile != '') {
		$.ajax({
			type : "post",
			url : "${base}/user/mobileCheck",
			data : {
				"mobileNo" : mobile,
				"id" : id
			},
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					$.messager.alert('系统提示', "手机号在系统中已存在，请更换手机号！", 'info');
					tag = "1";
				} else {
					tag = "0";
				}
			}
		})
	}
	if (tag == "0") {
		return true;
	} else if (tag == "1") {
		return false;
	}
}
</script>
</body>