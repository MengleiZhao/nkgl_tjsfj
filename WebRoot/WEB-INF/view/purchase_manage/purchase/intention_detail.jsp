.<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="window-div">
<form id="purchase_intention_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
		<div class="window-left-div" style="width:760px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" style="width:715px;margin-left: 20px">
					<div  title="意向公开申请" id="cgxxdiv" data-options="collapsed:false,collapsible:false"
						style="overflow:auto;margin-top: 10px;">
						<table cellpadding="0" cellspacing="0" class="ourtable">
							<!-- 表单样式参考 -->
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;申请处室</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" readonly="readonly" required="required" name="fDeptName" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.fDeptName}"/>
								</td>
								<td class="td4">
									<!-- 隐藏域 -->
									<input type="hidden" name="fId"  value="${bean.fId}"/>
									<input type="hidden" name="nCode" value="${bean.nCode}"/>
									<input type="hidden" name="flowStauts" id="flowStauts" value="${bean.flowStauts}"/><!--审批状态  -->
									<input type="hidden" name="stauts" value="${bean.stauts}"/><!--删除状态  -->
									<input type="hidden" name="publicStatus" value="${bean.publicStatus}"/><!--公开状态  -->
									<input type="hidden" name="fUser" value="${bean.fUser}"/><!-- 申请人ID -->
									<input type="hidden" name="fDeptId" value="${bean.fDeptId}"/><!-- 申请处室ID -->
									<input type="hidden" id="nextKey" value="${bean.nCode}"/><!-- 下一级审批节点  -->
									<input type="hidden" id="historyNodes" value="${historyNodes}"/><!--历史审批节点  -->
									<input type="hidden" name="fUserName" value="${bean.fUserName}"/>
									<input type="hidden" name="fIntentionCode" value="${bean.fIntentionCode}"/>
								</td>
								<td class="td1"><span class="style1">*</span>&nbsp;申请时间</td>
								<td class="td2">
									<input class="easyui-datebox" class="dfinput" name="fReqTime" readonly="readonly" required="required" data-options="validType:'length[1,20]'" style="width: 200px;" value="${bean.fReqTime}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;采购项目名称</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" required="required" name="fPurchaseName" readonly="readonly" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.fPurchaseName}"/>
								</td>
								<td class="td4"></td>
								<td class="td1"><span class="style1">*</span>&nbsp;预算金额</td>
								<td class="td2">
									<input class="easyui-textbox" type="text" required="required" id="f_amount" name="amount" readonly="readonly" data-options="icons: [{iconCls:'icon-yuan'}],validType:'length[1,25]'" style="width: 200px" value="${bean.amount}"/>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;预计采购时间</td>
								<td colspan="4">
									<input id="purchaseTime" name="fPurchaseTime" readonly="readonly" type="text" class="easyui-datebox" style="width: 200px" value="${bean.fPurchaseTime}"/>
								</td>
							</tr>
							<tr style="height: 70px;">
								<td class="td1"><span class="style1">*</span>&nbsp;采购需求概况</td>
								<td colspan="4">
									<textarea class="textbox-text" id="fPurchaseDemand" name="fPurchaseDemand" readonly="readonly" oninput="textareaNum(this,'textareaNum1')" autocomplete="off"  style="width:555px;height:70px">${bean.fPurchaseDemand}</textarea>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1"><span class="style1">*</span>&nbsp;是否专门面向中小企业</td>
								<td class="td2" colspan="4">
         							<input type="radio" name="isForSmes" <c:if test="${bean.isForSmes=='0'}">checked="checked"</c:if> value="0" disabled>是</input>
         							<input type="radio" name="isForSmes" <c:if test="${bean.isForSmes!='0'}">checked="checked"</c:if> value="1" disabled>否</input>
								</td>
							</tr>
							<tr style="height: 70px;">
								<td class="td1">备注</td>
								<td colspan="4">
									<textarea class="textbox-text" id="remark" name="remark" readonly="readonly" oninput="textareaNum(this,'textareaNum1')" autocomplete="off"  style="width:555px;height:70px">${bean.remark}</textarea>
								</td>
							</tr>
							<tr class="trbody">
								<td class="td1">
									&nbsp;&nbsp;附件
									<input type="file" multiple="multiple" id="purchaseFile" onchange="upladFile(this,'cgyx','cggl08')" hidden="hidden">
									<input type="text" id="files" name="files"  hidden="hidden">
								</td>
								<td colspan="4" id="tdf">
									<a onclick="$('#purchaseFile').click()" style="font-weight: bold;" href="#">
										<%-- <img style="vertical-align:bottom" src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">  --%>
									</a>
									<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
								        <div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
								        </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
						    	    </div>
									<c:forEach items="${attac}" var="att">
										<c:if test="${att.serviceType=='cgyx' }">
											<div style="margin-top: 10px;">
												<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
												&nbsp;&nbsp;&nbsp;&nbsp;
											</div>
										</c:if>
									</c:forEach>
								</td>
							</tr>
						</table>
						
							
					</div>
					<div title="审批记录" data-options="collapsed:false,collapsible:false" style="overflow-x:hidden;margin-top: 10px;">
						<jsp:include page="../../check_history.jsp" />												
					</div> 
				</div>
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="window-right-div" id="check_system_div" style="margin: 20px 20px 30px 0px;" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
</form>
</div>
<script type="text/javascript">
	
	$(document).ready(function() {
		//初始化时间控件
		intiMonthBox('purchaseTime');
		//预算金额
		var f_amount = $("#f_amount").val();
		if(f_amount !=""){
			$('#f_amount').val(fomatMoney(f_amount,2));
		}
	});
	
	function intiMonthBox(id){
		var db = $('#'+id);
        db.datebox({
               onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                   span.trigger('click'); //触发click事件弹出月份层
                   if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                       tds = p.find('div.calendar-menu-month-inner td');
                       tds.click(function (e) {
                           e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                           var year = /\d{4}/.exec(span.html())[0]//得到年份
                           , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                           db.datebox('hidePanel')//隐藏日期对象
                           .datebox('setValue', year + '-' + month); //设置日期的值
                       });
                   }, 0);
                   yearIpt.unbind();//解绑年份输入框中任何事件
               },
               parser: function (s) {
                   if (!s) return new Date();
                   var arr = s.split('-');
                   return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
               },
               formatter: function (d) {
                   return d.getFullYear() + '-' + (d.getMonth() + 1);
               }
           });
           var p = db.datebox('panel'), //日期选择对象
               tds = false, //日期选择对象中月份
               yearIpt = p.find('input.calendar-menu-year'),//年份输入框
               span = p.find('span.calendar-text'); //显示月份层的触发控件
	}
	
	 
</script>
</body>