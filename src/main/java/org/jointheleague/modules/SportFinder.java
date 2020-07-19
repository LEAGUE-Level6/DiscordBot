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
				"Just type !sportfinder followed by the most common/known piece of equipment that your sport uses or 'none'. Make sure your sport is a popular one! It will keep asking you questions until it determines your sport.");

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String response = event.getMessageContent();
		String equipment = response.replace("!SportFinder", "").replaceAll(" ", "");

		if (equipment.equalsIgnoreCase("racket")) {
			event.getChannel().sendMessage(
					"Your sport is either Tennis, Badminton, Squash, Table Tennis, Racquetball, or Pickleball. Is Rafael Nadal a pro player of your sport?");
		}

		else if (equipment.equalsIgnoreCase("bat")) {
			event.getChannel().sendMessage(
					"Your sport is either Baseball, Softball, or Cricket. Is your sport played popular in England, Australia, and India? Reply with yes or no and be sure to use the !SportFinder command");
//			if (equipment.equalsIgnoreCase("yes")) {
//				event.getChannel().sendMessage("Your sport is Cricket!");
//			}
		}

		else if (equipment.equalsIgnoreCase("goal")) {
			event.getChannel().sendMessage(
					"Your sport is either Soccer, Rugby, Football, Water polo, Lacrosse, Field Hockey, or Ice Hockey. Is your sport played in the pool? Reply with yes or no and be sure to use the !SportFinder command");

		}

		else if (equipment.equalsIgnoreCase("net")) {
			event.getChannel().sendMessage(
					"Your sport is either Basketball, Volleyball, Tennis, Badminton, Squash, Table Tennis, Racquetball, or Pickleball. Is your sport's pro league called the NBA Reply with yes or no and be sure to use the !SportFinder command");

		}

		else if (equipment.equalsIgnoreCase("none")) {
			event.getChannel().sendMessage(
					"Your sport is either Boxing, Wrestling, Swimming or Running. Is your sport played in the pool? Reply with yes or no and be sure to use the !SportFinder command");

		}
		
		else if (equipment.equalsIgnoreCase("gloves")) {
			event.getChannel().sendMessage(
					"Your sport is either Boxing, Football, Baseball, Softball, or Golf. Does your sport involve fighting? Reply with yes or no and be sure to use the !SportFinder command");

		}
		
		else if (equipment.equalsIgnoreCase("club")) {
			event.getChannel().sendMessage(
					"Your sport is golf! :)");

		}
		
		else if (equipment.equalsIgnoreCase("ball") || equipment.equalsIgnoreCase("balls")) {
			event.getChannel().sendMessage(
					"Your sport is either Football, Basketball, Soccer, Baseball, Tennis, Golf, Volleyball, Water Polo, Lacrosse, Softball, Cricket or Rugby. Almost every sport uses a ball, so to get an actual answer of what your sport is, please use a word more descriptive than 'ball'. Thanks!");

		}
		
		else if (equipment.equalsIgnoreCase("puck")) {
			event.getChannel().sendMessage(
					"Your sport is either Ice Hockey or Field Hockey. Is your sport played on ice? Reply with yes or no and be sure to use the !SportFinder command");

		}
		
		else if (equipment.equalsIgnoreCase("stick")) {
			event.getChannel().sendMessage(
					"Your sport is either Ice Hockey or Field Hockey. Is your played on ice? Reply with yes or no and be sure to use the !SportFinder command");

		}
		else if (equipment.equalsIgnoreCase("birdie") || equipment.equalsIgnoreCase("shuttlecock")) {
			event.getChannel().sendMessage(
					"Your sport is Badminton! :)");

					
		}
		
		else if (equipment.equalsIgnoreCase("car") ) {
			event.getChannel().sendMessage(
					"Your sport is Car Racing! :)");

		}
		
	}

}