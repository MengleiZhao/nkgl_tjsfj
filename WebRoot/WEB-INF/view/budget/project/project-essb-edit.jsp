<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<style>
select{width: 200px}
</style>

<body>
<div class="win-div">
<form id="project_essb_edit_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 559px;">
		<div class="win-left-div-ys" data-options="region:'west',split:true">
			<div class="win-left-top-div-ys">
				
				<div class="tab-wrapper" id="essb-edit-tab">
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
						   	<%@ include file="project-essb-base.jsp" %>
						</div> 
						 
						<div title="立项依据" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						   	<%@ include file="project-add-lxyj.jsp" %>
						</div> 
						
						<div title="项目实施方案" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						    <%@ include file="project-add-xmssfa.jsp" %>
						</div> 
						        	
						<%-- <div title="项目支出计划" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						    <%@ include file="../project/project-add-plan.jsp" %>
						</div> --%>
						 	
						<div title="项目支出明细" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<%@ include file="project-add-outcome.jsp" %>
						</div> 
												
						<div title="审批记录"  style="overflow-x:hidden;margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						 	<%@ include file="project-add-history.jsp" %>
						</div>
						<%-- <div title="项目支出绩效目标" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						     <%@ include file="../project/project-add-goal.jsp" %>
						</div> --%>
					</div>
			
				</div>
			</div>
			
			<div class="win-left-bottom-div-ys">
					<a href="javascript:void(0)" onclick="saveEsProject('sb')">
						<img src="${base}/resource-modality/${themenurl}/button/songshen1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/songshen2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/songshen1.png')"
						/>
					</a> 
				&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="closeWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/guanbi2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/guanbi1.png')"
						/>
					</a>
			</div>
		</div>
	
		<div class="win-right-div" style="height: 570px;" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
</div>

<script type="text/javascript">
//加载tab页
flashtab('essb-edit-tab');


function validateProjectAdd(stepName){
	if(stepName=='项目基本信息'){
		return validateProjectAddBase();
	} else if(stepName=''){
		
	}
	return true;
}
	
	function validateProjectAddBase(){
		/* //验证预算额不大于控制数
		var controlAmount = $('#pro_add_provideAmount1').numberbox('getValue');
		if(controlAmount!=''){
			var num1 = $('#pro_add_FProBudgetAmount').numberbox('getValue');
			if(num1>controlAmount){
				alert('请确保"项目预算金额"不大于"一下控制财拨数"金额！');
				return false;
			}
		} */
		if(parseFloat($('#pro_add_provideAmount1').val())!=parseFloat($('#pro_add_FProBudgetAmount').val())){
			alert("项目控制金额与项目预算金额不一致");
			return false;
		}
		if(parseFloat($('#outcomeTotal').val())!=parseFloat($('#pro_add_FProBudgetAmount').val())){
			alert("项目支出明细总额与项目预算金额不一致");
			return false;
		}
		//验证项目属性
		if ($('#project_add_FProName').textbox('getValue')==''){
			alert('请填写项目名称！');
			return false;
		} else if ($('#pro_add_FProBudgetAmount').numberbox('getValue')=='') {
			alert('请填写项目预算金额！');
			return false;
		}
		
		return true;
	}
	
	
	//保存 saveType,暂存zc，申报sb
	function saveEsProject(saveType){
		if (validateProjectAddBase()==false) {
			return;
		} 

		setAccoFile();//立项依据附件
		setPlanFile();//实施方案附件
		fundsaccept();
		var fundsrows = $('#fundssource').datagrid('getRows');
		var funds = "";
		for (var j = 0; j < fundsrows.length; j++) {
			funds = funds + JSON.stringify(fundsrows[j]) + ",";
		}
		$('#fundsJson').val(funds);
		$('#project_essb_edit_form').form('submit', {
   				onSubmit: function(param){
   					param.saveType = saveType;
   					/* param.outcomeJson = outcome;
   					param.planJson = plan;
   					param.longAimJson = longAim;
   					param.longIndexJson = longIndex;
   					param.yearAimJson = yearAim;
   					param.yearIndexJson = yearIndex; */
   				 	flag=$(this).form('enableValidation').form('validate');
   					if(flag){
   						$.messager.progress();
   					}
   					return flag; 
   				}, 
   				url:base+'/declare/essb', 
   				success:function(data){
   					if(flag){
   						$.messager.progress('close');
   					}
   					data=eval("("+data+")");
   					if(data.success){
   						$.messager.alert('系统提示', data.info, 'info');
   						closeWindow();
   						$('#project_essb_edit_form').form('clear');
   						$('#project-essb-table').datagrid('reload'); 
   						$('#indexdb').datagrid('reload');
   					}else{
   						$.messager.alert('系统提示', data.info, 'error');
   						closeWindow();
   						$('#project_essb_edit_form').form('clear');
   					}
   				} 
   			});		
	}
</script>
</body>
