<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>

<aside class="menu-accordion">
	<gwideal:perm url="/purchaseIntention/cgyxgk">
	<h1>采购意向公开</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/purchaseIntention/list">
		<h2 onclick="addTabs('意向公开申请','${base}/purchaseIntention/list');">意向公开申请</h2>
		<div></div>
		</gwideal:perm> 
		<gwideal:perm url="/purchaseIntentionCheck/list">
		<h2 onclick="addTabs('意向公开审批','${base}/purchaseIntentionCheck/list');">意向公开审批</h2>
		<div></div>
		</gwideal:perm>
		
		<gwideal:perm url="/purchaseIntentionPub/list">
		<h2 onclick="addTabs('公开完成确认','${base}/purchaseIntentionPub/list');">公开完成确认</h2>
		<div></div>
		</gwideal:perm>
	</div>
	</gwideal:perm> 
	<gwideal:perm url="/cgsqsp/cgsqgl">
	<h1>采购申请管理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/cgsqsp/list">
		<h2 onclick="addTabs('采购申请','${base}/cgsqsp/list');">采购申请</h2>
		<div></div>
		</gwideal:perm> 

		<gwideal:perm url="/cgcheck/list">
		<h2 onclick="addTabs('采购确认','${base}/cgcheck/list');">采购确认</h2>
		<div></div>
		</gwideal:perm> 
	</div>
	</gwideal:perm> 
	<gwideal:perm url="/procurementNeeds/xmcggl">
	<h1>项目采购管理</h1>
	<div class="opened-for-codepen">
	<gwideal:perm url="/procurementNeeds/cgxq">
		<h2>采购需求</h2>
		<div class="opened-for-codepen">
			<gwideal:perm url="/procurementNeeds/list">
				<h3 onclick="addTabs('需求申请','${base}/procurementNeeds/list');">需求申请</h3>
				<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/procurementNeedsCheck/list">
				<h3 onclick="addTabs('需求确认','${base}/procurementNeedsCheck/list');">需求确认</h3>
				<div></div>
			</gwideal:perm>
		</div>
		</gwideal:perm>
		<gwideal:perm url="/purchasePlan/zcjh">
		<h2>政采计划</h2>
		<div class="opened-for-codepen">
			<gwideal:perm url="/purchasePlan/list">
			<h3 onclick="addTabs('政采计划申请','${base}/purchasePlan/list');">政采计划申请</h3>
			<div></div>
			</gwideal:perm> 
			<gwideal:perm url="/purchasePlanCheck/list">
			<h3 onclick="addTabs('政采计划审批','${base}/purchasePlanCheck/list');">政采计划审批</h3>
			<div></div>
			</gwideal:perm>
		</div>
		</gwideal:perm> 
		<gwideal:perm url="/cgsqsp/cgwj">
		<h2>采购文件</h2>
		<div class="opened-for-codepen">
			<gwideal:perm url="/cgsqsp/cgFilelist">
				<h3 onclick="addTabs('采购文件上传','${base}/cgsqsp/cgFilelist');">采购文件上传</h3>
				<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/cgsqsp/cgFileAffirmlist">
				<h3 onclick="addTabs('采购文件确认','${base}/cgsqsp/cgFileAffirmlist');">采购文件确认</h3>
				<div></div>
			</gwideal:perm>
		</div>
		</gwideal:perm>	
		<gwideal:perm url="/cgprocess/cgjgdj">
		<h2>采购结果登记</h2>
		<div class="opened-for-codepen">
			<gwideal:perm url="/cgprocess/list">
				<h3 onclick="addTabs('采购登记申请','${base}/cgprocess/list');">采购登记申请</h3>
				<div></div>
			</gwideal:perm>
			
			<gwideal:perm url="/cgprocess/checkList">
				<h3 onclick="addTabs('采购登记审批','${base}/cgprocess/checkList');">采购登记审批</h3>
				<div></div>
			</gwideal:perm>
		</div>
		</gwideal:perm>	
		<gwideal:perm url="/putOnRecords/zcba">
		<h2>政采备案</h2>
		<div class="opened-for-codepen">
			<gwideal:perm url="/putOnRecords/cgRegisterList">
				<h3 onclick="addTabs('备案登记','${base}/putOnRecords/cgRegisterList');">备案登记</h3>
				<div></div>
			</gwideal:perm>
			<gwideal:perm url="/putOnRecords/cgRegisterAffirmlist?index=1">
				<h3 onclick="addTabs('备案确认','${base}/putOnRecords/cgRegisterAffirmlist?index=1');">备案确认</h3>
				<div></div>
			</gwideal:perm>
			<gwideal:perm url="/putOnRecords/cgRegisterAffirmlist?index=2">
				<h3 onclick="addTabs('备案台账','${base}/putOnRecords/cgRegisterAffirmlist?index=2');">备案台账</h3>
				<div></div>
			</gwideal:perm>
		</div>
		</gwideal:perm>
	</div>
	</gwideal:perm>
	<%-- <h1>采购登记管理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/cgprocess/list">
		<h2 onclick="addTabs('登记申请','${base}/cgprocess/list');">登记申请</h2>
		<div></div>
		</gwideal:perm> 

		<gwideal:perm url="/cgprocess/checkList">
		<h2 onclick="addTabs('登记审批','${base}/cgprocess/checkList');">登记审批</h2>
		<div></div>
		</gwideal:perm> 	
	</div> --%>
	
	<%-- <h1>采购验收管理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/cgreceive/list">
		<h2 onclick="addTabs('验收申请','${base}/cgreceive/list');">验收申请</h2>
		<div></div>
		</gwideal:perm> 

		<gwideal:perm url="/cgreceive/checkList">
		<h2 onclick="addTabs('验收审批','${base}/cgreceive/checkList');">验收审批</h2>
		<div></div>
		</gwideal:perm> 	
	
	</div>
	<h1>采购台账管理</h1>
	<div class="opened-for-codepen">
		<gwideal:perm url="/cgsqLedger/list">
		<h2 onclick="addTabs('采购台账','${base}/cgsqLedger/list');">采购台账</h2>
		<div></div> 
		</gwideal:perm> 
	</div>--%> 
	<gwideal:perm url="/cgAsk/znzl">
	<h1>质疑处理</h1>
	<div class="opened-for-codepen">
	
		<gwideal:perm url="/cgAsk/list">
			<h2 onclick="addTabs('质疑发起','${base}/cgAsk/list');">质疑发起</h2>
			<div></div>
		</gwideal:perm> 
		
		<gwideal:perm url="/cgAsk/cgAnswer">
		<h2>质疑答复</h2>
		<div class="opened-for-codepen">
		
			<gwideal:perm url="/cgAsk/AnswerList">
				<h3 onclick="addTabs('答复申请','${base}/cgAsk/AnswerList');">答复申请</h3>
				<div></div>
			</gwideal:perm> 
			
			<gwideal:perm url="/cgAsk/AnswerCheckList">
				<h3 onclick="addTabs('答复审批','${base}/cgAsk/AnswerCheckList');">答复审批</h3>
				<div></div>
			</gwideal:perm> 
			
		</div>
		</gwideal:perm> 
	</div>
	</gwideal:perm> 
</aside>

