package org.jointheleague.modules;

import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class MoreThan7LettersListener extends CustomMessageCreateListener{

	public MoreThan7LettersListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageAuthor().asUser().get().isBot()){
			return; //stops the bot from reading its own messages
		}
		String[] words = event.getMessageContent() //gets the message
				.replaceAll("[^ a-zA-Z0-9]", "") //removes all punctuation
				.split(" ");
		//gets longest word
		String longestWord = getFirstLongWord(words); //if there is no word longer than 7 letters, it will return "."
		if (longestWord.contentEquals("."))
		{
			// there is no word longer than 7 letters
			return;
		}
		//longest word is longer than 7 letters
		String message = "Wow, that is a long word. I don't even know what " +  longestWord +
				" means. You should teach me to be a smart AI that can take over the world";
		CompletableFuture<Message> waiter = event.getChannel().sendMessage(message); //sending message
		
		try {
			waiter.get(); // waits for the message to be sent
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String getFirstLongWord(String[] words)
	{
		String longestWord = ".";
		for (int i = 0; i < words.length; i++)
		{
			if (words[i].length() >= 7 && words[i].length() > longestWord.length()) //checks to see if it is the longest word
			{		
				longestWord = words[i]; //sets it to replace the longest word
			}
		}
		return longestWord;
	}
}
