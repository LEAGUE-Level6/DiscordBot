package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.w3c.dom.events.EventTarget;

import net.aksingh.owmjapis.api.APIException;

public class TriviaQuestions extends CustomMessageCreateListener {
String COMMAND = "!triviaStart";
String COMMAND2 = "!choose";
String COMMAND3 ="!select";
String[] questionList ;
String[] answerList;
	public TriviaQuestions(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains(COMMAND)) {
			event.getChannel().sendMessage("Welcome to TRIVIA! First, choose a category: !choose <Books, Movies, or Computers>.Then !select <answer>");
			
		}
		else if(event.getMessageContent().contains(COMMAND2)) {
			
			if(event.getMessageContent().trim().substring(8,event.getMessageContent().length()).equals("Books")){
				for (int i = 0; i < answerList.length; i++) {
					event.getChannel().sendMessage(answerList[i]);
					
				}
			}
			else if(event.getMessageContent().trim().substring(8,event.getMessageContent().length()).equals("Movies")) {
				
			}
			else if(event.getMessageContent().trim().substring(8,event.getMessageContent().length()).equals("Computers")) {
				
			}
		}
	}

}
