<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<style>
	.input_amonut{
		width: 140px;
		height: 20px;
		border-radius: 4px;
		border: 1px solid #F0F5F7;
	}
	.otherfp_span{
		width:100px;
		margin-bottom:5px;
		display: inline-block;
	}
	.input_amonut1{
		width: 150px;
		height: 20px;
		border-radius: 4px;
		border: 1px solid #F0F5F7;
	}
	.otherfp_span1{
		width:85px;
		margin-bottom:5px;
		margin-left:15px;
		display: inline-block;
	}
</style>
<div id="dlgdiv" class="easyui-dialog"
     style="width: 650px; height: 550px; padding: 10px 20px" closed="true"
     buttons="#dlgdiv-buttons">
        <div id="divlarge"></div>
</div>
<div style="margin-top: 5px;margin-bottom: 5px;height: 15px;">
	<a style="float: left; font-weight: bold;color: #005E8A;font-size:12px;">发票明细</a>
	<a style="float: left;">&nbsp;&nbsp;</a>
</div>
<form id="formOther">
	<table id="otherfapiao" class="window-table-readonly-zc" cellspacing="0" cellpadding="0" style=" height: 100px;width:695px;">
		<c:forEach items="${InvoicelistOther }" varStatus="i" var="invoice">
			<tbody id="other_${i.index}" >
				<tr>
					<td class="td1" style="width: 100px;">
						&nbsp;&nbsp;<span id="otherFp_${i.index}">发票${i.index+1}</span>
						<input type="text" id="otherfp_${i.index}" value="${invoice.fileId}" name="fileId" hidden="hidden">
						<input type="file" id="otherimage_${i.index}" accept="image/jpeg,image/jpg,image/png" onchange="upImageFileOther(this)" hidden="hidden">
					</td>
					<td class="td2" colspan="3" id="otherimagetd_${i.index}" style="width: 150px;">
						<c:if test="${invoice.fileId!=null&&!empty invoice.fileId}">
							<img style="vertical-align:bottom;width: 100px; height: 73px;margin-left:0px" onclick="yl(this.src)" src="${base}/attachment/download/${invoice.fileId}"/><a style="color:red" href="#" onclick="deleteImageOther(this)" id="othersc_${i.index}">    删除</a>
						</c:if>
						<c:if test="${invoice.fileId==null||empty invoice.fileId}">
							<a onclick="clickUpFileOther(this)" id="otherclick_${i.index}" style="font-weight: bold;" href="#">
								<img style="vertical-align:bottom;margin-left:30px" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
							</a>
						</c:if>
					</td>
					<td class="td2" colspan="3" id="">
						<div style="width: auto; height:auto; height:auto;margin-top:5px;margin-left:49px;width: 300px;">
							<p style="margin-bottom:10px;">
								<span class="otherfp_span" >金额(元)：</span>
								<input class="input_amonut1 a easyui-numberbox" name="amount" id="otheramount_${i.index}" value="${invoice.amount}" onchange="addAmonut2(this)"data-options="precision:2"/>
								<!-- oninput = "value=value.replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')" value=""  -->
							</p>
							<p style="margin-bottom:5px;" >
								<span class="otherfp_span">备注：</span>
								<input  class="input_amonut1 easyui-textbox" name="remark" id="otherremark_${i.index}"value="${invoice.remark}" />
							</p>
						</div>
					</td>
					<td style="vertical-align:top">
						<div>
							<a onclick="deleteOther(this)" style="float: right;" href="#" > 
								<img style="vertical-align:bottom;margin-left:30px;margin-right: 5px;margin-top: 5px;" src="${base}/resource-modality/${themenurl}/button/gb1.jpg">
								<%-- <img style="vertical-align:bottom;margin-left:30px" src="${base}/resource-modality/${themenurl}/button/scyh1.png"
								onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> --%>
							</a>
						</div>
					</td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
	<a onclick="appendOther(this)" style="font-weight: bold;vertical-align: middle;margin-left: 308px;" href="#">
		<img style="vertical-align:bottom;" src="${base}/resource-modality/${themenurl}/button/tjfp1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
	</a>
</form>	
<div style="height:10px"></div>
<script type="text/javascript">
var index =4;
var a =1;
var b =0;
var c =0;
var d =0;
var f =0;
$(function(){
/* 	for(var i=0; i<=index; i++){
		
	$('#startTime' + i).change(function(){
		countDays2(i);
		});
	$('#endTime' + i).change(function(){
		countDays2(i);
		});
	} */
});
//计算报销金额
function addAmonut2(obj){
	var totalAmount=0;
    var tags = [];
    $(".a").each(function(i,e){
        tags[i]= e.value;
        var num =parseFloat(tags[i]);
        if(!isNaN(num)){
        	totalAmount+=num;
        }
      });
    $('#p_amount').html(fomatMoney(totalAmount,2)+" [元]");
    $('#reimburseAmount').val(totalAmount.toFixed(2));
      countDays2(obj);
      cx();
}

