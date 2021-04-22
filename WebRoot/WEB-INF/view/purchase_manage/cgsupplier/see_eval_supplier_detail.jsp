<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<body>
  <style type="text/css">
    .stars{
      white-space: nowrap;
      text-align: left;
/*       margin-top: 20px;
      margin-bottom: 20px; */
    }
    .stars li{
      display: inline-block;
      color: #b1b1b1;
      font-size: 20px;
    }
	.stars5{
      white-space: nowrap;
      text-align: left;
/*       margin-top: 20px;
      margin-bottom: 20px; */
    }
    .stars5 li{
      display: inline-block;
      color: #b1b1b1;
      font-size: 25px;
    }
  </style>
<div class="win-div">
<form id="supplier_save_form" method="post" data-options="novalidate:true" class="easyui-form" enctype="multipart/form-data">
	<div class="easyui-layout" style="height: 479px;">
		<div class="win-left-div" data-options="region:'west',split:true">
			<div class="win-left-top-div" style="height: 410px;">
				<div class="easyui-accordion" data-options="" id="" style="width:662px;margin-left: 20px">
									<table cellpadding="0" cellspacing="0" class="ourtable" border="0">
										<tr class="trbody">
											<td colspan="6" style="text-align: center">
												<a style="color: #0170a4;font-size: 15px;font-weight: bold;">评价服务</a>
											</td>
										</tr>
										<tr>
											<td class="tdstar"></td>
											<td class="tdstar" colspan="2" style="text-align: center;">
												<a style="color: #b1b1b1;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													采购名称:&nbsp;&nbsp;${hisbean.fpName}</a>
												<input type="hidden" name="fpId" id="F_fpId" value="${hisbean.fpId}"/><!--隐藏域  采购信息的主键id  -->
												<input type="hidden" name="fpCode" id="F_fpCode" value="${hisbean.fpCode}"/><!--隐藏域  采购信息的采购单编号  -->
												<input type="hidden" name="fpName" id="F_fpName" value="${hisbean.fpName}"/><!--隐藏域  供应商的主键id -->
												<input type="hidden" name="fwId" id="F_fwId" value="${fwbean.fwId}"/><!--隐藏域  供应商的主键id -->
											</td>
											<td class="tdstar" style="color: color: #b1b1b1;" colspan="2">
												<a style="color: #b1b1b1;">采购时间:&nbsp;&nbsp;${dealTime.substring(0,10)}</a>
											</td>
											<td class="tdstar" ></td>
										</tr>
										<tr class="trbody">
											<td class="tdstar5" style="text-align: left;font-size: 14px;font-weight: bold;">综合评价</td>
											<td class="tdstar5" style="text-align: center;" >
													<ul class="stars5" id="star5">
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												    </ul>
											</td>
											<td id="pingfen5" class="tdstar5" colspan="2"></td>
										</tr>
										<tr style="height: 30px">
											<td colspan="6">
												<hr style="color: #b1b1b1;height:1px;border:none;border-top:1px solid #b1b1b1;"/>
											</td>
										</tr>
										<tr class="trbody">
											<td class="tdstar" style="text-align: left;font-size: 14px;font-weight: bold;">分项评价</td>
										</tr>
										<!-- <tr>
											<td class="tdstar">&nbsp;&nbsp;&nbsp;&nbsp;价格</td>
											<td class="tdstar">
													<ul class="stars" id="star1">
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												    </ul>
											</td>
											<td id="pingfen1" class="tdstar"></td>
											<td class="tdstar">性价比</td>
											<td class="tdstar">
													<ul class="stars" id="star2">
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												    </ul>										
											</td>
											<td id="pingfen2" class="tdstar"></td>
										</tr> -->
										<tr>
											<td class="tdstar">&nbsp;&nbsp;&nbsp;&nbsp;质量</td>
											<td class="tdstar">
													<ul class="stars" id="star3">
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												    </ul>											
											</td>
											<td id="pingfen3" class="tdstar"></td>
											<td class="tdstar">服务</td>
											<td class="tdstar">
													<ul class="stars" id="star4">
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												      <li>★</li>
												    </ul>											
											</td>
											<td id="pingfen4" class="tdstar"></td>
										</tr>
										<tr>
											<td class="tdstar"></td>
											<td class="tdstar" colspan="5"><span id="pingfen6" style="display: none;" class="style4" >不满意？请留下您的宝贵意见</span></td>
										</tr>
										<tr style="height: 70px;">
											<td class="tdstar" valign="top"><p style="margin-top: 8px">个人意见</td>
											<td colspan="5">
												<input class="easyui-textbox" type="text" id="F_fremark"  name="fremark" readonly="readonly"  data-options="validType:'length[1,50]',multiline:true"   style="width:555px;height:70px;" value="${evalbean.fremark}" />
											</td>
										</tr>
									</table>
									
				</div>
			</div>
			
			<div class="win-left-bottom-div">
				<a href="javascript:void(0)" onclick="closeFirstWindow()">
					<img src="${base}/resource-modality/${themenurl}/button/guanbi1.png" onMouseOver="mouseOver(this)" onMouseOut="mouseOut(this)">
				</a>
			</div>
		</div>
	
		
	</div>
