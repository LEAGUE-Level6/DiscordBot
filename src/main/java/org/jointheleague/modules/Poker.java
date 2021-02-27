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
	boolean userCooporates=false;
	boolean isUsed=false;
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
					//in the future maybe add a choice to add players
				}
				}
				catch(Exception e){
					event.getChannel().sendMessage("Choose a number after the command to gamble");
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



