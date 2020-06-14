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
	static int counter = -1;
	boolean stopcommand = false;
	boolean stopuser = false;
	public TicTacToe(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith(COMMAND)) {
			commands.add(TopLeft);
			commands.add(TopMiddle);
			commands.add(TopRight);
			commands.add(MiddleLeft);
			commands.add(MiddleMiddle);
			commands.add(MiddleRight);
			commands.add(BottomLeft);
			commands.add(BottomMiddle);
			commands.add(BottomRight);
			//counter++;
			counter = 0;
			//System.out.println(counter);
			event.getChannel().sendMessage("Choose where you want to place the X");
		} else {
			//System.out.println(counter);
			if (counter == -1) {
				return;
			}
			if (counter % 2 == 0) {
				event.getChannel().sendMessage("Choose where you want to place the X");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String location = event.getMessageContent();
				user.add(location);
			} 
			else if (counter % 2 == 1) {
				if(CPU.size() == 0) {
					for (int i = 0; i < commands.size(); i++) {
						if(user.get(0) != commands.get(i)) {
							System.out.println(user.get(0) + "user");
							CPU.add(commands.get(i));
							event.getChannel().sendMessage("CPU places the O at " + commands.get(i));
							break;
						}
					}
				}
				else {
					String temp = "";
					for (int i = 0; i < commands.size(); i++) {
						for (int j = 0; j < user.size(); j++) {
							if(commands.get(i) != user.get(j)) {
								temp = commands.get(i);
								break;
							}
						}
						for (int j = 0; j < CPU.size(); j++) {
							if(temp != CPU.get(j)) {
								CPU.add(temp);
								event.getChannel().sendMessage("CPU places the O at " + temp);
								i = commands.size();
								break;
							}
						}
						
						
					}
				}

				
				
				
				
			}
			counter++;
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
}
