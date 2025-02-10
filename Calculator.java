import java.util.Scanner;

public class Calculator  {
    static String inputInfixExpression;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the expression: ");
        inputInfixExpression = scanner.nextLine();

        try {
            String postfixExpression = Postfix.convert(inputInfixExpression);
            double result = Postfix.evaluate(postfixExpression);

            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error occured: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }


}