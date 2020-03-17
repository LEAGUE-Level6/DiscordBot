package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class CTAtimes extends CustomMessageCreateListener {

	private static final String ARRIVALS = "!ctatt";
	private static final String HELP = "!ctahelp";
	private static final String STATUS = "!ctastatus";
	Gson gson = new Gson();

	public CTAtimes(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(ARRIVALS)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!ctatt","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("CTATT BOT: Please enter a stop ID. The stop ID can be found with the !‎ctahelp command.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=e325bc1ce4ad4ce0a3ad0830739c4993&mapid=" + cmd + "&max=6&outputType=JSON");
				    String readLine = null;
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						JsonObject fileData = (JsonObject) parser.parse(in);
						JsonObject root = (JsonObject) fileData.get("ctatt");
						JsonObject eta1 = (JsonObject) root.get("eta").getAsJsonArray().get(0);
						JsonObject eta2 = (JsonObject) root.get("eta").getAsJsonArray().get(1);
						JsonObject eta3 = (JsonObject) root.get("eta").getAsJsonArray().get(2);
						JsonObject eta4 = (JsonObject) root.get("eta").getAsJsonArray().get(3);
				        event.getChannel().sendMessage("Next Arrivals for " + eta1.get("staNm").toString().replace("\"", "") + ":");
				        if(eta1.get("isApp").equals("1")) {
				        	event.getChannel().sendMessage(eta1.get("destNm").toString().replace("\"", "") + " DUE");
				        }
				        else {
				        	event.getChannel().sendMessage(eta1.get("destNm").toString().replace("\"", "") + " at " + eta1.get("arrT").toString().replace("\"", "").split("T")[1]);
				        }
				        if(eta2.get("isApp").equals("1")) {
				        	event.getChannel().sendMessage(eta2.get("destNm").toString().replace("\"", "") + " DUE");
				        }
				        else {
				        	event.getChannel().sendMessage(eta2.get("destNm").toString().replace("\"", "") + " at " + eta2.get("arrT").toString().replace("\"", "").split("T")[1]);
				        }
				        if(eta3.get("isApp").equals("1")) {
				        	event.getChannel().sendMessage(eta3.get("destNm").toString().replace("\"", "") + " DUE");
				        }
				        else {
				        	event.getChannel().sendMessage(eta3.get("destNm").toString().replace("\"", "") + " at " + eta3.get("arrT").toString().replace("\"", "").split("T")[1]);
				        }
				        if(eta4.get("isApp").equals("1")) {
				        	event.getChannel().sendMessage(eta4.get("destNm").toString().replace("\"", "") + " DUE");
				        }
				        else {
				        	event.getChannel().sendMessage(eta4.get("destNm").toString().replace("\"", "") + " at " + eta4.get("arrT").toString().replace("\"", "").split("T")[1]);
				        }
				        
				    } else {
						event.getChannel().sendMessage("We're sorry, we could not access the CTA API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("CTATT BOT: Please make sure that stopid is valid. Remember, this only works for trains, not buses.");
					e.printStackTrace();
				}
				
			}
			
		}
		else if(event.getMessageContent().contains(STATUS)) {
			try {
				URL urlForGetRequest = new URL("http://www.transitchicago.com/api/1.0/routes.aspx?outputType=JSON&type=rail");
			    String readLine = null;
			    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
			    conection.setRequestMethod("GET");
			    int responseCode = conection.getResponseCode();
			    if (responseCode == HttpURLConnection.HTTP_OK) {
			        BufferedReader in = new BufferedReader(
			        new InputStreamReader(conection.getInputStream()));
			        JsonParser parser = new JsonParser();
					JsonObject fileData = (JsonObject) parser.parse(in);
					JsonObject root = (JsonObject) fileData.get("CTARoutes");
					JsonObject red = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(0);
					JsonObject blue = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(1);
					JsonObject brown = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(2);
					JsonObject green = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(3);
					JsonObject orange = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(4);
					JsonObject pink = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(5);
					JsonObject purple = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(6);
					JsonObject pexp = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(7);
					JsonObject yellow = (JsonObject) root.get("RouteInfo").getAsJsonArray().get(8);
					event.getChannel().sendMessage("CTA Status:");
					event.getChannel().sendMessage("Red Line: " + red.get("RouteStatus").toString().replace("\"", ""));
					event.getChannel().sendMessage("Blue Line: " + blue.get("RouteStatus").toString().replace("\"", ""));
					event.getChannel().sendMessage("Brown Line: " + brown.get("RouteStatus").toString().replace("\"", ""));
					event.getChannel().sendMessage("Green Line: " + green.get("RouteStatus").toString().replace("\"", ""));
					event.getChannel().sendMessage("Orange Line: " + orange.get("RouteStatus").toString().replace("\"", ""));
					event.getChannel().sendMessage("Pink Line: " + pink.get("RouteStatus").toString().replace("\"", ""));
					event.getChannel().sendMessage("Purple Line SHUTTLE: " + purple.get("RouteStatus").toString().replace("\"", ""));
					event.getChannel().sendMessage("Purple Line EXPRESS: " + pexp.get("RouteStatus").toString().replace("\"", ""));
					event.getChannel().sendMessage("Yellow Line: " + yellow.get("RouteStatus").toString().replace("\"", ""));
			        
			    } else {
					event.getChannel().sendMessage("We're sorry, we could not access the CTA API servers.");			    
			    }
			}
			catch(Exception e) {
				event.getChannel().sendMessage("CTATT BOT: Please make sure that stopid is valid. Remember, this only works for trains, not buses.");
				e.printStackTrace();
			}
		}
	}

}