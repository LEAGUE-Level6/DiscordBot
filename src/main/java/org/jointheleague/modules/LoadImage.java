package org.jointheleague.modules;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class LoadImage extends CustomMessageCreateListener{
	
	public LoadImage(String channelName) {
		super(channelName);
	}

	private static final String COMMAND = "!urlimg";

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().toLowerCase().trim().startsWith(COMMAND)) {
			if(event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "").equals("")) {
				event.getChannel().sendMessage("[!urlimg error] Please format the command correctly: !urlimg <url>");
			} else {
				Image i;
				try {
					i = ImageIO.read(new URL(event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "")));
					if(i == null) {
						event.getChannel().sendMessage("[!urlimg error] That url is not an image.");
					} else {
						MessageBuilder builder = new MessageBuilder();
						try {
							builder.addAttachment(new URL(event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "")));
							builder.send(event.getChannel());
						} catch (MalformedURLException e) {
							event.getChannel().sendMessage("[!urlimg error] That is not a valid url.");
						}
					}
				} catch (Exception ex) {
					event.getChannel().sendMessage("[!urlimg error] An error happened :(");
				}
			}
		}
	}
	
}
