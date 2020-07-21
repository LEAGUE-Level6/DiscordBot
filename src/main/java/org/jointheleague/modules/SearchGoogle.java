package org.jointheleague.modules;

import java.awt.Color;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchGoogle extends CustomMessageCreateListener {
	
	private static final String COMMAND = "!gsearch";
	private static final String HELP = "!gsearch-help";
	private String help;

	public SearchGoogle(String channelName) {
		super(channelName);
		help = String.join("\n", 
				"[!gsearch] returns the urls of the first few results",
				"with a search using the <keyword> on google.",
				"**Format: ** !gsearch <keyword>",
				"**Warning: ** This command is untested with mac system.");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if(event.getMessageContent().startsWith(HELP)) {
			EmbedBuilder help = new EmbedBuilder().setTitle("!gsearch Help").setColor(Color.blue).setDescription(this.help);
			event.getChannel().sendMessage(help);
		} else if(event.getMessageContent().startsWith(COMMAND)) {
			String[] params = event.getMessageContent().trim().split(" ");
			params[0] = "";
			String keyword = String.join(" ", params);
			
			try {
				String[] results = searchGoogle(keyword);
				String resultsString = "";
				for(String url : results) {
					resultsString += url + "\n";
				}
				EmbedBuilder result = new EmbedBuilder().setTitle("!gsearch Results").setColor(Color.green).setDescription("Search results: \n" + resultsString);
				event.getChannel().sendMessage(result);
			} catch (IOException e) {
				EmbedBuilder error = new EmbedBuilder().setTitle("!gsearch Error").setColor(Color.red).setDescription("[!gsearch Error] An IO error has occured when trying to retrive data.");
				event.getChannel().sendMessage(error);
			}
		}
	}
	
	/**
	* Searches for a result on Google using a keyword
	* @param keyword The keyword to be used to search
	* 
	* @return A String array containing the URLs of the result
	 * @throws IOException When an IO error occurs
	*/
	private String[] searchGoogle(String keyword) throws IOException{
		List<String> resultURLs = new ArrayList<String>();
		String url = "https://www.google.com/search?q=" + keyword;
		Document resultDoc = Jsoup.connect(url).get();
		String resultHtml = resultDoc.html();
		
		Files.write(Paths.get(System.getProperty("user.home") + "\\Downloads\\[!gsearch]html.txt"), resultHtml.getBytes());
		
		Elements results = resultDoc.select("cite");
		for(Element link : results) {
			String resultText = link.text();
			if(resultText.contains("›")) {
				resultText = resultText.replaceAll(" › ", "/");
			}
			resultURLs.add(resultText);
		}
		
		String[] resultArray = resultURLs.toArray(new String[resultURLs.size()]);
		return resultArray;
		
	}
}
