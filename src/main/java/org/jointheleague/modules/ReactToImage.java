package org.jointheleague.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.Timer;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.UserTest;

import com.google.gson.Gson;

public class ReactToImage extends CustomMessageCreateListener implements ActionListener {
	private UserTest user;
	Timer userReaction;
	Timer randomCountdown;
	private static final String COMMAND = "!reactionTime";
	Gson gson = new Gson();
	String answer = "";
	Random ran = new Random();
	boolean gameInMotion = false;
	String url = "";
	int setTime = 0;
	int timeTaken = 0;
	int delay = 0;
	boolean searchValid = false;
	Timer distractionTimer = new Timer(1, this);
	boolean replayValid = false;
	boolean changeImage = true;
	String currentReactionImage = "";
	private static final String apiSECRETKey = "2-_8IKLhGunAfTXr9sTtSTayMous90raYpZygsBFrOI";
	MessageCreateEvent streamEvent;
	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	public ReactToImage(String channelName) {
		super(channelName);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {

		streamEvent = event;
		if(event.getMessageContent().equals("To replay, type  !replayReact.")==false) {
		if (event.getMessageContent().contains(COMMAND) || event.getMessageContent().contains("!replayReact")) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

			if (cmd.equals("")) {
				event.getChannel().sendMessage(
						"Enter in the thing that you want to react to. This image will pop up and clock your reaction time. Type it as !giveImage + search item. ei. !giveImage puppy");
				searchValid = true;
			} else if (replayValid = true) {
				event.getChannel().sendMessage(
						"Would you like the change the image you want to react to? Type -!reactionYes- if so and -!reactionNo- if you DO NOT want to change your image.");
			}
		}
		} else if (searchValid && event.getMessageContent().equals(
				"Enter in the thing that you want to react to. This image will pop up and clock your reaction time. Type it as !giveImage + search item. ei. !giveImage puppy") == false) {
			GetPicture picture = new GetPicture(channelName);
			try {
				if(changeImage) {
				String removeCommand= event.getMessageContent();
				removeCommand = removeCommand.replaceAll("!giveImage ", "");
				if(removeCommand.equals("elephant")) {
				      url = "https://assets.nrdc.org/sites/default/files/styles/full_content--retina/public/media-uploads/wlds43_654640_2400.jpg?itok=LbhnLIk9";
				}else {
				url = picture.getUser(removeCommand);
				}
				event.getChannel().sendMessage(url);
				currentReactionImage = url;
				}else {
				event.getChannel().sendMessage(currentReactionImage);
				}
				randomCountdown = new Timer(ran.nextInt(5000), this);
				userReaction = new Timer(1, this);
				event.getChannel().sendMessage(
						"This image will pop onto your screen  to test your reaction time. Enter any character when the image pops up as fast as you can.");
				event.getChannel().sendMessage("Type -!reactionOkay- when you are ready.");

				searchValid = false;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (gameInMotion && event.getMessageContent().length()<2) {
			userReaction.stop();
			distractionTimer.stop();
			setTime = timeTaken;
			timeTaken = 0;
			gameInMotion = false;
			event.getChannel().sendMessage("Good Job! Your reaction Time was clocked at " + setTime + " milliseconds!");
		} else if (event.getMessageContent().equals("!reactionOkay")) {
			event.getChannel().sendMessage("(Get your enter key ready!) Countdown starting in");
			event.getChannel().sendMessage("3");
			event.getChannel().sendMessage("2");
			event.getChannel().sendMessage("1");
			event.getChannel().sendMessage("Reaction Time!");
			randomCountdown.start();
		} else if (event.getMessageContent().contains("Good Job! Your reaction Time was clocked at")) {
			event.getChannel().sendMessage("To replay, type  !replayReact.");
			replayValid = true;
		}else if(event.getMessageContent().equals("!reactionYes")) {
			event.getChannel().sendMessage("Enter in the thing that you want to react to. This image will pop up and clock your reaction time. Type it as !giveImage + search item. ei. !giveImage puppy");
			searchValid = true;
			changeImage = true;
		}else if(event.getMessageContent().equals("!reactionNo")) {
			searchValid = true;
			changeImage = false;
			event.getChannel().sendMessage("your image will stay the same");
		}
	}

	String getUser(String cmd) throws IOException {
		String getUnsplashURL = "https://api.unsplash.com/search/photos?query=" + cmd;
		URL fetchedURL = new URL(getUnsplashURL);
		HttpURLConnection connection = (HttpURLConnection) fetchedURL.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", apiSECRETKey);
		JsonReader repoReader = Json.createReader(connection.getInputStream());
		JsonObject userJSONDoc = ((JsonObject) repoReader.read());
		connection.disconnect();
		user = gson.fromJson(userJSONDoc.toString(), UserTest.class);
		boolean found = false;
		String discordURL = "";
		for (int i = userJSONDoc.toString().indexOf("https"); i < userJSONDoc.toString().indexOf("https") + 1000; i++) {

			if (userJSONDoc.toString().charAt(i) == '"') {

				found = true;

			} else if (found == false) {
				discordURL += userJSONDoc.toString().charAt(i);
			}
		}
		return discordURL;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//			if(e.getSource()==distractionTimer) {
//				streamEvent.getChannel().sendMessage("|");
//			}

		if (e.getSource() == randomCountdown && delay == 2) {
			streamEvent.getChannel().sendMessage(currentReactionImage);
			gameInMotion = true;
			userReaction.start();
			randomCountdown.stop();
			System.out.println("action");
			delay = 0;
			System.out.println(currentReactionImage);
		} else if (e.getSource() == randomCountdown) {
			delay++;
		} else if (e.getSource() == userReaction) {
			timeTaken++;
			System.out.println(timeTaken);
		}
	}
}
