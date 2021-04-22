<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
  
<body>
<div class="win-div">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="easyAcc" style="width:662px;margin-left: 20px">
					<div title="指标调整信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
						<div style="height:30px">
							<span><strong>调增指标:</strong></span>
						</div>
						<div>
							<jsp:include page="inside/zbdr.jsp" />
						</div>
						<div style="height:30px">
						<span><strong>调减指标:</strong></span>
						</div>
						<div>
							<jsp:include page="inside/zbdc.jsp" />
						</div>
						</br>
						
						<div style="margin-top: 10px;">
							<table cellpadding="0" cellspacing="0" class="ourtable">
								<tr class="trbody">
									<td class="td1"><span class="style1">*</span>&nbsp;操作人</td>
	
									<td class="td2"><input style="width: 200px;" id=""
										name="opUser" class="easyui-textbox" required="required"
										value="${bean.opUser}" readonly="readonly"></input>
									</td>
									
									<td class="td4">
										<!-- 主键ID --><input type="hidden" name="inId" value="${bean.inId}" />
										<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="insideflowStauts" />
										<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="insideStauts" />
									</td>
	
									<td class="td1"><span class="style1">*</span>&nbsp;调整时间</td>
									<td class="td2"><input style="width: 200px;" required="required"
										id="insideOpTime" name="opTime" class="easyui-datebox"
										value="${bean.opTime}" readonly="readonly"></input>
									</td>
								</tr>
								
								<%-- <tr class="trbody">
									<td class="td1">&nbsp;&nbsp;调减部门</td>
									<td colspan="4">
										<input class="easyui-combobox" style="width: 555px;" id="inside_departId" name="insideDeptId" onclick="refreshProcess()" readonly="readonly" data-options="editable:false,panelHeight:'auto',
											url:'${base}/depart/chooseDepart?selected=${bean.insideDeptId }',
											method:'POST',
											valueField:'code',
											textField:'text',
											"/>
									</td>	
								</tr> --%>
								<tr style="height: 5px;">&nbsp;</tr>
								<tr style="height: 70px;">
									<td class="td1" valign="top"><p style="margin-top: 8px">&nbsp;&nbsp;申请事由</p></td>
									<td colspan="4">
										<input class="easyui-textbox" data-options="multiline:true" name="appDesc" style="width:555px;height:70px;" value="${bean.appDesc}" readonly="readonly">
									</td>
								</tr>
							</table>
						</div>
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
</div>	
</body>


