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
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class FEHStatListener extends CustomMessageCreateListener {
	String webpageRead;
	int timesSpecified = 0;
	int indexFound = 0;
	boolean foundInNoColon = false;
	boolean foundInColon = false;
	String finalizedMessage = "";
	ArrayList<String> heroNamesRaw = new ArrayList<String>();
	ArrayList<String> heroNamesNoColon = new ArrayList<String>();
	ArrayList<String> heroNamesColon = new ArrayList<String>();

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
			if (event.getMessageContent().length() == 9) {
				event.getChannel().sendMessage("(。ヘ°) Please specify which unit to search for!");
			/*} else if (event.getMessageContent().length() > 21) {
				String unitName = event.getMessageContent().substring(10);
				int i = howMany(unitName);
				cleanNames();
				event.getChannel().sendMessage("There is " + i + " match(es) for "+ unitName + "\".");
				findStats(unitName);
				event.getChannel().sendMessage(statsS);*/
			} else {
				String unitName = event.getMessageContent().substring(10);
				unitName = unitName.toLowerCase();
				int i = howMany(unitName);
				cleanNames();
				if (i == 1) { // 1 match
					event.getChannel().sendMessage("There is " + i + " match for \"" + unitName + "\".");
					findStats(unitName);
					event.getChannel().sendMessage(statsS);
					timesSpecified++;
				} else if (i == 0) { // no matches
					event.getChannel().sendMessage("There are " + i + " matches for \"" + unitName + "\". Maybe they'll be added to FEH someday!");
				} else { // more than 1 match
					event.getChannel().sendMessage("There are " + i + " matches for \"" + unitName + "\".");
					String temp = "";
					for (int j = 0; j < heroNamesColon.size(); j++) {
						temp += heroNamesColon.get(j);
						if (j < (heroNamesColon.size() - 2)) {
							temp += ", ";
						} else if (j < (heroNamesColon.size() - 1)) {
							temp += ", or ";
						}
					}
					event.getChannel().sendMessage(
							"Would you like to see stats for " + temp + "? Please reply with their full title.");
				}
			}
		}
		String message = event.getMessageContent();
		checkArrays(message);
		// checking here
		if (!heroNamesNoColon.isEmpty() && (foundInNoColon || foundInColon) && timesSpecified == 0
				&& (!(event.getMessageAuthor().asUser().get().isBot()))) {
			event.getChannel()
					.sendMessage("Your message " + finalizedMessage + " was deemed to match with "
							+ heroNamesColon.get(indexFound) + ".\nFound in colon array: " + foundInColon
							+ ".\nFound in no colon array: " + foundInNoColon + ".");
			timesSpecified = 0;
			findSpecificStats(finalizedMessage);
			event.getChannel().sendMessage(statsS);
			timesSpecified++;
		}
	}

	public void checkArrays(String message) {
		finalizedMessage = "";
		// converting message
		char[] messageChars = message.toCharArray();
		messageChars[0] = Character.toUpperCase(messageChars[0]);
		int tempIndex = 0;
		int index = 0;
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		while (tempIndex != -1) {
			tempIndex = message.indexOf(" ", index);
			index = tempIndex + 1;
			indexes.add(index);
			messageChars[index] = Character.toUpperCase(messageChars[index]);
		}
		for (int i = 1; i < messageChars.length; i++) {
			if (!indexes.contains(i)) {
				messageChars[i] = Character.toLowerCase(messageChars[i]);
			}
		}

		for (char c : messageChars) {
			finalizedMessage = finalizedMessage + c;
		}
		// if specified name is sent
		System.out.println("checking no colon array...");
		for (String s : heroNamesNoColon) {
			String sTemp = fixCapitalization(s);
			System.out.println(sTemp);
			if (sTemp.equals(finalizedMessage)) {
				foundInNoColon = true;
				indexFound = heroNamesNoColon.indexOf(s);
			}
		}
		System.out.println("checking colon array...");
		for (String s : heroNamesColon) {
			String sTemp = fixCapitalization(s);
			System.out.println(sTemp);
			if (sTemp.equals(finalizedMessage)) {
				foundInColon = true;
				indexFound = heroNamesColon.indexOf(s);
			}
		}
	}

	public String fixCapitalization(String heroName) {
		char[] name = heroName.toCharArray();
		name[0] = Character.toUpperCase(name[0]);
		if (heroName.contains(" ")) {
			System.out.println("name contains a space!");
			name[0] = Character.toUpperCase(name[0]);
			int tempIndex = 0;
			int index = 0;
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			while (tempIndex != -1) {
				tempIndex = heroName.indexOf(" ", index);
				index = tempIndex + 1;
				indexes.add(index);
				name[index] = Character.toUpperCase(name[index]);
			}
			for (int i = 1; i < name.length; i++) {
				if (!indexes.contains(i)) {
					name[i] = Character.toLowerCase(name[i]);
				}
			}
		} else {
			for (int i = 1; i < name.length; i++) {
				name[i] = Character.toLowerCase(name[i]);
			}
		}
		String unitName = "";
		for (char c : name) {
			unitName = unitName + c;
		}
		return unitName;
	}

	ArrayList<String> lines = new ArrayList<String>();

	public int howMany(String heroName) {
		int timesF = 0;
		String str = "";
		// make sure the capitalization is correct
		String unitName = fixCapitalization(heroName);
		// get the one big table line
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
		if (str.contains(unitName) && !(unitName.contains(" "))) {
			System.out.println("collected the string to search");
			// find the number of unit names within the string
			int index = 675; // uh, i counted?
			int tempIndex = 0;
			if (unitName.equals("Gunnthra")) {
				while (tempIndex != -1) {
					tempIndex = str.indexOf("Gunnthr", index);
					index = tempIndex + 1;
					if (tempIndex != -1 && (timesF % 12 == 3)) {
						heroNamesRaw.add(str.substring(tempIndex, tempIndex + 31));
					}
					timesF++;
				}
			} else {
				while (tempIndex != -1) {
					tempIndex = str.indexOf(unitName, index);
					index = tempIndex + 1;
					if (tempIndex != -1 && (timesF % 12 == 3)) {
						heroNamesRaw.add(str.substring(tempIndex, tempIndex + 31));
					}
					timesF++;
				}
			}
			System.out.println("finished searching");
			return timesF / 12;
		} else if (str.contains(unitName) && (unitName.contains(" "))) {
			System.out.println("THERE IS A SPACE");
			if(unitName.indexOf(" ") == unitName.lastIndexOf(" ")) {
				unitName += ":";
			}
			// find the number of unit names within the string, copy-pasted edition
			int index = 675; // uh, i counted?
			int tempIndex = 0;
			while (tempIndex != -1) {
				tempIndex = str.indexOf(unitName, index);
				index = tempIndex + 1;
				if (tempIndex != -1 && (timesF % 3 == 2)) {
					heroNamesRaw.add(str.substring(tempIndex, tempIndex + 31));
				}
				timesF++;
			}
			System.out.println("finished searching");
			return timesF / 3;
		} else {
			return 0;
		}
	}

	public void cleanNames() {
		for (int i = 0; i < heroNamesRaw.size(); i++) {
			String s = heroNamesRaw.get(i);
			s = s.replace("_", " ");
			// remove ending
			if (s.contains("Fa")) {
				int face = s.lastIndexOf("Fa");
				s = s.substring(0, face - 1);
			} else if (s.contains("<")) {
				int bracket = s.indexOf("<");
				s = s.substring(0, bracket);
			}
			heroNamesNoColon.add(s);
			// insert colon
			int spacePlace = s.indexOf(" ");
			int colonPlace = s.indexOf(":");
			int colonInsert = 0;
			if (spacePlace > colonPlace) { //if space is after colon: if there isn't a space in unit name
				colonInsert = spacePlace;
				s = s.substring(0, colonInsert) + ":" + s.substring(colonInsert);
			}
			heroNamesColon.add(s);
		}
	}

	String statsS = "";

	public void findStats(String heroName) {
		statsS = "";
		String str = "";
		int stringF = 0;
		String unitName = fixCapitalization(heroName);
		System.out.println(unitName + "-------------------------------------------------------------");
		// grab the line again
		System.out.println("getting string");
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
		System.out.println("starting to search");
		int index = 675; // copied from above
		int tempIndex = 0;
		if (unitName.contains(" ")) {
			while (tempIndex != -1) {
				String tempName = unitName += ":";
				tempIndex = str.indexOf(tempName, index);
				index = tempIndex + 1;
				stringF++;
				if (stringF == 1) {
					System.out.println("found it :)");
					break;
				}
			}
		} else {
			while (tempIndex != -1) {
				tempIndex = str.indexOf(unitName, index);
				index = tempIndex + 1;
				stringF++;
				if (stringF == 12) {
					System.out.println("found 12 times :)");
					break;
				}
			}
		}
		tempIndex = str.indexOf("/></td><td>", index);
		System.out.println("index is " + tempIndex);
		statsS = str.substring(tempIndex, tempIndex + 74);
		System.out.println("found it");
		String tempString = "Here are the stats I found for \"" + heroNamesColon.get(0) + "\":\n";
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

	public void findSpecificStats(String heroName) {
		String unitName = heroNamesColon.get(indexFound);
		if (heroName.contains(" ")) {
			unitName = heroNamesColon.get(0);
		}
		statsS = "";
		String str = "";
		int stringF = 0;
		// grab the line again
		System.out.println("getting string");
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
		System.out.println("starting to search...");
		int index = 675; // copied from above
		int tempIndex = 0;
		if (unitName.contains("Gunnthra")) {
			String hmmTemp = unitName.replace(":", "");
			tempIndex = str.indexOf(hmmTemp, index);
			index = tempIndex + 1;
			System.out.println("Index is " + index);
		} else if (unitName.contains(":")) {
			while (tempIndex != -1) {
				tempIndex = str.indexOf(unitName, index);
				index = tempIndex + 1;
				stringF++;
				if (stringF == 3) {
					System.out.println("found 3 times :)");
					break;
				}
			}
		}
		tempIndex = str.indexOf("/></td><td>", index);
		System.out.println("index is " + tempIndex);
		statsS = str.substring(tempIndex, tempIndex + 74);
		System.out.println("found it");
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

}
