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
			String card = new String(eventContent.substring(eventContent.indexOf(activator)+9,eventContent.length()));
			event.getChannel().sendMessage(card);
			try{ 
				URL url = new URL("https://api.tcgplayer.com/catalog/products?categoryId=1?productType=null%22");
				    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				    connection.setRequestMethod("GET");
				    connection.setRequestProperty("Authorization","bearer ");
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
//			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
//			    for (String line; (line = reader.readLine()) != null;) {
//			        System.out.println(line);
//			    }
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
	}
	}

	//https://api.tcgplayer.com/v1.17.0/catalog/categories/24/search/manifest
	//api.tcgplayer.comq= jace, vryn's prodigy&sortBy=popularity&apiKey=DNzc3NjAyNzUwNzU4Mzg3NzUy.X7F0_g.rCkANQqM-BX3tfpth5K_hSY5j3k

