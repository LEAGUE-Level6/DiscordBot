package org.jointheleague.modules;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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
			System.out.println("P" + posibleUrls.length);
			ArrayList<String> urls = new ArrayList<>();
			for(int i = 0; i < posibleUrls.length; i++) {
				if(posibleUrls[i].contains("https://")) {
					System.err.println("ran");
					urls.add(posibleUrls[i].trim());
				}
			}
			
			BufferedImage[] images = new BufferedImage[urls.size()];
			//////////////////// us this shit images[0].getr
			System.err.println(images.length);
			for(int i = 0; i < images.length; i++) {
				try {
					images[i] = ImageIO.read(new URL(urls.get(i)));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			CustomImage[] imagesCustom = new CustomImage[images.length];
			
			for(int i = 0; i < images.length; i++) {
				PixleHelper[] pixles = new PixleHelper[images[i].getWidth() * images[i].getHeight()];
				for(int j = 0; j < images[i].getHeight(); j++) {
					for(int k = 0; k < images[i].getWidth(); k++) {
						pixles[j * images[i].getWidth() + k] = new PixleHelper(images[i].getRGB(k, j));
					}
				}
				imagesCustom[i] = new CustomImage(pixles, images[i].getWidth());
			}
			int maxWidth = 0;
			int maxHeight = 0;
			for(int i = 0; i < imagesCustom.length; i++) {
				if(imagesCustom[i].getHeight() > maxHeight) {
					maxHeight = imagesCustom[i].getHeight();
				}
				if(imagesCustom[i].getWidth() > maxWidth) {
					maxWidth = imagesCustom[i].getWidth();
				}
			}
			PixleHelper[] pixles2 = new PixleHelper[maxWidth * maxHeight];
			for(int i = 0; i < maxHeight; i++) {
				for(int j = 0; j < maxWidth; j++) {
					pixles2[i * maxWidth + j] = calcPixleAt(imagesCustom, i,j);
				}
			}
			CustomImage finalImage = new CustomImage(pixles2, maxWidth);
			int[] rgbPixles = new int[maxHeight * maxWidth];
			
			for(int i = 0; i < maxHeight; i ++) {
				for(int j = 0; j < maxWidth; j++) {
					rgbPixles[i * maxWidth + j] = finalImage.getPixleAt(j, i).convertToInt();
				}
			}
			System.err.println(rgbPixles.length+ " " + maxWidth + " " + maxHeight);
			BufferedImage finalImageBuffered = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
			finalImageBuffered.setRGB(0, 0, maxWidth, maxHeight, rgbPixles, 0, maxWidth);
			
			try {
				
				File outputfile = new File("src/main/resources/TempFile.png");
				ImageIO.write(finalImageBuffered, "png", outputfile);
				
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
	
	public PixleHelper calcPixleAt(CustomImage[] images, int x, int y){
		int r = 0,g = 0,b = 0;
		int num = 0;
		for(int i = 0; i < images.length;i++) {

			if(!images[i].getPixleAt(x, y).exists) {
				r += images[i].getPixleAt(x, y).r;
				g += images[i].getPixleAt(x, y).g;
				b += images[i].getPixleAt(x, y).b;
				num++;
			}
		}
		r = Math.round(r/num);
		g = Math.round(g/num);
		b = Math.round(b/num);
		return new PixleHelper(r,g,b);

	}
}
