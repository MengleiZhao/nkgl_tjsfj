<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
<style type="text/css">
.panel-header{
	height: 20px;
}
</style>
<div class="win-div">
<form id="bid_detail_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 509px; ">
		<div class="win-left-div" style="width: 720px" data-options="region:'west',split:true">
			<div class="win-left-top-div">
				<div class="tab-wrapper" id="yx-tab">
					<ul class="tab-menu" >
						<li class="active">过程登记</li>
					    <li>采购申请</li>
					</ul>
					<div class="tab-content">
						<div title="过程登记" style="margin-bottom:35px;" data-options="iconCls:'icon-xxlb'">
							<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
								<!--第1个div  -->
								<div title="供应商信息"  id="gysxxdiv"  data-options="iconCls:'icon-xxlb'" style="overflow:auto;margin-top: 10px;">
									<jsp:include page="course_gys.jsp" />
								</div>
								
								<%-- <!--第2个div  -->
								<div title="代理人信息"   id="dlrxxdiv" data-options="iconCls:'icon-xxlb',collapsed:false,collapsible:false" style="overflow:auto;margin-top: 10px;">
								  	<jsp:include page="course_dlr.jsp" />						
								</div> --%>
							</div>
						</div>
					
						<div title="采购申请" style="margin-bottom:35px;overflow:auto" data-options="iconCls:'icon-xxlb'" >
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
				<a href="javascript:void(0)" onclick="closeSecondWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	</div>
</form>
</div>
	<script type="text/javascript">
	//加载tab页
	flashtab('yx-tab');
	 //寻找相关制度
	function findSystemFile(id) {
		$.ajax({ 
			url: base+"/cheter/systemFind?id="+id, 
			success: function(data){
				data=data.replace('\"','');
				data=data.replace('\"','');
				window.open(data);
	    }});
	} 
	 
	//查看附件
	function findAttacFile(id) {
		$.ajax({ 
			url: base+"/cgsqsp/attacFind?id="+id, 
			success: function(data){
				data=data.replace('\"','');
				data=data.replace('\"','');
				window.open(data);
	    }});
	}
	//合同详情
	function detail(id) {
		var win = creatWin('合同拟定申请', 970, 580, 'icon-search',"/Formulation/detail/" + id);
		win.window('open');
}
	</script>
</body>