<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
   <div class="list_lh" id="s1">
			<ul id="loan_ul" style="width: 100%; height: 100%;"></ul>
			<ul id="loan_ul1" style="width: 100%; height: 105%;"></ul>
		</div>
<script type="text/javascript">
loanMarquee();
function loanMarquee(){
	$.ajax({
		url: base+ "/index/basicInfos",
		type : 'post',
		dataType : 'json',
	    contentType:'application/json;charset=UTF-8',
		success : function(json) {
				var html = "";
				if(json!=null&&json.length>0){
				for( var i=0; i<json.length;i++){
					var lId = json[i].lId;
					var loanPurpose = json[i].loanPurpose;
					var lAmount = json[i].lAmount;
					var reqTime = zcsjFormat(json[i].reqTime);
					html += "<li class='list_li' style='float:left;'>";
					html += "<a href=\"#\" style='font-size:12px;font-family:MicrosoftYaHei;font-weight:bold; color:#666666' onclick=\"queryBorrow('"+lId+"')\">";
					html += "<span style='margin-left: 4%;width: 50%;height: 40%'>"+loanPurpose+"</span>";
					html += "<br><br><span id='loan7' class='div3'  style='float: left;height: 40%;margin-left: 4%;font-family:MicrosoftYaHei;font-weight:bold;color:#666666;'>¥"+fomatMoney(lAmount,2)+"</span>";
					html += "<span style='padding-top: 1.9%;font-weight:400;height: 40%;font-size:12px;font-family:MicrosoftYaHei;color:#999999;float: right;margin-right:4%'>"+reqTime+"</span>";
					html += "</a>";
					html += "</li>";
				}
				}
				$("#loan_ul").append(html);
				//我的借款走马灯
				var speed = 50;    //数值越大滚动速度越慢			
				var demo = document.getElementById("s1");
				var demo1 = document.getElementById("loan_ul");
				var demo2 = document.getElementById("loan_ul1");
				demo2.innerHTML = demo1.innerHTML;							
				demo.scrollTop = demo.scrollHeight + 'px';
				function Marquee() {
					if(demo.scrollTop >= demo1.offsetHeight) {
					demo.scrollTop = 0;
					} else {
					demo.scrollTop = demo.scrollTop + 1;
					}
				}
				 var MyMar1 = setInterval(Marquee, speed);
			     demo.onmouseover = function () { clearInterval(MyMar1)};
			     demo.onmouseout = function () { MyMar1 = setInterval(Marquee, speed)};
		}
	});
}
//时间格式化
function zcsjFormat(val) {
	//alert(val)
    var t, y, m, d, h, i, s;
    if(val==null){
  	  return "未填写";
    }
    t = new Date(val)
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) ;
}
</script>