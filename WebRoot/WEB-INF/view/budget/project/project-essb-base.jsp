<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-accordion" data-options="" style="width:922px;margin-left: 20px">
	<div title="项目基本信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
		<table cellpadding="0" cellspacing="0" >
			<tr class="trbody">
		   		<td class="td1-ys"><span class="style_must">*</span>&nbsp;项目编号</td>
		     	<td class="td2"  colspan="4">
		     		<input id="project_add_FProCode" class="easyui-textbox" data-options="required:false" readonly="readonly" 
		     		style="height:30px;width:750px" name="FProCode" value="${bean.FProCode}"/>
		     		<!-- 项目id -->
					<input type="hidden" name="FProId" value="${bean.FProId }"/>
					<!-- 预算支出类型 -->
					<input type="hidden" id="project_add_FProOrBasic" onchange="FProOrBasicChange(${bean.FProOrBasic})" name="FProOrBasic" value="${bean.FProOrBasic}"/>
		     	</td>
		   	</tr>
			<tr class="trbody">
		   		<td class="td1-ys"><span class="style_must">*</span>&nbsp;项目名称</td>
		     	<td class="td2" colspan="4">
		     		<input id="project_add_FProName" name="FProName" class="easyui-textbox" style="width:750px"
		     		<c:if test="${operation !='review'}">
			     		readonly="readonly"
			     	</c:if>  value="<c:out value="${bean.FProName}"></c:out>" >
		     	</td>
		   	</tr>
					    <tr class="trbody">
		    	<td class="td1-ys"><span class="style_must">*</span>&nbsp;预算支出类型</td>
		     	<td class="td2">
		     		<input class="easyui-textbox" id="project_add_FProOrBasic_show" readonly="readonly"  style="height:30px; width: 300px" >
		     	</td>
		     	<td class="td3-ys"></td>
     			<td class="td1-ys"><span class="style_must">*</span>&nbsp;部门</td>
		     	<td class="td2" colspan="2">
		     		<input class="easyui-combobox" style="width: 300px;height: 30px; " readonly="readonly" id="pro_add_departid" name="FProAppliDepartId"   data-options="editable:false,panelHeight:'auto',url:'${base}/depart/chooseDepart?selected=${bean.FProAppliDepartId}',method:'POST',valueField:'code',textField:'text',editable:false"/>

		     	</td>
		    </tr>
			<%-- <tr class="trbody">
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;所属一级分类名称</td>
		     	<td class="td2">
		     		<input  class="easyui-textbox" style="width: 300px"
		     		value="${bean.firstLevelName}" readonly="readonly"/>
		     	</td>
		     	
				<td class="td3-ys"></td>
				
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;所属一级分类代码</td>
				<td class="td2">
		     		<input class="easyui-textbox" id="project_yjxmdm" readonly="readonly"  value="${bean.firstLevelCode }" style="width: 300px"/>
		     	</td> 
			</tr>
			
			<tr class="trbody">
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;二级分类名称</td>
		     	<td class="td2">
		     		<input 
		     		class="easyui-textbox" style="width: 300px"
		     		value="${bean.secondLevelName}" readonly="readonly"/>
		     	</td>
		     	
		     	<td class="td3-ys">
				</td>
				
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;二级分类代码</td>
				<td class="td2">
		     		<input class="easyui-textbox" style="height:30px;width: 300px" data-options="required:false,validType:'length[1,50]'"
		   			id="project_ejxmdm"   readonly="readonly"  value="${bean.secondLevelCode}"/>
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
     				<input class="easyui-numberbox" readonly="readonly" style="height:30px;width: 300px" data-options="validType:'length[4,4]'" 
     				id="pro_add_planStartYear" name="planStartYear" value="${bean.planStartYear}" "/>
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
	    		<jsp:include page="project-add-fundssource.jsp" />
			</td>
		    </tr>
		</table>
	</div>
	
	<div title="项目管理信息" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
		<table cellpadding="0" cellspacing="0" >
			<tr class="trbody">
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;申报部门</td>
     			<td class="td2">
     				<div ondblclick="">
     					<input class="easyui-textbox" readonly="readonly" name="FProAppliDepart"
     					value="${sbbm }" readonly="readonly" style="width: 300px">
     				</div>
     			</td>
     			<td class="td3-ys" style="width: 35px;"></td>
				<td class="td1-ys"><span class="style_must">*</span>&nbsp;申报人</td>
   				<td class="td2">
   					<input class="easyui-textbox" data-options="required:false"  style="width: 300px"
     				id="project-add-FProAppliPeople"  name="FProAppliPeople" value="${sbr }" readonly="readonly">
   				</td>
			</tr>
			
   			<tr class="trbody">
   				<td class="td1-ys">&nbsp;&nbsp;负责人电话</td>
   				<td class="td2">
   					<input class="easyui-textbox" style="width: 300px" readonly="readonly"
   					id="pro_add_headerPhone" name="headerPhone" value="${bean.headerPhone}"/>
   				</td>
   				<td class="td3-ys"></td>
   			</tr>
		</table>
	</div>
	
	<c:if test="${bean.FProOrBasic==1 }">
		<div title="项目支出绩效目标" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
			<%@ include file="detail/project-detail-performance.jsp" %>
		</div>
	</c:if>
