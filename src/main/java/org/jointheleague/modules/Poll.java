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

public class Poll extends CustomMessageCreateListener implements ReactionAddListener {

	public static String[] emoji = { "1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "🔟" };

	public static final String COMMAND = "!createPoll";
	
	ArrayList<Integer> votePercentages = new ArrayList<Integer>();
	
	public Poll(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		if (event.getMessageContent().contains(COMMAND)) {

			String messageContent = event.getMessageContent().replace(COMMAND, "");
			Parameters parameters = getParameters(messageContent);

			String time = parameters.time;
			String title = parameters.title;
			String[] options = parameters.options;

			OptionContent[] oc = initializeOptions(options);

			Message m = buildEmbed(title, oc, event.getChannel());
			handleReactions(m, options.length);
			
		}

	}

	private void handleReactions(Message m, int amount) {
		for (int i = 0; i < amount; i++) {
			m.addReaction(emoji[i]);
		}
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		m.addReactionRemoveListener(e -> calcVotePercentages(m, amount));
		m.addReactionAddListener(e -> calcVotePercentages(m, amount));
	}

	private OptionContent[] initializeOptions(String[] options) {
		OptionContent[] oc = new OptionContent[options.length];

		for (int i = 0; i < options.length; i++) {
			oc[i] = new OptionContent(emoji[i], options[i], "0%");
		}
		return oc;
	}

	private Message buildEmbed(String title, OptionContent[] options, TextChannel channel) {
		MessageBuilder mb = new MessageBuilder();
		EmbedBuilder eb = new EmbedBuilder();

		eb.setTitle(title);
		eb.setFooter("Testing what this does");

		String descriptionContent = "";
		for (OptionContent oc : options) {
			descriptionContent += oc.toString() + "\n";
		}
		eb.setDescription(descriptionContent);

		mb.setEmbed(eb);
		Message m = null;
		try {
			m = mb.send(channel).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return m;
	}

	private Parameters getParameters(String message) {
		String messageContent = message;
		System.out.println("Message: " + messageContent);

		String time = messageContent.substring(0, messageContent.indexOf(',') + 1);
		messageContent = messageContent.replace(time, "");
		time = time.replace(",", "").trim();

		String title = messageContent.substring(0, messageContent.indexOf(',') + 1);
		messageContent = messageContent.replace(title, "");
		title = title.replace(",", "").trim();

		ArrayList<String> options = new ArrayList<String>();

		while (messageContent != "") {
			String option = messageContent.substring(0, messageContent.indexOf(',') + 1);
			messageContent = messageContent.replace(option, "");
			option = option.replace(",", "").trim();
			options.add(option);

		}

		Parameters p = new Parameters(time, title, options.toArray(new String[options.size()]));

		return p;
	}

	@Override
	public void onReactionAdd(ReactionAddEvent event) {

	}

	private void calcVotePercentages(Message m, int amount) {		
		List<Reaction> l = m.getReactions();
		int totalVotes = 0;
		
		for (int i = 0; i < amount; i++) {
			totalVotes += l.get(i).getCount() - 1;
		}
		for (int i = 0; i < amount; i++) {
			int percentage = 100 * (l.get(i).getCount() - 1) / totalVotes;
			votePercentages.add(percentage);
		}
	}

}

class Parameters {
	String time;
	String title;
	String[] options;

	Parameters(String time, String title, String[] options) {
		this.time = time;
		this.title = title;
		this.options = options;
	}

}

class OptionContent {
	String emoji;
	String option;
	String percentage;

	OptionContent(String emoji, String option, String percentage) {
		this.emoji = emoji;
		this.option = option;
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return emoji + "  " + option + "   " + percentage;
	}

}
