<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<div class="win-div">
	<form id="economicAddEditForm" action="${base}/economicClassification/save" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="easyui-layout" style="height: 309px;">
			<div class="win-left-div" data-options="region:'west',split:true">
				<div class="win-left-top-div" style="height: 200px">
					<div class="easyui-accordion" style="width:662px;margin-left: 20px;">
						<div title="经济科目" id="djdxqDiv" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
							<table class="ourtable">
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>科目编号</td>
									<td class="td2">
										<input class="easyui-textbox" type="text" onchange="EL()" required="required"  name="code" data-options="validType:'length[1,20]'"style="width: 200px" value="${bean.code}"/>
									</td>
									<td class="td4">
										<input type="hidden" class="easyui-textbox" name="fid" value="${bean.fid}"/>
									</td>
									<td class="td1"><span class="style1">*</span>科目名称</td>
									<td class="td2">
										<input class="easyui-textbox" required="required" name="name" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.name}"/>					
									</td>
								</tr>			
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>科目级别</td>
									<td class="td2">
										<input name="leve" style="width: 200px;" value="${bean.leve}" data-options="url:'${base}/economic/lookupsJson?parentCode=KMJB&selected=${bean.leve}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'" class="easyui-combobox">
									</td>
									<td class="td4">&nbsp;</td>
									<td class="td1"><span class="style1">*</span>上级科目编号</td>
									<td class="td2">
										<input class="easyui-textbox" style="width: 200px" type="text" name="pid" required="required" data-options="validType:'length[1,50]'"value="${bean.pid}"/>
									</td>
								</tr>			
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>科目类型</td>
									<td class="td2">
										<input class="easyui-combobox" <c:if test="${openType=='edit'}">readonly="readonly"</c:if> name="type" value="${bean.type}" style="width: 200px;" data-options="url:'${base}/economic/lookupsJson?parentCode=KMLX&selected=${bean.type}',method:'POST',valueField:'code',textField:'text',editable:false,validType:'selectValid'" class="easyui-combobox">
									</td>
									<td class="td4">&nbsp;</td>
									<td class="td1"><span class="style1">*</span>应用年份</td>
									<td class="td2">
										<select class="easyui-combobox" id="ecgYear" name="year" style="width: 200px;" data-options="editable:false,panelHeight:'auto'">
											<c:forEach items="${years}" var="y">
												<option value="${y}" >${y}</option>
											</c:forEach>
										</select>
									</td>
								</tr>			
					
							</table>
						</div>
					</div>
				</div>
				
				<div class="win-left-bottom-div">
					<a href="javascript:void(0)" onclick="economicAddEditForm()">
					<img src="${base}/resource-modality/img/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/img/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
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
		var year = '${bean.year}';
		$('#ecgYear').val(year);
	
		function economicAddEditForm(){
			if('#')
			$('#economicAddEditForm').form('submit', {
   				onSubmit: function(){ 
   					flag=$(this).form('enableValidation').form('validate');
   					if(flag){
   						$.messager.progress();
   					}
   					return flag;
   				}, 
   				
   				/* url:base+'/demo/save',  */
   				success:function(data){
   					if(flag){
   						$.messager.progress('close');
   					}
   					data=eval("("+data+")");
   					if(data.success){
   						$.messager.alert('系统提示', data.info, 'info');
   						closeWindow();
   						$('#economicAddEditForm').form('clear');
   						$("#ecg_dg").datagrid('reload'); 
   					}else{
   						$.messager.alert('系统提示', data.info, 'error');
   						closeWindow();
   						$('#economicAddEditForm').form('clear');
   					}
   				} 
   			});		
		}
			
	</script>
</body>