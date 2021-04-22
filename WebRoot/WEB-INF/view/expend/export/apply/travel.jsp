<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="ProgId" content="Excel.Sheet">
  <meta name="Generator" content="WPS Office ET">
      <!-- jquery.js  -->
	<script type="text/javascript" src="${base}/resource/js/jquery.js"></script>
	<!-- jquery.min.js   -->
	<script type="text/javascript" src="${base}/resource/ui/jquery.min.js"></script>
		
  <style>
<!-- @page
	{margin:1.00in 1.00in 1.00in 1.00in;
	mso-header-margin:0.50in;
	mso-footer-margin:0.50in;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.font0
	{color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font1
	{color:#000000;
	font-size:20.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font2
	{color:#000000;
	font-size:14.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font3
	{color:#000000;
	font-size:14.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font4
	{color:#FF0000;
	font-size:14.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font5
	{color:#D9D9D9;
	font-size:72.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font6
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font7
	{color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font8
	{color:#3F3F3F;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font9
	{color:#3F3F76;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font10
	{color:#9C0006;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font11
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font12
	{color:#FF0000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font13
	{color:#0000FF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font14
	{color:#800080;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font15
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font16
	{color:#44546A;
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font17
	{color:#44546A;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font18
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font19
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font20
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font21
	{color:#006100;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font22
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font23
	{color:#9C6500;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font24
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.style0
	{mso-number-format:"General";
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	mso-pattern:auto;
	mso-background-source:auto;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:"常规";
	mso-style-id:0;}
.style16
	{mso-number-format:"_ \0022\00A5\0022* \#\,\#\#0_ \;_ \0022\00A5\0022* \\-\#\,\#\#0_ \;_ \0022\00A5\0022* \0022-\0022_ \;_ \@_ ";
	mso-style-name:"货币[0]";
	mso-style-id:7;}
.style17
	{mso-pattern:auto none;
	background:#EDEDED;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 3";}
.style18
	{mso-pattern:auto none;
	background:#FFCC99;
	color:#3F3F76;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border:.5pt solid #7F7F7F;
	mso-style-name:"输入";}
.style19
	{mso-number-format:"_ \0022\00A5\0022* \#\,\#\#0\.00_ \;_ \0022\00A5\0022* \\-\#\,\#\#0\.00_ \;_ \0022\00A5\0022* \0022-\0022??_ \;_ \@_ ";
	mso-style-name:"货币";
	mso-style-id:4;}
.style20
	{mso-number-format:"_ * \#\,\#\#0_ \;_ * \\-\#\,\#\#0_ \;_ * \0022-\0022_ \;_ \@_ ";
	mso-style-name:"千位分隔[0]";
	mso-style-id:6;}
.style21
	{mso-pattern:auto none;
	background:#DBDBDB;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 3";}
.style22
	{mso-pattern:auto none;
	background:#FFC7CE;
	color:#9C0006;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"差";}
.style23
	{mso-number-format:"_ * \#\,\#\#0\.00_ \;_ * \\-\#\,\#\#0\.00_ \;_ * \0022-\0022??_ \;_ \@_ ";
	mso-style-name:"千位分隔";
	mso-style-id:3;}
.style24
	{mso-pattern:auto none;
	background:#C9C9C9;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 3";}
.style25
	{color:#0000FF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"超链接";
	mso-style-id:8;}
.style26
	{mso-number-format:"0%";
	mso-style-name:"百分比";
	mso-style-id:5;}
.style27
	{color:#800080;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"已访问的超链接";
	mso-style-id:9;}
.style28
	{mso-pattern:auto none;
	background:#FFFFCC;
	border:.5pt solid #B2B2B2;
	mso-style-name:"注释";}
.style29
	{mso-pattern:auto none;
	background:#F4B084;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 2";}
.style30
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-style-name:"标题 4";}
.style31
	{color:#FF0000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"警告文本";}
.style32
	{color:#44546A;
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-style-name:"标题";}
.style33
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"解释性文本";}
.style34
	{color:#44546A;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid #5B9BD5;
	mso-style-name:"标题 1";}
.style35
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid #5B9BD5;
	mso-style-name:"标题 2";}
.style36
	{mso-pattern:auto none;
	background:#9BC2E6;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 1";}
.style37
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid #ACCCEA;
	mso-style-name:"标题 3";}
.style38
	{mso-pattern:auto none;
	background:#FFD966;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 4";}
.style39
	{mso-pattern:auto none;
	background:#F2F2F2;
	color:#3F3F3F;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border:.5pt solid #3F3F3F;
	mso-style-name:"输出";}
.style40
	{mso-pattern:auto none;
	background:#F2F2F2;
	color:#FA7D00;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border:.5pt solid #7F7F7F;
	mso-style-name:"计算";}
.style41
	{mso-pattern:auto none;
	background:#A5A5A5;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border:2.0pt double #3F3F3F;
	mso-style-name:"检查单元格";}
.style42
	{mso-pattern:auto none;
	background:#E2EFDA;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 6";}
.style43
	{mso-pattern:auto none;
	background:#ED7D31;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 2";}
.style44
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:2.0pt double #FF8001;
	mso-style-name:"链接单元格";}
.style45
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border-left:none;
	border-top:.5pt solid #5B9BD5;
	border-right:none;
	border-bottom:2.0pt double #5B9BD5;
	mso-style-name:"汇总";}
.style46
	{mso-pattern:auto none;
	background:#C6EFCE;
	color:#006100;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"好";}
.style47
	{mso-pattern:auto none;
	background:#FFEB9C;
	color:#9C6500;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"适中";}
.style48
	{mso-pattern:auto none;
	background:#D9E1F2;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 5";}
.style49
	{mso-pattern:auto none;
	background:#5B9BD5;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 1";}
.style50
	{mso-pattern:auto none;
	background:#DDEBF7;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 1";}
.style51
	{mso-pattern:auto none;
	background:#BDD7EE;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 1";}
.style52
	{mso-pattern:auto none;
	background:#FCE4D6;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 2";}
.style53
	{mso-pattern:auto none;
	background:#F8CBAD;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 2";}
.style54
	{mso-pattern:auto none;
	background:#A5A5A5;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 3";}
.style55
	{mso-pattern:auto none;
	background:#FFC000;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 4";}
.style56
	{mso-pattern:auto none;
	background:#FFF2CC;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 4";}
.style57
	{mso-pattern:auto none;
	background:#FFE699;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 4";}
.style58
	{mso-pattern:auto none;
	background:#4472C4;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 5";}
.style59
	{mso-pattern:auto none;
	background:#B4C6E7;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 5";}
.style60
	{mso-pattern:auto none;
	background:#8EA9DB;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 5";}
.style61
	{mso-pattern:auto none;
	background:#70AD47;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 6";}
.style62
	{mso-pattern:auto none;
	background:#C6E0B4;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 6";}
.style63
	{mso-pattern:auto none;
	background:#A9D08E;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 6";}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	mso-number-format:"General";
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	mso-pattern:auto;
	mso-background-source:auto;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;}
.xl65
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:top;
	font-size:20.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;}
.xl66
	{mso-style-parent:style0;
	text-align:left;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl67
	{mso-style-parent:style0;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl68
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl168
	{mso-style-parent:style0;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl69
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl70
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl71
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl72
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl73
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl74
	{mso-style-parent:style0;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl75
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl76
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl77
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl78
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl79
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl80
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	color:#FF0000;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl81
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl82
	{mso-style-parent:style0;
	text-align:justify;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl83
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl84
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl85
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl86
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl87
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;}
.xl88
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;}
.xl89
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl90
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;}
.xl91
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;}
.xl92
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl93
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;}
.xl94
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl95
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;}
.xl96
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;}
.xl97
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl98
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl99
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-bottom:.5pt solid windowtext;}
.xl100
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl101
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl102
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-bottom:.5pt solid windowtext;}
.xl103
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl104
	{mso-style-parent:style0;
	white-space:normal;
	color:#D9D9D9;
	font-size:72.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl105
	{mso-style-parent:style0;
	text-align:right;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl106
	{mso-style-parent:style0;
	text-align:right;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl107
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl108
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl109
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl110
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl111
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl112
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl113
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;}
.xl114
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl115
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;}
 -->  </style>
 </head>
 <body link="blue" vlink="purple">
  <table width="1249" border="0" cellpadding="0" cellspacing="0" style='width:936.75pt;border-collapse:collapse;table-layout:fixed;'>
   <col width="182" style='mso-width-source:userset;mso-width-alt:5824;'/>
   <col width="75" style='mso-width-source:userset;mso-width-alt:2400;'/>
   <col width="69" style='mso-width-source:userset;mso-width-alt:2208;'/>
   <col width="64" style='mso-width-source:userset;mso-width-alt:2048;'/>
   <col width="37" style='mso-width-source:userset;mso-width-alt:1184;'/>
   <col width="73" style='mso-width-source:userset;mso-width-alt:2336;'/>
   <col width="62" style='mso-width-source:userset;mso-width-alt:1984;'/>
   <col width="83" style='mso-width-source:userset;mso-width-alt:2656;'/>
   <col width="49" style='mso-width-source:userset;mso-width-alt:1568;'/>
   <col width="26" style='mso-width-source:userset;mso-width-alt:832;'/>
   <col width="169" style='mso-width-source:userset;mso-width-alt:5408;'/>
   <col width="72" span="5" style='width:54.00pt;'/>
   <tr height="80" style='height:60.00pt;mso-height-source:userset;mso-height-alt:1200;'>
    <td class="xl65" height="80" width="889" colspan="11" style='height:60.00pt;width:666.75pt;border-right:none;border-bottom:none;' x:str>天津市司法局公务出差申请单</td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
   </tr>
  <%--  <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl66" height="33.27" colspan="8" style='height:24.95pt;mso-ignore:colspan;' x:str>摘要：${bean.gName}<span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
    <td class="xl106" colspan="3" style='border-right:none;border-bottom:none;' x:str>单据编号：${bean.gCode}<span style='display:none;'></span></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr> --%>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl67" height="33.27" style='height:24.95pt;' x:str><font class="font2">摘要：${bean.gName}</font><font class="font4"><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></font></td>
    <td class="xl168"></td>
    <td class="xl168"></td>
    <td class="xl168"></td>
    <td class="xl168"></td>
    <td class="xl168"></td>
    <td class="xl95" colspan="6" style='border-right:none;border-bottom:none;padding-left: 50px' x:str>单据编号：${bean.gCode}</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl66" height="33.27" colspan="8" style='height:24.95pt;mso-ignore:colspan;' x:str>申请日期：<fmt:formatDate value="${bean.reqTime}" pattern="yyyy-MM-dd"/><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span style='display:none;'><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></td>
    <td class="xl106" colspan="3" style='border-right:none;border-bottom:none;'></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>申请人姓名</td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.userNames}</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>部门（处室）</td>
    <td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.deptName}</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>出差事由</td>
    <td class="xl68" colspan="10" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.reason}</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl73" height="33.27" colspan="11" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>行程清单</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="48" style='height:36.00pt;mso-height-source:userset;mso-height-alt:720;'>
    <td class="xl68" height="48" style='height:36.00pt;' x:str>出发时间</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>撤离时间/抵津时间</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>目的地</td>
    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>人员(可多选)</td>
    <td class="xl70" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>主要工作内容</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <c:forEach items="${travellist}" var="travelbean">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;'><fmt:formatDate value="${travelbean.travelDateStart}" pattern="yyyy-MM-dd"/></td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${travelbean.travelDateEnd}" pattern="yyyy-MM-dd"/></td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${travelbean.travelAreaName}</td>
    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${travelbean.travelAttendPeop}</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${travelbean.reason}</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:forEach>
   <c:if test="${empty travellist}">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl73" height="33.27" colspan="11" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用明细</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl75" height="33.27" colspan="11" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>城市间交通费</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="46.67" style='height:35.00pt;mso-height-source:userset;mso-height-alt:700;'>
    <td class="xl77" height="46.67" style='height:35.00pt;' x:str>出发时间</td>
    <td class="xl78" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>到达时间</td>
    <td class="xl78" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>出行人员</td>
    <td class="xl78" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>交通工具</td>
    <td class="xl78" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>交通工具级别</td>
    <td class="xl77" x:str>申请金额</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <c:forEach items="${outsidelist}" var="outsidebean">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" style='height:24.95pt;'><fmt:formatDate value="${outsidebean.fStartDate}" pattern="yyyy-MM-dd"/></td>
    <td colspan="2" class="xl78"><fmt:formatDate value="${outsidebean.fEndDate}" pattern="yyyy-MM-dd"/></td>
    <td colspan="2" class="xl78">${outsidebean.vehicle }</td>
    <td colspan="2" class="xl78">${outsidebean.vehicleLevel }</td>
    <td colspan="3" class="xl78">${outsidebean.travelPersonnel }</td>
    <td class="xl77"><fmt:formatNumber groupingUsed="true" value="${outsidebean.applyAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:forEach>
   <c:if test="${empty outsidelist}">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" style='height:24.95pt;'>-</td>
    <td colspan="2" class="xl78">-</td>
    <td colspan="2" class="xl78">-</td>
    <td colspan="2" class="xl78">-</td>
    <td colspan="3" class="xl78">-</td>
    <td class="xl77">-</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl80" height="33.27" colspan="10" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>提示：如购买机票需从政府采购机票网站使用公务卡购买</td>
    <td class="xl110" x:str>小计金额：（元）<fmt:formatNumber groupingUsed="true" value="${bean.outsideAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl82" height="33.27" colspan="11" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>市内交通费</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>姓名</td>
    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>补贴天数</td>
    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>补贴费用</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <c:forEach items="${inCitylist}" var="inCitybean">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;'>${inCitybean.fPerson }</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${inCitybean.fSubsidyDay }</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${inCitybean.fApplyAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:forEach>
   <c:if test="${empty inCitylist}">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td colspan="5" style='mso-ignore:colspan;'>-</td>
   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl74" height="33.27" style='height:24.95pt;'></td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl72" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>小计金额：（元）<fmt:formatNumber groupingUsed="true" value="${bean.cityAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl82" height="33.27" colspan="11" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>住宿费</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>入住日期</td>
    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>退房日期</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>所在城市</td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>住宿人员</td>
    <td class="xl74" x:str>申请金额</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <c:forEach items="${hotellist}" var="hotelbean">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" style='height:24.95pt;'><fmt:formatDate value="${hotelbean.checkInTime}" pattern="yyyy-MM-dd"/></td>
    <td class="xl72" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${hotelbean.checkOUTTime}" pattern="yyyy-MM-dd"/></td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotelbean.locationCity }</td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotelbean.travelPersonnel }</td>
    <td class="xl72"><fmt:formatNumber groupingUsed="true" value="${hotelbean.applyAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:forEach>
   <c:if test="${empty hotellist}">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl72" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl72">-</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" style='height:24.95pt;'></td>
    <td class="xl72" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl72" x:str>小计金额：（元）<fmt:formatNumber groupingUsed="true" value="${bean.hotelAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl83" height="33.27" colspan="11" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>伙食补助费</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" style='height:24.95pt;' x:str>姓名</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>补贴天数</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>补贴费用</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   	<c:forEach items="${foodlist}" var="foodbean">
   	
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" style='height:24.95pt;'>${foodbean.fname }</td>
    <td class="xl69"></td>
    <td class="xl70" colspan="3" style='mso-ignore:colspan;'>${foodbean.fDays }</td>
    <td class="xl71"></td>
    <td class="xl69"></td>
    <td class="xl70" colspan="3" style='mso-ignore:colspan;'><fmt:formatNumber groupingUsed="true" value="${foodbean.fApplyAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td class="xl71"></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:forEach>
   <c:if test="${empty foodlist}">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl69"></td>
    <td class="xl70" colspan="3" style='mso-ignore:colspan;'>-</td>
    <td class="xl71"></td>
    <td class="xl69"></td>
    <td class="xl70" colspan="3" style='mso-ignore:colspan;'>-</td>
    <td class="xl71"></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" style='height:24.95pt;'></td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl70" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>小计金额：（元）<fmt:formatNumber groupingUsed="true" value="${bean.foodAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>合计</td>
    <td class="xl72" colspan="10" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${bean.amount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="46" style='height:34.50pt;mso-height-source:userset;mso-height-alt:690;'>
    <td class="xl68" height="46" style='height:34.50pt;' x:str>合计金额（大写）</td>
    <td class="xl72" colspan="10" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.applyAmountcapital }整</td>
    <td colspan="5" style='mso-ignore:colspan;'></td>
   </tr>
       <td class="xl86" rowspan="12" style='height:520.00pt;border:.5pt solid windowtext' x:str>审签</td>
    <c:forEach items="${listTProcessChecks}" var="check">
	   <tr height="33.27" style='height:50pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl87" colspan="2" rowspan="2" style='height:69.80pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>${check.fRoleName }</td>
	    <td class="xl115" colspan="8" rowspan="1" style='border-right:.5pt solid windowtext;border-bottom: none'>${check.fcheckRemake }</td>
	   </tr>
	   <tr height="33.27" style='height:50pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl90" colspan="8" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;border-top: none' x:str>
	    <c:if test="${!empty check.fuserId}">
	    <span style='mso-spacerun:yes;'><img style="vertical-align:bottom;width: 150px; height: 60px;margin-left:0px;margin-bottom: 10px"  src="${base}/attachment/downloadQZ/${check.fuserId}"/></span><fmt:formatDate value="${check.fcheckTime }" pattern="yyyy年MM月dd日"/>
	    </c:if>
	    <c:if test="${empty check.fuserId}">
	    <span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;年&nbsp;月&nbsp;日
	    </c:if>
	    </td>
	   </tr>
   </c:forEach>
  </table>
<script type="text/javascript">
  $(document).ready(function() {
		window.print();
	}); 
</script>
 </body>
</html>


