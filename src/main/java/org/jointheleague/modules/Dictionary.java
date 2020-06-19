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
import org.jointheleague.modules.pojo.dictionary.ThesaurusWrapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class Dictionary extends CustomMessageCreateListener {

	Gson gson = new Gson();

	public Dictionary(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed("!define + !syns",
				"Call !define (Message) and it will return the definition of the given word. Call !syns "
						+ "(Message) and it will return the synonyms of the given word");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains("!define")) {

			String msg = event.getMessageContent().replaceAll(" ", "").replace("!define", "");

			if (msg.equals("")) {

				event.getChannel().sendMessage("Please put a word after the command");

			} else {

				String definition = getDefinition(msg);
				event.getChannel().sendMessage(definition);
			}

		} else if (event.getMessageContent().contains("!syns")) {

			String msg = event.getMessageContent().replaceAll(" ", "").replace("!syns", "");

			if (msg.equals("")) {

				event.getChannel().sendMessage("Please put a word after the command");

			} else {

				String synonyms = getSynonyms(msg);
				event.getChannel().sendMessage(synonyms);
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

			JsonReader reader = Json.createReader(con.getInputStream());
			JsonStructure json = reader.read();

			con.disconnect();

			DictionaryWrapper[] wrapper = gson.fromJson(json.toString(), DictionaryWrapper[].class);

			return word + " (" + wrapper[0].getFl() + "): " + wrapper[0].getShortdef().get(0);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Definition not found";
	}

	String getSynonyms(String word) {

		String requestURL = "https://dictionaryapi.com/api/v3/references/thesaurus/json/" + word
				+ "?key=5a2a4fe6-a8c2-4b98-976d-a7dfd6cab0b2";
		URL url;

		try {

			url = new URL(requestURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");

			JsonReader reader = Json.createReader(con.getInputStream());
			JsonStructure json = reader.read();

			con.disconnect();

			ThesaurusWrapper[] wrapper = gson.fromJson(json.toString(), ThesaurusWrapper[].class);

			return "Synonyms for " + word + " include - " + wrapper[0].getSyns().get(0);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "No synonyms found";
	}
}
