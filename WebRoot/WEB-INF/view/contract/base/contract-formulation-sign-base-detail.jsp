<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<table cellpadding="0" cellspacing="0"  class="ourtable">
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;法定代表人</td>
		<td>
			<input class="easyui-textbox" id="f_fOfficialUser" name="fOfficialUser" readonly="readonly"  data-options="validType:'length[1,50]'" style="width: 200px" value="${signInfo.fOfficialUser}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;是否法定代表人签字</td>
		<td>
			<input type="radio" id="f_fIsOfficialUserYes" name="fIsOfficialUser" onclick="fIsOfficialUserYes()" disabled="disabled" readonly="readonly" name="fIsOfficialUser"<c:if test="${signInfo.fIsOfficialUser==1}">checked="checked"</c:if> value="1" >是
			<input type="radio" id="f_fIsOfficialUserNo" name="fIsOfficialUser" onclick="fIsOfficialUserNo()" disabled="disabled" readonly="readonly" name="fIsOfficialUser"<c:if test="${signInfo.fIsOfficialUser==0}">checked="checked"</c:if> value="0"  >否
		</td>
	</tr>
	<tr class="trbody fIsOfficialUser">
		<td class="td1"><span class="style1">*</span>&nbsp;授权代表人</td>
		<td>
			<input class="easyui-textbox" id="f_fLegalUser" name="fLegalUser" readonly="readonly"  data-options="validType:'length[1,25]'" style="width: 200px" value="${signInfo.fLegalUser}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1">&nbsp;<span class="style1">*</span>&nbsp;授权委托书
			<input type="file" multiple="multiple" id="fsqwts" onchange="upladFilesqwts(this,'sqwts','htgl01')" hidden="hidden">
			<input type="text" id="filessqwts" name="filessqwts" hidden="hidden"></td>
		<td>
			<c:forEach items="${signInfoAttaList}" var="att">
				<c:if test="${att.serviceType=='sqwts' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%-- <img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="sqwtsfileUrl" href="#" style="color:red" onclick="sqwtsdeleteAttac(this)">删除</a> --%>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;对方联系人</td>
		<td>
			<input class="easyui-textbox" id="f_fConcUser" name="fConcUser" readonly="readonly"  data-options="validType:'length[1,25]'" style="width: 200px" value="${signInfo.fConcUser}"/>
		</td>
		<td class="td4">&nbsp;</td>
		<td class="td1"><span class="style1">*</span>&nbsp;联系电话</td>
		<td>
			<input class="easyui-textbox" id="f_fConcTel" name="fConcTel" readonly="readonly"  data-options="validType:'length[1,13]',validType:'mobileAndTel'" style="width: 200px" value="${signInfo.fConcTel}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;银行账户</td>
		<td colspan="4">
			<input class="easyui-textbox" id="f_fCardNo" name="fCardNo" readonly="readonly" style="width: 555px" value="${signInfo.fCardNo}"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>&nbsp;开户银行</td>
		<td colspan="4">
			<input class="easyui-textbox" id="f_fBankName" name="fBankName" readonly="readonly"  data-options="validType:'length[1,25]'" style="width: 555px" value="${signInfo.fBankName}"/>
		</td>
	</tr>	
	<%-- <tr class="trbody fIsOfficialUser">
		<td class="td1">
			&nbsp;&nbsp;授权委托书
			<input type="file" multiple="multiple" id="fsqwts" onchange="upladFilesqwts(this,'sqwts','htgl01')" hidden="hidden">
			<input type="text" id="filessqwts" name="filessqwts" hidden="hidden">
		</td>
		<td colspan="4" id="sqwtstdf">
			<a onclick="$('#fsqwts').click()" style="font-weight: bold;" href="#">
				<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
			</a>
			<div id="sqwtsprogid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
				<div id="sqwtsprogressNumber" style="background:#3AF960;width:0px;height:10px" >
				</div>文件上传中...&nbsp;&nbsp;<font id="sqwtspercent">0%</font> 
			</div>
			<c:forEach items="${signInfoAttaList}" var="att">
				<c:if test="${att.serviceType=='sqwts' }">
					<div style="margin-top: 10px;">
						<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/sccg.png">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="${att.id}" class="sqwtsfileUrl" href="#" style="color:red" onclick="sqwtsdeleteAttac(this)">删除</a>
					</div>
				</c:if>
			</c:forEach>
		</td>
	</tr> --%>
</table>
<script type="text/javascript">
fIsOfficialUser();
function fIsOfficialUser(){
	var cxjk = $('input[name="fIsOfficialUser"]:checked').val();
	if(cxjk==0){
		$('.fIsOfficialUser').show();
	}else{
		$('.fIsOfficialUser').hide();
	}
}
   //pathNum-路径编码
   function upladFilesqwts(obj,serviceType,pathNum,mark) {
     var files = obj.files;
     var size=files[0].size;
     if(size==0){
   	  alert("不允许上传空文件");
   	  return false;
     }
     
     var formData = new FormData();
     //判断有没有选中附件信息
     if(files==null || files=="null"|| files.length==0){
   	  alert("请选择附件上传");
   	  return false;
     }
     //把所有的附件都存入变量，准备传给后台
     for(var i=0; i< files.length; i++){
     	 formData.append("attFiles",files[i]);
     } 
     //初始化进度条为0
     $("#czbmyjpercent").html(0 + '%');
     $("#czbmyjprogressNumber").css("width",""+0+"px");
     
     $('.win-left-bottom-div').hide();
     
     // 接收上传文件的后台地址 
     var url = base + "/attachment/uploadAtt?serviceType="+serviceType+"&pathNum="+pathNum;          
     //1.创建XMLHttpRequest组建    
     xmlHttpRequest = createXmlHttpRequest();  
     //post请求
     xmlHttpRequest.open("post", url, true);
     //请求成功回调
     xmlHttpRequest.onload = function (data) {
   	  callbacksqwts(mark);
   	  var resObj = JSON.parse(data.currentTarget.response);
   	  if(resObj.success && 'zdsy'==serviceType){
   		  //如果是制度索引文件上传
   		  var fileName = resObj.info.split("@")[1];
   		  var fileId = resObj.info.split("@")[0];
   		  fileName = fileName.replace('.pdf','');
   		  $('#cheter_add_fileName').textbox('setValue',fileName);
   		  $('#F_attachmentId').val(fileId);
   		  $('#systemcenter_add_uploadbtn').hide();
   	  }
     };
     //调用线程监听上传进度
     xmlHttpRequest.upload.addEventListener("progress", progressFunction1, false);
     //把文件数据发送出去
     xmlHttpRequest.send(formData);
     //清空value （作用：删除文件后再上传同一个文件，onchange方法不调用）
     event.target.value=null;
   }
