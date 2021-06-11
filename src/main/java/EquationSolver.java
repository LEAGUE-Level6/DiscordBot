import java.util.ArrayList;

public class EquationSolver {
	static String eq = "1+22-333*4444/55555";
	static String num = "";
	static ArrayList<String> hold;

	public static void main(String[] args) {
		hold = new ArrayList<String>();
		for (int i = 0; i < eq.length(); i++) {
			if(eq.charAt(eq.length()-1)=='+' ||eq.charAt(eq.length()-1)=='-'||eq.charAt(eq.length()-1)=='/'||eq.charAt(eq.length()-1)=='*'){
				System.out.println("Not A Valid Equation");
				System.exit(0);
				}
			
			if (Character.isDigit(eq.charAt(i))) {
				num += "" + eq.charAt(i);

			} else {
				//int add = Integer.parseInt(num);
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
/* 5+6*7-8/2
 * 5+42-8/2
 * 5+42-4
 * 47-4
 * 43
 */
		}
		hold.add(num);
		num = "";
		
		System.out.println(hold);
	}

}
