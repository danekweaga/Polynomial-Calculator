import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class PolynomialSolverUI extends Application {

    private static final String[] TICKER_WORDS = {
            "Linear", "Quadratic", "Cubic", "Polynomial", "Equation", "Solver"
    };

    private static final String[] FONTS = {
            "Segoe UI", "Consolas", "Comic Sans MS", "Courier New", "Arial Black", "Verdana"
    };

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #dbe9f4, #cad9e3);");

        // ??? Scrolling Ticker ????????????????????????????????
        HBox tickerContent = createTickerContent();
        HBox scrollingContent = new HBox(tickerContent, cloneHBox(tickerContent));
        scrollingContent.setSpacing(50);

        StackPane tickerPane = new StackPane(scrollingContent);
        tickerPane.setPrefHeight(60);
        tickerPane.setClip(new Rectangle(800, 60));
        applyGlassStyle(tickerPane);

        TranslateTransition scroll = new TranslateTransition(Duration.seconds(12), scrollingContent);
        scroll.setFromX(0);
        scroll.setToX(-800);
        scroll.setCycleCount(Animation.INDEFINITE);
        scroll.setInterpolator(Interpolator.LINEAR);
        scroll.play();

        // ??? Equation Input Pane ?????????????????????????????
        TextField lhsInput = new TextField();
        lhsInput.setPromptText("Enter LHS");
        lhsInput.setPrefWidth(250);

        TextField rhsInput = new TextField();
        rhsInput.setPromptText("Enter RHS");
        rhsInput.setPrefWidth(250);

        HBox inputBox = new HBox(10, lhsInput, new Label("="), rhsInput);
        inputBox.setAlignment(Pos.CENTER);
        applyGlassStyle(inputBox);
        inputBox.setPadding(new Insets(15));

        // ??? Equation Preview (Non-functional for now) ???????
        Label previewLabel = new Label("2x³ + 3x - 5 = x² + 1");
        previewLabel.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 18));
        previewLabel.setPadding(new Insets(10));
        previewLabel.setTextFill(Color.WHITE);
        applyGlassStyle(previewLabel);

        // ??? GridPane Buttons (Glass Panel) ??????????????????
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));

        String[] labels = {
                "x³", "x²", "x", "+", "-", "Clear", "=",
                "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "Del"
        };

        int col = 0, row = 0;
        for (String label : labels) {
            Button btn = new Button(label);
            btn.setPrefSize(60, 40);
            btn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white;");
            btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            btn.setEffect(new DropShadow(3, Color.gray(0.3)));
            grid.add(btn, col++, row);
            if (col == 5) {
                col = 0;
                row++;
            }
        }
        applyGlassStyle(grid);

        // ??? Output Display (Result placeholder) ?????????????
        Label output = new Label("Output:\nSimplified: x³ + 2x² – 6x + 6 = 0\nSolution: x = 1, x = -2, x = 3");
        output.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        output.setTextFill(Color.WHITE);
        output.setWrapText(true);
        output.setPadding(new Insets(10));
        applyGlassStyle(output);

        // ??? Final Layout ????????????????????????????????????
        root.getChildren().addAll(tickerPane, inputBox, previewLabel, grid, output);
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Polynomial Solver UI");
        primaryStage.show();
    }

    private HBox createTickerContent() {
        HBox box = new HBox(40);
        box.setAlignment(Pos.CENTER_LEFT);
        Random rand = new Random();

        for (String word : TICKER_WORDS) {
            Label label = new Label(word);
            String randomFont = FONTS[rand.nextInt(FONTS.length)];
            label.setFont(Font.font(randomFont, FontWeight.BOLD, 22));
            label.setTextFill(Color.hsb(rand.nextDouble() * 360, 0.8, 0.95));
            box.getChildren().add(label);
        }

        return box;
    }

    private HBox cloneHBox(HBox original) {
        HBox clone = new HBox(40);
        clone.setAlignment(Pos.CENTER_LEFT);
        for (javafx.scene.Node node : original.getChildren()) {
            if (node instanceof Label) {
                Label originalLabel = (Label) node;
                Label copy = new Label(originalLabel.getText());
                copy.setFont(originalLabel.getFont());
                copy.setTextFill(originalLabel.getTextFill());
                clone.getChildren().add(copy);
            }
        }
        return clone;
    }

    private void applyGlassStyle(Region node) {
        node.setStyle("-fx-background-color: rgba(255,255,255,0.1);" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: rgba(255,255,255,0.3);" +
                "-fx-border-radius: 15;" +
                "-fx-border-width: 1;");
        node.setEffect(new DropShadow(10, Color.gray(0.2)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
