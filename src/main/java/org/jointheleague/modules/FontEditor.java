package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class FontEditor extends CustomMessageCreateListener {

	public FontEditor(String channelName) {
		super(channelName);
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String m = event.getMessageContent();
		if(m.toLowerCase().contains("!font")) {
			String x=event.getMessageContent();
			int comma =x.indexOf(',');
				String message=x.substring(comma+1,x.length());
				String finalMessage=message.trim();
				for(int i = 0; i<getSpacing(finalMessage).size();i++) {
				event.getChannel().sendMessage(getSpacing(finalMessage).get(i));
				}
		}
		else if(m.toLowerCase().contains("!big")) {
			String x = event.getMessageContent();
			int comma=x.indexOf(',');
			//message = message2
			String message=x.substring(comma+1,x.length());
			String finalMessage=message.trim();
			for(int i = 0; i<getSpacingBigger(finalMessage).size();i++) {
				event.getChannel().sendMessage(getSpacingBigger(finalMessage).get(i));
				}
		}
	}
	ArrayList<String> getSpacing(String s) {	
		ArrayList<String> sentences = new ArrayList<String>(); 
			if(s.length()==1) {
				sentences.add(getLetters(s));
			}
			else if(!s.contains(" ")){
				for(int i =1;i<s.length();i++) {
					if(i%13==0) {
						String sub = s.substring(0, i);
						sentences.add(getLetters(sub));
						s=s.substring(i, s.length());
						i=0;
					}
					if(s.length()<=13) {
						sentences.add(getLetters(s));
						break;
					}
				}
			}
			else {
				for(int i = 1;i<s.length();i++) {
					boolean ifSpace = false; 
					if(i%13==0) {
						int x = i; 
						while(!ifSpace) {
							if(s.charAt(x)==' ') {
								ifSpace=true;
							}
							else {
							 x--;
							}
						}
						String sub = s.substring(0,x);
						String sub2=sub.trim();
						sentences.add(getLetters(sub2));
						s= s.substring(x, s.length());
						i=0;
					}
				}
				if(s.length()<=13) {
					sentences.add(getLetters(s));
				}
		
			}
			return sentences;
	}
	ArrayList<String> getSpacingBigger(String s) {		
		ArrayList<String> sentences = new ArrayList<String>(); 
			if(s.length()==1) {
				sentences.add(getLettersBigger(s));
			}
			else if(!s.contains(" ")){
				for(int i =1;i<s.length();i++) {
					if(i%8==0) {
						String sub = s.substring(0, i);
						sentences.add(getLettersBigger(sub));
						s=s.substring(i, s.length());
						i=0;
					}
					if(s.length()<=8) {
						sentences.add(getLettersBigger(s));
						break;
					}
				}
			}
			else {
				for(int i = 1;i<s.length();i++) {
					boolean ifSpace = false; 
					if(i%8==0) {
						int x = i; 
						while(!ifSpace) {
							if(s.charAt(x)==' ') {
								ifSpace=true;
							}
							else {
							 x--;
							}
						}
						String sub = s.substring(0,x);
						String sub2=sub.trim();
						sentences.add(getLettersBigger(sub2));
						s= s.substring(x, s.length());
						i=0;
					}
				}
				if(s.length()<=8) {
					sentences.add(getLettersBigger(s));
				}
		
			}
			return sentences;
	}
	String getLetters(String s) {

		String message="";
		String messageLineOne="";
		String messageLineTwo="";
		String messageLineThree="";
		String messageLineFour="";
		String messageLineFive="";
		String messageLineSix="";
		String messageLineSeven="";
		String sLower=s.toLowerCase();
		for(int i =0;i<s.length();i++) {
			if(sLower.charAt(i)=='a') {
				messageLineOne=messageLineOne+"────"; 
			}
			if(sLower.charAt(i)=='b') {
				messageLineOne=messageLineOne+"╔╗──";
			}
			if(sLower.charAt(i)=='c') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='d') {
				messageLineOne=messageLineOne+"──╔╗";
			}
			if(sLower.charAt(i)=='e') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='f') {
				messageLineOne=messageLineOne+"─╔═╗";
			}
			if(sLower.charAt(i)=='g') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='h') {
				messageLineOne=messageLineOne+"╔╗──";
			}
			if(sLower.charAt(i)=='i') {
				messageLineOne=messageLineOne+"──";
			}
			if(sLower.charAt(i)=='j') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='k') {
				messageLineOne=messageLineOne+"╔╗──";
			}
			if(sLower.charAt(i)=='l') {
				messageLineOne=messageLineOne+"╔╗──";
			}
			if(sLower.charAt(i)=='m') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='n') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='o') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='p') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='q') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='r') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='s') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='t') {
				messageLineOne=messageLineOne+"─╔╗─";
			}
			if(sLower.charAt(i)=='u') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='v') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='w') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='x') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='y') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)=='z') {
				messageLineOne=messageLineOne+"────";
			}
			if(sLower.charAt(i)==' ') {
				messageLineOne = messageLineOne+"──";
			}
				//line two
				//line two
			if(sLower.charAt(i)=='a') {
				messageLineTwo=messageLineTwo+"────"; 
			}	
			if(sLower.charAt(i)=='b') {
				messageLineTwo=messageLineTwo+"║║──";
			}
			if(sLower.charAt(i)=='c') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='d') {
				messageLineTwo=messageLineTwo+"──║║";
			}
			if(sLower.charAt(i)=='e') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='f') {
				messageLineTwo=messageLineTwo+"─║╔╝";
			}
			if(sLower.charAt(i)=='g') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='h') {
				messageLineTwo=messageLineTwo+"║║──";
			}
			if(sLower.charAt(i)=='i') {
				messageLineTwo=messageLineTwo+"╔╗";
			}
			if(sLower.charAt(i)=='j') {
				messageLineTwo=messageLineTwo+"─╔╗─";
			}
			if(sLower.charAt(i)=='k') {
				messageLineTwo=messageLineTwo+"║║──";
			}
			if(sLower.charAt(i)=='l') {
				messageLineTwo=messageLineTwo+"║║──";
			}
			if(sLower.charAt(i)=='m') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='n') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='o') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='p') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='q') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='r') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='s') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='t') {
				messageLineTwo=messageLineTwo+"╔╝╚╗";
			}
			if(sLower.charAt(i)=='u') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='v') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='w') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='x') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='y') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)=='z') {
				messageLineTwo=messageLineTwo+"────";
			}
			if(sLower.charAt(i)==' ') {
				messageLineTwo = messageLineTwo+"──";
			}
			//line three
			//line three
			if(sLower.charAt(i)=='a') {
				messageLineThree=messageLineThree+"╔══╗"; 
			}	
			if(sLower.charAt(i)=='b') {
				messageLineThree=messageLineThree+"║╚═╗";
			}
			if(sLower.charAt(i)=='c') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='d') {
				messageLineThree=messageLineThree+"╔═╝║";
			}
			if(sLower.charAt(i)=='e') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='f') {
				messageLineThree=messageLineThree+"╔╝╚╗";
			}
			if(sLower.charAt(i)=='g') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='h') {
				messageLineThree=messageLineThree+"║╚═╗";
			}
			if(sLower.charAt(i)=='i') {
				messageLineThree=messageLineThree+"╚╝";
			}
			if(sLower.charAt(i)=='j') {
				messageLineThree=messageLineThree+"─╚╝─";
			}
			if(sLower.charAt(i)=='k') {
				messageLineThree=messageLineThree+"║║╔╗";
			}
			if(sLower.charAt(i)=='l') {
				messageLineThree=messageLineThree+"║║──";
			}
			if(sLower.charAt(i)=='m') {
				messageLineThree=messageLineThree+"╔╗╔╗";
			}
			if(sLower.charAt(i)=='n') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='o') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='p') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='q') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='r') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='s') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)=='t') {
				messageLineThree=messageLineThree+"╚╗╔╝";
			}
			if(sLower.charAt(i)=='u') {
				messageLineThree=messageLineThree+"╔╗╔╗";
			}
			if(sLower.charAt(i)=='v') {
				messageLineThree=messageLineThree+"╔╗╔╗";
			}
			if(sLower.charAt(i)=='w') {
				messageLineThree=messageLineThree+"╔╦╦╗";
			}
			if(sLower.charAt(i)=='x') {
				messageLineThree=messageLineThree+"╔╗╔╗";
			}
			if(sLower.charAt(i)=='y') {
				messageLineThree=messageLineThree+"╔╗╔╗";
			}
			if(sLower.charAt(i)=='z') {
				messageLineThree=messageLineThree+"╔══╗";
			}
			if(sLower.charAt(i)==' ') {
				messageLineThree = messageLineThree+"──";
			}
			//line four
			//line four
			if(sLower.charAt(i)=='a') {
				messageLineFour=messageLineFour+"║╔╗║"; 
			}	
			if(sLower.charAt(i)=='b') {
				messageLineFour=messageLineFour+"║╔╗║";
			}
			if(sLower.charAt(i)=='c') {
				messageLineFour=messageLineFour+"║╔═╝";
			}
			if(sLower.charAt(i)=='d') {
				messageLineFour=messageLineFour+"║╔╗║";
			}
			if(sLower.charAt(i)=='e') {
				messageLineFour=messageLineFour+"║║═╣";
			}
			if(sLower.charAt(i)=='f') {
				messageLineFour=messageLineFour+"╚╗╔╝";
			}
			if(sLower.charAt(i)=='g') {
				messageLineFour=messageLineFour+"║╔╗║";
			}
			if(sLower.charAt(i)=='h') {
				messageLineFour=messageLineFour+"║╔╗║";
			}
			if(sLower.charAt(i)=='i') {
				messageLineFour=messageLineFour+"╔╗";
			}
			if(sLower.charAt(i)=='j') {
				messageLineFour=messageLineFour+"─╔╗─";
			}
			if(sLower.charAt(i)=='k') {
				messageLineFour=messageLineFour+"║╚╝╝";
			}
			if(sLower.charAt(i)=='l') {
				messageLineFour=messageLineFour+"║║──";
			}
			if(sLower.charAt(i)=='m') {
				messageLineFour=messageLineFour+"║╚╝║";
			}
			if(sLower.charAt(i)=='n') {
				messageLineFour=messageLineFour+"║╔╗║";
			}
			if(sLower.charAt(i)=='o') {
				messageLineFour=messageLineFour+"║╔╗║";
			}
			if(sLower.charAt(i)=='p') {
				messageLineFour=messageLineFour+"║╔╗║";
			}
			if(sLower.charAt(i)=='q') {
				messageLineFour=messageLineFour+"║╔╗║";
			}
			if(sLower.charAt(i)=='r') {
				messageLineFour=messageLineFour+"║╔═╝";
			}
			if(sLower.charAt(i)=='s') {
				messageLineFour=messageLineFour+"║╚═╣";
			}
			if(sLower.charAt(i)=='t') {
				messageLineFour=messageLineFour+"─║║─";
			}
			if(sLower.charAt(i)=='u') {
				messageLineFour=messageLineFour+"║║║║";
			}
			if(sLower.charAt(i)=='v') {
				messageLineFour=messageLineFour+"║║║║";
			}
			if(sLower.charAt(i)=='w') {
				messageLineFour=messageLineFour+"║║║║";
			}
			if(sLower.charAt(i)=='x') {
				messageLineFour=messageLineFour+"╚╬╬╝";
			}
			if(sLower.charAt(i)=='y') {
				messageLineFour=messageLineFour+"║╚╝║";
			}
			if(sLower.charAt(i)=='z') {
				messageLineFour=messageLineFour+"╠═╝║";
			}
			if(sLower.charAt(i)==' ') {
				messageLineFour = messageLineFour+"──";
			}
			//line five
			//line five
			if(sLower.charAt(i)=='a') {
				messageLineFive=messageLineFive+"║╔╗║"; 
			}	
			if(sLower.charAt(i)=='b') {
				messageLineFive=messageLineFive+"║╚╝║";
			}
			if(sLower.charAt(i)=='c') {
				messageLineFive=messageLineFive+"║╚═╗";
			}
			if(sLower.charAt(i)=='d') {
				messageLineFive=messageLineFive+"║╚╝║";
			}
			if(sLower.charAt(i)=='e') {
				messageLineFive=messageLineFive+"║║═╣";
			}
			if(sLower.charAt(i)=='f') {
				messageLineFive=messageLineFive+"─║║─";
			}
			if(sLower.charAt(i)=='g') {
				messageLineFive=messageLineFive+"╚═╗║";
			}
			if(sLower.charAt(i)=='h') {
				messageLineFive=messageLineFive+"║║║║";
			}
			if(sLower.charAt(i)=='i') {
				messageLineFive=messageLineFive+"║║";
			}
			if(sLower.charAt(i)=='j') {
				messageLineFive=messageLineFive+"─║║─";
			}
			if(sLower.charAt(i)=='k') {
				messageLineFive=messageLineFive+"║╔╗╗";
			}
			if(sLower.charAt(i)=='l') {
				messageLineFive=messageLineFive+"║╚═╗";
			}
			if(sLower.charAt(i)=='m') {
				messageLineFive=messageLineFive+"║║║║";
			}
			if(sLower.charAt(i)=='n') {
				messageLineFive=messageLineFive+"║║║║";
			}
			if(sLower.charAt(i)=='o') {
				messageLineFive=messageLineFive+"║╚╝║";
			}
			if(sLower.charAt(i)=='p') {
				messageLineFive=messageLineFive+"║╔═╝";
			}
			if(sLower.charAt(i)=='q') {
				messageLineFive=messageLineFive+"╚═╗║";
			}
			if(sLower.charAt(i)=='r') {
				messageLineFive=messageLineFive+"║║──";
			}
			if(sLower.charAt(i)=='s') {
				messageLineFive=messageLineFive+"╠═╝║";
			}
			if(sLower.charAt(i)=='t') {
				messageLineFive=messageLineFive+"─║║─";
			}
			if(sLower.charAt(i)=='u') {
				messageLineFive=messageLineFive+"║╚╝║";
			}
			if(sLower.charAt(i)=='v') {
				messageLineFive=messageLineFive+"╚╗╔╝";
			}
			if(sLower.charAt(i)=='w') {
				messageLineFive=messageLineFive+"║╔╗║";
			}
			if(sLower.charAt(i)=='x') {
				messageLineFive=messageLineFive+"╔╬╬╗";
			}
			if(sLower.charAt(i)=='y') {
				messageLineFive=messageLineFive+"╚═╗║";
			}
			if(sLower.charAt(i)=='z') {
				messageLineFive=messageLineFive+"║╚═╣";
			}
			if(sLower.charAt(i)==' ') {
				messageLineFive = messageLineFive+"──";
			}
			//line six
			//line six
			if(sLower.charAt(i)=='a') {
				messageLineSix=messageLineSix+"╚╝╚╝"; 
			}	
			if(sLower.charAt(i)=='b') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)=='c') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)=='d') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)=='e') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)=='f') {
				messageLineSix=messageLineSix+"─╚╝─";
			}
			if(sLower.charAt(i)=='g') {
				messageLineSix=messageLineSix+"╔═╝║";
			}
			if(sLower.charAt(i)=='h') {
				messageLineSix=messageLineSix+"╚╝╚╝";
			}
			if(sLower.charAt(i)=='i') {
				messageLineSix=messageLineSix+"╚╝";
			}
			if(sLower.charAt(i)=='j') {
				messageLineSix=messageLineSix+"╔╝║─";
			}
			if(sLower.charAt(i)=='k') {
				messageLineSix=messageLineSix+"╚╝╚╝";
			}
			if(sLower.charAt(i)=='l') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)=='m') {
				messageLineSix=messageLineSix+"╚╩╩╝";
			}
			if(sLower.charAt(i)=='n') {
				messageLineSix=messageLineSix+"╚╝╚╝";
			}
			if(sLower.charAt(i)=='o') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)=='p') {
				messageLineSix=messageLineSix+"║║──";
			}
			if(sLower.charAt(i)=='q') {
				messageLineSix=messageLineSix+"──║║";
			}
			if(sLower.charAt(i)=='r') {
				messageLineSix=messageLineSix+"╚╝──";
			}
			if(sLower.charAt(i)=='s') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)=='t') {
				messageLineSix=messageLineSix+"─╚╝─";
			}
			if(sLower.charAt(i)=='u') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)=='v') {
				messageLineSix=messageLineSix+"─╚╝─";
			}
			if(sLower.charAt(i)=='w') {
				messageLineSix=messageLineSix+"╚╝╚╝";
			}
			if(sLower.charAt(i)=='x') {
				messageLineSix=messageLineSix+"╚╝╚╝";
			}
			if(sLower.charAt(i)=='y') {
				messageLineSix=messageLineSix+"╔═╝║";
			}
			if(sLower.charAt(i)=='z') {
				messageLineSix=messageLineSix+"╚══╝";
			}
			if(sLower.charAt(i)==' ') {
				messageLineSix = messageLineSix+"──";
			}
			//line seven
			//line seven
			if(sLower.charAt(i)=='a') {
				messageLineSeven=messageLineSeven+"────"; 
			}	
			if(sLower.charAt(i)=='b') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='c') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='d') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='e') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='f') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='g') {
				messageLineSeven=messageLineSeven+"╚══╝";
			}
			if(sLower.charAt(i)=='h') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='i') {
				messageLineSeven=messageLineSeven+"──";
			}
			if(sLower.charAt(i)=='j') {
				messageLineSeven=messageLineSeven+"╚═╝─";
			}
			if(sLower.charAt(i)=='k') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='l') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='m') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='n') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='o') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='p') {
				messageLineSeven=messageLineSeven+"╚╝──";
			}
			if(sLower.charAt(i)=='q') {
				messageLineSeven=messageLineSeven+"──╚╝";
			}
			if(sLower.charAt(i)=='r') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='s') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='t') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='u') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='v') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='w') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='x') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)=='y') {
				messageLineSeven=messageLineSeven+"╚══╝";
			}
			if(sLower.charAt(i)=='z') {
				messageLineSeven=messageLineSeven+"────";
			}
			if(sLower.charAt(i)==' ') {
				messageLineSeven = messageLineSeven+"──";
			}
		}
		message=messageLineOne+"\n"+messageLineTwo+"\n"+messageLineThree+"\n"+messageLineFour+"\n"+messageLineFive+"\n"+messageLineSix+"\n"+messageLineSeven;
		return message;
	}
	String getLettersBigger(String s) {
		String message="";
		String messageLineOne="";
		String messageLineTwo="";
		String messageLineThree="";
		String messageLineFour="";
		String messageLineFive="";
		String messageLineSix="";
		String sLower=s.toLowerCase();
		for(int i =0;i<s.length();i++) {
			if(sLower.charAt(i)=='a') {
				messageLineOne=messageLineOne+"░█████╗░"; 
			}
			if(sLower.charAt(i)=='b') {
				messageLineOne=messageLineOne+"██████╗░";
			}
			if(sLower.charAt(i)=='c') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='d') {
				messageLineOne=messageLineOne+"██████╗░";
			}
			if(sLower.charAt(i)=='e') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='f') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='g') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='h') {
				messageLineOne=messageLineOne+"██╗░░██╗";
			}
			if(sLower.charAt(i)=='i') {
				messageLineOne=messageLineOne+"██████╗░";
			}
			if(sLower.charAt(i)=='j') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='k') {
				messageLineOne=messageLineOne+"██╗░░██╗";
			}
			if(sLower.charAt(i)=='l') {
				messageLineOne=messageLineOne+"██╗░░░░░";
			}
			if(sLower.charAt(i)=='m') {
				messageLineOne=messageLineOne+"░██╗██╗░";
			}
			if(sLower.charAt(i)=='n') {
				messageLineOne=messageLineOne+"███╗░██╗";
			}
			if(sLower.charAt(i)=='o') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='p') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='q') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='r') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='s') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)=='t') {
				messageLineOne=messageLineOne+"██████╗░";
			}
			if(sLower.charAt(i)=='u') {
				messageLineOne=messageLineOne+"██╗░░██╗";
			}
			if(sLower.charAt(i)=='v') {
				messageLineOne=messageLineOne+"██╗░░██╗";
			}
			if(sLower.charAt(i)=='w') {
				messageLineOne=messageLineOne+"██╗░░██╗";
			}
			if(sLower.charAt(i)=='x') {
				messageLineOne=messageLineOne+"██╗░░██╗";
			}
			if(sLower.charAt(i)=='y') {
				messageLineOne=messageLineOne+"██╗░░██╗";
			}
			if(sLower.charAt(i)=='z') {
				messageLineOne=messageLineOne+"███████╗";
			}
			if(sLower.charAt(i)==' ') {
				messageLineOne=messageLineOne+" ";
			}
			//line two
			if(sLower.charAt(i)=='a') {
				messageLineTwo=messageLineTwo+"██╔══██╗";
			}
			if(sLower.charAt(i)=='b') {
				messageLineTwo=messageLineTwo+"██╔═══█╗";
			}
			if(sLower.charAt(i)=='c') {
				messageLineTwo=messageLineTwo+"██╔════╝";
			}
			if(sLower.charAt(i)=='d') {
				messageLineTwo=messageLineTwo+"██╔═══█╗";
			}
			if(sLower.charAt(i)=='e') {
				messageLineTwo=messageLineTwo+"██╔════╝";
			}
			if(sLower.charAt(i)=='f') {
				messageLineTwo=messageLineTwo+"██╔════╝";
			}
			if(sLower.charAt(i)=='g') {
				messageLineTwo=messageLineTwo+"██╔════╣";
			}
			if(sLower.charAt(i)=='h') {
				messageLineTwo=messageLineTwo+"██╚══██║";
			}
			if(sLower.charAt(i)=='i') {
				messageLineTwo=messageLineTwo+"╚═██╔═╝░";
			}
			if(sLower.charAt(i)=='j') {
				messageLineTwo=messageLineTwo+"╚══██╔═╝";
			}
			if(sLower.charAt(i)=='k') {
				messageLineTwo=messageLineTwo+"██║██╔═╝";
			}
			if(sLower.charAt(i)=='l') {
				messageLineTwo=messageLineTwo+"██║░░░░░";
			}
			if(sLower.charAt(i)=='m') {
				messageLineTwo=messageLineTwo+"██║█║██╗";
			}
			if(sLower.charAt(i)=='n') {
				messageLineTwo=messageLineTwo+"██║█╗██║";
			}
			if(sLower.charAt(i)=='o') {
				messageLineTwo=messageLineTwo+"██╔══██║";
			}
			if(sLower.charAt(i)=='p') {
				messageLineTwo=messageLineTwo+"██╔══██║";
			}
			if(sLower.charAt(i)=='q') {
				messageLineTwo=messageLineTwo+"██╔══██║";
			}
			if(sLower.charAt(i)=='r') {
				messageLineTwo=messageLineTwo+"██╔══██║";
			}
			if(sLower.charAt(i)=='s') {
				messageLineTwo=messageLineTwo+"██╔════╝";
			}
			if(sLower.charAt(i)=='t') {
				messageLineTwo=messageLineTwo+"╚═██╔═╝░";
			}
			if(sLower.charAt(i)=='u') {
				messageLineTwo=messageLineTwo+"██║░░██║";
			}
			if(sLower.charAt(i)=='v') {
				messageLineTwo=messageLineTwo+"██║░░██║";
			}if(sLower.charAt(i)=='w') {
				messageLineTwo=messageLineTwo+"██║░░██║";
			}
			if(sLower.charAt(i)=='x') {
				messageLineTwo=messageLineTwo+"╚██╗██╔╝";
			}
			if(sLower.charAt(i)=='y') {
				messageLineTwo=messageLineTwo+"██║░░██║";
			}
			if(sLower.charAt(i)=='z') {
				messageLineTwo=messageLineTwo+"╚════██║";
			}
			if(sLower.charAt(i)==' ') {
				messageLineTwo=messageLineTwo+" ";
			}
			//line three
			if(sLower.charAt(i)=='a') {
				messageLineThree=messageLineThree+"███████║";	
			}
			if(sLower.charAt(i)=='b') {
				messageLineThree=messageLineThree+"██║░██╔╝";	
			}
			if(sLower.charAt(i)=='c') {
				messageLineThree=messageLineThree+"██║░░░░░";	
			}
			if(sLower.charAt(i)=='d') {
				messageLineThree=messageLineThree+"██║░░░█║";	
			}
			if(sLower.charAt(i)=='e') {
				messageLineThree=messageLineThree+"█████╗░░";	
			}
			if(sLower.charAt(i)=='f') {
				messageLineThree=messageLineThree+"█████╗░░";	
			}
			if(sLower.charAt(i)=='g') {
				messageLineThree=messageLineThree+"██║░███║";	
			}
			if(sLower.charAt(i)=='h') {
				messageLineThree=messageLineThree+"███████║";	
			}
			if(sLower.charAt(i)=='i') {
				messageLineThree=messageLineThree+"░░██║░░░";	
			}
			if(sLower.charAt(i)=='j') {
				messageLineThree=messageLineThree+"░░░██║░░";	
			}
			if(sLower.charAt(i)=='k') {
				messageLineThree=messageLineThree+"███══╝░░";	
			}
			if(sLower.charAt(i)=='l') {
				messageLineThree=messageLineThree+"██║░░░░░";	
			}
			if(sLower.charAt(i)=='m') {
				messageLineThree=messageLineThree+"██║█║██║";	
			}
			if(sLower.charAt(i)=='n') {
				messageLineThree=messageLineThree+"██║█║██║";	
			}
			if(sLower.charAt(i)=='o') {
				messageLineThree=messageLineThree+"██║░░██║";	
			}
			if(sLower.charAt(i)=='p') {
				messageLineThree=messageLineThree+"███████║";	
			}
			if(sLower.charAt(i)=='q') {
				messageLineThree=messageLineThree+"███████║";	
			}
			if(sLower.charAt(i)=='r') {
				messageLineThree=messageLineThree+"███████║";	
			}
			if(sLower.charAt(i)=='s') {
				messageLineThree=messageLineThree+"░░███░░░";	
			}
			if(sLower.charAt(i)=='t') {
				messageLineThree=messageLineThree+"░░██║░░░";	
			}
			if(sLower.charAt(i)=='u') {
				messageLineThree=messageLineThree+"██║░░██║";	
			}
			if(sLower.charAt(i)=='v') {
				messageLineThree=messageLineThree+"██║░░██║";	
			}
			if(sLower.charAt(i)=='w') {
				messageLineThree=messageLineThree+"██║█╗██║";	
			}
			if(sLower.charAt(i)=='x') {
				messageLineThree=messageLineThree+"░╚███╔╝░";	
			}
			if(sLower.charAt(i)=='y') {
				messageLineThree=messageLineThree+"███████║";	
			}
			if(sLower.charAt(i)=='z') {
				messageLineThree=messageLineThree+"░░███╔═╝";	
			}
			if(sLower.charAt(i)==' ') {
				messageLineThree=messageLineThree+" ";	
			}
			//line four
			if(sLower.charAt(i)=='a') {
				messageLineFour=messageLineFour+"██║░░██║";
			}
			if(sLower.charAt(i)=='b') {
				messageLineFour=messageLineFour+"██║░╚═█╗";
			}
			if(sLower.charAt(i)=='c') {
				messageLineFour=messageLineFour+"██╚════╗";
			}
			if(sLower.charAt(i)=='d') {
				messageLineFour=messageLineFour+"██║░░░█║";
			}
			if(sLower.charAt(i)=='e') {
				messageLineFour=messageLineFour+"██╔══╝░░";
			}
			if(sLower.charAt(i)=='f') {
				messageLineFour=messageLineFour+"██╔══╝░░";
			}
			if(sLower.charAt(i)=='g') {
				messageLineFour=messageLineFour+"██║░░██║";
			}
			if(sLower.charAt(i)=='h') {
				messageLineFour=messageLineFour+"██╔══██║";
			}
			if(sLower.charAt(i)=='i') {
				messageLineFour=messageLineFour+"░░██║░░░";
			}
			if(sLower.charAt(i)=='j') {
				messageLineFour=messageLineFour+"░░░██║░░";
			}
			if(sLower.charAt(i)=='k') {
				messageLineFour=messageLineFour+"██║██╗░░";
			}
			if(sLower.charAt(i)=='l') {
				messageLineFour=messageLineFour+"██║░░░░░";
			}
			if(sLower.charAt(i)=='m') {
				messageLineFour=messageLineFour+"██║╚╝██║";
			}
			if(sLower.charAt(i)=='n') {
				messageLineFour=messageLineFour+"██║╚███║";
			}
			if(sLower.charAt(i)=='o') {
				messageLineFour=messageLineFour+"██║░░██║";
			}
			if(sLower.charAt(i)=='p') {
				messageLineFour=messageLineFour+"██╔════╝";
			}
			if(sLower.charAt(i)=='q') {
				messageLineFour=messageLineFour+"╚════██║";
			}
			if(sLower.charAt(i)=='r') {
				messageLineFour=messageLineFour+"██║██══╝";
			}
			if(sLower.charAt(i)=='s') {
				messageLineFour=messageLineFour+"░░░╚═██╗";
			}
			if(sLower.charAt(i)=='t') {
				messageLineFour=messageLineFour+"░░██║░░░";
			}
			if(sLower.charAt(i)=='u') {
				messageLineFour=messageLineFour+"██║░░██║";
			}
			if(sLower.charAt(i)=='v') {
				messageLineFour=messageLineFour+"██║░░██║";
			}
			if(sLower.charAt(i)=='w') {
				messageLineFour=messageLineFour+"██║░░██║";
			}
			if(sLower.charAt(i)=='x') {
				messageLineFour=messageLineFour+"░██║██╗░";
			}
			if(sLower.charAt(i)=='y') {
				messageLineFour=messageLineFour+"╚════██║";
			}
			if(sLower.charAt(i)=='z') {
				messageLineFour=messageLineFour+"██╔══╝░░";
			}
			if(sLower.charAt(i)==' ') {
				messageLineFour=messageLineFour+" ";
			}
			//line five
			if(sLower.charAt(i)=='a') {
				messageLineFive=messageLineFive+"██║░░██║";
			}
			if(sLower.charAt(i)=='b') {
				messageLineFive=messageLineFive+"██████╔╝";
			}
			if(sLower.charAt(i)=='c') {
				messageLineFive=messageLineFive+"███████║";
			}
			if(sLower.charAt(i)=='d') {
				messageLineFive=messageLineFive+"██████╔╝";
			}
			if(sLower.charAt(i)=='e') {
				messageLineFive=messageLineFive+"███████╗";
			}
			if(sLower.charAt(i)=='f') {
				messageLineFive=messageLineFive+"██║░░░░░";
			}
			if(sLower.charAt(i)=='g') {
				messageLineFive=messageLineFive+"███████║";
			}
			if(sLower.charAt(i)=='h') {
				messageLineFive=messageLineFive+"██║░░██";
			}
			if(sLower.charAt(i)=='i') {
				messageLineFive=messageLineFive+"██████╗░";
			}
			if(sLower.charAt(i)=='j') {
				messageLineFive=messageLineFive+"░████║░░";
			}
			if(sLower.charAt(i)=='k') {
				messageLineFive=messageLineFive+"██║╚═██╗";
			}
			if(sLower.charAt(i)=='l') {
				messageLineFive=messageLineFive+"███████╗";
			}
			if(sLower.charAt(i)=='m') {
				messageLineFive=messageLineFive+"██║░░██║";
			}
			if(sLower.charAt(i)=='n') {
				messageLineFive=messageLineFive+"██║░╚██║";
			}
			if(sLower.charAt(i)=='o') {
				messageLineFive=messageLineFive+"███████║";
			}
			if(sLower.charAt(i)=='p') {
				messageLineFive=messageLineFive+"██║░░░░░";
			}
			if(sLower.charAt(i)=='q') {
				messageLineFive=messageLineFive+"░░░░░██║";
			}
			if(sLower.charAt(i)=='r') {
				messageLineFive=messageLineFive+"██║░███╗";
			}
			if(sLower.charAt(i)=='s') {
				messageLineFive=messageLineFive+"███████║";
			}
			if(sLower.charAt(i)=='t') {
				messageLineFive=messageLineFive+"░░██║░░░";
			}
			if(sLower.charAt(i)=='u') {
				messageLineFive=messageLineFive+"███████║";
			}
			if(sLower.charAt(i)=='v') {
				messageLineFive=messageLineFive+"╚═███╔═╝";
			}
			if(sLower.charAt(i)=='w') {
				messageLineFive=messageLineFive+"╚██║██╔╝";
			}
			if(sLower.charAt(i)=='x') {
				messageLineFive=messageLineFive+"██╔╝╚██╗";
			}
			if(sLower.charAt(i)=='y') {
				messageLineFive=messageLineFive+"░██████║";
			}
			if(sLower.charAt(i)=='z') {
				messageLineFive=messageLineFive+"███████╗";
			}
			if(sLower.charAt(i)==' ') {
				messageLineFive=messageLineFive+" ";
			}
			//line six
			if(sLower.charAt(i)=='a') {
				messageLineSix=messageLineSix+"╚═╝░░╚═╝";
			}
			if(sLower.charAt(i)=='b') {
				messageLineSix=messageLineSix+"╚═════╝░";
			}
			if(sLower.charAt(i)=='c') {
				messageLineSix=messageLineSix+"╚══════╝";
			}
			if(sLower.charAt(i)=='d') {
				messageLineSix=messageLineSix+"╚═════╝░";
			}
			if(sLower.charAt(i)=='e') {
				messageLineSix=messageLineSix+"╚══════╝";
			}
			if(sLower.charAt(i)=='f') {
				messageLineSix=messageLineSix+"╚═╝░░░░░";
			}
			if(sLower.charAt(i)=='g') {
				messageLineSix=messageLineSix+"╚══════╝";
			}
			if(sLower.charAt(i)=='h') {
				messageLineSix=messageLineSix+"╚═╝░░╚═╝";
			}
			if(sLower.charAt(i)=='i') {
				messageLineSix=messageLineSix+"╚═════╝░";
			}
			if(sLower.charAt(i)=='j') {
				messageLineSix=messageLineSix+"░╚═══╝░░";
			}
			if(sLower.charAt(i)=='k') {
				messageLineSix=messageLineSix+"╚═╝░░╚═╝";
			}
			if(sLower.charAt(i)=='l') {
				messageLineSix=messageLineSix+"╚══════╝";
			}
			if(sLower.charAt(i)=='m') {
				messageLineSix=messageLineSix+"╚═╝░░╚═╝";
			}
			if(sLower.charAt(i)=='n') {
				messageLineSix=messageLineSix+"╚═╝░░╚═╝";
			}
			if(sLower.charAt(i)=='o') {
				messageLineSix=messageLineSix+"╚══════╝";
			}
			if(sLower.charAt(i)=='p') {
				messageLineSix=messageLineSix+"╚═╝░░░░░";
			}
			if(sLower.charAt(i)=='q') {
				messageLineSix=messageLineSix+"░░░░░╚═╝";
			}
			if(sLower.charAt(i)=='r') {
				messageLineSix=messageLineSix+"╚═╝░╚══╝";
			}
			if(sLower.charAt(i)=='s') {
				messageLineSix=messageLineSix+"╚══════╝";
			}
			if(sLower.charAt(i)=='t') {
				messageLineSix=messageLineSix+"░░╚═╝░░░";
			}
			if(sLower.charAt(i)=='u') {
				messageLineSix=messageLineSix+"╚══════╝";
			}
			if(sLower.charAt(i)=='v') {
				messageLineSix=messageLineSix+"░░╚══╝░░";
			}
			if(sLower.charAt(i)=='w') {
				messageLineSix=messageLineSix+"░╚═╝╚═╝░";
			}
			if(sLower.charAt(i)=='x') {
				messageLineSix=messageLineSix+"╚═╝░░╚═╝";
			}
			if(sLower.charAt(i)=='y') {
				messageLineSix=messageLineSix+"░╚═════╝";
			}
			if(sLower.charAt(i)=='z') {
				messageLineSix=messageLineSix+"╚══════╝";
			} 
			if(sLower.charAt(i)==' ') {
				messageLineSix=messageLineSix+" ";
			}
		}
		message=message+messageLineOne+"\n"+messageLineTwo+"\n"+messageLineThree+"\n"+messageLineFour+"\n"+messageLineFive+"\n"+messageLineSix;
		return message;
	}
	
}

