package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			
			try {
				URL add = new URL("https://www.dictionaryapi.com/api/v3/references/collegiate/json/"+ word + "?key=d26d7e1d-65ea-4d57-8b7d-772327fcde01");
				InputStream is = add.openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String file = "";
				String read = br.readLine();
				while(read != null) {
					file += read;
					read = br.readLine();
				}
				//shortdef":["a carnivorous mammal (Felis catus) long 
				file = file.substring(file.indexOf("shortdef\"") + 12);
				file = file.substring(0,file.indexOf("\""));
				event.getChannel().sendMessage("Definition: " +file);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
