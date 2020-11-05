package calculate_points_methods.numerical_methods.numerical_methods;

import functions.DerivativeFunction;

/**
 * Euler's method. Calculates points for given derivative function
 */
public class EulerMethod extends NumericalMethodCalculation {

    public EulerMethod(DerivativeFunction function) {
        super(function);
    }

    @Override
    public double calculateY(double xPrev, double yPrev) {
        return yPrev + h * function.getValue(xPrev, yPrev);
    }
}
