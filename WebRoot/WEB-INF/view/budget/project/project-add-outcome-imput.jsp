<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<form id="project-outcome-imput-form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
<div style="width: 436px;height: 127px;border:1px #d9e3e7 solid;">
	<table style="width: 436px;height: 127px;" cellpadding="0" cellspacing="0" border="0" >
		<tr>
			<td style="padding-left: 20px;width: 60px">导入文件</td>
			<td>
				<input style="width: 260px;" value="${bean.deptName}" readonly="readonly" id="fil" name="" class="easyui-textbox"/>
				<input type="file" id="f" name="xlsx" onchange="upFile()" hidden="hidden">
			</td>
			<td style="padding-right: 20px">
				<a onclick="$('#f').click()" style="font-weight: bold;" href="#">
					<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
				</a>
			</td>
		</tr>
		<tr style="text-align: center;visibility: hidden; " id="mingxiprogid" >
			<td colspan="3">
				<div style="background:#EFF5F7;width:400px;height:10px;margin-top:5px;" >
		       <div id="mingxiprogressNumber" style="background:#3AF960;width:0px;height:10px" >
		       </div>文件导入中...&nbsp;&nbsp;<font id="mingxipercent">0%</font> 
				</div>
			</td>
		</tr>
		<tr style="text-align: center;" id="buttonid">
			<td colspan="3">
				<a href="javascript:void(0)" onclick="save()">
					<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="closeFirstWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</td>
		</tr>
	</table>
	
</div>
</form>

<script type="text/javascript">
function progressFunction(index,total) {
		  //index：文件加载的大小   total：文件总的大小                    
	      var percentComplete = Math.round(index * 100 / total);    
	      //加载进度条，同时显示信息          
	      $("#mingxipercent").html(percentComplete + '%');
	      //percentComplete*2  这个2是根据总像素大小来改变的，如果是300px就 *3
	      $("#mingxiprogressNumber").css("width",""+percentComplete*4+"px");   
	} 
function upFile() {
	var url = $("#f").val();
	$('#fil').textbox('setValue',url);
}

function save() {
	$("#mingxiprogid").css("visibility","visible");
	$("#buttonid").css("visibility","hidden");
	//保存导入的xml文件
	var url = $('#fil').textbox('getValue');
	
	var index1=url.lastIndexOf(".");
    var index2=url.length;
	var arr = url.substring(index1+1,index2);
	
	
	
	var formData = new FormData($('#project-outcome-imput-form')[0]);
	if(arr != "xlsx") {
		alert("请上传xlsx文件！");
		$("#mingxiprogid").css("visibility","hidden");
		$("#buttonid").css("visibility","visible");
	} else {
		$.ajax({
        	type: 'post',
            url: base+'/project/outcomeCollect',
			data: formData,
			cache: false,
			processData: false,
			contentType: false,
			dataType: 'json',
		}).success(function (data) {
			ajaxAppendOutCome(data);
			closeFirstWindow();
		}).error(function () {
			alert('上传失败！');
			$("#mingxiprogid").css("visibility","hidden");
			$("#buttonid").css("visibility","visible");
			closeFirstWindow();
		});
		
	} 
}

