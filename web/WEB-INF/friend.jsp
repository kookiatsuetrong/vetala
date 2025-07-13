<%@page pageEncoding="UTF8" %>
<%@page import="java.util.ArrayList"%>
<%@page import="server.User"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Friends</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container">
				<h2>Friends</h2>
				<br/>
				<br/>
			</section>
			
			<section class="container trio">
				<%
					ArrayList<User> friends = (ArrayList<User>)
							request.getAttribute("friends");
					for (User u : friends) 
					{
				%>
				<section class="profile">
					<img class="profile-photo"
						id="profile-photo-<%= u.number %>"
						src="/uploaded/empty-photo.png" />
					<span>
						<a class="name"
							href="/member-detail?number=<%= u.number %>">
							<%= u.firstName %> <%= u.lastName %>
						</a>
						<span class='status' id='status-<%= u.number %>'>
							...
						</span>
					</span>
					
				</section>
				<%
					}
				%>
				
				<script>
					var users = [
									<%  for (User u : friends) {
										out.print(u.number);
										out.println(",");
										}
									%>						
								]
					updatePhoto()
					updateStatus()
					setInterval( () => updateStatus(), 5000)
					
					async function updatePhoto() {
						for (var i = 0; i < users.length; i++) {
							var target = "profile-photo-" + users[i]
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
					.trio {
						display: grid;
						column-gap: 2rem;
					}
					@media (min-width: 640px) {
						.trio {
							grid-template-columns: 1fr 1fr;
						}
					}
					@media (min-width: 768px) {
						.trio {
							grid-template-columns: 1fr 1fr 1fr;
						}
					}
					.trio section {
						margin-bottom: 2rem;
					}
					.profile {
						display: grid;
						grid-template-columns: 4rem 1fr;
						column-gap: 1rem;
					}
					.profile-photo {
						max-width: 4rem;
						border: .15rem solid var(--brand-color);
						border-radius: 4rem;
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



