<%@page pageEncoding="UTF8" import="server.User"%><!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width,viewport-fit=cover" />
		<title>Profile</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container profile-top">
				<%
				User user = (User)session.getAttribute("user");
				String photo = "/uploaded/empty-photo.png";
				String userName = "";
				String userEmail = "";
				int userNumber = 0;
				if (user == null) { }
				if (user != null) {
					userName   = user.firstName + " " + user.lastName;
					userNumber = user.number;
					userEmail  = user.email;
					photo = "/uploaded/profile-" + userNumber + ".png";
				}
				%>
				<section>
					<img class="profile" id="profile" />
				</section>
				
				<section>
					<b><%= userName %></b>
					<br/>
					<code><%= userEmail %></code>
				</section>
			</section>
			
			<style>
				.profile-top {
					display: grid;
					grid-template-columns: 4rem 1fr;
					column-gap: 2rem;
				}
				.profile-top code {
					color: #888;
				}
				.profile {
					width: 4rem;
					height: 4rem;
					border-radius: 3rem;
					background: white;
					border: .2rem solid var(--brand-color);
					margin-bottom: 1rem;
				}
			</style>
			
			<script src="/file-upload.js"></script>
			<script>
				checkCurrentPhoto()
				
				async function checkCurrentPhoto() {
					var response = await fetch("<%= photo %>")
					var photo = "/uploaded/empty-photo.png"
					if (response.status == 200) {
						photo = "<%= photo %>"
					}
					var element = document.getElementById("profile")
					element.setAttribute("src", photo)
				}

			</script>
			
			<section class="container block-menu-container">
				<a class="block-menu" href="/">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M20 9v11a2 2 0 0 
								1-2 2H6a2 2 0 0 1-2-2V9" />
						<path d="M9 22V12h6v10M2 
								10.6L12 2l10 8.6" />
					</svg>
					<h3>Home</h3>
					<p>
						The landing page of the website.
					</p>
				</a>
				<a class="block-menu" href="/contact">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M4 4h16c1.1 0 2 .9 2 
							2v12c0 1.1-.9 2-2 2H4c-1.1 
							0-2-.9-2-2V6c0-1.1.9-2 2-2z" />
						<polyline points="22,6 12,13 2,6" />
					</svg>
					<h3>Contact</h3>
					<p>
						Contact the administrator
						of the website.
					</p>
				</a>
				<a class="block-menu" href="/user-profile">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">
						<rect x= "3" y= "3" width="7" height="7" />
						<rect x="14" y= "3" width="7" height="7" />
						<rect x="14" y="14" width="7" height="7" />
						<rect x= "3" y="14" width="7" height="7" />
					</svg>
					<h3>Profile</h3>
					<p>
						View the profile of
						current account.
					</p>
				</a>
				
				<a class="block-menu" href="/friends">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">

						<path d="M17 21v-2a4 4 0 0 
							0-4-4H5a4 4 0 0 0-4 4v2" />
						<circle cx="9" cy="7" r="4" />
						<path d="M23 21v-2a4 4 0 0 0-3-3.87" />
						<path d="M16 3.13a4 4 0 0 1 0 7.75" />
					</svg>
					<h3>Friends</h3>
					<p>
						View all friends.
					</p>
				</a>
				
				<a class="block-menu" href="/friend-search">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">
										
						<circle cx="11" cy="11" r="8" />
						<line x1="21" y1="21" x2="16.65" y2="16.65" />
					</svg>
					<h3>Search</h3>
					<p>
						Search friends and other users.
					</p>
				</a>
				
				<a class="block-menu" href="/user-photo">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M5.52 19c.64-2.2 1.84-3 
							3.22-3h6.52c1.38 0 2.58.8 3.22 3" />
						<circle cx="12" cy="10" r="3" />
						<circle cx="12" cy="12" r="10" />
					</svg>
					<h3>Photo</h3>
					<p>
						Change the profile photo.
					</p>
				</a>
				
				<a class="block-menu" href="/user-password">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M20.59 13.41l-7.17 7.17a2 
								2 0 0 1-2.83 0L2 12V2h10l8.59 
								8.59a2 2 0 0 1 0 2.82z" />
						<line x1="7" y1="7" x2="7.01" y2="7" />
					</svg>
					<h3>Password</h3>
					<p>
						Change password for current account.
					</p>
				</a>

				<% if (user != null && "administrator".equals(user.type)) { %>
				<a class="block-menu" href="/system-settings">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">

						<circle cx="12" cy="12" r="3" />
						<path d="M19.4 15a1.65 1.65 0 0 0 
							.33 1.82l.06.06a2 2 0 0 1 0 
							2.83 2 2 0 0 1-2.83 
							0l-.06-.06a1.65 1.65 
							0 0 0-1.82-.33 1.65 1.65 
							0 0 0-1 1.51V21a2 2 0 0 
							1-2 2 2 2 0 0 1-2-2v-.09A1.65 
							1.65 0 0 0 9 19.4a1.65 1.65 
							0 0 0-1.82.33l-.06.06a2 2 
							0 0 1-2.83 0 2 2 0 0 1 
							0-2.83l.06-.06a1.65 1.65 
							0 0 0 .33-1.82 1.65 1.65 
							0 0 0-1.51-1H3a2 2 0 0 1-2-2 
							2 2 0 0 1 2-2h.09A1.65 1.65 
							0 0 0 4.6 9a1.65 1.65 0 0 
							0-.33-1.82l-.06-.06a2 2 0 0 1 
							0-2.83 2 2 0 0 1 2.83 
							0l.06.06a1.65 1.65 0 0 0 
							1.82.33H9a1.65 1.65 0 0 0 
							1-1.51V3a2 2 0 0 1 2-2 2 2 
							0 0 1 2 2v.09a1.65 1.65 0 0 0 
							1 1.51 1.65 1.65 0 0 0 
							1.82-.33l.06-.06a2 2 0 0 1 
							2.83 0 2 2 0 0 1 0 
							2.83l-.06.06a1.65 1.65 
							0 0 0-.33 1.82V9a1.65 1.65 
							0 0 0 1.51 1H21a2 2 0 0 
							1 2 2 2 2 0 0 1-2 2h-.09a1.65 
							1.65 0 0 0-1.51 1z" />
					</svg>
					<h3>Settings</h3>
					<p>
						Change system settings and reload.
					</p>
				</a>

				<a class="block-menu" href="/system-users">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">

						<path d="M17 21v-2a4 4 0 0 
							0-4-4H5a4 4 0 0 0-4 4v2" />
						<circle cx="9" cy="7" r="4" />
						<path d="M23 21v-2a4 4 0 0 0-3-3.87" />
						<path d="M16 3.13a4 4 0 0 1 0 7.75" />

					</svg>
					<h3>Users</h3>
					<p>
						Display all users information.
					</p>
				</a>

				<% } %>

				<a class="block-menu" href="/user-logout">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="2" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M18.36 6.64a9 9 0 1 1-12.73 0" />
						<line x1="12" y1="2" x2="12" y2="12" />
					</svg>
					<h3>Log Out</h3>
					<p>
						Log out from current account.
					</p>
				</a>
			</section>
			
			<style>
			.block-menu-container {
				display: grid;
				grid-template-columns: 1fr;
			}
			
			@media (min-width: 512px) {
				.block-menu-container {
					grid-template-columns: 1fr 1fr;
					column-gap: 2rem;
				}
			}
			
			@media (min-width: 768px) {
				.block-menu-container {
					grid-template-columns: 1fr 1fr 1fr;
				}
			}
			.block-menu {
				padding: 1rem;
				border: none;
				border-radius: .5rem;
				margin-bottom: 2rem;
				background: rgba(20%, 80%, 40%, 0.8);
				background: var(--header-background);
			}
			.block-menu h3 {
				font-weight: regular;
				font-size: 2rem;
				color: white;
			}
			.block-menu p {
				color: white;
				line-height: 1.5rem;
				margin-top: .5rem;
			}
			.block-menu svg {
				width: 4rem;
				height: 4rem;
				float: right;
			}
			</style>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>
