<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>


<body >
<div  class="list-div">
	<div style="height: 10px;background-color:#f0f5f7 "></div>
	<div class="list-top" >
		<table class="topTable" style="width: 100%;font-size: 12px;height: 40px;"  cellpadding="0" cellspacing="0">
			<tr>
				<td class="top-table-search" class="queryth">
				
					&nbsp;&nbsp;查询年份&nbsp;
					<select id="statistics_query_years" class="easyui-combobox" style="width: 150px;height:25px;">
						<option>${currentYear-1 }</option>
						<option>${currentYear }</option>
						<option>${currentYear+1 }</option>
					</select>
					&nbsp;&nbsp;查询月份&nbsp;
					<select id="statistics_query_month" class="easyui-combobox" style="width: 150px;height:25px;">
						<option value="1">1月</option>
						<option value="2">2月</option>
						<option value="3">3月</option>
						<option value="4">4月</option>
						<option value="5">5月</option>
						<option value="6">6月</option>
						<option value="7">7月</option>
						<option value="8">8月</option>
						<option value="9">9月</option>
						<option value="10">10月</option>
						<option value="11">11月</option>
						<option value="12">12月</option>
					</select>
					
					&nbsp;&nbsp;
					<a href="#" onclick="queryStatistics();">
						<img src="${base}/resource-modality/${themenurl}/button/detail1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
					&nbsp;&nbsp;
					<a href="#" onclick="clearStatistics();">
						<img src="${base}/resource-modality/${themenurl}/button/qingchu1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
					</a>
				</td>
				
			</tr>
		</table>   
	</div>

	<div id="statisticsTab">
		<jsp:include page="reimb_statistics_list_tab.jsp" />
	</div>




<script type="text/javascript">
//查询
function queryStatistics() {
	//$.messager.progress();
	var years = $("#statistics_query_years").combobox('getValue').trim();
	var month = $("#statistics_query_month").combobox('getValue').trim();
	$('#statisticsTab').load('${base}/reimburse/statisticsJson?years='+years+'&month='+month);
	//alert(1);
	/* $.ajax({
		type : 'POST',
		url : '${base}/reimburse/statisticsJson',
		dataType : 'json',
		async:false,
		data:{'years':years,'month':month},
		success : function(data) {
			if (data.success) {
				$.messager.progress('close');
			} else {
				
			}
		}
	});
	 */
}
//清除查询条件
function clearStatistics() {
	$("#statistics_query_years").combobox('setValue','');
	$("#statistics_query_month").combobox('setValue','');
}

</script>
</body>
</html>

