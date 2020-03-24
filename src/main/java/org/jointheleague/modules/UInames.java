package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class UInames extends CustomMessageCreateListener {

	private static final String NAME = "!user";
	Gson gson = new Gson();

	public UInames(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(NAME)) {
			try {
				URL urlForGetRequest = new URL("https://uinames.com/api/?gender=male&region=United%20States&ext");
				HttpURLConnection connection = (HttpURLConnection)	urlForGetRequest.openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();
				System.out.println(responseCode);
				if (responseCode == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					JsonParser parser = new JsonParser();
					JsonObject data = (JsonObject) parser.parse(in);
					String photo = data.get("photo").toString().replace("\"", "");
					String name = data.get("name").toString().replace("\"", "") + " " + data.get("surname").toString().replace("\"", "");
					String gender = data.get("gender").toString().replace("\"", "");
					String region = data.get("region").toString().replace("\"", "");
					String phone = data.get("phone").toString().replace("\"", "");
					String email = data.get("email").toString().replace("\"", "");
					String password = data.get("password").toString().replace("\"", "");
					String bday = data.get("birthday").getAsJsonObject().get("mdy").toString().replace("\"", "");
					event.getChannel().sendMessage("Please wait, name loading...‎‎‎‎‎‎‎‎‎‎");
					event.getChannel().sendMessage(photo);
					event.getChannel().sendMessage(name);
					event.getChannel().sendMessage(gender);
					event.getChannel().sendMessage(region);
					event.getChannel().sendMessage(phone);
					event.getChannel().sendMessage(email);
					event.getChannel().sendMessage(password);
					event.getChannel().sendMessage(bday);

				} else {
					event.getChannel().sendMessage("Oops! You are being rate limited!");
				}
			} catch (Exception e) {
				event.getChannel().sendMessage("We're sorry, a code error occured. \n Here's the full info: \n "
						+ e.getStackTrace().toString());
				e.printStackTrace();
			}
		}
	}
}