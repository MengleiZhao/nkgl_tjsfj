<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<div class="easyui-accordion" data-options="" style="width:922px;margin-left: 20px">
	<div title="项目基本信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
		<table cellpadding="0" cellspacing="0">
			<tr class="trbody">
		   		<td class="td1-ys"><span class="style_must">*</span>&nbsp;项目编号</td>
		     	<td class="td2"  colspan="4">
		     		<input id="project_add_FProCode" class="easyui-textbox" readonly="readonly" 
		     		style="height:30px;width:750px" name="FProCode" value="${bean.FProCode}" />
		     	</td>
		   	</tr>
		   	
			<tr class="trbody">
		   		<td class="td1-ys"><span class="style_must">*</span>&nbsp;项目名称</td>
		     	<td class="td2" colspan="4">
		     		<input id="project_add_FProName" name="FProName" class="easyui-textbox" style="height:30px; width:750px" 
		     		data-options="validType:'length[1,50]'" prompt="请输入项目名称"  value="<c:out value="${bean.FProName}"></c:out>">
		     		<%-- <input class="easyui-combobox" id="project_add_FProName" style="height:30px; width:750px"
							name="FProName" prompt="请输入项目名称"  value="<c:out value="${bean.FProName}"></c:out>" 
							data-options="
									url:'${base}/project/getProAllList',
									method:'get',
									valueField:'id',
									textField:'text',
									panelHeight:'auto',
									validType:'length[1,50]',
									
							"> --%>
		     		<!-- 一级分类名 -->
		     		<input type="hidden" id="add_FirstLevelName" name="firstLevelName" <c:if test="${bean.FProOrBasic==0}"> value="经常性支出项目" </c:if>/>
		     		<!-- 二级分类名 -->
		     		<input type="hidden" id="add_secondLevelName" name="secondLevelName" value="${bean.secondLevelName}"/>
					<!-- 项目id -->
					<input type="hidden" id="F_fProId" name="FProId" value="${bean.FProId}"/>
					<!-- 审批状态 -->
					<input id="" type="hidden" name="FFlowStauts" value="${bean.FFlowStauts}"/>
					<!-- 项目预算周期 -->
					<input type="hidden"  name="FProBudgetCycle" value="1"/>
					<!-- 预算支出类型 -->
					<input type="hidden" id="project_add_FProOrBasic" onchange="FProOrBasicChange(${bean.FProOrBasic})" name="FProOrBasic" value="${bean.FProOrBasic}"/>
		     	</td>
		   	</tr>
		   	
		    <tr class="trbody">
		    	<td class="td1-ys"><span class="style_must">*</span>&nbsp;预算支出类型</td>
		     	<td class="td2">
		     		<input class="easyui-textbox" id="project_add_FProOrBasic_show" style="height:30px; width: 300px" >
		     	</td>
		     	<td class="td3-ys"></td>
		     	<td class="td1-ys"><span class="style_must">*</span>&nbsp;部门</td>
		     	<td class="td2" colspan="2">
		     		<input class="easyui-combobox" style="width: 300px;height: 30px; " id="pro_add_departid" readonly="readonly" name="FProAppliDepartId"   data-options="editable:false,hasDownArrow:false,panelHeight:'auto',url:'${base}/depart/chooseDepart?selected=${bean.FProAppliDepartId}',method:'POST',valueField:'code',textField:'text'"/>
		     	</td>
		    </tr>
		    
		    <%-- <!-- 项目支出 -->
			<tr class="trbody" id="project_firstLevel" hidden="hidden">
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;所属一级分类名称</td>
		     	<td class="td2">
		     		<input id="project_add_firstLevel"  class="easyui-combobox" style="height:30px;width: 300px" required="true"
		     		data-options="url:'${base}/project/getSubject1?selected=${bean.firstLevelCode}&blank=XD-01',method:'get',valueField:'code',textField:'text',editable:false,validType:'selectValid',
					    onSelect: function(rec){
					    var url = '${base}/project/getSubject2?selected=${bean.secondLevelCode}&blank=XD-01&parentCode='+rec.code;
					    $('#project_add_secondLevel').combobox('reload', url);}"/>
		     	</td>
				<td class="td3-ys"></td>
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;所属一级分类代码</td>
				<td class="td2">
		     		<input class="easyui-textbox" id="project_yjxmdm" name="firstLevelCode" readonly="readonly" style="height:30px; width: 300px" value="${bean.firstLevelCode}"/>
		     	</td>
			</tr>
			
			<!-- 项目支出 -->
			<tr class="trbody" id="project_secondLevel" hidden="hidden">
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;二级分类名称</td>
		     	<td class="td2">
		     		<input id="project_add_secondLevel" 
		     		class="easyui-combobox" style="height:30px; width: 300px" required="true"
		     		data-options="url:'${base}/project/getSubject2?selected=${bean.secondLevelCode}&parentCode=${bean.firstLevelCode}',method:'get',valueField:'code',textField:'text',editable:false,validType:'selectValid', 
		     		onSelect:function(item){
			     		var tabdata = $('#fundssource').datagrid('getData');
			     		var Rows = $('#fundssource').datagrid('getRows');
			     		selectCode = item.code;
			     		selectVal = item.text;
			     		if(4001 == selectCode){
			     			selectCode = 0;
			     			selectVal = '财政拨款收入';
			     		}else if(4002 == selectCode){
			     			selectCode = 4;
			     			selectVal = '其它收入';
			     		}else if(4003 == selectCode){
			     			selectCode = 1;
			     			selectVal = '教育事业收入';
			     		}
			     		for (var i = 0; i < Rows.length; i++) {
				     		$('#fundssource').datagrid('updateRow',{
					     		index:i,
					     		row: {fundsSource:selectCode,fundsSourceText:selectVal}
				     		});
						}
						var outTable = document.getElementById('pro_outcome_table'); 
						var trnum = outTable.childNodes[1].children.length;
						<!-- 避免修改时前面加载一级指标时就修改后面的数据 ，修改时打开初始一共加载6次-->
						n+=1;
						if(n>6){
							for (var i = 1; i < trnum; i++) {
								outTable.childNodes[1].children[i].children[2].children[2].innerHTML='点击选择经济分类科目';
								outTable.childNodes[1].children[i].children[2].children[1].value='';
								outTable.childNodes[1].children[i].children[2].children[0].value='';
								<!--outTable.childNodes[1].children[i].children[1].children[0].value=''; -->
							}
							<!-- console.log(outTable.childNodes[1].children[1].children[2].children[2].innerHTML='点击选择经济分类科目'); -->
							<c:if test="${operation=='add'||operation=='edit'}">
								$('#fundssource').datagrid('cancelEdit', fundsEditIndex).datagrid('deleteRow',0);
								fundsEditIndex = undefined;
								setFsumMoney(0,0);
								fundsappend()
							</c:if>
						}
		     		}
		     	"/>
		     	</td>
		     	<td class="td3-ys"></td>
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;二级分类代码</td>
				<td class="td2">
		     		<input class="easyui-textbox" style="height:30px; width: 300px" data-options="validType:'length[1,50]'"
		   			id="project_ejxmdm" name="secondLevelCode" readonly="readonly"  value="${bean.secondLevelCode}"/>
		     	</td> 
			</tr> --%>
			
		    <tr class="trbody">
		    	<td class="td1-ys"><span class="style_must">*</span>&nbsp;项目预算金额</td>
		     	<td class="td2">
		     		<input class="easyui-textberbox" data-options="validType:'length[1,10]',iconWidth: 30,icons: [{iconCls:'icon-wanyuan',handler: function(e){}}]" style="height:30px; width: 300px"
		     		 id="pro_add_FProBudgetAmount"  readonly="readonly" name="FProBudgetAmount"  prompt="根据资金来源自动计算" />
		     	</td>
		     	<td class="td3-ys"></td>
		     	<td class="td1-ys">&nbsp;&nbsp;计划开始执行年份</td>
     			<td class="td2">
     				<input class="easyui-numberbox" style="height:30px;width: 300px" data-options="validType:'length[4,4]'" 
     				id="pro_add_planStartYear" name="planStartYear" value="${bean.planStartYear}"/>
     			</td> 
		    </tr>
		    
		    <tr class="trbody">
		    	<td class="td1-ys">大写金额</td>
		     	<td class="td2" >
		     		<span style="color: red"  id="pro_add_UP_FProBudgetAmount"></span>
		     		<input type="hidden" id="pro_add_provideAmount1"  name="provideAmount1" value="${bean.provideAmount1}"/>
		     	</td>
		     	<td class="td3-ys"></td>
		    </tr>
		</table>
	</div>
	
	<div title="资金来源" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
		<table cellpadding="0" cellspacing="0" >
			<tr class="trbody">
		    	<td class="td1-ys" style="padding-top: 0px" colspan="6">
		    		<%@ include file="project-add-fundssource.jsp" %>
				</td>
		    </tr>
		</table>
	</div>
	
	<div title="项目管理信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
		<table cellpadding="0" cellspacing="0" >
			<tr class="trbody">
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;申报部门</td>
     			<td class="td2">
     				<input class="easyui-textbox" data-options="required:false" style="height:30px; width: 300px" readonly="readonly" 
     				id="project-add-FProAppliDepart" name="FProAppliDepart" value="${sbbm}" />
     			</td>
     			<td class="td3-ys" style="width: 35px;"></td>
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;负责人</td>
   				<td class="td2">
   					<input class="easyui-textbox" data-options="required:false" style="height:30px; width: 300px" readonly="readonly"
     				id="project-add-FProAppliPeople" name="FProAppliPeople" value="${sbr}" />
   				</td>
			</tr>
			
   			<tr class="trbody">
   				<td class="td1-ys">&nbsp;&nbsp;负责人电话</td>
   				<td class="td2">
   					<input class="easyui-textbox" data-options="required:false" style="height:30px; width: 300px"
   					id="pro_add_headerPhone" name="headerPhone" value="${bean.headerPhone}"/>
   				</td>
   				<td class="td3-ys"></td>
   			</tr>
		</table>
	</div>
	
	<div id="project-add-performance" hidden="hidden" title="项目支出绩效目标" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
		<%@ include file="project-add-performance.jsp" %>
	</div>
