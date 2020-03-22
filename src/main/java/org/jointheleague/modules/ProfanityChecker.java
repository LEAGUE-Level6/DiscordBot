package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class ProfanityChecker extends CustomMessageCreateListener {
	Gson gson = new Gson();

	public ProfanityChecker(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
			try {
					URL urlForGetRequest = new URL(
							"https://github.com/ChaseFlorell/jQuery.ProfanityFilter/raw/master/swearWords.json");
					String readLine = null;
					HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
					conection.setRequestMethod("GET");
					int responseCode = conection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
						JsonParser parser = new JsonParser();
						JsonArray data = parser.parse(in).getAsJsonArray();
						for(int i = 0; i<data.size(); i++) {
							if(event.getMessageContent().contains(data.get(i).getAsString())) {
								event.getMessage().edit("[PROFANITY]");
								event.getChannel().sendMessage("Hey " + event.getMessageAuthor().getDisplayName() + "! Don't send profane messages! You and your filthy " + event.getMessageAuthor().getDiscriminatedName().split("#")[1] + " Discord Tag are ruining this server!");
							}
						}
					} else {
						System.out.println("Profanity Check Passed!");
					}
			} catch (Exception e) {
				event.getChannel().sendMessage("We're sorry, a code error occured. \n Here's the full info: \n "
						+ e.getStackTrace().toString());
				e.printStackTrace();
			}
		}
	}