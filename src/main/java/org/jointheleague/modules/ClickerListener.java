package org.jointheleague.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class ClickerListener extends CustomMessageCreateListener implements ActionListener{
	private static final String COMMAND = "!clicker";
	private boolean started=false;
	private double cash=0;
	private double nextUpgradeCost=1;
	private double clickMultiplier=1;
	private double[] prestigeCosts= {500,5000,150000,1000000000,100000000000D};
	private double prestigeCost=prestigeCosts[0];
	private double prestigeMultiplier=1;
	private int prestiges=0;
	private String[] suffixes= {"k","M","B","T","Qd","Qn","Sx","Sp","Oc","No","De"};
	private ArrayList<String> upgradeNames=new ArrayList<String>();
	private ArrayList<Integer> upgradeCosts=new ArrayList<Integer>();
	private Timer autoClickTimer=new Timer(5000, this);
	private TextChannel channel=null;
	public ClickerListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Type !clicker to start the game. Type \"help\" for commands.");
		upgradeNames.add("Auto Click");
		upgradeCosts.add(1000000);
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(channel==null) {
			channel=event.getChannel();
		}
		if(!event.getMessageAuthor().getDisplayName().equals("pasetto league discord bot")) {
			String msg=event.getMessageContent().toLowerCase();
			if(msg.contains(COMMAND)) {
				started=true;
				event.getChannel().sendMessage("The game started. Type \"help\" for commands.");
			}else if(started) {
				if(msg.contains("help")) {
					event.getChannel().sendMessage("Type Click to click.\nType Stats to see your stats.\nType Upgrade to upgrade.\nType Prestige to prestige.\nType !stop to stop the game.\nType Boosts to see boosts.\nType Buy <boost> to buy a boost.");
				}else if(msg.contains("buy")) {
					msg=msg.substring(msg.indexOf(" ")+1);
					int upgradeIndex=-1;
					for (int i = 0; i < upgradeNames.size(); i++) {
						if(upgradeNames.get(i).equalsIgnoreCase(msg)) {
							upgradeIndex=i;
							break;
						}
					}
					if(upgradeIndex==-1) {
						event.getChannel().sendMessage(msg+" is not an upgrade.");
					}else if(upgradeCosts.get(upgradeIndex)>cash){
						event.getChannel().sendMessage("You need "+convert(upgradeCosts.get(upgradeIndex))+" cash to buy "+upgradeNames.get(upgradeIndex)+".");
					}else {
						event.getChannel().sendMessage("Successfully bought "+upgradeNames.get(upgradeIndex)+".");
						cash-=upgradeCosts.get(upgradeIndex);
						if(upgradeNames.get(upgradeIndex).equals("Auto Click")) {
							autoClickTimer.start();
						}
						upgradeCosts.remove(upgradeIndex);
						upgradeNames.remove(upgradeIndex);
					}
				}else if(msg.contains("click")) {
					cash+=clickMultiplier*prestigeMultiplier;
					event.getChannel().sendMessage("You now have "+convert(cash)+" cash.");
				}else if(msg.contains("stats")) {
					event.getChannel().sendMessage("Cash: "+convert(cash)+"\nNext upgrade cost: "+convert(nextUpgradeCost)+"\nClick multiplier: "+convert(clickMultiplier)+"\nPrestiges: "+convert(prestiges)+"\nNext prestige cost: "+convert(prestigeCost)+"\nPrestige multiplier: "+convert(prestigeMultiplier));
				}else if(msg.contains("upgrade")) {
					if(cash>=nextUpgradeCost) {
						event.getChannel().sendMessage("Successfully upgraded.");
						cash-=nextUpgradeCost;
						nextUpgradeCost*=3;
						clickMultiplier*=2;
					}else {
						event.getChannel().sendMessage("You need "+convert(nextUpgradeCost)+" cash to upgrade. You only have "+convert(cash)+" cash.");
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
						event.getChannel().sendMessage("You need "+convert(prestigeCost)+" cash to prestige. You only have "+convert(cash)+" cash.");
					}
				}else if(msg.contains("!stop")) {
					started=false;
					cash=0;
					nextUpgradeCost=1;
					clickMultiplier=1;
					prestiges=0;
					prestigeCost=prestigeCosts[0];
					prestigeMultiplier=1;
					upgradeNames.clear();
					upgradeCosts.clear();
					upgradeNames.add("Auto Click");
					upgradeCosts.add(1000000);
					autoClickTimer.stop();
				}else if(msg.contains("boosts")) {
					String print="Boosts:\n";
					for (int i = 0; i < upgradeNames.size(); i++) {
						print+=upgradeNames.get(i)+": "+convert(upgradeCosts.get(i))+" cash\n";
					}
					event.getChannel().sendMessage(print);
				}
			}
		}
	}
	public String convert(double num) {
		int divides=-1;
		while(num>=1000) {
			num/=1000;
			divides++;
		}
		if(num<10) {
			num=Math.rint(num*100)/100;
		}else if(num<100) {
			num=Math.rint(num*10)/10;
		}else {
			num=Math.rint(num);
		}
		if(divides==-1) {
			return num+"";
		}else {
			return num+suffixes[divides];
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		cash+=10*clickMultiplier*prestigeMultiplier;
		channel.sendMessage("Clicked 10 times. You now have "+convert(cash)+" cash.");
	}
}
