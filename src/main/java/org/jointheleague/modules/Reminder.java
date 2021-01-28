package org.jointheleague.modules;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import javax.swing.Timer;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class Reminder extends CustomMessageCreateListener implements ActionListener {

	ArrayList<String> userNames = new ArrayList<String>();
	ArrayList<String> messages = new ArrayList<String>();
	ArrayList<TextChannel> channelNames = new ArrayList<TextChannel>();
	ArrayList<Timer> timers = new ArrayList<Timer>();

	public static final String REMIND_COMMAND = "!setReminder";
	public static final String HELP_COMMAND = "!setReminderHelp";

	public Reminder(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		if (event.getMessageContent().contains(HELP_COMMAND)) {

			//event.get
			
			new MessageBuilder().setEmbed(new EmbedBuilder().setTitle(
					"To use this command first type the '!setReminder' command, followed by the time you want to be reminded (In Military Time), a comma, then your remind message")
					.setDescription("P.S. Make sure when you input your time use a colon between the hours and minutes")
					.setFooter("")).send(event.getChannel());

		} else if (event.getMessageContent().contains(REMIND_COMMAND)) {

			// add use to the arraylist
			String user = event.getMessageAuthor().getIdAsString();
			userNames.add(user);

			// add the channel to the arraylist
			TextChannel txtChannel = event.getChannel();
			channelNames.add(txtChannel);

			String parameters = event.getMessageContent().replace(REMIND_COMMAND, "");

			if (parameters.equals("")) {
				event.getChannel().sendMessage("Invalid Input");
			}

			String timeParameter = "";

			try {
				// get the raw timeParameter, fix it later
				timeParameter = parameters.substring(0, parameters.indexOf(','));

				parameters = parameters.substring(parameters.indexOf(',') + 1, parameters.length());
			} catch (StringIndexOutOfBoundsException e) {
				// This catches if there is no comma, which probably means there is no message
				// so I set that to an empty string

				timeParameter = parameters.substring(0, parameters.length());

				parameters = "";

			}

			// get the raw messageParameter, no need to fix it and I add it to the arrayList
			// immediately
			String messageParameter = parameters.substring(0, parameters.length());
			messages.add(messageParameter);

			// all this is fixing the raw timeParameter so I can parse it to the LocalTime
			// class and compare in to the current time
			timeParameter = timeParameter.replaceAll(" ", "");

			int count = timeParameter.length() - timeParameter.replaceAll(":", "").length();
			if (count == 1) {
				timeParameter += ":00";
			}

			// parse fixed timeParameter to LocalTime class and get the current local time
			LocalTime time = LocalTime.parse(timeParameter);
			LocalTime currentTime = LocalTime.now();

			// using LocalTime class functions, find the time until the user needs to be
			// reminded, in seconds
			// what the heck is a chronofield
			int timeUntil = (int) (time.getLong(ChronoField.SECOND_OF_DAY)
					- currentTime.getLong(ChronoField.SECOND_OF_DAY));

			// Using the calculated time in seconds to create a timer that goes off after
			// that time, add to ArrayList
			timers.add(new Timer(timeUntil * 1000, this));
			timers.get(timers.size() - 1).start();

			event.getChannel().sendMessage("Successfully created a reminder for " + time.toString());
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < timers.size(); i++) {

			// Check which timer the ActionEvent belongs to, to find which index to be using
			if (e.getSource().equals(timers.get(i))) {
				channelNames.get(i).sendMessage("<@" + userNames.get(i) + ">");

				new MessageBuilder()
						.setEmbed(new EmbedBuilder().setTitle(messages.get(i)).setDescription("").setFooter(""))
						.send(channelNames.get(i));

				timers.get(i).stop();

				// removing the information from the ArrayLists
				userNames.remove(i);
				channelNames.remove(i);
				timers.remove(i);
				messages.remove(i);

			}
		}
	}

}
