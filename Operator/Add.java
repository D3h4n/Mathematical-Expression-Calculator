package Operator;

public class Add implements Operator {
    @Override
    public double eval(double... args) {
        double total = 0.0;

        for (double val : args) {
            total += val;
        }

        return total;
    }
}
