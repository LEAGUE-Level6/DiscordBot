package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class TextAdventure extends CustomMessageCreateListener {
		String[][] cells = new String[10][10];
		int r = 9;
		int c = 9;
		boolean playing = false;
		public TextAdventure(String channelName) {
			super(channelName);
		}
		public void setup() {
			int r = 9;
			int c = 9;
			for (int i = 0; i < 10; i++) {
				cells[0][i]="o";
			}
			for (int i = 0; i < 10; i++) {
				cells[6][i]="f";
				cells[7][i]="f";
				cells[8][i]="f";
				cells[9][i]="f";
			}
			cells[1][0]="o";
			cells[2][0]="o";
			cells[1][2]="o";
			cells[1][3]="o";
			cells[1][0]="o";
			cells[1][1]="s";
			cells[2][1]="s";
			cells[2][2]="s";
			cells[1][4]="s";
			cells[1][5]="s";
			cells[1][7]="s";
			cells[1][8]="s";
			cells[1][9]="s";
			cells[2][3]="s";
			cells[2][6]="s";
			cells[3][0]="s";
			cells[3][1]="s";
			cells[4][0]="s";
			cells[5][0]="s";
			cells[6][0]="s";
			cells[7][0]="s";
			cells[2][4]="g";
			cells[2][4]="g";
			cells[2][5]="g";
			cells[2][8]="g";
			cells[2][9]="g";
			cells[2][7]="lo";
			cells[3][2]="g";
			cells[3][3]="g";
			cells[3][4]="g";
			cells[3][5]="d";
			cells[3][6]="g";
			cells[3][7]="g";
			cells[3][8]="g";
			cells[3][9]="g";
			cells[4][1]="g";
			cells[4][2]="g";
			cells[5][1]="g";
			cells[5][2]="f";
			for (int i = 3; i <10; i++) {
				cells[4][i]="f";
				cells[5][i]="f";
			}
			cells[6][3]="h";
			cells[6][5]="d";
			cells[6][7]="d";
			cells[7][4]="d";
			cells[7][7]="m";
			cells[8][1]="d";
			cells[9][8]="d";
			cells[9][9]="start";
			cells[1][6]="end";
			//house gives materials for boat
			
		}
		
		@Override
		public void handle(MessageCreateEvent event) {
			// TODO Auto-generated method stub
			String a= event.getMessageContent();
		
			if(a.equalsIgnoreCase("!textadventure")) {
				setup();
				playing=true;
				event.getChannel().sendMessage("Welcome to text adventure! \nYou wake up in a deserted island and don't know how to escape. You feel invisible walls to the south and east.\nType \"w\" to go west, \"n\" to go north, and etc.");
						
			}
			if(playing && a.equalsIgnoreCase("w")) {
				if(c!=0) {
					c--;
				}
				else {
					event.getChannel().sendMessage("you explored too far and died!");
					playing=false;
					
				}
			}
			
			if(playing && a.equalsIgnoreCase("n")) {
				if(r!=0) {
					r--;
				}
				else {
					event.getChannel().sendMessage("you explored too far and died!");
					playing=false;
					
				}
			}
		}
}
