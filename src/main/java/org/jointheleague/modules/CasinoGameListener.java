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


public class CasinoGameListener extends CustomMessageCreateListener{

	final String BET_ALL_COMMAND = "!bet-it-all";
	final String GET_COINS_COMMAND = "!get-my-coins";
	final String HELP_COMMAND = "!casino-help";
	final String BET_COMMAND = "!bet ";
	
	Users users;
	
	public CasinoGameListener(String channelName) {
		super(channelName);
		users = getUsers();
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String message = event.getMessageContent();
		System.out.println(message);
		if (event.getMessageAuthor().asUser().get().isBot()) return;
		if (message.startsWith(BET_ALL_COMMAND)) {
			handleBetItAll(event);
		}
		else if (message.startsWith(BET_COMMAND)
				&& message.length() != BET_COMMAND.length())
		{
			handleBetCommand(event);
		}
		else if (message.startsWith(GET_COINS_COMMAND))
		{
			handleGetMyCoins(event);
		}
		else if (message.startsWith(HELP_COMMAND))
		{
			event.getChannel().sendMessage("The commands are:\n" + 
					BET_ALL_COMMAND + ": that bets all of your coins making you have either double the coins or resets you to 1 coin\n" +
					GET_COINS_COMMAND + ": that tells you how many coins you have\n" + 
					BET_COMMAND + "#: that bets whatever number of coins you put instead of the # and adds double that to your total, or subtracts that from your total");
		}
	}
	
	private void handleBetCommand(MessageCreateEvent event) {
		String s = event.getMessageContent();
		String stringToBet = s.split(" ")[1].replaceAll("[^0-9]", "");
		long coinsToBet = Long.parseLong(stringToBet);
		long coins = getCoins(event);
		if (coins - coinsToBet >= 0)
		{
			coinsToBet=(new Random().nextInt(2)==0) ? coinsToBet : coinsToBet*-1;
			coins+=coinsToBet;
			coins=(coins==0) ? 1 : coins;
		}
		setCoins(coins,event);
		event.getChannel().sendMessage("You now have " + coins + " coins(s).");
	}

	private void handleGetMyCoins(MessageCreateEvent event) {
		event.getChannel().sendMessage("You have " + getCoins(event) + " coin(s).");		
	}

	private void handleBetItAll(MessageCreateEvent event)
	{
		long coins = getCoins(event);
		System.out.println(getCoins(event));
		coins = (new Random().nextInt(2)==0)? coins*3 : 1;
		setCoins(coins, event);
		event.getChannel().sendMessage("You now have " + coins + " coin(s).");
	}
	
	long getCoins(MessageCreateEvent event)
	{
		List<User> lusers = users.getUsers();
		long id = event.getMessageAuthor().asUser().get().getId();
		
		for (int i = 0; i < lusers.size(); i++)
		{
			User currentUser = lusers.get(i);
			if (Long.parseLong(currentUser.getId()) == id)
			{
				System.out.println("user has: " + currentUser.getCoins());
				return Long.parseLong(currentUser.getCoins());
			}
		}
		setCoins(1, event);
		return 1;
	}
	
	void setCoins(long coins, MessageCreateEvent event)
	{
		List<User> lusers = users.getUsers();
		long id = event.getMessageAuthor().asUser().get().getId();
		
		for (int i = 0; i < lusers.size(); i++)
		{
			if (Long.parseLong(lusers.get(i).getId()) == id)
			{
				User replacer = new User();
				replacer.setId("" +  id);
				replacer.setCoins("" + coins);
				lusers.remove(i);
				lusers.add(replacer);
				
				users.setUsers(lusers);
				setUsers(users);
				return;
			}
		}
		//didn't find the user
		User u = new User();
		u.setCoins("" + coins);
		u.setId("" + id);
		
		lusers.add(u);
		users.setUsers(lusers);
		setUsers(users);
	}

	
	private Users getUsers() {
		try (Reader reader = new InputStreamReader(
				Utilities.class.getClassLoader().getResourceAsStream("userinfo.json"))) {
			Gson gson = new GsonBuilder().create();
			System.out.println("Loaded Users");
			return gson.fromJson(reader, Users.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Failed to load Users");
		return null;
	}
	
	private void setUsers(Users s)
	{
		System.out.println("set Users Called");
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
			System.out.println("error saving");
		}
	}
	
}
