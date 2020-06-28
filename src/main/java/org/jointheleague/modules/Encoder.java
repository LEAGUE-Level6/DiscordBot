package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class Encoder extends CustomMessageCreateListener {

	private final String help = "!encoderHelp";
	private final String encode = "!e";
	private final String decode = "!d";
	private final String pigLatin = "PigLatin"; 
	private final String shift = "Shift";
	private final String morse = "Morse";
	
	private final String[] letters = {"A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", 
			"T", "U", "V", "W", "X", "Y", "Z", ".", ","};
	private final String[] codes = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
			"....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", 
			"-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".-.-.-", "..-.."};
	
	
	public Encoder(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		

		if(message.startsWith(help)) {
			event.getChannel().sendMessage("Start your message with either " + encode + " (to encode) or " + decode + " (to decode).");
			event.getChannel().sendMessage("To encode/decode each different type of code, follow with: ");
			event.getChannel().sendMessage(pigLatin + " (for pig latin)");
			event.getChannel().sendMessage(shift + "# (for shift) (replace # with number to shift)");
			event.getChannel().sendMessage(morse + " (for morse code)");
		}
		
		else if(message.startsWith(encode)) {
			message = message.substring(encode.length());
			
			if(message.startsWith(pigLatin)) {
				message = message.substring(pigLatin.length() +1);
				event.getChannel().sendMessage(encodePigLatin(message));
			}
			else if(message.startsWith(shift)) {
				message = message.substring(shift.length());
				event.getChannel().sendMessage(encodeShift(message));
			}
			else if(message.startsWith(morse)) {
				message = message.substring(morse.length() +1);
				event.getChannel().sendMessage(encodeMorse(message));
			}
		}
		else if(message.startsWith(decode)) {
			message = message.substring(decode.length());
			
			if(message.startsWith(pigLatin)) {
				message = message.substring(pigLatin.length() +1);
				event.getChannel().sendMessage(decodePigLatin(message));
			}
			else if(message.startsWith(shift)) {
				message = message.substring(shift.length());
				event.getChannel().sendMessage(decodeShift(message));
			}
			else if(message.startsWith(morse)) {
				message = message.substring(morse.length() +1);
				event.getChannel().sendMessage(decodeMorse(message));

			}
		}
		
	}
	
	String encodePigLatin(String message) {
		if(message.indexOf(' ') == -1) {
			return message.substring(1) + message.charAt(0) + "ay";
		}
		else {
			String[] words = message.split(" ");
			String fin = "";
			
			for(String word : words) {
				fin += word.substring(1) + word.charAt(0) + "ay ";
			}
			
			return fin;
		}
	}
	
	String decodePigLatin(String message) {
		if(message.indexOf(' ') == -1) {
			return message.charAt(message.length()-3) + message.substring(0, message.length()-3);
		}
		else {
			String[] words = message.split(" ");
			String fin = "";
			
			for(String word : words) {
				fin += word.charAt(word.length()-3) + word.substring(0, word.length()-3) + " ";
			}
			
			return fin;
		}
	}
	
	String encodeShift(String message) {
		String shiftAmountS = message.substring(0, message.indexOf(' '));
		int shiftAmount = Integer.parseInt(shiftAmountS);
		
		message = message.substring(message.indexOf(' ') +1);
		String fin = "";
		
		for(int i = 0; i < message.length(); i++) {
			if(message.charAt(i) == ' ') fin += ' ';
			else fin += (char)(message.charAt(i)+shiftAmount);
		}
		
		return fin;
	}
	
	String decodeShift(String message) {
		String shiftAmountS = message.substring(0, message.indexOf(' '));
		int shiftAmount = Integer.parseInt(shiftAmountS);
		
		message = message.substring(message.indexOf(' ') +1);
		String fin = "";
		
		for(int i = 0; i < message.length(); i++) {
			if(message.charAt(i) == ' ') fin += ' ';
			else fin += (char)(message.charAt(i)-shiftAmount);
		}
		
		return fin;
	}
	
	String encodeMorse(String message) {
		String fin = "";
		for(int i = 0; i < message.length(); i++) {
			if(message.charAt(i) == ' ') fin += "// ";
			else {
				int index = getIndex(letters, message.toUpperCase().charAt(i)+"");
				if(index == -1) fin += message.charAt(i) + " ";
				else fin += codes[index] + " ";
			}
		}
		
		return fin;
	}
	
	String decodeMorse(String message) {
		String fin = "";
		String[] sLetters = message.split(" ");
		
		for(int i = 0; i < sLetters.length; i++	) {
			if(sLetters[i].equals("//")) fin += " ";
			else {
				int index = getIndex(codes, sLetters[i]);
				if(index == -1) fin += sLetters[i];
				else fin += letters[index];
			}
		}
		
		
		return fin.toLowerCase();
	}
	
	int getIndex(String[] arr, String str) {
		int i = 0;
		for(String x : arr) {
			if(x.equals(str)) return i;
			i++;
		}
		return -1;
	}
}
