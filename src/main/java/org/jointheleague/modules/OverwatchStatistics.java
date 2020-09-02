package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.OverwatchStatisticsRequest;
import org.jointheleague.modules.pojo.Rating;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class OverwatchStatistics extends CustomMessageCreateListener {
	private static final String COMMAND = "!owstats";
	private final Gson gson = new Gson();

	public OverwatchStatistics(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
			String msg = event.getMessageContent();
			String battleTag = "Ignidris-1573";
			String[] phrase = msg.split(" ");
			if (phrase.length < 2) {
				event.getChannel().sendMessage("Please include a battle tag in your message");
			} else {
				battleTag = phrase[1];
				if (battleTag.contains("#")) {
					battleTag = battleTag.replace('#', '-');
				}
				String requestURL = "https://ow-api.com/v1/stats/pc/us/" + battleTag + "/profile";

				URL url;
				try {
					url = new URL(requestURL);

					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.addRequestProperty("User-Agent", "Chrome");

					JsonReader repoReader = Json.createReader(con.getInputStream());
					JsonObject userJSON = ((JsonObject) repoReader.read());
					con.disconnect();

					OverwatchStatisticsRequest owstatsrequest = gson.fromJson(userJSON.toString(), OverwatchStatisticsRequest.class);
					Rating rating = gson.fromJson(userJSON.toString(), Rating.class);

					if (owstatsrequest.getPrivate()) {
						event.getChannel().sendMessage("Career profile is private.");
					} else {
						MessageBuilder builder = new MessageBuilder();
						builder.addAttachment(new URL(owstatsrequest.getPrestigeIcon()));
						builder.addAttachment(new URL(owstatsrequest.getLevelIcon()));
						builder.addAttachment(new URL(owstatsrequest.getRatings().get(0).getRoleIcon()));
						builder.addAttachment(new URL(owstatsrequest.getRatings().get(0).getRankIcon()));
						builder.addAttachment(new URL(owstatsrequest.getRatings().get(1).getRoleIcon()));
						builder.addAttachment(new URL(owstatsrequest.getRatings().get(1).getRankIcon()));
						builder.addAttachment(new URL(owstatsrequest.getRatings().get(2).getRoleIcon()));
						builder.addAttachment(new URL(owstatsrequest.getRatings().get(2).getRankIcon()));
						builder.addAttachment(new URL(owstatsrequest.getIcon()));
						builder.send(event.getChannel());
					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
