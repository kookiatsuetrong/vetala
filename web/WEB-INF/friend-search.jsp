<%@page pageEncoding="UTF8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="server.User" %><!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width,viewport-fit=cover" />
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
						<h2>Friend Search</h2>
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
						out.println("<img src='/uploaded/empty-photo.png' " +
										"id='profile-" + u.number + "' />");
						out.println("</span>");
						
						out.println("<span>");
						out.println("<span class='name'>" +
									"<a href='/member-detail?number=" +
									u.number + "'>");
						out.println(u.firstName + " " + u.lastName);
						out.println("</a></span>");
						out.println("<span class='status' " +
										"id='status-" + u.number +
										"'>...</span>");
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
	
					updateStatus()
					setInterval( () => updateStatus(), 5000)
					
					updatePhoto()
					
					async function updatePhoto() {
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
					
					function updateStatus() {
						console.log("Update Status " + new Date() )
						for (var i = 0; i < users.length; i++) {
							var url = "/service-user-status?user=" + users[i]
							fetch(url)
							.then(convertStatus)
							.then(displayStatus)
						}
					}
					
					function convertStatus(response) {
						return response.json()
					}
					
					function displayStatus(detail) {
						console.log(detail)
						
						var target   = "status-" + detail.user
						var element  = document.getElementById(target)
						element.innerHTML = detail.result
						
						if (detail.result == "Online") {
							element.classList.add("status-online")
							element.classList.remove("status-offline")
						}
						
						if (detail.result == "Offline") {
							element.classList.add("status-offline")
							element.classList.remove("status-online")
						}
						
						// TODO: Add "Access Denied"
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
						margin: .5rem 0;
						font-size: 1rem;
						font-weight: bold;
					}
					.profile .status {
						font-size: .8rem;
						border-radius: 1rem;
						padding: .2rem .5rem;
					}
					.profile .status-online {
						background: #8f8;
						color: #262;
					}
					.profile .status-offline {
						background: #fdd;
						color: #622;
					}
				</style>
			</section>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>

