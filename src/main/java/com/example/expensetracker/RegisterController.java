package com.example.expensetracker;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private UserService userService = new UserService();

    @FXML
    private void onRegister() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userService.register(username, password)) {
            statusLabel.setText("Registration successful!");
        } else {
            statusLabel.setText("This username is already in use.");
        }
    }

    @FXML
    private void onBackToLogin() throws Exception {
        MainApp.primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login.fxml"))));
    }
}
