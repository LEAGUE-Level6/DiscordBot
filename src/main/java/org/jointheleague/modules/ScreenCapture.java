package org.jointheleague.modules;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class ScreenCapture extends CustomMessageCreateListener {
	
	private static final String COMMAND = "!screenshot";

	public ScreenCapture(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Takes a screenshot and sends it using the bot. The captured can be delayed by a specified value in milliseconds (e.g. !screenshot 5000).");
	}

	@Override
	public void handle(MessageCreateEvent event){
		if(event.getMessageContent().toLowerCase().trim().startsWith(COMMAND)) {
			try {
				if(event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "") != "") {
					int delay = Integer.parseInt(event.getMessageContent().replaceAll(" ", "").replace(COMMAND, ""));
					Thread.sleep(delay);
				}
				
				new MessageBuilder().addAttachment((Icon) new ImageIcon(getScreenShot()));
				
			} catch(NumberFormatException nfe) {
				event.getChannel().sendMessage("[!screenshot error] Please format the command correctly: !screenshot <delay in millisec>");
			} catch (InterruptedException ie) {
				event.getChannel().sendMessage("[!screenshot error] Something has interrupted the delay.");
			} catch (AWTException awte) {
				event.getChannel().sendMessage("[!screenshot error] Something went wrong when capturing the screen.");
			}
		}
	}
	
	private BufferedImage getScreenShot() throws AWTException {
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		return new Robot().createScreenCapture(screen);
	}
	
}