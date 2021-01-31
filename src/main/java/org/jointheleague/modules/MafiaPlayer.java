package org.jointheleague.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discord_bot_example.BotInfo;
import org.jointheleague.discord_bot_example.Utilities;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.javacord.api.entity.user.User;

public class MafiaPlayer extends CustomMessageCreateListener {

    private static final String COMMAND = ".playMafia";
    private static final String vote = ".mafia-vote";
    private static final String instructions = ".mafia-intructions";
    private static final String end = ".mafia-end";
    private static final String ready = ".mafia-ready";

    private static final String mafiaKill = ".mafia-kill";
    private static final String detectiveInspect = ".mafia-inspect";
    private static final String doctorSave = ".mafia-save";

    private static boolean gameStarted = false;
    private static boolean mafiatime = false;
    private static boolean detectivetime = false;
    private static boolean doctortime = false;
    private boolean settingRoles = true;

    ArrayList<User> names = new ArrayList<>();
    ArrayList<User> backup = new ArrayList<>();

    ArrayList<User> mafiaMembers = new ArrayList<>();
    ArrayList<User> villageMembers = new ArrayList<>();
    User Doctor;
    User Detective;


    int numPlayers = -1;
    boolean assigningPlayers = false;
    int currentPlayerNumber = 2;
    long botId;
    User killed;

    long mafiaPrivateChannelId;
    private long doctorPrivateChannelId;
    private long detectivePrivateChannelId;
    String pmchannel;
    DiscordApi api;

    int day = 1;

    static final String[] beginningStorylines = {"beginningStoryline"/*You are all passengers on a plane when suddenly, the engine gets stuck and the plane must take a crash landing. Luckily, the pilot(s) crash the plane onto a small, deserted island in the middle of the ocean, but the pilot is gone. " +
		"Now, you must all work together to survive until help arrives. However, there are imposters among the group. Collaborate together to find these imposters and survive until the end."*/};
    static final String[] deathStorylines = {" was chased by a flock of angry pelicans.", " went for a swim and never came back.", " got crushed by a rock.", " hit their head too hard on a tree."};


	/*

	-give .playMafia 7
		-send error message if they didn't include a number

	-have the 7 players send a message in the chat to record their IDs

	-set the roles

	-beginningStoryline in public channel

	-sends a private message to each player in the mafia, to see who to kill
		-currently only the first player

	-sends private message to doctor
		-enters a name, if same as mafia kill, then they are not killed

	-send private message to detective
		-enters a name, tells them the role of that person

	- public channel - player X has been killed / saved by the doctor

	- everyone votes on who they think is mafia (one person removed per round)

	- kill/remove the person that was voted
		- removed from all arraylists
		- can't vote any more
		- if all of the mafia is now removed, end the game

	-if all of the villagers are removed, the mafia wins





	 */

	/*
	Changes:
	playing renamed to gameStarted

	if(gameStarted) changed to else

	setRoles moved to member variables


	ArrayList<org.javacord.api.entity.user.User> Mafia = new ArrayList<org.javacord.api.entity.user.User>();
	ArrayList<org.javacord.api.entity.user.User> Doctor = new ArrayList<org.javacord.api.entity.user.User>();
	ArrayList<org.javacord.api.entity.user.User> Detective = new ArrayList<org.javacord.api.entity.user.User>();
	ArrayList<org.javacord.api.entity.user.User> Villagers = new ArrayList<org.javacord.api.entity.user.User>();

	Changed to

	ArrayList<User> mafiaMembers = new ArrayList<>();
	ArrayList<User> villageMembers = new ArrayList<>();
	User Doctor;
	User Detective;


	explicitly imported org.javacord.api.entity.user.User to avoid having to use fully qualified class name
	for variables

	renaming bot to botId

	renaming p to numPlayers

	renaming currentPlayer to currentPlayerNum

	renaming confirmPlayers() to addPlayer()

	making story strings static and final

	renaming Mafia() to playRound()

	moving if statement that finds bot id to start of handle method

	extracting code to checkGameOver method

	moving setRoles() call to inside of if (names.size() == numPlayers) and removing unused variable

	moving instructions if statement to the start of the handle method
	 */

