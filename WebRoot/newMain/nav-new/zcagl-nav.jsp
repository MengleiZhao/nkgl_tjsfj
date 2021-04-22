<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<aside class="menu-accordion">

	<%-- <h1>存货管理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Storage/lowlist">
		<h2 onclick="addTabs('易耗品-新增','${base}/Storage/lowlist');">新增</h2>
		<div></div>
		</gwideal:perm> 
		
		 
		<gwideal:perm url="/Rece/lowlist">
		<h2 onclick="addTabs('存货-领用申请','${base}/Rece/lowlist');">领用申请</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Rece/approvalList?fAssType=ZCLX-01">
		<h2 onclick="addTabs('存货-领用审批','${base}/Rece/approvalList?fAssType=ZCLX-01');">领用审批</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Handle/lowapplicationList">
		<h2 onclick="addTabs('存货-处置申请','${base}/Handle/lowapplicationList');">处置申请</h2>
		<div></div>
		</gwideal:perm>
		 
		<gwideal:perm url="/Handle/approvalList?fAssType=ZCLX-01">
		<h2 onclick="addTabs('存货-处置审批','${base}/Handle/approvalList?fAssType=ZCLX-01');">处置审批</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/AssetsLedger/lowList">
		<h2 onclick="addTabs('易耗品-台账','${base}/AssetsLedger/lowList');">台账</h2>
		<div></div>
		</gwideal:perm> 
	</div> --%>
	<gwideal:perm url="/Storage/gdzc">
	<h1>固定资产</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Storage/fixedlist?fAssType=ZCLX-02">
		<h2 onclick="addTabs('固定资产-增加申请','${base}/Storage/fixedlist?fAssType=ZCLX-02');">增加申请</h2>
		<div></div>
		</gwideal:perm>
		 
		<%-- <gwideal:perm url="/Storage/approvalList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('固定资产-增加审批','${base}/Storage/approvalList?fAssType=ZCLX-02');">增加审批</h2>
		<div></div>
		</gwideal:perm>  --%>
		<gwideal:perm url="/Storage/fixedAccountantlist?fAssType=ZCLX-02">
		<h2 onclick="addTabs('资产新增会计补录','${base}/Storage/fixedAccountantlist?fAssType=ZCLX-02');">资产新增会计补录</h2>
		<div></div>
		</gwideal:perm>
		<gwideal:perm url="/Rece/fixedlist">
		<h2 onclick="addTabs('固定资产-领用申请','${base}/Rece/fixedlist');">领用申请</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Rece/approvalList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('固定资产-领用审批','${base}/Rece/approvalList?fAssType=ZCLX-02');">领用审批</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Rece/acceptList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('固定资产-领用受理','${base}/Rece/acceptList?fAssType=ZCLX-02');">领用受理</h2>
		<div></div>
		</gwideal:perm> 
		
