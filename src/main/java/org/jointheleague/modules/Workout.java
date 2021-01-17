package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discord_bot_example.Utilities;
import org.jointheleague.modules.pojo.HelpEmbed;

import okio.Buffer;

public class Workout extends CustomMessageCreateListener {
	private static final String COMMAND = "!workout";

	long botID = 754465720934596650L;
	// saves the part of the body
	// prevents the if statement (workoutType != ....) from running over and over
	// again
	boolean enteredPart = true;
	// prevents the else statement from running over and over again
	boolean completed = true;
	static String workoutType;

	// specific files to access later depending on which category the user inputs
	static File legFile = new File("src/main/java/org/jointheleague/modules/legWorkouts.txt");
	static File armFile = new File("src/main/java/org/jointheleague/modules/armWorkouts.txt");
	static File coreFile = new File("src/main/java/org/jointheleague/modules/coreWorkouts.txt");

	public Workout(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Gives a certain exercise for different parts of the body [core, arms, legs] and add the name of the workout after !workout (e.g. !workout"
						+ " legs)");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageAuthor().getId() != botID && event.getMessageContent().contains(COMMAND)) {

			// gets the part of the body [core, legs, arms] from the initial message and
			// saves it into workoutType
			String workoutPart = event.getMessageContent().replace("!workout", "").trim();
			workoutType = workoutPart;
			enteredPart = false;
			completed = false;
		}

		if (!enteredPart && !workoutType.equals("legs") && !workoutType.equals("arms") && !workoutType.equals("core")) {
			event.getChannel()
					.sendMessage("Put in the right category after the initial message (!workout) arms or legs or core");
			//confirms that a part was entered 
			enteredPart = !enteredPart;

		} else if (!completed) {
			
			String entireMessage = returnWorkout(event);
			//makes sure it works in the presence or absence of the :
			if(entireMessage.contains(":")) {
				String link = entireMessage.substring(entireMessage.indexOf(':') + 1);
				String exercise = entireMessage.substring(0, entireMessage.indexOf(':'));
				event.getChannel().sendMessage("Your workout is " + exercise + ".");
				event.getChannel().sendMessage(link);
			}
			else {
				event.getChannel().sendMessage(entireMessage);
			}

			completed = !completed;
		}
	}

	public static String returnWorkout(MessageCreateEvent event) {
		//PROBLEM TO FIX FOR NEXT WEEK: THE FILE WILL NOT RETURN A LINE IF THERE IS ONLY 1 LINE IN THE FILE (MAY BE FIXED BY JUST RETURNING THAT LINE BUT WILL HAVE TO SEE LATER)
		//Another problem to fix is making sure the message "there is currently nothing in this ...." to show up
		
		if (workoutType.equals("arms")) {
			// Check if there is nothing in the file
			String randWorkout = readRandomLineFromFile(armFile);
			int failCounter = 0; 
			
		
			while(randWorkout.length()<2) {
				randWorkout = readRandomLineFromFile(armFile);
				failCounter++;
				if(failCounter==100) {
					event.getChannel().sendMessage("There is currently nothing in this category. Be the first to add a workout!");
					break;
				}
			}
			
			return randWorkout;
			
		}

		else if (workoutType.equals("legs")) {
			// Check if there is nothing in the file
			String randWorkout = readRandomLineFromFile(legFile);
			int failCounter = 0; 
			
			while(randWorkout.length()<2) {
				randWorkout = readRandomLineFromFile(legFile);
				failCounter++;
				if(failCounter==100) {
					event.getChannel()
					.sendMessage("There is currently nothing in this category. Be the first to add a workout!");
					break;
				}
			}
			return readRandomLineFromFile(legFile);
		}
		
		else if (workoutType.equals("core")) {
			// Check if there is nothing in the file
			String randWorkout = readRandomLineFromFile(coreFile);
			int failCounter = 0; 
			
			
			
			while(randWorkout.length()<2) {
				randWorkout = readRandomLineFromFile(coreFile);
				failCounter++;
				if(failCounter==100) {
					event.getChannel()
					.sendMessage("There is currently nothing in this category. Be the first to add a workout!");
					break;
				}
			}
			return readRandomLineFromFile(coreFile);
		}
		return null;
	}

	public static String readRandomLineFromFile(String filename) {
		String word = "";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			int totalLines = getTotalLinesInFile(filename);
		
			if(totalLines!=0) {
				int randomNumber = new Random().nextInt(totalLines);
				br.close();
				br = new BufferedReader(new FileReader(filename));
				
				for (int i = 0; i <= randomNumber; i++) {
					word = br.readLine();
				}
				
				br.close();
			}
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Could not find file.", "ERROR", 1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return word;
	}

	public static String readRandomLineFromFile(File file) {
		String fileName = file.getPath();

		return readRandomLineFromFile(fileName);
	}

	public static int getTotalLinesInFile(String filename) {
		int totalLines = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();

			while (line != null) {
				totalLines++;
				line = br.readLine();
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(totalLines);
		return totalLines;
		
	}
	
	public static int getTotalLinesInFile(File file) {
		String fileName = file.getPath();
		
		return getTotalLinesInFile(fileName);
	}

}
