<%@page pageEncoding="UTF8" import="server.User"%><!DOCTYPE html>
<html>
	<head>
		<title>Friend Search</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container">
				<form class="user-form">
					<h3>Friend Search</h3>
					<input
						type="search" 
						placeholder="Name or email"
						autofocus
						/>
					<button>Search</button>
				</form>
			</section>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>

