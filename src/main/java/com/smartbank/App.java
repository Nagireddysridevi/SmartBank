
package com.smartbank;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("SMART BANK");
        title.setStyle(
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #173B63;"
        );

        Label subtitle = new Label("Banking Made Simple & Secure");

        TextField accountField = new TextField();
        accountField.setPromptText("Enter Account Number");
        accountField.setMaxWidth(280);

        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Enter PIN");
        pinField.setMaxWidth(280);

        Button loginButton = new Button("LOGIN");
        loginButton.setPrefWidth(280);
        loginButton.setStyle(
            "-fx-background-color: #173B63;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;"
        );

        loginButton.setOnAction(e -> {
            if (accountField.getText().isEmpty()
                    || pinField.getText().isEmpty()) {

                showAlert("Please enter account number and PIN.");

            } else {
                showAlert("Welcome to SmartBank!");
            }
        });

        VBox layout = new VBox(
            18, title, subtitle, accountField, pinField, loginButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #F1F5FA;");

        Scene scene = new Scene(layout, 600, 450);

        stage.setTitle("SmartBank - Banking System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SmartBank");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
