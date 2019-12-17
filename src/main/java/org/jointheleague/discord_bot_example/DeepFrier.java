package org.jointheleague.discord_bot_example;

import java.util.List;

import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.CustomMessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class DeepFrier extends CustomMessageCreateListener{

	public DeepFrier(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!deepfry")&&event.getMessage().getAttachments().size()>0) {
			List<MessageAttachment> l = event.getMessage().getAttachments();
			for (MessageAttachment i : l) {
				if(!i.isImage()) {
					return;
				}
			}
			
		}
	}

}
