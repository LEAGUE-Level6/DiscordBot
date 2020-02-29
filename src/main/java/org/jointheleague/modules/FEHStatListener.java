package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class FEHStatListener extends CustomMessageCreateListener {
	String webpageRead;

	public FEHStatListener(String channelName) { //thank you http://zetcode.com/java/readwebpage/
		super(channelName);
		try {
			URL webpage = new URL("https://feheroes.gamepedia.com/Level_40_stats_table");
			BufferedReader br = new BufferedReader(new InputStreamReader(webpage.openStream()));
			String str = "";
			 while ((str = br.readLine()) != null) {
				webpageRead += str;
				webpageRead += "\n";
			}
			br.close();
			FileWriter fw = new FileWriter("src/main/resources/FEHStats.txt");
			fw.write(webpageRead);
			fw.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!fehstats")) {
			if (event.getMessageContent().length() == 9) {
				event.getChannel().sendMessage("Please specify which unit to search for :)");
			} else {
				String unitName = event.getMessageContent().substring(9);
				unitName = unitName.toLowerCase();
				event.getChannel().sendMessage(unitName);
			}
		}
	}

}
