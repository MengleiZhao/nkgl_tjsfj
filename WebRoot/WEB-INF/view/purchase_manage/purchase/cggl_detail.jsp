<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/lang/zh-cn/zh-cn.js"></script>
<div class="window-div">
<form id="cgsq_apply_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:750px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="easyAcc" style="width:682px;margin-left: 20px">
					<div  title="采购项目签报表" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
							style="overflow:auto;margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable">
								<!-- 表单样式参考 -->
								<%-- <tr >
									<td style="width: 150px" colspan="5">采购项目签报名称</td>
								</tr>
								<tr >
									<td colspan="5">
										<input class="easyui-textbox" readonly="readonly" id="F_proSignName" name="proSignName"  style="width:670px;" value="${bean.proSignName}"/>
									</td>
								</tr> --%>
		
								<tr >
									<td colspan="5">采购项目签报内容</td>
								</tr>
								<tr >
									<td colspan="5">
										<textarea id="proSignContentDetail" readonly="readonly" style="width:670px;height:800px;"></textarea>
									</td>
								</tr>
							</table>
						</div>
					<div  title="采购信息" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>采购项目编号</td>
								<td class="td2">
									<input class="easyui-textbox" id="F_fpCode" readonly="readonly" name="fpCode"  style="width:200px;" value="${bean.fpCode}"/>
								</td>
								<td class="td4">
									<!-- 隐藏域 --> 
									<input type="hidden" name="nCode" value="${bean.nCode}"/>
									<input type="hidden" name="fpId"  value="${bean.fpId}"/>
				    				<input type="hidden" name="fCheckStauts" id="F_fCheckStauts" value="${bean.fCheckStauts}"/><!--采购审批状态  -->
				    				<input type="hidden" name="fStauts" id="F_fStauts" value="${bean.fStauts}"/><!--采购数据的删除状态  -->
				    				<input type="hidden" name="fIsReceive" id="F_fIsReceive" value="${bean.fIsReceive}"/><!--验收状态  -->
				    				<input type="hidden" name="fbidStauts" id="F_fbidStauts" value="${bean.fbidStauts}"/><!--中标状态  -->
				    				<input type="hidden" name="fpayStauts" id="F_fpayStauts" value="${bean.fpayStauts}"/><!--付款申请的审批状态  -->
				    				<input type="hidden" name="fevalStauts" id="F_fevalStauts" value="${bean.fevalStauts}"/><!--供应商的评价状态  -->
				    				<input type="hidden" name="indexId" id="F_fBudgetIndexCode" value="${bean.indexId}"/><!-- 指标ID -->
				    				<input type="hidden" name="indexType" id="F_indexType" value="${bean.indexType}"/><!--采购指标type  -->
				    				
									<!-- 申请人ID --><input type="hidden" id="F_fUser" name="fUser" value="${bean.fUser}"/>
									<!-- 项目支出明细ID --><input type="hidden" id="F_proDetailId" name="proDetailId" value="${bean.proDetailId}"/>
									<!-- 可用金额  --><input type="hidden" id="syAmount" value="${bean.syAmount}"/>
									<!-- 批复金额  --><input type="hidden" id="pfAmount" value="${bean.pfAmount}"/>
									<!-- 批复时间  --><input type="hidden" id="pfDate"  value="${bean.pfDate}"/>
									<!-- 采购金额  --><input type="hidden" id="F_fpAmount"  name="amount" value="${bean.amount}"/>
									<!-- 下一级审批节点  --><input type="hidden" id="nextKey"  value="${bean.nCode}"/>
									<!--历史审批节点  --><input type="hidden" id="historyNodes"  value="${historyNodes}"/>
								</td>
								<%-- <td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
								<td class="td2">
									<input id="" class="easyui-textbox" type="text" required="required" readonly="readonly" name="fpName" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fpName}"/>
								</td> --%>
								<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" id="" readonly="readonly" required="required" name="fDeptName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fDeptName}"/>
								</td>
							</tr>
	
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;申请日期</td>
								<td class="td2">
									<input class="easyui-datebox" class="dfinput" id="" readonly="readonly" name="fReqTime" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fReqTime}"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
								<td class="td2">
									<input id="" class="easyui-textbox" type="text" readonly="readonly" required="required" name="fUserName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fUserName}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;本次申请金额:</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" id="f_amount" required="required" name="amount" style="width: 200px;" data-options="icons: [{iconCls:'icon-yuan'}]" value="${bean.amount}" readonly="readonly"/>
								</td>
								<td class="td4"></td>
								
								<td class="td1" id = "objName1"><span class="style1">*</span>&nbsp;采购项目名称:</td>
								<td class="td2" id = "objName2">
									<input id="F_fpName" class="easyui-textbox" type="text" name="fpName" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fpName}"  readonly="readonly"/>
								</td>
							</tr>
							
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;进口产品</td>
								<td class="td2">
         							<input type="radio" onclick="jkcp(1)" name="fIsImp" disabled="disabled" <c:if test="${bean.fIsImp=='1'}">checked="checked"</c:if> value="1">是</input>
         							<input type="radio" onclick="jkcp(0)" name="fIsImp" disabled="disabled" <c:if test="${bean.fIsImp!='1'}">checked="checked"</c:if> value="0">否</input>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;涉密采购</td>
								<td class="td2">
         							<input type="radio" onclick="jkcp(1)" name="fIsPers" disabled="disabled" <c:if test="${bean.fIsPers=='1'}">checked="checked"</c:if> value="1">是</input>
         							<input type="radio" onclick="jkcp(0)" name="fIsPers" disabled="disabled" <c:if test="${bean.fIsPers!='1'}">checked="checked"</c:if> value="0">否</input>
								</td>
							</tr>
							
							<%-- <tr class="trbody" id="jkzjyjTr" hidden="hidden">
								<td class="td1">
									&nbsp;&nbsp;进口产品专家意见
								</td>
								<td colspan="4" id="tdf0">
									<c:forEach items="${attac}" var="att0">
										<c:if test="${att0.serviceType=='JKCPZJYJ' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att0.id}' style="color: #666666;font-weight: bold;">${att0.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr> --%>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;预算指标</td>
								<td colspan="4">
									<a  href="#">
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
							
							<%-- <tr>
								<td class="window-table-td1"><p>使用部门：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_pfDepartName">${bean.pfDepartName}</p></td>
								
								<td class="window-table-td1"><p>批复时间：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_pfDate">${bean.pfDate}</p></td>
							</tr>
							<tr>
								<td class="window-table-td1"><p>资金渠道：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_pfIndexType">${bean.pfIndexType}</p></td>
								
								<td class="window-table-td1"><p></p></td>
								<td class="window-table-td2">
									<p id="">
										
									</p>
								</td>
							</tr> --%>
						</table>
							
						<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 20px;">
							<tr>
								<td class="td1"><span class="style1">*</span>&nbsp;预算价格依据</td>
								<td colspan="4">
									<input class="easyui-combobox" id="f_budgetPriceBasis" readonly="readonly" name="budgetPriceBasis" value="${bean.budgetPriceBasis}" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=YSJGYJ&selected=${bean.budgetPriceBasis}',method:'get',valueField:'code',textField:'text',editable:false"/>
								</td>
							</tr>
							
						</table>
					</div>
					<div title="采购清单" id="cgqddiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
					  	<jsp:include page="select_cgconf_plan_mingxi_detail.jsp" />
					</div>

					<div title="采购方式确认" id="cgqddiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;采购类型:</td>
								<td class="td2">
									<input class="easyui-combobox" id="fpMethod" name="fpMethod" readonly="readonly" style="width: 200px;" data-options="editable:false" value="${bean.fpMethod}"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;采购方式:</td>
								<td class="td2">
									<input class="easyui-combobox" id="fpPype" name="fpPype" style="width: 200px;" readonly="readonly" data-options="editable:false" value="${bean.fpPype}"/>
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
									<textarea name="fRemark" id="fRemark" class="textbox-text" readonly="readonly"
											oninput="textareaNum(this,'textareaNum1')" autocomplete="off"
											style="width:545px;height:70px;resize:none; border-radius: 5px;border: 1px solid #D9E3E7; margin-top:8px; margin-bottom:0px;">${bean.fRemark}</textarea>
								</td>
							</tr>
							<%-- <c:if test="${bean.fIsImp=='1'}">
							<tr class="trbody">
								<td class="td1">
									<span class="style1">*</span>&nbsp;行业主管部门意见
									<input type="file" multiple="multiple" id="fhyzgbmyjf" onchange="hyzgbmyjupladFile(this,'hyzgbmyj','cgfsqr01')" hidden="hidden">
									<input type="text" id="hyzgbmyjfiles" name="hyzgbmyjfiles" hidden="hidden" >
								</td>
								<td colspan="4" id="hyzgbmyjtdf">
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='hyzgbmyj' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1">
									<span class="style1">*</span>&nbsp;财政部门意见
									<input type="file" multiple="multiple" id="fczbmyj" onchange="czbmyjupladFile(this,'czbmyj','cgfsqr01')" hidden="hidden">
									<input type="text" id="czbmyjfiles" name="czbmyjfiles" hidden="hidden" >
								</td>
								<td colspan="4" id="czbmyjtdf">
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='czbmyj' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							</c:if> --%>
						</table>
					</div>
					<c:if test="${bean.amount>=200000 }">
						<div title="党委会会议号" id="cgdwhdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 10px;">
								<tr>
									<td class="td1"><span class="style1">*</span>&nbsp;编号:</td>
									<td colspan="4">
										<input class="easyui-textbox" id="f_cdgwh" name="fDZHCode" style="width: 200px;" data-options="" value="${bean.fDZHCode }" readonly="readonly"/>
									</td>
								</tr>
								<tr class="trbody">
									<td class="td1">
										&nbsp;<span class="style1">*</span>&nbsp;会议纪要上传：
										<input type="file" multiple="multiple" id="f1" onchange="upladFile(this,'hyjy','cggl01')" hidden="hidden">
										<input type="text" id="hyjyfiles" name="hyjyfiles"  hidden="hidden">
									</td>
									<td colspan="4" id="tdf">
										<c:forEach items="${attac}" var="att">
											<c:if test="${att.serviceType=='hyjy' }">
												<div style="margin-top: 10px;">
													<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
													&nbsp;&nbsp;&nbsp;&nbsp;
												</div>
											</c:if>
										</c:forEach>
									</td>
								</tr>
							</table>
						</div>
					</c:if>
					<div title="附件信息" id="cgfjdiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 10px;margin-left: -60px">
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;附件
									<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'cggl','cggl01')" hidden="hidden">
									<input type="text" id="files" name="files"  hidden="hidden">
								</td>
								<td colspan="4" id="tdf">
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='cggl' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
						</table>
					</div>
					<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<jsp:include page="../../check_history.jsp" />												
					</div> 
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<c:if test="${openType!='contract' }">
					<a href="javascript:void(0)" onclick="closeWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</c:if>
				<c:if test="${openType=='contract' }">
					<a href="javascript:void(0)" onclick="closeSecondWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</c:if>
			</div>
		</div>
	
		<div class="window-right-div" id="check_system_div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