//删除支出金额为零的项，与行删除方法不同
function deleteOutRows(row,index){
	row.parentNode.removeChild(row);
	arr.push(index);					//往数组末尾添加值 值为删除行序号
	arr.sort();							//按数字顺序排序
	var delNum=arr.join(",");			//用逗号分隔数组元素	
	$("#delIndex").val(delNum);			//将数据存到隐藏域中
	
	//重新计算支出金额
	autoCalTotal(null);
	
}
function ajaxAppendOutCome(data){
	var table = $('#pro_outcome_table');
	var tableData = $('#pro_outcome_table').val();
	var total = data.length;	//模板导入数据数
	
	var rIndex = 0;
	var rows = document.getElementById('pro_outcome_table').rows;
	for(var i=1;i<rows.length;i++){
		var indexs = rows[i].cells[0].innerText;
		var amount=$('#outcome10_'+(indexs-1)).val();
	    if(amount==undefined ||amount=="" ||amount==0){		//删除支出金额为零的行
	    	deleteOutRows(rows[i],indexs-1);
	    	i=i-1;
	   	}
	}
	//对支出金额不为零的行的序号重新排序
	for(var i=1;i<rows.length;i++){
		rows[i].cells[0].innerText = i;
	}
	for(var i=0;i<total;i++){
		if(data[i].subName == null) {
			table.append("<tr>"
					+"<td>"+(rows.length)+"</td>"	//模板导入数据行号从添加行+1开始	即为rows.length
					+"<td><textarea class='pro_outcome_input' id='outcome2_"+rIndex+"' name='expendList["+rIndex+"].activity'>"+data[i].activity+"</textarea></td>"
					+"<td>"
					+"<input id='outcome7_"+rIndex+"' name='expendList["+rIndex+"].subName' type='hidden'/>"
					+"<a id='outcome12_"+rIndex+"' onclick='chooseOut("+rIndex+")' style='color: blue;width: 175px;display: block;' href='#'>点击选择经济分类科目</a></td>"
					+"<td><textarea class='pro_outcome_input' id='outcome10_"+rIndex+"' name='expendList["+rIndex+"].outAmount'>"+data[i].outAmount+"</textarea></td>"
					+"<td><textarea class='pro_outcome_input' id='outcome3_"+rIndex+"' name='expendList["+rIndex+"].actDesc'>"+data[i].actDesc+"</textarea></td>"
					/* +"<td><textarea class='pro_outcome_input' id='outcome4_"+rIndex+"' name='expendList["+rIndex+"].sonActivity'>"+data[i].sonActivity+"</textarea></td>"
					+"<td><textarea class='pro_outcome_input' id='outcome5_"+rIndex+"' name='expendList["+rIndex+"].sonActDesc'>"+data[i].sonActDesc+"</textarea></td>" */
					+"<td><a href='javascript:void(0)' onclick='deleteOutRow(this,"+rIndex+")'><img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/delete1.png'></a></td></tr>");
		} else {
			table.append("<tr>"
					+"<td>"+(rows.length)+"</td>"
					+"<td><textarea class='pro_outcome_input' id='outcome2_"+rIndex+"' name='expendList["+rIndex+"].activity'>"+data[i].activity+"</textarea></td>"
					+"<td>"+"<input id='outcome7_"+rIndex+"' name='expendList["+rIndex+"].subName' type='hidden'/>"
					+"<a id='outcome12_"+rIndex+"' onclick='chooseOut("+rIndex+")' style='color: blue;width: 175px;display: block;' href='#'>"+data[i].subName+"</a></td>"
					+"<td><textarea class='pro_outcome_input' id='outcome10_"+rIndex+"' name='expendList["+rIndex+"].outAmount'>"+data[i].outAmount+"</textarea></td>"
					+"<td><textarea class='pro_outcome_input' id='outcome3_"+rIndex+"' name='expendList["+rIndex+"].actDesc'>"+data[i].actDesc+"</textarea></td>"
					/* +"<td><textarea class='pro_outcome_input' id='outcome4_"+rIndex+"' name='expendList["+rIndex+"].sonActivity'>"+data[i].sonActivity+"</textarea></td>"
					+"<td><textarea class='pro_outcome_input' id='outcome5_"+rIndex+"' name='expendList["+rIndex+"].sonActDesc'>"+data[i].sonActDesc+"</textarea></td>" */
					+"<td><a href='javascript:void(0)' onclick='deleteOutRow(this,"+rIndex+")'><img onmouseover='listMouseOver(this)' onmouseout='listMouseOut(this)' src='"+base+"/resource-modality/${themenurl}/list/delete1.png'></a></td></tr>");
		}
		rIndex++;
		progressFunction(rIndex, total);
	}
}
</script>