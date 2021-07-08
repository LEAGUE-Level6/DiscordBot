package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.XKCD;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;

import com.google.gson.Gson;

public class GetXkcd extends CustomMessageCreateListener{
	public GetXkcd(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Get the xkcd of a given number. 0 will give a random xkcd. An number for which there is not yet an xkcd will give you the latest xkcd.");

	}

			private static final String COMMAND = "!getxkcd";
			private final Gson gson = new Gson();
			
			int latestcomic=2473;
			//HEY PERSON IN THE FUTURE
			//Yes you! Please update latestcomic to whatecer the number of the latest xkcd is.
			//Go to xkcd.com, go back one, go forward one, and look at the number in the url
			//Thanks!
			

			@Override
			public void handle(MessageCreateEvent event) {
				if(event.getMessageContent().contains(COMMAND)) {

					//remove the command so we are only left with the search term
					String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

					if (msg.equals("")) {
						event.getChannel().sendMessage("Please put a number after the command");
					} else {
						getComic(Integer.parseInt(msg), event);

					}
					
				}
			}
			
			public void getComic(int comic, MessageCreateEvent event) {
				
				//create the request URL (can be found in the documentation)
				if(comic==0) {
					Random r=new Random();
					comic=r.nextInt(latestcomic)+1;
				}
				
				
				String requestURL = "https://xkcd.com/"+comic+"/info.0.json";
				
				 if(comic>latestcomic) {
						requestURL = "https://xkcd.com/info.0.json";
				 }
					
					//the following code will probably be the same for your feature
					URL url;
					try {
						url = new URL(requestURL);
					
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					JsonReader repoReader = Json.createReader(con.getInputStream());
				    JsonObject userJSON = ((JsonObject) repoReader.read());
				    con.disconnect();

					//turn the json response into a java object
					//you will need to create a java class that represents the response in org.jointheleague.modules.pojo
					//you can use a tools like Postman and jsonschema2pojo.com to help with that
				    
				    //you can use postman to make the request and receive a response, then take that and put it right into jsonschema2pojo.com
					//If using jsonschema2pojo.com, select Target Langue = java, Source Type = JSON, Annotation Style = Gson


					//get the first article (these are just java objects now)
					XKCD strip = gson.fromJson(userJSON.toString(), XKCD.class);

					
					//get the title of the article 
					String title = strip.getTitle();
					
					//get the content of the article 
					String image = strip.getImage();
					String date =strip.getDate();
					
					//create the message
					String alt=strip.getAlt();
					if(comic<=latestcomic) {
					event.getChannel().sendMessage("xkcd comic "+comic+": "+title);
					}else {
					event.getChannel().sendMessage("xkcd comic "+latestcomic+": "+title);
					}
					if(date.contentEquals("1179")) {
					event.getChannel().sendMessage("From: "+date+" (see below)");
					}else {
					event.getChannel().sendMessage("From: "+date+" (see #1179)");
					}
					event.getChannel().sendMessage(image);
					event.getChannel().sendMessage("Mouseover text: "+alt);
					

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			
				
			}
			
	
}
