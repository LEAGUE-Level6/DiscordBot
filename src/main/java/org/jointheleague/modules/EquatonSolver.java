package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class EquatonSolver extends CustomMessageCreateListener {
	private static final String command = "!equation";

	public EquatonSolver(String channelName) {
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

			String num = "";
			ArrayList<String> hold;
			int start = -1;
			int end = -1;
			hold = new ArrayList<String>();
			for (int i = 0; i < eq.length(); i++) {

				if (Character.isDigit(eq.charAt(i))) {
					num += "" + eq.charAt(i);

				} else {
					// int add = Integer.parseInt(num);
					hold.add(num);
					num = "";

				}

				if (eq.charAt(i) == '+') {
					hold.add("+");
				}
				if (eq.charAt(i) == '-') {
					hold.add("-");
				}
				if (eq.charAt(i) == '*') {
					hold.add("*");
				}
				if (eq.charAt(i) == '/') {
					hold.add("/");
				}
				if (eq.charAt(i) == '%') {
					hold.add("%");

				}
				if (eq.charAt(i) == '(') {
					hold.add("(");
					start = i;
					start++;
				}
				if (eq.charAt(i) == ')') {
					hold.add(")");
					end = i;
				}

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
			hold.add(num);
			num = "";
//			solveEquation(input);
//			System.out.println("input " +solveEquation(input));
//			hold.add(0, solveEquation(input));
			System.out.println("hold is " + hold);
			event.getChannel().sendMessage(solveEquation(hold));
		}

	}

	public String solveEquation(ArrayList<String> hold) {

		boolean done = false;
		String[] symbols = { "*", "/", "%", "-", "+" };
		int currentIndex = 0;
		String currentSym = symbols[currentIndex];
		while (!done) {
			boolean found = false;

			for (int i = 0; i < hold.size(); i++) {

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
}
