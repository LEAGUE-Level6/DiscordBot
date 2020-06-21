package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.util.SystemPropertiesPropertySource;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class TicTacToe extends CustomMessageCreateListener {
	private static final String COMMAND = "!TicTacToe";
	final String TopLeft = "!TopLeft";
	final String TopMiddle = "!TopMiddle";
	final String TopRight = "!TopRight";
	final String MiddleLeft = "!MiddleLeft";
	final String MiddleMiddle = "!MiddleMiddle";
	final String MiddleRight = "!MiddleRight";
	final String BottomLeft = "!BottomLeft";
	final String BottomMiddle = "!BottomMiddle";
	final String BottomRight = "!BottomRight";

	ArrayList<String> commands = new ArrayList<String>();
	ArrayList<String> user = new ArrayList<String>();
	ArrayList<String> CPU = new ArrayList<String>();
	boolean turn = false;
	boolean stopcommand = false;
	boolean stopuser = false;
	public TicTacToe(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String location = event.getMessageContent();
		if (location.startsWith(COMMAND)) {
			commands.add(TopLeft);
			commands.add(TopMiddle);
			commands.add(TopRight);
			commands.add(MiddleLeft);
			commands.add(MiddleMiddle);
			commands.add(MiddleRight);
			commands.add(BottomLeft);
			commands.add(BottomMiddle);
			commands.add(BottomRight);
			event.getChannel().sendMessage("Welcome to the Game. Type Top to start");
		} 
		else if(location.contains("Top") || location.contains("Middle") || location.contains("Bottom") ){		
			if (turn == false) {
				event.getChannel().sendMessage("Choose where you want to place the X");
//				try {
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				
				System.out.println(location + "location");
				user.add(location);
				for (int i = commands.size()-1; i >= 0; i--) {
					if(commands.get(i).equals(location)) {
						commands.remove(i);
					}
				}
				if(location != null) {
					System.out.println("Hello");
					turn = true;
				}
			} 
			else if (turn == true) {
				if(CPU.size() == 0) {
					for (int i = 0; i < commands.size(); i++) {
							CPU.add(commands.get(i));
							for (int j = commands.size()-1; j >= 0; j--) {
								if(commands.get(j).equals(commands.get(i))) {
									commands.remove(j);
								}
							}
							event.getChannel().sendMessage("CPU places the O at " + commands.get(i));
							turn = false;
							break;
						}
					}
				}
				else {
					String temp = "";
					for (int i = 0; i < commands.size(); i++) {
						for (int j = 0; j < user.size(); j++) {	
								temp = commands.get(i);
								System.out.println(temp + "temp");
								break;
						}
								CPU.add(temp);
								for (int k = commands.size()-1; k >= 0; k--) {
									if(commands.get(k).equals(temp)) {
										commands.remove(k);
									}
								}
								event.getChannel().sendMessage("CPU places the O at " + temp);
								turn = false;
								i = commands.size();
								break;
					
						
						
						
					}
				}

				
				
				
				
			}
		
			for (int i = 0; i < CPU.size(); i++) {
				for (int j = 0; j < user.size(); j++) {
					if (CPU.get(i).equals(TopLeft) && CPU.get(i).equals(TopMiddle) && CPU.get(i).equals(TopRight)) {
						event.getChannel().sendMessage("You lose");
					} else if (user.get(j).equals(TopLeft) && user.get(j).equals(TopMiddle)
							&& user.get(j).equals(TopRight)) {
						System.out.println("You win");
						event.getChannel().sendMessage("You win");
					} else if (CPU.get(i).equals(MiddleLeft) && CPU.get(i).equals(MiddleMiddle)
							&& CPU.get(i).equals(MiddleRight)) {
						event.getChannel().sendMessage("You lose");
					} else if (user.get(j).equals(MiddleLeft) && user.get(j).equals(MiddleMiddle)
							&& user.get(j).equals(MiddleRight)) {
						event.getChannel().sendMessage("You win");
					} else if (CPU.get(i).equals(BottomLeft) && CPU.get(i).equals(BottomMiddle)
							&& CPU.get(i).equals(BottomRight)) {
						event.getChannel().sendMessage("You lose");
					} else if (user.get(j).equals(BottomLeft) && user.get(j).equals(BottomMiddle)
							&& user.get(j).equals(BottomRight)) {
						event.getChannel().sendMessage("You win");
					} else if (CPU.get(i).equals(TopLeft) && CPU.get(i).equals(MiddleLeft)
							&& CPU.get(i).equals(BottomLeft)) {
						event.getChannel().sendMessage("You lose");
					} else if (user.get(j).equals(TopLeft) && user.get(j).equals(MiddleLeft)
							&& user.get(j).equals(BottomLeft)) {
						System.out.println("You win");
						event.getChannel().sendMessage("You win");
					} else if (CPU.get(i).equals(TopMiddle) && CPU.get(i).equals(MiddleMiddle)
							&& CPU.get(i).equals(BottomMiddle)) {
						event.getChannel().sendMessage("You lose");
					} else if (user.get(j).equals(TopMiddle) && user.get(j).equals(MiddleMiddle)
							&& user.get(j).equals(BottomMiddle)) {
						event.getChannel().sendMessage("You win");
					} else if (CPU.get(i).equals(TopRight) && CPU.get(i).equals(MiddleRight)
							&& CPU.get(i).equals(BottomRight)) {
						event.getChannel().sendMessage("You lose");
					} else if (user.get(j).equals(TopRight) && user.get(j).equals(MiddleRight)
							&& user.get(j).equals(BottomRight)) {
						event.getChannel().sendMessage("You lose");
					}

				}
			}
		}
	}

