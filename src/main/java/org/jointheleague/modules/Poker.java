package org.jointheleague.modules;

import java.lang.reflect.Array;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class Poker extends CustomMessageCreateListener {

	public Poker(String channelName) {
		// TODO Auto-generated constructor stub
		super(channelName);
		helpEmbed = new HelpEmbed("!gamble poker", "This command starts a new poker game. You add a number after this command as the starting wager. The command !gamble bet with a number after it as the next wager continues the game. You can fold by using !gamble fold. If you run out of money, you can use !gamble work with a number after it to gain money in your balance.");
	}

	private static final String command = "!gamble";
	int balance = 50;
	int wager;
	int totalBet;
	int payment;
	Random randCards = new Random();
	String[] cards = new String[52];
	String[] botAndMiddleCards = new String[7];
	String[] playerAndMiddleCards=new String[7];
	int[] usedCards = new int[9];
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
	int playerScore;
	int botScore;
	int numberPairs;
	int highestPairValue;
	int lowestPairValue;
	int highestTripleValue;
	int playerPairValue;
	int botPairValue;
	int playerLowPair;
	int botLowPair;
	int playerHighCard;
	int playerSecondHighCard;
	int playerThirdHighCard;
	int playerFourthHighCard;
	int playerFifthHighCard;
	int botHighCard;
	int botSecondHighCard;
	int botThirdHighCard;
	int botFourthHighCard;
	int botFifthHighCard;
	boolean triple;
	boolean userCooporates = false;
	boolean isUsed = false;
	boolean flop = true;
	boolean turn = true;
	boolean river=true;
	boolean gameOver = true;
	boolean playerWon;
	boolean rewardGiven;
	boolean tie=false;
	boolean straight;
	boolean number;
	int highestCallChance = 0;
	int botCallChance = 0;
	int botSpades;
	int botDiamonds;
	int botHearts;
	int botClubs;
	int playerSpades;
	int playerDiamonds;
	int playerHearts;
	int playerClubs;
	int highestSuit;
	int highCard;
	int secondHighCard;
	int thirdHighCard;
	int fourthHighCard;
	int fifthHighCard;
	int highStraightCard;
	boolean ace;
	boolean two;
	boolean three;
	boolean four;
	boolean five;
	boolean six;
	boolean seven;
	boolean eight;
	boolean nine;
	boolean ten;
	boolean jack;
	boolean queen;
	boolean king;

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		cards[0] = "ace of spades";
		cards[1] = "2 of spades";
		cards[2] = "3 of spades";
		cards[3] = "4 of spades";
		cards[4] = "5 of spades";
		cards[5] = "6 of spades";
		cards[6] = "7 of spades";
		cards[7] = "8 of spades";
		cards[8] = "9 of spades";
		cards[9] = "10 of spades";
		cards[10] = "jack of spades";
		cards[11] = "queen of spades";
		cards[12] = "king of spades";
		cards[13] = "ace of hearts";
		cards[14] = "2 of hearts";
		cards[15] = "3 of hearts";
		cards[16] = "4 of hearts";
		cards[17] = "5 of hearts";
		cards[18] = "6 of hearts";
		cards[19] = "7 of hearts";
		cards[20] = "8 of hearts";
		cards[21] = "9 of hearts";
		cards[22] = "10 of hearts";
		cards[23] = "jack of hearts";
		cards[24] = "queen of hearts";
		cards[25] = "king of hearts";
		cards[26] = "ace of diamonds";
		cards[27] = "2 of diamonds";
		cards[28] = "3 of diamonds";
		cards[29] = "4 of diamonds";
		cards[30] = "5 of diamonds";
		cards[31] = "6 of diamonds";
		cards[32] = "7 of diamonds";
		cards[33] = "8 of diamonds";
		cards[34] = "9 of diamonds";
		cards[35] = "10 of diamonds";
		cards[36] = "jack of diamonds";
		cards[37] = "queen of diamonds";
		cards[38] = "king of diamonds";
		cards[39] = "ace of clubs";
		cards[40] = "2 of clubs";
		cards[41] = "3 of clubs";
		cards[42] = "4 of clubs";
		cards[43] = "5 of clubs";
		cards[44] = "6 of clubs";
		cards[45] = "7 of clubs";
		cards[46] = "8 of clubs";
		cards[47] = "9 of clubs";
		cards[48] = "10 of clubs";
		cards[49] = "jack of clubs";
		cards[50] = "queen of clubs";
		cards[51] = "king of clubs";
		if (event.getMessageContent().contains(command)) {
			String content = event.getMessageContent().replaceAll(" ", "").replace("!gamble", "");
			if (content.contains("poker")) {
				number=false;
				content = content.replace("poker", "");
				try {
					wager = Integer.parseInt(content);
					number=true;
				} catch (Exception e) {
					event.getChannel().sendMessage("Choose a number after the command to gamble");
					return;
				}
				if (gameOver == true) {
					gameOver = false;
					flop=true;
					turn=true;
					river=true;
					rewardGiven=false;
					totalBet = 0;
					highestCallChance=0;
					botCallChance=0;
					numberPairs=0;
					triple=false;
					usedCards[0]=-1;
					usedCards[1]=-1;
					usedCards[2]=-1;
					usedCards[3]=-1;
					usedCards[4]=-1;
					usedCards[5]=-1;
					usedCards[6]=-1;
					usedCards[7]=-1;
					usedCards[8]=-1;
					botAndMiddleCards[0]="";
					botAndMiddleCards[1]="";
					botAndMiddleCards[2]="";
					botAndMiddleCards[3]="";
					botAndMiddleCards[4]="";
					botAndMiddleCards[5]="";
					botAndMiddleCards[6]="";
					playerAndMiddleCards[0]="";
					playerAndMiddleCards[1]="";
					playerAndMiddleCards[2]="";
					playerAndMiddleCards[3]="";
					playerAndMiddleCards[4]="";
					playerAndMiddleCards[5]="";
					playerAndMiddleCards[6]="";
					botSpades=0;
					botDiamonds=0;
					botHearts=0;
					botClubs=0;
					playerSpades=0;
					playerDiamonds=0;
					playerHearts=0;
					playerClubs=0;
					
					if (balance-wager < 0) {
						event.getChannel().sendMessage("You don't have enough money to wager that much.");
						gameOver=true;
					} else if (wager<0) {
						event.getChannel().sendMessage("Choose a positive number");
						gameOver=true;
					}
					else {
						balance -= wager;
						totalBet += wager;
						event.getChannel().sendMessage("Your balance is " + balance);
						// send photos of cards in the middle, its only text currently
						middleCard1 = randCards.nextInt(52);
						usedCards[0] = middleCard1;
						middleCard2 = randCards.nextInt(52);
						for (int i = 0; i < usedCards.length; i++) {
							if (usedCards[i] == middleCard2) {
								isUsed = true;

							}
							while (isUsed == true) {
								middleCard2 = randCards.nextInt(52);
								isUsed = false;
							}
						}
						usedCards[1] = middleCard2;
						middleCard3 = randCards.nextInt(52);
						for (int i = 0; i < usedCards.length; i++) {
							if (usedCards[i] == middleCard3) {
								isUsed = true;

							}
							while (isUsed == true) {
								middleCard3 = randCards.nextInt(52);
								isUsed = false;
							}
						}
						usedCards[2] = middleCard3;
						event.getChannel().sendMessage("The middle cards are the " + cards[middleCard1] + ", the "
								+ cards[middleCard2] + " and the " + cards[middleCard3]);

						// send photo of player cards, its only text currently
						playerCard1 = randCards.nextInt(52);
						for (int i = 0; i < usedCards.length; i++) {
							if (usedCards[i] == playerCard1) {
								isUsed = true;

							}
							while (isUsed == true) {
								playerCard1 = randCards.nextInt(52);
								isUsed = false;
							}
						}
						usedCards[3] = playerCard1;
						playerCard2 = randCards.nextInt(52);
						for (int i = 0; i < usedCards.length; i++) {
							if (usedCards[i] == playerCard2) {
								isUsed = true;

							}
							while (isUsed == true) {
								playerCard2 = randCards.nextInt(52);
								isUsed = false;
							}
						}
						usedCards[4] = playerCard2;
						event.getChannel().sendMessage(
								"Your cards are the " + cards[playerCard1] + " and the " + cards[playerCard2]);

						botCard1 = randCards.nextInt(52);
						for (int i = 0; i < usedCards.length; i++) {
							if (usedCards[i] == botCard1) {
								isUsed = true;

							}
							while (isUsed == true) {
								botCard1 = randCards.nextInt(52);
								isUsed = false;
							}
						}
						usedCards[5] = botCard1;
						botCard2 = randCards.nextInt(52);
						for (int i = 0; i < usedCards.length; i++) {
							if (usedCards[i] == botCard2) {
								isUsed = true;

							}
							while (isUsed == true) {
								botCard2 = randCards.nextInt(52);
								isUsed = false;
							}
						}
						usedCards[6] = botCard2;

						// shows bot's cards
						System.out.println("Temporary: Bot's cards are the " + cards[botCard1] + " and the " + cards[botCard2]);

						if (cards[middleCard1].contains("ace")) {
							botAndMiddleCards[0] = "ace";
						} else if (cards[middleCard1].contains("2")) {
							botAndMiddleCards[0] = "2";
						} else if (cards[middleCard1].contains("3")) {
							botAndMiddleCards[0] = "3";
						} else if (cards[middleCard1].contains("4")) {
							botAndMiddleCards[0] = "4";
						} else if (cards[middleCard1].contains("5")) {
							botAndMiddleCards[0] = "5";
						} else if (cards[middleCard1].contains("6")) {
							botAndMiddleCards[0] = "6";
						} else if (cards[middleCard1].contains("7")) {
							botAndMiddleCards[0] = "7";
						} else if (cards[middleCard1].contains("8")) {
							botAndMiddleCards[0] = "8";
						} else if (cards[middleCard1].contains("9")) {
							botAndMiddleCards[0] = "9";
						} else if (cards[middleCard1].contains("10")) {
							botAndMiddleCards[0] = "10";
						} else if (cards[middleCard1].contains("jack")) {
							botAndMiddleCards[0] = "jack";
						} else if (cards[middleCard1].contains("queen")) {
							botAndMiddleCards[0] = "queen";
						} else if (cards[middleCard1].contains("king")) {
							botAndMiddleCards[0] = "king";
						}
						if (cards[middleCard1].contains("spades")) {
							botSpades+=1;
						}
						else if(cards[middleCard1].contains("hearts")) {
							botHearts+=1;
						}
						else if(cards[middleCard1].contains("diamonds")) {
							botDiamonds+=1;
						}
						else if(cards[middleCard1].contains("clubs")) {
							botClubs+=1;
						}

						if (cards[middleCard2].contains("ace")) {
							botAndMiddleCards[1] = "ace";
						} else if (cards[middleCard2].contains("2")) {
							botAndMiddleCards[1] = "2";
						} else if (cards[middleCard2].contains("3")) {
							botAndMiddleCards[1] = "3";
						} else if (cards[middleCard2].contains("4")) {
							botAndMiddleCards[1] = "4";
						} else if (cards[middleCard2].contains("5")) {
							botAndMiddleCards[1] = "5";
						} else if (cards[middleCard2].contains("6")) {
							botAndMiddleCards[1] = "6";
						} else if (cards[middleCard2].contains("7")) {
							botAndMiddleCards[1] = "7";
						} else if (cards[middleCard2].contains("8")) {
							botAndMiddleCards[1] = "8";
						} else if (cards[middleCard2].contains("9")) {
							botAndMiddleCards[1] = "9";
						} else if (cards[middleCard2].contains("10")) {
							botAndMiddleCards[1] = "10";
						} else if (cards[middleCard2].contains("jack")) {
							botAndMiddleCards[1] = "jack";
						} else if (cards[middleCard2].contains("queen")) {
							botAndMiddleCards[1] = "queen";
						} else if (cards[middleCard2].contains("king")) {
							botAndMiddleCards[1] = "king";
						}
						if (cards[middleCard2].contains("spades")) {
							botSpades+=1;
						}
						else if(cards[middleCard2].contains("hearts")) {
							botHearts+=1;
						}
						else if(cards[middleCard2].contains("diamonds")) {
							botDiamonds+=1;
						}
						else if(cards[middleCard2].contains("clubs")) {
							botClubs+=1;
						}
						
						if (cards[middleCard3].contains("ace")) {
							botAndMiddleCards[2] = "ace";
						} else if (cards[middleCard3].contains("2")) {
							botAndMiddleCards[2] = "2";
						} else if (cards[middleCard3].contains("3")) {
							botAndMiddleCards[2] = "3";
						} else if (cards[middleCard3].contains("4")) {
							botAndMiddleCards[2] = "4";
						} else if (cards[middleCard3].contains("5")) {
							botAndMiddleCards[2] = "5";
						} else if (cards[middleCard3].contains("6")) {
							botAndMiddleCards[2] = "6";
						} else if (cards[middleCard3].contains("7")) {
							botAndMiddleCards[2] = "7";
						} else if (cards[middleCard3].contains("8")) {
							botAndMiddleCards[2] = "8";
						} else if (cards[middleCard3].contains("9")) {
							botAndMiddleCards[2] = "9";
						} else if (cards[middleCard3].contains("10")) {
							botAndMiddleCards[2] = "10";
						} else if (cards[middleCard3].contains("jack")) {
							botAndMiddleCards[2] = "jack";
						} else if (cards[middleCard3].contains("queen")) {
							botAndMiddleCards[2] = "queen";
						} else if (cards[middleCard3].contains("king")) {
							botAndMiddleCards[2] = "king";
						}
						if (cards[middleCard3].contains("spades")) {
							botSpades+=1;
						}
						else if(cards[middleCard3].contains("hearts")) {
							botHearts+=1;
						}
						else if(cards[middleCard3].contains("diamonds")) {
							botDiamonds+=1;
						}
						else if(cards[middleCard3].contains("clubs")) {
							botClubs+=1;
						}

						if (cards[botCard1].contains("ace")) {
							botAndMiddleCards[3] = "ace";
						} else if (cards[botCard1].contains("2")) {
							botAndMiddleCards[3] = "2";
						} else if (cards[botCard1].contains("3")) {
							botAndMiddleCards[3] = "3";
						} else if (cards[botCard1].contains("4")) {
							botAndMiddleCards[3] = "4";
						} else if (cards[botCard1].contains("5")) {
							botAndMiddleCards[3] = "5";
						} else if (cards[botCard1].contains("6")) {
							botAndMiddleCards[3] = "6";
						} else if (cards[botCard1].contains("7")) {
							botAndMiddleCards[3] = "7";
						} else if (cards[botCard1].contains("8")) {
							botAndMiddleCards[3] = "8";
						} else if (cards[botCard1].contains("9")) {
							botAndMiddleCards[3] = "9";
						} else if (cards[botCard1].contains("10")) {
							botAndMiddleCards[3] = "10";
						} else if (cards[botCard1].contains("jack")) {
							botAndMiddleCards[3] = "jack";
						} else if (cards[botCard1].contains("queen")) {
							botAndMiddleCards[3] = "queen";
						} else if (cards[botCard1].contains("king")) {
							botAndMiddleCards[3] = "king";
						}
						if (cards[botCard1].contains("spades")) {
							botSpades+=1;
						}
						else if(cards[botCard1].contains("hearts")) {
							botHearts+=1;
						}
						else if(cards[botCard1].contains("diamonds")) {
							botDiamonds+=1;
						}
						else if(cards[botCard1].contains("clubs")) {
							botClubs+=1;
						}

						if (cards[botCard2].contains("ace")) {
							botAndMiddleCards[4] = "ace";
						} else if (cards[botCard2].contains("2")) {
							botAndMiddleCards[4] = "2";
						} else if (cards[botCard2].contains("3")) {
							botAndMiddleCards[4] = "3";
						} else if (cards[botCard2].contains("4")) {
							botAndMiddleCards[4] = "4";
						} else if (cards[botCard2].contains("5")) {
							botAndMiddleCards[4] = "5";
						} else if (cards[botCard2].contains("6")) {
							botAndMiddleCards[4] = "6";
						} else if (cards[botCard2].contains("7")) {
							botAndMiddleCards[4] = "7";
						} else if (cards[botCard2].contains("8")) {
							botAndMiddleCards[4] = "8";
						} else if (cards[botCard2].contains("9")) {
							botAndMiddleCards[4] = "9";
						} else if (cards[botCard2].contains("10")) {
							botAndMiddleCards[4] = "10";
						} else if (cards[botCard2].contains("jack")) {
							botAndMiddleCards[4] = "jack";
						} else if (cards[botCard2].contains("queen")) {
							botAndMiddleCards[4] = "queen";
						} else if (cards[botCard2].contains("king")) {
							botAndMiddleCards[4] = "king";
						}
						if (cards[botCard2].contains("spades")) {
							botSpades+=1;
						}
						else if(cards[botCard2].contains("hearts")) {
							botHearts+=1;
						}
						else if(cards[botCard2].contains("diamonds")) {
							botDiamonds+=1;
						}
						else if(cards[botCard2].contains("clubs")) {
							botClubs+=1;
						}
						System.out.println("The bot has " + botAndMiddleCards[0]+", "+botAndMiddleCards[1]+", "
								+botAndMiddleCards[2]+", "+botAndMiddleCards[3]+", and "+botAndMiddleCards[4]);
						event.getChannel().sendMessage("Your hand is now the " +cards[playerCard1]+", "+cards[playerCard2]+", "+cards[middleCard1]+", "+cards[middleCard2]+", and "+cards[middleCard3]);
						event.getChannel().sendMessage("How much would you like to bet");
					}
				} 
			else {
					event.getChannel().sendMessage("You already have a game running.");
			}
			}
			else if(content.contains("fold") && !gameOver) {
				event.getChannel().sendMessage("You folded. Your balance is now "+balance);
				gameOver=true;
			}
				else if (content.contains("bet") && flop && !gameOver) {
					content = content.replace("bet", "");
					try {
						wager = Integer.parseInt(content);
					} catch (Exception e) {
						event.getChannel().sendMessage("Choose a number after the command to gamble");
						return;
					}
					System.out.println(totalBet);
					if (balance-wager < 0) {
						event.getChannel().sendMessage("You don't have enough money to wager that much.");
						gameOver=true;
					} else if(wager<0) {
						event.getChannel().sendMessage("Choose a positive number");
					}
					else {
						flop = false;
						balance -= wager;
						totalBet += wager;
						event.getChannel().sendMessage("Your balance is " + balance);
						highestSuit=0;
						highestSuit=botSpades;
						if(botDiamonds>highestSuit) {
							highestSuit=botDiamonds;
						}
						if(botHearts>highestSuit) {
							highestSuit=botHearts;
						}
						if(botSpades>highestSuit) {
							highestSuit=botSpades;
						}

						highestCallChance = botAlgorithm(botAndMiddleCards, highestSuit);


						if (500 > highestCallChance) {
							highestCallChance = 500;
						}
						botCallChance = highestCallChance;
						Random callRandom = new Random();
						int i = callRandom.nextInt(750);
						if (botCallChance >= i) {
							// bot calls
							System.out.println(
									"Bot calls. Bot chance of calling was " + botCallChance + ". The random was " + i);
							middleCard4 = randCards.nextInt(52);
							event.getChannel().sendMessage("Bot calls.");
							for (int k = 0; k < usedCards.length; k++) {
								if (usedCards[k] == middleCard4) {
									isUsed = true;

								}
								while (isUsed == true) {
									middleCard4 = randCards.nextInt(52);
									isUsed = false;
								}
							}
							usedCards[7] = middleCard4;
							event.getChannel()
									.sendMessage("The middle cards are now the " + cards[middleCard1] + ", the "
											+ cards[middleCard2] + ", the " + cards[middleCard3] + " and the "
											+ cards[middleCard4]);
							event.getChannel().sendMessage("Your hand is now the " +cards[playerCard1]+", "+cards[playerCard2]+", "+cards[middleCard1]+", "+cards[middleCard2]+", "+cards[middleCard3]+", and "+cards[middleCard4]);

							if (cards[middleCard4].contains("ace")) {
								botAndMiddleCards[5] = "ace";
							} else if (cards[middleCard4].contains("2")) {
								botAndMiddleCards[5] = "2";
							} else if (cards[middleCard4].contains("3")) {
								botAndMiddleCards[5] = "3";
							} else if (cards[middleCard4].contains("4")) {
								botAndMiddleCards[5] = "4";
							} else if (cards[middleCard4].contains("5")) {
								botAndMiddleCards[5] = "5";
							} else if (cards[middleCard4].contains("6")) {
								botAndMiddleCards[5] = "6";
							} else if (cards[middleCard4].contains("7")) {
								botAndMiddleCards[5] = "7";
							} else if (cards[middleCard4].contains("8")) {
								botAndMiddleCards[5] = "8";
							} else if (cards[middleCard4].contains("9")) {
								botAndMiddleCards[5] = "9";
							} else if (cards[middleCard4].contains("10")) {
								botAndMiddleCards[5] = "10";
							} else if (cards[middleCard4].contains("jack")) {
								botAndMiddleCards[5] = "jack";
							} else if (cards[middleCard4].contains("queen")) {
								botAndMiddleCards[5] = "queen";
							} else if (cards[middleCard4].contains("king")) {
								botAndMiddleCards[5] = "king";
							}
							if (cards[middleCard4].contains("spades")) {
								botSpades+=1;
							}
							else if(cards[middleCard4].contains("hearts")) {
								botHearts+=1;
							}
							else if(cards[middleCard4].contains("diamonds")) {
								botDiamonds+=1;
							}
							else if(cards[middleCard4].contains("clubs")) {
								botClubs+=1;
							}
							
							System.out.println("The bot has "+botAndMiddleCards[0]+", "+botAndMiddleCards[1]
											+", "+botAndMiddleCards[2]+", "+botAndMiddleCards[3]+", "+botAndMiddleCards[4]
											+" and "+botAndMiddleCards[5]);
							event.getChannel().sendMessage("How much would you like to bet");
						} else {
							balance += totalBet * 2;
							event.getChannel().sendMessage(
									"The bot folded, you win " + ((totalBet * 2)-wager) + ". Your balance is now " + balance);
							gameOver = true;
							rewardGiven=true;
						}
					}
				} else if (content.contains("bet") && turn && !gameOver) {
					content = content.replace("bet", "");
					try {
						wager = Integer.parseInt(content);
					} catch (Exception e) {
						event.getChannel().sendMessage("Choose a number after the command to gamble");
						return;
					}
					System.out.println(totalBet);
					if (balance-wager < 0) {
						event.getChannel().sendMessage("You don't have enough money to wager that much.");
						gameOver=true;
					} else if(wager<0) {
						event.getChannel().sendMessage("Choose a positive number.");
					}
					else {
						turn = false;
						balance -= wager;
						totalBet += wager;
						event.getChannel().sendMessage("Your balance is " + balance);
						highestSuit=0;
						highestSuit=botSpades;
						if(botDiamonds>highestSuit) {
							highestSuit=botDiamonds;
						}
						if(botHearts>highestSuit) {
							highestSuit=botHearts;
						}
						if(botSpades>highestSuit) {
							highestSuit=botSpades;
						}
						
						highestCallChance = botAlgorithm(botAndMiddleCards, highestSuit);

						// add more ways of having good hand here, in method

						if (500 > highestCallChance) {
							highestCallChance = 500;
						}
						botCallChance = highestCallChance;
						Random callRandom = new Random();
						int i = callRandom.nextInt(750);
						if (botCallChance >= i) {
							// bot calls
							System.out.println("Bot calls. Bot chance of calling was " + botCallChance + ". The random was " + i);
							event.getChannel().sendMessage("Bot calls.");
							middleCard5 = randCards.nextInt(52);
							for (int k = 0; k < usedCards.length; k++) {
								if (usedCards[k] == middleCard5) {
									isUsed = true;

								}
								while (isUsed == true) {
									middleCard5 = randCards.nextInt(52);
									isUsed = false;
								}
							}
							usedCards[8] = middleCard5;
							event.getChannel()
									.sendMessage("The middle cards are now the " + cards[middleCard1] + ", the "
											+ cards[middleCard2] + ", the " + cards[middleCard3] + " , the "
											+ cards[middleCard4] + " and the " + cards[middleCard5]);

							if (cards[middleCard5].contains("ace")) {
								botAndMiddleCards[6] = "ace";
							} else if (cards[middleCard5].contains("2")) {
								botAndMiddleCards[6] = "2";
							} else if (cards[middleCard5].contains("3")) {
								botAndMiddleCards[6] = "3";
							} else if (cards[middleCard5].contains("4")) {
								botAndMiddleCards[6] = "4";
							} else if (cards[middleCard5].contains("5")) {
								botAndMiddleCards[6] = "5";
							} else if (cards[middleCard5].contains("6")) {
								botAndMiddleCards[6] = "6";
							} else if (cards[middleCard5].contains("7")) {
								botAndMiddleCards[6] = "7";
							} else if (cards[middleCard5].contains("8")) {
								botAndMiddleCards[6] = "8";
							} else if (cards[middleCard5].contains("9")) {
								botAndMiddleCards[6] = "9";
							} else if (cards[middleCard5].contains("10")) {
								botAndMiddleCards[6] = "10";
							} else if (cards[middleCard5].contains("jack")) {
								botAndMiddleCards[6] = "jack";
							} else if (cards[middleCard5].contains("queen")) {
								botAndMiddleCards[6] = "queen";
							} else if (cards[middleCard5].contains("king")) {
								botAndMiddleCards[6] = "king";
							}
							if (cards[middleCard5].contains("spades")) {
								botSpades+=1;
							}
							else if(cards[middleCard5].contains("hearts")) {
								botHearts+=1;
							}
							else if(cards[middleCard5].contains("diamonds")) {
								botDiamonds+=1;
							}
							else if(cards[middleCard5].contains("clubs")) {
								botClubs+=1;
							}
							
							System.out.println("The bot has "+botAndMiddleCards[0]+", "+botAndMiddleCards[1]
											+", "+botAndMiddleCards[2]+", "+botAndMiddleCards[3]+", "+botAndMiddleCards[4]
											+", "+botAndMiddleCards[5]+" and "+botAndMiddleCards[6]);
							event.getChannel().sendMessage("Your hand is now the " +cards[playerCard1]+", "+cards[playerCard2]+", "+cards[middleCard1]+", "+cards[middleCard2]+", "+cards[middleCard3]+", "+cards[middleCard4]+", and "+cards[middleCard5]);
							event.getChannel().sendMessage("How much would you like to bet?");
						} else {
							balance += totalBet * 2;
							event.getChannel().sendMessage(
									"The bot folded, you win " + ((totalBet * 2)-wager) + ". Your balance is now " + balance);
							gameOver = true;
							rewardGiven=true;
						}
						}
					}else if (content.contains("bet") && river && !gameOver) {
							content = content.replace("bet", "");
							try {
								wager = Integer.parseInt(content);
							} catch (Exception e) {
								event.getChannel().sendMessage("Choose a number after the command to gamble");
								return;
							}
							System.out.println(totalBet);
							if (balance-wager < 0) {
								event.getChannel().sendMessage("You don't have enough money to wager that much.");
								gameOver=true;
							} else if(wager<0) {
								event.getChannel().sendMessage("Choose a positive number.");
							}
							else {
								river = false;
								balance -= wager;
								totalBet += wager;
								event.getChannel().sendMessage("Your balance is " + balance);
								event.getChannel().sendMessage("Bot calls.");
								
						if (cards[middleCard1].contains("ace")) {
							playerAndMiddleCards[0] = "ace";
						} else if (cards[middleCard1].contains("2")) {
							playerAndMiddleCards[0] = "2";
						} else if (cards[middleCard1].contains("3")) {
							playerAndMiddleCards[0] = "3";
						} else if (cards[middleCard1].contains("4")) {
							playerAndMiddleCards[0] = "4";
						} else if (cards[middleCard1].contains("5")) {
							playerAndMiddleCards[0] = "5";
						} else if (cards[middleCard1].contains("6")) {
							playerAndMiddleCards[0] = "6";
						} else if (cards[middleCard1].contains("7")) {
							playerAndMiddleCards[0] = "7";
						} else if (cards[middleCard1].contains("8")) {
							playerAndMiddleCards[0] = "8";
						} else if (cards[middleCard1].contains("9")) {
							playerAndMiddleCards[0] = "9";
						} else if (cards[middleCard1].contains("10")) {
							playerAndMiddleCards[0] = "10";
						} else if (cards[middleCard1].contains("jack")) {
							playerAndMiddleCards[0] = "jack";
						} else if (cards[middleCard1].contains("queen")) {
							playerAndMiddleCards[0] = "queen";
						} else if (cards[middleCard1].contains("king")) {
							playerAndMiddleCards[0] = "king";
						}
						if (cards[middleCard1].contains("spades")) {
							playerSpades+=1;
						}
						else if(cards[middleCard1].contains("hearts")) {
							playerHearts+=1;
						}
						else if(cards[middleCard1].contains("diamonds")) {
							playerDiamonds+=1;
						}
						else if(cards[middleCard1].contains("clubs")) {
							playerClubs+=1;
						}
						
						if (cards[middleCard2].contains("ace")) {
							playerAndMiddleCards[1] = "ace";
						} else if (cards[middleCard2].contains("2")) {
							playerAndMiddleCards[1] = "2";
						} else if (cards[middleCard2].contains("3")) {
							playerAndMiddleCards[1] = "3";
						} else if (cards[middleCard2].contains("4")) {
							playerAndMiddleCards[1] = "4";
						} else if (cards[middleCard2].contains("5")) {
							playerAndMiddleCards[1] = "5";
						} else if (cards[middleCard2].contains("6")) {
							playerAndMiddleCards[1] = "6";
						} else if (cards[middleCard2].contains("7")) {
							playerAndMiddleCards[1] = "7";
						} else if (cards[middleCard2].contains("8")) {
							playerAndMiddleCards[1] = "8";
						} else if (cards[middleCard2].contains("9")) {
							playerAndMiddleCards[1] = "9";
						} else if (cards[middleCard2].contains("10")) {
							playerAndMiddleCards[1] = "10";
						} else if (cards[middleCard2].contains("jack")) {
							playerAndMiddleCards[1] = "jack";
						} else if (cards[middleCard2].contains("queen")) {
							playerAndMiddleCards[1] = "queen";
						} else if (cards[middleCard2].contains("king")) {
							playerAndMiddleCards[1] = "king";
						}
						if (cards[middleCard2].contains("spades")) {
							playerSpades+=1;
						}
						else if(cards[middleCard2].contains("hearts")) {
							playerHearts+=1;
						}
						else if(cards[middleCard2].contains("diamonds")) {
							playerDiamonds+=1;
						}
						else if(cards[middleCard2].contains("clubs")) {
							playerClubs+=1;
						}
						if (cards[middleCard3].contains("ace")) {
							playerAndMiddleCards[2] = "ace";
						} else if (cards[middleCard3].contains("2")) {
							playerAndMiddleCards[2] = "2";
						} else if (cards[middleCard3].contains("3")) {
							playerAndMiddleCards[2] = "3";
						} else if (cards[middleCard3].contains("4")) {
							playerAndMiddleCards[2] = "4";
						} else if (cards[middleCard3].contains("5")) {
							playerAndMiddleCards[2] = "5";
						} else if (cards[middleCard3].contains("6")) {
							playerAndMiddleCards[2] = "6";
						} else if (cards[middleCard3].contains("7")) {
							playerAndMiddleCards[2] = "7";
						} else if (cards[middleCard3].contains("8")) {
							playerAndMiddleCards[2] = "8";
						} else if (cards[middleCard3].contains("9")) {
							playerAndMiddleCards[2] = "9";
						} else if (cards[middleCard3].contains("10")) {
							playerAndMiddleCards[2] = "10";
						} else if (cards[middleCard3].contains("jack")) {
							playerAndMiddleCards[2] = "jack";
						} else if (cards[middleCard3].contains("queen")) {
							playerAndMiddleCards[2] = "queen";
						} else if (cards[middleCard3].contains("king")) {
							playerAndMiddleCards[2] = "king";
						}
						if (cards[middleCard3].contains("spades")) {
							playerSpades+=1;
						}
						else if(cards[middleCard3].contains("hearts")) {
							playerHearts+=1;
						}
						else if(cards[middleCard3].contains("diamonds")) {
							playerDiamonds+=1;
						}
						else if(cards[middleCard3].contains("clubs")) {
							playerClubs+=1;
						}
						if (cards[playerCard1].contains("ace")) {
							playerAndMiddleCards[3] = "ace";
						} else if (cards[playerCard1].contains("2")) {
							playerAndMiddleCards[3] = "2";
						} else if (cards[playerCard1].contains("3")) {
							playerAndMiddleCards[3] = "3";
						} else if (cards[playerCard1].contains("4")) {
							playerAndMiddleCards[3] = "4";
						} else if (cards[playerCard1].contains("5")) {
							playerAndMiddleCards[3] = "5";
						} else if (cards[playerCard1].contains("6")) {
							playerAndMiddleCards[3] = "6";
						} else if (cards[playerCard1].contains("7")) {
							playerAndMiddleCards[3] = "7";
						} else if (cards[playerCard1].contains("8")) {
							playerAndMiddleCards[3] = "8";
						} else if (cards[playerCard1].contains("9")) {
							playerAndMiddleCards[3] = "9";
						} else if (cards[playerCard1].contains("10")) {
							playerAndMiddleCards[3] = "10";
						} else if (cards[playerCard1].contains("jack")) {
							playerAndMiddleCards[3] = "jack";
						} else if (cards[playerCard1].contains("queen")) {
							playerAndMiddleCards[3] = "queen";
						} else if (cards[playerCard1].contains("king")) {
							playerAndMiddleCards[3] = "king";
						}
						if (cards[playerCard1].contains("spades")) {
							playerSpades+=1;
						}
						else if(cards[playerCard1].contains("hearts")) {
							playerHearts+=1;
						}
						else if(cards[playerCard1].contains("diamonds")) {
							playerDiamonds+=1;
						}
						else if(cards[playerCard1].contains("clubs")) {
							playerClubs+=1;
						}
						if (cards[playerCard2].contains("ace")) {
							playerAndMiddleCards[4] = "ace";
						} else if (cards[playerCard2].contains("2")) {
							playerAndMiddleCards[4] = "2";
						} else if (cards[playerCard2].contains("3")) {
							playerAndMiddleCards[4] = "3";
						} else if (cards[playerCard2].contains("4")) {
							playerAndMiddleCards[4] = "4";
						} else if (cards[playerCard2].contains("5")) {
							playerAndMiddleCards[4] = "5";
						} else if (cards[playerCard2].contains("6")) {
							playerAndMiddleCards[4] = "6";
						} else if (cards[playerCard2].contains("7")) {
							playerAndMiddleCards[4] = "7";
						} else if (cards[playerCard2].contains("8")) {
							playerAndMiddleCards[4] = "8";
						} else if (cards[playerCard2].contains("9")) {
							playerAndMiddleCards[4] = "9";
						} else if (cards[playerCard2].contains("10")) {
							playerAndMiddleCards[4] = "10";
						} else if (cards[playerCard2].contains("jack")) {
							playerAndMiddleCards[4] = "jack";
						} else if (cards[playerCard2].contains("queen")) {
							playerAndMiddleCards[4] = "queen";
						} else if (cards[playerCard2].contains("king")) {
							playerAndMiddleCards[4] = "king";
						}
						if (cards[playerCard2].contains("spades")) {
							playerSpades+=1;
						}
						else if(cards[playerCard2].contains("hearts")) {
							playerHearts+=1;
						}
						else if(cards[playerCard2].contains("diamonds")) {
							playerDiamonds+=1;
						}
						else if(cards[playerCard2].contains("clubs")) {
							playerClubs+=1;
						}
						if (cards[middleCard4].contains("ace")) {
							playerAndMiddleCards[5] = "ace";
						} else if (cards[middleCard4].contains("2")) {
							playerAndMiddleCards[5] = "2";
						} else if (cards[middleCard4].contains("3")) {
							playerAndMiddleCards[5] = "3";
						} else if (cards[middleCard4].contains("4")) {
							playerAndMiddleCards[5] = "4";
						} else if (cards[middleCard4].contains("5")) {
							playerAndMiddleCards[5] = "5";
						} else if (cards[middleCard4].contains("6")) {
							playerAndMiddleCards[5] = "6";
						} else if (cards[middleCard4].contains("7")) {
							playerAndMiddleCards[5] = "7";
						} else if (cards[middleCard4].contains("8")) {
							playerAndMiddleCards[5] = "8";
						} else if (cards[middleCard4].contains("9")) {
							playerAndMiddleCards[5] = "9";
						} else if (cards[middleCard4].contains("10")) {
							playerAndMiddleCards[5] = "10";
						} else if (cards[middleCard4].contains("jack")) {
							playerAndMiddleCards[5] = "jack";
						} else if (cards[middleCard4].contains("queen")) {
							playerAndMiddleCards[5] = "queen";
						} else if (cards[middleCard4].contains("king")) {
							playerAndMiddleCards[5] = "king";
						}
						if (cards[middleCard4].contains("spades")) {
							playerSpades+=1;
						}
						else if(cards[middleCard4].contains("hearts")) {
							playerHearts+=1;
						}
						else if(cards[middleCard4].contains("diamonds")) {
							playerDiamonds+=1;
						}
						else if(cards[middleCard4].contains("clubs")) {
							playerClubs+=1;
						}
						if (cards[middleCard5].contains("ace")) {
							playerAndMiddleCards[6] = "ace";
						} else if (cards[middleCard5].contains("2")) {
							playerAndMiddleCards[6] = "2";
						} else if (cards[middleCard5].contains("3")) {
							playerAndMiddleCards[6] = "3";
						} else if (cards[middleCard5].contains("4")) {
							playerAndMiddleCards[6] = "4";
						} else if (cards[middleCard5].contains("5")) {
							playerAndMiddleCards[6] = "5";
						} else if (cards[middleCard5].contains("6")) {
							playerAndMiddleCards[6] = "6";
						} else if (cards[middleCard5].contains("7")) {
							playerAndMiddleCards[6] = "7";
						} else if (cards[middleCard5].contains("8")) {
							playerAndMiddleCards[6] = "8";
						} else if (cards[middleCard5].contains("9")) {
							playerAndMiddleCards[6] = "9";
						} else if (cards[middleCard5].contains("10")) {
							playerAndMiddleCards[6] = "10";
						} else if (cards[middleCard5].contains("jack")) {
							playerAndMiddleCards[6] = "jack";
						} else if (cards[middleCard5].contains("queen")) {
							playerAndMiddleCards[6] = "queen";
						} else if (cards[middleCard5].contains("king")) {
							playerAndMiddleCards[6] = "king";
						}
						if (cards[middleCard5].contains("spades")) {
							playerSpades+=1;
						}
						else if(cards[middleCard5].contains("hearts")) {
							playerHearts+=1;
						}
						else if(cards[middleCard5].contains("diamonds")) {
							playerDiamonds+=1;
						}
						else if(cards[middleCard5].contains("clubs")) {
							playerClubs+=1;
						}
						
						//test certain hands
						/*botAndMiddleCards[0]="7";
						botAndMiddleCards[1]="8";
						botAndMiddleCards[2]="9";
						botAndMiddleCards[3]="jack";
						botAndMiddleCards[4]="queen";
						botAndMiddleCards[5]="king";
						botAndMiddleCards[6]="ace";
						
						playerAndMiddleCards[0]="7";
						playerAndMiddleCards[1]="8";
						playerAndMiddleCards[2]="10";
						playerAndMiddleCards[3]="jack";
						playerAndMiddleCards[4]="queen";
						playerAndMiddleCards[5]="king";
						playerAndMiddleCards[6]="ace";
						*/
						
						
						if(!rewardGiven) {
							event.getChannel().sendMessage("Your hand is now the " +cards[playerCard1]+", "+cards[playerCard2]+", "+cards[middleCard1]+", "+cards[middleCard2]+", "+cards[middleCard3]+", "+cards[middleCard4]+", and "+cards[middleCard5]);
							event.getChannel().sendMessage("The bot's final hand has the "+cards[botCard1]+", "+cards[botCard2]+", "+cards[middleCard1]+", "+cards[middleCard2]+", "+cards[middleCard3]+", "+cards[middleCard4]+", and "+cards[middleCard5]);
						playerWon=playerWins(playerAndMiddleCards, botAndMiddleCards);
						if(playerWon) {
							balance += totalBet * 2;
							event.getChannel().sendMessage(
									"You have the better hand. You won " + ((totalBet * 2)) + ". Your balance is now " + balance);
							gameOver = true;
						}
						else if(!playerWon) {
							if(tie) {
								balance+=totalBet;
								event.getChannel().sendMessage("It is a tie. You get back "+totalBet+". Your balance is now "+balance);
							}
							else {
							event.getChannel().sendMessage("The bot has the better hand. You win nothing. Your balance is now "+balance);
						}
						}
						}
						else {
							System.out.println("error");
						}
						}
					}
					else if(event.getMessageContent().contains("work")) {
				String work = event.getMessageContent().replaceAll(" ", "").replace("!gamble", "").replace("work", "");
			try {
				payment=Integer.parseInt(work);
			}
			catch (Exception e){
				event.getChannel().sendMessage("Choose a number after the command to gamble");
				return;
			}
			balance+=payment;
			event.getChannel().sendMessage("Your balance is now "+balance);
				}
				}
	}

	public int botAlgorithm(String[] botAndMiddleCards, int suits) {
		highestCallChance = 0;
		numberSameCards = 0;
		numberPairs=0;
		highestPairValue=0;
		lowestPairValue=0;
		highestTripleValue=0;
		highCard=0;
		secondHighCard=0;
		thirdHighCard=0;
		fourthHighCard=0;
		fifthHighCard=0;
		highStraightCard=0;
		straight=false;
		ace=false;
		two=false;
		three=false;
		four=false;
		five=false;
		six=false;
		seven=false;
		eight=false;
		nine=false;
		ten=false;
		jack=false;
		queen=false;
		king=false;
		
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if(botAndMiddleCards[j]=="ace") {
				ace=true;
			}
			else if(botAndMiddleCards[j]=="2") {
				two=true;
			}
			else if(botAndMiddleCards[j]=="3") {
				three=true;
			}
			else if(botAndMiddleCards[j]=="4") {
				four=true;
			}
			else if(botAndMiddleCards[j]=="5") {
				five=true;
			}
			else if(botAndMiddleCards[j]=="6") {
				six=true;
			}
			else if(botAndMiddleCards[j]=="7") {
				seven=true;
			}
			else if(botAndMiddleCards[j]=="8") {
				eight=true;
			}
			else if(botAndMiddleCards[j]=="9") {
				nine=true;
			}
			else if(botAndMiddleCards[j]=="10") {
				ten=true;
			}
			else if(botAndMiddleCards[j]=="jack") {
				jack=true;
			}
			else if(botAndMiddleCards[j]=="queen") {
				queen=true;
			}
			else if(botAndMiddleCards[j]=="king") {
				king=true;
			}
			if(ace) {
				if(two) {
					if(three) {
						if(four) {
							if(five) {
								if(740>highestCallChance) {
								highestCallChance=740;
								straight=true;
								highStraightCard=1;
								}
							}
						}
					}
				}
			}
			else if(two) {
				if(three) {
					if(four) {
						if(five) {
							if(six) {
								if(741>highestCallChance) {
									highestCallChance=741;
									straight=true;
									highStraightCard=2;
								}
							}
						}
					}
				}
			}
			else if(three) {
				if(four) {
					if(five) {
						if(six) {
							if(seven) {
								if(742>highestCallChance) {
									highestCallChance=742;
									straight=true;
									highStraightCard=3;
								}
							}
						}
					}
				}
			}
			else if(four) {
				if(five) {
					if(six) {
						if(seven) {
							if(eight) {
								if(743>highestCallChance) {
									highestCallChance=743;
									straight=true;
									highStraightCard=4;
								}
							}
						}
					}
				}
			}
			else if(five) {
				if(six) {
					if(seven) {
						if(eight) {
							if(nine) {
								if(744>highestCallChance) {
									highestCallChance=744;
									straight=true;
									highStraightCard=5;
								}
							}
						}
					}
				}
			}
			else if(six) {
				if(seven) {
					if(eight) {
						if(nine) {
							if(ten) {
								if(745>highestCallChance) {
									highestCallChance=745;
									straight=true;
									highStraightCard=6;
								}
							}
						}
					}
				}
			}
			else if(seven) {
				if(eight) {
					if(nine) {
						if(ten) {
							if(jack) {
								if(746>highestCallChance) {
									highestCallChance=746;
									straight=true;
									highStraightCard=7;
								}
							}
						}
					}
				}
			}
			else if(eight) {
				if(nine) {
					if(ten) {
						if(jack) {
							if(queen) {
								if(747>highestCallChance) {
									highestCallChance=747;
									straight=true;
									highStraightCard=8;
								}
							}
						}
					}
				}
			}
			else if(nine) {
				if(ten) {
					if(jack) {
						if(queen) {
							if(king) {
								if(748>highestCallChance) {
									highestCallChance=748;
									straight=true;
									highStraightCard=9;
								}
							}
						}
					}
				}
			}
			else if(ten) {
				if(jack) {
					if(queen) {
						if(king) {
							if(ace) {
								if(749>highestCallChance) {
									highestCallChance=749;
									straight=true;
									highStraightCard=10;
								}
							}
						}
					}
				}
			}
		}
		
		
		
		
		
		
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if(secondHighCard==12) {
				System.out.println("");
			}
			if (botAndMiddleCards[j].equals("ace")) {
				numberSameCards += 1;
				if(13>highCard) {
				highCard=13;
				}
				else if(13>secondHighCard) {
					secondHighCard=13;
				}
				else if(13>thirdHighCard) {
					thirdHighCard=13;
				}
				else if(13>fourthHighCard) {
					fourthHighCard=13;
				}
				else if (13>fifthHighCard) {
					fifthHighCard=13;
				}
				if(513>highestCallChance) {
				highestCallChance=513;
				}
			}
		}
		if (numberSameCards == 2) {
			if (712 > highestCallChance) {
				highestCallChance = 712;
				numberPairs+=1;
				if(13>highestPairValue) {
					highestPairValue=13;
				}
				else {
					lowestPairValue=13;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 739;
			triple=true;
			highestTripleValue=13;
		}
		else if(numberSameCards == 4) {
			highestCallChance=790;
		}
		
		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("king")) {
				numberSameCards += 1;
				if(12>highCard) {
					highCard=12;
					}
				else if(12>secondHighCard) {
					secondHighCard=12;
				}
				else if(12>thirdHighCard) {
					thirdHighCard=12;
				}
				else if(12>fourthHighCard) {
					fourthHighCard=12;
				}
				else if (12>fifthHighCard) {
					fifthHighCard=12;
				}
				if(512>highestCallChance) {
				highestCallChance=512;
				}
			}
		}
		if (numberSameCards == 2) {
			if (711 > highestCallChance) {
				highestCallChance = 711;
				numberPairs+=1;
				if(12>highestPairValue) {
					highestPairValue=12;
				}
				else {
					lowestPairValue=12;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 738;
			triple=true;
			highestTripleValue=12;
		}
		else if(numberSameCards == 4) {
			highestCallChance=789;
		}
		
		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("queen")) {
				numberSameCards += 1;
				if(11>highCard) {
					highCard=11;
					}
				else if(11>secondHighCard) {
					secondHighCard=11;
				}
				else if(11>thirdHighCard) {
					thirdHighCard=11;
				}
				else if(11>fourthHighCard) {
					fourthHighCard=11;
				}
				else if (11>fifthHighCard) {
					fifthHighCard=11;
				}
				if(511>highestCallChance) {
				highestCallChance=511;
				}
			}
		}
		if (numberSameCards == 2) {
			if (710 > highestCallChance) {
				highestCallChance = 710;
				numberPairs+=1;
				if(11>highestPairValue) {
					highestPairValue=11;
				}
				else {
					lowestPairValue=11;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 737;
			triple=true;
			highestTripleValue=11;
		}
		else if(numberSameCards == 4) {
			highestCallChance=788;
		}

		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("jack")) {
				numberSameCards += 1;
				if(10>highCard) {
					highCard=10;
					}
				else if(10>secondHighCard) {
					secondHighCard=10;
				}
				else if(10>thirdHighCard) {
					thirdHighCard=10;
				}
				else if(10>fourthHighCard) {
					fourthHighCard=10;
				}
				else if (10>fifthHighCard) {
					fifthHighCard=10;
				}
				if(510>highestCallChance) {
				highestCallChance=510;
				}
			}
		}
		if (numberSameCards == 2) {
			if (709 > highestCallChance) {
				highestCallChance = 709;
				numberPairs+=1;
				if(10>highestPairValue) {
					highestPairValue=10;
				}
				else {
					lowestPairValue=10;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 736;
			triple=true;
			highestTripleValue=10;
		}
		else if(numberSameCards == 4) {
			highestCallChance=787;
		}

		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("10")) {
				numberSameCards += 1;
				if(9>highCard) {
					highCard=9;
					}
				else if(9>secondHighCard) {
					secondHighCard=9;
				}
				else if(9>thirdHighCard) {
					thirdHighCard=9;
				}
				else if(9>fourthHighCard) {
					fourthHighCard=9;
				}
				else if (9>fifthHighCard) {
					fifthHighCard=9;
				}
				if(509>highestCallChance) {
				highestCallChance=509;
				}
			}
		}
		if (numberSameCards == 2) {
			if (708 > highestCallChance) {
				highestCallChance = 708;
				numberPairs+=1;
				if(9>highestPairValue) {
					highestPairValue=9;
				}
				else {
					lowestPairValue=9;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 735;
			triple=true;
			highestTripleValue=9;
		}
		else if(numberSameCards == 4) {
			highestCallChance=786;
		}
		
		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("9")) {
				numberSameCards += 1;
				if(8>highCard) {
					highCard=8;
					}
				else if(8>secondHighCard) {
					secondHighCard=8;
				}
				else if(8>thirdHighCard) {
					thirdHighCard=8;
				}
				else if(8>fourthHighCard) {
					fourthHighCard=8;
				}
				else if (8>fifthHighCard) {
					fifthHighCard=8;
				}
				if(508>highestCallChance) {
				highestCallChance=508;
				}
			}
		}
		if (numberSameCards == 2) {
			if (707 > highestCallChance) {
				highestCallChance = 707;
				numberPairs+=1;
				if(8>highestPairValue) {
					highestPairValue=8;
				}
				else {
					lowestPairValue=8;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 734;
			triple=true;
			highestTripleValue=8;
		}
		else if(numberSameCards == 4) {
			highestCallChance=785;
		}

		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("8")) {
				numberSameCards += 1;
				if(7>highCard) {
					highCard=7;
					}
				else if(7>secondHighCard) {
					secondHighCard=7;
				}
				else if(7>thirdHighCard) {
					thirdHighCard=7;
				}
				else if(7>fourthHighCard) {
					fourthHighCard=7;
				}
				else if (7>fifthHighCard) {
					fifthHighCard=7;
				}
				if(507>highestCallChance) {
				highestCallChance=507;
				}
			}
		}
		if (numberSameCards == 2) {
			if (706 > highestCallChance) {
				highestCallChance = 706;
				numberPairs+=1;
				if(7>highestPairValue) {
					highestPairValue=7;
				}
				else {
					lowestPairValue=7;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 733;
			triple=true;
			highestTripleValue=7;
		}
		else if(numberSameCards == 4) {
			highestCallChance=784;
		}

		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("7")) {
				numberSameCards += 1;
				if(6>highCard) {
					highCard=6;
					}
				else if(6>secondHighCard) {
					secondHighCard=6;
				}
				else if(6>thirdHighCard) {
					thirdHighCard=6;
				}
				else if(6>fourthHighCard) {
					fourthHighCard=6;
				}
				else if (6>fifthHighCard) {
					fifthHighCard=6;
				}
				if(506>highestCallChance) {
				highestCallChance=506;
				}
			}
		}
		if (numberSameCards == 2) {
			if (705 > highestCallChance) {
				highestCallChance = 705;
				numberPairs+=1;
				if(6>highestPairValue) {
					highestPairValue=6;
				}else {
					lowestPairValue=6;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 732;
			triple=true;
			highestTripleValue=6;
		}
		else if(numberSameCards == 4) {
			highestCallChance=783;
		}
		
		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("6")) {
				numberSameCards += 1;
				if(5>highCard) {
					highCard=5;
					}
				else if(5>secondHighCard) {
					secondHighCard=5;
				}
				else if(5>thirdHighCard) {
					thirdHighCard=5;
				}
				else if(5>fourthHighCard) {
					fourthHighCard=5;
				}
				else if (5>fifthHighCard) {
					fifthHighCard=5;
				}
				if(505>highestCallChance) {
				highestCallChance=505;
				}
			}
		}
		if (numberSameCards == 2) {
			if (704 > highestCallChance) {
				highestCallChance = 704;
				numberPairs+=1;
				if(5>highestPairValue) {
					highestPairValue=5;
				}
				else {
					lowestPairValue=5;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 731;
			triple=true;
			highestTripleValue=5;
		}
		else if(numberSameCards == 4) {
			highestCallChance=782;
		}
		
		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("5")) {
				numberSameCards += 1;
				if(4>highCard) {
					highCard=4;
					}
					else if(4>secondHighCard) {
						secondHighCard=4;
					}
					else if(4>thirdHighCard) {
						thirdHighCard=4;
					}
					else if(4>fourthHighCard) {
						fourthHighCard=4;
					}
					else if (4>fifthHighCard) {
						fifthHighCard=4;
					}
				if(504>highestCallChance) {
				highestCallChance=504;
				}
			}
		}
		if (numberSameCards == 2) {
			if (703 > highestCallChance) {
				highestCallChance = 703;
				numberPairs+=1;
				if(4>highestPairValue) {
					highestPairValue=4;
				}
				else {
					lowestPairValue=4;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 730;
			triple=true;
			highestTripleValue=4;
		}
		else if(numberSameCards == 4) {
			highestCallChance=781;
		}
		
		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("4")) {
				numberSameCards += 1;
				if(3>highCard) {
					highCard=3;
					}
				else if(3>secondHighCard) {
					secondHighCard=3;
				}
				else if(3>thirdHighCard) {
					thirdHighCard=3;
				}
				else if(3>fourthHighCard) {
					fourthHighCard=3;
				}
				else if (3>fifthHighCard) {
					fifthHighCard=3;
				}
				if(503>highestCallChance) {
				highestCallChance=503;
				}
			}
		}
		if (numberSameCards == 2) {
			if (702 > highestCallChance) {
				highestCallChance = 702;
				numberPairs+=1;
				if(3>highestPairValue) {
					highestPairValue=3;
				}
				else {
					lowestPairValue=3;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 729;
			triple=true;
			highestTripleValue=3;
		}
		else if(numberSameCards == 4) {
			highestCallChance=780;
		}
		
		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("3")) {
				numberSameCards += 1;
				if(2>highCard) {
					highCard=2;
					}
				else if(2>secondHighCard) {
					secondHighCard=2;
				}
				else if(2>thirdHighCard) {
					thirdHighCard=2;
				}
				else if(2>fourthHighCard) {
					fourthHighCard=2;
				}
				else if (2>fifthHighCard) {
					fifthHighCard=2;
				}
				if(502>highestCallChance) {
				highestCallChance=502;
				}
			}
		}
		if (numberSameCards == 2) {
			if (701 > highestCallChance) {
				highestCallChance = 701;
				numberPairs+=1;
				if(2>highestPairValue) {
					highestPairValue=2;
				}
				else {
					lowestPairValue=2;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 728;
			triple=true;
			highestTripleValue=2;
		}
		else if(numberSameCards == 4) {
			highestCallChance=779;
		}
		
		numberSameCards = 0;
		for (int j = 0; j < botAndMiddleCards.length; j++) {
			if (botAndMiddleCards[j].equals("2")) {
				numberSameCards += 1;
				if(1>highCard) {
					highCard=1;
					}
				else if(1>secondHighCard) {
					secondHighCard=1;
				}
				else if(1>thirdHighCard) {
					thirdHighCard=1;
				}
				else if(1>fourthHighCard) {
					fourthHighCard=1;
				}
				else if (1>fifthHighCard) {
					fifthHighCard=1;
				}
				if(501>highestCallChance) {
				highestCallChance=501;
				}
			}
		}
		if (numberSameCards == 2) {
			if (700 > highestCallChance) {
				highestCallChance = 700;
				numberPairs+=1;
				if(1>highestPairValue) {
					highestPairValue=1;
				}
				else {
					lowestPairValue=1;
				}
			}
		} else if (numberSameCards == 3) {
			highestCallChance = 727;
			triple=true;
			highestTripleValue=1;
		}
		else if(numberSameCards == 4) {
			highestCallChance=778;
		}

		if(numberPairs==2) {
			highestCallChance=713+highestPairValue;
		}
		if(suits>=5) {
			if(straight && 791+highStraightCard>highestCallChance) {
				highestCallChance=791+highStraightCard;
			}
			else if((750+highCard)>highestCallChance) {
			highestCallChance=750+highCard;
			}
		}
		if(numberPairs==1 && triple) {
			if(highestCallChance<764+highestTripleValue) {
			highestCallChance=764+highestTripleValue;
			}
		}
		
		return highestCallChance;
	}
	public boolean playerWins(String [] playerAndMiddleCards, String [] botAndMiddleCards) {
		highestSuit=0;
		playerPairValue=0;
		botPairValue=0;
		playerLowPair=0;
		botLowPair=0;
		highestSuit=playerSpades;
		if(playerDiamonds>highestSuit) {
			highestSuit=playerDiamonds;
		}
		if(playerHearts>highestSuit) {
			highestSuit=playerHearts;
		}
		if(playerSpades>highestSuit) {
			highestSuit=playerSpades;
		}
		if(playerClubs>highestSuit) {
			highestSuit=playerClubs;
		}
		
		playerScore=botAlgorithm(playerAndMiddleCards, highestSuit);
		playerPairValue=highestPairValue;
		playerLowPair=lowestPairValue;
		playerHighCard=highCard;
		playerSecondHighCard=secondHighCard;
		playerThirdHighCard=thirdHighCard;
		playerFourthHighCard=fourthHighCard;
		playerFifthHighCard=fifthHighCard;
		highestSuit=0;
		highestSuit=botSpades;
		if(botDiamonds>highestSuit) {
			highestSuit=botDiamonds;
		}
		if(botHearts>highestSuit) {
			highestSuit=botHearts;
		}
		if(botSpades>highestSuit) {
			highestSuit=botSpades;
		}
		if(botClubs>highestSuit) {
			highestSuit=botClubs;
		}
		botScore=botAlgorithm(botAndMiddleCards, highestSuit);
		botPairValue=highestPairValue;
		botLowPair=lowestPairValue;
		botHighCard=highCard;
		botSecondHighCard=secondHighCard;
		botThirdHighCard=thirdHighCard;
		botFourthHighCard=fourthHighCard;
		botFifthHighCard=fifthHighCard;
		gameOver=true;
		tie=false;
		if(playerScore>botScore) {
			return true;
		}
		else if(playerScore==botScore) {
			if(playerPairValue>botPairValue) {
				return true;
			}
			else if(playerPairValue<botPairValue) {
				return false;
			}
			else if(playerPairValue==botPairValue) {
				if(playerLowPair>botLowPair) {
					return true;
				}
				else if(playerLowPair<botLowPair) {
					return false;
				}
				else if(playerLowPair==botLowPair) {
					if(playerHighCard>botHighCard) {
						return true;
					}
					else if(playerHighCard<botHighCard) {
						return false;
					}
					else if(playerHighCard==botHighCard) {
						if(playerSecondHighCard>botSecondHighCard) {
							return true;
						}
						else if(playerSecondHighCard<botSecondHighCard) {
							return false;
						}
						else if(playerSecondHighCard==botSecondHighCard) {
							if(playerThirdHighCard>botThirdHighCard) {
								return true;
							}
							else if(playerThirdHighCard<botThirdHighCard) {
								return false;
							}
							else if(playerThirdHighCard==botThirdHighCard) {
								if(playerFourthHighCard>botFourthHighCard) {
									return true;
								}
								else if(playerFourthHighCard<botFourthHighCard) {
									return false;
								}
								else if(playerFourthHighCard==botFourthHighCard) {
									if(playerFifthHighCard>botFifthHighCard) {
										return true;
									}
									else if(playerFifthHighCard<botFifthHighCard) {
										return false;
									}
									else if(playerFifthHighCard==botFifthHighCard) {
										tie=true;
										return false;
									}
								}
							}
						}
					}
				}
			}
			else {
				System.out.println("error");
			}
		}
		return false;
	}
}
