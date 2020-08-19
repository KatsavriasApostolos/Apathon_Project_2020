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

public class cardsearch extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2300886301597400073L;

	//This method request info from API where selected_card - variabe
	//and returns a JSON Object
	public static JSONObject api_search(String selected_card) throws Exception{
		
		//Generating Parameters
		String host = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/search/"+ selected_card +"?collectible=1";
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
	
	//This is the method where we get the results from the database
	public static ResultSet database_search (String selected_card) throws Exception{
		
 		String connectionURL = "jdbc:mysql://localhost:3306/Hearthstone";
 		Connection connection = null;
 		Class.forName("com.mysql.jdbc.Driver").newInstance();
 		connection = DriverManager.getConnection(connectionURL, "root", "zabuzademon13gr");
 		
         	Statement statement = connection.createStatement() ;
         	ResultSet resultset1 = statement.executeQuery("SELECT * FROM hearthstone.hearthstone_cards where card_name LIKE '%" +selected_card+ "%'");
         	return resultset1;
	
}
		
	//This is the method for posting results
	public void service(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException {
		String card = req.getParameter("selected_card");
		// Creation of the HTML Page
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
  		// use try block just in case
  		try {
			api_search_result = api_search(card);
			JSONObject obj = new JSONObject(api_search_result.get("body").toString());
			JSONArray array = obj.getJSONArray("array");
			for (int i=0; i < array.length();i++){
				if (array.getJSONObject(i).get("type").equals("Minion")) {
					out.println("<TABLE CLASS = 'cards'>" + "<TR>" +"<TH>" +  array.getJSONObject(i).get("name")+"<BR>");
					out.println("<IMG width = '150' height='200' src='"+array.getJSONObject(i).get("img")+"' >"); 
					out.println( "</TH>"+"</TR>");
					out.println("<TR>" + "<TD>" + "<UL>");
					out.println("<U>Card Attributes:</U>");
					out.println(" <LI> Mana cost: " + array.getJSONObject(i).get("cost"));
					out.println(" <LI> Attack: " + array.getJSONObject(i).get("attack"));
					out.println(" <LI> Health:" + array.getJSONObject(i).get("health"));
					out.println(" <LI> Type:" + array.getJSONObject(i).get("type"));
					out.println("<TR>" + "<TD>" + "</UL>");
					out.println("<TR>" + "<TD>" + "<UL>");
					out.println("<U>Card Info:</U>");
					out.println(" <LI> Artist: " + array.getJSONObject(i).get("artist"));
					out.println(" <LI> Flavor: " + array.getJSONObject(i).get("flavor"));
					out.println(" <LI> Class: " + array.getJSONObject(i).get("playerClass"));
					out.println(" <LI> CardSet: " + array.getJSONObject(i).get("cardSet"));
					out.println(" <LI> Text: " + array.getJSONObject(i).get("text"));
					out.println(" <LI> Rarity: " + array.getJSONObject(i).get("rarity"));
					out.println("</UL>" + "</TD>" + "</TR>");
					out.println("<TR>" + "<TD>" + "<UL>");
					out.println("<U>Card Popularity:</U>");
					db_search_result = database_search(array.getJSONObject(i).get("name").toString());
					while (db_search_result.next()) {
						out.println(" <LI> In number of decks: " + db_search_result.getString(3));
						out.println(" <LI> Avg.Copies " + db_search_result.getString(4));
						out.println(" <LI> Deck Winrate: " + db_search_result.getString(5));
						out.println(" <LI> Played WInrate: " + db_search_result.getString(6));
					}
					
					out.println("</UL>" + "</TD>" + "</TR>");
											
				    	 
				    out.println("</TABLE>");
					
				}else {
					out.println("<H1 ALIGN = CENTER> Something went Wrong </h1>");
				}
				//return to top or to card search
			
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



