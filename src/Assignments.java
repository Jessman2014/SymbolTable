import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import ExpressionTree.Node;


public class Assignments {

	private class Node {
		Node left;
		String data;
		Node right;

		Node(Node l, String d, Node r) {
			left = l;
			data = d;
			right = r;
		}
	}
	
	Node root;
	String chain = "";
	Stack<Node> operand = new Stack<>();
	Stack<Node> operator = new Stack<>();
	
	public Assignments(String assn) {
		// TODO Auto-generated constructor stub
		
	}

	
	/*
	 * Scans through expression until empty. During scan it pushes operands onto that stack and treats operators separately. After scan it finishes
	 * the rest of the operations left on that stack and returns the final resulting node as the root.
	 */
	private void buildInfix(String exp) {
		Scanner scan = new Scanner (exp);
		String str;
		while (scan.hasNext()) {
			str = scan.next();
			if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
				operand.push(new Node(null, str, null));
			}
			else {
				solve(str);
			}
		}
		// After the expression is read there still might be operations left on the stack depending on associativity
		// and precedence order if there were no surrounding parenthesis.
		while (operator.size() > 0) {
			offStack(operator.pop().data);
		}
		root = operand.pop();
		scan.close();
	}
	
	// Gives us the precedence ranking of the operators.
	public int precedence (String a) {
		switch (a) {
		case "!":
			return 3;
		case "^":
			return 2;
		case "*":
			return 1;
		case "/":
			return 1;
		case "+":
			return 0;
		case "-":
			return 0;
		default:
			return -1;
		
		}
		
	}
	
	public void offStack(String a) {
		if (a.equals("!")) {
			Node n = new Node(null, a, operand.pop());
			operand.push(n);
		}
		else {
			Node right = operand.pop();
			Node n = new Node(operand.pop(), a, right);
			operand.push(n);
		}
	}
	
	// Recursive method to help solve with large chain reactions.
	public void solve (String a) {
		// If nothing or a left paren is on the stack due to previous operations we don't need to check for precedence
		if (operator.empty() || operator.peek().data.equals("(") || a.equals("(")) {
			operator.push(new Node (null, a, null));
		}
		else {
			// When the right paren appears everything needs to be solved till its appropriate left paren appears.
			if (a.equals(")")) {
				while (!operator.peek().data.equals("("))
					offStack(operator.pop().data);
				// We need to get rid of the left paren so it doesn't end up in the tree.
				operator.pop();
			}
			// Other operators need to be evaluated based on what's on the stack and precedence.
			else {
				// Otherwise we check for precedence and put higher ones on the stack. Lower precedence causes operators to pop 
				// off until the current operator can be pushed on.
				int onStack = precedence(operator.peek().data);
				int current = precedence(a);
				if (current > onStack || (current == onStack && current <= 1)) 
					operator.push(new Node (null, a, null));
				else {
					offStack(operator.pop().data);
					solve(a);
				}
			}
		}
	}

	public int evaluate() {
		return (int)evaluate(root);
	}
	
	/*
	 * Uses postfix to explore left and right trees till it finds a leaf and returns the left leaf
	 * as the variable a and the right leaf as variable b. it then evaluates them based on the operator.
	 */
	public double evaluate(Node r) {
		if (r != null) {
			double a = evaluate(r.left);
			double b = evaluate(r.right);
			switch (r.data) {
			case "+":
				return a+b;
			case "-":
				return a-b;
			case "*":
				return a*b;
			case "/":
				return a/b;
			case "^":
				return Math.pow(a, b);
			case "!":
				return -b;
			default:
				return Double.parseDouble(r.data);
			}
		}
		else
			return 0.0;
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader r = new BufferedReader(new FileReader (args[0]));
		Assignments a;
		String assn = r.readLine();
		
		while (assn != null) {
			a = new Assignments(assn);
			
		}
		
	}

}