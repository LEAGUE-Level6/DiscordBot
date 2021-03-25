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
import javax.swing.plaf.metal.OceanTheme;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.jointheleague.modules.pojo.HelpEmbed;

import javassist.compiler.MemberCodeGen;
import net.aksingh.owmjapis.api.APIException;

public class Poll extends CustomMessageCreateListener implements ReactionAddListener {

	public static String[] emoji = { "1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "🔟" };

	public static final String COMMAND = "!createPoll";

	boolean pollUp = false;

	public Poll(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"You can create polls where people can vote for options using emojis. In order to create a poll first type the command "
						+ COMMAND
						+ " and then enter how long you want the poll to be up, its name, and then list the options in the poll. Everything should be separated by a comma \n Ex. !createPoll 5 minutes, Cats vs Dogs, Cats, Dogs, Neither");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		if (event.getMessageContent().contains(COMMAND)) {

			String messageContent = event.getMessageContent().replace(COMMAND, "");
			Parameters parameters = getParameters(messageContent);

			String time = parameters.time;
			String title = parameters.title;
			String[] options = parameters.options;

			OptionContent[] oc = initializeOptions(options, null);

			Message m = buildEmbed(title, oc, event.getChannel());
			System.out.println(m.getContent());
			handleReactions(m, options.length);

			createTimer(time, m);

			try {
				Thread.sleep(6000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			m.addReactionRemoveListener(e -> votePercentages(m, options));
			m.addReactionAddListener(e -> votePercentages(m, options));

			pollUp = true;

		}

	}

	private void createTimer(String timeParameter, Message m) {

		String measure = timeParameter.substring(timeParameter.indexOf(' ') + 1, timeParameter.length());
		String amount = timeParameter.substring(0, timeParameter.indexOf(' '));

		int duration = 0;

		if (measure.equals("hours") || measure.equals("hour")) {
			duration = Integer.parseInt(amount) * 60 * 60;

		} else if (measure.equals("minutes") || measure.equals("minute")) {
			duration = Integer.parseInt(amount) * 60;

		} else if (measure.equals("seconds") || measure.equals("second")) {
			duration = Integer.parseInt(amount);

		}

		Timer timer = new Timer(duration * 1000, e -> {
			pollUp = false;

			String title = m.getEmbeds().get(0).getTitle().get();
			String footer = m.getEmbeds().get(0).getFooter().get().getText().get() + "\n(POLL HAS ENDED)\n";
			String description = m.getEmbeds().get(0).getDescription().get();

			EmbedBuilder eb = new EmbedBuilder().setTitle(title).setDescription(description).setFooter(footer);

			m.edit(eb);
		});

		timer.setRepeats(false);
		timer.start();
	}

	private void votePercentages(Message m, String[] options) {
		if (pollUp) {
			List<Reaction> reactionList = m.getReactions();

			int[] percentages = new int[options.length];

			int totalVotes = 0;

			for (int i = 0; i < options.length; i++) {
				totalVotes += reactionList.get(i).getCount() - 1;
			}

			for (int i = 0; i < options.length; i++) {
				int percentage = 0;
				try {
					percentage = (reactionList.get(i).getCount() - 1) * (100 / totalVotes);
				} catch (Exception e) {
					e.getStackTrace();
				}
				percentages[i] = percentage;
			}

			OptionContent[] oc = initializeOptions(options, percentages);

			String descriptionContent = "";

			for (OptionContent c : oc) {
				descriptionContent += c.toString() + "\n";
			}

			String title = m.getEmbeds().get(0).getTitle().get();
			String footer = m.getEmbeds().get(0).getFooter().get().getText().get();

			m.edit(new EmbedBuilder().setTitle(title).setDescription(descriptionContent).setFooter(footer));
		}
	}

	private void handleReactions(Message m, int amount) {
		for (int i = 0; i < amount; i++) {
			m.addReaction(emoji[i]);
		}
	}

	private OptionContent[] initializeOptions(String[] options, int[] percentages) {
		OptionContent[] oc = new OptionContent[options.length];

		String percent = "     0%";

		for (int i = 0; i < options.length; i++) {
			if (percentages != null) {
				percent = "     " + percentages[i] + "%";
			}

			oc[i] = new OptionContent(emoji[i], options[i], percent);
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
			System.out.println("exception");
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
			String option = "";

			if(messageContent.contains(",")) {
				option = messageContent.substring(0, messageContent.indexOf(',') + 1);
				messageContent = messageContent.replace(option, "");
				option = option.replace(",", "").trim();
				options.add(option);
			} else {
				option = messageContent.substring(0, messageContent.length());
				messageContent = messageContent.replace(option, "");
				option.trim();
				options.add(option);
			}
			
			
		

		}

		Parameters p = new Parameters(time, title, options.toArray(new String[options.size()]));

		return p;
	}

	@Override
	public void onReactionAdd(ReactionAddEvent event) {

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
