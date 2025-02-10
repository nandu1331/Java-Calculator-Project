import java.util.Stack;

public class Postfix {
    public static String convertToPostfix (String infixExpression) throws Exception {
        Stack<Character> operatorStack = new Stack<>();
        String postfix = "";
        char[] tokens = infixExpression.replaceAll("\\s+", "").toCharArray();

        for (char token : tokens) {
            if (Character.isDigit(token) || token == '.') {
                postfix += token;
            } else {
                postfix += " ";

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
    }
}
