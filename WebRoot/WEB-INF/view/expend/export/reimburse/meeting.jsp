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
	{margin:0.54in 0.70in 0.75in 0.70in;
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
	{color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font5
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font6
	{color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font7
	{color:#3F3F3F;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font8
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
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
	{color:#006100;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font11
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
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
	{color:#3F3F76;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font14
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font15
	{color:#800080;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font16
	{color:#9C6500;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font17
	{color:#0000FF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font18
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
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
	font-size:18.0pt;
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
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font23
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"等线";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.style0
	{mso-number-format:"General";
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:"常规";
	mso-style-id:0;}
.style16
	{mso-number-format:"_ \\¥* \#\,\#\#0_ \;_ \\¥* \\-\#\,\#\#0_ \;_ \\¥* \0022-\0022_ \;_ \@_ ";
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
	mso-font-charset:0;
	border:.5pt solid #7F7F7F;
	mso-style-name:"输入";}
.style19
	{mso-number-format:"_ \\¥* \#\,\#\#0\.00_ \;_ \\¥* \\-\#\,\#\#0\.00_ \;_ \\¥* \0022-\0022??_ \;_ \@_ ";
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
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 2";}
.style30
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:134;
	mso-style-name:"标题 4";}
.style31
	{color:#FF0000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:0;
	mso-style-name:"警告文本";}
.style32
	{color:#44546A;
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:134;
	mso-style-name:"标题";}
.style33
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:0;
	mso-style-name:"解释性文本";}
.style34
	{color:#44546A;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:134;
	border-bottom:1.0pt solid #5B9BD5;
	mso-style-name:"标题 1";}
.style35
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:134;
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
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 1";}
.style37
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:134;
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
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 2";}
.style44
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:0;
	border-bottom:2.0pt double #FF8001;
	mso-style-name:"链接单元格";}
.style45
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
	mso-font-charset:0;
	border-top:.5pt solid #5B9BD5;
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
	color:#000000;
	font-size:14.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:等线;
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
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;}
.xl69
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl70
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
.xl71
	{mso-style-parent:style0;
	text-align:center;
	font-family:宋体;
	mso-font-charset:134;}
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
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl74
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl75
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
.xl76
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-family:宋体;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl77
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
 </head>
 <body link="blue" vlink="purple">
  <table width="886.40" border="0" cellpadding="0" cellspacing="0" style='width:664.80pt;border-collapse:collapse;table-layout:fixed;'>
   <col width="221" style='mso-width-source:userset;mso-width-alt:7072;'/>
   <col width="213.33" style='mso-width-source:userset;mso-width-alt:6826;'/>
   <col width="200" style='mso-width-source:userset;mso-width-alt:6400;'/>
   <col width="252.07" style='mso-width-source:userset;mso-width-alt:8066;'/>
   <tr height="34" style='height:25.50pt;'>
    <td class="xl65" height="34" width="886.40" colspan="4" style='height:25.50pt;width:664.80pt;border-right:none;border-bottom:none;' x:str>天津市司法局会议报销单</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl66" height="33.27" style='height:24.95pt;' x:str>摘要：${bean.gName}</td>
    <td class="xl67"></td>
    <td class="xl68"  colspan="2" x:str>单据编号：${bean.gCode}</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl66" height="33.27" style='height:24.95pt;' x:str>报销日期：<fmt:formatDate value="${bean.reimburseReqTime}" pattern="yyyy-MM-dd"/></td>
    <td class="xl67"></td>
    <td class="xl67"></td>
    <td class="xl68" x:str>附单据<font class="font2"><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font class="font2">张</font></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>报销人姓名</td>
    <td class="xl69">${bean.userName}</td>
    <td class="xl69" x:str>部门（处室）</td>
    <td class="xl69">${bean.deptName}</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>会议名称</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetingBean.meetingName}</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>报到时间</td>
    <td class="xl69"><fmt:formatDate value="${meetingBean.dateStart}" pattern="yyyy-MM-dd"/></td>
    <td class="xl69" x:str>离开时间</td>
    <td class="xl69"><fmt:formatDate value="${meetingBean.dateEnd}" pattern="yyyy-MM-dd"/></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>会议天数</td>
    <td class="xl69">${meetingBean.duration}</td>
    <td class="xl69" x:str>会议类型</td>
    <c:choose>
    	<c:when test="${meetingBean.meetingType == '1'}"><td class="xl69">一级会议</td></c:when>
    	<c:when test="${meetingBean.meetingType == '2'}"><td class="xl69">二级会议</td></c:when>
    	<c:when test="${meetingBean.meetingType == '3'}"><td class="xl69">三级会议</td></c:when>
    	<c:when test="${meetingBean.meetingType == '4'}"><td class="xl69">四级会议</td></c:when>
    </c:choose>
    <%-- <td class="xl69">${meetingBean.meetingType}</td> --%>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>会议地点</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetingBean.meetingPlace}</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>主要参会人员</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetingBean.attendPeople}</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>参会人数</td>
    <td class="xl69">${meetingBean.attendNum}</td>
    <td class="xl69" x:str>其中工作人员人数</td>
    <td class="xl69">${meetingBean.staffNum}</td>
   </tr>
   <tr height="77.33" style='height:58.00pt;mso-height-source:userset;mso-height-alt:1160;'>
    <td class="xl69" height="77.33" style='height:58.00pt;' x:str>调整事项说明</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.fupdateReason}</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl70" height="33.27" colspan="4" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>会议日程</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>起始时间</td>
    <td class="xl69" x:str>结束时间</td>
    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>事项安排</td>
   </tr>
   <c:if test="${!empty listMeetingPlan }">
	   <c:forEach items="${listMeetingPlan }" var="meetingPlan">
		   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
		    <td class="xl69" height="33.27" style='height:24.95pt;'><fmt:formatDate value="${meetingPlan.times }" pattern="yyyy-MM-dd"/></td>
		    <td class="xl69"><fmt:formatDate value="${meetingPlan.timee }" pattern="yyyy-MM-dd"/></td>
		    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetingPlan.content }</td>
		   </tr>
	   </c:forEach>
   </c:if>
   <c:if test="${empty listMeetingPlan }">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl69" height="33.27" style='height:24.95pt;'>-</td>
	    <td class="xl69">-</td>
	    <td class="xl69" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl70" height="33.27" colspan="4" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用明细</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>费用名称</td>
    <td class="xl69" x:str>费用标准（元/人天）</td>
    <td class="xl69" x:str>总额标准（元）</td>
    <td class="xl69" x:str>报销金额（元）</td>
   </tr>
  <c:if test="${!empty mingxiList }">
	   <c:forEach items="${mingxiList }" var="mingxi">
		   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
		    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>${mingxi.costDetail }</td>
		    <td class="xl69">${mingxi.standard }</td>
		    <td class="xl69">${mingxi.totalStandard }</td>
		    <td class="xl69"><fmt:formatNumber groupingUsed="true" value="${mingxi.remibAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
		   </tr>
	   </c:forEach>
   </c:if>
   <c:if test="${empty mingxiList }">
	   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
	    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>-</td>
	    <td class="xl69">-</td>
	    <td class="xl69">-</td>
	    <td class="xl69">-</td>
	   </tr>
   </c:if>
   <!-- <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl70" height="33.27" colspan="4" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>合计（元）</td>
   </tr> -->
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>合计</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${bean.amount}" minFractionDigits="2" maxFractionDigits="2"/></td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" height="33.27" style='height:24.95pt;' x:str>合计金额（大写）</td>
    <td class="xl69" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.reimAmountcapital}整</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl70" height="33.27" colspan="4" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>冲销借款情况</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" colspan="2" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>借款单号</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>冲销借款（元）</td>
   </tr>
   <c:if test="${empty bean.withLoan || bean.withLoan ==0}">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" colspan="2" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
   </tr>
   </c:if>
   <c:if test="${!empty bean.withLoan && bean.withLoan ==1}">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl72" height="33.27" colspan="2" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.loan.lCode }</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${bean.cxAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
   </tr>
   </c:if>
   <%-- <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl70" height="33.27" colspan="4" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>收款人信息</td>
   </tr>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" colspan="1" height="33.27" style='height:24.95pt;' x:str>收款人</td>
    <td class="xl69" colspan="2" x:str>建行卡号</td>
    <td class="xl69" colspan="1" x:str>转账金额（元）</td>
   </tr>
   <c:if test="${!empty listPayee }">
	   <c:forEach items="${listPayee }" var="payee">
		   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
		    <td class="xl69" colspan="1" height="33.27" style='height:24.95pt;'>${payee.payeeName }</td>
		    <td class="xl69" colspan="2" >${payee.bankAccount }</td>
		    <td class="xl69" colspan="1" ><fmt:formatNumber groupingUsed="true" value="${payee.payeeAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
		   </tr>
   	  </c:forEach>
   </c:if>
   <c:if test="${empty listPayee }">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" colspan="1"height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl69" colspan="2" >-</td>
    <td class="xl69" colspan="1" >-</td>
   </tr>
   </c:if> --%>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl70" height="33.27" colspan="4" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>单位内部收款人信息</td>
   </tr>
   <tr id="nbskr" height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" colspan="1" height="33.27" style='height:24.95pt;' x:str>收款人</td>
    <td class="xl69" colspan="1" x:str>银行账号</td>
    <td class="xl69" colspan="1" x:str>开户银行</td>
    <td class="xl69" colspan="1" x:str>转账金额</td>
   </tr>
   <c:if test="${!empty listPayee }">
	   <c:forEach items="${listPayee }" var="payee">
	   	<c:if test="${payee.fInnerOrOuter == '0'}">
		   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
		    <td class="xl69" colspan="1" height="33.27" style='height:24.95pt;'>${payee.payeeName }</td>
		    <td class="xl69" colspan="1" >${payee.bankAccount }</td>
		    <td class="xl69" colspan="1" >${payee.bank }</td>
		    <td class="xl69" colspan="1" ><fmt:formatNumber groupingUsed="true" value="${payee.payeeAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
		   </tr>
		</c:if>
   	  </c:forEach>
   </c:if>
   <c:if test="${index1 == '0' }">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" colspan="1"height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl69" colspan="1" >-</td>
    <td class="xl69" colspan="1" >-</td>
    <td class="xl69" colspan="1" >-</td>
   </tr>
   </c:if>
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl70" height="33.27" colspan="4" style='height:24.95pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>单位外部收款人信息</td>
   </tr>
   <tr id="wbskr" height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" colspan="1" height="33.27" style='height:24.95pt;' x:str>收款人</td>
    <td class="xl69" colspan="1" x:str>银行账号</td>
    <td class="xl69" colspan="1" x:str>开户银行</td>
    <td class="xl69" colspan="1" x:str>转账金额</td>
   </tr>
   <c:if test="${!empty listPayee }">
	   <c:forEach items="${listPayee }" var="payee">
	    <c:if test="${payee.fInnerOrOuter == '1'}">
		   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
		    <td class="xl69" colspan="1" height="33.27" style='height:24.95pt;'>${payee.payeeName }</td>
		    <td class="xl69" colspan="1" >${payee.bankAccount }</td>
		    <td class="xl69" colspan="1" >${payee.bank }</td>
		    <td class="xl69" colspan="1" ><fmt:formatNumber groupingUsed="true" value="${payee.payeeAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
		   </tr>
		</c:if>
   	  </c:forEach>
   </c:if>
   <c:if test="${index2 == '0' }">
   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
    <td class="xl69" colspan="1"height="33.27" style='height:24.95pt;'>-</td>
    <td class="xl69" colspan="1" >-</td>
    <td class="xl69" colspan="1" >-</td>
    <td class="xl69" colspan="1" >-</td>
   </tr>
   </c:if>
    <td class="xl75" height="33.27" rowspan="12" style='height:24.95pt;border:.5pt solid windowtext;' x:str>审签</td>
   <c:if test="${!empty listTProcessChecks }">
	   <c:forEach items="${listTProcessChecks }" var="check">
		   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
		    <td class="xl69" height="133.07" rowspan="2" style='height:69.80pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>${check.fRoleName}</td>
		    <td class="xl76" colspan="2" rowspan="1" style='border-right:.5pt solid windowtext;border-bottom:none;'>${check.fcheckRemake}</td>
		   </tr>
		   <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
		    <td class="xl77" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>
		    <c:if test="${!empty check.fuserId}">
		    <font class="font2"><span style='mso-spacerun:yes;'><img style="vertical-align:bottom;width: 150px; height: 60px;margin-left:0px;margin-bottom: 10px"  src="${base}/attachment/downloadQZ/${check.fuserId}"/></span></font><font class="font2"><fmt:formatDate value="${check.fcheckTime}" pattern="yyyy年MM月dd日"/></font>
		    </c:if>
    		<c:if test="${empty check.fuserId}">
		    <font class="font2"><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></font><font class="font2">&nbsp;&nbsp;年&nbsp;月&nbsp;日</font>
		    </c:if>
		    </td>
		   </tr>
	   </c:forEach>
   </c:if>
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

