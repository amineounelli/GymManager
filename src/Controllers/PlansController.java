package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PlansController {

    @FXML private TableView<?> plansTable;

    @FXML
    public void initialize() {
        System.out.println("Plans loaded");
    }

    @FXML
    private void addPlan() {
        System.out.println("Add Plan clicked");
    }

    @FXML
    private void removePlan() {
        System.out.println("Remove Plan clicked");
    }
}
