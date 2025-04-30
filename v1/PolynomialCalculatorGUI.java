import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**********************************************************************************************************
 * @title The PolynomialCalculatorGUI class.
 *
 * @author Chukwunonso Daniel Ekweaga
 **********************************************************************************************************/

public class PolynomialCalculatorGUI 
{
    // Instance data
    private TextField lhsField;
    private TextField rhsField;
    private Label resultLabel;
    private MathLogic calculator;
    private PolynomialGrapher grapher;
    private Stage graphStage;
    private boolean isGraphVisible = false;
    private boolean typingLHS = true;

    // Button style constants
    private static final String NUMBER_BUTTON_STYLE = """
            -fx-background-color: #2196F3;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 5;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);
            """;

    private static final String FUNCTION_BUTTON_STYLE = """
            -fx-background-color: #1565C0;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 5;
            """;

    /**********************************************************************************************************
     * Method that builds and returns the main application scene with GUI components.
     
     * @param owner - the primary stage (application window)
     * @return Scene - the constructed GUI scene
     **********************************************************************************************************/
    public Scene buildGUI(Stage owner) 
    {
        calculator = new MathLogic();
        grapher = new PolynomialGrapher();

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #202020;");

        // Create power off button at the top right
        Button powerButton = createPowerButton(owner);
        HBox topBar = new HBox(powerButton);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(0, 0, 10, 0));

        // Title
        Label title = new Label("Polynomial Calculator");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        // Equation fields
        lhsField = createStyledTextField("Left-hand side");
        rhsField = createStyledTextField("Right-hand side (default 0)");
        rhsField.setDisable(true);

        HBox inputRow = new HBox(10, lhsField, new Label("="), rhsField);
        inputRow.setAlignment(Pos.CENTER);

        // Switch typing side
        Button switchButton = createStyledButton("Switch Side");
        switchButton.setOnAction(e -> toggleTypingSide());

        // Result label
        resultLabel = new Label("Roots will appear here");
        resultLabel.setFont(Font.font("Consolas", 16));
        resultLabel.setTextFill(Color.LIGHTBLUE);
        resultLabel.setStyle("-fx-background-color: #303030; -fx-padding: 20; -fx-background-radius: 5;");
        resultLabel.setMaxWidth(460);
        resultLabel.setPrefHeight(100);

        // Functional buttons
        Button solveButton = createStyledButton("SOLVE");
        solveButton.setOnAction(e -> solveEquation());

        Button graphButton = createStyledButton("GRAPH");
        graphButton.setOnAction(e -> toggleGraph());

        Button clearButton = createStyledButton("CLEAR");
        clearButton.setOnAction(e -> clearFields());

        HBox actionButtons = new HBox(10, solveButton, graphButton, clearButton);
        actionButtons.setAlignment(Pos.CENTER);

        // Keypad layout
        GridPane keypad = new GridPane();
        keypad.setHgap(10);
        keypad.setVgap(10);
        keypad.setAlignment(Pos.CENTER);

        String[] keys = {
                "7", "8", "9", "x³",
                "4", "5", "6", "x²",
                "1", "2", "3", "x",
                "0", "+", "-", "DEL"
        };

        for (int i = 0; i < keys.length; i++)
        {
            Button b = new Button(keys[i]);
            String value = keys[i];

            if ("+-".contains(value) || value.matches("x³|x²|x|DEL")) {
                b.setStyle(FUNCTION_BUTTON_STYLE);
            } 
            
            else 
            {
                b.setStyle(NUMBER_BUTTON_STYLE);
            }

            b.setPrefWidth(60);
            b.setOnAction(e -> handleKeyPress(value));
            b.setOnMousePressed(e -> b.setStyle(b.getStyle() + "-fx-effect: innershadow(gaussian, #00BFFF, 10, 0.5, 0, 0);"));
            b.setOnMouseReleased(e -> b.setStyle(b.getStyle().replace("-fx-effect: innershadow(gaussian, #00BFFF, 10, 0.5, 0, 0);", "")));
            keypad.add(b, i % 4, i / 4);
        }

        // Build full layout
        root.getChildren().addAll(topBar, title, inputRow, switchButton, resultLabel, keypad, actionButtons);

        // Scene creation
        Scene scene = new Scene(root, 500, 650);
        scene.setFill(Color.TRANSPARENT);
        owner.initStyle(StageStyle.TRANSPARENT);
        owner.setScene(scene);

        // Rounded corners
        Rectangle clip = new Rectangle(500, 650);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        root.setClip(clip);

