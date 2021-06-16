import java.util.Scanner;

public class Calculator {
  public static void main(String[] args) {
    final Scanner stdin = new Scanner(System.in);

    System.out.print("Enter an expression: ");
    String input = stdin.nextLine();
    
    while (input.compareTo("end") != 0) {
      double result = calculateExpression(input);
      System.out.println("Result: " + result + "\n");
      
      System.out.print("Enter an expression: ");
      input = stdin.nextLine();
    }
    
    stdin.close(); // close scanner
  }

  static double calculateExpression(String expression) {
    final String[] operations = {"-", "+", "*", "/", "^"};
    String[] operands;
    String operation = "";
    expression = removeSpaces(expression);
    
    for (String opp: operations) {
      if (expression.contains(opp)) {
        operation = opp;
        break;
      }
    }

    if (operation.isBlank()) {
      return expression.isBlank() ? 0 : Double.valueOf(expression);
    }
    else {
      operands = splitByOperation(expression, operation);

      switch(operation) {
        case "-":
          return calculateExpression(operands[0]) - calculateExpression(operands[1]);

        case "+":
          return calculateExpression(operands[0]) + calculateExpression(operands[1]);

        case "*":
          return calculateExpression(operands[0]) * calculateExpression(operands[1]);

        case "/":
          return calculateExpression(operands[0]) / calculateExpression(operands[1]);
        
        case "^":
          return Math.pow(calculateExpression(operands[0]),  calculateExpression(operands[1]));
      }

      return Integer.MAX_VALUE;
    }
  }

  static String removeSpaces(String input) {
    return input.replaceAll(" ", "");
  }
  
  static String[] splitByOperation(String expression, String operation) {
    String[] result = {"", ""};
    
    int position = expression.lastIndexOf(operation);
  
    result[0] = expression.substring(0, position);
    result[1] = expression.substring(position + 1);
    
    return result;
  }
}
