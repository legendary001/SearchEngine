<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
        <title>极易搜索</title>
        <link href="css/index.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div id="header">
                <ul>
                    <li class="active"><a href="#">赞</a></li>
                    <li><a href="#">登录</a></li>
                    <li><a href="#">注销</a></li>
                </ul>
            </div>
            <div id="logo">
                <h1>极易搜索</h1>
            </div>
            <div id="mainbody">
                <form action="<%=request.getContextPath()%>/pagingSearchServlet" id="form">
                    <input type="text" name="queryString"/>
                    <button>嗖的一下</button>
                </form>
            </div>
            <div id="bottom">
                <ul>
                    <li><a href="#">关于极易</a></li>
                    <li><a href="#">Xeon主页</a></li>
                </ul>
            </div>
            <div id="footer">
                <p>版权所有&copy2014-2015&nbsp;<span>郑海旭Xeon工作室</span></p>
            </div>
        </div>
         <script src="js/index.js"></script>
    </body>
</html>