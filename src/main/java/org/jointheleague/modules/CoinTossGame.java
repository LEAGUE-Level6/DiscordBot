package org.jointheleague.modules;

import java.util.Random;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class CoinTossGame extends CustomMessageCreateListener {
	boolean start = false;

	public CoinTossGame(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed("!coinTossGame", "Asks if you guess heads or tails, then tells you if your guess was correct");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!coinTossGame")) {
			event.getChannel().sendMessage("Do you think it is heads or tails");
			start = true;
		}
		else if (start) {
			stageTwo(event);
		}
	}
	void stageTwo(MessageCreateEvent event) {
		if (!(event.getMessageAuthor().isYourself())) {
			start = false;
		}
		String answer = event.getMessageContent();
		Random rand = new Random();
		int r = rand.nextInt(50);
		if (r % 2 == 0) {
			if (answer.startsWith("heads")) {
				event.getChannel().sendMessage("You are correct, it is heads");
			} else if (answer.startsWith("tails")) {
				event.getChannel().sendMessage("You are incorrect, it is heads");
			}
		} else {
			if (answer.startsWith("tails")) {
				event.getChannel().sendMessage("You are correct, it is tails");
			} else if (answer.startsWith("heads")) {
				event.getChannel().sendMessage("You are incorrect, it is tails");
			}
		}
	}
}
