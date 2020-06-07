package org.jointheleague.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class _HelpListener extends CustomMessageCreateListener{

	private static final String COMMAND = "!help";
	private List<HelpEmbed> helpEmbeds = new ArrayList<>();

	public _HelpListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			for(HelpEmbed helpEmbed : helpEmbeds) {
				event.getChannel().sendMessage(helpEmbed.getEmbed());
			}
			
		}
	}
	
	public void addHelpEmbed(HelpEmbed helpEmbed) {
		helpEmbeds.add(helpEmbed);
	}
	
	
}
