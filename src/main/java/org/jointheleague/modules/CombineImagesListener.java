package org.jointheleague.modules;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.javacord.api.event.message.MessageCreateEvent;

public class CombineImagesListener extends CustomMessageCreateListener {
	
	final String command = "!combine";

	public CombineImagesListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String message = event.getMessageContent();
		if(message.contains(command)) {
			String[] posibleUrls = message.split(" ");
			ArrayList<String> urls = new ArrayList<>();
			for(int i = 0; i < posibleUrls.length; i++) {
				if(posibleUrls[i].contains("https://www.")) {
					urls.add(posibleUrls[i].trim());
				}
			}
			
			BufferedImage[] images = new BufferedImage[urls.size()];
			for(int i = 0; i < images.length; i++) {
				images[i] = ImageIO.read(new URL(urls.get(i)));
			}
			CustomImage[] imagesCustom = new CustomImage[images.length];
			
			for(int i = 0; i < images.length; i++) {
				PixleHelper[] pixles = new PixleHelper[images[i].getWidth() * images[i].getHeight()];
				for(int j = 0; j < images[i].getHeight(); j++) {
					for(int k = 0; k < images[i].getWidth(); k++) {
						pixles[i * images[i].getWidth() + k] = new PixleHelper(images[i].getRGB(k, j));
					}
				}
				imagesCustom[i] = new CustomImage(pixles, images[i].getWidth());
			}
			
			try {
				
				BufferedImage image = ImageIO.read(new URL(url));
				File outputfile = new File("src/main/resources/TempFile.png");
				ImageIO.write(image, "png", outputfile);
				
				event.getChannel().sendMessage(outputfile);
				//outputfile.delete();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
