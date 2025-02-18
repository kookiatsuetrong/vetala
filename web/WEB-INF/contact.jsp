<!DOCTYPE html>
<html>
	<head>
		<title>Contact</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container">
				<%
				String message = (String)session.getAttribute("message");
				if (message == null) message = "";
				session.removeAttribute("message");

				String photoCode = (String)session.getAttribute("photo-code");
				if (photoCode == null) photoCode = "";
				session.removeAttribute("photo-code");

				String email = (String)session.getAttribute("email");
				if (email == null) email = "";
				session.removeAttribute("email");
				
				String topic = (String)session.getAttribute("topic");
				if (topic == null) topic = "";
				session.removeAttribute("topic");
				
				String detail = (String)session.getAttribute("detail");
				if (detail == null) detail = "";
				session.removeAttribute("detail");
				%>
				<form class="user-form" method="post"
					enctype="multipart/form-data">
					<h3>Contact</h3>
					<% if ("".equals(message) == false) {       %>
						<span class="error">
							<%= message %>
						</span>
					<% }                                        %>
					<input name="topic"
						placeholder="Topic"
						required
						autofocus
						autocomplete="off"
						value="<%= topic %>"
						/>
					<textarea name="detail"
						placeholder="Detail"><%= detail %></textarea>

					<section class="file-upload">
					</section>

					<input name="email"
						placeholder="Your Email" 
						type="email"
						required
						value="<%= email %>"
						autocomplete="off"
						/>
					<span class="trio">
						<input name="code" 
							placeholder="4-Digit Code"
							type="number"
							required 
							autocomplete="off"
							/>
						
						<svg width="24" height="24" 
							viewBox="0 0 24 24" fill="none" 
							stroke="#888" stroke-width="2" 
							stroke-linecap="round" 
							stroke-linejoin="round">
							<path d="M19 12H6M12 5l-7 7 7 7"/>
						</svg>
						
						<img src="data:image/png;base64, 
							<%= photoCode %>" />
					</span>
					<button>Send</button>
				</form>
			</section>
			
			<style>
				.trio {
					display: grid;
					grid-template-columns: 1fr 1.5rem 4rem;
					column-gap: .5rem;
				}
				.trio svg {
					margin-top: .4rem;
				}
				img {
					background: rgba(240,240,240,.8);
					padding: .65rem;
					margin-top: .005rem;
					border-radius: .35rem;
				}
				.file-upload {
					margin-bottom: .5rem;
				}
			</style>

			<script src="/file-upload.js"></script>
			<script>
				FileUpload.create(".file-upload")
				FileUpload.setText("File Upload")
				FileUpload.setType("image/*")
				/*
				FileUpload.setStyle({
						borderColor: "darkgreen", 
						color: "#ccc",
						background: "#8ba"
					}) */
			</script>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>

