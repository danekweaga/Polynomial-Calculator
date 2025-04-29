import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**********************************************************************************************************
 * @title The PolynomialCalculatorGUI class.
 * 
 * @author Chukwunonso Daniel Ekweaga
 * @version (Final Version)
 * @student ID : 3781089
 **********************************************************************************************************/

public class PolynomialCalculatorGUI 
{
    //Instabce Data
    private TextField lhsField;
    private TextField rhsField;
    private Label resultLabel;
    private MathLogic calculator;
    private PolynomialGrapher grapher;
    private Stage graphStage;
    private boolean isGraphVisible = false;

    private static final String BUTTON_STYLE = """
            -fx-background-color: #404040;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 5;
            """;

    private static final String FUNCTION_BUTTON_STYLE = """
            -fx-background-color: #333333;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 5;
            """;

    /**************************************************************************************
     * Method to build the graphical user interface for the polynomial calculator.
     * 
     * @param owner The primary stage of the application.
     * @return The scene containing the GUI components.
     **************************************************************************************/
    public Scene buildGUI(Stage owner) 
    {
        calculator = new MathLogic();
        grapher = new PolynomialGrapher();

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #202020;");
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Polynomial Calculator");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        lhsField = createStyledTextField("Left-hand side (e.g., 2x^2-5x+3)");
        rhsField = createStyledTextField("Right-hand side (default 0)");

        HBox inputRow = new HBox(10, lhsField, new Label("="), rhsField);
        inputRow.setAlignment(Pos.CENTER);

        resultLabel = new Label("Roots will appear here");
        resultLabel.setFont(Font.font("Consolas", 16));
        resultLabel.setTextFill(Color.LIGHTGREEN);
        resultLabel.setStyle("-fx-background-color: #303030; -fx-padding: 10; -fx-background-radius: 5;");
        resultLabel.setMaxWidth(400);

        Button solveButton = createStyledButton("SOLVE");
        solveButton.setOnAction(e -> solveEquation());

        Button graphButton = createStyledButton("GRAPH");
        graphButton.setOnAction(e -> toggleGraph());

        Button clearButton = createStyledButton("CLEAR");
        clearButton.setOnAction(e -> clearFields());

        HBox buttonRow = new HBox(15, solveButton, graphButton, clearButton);
        buttonRow.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, inputRow, resultLabel, buttonRow);

        return new Scene(root, 500, 500);
    }

    /**************************************************************************************
     * Method to create a styled text field with a given prompt text.
     * 
     * @param prompt The prompt text for the text field.
     * @return The styled text field.
     **************************************************************************************/
    private TextField createStyledTextField(String prompt) 
    {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setFont(Font.font("Arial", 16));
        field.setPrefWidth(200);
        field.setStyle("-fx-background-color: #303030; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10;");
        return field;
    }

    /**************************************************************************************
     * Method to create a styled button with given text.
     * 
     * @param text The text to display on the button.
     * @return The styled button.
     **************************************************************************************/
    private Button createStyledButton(String text) 
    {
        Button button = new Button(text);
        button.setStyle(FUNCTION_BUTTON_STYLE);
        button.setPrefWidth(100);
        return button;
    }

    /**************************************************************************************
     * Method to solve the polynomial equation entered by the user.
     **************************************************************************************/
    private void solveEquation() 
    {
        String lhs = lhsField.getText().trim();
        String rhs = rhsField.getText().trim();
        if (lhs.isEmpty()) 
        {
            resultLabel.setText("Please enter the left-hand side.");
            return;
        }
        if (rhs.isEmpty()) rhs = "0";
        
        String solution = calculator.solveAnyEquation(lhs, rhs);
        resultLabel.setText(solution);
        if (isGraphVisible) 
        {
            updateGraph(lhs, rhs);
        }
    }

    /**************************************************************************************
     * Method to toggle the visibility of the polynomial graph.
     **************************************************************************************/
    private void toggleGraph() 
    {
        if (graphStage == null) 
        {
            graphStage = new Stage();
            graphStage.setTitle("Polynomial Graph");
        }

        if (isGraphVisible) 
        {
            graphStage.hide();
            isGraphVisible = false;
        } 
        
        else 
        {
            String lhs = lhsField.getText().trim();
            String rhs = rhsField.getText().trim();
            if (rhs.isEmpty()) rhs = "0";

            Scene graphScene = grapher.createGraphScene(calculator.formatEquation(lhs, rhs));
            graphStage.setScene(graphScene);
            graphStage.show();
            isGraphVisible = true;
        }
    }

    /**************************************************************************************
     * Method to update the polynomial graph with new equation coefficients.
     * 
     * @param lhs The left-hand side of the equation.
     * @param rhs The right-hand side of the equation.
     **************************************************************************************/
    private void updateGraph(String lhs, String rhs) 
    {
        if (graphStage != null && isGraphVisible) 
        {
            Scene graphScene = grapher.createGraphScene(calculator.formatEquation(lhs, rhs));
            graphStage.setScene(graphScene);
        }
    }

    /**************************************************************************************
     * Method to clear the input fields and reset the result label.
     **************************************************************************************/
    private void clearFields() 
    { 
        lhsField.clear();
        rhsField.clear();
        resultLabel.setText("Roots will appear here");
        if (graphStage != null) graphStage.hide();
        isGraphVisible = false;
    }
}