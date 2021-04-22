<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<div class="win-div">
<form id="confplan_save_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px;">
		<div class="win-left-div" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
																	<!-- 第一个div -->
								<div title="配置计划信息"   id="pzjhdiv" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
									<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
										<tr class="trbody">
											<td class="td1"><span class="style1">*</span>单据编号</td>
											<td class="td2">
												<input class="easyui-textbox" id="F_flistNum"  name="flistNum"  style="width:200px;" readonly="readonly" data-options="validType:'length[1,50]'" value="${bean.flistNum}"/>
												<input type="hidden" name="fplId" id="F_fplId" value="${bean.fplId}"/><!--隐藏域  -->
												<input type="hidden" name="fcheckStauts" id="F_fcheckStauts" value="${bean.fcheckStauts}"/><!--配置申请的审批状态  -->
												<input type="hidden" name="fstauts" id="F_fstauts" value="${bean.fstauts}" /><!--数据的删除状态  -->
												<input type="hidden" name="fisChecked" id="F_fisChecked" value="${bean.fisChecked}" /><!--采购的选择状态  -->
											</td>
											<td class="td4"></td>
											<td class="td1"><span class="style1">*</span>申请部门</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_freqDept"  name="freqDept" readonly="readonly" required="required" data-options="validType:'length[1,25]'" style="width: 200px" value="${bean.freqDept}"/>
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>申请日期</td>
											<td class="td2">
												<input class="easyui-datebox" type="text" id="F_freqTime"   name="freqTime"  required="required" data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.freqTime}"/>
											</td>
											<td class="td4"></td>
											<td class="td1"><span class="style1">*</span>配置采购类型</td>
											<td class="td2">
												<c:if test="${empty bean.fplId}">
													<select class="easyui-combobox" id="F_fprocurType"  name="fprocurType" required="required" style="width: 200px;" data-options="editable:false,panelHeight:'auto'" validType="selectValueRequired['#test']">
														<!-- <option value="0">--请选择--</option> -->
														<option value="A10" <c:if test="${bean.fprocurType=='A10'}">selected="selected"</c:if>>货物</option>
														<option value="A20" <c:if test="${bean.fprocurType=='A20'}">selected="selected"</c:if>>工程采购</option>
														<option value="A30" <c:if test="${bean.fprocurType=='A30'}">selected="selected"</c:if>>服务</option>
													</select>												
												</c:if>
												<c:if test="${!empty bean.fplId}">
													<select class="easyui-combobox" id="F_fprocurType"  name="fprocurType" readonly="readonly" required="required" style="width: 200px;" data-options="editable:false,panelHeight:'auto'" validType="selectValueRequired['#test']">
														<option value="0">--请选择--</option>
														<option value="A10" <c:if test="${bean.fprocurType=='A10'}">selected="selected"</c:if>>货物</option>
														<option value="A20" <c:if test="${bean.fprocurType=='A20'}">selected="selected"</c:if>>工程采购</option>
														<option value="A30" <c:if test="${bean.fprocurType=='A30'}">selected="selected"</c:if>>服务</option>
													</select>	
												</c:if>
											</td>
										</tr>
										<tr>
											<td class="td1"><span class="style1">*</span>申请部门联系人</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_freqLinkMen"  name="freqLinkMen"   readonly="readonly"  data-options="validType:'length[1,20]'" style="width: 200px" value="${bean.freqLinkMen}"/>
											</td>
											<td class="td4"></td>
											<td class="td1"><span class="style1">*</span>联系人电话</td>
											<td class="td2">
												<input class="easyui-textbox" type="text" id="F_flinkTel"  name="flinkTel"  required="required" data-options="validType:'tel'" style="width: 200px" value="${bean.flinkTel}"/>
											</td>
										</tr>
										<tr style="height: 70px;">
											<td class="td1" valign="top"><p style="margin-top: 8px"><span class="style1">*</span>配置申请内容</td>
											<td colspan="4">
												<%-- <input class="easyui-textbox" type="text" id="F_freqContent"  name="freqContent"  data-options="validType:'length[1,100]',multiline:true"   style="width:555px;height:70px;" value="${bean.freqContent}"/> --%>
												<textarea name="freqContent"  id="F_freqContent"  class="textbox-text"  oninput="textareaNum(this,'textareaNum1')" autocomplete="off"   style="width:555px;height:70px;resize:none">${bean.freqContent }</textarea> 
											</td>
										</tr>
										<c:if test="${openType=='add'||openType=='edit'}">
											<tr>
												<td align="right" colspan="5" style="padding-right: 0px;">
												可输入剩余数：<span id="textareaNum1" class="200">
													<c:if test="${empty bean.freqContent}">200</c:if>
													<c:if test="${!empty bean.freqContent}">${200-bean.freqContent.length()}</c:if>
												</span>
												</td>
											</tr>
										</c:if>
										<tr style="height: 70px;">
											<td class="td1" valign="top"><p style="margin-top: 8px">备注</td>
											<td colspan="4">
												<textarea name="fremark"  id="F_fremark"  class="textbox-text"  oninput="textareaNum(this,'textareaNum2')" autocomplete="off"   style="width:555px;height:70px;resize:none">${bean.fremark }</textarea>
												<%-- <input class="easyui-textbox" type="text" id="F_fremark"  name="fremark"  data-options="validType:'length[1,250]',multiline:true"   style="width:555px;height:70px;" value="${bean.fremark}"/> --%>
											</td>
										</tr>
										<c:if test="${openType=='add'||openType=='edit'}">
											<tr>
												<td align="right" colspan="5" style="padding-right: 00px;">
												可输入剩余数：<span id="textareaNum2" class="200">
													<c:if test="${empty bean.fremark}">200</c:if>
													<c:if test="${!empty bean.fremark}">${200-bean.fremark.length()}</c:if>
												</span>
												</td>
											</tr>
										</c:if>
										<tr class="trbody">
											<td class="td1">
												&nbsp;&nbsp;附件
												<input type="file" multiple="multiple" id="f" onchange="upladFile(this,'cggl','cggl01')" hidden="hidden">
												<input type="text" id="files" name="files" hidden="hidden">
											</td>
											<td colspan="4" id="tdf">
												<a onclick="$('#f').click()" style="font-weight: bold;" href="#">
													<img src="${base}/resource-modality/${themenurl}/button/shangchuan1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)"> 
												</a>
												<div id="progid" style="background:#EFF5F7;width:300px;height:10px;margin-top:5px;display: none" >
											        <div id="progressNumber" style="background:#3AF960;width:0px;height:10px" >
											        </div>文件上传中...&nbsp;&nbsp;<font id="percent">0%</font> 
									    	    </div>
												<c:forEach items="${attac}" var="att">
													<div style="margin-top: 10px;">
														<a href='${base}/attachment/download/${att.id}' style="color: #666666;font-weight: bold;">${att.originalName}</a>
														&nbsp;&nbsp;&nbsp;&nbsp;
														<img src="${base}/resource-modality/${themenurl}/sccg.png">
														&nbsp;&nbsp;&nbsp;&nbsp;
														<a id="${att.id}" class="fileUrl" href="#" style="color:red" onclick="deleteAttac(this)">删除</a>
													</div>
												</c:forEach>
												</td>
										</tr>
										<tr></tr>
									</table>
								</div>
												<!-- 第二个div -->
								<div title="配置采购商品清单"   id="pzcgspqddiv" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
 							  		<jsp:include page="cgconf_plan.jsp" />												
								</div>
				</div>
			</div>
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveConfplan(0)">
					<img src="${base}/resource-modality/${themenurl}/button/zhanchun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="saveConfplan(1)">
					<img src="${base}/resource-modality/${themenurl}/button/songshen1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=资产管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		<div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div>
		
	</div>
