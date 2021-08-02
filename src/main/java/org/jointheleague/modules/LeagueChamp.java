package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class LeagueChamp extends CustomMessageCreateListener {
	
	final String[] champions = {"Aatrox", "Ekko", "Jinx", "Miss Fortune", "Shen", "Varus", "Ahri", "Akali",	"Evelynn", "Karma", "Morgana", "Singed", "Veigar",
			"Ahri", "Elise", "Kalista", "Mordekaiser", "Shyvana", "Vayne", "Alistar", "Ezreal", "Karthus", "Nami", "Sion", "Vel'Koz", "Amumu", "Fiddlesticks", "Kassadin", "Nasus", "Sivir", "Vi", "Anivia", "Fiora", "Katarina", "Nautilus",
			"Skarner", "Viktor", "Annie", "Fizz", "Kayle", "Nidalee", "Sona", "Vladmir", "Ashe", "Galio", "Kennen", "Nocturne", "Soraka", "Volibear", "Aurelion Sol", "Gangplank", "Kha'Zix", "Nunu", "Swain", "Warwick", "Azir", "Garen", "Kindred", "Olaf", 
			 "Syndra", "Wukong", "Bard", "Gnar", "Kled", "Orianna", "Tahm Kench", "Xerath", "Blitzcrank", "Gragas", "Kog'Maw", "Pantheon", "Taliyah", "Xin Zhao", "Brand", "Graves",
			 "Leblanc", "Poppy", "Talon", "Yasuo", "Braum", "Hecarim", "Lee sin", "Quinn", "Taric", "Yorick", "Caitlyn", "Heimerdinger", "Leona", "Rammus", "Teemo", "Zac", "Camille", "Illaoi", "Lissandra", "Rek'Sai", "Thresh", "Zed", "Cassiopeia" ,
			 "Irelia", "Lucian", "Renekton", "Tristana", "Ziggs", "Cho'Gath", "Ivern", "Lulu", "Rengar", "Trundle", "Zilean", "Corki", "Janna", "Lux", "Riven", "Tryndamere", "Zyra", "Darius", "Jarvan IV", "Malphite ", "Rumble", "Twisted Fate", "Diana",
			 "Jax", "Malzahar", "Ryze", "Twitch", "Dr. Mundo", "Jayce", "Maokai", "Sejuani", "Udyr", "Draven", "Jhin", "Master Yi", "Shaco", "Urgot"};
	
	public LeagueChamp(String channelName) {
		super(channelName);
		
		// TODO Auto-generated constructor stub
	}
	
	
	private static final String COMMAND = "!randChamp";

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		if(event.getMessageContent().startsWith(COMMAND)) {
			event.getChannel().sendMessage("Your champion is: " + champions[rand.nextInt(champions.length)]);
		}
	}

}







