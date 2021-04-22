<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<table id="dg" class="easyui-datagrid" style="width:660px;height:auto"
data-options="
singleSelect: true,
toolbar: '#tb',
rownumbers : true,
 <c:if test="${empty bean.fplId}">
url: '',
</c:if>
<c:if test="${!empty bean.fplId}">
url: '${base}/cgconfplangl/mingxi?id=${bean.fplId}',
</c:if>
method: 'post',
onClickRow: onClickRow
">

</table>
<div id="tb" style="height:30px">
	<a style="color: red;">配置计划总额：</a><input style="width: 100px;" id="totalPrice" name="ffinalPrice"  class="easyui-numberbox"  readonly="readonly" data-options="precision:2,icons: [{iconCls:'icon-yuan'}]"/>
	(填写配置计划清单后自动计算)
	<a href="javascript:void(0)" onclick="removeit()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/scyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
	<a style="float: right;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0)" onclick="append()" style="float: right;"><img src="${base}/resource-modality/${themenurl}/button/tjyh1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"></a>
</div>
<input type="hidden" id="mingxiJson" name="mingxi"/>
<script type="text/javascript">
//特殊日期格式化！！！！！
function formatterEmptyTime(val) {
	//alert(val)
	var t, y, m, d, h, i, s;
	if (val == "") {
		return "";
	}
	t = new Date(val)
	y = t.getFullYear();
	m = t.getMonth() + 1;
	d = t.getDate();
	h = t.getHours();
	i = t.getMinutes();
	s = t.getSeconds();
	// 可根据需要在这里定义时间格式  
	return y + '-' + (m < 10 ? '0' + m : m) + '-'
			+ (d < 10 ? '0' + d : d);
}	
</script>


