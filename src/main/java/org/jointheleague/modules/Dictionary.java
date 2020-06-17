package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.UserTest;
import org.jointheleague.modules.pojo.dictionary.DictionaryWrapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class Dictionary extends CustomMessageCreateListener {

	private static final String COMMAND = "!define";
	Gson gson = new Gson();

	public Dictionary(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Call " + COMMAND + " (Message) and it will return the definition of the given word");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {

			String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

			if (msg.equals("")) {

				event.getChannel().sendMessage("Please put a word after the command");

			} else {

				String definition = getDefinition(msg);
				event.getChannel().sendMessage(definition);
			}
		}
	}

	String getDefinition(String word) {

		String requestURL = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/" + word
				+ "?key=e9d88a70-4fad-4002-a2d6-b15419798e82";
		URL url;

		try {

			url = new URL(requestURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");

			JsonReader repoReader = Json.createReader(con.getInputStream());
			JsonObject userJSON = ((JsonObject) repoReader.read());

			con.disconnect();

			DictionaryWrapper wrapper = gson.fromJson(userJSON.toString(), DictionaryWrapper.class);

			return wrapper.getShortdef().get(0);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Definition not found";
	}
}
