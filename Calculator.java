import Operator.*;

import java.util.HashMap;
import java.util.Scanner;

public class Calculator {
    final HashMap<Character, Operator> operations;

    Calculator() {
        operations = new HashMap<>();

        operations.put('+', new Add());
        operations.put('-', new Subtract());
        operations.put('*', new Multiply());
        operations.put('/', new Divide());
        operations.put('^', new Exponent());
    }

    public static void main(String[] args) {
        final Scanner stdin = new Scanner(System.in);
        final Calculator calculator = new Calculator();

        System.out.print("\nEnter an expression:(end) ");
        String input = stdin.nextLine();

        while (!input.isEmpty()) {
            try {
                double result = calculator.calculateExpression(input);
                System.out.println("Result: " + result);
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }

            System.out.print("\nEnter an expression:(end) ");
            input = stdin.nextLine();
        }

        stdin.close();
    }


    /**
     * Calculate the value of the expression
     *
     * @param expression - expression to be calculated
     * @return value of the expression
     */
    double calculateExpression(String expression) throws NumberFormatException {
        expression = evaluateSubexpressions(expression);
        return evaluateExpression(expression);
    }

    /**
     * Replace Subexpression in parentheses with their value
     *
     * @param expression expression with parentheses
     * @return resulting expression
     */
    private String evaluateSubexpressions(String expression) {
        int startIdx = expression.indexOf('(');

        while (startIdx != -1) {
            int endIdx = findCloseParenthesis(expression, startIdx);
            String subexpression = expression.substring(startIdx + 1, endIdx - 1);
            expression = expression.substring(0, startIdx) + calculateExpression(subexpression) + expression.substring(endIdx);
            startIdx = expression.indexOf('(');
        }

        return expression;
    }

    /**
     * Evaluate an expression with no parentheses.
     *
     * @param expression without parentheses
     * @return the value of the expression
     */
    private double evaluateExpression(String expression) {
        if (expression.isBlank())
            return 0.0;

        Operator operation = null;
        int operationIndex = -1;

        for (char c : operations.keySet()) {
            if ((operationIndex = expression.lastIndexOf(c)) != -1) {
                operation = operations.get(c);
                break;
            }
        }

        if (operation == null)
            return Double.parseDouble(expression);

        String[] operands = splitByOperation(expression, operationIndex);
        return operation.eval(calculateExpression(operands[0]), calculateExpression(operands[1]));
    }

    /**
     * Find related close parenthesis
     *
     * @param expression expression with parentheses
     * @param startIdx   index of open parenthesis
     * @return index of close parenthesis
     */
    private int findCloseParenthesis(String expression, int startIdx) {
        final int length = expression.length();
        int parenthesesCount = 1;
        int endIdx = startIdx + 1;

        for (; parenthesesCount != 0; endIdx++) {
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
     * @param expression     expression to separate
     * @param operationIndex index of operation in expression
     * @return ordered pair of operands
     */
    private String[] splitByOperation(String expression, int operationIndex) {
        String[] result = {"", ""};

        result[0] = expression.substring(0, operationIndex);
        result[1] = expression.substring(operationIndex + 1);

        return result;
    }
}
