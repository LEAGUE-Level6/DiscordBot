package org.jointheleague.modules;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import net.aksingh.owmjapis.api.APIException;
public class Dictionary extends CustomMessageCreateListener {

	public Dictionary(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().contains("!dictionary")) {
			String word = event.getMessageContent().replaceAll(" ", "").replace("!dictionary","");
			String add = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/"+ word + "?key=d26d7e1d-65ea-4d57-8b7d-772327fcde01";
			try {
				Gson g = new GsonBuilder().setLenient().create();
				String json = new Scanner(new URL(add).openStream(), "UTF-8").toString();
				JsonParser jp = new JsonParser();
				System.out.println(jp.parse(json));
				//JsonReader jr = Json.createReader(is);
				//JsonObject jo = jr.readObject();
				//jr.close();
				//System.out.println("Def: " + jo.getString("def"));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
