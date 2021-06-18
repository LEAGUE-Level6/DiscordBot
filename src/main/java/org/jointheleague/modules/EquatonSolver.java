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
				}
				if (eq.charAt(i) == ')') {
					hold.add(")");
				}
				/*
				 * 5+6*7-8/2 5+42-8/2 5+42-4 47-4 43
				 */
			}
			hold.add(num);
			num = "";

			boolean done = false;
			String[] symbols = { "(", ")", "*", "/", "%", "+", "-" };
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

			event.getChannel().sendMessage(hold.get(0));
		}

	}

}
