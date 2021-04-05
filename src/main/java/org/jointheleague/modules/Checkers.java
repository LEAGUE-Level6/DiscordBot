package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Checkers extends CustomMessageCreateListener {
	private final String CMD = "!checkers";
	private final String INP = "!helpCheckers";
	private final String CMDSUN = "!moveSun";
	private final String CMDMOON = "!moveMoon";
	private final String CMDMOONKING = "!moveMoonKing";


	// makes strings for emojis
	private final String MOONFACE = "<:moon_with_red:785324927465160704>";
	private final String KINGMOONFACE = "<:king_moon_with_red>";
	private final String KINGSUNFACE = "<:king_sun_with_red>";
	private final String SUNFACE = "<:sun_with_red:785320897199734825>";
	private final String REDSQUARE = ":red_square:";
	private final String BLACKSQUARE = ":black_large_square:";
	String boardTwo = "";

	public Checkers(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub

	}

	String[][] board = new String[8][8];
	String[] numRow = { ":zero:", ":one:", ":two:", ":three:", ":four:", ":five:", ":six:", ":seven:" };

	public void checkerboard() {

		boardTwo += ":owl:";

		for (int i = 0; i < numRow.length; i++) {
			boardTwo += numRow[i];
		}
		boardTwo += "\n";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (i < 3 && i % 2 == 0 && j % 2 != 0 || i == 1 && j % 2 == 0) {
					board[i][j] = MOONFACE;
				} else if (i >= 5 && i % 2 != 0 && j % 2 == 0 || i == 6 && j % 2 != 0) {
					board[i][j] = SUNFACE;
				} else if (i % 2 != 0 && j % 2 != 0 || i % 2 == 0 && j % 2 == 0) {
					board[i][j] = BLACKSQUARE;

				} else {
					board[i][j] = REDSQUARE;

				}
				if (j == 0) {
					boardTwo += numRow[i];
				}
				boardTwo += board[i][j];

			}
			boardTwo += "\n";
		}
	}

	public void displayBoard(MessageCreateEvent event) {
		boardTwo = "";
		boardTwo += ":owl:";

		for (int i = 0; i < numRow.length; i++) {
			boardTwo += numRow[i];

		}
		boardTwo += "\n";

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (j == 0) {
					boardTwo += numRow[i];
				}
				boardTwo += board[i][j];
			}
			boardTwo += "\n";
		}
		event.getChannel().sendMessage(boardTwo);
	}

	public void moveLeft(int x, int y, MessageCreateEvent event) {
		int newY = y - 1;
		int newX = x - 1;
		if (board[x][y].equals(SUNFACE) && board[newX][newY].equals(MOONFACE)
				&& board[newX - 1][newY - 1].equals(REDSQUARE)) {
			checkJumpLeftSun(x, y, event);
			displayBoard(event);

		} else {
			String holder = board[x][y];

			System.out.println(board[x][y]);
			System.out.println(board[newX][newY]);

			board[x][y] = board[newX][newY];

			board[newX][newY] = holder;
			displayBoard(event);
		}
	}

	public void moveRight(int x, int y, MessageCreateEvent event) {
		String holder = board[x][y];
		int newY = y + 1;
		int newX = x - 1;
		if (board[x][y].equals(SUNFACE) && board[newX][newY].equals(MOONFACE)
				&& board[newX - 1][newY + 1].equals(REDSQUARE)) {
			checkJumpRightSun(x, y, event);
			displayBoard(event);

		} else {
			System.out.println(board[x][y]);
			System.out.println(board[newX][newY]);
			board[x][y] = board[newX][newY];
			board[newX][newY] = holder;
			displayBoard(event);
		}
	}

	public void moveRightMoon(int x, int y, MessageCreateEvent event) {
		String holder = board[x][y];
		int newY = y - 1;
		int newX = x - 1;
		if (board[x][y].equals(MOONFACE) && board[newX][newY].equals(SUNFACE)
				&& board[newX - 1][newY - 1].equals(REDSQUARE)) {
			checkJumpRightMoon(x, y, event);
			displayBoard(event);
		} else {	
		System.out.println(board[x][y]);
		System.out.println(board[newX][newY]);
		board[x][y] = board[newX][newY];
		board[newX][newY] = holder;
		displayBoard(event);
		System.out.println(board[x][y]);
		System.out.println(board[newX][newY]);
		}
	}

	public void moveLeftMoon(int x, int y, MessageCreateEvent event) {
		String holder = board[x][y];
		int newX = x + 1;
		int newY = y - 1;
		if (board[x][y].equals(MOONFACE) && board[newX][newY].equals(SUNFACE)
				&& board[newX - 1][newY - 1].equals(REDSQUARE)) {
			checkJumpLeftMoon(x, y, event);
			displayBoard(event);
		} else {	
		System.out.println(newX);
		System.out.println(newY);
		board[x][y] = board[newX][newY];
		board[newX][newY] = holder;
		displayBoard(event);
		}
	}

	public void checkJumpLeftSun(int x, int y, MessageCreateEvent event) {
		int newY = y - 1;
		int newX = x + 1;
		// red square = sun
		board[newX + 1][newY - 1] = SUNFACE;
		// moon = red square
		board[newX][newY] = REDSQUARE;
		// sun = red square
		board[x][y] = REDSQUARE;

	}

	public void checkJumpRightSun(int x, int y, MessageCreateEvent event) {
		int newY = y + 1;
		int newX = x + 1;
		// red square = sun
		board[newX + 1][newY + 1] = SUNFACE;
		// moon = red square
		board[newX][newY] = REDSQUARE;
		// sun = red square
		board[x][y] = REDSQUARE;

	}

	public void checkJumpLeftMoon(int x, int y, MessageCreateEvent event) {
		int newY = y - 1;
		int newX = x + 1;
		// red square = sun
		board[newX + 1][newY - 1] = MOONFACE;
		// moon = red square
		board[newX][newY] = REDSQUARE;
		// sun = red square
		board[x][y] = REDSQUARE;

	}

	public void checkJumpRightMoon(int x, int y, MessageCreateEvent event) {
		int newY = y- 1;
		int newX = x- 1;
		// red square = sun
		board[newX - 1][newY - 1] = MOONFACE;
		// moon = red square
		board[newX][newY] = REDSQUARE;
		// sun = red square
		board[x][y] = REDSQUARE;
	 }

	// moon king function
	public void moonKing(int x, int y, MessageCreateEvent event) {
		if(board[x][7].equals(MOONFACE)) {
			board[x][y] = KINGMOONFACE;
		}

	}

	// sun king function
	public void sunKing(int x, int y, MessageCreateEvent event) {
		if(board[x][0].equals(SUNFACE)) {
			board[x][y] = KINGSUNFACE;
		}
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String input = event.getMessageContent();

		if (input.contains(CMD)) {
			event.getChannel()
					.sendMessage("To move a piece use !moveSun or !moveMoon following the coordinates and direction");
			checkerboard();
			event.getChannel().sendMessage(boardTwo);
		} else if (input.substring(0, CMDSUN.length()).equals(CMDSUN)
				|| input.substring(0, CMDMOON.length()).equals(CMDMOON)) {
			String[] parts = input.split(" ");
			String part1 = parts[0];
			String part2 = parts[1];
			// splitting x and y position
			String[] partsTwo = part2.split(",");
			String xPos = partsTwo[0];
			String yPos = partsTwo[1];
			String part3 = parts[2];

			event.getChannel().sendMessage(xPos + "  " + yPos + " " + part3);

			int xPos2 = Integer.parseInt(xPos);
			int yPos2 = Integer.parseInt(yPos);

			// call the move methods
			if (part3.contains("left") && part1.contains(CMDSUN)) {
				System.out.println("Working");
				moveLeft(yPos2, xPos2, event);
			}

			if (part3.contains("right") && part1.contains(CMDSUN)) {
				System.out.println("Working");
				moveRight(yPos2, xPos2, event);
			}

			// diff for moon side
			if (part3.contains("left") && part1.contains(CMDMOON)) {
				System.out.println("Working");
				moveLeftMoon(yPos2, xPos2, event);
			}

			// diff for moon side
			if (part3.contains("right") && part1.contains(CMDMOON)) {
				System.out.println("Working");
				moveRightMoon(yPos2, xPos2, event);

			}
			//king function
			if (part1.contains(CMDMOONKING)) {
				System.out.println("Working");
				moveRightMoon(yPos2, xPos2, event);
				moveLeftMoon(yPos2, xPos2, event);
			}
		

		}

	}

}