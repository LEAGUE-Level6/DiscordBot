package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Iterator;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Poll extends CustomMessageCreateListener {

	
	ArrayList<String> options;

	public static String pollOptionContent = "";

	public static final String COMMAND = "!createPoll";

	public Poll(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND)) {

			String parameters = event.getMessageContent().replace(COMMAND, "");

			System.out.println(parameters);

			String timeParameter = parameters.substring(0, parameters.indexOf(','));
			parameters = parameters.substring(parameters.indexOf(',') + 1, parameters.length());

			System.out.println(timeParameter);

			String pollTitle = parameters.substring(0, parameters.indexOf(','));
			parameters = parameters.substring(parameters.indexOf(',') + 1, parameters.length());

			ArrayList<String> optionInputs = new ArrayList<String>();

			while (parameters != "") {
				try {
					optionInputs.add(parameters.substring(0, parameters.indexOf(',')));

					parameters = parameters.substring(parameters.indexOf(',') + 1, parameters.length());

				} catch (StringIndexOutOfBoundsException e) {
					optionInputs.add(parameters.substring(0, parameters.length()));

					parameters = "";

				}
			}

			System.out.println("List of options");

			for (String s : optionInputs) {
				pollOptionContent += s + "\n";
			}

//			for(int i = 0; i < optionInputs.size(); i++) {
//				pollOptionContent += optionInputs.get(i);
//			}

			new MessageBuilder()
					.setEmbed(new EmbedBuilder().setTitle(pollTitle).setDescription(pollOptionContent)
							.setFooter("The poll will only be up for about ___ minutes/seconds"))
					.send(event.getChannel());

		}

	}

}
