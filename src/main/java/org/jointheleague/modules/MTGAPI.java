package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.auth.Request;
import org.jointheleague.modules.pojo.MTGAPI.CardReturn;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class MTGAPI extends CustomMessageCreateListener{
	private static final String activator = "!get card";
	private static final String instructions = "!instructions";
	String card = "";
	URL url;
	String messageOut = "";
	String temp = "";
	int index = 0;
	private final Gson gson = new Gson();
	public MTGAPI(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String eventContent = event.getMessageContent();
		if(eventContent.contains(instructions)) {
			event.getChannel().sendMessage("To use this bot type !get card and then the full card name.\nCapitolization doesn't matter");
		}
		if(eventContent.contains(activator)&&!event.getMessageAuthor().isBotUser()) {
			String card = new String(eventContent.substring(eventContent.indexOf(activator)+10,eventContent.length()));
			card=card.replaceAll(" ", "%20");
			try{ 
				String requestURL = "https://api.tcgplayer.com/catalog/products?" + 
						"productName=" + card +"" ;		
				URL url = new URL(requestURL);
				System.out.println(url);
				    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				    connection.setRequestMethod("GET");
				    connection.setRequestProperty("Authorization","bearer a4dlnlnY6MHGhImmojFsCHsLueOasM3KKAei09fdmp7JwjYLlt9d3p-eyQ3L-_qe3wfi7EcYEkoXoRNa3X30s45TiEw3A9QuuqOWf5ih5soRyfkockFpSAqU3tyf1nEZgSdTeiipFuzoVV7Ire1oa9V43Ua9kyCqn-HBfJRxVwiED_aa6hbxWdlUp3GTLQ51ja-bW_1tIg9pbc-ndMY_aiNY5mN1Qcr3erNFgylzjPZgfOqEMQiGHBReOVr5WYEzweRJuk0GgqMmyV1Jba1dQjiEGuk8gJnSurSahS0KnzJv5K_sz4eMedcmcDke7EOKJb2U9Q");
				    connection.setRequestProperty("Accept","application/json");
				    System.out.println(connection.getResponseMessage()); //This is error code, e.g. 401, or "OK"
				    String inputLine;
				    StringBuilder response = new StringBuilder();
				    JsonReader repoReader = Json.createReader(connection.getInputStream());
				    JsonObject userJSON = ((JsonObject) repoReader.read());
				    CardReturn cardReturn = gson.fromJson(userJSON.toString(), CardReturn.class);
				    temp = temp+cardReturn.getResults().get(0).getCleanName();
				    if(card.equalsIgnoreCase("mountain")) {
				    	temp = temp+" "+cardReturn.getResults().get(3).getImageUrl();
				    }
				    else {
				    temp = temp+" "+cardReturn.getResults().get(0).getImageUrl();
				    }
				    connection.disconnect();
				    System.out.println(response.toString());
				    messageOut=temp;
				    temp = "";
					event.getChannel().sendMessage(messageOut);
				} catch (Exception e) {
				e.printStackTrace();
				event.getChannel().sendMessage("No Card With That Name Was Found");
				}
			
					}		
	}
	}





