package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class Poker extends CustomMessageCreateListener {

	public Poker(String channelName) {
		// TODO Auto-generated constructor stub
		super(channelName);
	}
	private static final String command = "!gamble";
	int balance=50;
	int wager;
	Random randCards=new Random();
	String[] cards=new String[52];
	String[] botAndMiddleCards=new String[5];
	int[] usedCards=new int[7];
	int playerCard1;
	int playerCard2;
	int middleCard1;
	int middleCard2;
	int middleCard3;
	int middleCard4;
	int middleCard5;
	int botCard1;
	int botCard2;
	int numberSameCards;
	boolean userCooporates=false;
	boolean isUsed=false;
	int highestCallChance=0;
	int botCallChance=0;
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		cards[0]="ace of spades";
		cards[1]="2 of spades";
		cards[2]="3 of spades";
		cards[3]="4 of spades";
		cards[4]="5 of spades";
		cards[5]="6 of spades";
		cards[6]="7 of spades";
		cards[7]="8 of spades";
		cards[8]="9 of spades";
		cards[9]="10 of spades";
		cards[10]="jack of spades";
		cards[11]="queen of spades";
		cards[12]="king of spades";
		cards[13]="ace of hearts";
		cards[14]="2 of hearts";
		cards[15]="3 of hearts";
		cards[16]="4 of hearts";
		cards[17]="5 of hearts";
		cards[18]="6 of hearts";
		cards[19]="7 of hearts";
		cards[20]="8 of hearts";
		cards[21]="9 of hearts";
		cards[22]="10 of hearts";
		cards[23]="jack of hearts";
		cards[24]="queen of hearts";
		cards[25]="king of hearts";
		cards[26]="ace of diamonds";
		cards[27]="2 of diamonds";
		cards[28]="3 of diamonds";
		cards[29]="4 of diamonds";
		cards[30]="5 of diamonds";
		cards[31]="6 of diamonds";
		cards[32]="7 of diamonds";
		cards[33]="8 of diamonds";
		cards[34]="9 of diamonds";
		cards[35]="10 of diamonds";
		cards[36]="jack of diamonds";
		cards[37]="queen of diamonds";
		cards[38]="king of diamonds";
		cards[39]="ace of clubs";
		cards[40]="2 of clubs";
		cards[41]="3 of clubs";
		cards[42]="4 of clubs";
		cards[43]="5 of clubs";
		cards[44]="6 of clubs";
		cards[45]="7 of clubs";
		cards[46]="8 of clubs";
		cards[47]="9 of clubs";
		cards[48]="10 of clubs";
		cards[49]="jack of clubs";
		cards[50]="queen of clubs";
		cards[51]="king of clubs";
		if(event.getMessageContent().contains(command)) {
			String content = event.getMessageContent().replaceAll(" ", "").replace("!gamble","");
			if(content.contains("poker")) {
				content=content.replace("poker", "");
				try {
				wager=Integer.parseInt(content);
				}
				catch(Exception e){
					event.getChannel().sendMessage("Choose a number after the command to gamble");
				}
				balance-=wager;
				if(balance<0) {
					event.getChannel().sendMessage("You don't have enough money to wager that much.");
				}
				else {
					event.getChannel().sendMessage("Your balance is "+balance);
					//send photos of cards in the middle, its only text currently
					middleCard1=randCards.nextInt(52);
					usedCards[0]=middleCard1;
					middleCard2=randCards.nextInt(52);
					for(int i=0; i<usedCards.length; i++) {
					if(usedCards[i]==middleCard2) {
						isUsed=true;
					
					}
					while(isUsed==true) {
						middleCard2=randCards.nextInt(52);
						isUsed=false;
					}
					}
					usedCards[1]=middleCard2;
					middleCard3=randCards.nextInt(52);
					for(int i=0; i<usedCards.length; i++) {
						if(usedCards[i]==middleCard3) {
							isUsed=true;
						
						}
						while(isUsed==true) {
							middleCard3=randCards.nextInt(52);
							isUsed=false;
						}
						}
					usedCards[2]=middleCard3;
					event.getChannel().sendMessage("The middle cards are the "+ cards[middleCard1]+", the "+cards[middleCard2]+" and the "+cards[middleCard3]);
					
					//send photo of player cards, its only text currently
					playerCard1=randCards.nextInt(52);
					for(int i=0; i<usedCards.length; i++) {
						if(usedCards[i]==playerCard1) {
							isUsed=true;
						
						}
						while(isUsed==true) {
							playerCard1=randCards.nextInt(52);
							isUsed=false;
						}
						}
					usedCards[3]=playerCard1;
					playerCard2=randCards.nextInt(52);
					for(int i=0; i<usedCards.length; i++) {
						if(usedCards[i]==playerCard2) {
							isUsed=true;
						
						}
						while(isUsed==true) {
							playerCard2=randCards.nextInt(52);
							isUsed=false;
						}
						}
					usedCards[4]=playerCard2;
					event.getChannel().sendMessage("Your cards are the "+ cards[playerCard1]+" and the "+cards[playerCard2]);
					
					//bot logic
					botCard1=randCards.nextInt(52);
					for(int i=0; i<usedCards.length; i++) {
						if(usedCards[i]==botCard1) {
							isUsed=true;
						
						}
						while(isUsed==true) {
							botCard1=randCards.nextInt(52);
							isUsed=false;
						}
						}
					usedCards[5]=botCard1;
					botCard2=randCards.nextInt(52);
					for(int i=0; i<usedCards.length; i++) {
						if(usedCards[i]==botCard2) {
							isUsed=true;
						
						}
						while(isUsed==true) {
							botCard2=randCards.nextInt(52);
							isUsed=false;
						}
						}
					usedCards[6]=botCard2;
					
					//shows bot's cards
					event.getChannel().sendMessage("Temporary: Bot's cards are the "+ cards[botCard1]+" and the "+cards[botCard2]);
					
					
					if(cards[middleCard1].contains("ace")) {
						botAndMiddleCards[0]="ace";
					}
					else if(cards[middleCard1].contains("2")) {
						botAndMiddleCards[0]="2";
					}
					else if(cards[middleCard1].contains("3")) {
						botAndMiddleCards[0]="3";
					}
					else if(cards[middleCard1].contains("4")) {
						botAndMiddleCards[0]="4";
					}
					else if(cards[middleCard1].contains("5")) {
						botAndMiddleCards[0]="5";
					}
					else if(cards[middleCard1].contains("6")) {
						botAndMiddleCards[0]="6";
					}
					else if(cards[middleCard1].contains("7")) {
						botAndMiddleCards[0]="7";
					}
					else if(cards[middleCard1].contains("8")) {
						botAndMiddleCards[0]="8";
					}
					else if(cards[middleCard1].contains("9")) {
						botAndMiddleCards[0]="9";
					}
					else if(cards[middleCard1].contains("10")) {
						botAndMiddleCards[0]="10";
					}
					else if(cards[middleCard1].contains("jack")) {
						botAndMiddleCards[0]="jack";
					}
					else if(cards[middleCard1].contains("queen")) {
						botAndMiddleCards[0]="queen";
					}
					else if(cards[middleCard1].contains("king")) {
						botAndMiddleCards[0]="king";
					}
					
					if(cards[middleCard2].contains("ace")) {
					botAndMiddleCards[1]="ace";
					}
					else if(cards[middleCard2].contains("2")) {
						botAndMiddleCards[1]="2";
					}
					else if(cards[middleCard2].contains("3")) {
						botAndMiddleCards[1]="3";
					}
					else if(cards[middleCard2].contains("4")) {
						botAndMiddleCards[1]="4";
					}
					else if(cards[middleCard2].contains("5")) {
						botAndMiddleCards[1]="5";
					}
					else if(cards[middleCard2].contains("6")) {
						botAndMiddleCards[1]="6";
					}
					else if(cards[middleCard2].contains("7")) {
						botAndMiddleCards[1]="7";
					}
					else if(cards[middleCard2].contains("8")) {
						botAndMiddleCards[1]="8";
					}
					else if(cards[middleCard2].contains("9")) {
						botAndMiddleCards[1]="9";
					}
					else if(cards[middleCard2].contains("10")) {
						botAndMiddleCards[1]="10";
					}
					else if(cards[middleCard2].contains("jack")) {
						botAndMiddleCards[1]="jack";
					}
					else if(cards[middleCard2].contains("queen")) {
						botAndMiddleCards[1]="queen";
					}
					else if(cards[middleCard2].contains("king")) {
						botAndMiddleCards[1]="king";
					}
					if(cards[middleCard3].contains("ace")) {
						botAndMiddleCards[2]="ace";
					}
					else if(cards[middleCard3].contains("2")) {
						botAndMiddleCards[2]="2";
					}
					else if(cards[middleCard3].contains("3")) {
						botAndMiddleCards[2]="3";
					}
					else if(cards[middleCard3].contains("4")) {
						botAndMiddleCards[2]="4";
					}
					else if(cards[middleCard3].contains("5")) {
						botAndMiddleCards[2]="5";
					}
					else if(cards[middleCard3].contains("6")) {
						botAndMiddleCards[2]="6";
					}
					else if(cards[middleCard3].contains("7")) {
						botAndMiddleCards[2]="7";
					}
					else if(cards[middleCard3].contains("8")) {
						botAndMiddleCards[2]="8";
					}
					else if(cards[middleCard3].contains("9")) {
						botAndMiddleCards[2]="9";
					}
					else if(cards[middleCard3].contains("10")) {
						botAndMiddleCards[2]="10";
					}
					else if(cards[middleCard3].contains("jack")) {
						botAndMiddleCards[2]="jack";
					}
					else if(cards[middleCard3].contains("queen")) {
						botAndMiddleCards[2]="queen";
					}
					else if(cards[middleCard3].contains("king")) {
						botAndMiddleCards[2]="king";
					}
					
					if(cards[botCard1].contains("ace")) {
						botAndMiddleCards[3]="ace";
					}
					else if(cards[botCard1].contains("2")) {
						botAndMiddleCards[3]="2";
					}
					else if(cards[botCard1].contains("3")) {
						botAndMiddleCards[3]="3";
					}
					else if(cards[botCard1].contains("4")) {
						botAndMiddleCards[3]="4";
					}
					else if(cards[botCard1].contains("5")) {
						botAndMiddleCards[3]="5";
					}
					else if(cards[botCard1].contains("6")) {
						botAndMiddleCards[3]="6";
					}
					else if(cards[botCard1].contains("7")) {
						botAndMiddleCards[3]="7";
					}
					else if(cards[botCard1].contains("8")) {
						botAndMiddleCards[3]="8";
					}
					else if(cards[botCard1].contains("9")) {
						botAndMiddleCards[3]="9";
					}
					else if(cards[botCard1].contains("10")) {
						botAndMiddleCards[3]="10";
					}
					else if(cards[botCard1].contains("jack")) {
						botAndMiddleCards[3]="jack";
					}
					else if(cards[botCard1].contains("queen")) {
						botAndMiddleCards[3]="queen";
					}
					else if(cards[botCard1].contains("king")) {
						botAndMiddleCards[3]="king";
					}
					
					if(cards[botCard2].contains("ace")) {
						botAndMiddleCards[4]="ace";
					}
					else if(cards[botCard2].contains("2")) {
						botAndMiddleCards[4]="2";
					}
					else if(cards[botCard2].contains("3")) {
						botAndMiddleCards[4]="3";
					}
					else if(cards[botCard2].contains("4")) {
						botAndMiddleCards[4]="4";
					}
					else if(cards[botCard2].contains("5")) {
						botAndMiddleCards[4]="5";
					}
					else if(cards[botCard2].contains("6")) {
						botAndMiddleCards[4]="6";
					}
					else if(cards[botCard2].contains("7")) {
						botAndMiddleCards[4]="7";
					}
					else if(cards[botCard2].contains("8")) {
						botAndMiddleCards[4]="8";
					}
					else if(cards[botCard2].contains("9")) {
						botAndMiddleCards[4]="9";
					}
					else if(cards[botCard2].contains("10")) {
						botAndMiddleCards[4]="10";
					}
					else if(cards[botCard2].contains("jack")) {
						botAndMiddleCards[4]="jack";
					}
					else if(cards[botCard2].contains("queen")) {
						botAndMiddleCards[4]="queen";
					}
					else if(cards[botCard2].contains("king")) {
						botAndMiddleCards[4]="king";
					}
					event.getChannel().sendMessage("The bot has "+botAndMiddleCards[0]+botAndMiddleCards[1]+botAndMiddleCards[2]+botAndMiddleCards[3]+botAndMiddleCards[4]);
					
					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="ace") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(100>highestCallChance) {
							highestCallChance=100;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}
					
					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="2") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(70>highestCallChance) {
							highestCallChance=70;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}
					
					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="3") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(75>highestCallChance) {
							highestCallChance=75;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="4") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(75>highestCallChance) {
							highestCallChance=75;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="5") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(80>highestCallChance) {
							highestCallChance=80;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="6") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(80>highestCallChance) {
							highestCallChance=80;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="7") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(85>highestCallChance) {
							highestCallChance=85;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="8") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(85>highestCallChance) {
							highestCallChance=85;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="9") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(85>highestCallChance) {
							highestCallChance=85;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="10") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(90>highestCallChance) {
							highestCallChance=90;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="jack") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(90>highestCallChance) {
							highestCallChance=90;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="queen") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(95>highestCallChance) {
							highestCallChance=95;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}

					numberSameCards=0;
					for(int j=0; j<botAndMiddleCards.length; j++) {
						if(botAndMiddleCards[j]=="king") {
							numberSameCards+=1;
						}
					}
					if(numberSameCards==2) {
						if(95>highestCallChance) {
							highestCallChance=95;
						}
					}
					else if(numberSameCards==3 || numberSameCards==4) {
						highestCallChance=100;
					}
					
					
					//add more ways of having good hand here
					
					if(50>highestCallChance) {
						highestCallChance=50;
					}
					botCallChance=highestCallChance;
					Random callRandom=new Random();
					int i=callRandom.nextInt(100);
					if(botCallChance>=i) {
						//bot calls
						event.getChannel().sendMessage("Bot calls. Bot chance of calling was "+botCallChance+". The random was "+i);
					}
					else {
						balance+=(wager*2);
						event.getChannel().sendMessage("The bot folded, you win "+wager*2+". Your balance is now "+balance);
					}
					//in the future maybe add a choice to add players
				}

			}
			//I don't know why this while loop doesn't work
			System.out.println(userCooporates);
			while(userCooporates=false) {
					event.getChannel().sendMessage("Would you like to check, raise, or fold");
					userCooporates=true;
					if(event.getMessageContent().contains("check")) {
						
					}
					else if(event.getMessageContent().contains("raise")) {
						
					}
					else if(event.getMessageContent().contains("fold")) {
						
					}
					else {
						userCooporates=false;
					}
				}
		}
			}
		}



