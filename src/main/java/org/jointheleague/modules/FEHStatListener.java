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

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class FEHStatListener extends CustomMessageCreateListener {
	String webpageRead;

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
			if (event.getMessageContent().length() == 9) {
				event.getChannel().sendMessage("Please specify which unit to search for :)");
			} else {
				String unitName = event.getMessageContent().substring(10);
				unitName = unitName.toLowerCase();
				int i = howMany(unitName);
				System.out.println("There were " + i + " matches for " + unitName);
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
		System.out.println("collected the string to search");
		//find the number of unit names within the string
		int index = 675; //uh, i counted?
		int tempIndex = 0;
		while (index != -1) {
			tempIndex = str.indexOf(unitName, index);
			index = tempIndex;
			timesF++;
		}
		System.out.println("finished searching");
		return timesF/12;
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
