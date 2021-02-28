package org.jointheleague.modules;

import java.awt.Color;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discord_bot_example.BotInfo;
import org.jointheleague.discord_bot_example.Utilities;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.javacord.api.entity.user.User;

public class MafiaPlayer extends CustomMessageCreateListener {

    //commands
    private static final String COMMAND = ".playMafia";
    private static final String help = ".mafia-intructions";
    private static final String mafiaKill = ".mafia-kill";
    private static final String detectiveInspect = ".mafia-inspect";
    private static final String doctorSave = ".mafia-save";
    private static final String vote = ".mafia-vote";
    private static final String end = ".mafia-end";

    //game states
    private boolean gameStarted = false;
    private boolean mafiatime = false;
    private boolean detectivetime = false;
    private boolean doctortime = false;
    private boolean deathannouncement = false;
    private boolean voteTime = false;
    private boolean sendKillMessage = true;
    private boolean sendSaveMessage = true;
    private boolean sendInspectMessage = true;
    private boolean sendVoteMessage = true;
    

    //users
    ArrayList<User> mafiaMembers = new ArrayList<>();
	ArrayList<User> mafiabackup = new ArrayList<User>();
    ArrayList<User> villageMembers = new ArrayList<>(); 
	ArrayList<User> villagersbackup = new ArrayList<User>();
    ArrayList<User> allPlayers = new ArrayList<>(); 
	ArrayList<User> temp = new ArrayList<User>();
    User doctor;
    User detective;
    HashMap<User, Integer> votes;

    int numPlayers = -1;
    boolean assigningPlayers = false;
    int currentPlayerNumber = 2;
    long botId;
    long channelId;
    User killed;
    boolean saved;

    List<Long> privateChannelIds = new ArrayList<>();
    private long mafiaPrivateChannelId;
    private int numVotesCast;


    DiscordApi api;

    int numResponseFromMafiaMembers;

    int day = 1;

    static final String[] beginningStorylines = {"You are all passengers on a plane when suddenly, the engine gets stuck and the plane must take a crash landing. "
    		+ "Luckily, the pilot(s) crash the plane onto a small, deserted island in the middle of the ocean, but the pilot is gone. "
    		+ "Now, you must all work together to survive until help arrives. However, there are imposters among the group, the mafia. "
            + "Collaborate together to find these imposters and survive until the end.\n"};
    
    static final String[] deathStorylines = {" was chased by a flock of angry pelicans.", " went for a swim and never came back.", " got crushed by a rock.", " hit their head too hard on a tree."};
    private boolean startDayTime = true;

    public MafiaPlayer(String channelName) {
        super(channelName);
        BotInfo n = Utilities.loadBotsFromJson();
        api = new DiscordApiBuilder().setToken(n.getToken()).login().join();
        helpEmbed = new HelpEmbed(COMMAND, "Starts a game of Mafia (e.g. !playMafia 8). For instructions, use the command: " + help +"\n**Make sure all players enable messages from server members. Bot must also have permission to message server members.");
    }


