import java.util.Scanner;

public class Calculator {
  public static void main(String[] args) {
    final Scanner stdin = new Scanner(System.in); // scanner for user input

    // get user input
    System.out.print("\nEnter an expression:(end) ");
    String input = stdin.nextLine();
    
    while (!input.isBlank()) { // loop while user inputs an expression
      double result = calculateExpression(input); // calculate value of expression



      if (Math.floor(result) == result) {
        System.out.println("Result: " + (int) result); // output value
      } else {
        System.out.println("Result: " + result); // output value
      }
      
    
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
  
  /**
   * Replace Subexpression in parentheses with their value
   * 
   * @param expression
   * @return resulting expression
   */

  static String calculateSubexpressions(String expression) {
    int startIdx = expression.indexOf("("); // find the index of open parenthesis
    
    while (startIdx != -1) {
      // find related close parenthesis
      int endIdx = findCloseParenthesis(expression, startIdx);

      // extract subexpression
      String subexpression = expression.substring(startIdx + 1, endIdx - 1);
      
      // calculate value of subexpression
      double value = calculateExpression(subexpression);
      
      // replace subexpression with value
      expression = expression.substring(0, startIdx) + String.valueOf(value) + expression.substring(endIdx);

      // find next subexpression
      startIdx = expression.indexOf("(");
    }

    return expression;
  }
  
  /**
   * Find related close parenthesis
   * 
   * @param expression - expression with parentheses
   * @param startIdx - index of open parenthesis
   * @return index of close parenthesis
   */
  static int findCloseParenthesis(String expression, int startIdx) {
    int length = expression.length();
    int parenthesesCount = 1;
    int endIdx = startIdx + 1;
    
    for (; parenthesesCount != 0; endIdx++) {
      // exit program if invalid number of parentheses
      if (endIdx == length) {
        System.exit(2);
      }
      
      if (expression.charAt(endIdx) == '(') {
        parenthesesCount++;
      } else if (expression.charAt(endIdx) == ')') {
        parenthesesCount--;
      }
    }

    return endIdx;
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
