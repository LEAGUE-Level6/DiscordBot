package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class TfLtimes extends CustomMessageCreateListener {

	private static final String ARRIVALS = "!tfl";
	Gson gson = new Gson();

	public TfLtimes(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(ARRIVALS)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!tfl","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("TfL BOT: Please enter a stop ID.");
				
				
			} else {
				try {
					event.getApi().updateUsername("Geoff");
					URL urlForGetRequest = new URL("http://api.tfl.gov.uk/StopPoint/" + cmd + "/arrivals");
				    System.out.println(urlForGetRequest);
					HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    event.getChannel().sendMessage("TfL Servers Returned Response Code: " + responseCode);
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						JsonObject eta = (JsonObject) parser.parse(in).getAsJsonArray().get(0);
				        event.getChannel().sendMessage("Next Arrival for " + eta.get("stationName").toString() + ":");
				        event.getChannel().sendMessage(eta.get("lineName").toString() + " for " + eta.get("destinationName").toString() + " on " + eta.get("platformName").toString() + " at " + eta.get("expectedArrival").toString());
				        event.getChannel().sendMessage("Current vehicle location: " + eta.get("currentLocation").getAsString());
				        
				    } else {
						event.getChannel().sendMessage("We're sorry, we could not access the TfL API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("TfL BOT: Please make sure that stopid is valid. Remember, this only works for trains, not buses.");
					e.printStackTrace();
				}
			    event.getApi().updateUsername("SBot By Samuel");
			}
			
		}
	}

}