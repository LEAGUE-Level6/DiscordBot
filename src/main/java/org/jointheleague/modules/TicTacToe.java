package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import net.aksingh.owmjapis.api.APIException;

public class TicTacToe extends CustomMessageCreateListener {
	boolean win = false;
	boolean botStart = false;
	boolean start = false;
	boolean bottomLeft = false;
	boolean bottomMiddle = false;
	boolean bottomRight = false;
	boolean middleLeft = false;
	boolean middleMiddle = false;
	boolean middleRight = false;
	boolean topLeft = false;
	boolean topMiddle = false;
	boolean topRight = false;
	boolean botBottomLeft = false;
	boolean botBottomMiddle = false;
	boolean botBottomRight = false;
	boolean botMiddleLeft = false;
	boolean botMiddleMiddle = false;
	boolean botMiddleRight = false;
	boolean botTopLeft = false;
	boolean botTopMiddle = false;
	boolean botTopRight = false;
	boolean OveralbottomLeft = false;
	boolean OveralbottomMiddle = false;
	boolean OveralbottomRight = false;
	boolean OveralmiddleLeft = false;
	boolean OveralmiddleMiddle = false;
	boolean OveralmiddleRight = false;
	boolean OveraltopLeft = false;
	boolean OveraltopMiddle = false;
	boolean OveraltopRight = false;
	boolean OveralbotBottomLeft = false;

