<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="win-div">
<form id="re_arrive_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" style="width: 780px" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
						<!-- 第一个div -->
								<div title="基本信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
									<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
										<tr>
											<td class="td1"><span class="style1">*</span>登记单号</td>
											<td class="td2">
												<input id="F_fincomeNum" class="easyui-textbox" type="text" readonly="readonly" required="required" name="fincomeNum" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.fincomeNum}"/>
												<!-- 隐藏域 --> 
											    <input type="hidden" name="fincomeId" id="F_fincomeId" value="${bean.fincomeId}"/>
												<!-- 收入科目类型 --><input type="hidden" name="indexType" value="${bean.indexType}" id="arriveIndexType"/>
												<!-- 收入科目id --><input type="hidden" name="fecCode" value="${bean.fecCode}" id="arriveIndexId"/>
												<%-- <!-- 到账项目名称 --><input type="hidden" name="fproName" value="${bean.fproName}" id="F_fproName"/> --%>
											</td>
											
											<td class="td4"></td>
											
											<td class="td1"><span class="style1">*</span>登记时间</td>
											<td class="td2">
												<input id="F_fregisterTime" class="easyui-datebox" type="text" required="required" name="fregisterTime" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.fregisterTime}"/>
											</td>
										</tr>
										
										<tr>
											<td class="td1"><span class="style1">*</span>合同编号</td>
											<td class="td2" onclick="chooseContract()" colspan="4">
												<input id="F_contractCode" class="easyui-textbox" type="text" required="required" name="contractCode" data-options="editable:false" style="width: 555px" value="${bean.contractCode}"/>
											</td>
											
											<td style="width: 0px"></td>
											
										</tr>
										
										<tr>
											<td class="td1"><span class="style1">*</span>合同名称</td>
											<td class="td2" colspan="4">
												<input id="F_contractName" class="easyui-textbox" type="text" required="required" name="contractName" data-options="editable:false" style="width: 555px" value="${bean.contractName}"/>
											</td>
										
										</tr>
										
										<tr>
											<td class="td1"><span class="style1">*</span>收入科目</td>
											<td class="td2">
											<a href="javascript:void(0)" onclick="chooseSrkm()">
												<input class="easyui-textbox" type="text" id="F_indexName"  name="indexName" readonly="readonly" required="required" data-options="prompt: '选择科目' ,icons: [{iconCls:'icon-sousuo',handler: function(){chooseSrkm()}}]" style="width: 200px" value="${bean.indexName}"/>
											</a>
											</td>
											
											<td class="td4"></td>
											
											<td class="td1"><span class="style1">*</span>收入金额</td>
											<td class="td2">
													<input class="easyui-textbox" type="text" id="F_fregisterAmount"  name="fregisterAmount"  required="required" data-options="validType:'length[1,20]',iconWidth: 22,icons: [{iconCls:'icon-yuan',handler: function(e){}}]"  style="width: 200px" value="${bean.fregisterAmount}"/>
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>到账类型</td>
											<td class="td2">
												<c:if test="${empty bean.fincomeId}">
													<input id="F_faccountType" name="faccountType.code"   class="easyui-combobox" style="width: 200px" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_TYPE',method:'get',valueField:'code',textField:'text',editable:false"  />
												</c:if>
												<c:if test="${!empty bean.fincomeId}">
													<input id="F_faccountType" name="faccountType.code"   class="easyui-combobox" style="width: 200px" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_TYPE&selected=${bean.faccountType.code}',method:'get',valueField:'code',textField:'text',editable:false"  />
												</c:if>
											</td>
											
											<td style="width: 0px"></td>
											
											<td class="td1"><span class="style1">*</span>到账方式</td>
											<td class="td2">
												<c:if test="${empty bean.fincomeId}">
													<input id="F_faccountWay" name="faccountWay.code"   class="easyui-combobox" style="width: 200px" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_METHOD',method:'get',valueField:'code',textField:'text',editable:false" />
												</c:if>
												<c:if test="${!empty bean.fincomeId}">
													<input id="F_faccountWay" name="faccountWay.code"   class="easyui-combobox" style="width: 200px" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_METHOD&selected=${bean.faccountWay.code}',method:'get',valueField:'code',textField:'text',editable:false"  />
												</c:if>
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>开户行</td>
											<%-- <td class="td2">
												<c:if test="${empty bean.fincomeId}">
												<input id="F_faccount" name="faccount.code"  class="easyui-combobox" style="width: 200px" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_ACCOUNT',method:'get',valueField:'code',textField:'text',editable:false"  />
												</c:if>
												<c:if test="${!empty bean.fincomeId}">
													<input id="F_faccount" name="faccount.code"   class="easyui-combobox" style="width: 200px" data-options="url:'${base}/lookup/lookupsJson?parentCode=ARRIVE_ACCOUNT&selected=${bean.faccount.code}',method:'get',valueField:'code',textField:'text',editable:false"  />
												</c:if>
											</td> --%>
											<td class="td2">
												<input id=F_faccount class="easyui-textbox" type="text" required="required" name="faccount" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.faccount}"/>
											</td>
											
											<td style="width: 0px"></td>
											
											<td class="td1"><span class="style1">*</span>到账帐号</td>
											<td class="td2">
												<input id="F_faccountNum" class="easyui-textbox" type="text" required="required" name="faccountNum" data-options="validType:'length[1,30]'" style="width: 200px" value="${bean.faccountNum}"/>
											</td>
										</tr>
									</table>
								</div>			
				</div>
			</div>
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveIncome()">
					<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=收入管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<%-- <div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div> --%>
		
	</div>
</form>
</div>
	<script type="text/javascript">

	$(document).ready(function(){
		var myDate = new Date();
		myDate = myformatter(myDate);
		$('#F_fregisterTime').textbox().textbox('setValue',myDate);
	});

	/* //弹出到账项目的选择页面
	function checkCode() {
		var win = creatFirstWin('招标信息', 970, 450, 'icon-search','/srregister/registerinfo');
		win.window('open');
	} */
	
	//保存
	function saveIncome() {	
		/* alert($("#F_indexName").val());
		alert($("#arriveIndexId").val());
		alert($("#arriveIndexType").val()); */
		/*alert($("#F_fproCode").val());
		alert($("#F_fproName").val());
		alert($("#F_fregisterDepart").val());
		alert($("#F_faccountType").val());
		alert($("#F_faccountWay").val());
		alert($("#F_faccount").val()); */		
	 	//提交
		$('#re_arrive_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}
				return flag;
			},
			url : base + '/srregister/save',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					closeWindow();
					$("#re_arrive_form").form("clear");  //带着新增完毕不能查询
					$("#registerTab").datagrid("reload");
				} else {
					$.messager.alert('系统提示', data.info, 'error');
					closeWindow();
					$('#re_arrive_form').form('clear');
				}
			}
		});  
	}
	
	//选择合同
	function chooseContract(){
		var win = creatFirstWin('选择-收入合同', 740, 580, 'icon-search', '/srregister/choContract');
		win.window('open');
	}
	//选择收入科目
	function chooseSrkm(){
		var win = creatFirstWin('选择-收入科目', 740, 580, 'icon-search', '/srregister/choSrkm');
		//var win=creatFirstWin('选择-支出预算事项',600,550,'icon-search','/project/toChooseKm');
		win.window('open');
	}
	
	
/* 	 //寻找相关制度
	function findSystemFile(id) {
		$.ajax({ 
			url: base+"/cheter/systemFind?id="+id, 
			success: function(data){
				data=data.replace('\"','');
				data=data.replace('\"','');
				window.open(data);
	    }});
	}  */
		
	</script>
</body>