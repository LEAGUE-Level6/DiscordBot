package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import org.jointheleague.modules.pojo.Pokemon.PokemonWrapper;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class PokemonBattle extends CustomMessageCreateListener {
	
//private final String apiKey = "59ac01326c584ac0a069a29798794bec";
private static final String COMMAND = "!pokemon";
private final Gson gson = new Gson();
List<String> movesList=new ArrayList<String>();
boolean comma=false;
String chars;
String move;
int progress;
Random rand=new Random();
List<String> randomList=new ArrayList<String>();
int random;
int players=0;

public PokemonBattle (String channelName){
super(channelName);
helpEmbed=new HelpEmbed("COMMAND", "");
}

@Override
public void handle(MessageCreateEvent event) throws APIException {
	// TODO Auto-generated method stub
	if(event.getMessageContent().contains(COMMAND)) {

		//remove the command so we are only left with the search term
		String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

		if (msg.equals("")) {
			event.getChannel().sendMessage("Please put a word after the command");
		} else if(players==0) {
			players=1;
			PokemonWrapper moves=getMoves(msg);
			moves.getMoves().get(0);
			for(int i=0; i<moves.getMoves().size(); i++) {
			//event.getChannel().sendMessage(moves.getMoves().get(i).toString());
			comma=false;
			progress=0;
			move="";
			chars="";
			while(!comma) {
				chars+=moves.getMoves().get(i).toString().charAt(12+progress);
			if(chars.equals(",")) {
				comma=true;
			}
			else {
				move+=chars;
				chars="";
				progress++;
			}
			}
			movesList.add(move);
			//System.out.println(move);
			}
			for(int i=0; i<4; i++) {
			random=rand.nextInt(movesList.size());
			randomList.add(movesList.get(random));
			System.out.println(movesList.get(random));
			}
			event.getChannel().sendMessage("You selected "+msg+". Your moves are "+randomList.get(0)+", "+randomList.get(1)+", "+randomList.get(2)+" and"+randomList.get(3)+ ". Waiting for second player.");
		}
	}
}
public PokemonWrapper getMoves(String pokemon) {
			
			//create the request URL (can be found in the documentation)
			String requestURL = "https://pokeapi.co/api/v2/pokemon/" +
			          pokemon+"/";
			 
			try {
				
				//the following code will probably be the same for your feature
				URL url = new URL(requestURL);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestProperty("User-Agent", "Jake");
				System.out.println("open");
				con.setRequestMethod("GET");
				JsonReader repoReader = Json.createReader(con.getInputStream());
				System.out.println("getinput");
			    JsonObject userJSON = ((JsonObject) repoReader.read());
			    con.disconnect();
			    
			    PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
			    return pokemonWrapper;
			}
			catch (MalformedURLException e) {
				System.out.println("url");
				e.printStackTrace();
			} catch (ProtocolException e) {
				System.out.println("prot");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("io");
				e.printStackTrace();
			}
			return null;
}
public PokemonWrapper getMovesUrl(String url1){
	url1.replaceFirst("%7D", "");
	String requestURL = url1;
	
	try {
		
		//the following code will probably be the same for your feature
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Jake");
		System.out.println("open");
		con.setRequestMethod("GET");
		JsonReader repoReader = Json.createReader(con.getInputStream());
		System.out.println("getinput");
	    JsonObject userJSON = ((JsonObject) repoReader.read());
	    con.disconnect();
	    
	    PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
	    return pokemonWrapper;
	}
	catch (MalformedURLException e) {
		System.out.println("url");
		e.printStackTrace();
	} catch (ProtocolException e) {
		System.out.println("prot");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("io");
		e.printStackTrace();
	}
	return null;
	
}
}
