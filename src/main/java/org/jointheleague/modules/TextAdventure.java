package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class TextAdventure extends CustomMessageCreateListener {
		String[][] cells = new String[10][10];
		public TextAdventure(String channelName) {
			super(channelName);
		}
		@Override
		public void handle(MessageCreateEvent event) {
			// TODO Auto-generated method stub
			for (int i = 0; i < 10; i++) {
				cells[0][i]="o";
			}
			for (int i = 0; i < 10; i++) {
				cells[8][i]="f";
				cells[8][i]="f";
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
			//cells[1][1]="g";
			//house gives materials for boat
			
			
		}
}
