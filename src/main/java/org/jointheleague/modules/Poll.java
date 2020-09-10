package org.jointheleague.modules;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import net.aksingh.owmjapis.api.APIException;
public class Poll extends CustomMessageCreateListener implements Reaction, Emoji{

	public Poll(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException{
		String message = "";
		String m = event.getMessageContent();
		if(m.toLowerCase().contains("!poll")) {
			String x=event.getMessageContent();
			int comma1=x.indexOf(",")+1;
			int comma2=x.lastIndexOf(",");
			String sub1 =x.substring(comma1, comma2);
			String sub2=x.substring(comma2+1, x.length());
			for(int i = 0; i<x.length();i++) {
				if(i>4) {
					 message=message+x.charAt(i);
				}
			}
			event.getChannel().sendMessage("-->Poll:"+message+"<--\n"+"Do 🟢 for "+sub1+"\n And do 🔴 for "+sub2);
		}
		if(event.getMessageContent().contains("-->Poll:")) {
			char greenNum=' ';
			char redNum=' ';
			event.addReactionsToMessage("🟢");
			event.addReactionsToMessage("🔴");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			String green =event.getMessage().getReactionByEmoji("🟢")+"";
			if(green.contains("count: ")) {
				 greenNum =green.charAt(green.indexOf("count: ")+7);
			}
			
			String red=event.getMessage().getReactionByEmoji("🔴")+"";
			if(red.contains("count: ")) {
				 redNum =red.charAt(red.indexOf("count: ")+7);
			}
			
			if(greenNum>redNum) {
				event.getChannel().sendMessage("The final verdict is that GREEN is the answer");
			}
			else if (redNum>greenNum) {
				event.getChannel().sendMessage("The final verdict is that RED is the answer");
			}
			else {
				event.getChannel().sendMessage("The people are undecisive");
			}
		}
		
		
	}

	@Override
	public Message getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Emoji getEmoji() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean containsYou() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMentionTag() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	@Override
	public Optional<String> asUnicodeEmoji() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAnimated() {
		// TODO Auto-generated method stub
		return false;
	}

}
