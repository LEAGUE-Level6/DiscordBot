package org.jointheleague.modules;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class factorCalc extends CustomMessageCreateListener{
	private static final String COMMAND = "!calculator";
	private static final String COMMAND2 = "!factor";
	int n = 0;
	int factor = 0;

String number = "";
	public factorCalc(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
		event.getChannel().sendMessage("Type in !factor <number> to get it's factors");
		
		}
		else if(event.getMessageContent().contains(COMMAND2)) {

			number = event.getMessageContent().trim().substring(8, event.getMessageContent().length());
			 n = Integer.parseInt(number);
		     for(int i = 1; i <= n; i++) {
		    	 
		            if (n % i == 0) {
		            	
		               factor = i;		  
		               
		 
		event.getChannel().sendMessage("Factors are: "+factor);
		}}
		
		}}

}
