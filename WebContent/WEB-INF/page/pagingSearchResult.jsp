<%@page import="java.net.URLEncoder"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="edu.dlnu.xeon.domain.HTML,java.util.List,edu.dlnu.xeon.domain.QueryResult"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<style type="text/css">
	*{
		margin:0px;
		padding:0px;
		
	}
	#main{
		width:1366px;
		margin-left:120px;
	}
	#count{
		font-size:14px;
		font-family:"微软雅黑";
		color:#999999;
	}
	#description{
		font-size:16px;
		font-family:"微软雅黑";
	}
	#url_date{
		font-size:13px;
		color:#008000;
		font-family:"微软雅黑";
	}
	#snapShot{
		font-size:13px;
		color:#999999;
		font-family:"微软雅黑";
	}
	#pageNow{
		font-size:18px;
		font-family:"微软雅黑";
		border:1px solid #999999;
		width:30px;
		height:30px;
		margin-right:20px;
		padding:0px 0px 5px 5px;
	}
	#pageNowUrl{
		text-decoration: none;
		color:#999999;
	}
	h4{
		font-family:"微软雅黑";
	}
</style>
<title>搜索结果</title>
</head>
<body>
		<%
			QueryResult<HTML> queryResult=(QueryResult<HTML>)request.getAttribute("queryResult");
			List<HTML> list=queryResult.getList();
			int rowCount=queryResult.getRowCount();
			String queryString=new String(request.getParameter("queryString").getBytes("iso-8859-1"),"utf-8");
		%>
	<div id="main">
		<br/>
		<span id="count">极易为您找到相关结果约<%=rowCount%>个</span>
		<br/><br/>
		<table  cellpadding="0" cellspacing="0" width="45%">
			<% 
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				for(HTML html:list){
					String date=format.format(html.getDate());
					String title=html.getTitle();
					title=title.replaceAll("<font color='red'>"+queryString+"</font>",queryString);
			%>
				<tr>
					<td><h4><a href=<%=html.getUrl()%> target="_blank"><%=html.getTitle()%></a></h4></td>
				</tr>
				<tr>
					<td><span id="description"><%=html.getDescription()%></span></td>
				</tr>
				<tr>
					<td><span id="url_date"><%=html.getUrl().substring(7)%>...<%=date %></span><a id="snapShot" href="<%=request.getContextPath()%>/snapShotServlet?title=<%=title%>" target="_blank">极易快照</a></td>
				</tr>
				<tr>
					<td><br/></td>
				</tr>
			<%
				}
			%>
		</table>
		<table>
			<tr>
				<%
					for(int i=0;i<queryResult.getPageCount();i++){
				%>
					<td><a id="pageNowUrl" href="<%=request.getContextPath()%>/pagingSearchServlet?pageNow=<%=i%>&&queryString=<%=queryString%>"><div id="pageNow"><%=i+1 %></div></a></div></td>
				<%
					}
				%>
			</tr>
		</table>
	</div>
</body>
</html>