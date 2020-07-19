package org.jointheleague.modules;

import java.math.BigInteger;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;


public class PrimeListener extends CustomMessageCreateListener{
	private static final String COMMAND = "!prime";
	private Random random=new Random();
	public PrimeListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Checks if a number is prime. Example: !prime 8191. If there is no input then it will generate a prime.");
	}
	@Override
	public void handle(MessageCreateEvent event){
		if(event.getMessageContent().contains(COMMAND)) {
			String cmd = event.getMessageContent().replaceAll(" ", "").replace(COMMAND,"");
			if(cmd.equals("")) {
				boolean isPrime=false;
				BigInteger prime=BigInteger.ZERO;
				while(!isPrime) {
					prime=BigInteger.valueOf(Math.abs(random.nextLong()));
					isPrime=prime.isProbablePrime(20);
				}
				event.getChannel().sendMessage(prime.longValueExact()+" has a 99.9999% chance of being prime.");
			}else {
				BigInteger primeCheck=new BigInteger(cmd);
				if(primeCheck.isProbablePrime(20)) {
					event.getChannel().sendMessage(primeCheck+" has a 99.9999% chance of being prime.");
				}else {
					event.getChannel().sendMessage(primeCheck+" is not prime.");
					int check=2;
					long last=0;
					try {
						last=primeCheck.longValueExact();
					}catch(Exception e) {
						last=10000;
					}
					if(last>10000) {
						last=10000;
					}
					while(check<=last) {
						if(primeCheck.mod(BigInteger.valueOf(check))==BigInteger.ZERO) {
							event.getChannel().sendMessage(primeCheck+" is divisible by "+check+".");
						}
						check++;
					}
				}
			}
		}
		
	}
	
}
