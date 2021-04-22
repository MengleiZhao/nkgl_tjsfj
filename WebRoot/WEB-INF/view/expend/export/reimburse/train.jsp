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
	font-size:22.0pt;
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
	{color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font5
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font6
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
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
	{color:#FF0000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font9
	{color:#3F3F3F;
	font-size:11.0pt;
	font-weight:700;
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
	{color:#0000FF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font12
	{color:#800080;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font13
	{color:#9C6500;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font14
	{color:#000000;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font15
	{color:#1F497D;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font16
	{color:#1F497D;
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font17
	{color:#1F497D;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font18
	{color:#1F497D;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font19
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font20
	{color:#FFFFFF;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font21
	{color:#006100;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:"宋体";
	mso-generic-font-family:auto;
	mso-font-charset:0;}
.font22
	{color:#FA7D00;
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
	mso-pattern:auto;
	mso-background-source:auto;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	background:#EBF1DE;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
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
	background:#D8E4BC;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"差";}
.style23
	{mso-number-format:"_ * \#\,\#\#0\.00_ \;_ * \\-\#\,\#\#0\.00_ \;_ * \0022-\0022??_ \;_ \@_ ";
	mso-style-name:"千位分隔";
	mso-style-id:3;}
.style24
	{mso-pattern:auto none;
	background:#C4D79B;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	background:#DA9694;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 2";}
.style30
	{color:#1F497D;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-style-name:"标题 4";}
.style31
	{color:#FF0000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"警告文本";}
.style32
	{color:#1F497D;
	font-size:18.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-style-name:"标题";}
.style33
	{color:#7F7F7F;
	font-size:11.0pt;
	font-weight:400;
	font-style:italic;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"解释性文本";}
.style34
	{color:#1F497D;
	font-size:15.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid #4F81BD;
	mso-style-name:"标题 1";}
.style35
	{color:#1F497D;
	font-size:13.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid #4F81BD;
	mso-style-name:"标题 2";}
.style36
	{mso-pattern:auto none;
	background:#95B3D7;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 1";}
.style37
	{color:#1F497D;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border-left:none;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid #A7BFDE;
	mso-style-name:"标题 3";}
.style38
	{mso-pattern:auto none;
	background:#B1A0C7;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
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
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border:2.0pt double #3F3F3F;
	mso-style-name:"检查单元格";}
.style42
	{mso-pattern:auto none;
	background:#FDE9D9;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 6";}
.style43
	{mso-pattern:auto none;
	background:#C0504D;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 2";}
.style44
	{color:#FA7D00;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border-left:none;
	border-top:.5pt solid #4F81BD;
	border-right:none;
	border-bottom:2.0pt double #4F81BD;
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
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"适中";}
.style48
	{mso-pattern:auto none;
	background:#DAEEF3;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 5";}
.style49
	{mso-pattern:auto none;
	background:#4F81BD;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 1";}
.style50
	{mso-pattern:auto none;
	background:#DCE6F1;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 1";}
.style51
	{mso-pattern:auto none;
	background:#B8CCE4;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 1";}
.style52
	{mso-pattern:auto none;
	background:#F2DCDB;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 2";}
.style53
	{mso-pattern:auto none;
	background:#E6B8B7;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 2";}
.style54
	{mso-pattern:auto none;
	background:#9BBB59;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 3";}
.style55
	{mso-pattern:auto none;
	background:#8064A2;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 4";}
.style56
	{mso-pattern:auto none;
	background:#E4DFEC;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"20% - 强调文字颜色 4";}
.style57
	{mso-pattern:auto none;
	background:#CCC0DA;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 4";}
.style58
	{mso-pattern:auto none;
	background:#4BACC6;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 5";}
.style59
	{mso-pattern:auto none;
	background:#B7DEE8;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 5";}
.style60
	{mso-pattern:auto none;
	background:#92CDDC;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"60% - 强调文字颜色 5";}
.style61
	{mso-pattern:auto none;
	background:#F79646;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"强调文字颜色 6";}
.style62
	{mso-pattern:auto none;
	background:#FCD5B4;
	color:#000000;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-style-name:"40% - 强调文字颜色 6";}
.style63
	{mso-pattern:auto none;
	background:#FABF8F;
	color:#FFFFFF;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
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
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;}
.xl65
	{mso-style-parent:style0;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl66
	{mso-style-parent:style0;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl67
	{mso-style-parent:style0;
	text-align:left;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl68
	{mso-style-parent:style0;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl69
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:22.0pt;
	font-weight:700;
	mso-font-charset:134;}
.xl70
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl71
	{mso-style-parent:style0;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;}
.xl72
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl73
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl74
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl75
	{mso-style-parent:style0;
	mso-number-format:"h:mm:ss";
	text-align:center;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
.xl76
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl77
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl78
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl79
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl80
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#F2F2F2;
	font-size:14.0pt;
	font-weight:700;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl81
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border-left:.5pt solid windowtext;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl82
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl83
	{mso-style-parent:style0;
	text-align:center;
	white-space:normal;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl84
	{mso-style-parent:style0;
	text-align:center;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border-top:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;}
.xl85
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:bottom;
	white-space:normal;
	mso-pattern:auto none;
	background:#FFFFFF;
	font-size:14.0pt;
	mso-font-charset:134;
	border:.5pt solid windowtext;}
 </style>
  <!--[if gte mso 9]>
   <xml>
    <x:ExcelWorkbook>
     <x:ExcelWorksheets>
      <x:ExcelWorksheet>
       <x:Name>Sheet1</x:Name>
       <x:WorksheetOptions>
        <x:DefaultRowHeight>499</x:DefaultRowHeight>
        <x:Unsynced/>
        <x:Selected/>
        <x:TopRowVisible>37</x:TopRowVisible>
        <x:Panes>
         <x:Pane>
          <x:Number>3</x:Number>
          <x:ActiveCol>5</x:ActiveCol>
          <x:ActiveRow>1</x:ActiveRow>
          <x:RangeSelection>F2:H2</x:RangeSelection>
         </x:Pane>
        </x:Panes>
        <x:ProtectContents>False</x:ProtectContents>
        <x:ProtectObjects>False</x:ProtectObjects>
        <x:ProtectScenarios>False</x:ProtectScenarios>
        <x:ShowPageBreakZoom/>
        <x:PageBreakZoom>100</x:PageBreakZoom>
        <x:Print>
         <x:ValidPrinterInfo/>
         <x:PaperSizeIndex>9</x:PaperSizeIndex>
         <x:Scale>76</x:Scale>
        </x:Print>
       </x:WorksheetOptions>
       <x:PageBreaks>
        <x:RowBreaks>
         <x:RowBreak>
          <x:Row>31</x:Row>
         </x:RowBreak>
         <x:RowBreak>
          <x:Row>66</x:Row>
         </x:RowBreak>
        </x:RowBreaks>
       </x:PageBreaks>
      </x:ExcelWorksheet>
     </x:ExcelWorksheets>
     <x:ProtectStructure>False</x:ProtectStructure>
     <x:ProtectWindows>False</x:ProtectWindows>
     <x:WindowHeight>12540</x:WindowHeight>
     <x:WindowWidth>28800</x:WindowWidth>
    </x:ExcelWorkbook>
    <x:ExcelName>
     <x:Name>_FilterDatabase</x:Name>
     <x:Hidden/>
     <x:SheetIndex>1</x:SheetIndex>
     <x:Formula>=Sheet1!$A$1:$G$70</x:Formula>
    </x:ExcelName>
    <x:ExcelName>
     <x:Name>Print_Area</x:Name>
     <x:SheetIndex>1</x:SheetIndex>
     <x:Formula>=Sheet1!$A$1:$H$70</x:Formula>
    </x:ExcelName>
   </xml>
  <![endif]-->
 </head>
 <body link="blue" vlink="purple" class="xl68">
  <table width="882" border="0" cellpadding="0" cellspacing="0" style='width:661.50pt;border-collapse:collapse;table-layout:fixed;'>
   <col width="102" class="xl67" style='mso-width-source:userset;mso-width-alt:3264;'/>
   <col width="84" class="xl67" style='mso-width-source:userset;mso-width-alt:2688;'/>
   <col width="87" class="xl67" style='mso-width-source:userset;mso-width-alt:2784;'/>
   <col width="95" class="xl67" style='mso-width-source:userset;mso-width-alt:3040;'/>
   <col width="112" class="xl67" style='mso-width-source:userset;mso-width-alt:3584;'/>
   <col width="100" class="xl68" style='mso-width-source:userset;mso-width-alt:3200;'/>
   <col width="116" class="xl68" style='mso-width-source:userset;mso-width-alt:3712;'/>
   <col width="186" class="xl68" style='mso-width-source:userset;mso-width-alt:5952;'/>
   <col width="72" span="16376" class="xl68" style='mso-width-source:userset;mso-width-alt:2304;'/>
   <tr height="58.67" class="xl65" style='height:44.00pt;mso-height-source:userset;mso-height-alt:880;'>
    <td class="xl69" height="58.67" width="696" colspan="7" style='height:44.00pt;width:522.00pt;border-right:none;border-bottom:none;' x:str>天津市司法局培训报销单</td>
    <td class="xl65" width="186" style='width:139.50pt;'></td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl67" height="34.67" colspan="3" style='height:26.00pt;border-right:none;border-bottom:none;' x:str>摘要：${bean.gName}</td>
    <td class="xl66" colspan="2" style='mso-ignore:colspan;'></td>
    <td class="xl70" colspan="3" style='border-right:none;border-bottom:none;' x:str>单据编号：${bean.gCode}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl67" height="34.67" colspan="3" style='height:26.00pt;border-right:none;border-bottom:none;' x:str>报销日期：<fmt:formatDate value="${bean.reimburseReqTime}" pattern="yyyy-MM-dd"/></td>
    <td class="xl71" colspan="2" style='mso-ignore:colspan;'></td>
    <td class="xl71"></td>
    <td class="xl70" colspan="2" style='border-right:none;border-bottom:none;' x:str>附单据<span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp; </span>张</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销人姓名</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.userName}</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>部门（处室）</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.deptName}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训名称</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBeanApply.trainingName}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训目的</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBeanApply.trContent}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报到日期</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${trainingBean.trDateStart}" pattern="yyyy-MM-dd"/></td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>撤离日期</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${trainingBean.trDateEnd}" pattern="yyyy-MM-dd"/></td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训天数</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trDayNum}</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训类别</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trainingType}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训地点</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trPlace}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>主要参训人员</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trAttendPeop}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>参训人数</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trAttendNum}</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>工作人员人数</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${trainingBean.trStaffNum}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>调整事项说明</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.fupdateReason}</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师信息</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师姓名</td>
    <td class="xl72" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>身份证号</td>
   </tr>
   <c:if test="${!empty listLecturer }">
	   <c:forEach items="${listLecturer}" var="lecturer">
		<tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    		<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${lecturer.lecturerName }</td>
    		<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${lecturer.idCard}</td>
   		</tr>
	   </c:forEach>
   </c:if>
   <c:if test="${empty listLecturer }">
   	<tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
   	</tr>
   </c:if>
   
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训日程</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训日期</td>
    <td class="xl73" x:str>开始时间</td>
    <td class="xl73" x:str>结束时间</td>
    <td class="xl73" x:str>讲师姓名</td>
    <td class="xl73" x:str>学时</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>培训内容</td>
   </tr>
   <c:if test="${!empty listMeetPlan}">
   	 <c:forEach items="${listMeetPlan}" var="meetPlan">
	   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatDate value="${meetPlan.date }" pattern="yyyy-MM-dd"/></td>
    	<td class="xl75">${meetPlan.timeStart }</td>
    	<td class="xl75">${meetPlan.timeEnd }</td>
    	<td class="xl73">${meetPlan.lecturerName }</td>
    	<td class="xl73">${meetPlan.lessonTime }</td>
    	<td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${meetPlan.arrange }</td>
   	   </tr>
      </c:forEach>
   </c:if>
   <c:if test="${empty listMeetPlan}">
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    <td class="xl75">-</td>
    <td class="xl75">-</td>
    <td class="xl73">-</td>
    <td class="xl73">-</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
   </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>费用明细</td>
   </tr>
   <c:if test="${!empty listZongHe}">
   	 <c:forEach items="${listZongHe}" var="zonghe">
	   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${zonghe.costDetail }</td>
    	<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${zonghe.remibAmount }" minFractionDigits="2" maxFractionDigits="2"/></td>
   	   </tr>
	 </c:forEach>
	</c:if>
   <c:if test="${empty listZongHe}">
	  <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
   	  </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>合计金额(元)</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${trainingBean.zongheMoney}" minFractionDigits="2" maxFractionDigits="2"/></td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—讲课费</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" style='height:26.00pt;' x:str>讲师姓名</td>
    <td class="xl73" x:str>学时</td>
    <td class="xl73" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>实发标准（元/学时）</td>
    <td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>个人所得税</td>
    <td class="xl73" x:str>报销金额（元）</td>
   </tr>
     <c:if test="${!empty listTeacherCost}">
   		<c:forEach items="${listTeacherCost}" var="teacherCost">
		   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    		<td class="xl73" height="34.67" style='height:26.00pt;'>${teacherCost.lecturerName }</td>
    		<td class="xl73">${teacherCost.lessonHours }</td>
    		<td class="xl73" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${teacherCost.lessonActualStd}</td>
    		<td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${teacherCost.lessonTaxAmount}</td>
    		<td class="xl73">${teacherCost.reimbSum}</td>
   		   </tr>
	   </c:forEach>
   </c:if>
   <c:if test="${empty listTeacherCost}">
	   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" style='height:26.00pt;'>-</td>
    	<td class="xl73">-</td>
    	<td class="xl73" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl73" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl73">-</td>
       </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—住宿费</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师姓名</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（元）</td>
   </tr>
   <c:if test="${!empty listHotel}">
   		<c:forEach items="${listHotel}" var="hotel">
		   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    		<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${hotel.lecturerName }</td>
    		<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${hotel.reimbSum }" minFractionDigits="2" maxFractionDigits="2"/></td>
   		   </tr>
	   </c:forEach>
   </c:if>
   <c:if test="${empty listHotel}">
	   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
   	   </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—伙食费</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师姓名</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（元）</td>
   </tr>
   <c:if test="${!empty listFood}">
   	 <c:forEach items="${listFood}" var="food">
	   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${food.lecturerName }</td>
    	<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${food.reimbSum }" minFractionDigits="2" maxFractionDigits="2"/></td>
   	   </tr>
	 </c:forEach>
   </c:if>
   <c:if test="${empty listFood}">
	   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
       </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—城市间交通费</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师姓名</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（元）</td>
   </tr>
   <c:if test="${!empty listTraffic1}">
   	 	<c:forEach items="${listTraffic1}" var="listTraffic1">
		   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    		<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listTraffic1.lecturerName }</td>
    		<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${listTraffic1.reimbSum }" minFractionDigits="2" maxFractionDigits="2"/></td>
   		   </tr>
    	</c:forEach>
   </c:if>
   <c:if test="${empty listTraffic1}">
	   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
       </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>师资费—市内交通费</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>讲师姓名</td>
    <td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>报销金额（元）</td>
   </tr>
   <c:if test="${!empty listTraffic2}">
   		<c:forEach items="${listTraffic2}" var="listTraffic2">
		 <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    		<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${listTraffic2.lecturerName }</td>
    		<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${listTraffic2.reimbSum }" minFractionDigits="2" maxFractionDigits="2"/></td>
   	     </tr>
	   </c:forEach>
   </c:if>
   <c:if test="${empty listTraffic2}">
	   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl73" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl73" colspan="6" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
   	   </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" colspan="2" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>合计金额（小写）</td>
    <td class="xl76" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'><fmt:formatNumber groupingUsed="true" value="${bean.amount }" minFractionDigits="2" maxFractionDigits="2"/></td>
    <td class="xl76" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>合计金额（大写）</td>
    <td class="xl76" colspan="2" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.reimAmountcapital }整</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl78" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>冲销借款情况</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl81" height="34.67" colspan="4" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>借款单号</td>
    <td class="xl76" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>冲销金额</td>
   </tr>
   <c:if test="${!empty bean.withLoan && bean.withLoan ==1}">
   	 <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl81" height="34.67" colspan="4" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.loan.lCode }</td>
    	<td class="xl76" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${bean.cxAmount }</td>
     </tr>
   </c:if>
   <c:if test="${empty bean.withLoan || bean.withLoan ==0}">
     <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl81" height="34.67" colspan="4" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl76" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
     </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>单位内部收款人信息</td>
   </tr>
   <tr height="34.67" class="xl66" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" style='height:26.00pt;' x:str>收款人</td>
    <td class="xl72" x:str>转账金额</td>
    <td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>银行账户</td>
    <td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>开户银行</td>
   </tr>
   <c:if test="${!empty listPayee}">
	   <c:forEach items="${listPayee }" var="payee">
	   <c:if test="${payee.fInnerOrOuter eq '0'}">
		 <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    		<td class="xl72" height="34.67" style='height:26.00pt;'>${payee.payeeName }</td>
    		<td class="xl72">${payee.payeeAmount }</td>
    		<td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${payee.bankAccount}</td>
    		<td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${payee.bank}</td>
   		 </tr>
   		 </c:if>
   	  </c:forEach>
   </c:if>
   <c:if test="${index1 == '0'}">
     <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl72" height="34.67" style='height:26.00pt;'>-</td>
    	<td class="xl72">-</td>
    	<td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
     </tr>
   </c:if>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl74" height="34.67" colspan="8" style='height:26.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>单位外部收款人信息</td>
   </tr>
   <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    <td class="xl72" height="34.67" style='height:26.00pt;' x:str>收款人</td>
    <td class="xl72" x:str>转账金额</td>
    <td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>银行账户</td>
    <td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>开户银行</td>
   </tr>
   <c:if test="${!empty listPayee}">
	   <c:forEach items="${listPayee }" var="payee">
	   <c:if test="${payee.fInnerOrOuter eq '1'}">
		 <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    		<td class="xl72" height="34.67" style='height:26.00pt;'>${payee.payeeName }</td>
    		<td class="xl72">${payee.payeeAmount }</td>
    		<td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${payee.bankAccount}</td>
    		<td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>${payee.bank}</td>
   		 </tr>
   		 </c:if>
   	  </c:forEach>
   </c:if>
   <c:if test="${index2 == '0'}">
     <tr height="34.67" class="xl65" style='height:26.00pt;mso-height-source:userset;mso-height-alt:520;'>
    	<td class="xl72" height="34.67" style='height:26.00pt;'>-</td>
    	<td class="xl72">-</td>
    	<td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
    	<td class="xl72" colspan="3" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;'>-</td>
     </tr>
   </c:if>
    <td class="xl74" height="665.33" rowspan="20" colspan="2" style='height:499.00pt;border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;' x:str>审签</td>
    <c:if test="${!empty listTProcessChecks }">
    <c:forEach items="${listTProcessChecks }" var="check">
     <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
      <td class="xl72" colspan="2" rowspan="2" style='height:45pt;border-right:none;border-bottom:.5pt solid windowtext;' x:str>${check.fRoleName}</td>
      <td class="xl72" colspan="4" rowspan="1" style='border-right:.5pt solid windowtext;border-bottom:none;'>${check.fcheckRemake}</td>
     </tr>
     <tr height="33.27" style='height:24.95pt;mso-height-source:userset;mso-height-alt:499;'>
      <td class="xl85" colspan="4" style='border-right:.5pt solid windowtext;border-bottom:.5pt solid windowtext;border-top: none' x:str>
      <c:if test="${!empty check.fuserId}">
      <font><span style='mso-spacerun:yes;'><img style="vertical-align:bottom;width: 150px; height: 60px;margin-bottom:10px" src="${base}/attachment/downloadQZ/${check.fuserId}"/></span></font><font><fmt:formatDate value="${check.fcheckTime }" pattern="yyyy年MM月dd日"/></font>
       </c:if>
      <c:if test="${empty check.fuserId}">
      <font><span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></font><font>&nbsp;&nbsp;年&nbsp;月&nbsp;日</font>
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
	  },500);
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
