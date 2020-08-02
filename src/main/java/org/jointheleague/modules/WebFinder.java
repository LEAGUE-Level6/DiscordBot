package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class WebFinder extends CustomMessageCreateListener {

	public WebFinder(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed("!WebFinder",
				"After typing **!WebFinder** just type any word, and it will take you to the .com version of the website. There are other commands that give you web links as well."
						+ " \nAfter **!CountryPopulation** type a country to get its population. \nAfter **!CountryInfo** type the country to get info about it. \nAfter **!NFLStats** type a player to get their stats (full name). \nAfter **!NBAStats** type a player's full name to get their stats.");

//		helpEmbed = new HelpEmbed("!NFLStats", "After !NFLStats type a player to get their stats (full name).");
//		
//		helpEmbed = new HelpEmbed("!StatePopulation", "After !StatePopulation type the abbreviation for the state to get its population.");
//		
//		helpEmbed = new HelpEmbed("!CountryPopulation", "After !CountryPopulation type a country to get its population.");
//		
//		helpEmbed = new HelpEmbed("!CountryInfo", "After !CountryInfo type the country to get info about it");
//		
//		helpEmbed = new HelpEmbed("!NBAStats", "After !NBAStats type a player's full name to get their stats.");

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String s = "";
		String message = event.getMessageContent();
		if (message.contains("!WebFinder")) {
			String ws = message.replace("!WebFinder", "").replaceAll(" ", "");
			event.getChannel().sendMessage("https://" + ws + ".com");
		}

		else if (message.contains("!NFLStats")) {
			String player = message.replace("!NFLStats ", "");
			for (int i = 0; i < player.length(); i++) {
				if (i != 0 && player.charAt(i) == ' ') {
					s += player.charAt(i);
					player = player.replace(s, "-");
				}
			}
			event.getChannel().sendMessage("https://www.nfl.com/players/" + player + "/");
		}

		else if (message.contains("!StatePopulation")) {
			String state = message.replace("!StatePopulation ", "").replaceAll(" ", "");
			event.getChannel().sendMessage("https://www.census.gov/quickfacts/" + state);
		}

		else if (message.contains("!CountryPopulation")) {
			String country = message.replace("!CountryPopulation ", "").replaceAll(" ", "");
			event.getChannel()
					.sendMessage("https://www.worldometers.info/world-population/" + country + "-population/");
		}

		else if (message.contains("!CountryInfo")) {
			String country = message.replace("!CountryInfo ", "").replaceAll(" ", "");
			event.getChannel().sendMessage("https://www.britannica.com/place/" + country);
		}

		else if (message.contains("!NBAStats")) {
			String players = message.replace("!NBAStats ", "").replaceAll(" ", "/");
			event.getChannel().sendMessage("https://www.nba.com/players/" + players);
		}

	}
}
