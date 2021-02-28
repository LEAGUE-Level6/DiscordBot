package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class _SammySamSam extends CustomMessageCreateListener {
	public _SammySamSam(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String m = event.getMessageContent();
		if (m.toLowerCase().contains("~bingbongbing")) {
			event.getChannel().sendMessage("Gaby is your supreme lord. He will vanquish you. You petty imbeciles");
		}
	}
}
