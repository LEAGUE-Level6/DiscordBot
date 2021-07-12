package org.jointheleague.modules;

import java.util.Iterator;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Millbot extends CustomMessageCreateListener{

	public Millbot(String channelName) {
		super(channelName);
	}
	static final String ADD = "!add ";
	static final String SUBTRACT = "!subtract ";
	static final String MULTIPLY = "!multiply ";
	static final String DIVIDE = "!divide ";
	static final String LOG = "!log ";
	static final String POWER = "!power ";
	static final String helpMe = "!help";

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		
		String m = event.getMessageContent();
		
		if(m.contains(ADD)) {
			event.getChannel().sendMessage("Command received");
			String newM = m.replace(ADD, "");
			String[] addends = newM.split(",");
			int counter = 0;
			String botMessage = "";
			for (int i = 0; i < addends.length; i++) {
				botMessage = botMessage + addends[i] + " + ";
				counter = counter + Integer.parseInt(addends[i]);
			}
			botMessage = botMessage.substring(0, botMessage.length() - 3) + " = " + counter;
			System.out.println(botMessage + "this is a bot message");
			System.out.println(botMessage);
			event.getChannel().sendMessage(botMessage);
		} else if (m.contains(SUBTRACT)) {
			event.getChannel().sendMessage("Command received");
			String newM = m.replace(SUBTRACT, "");
			String[] subtractends = newM.split(",");
			int counter = Integer.parseInt(subtractends[0]);
			String botMessage = subtractends[0] + " - ";
			for (int i = 1; i < subtractends.length; i++) {
				botMessage = botMessage + subtractends[i] + " - ";
				counter = counter - Integer.parseInt(subtractends[i]);
			}
			botMessage = botMessage.substring(0, botMessage.length() - 3) + " = " + counter;
			System.out.println(botMessage + "this is a bot message");
			System.out.println(botMessage);
			event.getChannel().sendMessage(botMessage);
		} else if (m.contains(MULTIPLY)){
			event.getChannel().sendMessage("Command received");
			String newM = m.replace(MULTIPLY, "");
			String[] products = newM.split(",");
			int counter = 1;
			String botMessage = "";
			for (int i = 0; i < products.length; i++) {
				botMessage = botMessage + products[i] + " × ";
				counter = counter * Integer.parseInt(products[i]);
			}
			botMessage = botMessage.substring(0, botMessage.length() - 3) + " = " + counter;
			System.out.println(botMessage + "this is a bot message");
			System.out.println(botMessage);
			event.getChannel().sendMessage(botMessage);
		} else if (m.contains(DIVIDE)) {
			event.getChannel().sendMessage("Command received");
			String newM = m.replace(DIVIDE, "");
			String[] theStuff = newM.split(",");
			int counter = Integer.parseInt(theStuff[0]);
			String botMessage = theStuff[0] + " / ";
			for (int i = 1; i < theStuff.length; i++) {
				botMessage = botMessage + theStuff[i] + " / ";
				counter = counter / Integer.parseInt(theStuff[i]);
			}
			botMessage = botMessage.substring(0, botMessage.length() - 3) + " = " + counter;
			System.out.println(botMessage + "this is a bot message");
			System.out.println(botMessage);
			event.getChannel().sendMessage(botMessage);
		} else if (m.contains(LOG)) {
			event.getChannel().sendMessage("Command received");
			String newM = m.replace(LOG, "");
			String[] theStuff = newM.split(",");
			if (theStuff.length == 2) {
				double answer = Math.log(Integer.parseInt(theStuff[1])) / Math.log(Integer.parseInt(theStuff[0]));
				String message = "log" + subscript(theStuff[0]) + "(" + theStuff[1] + ") = " + Double.toString(answer);
				System.out.println(message + "this is a bot message");
				System.out.println(message);
				event.getChannel().sendMessage(message);
			} else {
				event.getChannel().sendMessage("You must provide two numbers in the format !log base,answer");
			}
			
		} else if (m.contains(POWER)){
			event.getChannel().sendMessage("Command received");
			String newM = m.replace(POWER, "");
			String[] theStuff = newM.split(",");
			String botMessage = "";
			int counter = 1;
			for (int i = 1; i < theStuff.length; i++) {
				counter = counter * Integer.parseInt(theStuff[i]);
			}
			double answer = Math.pow(Integer.parseInt(theStuff[0]), counter);
			String message = theStuff[0] + superscript(Integer.toString(counter)) + " = " + answer;
			event.getChannel().sendMessage(message);
		}
		
		else if(m.equals(helpMe)) {
			event.getChannel().sendMessage("Type ![name of function] and put the numbers you want to add after it in a list like so:\n!add A,B,C");
			event.getChannel().sendMessage("Supported functions are addition, subtraction, multiplication, division, logarithms, exponents, and all trigonometric functions.");
			event.getChannel().sendMessage("For logarithmic functions, type numbers in order of base then answer.");
		}
		
	}
	
	public static String superscript(String str) {
	    str = str.replaceAll("0", "⁰");
	    str = str.replaceAll("1", "¹");
	    str = str.replaceAll("2", "²");
	    str = str.replaceAll("3", "³");
	    str = str.replaceAll("4", "⁴");
	    str = str.replaceAll("5", "⁵");
	    str = str.replaceAll("6", "⁶");
	    str = str.replaceAll("7", "⁷");
	    str = str.replaceAll("8", "⁸");
	    str = str.replaceAll("9", "⁹");         
		return str;
	}	
		
	public static String subscript(String str) {
	    str = str.replaceAll("0", "₀");
	    str = str.replaceAll("1", "₁");
	    str = str.replaceAll("2", "₂");
	    str = str.replaceAll("3", "₃");
	    str = str.replaceAll("4", "₄");
	    str = str.replaceAll("5", "₅");
	    str = str.replaceAll("6", "₆");
	    str = str.replaceAll("7", "₇");
	    str = str.replaceAll("8", "₈");
	    str = str.replaceAll("9", "₉");
	    return str;
	}

}