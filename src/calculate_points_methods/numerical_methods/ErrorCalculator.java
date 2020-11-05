package calculate_points_methods.numerical_methods;

import calculate_points_methods.numerical_methods.numerical_methods.NumericalMethod;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * This class contains static methods for calculating different types of errors.
 */
public class ErrorCalculator {
    /**
     * Calculate Global Truncation Error
     * For each point x_i calculate e_i = |yExact_i - yApprox_i|
     */
    public static ArrayList<Point> calculateGTE(ArrayList<Point> exactSolutionPoints, ArrayList<Point> numericalMethodPoints) {
        return calculateTruncationError(exactSolutionPoints, numericalMethodPoints);
    }

    /**
     * Calculate Local Truncation Error
     * For each point x_i calculate e_i+1 =|yExact(x_i+1) - yApprox(x_i, yExact(x_i))|
     */
    public static ArrayList<Point> calculateLTE(ArrayList<Point> exactSolutionPoints, NumericalMethod numericalMethod) {

        // To find the difference between exact solution points and numerical method points,
        // firstly we need to calculate numerical method points
        // since each y_i+1 depends on y_i of exact solution
        ArrayList<Point> numericalMethodPoints = new ArrayList<>(exactSolutionPoints.size());
        numericalMethodPoints.add(new Point(exactSolutionPoints.get(0).x, exactSolutionPoints.get(0).y));

        for (int i = 1; i < exactSolutionPoints.size(); i++) {
            double xPrev = exactSolutionPoints.get(i - 1).x;
            double yPrev = exactSolutionPoints.get(i - 1).y;

            double x = exactSolutionPoints.get(i).x;
            double y = numericalMethod.calculateY(xPrev, yPrev);

            numericalMethodPoints.add(new Point(x, y));
        }

        return calculateTruncationError(exactSolutionPoints, numericalMethodPoints);
    }

    /**
     * Calculate Truncation Error
     * For each point x_i calculate e_i = |yExact_i - yApprox_i|
     */
    public static ArrayList<Point> calculateTruncationError(ArrayList<Point> exactSolutionPoints, ArrayList<Point> numericalMethodPoints) {

        // Check the difference between exact solution and numerical method.
        // Calculate the difference of y value for each x_i
        ArrayList<Point> truncationErrors = new ArrayList<>(exactSolutionPoints.size());
        for (int i = 0; i < exactSolutionPoints.size(); i++) {
            double x = exactSolutionPoints.get(i).x;
            double error = abs(exactSolutionPoints.get(i).y - numericalMethodPoints.get(i).y);

            truncationErrors.add(new Point(x, error));
        }

        return truncationErrors;
    }

    /**
     * Calculate Global Truncation Error
     * For each number of grid cells in range n0..N find the maximum e_i for a particular Numerical Method
     * <p>
     * To do that firstly for each point x_i calculate e_i = |yExact_i - yApprox_i|
     * Then find maximum e_i value
     */
    public static ArrayList<Point> calculateTotalApproximationError(ExactSolution exactSolution, NumericalMethod numericalMethod, double x0, double y0, double X, int n0, int N) {
        ArrayList<Point> totalErrors = new ArrayList<>(N - n0 + 1);

        // Calculate truncation error for the number of grid cells n_i in n0..N
        for (int n = n0; n <= N; n++) {
            ArrayList<Point> exactSolutionPoints = exactSolution.calculatePoints(x0, y0, X, n);
            ArrayList<Point> numericalMethodPoints = numericalMethod.calculatePoints(x0, y0, X, n);

            ArrayList<Point> gte = calculateGTE(exactSolutionPoints, numericalMethodPoints);

            // Find the max error
            double maxError = abs(gte.get(0).y);
            for (Point point : gte) {
                if (maxError < abs(point.y)) {
                    maxError = abs(point.y);
                }
            }

            totalErrors.add(new Point(n, maxError));
        }

        return totalErrors;
    }
}
