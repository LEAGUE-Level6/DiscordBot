package org.jointheleague.modules;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;


import java.util.Random;

public class NumberRandom extends CustomMessageCreateListener {
    public NumberRandom(String channelName) {
        super(channelName);
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException, InterruptedException {
        String COMMAND = "!random";
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            Random r = new Random();
            event.getChannel().sendMessage("Your random number is " + r.nextInt(1001));
        }

        String input = "!game";
        String random = "";
        String messageContent2 = event.getMessageContent();
        String messageContent3 = event.getMessageContent();
        if (messageContent2.startsWith(input)) {
            Random r2 = new Random();
            int ran = r2.nextInt(3);
            event.getChannel().sendMessage("Welcome to Guess The Number from 0-3. Your guess: " + ran);
            if (ran == 0){
                input += "0";
            }
            if (ran == 1){
                input += "1";
            }
            if (ran == 2){
                input += "2";
            }
//            event.getChannel().sendMessage(random);
//            event.getChannel().sendMessage(event.getMessageContent());

//            int guess = Integer.parseInt(messageContent3);
//            System.out.println(guess);
            if (messageContent3.startsWith(random)){
                event.getChannel().sendMessage("Correct");
            }
//            else if (guess < ran){
//                event.getChannel().sendMessage("Higher!");
//            }
            else {
                event.getChannel().sendMessage("Nope! ");
            }

        }
    }
}
