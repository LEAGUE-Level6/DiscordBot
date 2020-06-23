package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;



public class BogoSorterListener extends CustomMessageCreateListener  {
	public static String command = "!bogoSort";

	public BogoSorterListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		if(message.contains(command)) {
			int startIndex = message.indexOf(command);
			String remaining = message.substring(startIndex + command.length());
			int openCurly = 0;
			int closedCurly = 0;
			boolean foundOpen = false;
			boolean foundClosed = false;
			for(int i = 0; i < remaining.length(); i++) {
				if(remaining.charAt(i) == '{' && !foundOpen) {
					foundOpen = true;
					openCurly = i;
				}
				else if(remaining.charAt(i) == '}' && !foundClosed) {
					foundClosed = true;
					closedCurly = i;
				}
			}
			if(openCurly >= closedCurly - 1 || !foundOpen || !foundClosed) {
				event.getChannel().sendMessage("ERROR: Incorrect format.");
			}
			else {
				remaining = remaining.substring(openCurly + 1, closedCurly);
				remaining = remaining.replaceAll(" ", "");
				boolean correct = true;
				
				String[] posibleNumbers = remaining.split(",");
				ArrayList<Double> numbers = new ArrayList<>();
				try {
					for(String s: posibleNumbers) {
						numbers.add(Double.parseDouble(s));
					}
				} catch(Exception e) {
					event.getChannel().sendMessage("ERROR: Incorrect format for number list.");
					correct = false;
				}
				if(correct) {
					double[] nums = new double[numbers.size()];
					for(int i = 0; i < numbers.size(); i++) {
						nums[i] = numbers.get(i);
					}
					
					event.getChannel().sendMessage("Sorting");
					long startTime = System.currentTimeMillis();
					Random random = new Random();
					while(!checkSorted(nums)) {
						int index1 = random.nextInt(nums.length);
						int index2 = random.nextInt(nums.length);
						while(index1 == index2) {
							index2 = random.nextInt(nums.length);
						}
						double temp = nums[index1];
						nums[index1] = nums[index2];
						nums[index2] = temp;
					}
					event.getChannel().sendMessage(Arrays.toString(nums) + "\n" + "Sorted in " + (System.currentTimeMillis() - startTime) + " milliseonds");

				}
				
				
			}
		}
	}
	
	private boolean checkSorted(double[] arr) {
		double lastNum = arr[0];
		for(double d: arr) {
			if(d >= lastNum) {
				lastNum = d;
			}
			else {
				return false;
			}
		}
		return true;
		
	}

}
