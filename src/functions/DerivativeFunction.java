package functions;

import java.util.ArrayList;

/**
 * Interface of derivative function y' = f(x,y)
 */
public interface DerivativeFunction {
    double getValue(double x, double y);

    ArrayList<Double> getDiscontinuityPoints();
}
