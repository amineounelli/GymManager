package Controllers;

import javafx.fxml.FXML;

public class SidebarController {

    @FXML
    private void goDashboard() {
        LayoutController.getInstance().loadView("/Views/Dashboard.fxml");
    }

    @FXML
    private void goMembers() {
        LayoutController.getInstance().loadView("/Views/Members/Members.fxml");
    }

    @FXML
    private void goCoaches() {
        LayoutController.getInstance().loadView("/Views/Coaches/Coaches.fxml");
    }

    @FXML
    private void goPlans() {
        LayoutController.getInstance().loadView("/Views/Plans.fxml");
    }

    @FXML
    private void goSessions() {
        LayoutController.getInstance().loadView("/Views/Sessions/Sessions.fxml");
}

    @FXML
    private void goRooms() {
        LayoutController.getInstance().loadView("/Views/Rooms.fxml");
    }

    @FXML
    private void goReports() {
        LayoutController.getInstance().loadView("/Views/Reports.fxml");
    }

    @FXML
    private void logout() {
        LayoutController.getInstance().loadView("/Views/Login.fxml");
    }
}