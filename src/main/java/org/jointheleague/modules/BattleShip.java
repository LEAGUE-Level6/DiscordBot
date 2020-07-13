package org.jointheleague.modules;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

public class BattleShip extends CustomMessageCreateListener {
	String[][] grid = new String[10][10];

	private static final String startCommand = "!battleship";

	public BattleShip(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {

		// TODO Auto-generated method stub
		if (event.getMessageContent().equals(startCommand)) {
			for (int row = 0; row < grid.length; row++) {
				for (int col = 0; col < grid[0].length; col++) {
					grid[row][col] = "🌊\t";
				}
			}
			event.getChannel().sendMessage(display(grid));

		}

	}

	public static String display(String[][] grid) {
		String matrix = "\n\n";
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				matrix += grid[row][col] + "\t";
			}
			matrix += "\n";
		}
		matrix += "-----------------------------------------------------------------------------------\n";
		matrix += "0\t\t\t1\t\t\t2\t\t\t3\t\t\t4\t\t\t5\t\t\t6\t\t\t7\t\t\t8\t\t\t9\n\n  ";

		return matrix;
	}

}
