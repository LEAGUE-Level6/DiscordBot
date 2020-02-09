package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Bot1Listener extends CustomMessageCreateListener{

	public Bot1Listener(String channelName) {
		super(channelName);
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().contains("*bet")&&event.getMessageAuthor().getId()==622894941667983404L) {
		//-------------------------------------------------------
		String pass="";
		int cc=0;
		try {
			BufferedReader r =new BufferedReader(new FileReader("src/main/resources/Untitled 1.txt"));
			pass=r.readLine();
			cc=Integer.parseInt(r.readLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			try {
				String[] m = event.getMessageContent().split(" ");
				int am = Integer.parseInt(m[1]);
				if(am<=cc) {
					Random r = new Random();
					int first = r.nextInt(19)+1;
					int second = r.nextInt(14)+1;
					event.getChannel().sendMessage("Your number was "+first+", and my number was "+second+".");
					if(first>second) {
						int multi = r.nextInt(60)+70;
						event.getChannel().sendMessage("Darn! Your number was a smidge more than mine! Your multiplier was "+multi+".");
						cc+=cc*multi*0.01;
					}else {
						event.getChannel().sendMessage("Haha! I win uwu");
						cc-=am;
					}
					}
			}catch(Exception e) {
				event.getChannel().sendMessage("Try again, but this time use ya brain");
			}
		}
		if(event.getMessageContent().equals("*bal")&&event.getMessageAuthor().getId()==622894941667983404L) {
			String pass="";
			int cc=0;
			try {
				BufferedReader r =new BufferedReader(new FileReader("src/main/resources/Untitled 1.txt"));
				pass=r.readLine();
				cc=Integer.parseInt(r.readLine());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			event.getChannel().sendMessage("Your current balance of CodingCoins is "+cc+" lmao git good");
			}
		if(event.getMessageAuthor().getId()!=622894941667983404L&&event.getMessageAuthor().getId()!=673595407011414016L) {
			event.getChannel().sendMessage("Out, Normie!");
		}
	}

}
