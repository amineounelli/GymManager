package Controllers.Sessions;

import Controllers.LayoutController;
import Controllers.Members.MembersController;
import Controllers.Coaches.CoachesController;
import Models.amen.Infrastructure.*;
import Models.amine.Personnel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.ZoneId;
import java.util.Date;

public class EditSessionController {

    @FXML private ComboBox<String> typeCombo;
    @FXML private TextField durationField;
    @FXML private DatePicker dateField;
    @FXML private ComboBox<Salle> salleCombo;
    @FXML private TextField costField;

    @FXML private ListView<Membre> membersList;
    @FXML private ComboBox<Coach> coachCombo;
    @FXML private TextField capacityField;

    @FXML private Label lblInfo;

    private final ObservableList<Membre> membersObs = FXCollections.observableArrayList();
    private final ObservableList<Coach> coachesObs = FXCollections.observableArrayList();
    private final ObservableList<Salle> sallesObs = FXCollections.observableArrayList();

    private Seance original;

    @FXML
    public void initialize() {
        typeCombo.setItems(FXCollections.observableArrayList("Individual", "Collective"));
        typeCombo.setOnAction(e -> typeChanged());

        membersObs.setAll(MembersController.getMembersList());
        membersList.setItems(membersObs);
        membersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        coachesObs.setAll(CoachesController.getCoachesList());
        coachCombo.setItems(coachesObs);

        // simple salles
        sallesObs.addAll(new Salle(1, "Salle A", 30), new Salle(2, "Salle B", 20));
        salleCombo.setItems(sallesObs);

        // get session to edit from SessionsController
        original = SessionsController.getEditingSession();
        if (original != null) populateFromSession(original);

        typeChanged();
    }

    private void populateFromSession(Seance s) {
        typeCombo.getSelectionModel().select((s instanceof SeanceIndividuelle) ? "Individual" : "Collective");
        durationField.setText(String.valueOf(s.getDuree()));
        dateField.setValue(s.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        salleCombo.getSelectionModel().select(s.getSalle());
        costField.setText(String.valueOf(s.getCoutHoraire()));

        if (s instanceof SeanceIndividuelle si) {
            membersList.getSelectionModel().clearSelection();
            membersList.getSelectionModel().select(si.getMembre());
        } else if (s instanceof SeanceCollective sc) {
            membersList.getSelectionModel().clearSelection();
            for (Membre m : sc.getListeParticipants()) membersList.getSelectionModel().select(m);
            coachCombo.getSelectionModel().select(sc.getCoach());
            capacityField.setText(String.valueOf(sc.getCapacite()));
        }
    }

    private void typeChanged() {
        String t = typeCombo.getSelectionModel().getSelectedItem();
        boolean individual = "Individual".equalsIgnoreCase(t);
        coachCombo.setVisible(!individual);
        coachCombo.setDisable(individual);
        capacityField.setVisible(!individual);
        capacityField.setDisable(individual);
        membersList.getSelectionModel().setSelectionMode(individual ? SelectionMode.SINGLE : SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleUpdate() {
        try {
            if (original == null) { lblInfo.setText("Original session missing."); return; }
            String type = typeCombo.getSelectionModel().getSelectedItem();
            double duree = Double.parseDouble(durationField.getText().trim());
            Date date = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Salle selSalle = salleCombo.getSelectionModel().getSelectedItem();
            double cout = Double.parseDouble(costField.getText().trim());
            if (selSalle == null) { lblInfo.setText("Choose a salle."); return; }

            Seance updated;
            if ("Individual".equalsIgnoreCase(type)) {
                Membre chosen = membersList.getSelectionModel().getSelectedItem();
                if (chosen == null) { lblInfo.setText("Select a member."); return; }
                updated = new SeanceIndividuelle(original.getIdSeance(), duree, date, selSalle, cout, chosen);
            } else {
                Coach chosenCoach = coachCombo.getSelectionModel().getSelectedItem();
                if (chosenCoach == null) { lblInfo.setText("Select a coach."); return; }
                int cap = Integer.parseInt(capacityField.getText().trim());
                SeanceCollective sc = new SeanceCollective(original.getIdSeance(), duree, date, selSalle, cout, chosenCoach, cap);
                for (Membre m : membersList.getSelectionModel().getSelectedItems()) {
                    if (sc.verifierDisponibilite()) {
                        try { sc.ajouterParticipant(m); } catch (SallePleineException ignored) {}
                    }
                }
                updated = sc;
            }

            SessionsController.getInstance().updateSession(original, updated);
            LayoutController.getInstance().loadView("/Views/Sessions/Sessions.fxml");

        } catch (NumberFormatException nfe) {
            lblInfo.setText("Numeric fields invalid.");
        } catch (Exception ex) {
            ex.printStackTrace();
            lblInfo.setText("Error: " + ex.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        LayoutController.getInstance().loadView("/Views/Sessions/Sessions.fxml");
    }
}
