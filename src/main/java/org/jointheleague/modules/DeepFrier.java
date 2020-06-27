package org.jointheleague.modules;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class DeepFrier extends CustomMessageCreateListener {

	public DeepFrier(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}
//	{
//    "default": {
//	      "channel": "raghav",
//	      "token": ""    
//	    }
//	  }
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!deepfry")&&(event.getMessageContent().contains(".png")||event.getMessageContent().contains(".jpg"))) {
			String u = event.getMessageContent().toString();
			u=u.substring(u.indexOf("https"), u.lastIndexOf(".")+4);
			//System.out.println(u);
			try {
				URL url = new URL(u);
		        BufferedImage img = ImageIO.read(url);
				for (int y = 0; y < img.getHeight(); y++) {
					for (int x = 0; x < img.getWidth(); x++) {
						Color c = new Color(img.getRGB(x, y));
						int r = c.getRed();
						int g = c.getGreen();
						int b = 0;
						
						double distanceToCenter = Math.hypot(x-(img.getWidth()/2), y-(img.getHeight()/2));
					    distanceToCenter = 1/(1+Math.exp(-distanceToCenter/Math.hypot(x, y)));
					    if (Math.abs(((int)(distanceToCenter*10))/10f-distanceToCenter)<0.02)
					    	g*=1f-(((int)(distanceToCenter*10+Math.random()/20))/10f);
					    g*=1f-((int)(distanceToCenter*10))/10f;
					    	
						Color color = new Color(r,g,b);
						if(Math.random()<0.07)
							color = new Color(255,255,255);
						else if (Math.random()<0.14) {
							color = new Color(255,255, 0);
						} else if (Math.random()<0.21) {
							color = new Color(255,0, 0);
						}

						img.setRGB(x, y, color.getRGB());
					}
				}
				File file = new File("downloaded.jpg");
		        ImageIO.write(img, "jpg", file);
		        event.getMessage().getChannel().sendMessage(file);
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
