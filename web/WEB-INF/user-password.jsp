<%@page pageEncoding="UTF8" import="server.User"%><!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width,viewport-fit=cover" />
		<title>Change Password</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container duo">
				<section>
					<section class="left-panel">
						<h3>Change Password</h3>
						<p>
							User can change his or her password
							from this page.
						</p>
					</section>
				</section>
				
				<section>
					
					<%
						String message = (String)session
										.getAttribute("message");
						if (message == null) message = "";
						session.removeAttribute("message");
					%>
					<form class="user-form" method="post">

						<h2>Change Password</h2>

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
			</section>
			<style>
				.duo {
					display: grid;
					column-gap: 2rem;
				}
				@media (min-width: 640px) {
					.duo {
						grid-template-columns: 20rem 1fr;
					}
				}
				.user-form {
					margin: 0;
				}
				.user-form h2 {
					color: var(--brand-color);
					margin-bottom: .5rem;
				}
				.left-panel {
					background: var(--brand-light);
					padding: 1rem 1rem .1rem 1rem;
					border-radius: .5rem;
					margin-bottom: 1rem;
				}
				.left-panel p {
					color: #666;
					padding: 0;
				}
			</style>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>
