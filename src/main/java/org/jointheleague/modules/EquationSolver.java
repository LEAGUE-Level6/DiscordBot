package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class EquationSolver extends CustomMessageCreateListener {
	private static final String command = "!equation";

	public EquationSolver(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub

		if (event.getMessageContent().contains("!equation")) {
			String eqws = event.getMessageContent().substring(9);
			String eq = "";
			for (int i = 0; i < eqws.length(); i++) {
				if (eqws.charAt(i) == ' ') {
					continue;
				} else {
					eq += eqws.charAt(i);
					
				}
			}
			//eq is good here
			String num = "";
			ArrayList<String> hold;
		
			hold = new ArrayList<String>();
			for (int i = 0; i < eq.length(); i++) {
				
				hold.add(""+ eq.charAt(i));
//				if (Character.isDigit(eq.charAt(i))) {
//					num="";
//					num += "" + eq.charAt(i);
//					//System.out.println(i);
//					
//					//System.out.println(num);
//
//				} else {
//					if(num.equals("")) {
//						
//					}
//					// int add = Integer.parseInt(num);
//					else {
//					System.out.println("eq.charat1: " + eq.charAt(i));
//					hold.add(num);
//					System.out.println(num);
//					}
//					
//
//				}
//
//				if (eq.charAt(i) == '+') {
//					hold.add("+");
//				}
//				if (eq.charAt(i) == '-') {
//					hold.add("-");
//				}
//				if (eq.charAt(i) == '*') {
//					hold.add("*");
//				}
//				if (eq.charAt(i) == '/') {
//					hold.add("/");
//				}
//				if (eq.charAt(i) == '%') {
//					hold.add("%");
//
//				}
//				if (eq.charAt(i) == '(') {
//					hold.add("(");
//				}
//				if (eq.charAt(i) == ')') {
//					hold.add(")");
//				}

			}
			
//			ArrayList<String> input = new ArrayList<String>();
//			
//			for (int i = start; i < end; i++) {
//				
//				input.add("" + eq.charAt(i));
//				
//				
//			}
//
//		
//			
		
//			solveEquation(input);
//			System.out.println("input " +solveEquation(input));
//			hold.add(0, solveEquation(input));
		
			event.getChannel().sendMessage(solveEquation(hold));
		}

	}

	public String solveEquation(ArrayList<String> hold) {

		boolean done = false;
		String[] symbols = {"*", "/", "%", "-", "+" };
		int currentIndex = 0;
		String currentSym = symbols[currentIndex];
		while (!done) {
			boolean found = false;

			for (int i = 0; i < hold.size(); i++) {
//System.out.println("hold.geti is " + hold.get(i));
				if (hold.get(i).equals(currentSym)) {
					double fVal = Double.parseDouble(hold.get(i - 1));
					double sVal = Double.parseDouble(hold.get(i + 1));
					double solution = 0;

					if (hold.get(i).equals("*")) {
						solution = fVal * sVal;

					}
					if (hold.get(i).equals("/")) {
						solution = fVal / sVal;

					}
					if (hold.get(i).equals("+")) {
						solution = fVal + sVal;

					}
					if (hold.get(i).equals("-")) {
						solution = fVal - sVal;

					}
					if (hold.get(i).equals("%")) {
						solution = fVal % sVal;

					}
					
					
					hold.remove(i - 1);
					hold.remove(i - 1);
					String ans = "" + solution;
					hold.set(i - 1, ans);
					found = true;
					break;
				}
				if(hold.get(i).equals("(")) {
					System.out.println("this is what is being put in for place " + i);
					solveParenth(hold, i);
				}

			}
			if (!found) {
				currentIndex++;

				if (currentIndex >= symbols.length) {
					done = true;
				} else {

					currentSym = symbols[currentIndex];
				}
			}
		}
		String ans = hold.get(0);
		return (ans);

	}
	public void solveParenth(ArrayList<String> hold, int place) {
		ArrayList<String> in = new ArrayList<String>();
		int end = -1;
		for(int i = place+1;i<hold.size();i++) {
			
			if(hold.get(i).equals(")")) {
				end=i;
				
				break;
				
			}
			else {
				in.add(hold.get(i));
			}
		}	
		solveEquation(in);
		System.out.println("in is "+ in);
			System.out.println("hold before removes "+hold);
		//in is good here
			hold.remove(place);
			hold.remove(end-1);
			System.out.println("this is the end " + end);
		for(int i = end-2;i<place;i--) {
			System.out.println(i);
			System.out.println(hold);
			hold.remove(i);
			System.out.println(hold);
		}
		
		
		System.out.println("hold after all of the removes " + hold);
	}
}