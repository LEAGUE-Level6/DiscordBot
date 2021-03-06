package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class DeathMatch extends CustomMessageCreateListener{
	private static final String COMMAND = "!DeathMatch";
	public boolean gS = false;
	public int stage = 0;
	public ArrayList<String> names = new ArrayList<String>(); 
	public ArrayList<String> namesR = new ArrayList<String>();
	public ArrayList<String>[] stringArray;
	public ArrayList<ArrayList<String>> teams = new ArrayList<ArrayList<String>>();
	public ArrayList<String> teamNames = new ArrayList<String>();
	public ArrayList<String> deceased = new ArrayList<String>();
	
	public DeathMatch (String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + sign to recieve a prediction about your life!");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		
		String m = event.getMessageContent();
		if(event.getMessageAuthor().getName().equals("Deathmatch Bot")) {
		return;
		}
		if(m.equals("!DeathMatch")) {
			gS = true;
			stage = 1;
			event.getChannel().sendMessage("Welcome to the Deathmatch. Enter 6-15 names to proceed:");
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
			//String[] namesBank = {"Kinkou", "OotS", "Cultists", "MorningStar Crew", "Death Eaters","Void Monsters"};
			String[] namesBank = {"District 1", "District 2", "District 3", "District 4", "District 5","District 6"};
			for(int i = 0; i < teams.size(); i++) {
				teamNames.add(namesBank[i]);
			}
			for(int i = 0; i < teams.size(); i++) {
				System.out.println(teamNames.get(i)+" size: "+teams.get(i).size());
			}	
			stage = 2;
			//printing all teams
			event.getChannel().sendMessage("Teams:");
			for(int i = 0; i < teams.size(); i++) {
				event.getChannel().sendMessage("**"+teamNames.get(i)+":**");
				for(int x = 0; x < teams.get(i).size(); x++) {
					event.getChannel().sendMessage("*"+teams.get(i).get(x)+"*");
				}
				event.getChannel().sendMessage(" ");
			}
			event.getChannel().sendMessage("Say '!N' to view round one. If you would like to view the current list of teams and alligiences, type '!Teams' at any time.");
			
			
		}
		//Adding players to the game
		else if(stage == 1) {
			event.getChannel().sendMessage(m+" has been added to the game. Say '!Start' to begin.");
			names.add(m);
		}
		else if(stage == 2 && m.equals("!N")) {
			event.getChannel().sendMessage(dissolveTeams());
			for(int i = 0; i < (teams.size()/2)+1; i++) {
				int action = new Random().nextInt(10);
				if(action<3) {
					event.getChannel().sendMessage(swap());
				}
				else if(action>=3 && action<5) {
					event.getChannel().sendMessage(revive());
				}
				else {
					event.getChannel().sendMessage(death());
				}
				event.getChannel().sendMessage(dissolveTeams());
				
			}
			
		}
		else if(m.equals("!Teams")) {
			//printing all teams
			event.getChannel().sendMessage("Teams:");
			for(int i = 0; i < teams.size(); i++) {
				event.getChannel().sendMessage("**"+teamNames.get(i)+":**");
				for(int x = 0; x < teams.get(i).size(); x++) {
					event.getChannel().sendMessage("*"+teams.get(i).get(x)+"*");
				}
				event.getChannel().sendMessage(" ");
			}
		}
		
		
		
		
	}
	
	public String swap(){
		dissolveTeams();
		int oldTeam = new Random().nextInt(teams.size());
		int newTeam = new Random().nextInt(teams.size());
		int member = new Random().nextInt(teams.get(oldTeam).size());
		String announcement;
		if(oldTeam == newTeam) {
			announcement = "";
		}
		else {
			announcement = teams.get(oldTeam).get(member)+" has abandoned "+teamNames.get(oldTeam)+ " and joined "+teamNames.get(newTeam);
		}
		teams.get(newTeam).add(teams.get(oldTeam).get(member));
		teams.get(oldTeam).remove(member);
		return announcement;
		
	}
	
	public String death(){
		dissolveTeams();
		String killMethod;
		int killerTeam = new Random().nextInt(teams.size());
		int victimTeam = new Random().nextInt(teams.size());
		int victim = new Random().nextInt(teams.get(victimTeam).size());
		int killer = new Random().nextInt(teams.get(killerTeam).size());
		if(teams.get(victimTeam).get(victim) != teams.get(killerTeam).get(killer)) {
		String[] strings = {" was killed by an arrow through the head from ", " was stabbed in the heart by ", " was killed by a trap set by "};
		killMethod = teams.get(victimTeam).get(victim)+ strings[new Random().nextInt(strings.length)]+ teams.get(killerTeam).get(killer);
		}
		else {
			return "";
		}
		String announcement = killMethod;
		deceased.add(teams.get(victimTeam).get(victim));
		teams.get(victimTeam).remove(victim);
		
		return announcement;
	}
	public String revive() {
		if(!deceased.isEmpty()) {
		int rebirthed = new Random().nextInt(deceased.size());
		int tTeam = new Random().nextInt(teams.size()); 
		teams.get(tTeam).add(deceased.get(rebirthed));
		String announcement = deceased.get(rebirthed) + " has been reborn and pledges alligience to "+teamNames.get(tTeam);
		deceased.remove(rebirthed);
		return announcement;
		}
		else {
			return null;
		}
	}
	
	
	public String dissolveTeams() {
		int dissolvedTeam;
		String winnerNames="\n";
		if(teams.size()==1) {
			for(int i =0; i < teams.get(0).size(); i++) {
				winnerNames+=teams.get(0).get(i)+"\n";
			}
			String winners = "**"+teamNames.get(0)+"** wins the Deathmatch! Congratulations to: "+winnerNames+"\nThis game has ended. Type '!DeathMatch' to play again!";
			stage = 0;
			return winners;
		}
		for(int i = 0; i < teams.size(); i++) {
			if(teams.get(i).isEmpty()) {
				dissolvedTeam = i;
				String announcement = "*"+teamNames.get(dissolvedTeam)+" has been dissolved, as no members remain.*";
				teams.remove(i);
				teamNames.remove(i);
				return announcement;
			}
				
		}
		return null;		
	}
	public boolean gameOver() {
		if(teams.size()==1) {
			return true;
		}
		return false;
	}
	
	
}
