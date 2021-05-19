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
					"1) Normandy beach (!DDay) \n 2) Midway island (!Midway) \n 3) Stalingrad (!Stalingrad)");

			event.getChannel().sendMessage(new File(image + "Normandy.jpg"));
			event.getChannel().sendMessage(new File(image + "midway.jpg"));
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
			} else if (txt.startsWith("!Midway")) {
				event.getChannel().sendMessage("Ok soldier, you will be deployed out in the pacific against japan!"
						+ "to survive this battle, you must play a game! Say !Continue to move on");
				stage++;
				if (stage >= 2) {
					if (txt.startsWith("!Contune")) {
						middway(event);
					}

				}
			}  else if (txt.startsWith("!Stalingrad")) {
				stage++;
			}
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
						+ " \n the correct answer was Soldier. If you wanna try again type !BeginWW2Journey");
				stage = 0;
				substage = 0;
			}
		}
		if (substage == 2) {
			event.getChannel().sendMessage("Now time for more questions.");
			event.getChannel().sendMessage("What is dnaraG 1M spelt fowards?");
			if (txt.startsWith("M1 Garand")) {
				event.getChannel().sendMessage("Nice work soldier!");
				event.getChannel().type();
				build = new EmbedBuilder();
				build.setColor(Color.orange).setTitle("M1 Garand").addField("Fun facts about the M1 Garand:",
						" This gun was used frequently used by"
								+ " US soldiers during WW2 and during the reload it had a special ding sound.");
				event.getChannel().sendMessage(build);
				event.getChannel().sendMessage("Here's what the M1 looks like:");
				event.getChannel().sendMessage(new File(image + "m1.jpg"));
				substage++;
			} else {
				event.getChannel().sendMessage("Sorry, you got the answer incorect. You have died. "
						+ " \n The correct answer was M1 Garand. If you wanna try again type !BeginWW2Journey");
				stage = 0;
				substage = 0;
			}

		}
		if (substage == 3) {
			event.getChannel().sendMessage("**"
					+ "Alright, heres a hard question. 2 more of these hard questions till u win the battle!" + "**");
			event.getChannel().sendMessage("What do many people call the Battle Of Normandy? Hint: it starts with a D");
			if (txt.equals("DDay") || txt.equals("dday") || txt.equals("invasion of normandy") || txt.equals("D-Day")
					|| txt.equals("d-day")) {
				event.getChannel().sendMessage("Great job!");
				event.getChannel().type();
				build = new EmbedBuilder();
				build.setColor(Color.cyan).setTitle("DDay, aka battle of normandy").addField(
						"Where the battle of normandy happened+ facts",
						"The battle of normandy occured at normandy beach, France when the \n"
								+ "Americans were on a dangerous and intense mission to get Germany to unoccupy france."
								+ "\n This also helped the allied forces to get closer to victory during the war!");
				event.getChannel().sendMessage(build);
				event.getChannel().sendMessage("Here's a map to show you where it happened:");
				event.getChannel().sendMessage(new File(image + "normandy map.jpg"));
				substage++;
			} else {
				event.getChannel().sendMessage("Sorry, you got the answer incorect. You have died. "
						+ " \n The correct answer was D-Day. If you wanna try again type !BeginWW2Journey");
				stage = 0;
				substage = 0;
			}
		}
		if (substage == 4) {
			event.getChannel().sendMessage("**" + "Final Question. If you get this right then you win!" + "**");
			event.getChannel().sendMessage("Lets see if you were paying attention: What gun was used in WW2 alot?");
			if (txt.equals("M1 garand") || txt.equals("M1") || txt.equals("garand") || txt.equals("M1 Garand")
					|| txt.equals("m1 garand")) {
				event.getChannel().sendMessage("Awesome job! You survived this battle!");

				substage++;
			} else {
				event.getChannel().sendMessage(
						"Sorry, you got the answer incorect. You have died when you almost won the battle. "
								+ " \n The correct answer was M1 Garand. If you wanna try again type !BeginWW2Journey");
				stage = 0;
				substage = 0;
			}
		}
		if (substage == 5) {
			event.getChannel()
					.sendMessage("If you want to start again you could do one of the battles listed above, or if you're"
							+ "done then type !go home, or say !continue if you want to continue");
			if (txt.equals("!go home")) {
				event.getChannel().sendMessage("Congrats soldier you have made it home safely!");
				stage = 0;
				substage = 0;
			} else if (txt.equals("!continue")) {
				event.getChannel().sendMessage(
						"Ok soldier, check the embed of the battle choices above so you can decide what battle you want to go to next!");
				stage = 0;
				substage = 0;
			}
		}
	}

	public void middway(MessageCreateEvent event) {
		int substage1 = 0;
		String txt = event.getMessageContent();
		EmbedBuilder build = new EmbedBuilder();
		event.getChannel().sendMessage("Here is your first question: ");
		substage1++;
		if (substage1 == 1) {
			event.getChannel().sendMessage("Spell Japan backwards");
			if (txt.equals("napaJ") || txt.equals("napaj")) {
				event.getChannel().sendMessage("Great work soldier");
				event.getChannel().type();
				build.setColor(Color.red).setTitle("Cool stuff about the battle of midway").addField(
						"Also some facts about the battle between japan vs USA",
						"After the pearl harbor bombing, aka the day we live in infamy,"
								+ "\n the US started getting fully involved in WW2.");
				build.addField("Another fact:",
						"The USA would do something known as \"Island Hopping\" which refers to them battling against japan "
								+ "\n and then setting up their bases on each island, helping them to progress closer to japan, towards victory");
				event.getChannel().sendMessage(build);
				
				substage1++;
			}
		}

	}

}
