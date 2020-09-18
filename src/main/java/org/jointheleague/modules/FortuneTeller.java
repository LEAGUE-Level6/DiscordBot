package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class FortuneTeller extends CustomMessageCreateListener {
	private static final String COMMAND = "!fortune";
	private boolean fO = false;
	private int interaction = 0;
	private String[] objects = {"food", "animal", "TV show", "restaurant", "school subject", "video game", "band", "fictional character", "sport", "season"};
	
	 public FortuneTeller (String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + fortune to recieve a prediction about your life!");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageAuthor().getName().equals("Ella's Bot")) {
		return;
		}
		
		String a = event.getMessageContent();
		if(a.equals("!fortune")) {
			fO = true;
			interaction = 1;
			event.getChannel().sendMessage("~Greetings, "+event.getMessageAuthor().getName() + ", I will tell you your fortune. Tell me, what is your favorite " + objects[new Random().nextInt(objects.length)]+"?~");
		}
		else if (interaction == 1) {
			String m = event.getMessageContent();
			event.getChannel().sendMessage("Interesting... Give me a moment as I forsee your future...");
			if(m.charAt(m.length()-1) == 'a') {
				event.getChannel().sendMessage("You will die.");
			}
		}
		
	}
}

