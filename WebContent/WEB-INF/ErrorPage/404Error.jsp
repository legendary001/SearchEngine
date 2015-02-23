<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>404错误友好提示页面</title>
    <!-- 3秒钟后自动跳转回首页 -->
    <meta http-equiv="refresh" content="20;url=<%=request.getContextPath()%>/index.jsp">
  </head>
  <body style="background:url('<%=request.getContextPath() %>/images/404.jpg') no-repeat center">
  	
  </body>
</html>