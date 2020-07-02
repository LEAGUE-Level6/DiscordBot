package org.jointheleague.modules;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class ScreenCapture extends CustomMessageCreateListener {
	
	private static final String COMMAND = "!screenshot";

	public ScreenCapture(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Takes a screenshot, saves it to desktop, and sends it using the bot. The command can be delayed by a specified value in milliseconds (e.g. !screenshot 5000).");
	}

	@Override
	public void handle(MessageCreateEvent event){
		if(event.getMessageContent().toLowerCase().trim().startsWith(COMMAND)) {
			try {
				if(!event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "").equals("")) {
					int delay = Integer.parseInt(event.getMessageContent().replaceAll(" ", "").replace(COMMAND, ""));
					Thread.sleep(delay);
				}
				
				event.getChannel().sendMessage("Screenshot saved!");
				MessageBuilder builder = new MessageBuilder().addAttachment(new File(saveScreenShot()));
				builder.send(event.getChannel());
			} catch(NumberFormatException nfe) {
				event.getChannel().sendMessage("[!screenshot error] Please format the command correctly: !screenshot <delay in millisec>");
			} catch (InterruptedException ie) {
				event.getChannel().sendMessage("[!screenshot error] Something has interrupted the delay.");
			} catch (AWTException awte) {
				event.getChannel().sendMessage("[!screenshot error] Something went wrong when capturing the screen.");
			}
		}
	}
	
	private String saveScreenShot() throws AWTException {
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage screenshot = new Robot().createScreenCapture(screen);
		String name = "Screenshot.jpg";
		int num = 0;
		File[] files = new File(System.getProperty("user.home") + "/Desktop").listFiles();
		whileLoop:
		while(true) {
			for(int i = 0; i < files.length; i++) {
				if(files[i].getName().equals(name)) {
					num++;
					name = "Screenshot (" + num + ").jpg";
					break;
				}
				if(i == files.length - 1) {
					break whileLoop;
				}
			}
		}
		File output = new File(System.getProperty("user.home") + "/Desktop/" + name);
		
		try {
			ImageIO.write(screenshot, "jpg", output);
		} catch(Exception e) { }
		return output.getPath();
	}
	
}