package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CacheCalculator extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        root.setStyle("-fx-background-color: #1E1E1A;");

        HBox MMBox = new HBox(5);
        Label MMprompt = createLabel("Main Memory Address Size (Gb): ");
        TextField MMsize = new TextField();
        MMBox.getChildren().addAll(MMprompt, MMsize);
        MMBox.setTranslateX(135);
        MMBox.setTranslateY(80);

        HBox CMBox = new HBox(68);
        Label CMprompt = createLabel("Cache Memory Size (Mb): ");
        TextField CMsize = new TextField();
        CMBox.getChildren().addAll(CMprompt, CMsize);
        CMBox.setTranslateX(135);
        CMBox.setTranslateY(130);

        HBox WordBox = new HBox(135);
        Label WordPrompt = createLabel("Word Size (byte): ");
        TextField WordSize = new TextField();
        WordBox.getChildren().addAll(WordPrompt, WordSize);
        WordBox.setTranslateX(135);
        WordBox.setTranslateY(180);

        HBox KSetBox = new HBox(24);
        Label KSetPrompt = createLabel("k-way (set associative cache): ");
        TextField KsetWay = new TextField();
        KSetBox.getChildren().addAll(KSetPrompt, KsetWay);
        KSetBox.setTranslateX(135);
        KSetBox.setTranslateY(230);

        Button DMapBtn = createButton("Direct Mapping");
        Button FAMapBtn = createButton("Fully/Associative\n      Mapping");
        Button SAMapBtn = createButton("Set-Associative\n     Mapping");

        HBox BtnBox = new HBox(10);
        BtnBox.setAlignment(Pos.CENTER);
        BtnBox.getChildren().addAll(DMapBtn, FAMapBtn, SAMapBtn);
        BtnBox.setTranslateX(350 - 182.5);
        BtnBox.setTranslateY(300);
        
        DMapBtn.setOnAction(e -> {
            if (checkFields(MMsize, CMsize, WordSize)) {
                double MMbits = 30 + Math.log(Double.parseDouble(MMsize.getText())) / Math.log(2);
                double Cachebits = 20 + Math.log(Double.parseDouble(CMsize.getText())) / Math.log(2);
                double Wordbits = Math.log(Double.parseDouble(WordSize.getText()) * 8.0) / Math.log(2);

                double tagBits = MMbits - Cachebits;
                double lineBits = Cachebits - Wordbits;

            }
        });
        
        FAMapBtn.setOnAction(e -> {
            if (checkFields(MMsize, CMsize, WordSize)) {
                double MMbits = 30 + Math.log(Double.parseDouble(MMsize.getText())) / Math.log(2);
                double Wordbits = Math.log(Double.parseDouble(WordSize.getText()) * 8.0) / Math.log(2);
                double tagBits = MMbits - Wordbits;
            }
        });
        
        SAMapBtn.setOnAction(e -> {
        	if (checkFields(MMsize, CMsize, WordSize, KsetWay)) {
                double MMbits = 30 + Math.log(Double.parseDouble(MMsize.getText())) / Math.log(2);
                double Cachebits = 20 + Math.log(Double.parseDouble(CMsize.getText()) / Double.parseDouble(KsetWay.getText())) / Math.log(2);
                double Wordbits = Math.log(Double.parseDouble(WordSize.getText()) * 8.0) / Math.log(2);

                double tagBits = MMbits - Cachebits;
                double lineBits = Cachebits - Wordbits;
            }
        });
        
        Rectangle BitsBox = new Rectangle(100, 400, 500, 77);
        BitsBox.setFill(Color.web("#f1fa8c"));
        BitsBox.setStroke(Color.BLACK);

        root.getChildren().addAll(MMBox, CMBox, WordBox, KSetBox, BtnBox, BitsBox);

        Scene scene = new Scene(root, 700, 550);

        primaryStage.setTitle("Cache Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15.5));
        return label;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(115);
        button.setPrefHeight(42);
        button.setAlignment(Pos.CENTER);
        return button;
    }
    
    private boolean checkFields(TextField t1, TextField t2, TextField t3) {
        return !t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty();
    }
    
    private boolean checkFields(TextField t1, TextField t2, TextField t3, TextField t4) {
        return !t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty() && t4.getText().isEmpty();
    }
    
    class LabelBox extends StackPane {

        private Rectangle rectangle;
        private Label label;

        public LabelBox(String labelText) {
            rectangle = new Rectangle(100, 50); // Adjust the width and height as per your requirements
            rectangle.setFill(Color.LIGHTGRAY);

            label = new Label(labelText);
            label.setStyle("-fx-font-size: 14px;"); // Adjust the font size as per your requirements

            getChildren().addAll(rectangle, label);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
