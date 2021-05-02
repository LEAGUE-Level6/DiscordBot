package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.PredictGender.PredictedGender;
import org.jointheleague.modules.pojo.Weather.Main;
import org.jointheleague.modules.pojo.Weather.Sys;
import org.jointheleague.modules.pojo.Weather.WeatherResponse;
import org.jointheleague.modules.pojo.Weather.Wind;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class Genderize extends CustomMessageCreateListener {
	public static final String Command = "!Genderize";
	ArrayList<String> arr = new ArrayList<>();
	String country_id = "";
	private final Gson gson = new Gson();
	public Genderize(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(Command, "Predicts gender based on user input. Enter !Genderize with a name");
	}
	//do it for array and different regions. . .

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		//https://genderize.io/
		
		
		
		boolean containsUnrecog = false;
		
		if (event.getMessageContent().contains(Command)) {
			// remove the command so we are only left with the search term
			String msg = event.getMessageContent().replace(Command, "").trim().replaceAll(" ", "%20");
			
			if(msg.contains("country_id=")) {
				msg = msg.replaceAll("%20", "");
				country_id = msg.substring(msg.indexOf("=")+1, msg.length());
				msg = msg.substring(0, msg.indexOf("=")-10);
				System.out.println(country_id);
			//	System.out.println("Name: "+ msg);
			}
			if(msg.contains(",")) {
				msg = msg.replaceAll("%20", "");
			}

			if (msg.equals("")) {
				event.getChannel().sendMessage("Please put a word after the command"); 
			}
			String str = "";
			for(int i=0; i<msg.length(); i++) {
				if(!Character.isLetter(msg.charAt(i)) && msg.charAt(i)!=',') {
					containsUnrecog = true;
				}
				if(msg.charAt(i)==',') {
					arr.add(str);
					str= "";
				}
				else {
					str = str+""+msg.charAt(i);
				}
			}
			arr.add(str);
		
			for(int i=0; i<arr.size(); i++) {
				System.out.println(arr.get(i));
			}
			
			if(!containsUnrecog) {
				try {
					predictGender(msg, event);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				event.getChannel().sendMessage("Please put a valid name"); 
				containsUnrecog = false;
			}
	}
	//make it so that it only takes first name	
		//name[]=peter&name[]=lois&name[]=stevie
	}
	public void predictGender(String msg, MessageCreateEvent event) throws IOException {
		//need new calls.... 
		String input = "";
		for(int i=0; i<arr.size(); i++) {
			input= input+"name[]="+ arr.get(i)+"&";
		}
		input = input.substring(0, input.length()-1);
		System.out.println(input);
		String requestURL;
		if(country_id.equals("")) {
			 requestURL = "https://api.genderize.io/?" + input; //fix this part to array
		}
		else {
			 requestURL = "https://api.genderize.io/?" + input+"&country_id="+country_id; //fix this part to array
		}
		System.out.println(requestURL);
		 URL url = new URL(requestURL);
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
		 
		 
		 con.setRequestMethod("GET");                   
		 JsonReader repoReader = Json.createReader(con.getInputStream());
		 JsonArray userJSON = ((JsonArray) repoReader.readArray	());
		 con.disconnect();
		 PredictedGender[] gender = gson.fromJson(userJSON.toString(), PredictedGender[].class);
		for(int i=0; i<gender.length; i++) {
			event.getChannel().sendMessage("The name "+ gender[i].getName()+" is "+gender[i].getGender()+" by "+gender[i].getProbability()*100+"%");
		}
		// System.out.println(gender);
		 //System.out.println(gender.getGender());
		// System.out.println(gender.getProbability());
		 
		 
		
	}
}
