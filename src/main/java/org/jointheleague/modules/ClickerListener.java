package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class ClickerListener extends CustomMessageCreateListener{
	private static final String COMMAND = "!clicker";
	private boolean started=false;
	private double cash=0;
	private double nextUpgradeCost=1;
	private double clickMultiplier=1;
	private double[] prestigeCosts= {1000};
	private double prestigeCost=prestigeCosts[0];
	private double prestigeMultiplier=1;
	private int prestiges=0;
	public ClickerListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Type !clicker to start the game. Type \"help\" for commands.");
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(!event.getMessageAuthor().getDisplayName().equals("pasetto league discord bot")) {
			String msg=event.getMessageContent().toLowerCase();
			if(msg.contains(COMMAND)) {
				started=true;
				event.getChannel().sendMessage("The game started. Type \"help\" for commands.");
			}else if(started) {
				if(msg.contains("help")) {
					event.getChannel().sendMessage("Type Click to click.\nType Stats to see your stats.\nType Upgrade to upgrade.\nType Prestige to prestige.");
				}else if(msg.contains("click")) {
					cash+=clickMultiplier*prestigeMultiplier;
					event.getChannel().sendMessage("You now have "+cash+" cash.");
				}else if(msg.contains("stats")) {
					event.getChannel().sendMessage("Cash: "+cash+"\nNext upgrade cost: "+nextUpgradeCost+"\nClick multiplier: "+clickMultiplier+"\nPrestiges: "+prestiges+"\nNext prestige cost: "+prestigeCost+"\nPrestige multiplier: "+prestigeMultiplier);
				}else if(msg.contains("upgrade")) {
					if(cash>=nextUpgradeCost) {
						event.getChannel().sendMessage("Successfully upgraded.");
						cash-=nextUpgradeCost;
						nextUpgradeCost*=3;
						clickMultiplier*=2;
					}else {
						event.getChannel().sendMessage("You need "+nextUpgradeCost+" cash to upgrade. You only have "+cash+" cash.");
					}
				}else if(msg.contains("prestige")) {
					if(cash>=prestigeCost) {
						event.getChannel().sendMessage("Successfully prestiged.");
						cash=0;
						nextUpgradeCost=1;
						clickMultiplier=1;
						prestiges++;
						if(prestiges>=prestigeCosts.length) {
							prestiges=0;
							prestigeCost=prestigeCosts[0];
							prestigeMultiplier=1;
							started=false;
							event.getChannel().sendMessage("You beat the game! Type !clicker to play again.");
						}else {
							prestigeCost=prestigeCosts[prestiges];
							prestigeMultiplier*=prestiges+1;
						}
					}else {
						event.getChannel().sendMessage("You need "+nextUpgradeCost+" cash to upgrade. You only have "+cash+" cash.");
					}
				}
			}
		}
	}

}
