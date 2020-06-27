package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Economy extends CustomMessageCreateListener {
	
	
	
	
	//Type eco info to use the bot
	
	
	
	static boolean start = false;
	private HashMap<String, Double> moneyMap = new HashMap<String, Double>();;
	private HashMap<String, String> jobMap;
	private HashMap<String, Long> workTimer;

	private List<String> people = new ArrayList<String>();
	private final String HELPCMD = "eco info";
	private final String REGISTERCMD = "eco register";
	private final String BALCMD = "eco bal";
	private final String JOBSCMD = "eco jobs";
	private final String WORKCMD = "eco work";
	private final String PEEPSCMD = "eco people";
	private final String GIVEMONEYCMD = "eco give";
	ArrayList<String> jobs = new ArrayList<String>();

	public Economy(String channelName) {

		super(channelName);
		initialize();
	}

	@Override
	public void handle(MessageCreateEvent e) throws APIException {

		String author = e.getMessageAuthor().getName().toString();
		if (author.equals("Jrcodd's Bot")) {
			return;
		}
		if (e.getMessageContent().equalsIgnoreCase(HELPCMD)) {
			e.getChannel().sendMessage("__**Valid Commands:**__\n"
					+ "**eco register** - register your account into the economy (required to do any commands)\n"
					+ "**eco bal** - check how much money you have\n" + "**eco jobs** - show job info (make money)\n"
					+ "**eco work** - gain money from your job\n"
					+ "**eco people** - list all the currently registered users\n"
					+ "**eco give** - give money to another registered user\n"
					+ "__**Creater:**__ Jackson Codd");
		}

		if (e.getMessageContent().contains(REGISTERCMD)) {

			if (!moneyMap.containsKey(author)) {
				try {
					FileWriter fw = new FileWriter("EconomyData.txt", true);
					fw.write(author + ": " + 0.0 + "-" + "\n");
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (!people.contains(author)) {
					people.add(author);
				}
				moneyMap.put(author, 0.0);

				e.getChannel().sendMessage(author + " is now registered in the economy!");
			} else {
				e.getChannel().sendMessage(author + " is already registered in the economy... smh");
			}

		}
		if (e.getMessageContent().equalsIgnoreCase(BALCMD)) {
			e.getChannel().sendMessage(author + ", you have $" + moneyMap.get(author));
		}

		if (e.getMessageContent().contains(JOBSCMD)) {
			boolean inJob = false;

			String cmd = e.getMessageContent().trim().replace(JOBSCMD, "");
			if (jobMap.containsKey(author) && jobs.contains(jobMap.get(author))) {
				inJob = true;
			}
			if (cmd.equals("")) {
				String jobsMessage = "You can join a job by typing **eco jobs join (job)** the available jobs are: ";
				for (String string : jobs) {
					if (jobs.indexOf(string) == jobs.size() - 1) {
						jobsMessage += "and " + string;
					} else {
						jobsMessage += string + ", ";
					}
				}
				e.getChannel().sendMessage(jobsMessage);
			} else if (cmd.trim().contains("join")) {
				cmd = cmd.trim().replace("join", "");
				String param = cmd.substring(0).trim();
				if (jobs.contains(param)) {
					if (!inJob) {

						e.getChannel().sendMessage("You have joined the job: " + param + "!");
						jobMap.put(author, param);
						replaceLine(people.indexOf(author), author + ": " + moneyMap.get(author) + "-" + param);

					} else {
						e.getChannel().sendMessage("You have already joined " + jobMap.get(author) + "!");
					}
					System.out.println(param);

				}
			}
		}
		if (e.getMessageContent().equalsIgnoreCase(WORKCMD)) {

			if (jobMap.containsKey(author)) {
				if (!workTimer.containsKey(author)) {
					workTimer.put(author, System.nanoTime());
				}

				if (getWorkTimeSeconds(author) >= 60) {

					Random r = new Random();
					Double moneyEarned = r.nextInt(20) + r.nextInt(10) * 0.1 + r.nextInt(10) * 0.01;
					DecimalFormat df = new DecimalFormat("#.##");
					moneyEarned = Double.parseDouble(df.format(moneyEarned));
					e.getChannel().sendMessage("you have earned " + "$" + moneyEarned + " by " + jobMap.get(author));
					moneyMap.put(author, (moneyMap.get(author) + moneyEarned));
					replaceLine(people.indexOf(author),
							author + ": " + moneyMap.get(author) + "-" + jobMap.get(author));
					workTimer.put(author, System.nanoTime());
				} else {
					e.getChannel().sendMessage("you need to wait until you can do this again! (╯°□°）╯︵ ┻━┻ "
							+ "pssst wait " + (60L - getWorkTimeSeconds(author)) + " more seconds.");
				}
			} else {
				e.getChannel().sendMessage("You need to be in a job in order to use that!");

			}

		}
		if (e.getMessageContent().equalsIgnoreCase(PEEPSCMD)) {
			e.getChannel().sendMessage("**Registered People:**\n");
			for (String string : people) {
				e.getChannel().sendMessage(string);
			}
		}
		if (e.getMessageContent().contains(GIVEMONEYCMD)) {
			String cmd = e.getMessageContent().trim().replace(GIVEMONEYCMD, "");
			if (cmd.trim().equals("")) {
				e.getChannel().sendMessage("Usage: **eco give <recipient> <amount>**");
			} else {
				String sender = author;
				String reciever = "null";
				double amount = 0;
				boolean transfer = true;
				for (String s : people) {
					if (cmd.contains(s)) {
						reciever = s;
					}
				}

				if (reciever == "null") {
					e.getChannel().sendMessage("Incorrect usage reciever does not exist");
					transfer = false;
				} else {
					try {
						cmd = cmd.trim().replace(reciever, "");
						amount = Double.parseDouble(cmd);

					} catch (NumberFormatException e1) {
						e1.printStackTrace();
						e.getChannel().sendMessage("Invalid amount");
						transfer = false;
					}
					if (reciever.trim().equalsIgnoreCase(sender.trim())) {
						e.getChannel().sendMessage("You cannot transfer money to yourself XD");
						transfer = false;
					}
					if (moneyMap.get(sender) < amount) {
						e.getChannel().sendMessage("Insufficient Funds");
					}
					if (transfer) {
						moneyMap.put(reciever, (moneyMap.get(reciever) + amount));
						moneyMap.put(sender, (moneyMap.get(sender) - amount));

						replaceLine(people.indexOf(reciever),
								reciever + ": " + moneyMap.get(reciever) + "-" + jobMap.get(reciever));
						replaceLine(people.indexOf(sender),
								sender + ": " + moneyMap.get(sender) + "-" + jobMap.get(sender));
						e.getChannel().sendMessage("You have successfully transferred  $" + amount + " from " + sender
								+ " to " + reciever);

					}
				}
			}
		}

	}

	private void initialize() {
		moneyMap = new HashMap<String, Double>();
		jobMap = new HashMap<String, String>();
		workTimer = new HashMap<String, Long>();
		jobs.add("farming");
		jobs.add("foraging");
		jobs.add("fishing");
		jobs.add("programming");
		jobs.add("cooking");

		try {
			BufferedReader br = new BufferedReader(new FileReader("EconomyData.txt"));
			String currentLine = br.readLine();
			while (currentLine != null) {
				String name = currentLine.substring(0, currentLine.indexOf(':'));
				double money = Double.parseDouble(
						currentLine.substring((currentLine.indexOf(':') + 1), currentLine.indexOf('-')).trim());
				moneyMap.put(name, money);
				String job = currentLine.substring(currentLine.indexOf('-') + 1).trim();
				if (!jobMap.containsKey(name)) {
					if (jobs.contains(job))
						jobMap.put(name, job);
				}

				people.add(name);

				currentLine = br.readLine();
			}
		} catch (IOException e2) {

			e2.printStackTrace();
		}
	}

	public static void replaceLine(int lineToReplace, String replaceWith) {

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("EconomyData.txt"));
			ArrayList<String> fileContents = new ArrayList<String>();
			String line;
			line = br.readLine();
			while (line != null) {
				fileContents.add(line);
				line = br.readLine();
			}

			fileContents.set(lineToReplace, replaceWith);

			FileWriter fw = new FileWriter("EconomyData.txt");

			for (String string : fileContents) {
				if (!string.endsWith("\n")) {
					string += "\n";
				}
				fw.write(string);
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public long getWorkTimeSeconds(String author) {
		long elapsedTimeNano = System.nanoTime() - workTimer.get(author);
		long elapsedTimeSeconds = (elapsedTimeNano / 1_000_000_000L);
		return elapsedTimeSeconds;

	}
}
