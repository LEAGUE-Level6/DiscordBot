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



	private static final String COMMAND = "!pythagCalc";
	int stage = 0;
	private static String n = "";
	private static int a;
	private static int b;

	//private static String a;
	//private static String b;
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
				n = event.getMessageAuthor().getDisplayName();
				event.getChannel().sendMessage("**Pythag Calc**: Enter the A value of the triangle");
			}
		}
		
		//System.out.println(stage);
		else if(stage == 1) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				stage = 2;
				a = Integer.parseInt(event.getMessageContent());
			//	a = new BigDecimal(event.getMessageContent(),mc);
				event.getChannel().sendMessage("**Pythag Calc**: Enter the B Value of the triangle");
				
				

				
			}
		}
		
		else if(stage == 2) {
			if(event.getMessageAuthor().getDisplayName().equals(n)) {
				//stage = 3;
				//b = new BigDecimal(event.getMessageContent(),mc);
				b = Integer.parseInt(event.getMessageContent());
				int aSquared = a * a;
				int bSquared = b * b;
				int bothAdded = aSquared + bSquared;
				System.out.println(bothAdded);
				
				Double rooted = Math.sqrt(bothAdded);
				//BigDecimal finalAnswerNotRounded = new BigDecimal(Math.sqrt(bothAdded));
				long finalAnswer = Math.round(rooted);
				event.getChannel().sendMessage("**Pythag Calc**: Your answer is " + finalAnswer);
				stage = 0;
				
			}
		}
		/*
		
		else if(stage == 3) {
			int aSquared = a * a;
			int bSquared = b * b;
			long bothAdded = a + b;
			
			BigDecimal finalAnswerNotRounded = new BigDecimal(Math.sqrt(bothAdded));
			long finalAnswer = Math.round(finalAnswerNotRounded.doubleValue());
			event.getChannel().sendMessage("**Pythag Calc**: Your answer is " + finalAnswer);
			stage = 0;

		}
		*/
	}

}

