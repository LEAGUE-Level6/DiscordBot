package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

public class Astrology extends CustomMessageCreateListener {
	
	public static void main(String[] args) {
		
	}
	
	
	private static final String COMMAND = "!astrology";

	public Astrology(String channelName) {
		super(channelName);
	}

	

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!astrology","");
			
			if(cmd.equals("")) {
				
				String firstPart = JOptionPane.showInputDialog("What is your sign?\n \n Aries: March 21st - April 19th \n Taurus: April 20th - May 20th \n Gemini: May 21st - June 20th \n Cancer: June 21st - July 22nd \n Leo: July 23rd - August 22nd \n Virgo: August 23rd - September 22nd \n Libra: September 23rd - October 22nd \n Scorpio: October 23rd - November 21st \n Sagittarius: November 22nd - December 21st \n Capricorn: December 22nd - January 19th \n Aquarius: January 20th - February 18th \n Pisces: February 19th - March 20th \n");
				String secondPart = JOptionPane.showInputDialog("What is your partner's sign?\n \n Aries: March 21st - April 19th \n Taurus: April 20th - May 20th \n Gemini: May 21st - June 20th \n Cancer: June 21st - July 22nd \n Leo: July 23rd - August 22nd \n Virgo: August 23rd - September 22nd \n Libra: September 23rd - October 22nd \n Scorpio: October 23rd - November 21st \n Sagittarius: November 22nd - December 21st \n Capricorn: December 22nd - January 19th \n Aquarius: January 20th - February 18th \n Pisces: February 19th - March 20th \n");
				
				//ARIES
				if (firstPart.equals("Aries") && secondPart.equals("Aries")) {
					event.getChannel().sendMessage("These two signs are molded by the planet Mars, so they'll lock horns frequently in small spats. Occasionally, they'll take things up a notch and create some real drama together. Aries are very protective of their personal space, so each member of this team should take pains to respect the other's domain. When these two show respect for each other's talent, things will go smoothly. They should also take turns assuming the leadership role. When forming a united front, nothing can stop this dynamic duo.\n");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Taurus")) {
					event.getChannel().sendMessage("Aries likes to look before they leap. Taurus prefers weighing their options. This dynamic can result in power struggles or it can be mutually beneficial. Aries can help Taurus become more strategically spontaneous, springing into action at the opportune moment. Taurus will assist Aries in getting better value from their work, relationships, and creative efforts. Occasionally, it will be best to leave each other alone. Trying to change one another will just undermine the value of this friendship. Viva la difference!");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Gemini")) {
					event.getChannel().sendMessage("This is an incredibly dynamic friendship that seems to run on rocket fuel. Aries loves putting Gemini's brilliant ideas into practice. Gemini has fun egging Aries on to glory. Together, great things can be accomplished. These two friends make wonderful creative collaborators. The only downside to this alliance is a tendency toward heedlessness. Aries has no fear of danger and Gemini is always eager to see what's around the corner. This can cause some unfortunate accidents. The best gifts these friends can give each other is safety equipment.");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Cancer")) {
					event.getChannel().sendMessage("When these two Cardinal signs get together, amazing things can happen. Both Aries and Cancer are wonderful leaders. Aries is willing to take creative risks to succeed, while Cancer is able to tap into public sentiment. As a result, these friends may spend much of their time developing ideas for businesses, fundraisers and parties. At times, Aries's direct approach will send shy Cancer scuttling for cover. At others, Cancer's guilt trips will dampen Aries's enthusiasm. For the most part, though, these friends appreciate and celebrate each other's strengths.");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Leo")) {
					event.getChannel().sendMessage("Bold Aries and fun-loving Leo generate lots of excitement together! That's because both these friends are Fire Signs. Aries is the more outgoing of the two and may prefer seeking adventure while Leo luxuriates in comfort, waiting for a full report. These friends are also extremely active and may enjoy playing sports, thus bringing out each other's competitive side. Neither sign is a particularly gracious loser, so some heated arguments can erupt on the field or court. For the most part, however, each friend will bask in the other's glow.");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Virgo")) {
					event.getChannel().sendMessage("Heroic Aries and helpful Virgo are both service-oriented people. They may strike up a friendship while doing volunteer work or performing some selfless task. Although Aries doesn't have a filter and says whatever pops into his or her mind, Virgo is extremely conscientious and carefully censors their words. Aries thinks Virgo is too sensitive, while Virgo feels Aries is bombastic. Fortunately, the Ram appreciates it when the Virgin fixes their public relations disasters, while Virgo is grateful when Aries pushes them to take risks. Each brings something valuable to the other's table.");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Libra")) {
					event.getChannel().sendMessage("Independent Aries and collaborative Libra form a funny sort of friendship. Aries tries convincing Libra to take chances, shake off criticism, and be more direct. Libra constantly tells Aries to slow down, smell the flowers, and be more appreciative. It's an odd couple type of relationship that can work extremely well. Aries can learn the fine art of diplomacy from Libra, while Libra may become more assertive thanks to Aries. At times, they'll go their separate ways. Aries will head for the sports arena, while Libra will visit a museum. They need their own friends, not just one exclusive clique.");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Scorpio")) {
					event.getChannel().sendMessage("Daring Aries and passionate Scorpio form an explosive friendship. These two love getting into trouble together. Going to extremes is their idea of fun. Occasionally, the authorities might have to be called! Fortunately, Scorpio is an expert at getting out of messes. When they can't slip through the net, Aries usually can help them break free with a lot of fast talking. When they're getting along, these two can move mountains. A tendency to make mountains out of molehills will cause some heated fights, however.");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Sagittarius")) {
					event.getChannel().sendMessage("Dynamic Aries and adventurous Sagittarius feel an instant affinity. Both these Fire Signs like to keep moving, partying long into the night. Aries is more interested in what is hot and new, while Sagittarius has a reverence for time-honored traditions. These different tastes can actually bolster their friendship. Aries enjoys Sagittarius's ability to make any lesson fun, while Sagittarius is amused by Aries's boundless enthusiasm. Together, these two can paint the town red. A shared love of sports could prompt meetings at the gym, playing field, or other athletic arena.");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Sagittarius")) {
					event.getChannel().sendMessage("Dynamic Aries and adventurous Sagittarius feel an instant affinity. Both these Fire Signs like to keep moving, partying long into the night. Aries is more interested in what is hot and new, while Sagittarius has a reverence for time-honored traditions. These different tastes can actually bolster their friendship. Aries enjoys Sagittarius's ability to make any lesson fun, while Sagittarius is amused by Aries's boundless enthusiasm. Together, these two can paint the town red. A shared love of sports could prompt meetings at the gym, playing field, or other athletic arena.");
				}
				
				if (firstPart.equals("Aries") && secondPart.equals("Capricorn")) {
					event.getChannel().sendMessage("Youthful Aries and seasoned Capricorn form an odd friendship. While Aries is always looking forward, Capricorn prefers to look back. If each puts forth some effort, they can meet in the present. Both these signs are remarkable leaders and will enjoy building things of lasting value. Aries is great at knocking on doors and launching sales initiatives. Capricorn is proficient at creating long-term financial security. Together, these friends can reach great heights. The key is for these two to always be working on a challenging project.");
				}
			}
			
		}
	}
	
}
