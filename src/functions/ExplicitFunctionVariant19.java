package functions;

import java.util.ArrayList;

public class ExplicitFunctionVariant19 implements ExplicitFunction {
    @Override
    public double getValue(double x, double x0, double y0) {
        return 1 - 2 * x + (y0 + 2 * x0 - 1) * Math.exp(x - x0);
    }

    @Override
    public ArrayList<Double> getDiscontinuityPoints() {
        return new ArrayList<>(0);
    }
}
