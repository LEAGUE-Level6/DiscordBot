package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.javacord.api.event.message.MessageCreateEvent;
public class NumberVs extends CustomMessageCreateListener {
    //Level 3 Discord Feature. NumberVs is a game played against the computer. The computer gives you a range of numbers. You and the computer both guess a number. Whoever is closer gets the point. The goal is to get more
    //points than the computer after x number of rounds.

    //Important Vars
    int range = 100; int playerScore = 0; int compScore = 0;
    //Limit to score to. Thinking 40 as default.
    int scoreLim = 10;
    //State Machine
    int state = 0;
    //Temp Vars
    int number = 0; String str = "";
    int randomNum = 0; Random random = new Random();
    int compTarget = 0; int compGuess  = 0;
    //default state 0, start state 1, game state 2

    public NumberVs(String channelName) {
        super(channelName);
    }


    private static final String COMMAND = "!GuessVs";

    @Override
    public void handle(MessageCreateEvent event) {
        // TODO Auto-generated method stub
        //use instead String message = event.getMessageContent();
        if (state == 0) {
            if (event.getMessageContent().equals("!GuessVs help") || event.getMessageContent().equals("!GuessVs Help")) {
                event.getChannel().sendMessage(
                        "To play, the computer will tell you a range of numbers. You will guess a number against the computer. Whoever is closer gets a pont. If you guess the number exactly, " +
                                "you get 5 extra points. Important commands(always include '!GuessVs '): range x --> specifies range, with x being maximum. start --> goes to game. score --> sets score to play to.");

            } else if (event.getMessageContent().startsWith("!GuessVs range")) {
                str = event.getMessageContent(); str = str.replace("!GuessVs range ", "");
                number = Integer.parseInt(str); range = number;
                event.getChannel().sendMessage("New Range: 0 to " + range);

            } else if (event.getMessageContent().startsWith("!GuessVs Range")) {
                str = event.getMessageContent(); str = str.replace("!GuessVs Range", "");
                number = Integer.parseInt(str); range = number;
                event.getChannel().sendMessage("New Range: 0 to " + range);

            } else if (event.getMessageContent().startsWith("!GuessVs Start") || event.getMessageContent().startsWith("!GuessVs start")) {
                event.getChannel().sendMessage("Time to Guess!! Type 'Guess x', with x being a number. Your range is from 0 " + range);
                state = 1;

            } else if (event.getMessageContent().startsWith("!GuessVs score") || event.getMessageContent().startsWith("!GuessVs score")){
                str = event.getMessageContent(); str = str.replace("!GuessVs score ", "");
                number = Integer.parseInt(str); scoreLim = number;
                event.getChannel().sendMessage("New Score range: " + scoreLim);
            }
        } else if (state == 1) {
            //Guessing state. Allow player to fight against computer.
            if(playerScore >= scoreLim){ event.getChannel().sendMessage("Congrats, you won... exiting game"); resetScores(); state = 0; }
            else if (compScore >= scoreLim){ event.getChannel().sendMessage("Congrats, you lost :( ... exiting game"); resetScores(); state = 0;}
            else if (event.getMessageContent().startsWith("Guess ")) {

                str = event.getMessageContent(); str = str.replace("Guess ", "");
                number = Integer.parseInt(str); compGuess = random.nextInt(range); compTarget = random.nextInt(range);
                // if the abs value of number - comptarget is lower than abs value of compguess - comptarget, player wins and vice versa
                event.getChannel().sendMessage("Your number was " + number + ". The computers was " + compGuess + ". The actual number was " + compTarget + ".");
                if (Math.abs(number - compTarget) < Math.abs(compGuess - compTarget)) {

                    if (number == compTarget) { playerScore += 5; event.getChannel().sendMessage("Nice! You guessed the number exactly! Plus 5 points!"); }
                    else { playerScore++; event.getChannel().sendMessage("Nice, you guessed closer! You get a point!"); }

                } else if(Math.abs(number - compTarget) > Math.abs(compGuess - compTarget)){

                    if (compGuess == compTarget) { compScore += 5; event.getChannel().sendMessage("Too bad! The computer guessed exactly! Plus 5 points :("); }
                    else { event.getChannel().sendMessage("Yikes, a computer beat you and guessed closer... it gets one point!"); compScore++; }

                } else {
                    event.getChannel().sendMessage("You guessed the same as the computer, which was " + number + ". You each get two points.");
                    event.getChannel().sendMessage("New Round: Scores are: " + "Player: " + playerScore + "Computer: " + compScore);
                }
                event.getChannel().sendMessage("Your score: " + playerScore + ". Computer score: " + compScore + ".");
            }
            //Exits state machine 1, back to 0.
            else if(event.getMessageContent().startsWith("Exit")){
                event.getChannel().sendMessage("Exiting game.");
                state = 0;
            }
        }
    }

    public void resetScores(){
        playerScore = 0;
        compScore = 0;
    }
}
