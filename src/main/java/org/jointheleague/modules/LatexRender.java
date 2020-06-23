package org.jointheleague.modules;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class LatexRender extends CustomMessageCreateListener {
	
	public LatexRender(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().contains("!latex")) {
			String s = event.getMessageContent().substring(7);
			event.getChannel().sendMessage(sToImage(s));
		}
	}

	public String fix(String s){
		if(s.contains("\\")){
			return fix(s.replace("\\", "%5C"));
		}
		if(s.contains("{")){
			return fix(s.replace("{", "%7B"));
		}
		if(s.contains("}")){
			return fix(s.replace("}", "%7D"));
		}
		s = s.replaceAll(" ", "");
		return s;
	}
	
	public File sToImage(String s) {
		try {
			URL u = new URL(fix("https://latex.codecogs.com/png.latex?%5Cbg_white%20%5Clarge%20\\boxed{" + s + "}"));
			BufferedImage image = ImageIO.read(u);
			File f = new File("image.png");
			ImageIO.write(image, "png", f);
			return f;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
