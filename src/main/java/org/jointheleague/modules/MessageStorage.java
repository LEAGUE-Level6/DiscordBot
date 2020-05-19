package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MessageStorage extends CustomMessageCreateListener {
	private static final String ADD_COMMAND = "!add";
	private static final String VIEW_COMMAND = "!view";

	private ArrayList<String> messages = new ArrayList<String>();

	public MessageStorage(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		if (event.getMessageContent().contains(ADD_COMMAND)) {

			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!add", "");

			if (cmd.equals("")) {

				event.getChannel().sendMessage("usage - !add (Message)");

			} else {

				String message = cmd.substring(0, cmd.length());

				event.getChannel().sendMessage("you have added " + message + " to the storage");
				messages.add(message);
			}

		} else if (event.getMessageContent().contains(VIEW_COMMAND)) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!view", "");

			if (cmd.equals("")) {
				for (String message : messages) {

					event.getChannel().sendMessage((messages.indexOf(message) + 1) + ": " + message);

				}
				event.getChannel().sendMessage("You can view a specific message by typing the index!");

			} else {

				String message = cmd.substring(0, cmd.length() - 1);
				try {
					event.getChannel().sendMessage(messages.get((Integer.parseInt(cmd)) - 1));

				} catch (NumberFormatException e) {
					event.getChannel().sendMessage("you must put a number after !add");

				}

			}

		}

	}

}
