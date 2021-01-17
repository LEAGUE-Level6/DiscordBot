package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class NewWorkout extends CustomMessageCreateListener {
	private static final String COMMAND = "!newWorkout";

	long botID = 754465720934596650L;

	private int completedCategories = 0;
	private String exerciseName;
	private String part;
	private String link;
	private static boolean checkingDuplicates = false;

	public NewWorkout(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"You can add a new workout to the list of known workouts but you must specificy body location. When adding the name of the workout start the message with "
						+ "!name and when it asks for the location start the message with !part.");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageAuthor().getId() != botID && event.getMessageContent().contains(COMMAND)) {

			event.getChannel().sendMessage(
					"Type in the name of the workout and remember to type !name at the beginning of the message");
		}

		if (event.getMessageContent().toLowerCase().startsWith("!name") && completedCategories == 0) {
			String nameOfWorkout = event.getMessageContent().replaceAll("!name", "").trim();
			exerciseName = nameOfWorkout;

			event.getChannel()
					.sendMessage("Copy and past a link to an image/video showing the workout into the text box "
							+ "(right click an image and press \"copy image address\"). Just type !link w/o a link to skip this but remember to put !link at the beginning of this message.");
			// completedCategories variable is to make sure both categories have been
			// fulfilled for the final success message
			completedCategories++;
		}

		if (event.getMessageContent().toLowerCase().startsWith("!link") && completedCategories == 1) {
			link = event.getMessageContent().replaceAll("!link", "").trim();

			event.getChannel().sendMessage(
					"Type in the part of the exercise: arms, legs, and core. Remember to put in !part at the beginning of the message");

			completedCategories++;
		}

		if (event.getMessageContent().toLowerCase().startsWith("!part") && completedCategories == 2) {
			String nameOfPart = event.getMessageContent().replaceAll("!part", "").trim();

			if (!nameOfPart.equals("arms") && !nameOfPart.equals("legs") && !nameOfPart.equals("core")) {

				event.getChannel().sendMessage("Put in a body location or spell it correctly (no uppercase)");

			} else {
				completedCategories++;
				part = nameOfPart;
			}
		}

		if (completedCategories == 3) {
			addExercise(event, part, exerciseName, link);
			completedCategories++;

			// then ask the user for a link to the image
			// show that image when the random workout command is called by simply calling
			// event.getchannel.sendmessage("link");
		}

	}

	public static void addExercise(MessageCreateEvent event, String part, String exerciseName, String link) {
		String message = exerciseName + ":" + link;

		if (part.equals("arms")) {
			try {

				FileWriter fw = new FileWriter(Workout.armFile, true);

				if (Workout.armFile.length() == 0) {
					fw.write(message);
				} else if (!anyDuplicates(event, exerciseName, Workout.armFile)) {
					// if there are no duplicates then proceed
					fw.write("\n" + message);
					event.getChannel().sendMessage("A new workout has been added");
				}

				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (part.equals("legs")) {
			try {
				FileWriter fw = new FileWriter(Workout.legFile, true);
				if (Workout.legFile.length() == 0) {
					fw.write(message);
				} else if (!anyDuplicates(event, exerciseName, Workout.legFile)) {
					// if there are no duplicates then proceed
					fw.write("\n" + message);
					event.getChannel().sendMessage("A new workout has been added");
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (part.equals("core")) {
			try {
				FileWriter fw = new FileWriter(Workout.coreFile, true);
				if (Workout.coreFile.length() == 0) {
					fw.write(message);
				} else if (!anyDuplicates(event, exerciseName, Workout.coreFile)) {
					// if there are no duplicates then proceed
					fw.write("\n" + message);
					event.getChannel().sendMessage("A new workout has been added");
				}
				
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param event
	 * @param newWorkout
	 * @param file
	 * @return
	 */
	public static boolean anyDuplicates(MessageCreateEvent event, String newWorkout, File file) {
		//splits the new proposed workout into separate words for comparison later
		String[] splitWorkout = newWorkout.split(" ");

		ArrayList<String> similarWorkouts = new ArrayList<String>();
				
		ArrayList<String> workoutsFromFile = fileToList(file);

		// make it so that if the new workout contains a word similar to past workouts
		// have it return a message
		// the message contains all the workouts with similar names
		// asks the user if any matches and if not it continues as normal

		// break file into an arraylist --> compare list

		for (int i = 0; i < workoutsFromFile.size(); i++) {
			
			for (int j = 0; j < splitWorkout.length; j++) {
				if (workoutsFromFile.get(i).contains(splitWorkout[j])) {
					similarWorkouts.add(workoutsFromFile.get(i));
					System.out.println("Similar workout detected!");
				}
			}

			// checks if the exercise in the list and the new exercise are the exact same
			if (workoutsFromFile.get(i).trim().toLowerCase().replaceAll(" ", "").equals(newWorkout.toLowerCase())) {
				// automatically returns true when they are the same word
				event.getChannel().sendMessage("This workout has already been added. Put in a different workout");
				return true;
			}
		}
		
		//figure out how to ask the user to determine if any of the words in the list are duplicates
		
		/*
		 * if (similarWorkouts.size() > 0) { // following lines convert the array into a
		 * string to be printed (similarWorkouts = array, simWorkouts = string) String
		 * simWorkouts = ""; for (int i = 0; i < similarWorkouts.size(); i++) {
		 * simWorkouts += similarWorkouts.get(i); simWorkouts += "\n"; } // gives user
		 * the ability to see if 2 workouts are actually the same or just contain
		 * similar words event.getChannel().sendMessage(
		 * "Checking for duplicates: Are any of these workouts the same as the one you typed in? Type !y or !n."
		 * ); //types out each workout to discord
		 * event.getChannel().sendMessage(simWorkouts); if (checkingDuplicates &&
		 * event.getMessageContent().toLowerCase().contains("!y")) { event.getChannel().
		 * sendMessage("This workout has already been added. Put in a different workout"
		 * ); return true; } else { event.getChannel().
		 * sendMessage("This workout has not been added yet so it will be added to the list."
		 * ); return false; } } else { // there are no similar workouts return false; }
		 */

	}

	public static ArrayList<String> fileToList(File file) {
		String filename = file.getPath();
		BufferedReader br;
		String exercise;

		ArrayList<String> similarWorkouts = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(filename));

			try {
				for (int i = 0; i < filename.length(); i++) {
					exercise = br.readLine();
					String linklessExercise = exercise.substring(0, exercise.indexOf(':'));
					similarWorkouts.add(linklessExercise);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return similarWorkouts;

	}
}
