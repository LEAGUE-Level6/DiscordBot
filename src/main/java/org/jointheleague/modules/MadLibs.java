package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class MadLibs extends CustomMessageCreateListener {

	public MadLibs(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed("!madLibs",
				"Begin a game of Mad Libs. Use the command !madAdd to add words to your Mad Lib.");
	}

	boolean playing = false;
	int story = -1;

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		String input = event.getMessageContent();

		if (!event.getMessageAuthor().isYourself()) {
			if (input.contains("!madLibs")) {

				playing = true;
				story = new Random().nextInt(3);
				event.getChannel().sendMessage("You are now playing Mad Libs");
				if (story == 0) {
					event.getChannel().sendMessage("Please enter an occupation, name, verb, adjective, noun, verb, "
							+ "noun, and verb in that order separated by a \", \"");
				} else if (story == 1) {
					event.getChannel().sendMessage("Please enter a food, name, place, animal (plural), occupation "
							+ "(plural), feeling, thing (plural), and article of clothing in that order separated "
							+ "by a \", \"");
				} else if (story == 2) {
					event.getChannel().sendMessage("Please enter a name, adjective, thing, color, animal, verb, thing, "
							+ "and adjective in that order separated by a \", \"" );
				}

			} else if (input.contains("!madAdd") && playing == true) {

				String message = event.getMessageContent().replace("!madAdd ", "");
				String[] words = message.split(", ");
				if (words.length > 8) {
					event.getChannel().sendMessage("You entered too many words! Please try again.");
				} else if (words.length < 8) {
					event.getChannel().sendMessage("You entered too little words! Please try again.");
				} else if (story == 0) {
					event.getChannel().sendMessage("Today a " + words[0] + " named " + words[1] + " came to our school "
							+ "to talk to us about her job. She said the most important skill you need to know to do her "
							+ "job is to be able to " + words[2] + " around a " + words[3] + " " + words[4]
							+ ". She said it was easy for her to learn her job because " + "she loved to " + words[5]
							+ " when she was my age. If you're considering her profession,"
							+ " I hope you can be near a " + words[6]
							+ ". That's very important! If you get too distracted"
							+ " in that situation you won't be able to " + words[7] + "!");
					playing = false;
				} else if (story == 1) {
					event.getChannel().sendMessage("Say \"" + words[0] + ",\" the photographer said as the camera "
							+ "flashed! " + words[1] + " and I had gone to " + words[2] + " to get our photos "
							+ "taken today. In the first photo, what we really wanted was a picture of us dressed "
							+ "as " + words[3] + " pretending to be a " + words[4] + ". When we saw the proofs of "
							+ "it, I was a bit " + words[5] + " because it looked different than in my head. However, "
							+ "the second photo was exactly what I wanted. We both looked like " + words[6] 
							+ " wearing " + words[7] + ", exactly what I had in mind!");
					playing = false;
				} else if (story == 2) {
					event.getChannel().sendMessage("My friend " + words[0] + " has the funniest pets. Their pet "
							+ "named " + words[1] + " " + words[2] + " is a large, " + words[3] + " " + words[4] 
							+ " that could " + words[5] + " all day. I tried to teach him to play fetch, but "
							+ "every time I threw the " + words[6] + " he let out a " + words[7] + " howl. "
							+ "He is not the brightest " + words[4] + "!");
					playing = false;
				}
			}
		}
	}
}
