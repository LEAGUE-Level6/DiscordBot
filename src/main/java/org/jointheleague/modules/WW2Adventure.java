package org.jointheleague.modules;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class WW2Adventure extends CustomMessageCreateListener{
	private static final String COMMAND = "!BeginWW2Journey";
	
	public WW2Adventure(String channelName)  {
		// TODO Auto-generated constructor stub
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + BeginWW2Journey to begin your journey soldier!");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
		EmbedBuilder build = new EmbedBuilder();
		String txt = event.getMessageContent();
		
		txt.trim();
		if(txt.equalsIgnoreCase(COMMAND)) {
			build.setColor(Color.GREEN);
			build.setTitle("**" + "Hello soldier, you have received a draft to the war!" + "**" +"\n"+  "**" +"What battle would you like to be deployed in, soldier?" + "**");
			build.setDescription("1) Normandy beach (!DDay) \n 2) Midway island (!Midway) \n 3) Dunkirk (!Dunkirk) \n 4) Stalingrad (!Stalingrad)" );;
			
		} else if (txt.equalsIgnoreCase("!DDay")) {
			event.getChannel().sendMessage("Alright soldier, you have entered a really tough battle, "
					+ "to survive this battle, you must play a game! Say !Continue to move on");
			
			if (txt.equalsIgnoreCase("!Continue")) {
				event.getChannel().sendMessage("Alright, your first task is to spell Soldier backwards");
				if (txt.equalsIgnoreCase("reidloS")) {
					event.getChannel().sendMessage("Good work! You will continue");
					try {
						event.wait(2002);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					event.getChannel().sendMessage("Now time for some questions.");
					event.getChannel().sendMessage("What is dnaraG 1M spelt fowards?");
					if (txt.equalsIgnoreCase("M1 Garand")) {
						event.getChannel().sendMessage("Nice work soldier!");
						event.getChannel().type();
						build.setColor(Color.orange).setTitle("M1 Garand").addField("Fun facts about the M1 Garand:", "Fun fact about the M1 Garand: This gun was used frequently used by"
								+ " US soldiers during WW2 and during the reload it had a special ding sound.");
						
					}
					else {
						wrongAnswer(event);
					}
				}
				else {
					wrongAnswer(event);
				}
			}
		} else if (txt.equalsIgnoreCase("!Midway")) {
			
		} else if (txt.equalsIgnoreCase("!Dunkirk")) {
			
		} else if (txt.equalsIgnoreCase("!Stalingrad")) {
			
		}
		
	}
	public void wrongAnswer(MessageCreateEvent e) {
		e.getChannel().sendMessage("Sorry, you got the answer incorect. You have died.");
		e.getChannel().sendMessage("If you wanna try again type !BeginWW2Journey");
	}
}
