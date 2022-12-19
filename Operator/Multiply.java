package Operator;

public class Multiply implements Operator {
    @Override
    public double eval(double... args) {
        double result = 1;

        for (double val : args) {
            result *= val;
        }

        return result;
    }
}