    @Override
    public void handle(MessageCreateEvent event) { //occurs whenever a msg is sent
        String msg = event.getMessageContent();

        if (msg.startsWith("Welcome To Mafia! Starting game with ")) {
            botId = event.getMessageAuthor().getId();
            channelId = event.getChannel().getId();
        }
        
        //instructions (can be at any time while MafiaPlayer is running)
        else if (msg.equalsIgnoreCase(help)) {
            EmbedBuilder instructions = new EmbedBuilder();

            instructions.setColor(new Color(40, 10, 255));
            instructions.setTitle("Mafia Game Instructions");
            instructions.setDescription(
                    "This is a version of the game Mafia, or Werewolf. \nFor help, use the command: " + help);
			instructions.addField("To play the game, use these commands:",
							"**" + COMMAND + "** - starts the game\n" +
							"**" + mafiaKill + "** - used by the mafia to kill a player\n" +
							"**" + doctorSave + "** - used by the doctor to attempt to save a player\n" +
							"**" + detectiveInspect + "** - used by the detective to inspect a player\n" +
							"**" + end + "** - ends the game at any point in the game\n" + 
							"\n**Game Play:** There are 7 to 16 players, some with special roles, listed below. Over a series of day-night cycles, players must collaborate to eliminate mafia members "
							+ "from the group. During the night, the Mafia, Doctor, and Detective perform their tasks, which can be found below. Then, once the day cycle beings, players discuss to "
							+ "decide a player they wish to vote out. If all mafia members are eliminated, then villagers win! If all but one villager and any mafia remain, then the mafia wins!"
							+ "\n**IMPORTANT:** Make sure all players enable messages from server members. Bot must also have permission to message server members.\n" +
							"\n**Roles:**" +
							"**Mafia** - vote to choose one villager to kill off during the night cycle\n" +
							"**Doctor** - can select one player to attempt to save from the mafia\n" +
							"**Detective** - can inspect one player which will reveal their role (Mafia or Villager)\n" +
							"\nGood luck and enjoy the game play!");
        } 
        
        else if (event.getMessageAuthor().getId() != botId) {
            //!Playing
            if (!gameStarted) {
                if (msg.startsWith(COMMAND)) {
                	restartGame();
                    try {
                        numPlayers = Integer.parseInt(msg.replace(COMMAND + " ", "").trim());
                        if (7 <= numPlayers && numPlayers <= 16) {
                            event.getChannel().sendMessage("Welcome To Mafia! Starting game with " + numPlayers + " players...");
                            event.getChannel().sendMessage("Player 1, please type below:");
                            assigningPlayers = true;
                            return;
                        }
                    } catch (NumberFormatException e) {
                        event.getChannel().sendMessage("Invalid command. Please include a number. (e.g. !playMafia 8)");
                    }
                }

                if (assigningPlayers) {
                    if (allPlayers.size() < numPlayers) {
                        if (allPlayers.size() < numPlayers - 1) {
                            event.getChannel().sendMessage("Player " + currentPlayerNumber++ + ", please type below:");
                        }
                        addPlayer(event);
                        if (allPlayers.size() == numPlayers) {
                            assigningPlayers = false;
                            gameStarted = true;
                            try {
                                event.getChannel().sendMessage("Selecting Mafia members, the Doctor, and the Detective.");
                                setRoles(event);
                                event.getChannel().sendMessage("Starting game!");
                                playRound(event);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }

            //Playing
            else {
                try {
                    playRound(event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void checkGameOver(MessageCreateEvent event) {
        if (mafiaMembers.isEmpty()) {
            String villagerss = villagersbackup.stream().map(member -> member.getName() + "\n").collect(Collectors.joining());
            event.getChannel().sendMessage("Villagers win!\n" + villagerss);
            System.out.println("mafia. " + mafiaMembers.size() + "\nvillagers. " + villageMembers.size());
            endGame();
        } else if (villageMembers.size() == 1) {
            String mafiaa = mafiabackup.stream().map(member -> member.getName() + "\n").collect(Collectors.joining());
            event.getChannel().sendMessage("Mafia win!\n" + mafiaa);
            System.out.println("mafia. " + mafiaMembers.size() + "\nvillagers. " + villageMembers.size());
            endGame();
        }
    }

    private void playRound(MessageCreateEvent event) throws InterruptedException {

        String msg = event.getMessageContent();

        if (startDayTime) {
            event.getChannel().sendMessage(":sunny: Day " + day + " :sunny:");

            if (day == 1) {
                event.getChannel().sendMessage(beginningStorylines[new Random().nextInt(beginningStorylines.length)]);
                event.getChannel().sendMessage(":knife::syringe::mag: The night cycle now begins. :knife::syringe::mag:\nWaiting for Mafia members, the Doctor, and the Detective to respond to direct messages.");
                startDayTime = false;
                mafiatime = true;
            } else {
                event.getChannel().sendMessage(":knife::syringe::mag: The night cycle now begins. :knife::syringe::mag:\nWaiting for Mafia members, the Doctor, and the Detective to respond to direct messages.");
                startDayTime = false;
                mafiatime = true;
            }
        }

        //get Mafia input
        if (mafiatime) {               	
            try {
                if (sendKillMessage) {
                	for (User mafiaMember : mafiaMembers) {
                		mafiaMember.openPrivateChannel().get().sendMessage("Who would you like to kill?  Type " + mafiaKill + " playerName");
                        String villageMemberString = villageMembers.stream().map(member -> member.getName() + "\n").collect(Collectors.joining());
                        mafiaMember.openPrivateChannel().get().sendMessage("Villagers List:\n" + villageMemberString);
                        sendKillMessage = false;
                    }
                    votes = new HashMap<>();
                    for (User villageMember : villageMembers) {
                    	votes.put(villageMember, 0);
                    }
                    return;
                }

                for (User mafiaMember : mafiaMembers) {
                    if (msg.contains(mafiaKill) && event.getMessageAuthor().getName().equals(mafiaMember.getName()) && event.isPrivateMessage()) {
                        User user = null;
                        numResponseFromMafiaMembers++;
                        boolean playerFound = false;
                        
                        String mafiaMessage = msg.replaceAll(" ", "").replace(mafiaKill, "");
                        for (int j = 0; j < villageMembers.size(); j++) {
                            if (villageMembers.get(j).getName().equals(mafiaMessage)) {
                                playerFound = true;
                                user = villageMembers.get(j);
                                mafiaMember.openPrivateChannel().get().sendMessage("You selected:  " + user.getName() + " :knife:\n\n");
                                break;
                            }
                        }
                        if (!playerFound) {
                            mafiaMember.openPrivateChannel().get().sendMessage("Player " + mafiaMessage + " not found. Please enter a user shown above");
                        } 
                        
                        else if (numResponseFromMafiaMembers >= mafiaMembers.size()) {
                            votes.put(user, votes.get(user) + 1);
                        	User userToRemove = votes.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
                            kill(event, userToRemove);
                            mafiatime = false;
                            doctortime = true;
                        }
                    } else if (event.getMessageAuthor().getName().equals(mafiaMember.getName()) && event.isPrivateMessage()) {
                        mafiaMember.openPrivateChannel().get().sendMessage("Please include the command " + mafiaKill);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


      Collections.shuffle(allPlayers);
      String allPlayerNamesString = allPlayers.stream().map(member -> member.getName() + "\n").collect(Collectors.joining());

        //get Doctor input
        if (doctortime) { 
            try {
            	if (sendSaveMessage) {
            		doctor.openPrivateChannel().get().sendMessage("Who would you like to attempt to save? Type " + doctorSave + " playerName");
            		doctor.openPrivateChannel().get().sendMessage("Player List:\n" + allPlayerNamesString);
            		
            		sendSaveMessage = false;
				}
            	
            	if (msg.contains(doctorSave) && event.getMessageAuthor().getName().equals(doctor.getName()) && event.isPrivateMessage()) {
            		User user = null;
            		boolean playerFound = false;         		

                    String doctorMessage = msg.replaceAll(" ", "").replace(doctorSave, "");
                	for (int j = 0; j < allPlayers.size(); j++) {
                        if (allPlayers.get(j).getName().equals(doctorMessage)) {
                            playerFound = true;
                            user = allPlayers.get(j);
                            doctor.openPrivateChannel().get().sendMessage("You selected: " + user.getName() + " :syringe:\n\n");
                            break;
                        }
                    }
                    if (playerFound) {
                    	save(event, user);
                        doctortime = false;
                        detectivetime = true;
                    } else {
                    	doctor.openPrivateChannel().get().sendMessage("Player " + doctorMessage + " not found. Please enter a user shown above");//                  	return;
                    }
                    
                } else if (event.getMessageAuthor().getName().equals(doctor.getName()) && !event.getMessageContent().startsWith(mafiaKill)  && event.isPrivateMessage()) {
                	doctor.openPrivateChannel().get().sendMessage("Please include the command " + doctorSave);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        //get Detective input
        if (detectivetime) {        	
            try {
            	if (sendInspectMessage) {
            		detective.openPrivateChannel().get().sendMessage("Who would you like to inspect? Type " + detectiveInspect + " playerName");
            		detective.openPrivateChannel().get().sendMessage("Player List:\n" + allPlayerNamesString);
            		
            		sendInspectMessage = false;
				}
            	
            	if (msg.startsWith(detectiveInspect) && event.getMessageAuthor().getName().equals(detective.getName()) && event.isPrivateMessage()) {
            		User user = null;
            		boolean playerFound = false;

                    String detectiveMessage = msg.replaceAll(" ", "").replace(detectiveInspect, "");
                	for (int j = 0; j < allPlayers.size(); j++) {
                        if (allPlayers.get(j).getName().equals(detectiveMessage)) {
                            playerFound = true;
                            user = allPlayers.get(j);
                            detective.openPrivateChannel().get().sendMessage("You selected: " + user.getName() + " :mag:\n\n");
                            break;
                        }
                    }
                    if (playerFound) {
                    	inspect(event, user);
                        detectivetime = false;
                        deathannouncement = true;
                    } else {
                    	detective.openPrivateChannel().get().sendMessage("Player " + detectiveMessage + " not found. Please enter a user shown above");
                    } 
                    
                } else if (event.getMessageAuthor().getName().equals(detective.getName()) && !event.getMessageContent().startsWith(doctorSave) && event.isPrivateMessage()) {
                	detective.openPrivateChannel().get().sendMessage("Please include the command " + detectiveInspect);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (deathannouncement) {        	
        	api.getServerTextChannelById(channelId).get().sendMessage("The night cycle is over.");
        	if (saved) {
        		api.getServerTextChannelById(channelId).get().sendMessage(killed.getName() + " was saved Doctor!");
        		deathannouncement = false;
        		voteTime = true;
			} else {
				villageMembers.remove(killed);
	            allPlayers.remove(killed);
	            api.getServerTextChannelById(channelId).get().sendMessage(killed.getName() + deathStorylines[new Random().nextInt(deathStorylines.length)] );
        		deathannouncement = false;
				voteTime = true;
			}   	    
        	api.getServerTextChannelById(channelId).get().sendMessage(allPlayers.size() + " players now remain. \nDiscuss, then when ready, vote for a player you think is a Mafia. \n");
        }
        
        //vote
        if (voteTime) {
        	if (sendVoteMessage) {
        		api.getServerTextChannelById(channelId).get().sendMessage("Which player would you like to vote out? Type " + vote + " playerName");

            	sendVoteMessage = false;
			}
    		
            if (msg.startsWith(vote) && event.getServerTextChannel().get().getId() == channelId) {
            	String voteMessage = msg.replace(" ", "").replace(vote, "");
            	
            	User user = null;
        		boolean playerFound = false;
            	
            	for (int j = 0; j < allPlayers.size(); j++) {
                    if (allPlayers.get(j).getName().equals(voteMessage)) {
                        playerFound = true;
                        user = allPlayers.get(j);
                        api.getServerTextChannelById(channelId).get().sendMessage("Voted for: " + user.getName());
                        break;
                    }
                }
            	
            	if (playerFound) {
            		votes.put(user, votes.get(user) + 1);
                    numVotesCast++;
				} else {
					api.getServerTextChannelById(channelId).get().sendMessage("Player " + voteMessage + " not found. Please enter a user shown above");
				}
                
            	if (numVotesCast >= allPlayers.size()) {
                    removeVotedOutPerson(event);
                    if (gameStarted) {
                    	day += 1;
                    	votes = null;
                    	numVotesCast = 0;
                        voteTime = false;
                        startDayTime = true;
                        sendKillMessage = true;
                        sendSaveMessage = true;
                        sendInspectMessage = true;
                        sendVoteMessage = true;
                        killed = null;
                        playRound(event);
					}
                } 
                
            } else if (!event.getMessageContent().startsWith(doctorSave) && !event.isPrivateMessage()) {
            	api.getServerTextChannelById(channelId).get().sendMessage("Please include the command " + vote);
            }
        }       
        
    }

    //end the game
    private void endGame() {
    	System.out.println("end");
    	restartGame();
        gameStarted = false;
    }

    //Get user input to confirm players
    private void addPlayer(MessageCreateEvent event) {
        allPlayers.add(event.getMessageAuthor().asUser().get());
    }
    
    
    //Randomly assign roles
    private void setRoles(MessageCreateEvent event) throws ExecutionException, InterruptedException {
    	temp.addAll(allPlayers);
    	
        //chooses mafia (2-4 mafia **every 4 players, one is added**)
        if (temp.size() <= 8) {
			for (int i = 0; i < 2; i++) {
				int r = new Random().nextInt(temp.size());
	            mafiaMembers.add(temp.get(r));
	            mafiaPrivateChannelId = mafiaMembers.get(0).openPrivateChannel().get().getId(); //change to get(r) later?
	            try {
	                //api.getPrivateChannelById(mafiaPrivateChannelId).get().addMessageCreateListener(this);
	                privateChannelIds.add(mafiaPrivateChannelId);
	            } catch (NoSuchElementException e) {
	                event.getChannel().sendMessage("An error occurred while creating private channels, please restart the game");
	                restartGame();
	                throw new IllegalStateException();
	            }
	            temp.remove(r);
			}
		} else {
			for (int i = 0; i < temp.size() / 4; i++) {
	        	int r = new Random().nextInt(temp.size());
	            mafiaMembers.add(temp.get(r));
	            mafiaPrivateChannelId = mafiaMembers.get(0).openPrivateChannel().get().getId();
	            try {
	                //api.getPrivateChannelById(mafiaPrivateChannelId).get().addMessageCreateListener(this);
	                privateChannelIds.add(mafiaPrivateChannelId);
	            } catch (NoSuchElementException e) {
	                event.getChannel().sendMessage("An error occurred while creating private channels, please restart the game");
	                restartGame();
	                throw new IllegalStateException();
	            }
	            temp.remove(r);
	        }
		}
        

        //chooses doctor (only 1)
        int r2 = new Random().nextInt(temp.size());
        doctor = temp.get(r2);
        villageMembers.add(doctor);  
        temp.remove(r2);

        //chooses detective (only 1)
        int r3 = new Random().nextInt(temp.size());
        detective = temp.get(r3);
        villageMembers.add(detective);  
        temp.remove(r3);
        
        villageMembers.addAll(temp); 
        
    	villagersbackup.addAll(villageMembers);
    	mafiabackup.addAll(mafiaMembers);
        
        System.out.println("mafia members: " + mafiaMembers.toString());
        System.out.println("doctor:" + doctor.toString());
        System.out.println("detective: " + detective.toString());
        System.out.println("village members" + villageMembers.toString());
        
    }

    private void removeVotedOutPerson(MessageCreateEvent event) {
        User userToRemove = votes.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        
        System.out.println("voted out user: " + userToRemove.getName());
        api.getServerTextChannelById(channelId).get().sendMessage(userToRemove.getName() + " has been voted out!\n");
        
        for (int i = 0; i < mafiaMembers.size(); i++) {
            if (mafiaMembers.get(i) == userToRemove) {
            	mafiaMembers.remove(userToRemove);
                allPlayers.remove(userToRemove);
				break;
            } else {
            	 if (villageMembers.contains(userToRemove)) {
            		 villageMembers.remove(userToRemove);
                     allPlayers.remove(userToRemove);
						break;
                 }                	
            }
		}
        checkGameOver(event);
    }


    //kill
    private void kill(MessageCreateEvent event, User user) {
        killed = user;
    	System.out.println("killed: " + user);
    }

    //save
    private void save(MessageCreateEvent event, User user) {
    	if (killed != null) {
    		if (killed == user) {
            	System.out.println("saved: " + user);
                saved = true;
            } else {
            	System.out.println("wasn't saved: " + killed + " | " + user);
            	saved = false;
            }
		}
    }

    //inspect
    private void inspect(MessageCreateEvent event, User c) {
       	try {
    		for (int i = 0; i < mafiaMembers.size(); i++) {
                if (mafiaMembers.get(i) == c) {
                	System.out.println("inspected: " + c);
					detective.openPrivateChannel().get().sendMessage(villageMembers.get(i).getName() + " is a Mafia.");
					break;
                } else {
                	 if (villageMembers.contains(c)) {
                     	System.out.println("inspected: " + c);
 						detective.openPrivateChannel().get().sendMessage(villageMembers.get(i).getName() + " is a Villager.");
 						break;
                     }                	
                }
    		} 
    	} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    }

    public void onMessageCreate(MessageCreateEvent event) {    	
    	event.getPrivateChannel().ifPresent(e -> {
              handle(event);
    	});
       
        event.getServerTextChannel().ifPresent(e -> {
            if (e.getName().equals(channelName)) {
        		//System.out.println(event.getMessageContent() + " | " + event.getMessageAuthor() + "\n");
                handle(event);
                return;
            } 
        });
    }

    public void restartGame() {
        gameStarted = false;
        mafiatime = false;
        detectivetime = false;
        deathannouncement = false;
        voteTime = false;

        mafiaMembers = new ArrayList<>();
        villageMembers = new ArrayList<>();
        allPlayers = new ArrayList<>();
        doctor = null;
        detective = null;

        votes = null;

        numPlayers = -1;
        assigningPlayers = false;
        currentPlayerNumber = 2;
        botId = 0;
        killed = null;

        mafiaPrivateChannelId = 0;
        numVotesCast = 0;
    }


}
