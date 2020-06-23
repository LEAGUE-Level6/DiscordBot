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
	int timesSpecified = 0;
	int indexFound = 0;
	boolean foundInNoColon = false;
	boolean foundInColon = false;
	boolean specialName = false;
	String finalizedMessage = "";
	ArrayList<String> heroNamesRaw = new ArrayList<String>();
	ArrayList<String> heroNamesNoColon = new ArrayList<String>();
	ArrayList<String> heroNamesColon = new ArrayList<String>();
	
	//Líf, Gunnthrá, Lon'qu, L'arachel, and Hríd are special snowflakes
	//https://www.youtube.com/watch?v=k6lRHPpBMiQ
	
	public FEHStatListener(String channelName) { // thank you http://zetcode.com/java/readwebpage/
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
		if (event.getMessageContent().contains("!fehstats") && (!(event.getMessageAuthor().asUser().get().isBot()))) {
			timesSpecified = 0;
			heroNamesRaw.clear();
			heroNamesNoColon.clear();
			heroNamesColon.clear();
			foundInColon = false;
			foundInNoColon = false;
			specialName = false;
			if (event.getMessageContent().length() == 9) { // just says "!fehstats"
				event.getChannel().sendMessage(
						"(。ヘ°) Please specify which unit to search for! \nType \"!fehstats help\" if you need help on how to use this listener.");
			} else if (event.getMessageContent().equalsIgnoreCase("!fehstats help")) { // !fehstats help
				event.getChannel().sendMessage(
						"After typing \"!fehstats\", type the name of the unit that you want to find stats for! "
						+ "\nYou can type either their name or their whole title."
						+ "\nIf there are multiple versions of the unit, I'll give you a list and ask for which one you want.");
			} else if (event.getMessageContent().length() > 23) { // search by full title. sorry lute.
				String unitName = event.getMessageContent().substring(10).trim();
				String unitNameTemp = unitName;
				unitName = unitName.replace("'", "");
				int i = howManySpecific(unitName);
				if (i == 1) {
					indexFound = 0;
					specialName = true;
					cleanNames();
					event.getChannel().sendMessage("I found " + i + " match for \"" + unitNameTemp + "\".");
					findSpecificStats(unitName);
					event.getChannel().sendMessage(statsS);
					timesSpecified++;
				} else {
					event.getChannel().sendMessage("(´−｀) ﾝｰ I couldn't find any matches for that name. Maybe they'll be added to FEH someday!");
				}
			} else if (event.getMessageContent().length() > 12) { // search by unit name only
				String unitName = event.getMessageContent().substring(10).trim();
				unitName = unitName.toLowerCase();
				int i = howMany(unitName);
				cleanNames();
				if (i == 1) { // 1 match
					event.getChannel().sendMessage("I found " + i + " match for \"" + unitName + "\".");
					findStats(unitName);
					event.getChannel().sendMessage(statsS);
					timesSpecified++;
				} else if (i == 0) { // no matches
					event.getChannel().sendMessage("(´−｀) ﾝｰ I couldn't find any matches for that name. Maybe they'll be added to FEH someday!");
				} else { // more than 1 match
					event.getChannel().sendMessage("I found " + i + " matches for \"" + unitName + "\".");
					String temp = "";
					for (int j = 0; j < heroNamesColon.size(); j++) {
						temp += heroNamesColon.get(j);
						if (j < (heroNamesColon.size() - 2)) {
							temp += ", ";
						} else if (j < (heroNamesColon.size() - 1)) {
							temp += ", or ";
						}
					}
					event.getChannel().sendMessage("Would you like to see stats for " + temp + "? Please reply with their full title, including any non-colon punctuation.");
				}
			}
		}
		String message = event.getMessageContent();
		checkArrays(message);
		// checking for specific units here
		if (!heroNamesNoColon.isEmpty() && (foundInNoColon || foundInColon) && timesSpecified == 0
				&& (!(event.getMessageAuthor().asUser().get().isBot()) && !(event.getMessageAuthor().isYourself()))) {
			timesSpecified = 0;
			findSpecificStats(finalizedMessage);
			event.getChannel().sendMessage(statsS);
			timesSpecified++;
		}
	}

	public void checkArrays(String message) { //disclaimer: this method runs every time someone sends a message
		finalizedMessage = message.toLowerCase();
		// if specified name is sent
		for (String s : heroNamesNoColon) {
			String sTemp = s.toLowerCase();
			if (sTemp.equals(finalizedMessage)) {
				foundInNoColon = true;
				indexFound = heroNamesNoColon.indexOf(s);
			}
		}
		for (String s : heroNamesColon) {
			String sTemp = s.toLowerCase();
			if (sTemp.equals(finalizedMessage)) {
				foundInColon = true;
				indexFound = heroNamesColon.indexOf(s);
			}
		}
	}

	public String fixCapitalization(String heroName) { //fixing capitalization.....
		//System.out.println("fixing capitalization...");
		char[] name = heroName.toCharArray();
		String unitName = "";
		name[0] = Character.toUpperCase(name[0]);
		if (heroName.contains(" ")) {
			int tempIndex = 0;
			int index = 0;
			ArrayList<Integer> indices = new ArrayList<Integer>();
			name[0] = Character.toUpperCase(name[0]);
			while (tempIndex != -1) {
				tempIndex = heroName.indexOf(" ", index);
				index = tempIndex + 1;
				indices.add(index);
				name[index] = Character.toUpperCase(name[index]);
			}
			for (int i = 1; i < name.length; i++) {
				if (!indices.contains(i)) {
					name[i] = Character.toLowerCase(name[i]);
				}
			}
			for (char c : name) {
				unitName += c;
			}
			// fixing capitalization on hyphens
			char[] unitNameChars = unitName.toCharArray();
			tempIndex = 0;
			index = 0;
			indices.clear();
			while (tempIndex != -1) {
				tempIndex = unitName.indexOf("-", index);
				index = tempIndex + 1;
				indices.add(index);
				unitNameChars[index] = Character.toUpperCase(unitNameChars[index]);
			}
			unitName = "";
			for (char c : unitNameChars) {
				unitName += c;
			}
		} else { // if there is no space
			for (int i = 1; i < name.length; i++) {
				name[i] = Character.toLowerCase(name[i]);
			}
			for (char c : name) {
				unitName = unitName + c;
			}
		}
		//System.out.println("finished fixing capitalization: " + unitName);
		return unitName;
	}

	ArrayList<String> lines = new ArrayList<String>();
	public int howMany(String heroName) { //looking for how many versions of a unit there are
		//System.out.println("counting heroes...");
		int timesF = 0;
		String strRaw = "";
		String str = "";
		// get the one big table line
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/FEHStats.txt"));
			while ((strRaw = br.readLine()) != null) {
				if (strRaw.contains("Alfonse: Prince of Askr")) {
					lines.add(strRaw);
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
		str = strRaw.toLowerCase();
		int index = 675; // thanks google docs
		int tempIndex = 0;
		if (str.contains(heroName) && !(heroName.contains(" "))) { //no space
			if (heroName.equalsIgnoreCase("gunnthra")) { // Gunnthr%C3%A1, L%C3%ADf, and Hr%C3%ADd are exceptions, unfortunately
				while (tempIndex != -1) {
					tempIndex = str.indexOf("gunnthr", index);
					index = tempIndex + 1;
					if (tempIndex != -1 && (timesF % 12 == 3)) {
						heroNamesRaw.add(strRaw.substring(tempIndex, tempIndex + 31));
					}
					timesF++;
				}
				//System.out.println("finished searching");
				return timesF / 12;
			} else if (heroName.equalsIgnoreCase("hrid")){
				while (tempIndex != -1) {
					tempIndex = str.indexOf("hrid", index);
					index = tempIndex + 1;
					if (tempIndex != -1 && (timesF % 7 == 3)) {
						heroNamesRaw.add(strRaw.substring(tempIndex, tempIndex + 31));
					}
					timesF++;
				}
				//System.out.println("finished searching");
				return timesF / 7;
			} else if (heroName.equalsIgnoreCase("lif")){
				while (tempIndex != -1) {
					tempIndex = str.indexOf("líf", index);
					index = tempIndex + 1;
					if (tempIndex != -1 && (timesF % 3 == 2)) {
						heroNamesRaw.add(strRaw.substring(tempIndex, tempIndex + 31));
					}
					timesF++;
				}
				//System.out.println("finished searching");
				return timesF / 3;
			} else if (heroName.contains("'")) { //I'm looking at you Lon'qu
				while (tempIndex != -1) {
					tempIndex = str.indexOf(heroName, index);
					index = tempIndex + 1;
					if (tempIndex != -1) {
						heroNamesRaw.add(strRaw.substring(tempIndex, tempIndex + 31));
						timesF++;
					}
				}
				//System.out.println("finished searching");
				return timesF;
			} else {
				heroName += ": ";
				while (tempIndex != -1) {
					tempIndex = str.indexOf(heroName, index);
					index = tempIndex + 1;
					if (tempIndex != -1 && (timesF % 3 == 1)) {
						heroNamesRaw.add(strRaw.substring(tempIndex, tempIndex + 31));
					}
					timesF++;
				}
				//System.out.println("finished searching");
				return timesF / 3;
			}
		} else if (str.contains(heroName) && (heroName.contains(" "))) { //if there is a space in the name (flame emperor, black knight, etc)
			if (heroName.indexOf(" ") == heroName.lastIndexOf(" ")) {
				heroName += ":";
			}
			while (tempIndex != -1) {
				tempIndex = str.indexOf(heroName, index);
				index = tempIndex + 1;
				if (tempIndex != -1 && (timesF % 3 == 2)) {
					heroNamesRaw.add(strRaw.substring(tempIndex, tempIndex + 31));
				}
				timesF++;
			}
			//System.out.println("finished searching");
			return timesF / 3;
		} else {
			//System.out.println("found none :(");
			return 0;
		}
	}
	
	public void cleanNames() { //cleaning up weird things in names
		//System.out.println("cleaning names...");
		for (int i = 0; i < heroNamesRaw.size(); i++) {
			String s = heroNamesRaw.get(i);
			s = s.replace("_", " ");
			s = s.replace("&#39;", "'");
			
			// remove ending
			if (s.contains("Fa")) {
				int begone = s.lastIndexOf("Fa");
				s = s.substring(0, begone - 1);
			} 
			if (s.contains("\"")) {
				int face = s.lastIndexOf("\"");
				s = s.substring(0, face);
			}
			if (s.contains("<")) {
				int bracket = s.indexOf("<");
				s = s.substring(0, bracket);
			} 
			s = s.replace("&quot;", "\"");
			if (!specialName && !(s.contains("Gunnthra") || s.contains("Hrid") || s.contains("Lif"))) { // Gunnthr%C3%A1 and Hr%C3%ADd strike again...
				heroNamesColon.add(s);
			}
			// remove colon
			s = s.replace(":", "");
			//System.out.println(s);
			heroNamesNoColon.add(s);
			// add colon
			if ((specialName || s.contains("Gunnthra") || s.contains("Hrid")) && !s.contains(":")) {
				int spacePlace = s.indexOf(" ");
				s = s.substring(0, spacePlace) + ":" + s.substring(spacePlace);
				heroNamesColon.add(s);
			}
		}
	}
	
	String statsS = "";
	public void findStats(String heroName) { // finding stats from name search only
		//System.out.println("finding stats...");
		statsS = "";
		String str = "";
		int stringF = 0;
		String unitName = fixCapitalization(heroName);
		// grab the line again
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/FEHStats.txt"));
			while ((str = br.readLine()) != null) {
				if (str.contains("Alfonse: Prince of Askr")) {
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
		// finding the start of the </td><td>
		int index = 675; // copied from above
		int tempIndex = 0;
		if (unitName.contains(" ") || unitName.contains("'")) {
			while (tempIndex != -1) {
				String tempName = unitName.concat(":");
				tempIndex = str.indexOf(tempName, index);
				index = tempIndex + 1;
				stringF++;
				if (stringF == 1) {
					//System.out.println("found it :)");
					break;
				}
			}
		} else if (unitName.contains("Lif")) { //>:(
			while (tempIndex != -1) {
				tempIndex = str.indexOf("Líf:", index);
				index = tempIndex + 1;
				stringF++;
				if (stringF == 1) {
					//System.out.println("found it :)");
					break;
				}
			}
		} else {
			while (tempIndex != -1) {
				tempIndex = str.indexOf(unitName, index);
				index = tempIndex + 1;
				stringF++;
				if (stringF == 12) {
					//System.out.println("found it 12 times :)");
					break;
				}
			}
		}
		tempIndex = str.indexOf("/></td><td>", index);
		//System.out.println("index is " + tempIndex + ". searching for stats...");
		statsS = str.substring(tempIndex, tempIndex + 74);
		String tempString = "Here are the stats I found for \"" + fixCapitalization(heroNamesColon.get(0)) + "\":\n";
		tempString += "HP: ";
		tempString += statsS.substring(11, 13);
		tempString += "\nAtk: ";
		tempString += statsS.substring(22, 24);
		tempString += "\nSpd: ";
		tempString += statsS.substring(33, 35);
		tempString += "\nDef: ";
		tempString += statsS.substring(44, 46);
		tempString += "\nRes: ";
		tempString += statsS.substring(55, 57);
		tempString += "\nTotal BST: ";
		tempString += statsS.substring(66, 69);
		tempString += "\n✧٩(•́⌄•́๑)و ✧";
		statsS = tempString;
	}

	public void findSpecificStats(String heroName) { //finding stats from name and title
		//System.out.println("finding specialized stats...");
		String unitName = heroNamesColon.get(indexFound);
		if (specialName) {
			unitName = heroNamesNoColon.get(0);
		}
		//System.out.println(unitName);
		statsS = "";
		String str = "";
		int stringF = 0;
		// grab the line again
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/FEHStats.txt"));
			while ((str = br.readLine()) != null) {
				if (str.contains("Alfonse: Prince of Askr")) {
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
		// finding the start of the </td><td>
		int index = 675; // copied from above
		int tempIndex = 0;
		if (unitName.contains("Gunnthra") || unitName.contains("Hrid") || unitName.contains("Lif")) { // Gunnthr%C3%A1 and Hr%C3%ADd strike again...
			String hmmTemp = unitName.replace(":", "");
			tempIndex = str.indexOf(hmmTemp, index);
			index = tempIndex + 1;
			//System.out.println("Index is " + index);
		} else if(unitName.contains(":") && !unitName.contains("'") && !unitName.contains("\"")) {
			while (tempIndex != -1) {
				tempIndex = str.indexOf(unitName, index);
				index = tempIndex + 1;
				stringF++;
				if (stringF == 3) {
					//System.out.println("found 3 times :)");
					break;
				}
			}
		} else {
			while (tempIndex != -1) {
				if (unitName.contains("\"")){
					unitName = unitName.replace("\"", "");
				}
				if (unitName.contains(":")) {
					unitName = unitName.replace(":", "");
				}
				//System.out.println(unitName);
				tempIndex = str.indexOf(unitName, index);
				index = tempIndex + 1;
				stringF++;
				if (stringF == 1) {
					//System.out.println("found it :)");
					break;
				}
			}
		}
		tempIndex = str.indexOf("/></td><td>", index);
		//System.out.println("index is " + tempIndex + ". searching for stats...");
		statsS = str.substring(tempIndex, tempIndex + 74);
		//System.out.println("found it");
		String tempString = "Here are the stats I found for \"" + heroNamesColon.get(indexFound) + "\":\n";
		tempString += "HP: ";
		tempString += statsS.substring(11, 13);
		tempString += "\nAtk: ";
		tempString += statsS.substring(22, 24);
		tempString += "\nSpd: ";
		tempString += statsS.substring(33, 35);
		tempString += "\nDef: ";
		tempString += statsS.substring(44, 46);
		tempString += "\nRes: ";
		tempString += statsS.substring(55, 57);
		tempString += "\nTotal BST: ";
		tempString += statsS.substring(66, 69);
		tempString += "\n✧٩(•́⌄•́๑)و ✧";
		statsS = tempString;
	}

	public int howManySpecific(String heroName) { //for searching for specific names
		int timesF = 0;
		String strRaw = "";
		String str = "";
		heroName = heroName.replace(":", "");
		heroName = heroName.replace("\"", "");
		// make sure the capitalization is correct
		String unitName = heroName.toLowerCase();
		// get the one big table line
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/FEHStats.txt"));
			while ((strRaw = br.readLine()) != null) {
				if (strRaw.contains("Alfonse: Prince of Askr")) {
					lines.add(strRaw);
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
		str = strRaw.toLowerCase();
		//System.out.println("collected the string to search");
		// find the unit name. it's either there or it isn't
		int index = 675;
		int tempIndex = 0;
		while (tempIndex != -1) {
			tempIndex = str.indexOf(unitName, index);
			index = tempIndex + 1;
			if (tempIndex != -1) {
				heroNamesRaw.add(strRaw.substring(tempIndex, tempIndex + 31));
				timesF++;
			}
			//System.out.println("found it");
		}
		//System.out.println("finished searching");
		return timesF;
	}
}
