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
			System.out.println(counter);
			event.getChannel().sendMessage("Choose where you want to place the X");
		} else {
			System.out.println(counter);
			if (counter == -1) {
				return;
			}
			if (counter % 2 == 0) {
				System.out.println("Hello");
				event.getChannel().sendMessage("Choose where you want to place the X");
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String location = event.getMessageContent().toString();
				System.out.println(location + "location");
				user.add(location);
				// for (int i = commands.size()-1; i >= 0; i--) {
				// if(commands.get(i).equals(location)) {
				//// commands.remove(i);
				//// System.out.println("remove");
				// }
				// }
			} else if (counter % 2 == 1) {
				// Random r = new Random();
				// int a = r.nextInt(commands.size());
				for (int j = commands.size() - 1; j >= 0; j--) {
					for (int i = 0; i < user.size(); i++) {
						if (CPU.size() == 0) {
							if (!commands.get(j).equals(user.get(i))) {
								System.out.println(commands.get(j) + "computer");
								CPU.add(commands.get(j));
								event.getChannel().sendMessage("CPU places O at" + commands.get(j));
								stopuser = true;
								stopcommand = true;
								break;
							}
						} else {
								System.out.println("elsecall");
							for (int k = 0; k < CPU.size(); k++) {
								System.out.println(commands.get(j) + "commands");
								System.out.println(user.get(i) + "user");
								System.out.println(CPU.get(k) + "CPU");
								if (!commands.get(j).equals(user.get(i)) && !commands.get(j).equals(CPU.get(k))) {
									System.out.println(commands.get(j) + "computer");
									CPU.add(commands.get(j));
									event.getChannel().sendMessage("CPU places O at" + commands.get(j));
									stopuser = true;
									stopcommand = true;
									break;

								}
								
							}
							if(stopuser) {
								break;
							}
						}
						
					}
					if(stopcommand) {
						break;
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
