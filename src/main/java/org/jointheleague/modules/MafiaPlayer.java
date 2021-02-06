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
    private static final String vote = ".mafia-vote";
    private static final String instructions = ".mafia-intructions";
    private static final String mafiaKill = ".mafia-kill";
    private static final String detectiveInspect = ".mafia-inspect";
    private static final String doctorSave = ".mafia-save";
    private static final String end = ".mafia-end";
    private static final String ready = ".mafia-ready";


    //game states
    private boolean gameStarted = false;
    private boolean mafiatime = false;
    private boolean detectivetime = false;
    private boolean doctortime = false;
    private boolean voteTime = false;
    private boolean settingRoles = true;
    private boolean sendKillMessage = true;

    //users
    ArrayList<User> mafiaMembers = new ArrayList<>();
    ArrayList<User> villageMembers = new ArrayList<>();
    User doctor;
    User detective;
    HashMap<User, Integer> votes;

    int numPlayers = -1;
    boolean assigningPlayers = false;
    int currentPlayerNumber = 2;
    long botId;
    User killed;

    List<Long> privateChannelIds = new ArrayList<>();
    long mafiaPrivateChannelId;
    private long doctorPrivateChannelId;
    private long detectivePrivateChannelId;
    private int numVotesCast;


    DiscordApi api;

    int numResponseFromMafiaMembers;


    int day = 1;

    static final String[] beginningStorylines = {"You are all passengers on a plane when suddenly, the engine gets stuck and the plane must take a crash landing. Luckily, the pilot(s) crash the plane onto a small, deserted island in the middle of the ocean, but the pilot is gone. " +
            "Now, you must all work together to survive until help arrives. However, there are imposters among the group. Collaborate together to find these imposters and survive until the end.\n"};
    static final String[] deathStorylines = {" was chased by a flock of angry pelicans.", " went for a swim and never came back.", " got crushed by a rock.", " hit their head too hard on a tree."};
    private boolean startDayTime = true;

    public MafiaPlayer(String channelName) {
        super(channelName);
        BotInfo n = Utilities.loadBotsFromJson();
        //api = new DiscordApiBuilder().setToken(n.getToken()).login().join();
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


            if (msg.contains(COMMAND) ||
                    msg.contains(vote) ||
                    msg.contains(mafiaKill) ||
                    msg.contains(detectiveInspect) ||
                    msg.contains(doctorSave)) {
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
                        if (villageMembers.size() < numPlayers) {
                            if (villageMembers.size() < numPlayers - 1) {
                                event.getChannel().sendMessage("Player " + currentPlayerNumber++ + ", please type below:");
                            }
                            addPlayer(event);
                            if (villageMembers.size() == numPlayers) {
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
                    checkGameOver(event);
                }
            }
        }
    }

    private void checkGameOver(MessageCreateEvent event) {
        if (mafiaMembers.isEmpty()) {
            event.getChannel().sendMessage("Villagers win!\n" + villageMembers.toString());
            restartGame();
        } else if (villageMembers.size() == 1) {
            event.getChannel().sendMessage("Mafia win!\n" + mafiaMembers.toString());
            restartGame();
        }
    }

    private void playRound(MessageCreateEvent event) throws InterruptedException {

        String msg = event.getMessageContent();

        if (startDayTime) {
            event.getChannel().sendMessage(":sunny: Day " + day + " :sunny:");

            if (day == 1) {
                event.getChannel().sendMessage(beginningStorylines[new Random().nextInt(beginningStorylines.length)]);
                event.getChannel().sendMessage(":knife::syringe::mag: The night cycle now begins. :knife::syringe::mag:\n Waiting for Mafia members, the Doctor, and the Detective to respond to direct messages.");
                //System.out.println(event.getChannel().getId());
                startDayTime = false;
                mafiatime = true;
            } else {
                event.getChannel().sendMessage(":knife::syringe::mag: The night cycle now begins. :knife::syringe::mag:\n  Waiting for Mafia members, the Doctor, and the Detective to respond to direct messages.");
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
                        mafiaMember.openPrivateChannel().get().sendMessage("Village Members:\n" + villageMemberString);
                        sendKillMessage = false;
                    }
                    votes = new HashMap<>();
                    for (User villageMember : villageMembers) {
                        votes.put(villageMember, 0);
                    }
                    return;
                }

                for (User mafiaMember : mafiaMembers) {
                    if (msg.contains(mafiaKill) && event.getMessageAuthor().getName().equals(mafiaMember.getName())) {
                        User user = null;

                        numResponseFromMafiaMembers++;
                        boolean playerFound = false;
                        String mafiaMessage = msg.replaceAll(" ", "").replace(mafiaKill, "");
                        for (int j = 0; j < villageMembers.size(); j++) {
                            if (villageMembers.get(j).getName().equalsIgnoreCase(mafiaMessage)) {
                                playerFound = true;
                                mafiaMember.openPrivateChannel().get().sendMessage("You selected:  " + mafiaMessage);
                                user = villageMembers.get(j);
                                votes.put(user, votes.get(user) + 1);
                            }
                        }
                        if (!playerFound) {
                            mafiaMember.openPrivateChannel().get().sendMessage("Player " + mafiaMessage + " not found. Please enter a user shown above");
                        } else if (numResponseFromMafiaMembers >= mafiaMembers.size()) {
                            User userToRemove = votes.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
                            kill(event, userToRemove);
                            sendKillMessage = true;
                            mafiatime = false;
                            doctortime = true;
                        }
                    } else if (event.getMessageAuthor().getName().equals(mafiaMember.getName())) {
                        mafiaMember.openPrivateChannel().get().sendMessage("Please include the command " + mafiaKill);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        List<User> allPlayers = new ArrayList(villageMembers);
        allPlayers.add(doctor);
        allPlayers.addAll(mafiaMembers);
        Collections.shuffle(allPlayers);
        String allPlayerNamesString = allPlayers.stream().map(member -> member.getName() + "\n").collect(Collectors.joining());

        //get Doctor input
        if (doctortime) {
            //System.out.println("doc");
            try {
                User user = null;
                //doctor.openPrivateChannel().get().sendMessage("Who would you like to attempt to save? Type " + doctorSave + " playerName");
                doctor.openPrivateChannel().get().sendMessage("Players:\n" + allPlayerNamesString);

                if (msg.startsWith(doctorSave) && event.getMessageAuthor() == doctor) {
                    for (int i1 = 0; i1 < villageMembers.size(); i1++) {
                        if (villageMembers.get(i1).getName() == msg.replaceAll(" ", "").replace(doctorSave, "")) {
                            user = villageMembers.get(i1);
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
                detective.openPrivateChannel().get().sendMessage("Who would you like to inspect?");
                if (msg.startsWith(detectiveInspect) && event.getMessageAuthor() == detective) {
                    String detectiveMessage = msg.replaceAll(" ", "").replace(detectiveInspect, "");
                    for (int i = 0; i < villageMembers.size(); i++) {
                        if (villageMembers.get(i).getName() == detectiveMessage) {
                            user = villageMembers.get(i);
                        }
                    }

                    for (int i = 0; i < mafiaMembers.size(); i++) {
                        if (mafiaMembers.get(i).getName() == detectiveMessage) {
                            user = mafiaMembers.get(i);
                        }
                    }

                    inspect(event, user);
                    detectivetime = false;
                    voteTime = true;
                    votes = new HashMap<>();
                    for (User villageMember : villageMembers) {
                        votes.put(villageMember, 0);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        //vote
        if (voteTime) {
            if (msg.startsWith(vote)) {
                User user = null;
                String voteMessage = msg.replaceAll(" ", "").replace(vote, "");
                for (int i = 0; i < villageMembers.size(); i++) {
                    if (villageMembers.get(i).getName() == voteMessage) {
                        user = villageMembers.get(i);
                        votes.put(user, votes.get(user) + 1);
                        numVotesCast++;
                    }
                }

                for (int i = 0; i < mafiaMembers.size(); i++) {
                    if (mafiaMembers.get(i).getName() == voteMessage) {
                        user = mafiaMembers.get(i);
                        votes.put(user, votes.get(user) + 1);
                        numVotesCast++;
                    }
                }

                if (numVotesCast >= villageMembers.size() + mafiaMembers.size()) {
                    removeVotedOutPerson(event);
                    day += 1;
                }

            }
        }
    }

    //end the game
    private void endGame() {
        gameStarted = false;
    }

    //Get user input to confirm players
    private void addPlayer(MessageCreateEvent event) {
        villageMembers.add(event.getMessageAuthor().asUser().get());
    }

    //Randomly assign roles
    private void setRoles(MessageCreateEvent event) throws ExecutionException, InterruptedException {
        //System.out.println("setting roles");

        //chooses mafia (can be from 1-3 mafia **every 5 players, one is added**)
        for (int i = 0; i < villageMembers.size() / 5; i++) {
            int r = new Random().nextInt(villageMembers.size());
            mafiaMembers.add(villageMembers.get(r));
            mafiaPrivateChannelId = mafiaMembers.get(0).openPrivateChannel().get().getId();
            try {
                api.getPrivateChannelById(mafiaPrivateChannelId).get().addMessageCreateListener(this);
                privateChannelIds.add(mafiaPrivateChannelId);
            } catch (NoSuchElementException e) {
                event.getChannel().sendMessage("An error occurred while creating private channels, please restart the game");
                restartGame();
                throw new IllegalStateException();
            }
            villageMembers.remove(r);
        }

        //chooses doctor (only 1)
        int r2 = new Random().nextInt(villageMembers.size());
        doctor = villageMembers.get(r2);
        //doctorPrivateChannelId = mafiaMembers.get(0).openPrivateChannel().get().getId();
        //privateChannelIds.add(doctorPrivateChannelId);
        //api.getPrivateChannelById(doctorPrivateChannelId).get().addMessageCreateListener(this);

        //chooses detective (only 1)
        int r3 = new Random().nextInt(villageMembers.size());
        detective = villageMembers.get(r3);
        //detectivePrivateChannelId = mafiaMembers.get(0).openPrivateChannel().get().getId();
        //privateChannelIds.add(detectivePrivateChannelId);
        //api.getPrivateChannelById(detectivePrivateChannelId).get().addMessageCreateListener(this);

        System.out.println("mafia members: " + mafiaMembers.toString());
        System.out.println("doctor:" + doctor.toString());
        System.out.println("detective: " + detective.toString());
        System.out.println("village members" + villageMembers.toString());
    }

    private void removeVotedOutPerson(MessageCreateEvent event) {
        User userToRemove = votes.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();

        try {
            villageMembers.remove(userToRemove);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mafiaMembers.remove(userToRemove);
        } catch (Exception e) {
            e.printStackTrace();
        }

        event.getChannel().sendMessage(userToRemove.getName() + " has been voted out!");
        checkGameOver(event);
    }


    //kill
    private void kill(MessageCreateEvent event, User user) {
        killed = user;
        villageMembers.remove(user);
    }

    //save
    private void save(MessageCreateEvent event, User user) {
        if (killed == user) {
            villageMembers.add(user);
            try {
                detective.openPrivateChannel().get().sendMessage(user.getName() + " was saved by the Doctor!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    //inspect
    private void inspect(MessageCreateEvent event, User c) throws InterruptedException, ExecutionException {
        for (int i = 0; i < mafiaMembers.size(); i++) {
            if (mafiaMembers.get(i) != c) {
                if (villageMembers.contains(c)) {
                    detective.openPrivateChannel().get().sendMessage(villageMembers.get(i).getName() + " is a Villager.");
                }
            } else {
                detective.openPrivateChannel().get().sendMessage(villageMembers.get(i).getName() + " is a Mafia.");
            }
        }
    }

    public void onMessageCreate(MessageCreateEvent event) {
        event.getPrivateChannel().ifPresent(e -> {
            for (long privateChannelId : privateChannelIds) {
                if (e.getId() == privateChannelId) {
                    System.out.println(event.getMessageContent() + "  |  " + event.getMessageAuthor());
                    handle(event);
                    break;
                }
            }
        });


        event.getServerTextChannel().ifPresent(e -> {
            if (e.getName().equals(channelName)) {
                handle(event);
            }
        });
    }

    public void restartGame() {
        gameStarted = false;
        mafiatime = false;
        detectivetime = false;
        voteTime = false;
        settingRoles = true;

        mafiaMembers = new ArrayList<>();
        villageMembers = new ArrayList<>();
        doctor = null;
        detective = null;

        votes = null;

        numPlayers = -1;
        assigningPlayers = false;
        currentPlayerNumber = 2;
        botId = 0;
        killed = null;

        mafiaPrivateChannelId = 0;
        doctorPrivateChannelId = 0;
        detectivePrivateChannelId = 0;
        numVotesCast = 0;
    }


}
