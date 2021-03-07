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

import net.aksingh.owmjapis.api.APIException;

public class MTGAPI extends CustomMessageCreateListener{
	private static final String activator = "!get card";
	String card = "";
	URL url;
	public MTGAPI(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String eventContent = event.getMessageContent();
		if(eventContent.contains(activator)) {
			String card = new String(eventContent.substring(eventContent.indexOf(activator)+10,eventContent.length()));
			event.getChannel().sendMessage(card);
			try{ 
				String requestURL = "https://api.tcgplayer.com/catalog/products?" + 
						"productName=" + card +"" ;		
				URL url = new URL(requestURL);
				System.out.println(url);
				    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				    connection.setRequestMethod("GET");
				    connection.setRequestProperty("Authorization","bearer goKejCH5gahYwBI2Kgq2hc_NN5btSfPDP7BhwNt6AXwGv9lPAU1Z5uDhqPRRe_9wZBoIEfmIHVtWsFwDgoEz7HtPME73EBN343bp9BSsodKh664O-_ujAYTDKllx5JZOldgLHXP91HfrVg_pi4suAPwXPe2cA82jU849UFvBzR56BSLd_ehzK655Gd6yyF1fLhkYstK7ucmrgaPLsqoTy6aeQqwkbyLjAdipUlqTCvQLO5qFRSv3ITbfZNEScyI16ujd9UoqguUZBXX--7Df2dwa7aiUpx1DVImYg0i2FN1UgQ8pHJH95zB8ppvRfTGfnBFwVA");
				    connection.setRequestProperty("Accept","application/json");
				    System.out.println(connection.getResponseMessage()); //This is error code, e.g. 401, or "OK"
				    event.getChannel().sendMessage(connection.getResponseMessage());
				    BufferedReader in = new BufferedReader(new InputStreamReader(
				    connection.getInputStream()));
				    String inputLine;
				    StringBuilder response = new StringBuilder();
				    while ((inputLine = in.readLine()) != null) {
				        response.append(inputLine);
				        System.out.println("Parsing TCGPlayer response");
				    }
				    in.close();
				    System.out.println(response.toString());
				} catch (Exception e) {
				e.printStackTrace();
				}
					}		
	}
	}