    public MafiaPlayer(String channelName) {
        super(channelName);
        BotInfo n = Utilities.loadBotsFromJson();
        api = new DiscordApiBuilder().setToken(n.getToken()).login().join();
        helpEmbed = new HelpEmbed(COMMAND, "Starts a game of Mafia /(e.g. !playMafia 8). **Make sure all players enable messages from server members. Bot must also have permission to message server members. ");
    }


    @Override
    public void handle(MessageCreateEvent event) { //occurs whenever a msg is sent
        String msg = event.getMessageContent();

        if (msg.startsWith("Welcome To Mafia! Starting game with ")) {
            botId = event.getMessageAuthor().getId();
        }
        //instructions (can be at any time while MafiaPlayer is running)
        else if (msg.equalsIgnoreCase(instructions)) {
            EmbedBuilder instructions = new EmbedBuilder();

            instructions.setColor(new Color(255, 40, 20));
            instructions.setTitle("Mafia Game Instructions");
            instructions.setDescription(
                    "This is a version of the game Mafia. \n" + "If you need to know the rules, enter the command **" + "" + "**\n");
//			instructions.addField("To play the game, use these commands:",
//							"**" + COMMAND + "** - start the game\n" +
//							...
//
//							"**" + again + "** - used only directly after the last game ends, it allows you to play a new game in quick succession");
        } else if (event.getMessageAuthor().getId() != botId) {
            //!Playing
            if (!gameStarted) {
                if (msg.startsWith(COMMAND)) {
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
                    if (currentPlayerNumber > numPlayers) {
                        assigningPlayers = false;
                    } else {
                        event.getChannel().sendMessage("Player " + currentPlayerNumber + ", please type below:");
                    }
                    addPlayer(event);
                    currentPlayerNumber++;
                }

                if (names.size() == numPlayers) {
                    System.out.println("names" + names.toString());
                    gameStarted = true;
                    try {
                        setRoles();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    event.getChannel().sendMessage("Starting game!");
                    try {
                        playRound(event);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
                checkGameOver(event);
            }
        }
    }

    private void checkGameOver(MessageCreateEvent event) {
        if (mafiaMembers.isEmpty()) {
            event.getChannel().sendMessage("Villagers win!\n" + villageMembers.toString());
            endGame();
        } else if (villageMembers.size() == 1) {
            event.getChannel().sendMessage("Mafia win!\n" + mafiaMembers.toString());
            endGame();
        } else {
            System.out.println("not there yet");
            gameStarted = false;
            names.clear();
        }
    }

    //end the game
    private void endGame() {
        gameStarted = false;
    }

    //Get user input to confirm players
    private void addPlayer(MessageCreateEvent event) {
        names.add(event.getMessageAuthor().asUser().get());
    }

    //Randomly assign roles
    private void setRoles() throws ExecutionException, InterruptedException {
        System.out.println("setting roles");

        backup = names;

        //chooses mafia (can be from 1-3 mafia **every 5 players, one is added**)
        for (int i = 0; i < ((int) backup.size() / 5); i++) {
            int r = new Random().nextInt(backup.size());
            mafiaMembers.add(backup.get(r));
            mafiaPrivateChannelId = mafiaMembers.get(0).openPrivateChannel().get().getId();
            api.getPrivateChannelById(mafiaPrivateChannelId).get().addMessageCreateListener(this);
            //backup.remove(backup.get(r));
        }

        //chooses doctor (only 1)
        int r2 = new Random().nextInt(backup.size());
        Doctor = backup.get(r2);
        doctorPrivateChannelId = mafiaMembers.get(0).openPrivateChannel().get().getId();
        api.getPrivateChannelById(doctorPrivateChannelId).get().addMessageCreateListener(this);
        villageMembers.add(backup.get(r2));
        backup.remove(r2);

        //chooses detective (only 1)
        int r3 = new Random().nextInt(backup.size());
        Detective = backup.get(r3);
        detectivePrivateChannelId = mafiaMembers.get(0).openPrivateChannel().get().getId();
        api.getPrivateChannelById(detectivePrivateChannelId).get().addMessageCreateListener(this);
        villageMembers.add(backup.get(r3));
        backup.remove(r3);

        backup = names;

//		System.out.println("n" + names.toString() + "\n");
//		System.out.println("m" + Mafia.toString());
//		System.out.println("doc" + Doctor.toString());
//		System.out.println("d" + Detective.toString());
//		System.out.println("v" + Villagers.toString());
    }

    private void playRound(MessageCreateEvent event) throws InterruptedException {
        MafiaPlayer mp = new MafiaPlayer(channelName);

        String msg = event.getMessageContent();

        event.getChannel().sendMessage("Day " + day);

        if (day == 1) {
            event.getChannel().sendMessage(beginningStorylines[(int) new Random().nextInt(beginningStorylines.length)]);
            event.getChannel().sendMessage("The night cycle now begins.");
            System.out.println(event.getChannel().getId());
            mafiatime = true;
        } else {
            event.getChannel().sendMessage("The night cycle now begins.");
            mafiatime = true;
        }

        //get Mafia input
        if (mafiatime) {
            try {
                User user = null;
                mafiaMembers.get(0).openPrivateChannel().get().sendMessage("Who would you like to kill?");

                if (msg.contains(mafiaKill) && event.getMessageAuthor() == mafiaMembers.get(0)) {
                    mafiaMembers.get(0).openPrivateChannel().get().sendMessage("thanks for the message");
                    for (int j = 0; j < names.size(); j++) {
                        if (names.get(j).getName().equalsIgnoreCase(msg.replaceAll(" ", "").replace(mafiaKill, ""))) {
                            user = names.get(j);
                        }
                    }
                    kill(event, user);
                    doctortime = true;
                    mafiatime = false;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        //get Doctor input
        if (doctortime) {
            System.out.println("doc");
            try {
                User user = null;
                Doctor.openPrivateChannel().get().sendMessage("Who would you like to save?");
                if (msg.startsWith(doctorSave) && event.getMessageAuthor() == Doctor) {
                    for (int i1 = 0; i1 < names.size(); i1++) {
                        if (names.get(i1).getName() == msg.replaceAll(" ", "").replace(doctorSave, "")) {
                            user = names.get(i1);
                        }
                    }
                    save(event, user);
                    detectivetime = true;
                    doctortime = false;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        //get Detective input
        if (detectivetime) {
            try {
                User user = null;
                Detective.openPrivateChannel().get().sendMessage("Who would you like to inspect?");
                if (msg.startsWith(detectiveInspect) && event.getMessageAuthor() == Detective) {
                    for (int i1 = 0; i1 < names.size(); i1++) {
                        if (names.get(i1).getName() == msg.replaceAll(" ", "").replace(detectiveInspect, "")) {
                            user = names.get(i1);
                        }
                    }
                    inspect(event, user);
                    detectivetime = false;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        //vote
        if (msg.startsWith(vote)) {
            User user = null;
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).getName() == msg.replaceAll(" ", "").replace(vote, "")) {
                    user = names.get(i);
                }
            }
            vote(event, user);
        }

        day += 1;
        System.out.println("Mafia method done");
    }


    private void vote(MessageCreateEvent event, User user) {
        try {
            villageMembers.remove(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mafiaMembers.remove(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        event.getChannel().sendMessage(user.getName() + " has been voted out!");
    }


    //kill
    private void kill(MessageCreateEvent event, User user) {
        System.out.println("kill");
        killed = user;
        villageMembers.remove(user);
    }

    //save
    private void save(MessageCreateEvent event, User user) {
        if (killed == user) {
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i) == user) {
                    villageMembers.add(names.get(i));
                }
            }
        }
    }

    //inspect
    private void inspect(MessageCreateEvent event, User c) throws InterruptedException, ExecutionException {
        for (int i = 0; i < mafiaMembers.size(); i++) {
            if (mafiaMembers.get(i) != c) {
                if (villageMembers.contains(c)) {
                    Detective.openPrivateChannel().get().sendMessage(villageMembers.get(i).getName() + " is a Villager.");
                }
            } else {
                Detective.openPrivateChannel().get().sendMessage(villageMembers.get(i).getName() + " is a Mafia.");
            }
        }
    }

    public void onMessageCreate(MessageCreateEvent event) {
        event.getPrivateChannel().ifPresent(e -> {
            if (e.getId() == mafiaPrivateChannelId) {
                System.out.println(event.getMessageContent() + "  |  " + event.getMessageAuthor());
                handle(event);
            }
        });


        event.getServerTextChannel().ifPresent(e -> {
            if (e.getName().equals(channelName)) {
                handle(event);
            }
        });
    }

}
