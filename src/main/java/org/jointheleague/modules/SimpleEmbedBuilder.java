package org.jointheleague.modules;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class SimpleEmbedBuilder extends CustomMessageCreateListener{
	public static final String COMMAND = "-embedbuilder";
	public SimpleEmbedBuilder(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().startsWith(COMMAND)) {
			System.out.println("something");
			String str = event.getMessageContent();
			str.trim();
			TextChannel channel = event.getChannel();
			int start = str.indexOf(" ");
			if(str.substring(start+1).equals("help")) {
				System.out.println("something else");
				new MessageBuilder()
					.setEmbed(new EmbedBuilder()
						.setTitle("How to: Simple Embed Builder")
						.setDescription("Send your embed imformation in one message. \n"
								+ "-embedbuilder <title>|<description>|<footer> \n"
								+ "Leave any section blank if you do not want it.")
						.setFooter("This is a help box."))
					.send(event.getChannel());
						//.)
			}
			String title;
			String description;
			String footer;
			int first = str.indexOf("|");
			int second = str.indexOf("|",first+1);
			title=str.substring(start+1, first);
			description=str.substring(first+1, second);
			footer=str.substring(second+1);
			new MessageBuilder()
				.setEmbed(new EmbedBuilder()
					.setTitle(title)
					.setDescription(description)
					.setFooter(footer))
				.send(event.getChannel());
		}
	}
}
