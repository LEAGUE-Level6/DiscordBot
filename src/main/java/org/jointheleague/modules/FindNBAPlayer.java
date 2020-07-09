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
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.NBA.NBARequest;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class FindNBAPlayer extends CustomMessageCreateListener {
	private static final String COMMAND = "!nbaplayer";
	private final Gson gson = new Gson();

	public FindNBAPlayer(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"!nbaplayer gives you the height, weight, position, "
						+ "or team of a NBA player of your choosing. Exempli gratia: \"!nba height James Harden\" "
						+ "would return \"James Harden is 6\'5\".\".");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			String msg = event.getMessageContent();
			String name = "";
			String[] phrase = msg.toLowerCase().split(" ");
			if (phrase.length == 4) {
				name = phrase[2] + " " + phrase[3];
				msg = name.replaceAll(" ", "%20");

				String requestURL = "https://www.balldontlie.io/api/v1/players?search=" + msg;

				try {
					URL url = new URL(requestURL);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					JsonReader repoReader = Json.createReader(con.getInputStream());
					JsonObject userJSON = ((JsonObject) repoReader.read());
					con.disconnect();

					name = name.replaceAll("%20", " ");
					NBARequest request = gson.fromJson(userJSON.toString(), NBARequest.class);
					String message = "";

					String teamName = "";
					Integer feet = 0;
					Integer weight = 0;
					String position = "";
					Integer inches = 0;
					if (phrase[1].equals("team")) {
						teamName = request.getData().get(0).getTeam().getFullName();
						message = name + " is/was on the " + teamName + ".";
					} else if (phrase[1].equals("height")) {
						feet = request.getData().get(0).getHeightFeet();
						inches = request.getData().get(0).getHeightInches();
						message = name + " is " + feet + "\'" + inches + "\".";
					} else if (phrase[1].equals("weight")) {
						weight = request.getData().get(0).getWeightPounds();
						message = name + " weighs " + weight + " pounds.";
					} else if (phrase[1].equals("position")) {
						position = request.getData().get(0).getPosition();
						message = name + " is a " + position + ".";
					} else {
						event.getChannel().sendMessage(phrase[1]
								+ " is not a valid parameter. Valid parameters include team, height, weight, and position.");
					}
					
					if (teamName == null) {
						event.getChannel().sendMessage(name + " does not have a registered team in the database.");
					} else if (feet == null) {
						event.getChannel().sendMessage(name + " does not have a registered height in the database.");
					} else if (weight == null) {
						event.getChannel().sendMessage(name + " does not have a registered weight in the database.");
					} else if (position == null) {
						event.getChannel().sendMessage(name + " does not have a registered position in the database.");
					} else {
						event.getChannel().sendMessage(message);
					}
					

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (ProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (msg.equals(COMMAND)) {
				event.getChannel().sendMessage("Please put a word after the command.");
			} else {
				event.getChannel().sendMessage("Invalid command was entered. Enter four parameters next time.");
			}
		}
	}
}
