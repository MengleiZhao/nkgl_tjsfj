<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
<div class="win-div">
<form id="bid_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px; ">
		<div class="win-left-div" style=" width: 720px" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="tab-wrapper" id="yx-tab">
					<ul class="tab-menu">
						<li class="active">过程登记</li>
					    <li>采购申请</li>
					</ul>
					<div class="tab-content">
						<div title="过程登记" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
								<!--第1个div  -->
								<div title="供应商信息"  id="gysxxdiv"  data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
									<jsp:include page="course_gys_edit.jsp" />
								</div>
								
								<%-- <!--第2个div  -->
								<div title="代理人信息"   id="dlrxxdiv" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
									 <jsp:include page="course_dlr.jsp" />						
								</div> --%>
							</div>
						</div>
					
						<div title="采购申请" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
								<!-- 第3个div -->
								<div title="采购信息" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
									<jsp:include page="../purchase/cgjh_msg.jsp" />
								</div>
	
								<!-- 第4个div -->
								<div title="采购清单" data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
	 							  	<jsp:include page="../purchase/cgconf_plan_mingxi.jsp" />												
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>	
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="saveBid()">
					<img src="${base}/resource-modality/${themenurl}/button/baocun1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="closeSecondWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>&nbsp;&nbsp;&nbsp;
				<a href="${base }/systemcentergl/list?typeStr=采购管理" target="blank">
					<img src="${base}/resource-modality/${themenurl}/button/xgzd1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
		
		<%-- <div class="win-right-div" data-options="region:'east',split:true">
			<jsp:include page="../../check_system.jsp" />
		</div> --%>
	</div>
</form>
</div>
<script type="text/javascript">
	//加载tab页
	flashtab('yx-tab');
	
	
	
    function courseRegistClick(){
		  $('#courseRegist').show();  	
		  $('#purchasePlan').hide();  	
    }
    function purchasePlanClick(){
    	 $('#courseRegist').hide();  
    	 $('#purchasePlan').show();  
    	
    }
    
	/* 	//弹出供应商信息
	function checkSel() {   			
	    var win = creatFirstWin('供应商信息', 840, 475, 'icon-search', '/cgbid/selinfo');
		win.window('open');  			
	} */

		//保存
		function saveBid() {	
			
			//附件的路径地址
			 var s="";
			$(".fileUrl").each(function(){
				s=s+$(this).attr("id")+",";
			});
			$("#files").val(s);
			
			//中标金额小于等于采购金额
			var fbidAmount = parseFloat($('#F_fbidAmount').numberbox('getValue'));//中标金额
			var fpAmount = parseFloat($('#F_fpAmount').numberbox('getValue'));//
			if(fbidAmount>fpAmount){
				return alert("中标金额不得大于采购申请金额！请重新填写。")
			}
			
			//提交
			$('#bid_form').form('submit', {
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
				//url : base + "/cgbid/save?pid="+$('#F_fpId').val()+"&wid="+$("#F_fwId").val()+"&org="+$("#F_fwName").val(),
				url : base + '/cgprocess/save',
				success : function(data) {
					if (flag) {
						$.messager.progress('close');
					}
					data = eval("(" + data + ")");
					if (data.success) {
						$.messager.alert('系统提示', data.info, 'info');
						closeSecondWindow();
						//$("#bid_form").form("clear");
						 $('#course_regist_div').datagrid('reload');
					} else {
						$.messager.alert('系统提示', data.info, 'error');
					}
				}
			});	
		}
</script>
</body>