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
					<h3>Package X</h3>
					<p>The quick brown fox jumps over a lazy dog.</p>
					<section class="inner-card color-1">
					
					</section>
				</section>
				<section class="card">
					<h3>Package Y</h3>
					<p>The quick brown fox jumps over a lazy dog.</p>
					<section class="inner-card color-2">
					
					</section>
				</section>
				<section class="card">
					<h3>Package Z</h3>
					<p>The quick brown fox jumps over a lazy dog.</p>
					<section class="inner-card color-3">
					
					</section>
				</section>
			</section>
			
			<section class="container">
				<h1>Write <b>More Feature</b>
					<br/>of Your Products</h1>
			</section>
			
			<section class="statistics">
				<section class="container trio">
					<section>
						<h1>100%</h1>
						<h3>Sample Message</h3>
					</section>
					<section>
						<h1>100%</h1>
						<h3>Sample Message</h3>
					</section>
					<section>
						<h1>100%</h1>
						<h3>Sample Message</h3>
					</section>
				</section>
			</section>
			<style>
				.statistics {
					text-align: center;
					background: rgba(20%, 20%, 20%, .75);
					padding: 4rem 0 6rem 0;
					color: white;
				}
				.statistics h1 {
					margin-bottom: 0;
				}
			</style>
			
			<section class="container">
				<h1>Discover <b>More Feature</b>
					<br/>of Our Products</h1>
			</section>
			
			<section class="container trio">
				<section class="feature">
					<h1>X</h1>
					<h3>Feature X</h3>
					<p>
						The quick brown fox jumps
						over a lazy dog.
						Describe more detail of this feature.
					</p>
				</section>
				<section class="feature">
					<h1>X</h1>
					<h3>Feature X</h3>
					<p>
						The quick brown fox jumps
						over a lazy dog.
						Describe more detail of this feature.
					</p>
				</section>
				<section class="feature">
					<h1>X</h1>
					<h3>Feature X</h3>
					<p>
						The quick brown fox jumps
						over a lazy dog.
						Describe more detail of this feature.
					</p>
				</section>
				<section class="feature">
					<h1>X</h1>
					<h3>Feature X</h3>
					<p>
						The quick brown fox jumps
						over a lazy dog.
						Describe more detail of this feature.
					</p>
				</section>
				<section class="feature">
					<h1>X</h1>
					<h3>Feature X</h3>
					<p>
						The quick brown fox jumps
						over a lazy dog.
						Describe more detail of this feature.
					</p>
				</section>
				<section class="feature">
					<h1>X</h1>
					<h3>Feature X</h3>
					<p>
						The quick brown fox jumps
						over a lazy dog.
						Describe more detail of this feature.
					</p>
				</section>
			</section>
			
			<style>
				main h1 {
					font-size: 3rem;
					margin: 2rem 0;
				}
				main .trio {
					display: grid;
					grid-template-columns: 1fr;
					column-gap: 1rem;
				}
				@media (min-width: 520px) {
					main .trio {
						grid-template-columns: 1fr 1fr;
					}
				}
				@media (min-width: 768px) {
					main .trio {
						grid-template-columns: 1fr 1fr 1fr;
					}
				}
				main .card {
					min-height: 20rem;
					background: rgba(100%, 100%, 100%, .8);
					background: rgba(90%, 90%, 90%, .5);
					padding: 1rem;
					border-radius: .75rem;
					margin-bottom: 2rem;
				}
				main .inner-card {
					background: #888;
					min-height: 13rem;
					border-radius: .5rem;
				}
				main .color-1 {
					background: dodgerblue;
				}
				main .color-2 {
					background: steelblue;
				}
				main .color-3 {
					background: slategray;
				}
			</style>
		</main>
		
		
		
		
		
		
		
		
		
		
		<%@include file="/WEB-INF/footer.jsp" %>
	</body>
</html>
