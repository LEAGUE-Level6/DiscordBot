package org.jointheleague.modules;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.Trivia.Result;
import org.jointheleague.modules.pojo.Trivia.TriviaResponse;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class Trivia extends CustomMessageCreateListener {
	private final Gson gson = new Gson();
	private static final String COMMAND = "!trivia";
	private Result r;

	public Trivia(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {

			String command = COMMAND.replaceAll(" ", "").replace("!trivia", "");

			if (command.equals("")) {
				try {
					r = getQuestion();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String question = r.getQuestion();
				String correct = r.getCorrectAnswer();
				List<String> answers = r.getIncorrectAnswers();
				answers.add(correct);
				Collections.shuffle(answers);
				String category = r.getCategory();
				
				
				MessageBuilder mb = new MessageBuilder().setEmbed(new EmbedBuilder());
			}

		}
	}

	Result getQuestion() throws Exception {
		String urlLink = "https://opentdb.com/api.php?amount=1";
		URL url = new URL(urlLink);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		JsonReader repoReader = Json.createReader(con.getInputStream());
		JsonObject userJSON = ((JsonObject) repoReader.read());
		con.disconnect();
		TriviaResponse response = gson.fromJson(userJSON.toString(), TriviaResponse.class);

		return response.getResults().get(0);

	}

}