</div>
<script type="text/javascript">
var n =0;
$('#project_add_firstLevel').combobox({
    onChange:function(newValue,oldValue){
    	if(newValue!=null||newValue!=''){
    		$("#project_add_secondLevel").combobox({disabled:false});
		}
    	if(newValue==null||newValue==''){
    		$("#project_add_secondLevel").combobox({disabled:true});
		}
    	//设置名称值
    	$('#add_FirstLevelName').val($('#project_add_firstLevel').combobox('getText'));
    	//设置显示值
    	$('#project_yjxmdm').textbox('setValue',$('#project_add_firstLevel').combobox('getValue'));
       //重置二级分类选择
       $('#project_add_secondLevel').combobox('reload','${base}/project/getSubject2?parentCode='+newValue);
        
    }
});
function FProOrBasicChange(newValue,oldValue){
	if(newValue==1){
	//项目支出
		$('.FProAccording').show();
		$('.FExplain').show();
		$('#base_firstLevel').hide();
		$('#project_firstLevel').show();
		$('#base_secondLevel').hide();
		$('#project_secondLevel').show();
	}
	//重新请求工作流数据
	$("#check_system_div").load("${base}/project/refreshProcess?proOrBasic="+newValue);
}

$('#pro_add_departid').combobox({
    onChange:function(newValue,oldValue){
    	var FProOrBasic = $('#project_add_FProOrBasic').val();
		//重新请求工作流数据
	//	$("#check_system_div").load("${base}/project/refreshProcess?proOrBasic="+FProOrBasic+'&FProAppliDepartId='+newValue);
        
    }
});

