package org.jointheleague.modules;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Jpegify extends CustomMessageCreateListener {

	public Jpegify(String channelName) {
		super(channelName);
	}

	String command;
	TextChannel channel;
	CompletableFuture<BufferedImage> image;
	BufferedImage downloadedImage;
	File imageFile;
	String filePath;
	String imageFormat;

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		// is command?
		if (!event.getMessageAuthor().isYourself()) {

			command = event.getMessageContent().toLowerCase();
			channel = event.getChannel();

			if (command.equals("?test")) {

				channel.sendMessage("command recieved");
				List<MessageAttachment> attachmentList = event.getMessageAttachments();
				channel.sendMessage("there are " + attachmentList.size() + " attachments in your message");

				// getting images
				for (MessageAttachment attachment : attachmentList) {

					String[] array = attachment.getFileName().split(".");
					channel.sendMessage("file name is " + attachment.getFileName());
					imageFormat = array[1];
					channel.sendMessage("Image format is " + imageFormat);
					image = attachment.downloadAsImage();
					channel.sendMessage("Image downloaded");

					try {
						downloadedImage = image.get();
						try {
							// writing image(s)
							filePath = "D:\\\\Desktop\\"
									+ generateRandomChars(
											"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890", 10)
									+ ".jpg";
							imageFile = new File(filePath);
							ImageIO.write(downloadedImage, imageFormat, imageFile);
							channel.sendMessage("Image written to " + filePath);

						} catch (IOException e) {

							e.printStackTrace();
						}

					} catch (InterruptedException e) {
						e.printStackTrace();

					} catch (ExecutionException e) {
						e.printStackTrace();
					}

				}

			}
		}

	}

	public static String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}

		return sb.toString();

	}
}
