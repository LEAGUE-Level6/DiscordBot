package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class HungerGames extends CustomMessageCreateListener{
	private static final String COMMAND = "!HungerGames";
	public boolean gS = false;
	public int stage = 0;
	public ArrayList<String> names = new ArrayList<String>(); 
	public ArrayList<String> namesR = new ArrayList<String>();
	public ArrayList<String> team1 = new ArrayList<String>();
	public ArrayList<String> team2 = new ArrayList<String>();
	public ArrayList<String> team3 = new ArrayList<String>();
	
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
		if(m.equals("!HungerGames")) {
			gS = true;
			stage = 1;
			event.getChannel().sendMessage("Welcome to the Deathmatch. Enter 4-10 names to proceed:");
		}
		if(m.equals("!Start")) {
			System.out.println("START INITIALIZED");
		//Randomizing list of names
			boolean[] printed = new boolean[names.size()];
			for(int i = 0; i < names.size(); i++) {
				int rand = new Random().nextInt(names.size());
				if(!printed[rand]) {
					printed[rand] = true;
					namesR.add(names.get(rand));
				}
				else {
					i--;
				}        
		    }   
		//Printing randomized names list
			for(int i = 0; i < namesR.size(); i++) {
				event.getChannel().sendMessage(namesR.get(i));
			}
			while(!(namesR.isEmpty())){
				team1.add(namesR.get(0));
				namesR.remove(0);
				if(names.isEmpty()) {
					
				}
				team2.add(names.get(0));
				if(names.isEmpty()) {
					return;
				}
				namesR.remove(0);
				team3.add(names.get(0));
				if(names.isEmpty()) {
					return;
				}
				namesR.remove(0);
			}
			for(int i = 0; i < team1.size(); i++){
				event.getChannel().sendMessage(team1.get(i));
			}	
			for(int i = 0; i < team2.size(); i++){
				event.getChannel().sendMessage(team2.get(i));
			}
			for(int i = 0; i < team3.size(); i++){
				event.getChannel().sendMessage(team3.get(i));
			}
		}
		else if(!(m.equals("!Start")) && stage == 1) {
			event.getChannel().sendMessage(m+"has been added to the game.");
			
		}

		
	}
	void kill(int killer, int victim) {
		
	}
	void summon(int summoner) {
		
	}
}
