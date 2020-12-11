package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class NegativityDetector extends CustomMessageCreateListener {
	private static final String COMMAND = "!negdetect";
	String[] wordbank = new String[595];
	String[] unrepeatedcmd;
	boolean bool = false;
	boolean hasentered = false;
	
	File infosheet = new File("src/main/resources/negdetectorinfo.txt");

	public NegativityDetector(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/negdetectorwordbank.txt"));
			int counter = 0;
			String line = br.readLine();
			while(line != null){
				wordbank[counter] = line;
				line = br.readLine();
				counter++;
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
	
	public void makeUnrepeatedList(String[] str) {
		ArrayList<String> tobearray = new ArrayList<String>();
		for (String s: str) {
			System.out.println("the string: "+ s);
			if (!tobearray.contains(s)) {
				tobearray.add(s);
			}
		}
		
		unrepeatedcmd = tobearray.toArray(new String[0]);
	}
	
	public static int binarySearch(String[] str, String s) {
//		int min = 0;
//		int max = str.length;
//		int mid;
		System.out.println(str.length + " " + s);
		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i].equals(s)) return i;
		}

		return -1;
	} 
	
	public void checkString(String[] input) {
		ArrayList<String> newList = new ArrayList<String>();
		System.out.println(unrepeatedcmd.length);
		for(int i = 0; i < unrepeatedcmd.length; i++) {
			int wordindex = binarySearch(wordbank, unrepeatedcmd[i]);
			System.out.println("word index: " + wordindex);
			if (wordindex != -1 && !newList.contains(unrepeatedcmd[i])) {
				System.out.println("adding word " + wordbank[wordindex]);
				newList.add(wordbank[wordindex]);
			}
		}
		
		String[] wordsused = newList.toArray(new String[0]);
		System.out.println("Wordsused length: " + wordsused.length);
		try {
			FileWriter fw = new FileWriter(infosheet);
			
			int count = 0;
			for (String s: wordsused) {
				System.out.println("reached");
				for (String s1: input) {
					if (s1.equals(s)) count++;
				}
				System.out.println(s + count);
				fw.write(s + ": " + count + "\n");
				System.out.println("hath happened");
				count = 0;
			}
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
			String cmd = event.getMessageContent().replace(COMMAND,"");
			System.out.println("This is it" + cmd);
			if (cmd.equals("")) {
				System.out.println("not supposed to be here");
				event.getChannel().sendMessage("Please enter a message you would liked to be checked. Make sure the spellings "
						+ "are all correct.");
			} else {
				System.out.println("am here");
				hasentered = true;
				cmd = cmd.trim().toLowerCase();
				System.out.println(cmd);
				String[] splited = cmd.split(" ");
				System.out.println(splited.length);
				makeUnrepeatedList(splited);
				checkString(splited);
				
				checkString(splited);
				event.getChannel().sendMessage("Done! Enter further commands to learn more.");
			}
		}
		
		if(event.getMessageContent().contains("!getstats")) {
			if (!hasentered) {
				event.getChannel().sendMessage("Please input your content first using the '!negdetect' command!");
			} else {
				try {
					BufferedReader br = new BufferedReader(new FileReader(infosheet));
					
					String message = "YOUR STATS:\n";
					String line = br.readLine();
					while(line != null){
						message += line + "\n";
						line = br.readLine();
					}
					
					event.getChannel().sendMessage(message);

					br.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				infosheet.delete();
//				System.exit(0);
			}
		}
	}

}
