<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title></title>



</head>
<body>



<div class="win-div">
<form id="project_verdict_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 559px;">
		<div class="win-left-div-ys" data-options="region:'west',split:true">
		 <!-- 审批附件 --><input type="hidden" name="spjlFile1" id="spjlFile1" value=""/>	
			<div class="win-left-top-div-ys">
				<div class="tab-wrapper" id="project-check-tab">
					<ul class="tab-menu">
						<li class="active">项目信息</li>
						<li class="FProAccording">立项依据</li>
						<li class="FExplain" >项目实施方案</li>
						<!-- <li>立项依据</li>
						<li>项目实施方案</li> -->
						<!-- <li>项目支出计划</li> -->
						<li>项目支出明细</li>
						<!-- <li>项目支出绩效目标</li> -->
						<li onclick="$('#check-history-dg').datagrid('reload')">审批记录</li>
					</ul>
					
					<div class="tab-content">
						<div title="项目信息" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						   	<%@ include file="project-detail-base.jsp" %>
						</div> 
						 
						<div title="立项依据" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						   	<%@ include file="project-detail-lxyj.jsp" %>
						</div> 
						
						<div title="项目实施方案" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						    <%@ include file="project-detail-xmssfa.jsp" %>
						</div> 
						        	
						<%-- <div title="项目支出计划" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						    <%@ include file="project-detail-plan.jsp" %>
						</div> --%>
						 	
						<div title="项目支出明细" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<%@ include file="project-detail-outcome.jsp" %>
						</div> 
						
						<div title="审批记录"  style="overflow-x:hidden;margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						 	<%@ include file="../project-add-history.jsp" %>
						</div>
					<%-- 	<div title="项目支出绩效目标" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						     <%@ include file="project-detail-goal.jsp" %>
						</div> --%>
					</div>
				</div>
			</div>
			
			<div class="win-left-bottom-div-ys">
			
				<a href="#" onclick="saveProjectVer('1')">
						<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				<a href="#" onclick="saveProjectVer('0')">
					<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				 <a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				<input type="hidden" name="fcheckResult" id="fcheckResult"/>
				<input type="hidden" name="fcheckRemake" id="fcheckRemake"/>
			</div>
		</div>
	
		<div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../../check_system.jsp" />
		</div>
	</div>
</form>
</div>




<script type="text/javascript">
	flashtab('project-check-tab');
	
	//保存 saveType,暂存zc，申报sb
	function saveProjectVer(iftg){
		/* //验证
		if(iftg=='false'){
			var suggest = $('#pro_verdict_suggest').textbox('getValue');
			if(suggest==''){
				alert('请填写审批意见！');
				return;
			}
		} */
		
		/* //设置状态
		var FFlowStauts = parseInt($('#pro_verdict_FFlowStauts').val());
		if (iftg=='true') {
			FFlowStauts = FFlowStauts + 1;
			$('#pro_verdict_FFlowStauts').val(FFlowStauts)
			if(FFlowStauts=='3'){
				$('#pro_verdict_FProLibType').val('2');
			};
			$('#pro_verdict_FOperation').val('yes');
		}else if (iftg=='false'){
			//FFlowStauts = FFlowStauts - 1;
			FFlowStauts = -1;
			$('#pro_verdict_FFlowStauts').val(FFlowStauts);
			$('#pro_verdict_FProLibType').val('1');
			$('#pro_verdict_FOperation').val('no');
		} */
		var win = creatFirstWin('审批意见', 560, 500, 'icon-search', '/project/checkRemake?type=xmsb&result='+iftg+"&listid=${listid}");
		win.window('open');
	}
</script>
</body>
</html>

