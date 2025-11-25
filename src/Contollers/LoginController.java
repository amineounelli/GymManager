/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Contollers;

import Views.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("amine".equals(username) && "1234".equals(password)) {
            MainApp.showMainMenu();
            messageLabel.setText("Login successful");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("Invalid informations");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
