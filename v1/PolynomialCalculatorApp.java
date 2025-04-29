import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PolynomialCalculatorApp extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        PolynomialCalculatorGUI gui = new PolynomialCalculatorGUI();
        Scene scene = new Scene(gui);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Polynomial Calculator");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }

}
