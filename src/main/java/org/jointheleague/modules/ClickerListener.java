package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class ClickerListener extends CustomMessageCreateListener{
	private static final String COMMAND="!clicker";
	private double cash=0;
	private double cashMultiplier=1;
	private double upgradeCost=1;
	private boolean started=false;
	private String[] suffixes= {"k","M","B","T","Qd","Qn","Sx","Sp","Oc","No","De"};
	public ClickerListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Type click to click and type commands to see commands.");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(!event.getMessageAuthor().getDisplayName().equals("pasetto league discord bot")) {
			if(event.getMessageContent().contains(COMMAND)) {
				started=true;
				event.getChannel().sendMessage("The game started. Type \"commands\" for commands.");
			}else if(started) {
				String cmd=event.getMessageContent().toLowerCase();
				if(cmd.contains("commands")) {
					event.getChannel().sendMessage("Type click to click.\nType stats to see your stats.\nType upgrade to upgrade your clicks.");
				}else if(cmd.contains("click")) {
					cash+=cashMultiplier;
					event.getChannel().sendMessage("You now have "+convert(cash)+" cash.");
				}else if(cmd.contains("stats")) {
					event.getChannel().sendMessage("You have "+convert(cash)+" cash.\nYou need "+convert(upgradeCost)+" cash for the next upgrade.\nYou get "+convert(cashMultiplier)+" cash per click.");
				}else if(cmd.contains("upgrade")) {
					if(cash<upgradeCost) {
						event.getChannel().sendMessage("You need "+convert(upgradeCost)+" cash to upgrade.\nYou only have "+convert(cash)+" cash.");
					}else {
						cash-=upgradeCost;
						upgradeCost*=5;
						cashMultiplier*=4;
						event.getChannel().sendMessage("You successsfully upgraded!\nYou now get "+convert(cashMultiplier)+" cash per click.");
					}
				}
			}
		}
	}
	public String convert(double n) {
		int suffixIndex=-1;
		while(n>=1000) {
			n/=1000;
			suffixIndex++;
		}
		if(n<10) {
			n=Math.rint(n*100)/100;
		}else if(n<100) {
			n=Math.rint(n*10)/10;
		}else {
			n=Math.rint(n);
		}
		if(suffixIndex==-1) {
			return n+"";
		}
		return n+suffixes[suffixIndex];
	}
}
