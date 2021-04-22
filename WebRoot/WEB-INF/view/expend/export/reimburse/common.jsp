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
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font4
	{color:#000000;
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
	font-family:"等线";
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
	{color:#006100;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font8
	{color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font9
	{color:#9C0006;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font10
	{color:#3F3F76;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font11
	{color:#800080;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font12
	{color:#0000FF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font13
	{color:#3F3F3F;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font14
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font15
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
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
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font18
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font19
	{color:#44546A;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font20
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font21
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
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
	{color:#FA7D00;
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
	text-align:right;
	font-size:12.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl69
	{mso-style-parent:style0;
	text-align:right;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl70
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl71
	{mso-style-parent:style0;
	text-align:justify;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
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
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;}
.xl74
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;}
.xl75
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl76
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;}
.xl77
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl78
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl79
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-bottom:.5pt solid windowtext;}
.xl80
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;}
.xl81
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;}
.xl82
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl83
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;}
.xl84
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl85
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;}
.xl86
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl87
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-bottom:.5pt solid windowtext;}
.xl88
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl89
	{mso-style-parent:style0;
	color:#D9D9D9;
	font-size:72.0pt;
	mso-font-charset:134;}
.xl90
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl91
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;}
.xl92
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
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
        <x:TopRowVisible>9</x:TopRowVisible>
        <x:Panes>
         <x:Pane>
          <x:Number>3</x:Number>
          <x:ActiveCol>7</x:ActiveCol>
          <x:ActiveRow>39</x:ActiveRow>
          <x:RangeSelection>H40</x:RangeSelection>
         </x:Pane>
        </x:Panes>
        <x:DoNotDisplayGridlines/>
        <x:ProtectContents>False</x:ProtectContents>
        <x:ProtectObjects>False</x:ProtectObjects>
        <x:ProtectScenarios>False</x:ProtectScenarios>
        <x:ShowPageBreakZoom/>
        <x:PageBreakZoom>100</x:PageBreakZoom>
        <x:Zoom>75</x:Zoom>
        <x:Print>
         <x:ValidPrinterInfo/>
         <x:PaperSizeIndex>9</x:PaperSizeIndex>
         <x:Scale>88</x:Scale>
         <x:HorizontalResolution>300</x:HorizontalResolution>
         <x:VerticalResolution>300</x:VerticalResolution>
        </x:Print>
       </x:WorksheetOptions>
       <x:PageBreaks>
        <x:RowBreaks>
         <x:RowBreak>
          <x:Row>36</x:Row>
         </x:RowBreak>
         <x:RowBreak>
          <x:Row>36</x:Row>
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
    <x:ExcelName>
     <x:Name>Print_Area</x:Name>
     <x:SheetIndex>1</x:SheetIndex>
     <x:Formula>=Sheet1!$A$1:$I$36</x:Formula>
    </x:ExcelName>
   </xml>
  <![endif]-->
 </head>
 <body link="blue" vlink="purple">
  <table width="1179" border="0" cellpadding="0" cellspacing="0" style="width:884.25pt;border-collapse:collapse;table-layout:fixed;">
   <colgroup><col width="112" style="mso-width-source:userset;mso-width-alt:3584;">
   <col width="51" style="mso-width-source:userset;mso-width-alt:1632;">
   <col width="40" style="mso-width-source:userset;mso-width-alt:1280;">
   <col width="101" style="mso-width-source:userset;mso-width-alt:3232;">
   <col width="61" style="mso-width-source:userset;mso-width-alt:1952;">
   <col width="72" span="2" style="mso-width-source:userset;mso-width-alt:2304;">
   <col width="146" style="mso-width-source:userset;mso-width-alt:4672;">
   <col width="236" style="mso-width-source:userset;mso-width-alt:7552;">
   <col width="72" span="4" style="width:54.00pt;">
   </colgroup><tbody><tr height="34" style="height:25.50pt;">
    <td class="xl65" height="34" width="891" colspan="9" style="height:25.50pt;width:668.25pt;border-right:none;border-bottom:none;" x:str="">天津市司法局通用事项报销单</td>
    <td width="72" style="width:54.00pt;"></td>
    <td width="72" style="width:54.00pt;"></td>
    <td width="72" style="width:54.00pt;"></td>
    <td width="72" style="width:54.00pt;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl66" height="33.27" colspan="6" style="height:24.95pt;mso-ignore:colspan;" x:str="">摘要：${bean.gName}<span style="mso-spacerun:yes;"></span><span style="display:none;"><span style="mso-spacerun:yes;"></span></span></td>
    <td class="xl68" colspan="3" style="border-right:none;border-bottom:none;" x:str="">单据编号：${bean.gCode}</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl66" height="33.27" colspan="6" style="height:24.95pt;mso-ignore:colspan;" x:str="">报销时间：<span style="mso-spacerun:yes;"><fmt:formatDate value="${bean.reimburseReqTime}" pattern="yyyy-MM-dd"/></span></td>
    <td class="xl67"></td>
    <td class="xl69" colspan="2" style="border-right:none;border-bottom:none;" x:str="">附单据<span style="mso-spacerun:yes;">&nbsp;&nbsp;&nbsp;&nbsp; </span>张</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" height="33.27" style="height:24.95pt;" x:str="">报销人姓名</td>
    <td class="xl70" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${bean.userName}</td>
    <td class="xl70" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">部门（处室）</td>
    <td class="xl71" colspan="2" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${bean.deptName}</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" height="33.27" style="height:24.95pt;" x:str="">报销说明</td>
    <td class="xl71" colspan="8" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${bean.reimburseReason}</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl72" height="33.27" colspan="9" style="height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">费用明细</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" height="33.27" colspan="2" style="height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">费用名称</td>
    <td class="xl70" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">报销金额</td>
    <td class="xl70" colspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">描述</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
    <c:if test="${!empty mingxiList }">
	   <c:forEach items="${mingxiList }" var="mingxi">
		  <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    		<td class="xl70" height="33.27" colspan="2" style="height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${mingxi.costDetail }</td>
    		<td class="xl70" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${mingxi.remibAmount }</td>
    		<td class="xl70" colspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${mingxi.remark }</td>
    		<td colspan="4" style="mso-ignore:colspan;"></td>
   		  </tr>
    	</c:forEach>
   </c:if>
   <c:if test="${empty mingxiList }">
   		 <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    		<td class="xl70" height="33.27" colspan="2" style="height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;"></td>
    		<td class="xl70" colspan="3" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;"></td>
    		<td class="xl70" colspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;"></td>
    		<td colspan="4" style="mso-ignore:colspan;"></td>
   		 </tr>
   </c:if>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" height="33.27" colspan="2" style="height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">合计</td>
    <td class="xl71" colspan="7" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${bean.amount}</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl72" height="33.27" colspan="9" style="height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">收款人信息</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" height="33.27" style="height:24.95pt;" x:str="">收款人</td>
    <td class="xl70" colspan="5" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">银行账号</td>
    <td class="xl70" colspan="2" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">开户银行</td>
    <td class="xl70" x:str="">转账金额</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <c:if test="${!empty listPayee }">
	   <c:forEach items="${listPayee }" var="payee">
		  <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    		<td class="xl70" height="33.27" style="height:24.95pt;">${payee.payeeName }</td>
    		<td class="xl70" colspan="5" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${payee.bankAccount }</td>
    		<td class="xl70" colspan="2" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">${payee.bank }</td>
    		<td class="xl70">${payee.payeeAmount }</td>
    		<td colspan="4" style="mso-ignore:colspan;"></td>
   		  </tr>
   	  </c:forEach>
   </c:if>
   <c:if test="${empty listPayee }">
     <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    	<td class="xl70" height="33.27" style="height:24.95pt;">-</td>
    	<td class="xl70" colspan="5" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">-</td>
    	<td class="xl70" colspan="2" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;">-</td>
    	<td class="xl70">-</td>
    	<td colspan="4" style="mso-ignore:colspan;"></td>
     </tr>
   </c:if>
   <td class="xl70" height="693.33" rowspan="12" colspan="2" style='height:520.00pt;border:.5pt solid windowtext' x:str>审签</td>
    <c:forEach items="${listTProcessChecks}" var="check">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl70" height="133.07" colspan="3" rowspan="2" style='height:69.80pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>${check.fRoleName }</td>
	    <td class="xl70" colspan="4" rowspan="1" style='border-right:.5pt solid windowtext;border-bottom: none'>${check.fcheckRemake }</td>
	   </tr>
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl73" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;border-top: none' x:str>
	    <c:if test="${!empty check.fuserId}">
	    <span style='mso-spacerun:yes;'><img style="vertical-align:bottom;width: 150px; height: 60px;margin-left:0px;margin-bottom: 10px"  src="${base}/attachment/downloadQZ/${check.fuserId}"/></span><fmt:formatDate value="${check.fcheckTime }" pattern="yyyy年MM月dd日"/>
	    </c:if>
	    <c:if test="${empty check.fuserId}">
	    <span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;年&nbsp;月&nbsp;日
	    </c:if>
	    </td>
	   </tr>
   </c:forEach>
   <!-- <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" height="665.33" rowspan="20" style="height:499.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">审签</td>
    <td class="xl70" colspan="3" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">经办人处室负责人</td>
    <td class="xl73" colspan="5" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">（签名） XXXX年X月X日</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" colspan="3" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">出纳岗</td>
    <td class="xl73" colspan="5" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">（签名） XXXX年X月X日</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" colspan="3" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">业务分管副局长</td>
    <td class="xl73" colspan="5" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">（签名） XXXX年X月X日</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl70" colspan="3" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">装财处处室负责人</td>
    <td class="xl73" colspan="5" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">（签名） XXXX年X月X日</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl80" colspan="3" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">业务分管财务局长</td>
    <td class="xl73" colspan="5" rowspan="4" style="border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;" x:str="">（签名） XXXX年X月X日</td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   <tr height="33.27" style="height:24.95pt;mso-height-source:userset;mso-height-alt:499;">
    <td class="xl89" height="33.27" colspan="9" style="height:24.95pt;mso-ignore:colspan;"></td>
    <td colspan="4" style="mso-ignore:colspan;"></td>
   </tr>
   [if supportMisalignedColumns]
    <tr width="0" style="display:none;">
     <td width="112" style="width:84;"></td>
     <td width="51" style="width:38;"></td>
     <td width="40" style="width:30;"></td>
     <td width="101" style="width:76;"></td>
     <td width="61" style="width:46;"></td>
     <td width="72" style="width:54;"></td>
     <td width="146" style="width:110;"></td>
     <td width="236" style="width:177;"></td>
    </tr>
   [endif] -->
  </tbody></table>
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
