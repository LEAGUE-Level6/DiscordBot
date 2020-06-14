package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class PEMDASListener extends CustomMessageCreateListener{
	public static final String[] ops = {"+","-","*","/","^","%","d"};
	public static boolean haveAnswer = false;
	public static double saveAnswer = 0;
	public static double lastAnswer = 0;
	public static boolean correctSyntax = true;
	public static double factorialSave = -1;
	public static boolean explain = false;
	public static long botId = -1;
	public static MessageCreateEvent publicEvent;
	HashMap<String,Double> codeToNum = new HashMap<String,Double>();

	public PEMDASListener(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed("<expression> =","simplifies the expression into a single decimal number");
	}
	
	//Holds the numbers and operations in the equation in a format for solve() to understand
	public class EquationHolder{
		public ArrayList<Double> nums;
		public ArrayList<String> operations;
		public ArrayList<Boolean> isNegative;
		
		EquationHolder(ArrayList<Double> num,ArrayList<String> operation, ArrayList<Boolean> isNegative){
			nums = num;
			operations = operation;
			this.isNegative = isNegative;
		}
	}

	//handle is called when message is sent into the chat
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		codeToNum = new HashMap<String,Double>();
		saveAnswer = lastAnswer;
		haveAnswer = false;
		correctSyntax = true;
		explain = false;
		publicEvent = event;
		try {
		if(!event.getMessageAuthor().getIdAsString().equals(""+botId)) {
			String s = event.getMessageContent();
			if(s.substring(s.length()-1).equals("=")||s.substring(s.length()-1).equals("= ")) {
				String equation = s.substring(0,s.length()-1);
				double answer = solve(convert(equation));
				lastAnswer = answer;
				if(answer%1!=0) {
					outputAnswer(event,""+answer);
				}else {
					outputAnswer(event,""+(int)answer);
				}
				
			}else {
				String equation = s;
				double answer = solve(convert(equation));
				lastAnswer = answer;
				if(answer%1!=0) {
					outputAnswer(event,""+answer);
				}else {
					outputAnswer(event,""+(int)answer);
				}
			}
		}
		}catch(Exception e) {
			
		}
	}
	
	public String fix(String s) {
		String out = "";
		for(int i = 0;i<s.length();i++) {
			
		}
		return out;
	}
	
	public void outputAnswer(MessageCreateEvent e, String s) {
		if(correctSyntax) {
			try {
				botId = e.getChannel().sendMessage(s).get().getAuthor().getId();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
			lastAnswer = saveAnswer;
		}
	}
	
	//codes
	public String createCode(double num) {
		String code = "a";
		for(int i = 0;i<codeToNum.size();i++) {
				code +="b";
		}
		code+="a";
		codeToNum.put(code, num);
		return code;
	}
	
	public int factorial(int i) {
		int out = 1;
		for(i=i;i>1;i--) {
			out*=i;
		}
		return out;
	}
	
	//convert() takes in a string of the equation and outputs an EquationHolder
	// ( ) and | | signs get solved, put into a code, and then calls convert again on the new string until no remain
	public EquationHolder convert(String s){
		ArrayList<Double> nums = new ArrayList<Double>();
		ArrayList<String> operations = new ArrayList<String>();
		ArrayList<Boolean> isNegative = new ArrayList<Boolean>();
		try {
			String temp = "";
			int lastType = -1;
			//lastType = 0 for number
			//1 for operation
			//2 for parenthesis
			int startP = -1;
			int endP = -1;
			int startA = -1;
			int endA = -1;
			boolean foundP = false;
			boolean foundA = false;
			boolean foundBeginA = false;
			boolean negativeOperation = false;
			String out = "";
			//takes out spaces
			for(int j = 0; j<s.length();j++) {
				if(!s.substring(j,j+1).equals(" ")) {
					out+=s.substring(j,j+1);
				}
			}
			s = out;
			//adds 0 at front if negative sign to start
			if("-".equals(s.substring(0, 1))){
				s = "0"+s;
			}
			//loops through the string
			//finds indexes of () and ||
			//temp contains part of string up to an operation
			//when get to operation, add number and operation to EquationHolder
			for(int i = 0;i<s.length();i++) {
				String sub = s.substring(i,i+1);
				if(sub.equals("-")&&!negativeOperation) {
					temp = "-";
				}else if(isOperation(sub)) {
				
					if(!temp.equals("")) {
						if(temp.toLowerCase().contains("ans")) {
							if(temp.contains("-")) {
								if(temp.substring(temp.length()-1).equals("!")) {
									nums.add((double) factorial((int)Math.abs(lastAnswer)));
								}else {
									nums.add(Math.abs(lastAnswer));
								}
								isNegative.add(lastAnswer>0);
							}else {
								if(temp.substring(temp.length()-1).equals("!")) {
									nums.add((double) factorial((int)Math.abs(lastAnswer)));
								}else {
									nums.add(Math.abs(lastAnswer));
								}
								isNegative.add(lastAnswer<0);
							}
						}else if(codeToNum.containsKey(temp) || codeToNum.containsKey(temp.substring(0,temp.length()-1))){
							double num;
							if(temp.substring(temp.length()-1).equals("!")) {
								num = codeToNum.get(temp.substring(0,temp.length()-1));
								nums.add((double)factorial((int)Math.abs(num)));
							}else {
							num = codeToNum.get(temp);
							nums.add(Math.abs(num));
							}
							isNegative.add(num<0);
//							
						}else if(temp.length()>1&&(codeToNum.containsKey(temp.substring(1)) || codeToNum.containsKey(temp.substring(1,temp.length()-1)))){
							if(temp.contains("-")) {
								if(temp.substring(temp.length()-1).equals("!")) {
									double num = codeToNum.get(temp.substring(1,temp.length()-1));
									nums.add((double)factorial((int)Math.abs(num)));
									isNegative.add(num>0);
								}else {
									double num = codeToNum.get(temp.substring(1));
									nums.add(Math.abs(num));
									isNegative.add(num>0);
								}
							}else {
								if(temp.substring(temp.length()-1).equals("!")) {
									double num = codeToNum.get(temp.substring(0,temp.length()-1));
									nums.add((double)factorial((int)Math.abs(num)));
									isNegative.add(num<0);
								}else {	
									double num = codeToNum.get(temp);
									nums.add(Math.abs(num));
									isNegative.add(num<0);
								}
							}
						}else{
							if(temp.substring(temp.length()-1).equals("!")) {
								nums.add(Math.abs((double)factorial((int)Double.parseDouble(temp.substring(0,temp.length()-1)))));
								isNegative.add(Double.parseDouble(temp.substring(0,temp.length()-1))<0);
							}else {	
								nums.add(Math.abs(Double.parseDouble(temp)));
								isNegative.add(Double.parseDouble(temp)<0);
							}
						}
						lastType = 0;
						temp = "";
					}else if(!sub.equals("-")){
						System.out.println("incorrect");
						correctSyntax = false;
					}
					operations.add(sub);
					lastType = 1;
					negativeOperation = false;
				}else if(sub.equals("(")||sub.equals(")")) {
					if(sub.equals("(")&&!foundP) {
						startP = i;
						lastType = 2;
						negativeOperation = false;
					}else if(sub.equals(")")&&!foundP) {
						endP = i;
						lastType = 2;
						foundP = true;
						negativeOperation = true;
					}
				}else if(sub.equals("|")){
					if((lastType == 1||lastType == -1)&&!foundA) {
						startA = i;
						foundBeginA = true;
						negativeOperation = false;
					}else if((lastType == 0||i==s.length()-1)&&!foundA&&foundBeginA) {
						endA = i;
						foundA = true;
						negativeOperation = true;
					}
				}else {	
					if(sub.equals("!")){
						s = s.substring(0,i+1)+"*1"+s.substring(i+1);
					}
					lastType = 0;
					temp += sub;
					negativeOperation = true;
				}
				
			}
			//computes the temp at the end of the loop
			if(temp.toLowerCase().contains("ans")) {
				if(temp.contains("-")) {
					if(temp.substring(temp.length()-1).equals("!")) {
						nums.add((double) factorial((int)Math.abs(lastAnswer)));
					}else {
						nums.add(Math.abs(lastAnswer));
					}
					isNegative.add(lastAnswer>0);
				}else {
					if(temp.substring(temp.length()-1).equals("!")) {
						nums.add((double) factorial((int)Math.abs(lastAnswer)));
					}else {
						nums.add(Math.abs(lastAnswer));
					}
					isNegative.add(lastAnswer<0);
				}
			}else if(codeToNum.containsKey(temp) || codeToNum.containsKey(temp.substring(0,temp.length()-1))){
				double num;
				if(temp.substring(temp.length()-1).equals("!")) {
					num = codeToNum.get(temp.substring(0,temp.length()-1));
					nums.add((double)factorial((int)Math.abs(num)));
				}else {
				num = codeToNum.get(temp);
				nums.add(Math.abs(num));
				}
				isNegative.add(num<0);
//				
			}else if(temp.length()>1&&(codeToNum.containsKey(temp.substring(1)) || codeToNum.containsKey(temp.substring(1,temp.length()-1)))){
				if(temp.contains("-")) {
					if(temp.substring(temp.length()-1).equals("!")) {
						double num = codeToNum.get(temp.substring(1,temp.length()-1));
						nums.add((double)factorial((int)Math.abs(num)));
						isNegative.add(num>0);
					}else {
						double num = codeToNum.get(temp.substring(1));
						nums.add(Math.abs(num));
						isNegative.add(num>0);
					}
				}else {
					if(temp.substring(temp.length()-1).equals("!")) {
						double num = codeToNum.get(temp.substring(0,temp.length()-1));
						nums.add((double)factorial((int)Math.abs(num)));
						isNegative.add(num<0);
					}else {	
						double num = codeToNum.get(temp);
						nums.add(Math.abs(num));
						isNegative.add(num<0);
					}
				}
			}else{
				if(temp.substring(temp.length()-1).equals("!")) {
					nums.add(Math.abs((double)factorial((int)Double.parseDouble(temp.substring(0,temp.length()-1)))));
					isNegative.add(Double.parseDouble(temp.substring(0,temp.length()-1))<0);
				}else {	
					nums.add(Math.abs(Double.parseDouble(temp)));
					isNegative.add(Double.parseDouble(temp)<0);
				}
			}
			//if found () or ||, take the string inside, solve it and place it back into string, then convert again
			if(foundP) {
				String parenString = s.substring(startP+1,endP);
				String simplified = s.substring(0,startP)+createCode(solve(convert(parenString)))+s.substring(endP+1);
				return convert(simplified);
			}else if(foundA) {
				String absString = s.substring(startA+1,endA);
				String simplified = s.substring(0,startA)+Math.abs(solve(convert(absString)))+s.substring(endA+1);
				return convert(simplified);
			}
			return new EquationHolder(nums,operations,isNegative);
		}catch(NumberFormatException | StringIndexOutOfBoundsException e) {
			System.out.println("incorrect");
			correctSyntax = false;
			return new EquationHolder(null,null,null);
		}	
	}
	
	//solve goes through an equationHolder and finds the highest priority operation and solves it. 
	//Repeats until only one number left
	public double solve(EquationHolder equation) {
		try {
			//end check
			if(equation.nums.size()==1) {
				if(equation.operations.size()==0&&equation.isNegative.get(0).equals(true)) {
					return -1*equation.nums.get(0);
				}
				return equation.nums.get(0);
			}
			//finds highest priority
			int topIndex = 0;
			int topPriority = 0;
			for(int i = 0;i<equation.nums.size()-1;i++) {
				if(getPriority(equation.operations.get(i))>topPriority) {
					topPriority = getPriority(equation.operations.get(i));
					topIndex = i;
				}
			}
			//finds numbers around that operation
			double a;
			if(equation.isNegative.get(topIndex)) {
				a = -1*equation.nums.get(topIndex);
			}else {
				a = equation.nums.get(topIndex);
			}
			double b;
			if(equation.isNegative.get(topIndex+1)) {
				b = -1*equation.nums.get(topIndex+1);
			}else {
				b = equation.nums.get(topIndex+1);
			}
			//calculates the simple operation
			double newCalc = calcSimple(a,b, equation.operations.get(topIndex));
			
			//replaces the new number into EquationHolder
			ArrayList<Double> num = equation.nums;
			ArrayList<String> operation = equation.operations;
			ArrayList<Boolean> isNegative = equation.isNegative;
			isNegative.set(topIndex, newCalc<0);
			num.set(topIndex, Math.abs(newCalc));
			
			num.remove(topIndex+1);
			isNegative.remove(topIndex+1);
			operation.remove(topIndex);
			
			return solve(new EquationHolder(num,operation,isNegative));
		}catch(NullPointerException e) {
			System.out.println("incorrect");
			correctSyntax = false;
			return -1;
		}
		
	}
	
	//takes two doubles and an operation and performs the operation: + - / * ^ % d
	public double calcSimple(Double a, Double b, String operation) {
		switch(operation) {
		case "+":return a+b;
		case "/":return a/b;
		case "-":return a-b;
		case "*":return a*b;
		case "^":return Math.pow(a, b);
		case "%":return a%b;
		case "d": int out = 0;
			//used for generating a random dice value
			Random randy = new Random();
			for(int i = 0;i<a;i++) {
				out += randy.nextInt((int)Math.floor(b))+1;
			}
			return out;
		default:
			System.out.println("incorrect");
			correctSyntax = false;
			return 0;
		}
	}
	
	//return the priority value of a given operation
	public int getPriority(String op) {
		switch(op) {
		case "+":return 0;
		case "-":return 0;
		case "/":return 1;
		case "*":return 1;
		case "%":return 1;
		case "^":return 2;
		case "d":return 3;
		default:
			System.out.println("incorrect");
			correctSyntax = false;
			return 0;
		}
	}
	
	//used for output
	public String equationToString(EquationHolder e) {
		String out = "";
		for(int i = 0;i<e.nums.size();i++) {
			if(i!=e.nums.size()-1) {
				if(e.isNegative.get(i)) {
					out += -1*e.nums.get(i);
				}else {
					out += e.nums.get(i);
				}
				out += " " + e.operations.get(i) + " ";
				
			}else {
				out+= e.nums.get(i);
			}
		}
		return out;
	}
	
	//true if operation, else false
	public boolean isOperation(String s) {
		for(String d : ops) {
			if(d.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
