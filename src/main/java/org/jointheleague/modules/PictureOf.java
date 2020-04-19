package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.Gson;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.jointheleague.modules.pojo.*;


public class PictureOf extends CustomMessageCreateListener {
	private UserTest user;
	private static final String COMMAND = "!PictureOf";
	Gson gson = new Gson();
	public PictureOf(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace(COMMAND,"");
			
			if(cmd.equals("")) {
				
				
				event.getChannel().sendMessage("Please put a string after the command!");
				
				
			} else {
				PictureOf picture = new PictureOf(channelName);
				try {
					picture.getUser(cmd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
		
		 void getUser(String cmd) throws IOException {

		        //The URL for the endpoint we want to access
			 
			 	//String requestURL = "https://jsonplaceholder.typicode.com/users/1";
		       String requestURL = "https://api.unsplash.com/search/photos?query=" + cmd;

		        //Setup request
		        URL url = new URL(requestURL);
		        HttpURLConnection con = (HttpURLConnection) url.openConnection();

		        //We want to GET the resource
		        con.setRequestMethod("GET");
		        con.setRequestProperty("Authorization", "Client-ID CcRHw6zPYau7eLeEaoJD7iB1LN7x4FznKKnj70xfmN4");


		        //Read the response
		        JsonReader repoReader = Json.createReader(con.getInputStream());
		        JsonObject userJSON = ((JsonObject) repoReader.read());

		        //Disconnect
		        con.disconnect();

		        //turn user to POJO
		        user = gson.fromJson(userJSON.toString(), UserTest.class);

		        //Proof that it works
		        System.out.println("userJSON: " + userJSON);
		        user.getAddress();
		        System.out.println("GET USER NAME FROM POJO: " + user.getName());
		        System.out.println("GET USER CITY FROM POJO: " + user.getAddress().getCity());
		        System.out.println("GET URL FROM POJO: " + user.getWebsite());

		    }
		 
	
	
}


	class Results {
		
		
	}