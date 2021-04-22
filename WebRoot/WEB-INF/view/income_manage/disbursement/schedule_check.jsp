<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<form id="schedule_check_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="window-div">
		<div class="window-left-div" style="width:765px;height: 491px;border: 1px solid #D9E3E7;margin-top: 20px;"">
			<div class="win-left-top-div">
				<!-- 隐藏域 --> 
				<!-- 主键ID --><input type="hidden" name="sId" value="${bean.sId}" />
				<!-- 申请单流水号 --><input type="hidden" name="scheduleCode" value="${bean.scheduleCode}" />
				<!-- 审批状态 --><input type="hidden" name="flowStauts" value="${bean.flowStauts}" id="flowStauts" />
				<!-- 申请状态 --><input type="hidden" name="stauts" value="${bean.stauts}" id="stauts" />
				<!-- 下节点节点编码 --><input type="hidden" name="nCode" value="${bean.nCode}" />
				<!-- 申请总额  --><input type="hidden" id="amount" name="amount" value="${bean.amount}"/>
				<!-- 流程id  --><input type="hidden" id="flowId"  value="${fpId}"/>
				<!--历史审批节点  --><input type="hidden" id="historyNodes"  value="${historyNodes}"/>
				<!-- 下一级审批节点  --><input type="hidden" id="nextKey"  value="${bean.nCode}"/>
				
				<input type="hidden" name="fcheckResult" id="fcheckResult" value=""/>
				<input type="hidden" name="fcheckRemake" id="fcheckRemake" value=""/>
<!-- 审批附件 --> <input type="hidden" name="spjlFile" id="spjlFile" value=""/>
				<!-- 基本信息 -->
				<div id="sqsqjbxx" style="overflow:auto;margin-top: 0px;">
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
				<div title="基本信息" data-options="collapsed:false,collapsible:false"style="overflow:auto;">
					<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 3px;">
						<tr class="trbody">
							<td class="td1" style="width: 80px;"> 申报部门:</td>
							<td style="width: 70px">
								<a id="deptName_show">${bean.deptName}</a>	
							</td>
							<td class="td1" style="width: 100px;"> 选择申报年度:</td>
							<td style="width: 70px">
								<input class="easyui-combobox" id="applyYear_show" readonly="readonly"
								 value="${bean.applyYear}" style="width: 105px;height: 30px;margin-left: 10px" 
								 data-options="valueField:'id',textField:'text',url:'${base}/schedule/getApplyYear?selected=${bean.applyYear}',editable:false"
								  />
							</td>
							<td class="td1" style="width: 70px;"> 申报人:</td>
							<td style="width: 70px">
								<a id="userName_show">${bean.userName}</a>
							</td>
							<td class="td1" style="width: 80px;"> 申报日期:</td>
							<td style="width: 70px">
								<c:set var="now" value="<%=new java.util.Date()%>" />
								<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</table>
				</div>				
				</div>
				</div>
				<!-- 费用明细 -->
				<div title="费用明细" data-options="collapsible:false" style="overflow:auto;padding:10px;">
					<div style="overflow:auto;margin-top: 0px">
						<jsp:include page="schedule_mingxi_detail.jsp" />
					</div>
				</div>
				<c:if test="${openType!='add' }">
				<!-- 审批记录 -->
				<div class="easyui-accordion" style="margin-left: 20px;margin-right: 20px">
					<div title="审批记录" data-options="collapsible:false" style="overflow:auto;padding:10px;">
						<jsp:include page="../../check_history.jsp" />
					</div>
				</div>
				</c:if>
			</div>
			
			<div class="window-left-bottom-div">
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','1')">
					<img src="${base}/resource-modality/${themenurl}/button/tg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="openCheckWin('审批意见','0')">
					<img src="${base}/resource-modality/${themenurl}/button/btg1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=收入管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		<div class="window-right-div" style="width:254px;height: auto;padding-bottom: 20px;">
			<jsp:include page="../../check_system.jsp" />
		</div>
	</div>
</form>
<script type="text/javascript">

//审批
function check(result) {
		$('#schedule_check_form').form('submit', {
			onSubmit : function() {
					$.messager.progress();
			},
			url : base + '/schedule/checkResult',
			success : function(data) {
					$.messager.progress('close');
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					$('#scheduleCheckTab').datagrid('reload');
					$('#indexdb').datagrid('reload');
					closeWindow();
				} else {
					$.messager.alert('系统提示', data.info, 'error');
				}
			}
		});
}
//显示详细信息手风琴页面
$(document).ready(function() {
});

function onLoadSuccessScheduleDetail(data){
	$('#schedule-detail-dg').datagrid('insertRow',{
		index: 0,	// index start with 0
		row: {
			sId: '',
			fProId: '',
			deptId: '',
			fProCode: '',
			pageOrder: '合计',
			fProName: '合计',
			deptName: '合计',
			pfAmount: ''
		}
	});
	var amount = 0;
	var firstAmounts = 0;
	var twoAmounts = 0;
	var threeAmounts = 0;
	var fourAmounts = 0;
	for (var i = 1; i < data.rows.length; i++) {
		var pfAmount = isNaN(parseFloat(data.rows[i].pfAmount))?0:parseFloat(data.rows[i].pfAmount);
		var firstAmount = isNaN(parseFloat(data.rows[i].firstAmount))?0:parseFloat(data.rows[i].firstAmount);
		var twoAmount = isNaN(parseFloat(data.rows[i].twoAmount))?0:parseFloat(data.rows[i].twoAmount);
		var threeAmount = isNaN(parseFloat(data.rows[i].threeAmount))?0:parseFloat(data.rows[i].threeAmount);
		var fourAmount = isNaN(parseFloat(data.rows[i].fourAmount))?0:parseFloat(data.rows[i].fourAmount);
		amount +=pfAmount;
		firstAmounts +=firstAmount;
		twoAmounts +=twoAmount;
		threeAmounts +=threeAmount;
		fourAmounts +=fourAmount;
	}

	$('#schedule-detail-dg').datagrid('updateRow',{
		index: 0,
		row: {
			pfAmount: amount,
			firstAmount: firstAmounts,
			twoAmount: twoAmounts,
			threeAmount: threeAmounts,
			fourAmount: fourAmounts
		}
	});
   $('#schedule-detail-dg').datagrid('mergeCells', {
	       index: 0,
	       field: 'pageOrder',//合并后单元格对应的属性值
	       colspan: 3
	    });
   }
</script>