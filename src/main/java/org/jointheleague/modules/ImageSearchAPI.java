package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.imageSearchAPI.ImageSearchAPIWrapper;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class ImageSearchAPI extends CustomMessageCreateListener{
	private final String apiKey = "0VjnP-BafmJEesd5BnFCGlN-QYEKhlVBTAEO45Lc1-w";
	private static final String COMMAND = "!imageSearch";
	private final Gson gson = new Gson();
	
	public ImageSearchAPI(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Gets images for a specified topic from unsplash.com. To use this bot enter !imageSearch "
				+ "<topic> <amount>. Amount must be greater than 0 and less than 10.");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
			String msg = event.getMessageContent();
			String topic = "";
			int amount = 0;
			boolean error = false;
			try {
				topic = msg.split(" ")[1];
				amount = Integer.parseInt(msg.split(" ")[2]);
			}catch(Exception e) {
				error = true;
			}
			if(amount > 10 || amount < 1 || error) {
				event.getChannel().sendMessage("Please format the command correctly!");
			}else {
				getImageByTopic(topic, amount, event);
			}
		}
	}
	public void getImageByTopic(String topic, int amount, MessageCreateEvent event) {
		
		//create the request URL (can be found in the documentation)
		String requestURL = "https://api.unsplash.com/search/photos?query=" + topic;
		try {
			
			//the following code will probably be the same for your feature
			URL url = new URL(requestURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Client-ID " + apiKey);
			JsonReader repoReader = Json.createReader(con.getInputStream());
		    JsonObject userJSON = ((JsonObject) repoReader.read());
		    con.disconnect();

			//turn the json response into a java object
			//you will need to create a java class that represents the response in org.jointheleague.modules.pojo
			//you can use a tools like Postman and jsonschema2pojo.com to help with that
		    
		    //you can use postman to make the request and receive a response, then take that and put it right into jsonschema2pojo.com
			//If using jsonschema2pojo.com, select Target Langue = java, Source Type = JSON, Annotation Style = Gson
		    ImageSearchAPIWrapper imageSearchAPIWrapper = gson.fromJson(userJSON.toString(), ImageSearchAPIWrapper.class);

			//get the first article (these are just java objects now)
		    try {
		    	for(int i = 0; i < amount; i++) {
		    		String image = imageSearchAPIWrapper.getResults().get(i).getUrls().getRegular();
		    		event.getChannel().sendMessage(image);
		    	}
		    } catch(Exception e) {
		    	if(imageSearchAPIWrapper.getResults().size() == 0) {
		    		event.getChannel().sendMessage("No image results found for the topic: " + topic);
		    	}else {
		    		event.getChannel().sendMessage("There are only " + imageSearchAPIWrapper.getResults().size() + 
		    				"results for the topic: " + topic);
		    	}
		    }

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
