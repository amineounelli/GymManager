package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill both fields");
            return;
        }

        try {
            // LOAD MAIN LAYOUT FIRST
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Layout.fxml"));
            Parent root = loader.load();

            // SWITCH SCENE
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            //AUTO LOAD DASHBOARD
            LayoutController.getInstance().loadView("/Views/Dashboard.fxml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
