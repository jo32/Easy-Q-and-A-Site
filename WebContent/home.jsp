<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="info.jo32.EasyQandASite.controller.User"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String userName = "";
	User user;
	HttpSession hs = request.getSession();
	user = (User) hs.getAttribute("user");
	if (user == null) {
		response.sendRedirect("signin.jsp");
	} else {
		userName = user.getName();
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="960gs.css">
<link type="text/css" rel="stylesheet" href="style.css">
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="xhEditor/xheditor-en.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
<title>home</title>
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
	<li>welcome back, <%=userName%></li>
	<li><a href="signout">sign out</a></li>
</ul>
</div>
<div class="clear"></div>
</div>
</div>
<div class="container_12">
<div class="sort grid_4">
<div class="sortby grid_1 alpha">sort by :</div>
<div id="byTime" class="time_sorting grid_1"><a>time</a></div>
<div id="byReplies" class="reply_sorting grid_1"><a>replies</a></div>
<div id="byDiggs" class="digg_sorting grid_1 omega"><a>diggs</a></div>
</div>
<div class="sort grid_3">
<div class="sortby grid_1 alpha">post:</div>
<div id="viewMine" class="time_sorting grid_1"><a>by me</a></div>
<div id="viewAll" class="reply_sorting grid_1 omega"><a>all</a></div>
</div>
<div class="clear"></div>
</div>
<div class="container container_12">
<div class="left_pane grid_6">
<div class="grid_2 alpha">
<div class="topicTitle">List&lt;Topic&gt;</div>
</div>
<div class="grid_1">
<div class="prev"><a>prev</a></div>
</div>
<div class="grid_1">
<div class="next"><a>next</a></div>
</div>
<div class="grid_2 omega">
<div class="newTopic"><a href="newtopic.jsp">new topic()</a></div>
</div>
<div class="clear"></div>
<div class="contentWrapper grid_6 alpha omega">
<div class="topicList"></div>
</div>
</div>
<div class="right_pane grid_6">
<div class="grid_3 alpha">
<div class="replyTitle">Topic.getCotent()</div>
</div>
<div class="grid_1">
<div id="digg" class="diggThisPost"><a>digg</a></div>
</div>
<div class="grid_1">
<div id="edit" class="diggThisPost"><a>edit</a></div>
</div>
<div class="grid_1 omega">
<div id="delete" class="diggThisPost"><a>delete</a></div>
</div>
<div class="clear"></div>
<div class="right contentWrapper grid_6 alpha omega"></div>
</div>
<div class="clear"></div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>