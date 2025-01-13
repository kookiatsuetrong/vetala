<!DOCTYPE html><%@page pageEncoding="UTF8" %>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,viewport-fit=cover" />

	<title>Sample</title>
	<link rel="stylesheet" href="/normalize.css" />
	<link rel="stylesheet" href="/main.css" />
	<link rel="icon" href="/favicon.ico" />

	<meta property="og:title"            content="Sampe" />
	<meta property="og:type"             content="website" />
	<meta property="og:url"              content="" />
	<meta property="og:image"            content="" />
	<meta property="og:image:secure_url" content="" />
	<meta property="og:image:type"       content="image/jpg" />
	<meta property="og:image:width"      content="1980" />
	<meta property="og:image:height"     content="1280" />
	<meta property="og:description"      content="Sample" />
</head>
<body>
	<header>
		<nav class="container">
			<span>
				<a class="logo" href=""><h3>Sample</h3></a>
			</span>
			<span>
				&nbsp;
			</span>
			<span class="menu-container">
				<a class="menu" href="/user-register">Register</a>
				<a class="menu" href="/user-login">Log In</a>
			</span>
		</nav>

		<section class="container">
			<section class="duo">
				<section>
					<h1>Your Slogan Here</h1>
					<p>
						The quick brown fox jumps
						over a lazy dog.
					</p>
					<br/>
					<a class="white-button" 
					   href="/user-register">Register</a>
					<a class="white-button" 
					   href="/user-login">Log In</a>
				</section>
				<section>
					<img src="" />
				</section>
			</section>
		</section>
		<style>
		body {
			/* background-color: white; */
		}
		header {
			min-height: 35rem;
			background-image: var(--header-background);
			color: white;
		}
		header nav {
			padding-top: .5rem;
			display: grid;
			grid-template-columns: 10rem 1fr 16rem;
		}
		header nav span {
			height: 2rem;
		}
		header nav .menu-container {
			text-align: right;
			display: none;
			padding-top: .25rem;
		}
		header nav .menu {
			margin-left: 1rem;
			transition: color .15s linear;
		}
		header nav .menu:hover {
			color: #666;
		}
		header nav a {
			color: white;
			font-size: 1.25rem;
		}
		@media (min-width: 520px) {
			header nav .menu-container {
				display: block;
			}
		}
		header h1 {
			font-size: 3rem;
			margin-top: 10rem;
		}
		header p {
			font-size: 1.25rem;
			color: white;
			margin-top: 1rem;
			line-height: 2rem;
		}

		header .duo {
			grid-template-columns: 1.5fr 1fr;
		}
		@media (min-width: 980px) {
			header .duo {
				grid-template-columns: 1fr 1.25fr;
				column-gap: 2rem;
			}
		}
		header img {
			width: 100%;
			margin-top: 4rem;
		}

		header .white-button {
			background: white;
			padding: .5rem 1rem;
			color: #666;
			border-radius: 2rem;
			border: .1rem solid white;
			margin-right: .5rem;
			transition: background .15s linear,
						color .15s linear;
		}
		header .white-button:hover {
			background: none;
			color: white;
		}
		</style>
	</header>

	<main>
		<br/>
		<h2 class="slogan">Unique Selling Point</h2>
		<br/>

		<section class="container trio">
			<p>
				The quick brown fox jumps over a lazy dog.
				The quick brown dog jumps over a lazy fox.
				The quick brown fox jumps over a lazy dog.
			</p>
			<p>
				The quick brown fox jumps over a lazy dog.
				The quick brown dog jumps over a lazy fox.
				The quick brown fox jumps over a lazy dog.
			</p>
			<p>
				The quick brown fox jumps over a lazy dog.
				The quick brown dog jumps over a lazy fox.
				The quick brown fox jumps over a lazy dog.
			</p>
		</section>
		<br/>
		<br/>
		<section class="container duo">
			<section class="panel">
				<h3>Register</h3>
				<p>
				The quick brown fox jumps over a lazy dog.
				The quick brown dog jumps over a lazy fox.
				The quick brown fox jumps over a lazy dog.
				<br/>
				<br/>
				</p>
				<span>
					<a class="button" 
						href="/user-register">Register</a>
				</span>
			</section>
			<section class="panel">
				<h3>Log In</h3>
				<p>
				The quick brown fox jumps over a lazy dog.
				</p>
				<span>
					<a class="button" 
						href="/user-login">Log In</a>
				</span>
			</section>
		</section>
		<br/>
		<br/>

		<section class="statistics">
			<section class="container trio">
				<span>
					<h1>24x7</h1>
					<p>Some Statistics</p>
				</span>
				<span>
					<h1>100+</h1>
					<p>Some Statistics</p>
				</span>
				<span>
					<h1>99.7%</h1>
					<p>Some Statistics</p>
				</span>
			</section>
		</section>

		<br/>
		<br/>
		<section class="container feature-container">
			<section class="feature">
				<svg width="24" height="24" 
					viewBox="0 0 24 24" fill="none" 
					stroke="#666" stroke-width="2" 
					stroke-linecap="round" 
					stroke-linejoin="round">
					<path d="M14 9V5a3 3 0 0 0-3-3l-4 
						9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 
						2 0 0 0-2-2.3zM7 22H4a2 2 0 0 
						1-2-2v-7a2 2 0 0 1 2-2h3" />
				</svg>
				<h3>Feature X</h3>
				<p>
					The quick brown fox jumps over lazy dog.
				</p>
			</section>
			<section class="feature">
				<svg width="24" height="24" 
					viewBox="0 0 24 24" fill="none" 
					stroke="#666" stroke-width="2" 
					stroke-linecap="round" 
					stroke-linejoin="round">
					<rect x="2" y="3" width="20" height="14" rx="2" ry="2" />
					<line x1="8" y1="21" x2="16" y2="21" />
					<line x1="12" y1="17" x2="12" y2="21" />
				</svg>
				<h3>Feature X</h3>
				<p>
					The quick brown fox jumps over lazy dog.
				</p>
			</section>
			<section class="feature">
				<svg width="24" height="24" 
					viewBox="0 0 24 24" fill="none" 
					stroke="#666" stroke-width="2" 
					stroke-linecap="round" 
					stroke-linejoin="round">
					<rect x="3" y="4" width="18" 
						height="18" rx="2" ry="2" />
					<line x1="16" y1="2" x2="16" y2="6" />
					<line x1="8" y1="2" x2="8" y2="6" />
					<line x1="3" y1="10" x2="21" y2="10" />
				</svg>
				<h3>Feature X</h3>
				<p>
					The quick brown fox jumps over lazy dog.
				</p>
			</section>
			<section class="feature">
				<svg width="24" height="24" 
					viewBox="0 0 24 24" fill="none" 
					stroke="#666" stroke-width="2" 
					stroke-linecap="round" 
					stroke-linejoin="round">
					<path d="M21 15a2 2 0 0 1-2 2H7l-4 
							4V5a2 2 0 0 1 2-2h14a2 
							2 0 0 1 2 2z" />
				</svg>
				<h3>Feature X</h3>
				<p>
					The quick brown fox jumps over lazy dog.
				</p>
			</section>
			<section class="feature">
				<svg width="24" height="24" 
					viewBox="0 0 24 24" fill="none" 
					stroke="#666" stroke-width="2" 
					stroke-linecap="round" 
					stroke-linejoin="round">
					<path d="M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 
							1-4 1-5-2-8-2-4 1-4 1z" />
					<line x1="4" y1="22" x2="4" y2="15" />
				</svg>
				<h3>Feature X</h3>
				<p>
					The quick brown fox jumps over lazy dog.
				</p>
			</section>
			<section class="feature">
				<svg width="24" height="24" 
					viewBox="0 0 24 24" fill="none" 
					stroke="#666" stroke-width="2" 
					stroke-linecap="round" 
					stroke-linejoin="round">
					<rect x="5" y="2" width="14" height="20" rx="2" ry="2" />
					<line x1="12" y1="18" x2="12.01" y2="18" />
				</svg>
				<h3>Feature X</h3>
				<p>
					The quick brown fox jumps over lazy dog.
				</p>
			</section>
		</section>

		<section class="container">
			<section class="photo-left">
				<img src="" />
				<p>
					The quick brown fox jumps over a lazy dog.
					The quick brown dog jumps over a lazy fox.
					The quick brown fox jumps over a lazy dog.
				</p>
			</section>
			<section class="photo-right">
				<p>					
					The quick brown fox jumps over a lazy dog.
					The quick brown dog jumps over a lazy fox.
					The quick brown fox jumps over a lazy dog.
				</p>
				<img src="" />
			</section>
		</section>
		
		<style>
		main .slogan {
			margin: 1rem 0 0 0;
			text-align: center;
		}
		main p {
			color: #444;
			font-size: 1.25rem;
			line-height: 2rem;
		}

		.duo {
			display: grid;
			grid-template-columns: 1fr;
		}
		@media (min-width: 768px) {
			.duo {
				grid-template-columns: 1fr 1fr;
				column-gap: 2rem;
			}
		}

		.trio {
			display: grid;
			grid-template-columns: 1fr;
		}
		@media (min-width: 768px) {
			.trio {
				grid-template-columns: 1fr 1fr 1fr;
				column-gap: 2rem;
			}
		}

		main .panel {
			background: rgba(0, 0, 0, .05);
			border-radius: .75rem;
			padding: 1.5rem;
			display: grid;
			grid-template-rows: auto 1fr auto;
			margin-bottom: 2rem;
		}
		main .panel h3 {
			margin: 0;
			text-align: center;
		}
		main .panel p {
			margin-bottom: 2rem;
		}
		main .button {
			border-radius: 2rem;
			border: none;
			padding: .5rem 1rem;
		}
		main .button:hover {
			border: none;
		}
		main .feature-container {
			display: grid;
			column-gap: 2rem;
		}
		@media (min-width: 480px) {
			main .feature-container {
				grid-template-columns: 1fr 1fr;
			}
		}
		@media (min-width: 768px) {
			main .feature-container {
				grid-template-columns: 1fr 1fr 1fr;
			}
		}
		@keyframes enlarge {
			  0% { }
			 90% { transform: scale(1.15); }
			100% { transform: scale(1.1); }
		}
		main .feature {
			margin-top: 2rem;
		}
		main .feature svg {
			margin-left: -.25rem;
			margin-bottom: 1rem;
			width: 4rem;
			height: 4rem;
			stroke: var(--brand-color);
		}
		main .feature:hover svg {
			animation: enlarge .2s;
			transform: scale(1.1);
			stroke: #666;
		}
		main .feature h3 {
			color: var(--text-color);
			transition: color .2s linear;
		}
		main .feature:hover h3 {
			color: var(--brand-color);
		}

		main .feature p {
			margin-top: 1rem;
			margin-bottom: 2rem;
			transition: color .2s linear;
		}
		main .feature:hover p {
			margin-top: 1rem;
			margin-bottom: 2rem;
			color: var(--brand-color);
		}

		.statistics {
			background: rgba(0, 0, 0, .5);
			text-align: center;
			color: white;
			padding: 8rem 0 6rem 0;
		}
		.statistics h1 {
			font-size: 3rem;
		}
		.statistics h1:hover {
			animation: enlarge .2s;
			transform: scale(1.1);
		}
		.statistics p {
			margin: 1rem 0 2rem 0;
			color: white;
		}

		.photo-left,
		.photo-right {
			display: grid;
			column-gap: 2rem;
			margin: 2rem 0;
		}
		@media (min-width: 768px) {
			.photo-left {
				grid-template-columns: 1fr 22rem;
			}
			.photo-right {
				grid-template-columns: 22rem 1fr;
			}
		}

		.photo-left img,
		.photo-right img {
			background: var(--header-background);
			border-radius: .75rem;
			border: .15rem solid #ccc;
			width: 100%;
			min-height: 20rem;
			overflow: hidden;
			transition: transform .1s linear;
		}

		.photo-left img:hover,
		.photo-right img:hover {
			transform: scale(1.05);
		}
		@media (min-width:320px) {
			.photo-left img { display: block; }
			.photo-right img { display: block; }
		}

		</style>

	</main>

	<%@include file="/WEB-INF/footer.jsp" %>
</body>
</html>
