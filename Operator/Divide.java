package Operator;

public class Divide implements Operator {
    @Override
    public double eval(double... args) {
        return args[0] / args[1];
    }
}
