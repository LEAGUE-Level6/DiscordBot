package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class guitarTab extends CustomMessageCreateListener{
String URL = "http://www.songsterr.com/a/ra/songs.xml?pattern=";
String typed = "";
	public guitarTab(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().startsWith("!tab")) {
			typed = event.getMessageContent().substring(5);
			URL += typed;
		event.getChannel().sendMessage("To get a tab, type in !tab <Chord Name>" + URL);
		}
	}

}
