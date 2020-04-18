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
	boolean specialName = false;
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
				event.getChannel().sendMessage(
						"(。ヘ°) Please specify which unit to search for! \nType \"!fehstats help\" if you need help on how to use this listener.");
			} else if (event.getMessageContent().equalsIgnoreCase("!fehstats help")) {
				event.getChannel().sendMessage(
						"After typing \"!fehstats\", type the name of the unit that you want to find stats for! \nIf there are multiple versions of the unit, I'll give you a list and ask for which one you want.");
			} else if (event.getMessageContent().length() > 23) { // search by full title. rip lute and flame emperor
				String unitName = event.getMessageContent().substring(10).trim();
				String unitNameTemp = unitName + "\"";
				unitName = unitName.replace("'", "");
				int i = howManySpecific(unitName);
				if (i == 1) {
					specialName = true;
					cleanNames();
					event.getChannel().sendMessage("There is " + i + " match for \"" + unitNameTemp + ".");
					findSpecificStats(unitName);
					event.getChannel().sendMessage(statsS);
					timesSpecified++;
				} else {
					event.getChannel().sendMessage("There are " + i + " matches for \"" + unitName
							+ "\". Maybe they'll be added to FEH someday!");
				}
			} else if (event.getMessageContent().length() > 12) { // search by unit name only
				String unitName = event.getMessageContent().substring(10).trim();
				unitName = unitName.toLowerCase();
				int i = howMany(unitName);
				cleanNames();
				if (i == 1) { // 1 match
					event.getChannel().sendMessage("There is " + i + " match for \"" + unitName + "\".");
					findStats(unitName);
					event.getChannel().sendMessage(statsS);
					timesSpecified++;
				} else if (i == 0) { // no matches
					event.getChannel().sendMessage("There are " + i + " matches for \"" + unitName
							+ "\". Maybe they'll be added to FEH someday!");
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
		if (!heroNamesNoColon.isEmpty() && (foundInNoColon || foundInColon) && timesSpecified == 0 && (!(event.getMessageAuthor().asUser().get().isBot()) && !(event.getMessageAuthor().isYourself()))) {
			event.getChannel()
					.sendMessage("Your message " + finalizedMessage + " matches with " + heroNamesColon.get(indexFound)
							+ ".\nFound in colon array: " + foundInColon + ".\nFound in no colon array: "
							+ foundInNoColon + ".");
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
		System.out.println("checking arrays...");
		for (String s : heroNamesNoColon) {
			String sTemp = fixCapitalization(s);
			System.out.println(sTemp);
			if (sTemp.equals(finalizedMessage)) {
				foundInNoColon = true;
				indexFound = heroNamesNoColon.indexOf(s);
			}
		}
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
		System.out.println("fixing capitalization...");
		char[] name = heroName.toCharArray();
		name[0] = Character.toUpperCase(name[0]);
		if (heroName.contains(" ")) {
			System.out.println("name contains a space!");
			//fixing capitalization on spaces
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
			//fixing capitalization on hyphens
			tempIndex = 0;
			index = 0;
			indexes = new ArrayList<Integer>();
			while (tempIndex != -1) {
				tempIndex = heroName.indexOf("-", index);
				index = tempIndex + 1;
				indexes.add(index);
				name[index] = Character.toUpperCase(name[index]);
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
		System.out.println("counting heroes...");
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
				System.out.println("finished searching");
				return timesF / 12;
			} else {
				unitName += ": ";
				while (tempIndex != -1) {
					tempIndex = str.indexOf(unitName, index);
					index = tempIndex + 1;
					if (tempIndex != -1 && (timesF % 3 == 1)) {
						heroNamesRaw.add(str.substring(tempIndex, tempIndex + 31));
					}
					timesF++;
				}
				System.out.println("finished searching");
				return timesF / 3;
			}
		} else if (str.contains(unitName) && (unitName.contains(" "))) {
			if (unitName.indexOf(" ") == unitName.lastIndexOf(" ")) {
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
			System.out.println("found none :(");
			return 0;
		}
	}

	public void cleanNames() {
		System.out.println("cleaning names...");
		for (int i = 0; i < heroNamesRaw.size(); i++) {
			String s = heroNamesRaw.get(i);
			s = s.replace("_", " ");
			s = s.replace("&#39;", "'");
			// remove ending
			if (s.contains("\">")) {
				int face = s.lastIndexOf("\">");
				s = s.substring(0, face);
			} else if (s.contains("<")) {
				int bracket = s.indexOf("<");
				s = s.substring(0, bracket);
			} else if (s.contains("Fa")) {
				int begone = s.lastIndexOf("Fa");
				s = s.substring(0, begone-1);
			}
			if (!specialName) {
				heroNamesColon.add(s);
			}
			// remove colon
			s = s.replace(":", "");
			heroNamesNoColon.add(s);
			//add colon
			if (specialName && !s.contains(":")) {
				int spacePlace = s.indexOf(" ");
				s = s.substring(0, spacePlace) + ":" + s.substring(spacePlace);
				heroNamesColon.add(s);
			}
		}
	}

	String statsS = "";

	public void findStats(String heroName) {
		System.out.println("finding stats...");
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
					System.out.println("found it 12 times :)");
					break;
				}
			}
		}
		tempIndex = str.indexOf("/></td><td>", index);
		System.out.println("index is " + tempIndex + ". searching for stats...");
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
		System.out.println("finding stats...");
		String unitName = heroNamesColon.get(indexFound);
		if (heroName.contains(" ")) {
			unitName = heroNamesColon.get(0);
		}
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
		System.out.println("index is " + tempIndex + ". searching for stats...");
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

	public int howManySpecific(String heroName) {
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
		System.out.println("collected the string to search");
		// find the unit name. it's either there or it isn't
		int index = 675;
		int tempIndex = 0;
		while (tempIndex != -1) {
			tempIndex = str.indexOf(unitName, index);
			index = tempIndex + 1;
			if (tempIndex != -1) {
				heroNamesRaw.add(str.substring(tempIndex, tempIndex + 31));
				timesF++;
			}
			System.out.println("found it");
		}
		System.out.println("finished searching");
		return timesF;
	}
}
