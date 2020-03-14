package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class FEHStatListener extends CustomMessageCreateListener {
	String webpageRead;
	ArrayList<String> heroNamesRaw = new ArrayList<String>();
	ArrayList<String> heroNamesNoColon = new ArrayList<String>();
	ArrayList<String> heroNamesColon = new ArrayList<String>();

	public FEHStatListener(String channelName) { //thank you http://zetcode.com/java/readwebpage/
		super(channelName);
		try {
			URL webpage = new URL("https://feheroes.gamepedia.com/Level_40_stats_table");
			BufferedReader br = new BufferedReader(new InputStreamReader(webpage.openStream()));
			String str = "";
			while ((str = br.readLine()) != null) {
				webpageRead += str;
				webpageRead += "\n";
			}
			br.close();
			FileWriter fw = new FileWriter("src/main/resources/FEHStats.txt");
			fw.write(webpageRead);
			fw.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!fehstats")) {
			heroNamesRaw.clear();
			if (event.getMessageContent().length() == 9) {
				event.getChannel().sendMessage("Please specify which unit to search for :)");
			} else {
				String unitName = event.getMessageContent().substring(10);
				unitName = unitName.toLowerCase();
				int i = howMany(unitName);
				cleanNames();
				if (i == 1) { //1 match
					event.getChannel().sendMessage("There is " + i + " match for \"" + unitName + "\".");
				} else if (i == 0) { //no matches
					event.getChannel().sendMessage("There are " + i + " matches for " + unitName + ". Sorry!");
				} else { //more than 1 match
					event.getChannel().sendMessage("There are " + i + " matches for \"" + unitName + "\".");
					String temp = "";
					for (int j = 0; j < heroNamesColon.size(); j++) {
						temp += heroNamesColon.get(j);
						if (j < (heroNamesColon.size() - 2)) {
							temp += ", ";
						} else if (j < (heroNamesColon.size() - 1)){
							temp += ", or ";
						}
					}
					event.getChannel().sendMessage("Would you like to see stats for " + temp + "?");
					event.getChannel().sendMessage(event.getMessageContent());
					
				}
			}
		}
	}
	
	ArrayList<String> lines = new ArrayList<String>();
	public int howMany(String heroName) {
		int timesF = 0;
		String str = "";
		//make sure the capitalization is correct
		char[] name = heroName.toCharArray();
		name[0] = Character.toUpperCase(name[0]);
		for (int i = 1; i < name.length; i++) {
			 name[i] = Character.toLowerCase(name[i]);
		}
		String unitName = "";
		for (char c: name) {
			unitName = unitName + c;
		}
		System.out.println("converted " + heroName + " to " + unitName);
		//get the one big table line
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/FEHStats.txt"));
			while ((str = br.readLine()) != null) {
				if (str.contains(unitName)) {
					lines.add(str);
					break;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str.contains(unitName)) {
			System.out.println("collected the string to search");
			//find the number of unit names within the string
			int index = 675; //uh, i counted?
			int tempIndex = 0;
			while (tempIndex != -1) {
				tempIndex = str.indexOf(unitName, index);
				index = tempIndex + 1;
				if (tempIndex != -1 && (timesF % 12 == 3)) {
					heroNamesRaw.add(str.substring(tempIndex, tempIndex+31));
				}
				timesF++;
			}
		} else {
			return 0;
		}
		System.out.println("finished searching");
		return timesF/12;
	}
	
	public void cleanNames() {
		for (int i = 0; i < heroNamesRaw.size(); i++) {
			String s = heroNamesRaw.get(i);
			s = s.replace("_", " ");
			//remove ending
			if (s.contains("Fa")) {
				int face = s.lastIndexOf("Fa");
				s = s.substring(0, face-1);
			}
			heroNamesNoColon.add(s);
			//insert colon
			int colonInsert = s.indexOf(" ");
			s = s.substring(0, colonInsert) + ":" + s.substring(colonInsert);
			heroNamesColon.add(s);
		}
	}
	
	String statsS = "";
	
	public void findStats(String heroName) {
		int timesF = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/FEHStats.txt"));
			String str = "";
			while ((str = br.readLine()) != null) {
				if (str.contains(heroName)) {
					timesF++;
				}
				if (timesF == 3) {
					if (str.contains("</td><td>")) {
						statsS = str;
						break;
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
