import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**********************************************************************************************
 * @title PolynomialCalculatorApp

 * @author Chukwunonso Daniel Ekweaga
 **********************************************************************************************/
public class PolynomialCalculatorApp extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        // Create an instance of the GUI
        primaryStage.initStyle(StageStyle.UNDECORATED);
        PolynomialCalculatorGUI gui = new PolynomialCalculatorGUI();
        
        // Build the GUI and attach it to the primary stage
        Scene scene = gui.buildGUI(primaryStage);
        
        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Polynomial Calculator");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
