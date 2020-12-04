package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class HungerGames extends CustomMessageCreateListener{
	private static final String COMMAND = "!HungerGames";
	private boolean gS = false;
	private int stage = 0;
	String month;
	String object;
	ArrayList<String> names = new ArrayList<String>();
	
	 public HungerGames (String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + sign to recieve a prediction about your life!");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		
		String m = event.getMessageContent();
		if(event.getMessageAuthor().getName().equals("Ella's Bot")) {
		return;
		}
		if(m.equals("!HungerGames")&& !gS) {
			names = new ArrayList<String>();
			stage = 0;
			event.getChannel().sendMessage("Select your party. Enter 3 names. They can be names of users in the server or made up:");
			gS = true;
			stage = 1;
		}
		else if(m.equals("!Start")) {
			stage = 2;
			String namesList = "";
			for(int i = 0; i < names.size(); i++) {
				namesList+=names.get(i)+"\n";
			}
			event.getChannel().sendMessage("Game started. \n"+names.size()+"people are gathered at the Cornocopia. Your party:\n"+namesList);
			
		}
		else if(stage == 1 && gS) {
			names.add(m);
			event.getChannel().sendMessage(m+" was added to the party. Message *Start!* to begin.");
		}
		
	}
}
