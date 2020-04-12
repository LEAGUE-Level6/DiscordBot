package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.omg.CORBA.portable.InputStream;

public class Name extends CustomMessageCreateListener {
	private static final String COMMAND = "!Name";
	public Name(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(COMMAND)) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace("!Name","");
			try {
				readFromWeb("https://www.behindthename.com/random/random.php?number=1&sets=1&gender=both&surname=&all=yes", event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	 public static void readFromWeb(String webURL, MessageCreateEvent event) throws IOException {
	        URL url = new URL(webURL);
	        InputStream is =  (InputStream) url.openStream();
	        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            	event.getChannel().sendMessage(line);
	            }
	        }
	        catch (MalformedURLException e) {
	            e.printStackTrace();
	            throw new MalformedURLException("URL is malformed!!");
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	            throw new IOException();
	        }
}
}