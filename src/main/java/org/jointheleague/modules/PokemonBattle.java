package org.jointheleague.modules;

import java.util.List;
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
		} else {
			PokemonWrapper moves=getMoves(msg);
			moves.getMoves().get(0);
			for(int i=0; i<moves.getMoves().size(); i++) {
			event.getChannel().sendMessage(moves.getMoves().get(i).toString());
			}
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
			    List <Object> moves=pokemonWrapper.getMoves();
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