//监听进度线程，生成进度条
   function progressFunction1(evt) {
     if (evt.lengthComputable) {
   	  //evt.loaded：文件上传的大小   evt.total：文件总的大小                    
         var percentComplete = Math.round((evt.loaded) * 100 / evt.total);    
         //加载进度条，同时显示信息          
         $("#sqwtsprogid").show();
         $("#sqwtspercent").html(percentComplete + '%');
         //percentComplete*2  这个2是根据总像素大小来改变的，如果是300px就 *3
         $("#sqwtsprogressNumber").css("width",""+percentComplete*3+"px");   
     }
   } 
   //回调函数
   function callbacksqwts(mark) {
       //5。接收响应数据
       //判断对象的状态是交互完成
       if (xmlHttpRequest.readyState == 4) {
           //判断http的交互是否成功
           if (xmlHttpRequest.status == 200) {
               //获取服务器段输出的纯文本数据
               var responseText = xmlHttpRequest.responseText;
               //文本数据转换成json
               var jsonResponse = JSON.parse(responseText);
   	    	 if(jsonResponse.success==true){
   	    		    //获取controller 返回的对象信息
   			        var datainfo = jsonResponse.info;
   	    		    //如果上传了多个文件，返回的string就有多个，使用逗号分割，现在截取
   	    		 	var infoArray = datainfo.split(',');	
   	    		 	for(var i=0; i< infoArray.length; i++){
   	    		 		var info = infoArray[i];
   	    		 		var inf = info.split('@');
   	    		 		var id = inf[0];//附件id
   	    		 		var name = inf[1];//附件名称
   				        $('#sqwtstdf').append(
   			    			"<div style='margin-top: 5px;'>"+
   			    				"<a href='"+base+"/attachment/download/"+id+"' style='color: #666666;font-weight: bold;'>"+name+"</a>"+
   			    				"&nbsp;&nbsp;&nbsp;&nbsp;"+
   			    				"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
   			    				"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
   			    				"&nbsp;&nbsp;&nbsp;&nbsp;<a id='"+id+"' class='sqwtsfileUrl' href='#' style='color:red' onclick='sqwtsdeleteAttac(this,\""+mark+"\")'>删除</a><div>"
   			    		);
   	    		 		
   	    		 	}
   	    		 	//放入附件id
   	    			var s="";
   	    			$(".sqwtsfileUrl").each(function(){
   	    				s=s+$(this).attr("id")+",";
   	    			});
   	    			$("#sqwtsfiles").val(s);
   	    			$("#sqwtsprogid").hide();
   	    	} else {
   	    		alert(jsonResponse.info);
   	    		$('#sqwtstdf').append(
       				"<div style='margin-top: 5px;'>"+
       					"<a href='#' style='color: #666666;font-weight: bold;'>"+filename[filename.length-1]+"</a>"+
       					"&nbsp;&nbsp;&nbsp;&nbsp;"+
       					"<img style='display:none;vertical-align:middle' src='"+base+"/resource-modality/img/sccg.png'>"+
       					"<img style='vertical-align:middle' src='"+base+"/resource-modality/img/scsb.png'>"+
       					"&nbsp;&nbsp;&nbsp;&nbsp;<a id='fail' class='sqwtsfileUrl' href='#' style='color:red' onclick='sqwtsdeleteAttac(this)'>删除</a><div>"
       			);
   	    		$("#sqwtsprogid").hide();
   	    	}
           } else {
               alert("上传失败");
           }
           $('.win-left-bottom-div').show();
       }
   }
   //附件删除
   function sqwtsdeleteAttac(obj,mark){
   	if(confirm("确认删除吗？")){
   		$.ajax({ 
   			type: 'POST', 
   			url: base+'/attachment/delete/'+obj.id,
   			dataType: 'json',  
   			success: function(data){ 
   				if(data.success){
   					$.messager.alert('系统提示',data.info,'info');
   					$(obj).parent().remove();
   					if(mark=='zdsy'){
   						//如果是制度索引新增
   						$('#systemcenter_add_uploadbtn').show();
   					}
   				}else{
   					$.messager.alert('系统提示',data.info,'error');
   				}
   			} 
   		}); 
   	}
   }
   
	function fIsOfficialUserYes(){
		var cxjk = $('input[name="fIsOfficialUser"]:checked').val();
		$('.fIsOfficialUser').hide();
	}
	function fIsOfficialUserNo(){
		var cxjk = $('input[name="fIsOfficialUser"]:checked').val();
		$('.fIsOfficialUser').show();
		$('#f_fLegalUser').attr('required',true);
	}
     
</script>