package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

/**
 * This controller provides the logic for charts.fxml file
 */
public class Controller {

    // The map of graphs for showing the Approximation chart.
    // For each method name (key) it stores the series (value) for this method.
    private HashMap<String, XYChart.Series> seriesGraphMethodsChart;
    // The map of graphs for showing the GTE chart.
    // For each method name (key) it stores the series (value) for this method.
    private HashMap<String, XYChart.Series> seriesGTEChart;
    // The map of graphs for showing the LTE chart.
    // For each method name (key) it stores the series (value) for this method.
    private HashMap<String, XYChart.Series> seriesLTEChart;
    // The map of graphs for showing the Total Approximation Error chart.
    // For each method name (key) it stores the series (value) for this method.
    private HashMap<String, XYChart.Series> seriesTotalErrorChart;

    // List of charts from gui for showing graphs
    @FXML
    private LineChart<Number, Number> graphMethodsChart;

    @FXML
    private LineChart<Number, Number> GTEChart;

    @FXML
    private LineChart<Number, Number> LTEChart;

    @FXML
    private LineChart<Number, Number> totalErrorChart;

    // List of text fields from gui for changing values
    @FXML
    private TextField x0;

    @FXML
    private TextField y0;

    @FXML
    private TextField X;

    @FXML
    private TextField n0;

    @FXML
    private TextField N;

    // List of checkboxes from gui for showing particular graphs
    @FXML
    private CheckBox euler_checkbox;

    @FXML
    private CheckBox improved_euler_checkbox;

    @FXML
    private CheckBox runge_kutta_checkbox;

    /**
     * Perform the action when the button for plotting charts was pressed.
     */
    @FXML
    void plotCharts(ActionEvent event) {

        // Plot charts iff the input of given values is valid
        if (validateInput()) {
            // Calculate series for given values
            GraphPlotter graphPlotter = new GraphPlotter(Double.parseDouble(x0.getText()),
                    Double.parseDouble(y0.getText()),
                    Double.parseDouble(X.getText()),
                    Integer.parseInt(n0.getText()),
                    Integer.parseInt(N.getText()));

            if (graphPlotter.isPossibleCalculate()) {
                // Assign series
                seriesGraphMethodsChart = graphPlotter.getSeriesGraphMethodsChart();
                seriesGTEChart = graphPlotter.getSeriesGTEChart();
                seriesLTEChart = graphPlotter.getSeriesLTEChart();
                seriesTotalErrorChart = graphPlotter.getSeriesTotalErrorChart();

                // Show charts
                plotChart(graphMethodsChart, seriesGraphMethodsChart);
                plotChart(GTEChart, seriesGTEChart);
                plotChart(LTEChart, seriesLTEChart);
                plotChart(totalErrorChart, seriesTotalErrorChart);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Couldn't plot charts because the range [x0; X] contains discontinuity points:");
                alert.setContentText(graphPlotter.getDiscontinuityPoints().toString());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void changeVisibilityEulerMethod(ActionEvent event) {
        changeVisibility(euler_checkbox.isSelected(), GraphNames.EULER_METHOD_NAME);
    }

    @FXML
    void changeVisibilityImprovedEulerMethod(ActionEvent event) {
        changeVisibility(improved_euler_checkbox.isSelected(), GraphNames.IMPROVED_EULER_METHOD_NAME);
    }

    @FXML
    void changeVisibilityRungeKuttaMethod(ActionEvent event) {
        changeVisibility(runge_kutta_checkbox.isSelected(), GraphNames.RUNGE_KUTTA_METHOD_NAME);
    }

    /**
     * Assign chart values to a Line Chart container from charts.fxml file
     *
     * @param chart       - chart from charts.fxml file
     * @param seriesChart - hashmap with calculated series for a chart
     */
    private void plotChart(LineChart<Number, Number> chart, HashMap<String, XYChart.Series> seriesChart) {
        // Remove previous stored data
        chart.getData().clear();

        // Add series for each graph
        for (Map.Entry<String, XYChart.Series> graph : seriesChart.entrySet()) {
            // Add Exact Solution graph
            if (graph.getKey().equals(GraphNames.EXACT_SOLUTION_NAME)) {
                chart.getData().add(graph.getValue());
            }
            // Add Euler's method graph if it's selected in checkbox from GUI
            if (graph.getKey().equals(GraphNames.EULER_METHOD_NAME) && euler_checkbox.isSelected()) {
                chart.getData().add(graph.getValue());
            }
            // Add Improved Euler's method graph if it's selected in checkbox from GUI
            if (graph.getKey().equals(GraphNames.IMPROVED_EULER_METHOD_NAME) && improved_euler_checkbox.isSelected()) {
                chart.getData().add(graph.getValue());
            }
            // Add Runge-Kutta method graph if it's selected in checkbox from GUI
            if (graph.getKey().equals(GraphNames.RUNGE_KUTTA_METHOD_NAME) && runge_kutta_checkbox.isSelected()) {
                chart.getData().add(graph.getValue());
            }
        }
    }

    /**
     * Validate the input for all text fields
     *
     * @return true if the input is valid
     */
    private boolean validateInput() {
        // Validate each text field
        if (validateDouble(x0) & validateDouble(y0) & validateDouble(X) & validateInteger(n0) & validateInteger(N)) {
            // Check if n0 <= N
            if (Integer.parseInt(n0.getText()) <= Integer.parseInt(N.getText())) {
                n0.setStyle("");
                N.setStyle("");
                return true;
            } else {
                n0.setStyle("-fx-border-color: red;");
                N.setStyle("-fx-border-color: red;");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("n0 should be less or equal than N");
                alert.setContentText("n0 <= N, but you have: n0 = " + n0.getText() + ", N = " + N.getText());
                alert.showAndWait();
                return false;
            }
        }
        return false;
    }

    /**
     * Validate text filed for a double value
     *
     * @param textField - filed to check
     * @return true if the input is valid
     */
    private boolean validateDouble(TextField textField) {
        if (!textField.getText().matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
            textField.setStyle("-fx-border-color: red;");
            return false;
        } else {
            textField.setStyle("");
            return true;
        }
    }

    /**
     * Validate text filed for an integer value
     *
     * @param textField - filed to check
     * @return true if the input is valid
     */
    private boolean validateInteger(TextField textField) {
        if (!textField.getText().matches("-?[1-9]\\d*")) {
            textField.setStyle("-fx-border-color: red;");
            return false;
        } else {
            textField.setStyle("");
            return true;
        }
    }

    /**
     * Add or remove series from Line Chart from charts.fxml file
     * according to the selected checkbox from GUI
     *
     * @param isSelected boolean vaule of a checkbox
     * @param methodName method to show or remove from Line Charts
     */
    private void changeVisibility(boolean isSelected, String methodName) {
        if (isSelected) {
            graphMethodsChart.getData().add(seriesGraphMethodsChart.get(methodName));
            GTEChart.getData().add(seriesGTEChart.get(methodName));
            LTEChart.getData().add(seriesLTEChart.get(methodName));
            totalErrorChart.getData().add(seriesTotalErrorChart.get(methodName));
        } else {
            graphMethodsChart.getData().remove(seriesGraphMethodsChart.get(methodName));
            GTEChart.getData().remove(seriesGTEChart.get(methodName));
            LTEChart.getData().remove(seriesLTEChart.get(methodName));
            totalErrorChart.getData().remove(seriesTotalErrorChart.get(methodName));
        }
    }
}
