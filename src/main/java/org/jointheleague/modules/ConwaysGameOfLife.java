
package org.jointheleague.modules;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class ConwaysGameOfLife extends CustomMessageCreateListener {
	private static final String COMMAND = "!conway";
	String message;
	boolean live = false;
	int generations = 0;
	int count = 0;
	int X = 11;
	int Y = 11;
	int[][] cells = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	public ConwaysGameOfLife(String channelName) {
		super(channelName);
	}

	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
			sendGrid(event);
			event.getChannel()
					.sendMessage("**Type !conway run (# of future generations) to run the simulation and !conway "
							+ "(letter row)(number collum) to change the cell in that coordnate**");
		} else if (event.getMessageContent().contains(COMMAND + " run")) {
			if (event.getMessageContent().length() == 14 || event.getMessageContent().length() == 13) {
				try {
					generations = Integer.parseInt(event.getMessageContent().substring(12));
				} catch (NumberFormatException e) {
					event.getChannel()
							.sendMessage("**Please insert a desired number of future generations, ex. !conway run 8**");
				}
			}
			live = true;
			for (count = 0; count < generations; count++) {
				sendGrid(event);
			}
			live = false;
			if (event.getMessageAuthor().isBotUser() && event.getMessageContent().isEmpty()) {
			}
		} else if (event.getMessageContent().contains(COMMAND) && event.getMessageContent().length() == 10) {
			message = event.getMessageContent().substring(8);
			change(event, message);
		} 
	}

	public void change(MessageCreateEvent event, String coordinate) {
		String letter = coordinate.substring(0, 1);
		int x = 0;
		int y = 0;
		try {
			x = Integer.parseInt(coordinate.substring(1));
		} catch (NumberFormatException e) {
		}
		if (letter.equalsIgnoreCase("a")) {
			y = 1;
		} else if (letter.equalsIgnoreCase("b")) {
			y = 2;
		} else if (letter.equalsIgnoreCase("c")) {
			y = 3;
		} else if (letter.equalsIgnoreCase("d")) {
			y = 4;
		} else if (letter.equalsIgnoreCase("e")) {
			y = 5;
		} else if (letter.equalsIgnoreCase("f")) {
			y = 6;
		} else if (letter.equalsIgnoreCase("g")) {
			y = 7;
		} else if (letter.equalsIgnoreCase("h")) {
			y = 8;
		} else if (letter.equalsIgnoreCase("i")) {
			y = 9;
		}
		if (x > 0 && y > 0) {
			if (cells[x][y] == 0) {
				cells[x][y] = 1;
			} else {
				cells[x][y] = 0;
			}
			sendGrid(event);
		} else {
			event.getChannel().sendMessage("**Please insert a valid coordinate to change, ex. !conway grid e6**");
		}
	}

	public void sendGrid(MessageCreateEvent event) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Conway's Game of Life");
		embed.setDescription(getGrid(event));
		embed.setDescription(getGrid(event));
		event.getChannel().sendMessage(embed);
	}

	public String getGrid(MessageCreateEvent event) {
		String grid = "**__A--B--C--D--E--F--G--H--I___**\n";
		for (int i = 1; i < X - 1; i++) {
			for (int j = 1; j < Y - 1; j++) {
				if (cells[i][j] == 0) {
					grid += ":black_medium_square:";
				} else {
					grid += ":white_medium_square:";
				}
			}
			grid += "**| " + (i) + "**\n";
		}
		if (live) {
			nextGeneration(event, cells, X, Y);
		}
		return grid;
	}

	void nextGeneration(MessageCreateEvent event, int grid[][], int X, int Y) {
		int[][] future = new int[X][Y];
		for (int l = 1; l < X - 1; l++) {
			for (int m = 1; m < Y - 1; m++) {
				int aliveNeighbours = 0;
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						aliveNeighbours += grid[l + i][m + j];
					}
				}
				aliveNeighbours -= grid[l][m];
				if ((grid[l][m] == 1) && (aliveNeighbours < 2)) {
					future[l][m] = 0;
				} else if ((grid[l][m] == 1) && (aliveNeighbours > 3)) {
					future[l][m] = 0;
				} else if ((grid[l][m] == 0) && (aliveNeighbours == 3)) {
					future[l][m] = 1;
				} else {
					future[l][m] = grid[l][m];
				}
			}
		}
		cells = future;
	}
}