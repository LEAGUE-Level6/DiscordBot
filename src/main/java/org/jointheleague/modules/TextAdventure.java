package org.jointheleague.modules;

import java.awt.Event;

import org.javacord.api.event.message.MessageCreateEvent;

public class TextAdventure extends CustomMessageCreateListener {
		String[][] cells = new String[10][10];
		int r = 9;
		int c = 9;
		boolean playing = false;
		boolean materials = false;
		boolean needMaterials = false;
		boolean needLava=false;
		boolean lava = false;
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
			cells[3][5]="d6";
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
			cells[6][5]="d1";
			cells[6][7]="d2";
			cells[7][4]="d3";
			cells[7][7]="m";
			cells[8][1]="d4";
			cells[9][8]="d5";
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
				event.getChannel().sendMessage("Welcome to text adventure! \nYou wake up in a dark forest of a deserted island and don't know how to escape. You feel invisible walls to the south and east.\nType \"w\" to go west, \"n\" to go north, and etc.");
						
			}
			if(playing && a.equalsIgnoreCase("w")) {
				if(c!=0) {
					c--;
					currentCell(event);
				}
				else {
					event.getChannel().sendMessage("you explored too far and died!");
					event.getChannel().sendMessage("type !textadventure to play again!");
					playing=false;
					
				}
			}
			
			if(playing && a.equalsIgnoreCase("n")) {
				if(r!=0) {
					r--;
					currentCell(event);
				}
				else {
					event.getChannel().sendMessage("you explored too far and died!");
					event.getChannel().sendMessage("type !textadventure to play again!");
					playing=false;
					
				}
			}
			if(playing && a.equalsIgnoreCase("s")) {
				if(r!=9) {
					r++;
					currentCell(event);
				}
				else {
					event.getChannel().sendMessage("you explored too far and died!");
					event.getChannel().sendMessage("type !textadventure to play again!");
					playing=false;
					
				}
			}
			if(playing && a.equalsIgnoreCase("e")) {
				if(c!=9) {
					c++;
					currentCell(event);
				}
				else {
					event.getChannel().sendMessage("you explored too far and died!");
					event.getChannel().sendMessage("type !textadventure to play again!");
					playing=false;
					
				}
			}
			
			
		}
		public void currentCell(MessageCreateEvent e){
			if(cells[r][c].equals("o")) {
				e.getChannel().sendMessage("You wade in a shallow ocean. It seems to get deeper pretty close by and you can't swim");
				
			}
			if(cells[r][c].equals("s")) {
				e.getChannel().sendMessage("You walk in scorching sand that burns your feet. There might be water nearby. But why is the sand so hot? Its 75 degrees at most");
				
		}
			if(cells[r][c].equals("g")) {
				e.getChannel().sendMessage("You're in a grass field. There's something uneasy about it. You're on guard");
			}
			if(cells[r][c].equals("f")) {
				e.getChannel().sendMessage("You're in a deep, dark forest. If you didn't have a compass, you'd definetly get lost in here. There's no way to get around with just your senses");
			}
			if(cells[r][c].equals("d1")) {
				playing=false;
				e.getChannel().sendMessage("You accidentally graze your leg on a rare type of poison ivy. You are instantly thrown to the ground due to immense pain, and you die a few minutes later. Better luck next time!");
				e.getChannel().sendMessage("type !textadventure to play again!");
			}
			if(cells[r][c].equals("d2")) {
				playing=false;
				e.getChannel().sendMessage("Blinded by the darkness, you can't see the black cobra that sneaks towards you. It takes one leap and bites your neck before you know it, and you die of the venom in under an hour. Better luck next time!");
				e.getChannel().sendMessage("type !textadventure to play again!");
			}
			if(cells[r][c].equals("d3")) {
				playing=false;
				e.getChannel().sendMessage("As you walk through the forest, you catch your feet in what seems to be a small hole. however, as you trip and fall, you realize that it's a deep well. You fall in and scream for seconds, then minutes, then hours. It's a bottomless well, you conclude. You're stuck there forever. Better luck next time!");
				e.getChannel().sendMessage("type !textadventure to play again!");
			}
			if(cells[r][c].equals("d4")) {
				playing=false;
				e.getChannel().sendMessage("You've been walking for hours and you're starving. Luckily, you find some bright red berries on a nearby shrub. Instinctively and naively, you reach for the berries and devour them. Unlucky for you, they were highly poisonous. In just minutes you crumble to your stomach and vomit everything you have, but it's too late. The poison is in you and kills you in less than an hour. Better luck next time!");
				e.getChannel().sendMessage("type !textadventure to play again!");
			}
			if(cells[r][c].equals("d5")) {
				playing=false;
				e.getChannel().sendMessage("Blinded by the dark forest but confindent in finding your way out, you stumble into a cliff and fall to your death. Better luck next time!");
				e.getChannel().sendMessage("type !textadventure to play again!");
			}
			if(cells[r][c].equals("d6")) {
				playing=false;
				e.getChannel().sendMessage("In the distance of the grass field you're in, you see a massive feline creature. You quickly run into the forest south of you, but the feline is too fast, catches you, and eats you for breakfast. Better luck next time!");
				e.getChannel().sendMessage("type !textadventure to play again!");
			}
			if(cells[r][c].equals("h")) {
				e.getChannel().sendMessage("As you explore the forest, you find a house! It's very old and looks kind of haunted. Enter it?");
				e.getChannel().sendMessage("Typen 'yes' for yes and 'no' for no");
				if(e.getMessageContent().equalsIgnoreCase("yes")) {
					e.getChannel().sendMessage("You creak open the door. It's dark and scary, but you find some wood and building tools. You take them.");
					materials=true;
				}
				if(e.getMessageContent().equalsIgnoreCase("no")) {
					e.getChannel().sendMessage("You decide not to go in. Maybe it's for the better.");
				}
			}
			if(cells[r][c].equals("end") && !materials) {
				e.getChannel().sendMessage("You find a dock, some oars, and a bucket. It looks like you could escape the island if you had a boat. Somehow.");
			}
			if(cells[r][c].equals("end") && materials && !lava) {
				e.getChannel().sendMessage("You have materials for a boat");
			}
			if(cells[r][c].equals("lo") && !needLava) {
				playing=false;
			e.getChannel().sendMessage("You trip and fall into a small oasis of lava next to the sand. No wonder it was so hot. Better luck next time!");
			e.getChannel().sendMessage("type !textadventure to play again!");
			}
		}//different outcome if: house then dock vs. dock then house then dock, hence "need materials". need lava true when realized boat has hole, then go to lava and take some, go back to dock and fix boat, win
}