</form>
</div>
<script type="text/javascript">
var a = '${bean.amount}';
var b = '${cgType}';
if(a != null && a != '' && a != undefined){
	$("#cgReason").show();
	$.ajax({
		url:'${base}/cgsqsp/cgsqspJson?amount='+a+'&parentCode='+b,
		success:function(data){
			var json = eval("("+data+")");
			var html = '';
			var flag = false;
			if(json.length == 1){
				$("#cgReason").hide();
			}
			json.forEach(function(val,index){
				var arr = '${bean.buyInfos}'.split(',');
				
				if(val.text != '--请选择--'){
					for(var i=0;i<arr.length;i++){
						if(arr[i] == val.id){
							flag = true;
						}
					}
					if(flag){
						html += '<input type="checkbox" name="reasonIds" disabled="disabled" checked="checked" value="'+val.id+'"/>&nbsp;'+val.text+'&nbsp;<br>';
						flag = false;
					}else{
						html += '<input type="checkbox" name="reasonIds" disabled="disabled" value="'+val.id+'"/>&nbsp;'+val.text+'&nbsp;<br>';
					}
				}
				$("#reasonChoose").html(html);
			});
		}
	});
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
			$('#f_amount').val(fomatMoney(f_amount,2));
		}
		//批复时间
		var pfDate = $("#pfDate").val();
		if(pfDate !=""){
			$('#p_pfDate').html(ChangeDateFormat(pfDate));
		}
		var jkcp = $('input[name="fIsImp"]:checked').val();
		if(jkcp==1){
			$('#jkzjyjTr').show();
		} else {
			$('#jkzjyjTr').hide();
		}
	});
	
	//根据选择的采购类型刷新审批流
	$('#F_fpPype').combobox({
		onChange: function (newValue, oldValue) {
			$('#check_system_div').load('${base}/cgsqsp/refreshProcess?fpPype='+newValue);
		}
	});
	
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
	$('#f_fpItemsNames').combobox({
		onChange:function(newVal,oldVal){
			
			if(newVal=='PMMC-4'||newVal=='PMMC-5'){
				$('#dg_detail').datagrid('hideColumn','fnum');
				$('#dg_detail').datagrid('hideColumn','fmeasureUnit');
				$('#dg_detail').datagrid('hideColumn','fIsImp');
				$('#dg_detail').datagrid('hideColumn','funitPrice');
				fpItemsName=0;
			}else {
				fpItemsName=1;
				$('#dg_detail').datagrid('showColumn','fnum');
				$('#dg_detail').datagrid('showColumn','fmeasureUnit');
				$('#dg_detail').datagrid('showColumn','fIsImp');
				$('#dg_detail').datagrid('showColumn','funitPrice');
			}
		}
	});
	
	
</script>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('proSignContentDetail',{

    	enterTag:'' ,

    	});
  //对编辑器的操作最好在编辑器ready之后再做
  ue.ready(function() {
	  var proSignContentDetail ='${beanCopy.proSignContent}';
      //设置编辑器的内容
      if(proSignContentDetail==''){
     	 ue.setContent('');
      }else{
      	ue.setContent(proSignContentDetail);
      }
      
      ue.setDisabled();
  });
</script>
</body>