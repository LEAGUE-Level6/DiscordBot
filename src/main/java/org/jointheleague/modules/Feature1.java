package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class Feature1 extends CustomMessageCreateListener {
	private static final String COMMAND = "!random";
	
	 private String tiger = "The tiger has a muscular body with powerful forelimbs, a large head and a tail that is about half the length of its body. Its pelage is dense and heavy, and colouration varies between shades of orange and brown with white ventral areas and distinctive vertical black stripes that are unique in each individual.";
	 private String lion = "Lions have strong, compact bodies and powerful forelegs, teeth and jaws for pulling down and killing prey. Their coats are yellow-gold, and adult males have shaggy manes that range in color from blond to reddish-brown to black. The length and color of a lion's mane is likely determined by age, genetics and hormones.";
	 private String cheetah = "Cheetahs (Acinonyx jubatus), known as the fastest land animals, have long, slender bodies covered with unique black spots scattered across their tan coats. The name cheetah comes from the Sanskrit word \"chitraka,\" which means \"the spotted one,\" according to the World Wildlife Fund.";
	 private String lynx = "The lynx is a solitary cat that haunts the remote northern forests of North America, Europe, and Asia. Lynx are covered with beautiful thick fur that keeps them warm during frigid winters. Their large paws are also furry and hit the ground with a spreading toe motion that makes them function as natural snowshoes.";
	 private String leopard = "Leopards are large cats, with light-colored fur, and black spots and rosettes across their entire body. The rosettes look somewhat like hollowed-out spots, and are smaller than those of the jaguar. Males of the species are larger than the females, and can stand up to 28 in. tall at the shoulder.";
	 

	
	 public Feature1 (String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Allows you to get a random number.  You can also specify a range of values (e.g. !random 50-100)");
	}

	@Override
	public void handle(MessageCreateEvent event) {

		
		String a = event.getMessageContent();
		if(a.equals("!tiger")) {
			event.getChannel().sendMessage(tiger);
		}
		if(a.equals("!lion")) {
			event.getChannel().sendMessage(lion);
		}
		if(a.equals("!cheetah")) {
			event.getChannel().sendMessage(cheetah);
		}
		if(a.equals("!lynx")) {
			event.getChannel().sendMessage(lynx);
		}
		if(a.equals("!leopard")) {
			event.getChannel().sendMessage(leopard);
		}
		if(a.equals("!help")) {
			event.getChannel().sendMessage(leopard);
		}
		
	}
}

