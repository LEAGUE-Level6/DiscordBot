
package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class SportFinder extends CustomMessageCreateListener {

	private static final String COMMAND = "!SportFinder";

	public SportFinder(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed(COMMAND,
				"Just type !SportFinder followed by the most common/known piece of equipment that your sport uses or 'none'. Make sure your sport is a popular one! It will narrow it down to a few options of sports that yours is part of.");

	}

	String equipment = " ";

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String response = event.getMessageContent();
		if (response.contains("!SportFinder")) {

			equipment = response.replace("!SportFinder", "").replaceAll(" ", "");
		}

		if (equipment.equalsIgnoreCase("racket")) {
			event.getChannel().sendMessage(
					"Your sport is either Tennis, Badminton, or Table Tennis. Is Rafael Nadal a pro player of your sport? Respond with !Nadal followed by yes or no");
		}

		if (response.contains("!Nadal")) {
			String racket = response.replace("!Nadal", "".replace(" ", ""));
			if (racket.equalsIgnoreCase("yes")) {
				event.getChannel().sendMessage("Your sport is tennis!");

			}
			else {
				event.getChannel().sendMessage("Your sport badminton or table tennis!");

			}
		}

		 if (equipment.equalsIgnoreCase("bat")) {
			event.getChannel().sendMessage(
					"Your sport is either Baseball, Softball, or Cricket. ");

		}

		 if (equipment.equalsIgnoreCase("goal")) {
			event.getChannel().sendMessage(
					"Your sport is either Soccer, Rugby, Football, Water polo, Lacrosse, Field Hockey, or Ice Hockey. ");

		}

		 if (equipment.equalsIgnoreCase("net")) {
			event.getChannel().sendMessage(
					"Your sport is either Basketball, Volleyball, Tennis, Badminton, Squash, Table Tennis, Racquetball, or Pickleball.");

		}

		 if (equipment.equalsIgnoreCase("none")) {
			event.getChannel().sendMessage(
					"Your sport is either Boxing, Wrestling, Swimming or Running.");

		}

		 if (equipment.equalsIgnoreCase("gloves")) {
			event.getChannel().sendMessage(
					"Your sport is either Boxing, Football, Baseball, Softball, or Golf. Too bad, many sports require gloves.");

		}

		 if (equipment.equalsIgnoreCase("club")) {
			event.getChannel().sendMessage("Your sport is golf! :)");

		}

		 if (equipment.equalsIgnoreCase("ball") || equipment.equalsIgnoreCase("balls")) {
			event.getChannel().sendMessage(
					"Your sport is either Football, Basketball, Soccer, Baseball, Tennis, Golf, Volleyball, Water Polo, Lacrosse, Softball, Cricket or Rugby. Almost every sport uses a ball, so to get an actual answer of what your sport is, please use a word more descriptive than 'ball'. Thanks!");

		}

		 if (equipment.equalsIgnoreCase("puck") || equipment.equalsIgnoreCase("stick")) {
			event.getChannel().sendMessage(
					"Your sport is either a version of hockey, Ice Hockey or Field Hockey.");

		}


		}if(equipment.equalsIgnoreCase("birdie")||equipment.equalsIgnoreCase("shuttlecock"))

	{
		event.getChannel().sendMessage("Your sport is Badminton! :)");

	}

	else
	{
		event.getChannel().sendMessage("Your sport is Car Racing/Nascar! :)");

	}

}

}