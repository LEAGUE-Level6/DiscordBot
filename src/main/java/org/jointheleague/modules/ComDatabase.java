package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class ComDatabase extends CustomMessageCreateListener {

	private static final String GET = "!getdata";
	private static final String POST = "!adddata";
	private static final String ALL = "!getalldata";
	Gson gson = new Gson();

	public ComDatabase(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(GET)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!getdata","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("COMMUNITY DATABASE: Please enter a key to request.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("https://leaguelinks-9cafa.firebaseio.com/communitydatabase.json");
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						event.getChannel().sendMessage("Data:");
						JsonObject data = (JsonObject) parser.parse(in);
						event.getChannel().sendMessage(data.get(cmd).toString());
				        
				    } else {
						event.getChannel().sendMessage("COMMUNITY DATABASE: We're sorry, we could not access the API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("Nothing, because that key isn't valid. Next time, please make sure that key is valid.");
					e.printStackTrace();
				}
				
			}
			
		}
		if (event.getMessageContent().contains(POST)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!adddata","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("COMMUNITY DATABASE: Please enter a key to request.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("https://leaguelinks-9cafa.firebaseio.com/communitydatabase.json");
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("POST");
				    conection.setRequestProperty("Content-Type", "application/json; utf-8");
				    conection.setDoOutput(true);
				    String responsebody = "\"" + cmd + "\"";
				    try(OutputStream os = conection.getOutputStream()){
				    	byte[] encodedbody = responsebody.getBytes("utf-8");
				    	os.write(encodedbody, 0, encodedbody.length);
				    }
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						event.getChannel().sendMessage("The key to access your data is:");
						JsonObject data = (JsonObject) parser.parse(in);
						event.getChannel().sendMessage(data.get("name").toString().replace("\"", ""));
				        
				    } else {
						event.getChannel().sendMessage("COMMUNITY DATABASE: We're sorry, we could not access the API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("Nothing, because that key isn't valid. Next time, please make sure that key is valid.");
					e.printStackTrace();
				}
				
			}
			
		}
		if (event.getMessageContent().contains(ALL)) {
			
				try {
					URL urlForGetRequest = new URL("https://leaguelinks-9cafa.firebaseio.com/communitydatabase.json");
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						event.getChannel().sendMessage("All Data:");
						event.getChannel().sendMessage(parser.parse(in).toString());
				        
				    } else {
						event.getChannel().sendMessage("COMMUNITY DATABASE: We're sorry, we could not access the API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("Nothing, because that key isn't valid. Next time, please make sure that key is valid.");
					e.printStackTrace();
				}
				
			}
			
		}

}