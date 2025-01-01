<%
// User footerUser = (User)session.getAttribute("user");
Object footerUser = null;
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

		</footer>
