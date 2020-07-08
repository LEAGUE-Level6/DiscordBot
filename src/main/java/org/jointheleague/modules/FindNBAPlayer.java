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
import org.jointheleague.modules.pojo.NBA.NBARequest;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class FindNBAPlayer extends CustomMessageCreateListener {

	//API key is received through creating an account on the web site.
			//API may not require a key, or may require the key as a header as in PictureOf.java
			private static final String COMMAND = "!NBAPlayer";
			private final Gson gson = new Gson();
		  
			public FindNBAPlayer(String channelName) {
				super(channelName);
				helpEmbed = new HelpEmbed(COMMAND, "Gets the team of selected NBA player (e.g. Kyrie Irving: Brooklyn Nets");

			}

			@Override
			public void handle(MessageCreateEvent event) {
				if(event.getMessageContent().contains(COMMAND)) {

					//remove the command so we are only left with the search term
					String msg = event.getMessageContent();

					if (msg.equals(COMMAND)) {
						event.getChannel().sendMessage("Please put a word after the command");
					} else {
						msg = msg.substring(msg.indexOf(' ')+1).replaceAll(" ", "%20");
						String definition = getTeam(msg);
						event.getChannel().sendMessage(definition);
					}
					
				}
			}
			
			public String getTeam(String name) {
				
				//create the request URL (can be found in the documentation)
				String requestURL = "https://www.balldontlie.io/api/v1/players?search=" + name;
				System.out.println(requestURL);
				try {
					
					//the following code will probably be the same for your feature
					URL url = new URL(requestURL);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					JsonReader repoReader = Json.createReader(con.getInputStream());
				    JsonObject userJSON = ((JsonObject) repoReader.read());
				    con.disconnect();

					//turn the json response into a java object
					//you will need to create a java class that represents the response in org.jointheleague.modules.pojo
					//you can use a tools like Postman and jsonschema2pojo.com to help with that
				   System.out.println(userJSON.toString()); 
				    //you can use postman to make the request and receive a response, then take that and put it right into jsonschema2pojo.com
					//If using jsonschema2pojo.com, select Target Langue = java, Source Type = JSON, Annotation Style = Gson
				    NBARequest request = gson.fromJson(userJSON.toString(), NBARequest.class);
				    
				    String teamName = request.getData().get(0).getTeam().getFullName();
				    name = name.replaceAll("%20", " ");
					//create the message
					String message = name + " is on " + teamName;
					
					//send the message 
					return message;
					

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return "No info for " + name;
			}
			
			

}
