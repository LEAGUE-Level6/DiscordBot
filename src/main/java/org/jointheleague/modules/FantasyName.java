package org.jointheleague.modules;

import java.util.Random;


import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class FantasyName extends CustomMessageCreateListener {

	String mnames[]= {"Finwe", "Feanor", "Fingolfin", "Finarfin", "Fingon", "Turgon", "Eol", "Maeglin", "Tuor", "Earendil", "Eldros", "Finrod", "Orodreth", "Aegnor", "Angrod", "Olwe", "Thingol", "Beren", "Dior"};
	//
	String fnames[]= {"Miriel", "Indis", "Earwen", "Idril", "Elwing", "Melian", "Nimloth", "Luthien", "Rian", "Morwen"};
	
	private static final String COMMAND = "!fantasyname";
	
	public FantasyName(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Allows you to generate a high fantasy name. Gender can be specified by appending 'm' or 'f' to your message.");
	//
	
	
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent();
			Boolean f=false;
			Random r = new Random();
			
			if(cmd.length()==12) {
				if(r.nextInt(2)==0) {
					f=true;
				}else {
					f=false;
				}
			}else if (cmd.charAt(12)=='f') {
				f=true;
			}else if(cmd.charAt(12)=='m') {
				f=false;
			}else {
				if(r.nextInt(2)==0) {
					f=true;
				}else {
					f=false;
				}
			}
			
			if(f==true) {
				String name=fnames[r.nextInt(fnames.length)];
				event.getChannel().sendMessage(name);
			}else {
				String name=mnames[r.nextInt(mnames.length)];
				event.getChannel().sendMessage(name);
			}
			
		}
	}
	
}
