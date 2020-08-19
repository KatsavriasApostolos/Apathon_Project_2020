<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type = "text/css" href="Style.css">
<title>Checking Database Connection</title>
</head>

<body>
<p> Checking database Connection</p>
<% 
try {
// Connection with database
String connectionURL = "jdbc:mysql://localhost:3306/collection"; 

// Connection interface 
Connection connection = null; 

// Load JBBC driver "com.mysql.jdbc.Driver"
Class.forName("com.mysql.jdbc.Driver").newInstance(); 

/* Create a connection by using getConnection() method */ 
connection = DriverManager.getConnection(connectionURL, "root", "zabuzademon13gr");

// check weather connection is established or not by isClosed() method 
if(!connection.isClosed())

%><p>
<%  
out.println("Successfully connected to Database.");

connection.close();
}
catch(Exception ex){

out.println("Unable to connect to database.");
}
%>

<ul><li><form class = "f1" method ="get" action ="index.html">
<input class = 'button2' type = "submit" value ="Back to Home" name = "submit"/></form></li></ul>



</body>
</html>