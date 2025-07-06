<%@page pageEncoding="UTF8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="server.User" %><!DOCTYPE html>
<html>
	<head>
		<title>Friend Search</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container">
				<section class="duo">
					<form class="user-form">
						<h3>Friend Search</h3>
						<input
							name="query"
							type="search" 
							placeholder="Name or email"
							autofocus
							/>
						<button>Search</button>
					</form>
					<section>
					<%
					ArrayList<User> list = (ArrayList<User>)
											request.getAttribute("list");
											
					if (list == null) { list = new ArrayList<User>(); }
					for (User u : list) {
						out.println("<section class='profile'>");
						out.println("<span>");
						out.println("<img id='profile-" +
										u.number + "' />");
						out.println("</span>");
						
						out.println("<span>");
						out.println("<span class='name'>");
						out.println(u.firstName + " " + u.lastName);
						out.println("</span>");
						out.println("</span>");
						out.println("</section>");
					}
					%>
					</section>
				</section>
				<script>
					var users =  [<%
						for (User u : list) {
							out.println(u.number + ",");
						}
								%>]
					start()
					async function start() {
						for (var i = 0; i < users.length; i++) {
							var target = "profile-" + users[i]
							var element = document.getElementById(target)
							var photo   = "/uploaded/profile-" + 
											users[i] + ".png"
							element.src = await loadPhoto(photo)
						}
					}
					async function loadPhoto(url) {
						var response = await fetch(url)
						if (response.status == 200) {
							return url
						}
						return "/uploaded/empty-photo.png"
					}
				</script>
				<style>
					.user-form {
						margin: 0;
						margin-bottom: 2rem;
					}
					.duo {
						display: grid;
						column-gap: 1rem;
					}
					@media (min-width: 720px) {
						.duo {
							grid-template-columns: 20rem 1fr;
						}
					}
					.profile {
						display: grid;
						column-gap: 1rem;
						grid-template-columns: 4rem 1fr;
						margin-bottom: 1rem;
					}
					.profile img {
						max-width: 4rem;
						border-radius: 4rem;
						border: .15rem solid var(--brand-color);
					}
					.profile .name {
						display: block;
						margin-top: .5rem;
						font-size: 1rem;
						font-weight: bold;
					}
				</style>
			</section>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>

