package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class AnyAPI extends CustomMessageCreateListener {

	private static final String ANYAPI = "!api";
	Gson gson = new Gson();

	public AnyAPI(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(ANYAPI)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!api","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("ANYAPI BOT: Please enter a URL to request to.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL(cmd);
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    event.getChannel().sendMessage("ANYAPI BOT: Request made. Response code: " + responseCode);
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						event.getChannel().sendMessage("API Data:");
						event.getChannel().sendMessage(parser.parse(in).toString());
				        
				    } else {
						event.getChannel().sendMessage("ANYAPI BOT: We're sorry, we could not access the API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("ANYAPI BOT: Please make sure that URL is valid. Are you sure the URL is a valid RESTful GET endpoint that returns in JSON and not in XML or GraphQL?");
					e.printStackTrace();
				}
				
			}
			
		}
	}

}