</form>
</div>
	<script type="text/javascript">

 	$(document).ready(function () {  //更改采购类型清空商品清单
		$("#F_fprocurType").combobox({
			onChange: function (newVal,oldVal) {
				if(newVal!=oldVal){
			    	var rows = $("#dg").datagrid("getRows");
					 if(rows==""){
						return false;
					}else{
						/* $.messager.confirm('提示','您所选择的采购类型与商品清单的商品不一致，是否确定使用新的采购类型?',function(r){
						    if (r){//清空清单信息 重新选择
						    	for (var i = rows.length - 1; i >= 0; i--) {
				                   	 	var index = $('#dg').datagrid('getRowIndex', rows[i]);
				                    	$('#dg').datagrid('deleteRow', index);
				               		 }
						   		 }
						    }); */
						$.messager.confirm({
						    width: 380,
						    height: 180, 
						    title: '提示',
						    msg: '您所选择的采购类型与商品清单的商品不一致，是否确定使用新的采购类型？',
						    fn: function (r) {
						    	if (r){//确定 清空商品清单
						    		for (var i = rows.length - 1; i >= 0; i--) {
				                   	 	var index = $('#dg').datagrid('getRowIndex', rows[i]);
				                    	$('#dg').datagrid('deleteRow', index);
				               		 }
						    	   }else{//取消  不操作
						    		  return false;
						    	  }
						        }
						    });
					} 
				}
			}
		});
	});
	
	
	
	//打开科目选择页面
	function openIndex() {
		var win = creatFirstWin('类型选择', 840, 450, 'icon-search', '/cgconfplangl/index');
		win.window('open');
	}
	
	
	//保存
	function saveConfplan(fcheckStauts) {
		// 在后台反序列话成采购明细Json的对象集合
		accept();
		var rows = $('#dg').datagrid('getRows');
		var mingxi = "";
		for (var i = 0; i < rows.length; i++) {
			mingxi = mingxi + JSON.stringify(rows[i]) + ",";
		}
		$('#mingxiJson').val(mingxi);
		
		var fplId=$('#F_fplId').val();
		var totalPrice=$('#totalPrice').val();
		if(fplId==""){
			if(totalPrice=="" || totalPrice=="0.00"){
				alert("请配置采购申请清单");
				return false;
			}
		}else{
			if(totalPrice=="0.00"){
				alert("请配置采购申请清单");
				return false;
			}
		}
		//附件的路径地址
		var s="";
		$(".fileUrl").each(function(){
			s=s+$(this).attr("id")+",";
		});
		$("#files").val(s);
		//设置审批状态
		$('#F_fcheckStauts').val(fcheckStauts);
		//提交
		$('#confplan_save_form').form('submit', {
			onSubmit : function() {
				flag = $(this).form('enableValidation').form('validate');
				if (flag) {
					$.messager.progress();
				}else{
					//校验不通过，就打开第一个校验失败的手风琴
					openAccordion();
				}
				return flag;
			},
			url : base + '/cgconfplangl/save',
			success : function(data) {
				if (flag) {
					$.messager.progress('close');
				}
				data = eval("(" + data + ")");
				if (data.success) {
					$.messager.alert('系统提示', data.info, 'info');
					closeWindow();
					$("#confplan_save_form").form("clear");
					$("#confplan_tab").datagrid("reload");
					$('#indexdb').datagrid('reload');
				} else {
					$.messager.alert('系统提示', data.info, 'error');
					closeWindow();
					$('#confplan_save_form').form('clear');
				}
			}
		});  
	}
	
	
	//明细表格添加删除，保存方法
	var editIndex = undefined;
	function endEditing() {
		if (editIndex == undefined) {
			return true
		}
		if ($('#dg').datagrid('validateRow', editIndex)) {
			var ed = $('#dg').datagrid('getEditor', {
				index : editIndex,
				field : 'costDetail'
			});
			$('#dg').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	function onClickRow(index) {
		if (editIndex != index) {
			if (endEditing()) {
				$('#dg').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				editIndex = index;
			} else {
				$('#dg').datagrid('selectRow', editIndex);
			}
		}
	}
	function append() {//未配置采购类型不可添加采购清单
		var confleixing=$("#F_fprocurType").combobox('getValue'); 
		if(confleixing=="0"){
			alert("请配置采购类型");
		}else{
			 if (endEditing()) {
					$('#dg').datagrid('appendRow', {
						status : 'P'
					});
					editIndex = $('#dg').datagrid('getRows').length - 1;
					$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit',
							editIndex);
				} 
		} 
		//页面随滚动条置底
		/* var div = document.getElementById('westDiv');
		div.scrollTop = div.scrollHeight; */
	}
	function removeit() {
		if (editIndex == undefined) {
			return
		}
		$('#dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow',
				editIndex);
		editIndex = undefined;
	}

	function accept() {
		if (endEditing()) {
			$('#dg').datagrid('acceptChanges');
		}
	}
	
	
	//计算配置计划总额
	function setFsumMoney(newValue,oldValue) {
		var totalFsumMoney = 0;
		var fsumMoney = 0;
		var index=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
		var rows = $('#dg').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(i==index){
				fsumMoney=setEditing(rows,i);
			}else{
				totalFsumMoney+=addNum(rows,i);
			}  
		 
		}
		totalFsumMoney=totalFsumMoney+fsumMoney;
		$('#totalPrice').textbox('setValue',totalFsumMoney.toFixed(2));
	}
	//未编辑或者已经编辑完毕的行，计算优惠后总价
	function addNum(rows,index){
		var totalPrice=0;
		var fnum=rows[index]['fnum'];
		var funitPrice=rows[index]['funitPrice'];
		if(fnum!="" && fnum!=null && funitPrice!="" && funitPrice!=null){
			totalPrice= parseFloat(fnum)*(parseFloat(funitPrice));
		}
		return totalPrice;
	}
	//对于正在编辑的行，计算优惠后总价
    function setEditing(rows,index){
        var editors = $('#dg').datagrid('getEditors', index);  
        var fnum = editors[5]; 
        var funitPrice = editors[6];   
        var fsumMoney = editors[7];
        var totalPrice = (fnum.target.val())*(funitPrice.target.val());		
        fsumMoney.target.numberbox("setValue",totalPrice);    
        return totalPrice;
    }
	//加载完以后自动计算金额
    $('#dg').datagrid({onLoadSuccess : function(data){
    	setFsumMoney();
    }});
	//填写清单信息   设置列固定  左右滚动
	$('#dg').datagrid({
		//var leixing=$('#F_fprocurType').val();
	 frozenColumns: [[
	                 	  { field: 'mainId', title: '主ID', width: 100,hidden:true },
	                 	  { field: 'fplId', title: '外键ID', width: 100 ,hidden:true},
	           			  { field: 'fpurCode', title: '目录编码', width: 180, editor:{
	              					editable:true,
	            					type:'combotree',
	            					options:{
	            						valueField:'id',//编码
	            						textField:'text',//名称
	            						method:'post',
	            						onBeforeLoad:function(node,param){//获取采购类型  传递到后台查询列表树
	            				            var leixing=$('#F_fprocurType').combobox('getValue');
											if(node==null){//页面第一次加载采购目录
		            				            param.id=leixing; 
											}else{//点击二级节点  展示三级节点
												param.id=node.id;
											}	
	            				        },
	            						url:"${base}/purchaseCatagl/tree",
	            						onBeforeSelect:function(node){//设置只能选择底层编码
	            							if (!$(this).tree('isLeaf', node.target)) {
	            								alert("请选择底层目录！");
	            								$(this).tree('expand', node.target); //展开子节点
	            				                return false;
	            				            } 
	            		                },
	            						onSelect:function(item){
	            							if(item.text.length!=0) {//设置计量单位
												var index=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
												var tr = $('#dg').datagrid('getEditors', index);
												tr[1].target.textbox('setValue', item.text);//设置目录编码
												tr[1].target.textbox('textbox').attr('readonly',true);//设置不可编辑
												tr[2].target.textbox('setValue', item.col9);//设置计量单位
												tr[2].target.textbox('textbox').attr('readonly',true);//设置不可编辑
											} else {
												var index=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
												var tr = $('#dg').datagrid('getEditors', index);
												tr[1].target.textbox('setValue', '');
												tr[1].target.textbox('textbox').attr('readonly',true);
												tr[2].target.textbox('setValue', '');
												tr[2].target.textbox('textbox').attr('readonly',true);
											}
	            						}
	            						/* onClick: function(node){
	            							alert(node.id); 
	            				    	} */
	            					}
	            				}
	           			 }
	           			  ]],
						columns:[[
								{field: 'fpurName', title: '目录名称', width: 100 ,editor:'textbox'},
								{field:'fmeasureUnit',title:'计量单位',width:70, editor:'textbox'},
						        {field:'fpurBrand',title:'采购品牌',width:100, editor:'textbox'},
						        {field:'fspecifModel',title:'规格型号',width:100,editor:'textbox'},
						        {field:'fnum',title:'采购数量',width:100, editor:{type:'numberbox',options:{onChange:setFsumMoney}}},
						        {field:'funitPrice',title:'单价[元]',width:100,editor:{type:'numberbox',options:{precision:2,onChange:setFsumMoney}}},
						        {field:'fsumMoney',title:'金额[元]',width:100,editor:{type:'numberbox',options:{precision:2,readonly:true}}},
						        {field:'fneedTime',title:'需求时间',width:110,editor:'datebox',formatter:formatterEmptyTime},//特殊时间格式化！！！！
						        {field:'fcommProp',title:'商品属性',width:130,editor:'textbox'}
						    ]]

	}); 
	
	
	
	
	
	</script>
</body>