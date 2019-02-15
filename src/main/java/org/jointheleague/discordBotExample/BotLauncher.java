package org.jointheleague.discordBotExample;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
/**
 * BotLauncher can launch one or more bots if needed. 
 * @author keithgroves
 *
 */
public class BotLauncher {

	public void launch() {
		System.out.println("Launching bots!");
		Bot myBot = new Bot("NTQ1Nzg4MDMzMTk1MTgwMDMy.D0ew-A.qwhq9WRTZ-MoYigFMONR4VS7aUw");
	}
}
