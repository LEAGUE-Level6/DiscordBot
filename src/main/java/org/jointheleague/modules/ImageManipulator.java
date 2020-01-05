package org.jointheleague.modules;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ImageManipulator extends CustomMessageCreateListener {
	
	URL url;
boolean b = false;
		BufferedImage imagee;
		public ImageManipulator(String channelName) {
			super(channelName);
			
		}

		@Override
		public void handle(MessageCreateEvent event) {
			if (event.getMessageContent().startsWith("!image")) {
				event.getChannel().sendMessage("Please insert the image you want me to manipulate.");
				String urll = event.getMessageContent().substring(6);
				try {
					url = new URL(urll);
					event.getChannel().sendMessage("Step 1 Complete");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					event.getChannel().sendMessage("Not an image!");
				}
				
				
				try {
					 Image image = ImageIO.read(url);
					 imagee = (BufferedImage) image;
					event.getChannel().sendMessage("Finalizing");
					System.out.println("Image height " + imagee.getHeight());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					event.getChannel().sendMessage("Not an image!");
				}
				
		for (int i = 0; i < imagee.getHeight(); i++) {
		for (int j = 0; j < imagee.getWidth(); j++) {	
			int red =   (imagee.getRGB(i, j) >> 16) & 0xFF;
			int green = (imagee.getRGB(i, j) >>  8) & 0xFF;
			int blue =  (imagee.getRGB(i, j)      ) & 0xFF;
				System.out.println("Red " + red + "Green " + green+ "Blue " + blue);
		}
		}
		//event.getChannel().sendMessage("Is this the image you want me to use?");
		event.getChannel().sendMessage(urll);
		
		
			}
		}
		
		
		
}
