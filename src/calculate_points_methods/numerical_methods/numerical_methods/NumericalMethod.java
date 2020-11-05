package calculate_points_methods.numerical_methods.numerical_methods;

import calculate_points_methods.numerical_methods.Point;

import java.util.ArrayList;

/**
 * Interface for numerical approximation methods
 */
public interface NumericalMethod {
    /**
     * Calculate the list of points for given values
     *
     * @param x0 - initial x value
     * @param y0 - initial y value
     * @param X  - the end of the x range
     * @param N  - number of grid cells
     * @return list of points
     */
    ArrayList<Point> calculatePoints(double x0, double y0, double X, int N);

    /**
     * Calculate a particular y_i value according to the y_i-1 and x_y-1 values
     * using the numerical approximation method
     */
    double calculateY(double xPrev, double yPrev);

    ArrayList<Double> getDiscontinuityPointsFromInterval(double x0, double X);
}
