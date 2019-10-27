package org.jointheleague.modules;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.javacord.api.event.message.MessageCreateEvent;

public class LatexRender extends CustomMessageCreateListener {
	
	public LatexRender(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().contains("!latex")) {
			String s = event.getMessageContent().substring(7);
			try {
				URL u = new URL("https://latex.codecogs.com/png.latex?%5Cbg_white%20%5Clarge%20\\boxed{" + s + "}");
				File file = Paths.get(u.toURI()).toFile();
				event.getChannel().sendMessage(file);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.getChannel().sendMessage("https://latex.codecogs.com/png.latex?%5Cbg_white%20%5Clarge%20\\boxed{" + s + "}");
		}
	}

	public String fix(String s){
		if(s.contains("\\")){
			return s.replace("\\", "%5C");
		}
		if(s.contains("{")){
			return s.replace("{", "%7B");
		}
		if(s.contains("}")){
			return s.replace("}", "%7D");
		}
		return s;
	}
	
}
