

package org.jointheleague.modules;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.jointheleague.modules.pojo.*;


public class GetPicture extends CustomMessageCreateListener {
	private UserTest user;
	private static final String COMMAND = "!GetPicture";
	Gson gson = new Gson();
	boolean begun = false;
	String answer = "";
	final int startingLives = 10;
	int livesRemaining = startingLives;
	String url = "";
	String test = "";
	public GetPicture(String channelName) {
		super(channelName);
	}
	//event.getMessageAuthor().getName()

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace(COMMAND,"");
			
			if(cmd.equals("")) {
				
				
				event.getChannel().sendMessage("Please put a string after the command!");
				
			
			}else {
				GetPicture picture = new GetPicture(channelName);
				try {
					
					url = picture.getUser(cmd);
					//System.out.println("ACTUAL URL: "+ url);
					event.getChannel().sendMessage(url);
					answer = test;
					begun = true;
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			
			
			
			}}
	}
		
		 String getUser(String cmd) throws IOException {

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
//		        //System.out.println("userJSON: " + userJSON);
//		        user.getAddress();
//		        //System.out.println("GET USER NAME FROM POJO: " + user.getName());
//		        //System.out.println("GET USER CITY FROM POJO: " + user.getAddress().getCity());
//		        //System.out.println("GET URL FROM POJO: " + user.getWebsite());

		        
		  //      //System.out.println(user.getWebsite());
		        
		        
		        
//		        for (int i = 0; i < user.toString().length(); i++) {
//					//System.out.println(i);
//				}
		        boolean found = false;
		        String tempURL ="";
		    	  for (int i = userJSON.toString().indexOf("https"); i < userJSON.toString().indexOf("https")+1000; i++) {
		    	  
		    		if(userJSON.toString().charAt(i)=='"') {
		    			
		    		  found = true;
		    		  
		    	  }else if(found==false) {
						tempURL+=userJSON.toString().charAt(i);
					}
					}
		    	  
		    	  return tempURL;
						
			}
		        
		        
		        
		    }
		 
		
		 
		 
	
	

