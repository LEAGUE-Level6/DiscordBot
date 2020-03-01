package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Spotify extends CustomMessageCreateListener {

	public Spotify(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		try {
			if (event.getMessageContent().contains("!spotify")) {
				String file = "";
				URL url = new URL("https://api.spotify.com");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();
				int response = conn.getResponseCode();
				Scanner s = new Scanner(url.openStream());
				while (s.hasNext()) {
					file += s.nextLine();
				}
				System.out.println(file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
