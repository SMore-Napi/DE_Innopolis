package calculate_points_methods.numerical_methods.numerical_methods;

import calculate_points_methods.numerical_methods.Point;
import functions.DerivativeFunction;

import java.util.ArrayList;

/**
 * Abstract class for numerical approximation methods.
 * It calculates points for given derivative function.
 */
public abstract class NumericalMethodCalculation implements NumericalMethod {
    protected DerivativeFunction function;
    protected double h;
    private final ArrayList<Double> discontinuityPoints;

    public NumericalMethodCalculation(DerivativeFunction function) {
        this.function = function;
        discontinuityPoints = function.getDiscontinuityPoints();
    }

    /**
     * Interface implementation
     *
     * @param x0 - initial x value
     * @param y0 - initial y value
     * @param X  - the end of the x range
     * @param N  - number of grid cells
     * @return list of points
     */
    @Override
    public ArrayList<Point> calculatePoints(double x0, double y0, double X, int N) {

        h = ((X - x0) / N);

        ArrayList<Point> points = new ArrayList<>(N + 1);
        points.add(new Point(x0, y0));

        for (int i = 1; i <= N; i++) {
            double xPrev = points.get(i - 1).x;
            double yPrev = points.get(i - 1).y;

            double x = xPrev + h;
            double y = calculateY(xPrev, yPrev);

            points.add(new Point(x, y));
        }

        return points;
    }

    /**
     * Each numerical approximation method will have its own implementation of calculation
     */
    @Override
    public abstract double calculateY(double xPrev, double yPrev);

    @Override
    public ArrayList<Double> getDiscontinuityPointsFromInterval(double x0, double X) {
        ArrayList<Double> points = new ArrayList<>();
        for (Double x : discontinuityPoints) {
            if (x0 <= x && x <= X) {
                points.add(x);
            }
        }
        return points;
    }
}
