import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;


/**********************************************************************************************************
 * @title The PolynomialGrapher class.
 * 
 * @author Chukwunonso Daniel Ekweaga
 * @version (Final Version)
 **********************************************************************************************************/
public class PolynomialGrapher
{
    /**************************************************************************************
     * Method to create a scene that graphs a polynomial based on coefficients.
     * @param coefficients The coefficients of the polynomial equation.
     * @return The scene containing the graph.
     **************************************************************************************/
    public Scene createGraphScene(double[] coefficients) 
    {
        // Extract coefficients: a (x^3), b (x^2), c (x), d (constant)
        double a = coefficients[0];
        double b = coefficients[1];
        double c = coefficients[2];
        double d = coefficients[3];

        // Set up X and Y axes
        NumberAxis xAxis = new NumberAxis(-10, 10, 1);
        NumberAxis yAxis = new NumberAxis(-50, 50, 5);
        xAxis.setLabel("x");
        yAxis.setLabel("y");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Polynomial Graph");
        lineChart.setCreateSymbols(false);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("f(x)");

        // Plot points
        for (double x = -10; x <= 10; x += 0.1) 
        {
            double y = a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
            series.getData().add(new XYChart.Data<>(x, y));
        }

        lineChart.getData().add(series);

        StackPane root = new StackPane(lineChart);
        return new Scene(root, 600, 400);
    }
}