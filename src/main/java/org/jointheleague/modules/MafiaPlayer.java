package org.jointheleague.modules;

import java.util.ArrayList;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class MafiaPlayer extends CustomMessageCreateListener {

	private static final String COMMAND = ".playMafia";
	private static boolean Playing = false;
	private static boolean playerscount = false;
	private static boolean usernames = false;

	
	public MafiaPlayer(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Starts a game of Mafia. If you're unsure what Mafia is, check out this link for an explantion: https://en.wikipedia.org/wiki/Mafia_(party_game) (e.g. !playMafia). Make sure you have the option to allow server members to private message you on.");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		int p = 6;
		ArrayList<String> names = new ArrayList<String>();
		
		if (!Playing) {
			if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
				event.getChannel().sendMessage("Welcome To Mafia! Please enter the number of players (needs 6+ players)");
				playerscount = true;
			}
			
			
			if (playerscount) {
				try {
					String players = event.getMessageContent();
					p = Integer.parseInt(players);
					usernames = true;
				} finally {
					event.getChannel().sendMessage("Invalid response. Please enter in a number greater than or equal to 6.");
					//recur
				}
			}
			
			
			if (usernames) {
				for (int i = 0; i < p; i++) {
					event.getChannel().sendMessage("Player " + (i+1) + ", enter a username: ");
					names.add(event.getMessageContent());
				}
			}
			
			
			Playing = true;
		}
		
		
		if (Playing) {
			event.getChannel().sendMessage("it wrosk :DD :OOOOO");
			for (int i = 0; i < names.size(); i++) {
				event.getChannel().sendMessage(names.get(i));
			}
			
			Playing = false;
		}
	}
	
}
