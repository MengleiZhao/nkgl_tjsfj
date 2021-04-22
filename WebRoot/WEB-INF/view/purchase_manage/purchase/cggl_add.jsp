.<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/lang/zh-cn/zh-cn.js"></script>
<div class="window-div">
<form id="cgsq_apply_form" name="example" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" style="width:715px;margin-left: 20px">
					<div  title="采购项目签报表" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<tr >
								<td style="width: 150px" colspan="5"><span class="style1">*</span>&nbsp;采购项目签报名称:</td>
							</tr>
							<tr >
								<td colspan="5">
									<input class="easyui-textbox" id="F_proSignName" name="proSignName"  style="width:710px;" value="${bean.proSignName}"/>
								</td>
							</tr>
	
							<tr >
								<td colspan="5"><span class="style1">*</span>&nbsp;采购项目签报内容:</td>
							</tr>
							<tr >
								<td colspan="5">
									<textarea id="proSignContent" style="width:710px;height:200px;"></textarea>
								</td>
							</tr>
						</table>
					</div>
				
					<div  title="采购信息" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;采购项目编号:</td>
								<td class="td2">
									<input class="easyui-textbox" id="F_fpCode" readonly="readonly" name="fpCode"  style="width:200px;" value="${bean.fpCode}"/>
								</td>
								<td class="td4">
									<!-- 隐藏域 --> 
									<input type="hidden" name="nCode" value="${bean.nCode}"/>
									<input type="hidden" name="fpId"  value="${bean.fpId}"/>
									<input type="hidden" name="projectLeaderId" value="${bean.projectLeaderId}"/><!--处室负责人id  -->
									<input type="hidden" name="projectLeaderName"  value="${bean.projectLeaderName}"/><!--处室负责人名字  -->
									<%-- <input type="hidden" name="fReqTime" id="fTime" value="${proposer.upTime}"/> --%>
				    				<input type="hidden" name="fCheckStauts" id="F_fCheckStauts" value="${bean.fCheckStauts}"/><!--采购审批状态  -->
				    				<input type="hidden" name="fStauts" id="F_fStauts" value="${bean.fStauts}"/><!--采购数据的删除状态  -->
				    				<input type="hidden" name="fIsReceive" id="F_fIsReceive" value="${bean.fIsReceive}"/><!--验收状态  -->
				    				<input type="hidden" name="fbidStauts" id="F_fbidStauts" value="${bean.fbidStauts}"/><!--中标状态  -->
				    				<input type="hidden" name="fpayStauts" id="F_fpayStauts" value="${bean.fpayStauts}"/><!--付款申请的审批状态  -->
				    				<input type="hidden" name="fevalStauts" id="F_fevalStauts" value="${bean.fevalStauts}"/><!--供应商的评价状态  -->
				    				<input type="hidden" name="indexId" id="F_fBudgetIndexCode" value="${bean.indexId}"/><!-- 指标ID -->
				    				<input type="hidden" name="indexType" id="F_indexType" value="${bean.indexType}"/><!--采购指标type  -->
				    				<input type="hidden" id="proSignContents" name="proSignContent"/><!-- 采购项目签报内容 -->
									<!-- 申请人ID --><input type="hidden" id="F_fUser" name="fUser" value="${bean.fUser}"/>
									<!-- 项目支出明细ID --><input type="hidden" id="F_proDetailId" name="proDetailId" value="${bean.proDetailId}"/>
									<!-- 可用金额  --><input type="hidden" id="syAmount" value="${bean.syAmount}"/>
									<!-- 批复金额  --><input type="hidden" id="pfAmount" value="${bean.pfAmount}"/>
									<!-- 批复时间  --><input type="hidden" id="pfDate"  value="${bean.pfDate}"/>
									<!-- 下一级审批节点  --><input type="hidden" id="nextKey"  value="${bean.nCode}"/>
									<!--历史审批节点  --><input type="hidden" id="historyNodes"  value="${historyNodes}"/>
								</td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请部门:</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" id="F_fDeptName" readonly="readonly" required="required" name="fDeptName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fDeptName}"/>
								</td>
							</tr>
	
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;申请日期:</td>
								<td class="td2">
									<input class="easyui-datebox" class="dfinput" id="F_fReqTime" name="fReqTime" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fReqTime}"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请人:</td>
								<td class="td2">
									<input id="F_fUserName" class="easyui-textbox" type="text" readonly="readonly" required="required" name="fUserName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fUserName}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;本次申请金额:</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" id="f_amount" required="required" name="amount" style="width: 200px;" data-options="icons: [{iconCls:'icon-yuan'}],onChange:amount,precision:2" value="${bean.amount}"/>
								</td>
								<td class="td4"></td>
								
								<td class="td1" id = "objName1"><span class="style1">*</span>&nbsp;采购项目名称:</td>
								<td class="td2" id = "objName2">
									<input id="F_fpName" class="easyui-textbox" type="text" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fpName}"/>
								</td>
							</tr>
							<tr class="trbody" hidden="hidden" id = "yxgk">
								<td class="td1"><span class="style1">*</span>&nbsp;意向公开项目编号：</td>
								<td class="td2">
									<a href="#" onclick="chooseYxgkd()">
										<input class="easyui-textbox" type="text" id="f_openObjCode" name="openObjCode" style="width: 200px;" value="${bean.openObjCode}" readonly="readonly"/>
									</a>
								</td>
								<td class="td4"></td>
								
								<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称:</td>
								<td class="td2">
									<input id="F_fpNameTwo" class="easyui-textbox" type="text" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fpName}" readonly="readonly"/>
								</td>
							</tr>
							
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;包含进口产品:</td>
								<td class="td2">
         							<input type="radio" name="fIsImp" <c:if test="${bean.fIsImp=='1'}">checked="checked"</c:if> value="1">是</input>
         							<input type="radio" name="fIsImp" <c:if test="${bean.fIsImp!='1'}">checked="checked"</c:if> value="0">否</input>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;涉密采购:</td>
								<td class="td2">
         							<input type="radio" name="fIsPers" onClick="workFlow1()" <c:if test="${bean.fIsPers=='1'}">checked="checked"</c:if> value="1">是</input>
         							<input type="radio" name="fIsPers" onClick="workFlow2()" <c:if test="${bean.fIsPers!='1'}">checked="checked"</c:if> value="0">否</input>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;预算指标:</td>
								<td colspan="4">
									<a onclick="openIndex1()" href="#">
									<input class="easyui-textbox" style="width: 555px;height: 30px;"
									name="indexName" value="${bean.indexName}" id="F_indexName"
									data-options="prompt: '选择指标' ,icons: [{iconCls:'icon-sousuo'}]" readonly="readonly" required="required"/>
									</a>
								</td>
							</tr>	
						</table>
						
						<table class="window-table-readonly" cellspacing="0" cellpadding="0" style="margin-left: 100px;width: 560px;">
							<tr>
								<td class="window-table-td1" style = "width:111px"><p>预算批复金额：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_pfAmount">${bean.pfAmount}元</p></td>
								
								<td class="window-table-td1"><p>当前可用金额：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_syAmount">${bean.syAmount}元</p></td>
							</tr>
							
							<tr>
								<td class="window-table-td1" hidden="hidden"><p>使用部门：&nbsp;</p></td>
								<td class="window-table-td2" hidden="hidden"><p id="p_pfDepartName">${bean.pfDepartName}</p></td>
								
								<td class="window-table-td1" hidden="hidden"><p>批复时间：&nbsp;</p></td>
								<td class="window-table-td2" hidden="hidden"><p id="p_pfDate">${bean.pfDate}</p></td>
							</tr>
							<tr>
								<td class="window-table-td1" hidden="hidden"><p>资金渠道：&nbsp;</p></td>
								<td class="window-table-td2" hidden="hidden"><p id="p_pfIndexType">${bean.pfIndexType}</p></td>
								
								<td class="window-table-td1" hidden="hidden"><p></p></td>
								<td class="window-table-td2" hidden="hidden">
									<p id="">
										
									</p>
								</td>
							</tr>
						</table>
							
						<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 20px;">
							<tr>
								<td class="td1"><span class="style1">*</span>&nbsp;预算价格依据:</td>
								<td colspan="4">
									<input class="easyui-combobox" id="f_budgetPriceBasis" name="budgetPriceBasis" value="${bean.budgetPriceBasis }" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=YSJGYJ&selected=${bean.budgetPriceBasis}',method:'get',validType:'selectValid',valueField:'code',textField:'text',editable:false"/>
								</td>
							</tr>
						</table>
					</div>
					<div title="采购清单" id="cgqddiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
					  	<jsp:include page="select_cgconf_plan_mingxi.jsp" />
					</div>
					<div title="采购方式确认" id="cgqrdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;height: auto;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;采购类型:</td>
								<td class="td2">
									<input class="easyui-combobox" id="fpMethod" name="fpMethod" style="width: 200px;" data-options="editable:false,onSelect:cgType" value="${bean.fpMethod}"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;采购方式:</td>
								<td class="td2">
									<input class="easyui-combobox" id="fpPype" name="fpPype" style="width: 200px;" data-options="editable:false,onSelect:cgWay" value="${bean.fpPype}"/>
								</td>
							</tr>
							<tr class="trbody" hidden="hidden" id="cgReason">
								<td class="td1"><span class="style1">*</span>&nbsp;采购方式选择理由：</td>
								<td class="td2" colspan="4">
									<div id="reasonChoose">
									</div>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1">备注：</td>
								<td colspan="4">
									<textarea name="fRemark" id="fRemark" class="textbox-text" 
											oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
											style="width:545px;height:70px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:8px; margin-bottom:0px;">${bean.fRemark}</textarea>
								</td>
							</tr>
						</table>
					</div>
					<div id="dwhDiv" hidden="hidden">
						<div class="easyui-accordion" style="width:710px">
							<div title="党委会会议号" id="cgdwhdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
								<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 10px;margin-left: 0px">
									<tr>
										<td class="td1"><span class="style1">*</span>&nbsp;编号:</td>
										<td colspan="4">
											<input class="easyui-textbox" id="f_cdgwh" name="cdgwh" style="width: 200px;" data-options="" readonly="readonly"/>
										</td>
									</tr>
									<tr class="trbody">
											<td class="td1">
												&nbsp;<span class="style1">*</span>&nbsp;会议纪要上传:
												<input type="file" multiple="multiple" id="f1" onchange="upladFile(this,'hyjy','cggl01')" hidden="hidden">
												<input type="text" id="hyjyfiles" name="hyjyfiles"  hidden="hidden">
											</td>
										</tr>
								</table>
							</div>
						</div>
					</div>
					<div title="附件信息" id="cgfjdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 10px;margin-left: -60px">
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;附件:
									<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'cggl','cggl01')" hidden="hidden">
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
										<c:if test="${att.serviceType=='cggl' }">
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
					
					<c:if test="${czlx=='edit'}">
						<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
							<jsp:include page="../../check_history.jsp" />												
						</div> 
					</c:if>
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveCgsqApply(0)">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveCgsqApply(1)">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="window-right-div" id="check_system_div" style="margin: 20px 20px 30px 0px;" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
</form>
</div>
<script type="text/javascript">
//选择意向公开单
function chooseYxgkd(){
	var win = creatFirstWin('意向公开单选择', 840, 450, 'icon-search', '/cgsqsp/choose?menuType=fromBxsq');
	win.window('open');
}
var a = '${bean.amount}';
var b = '${cgType}';
var c = '${cgWay}';
var count = 0;
if(a != null && a != '' && a != undefined){
	$("#cgReason").show();
	amount(a);
	$.ajax({	
		url:'${base}/cgsqsp/cgsqspJson?amount='+a+'&parentCode='+c,
		success:function(data){
			var json = eval("("+data+")");
			var html = '';
			var flag = false;
			json.forEach(function(val,index){
				var arr = '${bean.buyInfos}'.split(',');
				
				if(val.text != '--请选择--'){
					for(var i=0;i<arr.length;i++){
						if(arr[i] == val.id){
							flag = true;
						}
					}
					if(flag){
						html += '<input type="checkbox" name="reasonIds" checked="checked" value="'+val.id+'"/>&nbsp;'+val.text+'&nbsp;<br>';
						flag = false;
					}else{
						html += '<input type="checkbox" name="reasonIds" value="'+val.id+'"/>&nbsp;'+val.text+'&nbsp;<br>';
					}
				}
				$("#reasonChoose").html(html);
			});
		}
	});
}
//申请金额判断
function amount(newValue){
	if(newValue != null){
		if(parseInt(newValue) < 200000){
			$("#F_fpName").attr("required",true);
			$("#F_fpNameTwo").attr("required",false);
			$("#f_openObjCode").attr("required",false);
			$("#F_fpNameTwo").removeAttr("name");
			$("#F_fpName").attr("name","fpName");
			$("#yxgk").hide();
			$("#dwhDiv").hide();
			$("#objName1").show();
			$("#objName2").show();
		}else if(parseInt(newValue) >= 200000 && parseInt(newValue) < 500000){
			$("#yxgk").hide();
			$("#dwhDiv").show();
			$("#F_fpName").attr("required",true);
			$("#f_openObjCode").attr("required",false);
			$("#F_fpNameTwo").attr("required",false);
			$("#F_fpNameTwo").removeAttr("name");
			$("#F_fpName").attr("name","fpName");
			$("#objName1").show();
			$("#objName2").show();
		}else{
			$("#F_fpName").attr("required",false);
			$("#f_openObjCode").attr("required",true);
			$("#F_fpNameTwo").attr("required",true);
			$("#F_fpNameTwo").attr("name","fpName");
			$("#F_fpName").removeAttr("name");
			$("#yxgk").show();
			$("#dwhDiv").show();
			$("#objName1").hide();
			$("#objName2").hide();
		}
	}
	var tr = $('#dg').datagrid('getEditors', 0);
	var str = "其他";
	
	if(tr[0] == null || tr[0] == '' || tr[0] == undefined){
		if(a != null && a != '' && a != undefined && count == 0 || count == 1){
			$("#fpMethod").combobox({
				url:'${base}/cgsqsp/cgsqspJson?amount='+newValue+'&selected='+b,
				valueField:'text',
				textField:'text',
			});
		}else{
			$("#fpMethod").combobox({
				url:'${base}/cgsqsp/cgsqspJson?amount='+newValue,
				valueField:'text',
				textField:'text',
			});
		}
	}else{
		if(tr[0].target.val().indexOf(str) == '-1'){
			$("#fpMethod").combobox({
				url:'${base}/cgsqsp/cgsqspJson?amount='+666666,
				valueField:'text',
				textField:'text',
			});
		}else{
			$("#fpMethod").combobox({
				url:'${base}/cgsqsp/cgsqspJson?amount='+$("#f_amount").val(),
				valueField:'text',
				textField:'text',
			});
		}
	}
}
//采购类型判断
function cgType(val){
	var amount = $("#f_amount").val();
	$("#fpPype").combobox("setValue",'');
	if(val.text == '政府采购'){
		$("#cgReason").show();
		
	}else if(val.text == '委托代理机构采购'){
		$("#cgReason").show();
	}else{
		$("#cgReason").hide();
	}
	if(val.text != '--请选择--'){
		if(a != null && a != '' && a != undefined && count == 0 || count == 1){
			$("#fpPype").combobox({
				url:'${base}/cgsqsp/cgsqspJson?amount='+amount+'&parentCode='+val.id+'&selected='+val.id,
				valueField:'text',
				textField:'text'
			});
		}else{
			$("#fpPype").combobox({
				url:'${base}/cgsqsp/cgsqspJson?amount='+amount+'&parentCode='+val.id,
				valueField:'text',
				textField:'text'
			});
		}
		
	}else{
		$("#fpPype").combobox({
			url:'${base}/cgsqsp/cgsqspJson?amount='+amount+'&parentCode='+val.id+'&type=1',
			valueField:'text',
			textField:'text'
		});
	}
}
//采购方式判断
function cgWay(val){
	var amount = $("#f_amount").val();
	if(val.text == '公开招标'){
		$("#cgReason").hide();
	}else if(val.text == '竞争性磋商'){
		$("#cgReason").show();
	}else if(val.text == '竞争性谈判'){
		$("#cgReason").show();
	}else if(val.text == '单一来源'){
		$("#cgReason").show();
	}else if(val.text == '协议供货'){
		$("#cgReason").show();
	}else if(val.text == '网上商城'){
		$("#cgReason").show();
	}else if(val.text == '邀请招标'){
		$("#cgReason").show();
	}else if(val.text == '询价采购'){
		$("#cgReason").show();
	}else{
		if($("#fpMethod").val() == '政府采购' || $("#fpMethod").val() == '委托代理机构采购'){
			$("#cgReason").show();
		}else{
			$("#cgReason").hide();
		}
	}
	if(a != null && a != '' && a != undefined && count == 0 || count == 1){
		count++;
	}else{
		if(val.text != '--请选择--'){
				$.ajax({
					url:'${base}/cgsqsp/cgsqspJson?amount='+amount+'&parentCode='+val.id,
					success:function(data){
						var json = eval("("+data+")");
						var html = '';
						json.forEach(function(val){
							if(val.text != '--请选择--'){
								html += '<input type="checkbox" name="reasonIds" value="'+val.id+'" <c:if test="${checked=='true'}">checked="checked"</c:if>/>&nbsp;'+val.text+'&nbsp;<br>';
								$("#reasonChoose").html(html);
							}
						});
					}
				});
		}else{
			$.ajax({
				url:'${base}/cgsqsp/cgsqspJson?amount='+amount+'&parentCode='+val.id+'&type=1',
				success:function(data){
					var json = eval("("+data+")");
					var html = '';
					json.forEach(function(val){
						$("#reasonChoose").html(html);
					});
				}
			});
		}
	}
	
}
var totalfpItemsName = 0;
	$(document).ready(function() {
		//批复金额
		var pfAmount = $("#pfAmount").val();
		if(pfAmount !=""){
			$('#p_pfAmount').html(fomatMoney(pfAmount,2)+" [元]");
		}
		//可用金额
		var syAmount = $("#syAmount").val();
		if(syAmount !=""){
			$('#p_syAmount').html(fomatMoney(syAmount,2)+" [元]");
		}
		 //申请金额
		var f_amount = $("#f_amount").val();
		if(f_amount !=""){
			$('#f_amount').html(fomatMoney(f_amount,2));
		} 
		//批复时间
		var pfDate = $("#pfDate").val();
		if(pfDate !=""){
			$('#p_pfDate').html(ChangeDateFormat(pfDate));
		}
		var jkcp = $('input[name="fIsImp"]:checked').val();
		var cgglg = '${cgglg}';
		
		if(jkcp==1){//是
			$('#jkzjyjTr').show();
			if(cgglg=='1'){
				$('#fhyzgbmyjfTr').show();
				$('#fczbmyjTr').show();
			}else{
				$('#fhyzgbmyjfTr').hide();
				$('#fczbmyjTr').hide();
			}
		}else if(jkcp!=1){//否
			$('#jkzjyjTr').hide();
			$('#fhyzgbmyjfTr').hide();
			$('#fczbmyjTr').hide();
		}
	});
	
	//根据选择的采购类型刷新审批流
	/* $('#F_fpPype').combobox({
		onChange: function (newValue, oldValue) {
			$('#check_system_div').load('${base}/cgsqsp/refreshProcess?fpPype='+newValue);
		}
	}); */
	
	//打开指标选择页面
	function openIndex1() {
		var win = creatFirstWin('选择指标', 860, 580, 'icon-search', '/apply/choiceIndex?menuType=beforeApply'); 
		win.window('open');
	}
	//是否进口商品
	function jkcp(val){
		if(val==1){//是
			$('#jkzjyjTr').show();
		}else if(val!=1){//否
			$('#jkzjyjTr').hide();
		
		}
	}
	//如果品目名称选择工程类或服务类，则隐藏数量、单位、是否进口、单价
	$('#f_fpItemsName').combobox({
		onSelect:function(record){
			
			totalfpItemsName += 1;
			if(totalfpItemsName>2){
				if(record.code=='PMMC-4'||record.code=='PMMC-5'){
					$('#dg').datagrid('hideColumn','fnum');
					$('#dg').datagrid('hideColumn','fmeasureUnit');
					$('#dg').datagrid('hideColumn','fIsImp');
					$('#dg').datagrid('hideColumn','funitPrice');
					$('#totalPrice').html("0.00元");
					fpItemsName=0;
				}else {
					fpItemsName=1;
					$('#dg').datagrid('showColumn','fnum');
					$('#dg').datagrid('showColumn','fmeasureUnit');
					$('#dg').datagrid('showColumn','fIsImp');
					$('#dg').datagrid('showColumn','funitPrice');
				}
				var rows = $('#dg').datagrid('getRows');
				$('#totalPrice').html("0.00元");
				for (var i = rows.length-1; i >= 0; i--) {
					$('#dg').datagrid('deleteRow',i);
				}
				$('#F_fpAmount').val(0.00);
			}
		},
		onChange:function(newVal,oldVal){
			if(newVal=='PMMC-4'||newVal=='PMMC-5'){
				$('#dg').datagrid('hideColumn','fnum');
				$('#dg').datagrid('hideColumn','fmeasureUnit');
				$('#dg').datagrid('hideColumn','fIsImp');
				$('#dg').datagrid('hideColumn','funitPrice');
				fpItemsName=0;
			}else {
				fpItemsName=1;
				$('#dg').datagrid('showColumn','fnum');
				$('#dg').datagrid('showColumn','fmeasureUnit');
				$('#dg').datagrid('showColumn','fIsImp');
				$('#dg').datagrid('showColumn','funitPrice');
			}
		}
	});
	//保存
	function saveCgsqApply(fCheckStauts) {
		
		if($("#f_amount").val() == ''){
			alert('请填写本次申请金额!');
			return false;
		}
		
		if($("#F_proSignName").val() == ''){
			alert('请填写采购项目签报名称!');
			return false;
		}
		
		var name = $("input[name='fpName']").val();
		if(name == ''){
			alert('请填写采购项目名!');
			return false;
		}
		
		var html = ue.getContent();
		$("#proSignContents").val(html);
		if($("#proSignContents").val() == ''){
			alert('请填写采购项目签报内容!');
			return false;
		}
		//设置审批状态
		$('#F_fCheckStauts').val(fCheckStauts);
		//设置申请状态
		$('#F_fStauts').val(fCheckStauts);
		// 在后台反序列话成采购明细Json的对象集合
		accept();
		var rows = $('#dg').datagrid('getRows');
		if(rows==null||rows==''||rows.length==0){
			alert('请填写采购清单!');
			return false;
		}
		var mingxi = "";
		var str = "其他";
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].fcommProp==null||rows[i].fcommProp==''||rows[i].fcommProp==undefined){
				alert('请填写采购清单的相关要求!');
				return false;
			}
			mingxi = mingxi + JSON.stringify(rows[i]) + ",";
		}
		for (var h = 0;h<rows.length;h++){
			if(rows[h].fpKind == null || rows[h].fpKind == '' || rows[h].fpKind == undefined){
				alert('请填写采购清单的品目!');
				return false;
			}else{
				if(rows[0].fpKind.indexOf(str) == '-1'){
					if(rows[h].fpKind.indexOf(str) != '-1'){
						alert("采购清单的品目只能是集采目录以内的或全部是集采目录以外的，请重新编辑");
						return;
					}
				}else{
					if(rows[h].fpKind.indexOf(str) == '-1'){
						alert("采购清单的品目只能是集采目录以内的或全部是集采目录以外的，请重新编辑");
						return;
					}
				}
			}
		}
		$('#mingxiJson').val(mingxi);
		/* var fpItemsName = $('#f_fpItemsName').combobox('getValue');
		if(fpItemsName==''){
			alert("请选择品目名称！");
			return false;
		} */
		//校验采购明细
		var jkcp = $('input[name="fIsImp"]:checked').val();
		var fIsImpnumber = 0;
		for (var i = 0; i < rows.length; i++) {
			if(rows[i].fIsImp=='是'){
				fIsImpnumber = (fIsImpnumber +1);
			}
		}
		if(fpItemsName!='PMMC-4' && fpItemsName!='PMMC-5'){
			if(jkcp==1&&fIsImpnumber<=0){
				alert('采购清单中无进口产品，请核对后提交!');
				return false;
			}else if(jkcp==0&&fIsImpnumber>0){
				alert('采购清单中有进口产品，请核对后提交!');
				return false;
			}
		}
		var fpAmount = $('#f_amount').val();
		var syAmount = $('#syAmount').val();
		if(fCheckStauts==1){
			/* if(fpAmount == '' || fpAmount == '0.00'||fpAmount==null) {
				alert('当前单据中,采购金额为0,请重新编辑采购清单！');
				return;
			} */
			if(parseFloat(syAmount)<parseFloat(fpAmount)){
				alert('预算可用金额不足,请重新选择预算指标！');
				return;
			}
		}
		
		var budgetPriceBasis = $('#f_budgetPriceBasis').combobox('getValue');
		if(budgetPriceBasis==''){
			alert("请选择预算价格依据！");
			return false;
		}
		var indexId = $('#F_fBudgetIndexCode').val();
		var indexName = $('#F_indexName').textbox('getValue');
		if(indexId==''||indexId==null||indexId==undefined){
			alert("请选择预算指标！");
			return false;
		}
		
		var fpMethod = $('#fpMethod').combobox('getValue');
		var fpPype = $('#fpPype').combobox('getValue');
		var cgglg = '${cgglg}';
		if(cgglg==1 && fCheckStauts==1){
			if(fpMethod == ""){
				alert('请选择采购类型！');
				return;
			}
			if(fpPype == ""){
				alert('请选择采购方式！');
				return;
			}
		}
		if(fpPype != '三家比选' && fpPype != '直接选定' && fpPype != '公开招标'){
			if($("input[name=reasonIds]").length > 0){
				var flag = false;
				for(var i = 0; i < $("input[name=reasonIds]").length; i++){
					if($("input[name=reasonIds]")[i].checked == true){
						flag = true;
					}
				}
				if(!flag){
					alert('请选择采购理由！');
					return;
				}
			}
		}
		
		
		//附件的路径地址
		var s="";
		$(".fileUrl").each(function(){
			s=s+$(this).attr("id")+",";
		});
		$("#files").val(s);
		/* var jkcp = $('input[name="fIsImp"]:checked').val();
		var s1="";
		$(".fileUrl0").each(function(){
			s1=s1+$(this).attr("id")+",";
		});
		if(jkcp==1){
			if(s1!=''){
				$("#files0").val(s1);
			}else{
				alert('请上传进口产品专家意见！');
				return false;
			}
		}else{
			$("#files0").val("");
		} */
		
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
			url : base + '/cgsqsp/save',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data=eval("("+data+")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
				    $('#cg_apply_Tab').datagrid('reload');
				    $('#indexdb').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
	}


	 
  //pathNum-路径编码
    function upladFile0(obj,serviceType,pathNum,mark) {
      var files = obj.files;
      var size=files[0].size;
      if(size==0){
    	  alert("不允许上传空文件");
    	  return false;
      }
      
      var formData = new FormData();
      //判断有没有选中附件信息
      if(files==null || files=="null"|| files.length==0){
    	  alert("请选择附件上传");
    	  return false;
      }
      //把所有的附件都存入变量，准备传给后台
      for(var i=0; i< files.length; i++){
      	 formData.append("attFiles",files[i]);
      } 
      //初始化进度条为0
      $("#percent0").html(0 + '%');
      $("#progressNumber0").css("width",""+0+"px");
      
      $('.win-left-bottom-div').hide();
      
      // 接收上传文件的后台地址 
      var url = base + "/attachment/uploadAtt?serviceType="+serviceType+"&pathNum="+pathNum;          
      //1.创建XMLHttpRequest组建    
      xmlHttpRequest = createXmlHttpRequest();  
      //post请求
      xmlHttpRequest.open("post", url, true);
      //请求成功回调
      xmlHttpRequest.onload = function (data) {
    	  callback0(mark);
    	  var resObj = JSON.parse(data.currentTarget.response);
    	  if(resObj.success && 'zdsy'==serviceType){
    		  //如果是制度索引文件上传
    		  var fileName = resObj.info.split("@")[1];
    		  var fileId = resObj.info.split("@")[0];
    		  fileName = fileName.replace('.pdf','');
    		  $('#cheter_add_fileName').textbox('setValue',fileName);
    		  $('#F_attachmentId').val(fileId);
    		  $('#systemcenter_add_uploadbtn').hide();
    	  }
      };
      //调用线程监听上传进度
      xmlHttpRequest.upload.addEventListener("progress", progressFunction0, false);
      //把文件数据发送出去
      xmlHttpRequest.send(formData);
      //清空value （作用：删除文件后再上传同一个文件，onchange方法不调用）
      event.target.value=null;
    }
	//监听进度线程，生成进度条
    function progressFunction0(evt) {
      if (evt.lengthComputable) {
    	  //evt.loaded：文件上传的大小   evt.total：文件总的大小                    
          var percentComplete = Math.round((evt.loaded) * 100 / evt.total);    
          //加载进度条，同时显示信息          
          $("#progid0").show();
          $("#percent0").html(percentComplete + '%');
          //percentComplete*2  这个2是根据总像素大小来改变的，如果是300px就 *3
          $("#progressNumber0").css("width",""+percentComplete*3+"px");   
      }
    } 
    //回调函数
    function callback0(mark) {
        //5。接收响应数据
        //判断对象的状态是交互完成
        if (xmlHttpRequest.readyState == 4) {
            //判断http的交互是否成功
            if (xmlHttpRequest.status == 200) {
                //获取服务器段输出的纯文本数据
                var responseText = xmlHttpRequest.responseText;
                //文本数据转换成json
                var jsonResponse = JSON.parse(responseText);
    	    	 if(jsonResponse.success==true){
    	    		    //获取controller 返回的对象信息
    			        var datainfo = jsonResponse.info;
    	    		    //如果上传了多个文件，返回的string就有多个，使用逗号分割，现在截取
    	    		 	var infoArray = datainfo.split(',');	
    	    		 	for(var i=0; i< infoArray.length; i++){
    	    		 		var info = infoArray[i];
    	    		 		var inf = info.split('@');
    	    		 		var id = inf[0];//附件id
    	    		 		var name = inf[1];//附件名称
    				        $('#tdf0').append(
    			    			"<div style='margin-top: 5px;'>"+
    			    				"<a href='"+base+"/attachment/download/"+id+"' style='color: #666666;font-weight: bold;'>"+name+"</a>"+
    			    				"&nbsp;&nbsp;&nbsp;&nbsp;"+
    			    				"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
    			    				"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
    			    				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+id+"' class='fileUrl0' href='#' style='color:red' onclick='deleteAttac0(this,\""+mark+"\")'>删除</a><div>"
    			    		);
    	    		 		
    	    		 	}
    	    		 	//放入附件id
    	    			var s="";
    	    			$(".fileUrl0").each(function(){
    	    				s=s+$(this).attr("id")+",";
    	    			});
    	    			$("#jzyjfiles").val(s);
    	    			$("#progid0").hide();
    	    	} else {
    	    		alert(jsonResponse.info);
    	    		$('#tdf0').append(
        				"<div style='margin-top: 5px;'>"+
        					"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
        					"&nbsp;&nbsp;&nbsp;&nbsp;"+
        					"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
        					"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
        					"&nbsp;&nbsp;&nbsp;&nbsp;<a id='fail' class='fileUrl0' href='#' style='color:red' onclick='deleteAttac0(this)'>删除</a><div>"
        			);
    	    		$("#progid0").hide();
    	    	}
            } else {
                alert("上传失败");
            }
            $('.win-left-bottom-div').show();
        }
    }
    //附件删除
    function deleteAttac0(obj,mark){
    	if(confirm("确认删除吗？")){
    		$.ajax({ 
    			type: 'POST', 
    			url: base+'/attachment/delete/'+obj.id,
    			dataType: 'json',  
    			success: function(data){ 
    				if(data.success){
    					$.messager.alert('系统提示',data.info,'info');
    					$(obj).parent().remove();
    					if(mark=='zdsy'){
    						//如果是制度索引新增
    						$('#systemcenter_add_uploadbtn').show();
    					}
    				}else{
    					$.messager.alert('系统提示',data.info,'error');
    				}
    			} 
    		}); 
    	}
    }
    
    
    function workFlow1(){
    	$('#check_system_div').load('${base}/cgsqsp/refreshProcess?workFlow=1');
    }
    function workFlow2(){
    	$('#check_system_div').load('${base}/cgsqsp/refreshProcess?workFlow=2');
    }
    
</script>
<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('proSignContent');
    
  //对编辑器的操作最好在编辑器ready之后再做
  ue.ready(function() {
	  var proSignContent ='${bean.proSignContent}';
      //设置编辑器的内容
      if(proSignContent==''){
     	 ue.setContent('');
      }else{
      	ue.setContent(proSignContent);
      }
  });
</script>
</body>