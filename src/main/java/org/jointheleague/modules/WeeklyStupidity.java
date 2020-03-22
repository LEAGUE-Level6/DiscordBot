package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class WeeklyStupidity extends CustomMessageCreateListener {

	private static final String WSGET = "!stupid";
	private static final String GET = "!blogger";
	Gson gson = new Gson();

	public WeeklyStupidity(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(GET)) {
			try {
				String cmd = event.getMessageContent().replaceAll(" ", "").replace("!blogger", "");

				if (cmd.equals("")) {

					event.getChannel().sendMessage("Please enter a Blog ID. You can find a blog ID by going into the Blogger homepage and copying the long number in the URL.");

				} else {
					URL urlForGetRequest = new URL(
							"https://www.googleapis.com/blogger/v3/blogs/" + cmd + "/posts?key=AIzaSyA_YM3vwuAQtbz6pPIh0KsQ3ZMupYIdAFs");
					String readLine = null;
					HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
					conection.setRequestMethod("GET");
					int responseCode = conection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
						JsonParser parser = new JsonParser();
						JsonObject fileData = (JsonObject) parser.parse(in);
						JsonObject root = (JsonObject) fileData.getAsJsonArray("items").get(0);
						event.getChannel()
								.sendMessage("The newest post is: "
										+ root.get("title").toString().replace("\"", "") + ", published on "
										+ root.get("published").toString().split("T")[0].replace("\"", "")
										+ ". You can view this post here: "
										+ root.get("url").toString().replace("\"", ""));
					} else {
						event.getChannel().sendMessage("We're sorry, we could not access the Blogger API servers.");
					}
				}
			} catch (Exception e) {
				event.getChannel().sendMessage("We're sorry, a code error occured. \n Here's the full info: \n "
						+ e.getStackTrace().toString());
				e.printStackTrace();
			}
		}
		if(event.getMessageContent().contains(WSGET)) {
			event.getChannel().sendMessage("Weekly Stupidity Shortcut activated! In the future you can use ");
			event.getChannel().sendMessage("!blogger 2889704498442246594");
		}
	}
}