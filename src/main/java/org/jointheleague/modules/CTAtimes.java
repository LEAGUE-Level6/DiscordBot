package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class CTAtimes extends CustomMessageCreateListener {

	private static final String ARRIVALS = "!ctatt";
	private static final String TRACK = "!ctattfollow";
	private static final String HELP = "!ctahelp";
	private static final String STATUS = "!ctastatus";
	private static final String DETINFO = "!ctainfo";

	public CTAtimes(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(ARRIVALS)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!ctatt","");
			
			if(cmd.equals("")) {
				
				Random r = new Random();
				event.getChannel().sendMessage("CTATT BOT \n ERROR: Please enter a valid stop ID. Use !ctatt [stopid]. The stop ID can be found with the !ctahelp command.");
				
				
			} else {
				try {
					URL urlForGetRequest = new URL("http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=e325bc1ce4ad4ce0a3ad0830739c4993&mapid=" + cmd + "&max=1&outputType=JSON");
				    String readLine = null;
				    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
				    conection.setRequestMethod("GET");
				    int responseCode = conection.getResponseCode();
				    if (responseCode == HttpURLConnection.HTTP_OK) {
				        BufferedReader in = new BufferedReader(
				        new InputStreamReader(conection.getInputStream()));
				        StringBuffer response = new StringBuffer();
				        while ((readLine = in .readLine()) != null) {
				            response.append(readLine);
				        } in .close();
				        System.out.println("JSON String Result " + response.toString());
				    } else {
						event.getChannel().sendMessage("We're sorry, we could not access the CTA API servers.");			    
				    }
				}
				catch(Exception e) {
					event.getChannel().sendMessage("We're sorry, a code error occured.");
				}
			}
			
		}
	}

}