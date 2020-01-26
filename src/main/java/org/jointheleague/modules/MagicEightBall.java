package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import net.aksingh.owmjapis.api.APIException;

public class MagicEightBall extends CustomMessageCreateListener {

	ArrayList<String> answers;
	Random rand = new Random();
	
public MagicEightBall(String channelName) {
		super(channelName);
		answers = new ArrayList<String>();
		answers.add("It is certain.");
		answers.add("It is decidedly so.");
		answers.add("Without a doubt.");
		answers.add("Yes -- definitely.");
		answers.add("You may rely on it.");
		answers.add("As I see it, yes.");
		answers.add("Most likely.");
		answers.add("Outlook good.");
		answers.add("Yes.");
		answers.add("Signs point to yes.");
		answers.add("Reply hazy, try again.");
		answers.add("Ask again later.");
		answers.add("Better not tell you now.");
		answers.add("Cannot predict now.");
		answers.add("Concentrate and ask again.");
		answers.add("Don't count on it.");
		answers.add("My reply is no.");
		answers.add("My sources say no.");
		answers.add("Outlook not so good.");
		answers.add("Very doubtful.");
		
}

@Override
public void handle(MessageCreateEvent event) throws APIException {
	// TODO Auto-generated method stub
	
	if(event.getMessageContent().contains("!eightball")) {
	event.getChannel().sendMessage("Hmmm, let me think...");
	event.getChannel().sendMessage(answers.get(rand.nextInt(answers.size())));
	}
	
}


}
