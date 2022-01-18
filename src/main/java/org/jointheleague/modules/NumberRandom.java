package org.jointheleague.modules;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;


import java.util.Random;

public class NumberRandom extends CustomMessageCreateListener {
    public NumberRandom(String channelName) {
        super(channelName);
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException {
        String COMMAND = "!random";
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            Random r = new Random();
            event.getChannel().sendMessage("Your random number is " + r.nextInt(1001));
        }

        String input = "!game";
        String input2 = "higher";
        String input3 = "lower";
        String messageContent2 = event.getMessageContent();
        if (messageContent2.startsWith(input)) {
            Random r2 = new Random();
            int ran = r2.nextInt(1001);
            event.getChannel().sendMessage("Welcome to higher or lower. Your guess: " + ran);


        }
    }
}
