<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
	<script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${base}/resource-modality/ueditor/lang/zh-cn/zh-cn.js"></script>
<form id="handleAddEdit" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
<div class="window-div">
		<div class="window-left-div" data-options="region:'west',split:true" style="width:745px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;">
			<div class="win-left-top-div">
				<input type="hidden" name="fAssType" id="H_fAssType" value="${bean.fAssType}"/>
				<input type="hidden" name="fId" id="H_fId" value="${bean.fId}"/>
		    	<input type="hidden" name="fFlowStauts" id="H_fFlowStauts" value="${bean.fFlowStauts}"/>
		    	<input type="hidden" name="fHandleStauts" id="H_fHandleStauts" value="${bean.fHandleStauts}"/>
		    	<input type="hidden" name="fHandleText" id="H_fHandleText"/>
		    	<input type="hidden" name="fNextUserName" id="H_fNextUserName" value="${bean.fNextUserName}"/>
		    	<input type="hidden" name="fNextUserCode" id="H_fNextUserCode" value="${bean.fNextUserCode}"/>
		    	<input type="hidden" name="fNextCode" id="H_fNextCode" value="${bean.fNextCode}"/>
		    	<input type="hidden" name="fRecDept" id="H_fRecDept" value="${bean.fRecDept}"/>
		    	<input type="hidden" name="fRecDeptId" id="H_fRecDeptID" value="${bean.fRecDeptId}"/>
		    	<input type="hidden" name="fRecUserId" id="H_fReqUserid" value="${bean.fRecUserId}"/>
	    		
				<div class="easyui-accordion" style="width:700px;margin-left: 20px;">
					<div  title="固定资产处置签报表" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<tr>
								<td style="width: 150px" colspan="5">固定资产处置签报名称</td>
							</tr>
							<tr>
								<td colspan="5">
									<input class="easyui-textbox" id="F_fHandleTitle" name="fHandleTitle"  style="width:670px;" value="${bean.fHandleTitle}"/>
								</td>
							</tr>
	
							<tr>
								<td colspan="5">固定资产处置签报内容</td>
							</tr>
							<tr>
								<td colspan="5">
									<textarea id="fHandleText" style="width:670px;height:200px;"></textarea>
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
									<input class="easyui-textbox" id="fMeetNumber" name="fMeetNumber" readonly="readonly" value="${bean.fMeetNumber}" style="width: 220px;height: 30px;margin-left: 10px " >
								</td>
							</tr>
						</table>	
					</div>
					<div title="处置清单表" data-options="collapsed:false,collapsible:false" style="overflow:auto;margin-top:10px;">
						<%@ include file="handle_fixed_add_plan.jsp" %>
					</div>
					<c:if test="${checkinfo==1}">
						<div title="审批记录" data-options="" style="overflow-x:hidden;margin-top: 10px;">
							<jsp:include page="../../check_history.jsp" />												
						</div>
					</c:if>
				</div>			
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="handleAddEdit(0);">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="handleAddEdit(1)">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="window-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
</div>
</form>
	<script type="text/javascript">
		function handleAddEdit(sts){
			var fHandleTitle = $('#F_fHandleTitle').textbox('getValue');
			if(fHandleTitle==''){
				alert('请输入固定资产处置签报名称！');
				return false;
			}
			var fHandleType = $('#fHandleType').combobox('getValue');
			if(fHandleType==''){
				alert('请选择处置形式！');
				return false;
			}
			var fAcceptName = $('#fAcceptName').textbox('getValue');
			if(fAcceptName==''){
				alert('请填写接收方信息！');
				return false;
			}
			$("#H_fFlowStauts").val(sts); 
			var html = ues.getContent();
			$("#H_fHandleText").val(html);
			var getPlanHandle = getPlan1();
			/* if(getPlanHandle==false){
				alert('请填写完整价值信息！');
				return false;
			} */
			if(getPlanHandle==''||getPlanHandle==null||getPlanHandle=='[]'){
				alert('请选择需要处置的资产！');
				return false;
			}
			$('#handleAddEdit').form('submit', {
				onSubmit: function(param){ 
					param.planJson=getPlanHandle;
   					flag=$(this).form('enableValidation').form('validate');
   					if(flag){
   						$.messager.progress();
   					}
   					return flag;
   				}, 
   				url:base+'/Handle/applicationSave', 
   				success:function(data){
   					if(flag){
   						$.messager.progress('close');
   					}
   					data=eval("("+data+")");
   					if(data.success){
   						$.messager.alert('系统提示', data.info, 'info');
   						closeWindow();
   						$('#handleAddEdit').form('clear');
   						$("#handle_low_dg").datagrid('reload'); 
   						$("#handle_fixed_dg").datagrid('reload');
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
	  var fHandleTexts ='${bean.fHandleText}';
      //设置编辑器的内容
      if(fHandleText==''){
     	 ues.setContent('');
      }else{
      	ues.setContent(fHandleTexts);
      }
  });
</script>
</body>