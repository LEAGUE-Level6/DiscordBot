package org.jointheleague.modules;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.javacord.api.event.message.MessageCreateEvent;

public class LatexRender extends CustomMessageCreateListener {
	
	public LatexRender(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().contains("!latex")) {
			String s = event.getMessageContent();
			String sFix = fix(s);
			System.out.println(sFix);
			URI u;
			File f;
			try {
				u = new URI("https://latex.codecogs.com/gif.latex?" + sFix.substring(7));
				f = new File(u);
				event.getChannel().sendMessage(f);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			//File f = new File("https://latex.codecogs.com/gif.latex?" + s.substring(7));
			//event.getChannel().sendMessage("https://latex.codecogs.com/gif.download?aaa" + s.substring(7));
			System.out.println("here");
		}
	}
	
	public String fix(String s) {
		String s1 = s.replace("{", "%7B");
		String s2 = s1.replace("\\", "%5C");
		return s2.replace("}", "%7D");
	}
	
}
