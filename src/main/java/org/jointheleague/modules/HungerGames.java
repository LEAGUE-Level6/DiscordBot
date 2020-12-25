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
		else if(m.equals("!Start")) {
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
			for(int i = 0; i < namesR.size(); i++){
				if(!(namesR.isEmpty())) {
				team1.add(namesR.get(0));
				namesR.remove(0);
				}
				else {
					break;
				}
				if(!(namesR.isEmpty())) {
				team2.add(namesR.get(0));
				namesR.remove(0);
				}
				else {
					break;
				}
				if(!(namesR.isEmpty())) {
				team3.add(namesR.get(0));
				namesR.remove(0);
				}
				else { 
					break;
				}
			}
			for(int i = 0; i < team1.size(); i++){
				event.getChannel().sendMessage("Team 1: "+team1.get(i));
			}	
			for(int i = 0; i < team2.size(); i++){
				event.getChannel().sendMessage("Team 2: "+team2.get(i));
			}
			for(int i = 0; i < team3.size(); i++){
				event.getChannel().sendMessage("Team 3: "+team3.get(i));
			}
			stage = 2;
		}
		//Adding players to the game
		else if(stage == 1) {
			event.getChannel().sendMessage(m+"has been added to the game.");
			names.add(m);
		}
		else if(stage == 2) {
			
		}
		
	}
	void kill(int killer, int victim) {
		
	}
	void summon(int summoner) {
		
	}
}
