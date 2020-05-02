package org.jointheleague.modules;
	import java.io.IOException;
	import org.javacord.api.event.message.MessageCreateEvent;
	import okhttp3.OkHttpClient;
	import okhttp3.Request;
	import okhttp3.Response;
	import okhttp3.ResponseBody;



	public class DateMonth extends CustomMessageCreateListener{
		private static final String COMMAND = "!Date";
	
	public DateMonth(String channelName) {
		super(channelName);
	}
	int month;
	int date;
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith(COMMAND)) {
			if(event.getMessageContent().equals(COMMAND)) {
				event.getChannel().sendMessage("Enter a month (Jan-1, Feb 2..");
				month = Integer.parseInt(event.getMessageContent());
				event.getChannel().sendMessage("Enter a day of the month");
				date = Integer.parseInt(event.getMessageContent());
			}
			OkHttpClient client = new OkHttpClient();
			String url = "https://numbersapi.p.rapidapi.com/" +month + "/"+date+"/date?fragment=true&json=true";
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
					System.out.println(responseBody.string());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

