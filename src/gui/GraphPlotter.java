package gui;

import calculate_points_methods.numerical_methods.ErrorCalculator;
import calculate_points_methods.numerical_methods.ExactSolution;
import calculate_points_methods.numerical_methods.numerical_methods.*;
import calculate_points_methods.numerical_methods.Point;
import functions.*;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helper class for calculating Line Charts series for given values
 */
public class GraphPlotter {

    // Input values from GUI
    private final double x0;
    private final double y0;
    private final double X;
    private final int n0;
    private final int N;

    private final ArrayList<Double> discontinuityPoints;

    // The function to plot
    private final ExplicitFunction explicitFunction;
    private final DerivativeFunction derivativeFunction;

    // The map of graphs for showing the Approximation chart.
    // For each method name (key) it stores the series (value) for this method.
    private final HashMap<String, XYChart.Series> seriesGraphMethodsChart;
    // The map of graphs for showing the GTE chart.
    // For each method name (key) it stores the series (value) for this method.
    private final HashMap<String, XYChart.Series> seriesGTEChart;
    // The map of graphs for showing the LTE chart.
    // For each method name (key) it stores the series (value) for this method.
    private final HashMap<String, XYChart.Series> seriesLTEChart;
    // The map of graphs for showing the Total Approximation Error chart.
    // For each method name (key) it stores the series (value) for this method.
    private final HashMap<String, XYChart.Series> seriesTotalErrorChart;

    public GraphPlotter(double x0, double y0, double X, int n0, int N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.n0 = n0;
        this.N = N;

        explicitFunction = new ExplicitFunctionVariant19();
        derivativeFunction = new DerivativeFunctionVariant19();

        seriesGraphMethodsChart = new HashMap<>(4);
        seriesGTEChart = new HashMap<>(3);
        seriesLTEChart = new HashMap<>(3);
        seriesTotalErrorChart = new HashMap<>(3);

        discontinuityPoints = getDiscontinuityPointsFromInterval();

        if (isPossibleCalculate()) {
            calculateSeriesForEachChart();
        }
    }

    public HashMap<String, XYChart.Series> getSeriesGraphMethodsChart() {
        return seriesGraphMethodsChart;
    }

    public HashMap<String, XYChart.Series> getSeriesGTEChart() {
        return seriesGTEChart;
    }

    public HashMap<String, XYChart.Series> getSeriesLTEChart() {
        return seriesLTEChart;
    }

    public HashMap<String, XYChart.Series> getSeriesTotalErrorChart() {
        return seriesTotalErrorChart;
    }

    public boolean isPossibleCalculate() {
        return discontinuityPoints.size() == 0;
    }

    public ArrayList<Double> getDiscontinuityPoints() {
        return discontinuityPoints;
    }

    private ArrayList<Double> getDiscontinuityPointsFromInterval() {
        NumericalMethod numericalMethod = new EulerMethod(derivativeFunction);
        return numericalMethod.getDiscontinuityPointsFromInterval(x0, X);
    }

