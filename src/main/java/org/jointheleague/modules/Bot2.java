package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class Bot2 extends CustomMessageCreateListener{
	public Bot2(String channelName) {
		super(channelName);
	}
	//  BOT'S ID: 691338351025848351
	boolean in = false;
	boolean waiting = false;
	boolean f1=false, f2 = false;
	long p1 = 0;
	long p2 = 0;
	boolean turn = true;
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().equals("*tigtagtow")&&!in) {
			event.getChannel().sendMessage("THE NEXT 2 PEOPLE TO TYPE ARE THE 2 PLAYERS");
			waiting = true;
		}else if(waiting){
			if(f1==false) {
				f1 = true;
				p1 = event.getMessageAuthor().getId();
			}else if(f2 == false && event.getMessageAuthor().getId()!=p1) {
				f2 = true;
				p2 = event.getMessageAuthor().getId();
			}
			waiting = false;
			in = true;
			boolean end = false;
			event.getChannel().sendMessage("DA GAME BEGINS");
			while(!end) {
				if(turn)turn=false;
				else turn = true;
			}
		}else if(event.getMessageAuthor().getId()!=691338351025848351L&&in&&!event.getMessageContent().equals("X")||!event.getMessageContent().equals("Y")) {
			event.getChannel().sendMessage("Your foolish actions give me brain damage. There is a game in progress!");
		}
	}
}
 