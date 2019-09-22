package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class CollaborativeText extends CustomMessageCreateListener{
	
	private static final String COMMAND3 = "!storystart";
	private static final String COMMAND2 = "!storydelete";
	private static final String COMMAND1 = "!storyedit";
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> body = new ArrayList<String>();
	
	public CollaborativeText(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND1)) {
			String toBeEdited = event.getMessageContent().substring(0,event.getMessageContent().indexOf(COMMAND1)-1);
			if(name.contains(toBeEdited)) {
				int i = name.indexOf(toBeEdited);
				body.set(i, body.get(i) + event.getMessageContent().substring(event.getMessageContent().indexOf(COMMAND1)+COMMAND1.length()));
				event.getChannel().sendMessage(name.get(i) + " so far: \n" + body.get(i));
			}else {
				event.getChannel().sendMessage("Sorry, that story could not be found");
			}
		}
		if (event.getMessageContent().contains(COMMAND2)) {
			String toBeRemoved = event.getMessageContent().substring( event.getMessageContent().indexOf('!')+13);
			if(name.contains(toBeRemoved)) {
				int i = name.indexOf(toBeRemoved);
				name.remove(i);
				body.remove(i);
				event.getChannel().sendMessage("That story has been removed.");
			}else {
				event.getChannel().sendMessage("Sorry, that story could not be found");
			}
		}
		if (event.getMessageContent().contains(COMMAND3)) {
			if(name.contains(event.getMessageContent().substring( event.getMessageContent().indexOf('!')+12)) == false) {
				name.add(event.getMessageContent().substring( event.getMessageContent().indexOf('!')+12));
				body.add("");
				event.getChannel().sendMessage("Story called " + event.getMessageContent().substring( event.getMessageContent().indexOf('!')+12) + " has been created!");
			}else {
				event.getChannel().sendMessage("That name is taken.");
			}			
		}
	}



}
