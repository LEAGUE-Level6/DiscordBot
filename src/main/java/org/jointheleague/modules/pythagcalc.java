package org.jointheleague.modules;

import java.math.BigDecimal;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;
import java.math.BigDecimal;

import java.math.MathContext;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
public class pythagcalc extends CustomMessageCreateListener {



	private static final String COMMAND = "!pythagcalc";
	private static int stage = 0;
	private static String n = "";
	private static String a;
	private static String b;
	private static MathContext mc = new MathContext(9);

	private static int ans1;
	private static int ans2;
	
	public pythagcalc(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Uses Pythag Theorem to find the C/hypotenuse of a triangle");

	}
	
	


	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(stage == 0) {
			if (event.getMessageContent().equalsIgnoreCase(COMMAND)) {
				stage = 1;
				a = event.getMessageContent();
				event.getChannel().sendMessage("**Pythag Calc**: Enter the A value of the triangle");
			}
		}
		else if(stage == 1) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				stage = 2;
				b = event.getMessageContent();
				event.getChannel().sendMessage("**Pythag Calc**: Enter the B Value of the triangle");
				
				int a1 = Integer.parseInt(a);
				int b1 = Integer.parseInt(b);
				 ans1 = a1 * a1;
				 ans2 = b1 * b1;
				 
				 int finalAnswer = ans1 + ans2;
				event.getChannel().sendMessage("**Pythag Calc**: Your answer is " + finalAnswer);

				
			}
		}
		
	}
	
	
}
