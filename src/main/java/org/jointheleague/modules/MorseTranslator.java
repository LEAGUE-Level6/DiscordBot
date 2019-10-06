package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.HashMap;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MorseTranslator extends CustomMessageCreateListener {
	
	//Commands
	static final String TOGGLE_MORSE = "!morse-toggle";
	static final String MANUAL_TRANSLATE = "!mrs";
	
	HashMap<String, String> englishMorse = new HashMap<String, String>();
	ArrayList<String> toggledUsers = new ArrayList<String>();
	
	
	public MorseTranslator(String channelName) {
		super(channelName);
	}

	//Utility
	String findMentionTag(MessageCreateEvent event) {
		return event.getMessageAuthor().asUser().get().getMentionTag();
	}
	
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().startsWith(TOGGLE_MORSE)) {
			if(toggledUsers.contains(findMentionTag(event))) {
				toggledUsers.remove(findMentionTag(event));
				event.getChannel().sendMessage("Automatic Morse Translation Toggled Off");
			}
			else {
				toggledUsers.add(findMentionTag(event));
				event.getChannel().sendMessage("Automatic Morse Translation Toggled On");
			}
		}
		if(event.getMessageContent().startsWith(MANUAL_TRANSLATE)) {
			
		}
	}
	
	String translateMorse(String english) {
		return null;
	}
	
	void generateMorse() {
		//Alphabet
		englishMorse.put("a", ".-");
		englishMorse.put("b", "-...");
		englishMorse.put("c", "-.-.");
		englishMorse.put("d", "-..");
		englishMorse.put("e", ".");
		englishMorse.put("f", "..-.");
		englishMorse.put("g", "--.");
		englishMorse.put("h", "....");
		englishMorse.put("i", "..");
		englishMorse.put("j", ".---");
		englishMorse.put("k", "-.-");
		englishMorse.put("l", ".-..");
		englishMorse.put("m", "--");
		englishMorse.put("n", "-.");
		englishMorse.put("o", "---");
		englishMorse.put("p", ".--.");
		englishMorse.put("q", "--.-");
		englishMorse.put("r", ".-.");
		englishMorse.put("s", "...");
		englishMorse.put("t", "-");
		englishMorse.put("u", "..-");
		englishMorse.put("v", "...-");
		englishMorse.put("w", ".--");
		englishMorse.put("x", "-..-");
		englishMorse.put("y", "-.--");
		englishMorse.put("z", "--..");
		
		//Numerals
		englishMorse.put("0", "-----");
		englishMorse.put("1", ".----");
		englishMorse.put("2", "..---");
		englishMorse.put("3", "...--");
		englishMorse.put("4", "....-");
		englishMorse.put("5", ".....");
		englishMorse.put("6", "-....");
		englishMorse.put("7", "--...");
		englishMorse.put("8", "---..");
		englishMorse.put("9", "----.");
		
		//Punctuation
		englishMorse.put(".", ".-.-.-");
		englishMorse.put(",", "--..--");
		englishMorse.put("?", "..--..");
	}

}
