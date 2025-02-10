import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a mathematical expression: ");
        String infixExpression = scanner.nextLine();

        try {
            Postfix postfix = new Postfix();

            // Convert infix to postfix
            String postfixExpression = postfix.convertToPostfix(infixExpression);
            System.out.println("Postfix Notation: " + postfixExpression);

            // Evaluate the postfix expression
            double result = postfix.evaluatePostfix(postfixExpression);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}