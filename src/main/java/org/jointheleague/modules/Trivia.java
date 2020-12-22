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
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.Trivia.Result;
import org.jointheleague.modules.pojo.Trivia.TriviaResponse;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class Trivia extends CustomMessageCreateListener {
	private final Gson gson = new Gson();
	private static final String COMMAND = "!trivia";
	private Result r;
	private String correctMessage = "That was the correct answer! Use !trivia for me to ask you another question.";
	private String incorrectMessage = "That was the incorrect answer. Try again.";
	private String question;
	private String correct;
	private boolean answered;
	private List<String> answers;
	private String category;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;

	public Trivia(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND,
				"Asks the user a random trivia question when the command !trivia is used. Answer the question with !tAnswer and then the letter with your answer (e.g. !triviaAnswer A).");
		// TODO Auto-generated constructor stub

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().equals(COMMAND)) {
			try {
				r = getNewQuestion();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String command = COMMAND.replaceAll(" ", "").replace("!trivia", "");

			answered = false;
			question = r.getQuestion();
			correct = r.getCorrectAnswer();
			answers = r.getIncorrectAnswers();
			answers.add(correct);
			Collections.shuffle(answers);
			category = r.getCategory();
			answerA = answers.get(0);
			answerB = answers.get(1);
			answerC = "";
			answerD = "";
			if (answers.size() == 3) {
				answerC = answers.get(2);
			} else if (answers.size() == 4) {
				answerC = answers.get(2);
				answerD = answers.get(3);
			}
			question = question.replaceAll("&#039;", "\'").replaceAll("&quot;", "\"");
			answerA = answerA.replaceAll("&#039;", "\'").replaceAll("&quot;", "\"");
			answerB = answerB.replaceAll("&#039;", "\'").replaceAll("&quot;", "\"");
			answerC = answerC.replaceAll("&#039;", "\'").replaceAll("&quot;", "\"");
			answerD = answerD.replaceAll("&#039;", "\'").replaceAll("&quot;", "\"");
			category = category.replaceAll("&#039;", "\'").replaceAll("&quot;", "\"");
			

			if (command.equals("")) {
				MessageBuilder mb = new MessageBuilder().setEmbed(new EmbedBuilder().setTitle("Trivia Question")
						.setDescription("Category: " + category).addField("Question", question + "\nA: " + answerA
								+ "\n B: " + answerB + "\nC: " + answerC + "\nD: " + answerD));

				mb.send(event.getChannel());
			}
		} else if (event.getMessageContent().contains("!tAnswer")) {
			String command = event.getMessageContent().replace("!tAnswer", "").replaceAll(" ", "");
				if (!answered) {
					if (command.equals("A")) {
						if (answerA == correct) {
							event.getChannel().sendMessage(correctMessage);
							answered = true;
						} else {
							event.getChannel().sendMessage(incorrectMessage);
						}
					} else if (command.equals("B")) {
						if (answerB == correct) {
							event.getChannel().sendMessage(correctMessage);
							answered = true;
						} else {
							event.getChannel().sendMessage(incorrectMessage);
						}
					} else if (command.equals("C")) {
						if (answerC == correct) {
							event.getChannel().sendMessage(correctMessage);
							answered = true;
						} else {
							event.getChannel().sendMessage(incorrectMessage);
						}
					} else if (command.equals("D")) {
						if (answerD == correct) {
							event.getChannel().sendMessage(correctMessage);
							answered = true;
						} else {
							event.getChannel().sendMessage(incorrectMessage);
						}
					}
				}
			}
		}
	

	Result getNewQuestion() throws Exception {
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
