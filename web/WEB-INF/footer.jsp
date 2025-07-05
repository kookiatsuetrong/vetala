<%@page pageEncoding="UTF8" import="server.User" %>
<%
User footerUser = (User)session.getAttribute("user");
%>
		<footer>
			<section class="container">
				<p>
					<a href="/">Home</a>
					<a href="/">About Us</a>
					<a href="/contact">Contact</a>
				</p>

				<% if (footerUser == null) {       %>
				<p>
					<a href="/user-check-email">Register</a>
					<a href="/user-check-email">Log In</a>
					<a href="/reset-password">Reset Password</a>
				</p>
				<% } %>


				<% if (footerUser != null) {       %>
				<p>
					<a href="/user-profile">Profile</a>
					<a href="/user-logout">Log Out</a>
				</p>
				<% } %>

				<p>
					The quick brown fox jumps over a lazy dog.
					The quick brown fox jumps over a lazy dog.
				</p>
			</section>

			<style>
				footer {

				}
				footer .container {
					display: grid;
				}
				@media (min-width: 640px) {
					footer .container {
						grid-template-columns: 1fr 1fr 1.5fr;
					}
				}
				footer a {
					margin: 0;
					display: block;
				}
				footer p {
					line-height: 2rem;
				}
			</style>

			<script>
				if (socket != null) {
					console.log("There exist the WebSocket instance")
				}
				
				var url = "ws://" + location.host + "/ws"
				// var url = "wss://" + location.host + "/ws"
				
				if (socket == null) {
					var socket = new WebSocket(url)
					console.log("WebSocket instance has been created")
				}
			</script>
		</footer>

