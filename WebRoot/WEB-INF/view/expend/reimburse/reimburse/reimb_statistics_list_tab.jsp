<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>


	<div id="statisticsTab" class="list-table">
		<table style="border-collapse:collapse" width="100%" height="100%" border="1">
		  <tr>
		    <td colspan="2" rowspan="2" bordercolor="#000000" bgcolor="#DDEEEE"><div align="center"><strong style="font-size: 16px;">项目</strong></div></td>
		    <td height="7%" colspan="2" bordercolor="#000000" bgcolor="#DDEEEE"><div align="center"><strong style="font-size: 16px;">实际总支出</strong></div></td>
		  </tr>
		  <tr>
		    <td width="25%" height="7%" bordercolor="#000000" bgcolor="#DDEEEE"><div align="center"><strong style="font-size: 16px;">本月累计数</strong></div></td>
		    <td width="25%" bordercolor="#000000" bgcolor="#DDEEEE"><div align="center"><strong style="font-size: 16px;">上年同期数</strong></div></td>
		  </tr>
		  <tr>
		    <td width="25%" rowspan="3" bordercolor="#000000"><div align="center"><strong>因公出国(境)</strong></div></td>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>出国（境）团组（个）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${abroadmap.count }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${abroadmap.lastcount }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>出国（境）人数（人次）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${abroadmap.teamPersonNum }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${abroadmap.lastteamPersonNum }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>经费支出（元）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${abroadmap.amount }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${abroadmap.lastamount }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" rowspan="3" bordercolor="#000000"><div align="center"><strong>公务接待</strong></div></td>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>接待批次（批）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${receptionmap.count }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${receptionmap.lastcount }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>接待人数 （人次）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${receptionmap.teamPersonNum }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${receptionmap.lastteamPersonNum }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>接待支出（元）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${receptionmap.amount }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${receptionmap.lastamount }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>会议</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center"><strong>经费支出（元）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${meetingnmap.amount }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${meetingnmap.lastamount }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" rowspan="2" bordercolor="#000000"><div align="center"><strong>培训</strong></div></td>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>培训人数（人次）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${trainingmap.trAttendNum }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${trainingmap.lasttrAttendNum }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>培训支出（元）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${trainingmap.amount }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${trainingmap.lastamount }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" rowspan="2" bordercolor="#000000"><div align="center"><strong>差旅</strong></div></td>
		    <td width="25%" height="7%" bordercolor="#000000"><div align="center"><strong>出差人数（人次）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${travelmap.travelPeop }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${travelmap.lasttravelPeop }</div></td>
		  </tr>
		  <tr>
		    <td width="25%" height="16%" bordercolor="#000000"><div align="center"><strong>差旅支出（元）</strong></div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${travelmap.amount }</div></td>
		    <td width="25%" bordercolor="#000000"><div align="center">${travelmap.lastamount }</div></td>
		  </tr>
		</table>
	</div>
