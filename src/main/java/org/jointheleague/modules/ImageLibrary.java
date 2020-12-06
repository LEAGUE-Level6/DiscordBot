package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import net.aksingh.owmjapis.api.APIException;

public class ImageLibrary extends CustomMessageCreateListener {

	private static final String COMMAND = "!image";
	BufferedReader br = null;
	BufferedWriter bw = null;
	String filePath = System.getProperty("user.dir")
			+ "\\src\\main\\java\\org\\jointheleague\\modules\\hashmap-data.txt";
	File file = new File(filePath);
	String[] fileTypes = { ".png", ".jpg", ".jpeg", ".gif" };

	public ImageLibrary(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Allows you to store and remove images in a list using urls and retreive those images with a name. \n e.g. !image add <url> <name> \n !image view <name> \n !image remove <name> \n !image names");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains(COMMAND)) {
			HashMap<String, String> images = new HashMap<String, String>();
			try {
				br = new BufferedReader(new FileReader(file));

				String line;
				while ((line = br.readLine()) != null) {
					String[] phrase = line.split(" ");
					images.put(phrase[0], phrase[1]);
				}

				br.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String msg = event.getMessageContent();
			String url = "";
			String name = "";
			String[] phrase = msg.split(" ");
			if (phrase.length > 1) {
				if (phrase[1].equals("add")) {
					if (phrase.length < 4 || phrase.length > 4) {
						event.getChannel().sendMessage(
								"Incorrect usage of the command. Correct usages: !image add <url> <name>  !image view <name>  !image remove <name>  !image names");
					} else {
						url = phrase[2];
						name = phrase[3];

						if (images.size() > 0) {
							if (images.containsKey(name)) {
								event.getChannel()
										.sendMessage("This name is already in use. Please use a different name.");
							} else if (url.contains(".jpg") || url.contains(".jpeg") || url.contains("png")
									|| url.contains("gif") && url.contains("https://")) {
								images.put(name, url);
								event.getChannel().sendMessage("Added " + url + " with the name of " + name);
							} else {
								event.getChannel().sendMessage("This is not an image url.");
							}
						} else {
							if (url.contains(".jpg") || url.contains(".jpeg") || url.contains("png")
									|| url.contains("gif") && url.contains("https://")) {
								images.put(name, url);
								event.getChannel().sendMessage("Added " + url + " with the name of " + name);
							} else {
								event.getChannel().sendMessage("This is not an image url");
							}
						}
					}

				} else if (phrase[1].equals("view")) {
					if (phrase.length < 3 || phrase.length > 3) {
						event.getChannel().sendMessage(
								"Incorrect usage of the command. Correct usages: !image add <url> <name>  !image view <name>  !image remove <name>  !image names");
					} else {
						name = phrase[2];
						if (images.get(name) == null) {
							event.getChannel().sendMessage("There is no image with that name.");
						} else {
							event.getChannel().sendMessage(images.get(name) + "\n" + name);
						}
					}
				} else if (phrase[1].equals("remove")) {
					if (phrase.length < 3 || phrase.length > 3) {
						event.getChannel().sendMessage(
								"Incorrect usage of the command. Correct usages: !image add <url> <name>  !image view <name>  !image remove <name>  !image names");
					} else {
						name = phrase[2];

						if (images.get(name) == null) {
							event.getChannel().sendMessage("There is no image with that name.");
						} else {
							images.remove(name);
							event.getChannel().sendMessage(name + " has been removed.");
						}
					}
				} else if (phrase[1].equals("names")) {
					if (phrase.length > 2) {
						event.getChannel().sendMessage(
								"Incorrect usage of the command. Correct usages: !image add <url> <name>  !image view <name>  !image remove <name>  !image names");
					} else if (!(images.size() == 0)) {
						String names = "";
						for (Map.Entry<String, String> entry : images.entrySet()) {
							names += entry.getKey() + "\n";
						}
						event.getChannel().sendMessage(names);
					} else {
						event.getChannel().sendMessage("There is currently no stored images.");
					}
				}
			} else {
				event.getChannel().sendMessage(
						"Incorrect usage of the command. Correct usages: !image add <url> <name>  !image view <name>  !image remove <name>  !image names");
			}

			try {
				bw = new BufferedWriter(new FileWriter(file));

				for (Map.Entry<String, String> entry : images.entrySet()) {
					bw.write(entry.getKey() + " " + entry.getValue());
					bw.newLine();
				}
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
