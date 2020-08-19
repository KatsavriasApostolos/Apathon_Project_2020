<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*" %>
<%@ page import="org.json.*" %> 
<%@ page import="com.mashape.unirest.http.*" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type = "text/css" href="Style.css">
<title>Checking Api</title>
</head>
<body>

<p> Checking API Connection </p>
<%
String host = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/search/Ysera";
String x_rapidapi_host = "omgvamp-hearthstone-v1.p.rapidapi.com";
String x_rapidapi_key = "edab6e5270mshde83f53d1213546p1a407cjsn26bf425fab76";
try {
	HttpResponse <JsonNode> response2 = Unirest.get(host)


  	.header("x-rapidapi-host", x_rapidapi_host)
	.header("x-rapidapi-key", x_rapidapi_key)
	.asJson();
	JSONObject response3 = new JSONObject(response2);
	Number x = response3.getNumber("status");
	if (x.equals(200)) { %>
	<p> <%
  	  out.println("Succesfully Connected with API");
    } else{%><p><%
    	out.println("Failed to Connect with API");
    }

}

catch (Exception ex) {
	%><p><%
	out.println("Something went Wrong");
	
}

%>
<ul><li><form class = "f1" method ="get" action ="index.html">
<input class = 'button2' type = "submit" value ="Back to Home" name = "submit"/></form></li></ul>

</body>
</html>