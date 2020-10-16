package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class War extends CustomMessageCreateListener {
	private static final String COMMAND = "!fortune";
	private boolean fO = false;
	private int interaction = 0;
	String month;
	String object;
	
	 public War (String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use ! + sign to recieve a prediction about your life!");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageAuthor().getName().equals("Ella's Bot")) {
		return;
		}
		
		String a = event.getMessageContent();
		if(a.equals("!War")) {

			System.out.println("GOT MESSAGE");
			interaction = 1;
			event.getChannel().sendMessage("🂡");
		}
		else if (interaction==1) {
				month = event.getMessageContent();
				event.getChannel().sendMessage("Understood... Give me a moment as I evaluate your birthstone...~");
				if(month.contains("January")) {
					event.getChannel().sendMessage("https://www.ngja.gov.lk/images/gem-slide.png");
					event.getChannel().sendMessage("```Garnet represents confidence, energy and willpower. Those born in this month tend to be feisty and spontaneous, willing to take on any challenge that comes their way. Whether you're a lover or a fighter, this gem will keep you protected and warn of impending danger.```");
				}
				if(month.contains("February")) {
					event.getChannel().sendMessage("https://i.dlpng.com/static/png/1262912-ruby-diamond-mine-purple-diamond-purple-ruby-png-png-images-amethyst-stone-png-400_240_preview.png");
					event.getChannel().sendMessage("```Those born in February tend to be in a hurry — whether it's to get home, earn a promotion or start a family. They tend to think about the big picture, rather than break life down into its separate moments. Those born in February tend to be in a hurry — whether it's to get home, earn a promotion or start a family. They tend to think about the big picture, rather than break life down into its separate moments.```");
				}
				if(month.contains("March")) {
					event.getChannel().sendMessage("https://vignette.wikia.nocookie.net/aqua-aura-delure/images/6/68/Gem2.png/revision/latest?cb=20170909232820");
					event.getChannel().sendMessage("```If you were born in March, your birthstone is aquamarine. Some ancient cultures believed this sea-colored stone could protect sailors and guarantee a safe voyage. During the Middle Ages, many thought wearing aquamarine would keep them safe from poisoning. Roman tradition stated you could carve a frog into the gemstone to reconcile differences between enemies. Aquamarine personalities make for great mediators, adept at settling disputes in the fairest way possible. They weigh both sides of an argument and consider all parties involved. Even under pressure, they're able to think clearly, express their thoughts and make decisions.```");
				}
				if(month.contains("April")) {
					event.getChannel().sendMessage("https://lh3.googleusercontent.com/proxy/eJDrise9_7UDAhQ4tmGuESxC7U82sLI9_G_pCLu8Z32hinI_NPzlFe38NvjH72KJwSu5SJXwl8cLFKvA0Exegm0-Yz3Io7FKg28ncrL3I184TBlKguJURwE");
					event.getChannel().sendMessage("```If you were born in April, your birthstone is the illustrious diamond. These gems form under immense pressure, about 100 miles below the Earth's surface. Then, deep volcanic eruptions eject them violently upward, at around 20 to 30 miles per hour. Here, humans eventually uncover them. Someone born in April is a person you can depend on. While fearless and stubborn, they're also fiercely loyal, willing to stand up for friends and family in times of need. A diamond is a grandma who takes in stray family members without a home and volunteers at the soup kitchen. Or an overseas volunteer building schools for underprivileged children.```");
				}
				if(month.contains("May")) {
					event.getChannel().sendMessage("https://www.freepngimg.com/thumb/jewellery/47886-9-emerald-hd-free-clipart-hq.png");
					event.getChannel().sendMessage("```Those born in May bear an emerald birthstone, a favorite of Cleopatra's. Legend states the gem was one of four stones given to King Solomon by God, giving him power over all creation. When placed under the tongue, it supposedly provides the ability to see the future. Some also believed emerald could cure diseases like cholera and malaria, as well as protect against evil spells. The gemstone has links to love and fertility. Those born in May tend to be deep-thinking romantics, seeking out flirtatious encounters and lifelong soulmates. Once you are friends with a May personality, prepare to have them in your life forever. They seek truth over falseness and value honest relationships.```");
				}
				if(month.contains("June")) {
					event.getChannel().sendMessage("https://pngimg.com/uploads/pearl/pearl_PNG13.png");
					event.getChannel().sendMessage("https://img2.pngio.com/transparent-stones-alexandrite-transparent-png-clipart-free-alexandrite-png-300_225.png");
					event.getChannel().sendMessage("https://omiprive.com/wp-content/uploads/2019/10/Moonstone.png");
					event.getChannel().sendMessage("```Those born in June have three birthstones to choose from — pearl, alexandrite and moonstone. Choose a gem that resonates most with you and your personality or wear a combination of all three. June babies, born in early summer, are known for their enthusiasm and sense of clarity.```");
				}
				if(month.contains("July")) {
					event.getChannel().sendMessage("https://pngimg.com/uploads/ruby/ruby_PNG4.png");
					event.getChannel().sendMessage("```Were you born in July? Your birthstone is the ruby, gem of kings. The nobility wore it to symbolize their power and wealth. People once thought rubies, when worn over the heart on the left side of the body, would protect the wearer and their home from threats. drama. They love to sing and take part in theater performances. Their self-confidence makes them excellent leaders, even at a young age.```");
				}
				if(month.contains("August")) {
					event.getChannel().sendMessage("https://www.icagemlab.com/wp-content/uploads/2018/10/Peridot.png");
					event.getChannel().sendMessage("```Coming in at the tail end of summer are August babies. If you were born in this month, your birthstone is peridot. With a deep green color, the gem resembles emerald. For centuries, people thought the Three Holy Kings Shrine at Cologne Cathedral in Germany was decorated with 200 carats of emeralds. However, the beautiful stones turned out to be peridot. Those born in this month have a reputation for being incredibly kind. They offer a welcoming light to anyone they see, both strangers and friends. They tend to be open with others because they are unafraid to be themselves. Others tend to trust peridot personalities instantly due to their friendly and welcoming nature.```");
				}
				if(month.contains("September")) {
					event.getChannel().sendMessage("https://lh3.googleusercontent.com/proxy/MlgFcV210Y4iR2FMsk2Da5PUJlWdvl7xL0e-glPjMieO2hxsT4NAMKeNrWFZ9XU5PQonriQVam-E6nUTnoGAAZvaiTIzefFNSEBt77W3eXeEy4Q");
					event.getChannel().sendMessage("```September is the month of sapphire, a stone said to instill wisdom, loyalty and nobility. While traditional sapphires are blue, the gem occurs in all colors of the rainbow, except red. The stone has been popular since the Middle Ages. Greeks wore it for guidance when asking the oracle for answers, and Buddhists thought it could bring spiritual enlightenment. Sapphire birthstone bearers tend to have a quiet type of dignity signaling intelligence. They have a keen sense of calm in any situation and, as a result, can make calculated decisions in high-pressure environments.```");
				}
				if(month.contains("October")) {
					event.getChannel().sendMessage("https://www.bijouxbrio.com/wp-content/uploads/2016/06/bijoux-brio-pink-tourmaline.png");
					event.getChannel().sendMessage("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/ecdd1af8-cd5d-4451-8644-780cb137c71e/d8fq431-ac51243d-4ac2-494a-983d-f39728e5e6d7.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvZWNkZDFhZjgtY2Q1ZC00NDUxLTg2NDQtNzgwY2IxMzdjNzFlXC9kOGZxNDMxLWFjNTEyNDNkLTRhYzItNDk0YS05ODNkLWYzOTcyOGU1ZTZkNy5wbmcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.fJMML7jttQxmeP5TZkkkw6Qk2-Rbh94cknKY2GMIZzA");
					event.getChannel().sendMessage("```If you were born in October, you can lay claim to two different birthstones — opal and pink tourmaline. Tourmaline is one of the most colorful minerals on Earth, and the pink variety perfectly represents an October-born personality. It forms in the fractures of Earth's cavities during hydrothermal activity. Those with a pink tourmaline personality tend to be restless, explorers and adventurers looking for an untouched corner of the world. While they might seem calm and composed on the outside, those born in October tend to have racing minds, always thinking about what's coming next. They're also known for their strong intuition.```");
				}
				if(month.contains("November")) {
					event.getChannel().sendMessage("https://www.pngarts.com/files/2/Topaz-Stone-PNG-Download-Image.png");
					event.getChannel().sendMessage("```Those born in November have a birthstone of topaz, a label that encompasses all brown, orange and yellow transparent gems before the 1900s. Some believed the stone could attract gold, wealth and esteem. During the Middle Ages, carved topaz was thought to bring special powers. A falcon, for example, would help one reach the good graces of the king. Topaz birthstone holders have a reputation for being lucky — especially when it comes to finances. Whether it's a fun vacation, stellar career or new car, outsiders see those born in November as having it all. That's because they work hard and persevere, even when facing insurmountable odds.```");
				}
				if(month.contains("December")) {
					event.getChannel().sendMessage("https://img.pngio.com/turquoise-png-transparent-images-pictures-photos-png-arts-turquoise-stone-png-740_493.png");					
					event.getChannel().sendMessage("https://www.pngarts.com/files/2/Tanzanite-PNG-High-Quality-Image.png");
					event.getChannel().sendMessage("https://static.rldcdn.com/images/loose-gemstones/245px/T/marquise.png");
					event.getChannel().sendMessage("```There are three different birthstones attributed to December — blue topaz, tanzanite and turquoise. Those born during this month are known for being wise beyond their years, often touted as “old souls.” Tanzanite, another December birthstone, takes its name from the blue gem's only known deposit in northern Tanzania. Those with this birthstone tend to have a love for knowledge — they're always attending a new class or have a book in their hands. They're happy to start a conversation with a stranger and debate differing opinions. This type of person can discuss politics while keeping an open and accepting mind.```");
				}
				else {
					event.getChannel().sendMessage("That's not a month!! For now your birthstone will be dirt.");
				}
				interaction = 0;
		}

		else {
			System.out.println("FAILED");
		}
	
		
	}
}