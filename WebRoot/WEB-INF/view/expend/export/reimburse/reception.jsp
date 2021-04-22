<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta name="ProgId" content="Excel.Sheet"/>
  <meta name="Generator" content="WPS Office ET"/>
  <!-- jquery.js  -->
	<script type="text/javascript" src="${base}/resource/js/jquery.js"></script>
	<!-- jquery.min.js   -->
	<script type="text/javascript" src="${base}/resource/ui/jquery.min.js"></script>	
  <style>
<!-- @page
	{margin:0.75in 0.51in 0.75in 0.76in;
	mso-header-margin:0.30in;
	mso-footer-margin:0.30in;}
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
	font-size:22.0pt;
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
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font4
	{color:#000000;
	font-size:14.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"Calibri";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font5
	{color:#000000;
	font-size:14.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font6
	{color:#800080;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
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
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font9
	{color:#0000FF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
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
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font12
	{color:#3F3F76;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font13
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font14
	{color:#44546A;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
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
	{color:#FF0000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font17
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font18
	{color:#44546A;
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font19
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font20
	{color:#3F3F3F;
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
	font-weight:700;
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
	{color:#FFFFFF;
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
	font-size:22.0pt;
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
	mso-font-charset:134;}
.xl68
	{mso-style-parent:style0;
	text-align:right;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl69
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-generic-font-family:auto;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl70
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl71
	{mso-style-parent:style0;
	text-align:justify;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl72
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
.xl73
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
.xl74
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
.xl75
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl76
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
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
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl78
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl79
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl80
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl81
	{mso-style-parent:style0;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl82
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl83
	{mso-style-parent:style0;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl84
	{mso-style-parent:style0;
	white-space:normal;
	mso-font-charset:134;}
 -->  </style>
  <!--[if gte mso 9]>
   <xml>
    <x:ExcelWorkbook>
     <x:ExcelWorksheets>
      <x:ExcelWorksheet>
       <x:Name>Sheet1</x:Name>
       <x:WorksheetOptions>
        <x:DefaultRowHeight>285</x:DefaultRowHeight>
        <x:Selected/>
        <x:TopRowVisible>32</x:TopRowVisible>
        <x:Panes>
         <x:Pane>
          <x:Number>3</x:Number>
          <x:ActiveCol>11</x:ActiveCol>
          <x:ActiveRow>18</x:ActiveRow>
          <x:RangeSelection>L19</x:RangeSelection>
         </x:Pane>
        </x:Panes>
        <x:DoNotDisplayGridlines/>
        <x:ProtectContents>False</x:ProtectContents>
        <x:ProtectObjects>False</x:ProtectObjects>
        <x:ProtectScenarios>False</x:ProtectScenarios>
        <x:ShowPageBreakZoom/>
        <x:PageBreakZoom>100</x:PageBreakZoom>
        <x:Zoom>50</x:Zoom>
        <x:Print>
         <x:ValidPrinterInfo/>
         <x:PaperSizeIndex>9</x:PaperSizeIndex>
         <x:Scale>51</x:Scale>
         <x:HorizontalResolution>300</x:HorizontalResolution>
         <x:VerticalResolution>300</x:VerticalResolution>
        </x:Print>
       </x:WorksheetOptions>
       <x:PageBreaks>
        <x:RowBreaks>
         <x:RowBreak>
          <x:Row>54</x:Row>
         </x:RowBreak>
        </x:RowBreaks>
       </x:PageBreaks>
      </x:ExcelWorksheet>
     </x:ExcelWorksheets>
     <x:ProtectStructure>False</x:ProtectStructure>
     <x:ProtectWindows>False</x:ProtectWindows>
     <x:SelectedSheets>0</x:SelectedSheets>
     <x:WindowHeight>12540</x:WindowHeight>
     <x:WindowWidth>28800</x:WindowWidth>
    </x:ExcelWorkbook>
   </xml>
  <![endif]-->
 </head>
 <body link="blue" vlink="purple">
  <table width="960.47" border="0" cellpadding="0" cellspacing="0" style='width:720.35pt;border-collapse:collapse;table-layout:fixed;'>
   <col width="170" style='mso-width-source:userset;mso-width-alt:5440;'/>
   <col width="66" style='mso-width-source:userset;mso-width-alt:2112;'/>
   <col width="77" style='mso-width-source:userset;mso-width-alt:2464;'/>
   <col width="93" style='mso-width-source:userset;mso-width-alt:2976;'/>
   <col width="54" style='mso-width-source:userset;mso-width-alt:1728;'/>
   <col width="29.53" style='mso-width-source:userset;mso-width-alt:945;'/>
   <col width="30" style='mso-width-source:userset;mso-width-alt:960;'/>
   <col width="23.80" style='mso-width-source:userset;mso-width-alt:761;'/>
   <col width="46" style='mso-width-source:userset;mso-width-alt:1472;'/>
   <col width="18.13" style='mso-width-source:userset;mso-width-alt:580;'/>
   <col width="113" style='mso-width-source:userset;mso-width-alt:3616;'/>
   <col width="168" style='mso-width-source:userset;mso-width-alt:5376;'/>
   <col width="72" style='width:54.00pt;'/>
   <tr height="76" style='height:57.00pt;mso-height-source:userset;mso-height-alt:1140;'>
    <td class="xl65" height="76" width="888.47" colspan="12" style='height:57.00pt;width:666.35pt;border-right:none;border-bottom:none;' x:str>天津市司法局公务接待报销单</td>
    <td width="72" style='width:54.00pt;'></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl66" height="34.67" style='height:26.00pt;' x:str><font class="font2">摘要：</font><font class="font4"><span style='mso-spacerun:yes;'>${bean.gName}</span></font></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl68" colspan="8" style='border-right:none;border-bottom:none;' x:str>单据编号：${bean.gCode}</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl66" height="34.67" style='height:26.00pt;' x:str><font class="font2">报销日期：</font><font class="font4"><span style='mso-spacerun:yes;'><fmt:formatDate value="${bean.reimburseReqTime}" pattern="yyyy-MM-dd"/></span></font></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl68" colspan="4" style='border-right:none;border-bottom:none;' x:str>附单据<span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp; </span>张</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>报销人姓名</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.userName}</td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>部门（处室）</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.deptName}</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>接待对象单位</td>
    <td class="xl69" colspan="11" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${receptionBean.receptionObject}</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>接待类型</td>
    <td class="xl70" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>
    <c:if test="${receptionBean.receptionLevel=='1'}">出席会议</c:if>
		 <c:if test="${receptionBean.receptionLevel=='2'}">考察调研</c:if>
		 <c:if test="${receptionBean.receptionLevel=='3'}">执行任务</c:if>
		 <c:if test="${receptionBean.receptionLevel=='4'}">学习交流</c:if>
		 <c:if test="${receptionBean.receptionLevel=='5'}">检查指导</c:if>
		 <c:if test="${receptionBean.receptionLevel=='6'}">请示汇报工作</c:if>
		 <c:if test="${receptionBean.receptionLevel=='7'}">其他</c:if>
    </td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>接待对象人数</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${receptionBean.rePeopNum}</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>开始日期</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${receptionBean.reDateStart}" pattern="yyyy-MM-dd"/></td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>结束日期</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${receptionBean.reDateEnd}" pattern="yyyy-MM-dd"/></td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>接待内容</td>
    <td class="xl70" colspan="11" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${receptionBean.receptionContent}</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>调整事项说明</td>
    <td class="xl70" colspan="11" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.fupdateReason}</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>接待对象</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="3" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>单位</td>
    <td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>姓名</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>职务</td>
    <td></td>
   </tr>
   <c:if test="${!empty objectListTwo}">
	   <c:forEach items="${objectListTwo }" var="objectList">
	   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl69" height="34.67" colspan="3" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${objectList.government}</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${objectList.receptionPeopName}</td>
    	<td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${objectList.position}</td>
    	<td></td>
   	   </tr>
	   </c:forEach>
   </c:if>
   <c:if test="${empty objectListTwo }">
	  <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl69" height="34.67" colspan="3" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td></td>
   </tr>
   </c:if>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>主要行程安排</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="3" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>项目</td>
    <td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>时间</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>场所</td>
    <td></td>
   </tr>
   <c:if test="${!empty xcList}">
	   <c:forEach items="${xcList }" var="xc">
	   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl69" height="34.67" colspan="3" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${xc.fPro}</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${xc.fTime}" pattern="yyyy-MM-dd"/></td>
    	<td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${xc.fAddress}</td>
    	<td></td>
   	   </tr>
	   </c:forEach>
   </c:if>
   <c:if test="${empty xcList}">
	  <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl69" height="34.67" colspan="3" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td></td>
   </tr>
   </c:if>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用明细</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>住宿费</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="1" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>姓名</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>职务/级别</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>住宿房型</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>住宿天数（天）</td>
    <td class="xl69" x:str>报销金额（元）</td>
    <td></td>
   </tr>
   <c:if test="${!empty listHotel }">
   <c:forEach items="${listHotel }" var="hotel">
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="1" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotel.fName }</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotel.position }</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotel.fRoomType }</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotel.fDays }</td>
    <td class="xl69"><fmt:formatNumber groupingUsed="true" value="${hotel.fCostHotel }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td></td>
   </tr>
   </c:forEach>
   </c:if>
   <c:if test="${empty listHotel}">
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="1" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69">-</td>
    <td></td>
   </tr>
   </c:if>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="1" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
    <td class="xl112" style='border-right:.5pt solid windowtext;'>小计金额：<fmt:formatNumber groupingUsed="true" value="${receptionBeanEdit.costHotel}" minFractionDigits="2" maxFractionDigits="2"/>(元)</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>餐费</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>日期</td>
    <td class="xl69" x:str>地点</td>
    <td class="xl69" x:str>类别</td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>接待人数</td>
    <td class="xl69" x:str>陪餐人数</td>
    <td class="xl69" x:str>报销金额（元）</td>
    <td></td>
   </tr>
   <c:if test="${!empty listFood }">
   <c:forEach items="${listFood }" var="food">
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${food.foodTime}" pattern="yyyy-MM-dd"/></td>
    <td class="xl69">${food.address}</td>
    <td class="xl69">${food.fFoodType}</td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${food.fPersonNum}</td>
    <td class="xl69">${food.attendPeopNum}</td>
    <td class="xl69"><fmt:formatNumber groupingUsed="true" value="${food.fCostFood }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td></td>
   </tr>
   </c:forEach>
   </c:if>
   <c:if test="${empty listFood }">
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69">-</td>
    <td class="xl69">-</td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69">-</td>
    <td class="xl69">-</td>
    <td></td>
   </tr>
   </c:if>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="11" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>合计金额（小写）</td>
    <td class="xl69"><fmt:formatNumber groupingUsed="true" value="${receptionBeanEdit.costFood}" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>会议室租金报销金额（元）</td>
    <td class="xl69" colspan="10" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${receptionBean.costRent}" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>其他费用</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>费用名称</td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（元）</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>备注</td>
    <td></td>
   </tr>
   <c:if test="${!empty listOther }">
    <c:forEach items="${listOther }" var="other">
    <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;'>${other.fCostName }</td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${other.fCost }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${other.fRemark }</td>
    <td></td>
   </tr>
    </c:forEach>
   </c:if>
   <c:if test="${empty listOther }">
    <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;'>-</td>
    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td></td>
   </tr>
   </c:if>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>合计金额（小写）</td>
    <td class="xl69" colspan="11" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${receptionBean.costOther}" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>收费明细</td>
    <td></td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>日期</td>
    <td class="xl69" x:str>早餐人数</td>
    <td class="xl69" x:str>午餐人数</td>
    <td class="xl69" x:str>晚餐人数</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>半天用车人数</td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>全天用车人数</td>
    <td class="xl69" x:str>当日餐费合计</td>
    <td class="xl69" x:str>当日车费合计</td>
    <td></td>
   </tr>
   <c:if test="${!empty sfList}">
	   <c:forEach items="${sfList}" var="sf">
		 <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${sf.fcTime}" pattern="yyyy-MM-dd"/></td>
    <td class="xl69">${sf.breakfastPeopleNum}</td>
    <td class="xl69">${sf.lunchPeopleNum}</td>
    <td class="xl69">${sf.dinnerPeopleNum}</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${sf.halfDayPeopleNum}</td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${sf.dayPeopleNum}</td>
    <td class="xl69"><fmt:formatNumber groupingUsed="true" value="${sf.totalMeal}" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td class="xl69"><fmt:formatNumber groupingUsed="true" value="${sf.totalFare}" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td></td>
   </tr>
	</c:forEach>
   </c:if>
   <c:if test="${empty sfList}">
	  <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69">-</td>
    <td class="xl69">-</td>
    <td class="xl69">-</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl69">-</td>
    <td class="xl69">-</td>
    <td></td>
   </tr>
   </c:if>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>合计金额（小写）</td>
    <td class="xl69" colspan="11" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${bean.amount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>合计金额（大写）</td>
    <td class="xl69" colspan="11" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.reimAmountcapital }整</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>冲销借款情况</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl75" height="34.67" colspan="6" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>借款单据</td>
    <td class="xl75" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>冲销金额</td>
    <td></td>
   </tr>
   <c:if test="${!empty bean.withLoan && bean.withLoan==1 }">
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl75" height="34.67" colspan="6" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.loan.lCode}</td>
    <td class="xl75" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.cxAmount}</td>
    <td></td>
   </tr>
   </c:if>
     <c:if test="${empty bean.withLoan || bean.withLoan==0 }">
  <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl75" height="34.67" colspan="6" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl75" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td></td>
   </tr>
   </c:if>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>单位内部收款人信息</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>收款人</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>银行账号</td>
    <td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>开户银行</td>
    <td class="xl69" x:str>转账金额</td>
    <td></td>
   </tr>
   <c:if test="${!empty listPayee}">
	   <c:forEach items="${listPayee }" var="payee">
	   <c:if test="${payee.fInnerOrOuter eq '0'}">
	    <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl69" height="34.67" style='height:26.00pt;'>${payee.payeeName }</td>
    	<td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${payee.bankAccount}</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${payee.bank}</td>
    	<td class="xl69" >${payee.payeeAmount }</td>
    	<td></td>
   		</tr>
   		 </c:if>
   	  </c:forEach>
   </c:if>
   <c:if test="${index1 == '0'}">
     <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl69" height="34.67" style='height:26.00pt;'>-</td>
    	<td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" >-</td>
    	<td></td>
   		</tr>
   </c:if>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="12" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>单位外部收款人信息</td>
    <td></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl69" height="34.67" style='height:26.00pt;' x:str>收款人</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>银行账号</td>
    <td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>开户银行</td>
    <td class="xl69" x:str>转账金额</td>
    <td></td>
   </tr>
   <c:if test="${!empty listPayee}">
	   <c:forEach items="${listPayee }" var="payee">
	   <c:if test="${payee.fInnerOrOuter eq '1'}">
	    <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl69" height="34.67" style='height:26.00pt;'>${payee.payeeName }</td>
    	<td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${payee.bankAccount}</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${payee.bank}</td>
    	<td class="xl69" >${payee.payeeAmount }</td>
    	<td></td>
   		</tr>
   		 </c:if>
   	  </c:forEach>
   </c:if>
   <c:if test="${index2 == '0'}">
     <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl69" height="34.67" style='height:26.00pt;'>-</td>
    	<td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" >-</td>
    	<td></td>
   		</tr>
   </c:if>
    <td class="xl69"  height="600" rowspan="28" style='height:728.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>审签</td>
    <c:forEach items="${listTProcessChecks}" var="check">
	   <tr height="33.27" style='height:50pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl69" colspan="3" height="133.07" rowspan="2" style='height:69.80pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>${check.fRoleName }</td>
	    <td class="xl69" colspan="8" rowspan="1" style='border-right:.5pt solid windowtext;border-bottom:none;'>${check.fcheckRemake }</td>
	   </tr>
	   <tr height="33.27" style='height:50pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl80" colspan="8" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;border-top: none' x:str>
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
		window.setTimeout(function (){
			CloseAfterPrint();
		},250);
	});
	function CloseAfterPrint() {
		if (document.execCommand("print")) {
			window.open("${base}/exportApplyAndReim/requestApply?id="+${id});//
			window.open("${base}/exportApplyAndReim/PastePage?id="+ ${id});
		} else {
			window.close();
		}
	}
  </script>
 </body>
</html>
