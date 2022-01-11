package org.jointheleague.modules;

import net.aksingh.owmjapis.api.APIException;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

public class PingPong extends CustomMessageCreateListener {
    private static final String prefix = "!ping";

    public PingPong(String channelName) {
        super(channelName);
        this.helpEmbed = new HelpEmbed(prefix, "Pong!!!!");
    }

    @Override
    public void handle(MessageCreateEvent event) throws APIException {
        String content = event.getMessageContent();

        if (content.isEmpty()) return;
        if (content.toLowerCase().contentEquals(prefix)) {
            event.getChannel().sendMessage("Pong!");
        }
    }
}