$('#project_add_secondLevel').combobox({
    onChange:function(newValue,oldValue){
    	//设置二级分类名称
    	$('#add_secondLevelName').val($('#project_add_secondLevel').combobox('getText'));
    	//设置显示值
    	$('#project_ejxmdm').textbox('setValue',$('#project_add_secondLevel').combobox('getValue'));
    }
});

//基本支出的二级分类选择
$('#project_add_base_secondLevel').combobox({
    onChange:function(newValue,oldValue){
    	//设置二级分类名称
    	$('#add_secondLevelName').val($('#project_add_base_secondLevel').combobox('getText'));
    	//设置显示值
    	$('#project_base_ejxmdm').textbox('setValue',$('#project_add_base_secondLevel').combobox('getValue'));
    }
});


//项目预算金额
$('#pro_add_FProBudgetAmount').numberbox({
	onChange:function(newValue,oldValue){
		$('#pro_add_UP_FProBudgetAmount').html(convertCurrency(newValue*10000));
	}
});
		
$(function(){ 
	$("#pro_add_FProBudgetAmount").textbox('setValue',${bean.FProBudgetAmount});
	var value=$("#pro_add_FProBudgetAmount").val();
	$('#pro_add_UP_FProBudgetAmount').html(convertCurrency(value*10000));
			var proOrBasic = '${bean.FProOrBasic}';
		   	if(proOrBasic==1){
		   	//项目支出
		   		$('.FProAccording').show();
		   		$('.FExplain').show();
	    		$('#project_firstLevel').show();
	    		$('#project_secondLevel').show();
	    		$('#project-add-performance').show();
	    		$('#project_add_FProOrBasic_show').val('项目支出');
		   	}
});
		
//开始执行年份
$("#pro_add_planStartYear").numberbox({
	onChange:function(){
		var planStartYear = $("#pro_add_planStartYear").val();
		//下一年度
		var nextYear = new Date().getFullYear()+1;
		if(planStartYear > nextYear){
			alert("开始执行年份不得大于下一年度, 请重新输入！");
			$("#pro_add_planStartYear").numberbox('setValue', nextYear);
		}
	}
});

function onSelectName(obj){
	//重新请求工作流数据
	$("#aaaaaaa").load("${base}/project/edit/"+obj.id);
}
</script>