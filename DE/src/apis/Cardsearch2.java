package apis;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * Servlet implementation class Cardsearch2
 */

public class Cardsearch2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
  public static ResultSet database_search (String selected_card) throws Exception{
		
 		String connectionURL = "jdbc:mysql://localhost:3306/Hearthstone";
 		Connection connection = null;
 		Class.forName("com.mysql.jdbc.Driver");
 		connection = DriverManager.getConnection(connectionURL, "root", "zabuzademon13gr");
 		
         	Statement statement = connection.createStatement() ;
         	ResultSet resultset1 = statement.executeQuery("SELECT * FROM hearthstone.hearthstone_cards where card_name LIKE '%" +selected_card+ "%'");
         	return resultset1;
	
}
	
	public static JSONObject api_search(String selected_card) throws Exception{
		
		//Generating Parameters
		String host = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/factions/"+ selected_card +"";
	    //String charset = "UTF-8";
	    // Headers for a request
	    String x_rapidapi_host = "omgvamp-hearthstone-v1.p.rapidapi.com";
	    String x_rapidapi_key = "edab6e5270mshde83f53d1213546p1a407cjsn26bf425fab76";
	           
	    // This is the response from the api search  
	    HttpResponse <JsonNode> response = Unirest.get(host)
	      
	      		  .header("x-rapidapi-host", x_rapidapi_host)
	    	      .header("x-rapidapi-key", x_rapidapi_key)
	    	      .asJson();
	    
	    // Make the response a new JSON Object
	    JSONObject response2 = new JSONObject(response);
	    return response2;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				
		String faction = req.getParameter("search");
		PrintWriter out =  res.getWriter();
		res.setContentType("text/html");
		String title = "Card Search results";
		String docType = "<!DOCTYPE HTML PUBLIC\"-//W3C//DTD HTML 4.0 "+
					"transitional//EN\">\n";
		out.println(docType + 
  				"<HTML>\n"+
  				"<HEAD>" + "<LINK REL=\"stylesheet\" TYPE = \"text/css\" HREF=\"Style.css\">"
  				+ "<META CHARSET = \"UTF-8\"> "
  				
  				+ "<TITLE>"+ title + "</TITLE></HEAD>\n"+
  				"<H1 ALIGN = CENTER>" + title + "</H1>\n");
		//Parameters
		JSONObject api_search_result;
		ResultSet db_search_result;
		try {
			api_search_result = api_search(faction);
			JSONObject obj = new JSONObject(api_search_result.get("body").toString());
			JSONArray array = obj.getJSONArray("array");
			for (int i=0; i < array.length();i++){
				
					out.println("<TABLE CLASS = 'cards'>" + "<TR>" +"<TH>" +  array.getJSONObject(i).get("name")+"<BR>");
					try {
						out.println("<IMG width = '150' height='200' src='"+array.getJSONObject(i).get("img")+"' >");
					}catch (Exception e) {
						// TODO Auto-generated catch block
						out.println("No image available");
					}
					//out.println("<IMG width = '150' height='200' src='"+array.getJSONObject(i).get("img")+"' >"); 
					out.println( "</TH>"+"</TR>");
					out.println("<TR>" + "<TD>" + "<UL>");
					out.println("<U>Card Popularity:</U>");
					db_search_result = database_search(array.getJSONObject(i).get("name").toString());
					try {
						while (db_search_result.next()) {
							out.println(" <LI> In number of decks: " + db_search_result.getString(3));
							out.println(" <LI> Avg.Copies " + db_search_result.getString(4));
							out.println(" <LI> Deck Winrate: " + db_search_result.getString(5));
							out.println(" <LI> Played WInrate: " + db_search_result.getString(6));
						}
						}catch (Exception e) {
							// TODO Auto-generated catch block
							out.println("No data from db available available");
					}
					
					
					out.println("</UL>" + "</TD>" + "</TR>");
												
				    	 
				    out.println("</TABLE><BR><BR>");
					
				
				}
				
			
		     
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		out.println("<UL> <LI> <FORM CLASS = 'f1' METHOD = 'GET' ACTION = 'search.html'> ");
		out.println("<INPUT CLASS  = 'BUTTON2' TYPE = 'SUBMIT' VALUE = 'SEARCH AGAIN' NAME = 'SUBMIT'></FORM>");
		out.println(" <LI> <FORM CLASS = 'f1' METHOD = 'GET' ACTION = 'index.html'>");
		out.println("<INPUT CLASS  = 'BUTTON2' TYPE = 'SUBMIT' VALUE = 'BACK TO TOP' NAME = 'SUBMIT'></FORM></LI>");
	}
		
		
		
	}

	


