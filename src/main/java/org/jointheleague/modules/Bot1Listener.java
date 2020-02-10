package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Bot1Listener extends CustomMessageCreateListener {

	public Bot1Listener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains("*bet") && event.getMessageAuthor().getId() == 622894941667983404L) {
			// -------------------------------------------------------
			String pass = "";
			int cc = 0;
			int luck = 0;
			try {
				BufferedReader r = new BufferedReader(new FileReader("src/main/resources/Untitled 1.txt"));
				pass = r.readLine();
				cc = Integer.parseInt(r.readLine());
				luck = Integer.parseInt(r.readLine());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileWriter fw = null;
			try {
				fw = new FileWriter("src/main/resources/Untitled 1.txt", false);
			} catch (IOException e2) {
				event.getChannel().sendMessage("Yikes, something mega wack");
			}

			try {
				String[] m = event.getMessageContent().split(" ");
				int am = Integer.parseInt(m[1]);
				if (am <= cc) {
					int second = 0;
					Random r = new Random();
					int first = r.nextInt(19) + 1;
					if (luck > 0) {
						event.getChannel().sendMessage("Haha! You feel like you could roll a 6!");
						second = r.nextInt(14) + 1;
					} else {
						second = r.nextInt(17) + 1;
					}
					
					
					event.getChannel().sendMessage("Your number was " + first + ", and my number was " + second + ".");
					if (first > second) {
						int multi = 0;
						if(luck>0) {
							multi = r.nextInt(90) + 40;
						}else {
							multi = r.nextInt(60) + 70;
						}
						event.getChannel().sendMessage(
								"Darn! Your number was a smidge more than mine! Your multiplier was " + multi + ".");
						cc += cc * multi * 0.01;
						if (luck > 0) {
							luck--;
						}
						fw.write(pass + "\n" + cc + "\n" + luck);
						fw.flush();

					} else {
						event.getChannel().sendMessage("Haha! I win uwu");
						cc -= am;
						if (luck > 0) {
							luck--;
						}
						fw.write(pass + "\n" + cc + "\n" + luck);
						fw.flush();
					}
				}
			} catch (Exception e) {
				event.getChannel().sendMessage("Try again, but this time use ya brain");
			}
		}

		// -----------------------------------------------------

		if (event.getMessageContent().equals("*bal") && event.getMessageAuthor().getId() == 622894941667983404L) {
			String pass = "";
			int cc = 0;
			int luck = 0;
			try {
				BufferedReader r = new BufferedReader(new FileReader("src/main/resources/Untitled 1.txt"));
				pass = r.readLine();
				cc = Integer.parseInt(r.readLine());
				luck = Integer.parseInt(r.readLine());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			event.getChannel().sendMessage("Your current balance of CodingCoins is " + cc + ", and you have "+luck+" more lucky bets. lmaoooo git good");
		}
		if (event.getMessageAuthor().getId() != 622894941667983404L
				&& event.getMessageAuthor().getId() != 673595407011414016L) {
			event.getChannel().sendMessage("Out, Normie!");
		}
	}

}
