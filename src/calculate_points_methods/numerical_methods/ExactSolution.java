package calculate_points_methods.numerical_methods;

import functions.ExplicitFunction;

import java.util.ArrayList;

/**
 * The class for calculating the exact solution
 * for a particular explicit function.
 */
public class ExactSolution {
    private final ExplicitFunction function;
    private final ArrayList<Double> discontinuityPoints;

    public ExactSolution(ExplicitFunction function) {
        this.function = function;
        discontinuityPoints = function.getDiscontinuityPoints();
    }

    /**
     * Calculate points for given values
     *
     * @param x0 - initial x value
     * @param y0 - initial y value
     * @param X  - the end of the x range
     * @param N  - number of grid cells
     * @return list of points
     */
    public ArrayList<Point> calculatePoints(double x0, double y0, double X, int N) {

        // Calculate the grid cell size
        double h = ((X - x0) / N);

        // Since N is the number of grid cells, then the total number
        // of points between these cells would be N+1
        ArrayList<Point> points = new ArrayList<>(N + 1);
        points.add(new Point(x0, y0));

        for (int i = 1; i <= N; i++) {
            double xPrev = points.get(i - 1).x;

            double x = xPrev + h;

            if (!discontinuityPoints.contains(x)) {
                double y = function.getValue(x, x0, y0);
                points.add(new Point(x, y));
            }
        }

        return points;
    }
}
