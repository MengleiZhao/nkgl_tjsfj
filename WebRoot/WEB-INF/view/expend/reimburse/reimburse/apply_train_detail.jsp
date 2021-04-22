<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
</style>


<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: 0px;">
	<tr class="trbody">
		<td class="td1" style=""><span class="style1">*</span> 培训名称</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 590px; height: 30px;"  readonly="readonly"
			value="${trainingBean.trainingName}" required="required" data-options="validType:'length[1,50]'"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1" style=""><span class="style1">*</span> 培训目的</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 590px; height: 30px;"  readonly="readonly"
			value="${trainingBean.trContent}" required="required" data-options="validType:'length[1,50]'"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 报到日期</td>
		<td class="td2">
			<input  class="easyui-datebox" style="width: 200px;; height: 30px;"  readonly="readonly"
			data-options="" value="${trainingBean.trDateStart}" required="required" editable="false"/>
		</td>

		<td class="td4" style="width: 67px;"></td>

		<td class="td1"><span class="style1">*</span> 撤离日期</td>
		<td class="td2">
			<input class="easyui-datebox" style="width: 206px;; height: 30px;"  readonly="readonly"
			data-options="onSelect:onSelect4" value="${trainingBean.trDateEnd}" required="required" editable="false"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>培训天数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px;; height: 30px;" 
			value="${trainingBean.trDayNum}" readonly="readonly" required="required" 
			data-options="validType:'length[1,2]',icons: [{iconCls:'icon-tian'}]"/>
		</td>
		<td class="td4" style="width: 67px;"></td>
		<td class="td1"><span class="style1">*</span> 培训类别</td>
		<td class="td2">
			<input  class="easyui-combobox" required="required" readonly="readonly" style="width: 206px; height: 30px;" value="${trainingBean.trainingType}" 
			data-options="valueField: 'trainingType',textField: 'value',editable: false,
				data: [{trainingType: '1',value: '一类培训'},{trainingType: '2',value: '二类培训'},{trainingType: '3',value: '三类培训'}]"/> 
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 培训地点</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" readonly="readonly"
			value="${trainingBean.trPlace}" required="required" data-options="prompt: '填写时地点精确到会议室房间号' ,validType:'length[1,50]'"/>
		</td>
	</tr>


	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 主要参训人员</td>
		<td colspan="4">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="trAttendPeop" readonly="readonly"
			value="${trainingBean.trAttendPeop}" required="required" data-options="validType:'length[1,100]'"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 参训人数</td>
		<td class="td2">
			<input  class="easyui-numberbox" style="width: 200px; height: 30px;"  readonly="readonly"
			value="${trainingBean.trAttendNum}" required="required" data-options="validType:'length[1,3]'"/>
		</td>

		<td class="td4" style="width: 67px;"></td>

		<td class="td1">工作人员人数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 206px; height: 30px;"   readonly="readonly"
			value="${trainingBean.trStaffNum}" data-options="validType:'length[1,3]'"/>
		</td>
	</tr>
	<tr class="trbody">
			<td class="td1"><span class="style1">*</span>是否安排住宿</td>
			<td class="td2">
				<input  name="isHotel" value="1"
					type="radio"  disabled="disabled" <c:if test="${trainingBean.isHotel==1 }">checked="checked"</c:if>/>是
				<input  name="isHotel" value="0"
					type="radio"   disabled="disabled" <c:if test="${trainingBean.isHotel!=1 }">checked="checked"</c:if>/>否
			</td>	
			<td class="td4" style="width: 67px;"></td>
			<td class="td1"><span class="style1">*</span>是否安排伙食</td>
			<td class="td2">
				<input  name="isFood" value="1"
					type="radio"  disabled="disabled" <c:if test="${trainingBean.isFood==1 }">checked="checked"</c:if>/>是
				<input  name="isFood" value="0"
					type="radio"  disabled="disabled" <c:if test="${trainingBean.isFood!=1 }">checked="checked"</c:if>/>否
			</td>	
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span>
			经办人</td>
		<td class="td2"><input class="easyui-textbox" 
			 readonly="readonly" value="${applyBean.userNames}"
			style="width: 200px;height: 30px; "></td>
		<td class="td4" style="width: 67px;"></td>
		<td class="td1"><span class="style1">*</span>
			部门名称</td>
		<td class="td2"><input class="easyui-textbox" 
			 readonly="readonly" value="${applyBean.deptName}"
			style="width: 206px;height: 30px; "></td>
	</tr>
</table>

<script type="text/javascript">

	
</script>
