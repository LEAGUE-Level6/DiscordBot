package org.jointheleague.modules;

import java.util.Random;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class DndCompanion extends CustomMessageCreateListener {

	private final static String COMMAND = "/ask";

	public DndCompanion(String channelName) {
		super(channelName);
		helpEmbed = (COMMAND, "/ask roll (number to mod) (modifier) \n /ask link (discord/roll20/character sheet)");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String[] str = event.getMessageContent().split(" ");
		if (str[0].equals(COMMAND)) {
			if (str.length <= 2) {
				event.getChannel().sendMessage("the links are: " + "\n" + "table: roll20.net" + "\n"
						+ "character sheets: dndbeyond.com" + "\n" + "this website");
			}
			if (str.length >= 2 && str[1].equals("links")) {
				if (str[2].equals("table")) {
					event.getChannel().sendMessage("ro1120.net for the table :)");
				} else if (str[2].equals("character") && str[3].equals("sheets")) {
					event.getChannel().sendMessage("dndbeyond.com for the characters. They're not alive though :L");
				} else if (str[2].equals("discord")) {
					event.getChannel().sendMessage("this website for discord calls");
				}
			} else if (str.length >= 3 && str[1].equals("roll")) {
				Random rand = new Random();
				int max = rand.nextInt(Integer.parseInt(str[2]));
				int mod = Integer.parseInt(str[3]);
				int finale = max + mod;
				event.getChannel().sendMessage("your number is: " + finale);
			} else if (str[1].equals("help")) {
				event.getChannel();
				if (str.length <= 2) {
					event.getChannel().sendMessage("");
				}
			}
		}
	}
}
