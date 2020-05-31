package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class Bot2 extends CustomMessageCreateListener {
	public Bot2(String channelName) {
		super(channelName);
	}

	boolean inGame = false;
	boolean p1 = false;
	long player = 0;
	int pl = 1, pr = 1, cl = 1, cr = 1;

	// BOT'S ID: 691338351025848351
	public void handle(MessageCreateEvent event) {
		if (event.getMessageAuthor().getId() == 691338351025848351L) {
			return;
		} else if (inGame && event.getMessageAuthor().getId() != player) {
			event.getChannel().sendMessage("begone1!1!!!!11!!1oneone1!");
		} else if (!inGame && !p1 && event.getMessageContent().equals("!stix")) {
			player = event.getMessageAuthor().getId();
			p1 = true;
			inGame = true;
				event.getChannel().sendMessage("Send something like l r (your left, their right) or r r (your right, their right)");
				event.getChannel().sendMessage("");
				event.getChannel().sendMessage("You: "+pl+" (left), "+pr+" (right)");
				event.getChannel().sendMessage("AI:    "+cl+" (left), "+cr+" (right)");
				event.getChannel().sendMessage("man_in_business_suit_levitating :levitate: man_in_business_suit_levitating");
		}else if(inGame) {
			String ez = event.getMessageContent();
			boolean yes = false;
			if(ez.length()==3&&ez.substring(1,2).equals(" ")) {
			if(ez.substring(0,1).equals("l")) {
				if(ez.substring(2).equals("r")) {
					yes = true;
					cr+=pl;
				}else if(ez.substring(2).equals("l")) {
					yes = true;
					cl+=pl;
				}
			}else if(ez.substring(0,1).equals("r")) {
				if(ez.substring(2).equals("r")) {
					yes = true;
					cr+=pr;
				}else if(ez.substring(2).equals("l")) {
					yes = true;
					cl+=pr;
				}
			}
			}
			if(!yes) {
				event.getChannel().sendMessage("not valid smh");
				return;
			}else {
				event.getChannel().sendMessage("You: "+pl+" (left), "+pr+" (right)");
				event.getChannel().sendMessage("AI:    "+cl+" (left), "+cr+" (right)");
				event.getChannel().sendMessage("man_in_business_suit_levitating :levitate: man_in_business_suit_levitating");
				if(pl>=pr) {
					pl+=Math.max(cl, cr);
				}else {
					pr+=Math.max(cl, cr);
				}
				event.getChannel().sendMessage("You: "+pl+" (left), "+pr+" (right)");
				event.getChannel().sendMessage("AI:    "+cl+" (left), "+cr+" (right)");
				event.getChannel().sendMessage("man_in_business_suit_levitating :levitate: man_in_business_suit_levitating");
				
			}
		}
	}
}
