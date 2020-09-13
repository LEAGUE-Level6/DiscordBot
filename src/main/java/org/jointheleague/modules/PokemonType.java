package org.jointheleague.modules;

import java.util.HashMap;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class PokemonType extends CustomMessageCreateListener{
	private static final String COMMAND = "!pokeType";
	public PokemonType(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Give me the type of a pokemon and I will tell you what types are effective against it.");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// only single type works
		String message = event.getMessageContent();
		HashMap<String, String[]> types = new HashMap<String,String[]>();
		String[] normal = {"Fighting"};
		types.put("normal",normal);
		String[] fire = {"Fire","Ground","Rock"};
		types.put("fire", fire);
		String[] water = {"Electric","Grass"};
		types.put("water",water);
		String[] electric = {"Ground"};
		types.put("electric", electric);
		String[] grass = {"Fire","Ice","Poison","Flying","Bug"};
		types.put("grass", grass);
		String[] ice = {"Fire","Fighting","Rock","Steel"};
		types.put("ice", ice);
		String[] fighting = {"Flying","Psychic","Fairy"};
		types.put("fighting", fighting);
		String[] poison = {"Ground","Psychic"};
		types.put("poison", poison);
		String[] ground = {"Water","Grass","Ice"};
		types.put("ground", ground);
		String[] flying = {"Electric","Ice","Rock"};
		types.put("flying", flying);
		String[] psychic = {"Bug","Ghost","Dark"};
		types.put("psychic", psychic);
		String[] bug = {"Fire","Flying","Rock"};
		types.put("bug", bug);
		String[] rock = {"Water","Grass","Fighting","Ground","Steel"};
		types.put("rock", rock);
		String[] ghost = {"Ghost","Dark"};
		types.put("ghost", ghost);
		String[] dragon = {"Ice","Dragon","Fairy"};
		types.put("dragon", dragon);
		String[] dark = {"Ice","Dragon","Fairy"};
		types.put("dark", dark);
		String[] steel = {"Fire","Fighting","Ground"};
		types.put("steel", steel);
		String[] fairy = {"Poison","Steel"};
		types.put("fairy", fairy);
		
		
		if(message.contains(COMMAND)) {
			String type = message.substring(message.indexOf(COMMAND)+COMMAND.length()+1).toLowerCase();
			String finalMessage = "";
			String[] currArray = types.get(type);
			for(int i=0;i<currArray.length-1;i++) {
				finalMessage+=currArray[i]+", ";
			}
			finalMessage+=currArray[currArray.length-1];
			
			
			event.getChannel().sendMessage(finalMessage);
		
		}
		
	}
}
