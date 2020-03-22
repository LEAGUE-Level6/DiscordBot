package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.javacord.api.event.message.MessageCreateEvent;


import java.util.HashMap;
import java.util.Map;

import net.aksingh.owmjapis.api.APIException;

public class MessageTranslator extends CustomMessageCreateListener {
String word;
	public MessageTranslator(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException, IOException {
		if (event.getMessageContent().contains("!translate")) {
			String origin = event.getMessageContent();
			 word = origin.replaceAll(" ", "").replace("!translate","");

			
			event.getChannel().sendMessage("Here is the link to all the language codes: https://tech.yandex.com/translate/doc/dg/concepts/api-overview-docpage/");
			event.getChannel().sendMessage("Find a code and type it below");

			
			
			if(!event.getMessageContent().equals(origin)) {
				String lang = event.getMessageContent();
			
			String output = TranslateAPI.translate(word, "en", lang);
			event.getChannel().sendMessage(output);
			}
			
			
			
		}else{
			String lang = event.getMessageContent().replaceAll(" ", "").replace("!lang", "");
			String output = TranslateAPI.translate(word, "en", lang);
			event.getChannel().sendMessage(output);
		}
	}
}
 class TranslateAPI {
	public static final String API_KEY = "trnsl.1.1.20200322T185835Z.0eff28d7fa75b847.165716c61866d66251ee19040c6cf074539a139f";
	
	private static String request(String URL) throws IOException {
		URL url = new URL(URL);
		URLConnection urlConn = url.openConnection();
		urlConn.addRequestProperty("User-Agent", "Mozilla");
		
		InputStream inStream = urlConn.getInputStream();
		
		String recieved = new BufferedReader(new InputStreamReader(inStream)).readLine();
		
		inStream.close();
		return recieved;
	}
	
	
	public static String translate(String text, String sourceLang, String targetLang) throws IOException {
		String response = request("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + API_KEY + "&text=" + text + "&lang=" + sourceLang + "-" + targetLang);
		return response.substring(response.indexOf("text")+8, response.length()-3);
	}
	
}