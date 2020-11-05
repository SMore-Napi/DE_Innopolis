package functions;

import java.util.ArrayList;

public class DerivativeFunctionVariant19 implements DerivativeFunction {
    @Override
    public double getValue(double x, double y) {
        return 2 * x + y - 3;
    }

    @Override
    public ArrayList<Double> getDiscontinuityPoints() {
        return new ArrayList<>(0);
    }
}
