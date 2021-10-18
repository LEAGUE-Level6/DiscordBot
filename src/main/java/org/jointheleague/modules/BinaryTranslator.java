package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class BinaryTranslator extends CustomMessageCreateListener{
	//set command to run feature in chat
	private static final String COMMAND = "!binary";
	
	
	public BinaryTranslator(String channelName) {
		super(channelName);
		//description for the !binary command

		helpEmbed = new HelpEmbed(COMMAND, "Will either convert between binary and decimal.  Type '!binary to' to convert from decimal to binary.  Type '!binary from' to convert from binary to decimal");
	} 

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		//splits user entered command by spaces
		String[] cmd = event.getMessageContent().split(" ");
		//looks for first word entered
		//check for !binary command
		if(cmd[0].equals(COMMAND)) {
			if(cmd.length == 3) {
				//check if user wants to go from decimal to binary
				//if second word is "to" convert their number to binary
				if(cmd[1].equals("to")) {
					try { 
						event.getChannel().sendMessage("Here's your binary number " + decimalToBinary(cmd[2]));
					}
					catch (NumberFormatException e) {
						e.printStackTrace(); 
						sendErrorMessage(event);
					} 
				} 
				//check if user wants to go from binary to decimal
				//if second word is "from" convert from their number to decimal
				else if(cmd[1].equals("from")){
					//make sure user enters binary
					if(cmd[2].contains("0") || cmd[2].contains("1")) {
						event.getChannel().sendMessage("Here's your decimal number " + binaryToDecimal(cmd[2]));
					}
					else {
						sendErrorMessage(event); 
					}
				}	
			} 
			else {
				sendErrorMessage(event);
			} 
		}
	} 
	
	//use when user enters incorrect command parameters
	public void sendErrorMessage(MessageCreateEvent event) {
		int randomNum = new Random().nextInt(3);
		if(randomNum == 0) {
			event.getChannel().sendMessage("I don't understand. Please try again");
		}
		else if(randomNum == 1) {
			event.getChannel().sendMessage("What?");
		}
		else {
			event.getChannel().sendMessage("Nope. Not today");
		}
	}
	
	//convert from binary to decimal
	public int binaryToDecimal(String bitString) {
		String reversed = "";
		int sum = 0;
		for(int i = bitString.length()-1; i >=0; i --) {
			reversed += bitString.charAt(i);
		}
		for(int i = 0; i < reversed.length(); i ++) {
			if(reversed.charAt(i) == '1') {
				sum += Math.pow(2, i);
			}
		}
		return sum;
	}
	
	//convert from decimal to binary
	public String decimalToBinary(String decimal) {
		int dec = Integer.parseInt(decimal);

		String binaryStr = "";
        do {
            // 1. Logical right shift by 1
            int quotient = dec >>> 1;
        
            // 2. Check remainder and add '1' or '0'
            if( dec % 2 != 0 ){
                binaryStr = '1' + binaryStr;
            } else {
                binaryStr = '0' + binaryStr;
            }
            
            
            dec = quotient;
            
        // 3. Repeat until number is 0
        } while( dec != 0 );
        while (binaryStr.length() != 8) {
        	binaryStr = '0' + binaryStr;
        }
        return binaryStr;
	}

	public static String getCommand() {
		// TODO Auto-generated method stub
		return COMMAND;
	}
}
