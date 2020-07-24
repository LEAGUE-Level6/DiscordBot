package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class LAtimes extends CustomMessageCreateListener {

	private static final String ARRIVALS = "!metrott";
	Gson gson = new Gson();

	public LAtimes(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(ARRIVALS)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!metrott","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("METRO BOT: Please enter a stop ID. The stop ID can be found with the !‎metrohelp command.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("http://api.metro.net/agencies/lametro-rail/stops/" + cmd + "/predictions/");
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						JsonObject root = (JsonObject) parser.parse(in);
						JsonObject eta1 = (JsonObject) root.get("items").getAsJsonArray().get(0);
						JsonObject eta2 = (JsonObject) root.get("items").getAsJsonArray().get(1);
						JsonObject eta3 = (JsonObject) root.get("items").getAsJsonArray().get(2);
						JsonObject eta4 = (JsonObject) root.get("items").getAsJsonArray().get(3);
				        event.getChannel().sendMessage("Next Arrivals for " + cmd + ":");
				        event.getChannel().sendMessage(eta1.get("minutes").getAsInt() + " minutes");
				        event.getChannel().sendMessage(eta2.get("minutes").getAsInt() + " minutes");
				        event.getChannel().sendMessage(eta3.get("minutes").getAsInt() + " minutes");
				        event.getChannel().sendMessage(eta4.get("minutes").getAsInt() + " minutes");
				        
				    } else {
						event.getChannel().sendMessage("We're sorry, we could not access the Metro API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("METRO BOT: Please make sure that stopid is valid. Remember, this only works for trains, not buses.");
					e.printStackTrace();
				}
				
			}
			
		}
	}

}