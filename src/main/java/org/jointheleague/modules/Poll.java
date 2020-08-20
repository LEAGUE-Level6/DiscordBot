package org.jointheleague.modules;

import java.awt.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
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
			for(int i = 0; i<x.length();i++) {
				if(i>4) {
					 message=message+x.charAt(i);
				}
			}
			event.getChannel().sendMessage("-->Poll:"+message+"<--");
		
			
		}
		if(event.getMessageContent().contains("-->Poll:")) {
			event.addReactionsToMessage("🟢");
			event.addReactionsToMessage("🔴");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Reaction.getUsers(event.getApi(),"738184728426971186", event.getMessageId()+"", getEmoji()).get().size();
				//System.out.println(size);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
