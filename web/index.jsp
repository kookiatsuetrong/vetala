<!DOCTYPE html>
<html>
	<head>
		<title>Home</title>
		<link rel="stylesheet" href="/normalize.css" />
		<link rel="stylesheet" href="/main.css" />
	</head>
	<body>
		<header>
			<section class="container navigator">
				<h3>Sample Logo</h3>
			</section>
			
			<section class="container banner">
			
			
			</section>
			
			<style>
				.container.navigator h3 {
					margin: 1rem 0;
				}
				.container.banner {
					min-height: 40rem;
					background: rgba(90%, 90%, 90%, .5);
					border-radius: 1rem;
				}
			</style>
		</header>
		<main>
			<section class="container">
				<h1>Sample <b>Awesome Feature</b>
					<br/>of Your Products</h1>
			</section>
			
			<section class="container trio">
				<section class="card">
					<h3>Feature X</h3>
					<p>The quick brown fox jumps over a lazy dog.</p>
					<section class="inner-card">
					
					</section>
				</section>
				<section class="card">
					<h3>Feature X</h3>
					<p>The quick brown fox jumps over a lazy dog.</p>
					<section class="inner-card">
					
					</section>
				</section>
				<section class="card">
					<h3>Feature X</h3>
					<p>The quick brown fox jumps over a lazy dog.</p>
					<section class="inner-card">
					
					</section>
				</section>
			</section>
			<style>
				main h1 {
					font-size: 3rem;
					margin: 2rem 0;
				}
				main .trio {
					display: grid;
					grid-template-columns: 1fr 1fr 1fr;
					column-gap: 1rem;
				}
				main .card {
					min-height: 20rem;
					background: rgba(100%, 100%, 100%, .8);
					background: rgba(80%, 80%, 80%, .8);
					padding: 1rem;
					border-radius: .75rem;
				}
				main .inner-card {
					background: #888;
					min-height: 13rem;
					border-radius: .5rem;
				}
			</style>
		</main>
		
		
		
		
		
		
		
		
		
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>
