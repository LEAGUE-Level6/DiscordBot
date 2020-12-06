package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;

public class Raffle {
	private String message;
	private String reward;
	private String name;
	org.javacord.api.entity.emoji.Emoji reaction;
	ArrayList<org.javacord.api.entity.user.User> participants = new ArrayList<org.javacord.api.entity.user.User>();

	public Raffle(String message, String reward, String name, org.javacord.api.entity.emoji.Emoji reaction) {
		this.message = message;
		this.reward = reward;
		this.name = name;
		this.reaction = reaction;
	}

	public String toString() {
		return "Name:" + name + ", Message:" + message + ", Reward:" + reward + ", Reaction: " + reaction;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public org.javacord.api.entity.emoji.Emoji getReaction() {
		return reaction;
	}

	public void setReaction(org.javacord.api.entity.emoji.Emoji reaction) {
		this.reaction = reaction;
	}

	public ArrayList<org.javacord.api.entity.user.User> getParticipants() {
		return participants;
	}

	public void addParticipant(org.javacord.api.entity.user.User participant) {
		this.participants.add(participant);
		System.out.println(this.participants);
	}

	public void removeParticipant(org.javacord.api.entity.user.User participant) {
		if (this.participants.size() > 0) {
			this.participants.remove(participant);
			System.out.println(this.participants);

		}
	}

	public void startRaffle(TextChannel c, ReactionListener rl) {
		long id;
		try {
			id = c.sendMessage(message).get().getId();

			c.getMessageById(id).get().addReaction(reaction);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rl.setRecievingEntries(true, this);

	}

	public org.javacord.api.entity.user.User rollWinner(TextChannel t, ReactionListener rl) {
		if (participants.size() < 2) {
			t.sendMessage("There are not enough people entered to run the giveaway ¯\\_(ツ)_/¯");
			return null;
		} else {
			Random r = new Random();
			int index = r.nextInt(participants.size());
			return participants.get(index);
		}
	}

}
