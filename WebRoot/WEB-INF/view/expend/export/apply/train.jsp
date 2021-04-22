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
	font-family:"Calibri";
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
	{color:#9C0006;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font6
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:700;
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
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font9
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font10
	{color:#3F3F3F;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font11
	{color:#3F3F76;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font12
	{color:#800080;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
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
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font15
	{color:#FF0000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
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
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font18
	{color:#44546A;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font19
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font20
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font21
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font22
	{color:#006100;
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
.xl69
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl70
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
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
	border-bottom:.5pt solid windowtext;}
.xl72
	{mso-style-parent:style0;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl73
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl74
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl75
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl76
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl77
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
.xl78
	{mso-style-parent:style0;
	text-align:left;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
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
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl81
	{mso-style-parent:style0;
	text-align:justify;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl82
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;}
.xl83
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;}
.xl84
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl85
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl86
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-bottom:.5pt solid windowtext;}
.xl87
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl88
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;}
.xl89
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
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
	font-family:Calibri;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl91
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;}
.xl92
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
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
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl97
	{mso-style-parent:style0;
	text-align:right;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl98
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl99
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	font-family:宋体;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl100
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	font-size:14.0pt;
	font-family:Calibri;
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
        <x:TopRowVisible>28</x:TopRowVisible>
        <x:Panes>
         <x:Pane>
          <x:Number>3</x:Number>
          <x:ActiveCol>22</x:ActiveCol>
          <x:ActiveRow>35</x:ActiveRow>
          <x:RangeSelection>W36</x:RangeSelection>
         </x:Pane>
        </x:Panes>
        <x:DoNotDisplayGridlines/>
        <x:ProtectContents>False</x:ProtectContents>
        <x:ProtectObjects>False</x:ProtectObjects>
        <x:ProtectScenarios>False</x:ProtectScenarios>
        <x:PageBreakZoom>100</x:PageBreakZoom>
        <x:Print>
         <x:ValidPrinterInfo/>
         <x:PaperSizeIndex>9</x:PaperSizeIndex>
         <x:HorizontalResolution>300</x:HorizontalResolution>
         <x:VerticalResolution>300</x:VerticalResolution>
        </x:Print>
       </x:WorksheetOptions>
       <x:PageBreaks>
        <x:RowBreaks>
         <x:RowBreak>
          <x:Row>33</x:Row>
         </x:RowBreak>
         <x:RowBreak>
          <x:Row>67</x:Row>
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
     <x:Formula>=Sheet1!$A$1:$S$67</x:Formula>
    </x:ExcelName>
   </xml>
  <![endif]-->
 </head>
 <body link="blue" vlink="purple">
  <table width="1826.13" border="0" cellpadding="0" cellspacing="0" style='width:1369.60pt;border-collapse:collapse;table-layout:fixed;'>
   <col width="170" style='mso-width-source:userset;mso-width-alt:5440;'/>
   <col width="57" style='mso-width-source:userset;mso-width-alt:1824;'/>
   <col width="29" style='mso-width-source:userset;mso-width-alt:928;'/>
   <col width="13" style='mso-width-source:userset;mso-width-alt:416;'/>
   <col width="59" style='mso-width-source:userset;mso-width-alt:1888;'/>
   <col width="33" style='mso-width-source:userset;mso-width-alt:1056;'/>
   <col width="36" style='mso-width-source:userset;mso-width-alt:1152;'/>
   <col width="40" style='mso-width-source:userset;mso-width-alt:1280;'/>
   <col width="19" style='mso-width-source:userset;mso-width-alt:608;'/>
   <col width="18" span="2" style='mso-width-source:userset;mso-width-alt:576;'/>
   <col width="37.47" style='mso-width-source:userset;mso-width-alt:1198;'/>
   <col width="42" style='mso-width-source:userset;mso-width-alt:1344;'/>
   <col width="72.53" style='mso-width-source:userset;mso-width-alt:2321;'/>
   <col width="54.93" style='mso-width-source:userset;mso-width-alt:1757;'/>
   <col width="8" style='mso-width-source:userset;mso-width-alt:256;'/>
   <col width="28.73" style='mso-width-source:userset;mso-width-alt:919;'/>
   <col width="22.47" style='mso-width-source:userset;mso-width-alt:718;'/>
   <col width="132" style='mso-width-source:userset;mso-width-alt:4224;'/>
   <col width="72" span="13" style='width:54.00pt;'/>
   <tr height="56" style='height:42.00pt;mso-height-source:userset;mso-height-alt:840;'>
    <td class="xl65" height="56" width="890.13" colspan="19" style='height:42.00pt;width:667.60pt;border-right:none;border-bottom:none;' x:str>天津市司法局培训申请单</td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
    <td width="72" style='width:54.00pt;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl66" height="33.27" colspan="12" style='height:24.95pt;mso-ignore:colspan;' x:str>摘要：${bean.gName}<span style='mso-spacerun:yes;'></span></span></td>
    <td class="xl96" colspan="7" style='border-right:none;border-bottom:none;' x:str>单据编号：${bean.gCode}</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl66" height="33.27" colspan="15" style='height:24.95pt;mso-ignore:colspan;' x:str>申请日期：<span style='mso-spacerun:yes;'><fmt:formatDate value="${bean.reqTime}" pattern="yyyy-MM-dd"/></span></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl97" colspan="2" style='border-right:none;border-bottom:none;'></td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>申请人姓名</td>
    <td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.userNames}</td>
    <td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>部门（处室）</td>
    <td class="xl70" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.deptName}</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>培训名称</td>
    <td class="xl69" colspan="18" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trainingName}</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>培训目的</td>
    <td class="xl69" colspan="18" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trContent}</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>报到日期</td>
    <td class="xl70" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${trainingBean.trDateStart}" pattern="yyyy-MM-dd"/></td>
    <td class="xl70" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>撤离日期</td>
    <td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${trainingBean.trDateEnd}" pattern="yyyy-MM-dd"/></td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>培训天数</td>
    <td class="xl73" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trDayNum}</td>
    <td class="xl70" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训类别</td>
     <c:choose>
    	<c:when test="${trainingBean.trainingType == '1'}"><td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>一类培训</td></c:when>
    	<c:when test="${trainingBean.trainingType == '2'}"><td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>二类培训</td></c:when>
    	<c:when test="${trainingBean.trainingType == '3'}"><td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>三类培训</td></c:when>
    </c:choose>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl76" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>培训地点：${trainingBean.trPlace}</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>主要参训人员</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trAttendPeop}</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>参训人数</td>
    <td class="xl73" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trAttendNum}</td>
    <td class="xl70" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>工作人员人数</td>
    <td class="xl71" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trStaffNum}</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师信息</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="34.67" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl68" height="34.67" style='height:26.00pt;' x:str>姓名</td>
    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>职称</td>
    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>身份证号</td>
    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>是否异地</td>
    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>银行卡号</td>
    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>开户行</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <c:if test="${!empty listLecturer}">
   	 <c:forEach items="${listLecturer}" var="lecturer">
	   <tr height="33.33" style='height:25.00pt;mso-height-source:userset;mso-height-alt:500;'>
	    <td class="xl68" height="34.67" style='height:26.00pt;'>${lecturer.lecturerName }</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${lecturer.lecturerLevel }</td>
	    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${lecturer.idCard}</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>
	   	<c:if test="${lecturer.isOutside==0 }">否</c:if>
	    <c:if test="${lecturer.isOutside==1 }">是</c:if> 
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${lecturer.bankCard }</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${lecturer.bank }</td>
	   </tr>
   	 </c:forEach>
   </c:if>
   <c:if test="${empty listLecturer }">
	   <tr height="33.33" style='height:25.00pt;mso-height-source:userset;mso-height-alt:500;'>
	    <td class="xl68" height="34.67" style='height:26.00pt;'>-</td>
    	<td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
       </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训日程</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' >日期</td>
    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>开始时间</td>
    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>结束时间</td>
    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师姓名</td>
    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>学时</td>
    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训内容</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <c:if test="${!empty listMeetPlan}">
   	 <c:forEach items="${listMeetPlan}" var="meetPlan">
	   <tr height="33.33" style='height:25.00pt;mso-height-source:userset;mso-height-alt:500;'>
	   	<td class="xl68" height="33.27" style='height:24.95pt;'><fmt:formatDate value="${meetPlan.date}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetPlan.timeStart }</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetPlan.timeEnd }</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetPlan.lecturerName }</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetPlan.lessonTime }</td>
	    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetPlan.arrange }</td>
	   </tr>
   </c:forEach>
   </c:if>
   <c:if test="${empty listMeetPlan}">
   	<tr height="33.33" style='height:25.00pt;mso-height-source:userset;mso-height-alt:500;'>
    	<td class="xl68" height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
   	</tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用明细</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>综合预算</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>费用名称</td>
    <td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str><font class="font2">费用标准（元</font><font class="font3">/</font><font class="font2">人天）</font></td>
    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>总额标准（元）</td>
    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>申请金额（元）</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <c:if test="${!empty listZongHe}">
   	 <c:forEach items="${listZongHe}" var="zonghe">
	    <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl68" height="33.27" style='height:24.95pt;'>${zonghe.costDetail }</td>
	    <td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${zonghe.standard }</td>
	    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${zonghe.totalStandard }</td>
	    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${zonghe.applySum }" minFractionDigits="2" maxFractionDigits="2"/></td>
	   </tr>
	 </c:forEach>
	</c:if>
   <c:if test="${empty listZongHe}">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl68" height="33.27" style='height:24.95pt;'>-</td>
	    <td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl69" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	   </tr>
	</c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—讲课费</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="53.27" rowspan="2" style='height:39.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师姓名</td>
    <td class="xl82" colspan="4" rowspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>学时</td>
    <td class="xl82" colspan="7" rowspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>实发标准（元/学时）</td>
    <td class="xl68" colspan="6" rowspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>个人所得税（元）</td>
    <td class="xl68" rowspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>申请金额（元）</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="20" style='height:15.00pt;mso-height-source:userset;mso-height-alt:300;'>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <c:if test="${!empty listTeacherCost}">
   	 <c:forEach items="${listTeacherCost}" var="teacherCost">
	<tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    	<td class="xl69" height="33.27" style='height:24.95pt;'>${teacherCost.lecturerName }</td>
    	<td class="xl73" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${teacherCost.lessonHours }</td>
    	<td class="xl73" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${teacherCost.lessonStd }</td>
    	<td class="xl81" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${teacherCost.lessonTaxAmount }</td>
    	<td class="xl81"><fmt:formatNumber groupingUsed="true" value="${teacherCost.applySum }" minFractionDigits="2" maxFractionDigits="2"/></td>
    	<td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
    </c:forEach>
   </c:if>
   <c:if test="${empty listTeacherCost}">
	 <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl73" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl73" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl81" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl81"></td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—住宿费</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>讲师姓名</td>
    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>住宿费标准</td>
    <td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>总额标准</td>
    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>申请金额（元）</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <c:if test="${!empty listHotel}">
   	 <c:forEach items="${listHotel}" var="hotel">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    	<td class="xl68" height="33.27" style='height:24.95pt;'>${hotel.lecturerName }</td>
    	<td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotel.hotelStd }</td>
    	<td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotel.hotelStdTotal }</td>
    	<td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${hotel.applySum }" minFractionDigits="2" maxFractionDigits="2"/></td>
    	<td colspan="13" style='mso-ignore:colspan;'></td>
   	   </tr>
	 </c:forEach>
   </c:if>
   <c:if test="${empty listHotel}">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—伙食费</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>讲师姓名</td>
    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>伙食费标准</td>
    <td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>总额标准</td>
    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>申请金额（元）</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
    <c:if test="${!empty listFood}">
   	 <c:forEach items="${listFood}" var="food">
	  <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    	<td class="xl69" height="33.27" style='height:24.95pt;'>${food.lecturerName }</td>
    	<td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${food.foodStd }</td>
    	<td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${food.foodStdTotal }</td>
    	<td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${food.applySum }" minFractionDigits="2" maxFractionDigits="2"/></td>
    	<td colspan="13" style='mso-ignore:colspan;'></td>
  	  </tr>
	 </c:forEach>
   </c:if>
   <c:if test="${empty listFood}">
	  <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    	<td class="xl69" height="33.27" style='height:24.95pt;'>-</td>
    	<td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td colspan="13" style='mso-ignore:colspan;'></td>
  	  </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—城市间交通费</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" style='height:24.95pt;' x:str>讲师姓名</td>
    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>行政级别</td>
    <td class="xl68" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>交通工具</td>
    <td class="xl68" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>交通工具级别</td>
    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>申请金额（元）</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <c:if test="${!empty listTraffic1}">
   	 <c:forEach items="${listTraffic1}" var="listTraffic1">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    	<td class="xl69" height="33.27" style='height:24.95pt;'>${listTraffic1.lecturerName }</td>
    	<td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listTraffic1.administrativeLevel }</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listTraffic1.vehicle }</td>
    	<td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listTraffic1.vehicleLevel }</td>
    	<td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${listTraffic1.applySum }" minFractionDigits="2" maxFractionDigits="2"/></td>
    	<td colspan="13" style='mso-ignore:colspan;'></td>
   	   </tr>
    </c:forEach>
   </c:if>
   <c:if test="${empty listTraffic1}">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    	<td class="xl69" height="33.27" style='height:24.95pt;'>-</td>
    	<td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="7" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="5" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl69" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td colspan="13" style='mso-ignore:colspan;'></td>
   	   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl77" height="33.27" colspan="19" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—市内交通费</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" colspan="2" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师姓名</td>
    <td class="xl68" colspan="17" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>申请金额（元）</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <c:if test="${!empty listTraffic2}">
   	 <c:forEach items="${listTraffic2}" var="listTraffic2">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    	<td class="xl69" height="33.27" colspan="2" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listTraffic2.lecturerName }</td>
    	<td class="xl81" colspan="17" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${listTraffic2.applySum }" minFractionDigits="2" maxFractionDigits="2"/></td>
    	<td colspan="13" style='mso-ignore:colspan;'></td>
       </tr>
    </c:forEach>
   </c:if>
   <c:if test="${empty listTraffic2}">
	  <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    	<td class="xl69" height="33.27" colspan="2" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl81" colspan="17" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td colspan="13" style='mso-ignore:colspan;'></td>
   	  </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" colspan="2" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>合计</td>
    <td class="xl68" colspan="17" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${bean.amount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl68" height="33.27" colspan="2" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>合计金额（大写）</td>
    <td class="xl68" colspan="17" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.applyAmountcapital }整</td>
    <td colspan="13" style='mso-ignore:colspan;'></td>
   </tr>
    <td class="xl88" height="33.33" rowspan="24" style='height:25.00pt;border:.5pt solid windowtext;' x:str>审签</td>
   <c:forEach items="${listTProcessChecks}" var="check">
   <tr height="33.33" style='height:25.00pt;mso-height-source:userset;mso-height-alt:500;'>
    <td class="xl68" height="133.33" colspan="6" rowspan="2" style='height:70.00pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>${check.fRoleName }</td>
    <td class="xl68" colspan="12" rowspan="1" style='border-right:.5pt solid windowtext;border-bottom:none;'>${check.fcheckRemake }</td>
   </tr>
   <tr height="33.33" style='height:25.00pt;mso-height-source:userset;mso-height-alt:500;'>
    <td class="xl90" colspan="12" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;border-top: none' x:str>
    <c:if test="${!empty check.fuserId}">
    <font class="font2"><span style='mso-spacerun:yes;'><img style="vertical-align:bottom;width: 150px; height: 60px;margin-left:0px;margin-bottom: 10px"  src="${base}/attachment/downloadQZ/${check.fuserId}"/></span></font><font class="font2"><fmt:formatDate value="${check.fcheckTime }" pattern="yyyy年MM月dd日"/></font>
    </c:if>
    <c:if test="${empty check.fuserId}">
    <font class="font2"><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></font><font class="font2">&nbsp;&nbsp;年&nbsp;月&nbsp;日</font>
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


