package org.jointheleague.discord_bot_example;

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
import org.jointheleague.modules.CustomMessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class DeepFrier extends CustomMessageCreateListener {

	public DeepFrier(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}
	//{
    //"default": {
	//      "channel": "raghav",
	//      "token": ""    
	//    }
	//  }
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!deepfry")&&(event.getMessageContent().contains(".png")||event.getMessageContent().contains(".jpg"))) {
			String u = event.getMessageContent().toString();
			u=u.substring(u.indexOf("https"), u.lastIndexOf(".")+4);
			System.out.println(u);
			try {
				URL url = new URL(u);
		        BufferedImage img = ImageIO.read(url);
				for (int y = 0; y < img.getHeight(); y++) {
					for (int x = 0; x < img.getWidth(); x++) {
						int p = img.getRGB(x, y);

						int a = (p >> 24) & 0xff;
						int r = (p >> 16) & 0xff;

						// set new RGB
						// keeping the r value same as in original
						// image and setting g and b as 0.
						p = (a << 24) | (r << 16) | (0 << 8) | 0;

						img.setRGB(x, y, p);
					}
				}
				File file = new File("downloaded.jpg");
		        ImageIO.write(img, "jpg", file);
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
