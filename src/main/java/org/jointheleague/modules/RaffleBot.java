package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.event.message.MessageCreateEvent;

public class RaffleBot extends CustomMessageCreateListener {
	private final String CREATERAFFLECMD = "raffle create";
	private final String STARTRAFFLECMD = "raffle start";
	private final String FINISHRAFFLECMD = "raffle roll winner";
	private final String RAFFLEHELPCMD = "raffle help";
	boolean gettingReward = false;
	boolean gettingEntryMessage = false;
	boolean gettingReaction = false;
	boolean recievedReaction = false;
	String sender;
	TextChannel c;
	ArrayList<Raffle> raffles = new ArrayList<Raffle>();
	int raffleIndex = 0;
	ReactionListener r;

	public RaffleBot(String channelName, DiscordApi api) {
		super(channelName);
		ReactionListener r = new ReactionListener(this);
		this.r = r;
		api.getServerTextChannelsByName(channelName).forEach(e -> e.addReactionAddListener(r));

	}

	@Override
	public void handle(MessageCreateEvent e) {
		if (!e.getMessageAuthor().asUser().get().isBot()) {
			String cmd = e.getMessageContent();
			c = e.getChannel();
			if (cmd.equalsIgnoreCase(RAFFLEHELPCMD)) {
				c.sendMessage("**Getting Started**\n"
						+ "1. Type 'raffle create <name>' to create a new raffle.\n"
						+ "2. Enter the raffle information as prompted by the bot\n"
						+ "3. Type 'raffle start <name>' to start the giveaway and wait for people to enter.\n"
						+ "4. Once enough people have joined, type 'raffle roll winner <name>' to roll the winner of the raffle.\n"
						+ "Note: there needs to be at least 2 non bot participants to roll the winner of the raffle.\n"
						+ "Once the winner has been rolled, they will be removed from the raffle entries and you can"
						+ "roll the winner again.");
				return;
			}
			if (cmd.contains(CREATERAFFLECMD)) {
				cmd = cmd.replace(CREATERAFFLECMD, "").trim();
				if (cmd.equals("")) {
					c.sendMessage("A raffle name must be specified");
					return;
				}
				Raffle r = new Raffle(null, null, cmd, null);
				if (raffles.contains(r)) {
					c.sendMessage("A raffle with this name already exists");
				} else {
					c.sendMessage("A raffle has been created with the name: " + cmd);
					raffles.add(r);
					raffleIndex = raffles.indexOf(r);
				}

				c.sendMessage("What would you like to give away?");
				gettingReward = true;
				sender = e.getMessageAuthor().getName();
				return;
			}
			if (gettingReward) {
				if (e.getMessageAuthor().getName().equals(sender)) {
					cmd = e.getMessageContent();
					raffles.get(raffleIndex).setReward(cmd);
					c.sendMessage("Reward set to " + cmd);
					gettingReward = false;
					gettingEntryMessage = true;
					c.sendMessage("What message would you like to send in order to enter the raffle?");
					return;
				}
			}
			if (gettingEntryMessage) {
				if (e.getMessageAuthor().getName().equals(sender)) {
					cmd = e.getMessageContent();

					raffles.get(raffleIndex).setMessage(cmd);
					;

					String message = "Message set to " + raffles.get(raffleIndex).getMessage() + "\n"
							+ "React to this message with the reaction people must use to enter the raffle.";
					c.sendMessage(message);
					gettingEntryMessage = false;
					gettingReaction = true;
					return;
				}
			}
			cmd = e.getMessageContent();
			if (cmd.contains(STARTRAFFLECMD)) {
				cmd = cmd.replace(STARTRAFFLECMD, "").trim();
				if (cmd.equals("")) {
					c.sendMessage("A raffle name must be specified");
					return;
				}

				for (Raffle raffle : raffles) {
					if (raffle.getName().equalsIgnoreCase(cmd)) {
						raffle.startRaffle(c, r);
						return;

					}
				}
				c.sendMessage("the raffle **" + cmd + "** does not exist");
			}
			cmd = e.getMessageContent();
			if (cmd.contains(FINISHRAFFLECMD)) {
				cmd = cmd.replace(FINISHRAFFLECMD, "").trim();
				if (cmd.equals("")) {
					c.sendMessage("A raffle name must be specified");
					return;
				}

				for (Raffle raffle : raffles) {

					if (raffle.getName().equalsIgnoreCase(cmd)) {

						org.javacord.api.entity.user.User winner = raffle.rollWinner(c, r);
						if (winner != null) {

							String message = "The Winner is... " + winner.getMentionTag() + "!";
							message += "\nYou have won " + raffle.getReward();
							raffle.getParticipants().remove(winner);
							c.sendMessage(message);
							return;

						} else {
							return;
						}
					}
				}
				c.sendMessage("the raffle **" + cmd + "** does not exist");
			}
		}
	}

	public void getReaction(String message, Emoji reaction) {
		if ((message.equals("Message set to " + raffles.get(raffleIndex).getMessage() + "\n"
				+ "React to this message with the reaction people must use to enter the raffle."))) {

			raffles.get(raffleIndex).setReaction(reaction);

		}
		System.out.println(reaction);
		String m = "Item to give away: **" + raffles.get(raffleIndex).getReward() + "** Reaction Selected: "
				+ raffles.get(raffleIndex).getReaction() + " Message to display: **"
				+ raffles.get(raffleIndex).getMessage() + "**";
		c.sendMessage(m);
		gettingReaction = false;
		recievedReaction = true;

	}

}
