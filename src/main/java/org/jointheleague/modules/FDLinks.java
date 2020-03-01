package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class FDLinks extends CustomMessageCreateListener {

	private static final String GEN = "!dynamiclink";
	private static final String HELP = "!fdlhelp";
	String suffix = "SHORT";
	Gson gson = new Gson();

	public FDLinks(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(GEN)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!dynamiclink","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("Please make sure you have a long link in your response. For help run !fdlhelp.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=AIzaSyAoLo-pqdRhfJWincNwGR5xIIvsnqS5jg0");
				    String readLine = null;
				    HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
				    connection.setRequestMethod("POST");
				    connection.setRequestProperty("Content-Type", "application/json; utf-8");
				    connection.setRequestProperty("Accept", "application/json");
				    connection.setDoOutput(true);
				    try(OutputStream os = connection.getOutputStream()) {
				    	String jsonInputString = "{\n" + 
				    			"   \"longDynamicLink\": \"https://jointheleague.page.link/?link=" + cmd + "\",\n" + 
				    			"   \"suffix\": {\n" + 
				    			"     \"option\": \"SHORT\"\n" + 
				    			"   }\n" + 
				    			"}\n" + 
				    			"";
				        byte[] input = jsonInputString.getBytes("utf-8");
				        os.write(input, 0, input.length);           
				    }
				    int responseCode = connection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(connection.getInputStream()));
				        JsonParser parser = new JsonParser();
						JsonObject fileData = (JsonObject) parser.parse(in);
				        event.getChannel().sendMessage("Short Link:" + fileData.get("shortLink").toString().replace("\"", ""));
				    } else {
						event.getChannel().sendMessage("We're sorry, we could not access the FDL API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("We're sorry, a code error occured. \n Here's the full info: \n " + e.getStackTrace().toString());
					e.printStackTrace();
				}
			}
			
		}
		else if(event.getMessageContent().equals(HELP)) {
			event.getChannel().sendMessage("The SBot's Dynamic Links feature lets you shorten links. Usage is !‎dynamiclink [url]. Currently no option is available to change the suffix or domain.");
		}
	}

}