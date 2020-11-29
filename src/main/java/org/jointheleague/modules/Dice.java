package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Dice extends CustomMessageCreateListener{
	private static final String COMMAND = "!roll";
	int randy = 0;
	int modifier = 0;
	int sides = 0;
	public Dice(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String eventContent = event.getMessageContent();
		if (eventContent.contains(COMMAND)) {
			if(eventContent.contains("+")) {
				modifier = Integer.parseInt(eventContent.substring(eventContent.indexOf("+"), eventContent.length()));
				if(eventContent.contains("d")) {
					sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.indexOf("+")));
					randy = new Random().nextInt(sides)+1;
					event.getChannel().sendMessage("Rolled a "+(randy+modifier));
					modifier = 0;
				}
			}
			else if(eventContent.contains("d")) {
			sides = Integer.parseInt(eventContent.substring(eventContent.indexOf("d")+1, eventContent.length()));
			randy = new Random().nextInt(sides)+1;
			event.getChannel().sendMessage("Rolled a "+(randy+modifier));
			modifier = 0;
		}
//			if(eventContent.contains("d4")) {
//				randy = new Random().nextInt(4)+1;
//				event.getChannel().sendMessage("Rolled a "+(randy+modifier));
//			}
//			else if(eventContent.contains("d6")) {
//				randy = new Random().nextInt(6)+1;
//				event.getChannel().sendMessage("Rolled a "+(randy+modifier));
//			}
//			else if(eventContent.contains("d8")) {
//				randy = new Random().nextInt(8)+1;
//				event.getChannel().sendMessage("Rolled a "+(randy+modifier));
//			}
//			else if(eventContent.contains("d10")&&!eventContent.contains("d100")) {
//				randy = new Random().nextInt(10)+1;
//				event.getChannel().sendMessage("Rolled a "+(randy+modifier));
//			}
//			else if(eventContent.contains("d12")) {
//				randy = new Random().nextInt(12)+1;
//				event.getChannel().sendMessage("Rolled a "+(randy+modifier));
//			}
//			else if(eventContent.contains("d20")) {
//				randy = new Random().nextInt(20)+1;
//				event.getChannel().sendMessage("Rolled a "+(randy+modifier));
//			}
//			else if(eventContent.contains("d100")) {
//				randy = new Random().nextInt(100)+1;
//				event.getChannel().sendMessage("Rolled a "+(randy+modifier));
//			}
		}
	}

}
