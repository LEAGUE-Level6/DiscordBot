package org.jointheleague.modules;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.javacord.api.event.message.MessageCreateEvent;

public class alphabetorder extends CustomMessageCreateListener {
	ArrayList<String> names = new ArrayList<>();
	int stage = 0;
	int stage2 = 0;
	int amount;
	String n; 
	String name;
	
	public alphabetorder(String channelName) {
		super(channelName);
	}
	
	@Override
	public void handle(MessageCreateEvent event) {
		
		
		if(stage == 0){
			stage = 0;
			stage2 = 0;
			names = new ArrayList<>();
			
			if (event.getMessageContent().startsWith("!alphabetOrder")) {
				n = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage("Enter the amount of names");
				stage = 1;
			}
			
		}
		else if(stage == 1 && event.getMessageAuthor().getDisplayName().equals(n)) {
			
		    amount = Integer.parseInt(event.getMessageContent());
			if(amount<=0) {
				event.getChannel().sendMessage("Enter Positive Value for the Amount");	
				stage = 1;
			}
			else {
				System.out.println("AMOUNT: "+ amount);
				
				
				//getNames(amount, event);
				stage=2;
				event.getChannel().sendMessage("Enter all the Names one by one?");	
			}
			

		}
	    else if(stage ==2) {
	    
	    		
	    	
	    		if(event.getMessageAuthor().getDisplayName().equals(n)) {
	    			
	    			name = event.getMessageContent();
	    			names.add(name);
	    			stage2+=1;
	    			System.out.println(stage2);
	    			
	    			
	    		}
	    		
	    	
	    	
	    	
	    	
	    if(stage2==amount) {
	    	event.getChannel().sendMessage("Moving to next stage");	
	    	stage = 3;
	    }
	    }
	    
	    	
		
	   if(stage ==3) {
		   for(int i=0; i<names.size(); i++) {
			  for(int j=0; j<names.get(i).length(); j++) {
				   names.set(i, names.get(i).toLowerCase());
			  }
		   }
		
		//now sort them and print them in order. 
		  Collections.sort(names);	
		  event.getChannel().sendMessage("Final Result:");	
		  event.getChannel().sendMessage(names.toString());
		  
		
		
		//print them in one line
		stage = 0;
	}
	}
	
	 
	 							
	}

		
		
		
	



