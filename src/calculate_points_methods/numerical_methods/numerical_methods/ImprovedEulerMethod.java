package calculate_points_methods.numerical_methods.numerical_methods;

import functions.DerivativeFunction;

/**
 * Improved Euler's method. Calculates points for given derivative function
 */
public class ImprovedEulerMethod extends NumericalMethodCalculation {

    private double k1;
    private double k2;

    public ImprovedEulerMethod(DerivativeFunction function) {
        super(function);
    }

    @Override
    public double calculateY(double xPrev, double yPrev) {
        k1 = calculateK1(xPrev, yPrev);
        k2 = calculateK2(xPrev, yPrev);

        return yPrev + (h / 2) * (k1 + k2);
    }

    private double calculateK1(double x, double y) {
        return function.getValue(x, y);
    }

    private double calculateK2(double x, double y) {
        return function.getValue(x + h, y + h * k1);
    }
}
