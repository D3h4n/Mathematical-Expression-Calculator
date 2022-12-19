package Operator;

public class Exponent implements Operator {
    @Override
    public double eval(double... args) {
        return Math.pow(args[0], args[1]);
    }
}
