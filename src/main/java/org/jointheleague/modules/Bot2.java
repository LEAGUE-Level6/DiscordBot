package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class Bot2 extends CustomMessageCreateListener{
	public Bot2(String channelName) {
		super(channelName);
	}
	//  BOT'S ID: 691338351025848351
	public void handle(MessageCreateEvent event) {
		if(event.getMessageAuthor().getId()!=691338351025848351L) {
			event.getChannel().sendMessage("Simp, you blabber in base 36! Express your thoughts more clearly in Arabic.\nDon't you mean "+baseConversion(event.getMessageContent(), 36, 10));
		}
	}
	public String baseConversion(String number, int sBase, int dBase) {
		return Integer.toString(Integer.parseInt(number, sBase), dBase).toUpperCase();
	}
}
 