# Java-Calculator-Project

Expression Calculator (Infix to Postfix & Evaluation)

Introduction

This project implements a Mathematical Expression Calculator in Java, which:
✅ Converts an infix expression to postfix notation using the Shunting-Yard Algorithm.
✅ Evaluates the postfix expression using a stack-based approach.
✅ Handles spaces in expressions gracefully.
✅ Implements exception handling for invalid expressions.


---

Project Structure

The project follows a modular approach for better readability and reusability:

📂 Expression Calculator
├── 📄 Calculator.java        # Main class to handle user input and process the expression
├── 📄 Postfix.java           # Contains both infix-to-postfix conversion & postfix evaluation
├── 📄 README.md              # Project documentation


---

Workflow

Step 1: User Input

The user enters a mathematical expression in infix notation (e.g., 3 + 5 * 2).

Spaces in the input are handled automatically.


Step 2: Convert to Postfix

The Postfix class uses the Shunting-Yard Algorithm to convert the infix expression to postfix notation.


Example:

Input:  3 + 5 * 2
Postfix Notation:  3 5 2 * +

Step 3: Evaluate Postfix Expression

The Postfix class evaluates the postfix expression using a stack.


Example:

Postfix Notation:  3 5 2 * +
Result:  13.0

Step 4: Display Result

The evaluated result is printed to the user.



---

Code Explanation

1️⃣ Calculator.java

Handles user input, calls the Postfix class, and prints the result.

import java.util.Scanner;

public class Calculator {
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
System.out.print("Enter a mathematical expression: ");
String infixExpression = scanner.nextLine();

        try {
            Postfix postfix = new Postfix();
            String postfixExpression = postfix.convertToPostfix(infixExpression);
            System.out.println("Postfix Notation: " + postfixExpression);
            double result = postfix.evaluatePostfix(postfixExpression);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

2️⃣ Postfix.java

Handles both conversion (infix → postfix) and evaluation.

import java.util.Stack;

public class Postfix {
    public String convertToPostfix(String infix) throws Exception {
    Stack<Character> operatorStack = new Stack<>();
    String postfix = "";
    char[] tokens = infix.replaceAll("\\s+", "").toCharArray();

        for (char token : tokens) {
            if (Character.isDigit(token) || token == '.') {
                postfix += token;
            } else {
                postfix += " ";
                if (token == '(') {
                    operatorStack.push(token);
                } else if (token == ')') {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        postfix += operatorStack.pop() + " ";
                    }
                    if (operatorStack.isEmpty()) throw new Exception("Mismatched parentheses");
                    operatorStack.pop();
                } else if (isOperator(token)) {
                    while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token)) {
                        postfix += operatorStack.pop() + " ";
                    }
                    operatorStack.push(token);
                } else {
                    throw new Exception("Invalid character: " + token);
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == '(') throw new Exception("Mismatched parentheses");
            postfix += " " + operatorStack.pop();
        }

        return postfix.trim();
    }

    public double evaluatePostfix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) throw new Exception("Invalid postfix expression");
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyOperator(a, b, token.charAt(0)));
            } else {
                throw new Exception("Invalid token: " + token);
            }
        }

        if (stack.size() != 1) throw new Exception("Invalid expression evaluation");
        return stack.pop();
    }

    private double applyOperator(double a, double b, char operator) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': if (b == 0) throw new ArithmeticException("Division by zero"); return a / b;
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }
}


---

Example Runs

Here are some expressions from the whiteboard with their expected outputs:


---

Features

✔ Supports basic arithmetic operations (+, -, *, /).
✔ Handles spaces in input expressions.
✔ Handles parentheses (()) for precedence.
✔ Uses stacks for both conversion and evaluation.
✔ Exception handling for invalid expressions.


---

How to Run

1️⃣ Compile the program:

javac Calculator.java Postfix.java

2️⃣ Run the program:

java Calculator

3️⃣ Enter an expression and see the result!


---

Possible Enhancements

Add exponentiation (^) and modulus (%) operators.

Support functions like sin(), cos(), sqrt().

Implement fractional numbers properly (1/3 as 0.3333).



---

Conclusion

This project demonstrates a structured approach to mathematical expression evaluation by separating concerns between input handling, parsing, and evaluation. The use of stacks ensures efficiency, and the modular design makes it easy to expand.
