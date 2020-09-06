package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class PokemonTypeEffectiveness extends CustomMessageCreateListener{
	private static final String COMMAND = "!pokeType";
	public PokemonTypeEffectiveness(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Give me the type of a pokemon and I will tell you what type is effective against it.");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// only single type works
		String message = event.getMessageContent();
		if(message.contains(COMMAND)) {
			String type = message.substring(message.indexOf(COMMAND)+COMMAND.length()+1).toLowerCase();
		}
		
	}
}
