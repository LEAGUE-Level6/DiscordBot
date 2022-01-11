package org.jointheleague.modules;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;


import java.util.Random;

public class NumberRandom extends CustomMessageCreateListener{
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
    }
}
