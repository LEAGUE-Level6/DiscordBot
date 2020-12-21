package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Checkers extends CustomMessageCreateListener {
	private final String CMD = "!checkers";
	private final String INP = "!helpCheckers";
	private final String CMDSUN = "!moveSun";
	private final String CMDMOON = "!moveMoon";

	// makes strings for emojis
	private final String MOONFACE = "<:moon_with_red:785324927465160704>";
	private final String SUNFACE = "<:sun_with_red:785320897199734825>";
	private final String REDSQUARE = ":red_square:";
	private final String BLACKSQUARE = ":black_large_square:";
	String boardTwo = "";
	int x;
	int y;
	
	public Checkers(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub

	}

	public void move(String num1, String num2) {
		x = Integer.parseInt(num1);
		y=  Integer.parseInt(num2);
		
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
			event.getChannel().sendMessage("To move a piece use !moveSun or !moveMoon following it with the coordinates");
			checkerboard();
			event.getChannel().sendMessage(boardTwo);
		}

		if(input.contains(CMDSUN)|| input.contains(CMDMOON)) {
			String[] parts = input.split(" ");
			String part1 = parts[0]; 
			String part2 = parts[1]; 
			String[]nums = part2.split(",");
			String nums1 = nums[0]; 
			String nums2 = nums[1]; 
			move(nums1, nums2);
			event.getChannel().sendMessage(x+" " + y);
		}
		

}

}
