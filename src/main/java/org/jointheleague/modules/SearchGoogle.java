package org.jointheleague.modules;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class SearchGoogle extends CustomMessageCreateListener {
	
	private static final String COMMAND = "!gsearch";
	private static final String HELP = "!gsearch-help";
	private String help;

	public SearchGoogle(String channelName) {
		super(channelName);
		help = String.join("\n", 
				"[!gsearch] returns the titles and urls of the first <resultNumber> results",
				"with a search using the <keyword> on google.",
				"<b>Format: </b> !gsearch <keyword> <resultNumber>",
				"<resultNumber> has to be an positive integer, ",
				"and if this parameter is null, one result will always be returned.");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().startsWith(HELP)) {
			EmbedBuilder help = new EmbedBuilder().setTitle("!gsearch Help").setColor(Color.blue).setDescription(this.help);
			event.getChannel().sendMessage(help);
		} else if(event.getMessageContent().startsWith(COMMAND)) {
			String[] params = event.getMessageContent().trim().split(" ");
			int resultNum = 1;
			try {
				int num = Integer.parseInt(params[2]);
				if(num > 0) {
					resultNum = num;
				}
			} catch(ArrayIndexOutOfBoundsException ie) { }
			
		}
	}
	
	/**
	* Searches for a result on Google using a keyword
	* @param keyword The keyword to be used to search
	* @param index The index of the searched results
	* 
	* @return A String array containing the title and URL of the result
	*/
	private String[] searchGoogle(String keyword, int index) {
		return null;
	}
	
}
