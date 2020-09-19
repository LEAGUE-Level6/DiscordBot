package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class AddTwoNumbers extends CustomMessageCreateListener {

	private static final String command = "!add";
	
	public AddTwoNumbers(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(command, "allows you to add two single digit numbers together");
	}

	@Override
	public void handle(MessageCreateEvent event){
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(command)) {
			String string = event.getMessageContent().replace("!add ", "");
			System.out.println(string);
			//get the character at index 0
			char no = string.charAt(0);
			String bah = Character.toString(no);
			//turn that into a number (parseInt())
			int one = Integer.parseInt(bah);
			//get the character at index 2 
			char nt = string.charAt(2);
			String h = Character.toString(nt);
			//turn into number 
			int two = Integer.parseInt(h);
			//add values 
			int oml = one + two;
			String what = Integer.toString(oml);
			System.out.println(oml);

			//send message using event.getChannel().sendMessage() ok thank you
			event.getChannel().sendMessage("the sum is " + oml);
		}
		
	}

}
