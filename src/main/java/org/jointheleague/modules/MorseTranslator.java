package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.HashMap;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MorseTranslator extends CustomMessageCreateListener {
	
	//Commands
	static final String TOGGLE_MORSE = "!morse-toggle";
	static final String MANUAL_TRANSLATE = "!mrs";
	
	HashMap<Character, String> englishMorse = new HashMap<String, String>();
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
		//Toggles the Automatic Morse Translator
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
		//Checks for the manual translation command
		else if(event.getMessageContent().startsWith(MANUAL_TRANSLATE)) {
			
		}
		//If the user has morse toggled, it will automatically edit his message to be in morse code
		else if(toggledUsers.contains(event.getMessageAuthor().asUser().get().getMentionTag())) {
			
		}
	}
	
	String translateMorse(String english) {
		
		String translated = "";
		for(int i = 0; i < english.length(); i++) {
			//Characters. Checks if the morse HashMap contains the character. If not, it just displays it in its original form.
			if(englishMorse.containsKey(english.charAt(i))) {
				translated += englishMorse.get(english.charAt(i));
			}
			else if(english.charAt(i) != ' '){
				translated += english.charAt(i);
			}
			
			//Whitespace. In Morse Code. Spaces are three spaces (used to identify end of word) and one space is used to identify the end of a character and the beginning of another
			//morse character.
			char nextCharacter = english.charAt(i+1);
			if(nextCharacter == ' ') {
				translated += "   ";
			}
			else {
				translated += " ";
			}
		}
		
		return translated;
	}
	
	void generateMorse() {
		//Alphabet
		englishMorse.put('a', ".-");
		englishMorse.put('b', "-...");
		englishMorse.put('c', "-.-.");
		englishMorse.put('d', "-..");
		englishMorse.put('e', ".");
		englishMorse.put('f', "..-.");
		englishMorse.put('g', "--.");
		englishMorse.put('h', "....");
		englishMorse.put('i', "..");
		englishMorse.put('j', ".---");
		englishMorse.put('k', "-.-");
		englishMorse.put('l', ".-..");
		englishMorse.put('m', "--");
		englishMorse.put('n', "-.");
		englishMorse.put('o', "---");
		englishMorse.put('p', ".--.");
		englishMorse.put('q', "--.-");
		englishMorse.put('r', ".-.");
		englishMorse.put('s', "...");
		englishMorse.put('t', "-");
		englishMorse.put('u', "..-");
		englishMorse.put('v', "...-");
		englishMorse.put('w', ".--");
		englishMorse.put('x', "-..-");
		englishMorse.put('y', "-.--");
		englishMorse.put('z', "--..");
		
		//Numerals
		englishMorse.put('0', "-----");
		englishMorse.put('1', ".----");
		englishMorse.put('2', "..---");
		englishMorse.put('3', "...--");
		englishMorse.put('4', "....-");
		englishMorse.put('5', ".....");
		englishMorse.put('6', "-....");
		englishMorse.put('7', "--...");
		englishMorse.put('8', "---..");
		englishMorse.put('9', "----.");
		
		//Punctuation
		englishMorse.put('.', ".-.-.-");
		englishMorse.put(',', "--..--");
		englishMorse.put('?', "..--..");
	}

}