</div>
<script type="text/javascript">
function onChangeCgjeInput(){
	var sfcg = 	$('input:radio[name="FProcurementYn"]:checked').val();
	/* if(sfcg==0){
		$('#th_cgje').hide();
		$('#td_cgje').hide();
	}else if(sfcg==1){
		$('#th_cgje').show();
		$('#td_cgje').show();
	} */
}
function onChangeSfcxxxm(){
	var sfcx = $('input:radio[name="FContinuousYn"]:checked').val();
	if(sfcx==0){
		$('#pro_add_FProRollingCycle').numberbox('setValue','1');
	}else if(sfcx==1){
		$('#pro_add_FProRollingCycle').numberbox('setValue','');
	}
}

//设置附件信息
function setAccoFile(){
	var s="";
	$(".fileUrl1").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#files1").val(s);
}
function setPlanFile(){
	var s="";
	$(".fileUrl2").each(function(){
		s=s+$(this).attr("id")+",";
	});
	$("#files2").val(s);
}

//附件上传
var c1=0;
function upFile1() {
	
	var obj = document.getElementById('f1');
	var files0 = obj.files[0];
	if (window.URL != undefined) {
		alert('in')
		var testurl = window.URL.createObjectURL(files0);
		alert(testurl)
		//输出	blob:http://localhost:8080/5c24a308-0a6e-4158-b1be-3cf7a02c48ab firefox / blob:D34-65D-4Y7-897-A13-4A643
	}
	
	
	var url = $("#f1").val();
	var urlli = url.split(',');
	for(var i=0; i< urlli.length; i++){
		var fileurl=urlli[i];
		var filename = fileurl.split('\\');
		$('#tdf1').append(
			"<div style='margin-top: 10px;'>"+
				"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<img id='imgt1"+c1+"' style='display:none' src='"+base+"/resource-modality/${themenurl}/sccg.png'>"+
				"<img id='imgf1"+c1+"' style='display:none' src='"+base+"/resource-modality/${themenurl}/scsb.png'>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+fileurl+"' class='fileUrl1' href='#' style='color:red' onclick='deleteAttac1(this)'>删除</a><div>"
		);
		c1++;
		fileurl = encodeURI(encodeURI(fileurl));
		$.ajax({
			async:false,
			type:"POST",
	        url:base+"/project/uploadFile?fileurl="+fileurl+"&type=lxyj",
	        success:function(data){
		    	if($.trim(data)=="true"){
					$('#imgt1'+i).css('display','');
		    	} else {
					$('#imgf1'+i).css('display','');
		    	}
	        }
	    });
	}	
} 
var c2=0;
function upFile2() {
	var url = $("#f2").val();
	var urlli = url.split(',');
	for(var i=0; i< urlli.length; i++){
		var fileurl=urlli[i];
		var filename = fileurl.split('\\');
		$('#tdf2').append(
			"<div style='margin-top: 10px;'>"+
				"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<img id='imgt2"+c2+"' style='display:none' src='"+base+"/resource-modality/${themenurl}/sccg.png'>"+
				"<img id='imgf2"+c2+"' style='display:none' src='"+base+"/resource-modality/${themenurl}/scsb.png'>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+fileurl+"' class='fileUrl2' href='#' style='color:red' onclick='deleteAttac2(this)'>删除</a><div>"
		);
		c1++;
		fileurl = encodeURI(encodeURI(fileurl));
		$.ajax({
			async:false,
			type:"POST",
	        url:base+"/project/uploadFile?fileurl="+fileurl+"&type=ssfa",
	        success:function(data){
		    	if($.trim(data)=="true"){
					$('#imgt2'+i).css('display','');
		    	} else {
					$('#imgf2'+i).css('display','');
		    	}
	        }
	    });
	}	
} 
//附件删除AJAX
function deleteAttac1(a){
	var filename = a.id;
	filename = encodeURI(encodeURI(filename));  
	$.ajax({
		type:"POST",
        url:base+"/proejct/deleteFile?filename="+filename+"&type=lxyj",
        success:function(data){
        	if($.trim(data)=="true"){
				//删除div
	        	$(a).parent().remove();
        		alert("附件删除成功！");
        	} else {
        		alert("附件删除失败！");
        	}
        }
    });
}
function deleteAttac2(a){
	var filename = a.id;
	filename = encodeURI(encodeURI(filename));  
	$.ajax({
		type:"POST",
        url:base+"/proejct/deleteFile?filename="+filename+"&type=ssfa",
        success:function(data){
        	if($.trim(data)=="true"){
				//删除div
	        	$(a).parent().remove();
        		alert("附件删除成功！");
        	} else {
        		alert("附件删除失败！");
        	}
        }
    });
}

