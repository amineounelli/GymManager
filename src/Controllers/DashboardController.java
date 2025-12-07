package Controllers;

import DAO.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DashboardController {

    @FXML private Label membersCount;
    @FXML private Label coachesCount;
    @FXML private Label plansCount;
    @FXML private Label equipmentCount;

    @FXML private Label monthlyRevenue;
    @FXML private Label todayRevenue;
    @FXML private Label totalPendingPayments;
    @FXML private Label monthlyExpenses;

    private final MembreDAO membreDAO = new MembreDAO();
    private final CoachDAO coachDAO = new CoachDAO();
    private final AbonnementDAO abonnementDAO = new AbonnementDAO();
    private final EquipementDAO equipementDAO = new EquipementDAO();
    private final PaiementDAO paiementDAO = new PaiementDAO();

    @FXML
    public void initialize() {
        loadKPI();
        loadFinance();
    }

    /* ---------------------------- KPI SECTION ---------------------------- */

    private void loadKPI() {

        // Total members
        membersCount.setText(String.valueOf(membreDAO.getAllMembres().size()));

        // Total coaches
        coachesCount.setText(String.valueOf(coachDAO.getAllCoachs().size()));

        // Active abonnements
        long activePlans = abonnementDAO.getAllAbonnements()
                .values()
                .stream()
                .filter(a -> a.getEtat().equalsIgnoreCase("Actif"))
                .count();
        plansCount.setText(String.valueOf(activePlans));

        // Equipment count
        equipmentCount.setText(String.valueOf(equipementDAO.getTotalEquipements()));
    }

    /* ---------------------------- FINANCE SECTION ---------------------------- */

    private void loadFinance() {

        // Monthly revenue
        double monthly = calculateMonthlyRevenue();
        monthlyRevenue.setText(formatDT(monthly));

        // Today's revenue
        double today = calculateTodayRevenue();
        todayRevenue.setText(formatDT(today));

        // Pending payments (if applicable, otherwise ZERO)
        // (You don't have a "pending" table, so we return 0)
        totalPendingPayments.setText("0 DT");

        // Monthly expenses (you don't have expenses table, so set 0)
        monthlyExpenses.setText("0 DT");
    }

    /* ------------------------- REVENUE CALCULATIONS ------------------------- */

    private double calculateMonthlyRevenue() {
        LocalDate firstDay = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDay = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        Date start = Date.from(firstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(lastDay.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return paiementDAO.getPaiementsByDateRange(start, end)
                .stream()
                .mapToDouble(p -> p.getMontant())
                .sum();
    }

    private double calculateTodayRevenue() {
        LocalDate today = LocalDate.now();

        Date start = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return paiementDAO.getPaiementsByDateRange(start, end)
                .stream()
                .mapToDouble(p -> p.getMontant())
                .sum();
    }

    /* ------------------------------ UTIL ------------------------------ */

    private String formatDT(double value) {
        return String.format("%.2f DT", value);
    }
}
