package org.jointheleague.modules;
import org.javacord.api.event.message.MessageCreateEvent;

public class ChangeUsername extends CustomMessageCreateListener {

	private static final String COMMAND = "!name";

	public ChangeUsername(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!name","");
			
			if(cmd.equals("")) {
				
				event.getChannel().sendMessage("What do you want to name me?");
				
				
			} else {
				event.getChannel().sendMessage("Sure! Changing my name to" + cmd);
				event.getApi().updateUsername(cmd);
			}
			
		}
	}
	
}
