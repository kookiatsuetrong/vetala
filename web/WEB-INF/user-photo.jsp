<!DOCTYPE html>
<%@page pageEncoding="UTF-8" import="server.User"%>
<html>
	<head>
		<meta name="viewport" content="width=device-width,viewport-fit=cover" />
		<title>Profile Photo</title>
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
						<h3>User Photo</h3>
						<p>
							User can change his or her photo
							from this page.
						</p>
					</section>
				</section>

				<section>

					<%
						User user = (User)session.getAttribute("user");
						String photo = "/uploaded/empty-photo.png";
						int userNumber = 0;
						if (user == null) { }
						if (user != null) {
							userNumber = user.number;
							photo = "/uploaded/profile-" + userNumber + ".png";
						}
					%>
					<img class="profile" id="profile" />

					<form class="user-form"
						method="post" 
						enctype="multipart/form-data">

						<section class="file-upload">
						</section>

						<br/>
						<button>Change Profile Photo</button>
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
				
				.file-upload {
					height: 3rem;
					border-style: dashed;
					border-width: .15rem;
					display: block;
				}
				.profile {
					width: 8rem;
					height: 8rem;
					border-radius: 6rem;
					background: white;
					border: .2rem solid var(--brand-color);
					margin-bottom: 1rem;
				}
			</style>
			
			<script src="/file-upload.js"></script>
			<script>
				FileUpload.create(".file-upload")
				FileUpload.setText("Choose Photo")
				FileUpload.setType("image/*")
				FileUpload.setMultiple(false)
				
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
			
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>