</form>
</div>
	<script type="text/javascript">
	//展示供应商的评价信息  
	$(function() {		
		var zonghe=${evalbean.fcompScore}-1;
		var price=${evalbean.fpriceScore}-1;
		var rate=${evalbean.fcostPerf}-1;
		var quality=${evalbean.fquality}-1;
		var service=${evalbean.fservice}-1;
		/* alert(zonghe);
		alert(price);
		alert(rate);
		alert(quality);
		alert(service); */
		//综合评价
		for(var i = 1; i <= zonghe+1; i++) {
            $('#star5 li:nth-child(' + i + ')').css('color', '#fed80b');
          }
		$('#pingfen5').empty();
        if(zonghe==4){//根据点击的星星数量显示满意度
        	$('#pingfen5').append('<span class="style2">非常满意</span>');
        }else if(zonghe==3){
        	$('#pingfen5').append('<span class="style2">满意</span>');
        }else if(zonghe==2){
        	$('#pingfen5').append('<span class="style2">一般</span>');
        }else if(zonghe==1){
        	$('#pingfen5').append('<span class="style2">不满意</span>');
        }else if(zonghe==0){
        	$('#pingfen5').append('<span class="style2">非常不满意</span>');
        }
        //价格
		for(var i = 1; i <= price+1; i++) {
            $('#star1 li:nth-child(' + i + ')').css('color', '#fed80b');
          }
		$('#pingfen1').empty();
        if(price==4){//根据点击的星星数量显示满意度
        	$('#pingfen1').append('<span class="style2">非常满意</span>');
        }else if(price==3){
        	$('#pingfen1').append('<span class="style2">满意</span>');
        }else if(price==2){
        	$('#pingfen1').append('<span class="style2">一般</span>');
        }else if(price==1){
        	$('#pingfen1').append('<span class="style2">不满意</span>');
        }else if(price==0){
        	$('#pingfen1').append('<span class="style2">非常不满意</span>');
        }
        //性价比
		for(var i = 1; i <= rate+1; i++) {
            $('#star2 li:nth-child(' + i + ')').css('color', '#fed80b');
          }
		$('#pingfen2').empty();
        if(rate==4){//根据点击的星星数量显示满意度
        	$('#pingfen2').append('<span class="style2">非常满意</span>');
        }else if(rate==3){
        	$('#pingfen2').append('<span class="style2">满意</span>');
        }else if(rate==2){
        	$('#pingfen2').append('<span class="style2">一般</span>');
        }else if(rate==1){
        	$('#pingfen2').append('<span class="style2">不满意</span>');
        }else if(rate==0){
        	$('#pingfen2').append('<span class="style2">非常不满意</span>');
        }
        //质量
        for(var i = 1; i <= quality+1; i++) {
            $('#star3 li:nth-child(' + i + ')').css('color', '#fed80b');
          }
		$('#pingfen3').empty();
        if(quality==4){//根据点击的星星数量显示满意度
        	$('#pingfen3').append('<span class="style2">非常满意</span>');
        }else if(quality==3){
        	$('#pingfen3').append('<span class="style2">满意</span>');
        }else if(quality==2){
        	$('#pingfen3').append('<span class="style2">一般</span>');
        }else if(quality==1){
        	$('#pingfen3').append('<span class="style2">不满意</span>');
        }else if(quality==0){
        	$('#pingfen3').append('<span class="style2">非常不满意</span>');
        }
        //服务
        for(var i = 1; i <= service+1; i++) {
            $('#star4 li:nth-child(' + i + ')').css('color', '#fed80b');
          }
		$('#pingfen4').empty();
        if(service==4){//根据点击的星星数量显示满意度
        	$('#pingfen4').append('<span class="style2">非常满意</span>');
        }else if(service==3){
        	$('#pingfen4').append('<span class="style2">满意</span>');
        }else if(service==2){
        	$('#pingfen4').append('<span class="style2">一般</span>');
        }else if(service==1){
        	$('#pingfen4').append('<span class="style2">不满意</span>');
        }else if(service==0){
        	$('#pingfen4').append('<span class="style2">非常不满意</span>');
        }
	});
	</script>
</body>