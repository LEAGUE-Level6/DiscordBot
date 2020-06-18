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
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;

import com.google.gson.Gson;

//this example uses the "News API"
//documentation for the API can be found here: https://newsapi.org/docs/get-started
public class _ApiExampleListener extends CustomMessageCreateListener{

		//API key is received through creating an account on the web site.
		//API may not require a key, or may require the key as a header as in PictureOf.java
		private final String apiKey = "59ac01326c584ac0a069a29798794bec";
		private static final String COMMAND = "!apiExample";
		private final Gson gson = new Gson();
	  
		public _ApiExampleListener(String channelName) {
			super(channelName);
			helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service");

		}

		@Override
		public void handle(MessageCreateEvent event) {
			if(event.getMessageContent().contains(COMMAND)) {

				//remove the command so we are only left with the search term
				String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

				if (msg.equals("")) {
					event.getChannel().sendMessage("Please put a word after the command");
				} else {
					String definition = getNewsStoryByTopic(msg);
					event.getChannel().sendMessage(definition);
				}
				
			}
		}
		
		public String getNewsStoryByTopic(String topic) {
			
			//create the request URL (can be found in the documentation)
			String requestURL = "http://newsapi.org/v2/everything?" +
			          "q="+topic+"&" +
			          "sortBy=popularity&" +
			          "apiKey="+apiKey;
			 
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
			    
			    //you can use postman to make the request and receive a response, then take that and put it right into jsonschema2pojo.com
				//If using jsonschema2pojo.com, select Target Langue = java, Source Type = JSON, Annotation Style = Gson
			    ApiExampleWrapper apiExampleWrapper = gson.fromJson(userJSON.toString(), ApiExampleWrapper.class);

				//get the first article (these are just java objects now)
				Article article = apiExampleWrapper.getArticles().get(0);
				
				//get the title of the article 
				String articleTitle = article.getTitle();
				
				//get the content of the article 
				String articleContent = article.getContent();
				
				//create the message
				String message = articleTitle + " - " + articleContent;
				
				//send the message 
				return message;
				

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "No news story found for the keyword: " + topic;
		}
		
		
	}

