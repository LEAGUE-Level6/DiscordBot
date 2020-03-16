package org.jointheleague.modules;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.javacord.api.event.message.MessageCreateEvent;
import com.gtranslate.*;

import net.aksingh.owmjapis.api.APIException;

public class MessageTranslator extends CustomMessageCreateListener {

	public MessageTranslator(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if (event.getMessageContent().contains("!translate")) {
			String word = event.getMessageContent().replaceAll(" ", "").replace("!translate","");
			//event.getChannel().sendMessage("")
			
			Translator translate = Translator.getInstance();
			String text = translate.translate(word, Language.ENGLISH, Language.CHINESE);
			event.getChannel().sendMessage(text);
		}
	}
}