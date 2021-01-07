package org.jointheleague.modules;

import java.time.LocalTime;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class Reminder extends CustomMessageCreateListener {

	public static final String COMMAND = "!setReminder";

	public static String reminderTime = "";

	public Reminder(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND)) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

			if (cmd.equals("")) {
				event.getChannel().sendMessage("Invalid Input");

			}

			System.out.println(cmd);

			String timeParameter = cmd.substring(0, cmd.indexOf(','));
			System.out.println(timeParameter);

			int count = timeParameter.length() - timeParameter.replaceAll(":", "").length();
			if (count == 1) {
				timeParameter += ":00";
			}

			System.out.println("Fixed time parameter: " + timeParameter);

			LocalTime time = LocalTime.parse(timeParameter);
			System.out.println("Time: " + time);

			cmd = cmd.substring(cmd.indexOf(',') + 1, cmd.length());
			System.out.println(cmd);

			String messageParameter = cmd.substring(0, cmd.length());
			System.out.println(messageParameter);

		}
		
		

	}

}
