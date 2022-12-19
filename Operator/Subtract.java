package Operator;

public class Subtract implements Operator {
    @Override
    public double eval(double... args) {
        if (args.length == 1)
            return -args[0];

        return args[0] - args[1];
    }
}
