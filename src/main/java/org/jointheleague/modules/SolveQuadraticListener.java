package org.jointheleague.modules;

import java.math.BigDecimal;

import java.math.MathContext;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;


public class SolveQuadraticListener extends CustomMessageCreateListener {

	private static final String COMMAND = "!solvequad";
	private static int stage = 0;
	private static String n = "";
	private User user;
	private static MathContext mc = new MathContext(9);
	private static BigDecimal a;
	private static BigDecimal b;
	private static BigDecimal c;
	private static BigDecimal ans1;
	private static BigDecimal ans2;
	private static BigDecimal four = new BigDecimal(4,mc);
	private static BigDecimal two = new BigDecimal(2,mc);

	public SolveQuadraticListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(stage == 0) {
			if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
				stage = 1;
				n = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage("Enter the 'A' Value");
			}
		}
		else if(stage == 1) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				stage = 2;
				a = new BigDecimal(event.getMessageContent(),mc);
				event.getChannel().sendMessage("Enter the 'B' Value");
			}
		}
		else if(stage == 2) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				stage = 3;
				b = new BigDecimal(event.getMessageContent(),mc);
				event.getChannel().sendMessage("Enter the 'C' Value");
			}
		}
		else if(stage == 3) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				stage = 0;
				c = new BigDecimal(event.getMessageContent(),mc);
				try {
					BigDecimal bottom = two.multiply(a);
					BigDecimal desc = new BigDecimal(Math.sqrt(((b.pow(2).subtract(four.multiply(a).multiply(c))).doubleValue())));
					BigDecimal negb = b.negate();
					ans1 = (negb.add(desc)).divide(bottom);
					ans2 = (negb.subtract(desc)).divide(bottom);
					if(ans1.equals(ans2)) {
						event.getChannel().sendMessage("Your answer is " + ans1.floatValue());
					} else {
						event.getChannel().sendMessage("Your answers are " + ans1.floatValue() + " and " + ans2.floatValue());
					}
				} catch(Exception e) {
					event.getChannel().sendMessage("Your answer is " + e.getMessage());
				}
			}
		}
	}
}
