package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SidebarController {

    @FXML private void goDashboard() {
        LayoutController.getInstance().loadView("/Views/Dashboard.fxml");
    }
    @FXML private void goManagers() {
        LayoutController.getInstance().loadView("/Views/Managers/Managers.fxml");
    }
    
    @FXML private void goMembers() {
        LayoutController.getInstance().loadView("/Views/Members/Members.fxml");
    }

    @FXML private void goCoaches() {
        LayoutController.getInstance().loadView("/Views/Coaches/Coaches.fxml");
    }

    @FXML private void goSessions() {
        LayoutController.getInstance().loadView("/Views/Sessions/Sessions.fxml");
    }

    @FXML private void goReservations() {
        LayoutController.getInstance().loadView("/Views/Reservations/Reservations.fxml");
    }

    @FXML private void goProgrammes() {
        LayoutController.getInstance().loadView("/Views/Programmes/Programmes.fxml");
    }

    @FXML private void goEquipments() {
        LayoutController.getInstance().loadView("/Views/Equipements/Equipements.fxml");
    }

    @FXML private void goRooms() {
        LayoutController.getInstance().loadView("/Views/Salles/Salles.fxml");
    }

    @FXML private void goPayments() {
        LayoutController.getInstance().loadView("/Views/Payments/Payments.fxml");
    }

    @FXML private void goAbonnements() {
        LayoutController.getInstance().loadView("/Views/Abonnements/Abonnements.fxml");
    }

    @FXML private void goStatistics() {
        LayoutController.getInstance().loadView("/Views/Statistics/Statistics.fxml");
    }

@FXML
private void logout() {
    try {
        // Load login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        Scene loginScene = new Scene(loader.load());

        // Get current stage from any node inside the sidebar
        Stage stage = (Stage) LayoutController.getInstance()
                .rootStack.getScene().getWindow();

        // Replace entire window scene
        stage.setScene(loginScene);
        stage.setTitle("Gym Management - Login");
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
