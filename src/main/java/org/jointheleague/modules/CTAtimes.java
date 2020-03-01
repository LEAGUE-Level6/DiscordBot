package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class CTAtimes extends CustomMessageCreateListener {

	private static final String ARRIVALS = "!ctatt";
	private static final String TRACK = "!ctattfollow";
	private static final String HELP = "!ctahelp";
	private static final String STATUS = "!ctastatus";
	private static final String DETINFO = "!ctainfo";
	Gson gson = new Gson();

	public CTAtimes(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(ARRIVALS)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!ctatt","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("CTATT BOT \n ERROR: Please enter a valid stop ID. Use !ctatt [stopid]. The stop ID can be found with the !ctahelp command.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=e325bc1ce4ad4ce0a3ad0830739c4993&mapid=" + cmd + "&max=5&outputType=JSON");
				    String readLine = null;
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        JsonParser parser = new JsonParser();
						JsonElement fileData = parser.parse(in);
				        event.getChannel().sendMessage("Next Arrivals:" + fileData);
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
	}

}