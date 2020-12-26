package org.jointheleague.modules;

import java.util.Random;
import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class Minesweeper extends CustomMessageCreateListener {
	public static final String game = "-minesweeper";
	public static final String cheat = "-cheat";
	public static final String flag = "-flag";
	public static final String click = "-click";
	public static final String help = "-help";

	public Minesweeper(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(help, "Starts a Minesweeper game! Type -help for instructions.");
		System.out.println("\nRunning...");
		Thread c = new Thread(() -> {
			console();
		});
		c.start();
	}

	boolean first = true;
	boolean isStarted = false;
	MessageCreateEvent lastCom;

	@Override
	public void handle(MessageCreateEvent event) {
		String message = event.getMessageContent();

		if (message.length() > game.length() && message.substring(0, game.length()).equals(game)) {

			if (message.substring(game.length() + 1, message.length()).equalsIgnoreCase("start")) {

				System.out.println("Message detected: '" + message + "'. Started Minesweeper");
				for (int i = 0; i < layout.length; i++) {
					for (int j = 0; j < layout[i].length; j++) {
						layout[i][j] = "☐";

					}
				}
				event.getChannel().sendMessage(update());

				isStarted = true;

			} else if (message.substring(game.length() + 1, message.length()).equalsIgnoreCase("stop")) {

				System.out.println("Message detected: '" + message + "'. Stopped Minesweeper");
				isStarted = false;

			}

		} else if (message.length() > click.length() && message.substring(0, click.length()).equals(click)
				&& isStarted) {

			System.out.println("Message detected: '" + message + "'. Performed Click");

			play(message.substring(click.length(), message.length()), first, false);
			first = false;

			event.getChannel().sendMessage(update());

		} else if (message.length() > flag.length() && message.substring(0, flag.length()).equals(flag) && isStarted) {

			System.out.println("Message detected: '" + message + "'. Placed Flag");

			play(message.substring(flag.length(), message.length()), first, true);

			event.getChannel().sendMessage(update());

		} else if (message.length() == cheat.length() && message.substring(0, cheat.length()).equals(cheat)
				&& isStarted) {

			System.out.println("Message detected: '" + message + "'. Showing Bombs");

			showMines();

			event.getChannel().sendMessage(update());

		} else if (message.length() == help.length() && message.substring(0, help.length()).equals(help)) {

			System.out.println("Message detected: '" + message + "'. Displaying help message");

			event.getChannel().sendMessage("Commands for Minesweeper game: \n\n" + "-minesweeper start\n"
					+ "Starts the game of Minesweeper. It should give you an empty board(All other commands except -help will not run before this).\n\n"
					+ "-minesweeper stop\n" + "Stops the game of Minesweeper\n\n" + "-click x y\n"
					+ "Peforms a click on the specified coordinate(Ex. -click 1 2)\n\n" + "-flag x y\n"
					+ "Places a flag on the specified coordinate(Ex. -flag 1 2)\n\n" + "-cheat\n"
					+ "Places a flag on all mines\n\n" + "-help\n" + "Shows this message\n\n"
					+ "You now know all the commands to play this game. Good Luck!");

		} else {

			System.out.println("Message Detected: '" + message + "'; Message length equals " + message.length()
					+ "; No action performed");
			System.out.println("isStarted is equal to " + isStarted);

		}

		if (message.charAt(0) == '`') {
			if (lastCom != null) {
				lastCom.deleteMessage();

			}
			lastCom = event;
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

		int m = 0;
		System.out.println("Generating " + numMines + " mines.");
		for (int i = 0; i < numMines; i++) {
			int mineX = rand.nextInt(length);
			int mineY = rand.nextInt(height);
			if ((mineX > x - 1 && mineX < x + 1 && mineY > y - 1 && mineY < y + 1) || mines[mineX][mineY]) {
				i--;

			} else {
				mines[mineX][mineY] = true;
				m++;
			}
		}
		System.out.println("Generated " + m + " mines.");

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

		if (layout[x][y].equals("☐") || layout[x][y].equals("⚐")) {
			System.out.println("Performed click |" + layout[x][y] + "|");

			if (layout[x][y].equals("⚐")) {
				System.out.println("Removed flag at (" + x + ", " + y + ")");
				layout[x][y] = "☐";

			} else if (mines[x][y]) {
				System.out.println("Discovered mine at (" + x + ", " + y + ")");
				isLost = true;

			} else if (numSurr[x][y] == 0) {
				System.out.println("Empty space at (" + x + ", " + y + ")");
				layout[x][y] = " ";
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
				System.out.println("Showing Number of Surrounding Mines at (" + x + ", " + y + ")");
				layout[x][y] = "" + numSurr[x][y] + "";

			}
		} else {
			System.out.println("Did not perform click; |" + layout[x][y] + "|");
		}

	}

	String update() {

		String board = "`\n";
		for (int y = height - 1; y >= -1; y--) {
			if (height > 9 && y < 9) {
				board += " ";
			}
			board += y + 1 + "  ";
			for (int x = 0; x < length; x++) {

				if (y == -1) {
					board += x + 1 + " ";
					if (length > 9 && x < 9) {
						board += " ";
					}

				} else {
					board += layout[x][y] + "  ";
				}
			}
			board += "\n";
		}
		board += "`";

//		System.out.println("----------------True Layout-----------------");
//		for (int y = height - 1; y >= 0; y--) {
//			for (int x = 0; x < length; x++) {
//				System.out.print(layout[x][y]);
//
//			}
//			System.out.println();
//		}

		if (isLost) {

			String lost = "Oops! That was a mine. GAME OVER\nshowing all mines";
			System.out.println("Detected loss; Showing Mines");
			for (int i = 0; i < mines.length; i++) {
				for (int j = 0; j < mines[i].length; j++) {
					if (mines[i][j] == false && (layout[i][j].equals("☐") || layout[i][j].equals("⚐"))) {
						checkBlock(i, j);
						if (layout[i][j].equals("⚐")) {
							checkBlock(i, j);
						}
					}
				}
			}
			board = "`\n";
			for (int y = height - 1; y >= -1; y--) {
				if (height > 9 && y < 9) {
					board += " ";
				}
				board += y + 1 + "  ";
				for (int x = 0; x < length; x++) {

					if (y == -1) {
						board += x + 1 + " ";
						if (length > 9 && x < 9) {
							board += " ";
						}

					} else {
						board += layout[x][y] + "  ";
					}
				}
				board += "\n";
			}
			board += "`";
			System.out.println("Game lost!");
			isStarted = false;
			return board + lost;
		}

		int num = 0;
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[i].length; j++) {
				if (layout[i][j].equals("☐") || layout[i][j].equals("⚐")) {
					num++;

				}

			}
		}
		System.out.println("Nums is equal to " + num + "; numMines is equal to " + numMines);
		if (num == numMines) {

			System.out.println("Game won!");
			isStarted = false;
			return board + "Congratulations You Win!";

		}

		return board;

	}

	void play(String str, boolean first, boolean isFlag) {

		String num = "";
		int x = -1;
		int y = -1;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isWhitespace(str.charAt(i)) || str.charAt(i) == ',') {
				if (x == -1 && num.isBlank() == false) {
					x = Integer.parseInt(num) - 1;

				} else if (num.isBlank() == false) {
					y = Integer.parseInt(num) - 1;
				}
				num = "";
			} else {
				num += str.charAt(i);

			}

		}
		y = Integer.parseInt(num) - 1;

		System.out.println("Move played at (" + x + ", " + y + ") with parameter '" + str + "'; First move is " + first
				+ "; Placing flag is " + isFlag);

		if (isFlag) {
			if (layout[x][y].equals("⚐")) {
				layout[x][y] = "☐";

			} else {
				layout[x][y] = "⚐";

			}
		} else {
			if (first) {
				genMines(x, y);
				genSurrNum();

			}
			checkBlock(x, y);
		}
	}

	void showMines() {

		int m = 0;
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if (mines[i][j]) {
					layout[i][j] = "⚐";
					m++;
				}
			}
		}
		System.out.println("Set number of mines is " + numMines + "; Number of found mines is " + m);
	}

	void console() {
		Scanner scan = new Scanner(System.in);
		String command = scan.nextLine();

		if (command.length() > game.length() && command.substring(0, game.length()).equals(game)) {

			if (command.substring(game.length() + 1, command.length()).equalsIgnoreCase("start")) {

				System.out.println("Message detected: '" + command + "'. Started Minesweeper");
				for (int i = 0; i < layout.length; i++) {
					for (int j = 0; j < layout[i].length; j++) {
						layout[i][j] = "☐";

					}
				}
				System.out.println(update());

				isStarted = true;

			} else if (command.substring(game.length() + 1, command.length()).equalsIgnoreCase("stop")) {

				System.out.println("Message detected: '" + command + "'. Stopped Minesweeper");
				isStarted = false;

			}

		} else if (command.length() > click.length() && command.substring(0, click.length()).equals(click)
				&& isStarted) {

			System.out.println("Message detected: '" + command + "'. Performed Click");

			play(command.substring(click.length(), command.length()), first, false);
			first = false;

			System.out.println(update());

		} else if (command.length() > flag.length() && command.substring(0, flag.length()).equals(flag) && isStarted) {

			System.out.println("Message detected: '" + command + "'. Placed Flag");

			play(command.substring(flag.length(), command.length()), first, true);

			System.out.println(update());

		} else if (command.length() == cheat.length() && command.substring(0, cheat.length()).equals(cheat)
				&& isStarted) {

			System.out.println("Message detected: '" + command + "'. Showing Bombs");

			showMines();

			System.out.println(update());

		} else if (command.length() == help.length() && command.substring(0, help.length()).equals(help)) {

			System.out.println("Message detected: '" + command + "'. Displaying help message");

			System.out.println("Commands for Minesweeper game: \n\n" + "-minesweeper start\n"
					+ "Starts the game of Minesweeper. It should give you an empty board(All other commands except -help will not run before this).\n\n"
					+ "-minesweeper stop\n" + "Stops the game of Minesweeper\n\n" + "-click x y\n"
					+ "Peforms a click on the specified coordinate(Ex. -click 1 2)\n\n" + "-flag x y\n"
					+ "Places a flag on the specified coordinate(Ex. -flag 1 2)\n\n" + "-cheat\n"
					+ "Places a flag on all mines\n\n" + "-help\n" + "Shows this message\n\n"
					+ "You now know all the commands to play this game. Good Luck!");

		} else {

			System.out.println("Message Detected: '" + command + "'; Message length equals " + command.length()
					+ "; No action performed");
			System.out.println("isStarted is equal to " + isStarted);

		}
		console();
	}
}
