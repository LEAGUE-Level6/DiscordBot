package org.jointheleague.modules;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class SimpleEmbedBuilder extends CustomMessageCreateListener {
	public static final String COMMAND = "-embedbuilder";

	public SimpleEmbedBuilder(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith(COMMAND)) {
			String str = event.getMessageContent();
			str.trim();
			TextChannel channel = event.getChannel();
			int start = str.indexOf(" ");
			if (str.substring(start + 1).equals("help")) {
				new MessageBuilder().setEmbed(new EmbedBuilder().setTitle("Embed Builder Help")
						.setDescription("Send your embed imformation in one message. \n"
								+ "`-embedbuilder <title>|<description>|<footer>` \n"
								+ "Leave any section blank if you do not want it.")
						.addField("Extras",
								"This bot also has all the normal LEAGUE bot commands: \n"
										+ "!math !date !comic !elmo \n" + "!fashion !flag !leet !fact\n"
										+ "Polling commands \n" + "Dad Jokes and Mom Bot")
						.setFooter("This is a help box. Generated at:").setTimestampToNow()).send(event.getChannel());
			} else {
				String title;
				String description;
				String footer;
				int first = str.indexOf("|");
				int second = str.indexOf("|", first + 1);
				if (first == -1 | second == -1) {
					new MessageBuilder().setEmbed(new EmbedBuilder().setTitle("Missing or Incomplete syntax")
							.setDescription("`-embedbuilder <title>|<description>|<footer>`\n"
									+ "`-embedbuilder help` for help")
							.setFooter("This is a help box. Generated at:").setTimestampToNow().setColor(Color.RED))
							.send(event.getChannel());
				} else {
					title = str.substring(start + 1, first);
					description = str.substring(first + 1, second);
					footer = str.substring(second + 1);
					new MessageBuilder()
							.setEmbed(new EmbedBuilder().setTitle(title).setDescription(description).setFooter(footer))
							.send(event.getChannel());
				}
			}
		}
	}
}
