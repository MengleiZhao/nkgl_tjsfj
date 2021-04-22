<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
<style>
select{width: 200px}
</style>


<body>
<div class="win-div">
<form id="project_add_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 559px;">
		<div class="win-left-div-ys" data-options="region:'west',split:true">
			<div class="win-left-top-div-ys">
				
				<div class="tab-wrapper" id="project-tab">
					<ul class="tab-menu">
						<li class="active">基本支出信息</li>
						<li>基本支出明细</li>
						<c:if test="${operation!='add'}"><li onclick="$('#check-history-dg').datagrid('reload')">审批记录</li></c:if>
					</ul>
					
					<div class="tab-content">
						<div title="基本支出信息" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
						   	<%@ include file="project-add-base-two.jsp" %>
						</div> 
						 	
						<div title="基本支出明细" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<%@ include file="project-add-outcome.jsp" %>
						</div> 
						
						<c:if test="${operation!='add'}">
						<div title="审批记录" data-options="iconCls:'icon-xxlb'" style="overflow-x:hidden;margin-top: 10px;">
							<%@ include file="project-add-history.jsp" %>											
						</div>
						</c:if>
					</div>
			
				</div>
			</div>
			
			<div class="win-left-bottom-div-ys">
				<c:if test="${(empty bean.FFlowStauts) or bean.FFlowStauts==0 or bean.FFlowStauts==-11 or bean.FFlowStauts=='-14' }">
					<a href="javascript:void(0)" onclick="saveProject('zc')">
						<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/zhanchun2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/zhanchun1.png')"
						/>
					</a> 
				</c:if>
				&nbsp;&nbsp;
				<c:if test="${(empty bean.FFlowStauts) or bean.FFlowStauts<3}">
					<a href="javascript:void(0)" onclick="if(confirm('是否送审,送审之后将由下一环节审批，审批期间将无法修改')){saveProject('sb');}">
						<img src="${base}/resource-modality/${themenurl}/button/songshen1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/songshen2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/songshen1.png')"
						/>
					</a> 
				</c:if>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="if(confirm('是否退出编辑，如您已编辑部分信息，建议您暂存')){closeWindow();}">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png"
						onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/guanbi2.png')"
						onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/guanbi1.png')"
					/>
				</a>
				<%-- &nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=budget" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a> --%>
			</div>
		</div>
	
		<div class="win-right-div" id="check_system_div"  data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
