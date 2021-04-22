<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<aside class="menu-accordion">
	<gwideal:perm url="/Formulation/htnd">
	<h1>合同拟定</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Formulation/list">
		<h2 onclick="addTabs('合同拟定','${base}/Formulation/list');">合同拟定</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Approval/list">
		<h2 onclick="addTabs('合同拟定审批','${base}/Approval/list');">合同拟定审批</h2>
		<div></div>
		</gwideal:perm> 
	</div>
	</gwideal:perm>
	<gwideal:perm url="/Change/htbg">
	<h1>合同变更/终止</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/Change/List">
		<h2 onclick="addTabs('合同变更/终止申请','${base}/Change/List');">合同变更/终止申请</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/Change/approvalList">
		<h2 onclick="addTabs('合同变更/终止审批','${base}/Change/approvalList');">合同变更/终止审批</h2>
		<div></div>
		</gwideal:perm> 
	</div>
	</gwideal:perm> 
<%-- 	<h1>合同终止</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/ending/list">
		<h2 onclick="addTabs('合同终止申请','${base}/ending/list');">合同终止申请</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/ending/applist">
		<h2 onclick="addTabs('合同终止审批','${base}/ending/applist');">合同终止审批</h2>
		<div></div>
		</gwideal:perm>
	</div> --%>
	
<%-- 	<gwideal:perm url="/Dispute/list">
	<h1 onclick="addTabs('合同纠纷','${base}/Dispute/list');">合同纠纷</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/htjfnav">
		<h2 onclick="addTabs('合同纠纷','${base}/Dispute/list');">合同纠纷</h2>
		<div></div>
		</gwideal:perm>
	</div>
	<div></div>
	</gwideal:perm> --%>
	
	<gwideal:perm url="/sealInfo/list">
	<h1 onclick="addTabs('合同用印','${base}/sealInfo/list');">合同用印</h1>
	<div></div>
	</gwideal:perm>
	
	<gwideal:perm url="/Archiving/list">
	<h1 onclick="addTabs('合同归档','${base}/Archiving/list');">合同归档</h1>
	<%-- <div class="opened-for-codepen">
		<gwideal:perm url="/htgdnav">
		<h2 onclick="addTabs('合同归档','${base}/Archiving/list');">合同归档</h2>
		<div></div>
		</gwideal:perm> 
	</div> --%>
	<div></div>
	</gwideal:perm>
	
	<gwideal:perm url="/Ledger/list">
	<h1 onclick="addTabs('合同台账','${base}/Ledger/list');">合同台账</h1>
	<%-- <div class="opened-for-codepen">
		<gwideal:perm url="/httznav">
		<h2 onclick="addTabs('合同台账','${base}/Ledger/list');">合同台账</h2>
		<div></div>
		</gwideal:perm>
	</div> --%>
	<div></div>
	</gwideal:perm>
	
<%-- 	<gwideal:perm url="/Expiration/list">
	<h1 onclick="addTabs('合同付款申请','${base}/Expiration/list');">合同付款申请</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/htdqtxnav">
		<h2 onclick="addTabs('合同到期提醒','${base}/Expiration/list');">合同到期提醒</h2>
		<div></div>
		</gwideal:perm> 
	</div>
	<div></div>
	</gwideal:perm> --%>
	
	
</aside>

