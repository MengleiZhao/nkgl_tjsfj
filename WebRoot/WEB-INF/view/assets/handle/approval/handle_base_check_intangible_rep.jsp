<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/lang/zh-cn/zh-cn.js"></script>
<form id="saveRepIntangibleHandle" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
<div class="window-div">
		<div class="window-left-div" data-options="region:'west',split:true" style="width:745px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<input type="hidden" name="fAssType" id="H_fAssType" value="${bean.fAssType}"/>
				<input type="hidden" name="fId" id="H_fId" value="${bean.fId}"/>
		    	<input type="hidden" name="fFlowStauts" id="H_fFlowStauts" value="${bean.fFlowStauts}"/>
		    	<input type="hidden" name="fHandleStauts" id="H_fHandleStauts" value="${bean.fHandleStauts}"/>
		    	<input type="hidden" name="fNextUserName" id="H_fNextUserName" value="${bean.fNextUserName}"/>
		    	<input type="hidden" name="fNextUserCode" id="H_fNextUserCode" value="${bean.fNextUserCode}"/>
		    	<input type="hidden" name="fNextCode" id="H_fNextCode" value="${bean.fNextCode}"/>
		    	<input type="hidden" name="fRecDept" id="H_fRecDept" value="${bean.fRecDept}"/>
		    	<input type="hidden" name="fRecDeptId" id="H_fRecDeptID" value="${bean.fRecDeptId}"/>
		    	<input type="hidden" name="fRecUserId" id="H_fReqUserid" value="${bean.fRecUserId}"/>
	    		
		    	<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
			    <input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
 <!-- 审批附件 --><input type="hidden" name="spjlFile" id="spjlFile" value=""/>
			    
				<div class="easyui-accordion" style="width:700px;margin-left: 20px;">
					<div  title="固定资产处置签报表" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<%-- <tr >
								<td style="width: 150px" colspan="5">固定资产处置签报名称</td>
							</tr>
							<tr >
								<td colspan="5">
									<input class="easyui-textbox" id="F_fHandleTitle" name="fHandleTitle"  style="width:670px;" value="${bean.fHandleTitle}"/>
								</td>
							</tr> --%>
	
							<tr >
								<td colspan="5">固定资产处置签报内容</td>
							</tr>
							<tr >
								<td colspan="5">
									<textarea id="fHandleText" style="width:670px;height:650px;"></textarea>
								</td>
							</tr>
						</table>
					</div>
					<div title="处置单详情" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<table class="ourtable" cellpadding="0" cellspacing="0">
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;处置单号</td>
								<td  colspan="4">
									<input id="H_fAssHandleCode" class="easyui-textbox" readonly="readonly" required="required" name="fAssHandleCode" data-options="prompt:'系统自动生成',validType:'length[1,30]'" value="${bean.fAssHandleCode}" style="width: 575px;color: #f7f7f7;"/> 
								</td>								
							</tr>
							<tr class="trbody">
								<td class="td1" style="width: 70px;"><span class="style1">*</span>处置形式</td>
								<td class="td2" >
									<input class="easyui-combobox" id="fHandleType" name="fHandleType.code" style="width: 220px;height: 30px;margin-left: 10px " data-options="url:'${base}/lookup/lookupsJson?parentCode=CZLX&selected=${bean.fHandleType.code}',method:'get',valueField:'code',textField:'text',editable:false">
								</td>
								<td class="td3"></td>
								<td class="td1" style="width: 70px;"><span class="style1">*</span>接收方</td>
								<td class="td2" >
									<input class="easyui-textbox" id="fAcceptName" name="fAcceptName" value="${bean.fAcceptName}" style="width: 220px;height: 30px;margin-left: 10px " >
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1" style="width: 70px;"><span class="style1">*</span>处置资产原值总额</td>
								<td class="td2" >
									<input class="easyui-numberbox" id="fOldAmount" name="fOldAmount" readonly="readonly" value="${bean.fOldAmount}" style="width: 220px;height: 30px;margin-left: 10px " >
								</td>
								<td class="td3"></td>
								<td class="td1" style="width: 70px;"><span class="style1">*</span>申请时间</td>
								<td class="td2" >
									<input class="easyui-datebox" id="fReqTime" name="fReqTime" readonly="readonly" value="${bean.fReqTime}" style="width: 220px;height: 30px;margin-left: 10px " >
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1" style="width: 70px;"><span class="style1">*</span>经办人</td>
								<td class="td2" >
									<input class="easyui-textbox" id="fRecUser" name="fRecUser" readonly="readonly" value="${bean.fRecUser}" style="width: 220px;height: 30px;margin-left: 10px " >
								</td>
								<td class="td3"></td>
								<td class="td1" style="width: 70px;"><span class="style1">*</span>党组会会议号</td>
								<td class="td2" >
									<input class="easyui-textbox" id="fMeetNumberId" name="fMeetNumber" <c:if test="${djhCode!=1 }"> readonly="readonly" </c:if> value="${bean.fMeetNumber}" style="width: 220px;height: 30px;margin-left: 10px " >
								</td>
							</tr>
							<tr class="trbody" id="trFJ01">
								<td class="td1">
									<span class="style1">*</span>
									资产处置定价文件
									<input type="file" multiple="multiple" id="f01" onchange="upladFileCGDJ(this,'zccz01','zcagl01')" hidden="hidden">
									<input type="text" id="files01" name="files01" hidden="hidden">
								</td>
								<td colspan="4" id="tdf01">
									<a onclick="$('#f01').click()" style="font-weight: bold;" href="#">
										<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="progid01" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
										<div id="progressNumber01" style="background:#3AF960;width:0px;height:10px"></div>
										文件上传中...&nbsp;&nbsp;<font id="percent01">0%</font> 
									</div>
									<c:forEach items="${handleList}" var="att">
										<c:if test="${att.serviceType=='zccz01' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<img src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a id="${att.id}" class="fileUrl01" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<c:if test="${bean.fOldAmount>=1000000}">
							<tr class="trbody" id="trFJ02">
								<td class="td1">
									<span class="style1">*</span>
									区财政局批复文件
									<input type="file" multiple="multiple" id="f02" onchange="upladFileCGDJ(this,'zccz02','zcagl01')" hidden="hidden">
									<input type="text" id="files02" name="files02" hidden="hidden">
								</td>
								
								<td colspan="4" id="tdf02">
									<a onclick="$('#f02').click()" style="font-weight: bold;" href="#">
										<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="progid02" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
										<div id="progressNumber02" style="background:#3AF960;width:0px;height:10px"></div>
										文件上传中...&nbsp;&nbsp;<font id="percent02">0%</font> 
									</div>
									<c:forEach items="${handleList}" var="att">
										<c:if test="${att.serviceType=='zccz02' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<img src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a id="${att.id}" class="fileUrl02" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							</c:if>
							<tr class="trbody" id="trFJ03">
								<td class="td1">
									<span class="style1">*</span>
									非税收入统一缴款书
									<input type="file" multiple="multiple" id="f03" onchange="upladFileCGDJ(this,'zccz03','zcagl01')" hidden="hidden">
									<input type="text" id="files03" name="files03" hidden="hidden">
								</td>
								
								<td colspan="4" id="tdf03">
									<a onclick="$('#f03').click()" style="font-weight: bold;" href="#">
										<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="progid03" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
										<div id="progressNumber03" style="background:#3AF960;width:0px;height:10px"></div>
										文件上传中...&nbsp;&nbsp;<font id="percent03">0%</font> 
									</div>
									<c:forEach items="${handleList}" var="att">
										<c:if test="${att.serviceType=='zccz03' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<img src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a id="${att.id}" class="fileUrl03" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr class="trbody" id="trFJ04">
								<td class="td1">
									<span class="style1">*</span>附件
									<input type="file" multiple="multiple" id="f04" onchange="upladFileCGDJ(this,'zccz04','zcagl01')" hidden="hidden">
									<input type="text" id="files04" name="files04" hidden="hidden">
								</td>
								
								<td colspan="4" id="tdf04">
									<a onclick="$('#f04').click()" style="font-weight: bold;" href="#">
										<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
									</a>
									<div id="progid04" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none">
										<div id="progressNumber04" style="background:#3AF960;width:0px;height:10px"></div>
										文件上传中...&nbsp;&nbsp;<font id="percent04">0%</font> 
									</div>
									<c:forEach items="${handleList}" var="att">
										<c:if test="${att.serviceType=='zccz04' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<img src="${base}/resource-modality/${themenurl}/sccg.png">
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a id="${att.id}" class="fileUrl04" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
						</table>	
					</div>
					<div title="处置清单表" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<%@ include file="handle_intangible_check_plan.jsp" %>
					</div>
					<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<jsp:include page="/WEB-INF/view/check_history.jsp" />												
					</div>
				</div>			
			</div>
			
			<div class="window-left-bottom-div">
					<a href="javascript:void(0)" onclick="saveRepIntangibleHandle()">
						<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="closeWindow()">
						<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				&nbsp;&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<div class="window-right-div" data-options="region:'east',split:true">
			<jsp:include page="/WEB-INF/view/check_system.jsp" />
		</div>
