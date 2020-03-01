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
			String x = br2.readLine();
			while(x !=null){
				noun.add(x);
				x = br2.readLine();
			}
			br2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith("!icebreaker")) {
			//String randomq = question.get(new Random.nextInt(question.size()));
			//String randomnoun
			//String randomverb
			//event.getChannel().sendMessage()
		}
	}

}
