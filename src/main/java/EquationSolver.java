import java.util.ArrayList;

public class EquationSolver {
	static String eq = "1/3";
	static String num = "";
	static ArrayList<String> hold;

	public static void main(String[] args) {
		hold = new ArrayList<String>();
		for (int i = 0; i < eq.length(); i++) {
			if (eq.charAt(eq.length() - 1) == '+' || eq.charAt(eq.length() - 1) == '-'
					|| eq.charAt(eq.length() - 1) == '/' || eq.charAt(eq.length() - 1) == '*') {
				System.out.println("Not A Valid Equation");
				System.exit(0);
			}

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
			/*
			 * 5+6*7-8/2 5+42-8/2 5+42-4 47-4 43
			 */
		}
		hold.add(num);
		num = "";
	

		boolean done = false;
		String[] symbols = { "*", "/", "+", "-" };
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

	}

}
