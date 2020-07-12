package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class SportsGuesser extends CustomMessageCreateListener {

	private static final String COMMAND = "!SportFinder";

	public SportsGuesser(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed(COMMAND, "Just type !SportFinder followed by the most common/known piece of equipment that your sport uses. Make sure your sport is a popular one! It will keep asking you questions until it determines your sport.");

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String response = event.getMessageContent();
		String equipment = response.replace("!SportFinder", "").replaceAll(" ", "");
		
		if (equipment.equalsIgnoreCase("racket")) {
			event.getChannel().sendMessage("Your sport is either Tennis, Badminton, Squash, Table Tennis, Racquetball, or Pickleball");
		}
		
		else if (equipment.equalsIgnoreCase("bat")) {
			event.getChannel().sendMessage("Your sport is either Baseball or Cricket. Is your sport played professionally in the US? Reply with yes or no and be sure to use the !SportFinder command");
			if (equipment.equalsIgnoreCase("yes")) {
				event.getChannel().sendMessage("Your sport is Baseball!");
			}
		}

	}

}
