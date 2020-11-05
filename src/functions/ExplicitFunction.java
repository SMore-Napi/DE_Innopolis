package functions;

import java.util.ArrayList;

/**
 * Interface of explicit function y = f(x)
 * with some constant C which can be expressed using IVP (x0, y0)
 */
public interface ExplicitFunction {
    double getValue(double x, double x0, double y0);

    ArrayList<Double> getDiscontinuityPoints();
}
