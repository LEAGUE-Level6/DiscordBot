package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;
import java.util.Map;

public class SemaphoreFlashCards extends CustomMessageCreateListener {
	boolean gameStatus = true;
	int scoreCounter = 0;
	HashMap<String, String> semMap = new HashMap<>();
	Random rand = new Random();
	Lock lock = new ReentrantLock();
	String userResponse = "";
	String flashCard[] = { };
	int counter = 1;
	public SemaphoreFlashCards(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	public void flashCardsList() {

		// This method stores all of the semaphore pictures
		semMap.put("a",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-a-alpha.png");
		semMap.put("b",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-b-bravo.png");
		semMap.put("c",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-c-charlie.png");
		semMap.put("d",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-d-delta.png");
		semMap.put("e",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-e-echo.png");
		semMap.put("f",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-f-foxtrot.png");
		semMap.put("g",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-g-golf.png");
		semMap.put("h",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-h-hotel.png");
		semMap.put("i",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-i-india.png");
		semMap.put("j",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-j-juliet.png");
		semMap.put("k",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-k-kilo.png");
		semMap.put("l",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-l-lima.png");
		semMap.put("m",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-m-mike.png");
		semMap.put("n",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-n-november.png");
		semMap.put("o",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-o-oscar.png");
		semMap.put("p",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-p-papa.png");
		semMap.put("q",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-q-quebec.png");
		semMap.put("r",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-r-romeo.png");
		semMap.put("s",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-s-sierra.png");
		semMap.put("t",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-t-tango.png");
		semMap.put("u",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-u-uniform.png");
		semMap.put("v",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-v-victor.png");
		semMap.put("w",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-w-whiskey.png");
		semMap.put("x",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-x-xray.png");
		semMap.put("y",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-y-yankee.png");
		semMap.put("z",
				"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-z-zulu.png");

	}
	public void flashCardShuffle() {
		flashCardsList();
		Random rand = new Random();
		String letters = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i <= 10 ; i++) {
			char letterHolder = letters.charAt(rand.nextInt(letters.length()));
			flashCard[i] = letterHolder + "";
		}
		
	}

	@Override
	public void handle(MessageCreateEvent event) {
		
		//This portion checks if the letter type into discord chat is from the English alphabet and returns the letter in the semaphore language. 
		flashCardsList();
		String holder = event.getMessageContent();
		String semImage = semMap.get(holder.toLowerCase());
		if (!semImage.equals(null)) {
			event.getChannel().sendMessage(semImage);
			
		}
		else {
			event.getChannel().sendMessage("Not valid");
		}
	
		// bot sends message, instructions, and begins when the right word commands are
		// typed into discord server chat
		if (event.getMessageContent().equalsIgnoreCase("!semaphore")) {
			event.getChannel().sendMessage("This is a semaphore translator");
			event.getChannel().sendMessage("type !helpSemaphore for instructions");
		}
		if (event.getMessageContent().equalsIgnoreCase("!helpSemaphore")) {
			event.getChannel().sendMessage(
					"type a single English alphabetical letter into the discrod chat.");
			event.getChannel().sendMessage("Non English letters and special characters are unavailable");
		}
		// !startStudy will make to bot send a url and the user will reply with what
		// they think it is
		if (event.getMessageContent().equalsIgnoreCase("!semLetter")) {
			flashCardsList();
			System.out.println("working");
			for (Map.Entry<String, String> entry : semMap.entrySet()) {
				if (event.getMessageContent().equalsIgnoreCase(entry.getValue())) {
					System.out.println("working for ");
					event.getChannel().sendMessage(entry.getValue());
				}
			}

		}

		if (event.getMessageContent().equalsIgnoreCase("!startStudy")) {
			flashCardsList();
			event.getChannel().sendMessage(
					"\"https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-a-alpha.png\"");
			// problem: pictures are sent to discord chat all at once without letting the
			// user answer
			// what it's suppose to be: picture is sent in chat, user replies, check if it's
			// rights, send message back to user, then move on to the next picture.
		}
		event.getMessageContent().equals(userResponse);
		/*
		 * for (Map.Entry<String, String> entry : semMap.entrySet()) {
		 * System.out.println(entry); //* boolean checkAnswer = false;
		 * event.getChannel().sendMessage(semMap.get(entry)); if
		 * (event.getMessageContent().equalsIgnoreCase(entry.getValue())) { checkAnswer
		 * = true;
		 * 
		 * } else {
		 * 
		 * checkAnswer = false; } if (checkAnswer == true) { scoreCounter = scoreCounter
		 * + 1; event.getChannel().sendMessage("You got it right!" + scoreCounter);
		 * event.getChannel().sendMessage(
		 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-a-alpha.png"
		 * ); } else if (checkAnswer == false) {
		 * event.getChannel().sendMessage("The right answer is actually ");
		 * event.getChannel().sendMessage(
		 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-b-bravo.png"
		 * ); }
		 */
		// }

	}

}

/*
 * // a urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-a-alpha.png"
 * ); // b urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-b-bravo.png"
 * ); // c urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-c-charlie.png"
 * ); // d urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-d-delta.png"
 * ); // e urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-e-echo.png"
 * ); // f urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-f-foxtrot.png"
 * ); // g urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-g-golf.png"
 * ); // h urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-h-hotel.png"
 * ); // i urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-i-india.png"
 * ); // j urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-j-juliet.png"
 * ); // k urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-k-kilo.png"
 * ); // l urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-l-lima.png"
 * ); // m urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-m-mike.png"
 * ); // n urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-n-november.png"
 * ); // 0 urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-o-oscar.png"
 * ); // p urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-p-papa.png"
 * ); // q urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-q-quebec.png"
 * ); // r urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-r-romeo.png"
 * ); // s urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-s-sierra.png"
 * ); // t urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-t-tango.png"
 * ); // u urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-u-uniform.png"
 * ); // v urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-v-victor.png"
 * ); // w urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-w-whiskey.png"
 * ); // x urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-x-xray.png"
 * ); // y urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-y-yankee.png"
 * ); // z urlSem.add(
 * "https://storage.googleapis.com/welovesailing-117.appspot.com/images/sailing-theory/sailing-communication/semaphore-flags/sailing-semaphore-signal-flag-z-zulu.png"
 * );
 * 
 * 
 * /*Random random = new Random(); for (int i = 0; i < urlSem.size()-1; i++) {
 * int randNum = random.nextInt(26);
 * event.getChannel().sendMessage(urlSem.get(randNum)); urlSem.remove(randNum);
 * ArrayList<String> newUrlSem = new ArrayList<String>(i); }
 */
// ArrayList<String> urlSem = new ArrayList<String>(26);
/*
 * void letterList() { letters.add("a"); letters.add("b"); letters.add("c");
 * letters.add("d"); letters.add("e"); letters.add("f"); letters.add("g");
 * letters.add("h"); letters.add("i"); letters.add("j"); letters.add("k");
 * letters.add("l"); letters.add("m"); letters.add("n"); letters.add("o");
 * letters.add("p"); letters.add("q"); letters.add("r"); letters.add("s");
 * letters.add("t"); letters.add("u"); letters.add("v"); letters.add("w");
 * letters.add("x"); letters.add("y"); letters.add("z");
 * 
 * }
 */