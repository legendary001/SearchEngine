<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<!--charset-->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
		<!--three key points-->
		<title>极易搜索</title>
		<meta name="Keywords" content="key"/>
		<meta name="description" content="description"/>
		<!--css js-->
		<style type="text/css">
		*{
			margin:0px;
			padding:0px;
		}
		#logo{
			width:1366px;
			height:70px;
			background: #333333;
		}
		#logo span{
			color:#FFFFFF;
			font-size: 50px;
			margin-left:120px;
		}
		#search{
			width:1366px;
			height:40px;
			margin-left:120px;
			margin-top:12px;
		}
		#search_input{
			width:500px;
			height:36px;
			float:left;
			font-size:16px;
			font-family: "微软雅黑";
			border:2px solid #BFB9B9;
		}
		#search_btn{
			width: 100px;
			height:40px;
			float:left;
			font-size: 16px;
			color:#FFFFFF;
			background: #FF0000;
			border:2px solid #FF0000;
			font-family: "微软雅黑";
		}
		</style>
		<script type="text/javascript">
			function validate(){
				var queryString=document.getElementById("search_input").value;
				if(queryString==""){
					return false;
				}else{
					return true;
				}
			}
		</script>
	</head>

	<body>
		<%
			String queryRecord=new String(request.getParameter("queryString").getBytes("iso-8859-1"),"utf-8");
		%>
		<!--盒子模型 width：宽度 height：高度 放内容-->
			<div id="logo">
				<span>极易搜索</span>
			</div>
			<div id="search">
				<form action="pagingSearchServlet" onsubmit="return validate()">
						<input id="search_input" type="text" name="queryString" value="<%=queryRecord%>"/>
						<button id="search_btn">嗖的一下</button>
				</form>
			</div>
		</div>
		<br/>
		<hr/>
	</body>
</html>