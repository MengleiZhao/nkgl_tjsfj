<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<aside class="menu-accordion">
	<gwideal:perm url="/apply/sqsq">
	<h1>事前申请</h1>
	<div class="opened-for-codepen">
	<gwideal:perm url="/apply/list">
		<h2>申请</h2>
		<div class="opened-for-codepen">
			<gwideal:perm url="/apply/list?applyType=1">
			<h3 onclick="addTabs('通用事项申请','${base}/apply/list?applyType=1');">通用事项申请</h3>
			<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/apply/list?applyType=2">
			<h3 onclick="addTabs('会议申请','${base}/apply/list?applyType=2');">会议申请</h3>
			<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/apply/list?applyType=3">
			<h3 onclick="addTabs('培训申请','${base}/apply/list?applyType=3');">培训申请</h3>
			<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/apply/list?applyType=4">
			<h3 onclick="addTabs('差旅申请','${base}/apply/list?applyType=4');">差旅申请</h3>
			<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/apply/list?applyType=5">
			<h3 onclick="addTabs('公务接待申请','${base}/apply/list?applyType=5');">公务接待申请</h3>
			<div></div>
			</gwideal:perm>
			
			<%-- <gwideal:perm url="/apply/list?applyType=6">
			<h3 onclick="addTabs('公车运维申请','${base}/apply/list?applyType=6');">公车运维申请</h3>
			<div></div>
			</gwideal:perm> --%>
			
			<%-- <gwideal:perm url="/apply/list?applyType=7">
			<h3 onclick="addTabs('公务出国申请','${base}/apply/list?applyType=7');">公务出国申请</h3>
			<div></div>
			</gwideal:perm> --%>
		</div>
		</gwideal:perm>
		<gwideal:perm url="/applyCheck/list">
		<h2 onclick="addTabs('审批','${base}/applyCheck/list');">审批</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/applyLedger/list">
		<h2 onclick="addTabs('台账','${base}/applyLedger/list');">台账</h2>
		<div></div>
		</gwideal:perm>
	</div>
	</gwideal:perm>
	<gwideal:perm url="/reimburse/bxsq">
	<h1>报销申请</h1>
	<div class="opened-for-codepen">
	<gwideal:perm url="/reimburse/sqbx">
		<h2>申请报销</h2>
		<div class="opened-for-codepen">
			<gwideal:perm url="/reimburse/list?reimburseType=1">
			<h3 onclick="addTabs('通用事项报销','${base}/reimburse/list?reimburseType=1');">通用事项报销</h3>
			<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/reimburse/list?reimburseType=2">
			<h3 onclick="addTabs('会议报销','${base}/reimburse/list?reimburseType=2');">会议报销</h3>
			<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/reimburse/list?reimburseType=3">
			<h3 onclick="addTabs('培训报销','${base}/reimburse/list?reimburseType=3');">培训报销</h3>
			<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/reimburse/list?reimburseType=4">
			<h3 onclick="addTabs('差旅报销','${base}/reimburse/list?reimburseType=4');">差旅报销</h3>
			<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/reimburse/list?reimburseType=5">
			<h3 onclick="addTabs('公务接待报销','${base}/reimburse/list?reimburseType=5');">公务接待报销</h3>
			<div></div>
			</gwideal:perm>
			<%-- 
			<gwideal:perm url="/reimburse/list?reimburseType=6">
			<h3 onclick="addTabs('公车运维报销','${base}/reimburse/list?reimburseType=6');">公车运维报销</h3>
			<div></div>
			</gwideal:perm>
			 --%>
			<%-- <gwideal:perm url="/reimburse/list?reimburseType=7">
			<h3 onclick="addTabs('公务出国报销','${base}/reimburse/list?reimburseType=7');">公务出国报销</h3>
			<div></div>
			</gwideal:perm> --%>
		</div>
		</gwideal:perm>
		 <gwideal:perm url="/directlyReimburse/list">
		<h2 onclick="addTabs('直接报销','${base}/directlyReimburse/list');">直接报销</h2>
		<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/reimburse/list?reimburseType=9">
		<h2 onclick="addTabs('往来款报销','${base}/reimburse/list?reimburseType=9');">往来款报销</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/reimburse/list?reimburseType=8">
		<h2 onclick="addTabs('合同报销','${base}/reimburse/list?reimburseType=8');">合同报销</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/reimburse/list?reimburseType=11">
		<h2 onclick="addTabs('保证金报销','${base}/reimburse/list?reimburseType=11');">保证金报销</h2>
		<div></div>
		</gwideal:perm>
	
		<gwideal:perm url="/reimburseCheck/list">
		<h2 onclick="addTabs('报销审批','${base}/reimburseCheck/list');">报销审批</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/reimburseLedger/list">
		<h2 onclick="addTabs('报销台账','${base}/reimburseLedger/list');">报销台账</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/reimburse/statisticslist">
		<h2 onclick="addTabs('报销统计','${base}/reimburse/statisticslist');">报销统计</h2>
		<div></div>
		</gwideal:perm>
	</div>
	</gwideal:perm>
	<gwideal:perm url="/loan/jksq">
	<h1>借款申请</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/loan/list">
		<h2 onclick="addTabs('借款申请','${base}/loan/list');">借款申请</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/loanCheck/list">
		<h2 onclick="addTabs('借款审批','${base}/loanCheck/list');">借款审批</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/payment/list?menuType=1">
		<h2 onclick="addTabs('还款登记','${base}/payment/list?menuType=1');">还款登记</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/payment/list?menuType=2">
		<h2 onclick="addTabs('还款确认','${base}/payment/list?menuType=2');">还款确认</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/loanLedger/list">
		<h2 onclick="addTabs('借款台账','${base}/loanLedger/list');">借款台账</h2>
		<div></div>
		</gwideal:perm>
	</div>
	</gwideal:perm>
	<gwideal:perm url="/reimburseLedger/cashierList">
	<h1>出纳受理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/reimburseLedger/cashierList">
		<h2 onclick="addTabs('出纳受理','${base}/reimburseLedger/cashierList');">出纳受理</h2>
		<div></div>
		</gwideal:perm>
	</div>
	</gwideal:perm>
<%-- 	<gwideal:perm url="/zcgl/cwsd">
	<h1>财务审定</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/audit/list">
		<h2 onclick="addTabs('财务审定','${base}/audit/list');">财务审定</h2>
		<div></div>
		</gwideal:perm>
	</div>
	</gwideal:perm> --%>
	
	<%-- <gwideal:perm url="/cashier/list">
	<h1 onclick="addTabs('凭证库管理','${base}/cashier/list');">凭证库管理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/pzkglnav">
		<h2 onclick="addTabs('凭证库管理','${base}/cashier/list');">凭证库管理</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/Voucher/list">
		<h2 onclick="addTabs('凭证库管理','${base}/Voucher/list');">凭证库管理</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/cashierCollect/list">
		<h2 onclick="addTabs('出纳机器人','${base}/cashierCollect/list');">出纳机器人</h2>
		<div></div>
		</gwideal:perm>
	</div>
	<div></div>
	</gwideal:perm> --%>
</aside>