    /**
     * Calculate values for each chart map
     */
    private void calculateSeriesForEachChart() {

        // Calculate the exact solution with number of grid cells = N
        ExactSolution exactSolution = new ExactSolution(explicitFunction);
        ArrayList<Point> exactSolutionPoints = exactSolution.calculatePoints(x0, y0, X, N);

        // Calculate approximation, GTE, LTE and Total Error graphs for Euler's method
        HashMap<Chart, ArrayList<Point>> eulerMethodGraphs = calculateGraphsForNumericalMethod(new EulerMethod(derivativeFunction), exactSolution, exactSolutionPoints);
        // Calculate approximation, GTE, LTE and Total Error graphs for Improved Euler's method
        HashMap<Chart, ArrayList<Point>> improvedEulerMethodGraphs = calculateGraphsForNumericalMethod(new ImprovedEulerMethod(derivativeFunction), exactSolution, exactSolutionPoints);
        // Calculate approximation, GTE, LTE and Total Error graphs for Runge-Kutta method
        HashMap<Chart, ArrayList<Point>> rungeKuttaMethodGraphs = calculateGraphsForNumericalMethod(new RungeKuttaMethod(derivativeFunction), exactSolution, exactSolutionPoints);

        // Add Exact Solution graph to the Approximation chart.
        // Firstly, recalculate points to make this graph looks like smooth curve.
        // For this purpose I decided to calculate points for each x with h=0.01 step.
        // Thus, for parameter N I placed this value N = 100 * (X - x0)
        setSeriesOfChartForExactSolution(exactSolution.calculatePoints(x0, y0, X, (int) (100 * (X - x0))));
        // Distribute Euler' method graphs for Approximation, GTE, LTE and Total Error Approximation charts
        setSeriesOfChartsForNumericalMethod(eulerMethodGraphs, GraphNames.EULER_METHOD_NAME);
        // Distribute Improved Euler' method graphs for Approximation, GTE, LTE and Total Error Approximation charts
        setSeriesOfChartsForNumericalMethod(improvedEulerMethodGraphs, GraphNames.IMPROVED_EULER_METHOD_NAME);
        // Distribute Runge-Kutta method graphs for Approximation, GTE, LTE and Total Error Approximation charts
        setSeriesOfChartsForNumericalMethod(rungeKuttaMethodGraphs, GraphNames.RUNGE_KUTTA_METHOD_NAME);
    }

    /**
     * Calculate approximation, GTE, LTE and Total Error graphs for some numerical approximation method
     */
    private HashMap<Chart, ArrayList<Point>> calculateGraphsForNumericalMethod(NumericalMethod numericalMethod, ExactSolution exactSolution, ArrayList<Point> exactSolutionPoints) {
        HashMap<Chart, ArrayList<Point>> numericalMethodGraphs = new HashMap<>(4);

        numericalMethodGraphs.put(Chart.GRAPH, numericalMethod.calculatePoints(x0, y0, X, N));
        numericalMethodGraphs.put(Chart.GTE, ErrorCalculator.calculateGTE(exactSolutionPoints, numericalMethodGraphs.get(Chart.GRAPH)));
        numericalMethodGraphs.put(Chart.LTE, ErrorCalculator.calculateLTE(exactSolutionPoints, numericalMethod));
        numericalMethodGraphs.put(Chart.TOTAL_ERROR, ErrorCalculator.calculateTotalApproximationError(exactSolution, numericalMethod, x0, y0, X, n0, N));

        return numericalMethodGraphs;
    }

    /**
     * Add Exact Solution graph to the chart.
     */
    private void setSeriesOfChartForExactSolution(ArrayList<Point> exactSolutionPoints) {
        // Convert points to series
        XYChart.Series series = new XYChart.Series();
        for (Point point : exactSolutionPoints) {
            series.getData().add(new XYChart.Data(point.x, point.y));
        }
        series.setName(GraphNames.EXACT_SOLUTION_NAME);
        seriesGraphMethodsChart.put(GraphNames.EXACT_SOLUTION_NAME, series);
    }

    /**
     * Distribute numerical approximatio method graphs for Approximation, GTE, LTE and Total Error Approximation charts
     */
    private void setSeriesOfChartsForNumericalMethod(HashMap<Chart, ArrayList<Point>> numericalMethodGraphs, String seriesName) {

        // Map of charts. Each key contains the series graph value
        HashMap<Chart, XYChart.Series> charts = new HashMap<>(4);
        for (Chart chart : Chart.values()) {
            // Convert points to series
            XYChart.Series series = new XYChart.Series();
            for (Point point : numericalMethodGraphs.get(chart)) {
                series.getData().add(new XYChart.Data(point.x, point.y));
            }
            series.setName(seriesName);

            charts.put(chart, series);
        }

        // Add numerical approximation method series graphs for charts
        seriesGraphMethodsChart.put(seriesName, charts.get(Chart.GRAPH));
        seriesGTEChart.put(seriesName, charts.get(Chart.GTE));
        seriesLTEChart.put(seriesName, charts.get(Chart.LTE));
        seriesTotalErrorChart.put(seriesName, charts.get(Chart.TOTAL_ERROR));
    }
}
