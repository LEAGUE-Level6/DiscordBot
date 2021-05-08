package org.jointheleague.modules;

import java.awt.Color;
import java.io.File;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class WW2Adventure extends CustomMessageCreateListener {
	private static final String COMMAND = "!BeginWW2Journey";

	private final Color col = Color.CYAN;
	private final String image = "src/main/resources/WW2Images/";
	private int stage = 0;

	public WW2Adventure(String channelName) {
		// TODO Auto-generated constructor stub
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + BeginWW2Journey to begin your journey soldier!");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub

		EmbedBuilder build = new EmbedBuilder();
		String txt = event.getMessageContent();

		if (txt.startsWith(COMMAND)) {
			build.setColor(col);
			build.setTitle("**" + "Hello soldier, you have received a draft to the war!" + "**" + "\n" + "**"
					+ "What battle would you like to be deployed in, soldier?" + "**");
			build.setDescription(
					"1) Normandy beach (!DDay) \n 2) Midway island (!Midway) \n 3) Dunkirk (!Dunkirk) \n 4) Stalingrad (!Stalingrad)");

			event.getChannel().sendMessage(new File(image + "Normandy.jpg"));
			event.getChannel().sendMessage(new File(image + "midway.jpg"));
			event.getChannel().sendMessage(new File(image + "dunkirk.jpg"));
			event.getChannel().sendMessage(new File(image + "stalingrad.jpg"));
			event.getChannel().sendMessage(build);
			stage++;
		}
		if (stage >= 1) {
			if (txt.equalsIgnoreCase("!DDay")) {
				event.getChannel().sendMessage("Alright soldier, you have entered a really tough battle, "

						+ "to survive this battle, you must play a game! Say !Continue to move on");
				stage++;
			}
			if (stage >= 2) {
				if (txt.startsWith("!Continue")) {
					DDay(event);
				}
			}
		} else if (txt.startsWith("!Midway")) {

		} else if (txt.startsWith("!Dunkirk")) {

		} else if (txt.startsWith("!Stalingrad")) {

		}

	}

	public void DDay(MessageCreateEvent event) {
		int substage = 0;
		String txt = event.getMessageContent();
		EmbedBuilder build = new EmbedBuilder();

		event.getChannel().sendMessage("Alright, your first task is to spell Soldier backwards");
		substage++;
		if (substage == 1) {
			if (txt.startsWith("reidloS")) {
				event.getChannel().sendMessage("Good work! You will continue");
				substage++;
			} else {
				event.getChannel().sendMessage("Sorry, you got the answer incorect. You have died."
						+ " \n If you wanna try again type !BeginWW2Journey");
			}

			event.getChannel().sendMessage("Now time for more questions.");
			event.getChannel().sendMessage("What is dnaraG 1M spelt fowards?");
			if (txt.startsWith("M1 Garand")) {
				event.getChannel().sendMessage("Nice work soldier!");
				event.getChannel().type();
				build.setColor(Color.orange).setTitle("M1 Garand").addField("Fun facts about the M1 Garand:",
						"Fun fact about the M1 Garand: This gun was used frequently used by"
								+ " US soldiers during WW2 and during the reload it had a special ding sound.");
				event.getChannel().sendMessage(build);
				event.getChannel().sendMessage("Here's what the M1 looks like:");
				
			}
		}
	}
}
