package org.jointheleague.modules;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Jpegify extends CustomMessageCreateListener {

	private String COMMAND = "?jpegify";
	private String command;
	private TextChannel channel;
	private CompletableFuture<BufferedImage> image;
	private BufferedImage downloadedImage;
	private File imageFile;
	private String filePath;
	private String imageFormat;
	private String[] fileNameSplit;
	private String[] commandSplit;
	private double qualityLvl;
	private List<MessageAttachment> attachmentList;
	private ImageWriter writer;
	private boolean running;

	public Jpegify(String channelName) {
		super(channelName);

		helpEmbed = new HelpEmbed(COMMAND,
				"Type ?jpegify + (a quality level from 1-100) along with an image and watch as your beautiful picture is horribly compressed beyond recognition!");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {

		running = true;

		while (running) {															// code is in a while loop so i can get

			if (!event.getMessageAuthor().isYourself()) {							// was command sent by bot?
				command = event.getMessageContent().toLowerCase();						// initializing command (the message sent)
				channel = event.getChannel();											// initializing channel

				if (isCommandCorrect(event)) {										// is the command correct?	
					attachmentList = event.getMessageAttachments();						// gets list of attachments in message

					if (!(attachmentList.size() < 1)) {								// checks for no attachments

						for (MessageAttachment attachment : attachmentList) {			// downloading image(s)

							if (isFilenameGood(attachment)) {						// is file name not png and has not more than 1 period?
								channel.sendMessage("file name is " + attachment.getFileName());
								imageFormat = fileNameSplit[1];							// sets imageFormat to second element in fileNameSplit
								channel.sendMessage("Image format is " + imageFormat);
								image = attachment.downloadAsImage();					// starting image download
								channel.sendMessage("Starting to download image...");
							}

							try {

								downloadedImage = image.get();						// when image is done downloading, following code can execute
								compressAndSendImage();									// calls compressAndSendImage

								// i can't figure out how to get java to tell windows it is done using it to delete it
								imageFile.delete();
								channel.sendMessage(
										"Make sure to delete the file at `" + imageFile.getAbsolutePath() + "`!");

							} catch (InterruptedException e) {
								e.printStackTrace();

							} catch (ExecutionException e) {
								e.printStackTrace();
							}

						}
					} else
						channel.sendMessage("There are no attachments in your message!");
				}
			}
			running = false;
		}
	}

	private boolean isCommandCorrect(MessageCreateEvent event) {

		if (!event.getMessageContent().startsWith(COMMAND)) {
			return false;
		}

		commandSplit = command.split(" ");								// splitting command to get quality level

		if (commandSplit.length == 1 || commandSplit.length > 2) {		// checks if user typed command incorrectly
			channel.sendMessage("Type the command like this: " + COMMAND + " (a quality level from 1-100)");
			return false;

		} else {

			qualityLvl = Integer.parseInt(commandSplit[1]) / 100;		// setting qualityLvl to second part of command
			return true;
		}
	}

	private boolean isFilenameGood(MessageAttachment attachment) {

		fileNameSplit = attachment.getFileName().split("\\.");	// splitting attachments filename to get file extension

		if (attachment.getFileName().split("\\.").length > 2) { // checks for periods in filename
			channel.sendMessage("You shouldn't put periods in files' names! Rename it and try again.");
			return false;
		}

		if (!fileNameSplit[1].equals("jpg")) {					// checks if image is a JPG
			channel.sendMessage(
					"Sorry, but the image has to be a JPG file. Right now the extension is " + fileNameSplit[1]);
			return false;
		}

		return true;
	}

	private void compressAndSendImage() {

		try {

			// writing image(s)
			filePath = "src/"
					+ generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890", 10)
					+ ".jpg";

			imageFile = new File(filePath);					// making new file for img

			ImageIO.write(downloadedImage, imageFormat, imageFile);	// writing to computer
			channel.sendMessage("Image written to " + filePath);

			// setting up to write compressed version of image
			// jpg encoding stuff
			JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
			jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			jpegParams.setCompressionQuality((float) qualityLvl);

			writer = ImageIO.getImageWritersByFormatName("jpg").next();
			writer.setOutput(new FileImageOutputStream(new File(filePath)));
			writer.write(null, new IIOImage(downloadedImage, null, null), jpegParams);

			// sending edited image
			channel.sendMessage(imageFile);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}

		return sb.toString();

	}

}
