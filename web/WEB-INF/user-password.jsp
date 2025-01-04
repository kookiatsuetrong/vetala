<%@page import="server.User"%><!DOCTYPE html>
<html>
	<head>
		<title>Change Password</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<%
				String message = (String)session.getAttribute("message");
				if (message == null) message = "";
				session.removeAttribute("message");
			%>
			<section class="container">
				<form class="user-form" method="post">
					<h3>Change Password</h3>
				
					<% if ("".equals(message) == false) {     %>
					<span class="error"><%= message %></span>
					<% }                                      %>
					
					<input name="current" 
						type="password"
						placeholder="Current Password"
						autocomplete="off"
						required
						/>
					
					<input name="password" 
						type="password"
						placeholder="New Password"
						autocomplete="off"
						required
						/>
					
					<input name="confirm"
						type="password"
						placeholder="Confirm Password"
						autocomplete="off"
						required
						/>
					
					<button>Change Password</button>
				</form>
			</section>
			<style>
				.user-form {
					margin: 0;
				}
			</style>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>






