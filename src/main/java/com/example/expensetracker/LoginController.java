package com.example.expensetracker;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private UserService userService = new UserService();

    @FXML
    private void onLogin() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userService.login(username, password)) {
            MainApp.currentUsername = username;
            MainApp.primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("dashboard.fxml"))));
        } else {
            errorLabel.setText("Invalid username or password.");
        }
    }

    @FXML
    private void onRegister() throws Exception {
        MainApp.primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("register.fxml"))));
    }
}
