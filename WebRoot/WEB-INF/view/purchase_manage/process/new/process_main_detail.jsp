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
					    <li onclick="onclickdetailProcessCG()">采购申请</li>
					</ul> -->
					
					<div class="tab-content">
						<div title="过程登记" style="margin-bottom:35px;" data-options="collapsed:false,collapsible:false">
							<div class="easyui-accordion" data-options="" id="" style="width:698px;margin-left: 20px">
								<!-- 第1个div -->
									<div title="采购信息"  id="gysxxdiv1"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="process_detail.jsp" />
									</div>
									<%-- <div title="采购邀请"  id="gysxxdiv2"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="process_invite_detail.jsp" />
									</div> --%>
									<div title="竞争过程"  id="gysxxdiv3"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="process_course_detail.jsp" />
									</div>
									<div title="采购结果"  id="gysxxdiv4"  data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
										<jsp:include page="purchasing_results_detail.jsp" />
									</div>
									<div title="审批记录" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;">
											<jsp:include page="../../../../view/check_history.jsp" />
									</div>
							</div>
						</div>
					
						<%-- <div title="采购申请" style="margin-bottom:35px;" data-options="">
							<div class="easyui-accordion" data-options="" id="" style="width:698px;margin-left: 20px">
								<!-- 第3个div -->
								<div title="采购信息" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
									<jsp:include page="../../purchase/cgjh_msg.jsp" />
								</div>
								<!-- 第4个div -->
								<div title="采购清单"id="CGdetails" data-options="collapsed:false,collapsible:false" style="width:698px;overflow:auto;margin-top: 10px;">
	 							  	<jsp:include page="../../purchase/select_cgconf_plan_mingxi_detail.jsp" />												
								</div>
								<c:if test="${bean.amount>=50000 }">
									<div title="党组会会议号" id="" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
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
										<tr class="trbody" id="fhyzgbmyjfTr" hidden="hidden">
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
										<tr class="trbody" id="fczbmyjTr" hidden="hidden">
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
							</div>
						</div> --%>
					</div>
				</div>
			</div>	
			
			<div class="window-left-bottom-div">
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
	function onclickdetailProcessCG(){
		$.parser.parse("#CGdetails");
	}
	$(document).ready(function() {
	var fpPype = '${bean.fpPype}';
	if(fpPype=='三家比选'){
		$("#fujian04").html('<span class="style1" style="width:110px">*</span>成交证明文件：');
		$("#fujian05").html('<span class="style1" style="width:110px">*</span>供应商资质证明：');
	}else{
		$("#gysxxdiv2").remove();
		$("#gysxxdiv3").remove();
	}
	/* if(fpPype=='GKZB'){
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
	} */
	
	});
</script>
</body>