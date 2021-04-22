<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
	<script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/lang/zh-cn/zh-cn.js"></script>
<div class="window-div">
<form id="process_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div"  style="width:750px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:false">
			<div class="win-left-top-div">
				<div class="tab-wrapper" id="yx-tab">
					<ul class="tab-menu">
					    <li class="active">采购申请</li>
					    <c:if test="${DJ==1 }">
						<li>过程登记</li>
						</c:if>
						<c:if test="${YS==1 }">
						<li>采购验收</li>
						</c:if>
					</ul>
					<div class="tab-content">
						<div title="采购申请" style="margin-bottom:35px;" data-options="">
								<div class="easyui-accordion" data-options="" id="" style="width:698px;margin-left: 20px">
									<div  title="采购项目签报表" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
										style="overflow:auto;margin-top: 10px;">
										<table cellpadding="0" cellspacing="0" class="ourtable">
											<!-- 表单样式参考 -->
											<%-- <tr >
												<td style="width: 150px" colspan="5">采购项目签报名称</td>
											</tr>
											<tr >
												<td colspan="5">
													<input class="easyui-textbox" id="F_proSignName" readonly="readonly" name="proSignName"  style="width:670px;" value="${bean.proSignName}"/>
												</td>
											</tr> --%>
					
											<tr >
												<td colspan="5">采购项目签报内容</td>
											</tr>
											<tr >
												<td colspan="5">
													<textarea id="proSignContentLedger" style="width:670px;height:800px;"></textarea>
												</td>
											</tr>
										</table>
									</div>
									<div title="采购信息" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="../purchase/cgjh_msg.jsp" />
									</div>
		
									<!-- 第4个div -->
									<div title="采购清单" id="CGdetail" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
		 							  	<jsp:include page="../purchase/select_cgconf_plan_mingxi_detail.jsp" />												
									</div>
									<div title="党组会会议号" id="" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
										<table cellpadding="0" cellspacing="0" class="ourtable" style="">
											<tr>
												<td>
													<input class="easyui-textbox"  name="fDZHCode" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fDZHCode}"/>
												</td>
											</tr>
										</table>
									</div>
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
											<tr class="trbody" id="fhyzgbmyjfTr">
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
										</table>
									</div>
									<div title="审批记录" id="checkId1" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="check_history_ledgerApply.jsp" />												
									</div> 
							</div>
						</div>
						<c:if test="${DJ==1 }">
						<div title="过程登记" style="margin-bottom:35px;" data-options="collapsed:false,collapsible:false">
							<div class="easyui-accordion" data-options="" id="" style="width:698px;margin-left: 20px">
								<!-- 第1个div -->
									<%-- <div title="基本信息"  id="gysxxdiv1"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="../process/new/process_detail.jsp" />
									</div> --%>
									<div title="采购邀请"  id="gysxxdiv2"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="../process/new/process_invite_detail.jsp" />
									</div>
									<div title="竞争过程"  id="gysxxdiv3"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="../process/new/process_course_detail.jsp" />
									</div>
									<div title="采购结果"  id="gysxxdiv4"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="../process/new/purchasing_results_detail.jsp" />
									</div>
									<div title="审批记录" id="checkId2" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="check_history_ledgerT.jsp" />												
									</div>
							</div>
						</div>
						</c:if>
						<c:if test="${YS==1}">
						<div title="采购验收" style="margin-bottom:35px;" data-options="collapsed:false,collapsible:false">
							<div class="easyui-accordion" data-options="" id="" style="width:698px;margin-left: 20px">
								<!-- 第1个div -->
						 		<div title="验收信息" id="gysxxdiv5" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
							  		<jsp:include page="receive_mingxi.jsp" />
								</div>
								<div title="审批记录" id="checkId3" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
									<jsp:include page="check_history_ledgerC.jsp" />												
								</div>
							</div>
						</div>
						</c:if>
					</div>
				</div>
			</div>	
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
</form>
</div>
<script type="text/javascript">
	//加载tab页
	flashtab('yx-tab');
	var ulItems = document.getElementById("yx-tab");
	ulItems.onclick = function(e){
	e = e || window.event;//这一行及下一行是为兼容IE8及以下版本
	var target = e.target || e.srcElement;
	if(target.tagName.toLowerCase() === "li"){
		if(target.innerText=="采购申请"){
			$.parser.parse("#CGdetail");
			$.parser.parse("#checkId1");
		}else{
			if(target.innerText=="过程登记"){
				$.parser.parse("#gysxxdiv4");
				$.parser.parse("#checkId2");
			}else{
				if(target.innerText=="采购验收"){
					$.parser.parse("#gysxxdiv5");
					$.parser.parse("#checkId3");
				}
			}
		}
	}
	}
	
	$(document).ready(function() {
		var fpPype = '${bean.fpPype}';
		if(fpPype=='GKZB'){
			$("#fujian01").html('<span class="style1">*</span>招标公告');
			$("#fujian02").html('<span class="style1">*</span>招标文件');
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#fujian04").html('<span class="style1">*</span>供应商投标书');
			$("#fujian05").html('<span class="style1">*</span>供应商资质证明');
		}else if(fpPype=='YQZB'){
			$("#fujian01").html('<span class="style1">*</span>招标公告');
			$("#fujian02").html('<span class="style1">*</span>招标文件');
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#fujian04").html('<span class="style1">*</span>供应商投标书');
			$("#fujian05").html('<span class="style1">*</span>供应商资质证明');
		}else if(fpPype=='JZXCS'){
			$("#fujian01").html('<span class="style1">*</span>磋商邀请函');
			$("#fujian02").html('<span class="style1">*</span>竞争性磋商文件');
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#fujian04").html('<span class="style1">*</span>供应商投标书');
			$("#fujian05").html('<span class="style1">*</span>供应商资质证明');
		}else if(fpPype=='JZXTP'){
			$("#fujian01").html('<span class="style1">*</span>谈判邀请函');
			$("#fujian02").html('<span class="style1">*</span>竞争性谈判文件');
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#fujian04").html('<span class="style1">*</span>供应商投标书');
			$("#fujian05").html('<span class="style1">*</span>供应商资质证明');
		}else if(fpPype=='DYLY'){
			$("#fujian01").html('<span class="style1">*</span>单一来源采购公告');
			$("#fujian02").html('<span class="style1">*</span>单一来源采购申请表');
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#trFJ04").remove();
			$("#trFJ05").remove();
		}else if(fpPype=='XBJ'){
			$("#fujian01").html('<span class="style1">*</span>询价采购邀请函');
			$("#trFJ02").remove();
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#fujian04").html('<span class="style1">*</span>供应商投标报价文件');
			$("#fujian05").html('<span class="style1">*</span>供应商资质证明');
		}else if(fpPype=='XYGH'){
			$("#fujian01").html('<span class="style1">*</span>采购信息公告');
			$("#trFJ02").remove();
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#trFJ04").remove();
			$("#trFJ05").remove();
		}else if(fpPype=='DDCG'){
			$("#fujian01").html('<span class="style1">*</span>定点采购公告');
			$("#trFJ02").remove();
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#trFJ04").remove();
			$("#trFJ05").remove();
		}else if(fpPype=='ZXZB'){
			$("#fujian01").html('<span class="style1">*</span>招标公告');
			$("#fujian02").html('<span class="style1">*</span>招标文件');
			$("#fujian03").html('<span class="style1">*</span>采购评价报告');
			$("#fujian04").html('<span class="style1">*</span>供应商投标书');
			$("#fujian05").html('<span class="style1">*</span>供应商资质证明');
		}else{
			$("#gysxxdiv2").remove();
			$("#gysxxdiv3").remove();
		}
		
		});
</script>
<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('proSignContentLedger');
    
    //对编辑器的操作最好在编辑器ready之后再做
    ue.ready(function(){
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