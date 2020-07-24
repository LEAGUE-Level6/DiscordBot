package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.javacord.api.event.message.MessageCreateEvent;
import com.google.gson.*;

public class GithubRepos extends CustomMessageCreateListener {

	private static final String ARRIVALS = "!github";
	Gson gson = new Gson();

	public GithubRepos(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(ARRIVALS)) {

			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!github", "");

			if (cmd.equals("")) {

				event.getChannel().sendMessage("GITHUB BOT: Please enter a Github username.");

			} else {
				try {
					URL urlForGetRequest = new URL("https://api.github.com/users/" + cmd + "/repos");
					HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
					conection.setRequestMethod("GET");
					int responseCode = conection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
						JsonParser parser = new JsonParser();
						JsonArray root = parser.parse(in).getAsJsonArray();
						event.getChannel().sendMessage("Repos for " + cmd + ":");
						for (int i = 0; i < root.size(); i++) {
							event.getChannel().sendMessage(
									root.get(i).getAsJsonObject().get("full_name").toString().replace("\"", ""));
						}
					} else {
						event.getChannel().sendMessage(
								"We're sorry, we could not access the Github API servers. Perhaps you're being rate-limited?");
					}
				} catch (Exception e) {
					event.getChannel().sendMessage(
							"GITHUB BOT: Please make sure that username is valid. Remember, this only works for users, not orgs.");
					e.printStackTrace();
				}

			}

		}
		if (event.getMessageContent().contains("!gists")) {

			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!gists", "");

			if (cmd.equals("")) {

				event.getChannel().sendMessage("GITHUB BOT: Please enter a Github username.");

			} else {
				try {
					URL urlForGetRequest = new URL("https://api.github.com/users/" + cmd + "/gists");
					HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
					conection.setRequestMethod("GET");
					int responseCode = conection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
						JsonParser parser = new JsonParser();
						JsonArray root = parser.parse(in).getAsJsonArray();
						event.getChannel().sendMessage("Gists for " + cmd + ":");
						for (int i = 0; i < root.size(); i++) {
							event.getChannel().sendMessage(
									root.get(i).getAsJsonObject().get("html_url").toString().replace("\"", ""));
						}
					} else {
						event.getChannel().sendMessage(
								"We're sorry, we could not access the Github API servers. Perhaps you're being rate-limited?");
					}
				} catch (Exception e) {
					event.getChannel().sendMessage("GITHUB BOT: Please make sure that username is valid.");
					e.printStackTrace();
				}

			}

		}
		if (event.getMessageContent().contains("!md")) {

			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!md", "");

			if (cmd.equals("")) {

				event.getChannel().sendMessage("MARKDOWN BOT: Please enter markdown syntax.");

			} else {
				try {
					URL urlForGetRequest = new URL("https://api.github.com/markdown/raw");
					HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
					connection.setRequestMethod("POST");
					connection.setRequestProperty("Content-Type", "text/x-markdown; utf-8");
					connection.setRequestProperty("Accept", "text/html");
					connection.setDoOutput(true);
					try(OutputStream os = connection.getOutputStream()){
						byte[] input = cmd.getBytes("utf-8");
						os.write(input);
					}
					int responseCode = connection.getResponseCode();
					System.out.println(cmd);
					System.out.println(responseCode);
					if (responseCode == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						event.getChannel().sendMessage(in.toString());
					} else {
						event.getChannel().sendMessage(
								"We're sorry, we could not access the Github API servers. Perhaps you're being rate-limited?");
					}
				} catch (Exception e) {
					event.getChannel().sendMessage(
							"MARKDOWN BOT: Please make sure that Markdown is valid.");
					e.printStackTrace();
				}

			}

		}
	}

}