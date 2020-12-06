package org.jointheleague.modules;

import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

public class ReactionListener implements ReactionAddListener {
	boolean recievingEntries = false;
	Raffle raffleToRecieveEntries;
	String channelName = "";
	RaffleBot rb;

	public ReactionListener(RaffleBot rb) {
		this.rb = rb;
	}

	@Override
	public void onReactionAdd(ReactionAddEvent e) {
		String message = e.getMessageContent().get();
		org.javacord.api.entity.emoji.Emoji reaction = e.getReaction().get().getEmoji();
		if (rb.gettingReaction) {
			rb.getReaction(message, reaction);
		}
		if (recievingEntries) {
			if (message.equalsIgnoreCase(raffleToRecieveEntries.getMessage())) {
				if (reaction.equalsEmoji(raffleToRecieveEntries.getReaction())) {
					if (!e.getUser().isBot()) {
						raffleToRecieveEntries.addParticipant(e.getUser());
					}
				}

			}
		}
	}

	public void setRecievingEntries(boolean recievingEntries, Raffle raffle) {
		this.recievingEntries = recievingEntries;
		raffleToRecieveEntries = raffle;

	}

}
