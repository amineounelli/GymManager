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
import java.util.List;

public class AddSessionController {

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

    @FXML
    public void initialize() {
        typeCombo.setItems(FXCollections.observableArrayList("Individual", "Collective"));
        typeCombo.getSelectionModel().selectFirst();
        typeCombo.setOnAction(e -> typeChanged());

        List<Membre> allMembers = MembersController.getMembersList();
        List<Coach> allCoaches = CoachesController.getCoachesList();

        membersObs.setAll(allMembers);
        membersList.setItems(membersObs);
        membersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        coachesObs.setAll(allCoaches);
        coachCombo.setItems(coachesObs);

        // simple sample salles
        sallesObs.addAll(new Salle(1, "Salle A", 30), new Salle(2, "Salle B", 20));
        salleCombo.setItems(sallesObs);

        typeChanged();
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
    private void handleSave() {
        try {
            String type = typeCombo.getSelectionModel().getSelectedItem();
            double duree = Double.parseDouble(durationField.getText().trim());
            Date date = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Salle selSalle = salleCombo.getSelectionModel().getSelectedItem();
            double cout = Double.parseDouble(costField.getText().trim());
            if (selSalle == null) { lblInfo.setText("Choose a salle."); return; }

            Seance created;
            if ("Individual".equalsIgnoreCase(type)) {
                Membre chosen = membersList.getSelectionModel().getSelectedItem();
                if (chosen == null) { lblInfo.setText("Select a member."); return; }
                created = new SeanceIndividuelle(0, duree, date, selSalle, cout, chosen);
            } else {
                Coach chosenCoach = coachCombo.getSelectionModel().getSelectedItem();
                if (chosenCoach == null) { lblInfo.setText("Select a coach."); return; }
                int cap = Integer.parseInt(capacityField.getText().trim());
                SeanceCollective sc = new SeanceCollective(0, duree, date, selSalle, cout, chosenCoach, cap);
                for (Membre m : membersList.getSelectionModel().getSelectedItems()) {
                    if (sc.verifierDisponibilite()) {
                        try { sc.ajouterParticipant(m); } catch (SallePleineException ignored) {}
                    }
                }
                created = sc;
            }

            // register in SessionsController
            SessionsController sc = SessionsController.getInstance();
            if (sc != null) sc.addSession(created);

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