<%-- 		<gwideal:perm url="/Alloca/AppList">
		<h2 onclick="addTabs('固定资产-调拨申请','${base}/Alloca/AppList');">调拨申请</h2>
		<div></div>
		</gwideal:perm>
		 
		<gwideal:perm url="/Alloca/approvalList">
		<h2 onclick="addTabs('固定资产-调拨审批','${base}/Alloca/approvalList');">调拨审批</h2>
		<div></div>
		</gwideal:perm>
		 
		<gwideal:perm url="/Maintain/list">
		<h2 onclick="addTabs('固定资产-维修申请','${base}/Maintain/list');">维修申请</h2>
		<div></div>
		</gwideal:perm>
		 
		<gwideal:perm url="/Maintain/approvalList">
		<h2 onclick="addTabs('固定资产-维修审批','${base}/Maintain/approvalList');">维修审批</h2>
		<div></div>
		</gwideal:perm>
		 
		<gwideal:perm url="/Maintain/registrationList">
		<h2 onclick="addTabs('固定资产-维修登记','${base}/Maintain/registrationList');">维修登记</h2>
		<div></div>
		</gwideal:perm>  --%>
		
		<gwideal:perm url="/Handle/fixedapplicationList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('固定资产-处置申请','${base}/Handle/fixedapplicationList?fAssType=ZCLX-02');">处置申请</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Handle/approvalFixedList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('固定资产-处置审批','${base}/Handle/approvalFixedList?fAssType=ZCLX-02');">处置审批</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Handle/replenishFixedList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('固定资产处置-信息补录','${base}/Handle/replenishFixedList?fAssType=ZCLX-02');">处置信息补录</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/Handle/disposeAcceptList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('固定资产处置受理','${base}/Handle/disposeAcceptList?fAssType=ZCLX-02');">处置受理</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/assetReturn/toAssetReturnList">
		<h2 onclick="addTabs('固定资产-交回申请','${base}/assetReturn/toAssetReturnList');">交回申请</h2>
		<div></div>
		</gwideal:perm>
		<gwideal:perm url="/assetReturn/toAssetReturnApprovalList">
		<h2 onclick="addTabs('固定资产-交回审批','${base}/assetReturn/toAssetReturnApprovalList');">交回审批</h2>
		<div></div>
		</gwideal:perm>
		<gwideal:perm url="/assetReturn/toAssetReturnAcceptList">
		<h2 onclick="addTabs('固定资产-交回受理','${base}/assetReturn/toAssetReturnAcceptList');">交回受理</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/AssetsLedger/fixedList">
		<h2 onclick="addTabs('固定资产-台账','${base}/AssetsLedger/fixedList');">台账</h2>
		<div></div>
		</gwideal:perm>
		
		<%-- <gwideal:perm url="/cggl/cgconfplangl"> 
		<h2>配置计划管理</h2>
		<div class="opened-for-codepen">
			<gwideal:perm url="/cgconfplangl/list">
			<h3 onclick="addTabs('配置计划申请','${base}/cgconfplangl/list');">配置计划申请</h3>
			<div></div>
			</gwideal:perm> 
	
			<gwideal:perm url="/cgconfplancheck/list">
			<h3 onclick="addTabs('配置计划审核','${base}/cgconfplancheck/list');">配置计划审核</h3>
			<div></div>
			</gwideal:perm> 
				
			<gwideal:perm url="/cgconfplancombine/list">
			<h3 onclick="addTabs('配置计划合并','${base}/cgconfplancombine/list');">配置计划合并</h3>
			<div></div>
			</gwideal:perm> 
			
		</div>
		</gwideal:perm> --%>
	</div>
	</gwideal:perm>
	<gwideal:perm url="/Storage/wxzc">
	<h1>无形资产</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Storage/intangiblelist?fAssType=ZCLX-03">
		<h2 onclick="addTabs('无形资产-增加申请','${base}/Storage/intangiblelist?fAssType=ZCLX-03');">增加申请</h2>
		<div></div>
		</gwideal:perm>
		 
		<gwideal:perm url="/Storage/approvalIntangibleList?fAssType=ZCLX-03">
		<h2 onclick="addTabs('无形资产-增加审批','${base}/Storage/approvalIntangibleList?fAssType=ZCLX-03');">增加审批</h2>
		<div></div>
		</gwideal:perm>
		<gwideal:perm url="/Storage/fixedAccountantlist?fAssType=ZCLX-03">
		<h2 onclick="addTabs('资产新增会计补录','${base}/Storage/fixedAccountantlist?fAssType=ZCLX-03');">资产新增会计补录</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/Handle/intangibleApplicationList?fAssType=ZCLX-03">
		<h2 onclick="addTabs('无形资产-处置申请','${base}/Handle/intangibleApplicationList?fAssType=ZCLX-03');">处置申请</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Handle/approvalIntangibleList?fAssType=ZCLX-03">
		<h2 onclick="addTabs('无形资产-处置审批','${base}/Handle/approvalIntangibleList?fAssType=ZCLX-03');">处置审批</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Handle/replenishIntangibleList?fAssType=ZCLX-03">
		<h2 onclick="addTabs('无形资产处置-信息补录','${base}/Handle/replenishIntangibleList?fAssType=ZCLX-03');">处置信息补录</h2>
		<div></div>
		</gwideal:perm> 

		<gwideal:perm url="/AssetsLedger/intangibleList?fAssType=ZCLX-03">
		<h2 onclick="addTabs('无形资产-台账','${base}/AssetsLedger/intangibleList?fAssType=ZCLX-03');">台账</h2>
		<div></div>
		</gwideal:perm>
	</div>
	</gwideal:perm>
	<%-- <h1>资产新增会计补录</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Storage/fixedAccountantlist">
		<h2 onclick="addTabs('资产新增会计补录','${base}/Storage/fixedAccountantlist');">资产新增会计补录</h2>
		<div></div>
		</gwideal:perm>
	</div>
	<h1>卡片编号登记</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Storage/fixedCardRegisterList">
		<h2 onclick="addTabs('卡片编号登记','${base}/Storage/fixedCardRegisterList');">卡片编号登记</h2>
		<div></div>
		</gwideal:perm>
	</div> --%>
<%-- 	<h1>资产新增</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Storage/fixedlist">
		<h2 onclick="addTabs('新增申请','${base}/Storage/fixedlist');">新增申请</h2>
		<div></div>
		</gwideal:perm>
			 
		<gwideal:perm url="/Storage/approvalList">
		<h2 onclick="addTabs('新增审批','${base}/Storage/approvalList');">新增审批</h2>
		<div></div>
		</gwideal:perm>
	</div>
	
	<h1>资产调拨</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Alloca/AppList">
		<h2 onclick="addTabs('调拨申请','${base}/Alloca/AppList');">调拨申请</h2>
		<div></div>
		</gwideal:perm>
			 
		<gwideal:perm url="/Alloca/approvalList">
		<h2 onclick="addTabs('调拨审批','${base}/Alloca/approvalList');">调拨审批</h2>
		<div></div>
		</gwideal:perm>
		
	</div>
	
	<h1>资产处置</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Handle/fixedapplicationList">
		<h2 onclick="addTabs('处置申请','${base}/Handle/fixedapplicationList');">处置申请</h2>
		<div></div>
		</gwideal:perm> 
			
		<gwideal:perm url="/Handle/approvalList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('处置审批','${base}/Handle/approvalList?fAssType=ZCLX-02');">处置审批</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/Handle/ledgerList?fAssType=ZCLX-02">
		<h2 onclick="addTabs('处置台账','${base}/Handle/ledgerList?fAssType=ZCLX-02');">处置台账</h2>
		<div></div>
		</gwideal:perm>
	</div> --%>
</aside>
