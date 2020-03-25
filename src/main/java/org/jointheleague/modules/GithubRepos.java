package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class GithubRepos extends CustomMessageCreateListener {

	private static final String ARRIVALS = "!github";
	Gson gson = new Gson();

	public GithubRepos(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(ARRIVALS)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!github","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("GITHUB BOT: Please enter a Github username.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("https://api.github.com/users/" + cmd + "/repos");
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						JsonArray root = parser.parse(in).getAsJsonArray();
				        event.getChannel().sendMessage("Repos for " + cmd + ":");
				        for(int i = 0; i<root.size(); i++) {
				        	event.getChannel().sendMessage(root.get(i).getAsJsonObject().get("full_name").toString().replace("\"", ""));
				        }
				    } else {
						event.getChannel().sendMessage("We're sorry, we could not access the Github API servers. Perhaps you're being rate-limited?");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("GITHUB BOT: Please make sure that username is valid. Remember, this only works for users, not orgs.");
					e.printStackTrace();
				}
				
			}
			
		}
	}

}