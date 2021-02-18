package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.Recipes.AnalyzedInstruction;
import org.jointheleague.modules.pojo.Recipes.Recipe;
import org.jointheleague.modules.pojo.Recipes.Recipe2;
import org.jointheleague.modules.pojo.Recipes.Result;
import org.jointheleague.modules.pojo.Recipes.Step;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;

import com.google.gson.Gson;

public class RecipeGetter extends CustomMessageCreateListener{
	private final String apiKey = "d5586ac37ada4f5fa9659784ce91e9a6";
	private static final String COMMAND = "!recipe";
	private final Gson gson = new Gson();
  
	public RecipeGetter(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service");

	}
	
	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().contains(COMMAND)) {
			System.out.println("hi");

			//remove the command so we are only left with the search term
			String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

			if (msg.equals("")) {
				event.getChannel().sendMessage("Please put a word after the command");
			} else {
				String definition = getRecipe(msg);
				event.getChannel().sendMessage(definition);
			}
			
		}
	}

	public String getRecipe(String topic) {
			
			//create the request URL (can be found in the documentation)
			String requestURL = "https://api.spoonacular.com/recipes/complexSearch/?apiKey=" +
			          apiKey+"&query=" +
			          topic;
			 
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
			    Recipe r = gson.fromJson(userJSON.toString(), Recipe.class);

				//get the first article (these are just java objects now)
				//Article article = apiExampleWrapper.getArticles().get(0);
				
				//get the title of the article 
				//String articleTitle = article.getTitle();
				
				//get the content of the article 
				//String articleContent = article.getContent();
				
				//create the message
				int id = r.getResults().get(0).getId();
				String requestURL2 = "https://api.spoonacular.com/recipes/"+ id + "/information?apiKey=" +
				          apiKey;
				 url = new URL(requestURL2);
				 con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				 repoReader = Json.createReader(con.getInputStream());
			     userJSON = ((JsonObject) repoReader.read());
			    con.disconnect();
			    
			    Recipe2 instructions = gson.fromJson(userJSON.toString(), Recipe2.class);

				List<Step> steps = instructions.getAnalyzedInstructions().get(0).getSteps();
				String message = "";
				int stepCount = 1;
				for (int i = 0; i < steps.size(); i++) {
					message+=steps.get(i).getStep();
				}
				//send the message 
				String message2 = "";
				for (int i = 0; i < message.length(); i++) {
					if(i==0) {
						message2+=stepCount + ". ";
					}
					message2+=message.charAt(i);
					if(message.charAt(i)=='.') {
						message2+="\n\n";
						stepCount++;
						if(i!=message.length()-1) {
						message2+=stepCount + ". ";
						}
					}
				}
				return message2;
				

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "No recipe found for the keyword: " + topic;
		}
}
