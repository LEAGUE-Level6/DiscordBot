package org.jointheleague.modules;

import org.javacord.api.event.message.CachedMessagePinEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Gomoku extends CustomMessageCreateListener {

	private static final String COMMAND = "!gomoku";
	String[][] gameboard = new String[15][15];
	boolean inProgress = false;
	int playerNum = 0;

	public Gomoku(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Starts a game of gomoku. !gomokuRules shows the rules. To play, put in a coordinate (e.g. !gm0,0) to place a marker there. Player 1 is orange. Player 2 is blue.");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {

			String command = event.getMessageContent().replaceAll(" ", "").replace("!gomoku", "");
			String board = "";

			if (inProgress == false) {
				for (int i = 0; i < gameboard.length; i++) {
					for (int j = 0; j < gameboard[i].length; j++) {
						gameboard[i][j] = ":black_medium_square:" + " ";
					}
				}
				inProgress = true;
			}

			System.out.println(board);

			if (command.equals("")) {
				for (int i = 0; i < gameboard.length; i++) {
					String row = "";
					for (int j = 0; j < gameboard[i].length; j++) {
						row += gameboard[i][j];

					}
					event.getChannel().sendMessage(row);
				}

			} else if(command.equals("Rules")) {
				event.getChannel().sendMessage("Players alternate turns placing a stone of their color on an empty square.\n The winner is the first player to form an unbroken chain of five stones horizontally, vertically, or diagonally.\n The origin of this board (0,0) is in the top left corner.");
			}

		} else if(event.getMessageContent().contains("!gm")) {
			String command = event.getMessageContent().replaceAll(" ","").replace("!gm", "");
			
			String xcoor = command.substring(0, command.indexOf(','));
			String ycoor = command.substring(command.indexOf(',')+1);
			
			int xc = Integer.parseInt(xcoor);
			int yc = Integer.parseInt(ycoor);
			
			if(playerNum==0) {
				gameboard[xc][yc] = ":small_orange_diamond:"+" ";
				playerNum = 1;
			} else if(playerNum ==1) {
				gameboard[xc][yc] = ":small_blue_diamond:"+" ";
				playerNum = 0;
			}
			
			for (int i = 0; i < gameboard.length; i++) {
				String row = "";
				for (int j = 0; j < gameboard[i].length; j++) {
					row += gameboard[i][j];

				}
				event.getChannel().sendMessage(row);
			}
			
		}

	}
}
