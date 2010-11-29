<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="info.jo32.EasyQandASite.controller.User"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	User user;
	HttpSession hs = request.getSession();
	user = (User) hs.getAttribute("user");
	if (user == null) {
		response.sendRedirect("signin.jsp");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="960gs.css">
<link type="text/css" rel="stylesheet" href="style.css">
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>

<script type="text/javascript" src="xhEditor/xheditor-en.min.js"></script>

<script type="text/javascript" src="js/newtopic.js"></script>

<title>new Topic()</title>
</head>
<body>
<div class="statusBar">123</div>
<div class="topBar">
<div class="container_12">
<div class="grid_7">
<div class="title">
<ul>
	<li class="name"><a href="">Exception.printTrace()</a></li>
	<li class="description">: a very easy Q&A site</li>
</ul>
</div>
</div>
<div class="options grid_5">
<ul>
	<li><a href="home.jsp">goHome()</a></li>
	<li><a href="signout">sign out</a></li>
</ul>
</div>
<div class="clear"></div>
</div>
</div>
<div class="container container_12">
<div class="newTopicTitle">new Topic() >> step into</div>
<form id="newTopic" name="newTopic">
<div class="titleDiv">
<h3>title</h3>
<input type="text" name="titleInput" id="titleInput"></input></div>
<div class="contentDiv"></div>
<h3 id="h3t">content</h3>
<textarea id="contentText"></textarea>
<div id="submit">submit</div>
</form>
<div class="clear"></div>
</div>
<div class="footer container_12">copyright 2010 <a href="http://jo32.info">jo32.info</a> | source
code | contact : <a href="http://jo32.info">@jo32</a></div>
</body>
</html>