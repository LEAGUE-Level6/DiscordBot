package org.jointheleague.modules;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import org.javacord.api.event.message.MessageCreateEvent;

public class SetProfilePic extends CustomMessageCreateListener {

	private static final String COMMAND = "!SetPfp";

	public SetProfilePic(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			String input = event.getMessageContent();
			URL url = event.getMessageAttachments().get(0).getUrl();
			
			System.err.println(url.toString());
			//System.out.println(event.getMessageContent().);
			try {
				event.getApi().updateAvatar(ImageIO.read(url));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(event.getChannel().getCreationTimestamp().getNano());
		}
	}

}
