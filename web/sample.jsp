<%@page pageEncoding="UTF8" %><!DOCTYPE html>
<html>
	<head>
		
	</head>
	<body>

		<%
			out.print("Sample JSP");
		%>
		
		
		<script>
			
			var socket = new WebSocket("ws://localhost:12345/ws")
			
			socket.onopen = function(event) {
				console.log(event)
				socket.send("Message 1")
			}

			socket.onmessage = function(event) {
				console.log(event)
			}

			socket.onerror = function(event) {

			}

			socket.onclose = function(event) {

			}
			
		</script>

	</body>
</html>