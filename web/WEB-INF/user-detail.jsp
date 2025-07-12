<%@page pageEncoding="UTF8" import="server.User"%><!DOCTYPE html>
<html>
	<head>
		<title>User Detail</title>
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
						<h3>User Detail</h3>

						<p>
							The detail and photo of 
							specific user.
						</p>
					</section>
				</section>
				<section>
					<%
						User user = (User)request.getAttribute("user");
						if (user == null) {
							user = new User();
						}
						if (user.firstName == null) user.firstName = "";
						if (user.lastName  == null) user.lastName  = "";
					%>
					<img src="/uploaded/empty-photo.png" 
						class="profile-photo"
						id="profile-<%= user.number %>" />
					<h3><%= user.firstName %>
						<%= user.lastName %>
					</h3>
					<span class="status" id="status-<%= user.number %>"
						>...</span>
				</section>
			</section>
			<script>
				var user = <%= user.number %>
				
				updateStatus()
				setInterval( () => updateStatus(), 5000)
				
				updatePhoto()
				
				async function updatePhoto() {
					var target = "profile-" + user
					var element = document.getElementById(target)
					var photo   = "/uploaded/profile-" + 
									user + ".png"
					element.src = await loadPhoto(photo)
				}

				async function loadPhoto(url) {
					var response = await fetch(url)
					if (response.status == 200) {
						return url
					}
					return "/uploaded/empty-photo.png"
				}
				
				function updateStatus() {
					var url = "/service-user-status?user=" + user
					fetch(url)
					.then(convertStatus)
					.then(displayStatus)
				}

				function convertStatus(response) {
					return response.json()
				}

				function displayStatus(detail) {
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
				
				.duo {
					display: grid;
					column-gap: 2rem;
				}
				@media (min-width: 640px) {
					.duo {
						grid-template-columns: 20rem 1fr;
					}
				}
				.left-panel {
					background: var(--brand-light);
					padding: 1rem 1rem .1rem 1rem;
					border-radius: .5rem;
					margin-bottom: 1rem;
				}
				.profile-photo {
					max-width: 4rem;
					border-radius: 4rem;
					border: .15rem solid var(--brand-color);
				}

				.status {
					font-size: .8rem;
					border-radius: 1rem;
					padding: .2rem .5rem;
				}
				.status-online {
					background: #8f8;
					color: #262;
				}
				.status-offline {
					background: #fdd;
					color: #622;
				}
			</style>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>


