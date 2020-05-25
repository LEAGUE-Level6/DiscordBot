package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class TicTacToe extends CustomMessageCreateListener{
	private static final String COMMAND = "!TicTacToe";
	final String TopLeft = "!TopLeft";
	final String TopMiddle = "!TopMiddle";
	final String TopRight = "!TopRight";
	final String MiddleLeft = "!MiddleLeft";
	final String MiddleMiddle = "!MiddleMiddle";
	final String MiddleRight = "!MiddleRight";
	final String BottomLeft = "!BottomLeft";
	final String BottomMiddle = "BottomMiddle";
	final String BottomRight = "BottomRight";

	ArrayList<String>commands = new ArrayList<String>();
	ArrayList<String>user = new ArrayList<String>();
	ArrayList<String>CPU = new ArrayList<String>();
	int counter = -1;
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
			counter = 0;
			event.getChannel().sendMessage("Choose where you want to place the X");
			return;
		}
		else {
			if(counter == -1) {
				return;
			}
		if(counter%2 == 0) {
			event.getChannel().sendMessage("Choose where you want to place the X");
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String location = event.getMessage().getContent().toString();
			System.out.println(location + " hi");
			user.add(location);
			commands.remove(location);
		}
		else if(counter%2 == 1) {
			Random r = new Random();
			int a = r.nextInt(commands.size());
			for (int j = 0; j < commands.size(); j++) {
				if(a==j) {
					CPU.add(commands.get(j));
					event.getChannel().sendMessage("CPU places O at"+ commands.get(j));
					commands.remove(j);
				}
			}
		}
		counter++;
		if(CPU.contains(TopLeft) && CPU.contains(TopMiddle) && CPU.contains(TopRight)) {
			event.getChannel().sendMessage("You lose");
		}
		else if(user.contains(TopLeft) && user.contains(TopMiddle) && user.contains(TopRight)) {
			event.getChannel().sendMessage("You win");
		}
		else if(CPU.contains(MiddleLeft) && CPU.contains(MiddleMiddle) && CPU.contains(MiddleRight)) {
			event.getChannel().sendMessage("You lose");
		}
		else if(user.contains(MiddleLeft) && user.contains(MiddleMiddle) && user.contains(MiddleRight)) {
			event.getChannel().sendMessage("You win");
		}
		else if(CPU.contains(BottomLeft) && CPU.contains(BottomMiddle) && CPU.contains(BottomRight)) {
			event.getChannel().sendMessage("You lose");
		}
		else if(user.contains(BottomLeft) && user.contains(BottomMiddle) && user.contains(BottomRight)) {
			event.getChannel().sendMessage("You win");
		}
		else if(CPU.contains(TopLeft) && CPU.contains(MiddleLeft) && CPU.contains(BottomLeft)) {
			event.getChannel().sendMessage("You lose");
		}
		else if(user.contains(TopLeft) && user.contains(MiddleLeft) && user.contains(BottomLeft)) {
			event.getChannel().sendMessage("You win");
		}
		else if(CPU.contains(TopMiddle) && CPU.contains(MiddleMiddle) && CPU.contains(BottomMiddle)) {
			event.getChannel().sendMessage("You lose");
		}
		else if(user.contains(TopMiddle) && user.contains(MiddleMiddle) && user.contains(BottomMiddle)) {
			event.getChannel().sendMessage("You win");
		}
		else if(CPU.contains(TopRight) && CPU.contains(MiddleRight) && CPU.contains(BottomRight)) {
			event.getChannel().sendMessage("You lose");
		}
		else if(user.contains(TopRight) && user.contains(MiddleRight) && user.contains(BottomRight)) {
			event.getChannel().sendMessage("You lose");
		}
		}
	}
}
