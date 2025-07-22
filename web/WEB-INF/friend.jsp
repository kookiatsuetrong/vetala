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
			</section>
			
			<section class="container trio" id="friend-list">
				<%
					ArrayList<User> friends = (ArrayList<User>)
							request.getAttribute("friends");
					for (User u : friends) 
					{
				%>
				<section class="profile" id="profile-<%= u.number %>">
					<img class="profile-photo"
						id="profile-photo-<%= u.number %>"
						src="/uploaded/empty-photo.png" />
					<span>
						<a class="name"
							href="/member-detail?number=<%= u.number %>">
							<span id="name-<%= u.number %>">
								<%= u.firstName %> <%= u.lastName %>
							</span>
						</a>
						<span class='status' id='status-<%= u.number %>'>
							...
						</span>
					</span>
				</section>
				<%
					}
				%>
			</section>
			
			<section class="container">
				<h2>Your Requests</h2>
				<br/>
			</section>
			
			<section class="container trio">
				<%
					ArrayList<User> requests = (ArrayList<User>)
							request.getAttribute("requests");
					for (User u : requests) 
					{
				%>
				<section class="profile" id="profile-<%= u.number %>">
					<img class="profile-photo"
						id="profile-photo-<%= u.number %>"
						src="/uploaded/empty-photo.png" />
					<span>
						<a class="name"
							href="/member-detail?number=<%= u.number %>">
							<span id="name-<%= u.number %>">
								<%= u.firstName %> <%= u.lastName %>
							</span>
						</a>
						<span class='status' id='status-<%= u.number %>'>
							...
						</span>
						<a href="javascript:cancelRequest(<%= u.number %>)"
							>Cancel</a>
					</span>
				</section>
				<%
					}
				%>
			</section>

			<section class="container">
				<h2>Friend Requests</h2>
				<br/>
			</section>
			
			<section class="container trio">
				<%
					ArrayList<User> toMe = (ArrayList<User>)
							request.getAttribute("toMe");
					for (User u : toMe) 
					{
				%>
				<section class="profile" id="profile-<%= u.number %>">
					<img class="profile-photo"
						id="profile-photo-<%= u.number %>"
						src="/uploaded/empty-photo.png" />
					<span>
						<a class="name"
							href="/member-detail?number=<%= u.number %>">
							<span id="name-<%= u.number %>">
								<%= u.firstName %> <%= u.lastName %>
							</span>
						</a>
						<span class='status' id='status-<%= u.number %>'>
							...
						</span>
						<a href="javascript:rejectRequest(<%= u.number %>)"
							>Reject</a>
						<a href="javascript:acceptRequest(<%= u.number %>)"
							>Accept</a>
					</span>
				</section>
				<%
					}
				%>
			</section>
				
			<script>
				var users = [
							<%  for (User u : friends) {
									out.print(u.number);
									out.println(",");
								}
							%>
							<%  for (User u : requests) {
									out.print(u.number);
									out.println(",");
								}
							%>
							<%  for (User u : toMe) {
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
				
				async function cancelRequest(number) {
					var url = "/service-cancel-friend-request" +
								"?number=" + number
					var response = await fetch(url)
					if (response.status == 200) { 
						console.log("Cancel Friend Request")
						var target  = "profile-" + number
						var element = document.getElementById(target)
						element.style.display = "none"
					}
				}
				
				async function rejectRequest(number) {
					var url = "/service-reject-friend-request" +
								"?number=" + number
					var response = await fetch(url)
					if (response.status == 200) {
						console.log("Reject Friend Request")
						var target  = "profile-" + number
						var element = document.getElementById(target)
						element.style.display = "none"
					}
				}
				
				async function acceptRequest(number) {
					console.log("Accept Friend Request")
					var nameElement = document
									.getElementById("name-" + number)
					var name = nameElement == null ? "" : nameElement.innerText

					var template = document.getElementById("template")
					var newly = template.innerText
					newly = newly.replaceAll("###user-number###", number)
					newly = newly.replaceAll("###user-name###",   name)

					var url = "/service-accept-friend" +
								"?number=" + number
					var response = await fetch(url)
					if (response.status == 200) {
						var target  = "profile-" + number
						var element = document.getElementById(target)
						element.style.display = "none"
						
						var list = document.getElementById("friend-list")
						list.innerHTML += newly
						
						var photoLocation = "/uploaded/profile-" + 
												number + ".png"
						var photo         = "profile-photo-" + number
						var photoElement  = document.getElementById(photo)
						photoElement.src = await loadPhoto(photoLocation)
					}
				}
				
				/*
				displayFriends()
				
				async function displayFriends() {
					var url = "/service-list-friend"
					var response = await fetch(url)
					var detail = await response.text()
					if (detail.result == "OK") {
						for (var i = 0; i < detail.list.length; i++) {
							appendFriend(detail.list[i])
						}
					}
				}
				
				function appendFriend(user) {
					
				}
				*/
			</script>
			
			<script type="text" id="template">
				<section class="profile" id="profile-###user-number###">
					<img class="profile-photo"
						id="profile-photo-###user-number###"
						src="/uploaded/empty-photo.png" />
					<span>
						<a class="name"
							href="/member-detail?number=###user-number###">
							<span id="name-###user-number###">
								###user-name###
							</span>
						</a>
						<span class='status' id='status-###user-number###'>
							...
						</span>
					</span>
				</section>
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
		</main>
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>



