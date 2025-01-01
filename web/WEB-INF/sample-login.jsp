<!DOCTYPE html>
<html>
	<head>
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
				String message   = (String)session.getAttribute("message");
				if (message == null) message = "";
				session.removeAttribute("message");
				%>

				<form class="user-form" method="post">
					<h3>Log In</h3>
					
					<% if ("".equals(message) == false) {       %>
						<span class="error">
							<%= message %>
						</span>
					<% }                                        %>
					<input name="email" 
						placeholder="Your Email"
						required
						type="email"
						autocomplete="off"
						/>

					<input name="password" 
						placeholder="Your Password"
						required
						type="password"
						autocomplete="off"
						/>


					<button>Log In</button>
				</form>
			</section>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>


