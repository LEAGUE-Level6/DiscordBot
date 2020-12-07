package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Checkers extends CustomMessageCreateListener {
	private final String CMD = "!checkers";
	private final String INP = "!helpCheckers";
	private final String CMDSUN = "!sun";
	private final String CMDMOON = "!moon";

	// makes strings for emojis
	private final String MOONFACE = "<:moon_with_red:785324927465160704>";
	private final String SUNFACE = "<:sun_with_red:785320897199734825>";
	private final String REDSQUARE = ":red_square:";
	private final String BLACKSQUARE = ":black_large_square:";
	String boardTwo = "";

	public Checkers(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub

	}

	public void move() {
		
		
		
		
		
		
	}
	public void checkerboard() {
		String[][] board = new String[8][8];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				 
				if(i <3 && i%2 ==0 && j%2 != 0 || i==1 && j%2 ==0) {
					board[i][j] = MOONFACE;
				}
				else if(i>= 5 && i%2 !=0 && j%2 == 0|| i==6 && j%2 !=0) {
					board[i][j] = SUNFACE;
				}
				else if (i % 2 != 0 && j % 2 != 0 || i % 2 == 0 && j % 2 == 0) {
					board[i][j] = BLACKSQUARE;
				
			}else {
					board[i][j] = REDSQUARE;
					
					}
				boardTwo += board[i][j];
			}
			boardTwo += "\n";
		}
	
	}

	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String input = event.getMessageContent();
		
		//if(input.cont)
		if (input.contains(CMD)) {
			event.getChannel().sendMessage("To move a piece use !movesSun or !moveMoon following it with the coordinates");
			checkerboard();
			event.getChannel().sendMessage(boardTwo);
		}

		
		

}

}
