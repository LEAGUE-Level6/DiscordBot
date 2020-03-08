package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class Bot1Listener extends CustomMessageCreateListener {
ArrayList<Person> arr = new ArrayList<Person>();
long time = System.nanoTime();
long id = 0;
String special = ""; boolean watch = false;
	public Bot1Listener(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		//622894941667983404
		if(event.getMessageAuthor().getId()==id&&watch) {
			if(System.nanoTime()-3000000000L<=time) {
				if(event.getMessageContent().equals(special)) {
					event.getChannel().sendMessage("You barely swim to the surface and survive. You get 1500 coins for your fruitless toil.");
					try {
						long cc = 0;
						long luck = 0;
						int save=0;
						boolean found=false;
						BufferedReader r = new BufferedReader(new FileReader("src/main/resources/Untitled 1.txt"));
						String str=r.readLine();
						arr.clear();
						while(str!=null) {
							arr.add(new Person(Long.parseLong(str), Long.parseLong(r.readLine()), Long.parseLong(r.readLine())));
							str=r.readLine();
						}
							if(arr.get(i).id==event.getMessageAuthor().getId()) {
								save=i;
								cc = arr.get(i).cc+1500;
								luck = arr.get(i).luck;
								found=true;
							}
						}
						if(!found) {
							event.getChannel().sendMessage("I've never seen your face before, take these coins and leave my family alone");
							FileWriter temp = new FileWriter("src/main/resources/Untitled 1.txt", true);
							temp.write(event.getMessageAuthor().getId()+"\n"+6969+"\n"+0+"\n");
							temp.close();
							watch=false;
							return;
						}
						r.close();
						FileWriter fw = new FileWriter("src/main/resources/Untitled 1.txt", false);
						arr.get(save).set(cc);
						arr.get(save).setL(luck);
						for(Person p : arr) {
							fw.write(p.toString());
						}
						fw.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else {
				event.getChannel().sendMessage("*Ỹ̸̬̞̮̻̻̪̖̼͍̝O̴̞͕̭̠͙̦̣͍͐̓̐̆̂̔̐̉̕͝U̶̡̳͇̗̘̰̻͌ ̸̛̟̱̼̲̩̪͚̔́̍̅̌̓̄͜͝D̸̤̲̍͑̉͆͜I̵̡͓̤̰̳̼̅̕Ē̶̫̜̩̲D̷̛͎̟̓͠*");
			}
			watch=false;
		}
		if (event.getMessageContent().contains("*bet")) {
			// -------------------------------------------------------
			long cc = 0;
			long luck = 0;
			int save=0;
			boolean found=false;
			try {
				BufferedReader r = new BufferedReader(new FileReader("src/main/resources/Untitled 1.txt"));
				String str=r.readLine();
				arr.clear();
				while(str!=null) {
					arr.add(new Person(Long.parseLong(str), Long.parseLong(r.readLine()), Long.parseLong(r.readLine())));
					str=r.readLine();
				}
				for(int i = 0; i < arr.size(); i++) {
					if(arr.get(i).id==event.getMessageAuthor().getId()) {
						save=i;
						cc = arr.get(i).cc;
						luck = arr.get(i).luck;
						found=true;
					}
				}
				if(!found) {
					event.getChannel().sendMessage("I've never seen your face before, take these coins and leave my family alone");
					FileWriter temp = new FileWriter("src/main/resources/Untitled 1.txt", true);
					temp.write(event.getMessageAuthor().getId()+"\n"+6969+"\n"+0+"\n");
					temp.close();
					return;
				}
				r.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileWriter fw = null;
			try {
				fw = new FileWriter("src/main/resources/Untitled 1.txt", false);
			} catch (IOException e2) {
				event.getChannel().sendMessage("Yikes, something mega wack");
			}

			try {
				String[] m = event.getMessageContent().split(" ");
				long am = Integer.parseInt(m[1]);
				if (am <= cc&&am > 0) {
					int second = 0;
					Random r = new Random();
					int first = r.nextInt(19) + 1;
					if (luck > 0) {
						event.getChannel().sendMessage("Haha! You feel like you could roll a 6!");
						second = r.nextInt(14) + 1;
					} else {
						second = r.nextInt(17) + 1;
					}
					
					
					event.getChannel().sendMessage("Your number was " + first + ", and my number was " + second + ".");
					if (first > second) {
						int multi = 0;
						if(luck>0) {
							multi = r.nextInt(90) + 40;
						}else {
							multi = r.nextInt(60) + 70;
						}
						event.getChannel().sendMessage(
								"Darn! Your number was a smidge more than mine! Your multiplier was " + multi + ".");
						cc += am * multi * 0.01;
						if (luck > 0) {
							luck--;
						}
						arr.get(save).set(cc);
						arr.get(save).setL(luck);
						for(Person p : arr) {
							fw.write(p.toString());
						}
						fw.close();

					} else {
						event.getChannel().sendMessage("Haha! I win uwu");
						cc -= am;
						if (luck > 0) {
							luck--;
						}
						arr.get(save).set(cc);
						arr.get(save).setL(luck);
						for(Person p : arr) {
							fw.write(p.toString());
						}
						fw.close();
					}
				}else {
					event.getChannel().sendMessage("Try again, but this time use ya brain");
					arr.get(save).set(cc);
					arr.get(save).setL(luck);
					for(Person p : arr) {
						fw.write(p.toString());
					}
					fw.close();
				}
			} catch (Exception e) {
				event.getChannel().sendMessage("Try again, but this time use ya brain");
			}
		}

		// -----------------------------------------------------

	if (event.getMessageContent().equals("*bal")) {
			long cc = 0;
			long luck = 0;
			boolean found=false;
			try {
				BufferedReader r = new BufferedReader(new FileReader("src/main/resources/Untitled 1.txt"));
				String str=r.readLine();
				arr.clear();
				while(str!=null) {
					arr.add(new Person(Long.parseLong(str), Long.parseLong(r.readLine()), Long.parseLong(r.readLine())));
					str=r.readLine();
				}
				for(Person p : arr) {
					if(p.id==event.getMessageAuthor().getId()) {
						cc = p.cc;
						luck = p.luck;
						found=true;
					}
				}
				if(!found) {
					event.getChannel().sendMessage("I've never seen your face before, take these coins and leave my family alone");
					FileWriter temp = new FileWriter("src/main/resources/Untitled 1.txt", true);
					temp.write(event.getMessageAuthor().getId()+"\n"+6969+"\n"+0+"\n");
					temp.close();
					return;
				}
				r.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			event.getChannel().sendMessage("Your current balance of CodingCoins is " + cc + ", and you have "+luck+" more lucky bets. lmaoooo git good");
		}
	
	//----------------------------------------------------------
		//------------------------------------------------------
		if (event.getMessageContent().equals("*shop")) {
			event.getChannel().sendMessage("𝘜𝘴𝘦 𝘤𝘰𝘮𝘮𝘢𝘯𝘥 *𝘣𝘶𝘺 𝘵𝘰 𝘱𝘶𝘳𝘤𝘩𝘢𝘴𝘦 𝘢𝘯 𝘪𝘵𝘦𝘮.");
			event.getChannel().sendMessage("There are the following items in the shop:");
			event.getChannel().sendMessage("DICE 🎲: LUCK++ FOR 2-12 BETS");
			event.getChannel().sendMessage("ID: dice");
			event.getChannel().sendMessage("PRICE: 5000 CC");
		}
		
		if(event.getMessageContent().contains("*work")) {
			time = System.nanoTime();
			id = event.getMessageAuthor().getId();
			watch = true;
			event.getChannel().sendMessage("You found a quirky gig to get money for holding your breath. All goes well until *AHHHHHH*cough*HHHH* SAVE YOUR LIFE AND TYPE THE FOLLOWING MESSAGE IN THE NEXT 3 SECONDS");
			Random r = new Random();
			int rand = r.nextInt(3);
			switch(rand) {
			case 0: event.getChannel().sendMessage("||DROWNING||"); special = "DROWNING";break;
			case 1: event.getChannel().sendMessage("||HELP ME||"); special = "HELP ME";break;
			case 2: event.getChannel().sendMessage("||CANNOT BREATHE||"); special = "CANNOT BREATHE";break;
			}
			
		}
		if (event.getMessageContent().contains("*buy")) {
			long cc = 0;
			long luck = 0;
			boolean found=false;
			int save=0;
			try {
				BufferedReader r = new BufferedReader(new FileReader("src/main/resources/Untitled 1.txt"));
				String str=r.readLine();
				arr.clear();
				while(str!=null) {
					arr.add(new Person(Long.parseLong(str), Long.parseLong(r.readLine()), Long.parseLong(r.readLine())));
					str=r.readLine();
				}
				
				for(int i= 0; i < arr.size(); i++) {
					if(arr.get(i).id==event.getMessageAuthor().getId()) {
						save = i;
						cc = arr.get(i).cc;
						luck = arr.get(i).luck;
						found=true;
					}
				}
				if(!found) {
					event.getChannel().sendMessage("I've never seen your face before, take these coins and leave my family alone");
					FileWriter temp = new FileWriter("src/main/resources/Untitled 1.txt", true);
					temp.write(event.getMessageAuthor().getId()+"\n"+6969+"\n"+0+"\n");
					temp.close();
					return;
				}
				r.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] m = event.getMessageContent().split(" ");
			String id = m[1];
			if(id.equals("dice")&&cc>=5000) {
				event.getChannel().sendMessage("You got a dice!");
				Random r = new Random();
				int l = r.nextInt(6)+1;
				event.getChannel().sendMessage("You roll your new dice, hitting a "+l+".");
				event.getChannel().sendMessage("Since you got a "+l+", you get a luck boost that lasts "+l*2+" bets.");
				luck+=l*2;
				cc-=5000;
				try {
					FileWriter fw = new FileWriter("src/main/resources/Untitled 1.txt");
					arr.get(save).set(cc);
					arr.get(save).setL(luck);
					for(Person p : arr) {
						fw.write(p.toString());
					}
					fw.close();
				}catch(IOException e) {
					event.getChannel().sendMessage("Yikes, something mega wack");
				}
			}
		}
	}

}
class Person {
	long id;
	long cc, luck;
	public Person(long a, long b, long c) {
		id =a;
		cc=b;
		luck=c;
	}
	public void set(long c) {
		cc=c;
	}
	public void setL(long c) {
		luck=c;
	}
	public String toString() {
		return id+"\n"+cc+"\n"+luck+"\n";
	}
}