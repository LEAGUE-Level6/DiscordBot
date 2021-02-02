package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;


import com.google.gson.Gson;

public class CurrencyConverter extends CustomMessageCreateListener {
	private static final String COMMAND = "!currency";
	private final Gson gson = new Gson();
	Conversions conversions; 
	public CurrencyConverter(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,"Helps you convert the live currency between different countries. All you have to do is type 'convert', an amount, and then '[currency1]->[currency2]'. e.g. '!currency convert 1 usd-eur'");
		// TODO Auto-generated constructor stub
		getConversionData();
	}

	@Override
	public void handle(MessageCreateEvent event)  {
		// TODO Auto-generated method stub
		String contents = event.getMessageContent();
		String[] cmds = contents.split(" ");
		if (contents.contains(COMMAND)) {
			if(cmds.length>1 && cmds[1].equalsIgnoreCase("convert")) {
				int messageindex = contents.indexOf("convert") + 8; //getting amount index
				String number = contents.substring(messageindex);
				String number1 = number.split(" ")[0];
				double amount = Double.parseDouble(number1);
				int convertindex = contents.indexOf(number1)+number1.length()+1; //getting countries index
				String convert = contents.substring(convertindex);
				//event.getChannel().sendMessage("convert index: " + convert);
				String c1 = convert.substring(0, convert.indexOf('-'));
				//event.getChannel().sendMessage("Country1: " + c1);
				String c2 = convert.replace(c1 + '-', "");
				//event.getChannel().sendMessage("Country2: " + c2);
				double ratio = conversions.getRates().getRatio(c2);
				//event.getChannel().sendMessage("Ratio: " + ratio);
				double change = amount * ratio;
				event.getChannel().sendMessage("The amount: " + amount + " in " + c1 + " has been converted to " + change + " in " + c2 + "!");
				
			} //else {
			//	event.getChannel().sendMessage("You must input the word 'convert', a value, and [country#1]-[country#2]. e.g. '!currency convert 1 usd-eur'");
			//}
		}
			
	}
	
//	public double convert() {
		
//		return null;
//	}

	public void getConversionData() {
		
		//create the request URL (can be found in the documentation)
		String requestURL = "https://api.exchangeratesapi.io/latest?base=USD";
		 
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
		     conversions = gson.fromJson(userJSON.toString(), Conversions.class);
		     try {
				conversions.getRates().initializeRatios2();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		    /*
			//get the first article (these are just java objects now)
			Article article = conversions.getArticles().get(0);
			
			//get the title of the article 
			String articleTitle = article.getTitle();
			
			//get the content of the article 
			String articleContent = article.getContent();
			
			//create the message
			String message = articleTitle + " - " + articleContent;
			
			//send the message 
			return message;
			*/

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	
	}
}
