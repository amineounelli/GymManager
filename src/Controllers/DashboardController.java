package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label membersCount;
    @FXML private Label coachesCount;
    @FXML private Label plansCount;
    @FXML private Label equipmentCount;

    @FXML private Label monthlyRevenue;
    @FXML private Label todayRevenue;
    @FXML private Label totalPendingPayments;
    @FXML private Label monthlyExpenses;

    @FXML
    public void initialize() {
        loadDummyKPI();
        loadDummyFinance();
    }

    private void loadDummyKPI() {
        membersCount.setText("128");
        coachesCount.setText("7");
        plansCount.setText("54");
        equipmentCount.setText("42");
    }

    private void loadDummyFinance() {
        monthlyRevenue.setText("12,450 DT");
        todayRevenue.setText("830 DT");
        totalPendingPayments.setText("2,190 DT");
        monthlyExpenses.setText("4,780 DT");
    }
}
