<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="win-div">
<form id="re_arrive_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
							<!-- 第一个div -->
								<div title="基本信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
									<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
										<tr>
											<td class="td1"><span class="style1">*</span>登记单号</td>
											<td class="td2">
												<input id="F_fincomeNum" class="easyui-textbox" type="text" readonly="readonly" required="required" name="fincomeNum"  style="width: 200px" value="${bean.fincomeNum}"/>
												<!-- 隐藏域 --><input type="hidden" name="fincomeId" id="F_fincomeId" value="${bean.fincomeId}"/>
												<!-- 收入科目类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="arriveIndexType"/>
												<!-- 收入科目id --><input type="hidden" name="fecCode" value="${bean.fecCode}" id="arriveIndexId"/>
												<!-- 到账项目名称 --><%-- <input type="hidden" name="fproName" value="${bean.fproName}" id="F_fproName"/> --%>
											</td>
											<td class="td4"></td>
											<td class="td1"><span class="style1">*</span>登记人</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_fregisterPerson"  name="fregisterPerson" readonly="readonly" required="required" data-options="validType:'length[1,20]'"  style="width: 200px" value="${bean.fregisterPerson}"/>
											</td>
										</tr>
										<%-- <tr class="trbody">
											<td class="td1"><span class="style1">*</span>到账项目</td>
											<td colspan="4">
												<input class="easyui-textbox" id="F_fproCode" readonly="readonly" name="fproCode"  style="width: 555px" value="${bean.fproCode}"/>
											</td>
										</tr> --%>
										<tr>
											<td class="td1"><span class="style1">*</span>收入科目</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_indexName"  name="indexName" readonly="readonly" required="required"  style="width: 200px" value="${bean.indexName}"/>
											</td>
											
											<td style="width: 0px"></td>
											
											<td class="td1"><span class="style1">*</span>收入金额</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_fregisterAmount"  name="fregisterAmount" readonly="readonly"  required="required" data-options="iconWidth: 22,icons: [{iconCls:'icon-yuan',handler: function(e){}}]"  style="width: 200px" value="${bean.fregisterAmount}"/>
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>登记时间</td>
											<td class="td2">
												<input id="F_fregisterTime" class="easyui-datebox" type="text" readonly="readonly" required="required" name="fregisterTime"  style="width: 200px" value="${bean.fregisterTime}"/>
											</td>
											
											<td style="width: 0px"></td>
											
											<td class="td1"><span class="style1">*</span>登记部门</td>
											<td class="td2">
													<input class="easyui-textbox" type="text" id="F_fregisterDepart"  name="fregisterDepart" readonly="readonly" required="required" data-options="validType:'length[1,20]'"  style="width: 200px" value="${bean.fregisterDepart}"/>
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>到账类型</td>
											<td class="td2">
												<input id="F_faccountType" name="faccountType.code"  readonly="readonly"  class="easyui-combobox" style="width: 200px" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_TYPE&selected=${bean.faccountType.code}',method:'get',valueField:'code',textField:'text',editable:false"  />
											</td>
											
											<td style="width: 0px"></td>
											
											<td class="td1"><span class="style1">*</span>到账方式</td>
											<td class="td2">
													<input id="F_faccountType" name="faccountWay.code"  readonly="readonly"  class="easyui-combobox" style="width: 200px" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_METHOD&selected=${bean.faccountWay.code}',method:'get',valueField:'code',textField:'text',editable:false"  />
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>开户行</td>
											<td class="td2">
												<input id=F_faccount class="easyui-textbox" type="text" readonly="readonly" required="required" name="faccount" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.faccount}"/>
											</td>
											
											<td style="width: 0px"></td>
											
											<td class="td1"><span class="style1">*</span>到账帐号</td>
											<td class="td2">
												<input id="F_faccountNum" class="easyui-textbox" type="text" readonly="readonly" required="required" name="faccountNum" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.faccountNum}"/>
											</td>
										</tr>
									</table>
								</div>					
				</div>
			</div>
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="closeWindow()">
				<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
</div>
	<script type="text/javascript">

	</script>
</body>