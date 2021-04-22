<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/lang/zh-cn/zh-cn.js"></script>
<div class="window-div">
<form id="cgsq_execution_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:715px;margin-left: 20px">
				
					<div  title="采购项目签报表" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<tr >
								<td style="width: 150px" colspan="5">采购项目签报名称</td>
							</tr>
							<tr >
								<td colspan="5">
									<input class="easyui-textbox" id="F_proSignName" readonly="readonly" name="proSignName"  style="width:670px;" value="${bean.proSignName}"/>
								</td>
							</tr>
	
							<tr >
								<td colspan="5">采购项目签报内容</td>
							</tr>
							<tr >
								<td colspan="5">
									<textarea id="proSignContent" style="width:670px;height:800px;"></textarea>
								</td>
							</tr>
						</table>
					</div>
				
				
					<div  title="采购信息" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;项目编号</td>
								<td class="td2">
									<input class="easyui-textbox" id="F_fpCode" readonly="readonly" name="fpCode"  style="width:200px;" value="${bean.fpCode}"/>
								</td>
								<td class="td4">
									<!-- 隐藏域 --> 
									<input type="hidden" name="nCode" value="${bean.nCode}"/>
									<input type="hidden" name="fpId"  value="${bean.fpId}"/>
									<%-- <input type="hidden" name="fReqTime" id="fTime" value="${proposer.upTime}"/> --%>
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
								<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
								<td class="td2">
									<input id="" class="easyui-textbox" type="text" required="required" readonly="readonly" name="fpName" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fpName}"/>
								</td>
							</tr>
	
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;申请部门</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" id="" readonly="readonly" required="required" name="fDeptName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fDeptName}"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请人</td>
								<td class="td2">
									<input id="" class="easyui-textbox" type="text" readonly="readonly" required="required" name="fUserName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fUserName}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;品目名称</td>
								<td class="td2">
									<input class="easyui-combobox" id="f_fpItemsName_affirm" name="fpItemsName" readonly="readonly" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PMMC&selected=${bean.fpItemsName}',method:'get',valueField:'code',textField:'text',editable:false"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请时间</td>
								<td class="td2">
									<input class="easyui-datebox" class="dfinput" id="" readonly="readonly" name="fReqTime" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fReqTime}"/>
								</td>
							</tr>
							
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;进口产品</td>
								<td class="td2" colspan="4">
         							<input type="radio" onclick="jkcp(1)" name="fIsImp" disabled="disabled" <c:if test="${bean.fIsImp=='1'}">checked="checked"</c:if> value="1">是</input>
         							<input type="radio" onclick="jkcp(0)" name="fIsImp" disabled="disabled" <c:if test="${bean.fIsImp!='1'}">checked="checked"</c:if> value="0">否</input>
								</td>
							</tr>
							
							<tr class="trbody" id="jkzjyjTr" hidden="hidden">
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
							</tr>
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
								<td class="window-table-td1"><p>批复金额：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_pfAmount">${bean.pfAmount}元</p></td>
								
								<td class="window-table-td1"><p>批复时间：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_pfDate">${bean.pfDate}</p></td>
							</tr>
							
							<tr>
								<td class="window-table-td1"><p>使用部门：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_pfDepartName">${bean.pfDepartName}</p></td>
								
								<td class="window-table-td1"><p>可用余额：&nbsp;</p></td>
								<td class="window-table-td2"><p id="p_syAmount">${bean.syAmount}元</p></td>
							</tr>
						</table>
							
						<table cellpadding="0" cellspacing="0" class="ourtable" style="margin-top: 20px;">
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;附件
								</td>
								<td colspan="4" id="tdf">
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='cggl' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<%-- <img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a> --%>
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
						</table>
					</div>
					<div title="采购清单" id="cgqddiv" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
					  	<jsp:include page="select_cgconf_plan_mingxi_detail.jsp" />
					</div>
					<c:if test="${bean.amount>=50000 }">
					<div title="党组会会议号" id="" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable" style="">
							<tr>
								<td>
									<input class="easyui-textbox"  name="fDZHCode" readonly="readonly" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fDZHCode}"/>
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
									<input class="easyui-combobox" id="fpMethod" name="fpMethod" readonly="readonly" style="width: 200px;" data-options="url:'${base}/lookup/lookupsJson?parentCode=PURCHASE_METHOD&selected=${bean.fpMethod}',method:'get',valueField:'code',textField:'text',editable:false,
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
							<tr class="trbody">
								<td class="td1">
									<span class="style1">*</span>&nbsp;行业主管部门意见
									<input type="file" multiple="multiple" id="fhyzgbmyjf" onchange="hyzgbmyjupladFile(this,'hyzgbmyj','cgfsqr01')" hidden="hidden">
									<input type="text" id="hyzgbmyjfiles" name="hyzgbmyjfiles" hidden="hidden" >
								</td>
								<td colspan="4" id="hyzgbmyjtdf">
									<a onclick="$('#fhyzgbmyjf').click()" style="font-weight: bold;" href="#">
										<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="hyzgbmyjprogid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
								        <div id="hyzgbmyjprogressNumber" style="background:#3AF960;width:0px;height:10px" >
								        </div>文件上传中...&nbsp;&nbsp;<font id="hyzgbmyjpercent">0%</font> 
						    	    </div>
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='hyzgbmyj' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a id="${att.id}" class="hyzgbmyjfileUrl" href="#" style="color:red" onclick="hyzgbmyjdeleteAttac(this)">删除</a>
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
									<a onclick="$('#fczbmyj').click()" style="font-weight: bold;" href="#">
										<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="czbmyjprogid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
								        <div id="czbmyjprogressNumber" style="background:#3AF960;width:0px;height:10px" >
								        </div>文件上传中...&nbsp;&nbsp;<font id="czbmyjpercent">0%</font> 
						    	    </div>
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='czbmyj' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a id="${att.id}" class="czbmyjfileUrl" href="#" style="color:red" onclick="czbmyjdeleteAttac(this)">删除</a>
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							</c:if>
						</table>
					</div>
					<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<jsp:include page="../../check_history.jsp" />												
					</div> 
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveExecution(1)">
					<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="window-right-div" id="check_system_div" data-options="region:'east',split:true" style="margin: 20px 20px 30px 0px;">
			<jsp:include page="../../check_system.jsp" />
		</div>
