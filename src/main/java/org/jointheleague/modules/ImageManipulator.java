package org.jointheleague.modules;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	StringBuilder sb;
	

	public ImageManipulator(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) {
		System.out.println("methpd is running");
		if (event.getMessageContent().startsWith("!image")) {
			System.out.println("methpd is runningasdfasdf");

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
			 sb = new StringBuilder((imagee.getWidth() + 1) * imagee.getHeight());
			checkChunks();
			
		}
		
		
//		File file = new File("downloaded.jpg");
//        try {
//        	System.out.println("Final string" + sb.toString());
//			ImageIO.write(textToJPG(sb.toString()), "jpg", file);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String content = sb.toString();
        try {
			PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/image.json", false));
			System.out.println(content);
			out.print(content);
			out.close();
			System.out.println("saved");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error saving");
		}
        File file = new File("src/main/resources/image.json");
        event.getChannel().sendMessage("Open this file to see your image.");
        event.getChannel().sendMessage(file);
	}
		
	
        //event.getMessage().getChannel().sendMessage(out);


	
	public void checkChunks() {
		for (int z = 0; z < imagee.getHeight(); z++)  {
			if (sb.length() != 0) {
				sb.append("\n");
			}
			for (int m = 0; m < imagee.getWidth(); m++) {
				//double chunkTotal = 0;

				
						Color color = new Color(imagee.getRGB(z, m));
						double red = color.getRed() * 0.2989;
						double green = color.getGreen() * 0.1140 ;
						double blue = color.getBlue() * 0.5870;
						System.out.println("Red " + red + "Green " + green + "Blue " + blue);
						//System.out.println(" Z " + z + " M " + m + " I " + i + " J " + j);
						double averageP = red + green + blue;
						//chunkTotal += averageP;
					
				
				//double chunkAvg = chunkTotal / 9;
				sb.append(returnStrPos(averageP));
				System.out.println(returnStrPos(averageP));
				System.out.println("Chunk average " + averageP);
				
			}

		}
	}
	private char returnStrPos(double g)//takes the grayscale value as parameter
    {
        final char str;

        if (g >= 230.0) {
            str = ' ';
        } else if (g >= 200.0) {
            str = '.';
        } else if (g >= 180.0) {
            str = '*';
        } else if (g >= 160.0) {
            str = ':';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = '&';
        } else if (g >= 70.0) {
            str = '8';
        } else if (g >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str; // return the character

    }
//	 public static BufferedImage textToJPG(String text) {
//	        /*
//	           Because font metrics is based on a graphics context, we need to create
//	           a small, temporary image so we can ascertain the width and height
//	           of the final image
//	         */
//	        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
//	        Graphics2D g2d = img.createGraphics();
//	        Font font = new Font("Arial", Font.PLAIN, 40);
//	        g2d.setFont(font);
//	        FontMetrics fm = g2d.getFontMetrics();
//	        int width = fm.stringWidth(text);
//	        int height = fm.getHeight();
//	        g2d.dispose();
//
//	        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//	        g2d = img.createGraphics();
//	        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//	        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//	        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//	        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//	        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//	        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
//	        g2d.setFont(font);
//	        
//	        fm = g2d.getFontMetrics();
//	        g2d.setColor(Color.WHITE);
//	        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
//	        g2d.setColor(Color.BLACK);
//	        g2d.drawString(text, 0, fm.getAscent());
//	        g2d.dispose();
//	        return img;
//
//	    	
//	    }  
}