function countDays2(obj){
	var id=obj.id;
	var j=id.lastIndexOf("\_");
	var i=id.substring(j+1,id.length);
	var time1 =new Date($('#startTime_' + i).val());
	var time2 =new Date($('#endTime_' + i).val());
	var d = (time2 - time1) / 86400000 ;
	if (d > 0) {
		var amount =parseFloat($('#otheramount_' + i).val());
		if(!isNaN(amount)){
			var average =(amount/d).toFixed(2);
			$('#average_' + i).val(average);
		}
		$('#days_' + i).val(d);
	}else {
		$('#days_' + i).val(0);
		$('#average_' + i).val(0);
	}
}

function countreception2(obj){
	var id=obj.id;
	var j=id.lastIndexOf("\_");
	var i=id.substring(j+1,id.length);
	var num =0;
	var num1 =parseFloat($('#otheramount_' + i).val());
	var num2 =parseFloat($('#amount1_' + i).val());
	if (!isNaN(num1)) {
		num=num+num1;
		}
	if (!isNaN(num2)) {
		num=num+num2;
		}
	$('#amount2_' + i).val(num);
	addAmonut2(obj);
}

function appendOther(obj) {
	index = index + 1;
	var fromNum = parseInt(obj.parentNode.parentNode.children[5].children[0].children.length);
	$('#otherfapiao').append('<tbody id="other_'+a+'" >'+
	'<tr><td class="td1" style="width: 100px;">&nbsp;&nbsp;<span id="otherFp_'+a+'">发票'+(fromNum+1)+'</span>'+
	'<input type="text" id="otherfp_'+a+'" name="fileId" hidden="hidden">'+
	'<input type="file" id="otherimage_'+a+'" accept="image/jpeg,image/jpg,image/png" onchange="upImageFileOther(this)" hidden="hidden"></td>'+
	'<td class="td2" colspan="3" id="otherimagetd_'+a+'" style="width: 150px;">'+
	'<a onclick="clickUpFileOther(this)" id="otherclick_'+a+'" style="font-weight: bold;" href="#">'+
	'<img style="vertical-align:bottom;margin-left:30px" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">'+
	'</a></td>'+
	'<td class="td2" colspan="3" id="">'+
	'<div style="width: auto; height:auto; height:auto;margin-top:5px;margin-left:49px;width: 300px;">'+
	'<p style="margin-bottom:10px;">'+
	'<span class="otherfp_span" >金额(元)：</span>'+
	'<input class="input_amonut1 a easyui-numberbox" name="amount" id="otheramount_'+a+'" onchange="addAmonut2(this)"data-options="precision:2"/>'+
	'</p>'+
	'<p style="margin-bottom:5px;" >'+
	'<span class="otherfp_span">备注：</span>'+
	'<input  class="input_amonut1 easyui-textbox" name="remark" id="otherremark_'+a+'" value="" />'+
	'</p></div>'+
	'</td>'+
	'<td style="vertical-align:top">'+
	'<div>'+
	'<a onclick="deleteOther(this)" style="float: right;" href="#" > '+
	'<img style="vertical-align:bottom;margin-left:0px;margin-right: 5px;margin-top: 5px;" src="${base}/resource-modality/${themenurl}/button/gb1.jpg">'+
	'</a>'+
	'</div>'+
	'</td>'+
	'</tr></tbody>');
	$.parser.parse($('#other_'+a).parent());
	
	
	
	a = a + 1;
}
//将表单序列化成json格式的数据(但不适用于含有控件的表单，例如复选框、多选的select)
(function($) {
	$.fn.serializeJson2 = function() {
		var jsonData1 = {};
		var serializeArray = this.serializeArray();
		// 先转换成{"id": ["12","14"], "name": ["aaa","bbb"], "pwd":["pwd1","pwd2"]}这种形式
		$(serializeArray).each(
				function() {
					if (jsonData1[this.name]) {
						if ($.isArray(jsonData1[this.name])) {
							jsonData1[this.name].push(this.value);
						} else {
							jsonData1[this.name] = [ jsonData1[this.name],
									this.value ];
						}
					} else {
						jsonData1[this.name] = this.value;
					}
				});
		// 再转成[{"id": "12", "name": "aaa", "pwd":"pwd1"},{"id": "14", "name": "bb", "pwd":"pwd2"}]的形式
		var vCount = 0;
		// 计算json内部的数组最大长度
		for ( var item in jsonData1) {
			var tmp = $.isArray(jsonData1[item]) ? jsonData1[item].length
					: 1;
			vCount = (tmp > vCount) ? tmp : vCount;
		}

		if (vCount > 1) {
			var jsonData2 = new Array();
			for (var i = 0; i < vCount; i++) {
				var jsonObj = {};
				for ( var item in jsonData1) {
					jsonObj[item] = jsonData1[item][i];
				}
				jsonData2.push(jsonObj);
			}
			return JSON.stringify(jsonData2);
		} else {
			return "[" + JSON.stringify(jsonData1) + "]";
		}
	};
})(jQuery);

