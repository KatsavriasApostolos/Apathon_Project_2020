<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %> 
<%@ page import= "java.sql.PreparedStatement" %>
<%@ page import="java.io.*" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type = "text/css" href="Style.css">
<title>Card Check</title>
</head>
<body>
<% 
   		//Get the parameter from previous selection
		String card_name =request.getParameter("search");
		//session.setAttribute("card_name", card_name);
		System.out.println(card_name);
%>

<p>
You have searched for <%=  card_name %>
<br>
Establishing connection with the database....
<br>

<% 
 
 	//Check the connection with the database
 	try{
 		String connectionURL = "jdbc:mysql://localhost:3306/Hearthstone";
 		Connection connection = null;
 		Class.forName("com.mysql.jdbc.Driver").newInstance();
 		connection = DriverManager.getConnection(connectionURL, "root", "zabuzademon13gr");
 		out.println("Success");
 		%> <br> Searching database for <%= card_name %>
 		<%
 		System.out.println(card_name);
 	 			Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_store", "root", "zabuzademon13gr");
 	         	Statement statement = connection2.createStatement() ;
 	         	ResultSet resultset1 = statement.executeQuery("SELECT * FROM hearthstone.hearthstone_cards where card_name LIKE '%" +card_name+ "%'");
 		%>
 		<br>Please select one from the following cards:<br>
 		<table class="cards">
 	              <tr>
 	              <th class = "td1">Number</th>
 	              <th class = "td1">Card Name</th>
 	              </tr>
 	              <% 
 	     //Getting the result set from db query
 	         		while(resultset1.next()){ 
 	     			%>
 	     		  <tr>
               	  <td class = "td1"> <%= resultset1.getString(1) %></td>
                  <td class = "td1"> <%= resultset1.getString(2) %></td>
                  </tr>
        
           <%		}	%>	</table> <%
 	             ResultSet resultset2 = statement.executeQuery("SELECT * FROM hearthstone.hearthstone_cards where card_name LIKE '%" +card_name+ "%'");
             
 
     		%> <form class = "form2" method = "get" action = "card">
     		<select name="selected_card"><%while (resultset2.next()){
     			%><option class = "option1"><%= resultset2.getString(2) %></option><%} %>
     		
     		</select>
     		<input class = "button3" type="submit"  name="select" value = "Search This Card">
     		</form>
 		<% 
   		String message=request.getParameter("selected_card");
		session.setAttribute("message", message);
    	  
		%>
 	
 	
 	
 	
 	
 	<%}

 		
 		catch(Exception ex){
 %>
  		<font size="+3" color="red"></b>
 <%
 	out.println("Unable to connect to database.");} 	
%>
</body>
</html>