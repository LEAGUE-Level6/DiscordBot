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
	public ArrayList<String>[] stringArray;
	public ArrayList<ArrayList<String>> teams = new ArrayList<ArrayList<String>>();
	
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
			if(namesR.size()%3 == 0) {
				for(int i = 0; i < namesR.size()/3; i++){
					teams.add(new ArrayList<String>());
				}
			}
			else {
				for(int i = 0; i < (namesR.size()/3)+1; i++){
					teams.add(new ArrayList<String>());
				}
			}

			
			for(int i = 0; i < teams.size(); i++) {
				for(int j = 0; j < 3; j++) {
				if(namesR.isEmpty()) {
					break;
				}
				else {
				teams.get(i).add(namesR.get(0));
				namesR.remove(0);
				}
				}
			}
			
			System.out.println("Number of teams: "+teams.size());
			for(int i = 0; i < teams.size(); i++) {
				System.out.println("Team "+i+" size: "+teams.get(i).size());
			}	
			stage = 2;
			event.getChannel().sendMessage("Say 'Continue' to view round one");
		}
		//Adding players to the game
		else if(stage == 1) {
			event.getChannel().sendMessage(m+" has been added to the game.");
			names.add(m);
		}
		else if(stage == 2 && m.equals("Continue")) {
			event.getChannel().sendMessage(swap());
		}
	}
	
	public String swap(){
		int oldTeam = new Random().nextInt(teams.size());
		int newTeam = new Random().nextInt(teams.size());
		int member = new Random().nextInt(teams.get(oldTeam).size());
		String announcement;
		if(oldTeam == newTeam) {
			announcement = "There have been no changes of alignment this round.";
		}
		else {
			announcement = teams.get(oldTeam).get(member)+" has abandoned Team "+(oldTeam+1)+" and joined Team "+(newTeam+1);
		}
		teams.get(newTeam).add(teams.get(oldTeam).get(member));
		teams.get(oldTeam).remove(member);
		return announcement;
	}
	
	
}
