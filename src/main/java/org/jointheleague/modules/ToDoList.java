package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class ToDoList extends CustomMessageCreateListener {

	private static final String COMMAND = "!list";
	ArrayList<String> assignments = new ArrayList<String>();
	boolean switchconfirm = false;
	

	public ToDoList(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Allows you to see what items on your list that still need to be done. You can add or remove any items on a list and prioritize items by swapping. (add [entity], remove [#], swap [#-#], clear)");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// TODO Auto-generated method stub
		String contents = event.getMessageContent();
		String[] cmds = contents.split(" ");
		if (contents.contains(COMMAND)) {
			if (cmds.length > 1 && cmds[1].equals("add")) { //adding by putting at very bottom of list
				if (cmds.length > 2) {
					int messageIndex = contents.indexOf("add") + 3;
					String addResponse = contents.substring(messageIndex);
					assignments.add(addResponse);
					event.getChannel().sendMessage("Assignment <"+addResponse+"> appended to your assignments list!");
				} else {
					event.getChannel().sendMessage("You must add a statement after 'add' to complete the command! You can add a statement and then a number afterwards to choose where to place the item.");
				}
			}
			else if(cmds.length >1 && cmds[1].equals("remove")) { //removing item on list
				if(cmds.length == 3) {
					int messageIndex = contents.indexOf("remove") + 7;
					String item = contents.substring(messageIndex);
					int index = Integer.parseInt(item);
					if(index>0 && index-1< assignments.size()) { //checks if the index is within the range of assignments
						
						event.getChannel().sendMessage("Assignment <"+assignments.get(index-1)+"> has been removed from your assignments list");
						assignments.remove(index-1);
					}
					
				} else {
					event.getChannel().sendMessage("You must add a number after 'remove' to choose which item to remove!");
				}
			}
			else if(cmds.length == 2 && cmds[1].equals("clear")) { //clearing list 
				assignments.clear();
				event.getChannel().sendMessage("List has been cleared!");
			}
			else if(cmds.length > 1 && cmds[1].equals("swap")) { //swapping tasks
				if(cmds.length >2) {
				int messageIndex = contents.indexOf("swap") + 5;
				String swap = contents.substring(messageIndex);
				String t1 = swap.substring(0, swap.indexOf('-'));
				String t2 = swap.replace(t1 + '-', "");
				int item1 = Integer.parseInt(t1);
				int item2 = Integer.parseInt(t2);
				if(item1 > 0 && item1-1 <assignments.size() && item2 > 0 && item2-1 <assignments.size() && item1 != item2) {
					String temp = assignments.get(item1-1);
					assignments.set(item1-1, assignments.get(item2-1));
					assignments.set(item2-1, temp);
					event.getChannel().sendMessage("Task #" + item1 + " and Task#" + item2 + " have been swapped!" );
				}
				} else {
					event.getChannel().sendMessage("Must input '#task1-#task2' after swap to choose which ones you want to switch!");
				}
			}
			
		else { // shows entire list
			for (int i = 0; i < assignments.size(); i++) {
				event.getChannel().sendMessage("Task #" + (i + 1) + ": " + assignments.get(i));
			}
		}
	}
	}
}
