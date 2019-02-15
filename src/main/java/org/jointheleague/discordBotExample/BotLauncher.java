package org.jointheleague.discordBotExample;

/**
 * BotLauncher can launch one or more bots if needed.
 * 
 * @author keithgroves
 *
 */
public class BotLauncher {
	public static void main(String[] args) {
		new BotLauncher().launch();
	}

	public void launch() {
		System.out.println("Launching bots!");
		Bot myBot = new Bot("NTQ1Nzg4MDMzMTk1MTgwMDMy.D0ew-A.qwhq9WRTZ-MoYigFMONR4VS7aUw");
	}
}
