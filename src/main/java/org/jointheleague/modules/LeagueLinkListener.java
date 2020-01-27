package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

public class LeagueLinkListener extends CustomMessageCreateListener {

	private static final String LEVEL = "https://central.jointheleague.org/levels/Level#/";
	private static final String MODULE = "modules/Mod#Recipes/index.html";

	private static final int[] modMaximums = { 5, 4, 2, 3, 4, 4 };

	public LeagueLinkListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String[] message = event.getMessageContent().split(" ");
		if (message.length > 0 && message[0].equalsIgnoreCase("level")) {
			if (message.length > 1 && message[1].matches("[0-9]+$")) {
				String level = message[1];
				int levelNum = Integer.parseInt(level);
				if(levelNum > 9) {
					event.getChannel().sendMessage("There are no levels above 9!");
				}
				String lvl = LEVEL.replace("#", level);
				if (message.length > 2 && message[2].equalsIgnoreCase("module") || message[2].equalsIgnoreCase("mod")) {
					int modMax = modMaximums[Integer.parseInt(level + "")];
					if (message.length > 3 && message[3].matches("[0-" + modMax + "]+$")) {
						String module = message[3];
						String mod = MODULE.replace("#", module);

						if (!level.equals("0")) {
							mod = mod.replace("modules/", "");
						}
						event.getChannel().sendMessage(lvl + mod);
					} else if (message.length > 3 && message[3].matches("[" + (modMax+1) + "-9]+$")) {
						event.getChannel().sendMessage("Level " + level + " only goes up to module " + modMax + "!");
					} else {
						event.getChannel().sendMessage("That's not a valid module number.");
					}
				}
			} else {
				event.getChannel().sendMessage("That's not a valid level number.");
			}
		}
	}

}
