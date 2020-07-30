package org.jointheleague.modules;

import java.util.ArrayList;
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
	private int level=1;
	private int XP=0;
	private boolean fightingEnemy=false;
	private int enemyHealth=0;
	private int enemyDamage=0;
	private int enemyXP=0;
	private int health=100;
	private ArrayList<String> inventoryNames=new ArrayList<String>();
	private ArrayList<Integer> inventoryDamage=new ArrayList<Integer>();
	private int equippedItemID=-1;
	private boolean ran=false;
	private int floor=1;
	private int enemiesLeft=0;
	private boolean hasMap=false;
	public TextAdventureListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Type !start to start the game. Use \"help\" in the game for instructions.");
		generateWorld(10);
		inventoryNames.add("Bronze Sword");
		inventoryDamage.add(3);
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(!event.getMessageAuthor().getDisplayName().equals("pasetto league discord bot")) {
			if(event.getMessageContent().contains(COMMAND)) {
				started=true;
				event.getChannel().sendMessage("The game started. Use \"help\" for commands.");
			}else if(started) {
				String msg=event.getMessageContent().toLowerCase();
				if(msg.contains("help")) {
					event.getChannel().sendMessage("Type North to move north.\nType East to move east.\nType South to move south.\nType West to move west.\nType Look to look around.\nType Attack to attack the nearest enemy.\nType Inventory to see all the items in your inventory.\nType Equip <item> to equip an item.\nType Unequip to unequip your equipped item.\nType Stats to see all your stats.\nType Run to attempt to run from the enemy.\nType Open to open a chest.\nType Reset to reset the current floor.");
				}else if(msg.contains("look")) {
					look(event);
				}else if(msg.contains("north")) {
					move(playerX,playerY-1,event);
				}else if(msg.contains("east")) {
					move(playerX+1,playerY,event);
				}else if(msg.contains("south")) {
					move(playerX,playerY+1,event);
				}else if(msg.contains("west")) {
					move(playerX-1,playerY,event);
				}else if(msg.contains("stats")) {
					event.getChannel().sendMessage("Your stats:\nHealth: "+health+"/100\nLevel "+level+"\nXP: "+XP+"/"+level*25+"\nFloor "+floor);
				}else if(msg.contains("inventory")) {
					String print="Your inventory:\n";
					for (int i = 0; i < inventoryNames.size(); i++) {
						print+=inventoryNames.get(i)+": "+inventoryDamage.get(i)+" Damage\n";
					}
					event.getChannel().sendMessage(print);
				}else if(msg.contains("equip")) {
					String item=msg.substring(msg.indexOf(" ")+1);
					boolean found=false;
					for (int i = 0; i < inventoryNames.size(); i++) {
						if(item.equalsIgnoreCase(inventoryNames.get(i))) {
							event.getChannel().sendMessage("Equipped "+inventoryNames.get(i));
							equippedItemID=i;
							found=true;
						}
					}
					if(!found) {
						event.getChannel().sendMessage("You do not have "+item);
					}
				}else if(msg.contains("unequip")) {
					if(equippedItemID==-1) {
						event.getChannel().sendMessage("You can't unequip a weapon if you don't have anything equipped.");
					}else {
						event.getChannel().sendMessage("Unequipped your weapon.");
						equippedItemID=-1;
					}
				}else if(msg.contains("attack")) {
					if(fightingEnemy) {
						if(equippedItemID==-1) {
							event.getChannel().sendMessage("You can't attack if you don't have a weapon equipped.");
						}else {
							int weaponDamage=random.nextInt(inventoryDamage.get(equippedItemID))+inventoryDamage.get(equippedItemID)/2;
							enemyHealth-=weaponDamage;
							if(enemyHealth<=0) {
								enemyHealth=0;
							}
							event.getChannel().sendMessage("You have done "+weaponDamage+" damage to the enemy.\nThe enemy now has "+enemyHealth+" health.");
							if(enemyHealth==0) {
								world[playerX][playerY]="none";
								event.getChannel().sendMessage("You defeated the enemy and got "+enemyXP+" XP!");
								fightingEnemy=false;
								giveXP(enemyXP,event);
								enemiesLeft--;
								boolean progressFloor=false;
								if(enemiesLeft==0) {
									event.getChannel().sendMessage("There are no more enemies left to defeat! You have progressed to the next floor.");
									progressFloor=true;
									playerX=1;
									playerY=1;
									generateWorld(10+floor*2);
								}else if(enemiesLeft==1) {
									event.getChannel().sendMessage("There is only one enemy left to get to the next floor!");
								}else {
									event.getChannel().sendMessage("There are "+enemiesLeft+" enemies left to defeat until you can get to the next floor.");
								}
								giveWeapon("Silver Blade "+floor,6*(int) Math.pow(5, floor-1),event,0.7);
								giveWeapon("Golden Broadsword "+floor,9*(int) Math.pow(5, floor-1),event,0.25);
								giveWeapon("The Destroyer "+floor,15*(int) Math.pow(5, floor-1),event,0.06);
								giveWeapon("????????????????? "+floor,999*(int) Math.pow(5, floor-1),event,0.005);
								if(progressFloor) {
									floor++;
									hasMap=false;
								}
							}
						}
					}else {
						event.getChannel().sendMessage("You currently aren't fighting any enemy.");
					}
				}else if(msg.contains("run")) {
					if(fightingEnemy) {
						if(ran) {
							event.getChannel().sendMessage("You can't try to run twice.");
						}else {
							if(random.nextInt(2)==0) {
								event.getChannel().sendMessage("You successfully ran from the enemy!");
								fightingEnemy=false;
							}else {
								event.getChannel().sendMessage("You failed to run from the enemy.");
								ran=true;
							}
						}
					}else {
						event.getChannel().sendMessage("You currently aren't fighting any enemy.");
					}
				}else if(msg.contains("open")) {
					if(world[playerX][playerY].equals("chest")) {
						world[playerX][playerY]="none";
						int randomNum=random.nextInt(6);
						if(randomNum==0) {
							event.getChannel().sendMessage("You opened the chest and got "+15*(int) Math.pow(6, floor-1)+" XP!");
							giveXP(15*(int) Math.pow(6, floor-1),event);
						}else if(randomNum==1) {
							giveWeapon("Chest Blade "+floor,8*(int) Math.pow(5, floor-1),event,1);
						}else if(randomNum==2){
							event.getChannel().sendMessage("The chest is empty.");
						}else if(randomNum==3){
							hasMap=true;
							event.getChannel().sendMessage("You got a map! Type \"map\" to look at it.");
						}else {
							changePlayerHealth(-25,event);
							event.getChannel().sendMessage("There was a trap in the chest! The trap has done 25 damage to you.\nYour health is "+health);
						}
					}else {
						event.getChannel().sendMessage("You aren't on a chest.");
					}
				}else if(msg.contains("reset")) {
					if(fightingEnemy) {
						event.getChannel().sendMessage("You can't reset while fighting an enemy.");
					}else {
						generateWorld(8+floor*2);
						playerX=1;
						playerY=1;
						hasMap=false;
						event.getChannel().sendMessage("The floor has been reset.");
					}
				}else if(msg.contains("map")) {
					if(hasMap) {
						String print="*[HTML]:**Map:\n";
						for (int i = 0; i < world.length; i++) {
							for (int j = 0; j < world[i].length; j++) {
								if(playerX==i && playerY==j) {
									print+="&";
								}else if(world[i][j].equals("none")) {
									print+="  ";
								}else if(world[i][j].equals("wall")) {
									print+="#";
								}else if(world[i][j].equals("enemy")) {
									print+="e";
								}else if(world[i][j].equals("chest")) {
									print+="c";
								}
							}
							print+="\n";
						}
						event.getChannel().sendMessage(print+"**{style=\"font-family: monospace; background-color: black; color: white\"}");
					}else {
						event.getChannel().sendMessage("You don't have a map.");
					}
				}
				if(fightingEnemy) {
					changePlayerHealth(-enemyDamage,event);
					event.getChannel().sendMessage("The enemy has done "+enemyDamage+" damage to you.\nYour health is "+health);
				}
				changePlayerHealth(3,event);
			}
		}
	}
	public void generateWorld(int size) {
		world=new String[size][size];
		enemiesLeft=0;
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				if(i==0 || j==0 || i==world.length-1 || j==world[i].length-1) {
					world[i][j]="wall";
				}else if(i==1 && j==1){
					world[i][j]="none";
				}else if(random.nextInt(6)==0) {
					world[i][j]="wall";
				}else if(random.nextInt(5)==0) {
					world[i][j]="enemy";
					enemiesLeft++;
				}else if(random.nextInt(8)==0){
					world[i][j]="chest";
				}else {
					world[i][j]="none";
				}
			}
		}
		enemiesLeft= (int) (enemiesLeft*(2.0/3.0));
	}
	public String find(int x, int y) {
		String check=world[x][y];
		if(check.equals("none")) {
			return "nothing";
		}else if(check.equals("wall")) {
			return "a wall";
		}else if(check.equals("enemy")) {
			return "an enemy";
		}else if(check.equals("chest")) {
			return "a chest";
		}else {
			return null;
		}
	}
	public void move(int x, int y, MessageCreateEvent event) {
		if(fightingEnemy) {
			event.getChannel().sendMessage("You cannot move because you are fighting an enemy.");
		}else {
			String object=world[x][y];
			if(object.equals("none")) {
				event.getChannel().sendMessage("Successfully moved.");
				playerX=x;
				playerY=y;
			}else if(object.equals("wall")) {
				event.getChannel().sendMessage("Cannot move because there is a wall in the way.");
			}else if(object.equals("enemy")){
				event.getChannel().sendMessage("Successfully moved. You are now fighting an enemy.");
				playerX=x;
				playerY=y;
				fightingEnemy=true;
				enemyHealth=(random.nextInt(10)+10)*(int) Math.pow(6, floor-1);
				enemyDamage=random.nextInt(5)+2+floor*4;
				enemyXP=(random.nextInt(10)+15)*(int) Math.pow(6, floor-1);
				ran=false;
			}else {
				event.getChannel().sendMessage("Successfully moved. You are now on a chest.");
				
				playerX=x;
				playerY=y;
			}
			look(event);
		}
	}
	public void giveXP(int XPAmount, MessageCreateEvent event) {
		XP+=XPAmount;
		int gainedLevels=0;
		while(XP>=level*25) {
			XP-=level*25;
			level++;
			gainedLevels++;
		}
		if(gainedLevels==1) {
			event.getChannel().sendMessage("You leveled up!\nYou are now level "+level+"!");
		}else if(gainedLevels>1) {
			event.getChannel().sendMessage("You leveled up "+gainedLevels+" times!\nYou are now level "+level+"!");
		}
	}
	public void giveWeapon(String name, int damage, MessageCreateEvent event, double chance) {
		if(!inventoryNames.contains(name) && random.nextDouble()<chance) {
			inventoryNames.add(name);
			inventoryDamage.add(damage);
			event.getChannel().sendMessage("You got "+name+" at a "+chance*100+"% chance.");
		}
	}
	public void changePlayerHealth(int change, MessageCreateEvent event) {
		health+=change;
		if(health>100) {
			health=100;
		}
		if(health<=0) {
			event.getChannel().sendMessage("You lost! Type !start to restart the game.");
			started=false;
			playerX=1;
			playerY=1;
			floor=1;
			level=1;
			XP=0;
			health=100;
			inventoryNames.clear();
			inventoryDamage.clear();
			inventoryNames.add("Bronze Sword");
			inventoryDamage.add(3);
			generateWorld(10);
			fightingEnemy=false;
			hasMap=false;
		}
	}
	public void look(MessageCreateEvent event) {
		event.getChannel().sendMessage("There is "+find(playerX,playerY-1)+" to the north.\nThere is "+find(playerX+1,playerY)+" to the east.\nThere is "+find(playerX,playerY+1)+" to the south.\nThere is "+find(playerX-1,playerY)+" to the west.");
	}
}