        return scene;
    }

    /**********************************************************************************************************
     * Method that creates a styled text field with a specified prompt.
     
     * @param prompt - the hint text to display inside the field
     * @return TextField - styled input field
     **********************************************************************************************************/
    private TextField createStyledTextField(String prompt) 
    {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setFont(Font.font("Arial", 16));
        field.setPrefWidth(200);
        field.setStyle("-fx-background-color: #303030; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10;");
        return field;
    }

    /**********************************************************************************************************
     * Method that creates a function-style styled button.
     
     * @param text - text to appear on button
     * @return Button - styled button
     **********************************************************************************************************/
    private Button createStyledButton(String text) 
    {
        Button button = new Button(text);
        button.setStyle(FUNCTION_BUTTON_STYLE);
        button.setPrefWidth(100);
        return button;
    }

    /**********************************************************************************************************
     * Method thats adds pressed character to the appropriate text field
    
     * @param value - character or symbol pressed
     **********************************************************************************************************/
    private void handleKeyPress(String value) 
    {
        TextField target = typingLHS ? lhsField : rhsField;

        switch (value)
        {
            case "x³" -> target.appendText("x^3");
            case "x²" -> target.appendText("x^2");
            case "x" -> target.appendText("x");
            case "DEL" -> {
                String current = target.getText();
                if (!current.isEmpty()) {
                    target.setText(current.substring(0, current.length() - 1));
                }
            }
            default -> target.appendText(value);
        }
    }

    /**********************************************************************************************************
     * Method that switches the active text field between LHS and RHS.
     **********************************************************************************************************/
    private void toggleTypingSide() 
    {
        typingLHS = !typingLHS;
        lhsField.setDisable(!typingLHS);
        rhsField.setDisable(typingLHS);
    }

    /**********************************************************************************************************
     * Method that computes and displays the solution to the entered polynomial equation.
     **********************************************************************************************************/
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
        if (isGraphVisible) updateGraph(lhs, rhs);
    }

    /**********************************************************************************************************
     * Method that shows or hides the graph window depending on current state.
     **********************************************************************************************************/
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

    /**********************************************************************************************************
     * Method that updates the graph stage with current polynomial equation.
     * 
     * @param lhs - left side of the equation
     * @param rhs - right side of the equation
     **********************************************************************************************************/
    private void updateGraph(String lhs, String rhs) 
    {
        if (graphStage != null && isGraphVisible) {
            Scene graphScene = grapher.createGraphScene(calculator.formatEquation(lhs, rhs));
            graphStage.setScene(graphScene);
        }
    }

    /**********************************************************************************************************
     * Method that clears all fields and resets result display and graph.
     **********************************************************************************************************/
    private void clearFields()
    {
        lhsField.clear();
        rhsField.clear();
        resultLabel.setText("Roots will appear here");
        if (graphStage != null) graphStage.hide();
        isGraphVisible = false;
    }

    /**********************************************************************************************************
     * Method that creates the custom power image button with hover/press effects.
     * 
     * @param owner - the app window to close when clicked
     * @return Button - styled image button
     **********************************************************************************************************/
    private Button createPowerButton(Stage owner)
    {
        Image powerImage = new Image("power.png");
        ImageView powerView = new ImageView(powerImage);
        powerView.setFitWidth(50);
        powerView.setFitHeight(50);

        Button powerButton = new Button();
        powerButton.setGraphic(powerView);
        powerButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        powerButton.setOnMouseEntered(e -> powerButton.setStyle("""
            -fx-background-color: transparent;
            -fx-effect: dropshadow(gaussian, #00BFFF, 12, 0.5, 0, 0);
            -fx-cursor: hand;
        """));

        powerButton.setOnMouseExited(e -> powerButton.setStyle("""
            -fx-background-color: transparent;
            -fx-cursor: hand;
        """));

        powerButton.setOnMousePressed(e -> powerButton.setStyle("""
            -fx-background-color: transparent;
            -fx-effect: innershadow(gaussian, #FF4500, 14, 0.5, 0, 0);
            -fx-cursor: hand;
        """));

        powerButton.setOnMouseReleased(e -> powerButton.setStyle("""
            -fx-background-color: transparent;
            -fx-effect: dropshadow(gaussian, #00BFFF, 12, 0.5, 0, 0);
            -fx-cursor: hand;
        """));

        powerButton.setOnAction(e -> owner.close());
        return powerButton;
    }
}
