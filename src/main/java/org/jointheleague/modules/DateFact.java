package org.jointheleague.modules;

import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DateFact extends CustomMessageCreateListener{
	
	private static final String COMMAND = "!DateFact";

	public DateFact(String channelName) {
		super(channelName);
	} 
	int month = 0;
	int date = 0;
	boolean b = false;
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith(COMMAND)) {
			if(event.getMessageContent().equals(COMMAND)) {
				event.getChannel().sendMessage("Enter a month and a date by typing !DateFact <mm/dd>");
			}
			month = Integer.parseInt(event.getMessageContent().substring(10 , 12));
			date =  Integer.parseInt(event.getMessageContent().substring(13));
			OkHttpClient client = new OkHttpClient();
			String url = "https://numbersapi.p.rapidapi.com/"+month+"/"+date+"/date?fragment=true&json=true";
			Request request = new Request.Builder()
					.url(url)
					.get()
					.addHeader("x-rapidapi-host", "numbersapi.p.rapidapi.com")
					.addHeader("x-rapidapi-key", "281623fc94msh8b9fe2abc1216bfp119c62jsnbb8e27ff8679")
					.build();

			try {
				Response response = client.newCall(request).execute();
				System.out.println(response.headers().get("Content-Type"));
				try (ResponseBody responseBody = response.body()) {
					event.getChannel().sendMessage(month + "/" + date);
					event.getChannel().sendMessage((responseBody.string()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
 	}
}

