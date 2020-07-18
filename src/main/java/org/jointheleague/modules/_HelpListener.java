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

			//legacy features
			event.getChannel().sendMessage(new HelpEmbed("Additional Features" , "" +
					"!dadon - turns on the dad mode"
					+ "\n !dadoff - turns off the dad mode"
					+ "\n !solvequad - solves quadratic equation"
					+ "\n !addrole - assigns roles to those below you in the role hierarchy"
					+ "\n !coinflip - flips a coin"
					+ "\n !kick - kicks those who can be kicked below you"
					+ "\n !nick - works with the manipulation or alteration of nicknames"
					+ "\n !ping - shows your current ping"
					+ "\n !playrps - starts a game of Rock Paper Scissors against the bot").getEmbed());
			
		}
	}
	
	public void addHelpEmbed(HelpEmbed helpEmbed) {
		helpEmbeds.add(helpEmbed);
	}
	
	
}
