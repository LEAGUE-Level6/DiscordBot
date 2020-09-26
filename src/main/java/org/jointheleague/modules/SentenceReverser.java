package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class SentenceReverser extends CustomMessageCreateListener {

		private static final String COMMAND = "!reverse";
		
		public SentenceReverser (String channelName) {
			super(channelName);
			helpEmbed = new HelpEmbed(COMMAND, "Will reverse the given sentence (e.g. !reverse Reverse this sentence.)");
		}

		@Override
		public void handle(MessageCreateEvent event) {
			if (event.getMessageContent().contains(COMMAND)) {
				
				String sntce = event.getMessageContent().replaceAll(" ", "").replace("!reverse","");
				
				String rsntce = ""; 
				for (int i = sntce.length()-1; i >= 0; i--) {
					rsntce += sntce.charAt(i);
					//System.out.println(sntce.charAt(i));
				}
				event.getChannel().sendMessage("Reversed sentence: " + rsntce);
				//System.out.println(rsntce);
					
			}
		}

}
