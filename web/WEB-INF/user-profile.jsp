<%@page import="server.User"%><!DOCTYPE html>
<html>
	<head>
		<title>Profile</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
		<link rel="icon" href="/favicon.ico" />
	</head>
	<body>
		<header></header>
		<main>
			<section class="container">
				<%
				User user = (User)session.getAttribute("user");
				%>
				<p>Email: <%= user.email %></p>				
			</section>
			
			<section class="container block-menu-container">
				<a class="block-menu" href="">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="1.5" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M6 2L3 6v14c0 1.1.9 
							2 2 2h14a2 2 0 0 0 
							2-2V6l-3-4H6zM3.8 
							6h16.4M16 10a4 4 0 1 1-8 0" />
					</svg>
					<h3>Menu Item</h3>
					<p>
						Detail information about
						this menu item.
					</p>
				</a>
				<a class="block-menu" href="">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="1.5" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M6 2L3 6v14c0 1.1.9 
							2 2 2h14a2 2 0 0 0 
							2-2V6l-3-4H6zM3.8 
							6h16.4M16 10a4 4 0 1 1-8 0" />
					</svg>
					<h3>Menu Item</h3>
					<p>
						Detail information about
						this menu item.
					</p>
				</a>
				<a class="block-menu" href="">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="1.5" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M6 2L3 6v14c0 1.1.9 
							2 2 2h14a2 2 0 0 0 
							2-2V6l-3-4H6zM3.8 
							6h16.4M16 10a4 4 0 1 1-8 0" />
					</svg>
					<h3>Menu Item</h3>
					<p>
						Detail information about
						this menu item.
					</p>
				</a>
				<a class="block-menu" href="">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="1.5" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M6 2L3 6v14c0 1.1.9 
							2 2 2h14a2 2 0 0 0 
							2-2V6l-3-4H6zM3.8 
							6h16.4M16 10a4 4 0 1 1-8 0" />
					</svg>
					<h3>Menu Item</h3>
					<p>
						Detail information about
						this menu item.
					</p>
				</a>
				<a class="block-menu" href="">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="1.5" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M6 2L3 6v14c0 1.1.9 
							2 2 2h14a2 2 0 0 0 
							2-2V6l-3-4H6zM3.8 
							6h16.4M16 10a4 4 0 1 1-8 0" />
					</svg>
					<h3>Menu Item</h3>
					<p>
						Detail information about
						this menu item.
					</p>
				</a>
				<a class="block-menu" href="">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="1.5" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M6 2L3 6v14c0 1.1.9 
							2 2 2h14a2 2 0 0 0 
							2-2V6l-3-4H6zM3.8 
							6h16.4M16 10a4 4 0 1 1-8 0" />
					</svg>
					<h3>Menu Item</h3>
					<p>
						Detail information about
						this menu item.
					</p>
				</a>
				<a class="block-menu" href="">
					<svg width="24" height="24" 
						viewBox="0 0 24 24" fill="none" stroke="white" 
						stroke-width="1.5" stroke-linecap="round" 
						stroke-linejoin="round">
						<path d="M6 2L3 6v14c0 1.1.9 
							2 2 2h14a2 2 0 0 0 
							2-2V6l-3-4H6zM3.8 
							6h16.4M16 10a4 4 0 1 1-8 0" />
					</svg>
					<h3>Menu Item</h3>
					<p>
						Detail information about
						this menu item.
					</p>
				</a>
				<style>
				.block-menu-container {
					display: grid;
					grid-template-columns: 1fr 1fr 1fr;
					column-gap: 2rem;
				}
				.block-menu {
					padding: 1rem;
					border: .15rem solid var(--button-border);
					border-radius: .5rem;
					margin-bottom: 2rem;
					background: rgba(20%, 80%, 40%, 0.8);
					background: linear-gradient(45deg,
										lightseagreen,
										var(--button-background)
										);
				}
				.block-menu h3 {
					font-weight: bolder;
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
			</section>
		</main>
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>





