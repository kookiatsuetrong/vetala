<!DOCTYPE html>
<%@page pageEncoding="UTF-8" %>
<html>
	<head>
		<meta name="viewport" content="width=device-width,viewport-fit=cover" />
		<title>Log In</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container">
				<%
				String email = (String)session.getAttribute("email");
				if (email == null) email = "";
				session.removeAttribute("email");

				String message = (String)session.getAttribute("message");
				if (message == null) message = "";
				session.removeAttribute("message");
				%>
				<form class="user-form" method="post">
					<h2>Log In</h2>
					
					<% if ("".equals(message) == false) {       %>
						<span class="error">
							<%= message %>
						</span>
					<% }                                        %>
					
					<input name="email" 
						placeholder="Your Email" 
						autocomplete="off"
						type="email"
						readonly
						value="<%= email %>"
						/>
					<input name="password" 
						   type="password"
						   placeholder="Your Password"
						   autofocus
						   />
					<button>Log In</button>
				</form>
			</section>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>
