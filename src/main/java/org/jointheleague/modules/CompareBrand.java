package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class CompareBrand extends CustomMessageCreateListener {

	private static final String COMMAND = "/compare";
	String[][] cars = {{"Acura ILX", "5", "201", "26100"}, {"Acura MDX", "7", "290", "46900"}, {"Acura TLX", "5", "272", "37500"}, {"Acura RDX", "5", "272", "38400"}, 
					   {"Alfa Romeo Stelvio", "5", "280", "42350"}, {"Alfa Romeo Gulia", "5", "280", "39400"},
					   {"Audi Q3", "5", "228", "34000"}, {"Audi Q5", "5", "362", "43300"}, {"Audi Q7", "7", "335", "54950"}, {"Audi Q8", "5", "335", "68200"}, {"Audi E-tron", "5 ", "402", "65900"}, {"Audi A3", "5", "184", "33300"}, {"Audi A4", "5", "261", "39100"}, {"Audi A5", "5", "261, '44000"}, {"Audi A6", "5", "335", "54900"}, {"Audi A7", "5", "335", "69200"}, {"Audi A8", "5", "453", "86500"},
					   {"BMW X1", "5", "228", "35400"}, {"BMW X2", "5", "228", "36600"}, {"BMW X3", "5", "248", "43000"}, {"BMW X4", "5", "245", "51600"}, {"BMW X5", "5", "335", "59400"}, {"BMW X6", "5", "335", "65050"}, {"BMW X7", "7", "335", "74900"}, {"BMW 2", "5", "228", "35900"}};
	public CompareBrand(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Type /compare and followed by budget/seats/horsepower followed by a parameter. you can do all 3 at once. Ex: /compare horsepower 2 seats 1 budget 200000");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String[] str = event.getMessageContent().split(" ");
		int budget;
		int seats;
		int horsepower;
		if(str[0].equals(COMMAND));
		event.getChannel().sendMessage("hi");
		for(int i = 0; i < str.length; i++) {
			if(str[i].contains("budget")) {
				budget = Integer.parseInt(str[i + 1]);
			} else if(str[i].contains("seats")) {
				seats = Integer.parseInt(str[i + 1]);
			} else if(str[i].contains("horsepower")) {
				horsepower = Integer.parseInt(str[i + 1]);
			}
			
		}
		
	}

}
// TODO: Find 2021 car brand models and write down a few's  seats, and horsepower, cost and put into a text file

