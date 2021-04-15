package org.jointheleague.modules;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class DndCompanion extends CustomMessageCreateListener {

	private final static String COMMAND = "/ask";

	public DndCompanion(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "/ask roll followed by a number to role between and a modifier \n /ask link pick from the following 3 choices: discord, character sheets or table");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String[] str = event.getMessageContent().split(" ");
		if (str[0].equals(COMMAND)) {
//			if (str.length <= 2 && str.length != 1) {
//				event.getChannel().sendMessage("the links are: " + "\n" + "table: roll20.net" + "\n"
//						+ "character sheets: dndbeyond.com" + "\n" + "this website");
//			}
			if (str.length >= 3 && str[1].equals("links")) {
				if (str[2].equals("table")) {
					event.getChannel().sendMessage("httpS://ro1120.net for the table :)");
				} else if (str.length >= 4 && str[2].equals("character") && str[3].equals("sheets")) {
					event.getChannel().sendMessage("dndbeyond.com for the characters. They're not alive though :L");
				} else if (str[2].equals("discord")) {
					event.getChannel().sendMessage("this website for discord calls");
				} else if(str[2].equals("all")){
					event.getChannel().sendMessage("the links are: " + "\n" + "table: httpS://roll20.net" + "\n"
							+ "character sheets: dndbeyond.com" + "\n" + "this website");
				}
			} else if (str.length >= 4 && str[1].equals("roll")) {
				Random rand = new Random();
				int max = 0;
				int mod = 0;
				int finale = 0;
				if(Integer.parseInt(str[2]) <= 0) {
					event.getChannel().sendMessage("you can't do a number less then 0");
				} else {
				max = rand.nextInt(Integer.parseInt(str[2])) + 1;
				mod = Integer.parseInt(str[3]);
				finale = max + mod;
				event.getChannel().sendMessage("your number is: "+ max + " + " + mod + " = " + finale);
				}
			} else if (str[1].equals("advice")) {
				try {
					FileWriter fw = new FileWriter("src/main/java/org/jointheleague/modules/DndCompanionExtras");
					for(int i = 2; i < str.length; i++) {
					fw.write(str[i]);
					fw.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
