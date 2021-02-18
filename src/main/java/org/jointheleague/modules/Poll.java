package org.jointheleague.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.swing.Timer;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

import net.aksingh.owmjapis.api.APIException;

public class Poll extends CustomMessageCreateListener {

	public static String[] emoji = { "1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "🔟" };

	public static final String COMMAND = "!createPoll";

	public Poll(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		if (event.getMessageContent().contains(COMMAND)) {

			String messageContent = event.getMessageContent().replace(COMMAND, "");

			ArrayList<String> parameters = getParameters(messageContent);

			String time = parameters.get(0);
			parameters.remove(0);

			String title = parameters.get(0);
			parameters.remove(0);

			String[] options = new String[parameters.size()];
			for (int i = 0; i < options.length; i++) {
				options[i] = parameters.get(i);
			}

			buildEmbed(title, options, event.getChannel());

		}

	}

	private void buildEmbed(String title, String[] options, TextChannel channel) {
		MessageBuilder mb = new MessageBuilder();
		EmbedBuilder eb = new EmbedBuilder();

		eb.setTitle(title);

		String optionString = "";
		for (String s : options) {
			optionString += " " + s;
		}

		eb.setDescription(optionString);

		mb.setEmbed(eb);

		mb.send(channel);
	}

	private ArrayList<String> getParameters(String message) {
		String messageContent = message;
		System.out.println("Message: " + messageContent);

		ArrayList<String> group = new ArrayList<String>();

		String time = messageContent.substring(0, messageContent.indexOf(',') + 1);
		messageContent = messageContent.replace(time, "");
		time = time.replace(",", "").trim();

		String title = messageContent.substring(0, messageContent.indexOf(',') + 1);
		messageContent = messageContent.replace(title, "");
		title = title.replace(",", "").trim();

		ArrayList<String> options = new ArrayList<String>();

		group.add(time);
		group.add(title);

		while (messageContent != "") {
			String option = messageContent.substring(0, messageContent.indexOf(',') + 1);
			messageContent = messageContent.replace(option, "");
			option = option.replace(",", "").trim();
			options.add(option);

		}

		for (String s : options) {
			group.add(s);
		}

		return group;

//		System.out.println("time: " + time);
//		System.out.println("title: " + title);
//		for (String s : options) {
//			System.out.println("option: " + s);
//		}
//		System.out.println("final: " + messageContent);

	}

}