</div>
</form>
	<script type="text/javascript">
	var djhCode = '${djhCode}';
	function filesIdYzCz(id){
		var s="";
		$(".fileUrl"+id).each(function(){
			s=s+$(this).attr("id")+",";
		});
		$("#files"+id).val(s);
		if(s==''){
			return true;
		}else{
			return false;
		}
	}
	function saveRepIntangibleHandle(){
		var fOldAmount = '${bean.fOldAmount}';
		if(fOldAmount<1000000){
			var files01 = filesIdYzCz('01');
			var files03 = filesIdYzCz('03');
			if(files01){
				alert("请上传资产处置定价文件!");
				return false;
			}
			if(files03){
				alert("请上传非税收入统一缴款书!");
				return false;
			}
		}else{
			var files01 = filesIdYzCz('01');
			var files02 = filesIdYzCz('02');
			var files03 = filesIdYzCz('03');
			if(files01){
				alert("请上传资产处置定价文件!");
				return false;
			}
			if(files02){
				alert("请上传区财政局批复文件!");
				return false;
			}
			if(files03){
				alert("请上传非税收入统一缴款书!");
				return false;
			}
		}
		$('#saveRepIntangibleHandle').form('submit', {
				onSubmit: function(){ 
					flag=$(this).form('enableValidation').form('validate');
					if(flag){
						$.messager.progress();
					}
					return flag;
				}, 
				url:'${base}/Handle/replenishHandleFile',
				success:function(data){
					if(flag){
						$.messager.progress('close');
					}
					data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示', data.info, 'info');
						closeWindow();
						$('#saveRepIntangibleHandle').form('clear');
						$("#handle_replenish_dg").datagrid('reload'); 
						$("#indexdb").datagrid('reload'); 
					}else{
						$.messager.alert('系统提示', data.info, 'error');
					}
				} 
			});	
	}
	</script>
	
	<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ues = UE.getEditor('fHandleText');
  //对编辑器的操作最好在编辑器ready之后再做
  ues.ready(function() {
	  var fHandleTexts ='${beanCopy.fHandleText}';
      //设置编辑器的内容
      if(fHandleText==''){
     	 ues.setContent('');
      }else{
      	ues.setContent(fHandleTexts);
      }
      ues.setDisabled();
  });
</script>
</body>