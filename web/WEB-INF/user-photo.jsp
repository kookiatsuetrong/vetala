<%@page pageEncoding="UTF8" import="server.User"%><!DOCTYPE html>
<html>
	<head>
		<title>Profile Photo</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
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
			<section class="container">
				
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
			<style>
				.user-form {
					margin: 0;
				}
				.profile {
					max-width: 8rem;
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
