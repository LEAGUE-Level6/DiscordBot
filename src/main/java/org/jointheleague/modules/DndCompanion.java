package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class DndCompanion extends CustomMessageCreateListener {

	private final static String COMMAND = "/ask";

	public DndCompanion(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("/ask", "");
			if (cmd.contains("links")) {
				cmd = event.getMessageContent().replaceAll(" ", "").replace("/ask links", "");
				if (cmd.equals("")) {
					event.getChannel().sendMessage("the links are: " + "\n" + "table: roll20.net" + "\n"
							+ "character sheets: dndbeyond.com" + "\n" + "this website");
				} else if (cmd.equals("table")) {
					event.getChannel().sendMessage("ro1120.net for the table :)");
				} else if (cmd.equals("character sheets")) {
					event.getChannel().sendMessage("dndbeyond.com for the table :)");
				} else if (cmd.equals("discord")) {
					event.getChannel().sendMessage("this website for discord calls");
				}
			}
		}
	}
}
