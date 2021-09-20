package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.Age;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class GuessAge extends CustomMessageCreateListener {
	private final Gson gson = new Gson(); 
	private WebClient webClient;
	private static final String COMMAND = "!age";

	public GuessAge(String channelName) {
		super(channelName);			
		helpEmbed = new HelpEmbed(COMMAND, "Enter your name and I will guess your age");

	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().contains(COMMAND)) {
			String name = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");
			if (name.equals("")) {
				event.getChannel().sendMessage("Please enter a name after the command");
			} else {
				int age = getAgeWithName(name);
				event.getChannel().sendMessage("You are "+age+ " years old");
			}
		}

	}
	public int getAgeWithName(String name) {
	    String requestURL = "https://api.agify.io?name="+name;
	    try {
			URL url = new URL(requestURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			JsonReader repoReader = Json.createReader(con.getInputStream());
		    JsonObject userJSON = ((JsonObject) repoReader.read());
		    con.disconnect();
		    
		    
		    Age age = gson.fromJson(userJSON.toString(), Age.class);
		    return age.getAge();

	    }
	    catch(MalformedURLException e){
			e.printStackTrace();
	    } catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return 0;
	}

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
	public static String getCommand() {
		// TODO Auto-generated method stub
		return COMMAND;
	}




	
}