/* //滚动周期变化
function gdzq(newValue, oldValue) {
	if(newValue>99){
		return;
	}
	$('.gdzqTr').each(function(i){
		if(newValue>=1){
			$(this).remove();
		}
	});
	
	var y = $('#pro_add_planStartYear').textbox('getValue');//开始执行年份
	
	for(var i=0;i<newValue;i++) {
		var n=i+1;
		var year = parseInt(y)+i;
		
		$('#pro_plan_table').append('<tr class="gdzqTr">'+
		'<td><span style="width: 30px;display: block;text-align: center;">'+n+'</span></td>'+
		'<td><input class="pro_plan_input" id="plan2_'+i+'" name="planList['+i+'].year" value="'+year+'"/></td>'+
		'<td><input class="pro_plan_input" id="plan3_'+i+'" name="planList['+i+'].totalPlan"/></td>'+
		'<td><input class="pro_plan_input" id="plan5_'+i+'" name="planList['+i+'].earlyAmount1" oninput="autoCaculate(this)"/></td>'+
		'<td><input class="pro_plan_input" id="plan6_'+i+'" name="planList['+i+'].earlyAmount2" oninput="autoCaculate(this)"/></td>'+
		'<td><input class="pro_plan_input" id="plan4_'+i+'" name="planList['+i+'].earlyTotal" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan8_'+i+'" name="planList['+i+'].adjustAmount1" oninput="autoCaculate(this)"/></td>'+
		'<td><input class="pro_plan_input" id="plan9_'+i+'" name="planList['+i+'].adjustAmount2" oninput="autoCaculate(this)"/></td>'+
		'<td><input class="pro_plan_input" id="plan7_'+i+'" name="planList['+i+'].adjustTotal" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan11_'+i+'" name="planList['+i+'].yearAmount1" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan12_'+i+'" name="planList['+i+'].yearAmount2" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan10_'+i+'" name="planList['+i+'].yearTotal" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan14_'+i+'" name="planList['+i+'].outAmount1" oninput="autoCaculate(this)"/></td>'+
		'<td><input class="pro_plan_input" id="plan15_'+i+'" name="planList['+i+'].outAmount2" oninput="autoCaculate(this)"/></td>'+
		'<td><input class="pro_plan_input" id="plan13_'+i+'" name="planList['+i+'].outTotal" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan17_'+i+'" name="planList['+i+'].leastAmount1" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan18_'+i+'" name="planList['+i+'].leastAmount2" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan16_'+i+'" name="planList['+i+'].leastTotal" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td>'+
		'<td><input class="pro_plan_input" id="plan20_'+i+'" name="planList['+i+'].actualAmount1" oninput="autoCaculate(this)"/></td>'+
		'<td><input class="pro_plan_input" id="plan21_'+i+'" name="planList['+i+'].actualAmount2" oninput="autoCaculate(this)"/></td>'+
		'<td><input class="pro_plan_input" id="plan19_'+i+'" name="planList['+i+'].actualTotal" readonly="readonly" oninput="autoCaculate(this)" placeholder="&lt;自动计算 &gt;"/></td></tr>'
		);
	}
	
} */

//项目预算金额
$('#pro_add_FProBudgetAmount').numberbox({
	onChange:function(newValue,oldValue){
		$('#pro_add_UP_FProBudgetAmount').html(convertCurrency(newValue*10000));
	}
});
$(function(){ 
	$("#pro_add_FProBudgetAmount").textbox('setValue',${bean.FProBudgetAmount});
		$('#pro_add_UP_FProBudgetAmount').html(convertCurrency(${bean.FProBudgetAmount}*10000));
		var proOrBasic = '${bean.FProOrBasic}';
	   	if(proOrBasic==0){
	   	//基本支出
	   		$('.FProAccording').hide();
	   		$('.FExplain').hide();
    		$('#base_firstLevel').show();
    		$('#base_secondLevel').show();
    		$('#project_add_FProOrBasic_show').val('基本支出');
	   	}else if(proOrBasic==1){
	   	//项目支出
	   		$('.FProAccording').show();
	   		$('.FExplain').show();
    		$('#project_firstLevel').show();
    		$('#project_secondLevel').show();
    		$('#project_add_FProOrBasic_show').val('项目支出');
	   	}
});
</script>