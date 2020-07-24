package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class Analogizer extends CustomMessageCreateListener {

	private static final String TS_CMD = "!analogy";
	Gson gson = new Gson();

	public Analogizer(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(TS_CMD)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!analogy","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("ANALOGIZER BOT: Please enter a unit to convert (e.g 2 litres). Remember to use the British spelling of litres.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("https://www.tomscott.com/analogizer/api/?q=" + cmd);
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						JsonObject root = (JsonObject) parser.parse(in);
				        event.getChannel().sendMessage("Analogy for " + cmd + ":");
				        event.getChannel().sendMessage(root.get("text").getAsString().replace("\"", ""));
				        event.getChannel().sendMessage("Source: " + root.get("source").getAsString().replace("\"", ""));
				        
				        
				    } else {
						event.getChannel().sendMessage("We're sorry, we could not access the Metro API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("ANALOGIZER BOT: Please make sure that unit is valid.");
					e.printStackTrace();
				}
				
			}
			
		}
	}

}