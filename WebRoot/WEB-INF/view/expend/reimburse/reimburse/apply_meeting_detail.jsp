<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="window-table" cellspacing="0" cellpadding="0" style="margin-top: -12px;">
	<tr class="trbody">
		<td class="td1" ><span class="style1">*</span> 会议名称</td>
		<td class="td2" colspan="3" >
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="meetingName" 
			value="${meetingBean.meetingName}" readonly="readonly" required="required" data-options="validType:'length[1,50]'"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 报到时间</td>
		<td class="td2" style="width: 281px;">
			<input class="easyui-datebox" readonly="readonly" style="width: 200px; height: 30px;"  name="dateStart"
			data-options="" value="${meetingBean.dateStart}" required="required" editable="false"/>
		</td>

		<td class="td1"><span class="style1">*</span> 离开时间</td>
		<td class="td2">
			<input class="easyui-datebox" readonly="readonly" style="width: 200px; height: 30px;"  name="dateEnd"
			data-options="onSelect:onSelect2" value="${meetingBean.dateEnd}" required="required" editable="false"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 会议天数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px; height: 30px;"  name="duration" 
			value="${meetingBean.duration}" readonly="readonly" required="required" data-options="iconCls:'icon-tian',validType:'length[1,2]'"/>
		</td>
			
		<td class="td1" ><span class="style1">*</span> 会议类型</td>
		<td class="td2" style="width: 200px;">
			<input  class="easyui-combobox" readonly="readonly" style="width: 200px; height: 30px;" name="meetingType" value="${meetingBean.meetingType}"
				data-options="prompt: '-请选择-' ,valueField: 'meetingType',textField: 'value',editable: false,
				data: [{meetingType: '1',value: '一类会议'},{meetingType: '2',value: '二类会议'},{meetingType: '3',value: '三类会议'},{meetingType: '4',value: '四类会议'}]"/>
		</td>
	</tr>

	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 会议地点</td>
		<td colspan="3">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="meetingPlace" readonly="readonly"
			value="${meetingBean.meetingPlace}" required="required" data-options="validType:'length[1,50]'"/>
		</td>
	</tr>
	
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 主要参会人员</td>
		<td colspan="3">
			<input class="easyui-textbox" style="width: 590px; height: 30px;" name="attendPeople" readonly="readonly"
			value="${meetingBean.attendPeople}" required="required" data-options="validType:'length[1,100]'"/>
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1"><span class="style1">*</span> 参会人数</td>
		<td class="td2"><input class="easyui-numberbox" style="width: 200px; height: 30px;" name="attendNum" readonly="readonly"
			value="${meetingBean.attendNum}" required="required" data-options="validType:'length[1,3]'"/>
		</td>

		<td class="td1">其中工作人员人数</td>
		<td class="td2">
			<input class="easyui-numberbox" style="width: 200px; height: 30px;" readonly="readonly"
			 name="staffNum"
			value="${meetingBean.staffNum}" data-options="validType:'length[1,3]'"/>
		</td>
	</tr>
	<tr style="height:5px;"></tr>
	<tr class="trbody">
		<td class="td1" valign="top"><p style="margin-top: 8px">会议内容</p></td>
		<td colspan="3">
			 <input class="easyui-textbox"data-options="multiline:true,required:false,validType:'length[0,250]'" readonly="readonly" name="content" style="width:590px;height:70px;" 
			value="${meetingBean.content}"> 
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1" style="width: 80px;"><span class="style1">*</span>是否安排住宿</td>
		<td class="td2">
			<input type="radio" value="1" disabled="disabled" <c:if test="${meetingBean.fWAHotel=='1'}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;是
			&nbsp;&nbsp;
			<input type="radio" value="0" disabled="disabled" <c:if test="${meetingBean.fWAHotel=='0' || empty meetingBean.fWAHotel}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;否
		</td>
		<td class="td1" style="width: 80px;"><span class="style1">*</span>是否安排伙食</td>
		<td class="td2">
			<input type="radio" value="1" disabled="disabled" <c:if test="${meetingBean.fWAFood=='1'}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;是
			&nbsp;&nbsp;
			<input type="radio" value="0" disabled="disabled" <c:if test="${meetingBean.fWAFood=='0' || empty meetingBean.fWAFood}">checked="checked" </c:if> style="vertical-align: text-top;"/>&nbsp;&nbsp;否
		</td>
	</tr>
	<tr class="trbody">
		<td class="td1" ><span class="style1">*</span>
			经办人</td>
		<td class="td2"><input class="easyui-textbox" 
			name="userNames" readonly="readonly" value="${applyBean.userNames}"
			style="width: 200px;height: 30px;margin-left: 10px "></td>
		<td class="td1" ><span class="style1">*</span>
			部门名称</td>
		<td class="td2"><input class="easyui-textbox" 
			name="deptName" readonly="readonly" value="${applyBean.deptName}"
			style="width: 200px;height: 30px;margin-left: 10px "></td>
	</tr>
</table>