</form>
</div>
<script type="text/javascript">
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

/* //根据选择的采购类型刷新审批流
$('#F_fpPype').combobox({
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
$('#f_fpItemsName_affirm').combobox({
	onSelect:function(record){
		totalfpItemsName += 1;
		if(totalfpItemsName>1){
			if(record.code=='PMMC-4'||record.code=='PMMC-5'){
				$('#dg_detail').datagrid('hideColumn','fnum');
				$('#dg_detail').datagrid('hideColumn','fmeasureUnit');
				$('#dg_detail').datagrid('hideColumn','fIsImp');
				$('#dg_detail').datagrid('hideColumn','funitPrice');
				$('#totalPrice').numberbox('setValue',null);
				fpItemsName=0;
			}else {
				fpItemsName=1;
				$('#dg_detail').datagrid('showColumn','fnum');
				$('#dg_detail').datagrid('showColumn','fmeasureUnit');
				$('#dg_detail').datagrid('showColumn','fIsImp');
				$('#dg_detail').datagrid('showColumn','funitPrice');
			}
			var rows = $('#dg_detail').datagrid('getRows');
			$('#totalPrice').numberbox('setValue',null);
			for (var i = rows.length-1; i >= 0; i--) {
				$('#dg_detail').datagrid('deleteRow',i);
			}
		}
	},
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
//保存
function saveExecution(fCheckStauts) {
	
	//附件的路径地址
	var s0="";
	$(".hyzgbmyjfileUrl").each(function(){
		s0=s0+$(this).attr("id")+",";
	});
	$("#hyzgbmyjfiles").val(s0);
	var s1="";
	$(".czbmyjfileUrl").each(function(){
		s1=s1+$(this).attr("id")+",";
	});
	$("#czbmyjfiles").val(s1);
	
	if('${bean.fIsImp}'=='1'){//是进口
		if($("#czbmyjfiles").val()==null||$("#czbmyjfiles").val()==''){
			alert('请上传财政部门意见');
			return false;
		}
		if($("#hyzgbmyjfiles").val()==null||$("#hyzgbmyjfiles").val()==''){
			alert('请上传行业主管部门意见');
			return false;
		}
	}
	//提交
	$('#cgsq_execution_form').form('submit', {
		onSubmit : function() {
			//获得校验结果
			flag = $(this).form('enableValidation').form('validate');
			if (flag) {
				//如果校验通过，则进行下一步
				$.messager.progress();
			}
			return flag;
		},
		url : base + '/cgsqsp/executionSave',
		success : function(data) {
			if (flag) {
				$.messager.progress('close');
			}
			data=eval("("+data+")");
			if (data.success) {
				$.messager.alert('系统提示', data.info, 'info');
			    $('#cg_apply_execution_Tab').datagrid('reload');
				closeWindow();
			} else {
				$.messager.alert('系统提示', data.info, 'error');
			}
		}
	});
}


 
 //pathNum-路径编码
   function hyzgbmyjupladFile(obj,serviceType,pathNum,mark) {
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
     $("#hyzgbmyjpercent").html(0 + '%');
     $("#hyzgbmyjprogressNumber").css("width",""+0+"px");
     
     $('.win-left-bottom-div').hide();
     
     // 接收上传文件的后台地址 
     var url = base + "/attachment/uploadAtt?serviceType="+serviceType+"&pathNum="+pathNum;          
     //1.创建XMLHttpRequest组建    
     xmlHttpRequest = createXmlHttpRequest();  
     //post请求
     xmlHttpRequest.open("post", url, true);
     //请求成功回调
     xmlHttpRequest.onload = function (data) {
    	 hyzgbmyjcallback(mark);
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
         $("#hyzgbmyjprogid").show();
         $("#hyzgbmyjpercent").html(percentComplete + '%');
         //percentComplete*2  这个2是根据总像素大小来改变的，如果是300px就 *3
         $("#hyzgbmyjprogressNumber").css("width",""+percentComplete*3+"px");   
     }
   } 
   //回调函数
   function hyzgbmyjcallback(mark) {
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
   				        $('#hyzgbmyjtdf').append(
   			    			"<div style='margin-top: 5px;'>"+
   			    				"<a href='"+base+"/attachment/download/"+id+"' style='color: #666666;font-weight: bold;'>"+name+"</a>"+
   			    				"&nbsp;&nbsp;&nbsp;&nbsp;"+
   			    				"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
   			    				"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
   			    				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+id+"' class='hyzgbmyjfileUrl' href='#' style='color:red' onclick='hyzgbmyjdeleteAttac(this,\""+mark+"\")'>删除</a><div>"
   			    		);
   	    		 	}
   	    		 	//放入附件id
   	    			var s="";
   	    			$(".hyzgbmyjfileUrl").each(function(){
   	    				s=s+$(this).attr("id")+",";
   	    			});
   	    			$("#hyzgbmyjfiles").val(s);
   	    			$("#hyzgbmyjprogid").hide();
   	    	} else {
   	    		alert(jsonResponse.info);
   	    		$('#hyzgbmyjtdf').append(
       				"<div style='margin-top: 5px;'>"+
       					"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
       					"&nbsp;&nbsp;&nbsp;&nbsp;"+
       					"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
       					"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
       					"&nbsp;&nbsp;&nbsp;&nbsp;<a id='fail' class='hyzgbmyjfileUrl' href='#' style='color:red' onclick='hyzgbmyjdeleteAttac(this)'>删除</a><div>"
       			);
   	    		$("#hyzgbmyjprogid").hide();
   	    	}
           } else {
               alert("上传失败");
           }
           $('.win-left-bottom-div').show();
       }
   }
   //附件删除
   function hyzgbmyjdeleteAttac(obj,mark){
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
   
   //pathNum-路径编码
   function czbmyjupladFile(obj,serviceType,pathNum,mark) {
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
     $("#czbmyjpercent").html(0 + '%');
     $("#czbmyjprogressNumber").css("width",""+0+"px");
     
     $('.win-left-bottom-div').hide();
     
     // 接收上传文件的后台地址 
     var url = base + "/attachment/uploadAtt?serviceType="+serviceType+"&pathNum="+pathNum;          
     //1.创建XMLHttpRequest组建    
     xmlHttpRequest = createXmlHttpRequest();  
     //post请求
     xmlHttpRequest.open("post", url, true);
     //请求成功回调
     xmlHttpRequest.onload = function (data) {
   	  callback1(mark);
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
     xmlHttpRequest.upload.addEventListener("progress", progressFunction1, false);
     //把文件数据发送出去
     xmlHttpRequest.send(formData);
     //清空value （作用：删除文件后再上传同一个文件，onchange方法不调用）
     event.target.value=null;
   }
//监听进度线程，生成进度条
   function progressFunction1(evt) {
     if (evt.lengthComputable) {
   	  //evt.loaded：文件上传的大小   evt.total：文件总的大小                    
         var percentComplete = Math.round((evt.loaded) * 100 / evt.total);    
         //加载进度条，同时显示信息          
         $("#czbmyjprogid").show();
         $("#czbmyjpercent").html(percentComplete + '%');
         //percentComplete*2  这个2是根据总像素大小来改变的，如果是300px就 *3
         $("#czbmyjprogressNumber").css("width",""+percentComplete*3+"px");   
     }
   } 
   //回调函数
   function callback1(mark) {
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
   				        $('#czbmyjtdf').append(
   			    			"<div style='margin-top: 5px;'>"+
   			    				"<a href='"+base+"/attachment/download/"+id+"' style='color: #666666;font-weight: bold;'>"+name+"</a>"+
   			    				"&nbsp;&nbsp;&nbsp;&nbsp;"+
   			    				"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
   			    				"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
   			    				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+id+"' class='czbmyjfileUrl' href='#' style='color:red' onclick='czbmyjdeleteAttac(this,\""+mark+"\")'>删除</a><div>"
   			    		);
   	    		 		
   	    		 	}
   	    		 	//放入附件id
   	    			var s="";
   	    			$(".czbmyjfileUrl").each(function(){
   	    				s=s+$(this).attr("id")+",";
   	    			});
   	    			$("#czbmyjfiles").val(s);
   	    			$("#czbmyjprogid").hide();
   	    	} else {
   	    		alert(jsonResponse.info);
   	    		$('#czbmyjtdf').append(
       				"<div style='margin-top: 5px;'>"+
       					"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
       					"&nbsp;&nbsp;&nbsp;&nbsp;"+
       					"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
       					"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
       					"&nbsp;&nbsp;&nbsp;&nbsp;<a id='fail' class='czbmyjfileUrl' href='#' style='color:red' onclick='czbmyjdeleteAttac(this)'>删除</a><div>"
       			);
   	    		$("#czbmyjprogid").hide();
   	    	}
           } else {
               alert("上传失败");
           }
           $('.win-left-bottom-div').show();
       }
   }
   //附件删除
   function czbmyjdeleteAttac(obj,mark){
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
</script>

<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('proSignContent');
    
  //对编辑器的操作最好在编辑器ready之后再做
  ue.ready(function() {
	  var proSignContent ='${beanCopy.proSignContent}';
      //设置编辑器的内容
      if(proSignContent==''){
     	 ue.setContent('');
      }else{
      	ue.setContent(proSignContent);
      }
      ue.setDisabled();
  });
</script>
</body>