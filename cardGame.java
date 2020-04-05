package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class cardGame extends CustomMessageCreateListener{
	private static final String COMMAND = "!play";
	private static final String COMMAND2 = "!deal";
private static final String COMMAND3 = "!select";
LinkedList<WarSetUp> deck1 = new LinkedList<WarSetUp>();
LinkedList<WarSetUp> deck2 = new LinkedList<WarSetUp>();
ArrayList<String> player1;
ArrayList<String> computer;
String currentCard;
	public cardGame(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
	
	     if(event.getMessageContent().contains(COMMAND)){
	 		event.getChannel().sendMessage("Welcome to WAR! The rules of the game are simple.You are player1. !deal will give you your cards and print them out. !select <Card> allowsz you to choose from your deck. , if your card is greater than the bot's, you get to keep both cards. If the cards are the same, declare !war and each player deals another card. The first to get all the cards wins. ");
	 		

	     }
	     else if(event.getMessageContent().contains(COMMAND2)) {
	    	    List<WarSetUp> cardDeck = new ArrayList<WarSetUp>(); //create an ArrayList "cardDeck"
	            
	            for(int x=0; x<4; x++){          //0-3 for suit (4 suits)
	                for(int y=2; y<15; y++){     //2-14 for rank (13 ranks)
	                    cardDeck.add(new WarSetUp(x,y)); //create new card and add into the deck
	                } //end rank for
	            }//end suit for
	            
	            Collections.shuffle(cardDeck, new Random()); //shuffle the deck randomly
	            
	           
	            
	            deck1.addAll(cardDeck.subList(0, 25));              //26 cards for p1       
	            deck2.addAll(cardDeck.subList(26, cardDeck.size()));//26 cards for p2
	            for (int i =0; i < deck1.size(); i++) {
event.getChannel().sendMessage("Your cards are: " + deck1.get(i)+" "+i);
				}
	          
	     }
	            	else if(event.getMessageContent().contains(COMMAND3))    {
	   
	         	             //each player place one card face up
	            			  String s = event.getMessageContent().trim().substring(9, event.getMessageContent().length());
	            			  int t = Integer.parseInt(s);
	            			  WarSetUp p1Card = deck1.get(t);
	            			  
	         	            	WarSetUp p2Card = deck2.pop();
	         	            
	           event.getChannel().sendMessage(   ("Player 1 plays card is " + p1Card.toString()));
	           event.getChannel().sendMessage(("Player 2 plays card is " + p2Card.toString()));
	               deck1.remove(deck1.get(t));
	                if(p1Card.getCard() > p2Card.getCard()){//if player 1 win 
	                    deck1.addLast(p1Card);  //higher rank wins both cards and 
	                    deck1.addLast(p2Card);  //places them at the bottom of his deck.
	                    event.getChannel().sendMessage(("PLayer 1 wins the round"));
	                }
	     
	                else if(p1Card.getCard() < p2Card.getCard()){//if player 2 win 
	                    deck2.addLast(p1Card);   
	                    deck2.addLast(p2Card);  
	                    event.getChannel().sendMessage(("PLayer 2 wins the round"));
	                }//end else if
	                
	                else { //war happens when both cards' rank matched
	                	   event.getChannel().sendMessage(("War")); 
	                    
	                    //creating war cards
	                    List<WarSetUp> war1 = new ArrayList<WarSetUp>(); 
	                    List<WarSetUp> war2 = new ArrayList<WarSetUp>();
	                    
	                    //checking do players have enough (4)cards to stay in game
	                    for(int x=0; x<3; x++){ 
	                        //either one player runs out of card is game over
	                        if(deck1.size() == 0 || deck2.size() == 0 ){                      
	                            break;
	                        }//end if
	                     
	                        war1.add(deck1.pop());  //place additional card for war
	                        war2.add(deck2.pop());                  
	                    }//end for
	                    
	                    //only compare result when both players have enough cards for war
	                    if(war1.size() == 3 && war2.size() == 3 ){
	                        //display the war cards from each player
	                    	   event.getChannel().sendMessage(("War card for player1 is " + war1.get(0).toString()));
	                    	   event.getChannel().sendMessage(("War card for player2 is " + war2.get(0).toString()));
	                        
	                        if(war1.get(2).getCard() > war2.get(2).getCard()){
	                            deck1.addAll(war1);
	                            deck1.addAll(war2);
	                            event.getChannel().sendMessage(("Player 1 wins the war round"));
	                        }
	                        else{
	                            deck2.addAll(war1); 
	                            deck2.addAll(war2);
	                            event.getChannel().sendMessage(("Player 2 wins the war round"));
	                        }                     
	                    }
	                    
	                }
	                if(deck1.size() == 0 ){
	                	   event.getChannel().sendMessage(("game over\nPlayer 1 wins the game"));
	        
	                }
	                else if(deck2.size() == 0){
	                	   event.getChannel().sendMessage(("game over\nPlayer 2 wins the game"));
	                    
	                }
	            }
	   
    }
	     


 
}


