package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class WebTaker extends CustomMessageCreateListener {

	public WebTaker(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed("WebFinder", "");

		helpEmbed = new HelpEmbed("NFLStats", "");

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String s = "";
		String message = event.getMessageContent();
		if (message.contains("!WebFinder")) {
			String ws = message.replace("!WebFinder", "").replaceAll(" ", "");
			event.getChannel().sendMessage("https://" + ws + ".com");
		}

		else if (message.contains("!NFLStats")) {
			String player = message.replace("!NFLStats ", "");
			for (int i = 0; i < player.length(); i++) {
				if (i != 0 && player.charAt(i) == ' ') {
					s += player.charAt(i);
					player = player.replace(s,"-" );
				}
			}
			event.getChannel().sendMessage("https://www.nfl.com/players/" + player + "/");
		}
	}
}
