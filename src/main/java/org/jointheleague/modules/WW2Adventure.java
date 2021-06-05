package org.jointheleague.modules;

import java.awt.Color;
import java.io.File;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class WW2Adventure extends CustomMessageCreateListener {
	private static final String COMMAND = "!BeginWW2Journey";
	private static final String CONTINUECMD = "!deploy";
	private final Color col = Color.CYAN;
	private final String image = "src/main/resources/WW2Images/";
	private int stage = 0;
	private int stage1 = 0;

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

		if (txt.equalsIgnoreCase(COMMAND)) {
			build.setColor(col);
			build.setTitle("**" + "Hello soldier, you have received a draft to the war!" + "**" + "\n" + "**"
					+ "What battle would you like to be deployed in, soldier?" + "**");
			build.setDescription("1) Normandy beach (!DDay) or \n 2) Midway island (!Midway))");
			build.setThumbnail(
					"https://ichef.bbci.co.uk/news/976/cpsprodpb/CD5C/production/_107227525_landing_craft.jpg");
			build.setImage("https://i.pinimg.com/originals/79/7f/7c/797f7c26a67cfb1c691ccaa7d540df24.jpg");

			event.getChannel().sendMessage(build);

		}
		if (stage >= 0) {
			if (txt.equalsIgnoreCase("!DDay")) {
				event.getChannel().sendMessage("Alright soldier, you have entered a really tough battle, "

						+ "to survive this battle, you must play a game! Say !deploy to move on");
				stage++;
			}
			if (stage >= 1) {
				if (txt.equalsIgnoreCase(CONTINUECMD)) {
					DDay(event);
				}
			}
			if (stage1 >= 0) {
				if (txt.equalsIgnoreCase("!Midway")) {
					event.getChannel().sendMessage("Ok soldier, you will be deployed out in the pacific against japan!"
							+ "to survive this battle, you must play a game! Say !deploy to move on");
					stage1++;
				}
				if (stage1 >= 1) {
					if (txt.equalsIgnoreCase(CONTINUECMD)) {
						middway(event);
					}

				}
			}
		}
	}

	public void DDay(MessageCreateEvent event) {
		stage++;
		String txt = event.getMessageContent();
		EmbedBuilder build = new EmbedBuilder();
		if (stage >= 3) {
			event.getChannel().sendMessage("Alright, your first task is to spell Soldier backwards");
			stage++;
		}
		if (stage >= 4) {
			if (txt.equalsIgnoreCase("reidloS")) {
				event.getChannel().sendMessage("Good work! You will continue");
				stage += 2;

			} else {
				stage++;

			}
		}
		if (stage >= 5) {
			event.getChannel().sendMessage("Sorry, you got the answer incorect. You have died."
					+ " \n the correct answer was reidloS. If you wanna try again type !BeginWW2Journey");
			stage = 0;

		}
		if (stage >= 6) {
			event.getChannel().sendMessage("Now time for more questions.");
			event.getChannel().type();
			event.getChannel().sendMessage("What is dnaraG 1M spelt fowards?");
			stage++;
		}
		if (stage >= 7) {
			if (txt.startsWith("M1 Garand")) {
				event.getChannel().sendMessage("Nice work soldier!");
				event.getChannel().type();
				stage++;
			}
			if (stage >= 7) {

				build = new EmbedBuilder();
				build.setColor(Color.orange).setTitle("M1 Garand").addField("Fun facts about the M1 Garand:",
						" This gun was used frequently used by"
								+ " US soldiers during WW2 and during the reload it had a special ding sound.");
				build.setThumbnail(
						"https://cdn11.bigcommerce.com/s-hqfgd419q8/images/stencil/1280x1280/products/3032/7377/22-1105_1K__37608.1580151079.jpg?c=2");
				event.getChannel().sendMessage(build);
				event.getChannel().sendMessage("Here's what the M1 looks like:");
				event.getChannel().sendMessage(new File(image + "m1.jpg"));
				stage++;

			} else {
				event.getChannel().sendMessage("Sorry, you got the answer incorect. You have died. "
						+ " \n The correct answer was M1 Garand. If you wanna try again type !BeginWW2Journey");
				stage = 0;

			}

		}
		if (stage >= 8) {
			event.getChannel().sendMessage("**"
					+ "Alright, heres a hard question. 2 more of these hard questions till u win the battle!" + "**");
			event.getChannel().sendMessage("What do many people call the Battle Of Normandy? Hint: it starts with a D");
			stage++;
		}
		if (stage >= 9) {
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
				build.addField("Heres an image of a map where DDAY happened", "");
				build.setImage("https://i.pinimg.com/originals/9a/b1/bf/9ab1bfb9b109aa1b13af667d9d9b1a93.jpg");
				event.getChannel().sendMessage(build);

				stage++;
			} else {
				event.getChannel().sendMessage("Sorry, you got the answer incorect. You have died. "
						+ " \n The correct answer was D-Day. If you wanna try again type !BeginWW2Journey");
				stage = 0;

			}

		}
		if (stage >= 10) {
			event.getChannel().sendMessage("**" + "Final Question. If you get this right then you win!" + "**");
			event.getChannel().sendMessage("Lets see if you were paying attention: What gun was used in WW2 alot?");
			stage++;
		}
		if (stage >= 11) {
			if (txt.equals("M1 garand") || txt.equals("M1") || txt.equals("garand") || txt.equals("M1 Garand")
					|| txt.equals("m1 garand")) {
				event.getChannel().sendMessage("Awesome job! You survived this battle!");

				stage++;
			} else {
				event.getChannel().sendMessage(
						"Sorry, you got the answer incorect. You have died when you almost won the battle. "
								+ " \n The correct answer was M1 Garand. If you wanna try again type !BeginWW2Journey");
				stage = 0;

			}
		}
		if (stage == 12) {
			event.getChannel()
					.sendMessage("If you want to start again you could do one of the battles listed above, or if you're"
							+ "done then type !go home, or say !continue if you want to continue");
			stage++;
		}
		if (stage >= 13) {
			if (txt.equals("!go home")) {
				event.getChannel().sendMessage("Congrats soldier you have made it home safely!");
				stage = 0;

			} else if (txt.equals(CONTINUECMD)) {
				event.getChannel().sendMessage(
						"Ok soldier, check the embed of the battle choices above so you can decide what battle you want to go to next!");
				stage = 0;

			}
		}
	}

	public void middway(MessageCreateEvent event) {

		String txt = event.getMessageContent();
		EmbedBuilder build = new EmbedBuilder();
		event.getChannel().sendMessage("Here is your first question: ");
		stage1++;
		if (stage1 >= 3) {
			event.getChannel().sendMessage("Spell Japan backwards");
			stage1++;
		}
		if (stage1 >= 4) {
			if (txt.equals("napaJ") || txt.equals("napaj")) {
				event.getChannel().sendMessage("Great work soldier");
				event.getChannel().type();
				build = new EmbedBuilder();
				build.setThumbnail("https://i.pinimg.com/originals/79/7f/7c/797f7c26a67cfb1c691ccaa7d540df24.jpg");
				build.setColor(Color.red).setTitle("Cool stuff about the battle of midway").addField(
						"Also some facts about the battle between japan vs USA",
						"After the pearl harbor bombing, aka the day we live in infamy,"
								+ "\n the US started getting fully involved in WW2.");
				build.addField("Another fact:",
						"The USA would do something known as \"Island Hopping\" which refers to them battling against japan "
								+ "\n and then setting up their bases on each island, helping them to progress closer to japan, towards victory");
				event.getChannel().sendMessage(build);
				stage1++;
			} else {
				event.getChannel().sendMessage(
						"Sorry soldier, you have died! The correct answer was napaJ. Type !BeginWW2Journey if you wanna start over.");
				stage1 = 0;

			}

		}
		if (stage1 == 5) {
			event.getChannel().sendMessage("Here is your next question: ");
			event.getChannel().type();
			event.getChannel().sendMessage("What ocean did the battle of midway happen in?");
			if (txt.equalsIgnoreCase("Pacific") || txt.equalsIgnoreCase("Pacific ocean")) {
				event.getChannel().sendMessage("Nice Job soldier!!");
				build.setThumbnail(
						"https://omniatlas-1598b.kxcdn.com/media/img/articles/subst/asia-pacific/asia-pacific19420607.png");
				build.setColor(Color.red).setTitle("Some things to know about the battle of Midway: ");
				build.setDescription(
						"The battle of midway was a major turning point for the US in the pacfic as they were coming closer and closer to defeating Japan.");
				build.addField("Another Fact: ",
						"After the US won this battle, victory for Japan and the axis were no longer able to happen");
				event.getChannel().sendMessage(build);
				stage1++;
			} else {
				event.getChannel().sendMessage("You have died, the Correct answer is pacific ocean");
				stage1 = 0;

			}
		}
		if (stage1 == 6) {
			event.getChannel().type();
			event.getChannel().sendMessage("Your next question is: /n spell Midway in alphabetical order");
			if (txt.equalsIgnoreCase("adiMwy")) {
				event.getChannel().sendMessage("Nice one!");
				stage1++;
			} else {
				event.getChannel().sendMessage("Sorry you have died. The correct answer is adiMwy.");
				stage = 0;

			}
		}
		if (stage1 == 7) {
			event.getChannel().type();
			event.getChannel().sendMessage(
					"Here is your final question. This will be tough, but if you get it right then you win this battle!");
			build = new EmbedBuilder();
			build.setTitle("Your final question (true or false): is this midway island?");
			build.setColor(Color.gray);
			build.setThumbnail("https://web.mst.edu/~rogersda/military_service/Eastern%20Island.jpg");
			event.getChannel().sendMessage(build);
		}
		if (txt.equalsIgnoreCase("true") || txt.equalsIgnoreCase("yes")) {
			event.getChannel().sendMessage("WWOOO U GOT IT BRO!");
			event.getChannel().type();
			event.getChannel().sendMessage("If you wanna play again type !BeginWW2Journey. If you want");
			stage1 = 0;

		} else {
			event.getChannel().sendMessage("Sorry you have died. The correct answer is true.");
			stage1 = 0;

		}

	}

}
