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
		
		public ImageManipulator(String channelName) {
			super(channelName);
			
		}

		@Override
		public void handle(MessageCreateEvent event) {
			if (event.getMessageContent().startsWith("!image")) {
				event.getChannel().sendMessage("Please insert the image you want me to manipulate.");
				if (event.getMessageContent() != null) {
				try {
					url = new URL(event.getMessageContent());
					event.getChannel().sendMessage("Step 1 Complete");
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					event.getChannel().sendMessage("Not an image!");
				}
				
				
				try {
					Image image = ImageIO.read(url);
					event.getChannel().sendMessage("Finalizing");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					event.getChannel().sendMessage("Not an image!");
				}
				}
		event.getChannel().sendMessage("Is this the image you want me to use?");
		
			}
		}
		
		
		
}
