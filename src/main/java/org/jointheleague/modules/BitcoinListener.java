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
import org.jointheleague.pojo.bitcoin.Bitcoin;
import org.jointheleague.pojo.bitcoin.EUR;
import org.jointheleague.pojo.bitcoin.GBP;
import org.jointheleague.pojo.bitcoin.Time;
import org.jointheleague.pojo.bitcoin.USD;

import com.google.gson.Gson;

//this example uses the "News API"
//documentation for the API can be found here: https://newsapi.org/docs/get-started
public class BitcoinListener extends CustomMessageCreateListener{

		//API key is received through creating an account on the web site.
		//API may not require a key, or may require the key as a header as in PictureOf.java
		private static final String COMMAND = "!Bitcoin";
		private final Gson gson = new Gson();
	  
		public BitcoinListener(String channelName) {
			super(channelName);
			helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service");

		}

		@Override
		public void handle(MessageCreateEvent event) {
			if(event.getMessageContent().contains(COMMAND)) {

				//remove the command so we are only left with the search term
				String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

				if (msg.equals("USD")||msg.equals("EUR")||msg.equals("GBP")&&!event.getMessageAuthor().isBotUser()) {
					String definition = Price(msg);
					event.getChannel().sendMessage(definition);
				} else if(!event.getMessageAuthor().isBotUser()){
					event.getChannel().sendMessage("Please input !Bitcoin and USD, EUR, or GBP after the command");
				}
				
			}
		}
		
		public String Price(String topic) {
			
			//create the request URL (can be found in the documentation)
			String requestURL = "https://api.coindesk.com/v1/bpi/currentprice.json";
			 
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
			    Bitcoin bc= gson.fromJson(userJSON.toString(), Bitcoin.class);
			    
				//get the first article (these are just java objects now)
			    USD us = bc.getBpi().getUSD();
			    GBP gp = bc.getBpi().getGBP();
			    EUR euro = bc.getBpi().getEUR();
				String t= bc.getTime().getUpdated();
				String price=" ";
				//get the title of the article 
				if (topic.equals("USD")) {
					price= us.getDescription()+ " $" +us.getRateFloat();
				} 
				else if (topic.equals("GBP")) {
					price= gp.getDescription()+ " £" +gp.getRateFloat();
				}
				else if (topic.equals("EUR")){
					price= euro.getDescription()+ " €" +euro.getRateFloat();
				}
				
				//get the content of the article 
				
				
				//create the message
				String message = t + "  " + topic + " " + price;
				
				//send the message 
				return message;
				

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "That currency does not exist";
		}
		
		
	}

