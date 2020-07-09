package org.jointheleague.modules;

import java.util.HashMap;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MorseCodeTranslator extends CustomMessageCreateListener {

	public MorseCodeTranslator(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String m = event.getMessageContent();
		if (m.toLowerCase().contains("!morsecode")) {
			String phrase = m.substring(11).toLowerCase();
			HashMap<Character, String> guide = new HashMap<Character, String>();
			guide.put('a', ".-");
			guide.put('b', "-...");
			guide.put('c', "-.-.");
			guide.put('d', "-..");
			guide.put('e', ".");
			guide.put('f', "..-.");
			guide.put('g', "--.");
			guide.put('h', "....");
			guide.put('i', "..");
			guide.put('j', ".---");
			guide.put('k', "-.-");
			guide.put('l', ".-..");
			guide.put('m', "--");
			guide.put('n', "-.");
			guide.put('o', "---");
			guide.put('p', ".--.");
			guide.put('q', "--.-");
			guide.put('r', ".-.");
			guide.put('s', "...");
			guide.put('t', "-");
			guide.put('u', "..-");
			guide.put('v', "...-");
			guide.put('w', ".--");
			guide.put('x', "-..-");
			guide.put('y', "-.--");
			guide.put('z', "--..");
			guide.put('0', "-----");
			guide.put('1', ".----");
			guide.put('2', "..---");
			guide.put('3', "...--");
			guide.put('4', "....-");
			guide.put('5', ".....");
			guide.put('6', "-....");
			guide.put('7', "--...");
			guide.put('8', "---..");
			guide.put('9', "----.");
			guide.put('.', ".-.-.-");
			guide.put(',', "--..--");
			guide.put('?', "..--..");
			guide.put(':', "---...");
			guide.put('/', "-..-.");
			guide.put('-', "-....-");
			guide.put('=', "-...-");
			guide.put('\'', ".----.");
			guide.put('(', "-.--.-");
			guide.put(')', "-.--.-");
			guide.put('_', "..--..");
			guide.put('&', ".-...");
			guide.put('\"', ".-..-.");
			guide.put(';', "-.-.-.");
			guide.put('$', "...-..-");
			guide.put('!', "-.-.--");
			
			String morse = "";
			for (int i = 0; i < phrase.length(); i++) {
				if(guide.containsKey(phrase.charAt(i))) {
					morse += guide.get(phrase.charAt(i)) + " ";
				} else if (phrase.charAt(i) == ' ') {
					morse += " / ";
				} else {
					morse += phrase.charAt(i);
				}
			
			}
			event.getChannel().sendMessage(morse);
		}

	}
}
