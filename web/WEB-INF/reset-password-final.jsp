<%@page pageEncoding="UTF8" %><!DOCTYPE html>
<html>
	<head>
		<title>Password Changed</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container">
				<%
				String message = (String)session.getAttribute("message");
				if (message == null) message = "";
				session.removeAttribute("message");		
				%>
				
				<p>
				<%= message %>
				</p>
				<a class="button" href="/user-check-email">Log In</a>
				<a class="button" href="/reset-password">Reset Password</a>
			</section>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>
