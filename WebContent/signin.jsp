<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="info.jo32.EasyQandASite.controller.User"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	User user;
	HttpSession hs = request.getSession();
	user = (User) hs.getAttribute("user");
	if (user != null) {
		response.sendRedirect("home.jsp");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="960gs.css">
<link type="text/css" rel="stylesheet" href="style.css">
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/signin.js"></script>
<title>Sign in</title>
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
	<li><a href="signup.jsp">sign up</a></li>
</ul>
</div>
<div class="clear"></div>
</div>
</div>
<div class="container container_12">
<div class="left_pane grid_6">
<div class="grid_6 alpha omega">
<div class="signupTitle">Please input your info here :</div>
</div>
<div class="clear"></div>
<div class="grid_6 alpha omega">
<div id="signupForm" class="myform">
<form id="signupForm" name="signupForm">
<h1>Welcome back!</h1>
<p>Please input your user name and password below.</p>
<label>Name <span class="small">input your user name</span> </label> <input
	type="text" name="name" id="name" /> <label>Password <span
	class="small">Min. size 6 chars</span> </label> <input type="password"
	name="password" id="password" />
<div id="submit">Sign-in</div>
<div class="spacer"></div>
</form>
</div>
</div>
</div>
<div class="right_pane grid_6">
<div class="grid_6 alpha omega">
<div class="signupHint">Hints :</div>
</div>
<div class="clear"></div>
<div class="hintPane grid_6 alpha omega">
<ul>
	<li>User name must be a combination of words and number, and no
	space or other character is allowed.So do password.</li>
</ul>
</div>
<div class="grid_6 alpha omega">
<div class="signupHint">About This Project :</div>
</div>
<div class="hintPane grid_6 alpha omega">
<ul>
	<li>This project is based on jQuery(1.4.4), xhEditor and Gson.</li>
	<li>Featuring ajax.</li>
	<li>Run on Apache Tomcat v6.0.20 and MySQL 5.</li>
	<li>Special thanks to <a href="http://twitter.com/cjwdxqq">@cjwdxqq</a>.</li>
</ul>
</div>
<div class="grid_6 alpha omega">
<div class="signupHint">Roadmap :</div>
</div>
<div class="hintPane grid_6 alpha omega">
<ul>
	<li>Fix the modification bug of n.User and n.Reply</li>
	<li>Add delete and edit function.</li>
	<li>Add starting function.</li>
	<li>Add search function.</li>
</ul>
</div>
</div>
<div class="clear"></div>
</div>
<div class="footer container_12">copyright 2010 <a href="http://jo32.info">jo32.info</a> | source
code | contact : <a href="http://jo32.info">@jo32</a></div>
</body>
</html>