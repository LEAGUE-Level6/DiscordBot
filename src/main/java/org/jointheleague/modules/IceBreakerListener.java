package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class IceBreakerListener extends CustomMessageCreateListener{
	List<String> question;
	ArrayList<String> verb;
	ArrayList<String> noun;
	ArrayList<String> adjective;
	public IceBreakerListener(String channelName) throws IOException {
		super(channelName);	
		
		//creating question words
		question = Arrays.asList("Who", "What" , "Where", "When" , "Why" );
		//creating nouns
		noun = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/jointheleague/modules/nouns"));
			String x = br.readLine();
			while(x !=null){
				noun.add(x);
				x = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create verbs
		verb = new ArrayList<String>();
		try {
			BufferedReader br2 = new BufferedReader(new FileReader("src/main/java/org/jointheleague/modules/verbs"));
			String y = br2.readLine();
			while(y !=null){
				verb.add(y);
				y = br2.readLine();
			}
			br2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create adjectives
		
		adjective = new ArrayList<String>();
		try {
			BufferedReader br3 = new BufferedReader(new FileReader("src/main/java/org/jointheleague/modules/adjectives"));
			String y = br3.readLine();
			while(y !=null){
				adjective.add(y);
				y = br3.readLine();
			}
			br3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
			String message;
			int sentenceStructure = new Random().nextInt(2);
			String randomq = question.get(new Random().nextInt(question.size()));
			String randomnoun = noun.get(new Random().nextInt(noun.size()));
			String randomverb = verb.get(new Random().nextInt(verb.size()));
			String randomadjective = adjective.get(new Random().nextInt(adjective.size()));
			String randomarticle;
			if (event.getMessageContent().startsWith("!icebreaker")) {
			if(sentenceStructure == 0) {
			message = randomq + " did the " + randomadjective + " " + randomnoun + " " + randomverb + "?";
			}else {
			message = "Did you " + randomverb + " the " + randomadjective + " " + randomnoun + "?";
			}
			event.getChannel().sendMessage(message);
		}else if(event.getMessageContent().startsWith("!pickup")) {
			if(randomadjective.charAt(0) == 'a' || randomadjective.charAt(0) == 'e'||randomadjective.charAt(0) == 'i'||randomadjective.charAt(0) == 'o'||randomadjective.charAt(0) == 'u') {
				randomarticle = "an";
			}else {
				randomarticle = "a";
			}
			message = "Are you " + randomarticle + " " + randomadjective + " " + randomnoun + "? Because you make me " + randomverb + "!";
			event.getChannel().sendMessage(message);
		}
	}

}
