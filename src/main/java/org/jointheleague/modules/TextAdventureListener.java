package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class TextAdventureListener extends CustomMessageCreateListener{
	private static final String COMMAND = "!start";
	private boolean started=false;
	private String[][] world;
	private Random random=new Random();
	private int playerX=1;
	private int playerY=1;
	public TextAdventureListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Use !start to start the game. Use \"help\" in the game for instructions.");
		generateWorld();
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().contains(COMMAND)) {
			started=true;
			event.getChannel().sendMessage("The game started. Use \"help\" for commands.");
		}else if(started) {
			String msg=event.getMessageContent().toLowerCase();
			if(msg.contains("help")) {
				event.getChannel().sendMessage("Type North to move north.\nType East to move east.\nType South to move south.\nType West to move west.\nType Look to look around.\nType Attack to attack the nearest enemy.\nType Inventory to see all the items in your inventory.\nType Equip <item> to equip an item.\nType Unequip to unequip your equipped item.\nType Stats to see all your stats.");
			}else if(msg.contains("look")) {
				event.getChannel().sendMessage("There is "+find(playerX,playerY-1)+" to the north.\nThere is "+find(playerX+1,playerY)+" to the east.\nThere is "+find(playerX,playerY+1)+" to the south.\nThere is "+find(playerX-1,playerY)+" to the west.");
			}
		}
	}
	public void generateWorld() {
		world=new String[10][10];
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				if(i==0 || j==0 || i==world.length-1 || j==world[i].length-1) {
					world[i][j]="wall";
				}else if(i==1 && j==1){
					world[i][j]="none";
				}else if(random.nextInt(6)==0) {
					world[i][j]="wall";
				}else if(random.nextInt(8)==0) {
					world[i][j]="enemy";
				}else {
					world[i][j]="none";
				}
			}
		}
	}
	public String find(int x, int y) {
		String check=world[x][y];
		if(check.equals("none")) {
			return "nothing";
		}else if(check.equals("wall")) {
			return "a wall";
		}else if(check.equals("enemy")) {
			return "an enemy";
		}else {
			return null;
		}
	}
}
