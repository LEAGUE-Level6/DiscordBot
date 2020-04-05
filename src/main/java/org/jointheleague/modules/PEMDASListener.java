package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class PEMDASListener extends CustomMessageCreateListener{
	
	public static final String MATH_COMMAND = "!solve ";
	public static final String[] ops = {"+","-","*","/","^","%","d"};
	public static boolean haveAnswer = false;
	public static double lastAnswer = 0;
	public static boolean correctSyntax = true;
	public static boolean explain = false;
	public static MessageCreateEvent publicEvent;

	public PEMDASListener(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}
	
	public class EquationHolder{
		public ArrayList<Double> nums;
		public ArrayList<String> operations;
		EquationHolder(ArrayList<Double> num,ArrayList<String> operation){
			nums = num;
			operations = operation;
		}
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		publicEvent = event;
		// TODO Auto-generated method stub
		if(!event.getMessageAuthor().getIdAsString().equals("683742358726377566")) {
		String s = event.getMessageContent();
		if(s.length()>=7) {
			
			String begin = s.substring(0,7);
			System.out.println(s.substring(7));
				if(begin.equals(MATH_COMMAND)) {
					String equation = s.substring(7);
					//System.out.println(solve(convert(equation)));
					explain = true;
					double answer = solve(convert(equation));
					if(answer%1!=0) {
						outputAnswer(event,""+answer);
					}else {
						outputAnswer(event,""+(int)answer);
					}
					lastAnswer = answer;
					explain = false;
				}
		}if(s.substring(s.length()-1).equals("=")||s.substring(s.length()-1).equals("= ")) {
			String equation = s.substring(0,s.length()-1);
			//System.out.println(solve(convert(equation)));
			double answer = solve(convert(equation));
			if(answer%1!=0) {
				outputAnswer(event,""+answer);
			}else {
				outputAnswer(event,""+(int)answer);
			}
			lastAnswer = answer;
		}else {
			String equation = s;
			//System.out.println(solve(convert(equation)));
			double answer = solve(convert(equation));
			if(answer%1!=0) {
				outputAnswer(event,""+answer);
			}else {
				outputAnswer(event,""+(int)answer);
			}
			lastAnswer = answer;
		}
		}
	}
	
	public void outputAnswer(MessageCreateEvent e, String s) {
		if(correctSyntax) {
			e.getChannel().sendMessage(s);
		}
	}
		
	public void outputExplain(String s) {
		if(explain) {
			publicEvent.getChannel().sendMessage(s);
		}	
	}
	
	public EquationHolder convert(String s){
		ArrayList<Double> nums = new ArrayList<Double>();
		ArrayList<String> operations = new ArrayList<String>();
		try {
			String temp = "";
			int startP = -1;
			int endP = -1;
			boolean foundP = false;
			for(int i = 0;i<s.length();i++) {
				String sub = s.substring(i,i+1);
				if(isOperation(sub)) {
					if(!temp.equals("")) {
						if(temp.contains("Ans")) {
							nums.add(lastAnswer);
						}else {
							nums.add(Double.parseDouble(temp));
						}
						temp = "";
					}else {
						System.out.println("incorrect");
						correctSyntax = false;
					}
					operations.add(sub);
				}else if(sub.equals("(")&&!foundP) {
					startP = i;
				}else if(sub.equals(")")&&!foundP) {
					endP = i;
					foundP = true;
				}else if(sub.equals("(")||sub.equals(")")){
					
				}else {
					temp += sub;
				}
			}
			if(foundP) {
				String parenString = s.substring(startP+1,endP);
				String simplified = s.substring(0,startP)+solve(convert(parenString))+s.substring(endP+1);
				return convert(simplified);
			}
			nums.add(Double.parseDouble(temp));
			return new EquationHolder(nums,operations);
		}catch(NumberFormatException e) {
			System.out.println("incorrect");
			correctSyntax = false;
			return new EquationHolder(null,null);

		}	
	}
	
	public double solve(EquationHolder equation) {
		try {
			outputExplain(equationToString(equation.nums, equation.operations));
			if(equation.nums.size()==1) {
				return equation.nums.get(0);
			}
			int topIndex = 0;
			int topPriority = 0;
			for(int i = 0;i<equation.nums.size()-1;i++) {
				if(getPriority(equation.operations.get(i))>topPriority) {
					topPriority = getPriority(equation.operations.get(i));
					topIndex = i;
				}
			}
			double newCalc = calcSimple(equation.nums.get(topIndex), equation.nums.get(topIndex+1), equation.operations.get(topIndex));
			ArrayList<Double> num = equation.nums;
			ArrayList<String> operation = equation.operations;
			num.set(topIndex, newCalc);
			num.remove(topIndex+1);
			operation.remove(topIndex);
			if(num.size()<=1) {
				return num.get(0);
			}
			
			return solve(new EquationHolder(num,operation));
		}catch(NullPointerException e) {
			System.out.println("incorrect");
			correctSyntax = false;
			return -1;
		}
		
	}
	
	public double calcSimple(Double a, Double b, String operation) {
		switch(operation) {
		case "+":return a+b;
		case "/":return a/b;
		case "-":return a-b;
		case "*":return a*b;
		case "^":return Math.pow(a, b);
		case "%":return a%b;
		case "d": int out = 0;
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
	
	public String equationToString(ArrayList<Double> nums, ArrayList<String> operations) {
		String out = "";
		for(int i = 0;i<nums.size();i++) {
			if(i!=nums.size()-1) {
				out += nums.get(i) + " " + operations.get(i) + " ";
				
			}else {
				out+= nums.get(i);
			}
		}
		return out + " =";
	}
	
	public boolean isOperation(String s) {
		for(String d : ops) {
			if(d.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
