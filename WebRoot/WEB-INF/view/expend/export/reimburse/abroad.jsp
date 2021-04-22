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
	{margin:1.00in 0.75in 1.00in 0.75in;
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
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font1
	{color:#000000;
	font-size:14.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font2
	{color:#000000;
	font-size:20.0pt;
	font-weight:700;
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
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font5
	{color:#0000FF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font6
	{color:#006100;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font7
	{color:#3F3F76;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font8
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font9
	{color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font10
	{color:#9C0006;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font11
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font12
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font13
	{color:#3F3F3F;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font14
	{color:#800080;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font15
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font16
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font17
	{color:#44546A;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font18
	{color:#44546A;
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font19
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font20
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font21
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font22
	{color:#9C6500;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 3";}
.style25
	{color:#0000FF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 2";}
.style30
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-font-charset:134;
	mso-style-name:"标题 4";}
.style31
	{color:#FF0000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-font-charset:0;
	mso-style-name:"警告文本";}
.style32
	{color:#44546A;
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-font-charset:134;
	mso-style-name:"标题";}
.style33
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:宋体;
	mso-font-charset:0;
	mso-style-name:"解释性文本";}
.style34
	{color:#44546A;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-font-charset:134;
	border-bottom:1.0pt solid #5B9BD5;
	mso-style-name:"标题 1";}
.style35
	{color:#44546A;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 1";}
.style37
	{color:#44546A;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 2";}
.style44
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-font-charset:0;
	border-bottom:2.0pt double #FF8001;
	mso-style-name:"链接单元格";}
.style45
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;}
.xl65
	{mso-style-parent:style0;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl66
	{mso-style-parent:style0;
	text-align:center;
	font-size:20.0pt;
	font-weight:700;
	mso-font-charset:134;}
.xl67
	{mso-style-parent:style0;
	text-align:center;
	font-size:14.0pt;
	white-space:normal;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl68
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl69
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl70
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl71
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;}
.xl72
	{mso-style-parent:style0;
	text-align:left;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl73
	{mso-style-parent:style0;
	text-align:center;
	font-size:14.0pt;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl74
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl75
	{mso-style-parent:style0;
	text-align:center;
	font-size:14.0pt;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl76
	{mso-style-parent:style0;
	text-align:center;
	font-size:14.0pt;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl77
	{mso-style-parent:style0;
	text-align:center;
	font-size:14.0pt;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl78
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;}
.xl79
	{mso-style-parent:style0;
	text-align:right;
	white-space:normal;
	font-size:14.0pt;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl80
	{mso-style-parent:style0;
	text-align:right;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl81
	{mso-style-parent:style0;
	text-align:right;
	font-size:14.0pt;
	mso-font-charset:134;
	border-bottom:.5pt solid windowtext;}
.xl82
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border-bottom:.5pt solid windowtext;}
.xl83
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl84
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl85
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl86
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl87
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl88
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	font-size:14.0pt;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
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
        <x:DefaultRowHeight>270</x:DefaultRowHeight>
        <x:Selected/>
        <x:TopRowVisible>23</x:TopRowVisible>
        <x:Panes>
         <x:Pane>
          <x:Number>3</x:Number>
          <x:ActiveCol>0</x:ActiveCol>
          <x:ActiveRow>35</x:ActiveRow>
          <x:RangeSelection>A36:L36</x:RangeSelection>
         </x:Pane>
        </x:Panes>
        <x:ProtectContents>False</x:ProtectContents>
        <x:ProtectObjects>False</x:ProtectObjects>
        <x:ProtectScenarios>False</x:ProtectScenarios>
        <x:ShowPageBreakZoom/>
        <x:Zoom>50</x:Zoom>
        <x:Print>
         <x:ValidPrinterInfo/>
         <x:PaperSizeIndex>9</x:PaperSizeIndex>
         <x:Scale>39</x:Scale>
        </x:Print>
       </x:WorksheetOptions>
      </x:ExcelWorksheet>
     </x:ExcelWorksheets>
     <x:ProtectStructure>False</x:ProtectStructure>
     <x:ProtectWindows>False</x:ProtectWindows>
     <x:SelectedSheets>0</x:SelectedSheets>
     <x:WindowHeight>7860</x:WindowHeight>
     <x:WindowWidth>20490</x:WindowWidth>
    </x:ExcelWorkbook>
    <x:ExcelName>
     <x:Name>Print_Area</x:Name>
     <x:SheetIndex>1</x:SheetIndex>
     <x:Formula>=Sheet1!$A$1:$L$57</x:Formula>
    </x:ExcelName>
   </xml>
  <![endif]-->
  </head>
  
  <body>
	  <table width="957.33" border="0" cellpadding="0" cellspacing="0" style='width:718.00pt;border-collapse:collapse;table-layout:fixed;'>
	   <col width="85" style='mso-width-source:userset;mso-width-alt:2720;'/>
	   <col width="36.67" style='mso-width-source:userset;mso-width-alt:1173;'/>
	   <col width="96" style='mso-width-source:userset;mso-width-alt:3072;'/>
	   <col width="26.67" style='mso-width-source:userset;mso-width-alt:853;'/>
	   <col width="55" style='mso-width-source:userset;mso-width-alt:1760;'/>
	   <col width="91.67" style='mso-width-source:userset;mso-width-alt:2933;'/>
	   <col width="61" style='mso-width-source:userset;mso-width-alt:1952;'/>
	   <col width="50" style='mso-width-source:userset;mso-width-alt:1600;'/>
	   <col width="101.67" style='mso-width-source:userset;mso-width-alt:3253;'/>
	   <col width="123.33" style='mso-width-source:userset;mso-width-alt:3946;'/>
	   <col width="67" style='mso-width-source:userset;mso-width-alt:2144;'/>
	   <col width="163.33" style='mso-width-source:userset;mso-width-alt:5226;'/>
	   <col width="85" style='mso-width-source:userset;mso-width-alt:2720;'/>
	   <col width="72" style='mso-width-source:userset;mso-width-alt:2304;'/>
	   <tr height="37.33" style='height:28.00pt;mso-height-source:userset;mso-height-alt:560;'>
	    <td class="xl66" height="37.33" width="957.33" colspan="12" style='height:28.00pt;width:718.00pt;border-right:none;border-bottom:none;' x:str>天津市滨海新区财政局公务出国报销单</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl65" height="28" style='height:21.00pt;' x:str>摘要：${bean.gName}</td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl80" colspan="4" style='border-right:none;border-bottom:none;' x:str>单据编号：${bean.gCode}</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl65" height="28" colspan="2" style='height:21.00pt;mso-ignore:colspan;' x:str>报销日期：<fmt:formatDate value="${bean.reimburseReqTime}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl65"></td>
	    <td class="xl81" colspan="2" style='border-right:none;border-bottom:.5pt solid windowtext;' x:str>附单据<font class="font1"><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font class="font1">张</font></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销人姓名</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.userName}</td>
	    <td class="xl67" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>部门（处室）</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.deptName}</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>团组名称</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${abroadEdit.fTeamName}</td>
	    <td class="xl67" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>组团单位</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${abroadEdit.fAbroadPlace}</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>团长（级别）</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${abroadEdit.fTeamLeader}</td>
	    <td class="xl67" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>团员人数</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${abroadEdit.fTeamPersonNum}</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>开始时间</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${abroadEdit.fAbroadDateStart}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl67" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>结束时间</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${abroadEdit.fAbroadDateEnd}" pattern="yyyy-MM-dd"/></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>出国天数</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${abroadEdit.fAbroadDayNum}</td>
	    <td class="xl67" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>宴请安排</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>
   			<c:if test="${abroadEdit.fDiningPlace==1}">宴请 </c:if>
		    &nbsp;&nbsp;&nbsp;&nbsp;
		    <c:if test="${abroadEdit.fDiningPlace!=1}">工作餐 </c:if>
	    </td>
	   </tr>
	   <tr height="80" class="xl65" style='height:60.00pt;mso-height-source:userset;mso-height-alt:1200;'>
	    <td class="xl67" height="80" colspan="2" style='height:60.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>出国人员名单</td>
	    <td class="xl68" colspan="10" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${abroadEdit.fAbroadPeople}</td>
	   </tr>
	   <tr height="80" class="xl65" style='height:60.00pt;mso-height-source:userset;mso-height-alt:1200;'>
	    <td class="xl67" height="80" colspan="2" style='height:60.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>调整事项说明</td>
	    <td class="xl68" colspan="10" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.fupdateReason }</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl70" height="28" colspan="12" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>出访计划</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>出发日期</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>撤离日期/<font class="font1"><br/></font><font class="font1">抵华日期</font></td>
	    <td class="xl67" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>目的国/地区</td>
	    <td class="xl67" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>城市</td>
	    <td class="xl67" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>备注</td>
	   </tr>
	   <c:forEach items="${abroadPlanlist }" var="abroadPlanbean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl71" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:none;'><fmt:formatDate value="${abroadPlanbean.abroadDate}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl71" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:none;'><fmt:formatDate value="${abroadPlanbean.timeEnd}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${abroadPlanbean.arriveCountry}</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${abroadPlanbean.arriveCity}</td>
	    <td class="xl71" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:none;'>${abroadPlanbean.remark}</td>
	   </tr>
	   </c:forEach>
	   <c:if test="${empty abroadPlanlist }">
	   	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
		    <td class="xl71" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:none;'>-</td>
		    <td class="xl71" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:none;'>-</td>
		    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
		    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
		    <td class="xl71" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:none;'>-</td>
		   </tr>
	   </c:if>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl70" height="28" colspan="12" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用明细</td>
	   </tr>
	   <tr height="22.67" class="xl65" style='height:17.00pt;mso-height-source:userset;mso-height-alt:340;'>
	    <td class="xl72" height="22.67" colspan="9" style='height:17.00pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>国际旅费</td>
	    <td class="xl82" colspan="2" style='border-right:none;border-bottom:.5pt solid windowtext;' x:str>汇总金额（人民币元<font class="font3">）：</font>${abroadEdit.travelMoney}</td>
	    <td class="xl83"></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl73" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>出发日期</td>
	    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>到达日期</td>
	    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>出发地</td>
	    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>目的地</td>
	    <td class="xl73" x:str>交通工具</td>
	    <td class="xl74" x:str>报销金额<font class="font1"><br/></font><font class="font1">（人民币元）</font></td>
	    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>其中国际列车补助（美元<font class="font1">）</font></td>
	   </tr>
	   <c:forEach items="${expenselist }" var="expensebean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${expensebean.timeStart}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${expensebean.timeEnd}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${expensebean.startCity}</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${expensebean.arriveCity}</td>
	    <td class="xl68">${expensebean.vehicleText}</td>
	    <td class="xl68">${expensebean.applyAmount}</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${expensebean.trainSubsidies}</td>
	   </tr>
	   </c:forEach>
	   <c:if test="${empty expenselist }">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68">-</td>
	    <td class="xl68">-</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	   </tr>
	   </c:if>
	   <tr height="24" class="xl65" style='height:18.00pt;mso-height-source:userset;mso-height-alt:360;'>
	    <td class="xl72" height="24" colspan="9" style='height:18.00pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>国外城市间交通费</td>
	    <td class="xl82" colspan="2" style='border-right:none;border-bottom:.5pt solid windowtext;' x:str>汇总金额（人民币元<font class="font3">）：</font>${abroadEdit.trafficMoney}</td>
	    <td class="xl83"></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl73" height="28" colspan="6" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>出行人员</td>
	    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（人民币元）</td>
	   </tr>
	    <c:forEach items="${listOutsideTrafficInfo }" var="listOutsideTrafficbean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="6" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listOutsideTrafficbean.travelPersonnel }</td>
	    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listOutsideTrafficbean.applyAmount }</td>
	   </tr>
	   </c:forEach>
	   <c:if test="${empty listOutsideTrafficInfo }">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="6" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	   </tr>
	   </c:if>
	   <tr height="22.67" class="xl65" style='height:17.00pt;mso-height-source:userset;mso-height-alt:340;'>
	    <td class="xl72" height="22.67" colspan="9" style='height:17.00pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>住宿费</td>
	    <td class="xl82" colspan="2" style='border-right:none;border-bottom:.5pt solid windowtext;' x:str>汇总金额（人民币元<font class="font3">）：</font>${abroadEdit.hotelMoney}</td>
	    <td class="xl83"></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl73" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>所在城市</td>
	    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>入住时间</td>
	    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>退房时间</td>
	    <td class="xl74" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>住宿标准<font class="font1"><br/></font><font class="font1">（外币元）</font></td>
	    <td class="xl74" x:str>住宿天数<font class="font1"><br/></font><font class="font1">（天）</font></td>
	    <td class="xl74" x:str>总额标准<font class="font1"><br/></font><font class="font1">（外币元）</font></td>
	    <td class="xl73" x:str>币种</td>
	    <td class="xl74" x:str>报销金额<font class="font1"><br/></font><font class="font1">（人民币元）</font></td>
	   </tr>
	    <c:forEach items="${listHotelExpenseInfo }" var="listHotelExpensebean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl74" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listHotelExpensebean.locationCity }</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${listHotelExpensebean.checkInTime}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${listHotelExpensebean.checkOUTTime}" pattern="yyyy-MM-dd"/></td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listHotelExpensebean.standard }</td>
	    <td class="xl68">${listHotelExpensebean.hotelDay }</td>
	    <td class="xl68">${listHotelExpensebean.countStandard }</td>
	    <td class="xl68">${listHotelExpensebean.currency }</td>
	    <td class="xl68">${listHotelExpensebean.applyAmount }</td>
	   </tr>
	   </c:forEach>
	   <c:if test="">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl74" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68">-</td>
	    <td class="xl68">-</td>
	    <td class="xl68">-</td>
	    <td class="xl68">-</td>
	   </tr>
	   </c:if>
	   <tr height="22.67" class="xl65" style='height:17.00pt;mso-height-source:userset;mso-height-alt:340;'>
	    <td class="xl72" height="22.67" colspan="9" style='height:17.00pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>伙食费</td>
	    <td class="xl82" colspan="2" style='border-right:none;border-bottom:.5pt solid windowtext;' x:str>汇总金额（人民币元<font class="font3">）：</font>${abroadEdit.foodMoney}</td>
	    <td class="xl83"></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl73" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>所在城市</td>
	    <td class="xl73" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用标准（元/人天）<span style='display:none;'></span></td>
	    <td class="xl73" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>总额标准（外币元）</td>
	    <td class="xl74" x:str>币种</td>
	    <td class="xl74" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（人民币元）</td>
	   </tr>
	   <c:forEach items="${listFoodAllowanceInfo }" var="listFoodAllowancebean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listFoodAllowancebean.fAddress }</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listFoodAllowancebean.standard }</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listFoodAllowancebean.countStandard }</td>
	    <td class="xl68">${listFoodAllowancebean.currency }</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listFoodAllowancebean.fApplyAmount }</td>
	   </tr>
	   </c:forEach>
	   <c:if test="${empty listFoodAllowanceInfo }">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68">-</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	   </tr>
	   </c:if>
	   <tr height="22.67" class="xl65" style='height:17.00pt;mso-height-source:userset;mso-height-alt:340;'>
	    <td class="xl72" height="22.67" colspan="9" style='height:17.00pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>公杂费</td>
	    <td class="xl82" colspan="2" style='border-right:none;border-bottom:.5pt solid windowtext;' x:str>汇总金额（人民币元<font class="font3">）：</font>${abroadEdit.mixMoney}</td>
	    <td class="xl83"></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>所在城市</td>
	    <td class="xl73" colspan="2" style='height:50px;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用标准<font class="font1"><br/></font><font class="font1">（外币元）</font></td>
	    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>公杂费天数<span style='display:none;'></span></td>
	    <td class="xl67" colspan="2" style='height:50px;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>总额标准<font class="font1"><br/></font><font class="font1">（外币元）</font></td>
	    <td class="xl68" x:str>币种</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额<font class="font1"><br/></font><font class="font1">（外币元）</font></td>
	   </tr>
	   <c:forEach items="${listMiscellaneousFeeInfo }" var="miscellaneousFeebean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${miscellaneousFeebean.fAddress }</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${miscellaneousFeebean.standard }</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${miscellaneousFeebean.fDays }</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${miscellaneousFeebean.countStandard }</td>
	    <td class="xl68">${miscellaneousFeebean.currency }</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${miscellaneousFeebean.fAddress }</td>
	   </tr>
	   </c:forEach>
	   <c:if test="${empty listMiscellaneousFeeInfo}">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
	    <td class="xl68"></td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'></td>
	   </tr>
	   </c:if>
	   <tr height="22.67" class="xl65" style='height:17.00pt;mso-height-source:userset;mso-height-alt:340;'>
	    <td class="xl72" height="22.67" colspan="9" style='height:17.00pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>宴请费用</td>
	    <td class="xl82" colspan="2" style='border-right:none;border-bottom:.5pt solid windowtext;' x:str>汇总金额（人民币元<font class="font3">）：</font>${abroadEdit.feteMoney}</td>
	    <td class="xl83"></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>所在城市</td>
	    <td class="xl73" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用标准（元/人）</td>
	    <td class="xl67" x:str>宴请人数</td>
	    <td class="xl67" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>陪餐人数</td>
	    <td class="xl68" x:str>总额标准（外币元）</td>
	    <td class="xl68" x:str>币种</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（人民币元）</td>
	   </tr>
	    <c:forEach items="${listFeteCostInfo }" var="feteCostbean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${feteCostbean.fAddress }</td>
	    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${feteCostbean.standard }</td>
	    <td class="xl68">${feteCostbean.fFeeNum }</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${feteCostbean.fAccompanyNum }</td>
	    <td class="xl68">${feteCostbean.countStandard }</td>
	    <td class="xl68">${feteCostbean.currency }</td>
	    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${feteCostbean.fApplyAmount }</td>
	   </tr>
	   </c:forEach>
	   <c:if test="${empty listFeteCostInfo }">
	   	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
		    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
		    <td class="xl68" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
		    <td class="xl68">-</td>
		    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
		    <td class="xl68">-</td>
		    <td class="xl68">-</td>
		    <td class="xl68" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
		   </tr>
	   </c:if>
	   <tr height="22.67" class="xl65" style='height:17.00pt;mso-height-source:userset;mso-height-alt:340;'>
	    <td class="xl72" height="22.67" colspan="9" style='height:17.00pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>其他费用</td>
	    <td class="xl82" colspan="2" style='border-right:none;border-bottom:.5pt solid windowtext;' x:str>汇总金额（人民币元<font class="font3">）：</font>${abroadEdit.totalOtherMoney}</td>
	    <td class="xl83"></td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用名称</td>
	    <td class="xl67" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（人民币元）</td>
	    <td class="xl84" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>备注</td>
	   </tr>
	    <c:forEach items="${listReceptionOther }" var="receptionbean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${receptionbean.fCostName }</td>
	    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${receptionbean.fCost }</td>
	    <td class="xl85" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${receptionbean.fRemark }</td>
	   </tr>
	   </c:forEach>
	    <c:if test="${empty listReceptionOther}">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl85" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	   </tr>
	   </c:if>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl70" height="28" colspan="12" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>合计（元）</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>金额（小写）</td>
	    <td class="xl68" colspan="10" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.amount}</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="2" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>金额（大写）</td>
	    <td class="xl68" colspan="10" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.reimAmountcapital}整</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl70" height="28" colspan="12" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>冲销借款情况</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl75" height="28" colspan="8" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>借款单号</td>
	    <td class="xl86" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>冲销借款（元）</td>
	   </tr>
	   <c:if test="${!empty bean.withLoan && bean.withLoan==1 }">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl75" height="28" colspan="8" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.loan.lCode}</td>
	    <td class="xl86" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.cxAmount}</td>
	   </tr>
	   </c:if>
	   <c:if test="${empty bean.withLoan || bean.withLoan==0 }">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl75" height="28" colspan="8" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl86" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	   </tr>
	   </c:if>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl70" height="28" colspan="12" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>收款人信息</td>
	   </tr>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl67" height="28" colspan="4" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>收款人</td>
	    <td class="xl67" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>建行卡号</td>
	    <td class="xl67" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>转账金额（人民币元）</td>
	   </tr>
	   <c:forEach items="${reimbPayeeInfolist }" var="reimbPayeebean">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="4" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${reimbPayeebean.payeeName }</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${reimbPayeebean.bankAccount }</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${reimbPayeebean.payeeAmount }</td>
	   </tr>
	   </c:forEach>
	   <c:if test="${empty reimbPayeeInfolist }">
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl68" height="28" colspan="4" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	    <td class="xl68" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
	   </tr>
	   </c:if>
	   <tr height="28" class="xl65" style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
	    <td class="xl70" height="28" colspan="12" style='height:21.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>审签</td>
	   </tr>
		<c:if test="${!empty listTProcessChecks }">
			<c:forEach items="${listTProcessChecks }" var="check">
				<tr height="28"
					style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
					<td class="xl67" height="84" colspan="2" rowspan="3"
						style='height:63.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'
						x:str>${check.fRoleName }</td>
					<td class="xl78" colspan="10" rowspan="2"
						style='border-right:.5pt solid windowtext;border-bottom:none;'>${check.fcheckRemake }</td>
				</tr>
				<tr height="28"
					style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;' />
				<tr height="28"
					style='height:21.00pt;mso-height-source:userset;mso-height-alt:420;'>
					<td class="xl79" colspan="10"
						style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'
						x:str>
						<c:if test="${!empty check.fuserId}">
						<font class="font1"><span
							style='mso-spacerun:yes;'><img style="vertical-align:bottom;width: 150px; height: 60px;margin-left:0px"  src="${base}/attachment/downloadQZ/${check.fuserId}"/>
						</span></font><font class="font1">${check.fcheckTimes }</font>
						</c:if>
						<c:if test="${empty check.fuserId}">
							<font class="font1">
								<span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							</font>
							<font class="font1">&nbsp;&nbsp;年&nbsp;月&nbsp;日</font>
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
