package org.jointheleague.modules;

import java.awt.Event;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v1.DbxEntry.File;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadBuilder;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

public class DiscordLibrary extends CustomMessageCreateListener {
	private static final String ACCESS_TOKEN = "eJ-UEvbyEmgAAAAAAAAAAY8Rh0IaGogCjXj_ohrm3mDiL7pVzxah9_YH7dN_swdf";
	DbxRequestConfig config;
	DbxClientV2 client;
	FullAccount account;
	private static final String COMMAND = "!DiscordLibrary";
	private static final String COMMAND2 = "!DL";

	public DiscordLibrary(String channelName) {
		// TODO Auto-generated constructor stub
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Uploads text, images, and other files into a cloud (DropBox). You can pull down any file from the cloud and use it. Commands: Do '!DL' before anything. 1) listall 2) upload 3) write 4) addimage 5) addvideo 6) addaudio 7) upload 8) get "

						+ " **NOTE: Before creating any file (write, addimage, addvideo, addaudio), you must create a title/name for the file. e.g. [!DL][nametitle][write][exampletext]"

						+ " **Once you created a file, the file won't go directly into the cloud. You must do [!DL][upload][example.txt]"
						+ " **NOTE: Audio and Video Files must be under 8.00MB.");
		config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
		client = new DbxClientV2(config, ACCESS_TOKEN);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String contents = event.getMessageContent();
		String[] cmds = contents.split(" ");
		if (contents.contains(COMMAND) || contents.contains(COMMAND2)) {

			// account name
			try {
				account = client.users().getCurrentAccount();
			} catch (DbxApiException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DbxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// System.out.println(account.getName().getDisplayName());

			if (cmds.length > 1 && cmds[1].equalsIgnoreCase("upload")) { // uploads a file to dropbox and checks if that
																			// file name already exists - make sure to
																			// add in file type after name.
				if (cmds.length > 2) {
					int messageIndex = contents.indexOf("upload") + 7;
					String file = contents.substring(messageIndex);
					java.io.File f = new java.io.File(event.getMessageAuthor().getIdAsString()); // creates the file
					if (f.exists()) { // checks if the file exists or not
						event.getChannel().sendMessage("This file already exists within your local directory");
						f.delete();
					}
					try (InputStream in = new FileInputStream(file)) {
						FileMetadata metadata = client.files()
								.uploadBuilder("/" + event.getMessageAuthor().getIdAsString() + "/" + file)
								.uploadAndFinish(in);
					} catch (Exception e) {
						e.printStackTrace();
						event.getChannel().sendMessage(
								"That file already exists within the system or the file " + file + " cannot be found");
					}
				}
			} else if (cmds[1].equalsIgnoreCase("commands")) {
				event.getChannel().sendMessage(
						"1) Getting items from cloud (make sure to specify the type of file): [!DL][get][file.txt]");
				event.getChannel().sendMessage(
						"2) Uploading created files (make sure to specify the type of file): [!DL][upload][file.txt]");
				event.getChannel().sendMessage("3) Listing all files in cloud: [!DL][listall]");
				event.getChannel().sendMessage("4) Writing a text file: [!DL][name of file][write][words]");
				event.getChannel().sendMessage(
						"5) Making an image file (png files are made): [!DL][name of file][addimage][url or attachment]");
				event.getChannel().sendMessage(
						"6) Adding a video is only takes attachments that are 8.00MB or under, not links. The format for making a video file is [!DL][title][addvideo][attachment]");
				event.getChannel().sendMessage(
						"7) Adding an audio file only takes attachments that are 8.00MB or under, not links. The format for making an audio file is [!DL][title][addaudio][attachment]");
			}

			else if (cmds.length > 1 && cmds[1].equalsIgnoreCase("listall")) { // Get files and folder metadata from
																				// Dropbox root directory
				event.getChannel().sendMessage("Here is all your files:");
				ListFolderResult result;
				try {
					result = client.files().listFolder("/" + event.getMessageAuthor().getIdAsString() + "/");
					// event.getChannel().sendMessage((EmbedBuilder) result.getEntries());
					while (true) {
						for (Metadata metadata : result.getEntries()) {

							event.getChannel().sendMessage((metadata.getPathLower()
									.replace("/" + event.getMessageAuthor().getIdAsString() + "/", "")));
						}

						if (!result.getHasMore()) {
							break;
						}

						result = client.files().listFolderContinue(result.getCursor());
					}
				} catch (DbxApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DbxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			else if (cmds.length > 1 && cmds[2].equalsIgnoreCase("write")) { // writes to the file you created; file is
																				// stored in your computer
				if (cmds.length > 3) {
					int messageIndex = contents.indexOf("write") + 6;
					String response = contents.substring(messageIndex);
					java.io.File f = new java.io.File(cmds[1] + ".txt"); // creates the file
					if (f.exists()) { // checks if the file exists or not
						event.getChannel().sendMessage("This file already exists within your local directory");
						f.delete();
					}
					System.out.println(f);
					try {
						f.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// writes to the file
					try {
						FileWriter fw = new FileWriter(f, true);
						fw.write("\n" + response);
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			else if (cmds.length > 1 && cmds[2].equalsIgnoreCase("addimage")) { // makes an image file; file is stored
																				// in your computer
				if (cmds.length > 3) {
					int messageIndex = contents.indexOf("addimage") + 9;
					String response = contents.substring(messageIndex);
					java.io.File f = new java.io.File(cmds[1] + ".png"); // creates the jpg file [title.jpb]
					if (f.exists()) { // checks if the file exists or not
						event.getChannel().sendMessage("This file already exists within your local directory");
						f.delete();
					}
					System.out.println(f);
					try {
						f.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// writes the image to file
					try {
						URL url = new URL(response);
						BufferedImage img = ImageIO.read(url);
						ImageIO.write(img, "png", f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else { // uploads image attachments
					BufferedImage img;
					List<MessageAttachment> att = event.getMessageAttachments();
					for (MessageAttachment m : att) {
						if (m.isImage()) { // checks if it's an image
							CompletableFuture<BufferedImage> im = m.downloadAsImage();
							try {
								img = im.get();
								// System.out.println(m.getFileName());
								java.io.File outputfile = new java.io.File(cmds[1] + ".png");
								if (outputfile.exists()) { // checks if the file exists or not
									event.getChannel()
											.sendMessage("This file already exists within your local directory");
									outputfile.delete();
								}
								System.out.println(outputfile);
								ImageIO.write(img, "png", outputfile);
							} catch (InterruptedException | ExecutionException | IOException e) {
								event.getChannel().sendMessage(
										"In order to add image attachments, you have to first do [!DL][title][addimage][attachment]");
								e.printStackTrace();
							}
						}
					}
				}
			} else if (cmds.length > 1 && cmds[2].equalsIgnoreCase("addvideo")) {// making a video file; no links - only
																					// attachments that are 8.00MB or
																					// under

				/*
				 * if(cmds.length>3) { int messageIndex = contents.indexOf("addvideo") + 9;
				 * String response = contents.substring(messageIndex); try { InputStream is =
				 * new URL(response).openStream(); //error on this line byte[] b = new
				 * byte[(int) is.available()]; is.read(b); java.io.File nf = new
				 * java.io.File(cmds[1] + ".mp4"); FileOutputStream fw = new
				 * FileOutputStream(nf); fw.write(b); fw.flush(); fw.close(); }
				 * catch(IOException e) { e.printStackTrace(); }
				 * 
				 * } else {
				 */
				if (cmds.length > 2) {
					List<MessageAttachment> att = event.getMessageAttachments();
					for (MessageAttachment m : att) {
						if (m.getFileName().contains(".mp4")) {
							try {
								CompletableFuture<byte[]> cf = m.downloadAsByteArray();
								byte[] b = cf.get();
								java.io.File nf = new java.io.File(cmds[1] + ".mp4");
								System.out.println(nf);
								FileOutputStream fw = new FileOutputStream(nf);
								fw.write(b);
								fw.flush();
								fw.close();
							} catch (IOException | InterruptedException | ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				// }
			} else if (cmds.length > 1 && cmds[2].equalsIgnoreCase("addaudio")) { // makes an audio file from
				 // attachments only 8.00MB & under
				if (cmds.length > 2) {
					List<MessageAttachment> att = event.getMessageAttachments();
					//System.out.println("Getting attachments");
					for (MessageAttachment m : att) {
						if (m.getFileName().contains(".mp3") || m.getFileName().contains(".wav")) {
							try {
								CompletableFuture<byte[]> cf = m.downloadAsByteArray();
								byte[] b = cf.get();
								java.io.File nf = new java.io.File(cmds[1] + ".mp3");
								System.out.println(nf);
								FileOutputStream fw = new FileOutputStream(nf);
								fw.write(b);
								fw.flush();
								fw.close();
							} catch (IOException | InterruptedException | ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			} else if (cmds.length > 1 && cmds[1].equalsIgnoreCase("get")) { // gets any file you want from the cloud
																				// and shows it to you
				if (cmds.length > 2) {
					int messageIndex = contents.indexOf("get") + 4;
					String file = contents.substring(messageIndex);
					try (OutputStream os = new FileOutputStream(file)) {
						InputStream inputstream = client.files()
								.download("/" + event.getMessageAuthor().getIdAsString() + "/" + file).getInputStream();
						event.getChannel().sendMessage(inputstream, file);
						System.out.println("pulled: " + file);
					} catch (IOException | DbxException e) {
						// TODO Auto-generated catch block
						event.getChannel().sendMessage("There is no file called '" + file + "' in your folder");
						e.printStackTrace();
					}
				}
			}
		}
	}
}
