package agh.ics.rrir.project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.linear.*;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    // List to store data points for the line chart
    private final List<Double[]> dataPairs = new ArrayList<>();
    private final Button startButton = new Button("START");
    private final TextField nInput = new TextField();

    private final static int x0 = 0;
    private final static int xn = 3;
    private int n;
    private double h;


    @Override
    public void start(Stage primaryStage) {
        configureStage(primaryStage);
        primaryStage.show();
        startButton.setOnAction(event -> calculate(primaryStage));
    }

    // Method to configure primary stage
    private void configureStage(Stage primaryStage){
        primaryStage.setTitle("Chart");
        nInput.setMaxWidth(100);
        VBox vBox = new VBox(new Label("Enter n: "), nInput, startButton);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 350, 100);
        primaryStage.setScene(scene);
    }

    // Main calculation method
    private void calculate(Stage primaryStage){
        n = Integer.parseInt(nInput.getText());
        h = (xn - x0) / (double) n;
        RealVector solution = new Solver(n, h).solve();

        for (int i = 0; i < n; i++) {
            double currPoint = h * i;
            int interval = findInterval(currPoint, h, n);
            dataPairs.add(new Double[]{currPoint, calcPhi(solution, currPoint, interval)});
        }
        configureLineChart(primaryStage);
    }

    // Method to calc phi(x) value
    public double calcPhi(RealVector solution, double point, int interval){
        Function fun1 = new Function(h);
        Function fun2 = new Function(h);
        PolynomialFunction baseFun = new PolynomialFunction(new double[]{5, -1 / 3.0});
        double phi;

        if (interval == 1) {
            fun1.setFormula(1, 1);
            phi = solution.getEntry(0) * fun1.getFunctionFormula().value(point) + baseFun.value(point);
        } else if (interval == n) {
            fun1.setFormula(n - 1, n);
            phi = solution.getEntry(n - 2) * fun1.getFunctionFormula().value(point) + baseFun.value(point);
        } else {
            fun1.setFormula(interval - 1, interval);
            fun2.setFormula(interval, interval);
            phi = solution.getEntry(interval - 2) * fun1.getFunctionFormula().value(point);
            phi += solution.getEntry(interval - 1) * fun2.getFunctionFormula().value(point);
            phi += baseFun.value(point);
        }
        return phi;
    }

    // Method to configure chart
    private void configureLineChart(Stage primaryStage){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("phi(x)");
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (Double[] dataPair : dataPairs) {
            series.getData().add(new XYChart.Data<>(dataPair[0], dataPair[1]));
        }

        Scene scene = new Scene(lineChart, 750, 750);
        lineChart.getData().add(series);
        primaryStage.setScene(scene);
    }


    // Method to find the interval for a given point of interest
    public static int findInterval(double point, double h, int n) {
        for (int i = 0; i <= n-1; i++) {
            double start = h * i;
            double end = h * (i + 1);
            if (start <= point && point <= end) {
                return i + 1;
            }
        }
        return 1;
    }
}

