package org.jointheleague.modules;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class PEMDASListener extends CustomMessageCreateListener{
	
	public static final String MATH_COMMAND = "!solve";
	public static final String[] ops = {"+","-","*","/","^","%"};
	public static boolean haveAnswer = false;
	public static double lastAnswer = 0;

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
		// TODO Auto-generated method stub
		if(!event.getMessageAuthor().getIdAsString().equals("683742358726377566")) {
		String s = event.getMessageContent();
		if(s.length()>=7) {
			String begin = s.substring(0,7);
				if(begin.equals(MATH_COMMAND)) {
					String equation = s.substring(8);
					//System.out.println(solve(convert(equation)));
					double answer = solve(convert(equation));
					if(answer%1!=0) {
						event.getChannel().sendMessage(""+answer);
					}else {
						event.getChannel().sendMessage(""+(int)answer);
					}
					lastAnswer = answer;
				}
			}if(s.substring(s.length()-1).equals("=")||s.substring(s.length()-1).equals("= ")) {
				String equation = s.substring(0,s.length()-1);
				//System.out.println(solve(convert(equation)));
				double answer = solve(convert(equation));
				if(answer%1!=0) {
					event.getChannel().sendMessage(""+answer);
				}else {
					event.getChannel().sendMessage(""+(int)answer);
				}
				lastAnswer = answer;
			}
		}
	}
	
	public double solve(EquationHolder equation) {
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
		printOut(num, operation);
		if(num.size()<=1) {
			return num.get(0);
		}
		return solve(new EquationHolder(num,operation));
		
	}
	
	public EquationHolder convert(String s) {
		ArrayList<Double> nums = new ArrayList<Double>();
		ArrayList<String> operations = new ArrayList<String>();
		ArrayList<int[]> parentheses = new ArrayList<int[]>();
		String temp = "";
		int startP = -1;
		int endP = -1;
		boolean foundP = false;
		int pCount = 0;
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
				}
				operations.add(sub);
			}else if(sub.equals("(")&&!foundP) {
				startP = i;
				System.out.println("Found (");
			}else if(sub.equals(")")&&!foundP) {
				System.out.println("Found )");
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
			System.out.println("simplified: "+simplified);
			return convert(simplified);
		}
		nums.add(Double.parseDouble(temp));
		printOut(nums, operations);
		return new EquationHolder(nums,operations);
	}
	
	public double calcSimple(Double a, Double b, String operation) {
		switch(operation) {
		case "+":return a+b;
		case "/":return a/b;
		case "-":return a-b;
		case "*":return a*b;
		case "^":return Math.pow(a, b);
		case "%":return a%b;
		default:
			System.out.println("Not valid opeator");
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
		default:
			System.out.println("Not valid opeator");
			return 0;
		}
	}
	
	public void printOut(ArrayList<Double> nums, ArrayList<String> operations) {
		String out = "";
		for(int i = 0;i<nums.size();i++) {
			if(i!=nums.size()-1) {
				out += nums.get(i) + " " + operations.get(i) + " ";
				
			}else {
				out+= nums.get(i);
			}
		}
		System.out.println(out);
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
