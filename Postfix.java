import java.util.Stack;

public class Postfix {
    public static String convertToPostfix (String infixExpression) throws Exception {
        Stack<Character> operatorStack = new Stack<>();
        String postfix = "";
        char[] tokens = infixExpression.replaceAll("\\s+", "").toCharArray(); // Remove spaces

        for (char token : tokens) {
            if (Character.isDigit(token) || token == '.') {
                postfix += token;
            } else {
                postfix += " "; // space to seperate numbers

                if (token == '(') {
                    operatorStack.push(token);
                } else if (token == ')') {
                    while (operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        postfix += operatorStack.pop() + " ";
                    }
                    if (operatorStack.isEmpty()) {
                        throw new Exception("Mismatched Paranthesis");
                    }
                    operatorStack.pop();
                } else if (isOperator(token)) {
                    while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(token)) {
                        postfix += operatorStack.pop() + " ";
                    }
                    operatorStack.push(token);
                } else {
                    throw new Exception("Invalid Character: " + token);
                }
            }
        }

        // Pop remaining operators
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == '(') {
                throw new Exception("Mismatched parentheses");
            }
            postfix += " " + operatorStack.pop();
        }

        return postfix.trim();
    }

    // Method to evaluate a postfix expression
    public double evaluatePostfix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+"); // Split by spaces

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token)); // Push numbers to stack
            } else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new Exception("Invalid postfix expression");
                }
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyOperator(a, b, token.charAt(0)));
            } else {
                throw new Exception("Invalid token: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new Exception("Invalid expression evaluation");
        }

        return stack.pop();
    }

    // Executing mathematical operation
    private double applyOperator(double a, double b, char operator) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    // Check if a character is an operator
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
