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
	final String ROCK = "!chooseRock";
	final String PAPER = "!choosePaper";
	final String SCISSORS = "!chooseScissors";
	final String LIZARD = "!chooseLizard";
	final String SPOCK = "!chooseSpock";
	final String howTo = "!howToPlay";
	int randy = 0;
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String eventContent = event.getMessageContent();
		randy = new Random().nextInt(5);
		if(eventContent.contains(howTo)) {
			event.getChannel().sendMessage("Simply use !choose, then either Rock, Paper, Scissors, Lizard, or Spock without a space inbetween. \nCapitalization does matter");
		}
		else if(eventContent.contains(ROCK)) {
			//display image of a rock
			if(randy == 0) {
				event.getChannel().sendMessage("rock");
				//display image of rock
				event.getChannel().sendMessage("TIE");
				event.getChannel().sendMessage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnP8kyUnsCRsrA8rBtZH0V9oAzhSeKOV4Vww&usqp=CAU");
			}
			else if(randy == 1) {
				event.getChannel().sendMessage("paper");
				//display image of paper
				event.getChannel().sendMessage("PAPER BEATS ROCK. I WIN!");
				event.getChannel().sendMessage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpCdHZ1WGWBxiE2KU_-pFEYsU4qqx3zq2LcA&usqp=CAU");
			}
			else if(randy == 2) {
				event.getChannel().sendMessage("scissors");
				//display image of scissors
				event.getChannel().sendMessage("ROCK BEATS SCISSORS. YOU WIN!");
				event.getChannel().sendMessage("https://www.mnartists.org/sites/default/files/styles/large/public/artwork/media/bb8167bd7924182dbc00f7c49838074d.jpg?itok=xtMSJIqT");
			}
			else if(randy == 3) {
				event.getChannel().sendMessage("lizard");
				//display image of scissors
				event.getChannel().sendMessage("ROCK SMASHES LIZARD. YOU WIN!");
				event.getChannel().sendMessage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjh5VN6wEx6U-tWUVp1D8DGtTOFsmA1VeDtA&usqp=CAU");
				
			}
			else if(randy == 4) {
				event.getChannel().sendMessage("spock");
				//display image of scissors
				event.getChannel().sendMessage("SPOCK VAPORIZES ROCK. I WIN!");
				event.getChannel().sendMessage("https://cdn.drawception.com/images/panels/2012/5-22/PtyEdLmRpD-2.png");
			}
		}
		else if(eventContent.contains(PAPER)) {
			if(randy == 0) {
				event.getChannel().sendMessage("rock");
				//display image of rock
				event.getChannel().sendMessage("PAPER BEATS ROCK. YOU WIN!");
				event.getChannel().sendMessage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpCdHZ1WGWBxiE2KU_-pFEYsU4qqx3zq2LcA&usqp=CAU");
			}
			else if(randy == 1) {
				event.getChannel().sendMessage("paper");
				//display image of paper
				event.getChannel().sendMessage("TIE");
				event.getChannel().sendMessage("https://image.shutterstock.com/image-illustration/a4-flyer-letterhead-mockup-two-260nw-1261283758.jpg");
			}
			else if(randy == 2) {
				event.getChannel().sendMessage("scissors");
				//display image of scissors
				event.getChannel().sendMessage("SCISSORS BEATS PAPER. I WIN!");
				event.getChannel().sendMessage("https://www.artnews.com/wp-content/uploads/2020/06/shutterstock_75352165.jpg");
			}
			else if(randy == 3) {
				event.getChannel().sendMessage("lizard");
				//display image of scissors
				event.getChannel().sendMessage("LIZARD EATS PAPER. I WIN!");
				event.getChannel().sendMessage("https://cdn.drawception.com/drawings/947828/0fzAPXyhOY.png");
			}
			else if(randy == 4) {
				event.getChannel().sendMessage("spock");
				//display image of scissors
				event.getChannel().sendMessage("PAPER DISPROVES SPOCK. YOU WIN!");
				event.getChannel().sendMessage("https://trekmovie.com/wp-content/uploads/corbomite/spock_mile_diameter.jpg");
			}
		}
		else if(eventContent.contains(SCISSORS)) {
			//display image of scissors
			if(randy == 0) {
				event.getChannel().sendMessage("rock");
				//display image of rock
				event.getChannel().sendMessage("ROCK BEATS SCISSORS. I WIN!");
				event.getChannel().sendMessage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpCdHZ1WGWBxiE2KU_-pFEYsU4qqx3zq2LcA&usqp=CAU");
			}
			else if(randy == 1) {
				event.getChannel().sendMessage("paper");
				//display image of paper
				event.getChannel().sendMessage("SCISSORS BEATS PAPER. YOU WIN!");
				event.getChannel().sendMessage("https://www.artnews.com/wp-content/uploads/2020/06/shutterstock_75352165.jpg");

			}
			else if(randy == 2) {
				event.getChannel().sendMessage("scissors");
				//display image of scissors
				event.getChannel().sendMessage("TIE");
				event.getChannel().sendMessage("https://cdn5.vectorstock.com/i/1000x1000/08/59/two-scissors-vector-1120859.jpg");
			}
			else if(randy == 3) {
				event.getChannel().sendMessage("lizard");
				//display image of scissors
				event.getChannel().sendMessage("SCISSORS DECAPITATES LIZARD. YOU WIN!");
				event.getChannel().sendMessage("https://images-na.ssl-images-amazon.com/images/I/61mpns1kbWL._AC_SX569_.jpg");
			}
			else if(randy == 4) {
				event.getChannel().sendMessage("spock");
				//display image of scissors
				event.getChannel().sendMessage("SPOCK BREAKS SCISSORS. I WIN!");
				event.getChannel().sendMessage("https://thumbs.gfycat.com/FirstDependentInganue-max-1mb.gif");
			}
		}
		else if(eventContent.contains(LIZARD)) {
			//display image of paper
			if(randy == 0) {
				event.getChannel().sendMessage("rock");
				//display image of rock
				event.getChannel().sendMessage("ROCK SMASHES LIZARD. I WIN!");
				event.getChannel().sendMessage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjh5VN6wEx6U-tWUVp1D8DGtTOFsmA1VeDtA&usqp=CAU");
			}
			else if(randy == 1) {
				event.getChannel().sendMessage("paper");
				//display image of paper
				event.getChannel().sendMessage("LIZARD EATS PAPER. YOU WIN!");
				event.getChannel().sendMessage("https://cdn.drawception.com/drawings/947828/0fzAPXyhOY.png");
			}
			else if(randy == 2) {
				event.getChannel().sendMessage("scissors");
				//display image of scissors
				event.getChannel().sendMessage("SCISSORS DECAPITATES LIZARD. I WIN!");
				event.getChannel().sendMessage("https://cdn.drawception.com/drawings/947828/0fzAPXyhOY.png");
			}
			else if(randy == 3) {
				event.getChannel().sendMessage("lizard");
				//display image of scissors
				event.getChannel().sendMessage("TIE");
				event.getChannel().sendMessage("https://previews.123rf.com/images/mass399/mass3991806/mass399180600059/104671894-two-lizards-are-breeding-love-.jpg");
			}
			else if(randy == 4) {
				event.getChannel().sendMessage("spock");
				//display image of scissors
				event.getChannel().sendMessage("LIZARD POISONS SPOCK. YOU WIN!");
				event.getChannel().sendMessage("https://www.momsminivan.com/wp-content/uploads/2020/06/lizard-spock.jpg");
			}
		}
		else if(eventContent.contains(SPOCK)) {
			//display image of paper
			if(randy == 0) {
				event.getChannel().sendMessage("rock");
				//display image of rock
				event.getChannel().sendMessage("SPOCKS VAPORIZES ROCK. YOU WIN!");
				event.getChannel().sendMessage("https://cdn.drawception.com/images/panels/2012/5-22/PtyEdLmRpD-2.png");
			}
			else if(randy == 1) {
				event.getChannel().sendMessage("paper");
				//display image of paper
				event.getChannel().sendMessage("PAPER DISPROVES SPOCK. I WIN!");
				event.getChannel().sendMessage("https://trekmovie.com/wp-content/uploads/corbomite/spock_mile_diameter.jpg");
			}
			else if(randy == 2) {
				event.getChannel().sendMessage("scissors");
				//display image of scissors
				event.getChannel().sendMessage("SPOCK BREAKS SCISSORS. YOU WIN!");
				event.getChannel().sendMessage("https://thumbs.gfycat.com/FirstDependentInganue-max-1mb.gif");
			}
			else if(randy == 3) {
				event.getChannel().sendMessage("lizard");
				//display image of scissors
				event.getChannel().sendMessage("LIZARD POISONS SPOCK. I WIN!");
				event.getChannel().sendMessage("https://www.momsminivan.com/wp-content/uploads/2020/06/lizard-spock.jpg");
			}
			else if(randy == 4) {
				event.getChannel().sendMessage("spock");
				//display image of scissors
				event.getChannel().sendMessage("TIE");
				event.getChannel().sendMessage("https://media.comicbook.com/2018/09/star-trek-the-next-generation-two-spocks-1134261-1280x0.jpeg");
			}
		}
	}
	
}
