package org.jointheleague.modules;


import java.nio.charset.StandardCharsets;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class TextStyler extends CustomMessageCreateListener {

	private static final String COMMAND = "!TextStyler";
	String regular = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	String oxford = "𝔮𝔴𝔢𝔯𝔱𝔶𝔲𝔦𝔬𝔭𝔞𝔰𝔡𝔣𝔤𝔥𝔧𝔨𝔩𝔷𝔵𝔠𝔳𝔟𝔫𝔪𝔔𝔚𝔈ℜ𝔗𝔜𝔘ℑ𝔒𝔓𝔄𝔖𝔇𝔉𝔊ℌ𝔍𝔎𝔏ℨ𝔛ℭ𝔙𝔅𝔑𝔐"; //2 char
	String oxfordThick = "𝖖𝖜𝖊𝖗𝖙𝖞𝖚𝖎𝖔𝖕𝖆𝖘𝖉𝖋𝖌𝖍𝖏𝖐𝖑𝖟𝖝𝖈𝖛𝖇𝖓𝖒𝕼𝖂𝕰𝕽𝕿𝖄𝖀𝕴𝕺𝕻𝕬𝕾𝕯𝕱𝕲𝕳𝕵𝕶𝕷𝖅𝖃𝕮𝖁𝕭𝕹𝕸"; //2 char
	String cursive = "𝓆𝓌𝑒𝓇𝓉𝓎𝓊𝒾𝑜𝓅𝒶𝓈𝒹𝒻𝑔𝒽𝒿𝓀𝓁𝓏𝓍𝒸𝓋𝒷𝓃𝓂𝒬𝒲𝐸𝑅𝒯𝒴𝒰𝐼𝒪𝒫𝒜𝒮𝒟𝐹𝒢𝐻𝒥𝒦𝐿𝒵𝒳𝒞𝒱𝐵𝒩𝑀";//2 char
	String cursiveThick = "𝓺𝔀𝓮𝓻𝓽𝔂𝓾𝓲𝓸𝓹𝓪𝓼𝓭𝓯𝓰𝓱𝓳𝓴𝓵𝔃𝔁𝓬𝓿𝓫𝓷𝓶𝓠𝓦𝓔𝓡𝓣𝓨𝓤𝓘𝓞𝓟𝓐𝓢𝓓𝓕𝓖𝓗𝓙𝓚𝓛𝓩𝓧𝓒𝓥𝓑𝓝𝓜"; //2char
	String hollow = "𝕢𝕨𝕖𝕣𝕥𝕪𝕦𝕚𝕠𝕡𝕒𝕤𝕕𝕗𝕘𝕙𝕛𝕜𝕝𝕫𝕩𝕔𝕧𝕓𝕟𝕞ℚ𝕎𝔼ℝ𝕋𝕐𝕌𝕀𝕆ℙ𝔸𝕊𝔻𝔽𝔾ℍ𝕁𝕂𝕃ℤ𝕏ℂ𝕍𝔹ℕ𝕄"; //2 char
	String blocks = "🅀🅆🄴🅁🅃🅈🅄🄸🄾🄿🄰🅂🄳🄵🄶🄷🄹🄺🄻🅉🅇🄲🅅🄱🄽🄼🅀🅆🄴🅁🅃🅈🅄🄸🄾🄿🄰🅂🄳🄵🄶🄷🄹🄺🄻🅉🅇🄲🅅🄱🄽🄼"; //2 char
	//also specify color, bold, italics, underline, strikethrough, (highlight?)
	
	
	public TextStyler(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Text Design: the spice of life. This program allows you to change the font[oxford, oxfordThick, cursive, cursiveThick, hollow, blocks] and style[Bold, Italics, Underline, Strikethrough]. Simply put call the TextStyler and then the specifications you want, and your original text in quotes");
	}
	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!random","");
			try {
				String input = cmd.substring(cmd.indexOf("\"")+1, cmd.lastIndexOf("\""));
				cmd = cmd.substring(0, cmd.indexOf("\"")) + cmd.substring(cmd.lastIndexOf("\"")+1);
				cmd = cmd.replace("!TextStyler", "");
				
				if(cmd.contains("oxfordThick")) {
					input = replaceLetters(input, oxfordThick);
				} else if(cmd.contains("oxford")) {
					input = replaceLetters(input, oxford);
				} else if(cmd.contains("cursiveThick")) {
					input = replaceLetters(input, cursiveThick);
				} else if(cmd.contains("cursive")) {
					input = replaceLetters(input, cursive);
				} else if(cmd.contains("hollow")) {
					input = replaceLetters(input, hollow);
				} else if(cmd.contains("blocks")) {
					input = replaceLetters(input, blocks);
				} else if(cmd.contains("regular")) {
					input = replaceLetters(input, regular);
				}
				
				if(cmd.contains("Bold")||cmd.contains("bold")) {
					input = "**"+input+"**";
				} if(cmd.contains("Italics")||cmd.contains("italics")) {
					input = "*"+input+"*";
				} if(cmd.contains("Underline")||cmd.contains("underline")) {
					input = "__"+input+"__";
				} if(cmd.contains("Strikethrough")||cmd.contains("strikethrough")) {
					input = "~~"+input+"~~";
				} 
				
				event.getChannel().sendMessage(input);
			}catch(StringIndexOutOfBoundsException e) {
				event.getChannel().sendMessage("don't forget to add quotations around your text that you want to change");
			}
				
			
		}
	}
	public String replaceLetters(String phrase, String newFont) {
		String Char;
		int throughIndex;
		String newPhrase = "";
		for (int i = 0; i < phrase.length(); i++) {
			Char = phrase.substring(i, i+1);
			try {
				throughIndex = regular.indexOf(Char);
				newPhrase = newPhrase + new String(newFont.substring(throughIndex*2,(throughIndex*2)+2).getBytes(StandardCharsets.UTF_16), StandardCharsets.UTF_16);;
			}catch(Exception e) {
				newPhrase = newPhrase + Char;
			}
		}
		return newPhrase;
	}
}
