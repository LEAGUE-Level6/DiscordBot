package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class IceBreakerListener extends CustomMessageCreateListener{
	public IceBreakerListener(String channelName) throws IOException {
		super(channelName);	
		
		//creating question words
		List<String> question = Arrays.asList("Who", "What" , "Where", "When" , "Why" );
		//creating nouns
		ArrayList<String> noun = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/jointheleague/modules/nouns"));
			String x = br.readLine();
			while(x !=null){
				noun.add(x);
				x = br.readLine();
			}
			br.close();
			for(int i = 0; i<noun.size();i++) {
				System.out.println(noun.get(i));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().startsWith("!icebreaker")) {
			
		}
	}

}