function clickUpFileOther(obj) {
	var id = obj.id;
	var i = id.lastIndexOf("\_");
	var j = id.substring(i + 1, id.length);
	$('#otherimage_' + j).click();
}

//上传图片文件转化为base64在页面显示
function upImageFileOther(obj) {
	var id = obj.id;
	var i = id.lastIndexOf("\_");
	var j = id.substring(i + 1, id.length);
	if (obj == null || obj == "") {
		return;
	}
	var files = obj.files;
	var formData = new FormData();
	for (var i = 0; i < files.length; i++) {
		formData.append("imageFile", files[i]);//图片文件
		$.ajax({
					url : base + '/base64/uploadImage',
					type : 'post',
					data : formData,
					cache : false,
					processData : false,
					contentType : false,
					async : false,
					dataType : 'json',
					success : function(data) {
						upladFp(obj, "receptionother", "fp04");
			        	var num = obj.parentNode.parentNode.parentNode.id;
			        	$('#otherimagetd_'+j).empty();
			        	$('#otherimage_'+j).val("");
			        	var x=num.lastIndexOf("\_");
			   	     	var y=num.substring(x+1,num.length);
			        	if(y!=0){
			        		$('#otherimagetd_' + j)
							.append(
									'<img style="vertical-align:bottom;margin-left:0px; width: 100px;height: 73px" src="'+data+'" onclick="yl(this.src)"/><a style="color:red" href="#" id="othersc_'
											+ j
											+ '" onclick="deleteImageOther(this)">    删除</a>');
				        	}else{
				        		$('#otherimagetd_'+j).append('<img style="vertical-align:bottom;margin-left:0px; width: 100px;height: 73px" src="'+data+'" onclick="yl(this.src)"/><a style="color:red" href="#" id="othersc_'+j+'" onclick="deleteImageOther(this)">    删除</a>');
				        	}
						$('#checkTrue_'+j).html('<img style="vertical-align:bottom;margin-left:30px" src="${base}/resource-modality/${themenurl}/left/zp.png">');
			        	$('#checkRepet_'+j).html('<img style="vertical-align:bottom;margin-left:30px" src="${base}/resource-modality/${themenurl}/left/wcf.png">');
					}
				});
	}
}
//删除上传的图片
function deleteImageOther(obj) {
	
	var id =obj.id;
	 var i=id.lastIndexOf("\_");
     var j=id.substring(i+1,id.length);
     var num = obj.parentNode.parentNode.parentNode.id;
    	$('#otherimagetd_'+j).empty();
    	$('#otherimage_'+j).val("");
    	var x=num.lastIndexOf("\_");
     var y=num.substring(x+1,num.length);
   	  if(y!=0){
   		$('#otherimagetd_' + j)
		.append(
				'<a onclick="clickUpFileOther(this)" id="otherclick_'
						+ j
						+ '" style="font-weight: bold;" href="#">'
						+ '<img style="vertical-align:bottom;margin-left:30px" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png"'
						+' onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>');
     	}else{
     		$('#otherimagetd_'+j).append('<a onclick="clickUpFileOther(this)" id="otherclick_'+j+'" style="font-weight: bold;" href="#">'+
				'<img style="vertical-align:bottom;margin-left:30px" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)"'
				+' onMouseOut="mouseOut(this)"></a>');
     	}
	$('#otherfp_' + j).val(null);
	$('#checkTrue_'+j).html('<img style="vertical-align:bottom;" src="${base}/resource-modality/${themenurl}/left/dyz.png">');
	$('#checkRepet_'+j).html("");
}

function  deleteOther(item){
	
	var fromNum = item.parentNode.parentNode.parentNode.parentNode.parentNode.children.length;
	var id = item.parentNode.parentNode.parentNode.parentNode.id;
	$("#"+id).remove();
	var arr=id.split("_");
	for(var i=1;i<fromNum;i++){
		if('incity'==arr[0]){
			var tableId = $('#otherfapiao').find('tbody');
			var fapiao = tableId[i].children[0].children[0].children[0].id;
			$("#"+fapiao).html("发票"+(i+1));
		}
		
	}
	addAmonut2(item);
	return ;
}



</script>
