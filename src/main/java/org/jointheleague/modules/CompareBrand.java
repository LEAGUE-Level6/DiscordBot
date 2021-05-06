package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class CompareBrand extends CustomMessageCreateListener {
	Car[] cars = {new Car("Acura ILX", 5, 201, 26100), new Car("Acura MDX", 7, 2090, 46900), new Car("Acura TLX", 5, 272, 37500), new Car("Acura RDX", 5, 272, 38400), 
				   new Car("Alfa Romeo Stelvio", 5, 280, 42350), new Car("Alfa Romeo Giulia", 5, 280, 42350),
				   new Car("Audi Q3", 5, 228, 34000), new Car("Audi Q5", 5, 362, 43300), new Car("Audi Q7", 7, 335, 54950), new Car("Audi Q8", 5, 335, 68200), new Car("Audi E-Tron", 5, 402, 65900),  new Car("Audi A3", 5, 184, 33300), new Car("Audi A4", 5, 261, 39100), new Car("Audi A5", 5, 261, 44000), new Car("Audi A6", 5, 335, 54900), new Car("Audi A7", 5, 335, 69200), new Car("Audi A8", 5, 453, 86500),
				   new Car("BMW X1", 5, 228, 35400), new Car("BMW X2", 5, 228, 36600), new Car("BMW X3", 5, 248, 43000), new Car("BMW X4", 5, 245, 51600), new Car("BMW X5", 5, 335, 59400), new Car("BMW X6", 5, 335, 65050), new Car("BMW X7", 5, 335, 74900), new Car("BMW 2", 5, 228, 35900)
				   };
	String possibilities = "";
	private static final String COMMAND = "/compare";
	public CompareBrand(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Type /compare and followed by budget/seats/horsepower followed by a parameter. you can do all 3 at once. Ex: /compare horsepower 2 seats 1 budget 200000");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String[] str = event.getMessageContent().split(" ");
		int budget = 0;
		boolean hasBudget = false;
		int seats = 0;
		boolean hasSeats = false;
		int horsepower = 0;
		boolean hasHorsies = false;
		if (str[0].equals(COMMAND)) {
			for (int i = 0; i < str.length; i++) {
				if (str[i].contains("budget")) {
					budget = Integer.parseInt(str[i + 1]);
					hasBudget = true;
				} else if (str[i].contains("seats")) {
					seats = Integer.parseInt(str[i + 1]);
					hasSeats = true;
				} else if (str[i].contains("horsepower")) {
					horsepower = Integer.parseInt(str[i + 1]);
					hasHorsies = true;
				}
			}
		}
		boolean hpMet = false;
		boolean budgetMet = false;
		boolean seatsMet = false;
		for (int i = 0; i < cars.length; i++) {
			if (hasBudget) {
				budgetMet = cars[i].checkPrice(budget);
			}
			if (hasHorsies) {
				hpMet = cars[i].checkHp(horsepower);
			}
			if (hasSeats) {
				seatsMet = cars[i].checkSeats(seats);
			}
				
				if ((budgetMet || !hasBudget) && (hpMet || !hasHorsies) && (seatsMet || !hasSeats)) {
					event.getChannel().sendMessage(cars[i] + " contends");
				}
		}

	}
}
// TODO: Find 2021 car brand models and write down a few's seats, and
// horsepower, cost and put into a text file

class Car {
	int seats = 0;
	int hp = 0;
	int price = 0;
	String name = "";
	Car(String name, int seats, int horsePower, int price) {
		this.seats = seats;
		hp = horsePower;
		this.price = price;
		this.name = name;
	}

	public boolean checkHp(int hpLimit) {
		if (hp <= hpLimit) {
			return true;
		}
		return false;
	}

	public boolean checkSeats(int seatLimit) {
		if (seats <= seatLimit) {
			return true;
		}
		return false;
	}

	public boolean checkPrice(int priceLimit) {
		if (price <= priceLimit) {
			return true;
		}
		return false;
	}
}