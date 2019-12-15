package org.jointheleague.discord_bot_example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.CustomMessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class Memes extends CustomMessageCreateListener {

	public Memes(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!memes")) {
			event.getChannel().sendMessage(getMeme());
		}
	}

	public String getMeme() { 
	
		String imgurl = "";
		URL memesURL;
		
		try {
			memesURL = new URL("https://www.reddit.com/r/memes/");
			URLConnection con = memesURL.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String s = null;
			s = br.readLine();
			while (s != null) {
				
				if (s.contains("<img alt=\"Post image\"")) { //find first meme
					s=s.substring(s.indexOf("src=\"")+5);
					imgurl=s.substring(0,s.indexOf("\""));
					System.out.println(imgurl);
					break;
				}
				s = br.readLine();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imgurl;
	}

}