</div>
<script type="text/javascript">
	//页面置顶
	$(function(){
		
	}); 
	
	//加载tab页
	flashtab_pro_add('project-tab');
	function flashtab_pro_add(tabid) {
		var $wrapper = $('#'+tabid),
			$allTabs = $wrapper.find('.tab-content > div'),
			$tabMenu = $wrapper.find('.tab-menu li');
			$line = $('<div class="line"></div>').appendTo($tabMenu);
		
		$allTabs.not(':first-of-type').hide();  
		$tabMenu.filter(':first-of-type').find(':first').width('100%');
		
		$tabMenu.each(function(i) {
		  $(this).attr('data-tab', 'tab'+i);
		});
		
		$allTabs.each(function(i) {
		  $(this).attr('data-tab', 'tab'+i);
		});
		
		$tabMenu.on('click', function() {
		  var dataTab = $(this).data('tab');
		  if(dataTab=='tab5'){
			  //点击绩效分页时候，打开绩效指标手风琴（解决绩效指标datagrid不显示的bug）
			  $('#acco_pro_add_jx').accordion('select','绩效指标');
		  }
		  
		  $getWrapper = $(this).closest($wrapper);
		  
		  $getWrapper.find($tabMenu).removeClass('active');
		  $(this).addClass('active');
		  
		  $getWrapper.find('.line').width(0);
		  $(this).find($line).animate({'width':'100%'}, 'fast');
		  $getWrapper.find($allTabs).hide();
		  $getWrapper.find($allTabs).filter('[data-tab='+dataTab+']').show();
		});
	}
	
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
		var proOrBasic = '${bean.FProOrBasic}';
	   	if(proOrBasic==1){
	   		if($('#fProAccordingId').val()==''){
				alert('请填立项依据！');
				return false;
			}
	   	}else{
	   		//基本支出项目
	   	}
	   	
		//验证项目属性
		if ($('#project_add_FProName').textbox('getValue')==''){
			alert('请填写项目名称！');
			return false;
		}  
		if ($('#pro_add_FProBudgetAmount').numberbox('getValue')=='') {
			alert('请填写资金来源！');
			return false;
		}
		if($('#pro_add_planStartYear').numberbox('getValue')==''){
			$('#pro_add_planStartYear').numberbox('setValue', new Date().getFullYear()+1);
			alert("开始执行年份不能为空！");
			return false;
		}
		/* if ($('#project_add_FProClass').combobox('getValue')=='') {
			alert('请选择项目类别！');
			return false;
		} */    
		  
		return true;
	}
	
	//保存 saveType,暂存zc，申报sb
	function saveProject(saveType){
		fundsaccept();
		var fundsrows = $('#fundssource').datagrid('getRows');
		var funds = "";
		for (var j = 0; j < fundsrows.length; j++) {
			funds = funds + JSON.stringify(fundsrows[j]) + ",";
		}
		$('#fundsJson').val(funds);
		//绩效指标
		//getPerformanceJson();
		//校验
		
		if("zc"==saveType){
			/* //$('#project_add_twe_firstLevelCode').combobox('validate');
			var firstLevelCode = $("#project_add_twe_firstLevelCode").val();
			var ejxmdm = $("#project_add_base_secondLevel").combobox('getValue');
			if(firstLevelCode==''||firstLevelCode=='--请选择--'){
				alert("请选择'所属一级分类名称'!");
				return
			}
			if(ejxmdm==''||ejxmdm=='--请选择--'){
				alert("请选择'二级分类名称'!");
				return
			} */
			
		}else if("sb"==saveType){
			//校验经济分类科目有没有填写
			var subCode = $('.pro_add_outcome_subCode');
			for (var i = 0; i < subCode.length; i++) {
				var valcode = subCode[i].value;
				if(valcode==''){
					alert("经济分类科目不允许为空，请检查是否已选择经济分类科目！");
					return;
				}
			}
			$('#project_add_form').form('enableValidation').form('validate');
			//跳转到校验失败的标签页
			openInvalidTab('project-tab');
			if (validateProjectAddBase()==false) {
				var fundsEditIndex = $('#fundssource').datagrid('getRows').length - 1;
				$('#fundssource').datagrid('selectRow', fundsEditIndex).datagrid('beginEdit',
						fundsEditIndex);
				return;
			}
			//重新计算一遍支出明细总额
			autoCalTotal(null);
			if(parseFloat($('#outcomeTotal').val())!=parseFloat($('#pro_add_FProBudgetAmount').val())){
				alert("项目支出明细总额与项目预算金额不一致");
				return false;
			}
		}
		
		
		$('#project_add_form').form('submit', {
   				onSubmit: function(param){
   					param.saveType = saveType;
   					/* param.outcomeJson = outcome;
   					param.planJson = plan;
   					param.longAimJson = longAim;
   					param.longIndexJson = longIndex;
   					param.yearAimJson = yearAim;
   					param.yearIndexJson = yearIndex; */
   					if(saveType=='sb'){
	   				 	flag=$(this).form('enableValidation').form('validate');
	   					if(flag){
	   						$.messager.progress();
	   					}
	   					return flag; 
   					}else{
	   					$.messager.progress();
   					}
   				}, 
   				url:base+'/project/save', 
   				success:function(data){
   					if(saveType=='sb'){
	   					if(flag){
	   						$.messager.progress('close');
	   					}
   					}else {
	   					$.messager.progress('close');
   					}
   					data=eval("("+data+")");
   					if(data.success){
   						$.messager.alert('系统提示', data.info, 'info');
   						$('#project_add_form').form('clear');
   						$('#pro_dg_2').datagrid('reload'); 
   						$('#indexdb').datagrid('reload');
   						closeWindow();
   					}else{
   						$.messager.alert('系统提示', data.info, 'error');
   						/* closeWindow();
   						$('#project_add_form').form('clear'); */
   					}
   				} 
   			});		
	}
	
	//校验不通过，就打开第一个校验失败的标签页
	function openInvalidTab(tabid){
		//获取所有标签页
		var $wrapper = $('#'+tabid),
			$allTabs = $wrapper.find('.tab-content > div'),
			$tabMenu = $wrapper.find('.tab-menu li');
			
		for(var i=0;i<$allTabs.length;i++){
			var forflag = true;
			//获取标签页标记
			var datab = $allTabs[i].getAttribute('data-tab');
			var inputs = $('div[data-tab="'+datab+'"] input');
			
			$('div[data-tab="'+datab+'"] input').each(function() {
				//打开校验不通过的标签
				if($(this).hasClass("validatebox-invalid")){
					$('li[data-tab="'+datab+'"]').click();
					//打开该标签页
					/*$getWrapper = $(this).closest($wrapper);
					$getWrapper.find($allTabs).css('display','none');
					$getWrapper.find($allTabs).filter('[data-tab='+datab+']').css('display','block');*/
					//更改标签头
					/*var lili= $('li[data-tab="'+datab+'"]');
					$getWrapper.find($tabMenu).removeClass('active');
					$('li[data-tab="'+datab+'"]').addClass('active');*/
					
					forflag=false;
					return false;
				}
			});
			
			if(forflag==false){
				return forflag;
			}
			
		}
		//循环每个标签页
	}
</script>
</body>