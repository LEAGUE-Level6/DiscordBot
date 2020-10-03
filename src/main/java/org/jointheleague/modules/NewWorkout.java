package org.jointheleague.modules;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class NewWorkout extends CustomMessageCreateListener {
	private static final String COMMAND = "!newWorkout";

	public NewWorkout(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"You can add a new workout to the list of known workouts but you must specificy body location [core,arms,legs]. Separate the parameters by dashes."
						+ "Add the exercise first and then the body location.");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			String message = event.getMessageContent().replaceAll(" ", "").replace("!newWorkout", "");

			int dashCount = 0;

			// counts for the number of dashes
			for (int i = 0; i < message.length(); i++) {
				if (message.charAt(i) == '-') {
					dashCount++;
				}
			}

			// makes sure there is a dash separating each parameter
			if (dashCount != 2) {
				event.getChannel().sendMessage("Please put in the correct number of dashes"
						+ "one after !newWorkout and another after the name of the workout (max of 2 dashes)");
			}

			// finds location of the first dash
			int firstDash = message.indexOf('-');
			// finds location of the second dash
			int secDash = message.substring(firstDash + 1).indexOf('-') + 1 + firstDash;

			// makes a substring of the exercise
			String exercise = message.substring(firstDash + 1, secDash);
			// makes a substring of the body location
			String part = message.substring(secDash + 1);
			
			if (part.equals(" ") | !part.equals("arms") | !part.equals("legs") | !part.equals("core")) {
				try {
					FileWriter fw = new FileWriter("src/main/java/org.jointheleague.modules/unorganizedWorkouts.txt",
							true);
					fw.write("\n" + exercise);
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				event.getChannel().sendMessage("Please put in a body location or spell it correctly (no uppercase)");

			} else {
				if (part.equals("arms")) {
					try {
						FileWriter fw = new FileWriter("src/main/java/org.jointheleague.modules/armWorkouts.txt", true);
						fw.write("\n" + exercise);
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (part.equals("legs")) {
					try {
						FileWriter fw = new FileWriter("src/main/java/org.jointheleague.modules/legWorkouts.txt", true);
						fw.write("\n" + exercise);
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (part.equals("core")) {
					try {
						FileWriter fw = new FileWriter("src/main/java/org.jointheleague.modules/coreWorkouts.txt", true);
						fw.write("\n" + exercise);
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
