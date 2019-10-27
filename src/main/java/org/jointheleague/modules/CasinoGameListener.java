package org.jointheleague.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.discord_bot_example.BotInfo;
import org.jointheleague.discord_bot_example.Utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.aksingh.owmjapis.api.APIException;
import sun.reflect.Reflection;

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
		System.out.println(message);
		if (event.getMessageAuthor().asUser().get().isBot()) return;
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
					BET_ALL_COMMAND + ": that bets all of your coins\n" +
					GET_COINS_COMMAND + ": that tells you how many coins you have\n" + 
					BET_COMMAND + "#: that bets whatever number of coins you put instead of the #" );
		}
	}
	
	private void handleGetMyCoins(MessageCreateEvent event) {
		event.getChannel().sendMessage("You have " + getCoins(event) + " coin(s).");		
	}

	private void handleBetItAll(MessageCreateEvent event)
	{
		long coins = getCoins(event);
		System.out.println(getCoins(event));
		coins = (new Random().nextInt(2)==0) ? 1 : coins*2;
		long user = event.getMessageAuthor().asUser().get().getId();
		setCoins(user, coins);
		event.getChannel().sendMessage("You now have: " + coins + " coin(s).");
	}
	
	private void setCoins(long id, long coins) {
		Users users = getUsers();
		boolean changed = false;
		for (int i = 0; i < users.getUsers().size(); i++)
		{
			User currentUser = users.getUsers().get(i);
			if (Long.parseLong(currentUser.getId()) == id)
			{ 
				User change = users.getUsers().get(i);
				change.setCoins("" + coins);
				users.getUsers().set(i, change);
				changed = true;
			}
		}
		if (changed == false)
		{
			User u = new User();
			u.setId("" + id);
			u.setCoins("" + coins);
			List<User> lUsers = users.getUsers();
			lUsers.add(u);
			users.setUsers(lUsers);
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
			PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/userinfo.json", false));
			System.out.println(str);
			out.print(str);
			out.close();
			System.out.println("saved");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
