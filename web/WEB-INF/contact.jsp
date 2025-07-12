<%@page pageEncoding="UTF8" %><!DOCTYPE html>
<html>
	<head>
		<title>Contact</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container duo">
				<section class="left-panel">
					<h3>Contact</h3>
					<p>
						Use this page to send
						message or photo
						directly to the operation team.
					</p>
				</section>
				<section>
					<%
					String message = (String)session.getAttribute("message");
					if (message == null) message = "";
					session.removeAttribute("message");

					String photoCode = (String)session
										.getAttribute("photo-code");
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
						<h2>Contact</h2>
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
					padding: 1rem 1rem 0 1rem;
					border-radius: .5rem;
				}
				.left-panel p {
					color: #666;
					padding: 0;
				}
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

