<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>欢迎使用内控管理平台 </title>
<script src="${base}/resource-modality/js/jquery-1.11.3.min.js"></script>
<script src="${base}/resource/plugins/jquery.cookie.js"></script>
<script language="javascript">
	var loginMsg='${loginMsg}';
	if(loginMsg.length>0){
		alert(loginMsg);
	}
	$(document).ready(function(){
		if ($.cookie("rmbUser") == "true") {
            $("#rememberMe").attr("checked", false);
            $("#accountNo").val($.cookie("username"));
            $("#password").val($.cookie("password"));
        }
		//回车键登录
		document.onkeydown = function (event){
			if (event.keyCode==13){
				doSubmit();
			}
		};
	});
	document.onkeydown=function(e){
		var keycode=document.all?event.keyCode:e.which;
		if(keycode==13){
			doSubmit();
		}
	};
	function mouseover(){
		
		$('#Asubmit').css('background-color','#478AF6');
	}
	function mouseout(){
		
		$('#Asubmit').css('background-color','#2271F1');
	}
    function doSubmit(){
    	if ($("#rememberMe").prop("checked")) {
    	    var accountNo = $("#accountNo").val();//用户名
    	    var password = $("#password").val();//密码
    	    $.cookie("rmbUser", "true", { expires: 9999 }); //存储一个带7天期限的cookie
    	    $.cookie("username", accountNo, { expires: 9999 });
    	    $.cookie("password", password, { expires: 9999 });
    	} else {
    	    $.cookie("rmbUser", "false", { expire: -1 });
    	    $.cookie("username", "", { expires: -1 });
    	    $.cookie("password", "", { expires: -1 });
    	}
		document.getElementById("loginForm").submit();
	}
    function changeRememberPassword() {
    	var s = $('#remember-password1').attr('src').substring(34,36);
    	if(s=='f1'){
	    	$('#remember-password1').attr('src','${base}/resource-modality/img/login/o1.png');
    	} else {
    		$('#remember-password1').attr('src','${base}/resource-modality/img/login/f1.png');
    	}
    }
</script> 
<style type="text/css">
input:-webkit-autofill , textarea:-webkit-autofill, select:-webkit-autofill { 
    -webkit-text-fill-color: #ffffff !important;  
    -webkit-box-shadow: 0 0 0px 1000px transparent  inset !important;  
    background-color:transparent;  
    background-image: none;  
    transition: background-color 50000s ease-in-out 0s; //背景色透明  生效时长  过渡效果  启用时延迟的时间  
}  
input {  
     background-color:transparent;  
}
.text_put_div{
	width: 64%;margin: 0 auto;padding:7px 0;height: 30px;
}
/* 判断显示屏宽度大于1300的显示大图、小于1300的显示方图 */
/* .cssDiv{overflow:hidden;background-size:100% 100% ; background-attachment: fixed;font-family:Microsoft YaHei;color:#ffffff;}
@media screen and (min-width:1301px){
.cssDiv{ background-image:url(${base}/resource-modality/img/login/register_bg1.png); 
}
}
@media screen and (max-width: 1300px){ 
.cssDiv{ background-image:url(${base}/resource-modality/img/login/register_bg2.png); 
}
 } */
.cssDiv{ 
	background: linear-gradient(175deg, #375BFD 0%, #01CFFF 100%, #000000 100%);
	opacity: 1;
	font-family:Microsoft YaHei;
	color:#ffffff;
	margin: 0;
}
.banner_header{
	width: 100%;
	height: 93px;
	background-image:url(${base}/resource-modality/img/login/banner.png);
	text-align: center;
}
.logo_img{
	width: 434px;
	height: 59px;
	margin-top: 1.1%;
}

input::-webkit-input-placeholder, textarea::-webkit-input-placeholder {
  color: #FFFFFF;
  font-size: 14px;
}

input:-moz-placeholder, textarea:-moz-placeholder {
  color: #FFFFFF;
  font-size: 14px;
}

input::-moz-placeholder, textarea::-moz-placeholder {
  color: #FFFFFF;
  font-size: 14px;
}

input:-ms-input-placeholder, textarea:-ms-input-placeholder {
  color: #FFFFFF;
  font-size: 14px;
}
</style>
</head>

<body class="cssDiv" >
<div class="banner_header">
	<img class="logo_img" alt="" src="${base}/resource-modality/img/login/bn_logo.png">
</div>
<div style="margin:0 auto;background-image:url(${base}/resource-modality/img/login/login_bg.png);background-repeat: no-repeat;">
	<form id="loginForm" action="${base}/login.do" style="border-radius:4px;margin:0 auto;margin-top: 6%;width:490px;height:294px;background-image:url(${base}/resource-modality/img/login/login_img.png);border:1px solid rgba(255,255,255,0.5);-webkit-box-shadow: 0 0 5px rgba(255,255,255,0.7);" method="post">
		<div style="width:100%;height:60%;margin-top: 12%;">
			<div class="text_put_div" style="border-bottom:1px solid #ffffff;">&nbsp;
				<span><img src="${base}/resource-modality/img/login/username.png" style="margin-top: 10px;margin-left: -5px;"></span>&nbsp;
				<input id="accountNo" name="accountNo" type="text" style="font-color:#ffffff;font-size:18px;margin-left: 10px;margin-top: -25px;outline: none;width:80%;height:25px;border:none;color: #ffffff;" autocomplete="off" placeholder="请输入用户名"/>
			</div>
			<div class="text_put_div" style="border-bottom:1px solid #ffffff;">&nbsp;
				<span ><img src="${base}/resource-modality/img/login/password.png" style="margin-top: 10px;margin-left: -5px;"></span>&nbsp;
				<input id="password" name="password" type="password" style="font-color:#ffffff;font-size:18px;margin-left: 10px;margin-top: -25px;outline: none;width:80%;height:25px;border:none;color: #ffffff" placeholder="请输入密码"/>
			</div>
			<div class="text_put_div" style="margin-top: 0px;">
				<input type="checkbox" name="rememberMe" id="rememberMe"/>
				<span style="font-size: 14px;">记住密码</span>
			</div> 
			<div class="text_put_div" style="margin-top: 0px;">
				<div style="text-align: center;">
					<a id="Asubmit" href="#" onclick="doSubmit()" style="width:300px;height:34px;text-align:center;text-decoration:none;background-color:#FFFFFF;border-radius:4px;display:block;line-height:34px;" >
						<span style="width:28px;height:19px;font-size:14px;font-family:Microsoft YaHei;text-align:center;font-weight:400;color:#333333;letter-spacing: 20px;">登录</span>
					</a>
				</div>
			</div>
		</div>
	</form>
	<div style="width:100%;height:9%;text-align: center;position: absolute;bottom: 20px;opacity:0.7;color:#2AFDFE;font-size: 15px;">全面预算  |  绩效一体  |  内控嵌套  |  管财融合  |  智能分析</div>
	<div style="width:100%;height:9%;text-align: center;position: absolute;bottom: 0;opacity:0.7;color:#2AFDFE;font-size: 15px;">技术支持：天职（上海）企业管理咨询有限公司</div>
</div>
</body>
</html>
