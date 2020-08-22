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
<title>All Cards in DB</title>
</head>
<body>
<p>
<br>
Establishing connection with the database....
<br>
<%
try{
 		String connectionURL = "jdbc:mysql://localhost:3306/Hearthstone";
 		Connection connection = null;
 		Class.forName("com.mysql.jdbc.Driver");
 		connection = DriverManager.getConnection(connectionURL, "root", "zabuzademon13gr");
 		out.println("Success");
 		 		
 	 	Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/sql_store", "root", "zabuzademon13gr");
 	    Statement statement = connection2.createStatement() ;
 	    ResultSet resultset1 = statement.executeQuery("SELECT * FROM hearthstone.hearthstone_cards ");
 		%>
 		
 		<table class="cards">
 	              <tr>
 	              <th class = "td1">Number</th>
 	              <th class = "td1">Card Name</th>
 	              <th class = "td1">In number of decks</th>
 	              <th class = "td1">Avg Copies</th>
 	              <th class = "td1">Deck Winrate</th>
 	              <th class = "td1">Times Played</th>
 	              <th class = "td1">Played Winrate</th>
 	              </tr>
 	              <% 
 	     //Getting the result set from db query
 	         		while(resultset1.next()){ 
 	     			%>
 	     		  <tr>
               	  <td class = "td1"> <%= resultset1.getString(1) %></td>
                  <td class = "td1"> <%= resultset1.getString(2) %></td>
                  <td class = "td1"> <%= resultset1.getString(3) %></td>
                  <td class = "td1"> <%= resultset1.getString(4) %></td>
                  <td class = "td1"> <%= resultset1.getString(5) %></td>
                  <td class = "td1"> <%= resultset1.getString(6) %></td>
                  <td class = "td1"> <%= resultset1.getString(7) %></td>
                  
                  </tr>
        
           <%		}	%>	</table> <%
 	             ResultSet resultset2 = statement.executeQuery("SELECT * FROM hearthstone.hearthstone_cards ");
             
 
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