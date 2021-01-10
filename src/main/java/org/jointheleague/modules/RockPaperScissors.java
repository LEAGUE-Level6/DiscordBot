package org.jointheleague.modules;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class RockPaperScissors extends CustomMessageCreateListener{
	
	public RockPaperScissors(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}
	final String ROCK = "!rock";
	final String PAPER = "!paper";
	final String SCISSORS = "!scissors";
	int randy = 0;
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String eventContent = event.getMessageContent();
		randy = new Random().nextInt(3);
		
		if(eventContent.contains(ROCK)) {
			//display image of a rock
			event.editMessage("https://api.time.com/wp-content/uploads/2020/01/colorado-sheriff-large-small-boulder-tweet.jpg?quality=85&w=1200&h=628&crop=1");
			if(randy == 0) {
				event.getChannel().sendMessage("rock");
				//display image of rock
				event.getChannel().sendMessage("TIE");
			}
			else if(randy == 1) {
				event.getChannel().sendMessage("paper");
				//display image of paper
				event.getChannel().sendMessage("PAPER BEATS ROCK. I WIN!");
			}
			else if(randy == 2) {
				event.getChannel().sendMessage("scissors");
				//display image of scissors
				event.getChannel().sendMessage("ROCK BEATS SCISSORS. YOU WIN!");
			}
		}
		else if(eventContent.contains(PAPER)) {
			//display image of paper
			if(randy == 0) {
				event.getChannel().sendMessage("rock");
				//display image of rock
				event.getChannel().sendMessage("PAPER BEATS ROCK. YOU WIN!");
			}
			else if(randy == 1) {
				event.getChannel().sendMessage("paper");
				//display image of paper
				event.getChannel().sendMessage("TIE");
			}
			else if(randy == 2) {
				event.getChannel().sendMessage("scissors");
				//display image of scissors
				event.getChannel().sendMessage("SCISSORS BEATS PAPER. I WIN!");
			}
		}
		else if(eventContent.contains(SCISSORS)) {
			//display image of scissors
			if(randy == 0) {
				event.getChannel().sendMessage("rock");
				//display image of rock
				event.getChannel().sendMessage("ROCK BEATS PAPER. I WIN!");
			}
			else if(randy == 1) {
				event.getChannel().sendMessage("paper");
				//display image of paper
				event.getChannel().sendMessage("SCISSORS BEATS PAPER. YOU WIN!");
			}
			else if(randy == 2) {
				event.getChannel().sendMessage("scissors");
				//display image of scissors
				event.getChannel().sendMessage("TIE");
			}
		}
	}
	
}
