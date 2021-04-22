<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<aside class="menu-accordion">

	<%-- <h1>经营服务性收入</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/business/list">
		<h2 onclick="addTabs('立项申请','${base}/business/list');">立项申请</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/business/checkList">
		<h2 onclick="addTabs('立项审批','${base}/business/checkList');">立项审批</h2>
		<div></div>
		</gwideal:perm> 
	</div> --%>
	
	<gwideal:perm url="/srregister/list"> 
	<h1 onclick="addTabs('收入登记','${base}/srregister/list');">收入登记</h1>
	<div></div>
	</gwideal:perm>
	
	<%-- <gwideal:perm url="/srgl/srcapital"> 
	<h1>资金归垫</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/srcapital/list">
		<h2 onclick="addTabs('资金归垫','${base}/srcapital/list');">资金归垫</h2>
		<div></div>
		</gwideal:perm>
	</div>
	</gwideal:perm> --%>
	
	<gwideal:perm url="/GoldPay/margin">
	<h1>合同保证金管理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/GoldPay/incomeRegistrationList">
		<h2 onclick="addTabs('合同保证金登记','${base}/GoldPay/incomeRegistrationList');">合同保证金登记</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/GoldPay/incomeLedger">
		<h2 onclick="addTabs('合同保证金台账','${base}/GoldPay/incomeLedger');">合同保证金台账</h2>
		<div></div>
		</gwideal:perm> 
	</div>
	</gwideal:perm> 
	<gwideal:perm url="/GoldPay/wlkdj">
	<h1>往来款登记</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/accountsCurrent/list">
		<h2 onclick="addTabs('来款立项','${base}/accountsCurrent/list');">来款立项</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/accountsCurrentCheck/list">
		<h2 onclick="addTabs('立项审批','${base}/accountsCurrentCheck/list');">立项审批</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/accountsRegister/registerList">
		<h2 onclick="addTabs('来款登记','${base}/accountsRegister/registerList');">来款登记</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/accountsRegisterCheck/registerList">
		<h2 onclick="addTabs('登记审批','${base}/accountsRegisterCheck/registerList');">登记审批</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/registerAffirm/listAffirm">
		<h2 onclick="addTabs('来款确认','${base}/registerAffirm/listAffirm');">来款确认</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/incomeManagerledger/listAccounts">
		<h2 onclick="addTabs('往来款台账','${base}/incomeManagerledger/listAccounts');">往来款台账</h2>
		<div></div>
		</gwideal:perm> 
	</div>
	</gwideal:perm> 
	<gwideal:perm url="/schedule/scd">
	<h1>用款计划</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/schedule/list">
		<h2 onclick="addTabs('计划申报','${base}/schedule/list');">计划申报</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/schedule/checkList">
		<h2 onclick="addTabs('计划审批','${base}/schedule/checkList');">计划审批</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/schedule/ledgerList">
		<h2 onclick="addTabs('计划台账','${base}/schedule/ledgerList');">计划台账</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/schedule/statistics">
		<h2 onclick="addTabs('计划统计','${base}/schedule/statistics');">计划统计</h2>
		<div></div>
		</gwideal:perm> 
	</div>
	</gwideal:perm> 
	<gwideal:perm url="/quotaManage">
	<h1>额度管理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/quotaManage/list">
		<h2 onclick="addTabs('额度登记','${base}/quotaManage/list');">额度登记</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/quotaManage/checkList">
		<h2 onclick="addTabs('额度复核','${base}/quotaManage/checkList');">额度复核</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/quotaManage/dataList">
		<h2 onclick="addTabs('可用额度','${base}/quotaManage/dataList');">可用额度</h2>
		<div></div>
		</gwideal:perm> 
		
	</div>
	</gwideal:perm> 
</aside>

