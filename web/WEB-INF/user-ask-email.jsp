<%@page pageEncoding="UTF8" %><!DOCTYPE html>
<html>
	<head>
		<title>Register or Log In</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container">
				
				<%
				String message   = (String)session.getAttribute("message");
				if (message == null) message = "";
				session.removeAttribute("message");
				
				String photoCode = (String)session.getAttribute("photo-code");
				if (photoCode == null) photoCode = "";
				session.removeAttribute("photo-code");
				%>

				<form class="user-form" method="post">
					<h2>Register or Log In</h2>
					
					<% if ("".equals(message) == false) {       %>
						<span class="error">
							<%= message %>
						</span>
					<% }                                        %>
					<input name="email" 
						placeholder="Your Email" 
						autofocus 
						required
						type="email"
						autocomplete="off"
						/>
					<span class="trio">
						<input name="code" 
							placeholder="4-Digit Code" 
							required
							type="number"
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

					<button>Continue</button>
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
			</style>

		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>

