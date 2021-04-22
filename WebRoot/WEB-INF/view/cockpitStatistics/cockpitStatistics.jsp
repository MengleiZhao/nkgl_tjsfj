<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ include file="/includes/links.jsp"%>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<link rel="stylesheet" type="text/css" href="${base}/resource-modality/cockpitStatistics/css/indexitem.css"/>
		<meta name="viewport" content="width=device-width, initial-scale=no">
		<title>管理驾驶舱</title>
	</head>
<body>

		<div class="brain">
			<div class="top-title">
				<div class="title-bg">
					<div class="center-contain-title">
						<span class="bigScreenName">管理驾驶舱</span>
					</div>
				</div>
				<div class="backHomePage" >
					<span onclick="window.close()">
						返回首页
					</span>
				</div>
			</div>
			<div class="left-contain flex-item">
				<div class="left-contain-top" style="position:relative;">
					<div class="left-contain-top-title">
						<span class="left-title left-title-purchase">采购管理</span>
					</div>
					<div class="left-contain-top-content">
						<div class="left-contain-top-statText" >
							<div class="left-contain-top-registered">
								<span>已登记:</span>
								<span><fmt:formatNumber groupingUsed="true" value="${cgData[0][1] }" minFractionDigits="2" maxFractionDigits="2"/></span>
								<span>元,</span>
								<span class="registerTimes"><fmt:formatNumber groupingUsed="true" value="${cgData[0][0] }"/>次</span>
							</div>
							<div class="left-contain-top-shopping">
								<span>采购中:</span>
								<span><fmt:formatNumber groupingUsed="true" value="${cgData[1][1] }" minFractionDigits="2" maxFractionDigits="2"/></span>
								<span>元,</span>
								<span class="shoppingTimes"><fmt:formatNumber groupingUsed="true" value="${cgData[1][0] }" />次</span>
							</div>
							<div class="left-contain-top-totalTimes">
								<span>总次数:</span>
								<span><fmt:formatNumber groupingUsed="true" value="${cgData[0][0]+cgData[1][0]}" />次</span>
							</div>
						</div>
						
					</div>
					<div style="position:absolute;color:rgba(153,157,165,1);right:10%;top:52%;">单位(元)</div>
					<div id="register-echart-histogram">
												
					</div>
				</div>
				<div class="left-contain-bottom" style="position:relative;">
					<div class="left-contain-top-title">
						<span class="left-title left-title-contract">合同管理</span>
					</div>
					<div class="left-contain-bottom-content">
						<div class="left-contain-bottom-contract">
							<div class="left-contain-bottom-text left-contain-bottom-executeing">
								<span>执行中：</span>
								<span id="progress">1,032,345.00</span>
								<span>元</span>
							</div>
							<div class="left-contain-bottom-text left-contain-bottom-cleared">
								<span>已结算：</span>
								<span id="paidAll">1,032,345.00</span>
								<span>元</span>
							</div>
							<div class="left-contain-bottom-text left-contain-bottom-unclear">
								<span>未结算：</span>
								<span id="arrearageAll">1,032,345.00</span>
								<span>元</span>
							</div>
							<div class="left-contain-bottom-text left-contain-bottom-contractTimes">
								<span>执行中合同份数：</span>
								<span id="contractCount">123</span>
								<span>份</span>
							</div>
						</div>
					</div>
					<div style="position:absolute;color:rgba(153,157,165,1);right:10%;top:53%;">单位(元)</div>
					<div id="contract-echart-histogram">
						
					</div>
				</div>
			</div>
			<div class="center-contain">
				<div class="budgetContainer">
					<div class="center-contain-top-title">
						<span class="center-title center-title-budget">预算管理</span>
					</div>
					<div class="center-contain-top-content">
						<div class="center-contain-top-budget">
							<div class="flexItem">
								<div class="center-contain-top-budgetAll">总预算</div>
								<div class="center-contain-top-text center-contain-top-budgetText">¥<fmt:formatNumber groupingUsed="true" value="${projectmap.projectAmount[0]}" minFractionDigits="2" maxFractionDigits="2"/></div>
							</div>
							<div  class="flexItem">
								<div class="center-contain-top-freezing">冻结中</div>
								<div class="center-contain-top-text center-contain-top-freezText">¥<fmt:formatNumber groupingUsed="true" value="${projectmap.projectAmount[2]}" minFractionDigits="2" maxFractionDigits="2"/></div>
							</div>
							<div  class="flexItem">
								<div class="center-contain-top-cleared">已结算</div>
								<div class="center-contain-top-text center-contain-top-clearText">¥<fmt:formatNumber groupingUsed="true" value="${projectmap.projectAmount[3]}" minFractionDigits="2" maxFractionDigits="2"/></div>
							</div>
							<div  class="flexItem">
								<div class="center-contain-top-surplus">剩&nbsp;&nbsp;&nbsp;余</div>
								<div class="center-contain-top-text center-contain-top-surText">¥<fmt:formatNumber groupingUsed="true" value="${projectmap.projectAmount[1]}" minFractionDigits="2" maxFractionDigits="2"/></div>
							</div>
							
						</div>
					</div>
					<!--柱状图-->
					<div id="budget-stackEchart-histogram">
						
					</div>
					<!--饼图-->
					<div class="pancakeContent" >
						<div class="pancake-histogram meetfee-pancake-histogram" >
							<div id="meetfee-pancake">
								
							</div>
							<div class="pancake-text meetfee-pancake-text">
								<span>会议费</span>
							</div>
						</div>
						<div class="pancake-histogram trainfee-pancake-histogram">
							<div id="trainfee-pancake">
								
							</div>
							<div class="pancake-text trainfee-pancake-text">
								<span>培训费</span>
							</div>
						</div>
						<div class="pancake-histogram travelfee-pancake-histogram">
							<div id="travelfee-pancake">
								
							</div>
							<div class="pancake-text travelfee-pancake-text">
								<span>差旅费</span>
							</div>
						</div>
						<div class="pancake-histogram receptionfee-pancake-histogram">
							<div id="receptionfee-pancake">
								
							</div>
							<div class="pancake-text receptionfee-pancake-text">
								<span>公务接待费</span>
							</div>
						</div>
						<div class="pancake-histogram abroadfee-pancake-histogram">
							<div id="abroadfee-pancake">
								
							</div>
							<div class="pancake-text abroadfee-pancake-text">
								<span>因公出国(境)费</span>
							</div>
						</div>
					</div>
					<!--柱状图-->
					<div id="scrap-Echart-histogram">
						
					</div>
				</div>
			</div>
			<div class="right-contain flex-item" >
				<div class="right-contain-top-title">
					<span class="right-title right-title-contract">资产管理</span>
				</div>
				<div class="right-contain-top-content" >
					<div class="right-contain-top-asset" >
						<div class="right-contain-top-assetText right-contain-top-assetAll">
							<span>总资产：</span>
							<span><fmt:formatNumber groupingUsed="true" value="${assetsamount.all}" minFractionDigits="2" maxFractionDigits="2"/></span>
							<span>元</span>
						</div>
						<div class="right-contain-top-assetText right-contain-top-assetFixed">
							<span>固定资产：</span>
							<span><fmt:formatNumber groupingUsed="true" value="${assetsamount.ZCLX02}" minFractionDigits="2" maxFractionDigits="2"/></span>
							<span>元</span>
						</div>
						<div class="right-contain-top-assetText right-contain-top-assetIntangible">
							<span>无形资产：</span>
							<span><fmt:formatNumber groupingUsed="true" value="${assetsamount.ZCLX03}" minFractionDigits="2" maxFractionDigits="2"/></span>
							<span>元</span>
						</div>
						<!-- <div class="right-contain-top-assetText right-contain-top-assetOther">
							<span>其他资产：</span>
							<span>1,345.00</span>
							<span>元</span>
						</div> -->
					</div>
				</div>
				<div style="position:relative;">
					<div style="position:absolute;color:rgba(153,157,165,1);left:10%;top:10%;">单位(元)</div>
					<div id="assetIntangible-echart-histogram">
					
					</div>
				</div>
				<div style="position:relative;">
					<div style="position:absolute;color:rgba(153,157,165,1);left:10%;top:10%;">单位(元)</div>
					<div id="assetFixed-echart-histogram">
					
					</div>
				</div>
				<div style="text-align: center;font-size: 13px;font-family: Microsoft YaHei;color: #FFFFFF;margin-top:5px;">
					<span>主要在库可用固定资产</span>
				</div>
				<div id="assetFixedUse-echart-histogram">
					
				</div>
				<div style="text-align: center;font-size: 13px;font-family: Microsoft YaHei;color: #FFFFFF;margin-top:5px;">
					<span>待报废资产占比</span>
				</div>
				<div id="assetScrap-echart-histogram">
					
				</div>
			</div>
		</div>
		<script src="${base}/resource-modality/cockpitStatistics/js/echarts.js" type="text/javascript" charset="utf-8"></script>
		<script src="${base}/resource-modality/cockpitStatistics/js/echarts.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${base}/resource-modality/cockpitStatistics/js/register.js" type="text/javascript" charset="utf-8"></script>
		<script src="${base}/resource-modality/cockpitStatistics/js/contract.js" type="text/javascript" charset="utf-8"></script>
		<script src="${base}/resource-modality/cockpitStatistics/js/asset.js" type="text/javascript" charset="utf-8"></script>
		<script src="${base}/resource-modality/cockpitStatistics/js/budget.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		//打开固定资产台账
		function openAssetsLedger(){
			window.location.href='${base}/cockpit/assetsLedger';
		}
		function indexPlanForm(){
			window.location.href='${base}/cockpit/indexPlanForm';
		}
		//开发采购台账
		function openRegisterLedger(){
			window.location.href='${base}/cockpit/cgsqledgerlist';
		}
		//开发报销台账
		function openReimburseLedger(type){
			window.location.href='${base}/cockpit/reimburselist?type='+type;
		}
		</script>
</body>
