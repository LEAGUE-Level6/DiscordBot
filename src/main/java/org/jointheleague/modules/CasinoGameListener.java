package org.jointheleague.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discord_bot_example.BotInfo;
import org.jointheleague.discord_bot_example.Utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.aksingh.owmjapis.api.APIException;

public class CasinoGameListener extends CustomMessageCreateListener{

	final String BET_ALL_COMMAND = "!bet-it-all";
	final String GET_COINS_COMMAND = "!get-my-coins";
	final String HELP_COMMAND = "!casino-help";
	final String BET_COMMAND = "!bet ";
	
	public CasinoGameListener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		if (message.startsWith(BET_ALL_COMMAND)) {
			handleBetItAll(event);
		}
		else if (message.startsWith(GET_COINS_COMMAND))
		{
			handleGetMyCoins(event);
		}
		else if (message.startsWith(HELP_COMMAND))
		{
			event.getChannel().sendMessage("The commands are:\n" + 
					BET_ALL_COMMAND + " which bets all of your coins");
		}
	}
	
	private void handleGetMyCoins(MessageCreateEvent event) {
		event.getChannel().sendMessage("You have " + getCoins(event) + " coin(s).");		
	}

	private void handleBetItAll(MessageCreateEvent event)
	{
		long l = getCoins(event);
		l = (new Random().nextInt(2)==0) ? 1 : l*2;
		long user = event.getMessageAuthor().asUser().get().getId();
		setCoins(user, l);
		event.getChannel().sendMessage("You now have: " + l + " coin(s).");
	}
	
	private void setCoins(long id, long coins) {
		Users users = getUsers();
		for (int i = 0; i < users.getUsers().size(); i++)
		{
			User currentUser = users.getUsers().get(i);
			if (Long.parseLong(currentUser.getId()) == id)
			{
				users.getUsers().get(i).setCoins("" + coins);
			}
		}
		Save(users);
	}

	private long getCoins(MessageCreateEvent event)
	{
		long user = event.getMessageAuthor().asUser().get().getId();
		Users s = getUsers();
		User currentUser = null;
		for (int i = 0; i < s.getUsers().size(); i++)
		{
			if (Long.parseLong(s.getUsers().get(i).getId()) == user)
			{
				currentUser = s.getUsers().get(i);
			}
		}
		if (currentUser == null)
		{
			User nu = new User();
			nu.setId("" + user);
			nu.setCoins("1");
			s.getUsers().add(nu);
			Save(s);
			return 1;
		}
		return Long.parseLong(currentUser.getCoins());
	}

	
	private Users getUsers() {
		try (Reader reader = new InputStreamReader(
				Utilities.class.getClassLoader().getResourceAsStream("userinfo.json"))) {
			System.out.println("Loading users");
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(reader, Users.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Failed to load users");
		return null;
	}
	
	private void Save(Users s)
	{
		Gson gson = new GsonBuilder().create();
		String str = gson.toJson(s);
		try {
			PrintWriter out = new PrintWriter(new File("userinfo.json"));
			out.print(str);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
