package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class DadBot extends CustomMessageCreateListener {

	public DadBot(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		char[] message=event.getMessageContent().toLowerCase().toCharArray();
		
		int key=0;
		int beginning=0;
		int end=0;
		
		for (int i = 0; i < message.length-2; i++) {
			if (message[i]=='i' && message[i+1]=='\'' && message[i+2]=='m' ) {
				key+=1;
				beginning=i+4;
			}
			else if (message[i]=='i' && message[i+2]=='a' && message[i+3]=='m' ) {
				key+=1;
				beginning=i+5;
			}
			else if (message[i]=='i' && message[i+1]=='m' ) {
				key+=1;
				beginning=i+3;
			}
			break;
			
		}
		for(  end = beginning; end < message.length; end++) {
		
			if (message[end]==' ') {
				break;
			}
		}
		
		
		if (key==1) {
			event.getChannel().sendMessage("Hi " +event.getReadableMessageContent().substring(beginning, end)+ " I'm Dad!" +" Yashwin's DadBot");
			
			
		}
	}

}
