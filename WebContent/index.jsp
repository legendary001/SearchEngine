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
		body{
			/*
				background:属性 url:属性值 属性:属性值;
				top:图片顶上放置 center:图片水平居中，在页面变化时居中
				no-repeat 放置页面被撑大时图片复制
			*/
			background:url("images/bg.jpg") top center no-repeat;
			/*让容器在页面变化时居中*/
			text-align: center;
		}
		#top{
			width:1092px;
			height:297px;
			margin:0px 0px 0px 125px;
			float:left;
		}
		#status{
			display: inline;
			float: left;
			width: 1092px;
			height: 20px;
			margin:10px 0px 10px 0px;
		}
		#status ul{
			list-style: none;
		}
		#status ul li{
			float:right;
			margin-left: 25px;
		}
		#status ul li a{
			text-decoration: none;
			font-size: 16px;
			font-family: "微软雅黑";
			color:#FFFFFF;
		}
		#logo{
			width:1092px;
			height:90px;
			margin:120px 0px 0px 0px;
		}
		#logo h1{
			color:#BFB9B9;
			font-size: 70px;
		}
		#search{
			width:684px;
			height:40px;
			margin-left:220px;
		}
		#search_input{
			width:580px;
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
			background: #DD4B39;
			border:2px solid #DD4B39;
			font-family: "微软雅黑";
		} 
		#buttom{
			width:1092px;
			height:355px;
			margin-left: 125px;
			float:left;
		}
		#msg{
			width:350px;
			height:20px;
			margin:250px 0px 0px 350px;
		}
		#msg h4{
			font-size: 14px;
			color:#FFFFFF;
			font-family: "微软雅黑";
		}
		#footer{
			width:350px;
			height:20px;
			margin:35px 0px 30px 350px;
		}
		#footer h4{
			font-size: 14px;
			color:#FFFFFF;
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
		<!--盒子模型 width：宽度 height：高度 放内容-->
		<div id="top">
			<div id="status">
				<ul>
					<li><a href="#">注册</a></li>
					<li><a href="#">登录</a></li>
					<li><a href="#">赞</a></li>
				</ul>
			</div>
			<div id="logo">
				<h1>极易搜索</h1>
			</div>
			<div id="search">
				<form action="pagingSearchServlet" onsubmit="return validate()">
						<input id="search_input" type="text" name="queryString"/>
						<button id="search_btn">嗖的一下</button>
				</form>
			</div>
		</div>
		<div id="buttom">
			<div id="msg">
				<h4>提示：使用快捷键 Ctrl+D 收藏本站，满意就赏个赞吧。</h4>
			</div>
			<div id="footer">
				<h4>版权所有&copy2014-2015 <span>极易——Xeon工作室</span></h4>
			</div>
		</div>
	</body>
</html>