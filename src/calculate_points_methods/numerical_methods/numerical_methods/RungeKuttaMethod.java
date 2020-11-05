package calculate_points_methods.numerical_methods.numerical_methods;

import functions.DerivativeFunction;

/**
 * Runge-Kutta method. Calculates points for given derivative function
 */
public class RungeKuttaMethod extends NumericalMethodCalculation {

    private double k1;
    private double k2;
    private double k3;
    private double k4;

    public RungeKuttaMethod(DerivativeFunction function) {
        super(function);
    }

    /**
     * Calculate y_i value using numner of K = 4
     */
    @Override
    public double calculateY(double xPrev, double yPrev) {
        k1 = calculateK1(xPrev, yPrev);
        k2 = calculateK2(xPrev, yPrev);
        k3 = calculateK3(xPrev, yPrev);
        k4 = calculateK4(xPrev, yPrev);

        return yPrev + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
    }

    private double calculateK1(double x, double y) {
        return function.getValue(x, y);
    }

    private double calculateK2(double x, double y) {
        return function.getValue(x + h / 2, y + (h / 2) * k1);
    }

    private double calculateK3(double x, double y) {
        return function.getValue(x + h / 2, y + (h / 2) * k2);
    }

    private double calculateK4(double x, double y) {
        return function.getValue(x + h, y + h * k3);
    }
}
