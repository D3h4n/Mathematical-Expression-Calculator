import java.util.Scanner;

public class Calculator {
  public static void main(String[] args) {
    final Scanner stdin = new Scanner(System.in); // scanner for user input

    // get user input
    System.out.print("Enter an expression:(end) ");
    String input = stdin.nextLine();
    
    while (!input.isBlank()) { // loop while user inputs an expression
      double result = calculateExpression(input); // calculate value of expression
      System.out.println("Result: " + result); // output value
    
      // get user input
      System.out.print("Enter an expression:(end) ");
      input = stdin.nextLine();
    }
    
    stdin.close(); // close scanner
  }


  /**
   * Calculate the value of the expression
   * 
   * @param expression - expression to be calculated
   * @return value of the expression
   */
  static double calculateExpression(String expression) {
    final String[] operations = {"+", "-", "*", "/", "^"}; // possible operatations in order of precedence 
    String operation = ""; // string to store operation
    
    expression = expression.replaceAll(" ", ""); // remove all whitespace from expression

    // assume blank expression has a value of 0
    if (expression.isBlank()) {
      return 0;
    }

    // Calculate value of subexpressions in parentheses
    expression = calculateSubexpressions(expression);

    // iterate to find operand with most precendence in expression
    for (String opp: operations) {
      if (expression.contains(opp)) {
        operation = opp;
        break;
      }
    }

    if (operation.isBlank()) { 
      // if no operand is found return value of expression
      return Double.valueOf(expression);
    }
    else {
      String[] operands = splitByOperation(expression, operation); // split string into the two operands of the operation

      // perform operation on operands and return result
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
  
  static String calculateSubexpressions(String expression) {
    int start = expression.indexOf("("); // find the index of open parenthesis
    
    while (start != -1) {
      // find related close parenthesis
      int end = findCloseParenthesis(expression, start);

      // extract subexpression
      String subexpression = expression.substring(start + 1, end - 1);
      
      // calculate value of subexpression
      double value = calculateExpression(subexpression);
      
      // replace subexpression with value
      expression = expression.replace(String.format("(%s)", subexpression), String.valueOf(value));

      // find next subexpression
      start = expression.indexOf("(");
    }

    return expression;
  }

  static int findCloseParenthesis(String expression, int start) {
    int length = expression.length();
    int parenthesesCount = 1;
    int end = start + 1;
    
    for (; parenthesesCount != 0; end++) {
      // exit program if invalid number of parentheses
      if (end == length) {
        System.exit(2);
      }
      
      if (expression.charAt(end) == '(') {
        parenthesesCount++;
      } else if (expression.charAt(end) == ')') {
        parenthesesCount--;
      }
    }

    return end;
  }

  /**
   * Split the operands of a binary operator 
   * 
   * @param expression - expression to separate
   * @param operation - operation to separate by
   * @return array of strings with separated operands
   */
  static String[] splitByOperation(String expression, String operation) {
    String[] result = {"", ""};
    
    // get index of operation
    int position = expression.lastIndexOf(operation);

    // split first half
    result[0] = expression.substring(0, position);
    
    // split second half
    result[1] = expression.substring(position + 1);
    
    return result;
  }
}
