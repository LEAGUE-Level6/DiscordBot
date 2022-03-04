package org.jointheleague.modules;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.Random;


public class BotKick extends CustomMessageCreateListener {
    public BotKick(String channelName) {
        super(channelName);
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException, InterruptedException {

        String messageContent2 = event.getMessageContent();
        String rock = "rock";
        String paper = "paper";
        String scissors = "scissors";
        String botChoice = "";
        Random ran = new Random();
        int Random = ran.nextInt(3);
        if (Random == 0){
            botChoice = scissors;
        }
        if (Random == 1){
            botChoice = rock;
        }
        if (Random == 2){
            botChoice = paper;
        }
        if(messageContent2.contains(rock)){
            event.getChannel().sendMessage("Computer chose: " + botChoice);
            if (botChoice.equals(scissors)){
                event.getChannel().sendMessage("You win");
            }
            else if (botChoice.equals(paper)){
                event.getChannel().sendMessage("You lose");
            }
        }
       else if(messageContent2.contains(scissors)) {
            event.getChannel().sendMessage("Computer chose: " + botChoice);
            if (botChoice.equals(paper)) {
                event.getChannel().sendMessage("You win");
            } else if (botChoice.equals(rock)) {
                event.getChannel().sendMessage("You lose");
            }
        }
        else if(messageContent2.contains(paper)) {
            event.getChannel().sendMessage("Computer chose: " + botChoice);
            if (botChoice.equals(rock)) {
                event.getChannel().sendMessage("You win");
            } else if (botChoice.equals(scissors)) {
                event.getChannel().sendMessage("You lose");
            }
        }



    }
}

