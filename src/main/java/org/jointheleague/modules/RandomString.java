package org.jointheleague.modules;

	import java.util.ArrayList;
	import java.util.Random;
	import java.util.concurrent.ThreadLocalRandom;

	import org.javacord.api.event.message.MessageCreateEvent;
	import org.jointheleague.modules.pojo.HelpEmbed;

	public class RandomString extends CustomMessageCreateListener {

		private static final String COMMAND = "!randomstring";
		char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

		
		public RandomString(String channelName) {
			super(channelName);
			helpEmbed = new HelpEmbed(COMMAND, "Creates a random string with a random amount of characters");
		
		}

		@Override
		public void handle(MessageCreateEvent event) {
			String contents = event.getMessageContent();
			String[] cmds = contents.split(" ");
			if (contents.contains(COMMAND)) {
				Random r = new Random();
				int numOfCharacters = r.nextInt(20);
				
				String newString = "";
				for (int i = 0; i < numOfCharacters; i++) {
				    int rnd = new Random().nextInt(alphabet.length - 1);
				    newString += alphabet[rnd];
				}
				event.getChannel().sendMessage("**Random String:** " + newString);
			}
		}
		
	}


