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
<script type="text/javascript" src="js/signup.js"></script>
<title>Sign up</title>
</head>
<body>
<div class="statusBar">123</div>
<div class="topBar">
<div class="container_12">
<div class="grid_7">
<%@ include file="title.jsp"%>
</div>
<div class="options grid_5">
<ul>
	<li><a href="home.jsp">goHome()</a></li>
	<li><a href="signin.jsp">sign in</a></li>
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
        <h1>Sign-up form</h1>
        <p>Please input your valid information below.</p>
        <label>Name
           <span class="small">Add your user name</span>
       </label>
       <input type="text" name="name" id="name" />
       <label>Email
            <span class="small">Add a valid address</span>
       </label>
       <input type="text" name="email" id="email" />
       <label>Password
            <span class="small">Min. size 6 chars</span>
       </label>
       <input type="password" name="password" id="password" />
       <label>Password again
            <span class="small">input your password again</span>
       </label>
       <input type="password" name="rePassword" id="rePassword" />
       <div id="submit">Sign-up</div>
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
	<li>User name must be a combination of words and number, and no space or other character is allowed.So do password.</li>
	<li>Email address must match the pattern like yourname@domain.com</li>
</ul>
</div>
<div class="grid_6 alpha omega">
<div class="signupHint">Agreements :</div>
</div>
<div class="hintPane grid_6 alpha omega">
<ul>
	<li>U will see it here when there is one.</li>
</ul>
</div>
</div>
<div class="clear"></div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>