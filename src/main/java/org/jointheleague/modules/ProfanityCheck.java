package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class ProfanityCheck extends CustomMessageCreateListener {

	Gson gson = new Gson();

	public ProfanityCheck(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {

				try {
					URL urlForGetRequest = new URL("https://raw.githubusercontent.com/zacanger/profane-words/master/words.json");
				    String readLine = null;
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						JsonObject fileData = (JsonObject) parser.parse(in);
						
				    } else {
						event.getChannel().sendMessage("We're sorry, we could not access the CTA API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("We're sorry, a code error occured. \n Here's the full info: \n " + e.getStackTrace().toString());
					e.printStackTrace();
				}
			}
			
		}
	