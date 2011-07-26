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
	if (user == null || !user.getRight().equals("admin")) {
		response.sendRedirect("signin.jsp");
	} else {
		userName = user.getName();
	}

	String pageString = request.getParameter("page");
	int pageInt = 0;
	if (pageString != null) {
		pageInt = Integer.parseInt(pageString);
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>log</title>
</head>
<body>
<%
	String path = "viewLog.jsp?page=" + pageInt;
	int lastPage = 0;
	int nextPage = 0;
	if (pageInt != 0) {
		lastPage = pageInt - 1;
	}
	nextPage = pageInt + 1;
	String lpath = "log.jsp?page=" + lastPage;
	String npath = "log.jsp?page=" + nextPage;
%>
<jsp:include page="<%= path %>"></jsp:include>
<a href="<%= lpath %>">上一页</a><a href="<%= npath %>">下一页</a>
</body>
</html>