	public TicTacToe(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains("!TicTacToe")) {
			win = false;
			botStart = false;
			start = false;
			bottomLeft = false;
			bottomMiddle = false;
			bottomRight = false;
			middleLeft = false;
			middleMiddle = false;
			middleRight = false;
			topLeft = false;
			topMiddle = false;
			topRight = false;
			botBottomLeft = false;
			botBottomMiddle = false;
			botBottomRight = false;
			botMiddleLeft = false;
			botMiddleMiddle = false;
			botMiddleRight = false;
			botTopLeft = false;
			botTopMiddle = false;
			botTopRight = false;
			event.getChannel().sendMessage(
					"You go first. Say where you want to go using coordinates, !0,0 is bottom left, up to !2,2 or top right");

			start = true;
			botStart = true;

		} else if (start) {
			stage2(event);
		}

	}

	void stage2(MessageCreateEvent event) {
		if ((event.getMessageAuthor().isYourself())) {
			botStart = false;
		} else {
			botStart = true;
		}
		if (botStart && !win) {
			if (event.getMessageContent().equals("0,0")) {
				if (!bottomLeft && !botBottomLeft) {
					bottomLeft = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (bottomLeft || botBottomLeft) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			} else if (event.getMessageContent().equals("1,0")) {
				if (!bottomMiddle && !botBottomMiddle) {
					bottomMiddle = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (bottomMiddle || botBottomMiddle) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			} else if (event.getMessageContent().equals("2,0")) {
				if (!bottomRight && !botBottomRight) {
					bottomRight = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (bottomRight || botBottomRight) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			} else if (event.getMessageContent().equals("0,1")) {
				if (!middleLeft && !botMiddleLeft) {
					middleLeft = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (middleLeft || botMiddleLeft) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			} else if (event.getMessageContent().equals("1,1")) {
				if (!middleMiddle && !botMiddleMiddle) {
					middleMiddle = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (middleMiddle || botMiddleMiddle) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			} else if (event.getMessageContent().equals("2,1")) {
				if (!middleRight && !botMiddleRight) {
					middleRight = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (middleRight || botMiddleRight) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			} else if (event.getMessageContent().equals("0,2")) {
				if (!topLeft && !botTopLeft) {
					topLeft = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (topLeft || botTopLeft) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			} else if (event.getMessageContent().equals("1,2")) {
				if (!topMiddle && !botTopMiddle) {
					topMiddle = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (topMiddle || botTopMiddle) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			} else if (event.getMessageContent().equals("2,2")) {
				if (!topRight && !botTopRight) {
					topRight = true;
					checkWinStage(event);
					event.getChannel().sendMessage("Ok my turn, I will go, hmmm...");
					stage3(event);
				} else if (topRight || botTopRight) {
					event.getChannel().sendMessage("That spot has already been used, try again");

				}
			}
		}
	}

	void stage3(MessageCreateEvent event) {
		Random r = new Random();
		int rand = r.nextInt(9);
		
			if (rand == 0) {
				if (!bottomLeft && !botBottomLeft) {
					event.getChannel().sendMessage("0, 0");
					botBottomLeft = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
			if (rand == 1) {
				if (!bottomMiddle && !botBottomMiddle) {
					event.getChannel().sendMessage("1, 0");
					botBottomMiddle = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
			if (rand == 2) {
				if (!bottomRight && !botBottomRight) {
					event.getChannel().sendMessage("2, 0");
					botBottomRight = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
			if (rand == 3) {
				if (!middleLeft && !botMiddleLeft) {
					event.getChannel().sendMessage("0, 1");
					botMiddleLeft = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
			if (rand == 4) {
				if (!middleMiddle && !botMiddleMiddle) {
					event.getChannel().sendMessage("1, 1");
					botMiddleMiddle = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
			if (rand == 5) {
				if (!middleRight && !botMiddleRight) {
					event.getChannel().sendMessage("2, 1");
					botMiddleRight = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
			if (rand == 6) {
				if (!topLeft && !botTopLeft) {
					event.getChannel().sendMessage("0, 2");
					botTopLeft = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
			if (rand == 7) {
				if (!topMiddle && !botTopMiddle) {
					event.getChannel().sendMessage("1, 2");
					botTopMiddle = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
			if (rand == 8) {
				if (!topRight && !botTopRight) {
					event.getChannel().sendMessage("2, 2");
					botTopRight = true;
					checkBotWinStage(event);

				} else {
					rand = r.nextInt(9);
				}
			}
		
	}

	void checkWinStage(MessageCreateEvent event) {
		if (topLeft && topMiddle && topRight) {
			winStage(event);
		}
		else if (middleLeft && middleMiddle && middleRight) {
			winStage(event);
		}
		else if (bottomLeft && bottomMiddle && bottomRight) {
			winStage(event);
		}
		else if (topLeft && middleLeft && bottomLeft) {
			winStage(event);
		}
		else if (topMiddle && middleMiddle && bottomMiddle) {
			winStage(event);
		}
		else if (topRight && middleRight && bottomRight) {
			winStage(event);
		}
		else if (topLeft && middleMiddle && bottomRight) {
			winStage(event);
		}
		else if (topRight && middleMiddle && bottomLeft) {
			winStage(event);
		}
	}

	void winStage(MessageCreateEvent event) {
		win = true;
		event.getChannel()
				.sendMessage("Congratulations, you have won, if you want to play again just run the command again");
		
	}

	void checkBotWinStage(MessageCreateEvent event) {
		if (botTopLeft && botTopMiddle && botTopRight) {
			botWinStage(event);
		}
		if (botMiddleLeft && botMiddleMiddle && botMiddleRight) {
			botWinStage(event);
		}
		if (botBottomLeft && botBottomMiddle && botBottomRight) {
			botWinStage(event);
		}
		if (botTopLeft && botMiddleLeft && botBottomLeft) {
			botWinStage(event);
		}
		if (botTopMiddle && botMiddleMiddle && botBottomMiddle) {
			botWinStage(event);
		}
		if (botTopRight && botMiddleRight && botBottomRight) {
			botWinStage(event);
		}
		if (botTopLeft && botMiddleMiddle && botBottomRight) {
			botWinStage(event);
		}
		if (botTopRight && botMiddleMiddle && botBottomLeft) {
			botWinStage(event);
		}
	}

	void botWinStage(MessageCreateEvent event) {
		event.getChannel().sendMessage(
				"Haha, I have won, You are absolute garbage, you utter buffoon, don't even bother playing again, you'll just embarrass yourself");
	}

	void checkForTie(MessageCreateEvent event) {
		if (topLeft || botTopLeft) {
			OveraltopLeft = true;
		}
		if (topMiddle || botTopMiddle) {
			OveraltopMiddle = true;
		}
		if (topRight || botTopRight) {
			OveraltopRight = true;
		}
		if (middleLeft || botMiddleLeft) {
			OveralmiddleLeft = true;
		}
		if (middleMiddle || botMiddleMiddle) {
			OveralmiddleMiddle = true;
		}
		if (middleRight || botMiddleRight) {
			OveralmiddleRight = true;
		}
		if (bottomLeft || botBottomLeft) {
			OveralbottomLeft = true;
		}
		if (bottomMiddle || botBottomMiddle) {
			OveralbottomMiddle = true;
		}
		if (bottomRight || botBottomRight) {
			OveralbottomRight = true;
		}

		if (OveraltopLeft && OveraltopMiddle && OveraltopRight && OveralmiddleLeft && OveralmiddleMiddle
				&& OveralmiddleRight && OveralbottomLeft && OveralbottomMiddle && OveralbottomRight) {
			tieStage(event);
		}

	}

	void tieStage(MessageCreateEvent event) {
		event.getChannel().sendMessage(
				"Well, I guess we tied, you're still absolute garbage for my moves are absolutely random with no strategy at all you utter buffoon");
	}
}
