package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class ToDoList extends CustomMessageCreateListener{

	
	final String addCommand = "!listadd";
	final String viewCommand = "!listview";
	final String removeCommand = "!listremove";
	final String helpCommand = "!listhelp";
	ArrayList<String> toDoList;
	Scanner scan;
		
	public ToDoList(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		
		toDoList = new ArrayList<String>();
		
		
	}
	
	public String showList(){

		String list = "Here is your current To Do List:\n";
		for (int i = 0; i < toDoList.size(); i++) {
			list += (i+1 + ". " + toDoList.get(i));
			list += "\n";
		}
		
		return list;
		
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		
		if(event.getMessageContent().contains(helpCommand)) {
			event.getChannel().sendMessage("All To Do List commands (following '!') :");
			event.getChannel().sendMessage("listadd - adds a task to the To Do List. (add task: ex. 'listadd buy coffee' adds 'buy coffee')");
			event.getChannel().sendMessage("listremove - removes a task from the To Do List. (add task #: ex. 'listremove 1' removes '1.' in To Do List)");
			event.getChannel().sendMessage("listview - shows the current To Do List.");
		}
		
		if(event.getMessageContent().contains(addCommand)) {
			String task = event.getMessageContent().replace(addCommand, "");
			if(task.trim().equals("")) {
				event.getChannel().sendMessage("Please include a task for your To Do List folling the add command.");
			}else {
				toDoList.add(task);
				event.getChannel().sendMessage("The task has been added to your To Do List at (" + (toDoList.indexOf(task)+1) + ". " + task + ").");
			}
			
			
		}
		
		if(event.getMessageContent().contains(viewCommand)) {
			event.getChannel().sendMessage(showList());
		}
		
		if(event.getMessageContent().contains(removeCommand)) {
			System.out.println("ran remove");
			String remove = event.getMessageContent().replace(removeCommand, "");
			if(remove.trim().equals("")) {
				event.getChannel().sendMessage("Please include the list number (ex. 2, 3, 7) of the task you'd like to remove following the remove command.");
			}else {
			scan = new Scanner(remove);
			int taskNum = scan.nextInt();
			if(taskNum <= toDoList.size() && taskNum >= 1) {
				event.getChannel().sendMessage("The task at '" + taskNum + ".' (" + toDoList.get(taskNum-1)+ ") has been removed from your To Do List.");
				toDoList.remove(taskNum-1);
			}else {
				event.getChannel().sendMessage("There is no task at this number on your To Do List. Please enter a valid number.");
				event.getChannel().sendMessage("Ex. If your To Do List includes '1.', '2.', and '3.', then 1, 2, and 3 are valid numbers.");
			}
			}
		}
		
	}

}
