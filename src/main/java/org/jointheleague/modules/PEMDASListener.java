package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class PEMDASListener extends CustomMessageCreateListener{
	
	public static final String MATH_COMMAND = "!solve ";
	public static final String[] ops = {"+","-","*","/","^","%","d"};
	public static boolean haveAnswer = false;
	public static double saveAnswer = 0;
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
		public ArrayList<Boolean> isNegative;
		EquationHolder(ArrayList<Double> num,ArrayList<String> operation, ArrayList<Boolean> isNegative){
			nums = num;
			operations = operation;
			this.isNegative = isNegative;
		}
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		saveAnswer = lastAnswer;
		haveAnswer = false;
		correctSyntax = true;
		explain = false;
		publicEvent = event;
		// TODO Auto-generated method stub
		if(!event.getMessageAuthor().getIdAsString().equals("683742358726377566")) {
		String s = event.getMessageContent();
		if(s.length()>=7) {
			
			String begin = s.substring(0,7);
			//System.out.println(s.substring(7));
				if(begin.equals(MATH_COMMAND)) {
					String equation = s.substring(7);
					//System.out.println(solve(convert(equation)));
					explain = true;
					double answer = solve(convert(equation));
					lastAnswer = answer;
					if(answer%1!=0) {
						outputAnswer(event,""+answer);
					}else {
						outputAnswer(event,""+(int)answer);
					}
					
					explain = false;
				}
		}if(s.substring(s.length()-1).equals("=")||s.substring(s.length()-1).equals("= ")) {
			String equation = s.substring(0,s.length()-1);
			//System.out.println(solve(convert(equation)));
			double answer = solve(convert(equation));
			lastAnswer = answer;
			if(answer%1!=0) {
				outputAnswer(event,""+answer);
			}else {
				outputAnswer(event,""+(int)answer);
			}
			
		}else {
			String equation = s;
			//System.out.println(solve(convert(equation)));
			double answer = solve(convert(equation));
			lastAnswer = answer;
			if(answer%1!=0) {
				outputAnswer(event,""+answer);
			}else {
				outputAnswer(event,""+(int)answer);
			}
			
			
		}
		System.out.println("lastanswer: "+lastAnswer);
		}
	}
	
	public void outputAnswer(MessageCreateEvent e, String s) {
		if(correctSyntax) {
			e.getChannel().sendMessage(s);
		}else {
			lastAnswer = saveAnswer;
		}
	}
		
	public void outputExplain(String s) {
		if(explain) {
			publicEvent.getChannel().sendMessage(s);
		}	
	}
	
	public EquationHolder convert(String s){
		System.out.println("Convert:");
		System.out.println("Last answer: "+lastAnswer);
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
			for(int j = 0; j<s.length();j++) {
				if(!s.substring(j,j+1).equals(" ")) {
					out+=s.substring(j,j+1);
				}
			}
			s = out;
			for(int i = 0;i<s.length();i++) {
				//System.out.println(lastType);
				String sub = s.substring(i,i+1);
				if(sub.equals("-")&&!negativeOperation) {
					temp = "-";
				}else if(isOperation(sub)) {
				
					if(!temp.equals("")) {
						if(temp.toLowerCase().contains("ans")) {
							if(temp.contains("-")) {
								if(lastAnswer>0) {
									nums.add(lastAnswer);
									isNegative.add(true);
								}else {
								nums.add(lastAnswer);
								isNegative.add(false);
								}
							}else {
								if(lastAnswer<0) {
									nums.add(-1*lastAnswer);
									isNegative.add(true);
								}else {
								nums.add(lastAnswer);
								isNegative.add(false);
								}
							}
						}else {
							if(Double.parseDouble(temp)<0) {
								nums.add(Math.abs(Double.parseDouble(temp)));
								isNegative.add(true);
							}else {
								nums.add(Double.parseDouble(temp));
								isNegative.add(false);
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
				}else if(sub.equals("(")&&!foundP) {
					startP = i;
					lastType = 2;
					negativeOperation = false;
				}else if(sub.equals(")")&&!foundP) {
					endP = i;
					lastType = 2;
					foundP = true;
					negativeOperation = true;
				}else if(sub.equals("(")||sub.equals(")")){
					
				}else if(sub.equals("|")){
					//System.out.println("LastType: "+lastType);
					//System.out.println("i: "+i);
					if((lastType == 1||lastType == -1)&&!foundA) {
						startA = i;
						foundBeginA = true;
						//System.out.println("begin at "+i);
						negativeOperation = false;
					}else if((lastType == 0||i==s.length()-1)&&!foundA&&foundBeginA) {
						endA = i;
						foundA = true;
						//System.out.println("end at "+i);
						negativeOperation = true;
					}
				}else {	
					lastType = 0;
					temp += sub;
					negativeOperation = true;
				}
				
			}
			if(temp.toLowerCase().contains("ans")) {
				if(temp.contains("-")) {
					if(lastAnswer>0) {
						nums.add(lastAnswer);
						isNegative.add(true);
					}else {
					nums.add(lastAnswer);
					isNegative.add(false);
					}
				}else {
					if(lastAnswer<0) {
						nums.add(-1*lastAnswer);
						isNegative.add(true);
					}else {
					nums.add(lastAnswer);
					isNegative.add(false);
					}
				}
				
			}else {
			
			nums.add(Math.abs(Double.parseDouble(temp)));
			isNegative.add(Double.parseDouble(temp)<0);
			}
			System.out.println(s);
			//System.out.println(equationToString(new EquationHolder(nums,operations,isNegative)));
			System.out.println("ABS: Begin at "+startA+" end at "+endA);
			System.out.println("PAREN: Begin at "+startP+" end at "+endP);
			for(int i = 0;i<nums.size();i++) {
				System.out.print(isNegative.get(i)+" ");
				System.out.print(nums.get(i)+"  ");
			}
			System.out.println("");
			System.out.println(s);
			
			if(foundP) {
				String parenString = s.substring(startP+1,endP);
				String simplified = s.substring(0,startP)+solve(convert(parenString))+s.substring(endP+1);
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
	
	public double solve(EquationHolder equation) {
		try {
			outputExplain(equationToString(equation));
			if(equation.nums.size()==1) {
				System.out.println("size == 1");
				System.out.println("op size = "+equation.operations.size());
				System.out.println("num = "+equation.nums.get(0));
				System.out.println("is negative: "+equation.isNegative.get(0));
				if(equation.operations.size()==0&&equation.isNegative.get(0).equals(true)) {
					System.out.println(-1*equation.nums.get(0));
					return -1*equation.nums.get(0);
				}
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
			double a;
			if(equation.isNegative.get(topIndex)) {
				a = -1*equation.nums.get(topIndex);
				System.out.println("setting a to -");
			}else {
				a = equation.nums.get(topIndex);
				System.out.println("setting a to +");
			}
			double b;
			if(equation.isNegative.get(topIndex+1)) {
				b = -1*equation.nums.get(topIndex+1);
				System.out.println("setting b to -");
			}else {
				b = equation.nums.get(topIndex+1);
				System.out.println("setting b to +");
			}
			double newCalc = calcSimple(a,b, equation.operations.get(topIndex));
			System.out.println("newcalc"+newCalc);
			ArrayList<Double> num = equation.nums;
			ArrayList<String> operation = equation.operations;
			ArrayList<Boolean> isNegative = equation.isNegative;
			isNegative.set(topIndex, newCalc<0);
			System.out.println(isNegative.get(topIndex));
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
	
	public double calcSimple(Double a, Double b, String operation) {
		System.out.println(a+" "+operation+" "+b);
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
