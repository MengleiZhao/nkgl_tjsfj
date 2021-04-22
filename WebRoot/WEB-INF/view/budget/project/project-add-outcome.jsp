<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/includes/taglibs.jsp"%>
<style type="text/css">
.pro_outcome_th {
	/* border-left:1px solid black;
	border-top:1px solid black;
	border-bottom:1px solid black;
	margin: 0;
	padding: 0 */
	
	background: #c1e3f2;
	font-weight: bold;
	color: #333333;
	text-align: center;
}
#pro_outcome_table td{text-align: center;}
.pro_outcome_input{
	width: 263px;
	border: 0;
	background-color: #f0f5f7;
	vertical-align: middle;
}
.accordion .accordion-header {
	height: 20px;
	width: 910px;
}
textarea {
	height: 60px;
	resize:none;
	padding: 8px;
	overflow: auto; 
}
/* .ystable{border:1px solid #999999;} */
</style>

	<table style="width:220px;margin-left: 20px">
		<tr id="ttttt" style="height: 15px">
			<c:if test="${operation!='detail'}">
				<td style="text-align: left;width: 70px">
					<a href="javascript:void(0)" onclick="appendOutCome()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/tjyh1.png"
							onmouseover="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/tjyh2.png')"
							onmouseout="this.setAttribute('src','${base}/resource-modality/${themenurl}/button/tjyh1.png')"
						/>
					</a>
				</td>
				<td style="text-align: left;width: 70px">
					<a href="#" id="index-imput" onclick="projectOutcomeImput()">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/daoru1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				
				<td style="text-align: left;width: 70px">
					<a href="${base}/project/outcomeDownload" id="index-imput">
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/mbxz1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
						<input type="hidden" id="outcomeTotal" name="outcomeTotal" value="${bean.FProBudgetAmount}"> 
					</a>
				</td>
			</c:if>
		</tr>
	</table>
	
	<table id="pro_outcome_table" style="width: 1100px;margin-left: 20px" cellpadding="1" cellspacing="1">
		<tr>
			<th class="pro_outcome_th" style="width: 40px">序号</th>
			<th class="pro_outcome_th">活动</th>
			<th class="pro_outcome_th">经济分类科目</th>
			<th class="pro_outcome_th">支出金额(元)</th>
			<th class="pro_outcome_th">摘要</th>
			<th class="pro_outcome_th" style="width: 40px">操作</th>
		</tr>
		<c:forEach var="i" items="${expDetailList}" varStatus="status">
			<tr class="pro_outcome_tr">
				<td id="outcome1_${status.index}">${status.index+1}</td>
				<td><textarea class="pro_outcome_input" id="outcome2_${status.index}" name="expendList[${status.index}].activity">${i.activity}</textarea></td>
				<td>
					<input id="outcome7_${status.index}" class="pro_add_outcome_subName" name="expendList[${status.index}].subName" value="${i.subName}" type="hidden"/>
					<input id="outcome8_${status.index}" class="pro_add_outcome_subCode" name="expendList[${status.index}].subCode" value="${i.subCode}" type="hidden"/>
					<a id="outcome12_${status.index}" onclick="chooseOut(${status.index})" style="color: blue;width: 175px;display: block;" href="#">
						<c:if test="${!empty i.subName}">${i.subName}</c:if>
						<c:if test="${empty i.subName}">点击选择经济分类科目</c:if>
					</a>
				</td>
				<td><textarea class="pro_outcome_input" id="outcome10_${status.index}" name="expendList[${status.index}].outAmount"  oninput="autoCalTotal(this)" >${i.outAmount}</textarea></td>
				<td><textarea class="pro_outcome_input" id="outcome3_${status.index}" name="expendList[${status.index}].actDesc">${i.actDesc}</textarea></td>
				<c:if test="${operation!='detail'}">
					<td><a href="javascript:void(0)" onclick="deleteOutRow(this,${status.index})"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="${base}/resource-modality/${themenurl}/list/delete1.png"></a></td>
				</c:if>
			</tr>
		</c:forEach>
		<c:if test="${empty expDetailList}">
		<tr>
			<td>1</td>
			<td><textarea class="pro_outcome_input" id="outcome2_0" name="expendList[0].activity"></textarea></td>
			<td>
				<input id="outcome7_0" name="expendList[0].subName" type="hidden"/>
				<input id="outcome8_0" name="expendList[0].subCode" class="pro_add_outcome_subCode" type="hidden"/>
				<a id="outcome12_0" onclick="chooseOut(0)" style="color: blue;width: 175px;display: block;" href="#">点击选择经济分类科目</a>
			</td>
			<td><textarea class="pro_outcome_input" id="outcome10_0" name="expendList[0].outAmount" onblur="autoCalTotal(this)">0</textarea></td>
			
			<td><textarea class="pro_outcome_input" id="outcome3_0" name="expendList[0].actDesc"></textarea></td>
			<c:if test="${operation!='detail'}">
			<td><a href="javascript:void(0)" onclick="deleteOutRow(this,${status.index})"><img onmouseover="listMouseOver(this)" onmouseout="listMouseOut(this)" src="${base}/resource-modality/${themenurl}/list/delete1.png"></a></td>
			</c:if>
		</tr>
		</c:if>
	</table>
	<!-- 删除的行数 -->
	<input type="hidden" id="delIndex" name="delIndex">
		
	<script type="text/javascript">
	var rIndex=0;
	
	function appendOutCome(){
		
		$("#pro_outcome_table").children("tr").length;
		rIndex = $("#pro_outcome_table").find("tr").length-1;
		var table = $('#pro_outcome_table');
		table.append("<tr>"
				+"<td>"+(rIndex+1)+"</td>"
				+"<td><textarea class='pro_outcome_input' id='outcome2_"+rIndex+"' name='expendList["+rIndex+"].activity'></textarea></td>"
				+"<td><input id='outcome7_"+rIndex+"' name='expendList["+rIndex+"].subName' type='hidden'/>"
				+"<input id='outcome8_"+rIndex+"' class='pro_add_outcome_subCode' name='expendList["+rIndex+"].subCode' type='hidden'/>"
				+"<a id='outcome12_"+rIndex+"' onclick='chooseOut("+rIndex+")' style='color: blue;width: 175px;display: block;' href='#'>点击选择经济分类科目</a></td>"
				+"<td><textarea class='pro_outcome_input' id='outcome10_"+rIndex+"' name='expendList["+rIndex+"].outAmount'  oninput='autoCalTotal(this)'>0</textarea></td>"
				+"<td><textarea class='pro_outcome_input' id='outcome3_"+rIndex+"' name='expendList["+rIndex+"].actDesc'></textarea></td>"
				+"<td><a href='javascript:void(0)' onclick='deleteOutRow(this,"+rIndex+")'><img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/delete1.png'></a></td></tr>");
	}
	
	function chooseOut(rIndex){
		var ejProCode="";
		var code = $('#outcome8_'+rIndex).val();
		var fProOrBasic = '${bean.FProOrBasic}';
		var win=creatFirstWin('选择-支出预算事项',600,550,'icon-search','/project/toChooseKm?rIndex='+rIndex+'&fProOrBasic='+fProOrBasic+'&ejProCode='+ejProCode+'&code='+code);
		win.window('open');
	}
	
	//删除行方法
	var arr=new Array();
	function deleteOutRow(obj,ss){
		
		var td=obj.parentNode;	//td
		var row=td.parentNode;	//tr
		row.parentNode.removeChild(row);
		arr.push(ss);						//往数组末尾添加值 值为删除行序号		
		arr.sort();							//按数字顺序排序
		var delNum=arr.join(",");			//用逗号分隔数组元素
		$("#delIndex").val(delNum);			//将数据存到隐藏域中
		
		//重新计算支出金额
		autoCalTotal(null);
		
		//对支出金额不为零的行的序号重新排序
		var rows = document.getElementById('pro_outcome_table').rows;
		for (var i = 1; i < rows.length; i++) {
			rows[i].cells[0].innerText = i;
		}
		
		//重新把ID进行排序
		var table = $('#pro_outcome_table');
		var trs = table[0].childNodes[1].children.length;
		for(var i = 1;i < trs; i++){
			/* //序号
			table[0].childNodes[1].children[i].children[0].id=('outcome1_'+(i-1)); */
			//活动
			table[0].childNodes[1].children[i].children[1].children[0].id=('outcome2_'+(i-1));
			table[0].childNodes[1].children[i].children[1].children[0].name=('expendList['+(i-1))+'].activity';
			//经济分类科目
			table[0].childNodes[1].children[i].children[2].children[0].id=('outcome7_'+(i-1));
			table[0].childNodes[1].children[i].children[2].children[0].name=('expendList['+(i-1))+'].subName';
			table[0].childNodes[1].children[i].children[2].children[1].id=('outcome8_'+(i-1));
			table[0].childNodes[1].children[i].children[2].children[1].name=('expendList['+(i-1))+'].subCode';
			table[0].childNodes[1].children[i].children[2].children[2].id=('outcome12_'+(i-1));
			table[0].childNodes[1].children[i].children[2].children[2].onclick=function onchlick(){
				var ejProCode="";
				var code = $('#outcome8_'+(i-1)).val();
				var fProOrBasic = '${bean.FProOrBasic}';
				/* if(fProOrBasic==0){
					ejProCode=$('#project_base_ejxmdm').textbox('getValue');
					if(ejProCode==""){
						$.messager.alert('系统提示', "请选择二级分类名称", 'info');
						return false;
					}
				} */
				var win=creatFirstWin('选择-支出预算事项',600,550,'icon-search','/project/toChooseKm?rIndex='+(i-1)+'&fProOrBasic='+fProOrBasic+'&ejProCode='+ejProCode+'&code='+code);
				win.window('open');
			};
			//支出金额(元)
			table[0].childNodes[1].children[i].children[3].children[0].id=('outcome10_'+(i-1));
			table[0].childNodes[1].children[i].children[3].children[0].name=('expendList['+(i-1))+'].outAmount';
			//摘要
			table[0].childNodes[1].children[i].children[4].children[0].id=('outcome3_'+(i-1));
			table[0].childNodes[1].children[i].children[4].children[0].name=('expendList['+(i-1))+'].actDesc';
		}
		
	}
	
	
	/* 叶添加 */
	//弹出支出明细导入页面
	function projectOutcomeImput(){
		var win = creatFirstWin('支出明细导入', 500, 200, 'icon-search', '/project/outcomeImput');
		win.window('open');
	}
	
	
	//自动计算总支出金额（元）
	function autoCalTotal(obj){
		var outcomeTotal=0;//支出金总额
		if(obj!=null){
			var patt = /^[0-9]*(\.){0,1}[0-9]{0,2}$/;//判断小数的正则表达式
			//字符校验
			if(patt.test(obj.value)){
				outcomeTotal = parseFloat(obj.value);
			} else {
				obj.value = outcomeTotal;
			}
		}
		// $('#pro_plan_table tr')
		///var length=$("#pro_outcome_table").find("tr").length;
		$('#pro_outcome_table tr').each(function(i){     // 遍历 tr
			var index=0;
			$(this).find('td').each(function(j){  // 遍历 tr 的各个 td
				$(this).find('textarea').each(function(k){
					index++;
					if(obj==null){
						if(index==2){
							outcomeTotal+=parseFloat(this.value);
						}
					}else if(index==2 && obj.id !=this.id){
						outcomeTotal+=parseFloat(this.value);
					}
				});
	      	});
	   });
		outcomeTotal=outcomeTotal/10000;
		//修改支出明细总额
		$('#outcomeTotal').val(outcomeTotal.toFixed(6));
	}
	</script>