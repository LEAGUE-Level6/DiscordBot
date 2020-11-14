package org.jointheleague.modules;

import java.util.Random;
import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Minesweeper extends CustomMessageCreateListener {
	public static final String game = "!minesweeper";
	public static final String cheat = "!cheat";
	public static final String flag = "!flag";
	public static final String click = "!click";

	public Minesweeper(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(game, "Does stuff");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		boolean first = true;

		if (message.length() > game.length() && message.substring(0, game.length()).equals(game)) {

			if (message.substring(game.length() + 1, message.length()).equalsIgnoreCase("start")) {
				for (int i = 0; i < layout.length; i++) {
					for (int j = 0; j < layout[i].length; j++) {
						layout[i][j] = "`☐`";

					}
				}
				event.getChannel().sendMessage(update());

			} else if (message.substring(game.length() + 1, message.length()).equalsIgnoreCase("stop")) {
				System.exit(0);

			}
		} else if (message.length() > click.length() && message.substring(0, click.length()).equals(click)) {
			play(message.substring(game.length() + 1, message.length()), first, false);
			first = false;
			event.getChannel().sendMessage(update());

		} else if (message.length() > flag.length() && message.substring(0, flag.length()).equals(flag)) {
			play(message.substring(game.length() + 1, message.length()), first, true);
			first = false;
			event.getChannel().sendMessage(update());

		} else if (message.length() > cheat.length() && message.substring(0, cheat.length()).equals(cheat)) {
			event.getChannel().sendMessage(showMines());
			event.getChannel().sendMessage(update());
		}

	}

	public static final int length = 20;
	public static final int height = 20;
	public static final int numMines = (length * height) / 10;
	String[][] layout = new String[length][height];

	Random rand = new Random();
	Scanner scan = new Scanner(System.in);

	boolean[][] mines = new boolean[length][height];

	void genMines(int x, int y) {
		for (int i = 0; i < numMines; i++) {
			int mineX = rand.nextInt(20);
			int mineY = rand.nextInt(20);
			if (mineX > x - 1 && mineX < x + 1 && mineY > y - 1 && mineY < y + 1) {
				i--;

			} else {
				mines[mineX][mineY] = true;

			}
		}

	}

	int[][] numSurr = new int[length][height];

	void genSurrNum() {
		for (int x = 0; x < numSurr.length; x++) {
			for (int y = 0; y < numSurr[x].length; y++) {
				if (mines[x][y] == true) {
					numSurr[x][y] = -1;

				} else {
					numSurr[x][y] = checkSurround(mines, x, y);

				}
			}

		}

	}

	Integer checkSurround(boolean[][] arr, int x, int y) {
		int num = 0;
		if (x > 0 && y > 0 && arr[x - 1][y - 1]) {
			num++;
		}
		if (x > 0 && arr[x - 1][y]) {
			num++;
		}
		if (x > 0 && y < height - 1 && arr[x - 1][y + 1]) {
			num++;
		}
		if (y > 0 && arr[x][y - 1]) {
			num++;
		}
		if (y < height - 1 && arr[x][y + 1]) {
			num++;
		}
		if (x < length - 1 && y > 0 && arr[x + 1][y - 1]) {
			num++;
		}
		if (x < length - 1 && arr[x + 1][y]) {
			num++;
		}
		if (x < length - 1 && y < height - 1 && arr[x + 1][y + 1]) {
			num++;
		}

		return num;
	}

	boolean isLost = false;

	void checkBlock(int x, int y) {
		if (layout[x][y] != "`☐`" || layout[x][y] != "`⚐`") {

		} else if (layout[x][y].equals("`⚐`")) {
			layout[x][y] = "`☐`";

		} else if (mines[x][y]) {

			isLost = true;

		} else if (numSurr[x][y] == 0) {
			layout[x][y] = "` `";
			numSurr[x][y] = -1;
			if (x > 0 && y > 0) {
				checkBlock(x - 1, y - 1);
			}
			if (x > 0) {
				checkBlock(x - 1, y);
			}
			if (x > 0 && y < height - 1) {
				checkBlock(x - 1, y + 1);
			}
			if (y > 0) {
				checkBlock(x, y - 1);
			}
			if (y < height - 1) {
				checkBlock(x, y + 1);
			}
			if (x < length - 1 && y > 0) {
				checkBlock(x + 1, y - 1);
			}
			if (x < height - 1) {
				checkBlock(x + 1, y);
			}
			if (x < length - 1 && y < height - 1) {
				checkBlock(x + 1, y + 1);
			}

		} else {
			layout[x][y] = "`" + numSurr[x][y] + "`";

		}

	}

	String update() {

		String board = "";
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < length; x++) {
				board += layout[x][y];

			}
			board += "\n";
		}

		int num = 0;
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[i].length; j++) {
				if (layout[i][j].equals("`☐`") == false) {
					num++;

				}

			}
		}
		if (num == (numMines * 10)) {
			return "You Win!";

		}

		String lost = "";
		if (isLost) {

			lost += "You have lost! ";
			for (int i = 0; i < mines.length; i++) {
				for (int j = 0; j < mines[i].length; j++) {
					if (mines[i][j]) {
						lost += "(" + i + ", " + j + ") ";

					}
				}
			}
			return lost;
		}
		return board;

	}

	void play(String str, boolean first, boolean isFlag) {

		String num = "";
		int x = -1;
		int y = -1;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isWhitespace(str.charAt(i)) || str.charAt(i) == ',') {
				if (x == -1) {
					x = Integer.parseInt(num) - 1;
					num = "";
				} else {
					y = Integer.parseInt(num) - 1;

				}

			} else {
				num += str.charAt(i);

			}

		}
		y = Integer.parseInt(num) - 1;
		System.out.println("Move played at " + x + ", " + y);

		if (isFlag) {
			if (layout[x][y].equals("`⚐`")) {
				layout[x][y] = "`☐`";

			} else {
				layout[x][y] = "`⚐`";

			}
		} else {
			if (first) {
				genMines(x, y);
				genSurrNum();

			}
			checkBlock(x, y);
		}
	}

	String showMines() {
		String allMines = "";
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if (mines[i][j]) {
					allMines += "(" + i + ", " + j + ") ";

				}
			}
		}
		return allMines;
	}

}
