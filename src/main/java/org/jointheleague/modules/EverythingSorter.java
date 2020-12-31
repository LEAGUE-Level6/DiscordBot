package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class EverythingSorter extends CustomMessageCreateListener{
	private static final String COMMAND = "!sortStuff";
	private static final String ALPHABETIZE = "!alphabetize";
	private static final String NUMBERS = "!sortNumbers";
	private static final String COLORS = "!sortColors";
	private static final String SEECOLORS = "!seeColors";
	private static final String DEACTIVATE = "!deactivate";
	boolean sorterInMotion = false;
	boolean alphabetizingStuff = false;
	boolean numberSorting = false;
	boolean colorSorting = false;
	boolean seeColors = false;
	public EverythingSorter(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Allows you to sort all text-related values (words, colors, numbers).");
	}
	
	public void handle(MessageCreateEvent event) {
		if(sorterInMotion == true && alphabetizingStuff == true&& event.getMessageContent().contains("i.e.") == false) {
			String[] wordsToBeSorted = event.getMessageContent().split(" ");
			String[] sortedStrings = StringSorter(wordsToBeSorted);
			String sortedValues = "";
			for(int i = 0; i<sortedStrings.length;i++) {
				sortedValues +=sortedStrings[i]+"\n";
			}
			event.getChannel().sendMessage("-----------------");
			event.getChannel().sendMessage(sortedValues);
			event.getChannel().sendMessage("-----------------");
			event.getChannel().sendMessage("If you want to sort more values, just specify the values (!alphabetize, !sortNumbers, !sortColors, !seeColors)");
			event.getChannel().sendMessage("To deactivate the bot, do the command !deactivate");
			alphabetizingStuff = false;
			//number them and send to stream
		}
		else if(sorterInMotion == true && numberSorting == true){
			String[] numsNotSorted = event.getMessageContent().split(" ");
			Double[] doubs = new Double[numsNotSorted.length];
			for(int i = 0;i< numsNotSorted.length;i++) {
				if(numsNotSorted[i].contains(".")) {
					doubs[i] = Double.parseDouble(numsNotSorted[i]);
				}else {
					String number = numsNotSorted[i]+".0";
				doubs[i] = Double.parseDouble(number);
				}
			}
			Double[] sortedDoubs = NumberSorter(doubs);
			String sortedValues = "";
			for(int i = 0; i<sortedDoubs.length;i++) {
				String dub = ""+sortedDoubs[i];
				if(dub.endsWith(".0")) {
					dub = dub.substring(0, dub.length()-2);
				}
				sortedValues +=dub+"\n";
			}
			event.getChannel().sendMessage("-----------------");
			event.getChannel().sendMessage(sortedValues);
			event.getChannel().sendMessage("-----------------");
			event.getChannel().sendMessage("If you want to sort more values, just specify the values (!alphabetize, !sortNumbers, !sortColors, !seeColors)");
			event.getChannel().sendMessage("To deactivate the bot, do the command !deactivate");
			numberSorting = false;
			//number and send to stream
		}
		else if(sorterInMotion == true && colorSorting == true&& event.getMessageContent().contains("i.e.") == false) {
			String[] colorsToBeSorted = event.getMessageContent().split(" ");
			System.out.println("Last element: "+colorsToBeSorted[colorsToBeSorted.length-1]);
			for(int i = 0; i<colorsToBeSorted.length; i++) {
				System.out.println(colorsToBeSorted[i]);
			}
			System.out.println("-------------------------------");
			String[] sortedColors = ColorSorter(colorsToBeSorted);
			String sortedValues = "";
			for(int i = 0; i<sortedColors.length;i++) {
				sortedValues +=sortedColors[i]+"\n";
			}
			event.getChannel().sendMessage("-----------------");
			event.getChannel().sendMessage(sortedValues);
			event.getChannel().sendMessage("-----------------");
			event.getChannel().sendMessage("If you want to sort more values, just specify the values (!alphabetize, !sortNumbers, !sortColors, !seeColors)");
			event.getChannel().sendMessage("To deactivate the bot, do the command !deactivate");
			colorSorting = false;
			//number and send to stream
		}
	else if(sorterInMotion == true) {
			if (event.getMessageContent().contains(ALPHABETIZE)) {
				
				String cmd = event.getMessageContent().replaceAll(" ", "").replace("!alphabetize","");
				
				if(cmd.equals("")) {
					event.getChannel().sendMessage("Enter in your words, seperated by spaces: i.e. nine kick food");
alphabetizingStuff = true;
				}else {
				}
		}
if (event.getMessageContent().contains(DEACTIVATE)) {
				
				String cmd = event.getMessageContent().replaceAll(" ", "").replace("!deactivate","");
				
				if(cmd.equals("")) {
					event.getChannel().sendMessage("deanBot deactivated. To activate, type command !sortStuff");
sorterInMotion = false;
				}else {
				}
		}
			else if (event.getMessageContent().contains(NUMBERS)) {
				
				String cmd = event.getMessageContent().replaceAll(" ", "").replace("!sortNumbers","");
				
				if(cmd.equals("")) {
					event.getChannel().sendMessage("Enter in your values, seperated by spaces: i.e. 5.3490 102.3 5.4924");
numberSorting = true;
				}else {
				}
		}	else if (event.getMessageContent().contains(COLORS)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!sortColors","");
			
			if(cmd.equals("")) {
				event.getChannel().sendMessage("Enter in your colors, seperated by spaces: i.e. orange yellow green");
colorSorting = true;
			}else {
			}
	}
		else if(event.getMessageContent().contains(SEECOLORS)) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!seeColors","");
			System.out.println("thing triggered");
			if(cmd.equals("")) {
				System.out.println("seeingColors");
				String[] rgbs = getRGBS();
				String[] colors = getColorWheel();
				String allOfThem = "";
						for(int i = 0; i<colors.length-1;i++) {
							allOfThem += colors[i]+" -RGB: "+rgbs[i]+"\n";
						}	
						event.getChannel().sendMessage("-----------------");
						event.getChannel().sendMessage(allOfThem);
						event.getChannel().sendMessage("-----------------");
						event.getChannel().sendMessage("If you want to sort more values, just specify the values (!alphabetize, !sortNumbers, !sortColors, !seeColors)");
						event.getChannel().sendMessage("To deactivate the bot, do the command !deactivate");
			}else {
				
			}
		}
		}
	else if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!sortStuff","");
			
			if(cmd.equals("")) {
				sorterInMotion = true;
				event.getChannel().sendMessage("The following values can be sorted:");
				event.getChannel().sendMessage("Numbers: these values will be sorted by size. (includes decimals) -called by command !sortNumbers");
				event.getChannel().sendMessage("Words: these values will be sorted alphbetically. -called by command !alphabetize");
				event.getChannel().sendMessage("Colors: these will be sorted using the rainbow/color wheel -see all colors eligeble using the command !seeColors | sorting is called using command !sortColors (is sorted from left to right starting with the closest color to red)");
				event.getChannel().sendMessage("You can now  enter in the command that you want to use to sort. (!alphabetize, !sortNumbers, !sortColors, !seeColors)");
			}else {
				
			}
	}
	}

	
 
	public String[] StringSorter(String[] strings) {
		String temp;
	      for(int i=0; i<strings.length-1; i++)
	      {
	          for(int j=0; j<strings.length-1; j++)
	          {
	              if(strings[j].compareTo(strings[j+1])>0)
	              {
	                  temp=strings[j];
	                  strings[j]=strings[j+1];
	                  strings[j+1]=temp;
	              }
	          }
	      }
			
	      for(int i=0;i<strings.length-1;i++)
	      {
	          System.out.println(strings[i]);
	      }
		return strings;
	}
	
	public Double[] NumberSorter(Double[] doubs) {
		System.out.println("numbers sorting");
		for (int i = 0; i < doubs.length - 1; i++) {
			for(int j = 0; j<doubs.length-1;j++) {
			if (doubs[j] <= doubs[j + 1]) {
			} else {
				double firstElement = doubs[j];
				doubs[j] = doubs[j + 1];
				doubs[j + 1] = firstElement;
			}
			}
		}
		for(int i = 0; i<doubs.length; i++) {
			System.out.println(doubs[i]);
		}
		return doubs;
	}
	
	
	public String[] ColorSorter(String[] colors) {
		System.out.println("colors sorting");
		String[] wheel = getColorWheel();
		String[] rgbs = getRGBS();
		for(int i = 0;i<colors.length;i++) {
			colors[i] = colors[i].toLowerCase();
		}
		for (int i = 0; i < colors.length - 1; i++) {
			for(int j = 0; j<colors.length-1;j++) {
				int color1Index = 0;
				int color2Index = 0;
				
				for(int findColorIndex = 0; findColorIndex<wheel.length; findColorIndex++) {
					if(colors[j].equals(wheel[findColorIndex])) {
						color1Index = findColorIndex;
					}
					else if(colors[j+1].equals(wheel[findColorIndex])) {
						color2Index = findColorIndex;
					}
				}
			if (color1Index <= color2Index) {
			} else {
				String firstElement = colors[j];
				colors[j] = colors[j+1];
				colors[j + 1] = firstElement;
			}
			}
		}
		String[] colorsWithValue = new String[colors.length];
		for(int i = 0; i<colors.length;i++) {
			int colorIndex = 0;

			for(int findColorIndex = 0; findColorIndex<rgbs.length-1; findColorIndex++) {
				if(colors[i].equals(wheel[findColorIndex])) {
					colorIndex = findColorIndex;
				}
			}
			colorsWithValue[i] = colors[i] +" -RGB: #"+rgbs[colorIndex];
		}
		for(int i = 0; i<colorsWithValue.length;i++) {
			System.out.println(colorsWithValue[i]);
		}
		return colorsWithValue;

	}
	public String[] getColorWheel() {
		ArrayList <String> addColors = new ArrayList <String>();
		addColors.add("red");
		addColors.add("red-orange");
		addColors.add("orange");
		addColors.add("yellow-orange");
		addColors.add("lemon yellow");
		addColors.add("yellow");
		addColors.add("chartreuse");
		addColors.add("yellow-green");
		addColors.add("light green");
		addColors.add("lime green");
		addColors.add("green");
		addColors.add("olive green");
		addColors.add("dark green");
		addColors.add("blue-green");
		addColors.add("turquoise");
		addColors.add("aquamarine");
		addColors.add("sky blue");
		addColors.add("cobalt blue");
		addColors.add("blue");
		addColors.add("dark blue");
		addColors.add("blue-voilet");
		addColors.add("voilet");
		addColors.add("red-voilet");
		addColors.add("maroon");
		addColors.add("dark red");
		String[] colors = new String[addColors.size()];
		for(int i = 0; i <colors.length; i++) {
			colors[i] = addColors.get(i);
		}
		return colors;
	}
	public String[] getRGBS() {
		ArrayList <String>addRgb = new ArrayList<String>(); 
		addRgb.add("235, 52, 52");
		addRgb.add("235, 113, 52");
		addRgb.add("235, 113, 52");
		addRgb.add("235, 143, 52");
		addRgb.add("235, 198, 52");
		addRgb.add("235, 198, 52");
		addRgb.add("251, 255, 0");
		addRgb.add("221, 255, 0");
		addRgb.add("221, 255, 0");
		addRgb.add("195, 255, 0");
		addRgb.add("157, 255, 0");
		addRgb.add("96, 214, 28");
		addRgb.add("50, 191, 69");
		addRgb.add("41, 97, 48");
		addRgb.add("33, 207, 192");
		addRgb.add("33, 207, 192");
		addRgb.add("41, 202, 217");
		addRgb.add("19, 217, 235");
		addRgb.add("0, 234, 255");
		addRgb.add("16, 150, 227");
		addRgb.add("0, 98, 255");
		addRgb.add("94, 51, 212");
		addRgb.add("128, 0, 255");
		addRgb.add("156, 65, 112");
		addRgb.add("110, 29, 45");
		addRgb.add("84, 1, 18");
		String[] rgb = new String[addRgb.size()];
		for(int i = 0; i <rgb.length; i++) {
			rgb[i] = addRgb.get(i);
		}
		return rgb;
	}
	}
