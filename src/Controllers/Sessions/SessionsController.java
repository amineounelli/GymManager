package Controllers.Sessions;

import Controllers.LayoutController;
import Controllers.Members.MembersController;
import Controllers.Coaches.CoachesController;
import Models.amen.Infrastructure.*;
import Models.amine.Personnel.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class SessionsController {

    @FXML private VBox cardsContainer;

    // keep an accessible instance
    private static SessionsController instance;
    public SessionsController() { instance = this; }
    public static SessionsController getInstance() { return instance; }

    // sessions list (in-memory)
    private final List<Seance> sessions = new ArrayList<>();

    // references to members/coaches lists from their controllers
    private List<Membre> membres;
    private List<Coach> coaches;
    private final List<Salle> salles = new ArrayList<>();

    @FXML
    public void initialize() {
        // get shared lists from the other controllers (they expose static ObservableLists)
        membres = new ArrayList<>(MembersController.getMembersList());
        coaches = new ArrayList<>(CoachesController.getCoachesList());

        // create dummy members/coaches if none exist so UI won't be empty (optional)
        if (membres.isEmpty()) {
            membres.add(new Membre(1, "Ahmed", "Ben Ali", "ahmed@gmail.com", "22222222"));
            membres.add(new Membre(2, "Sarra", "Kefi", "sarra@gmail.com", "90909090"));
        }
        if (coaches.isEmpty()) {
            coaches.add(new Coach(1, "Leila", "Trabelsi", "leila@gmail.com", "99999999", "Yoga", 40));
            coaches.add(new Coach(2, "Omar", "Gharbi", "omar@gmail.com", "50505050", "Boxing", 60));
        }

        // dummy salles (you can replace by real Salle controller later)
        salles.add(new Salle(1, "Salle A", 30));
        salles.add(new Salle(2, "Salle B", 20));

        // add a couple of sample sessions so cards are visible
        sessions.clear();
        sessions.add(new SeanceIndividuelle(1, 1.0, new java.util.Date(), salles.get(0), 20, membres.get(0)));
        sessions.add(new SeanceCollective(2, 2.0, new java.util.Date(), salles.get(0), 15, coaches.get(0), 10));

        refreshCards();
    }

    private void refreshCards() {
        if (cardsContainer == null) return;
        cardsContainer.getChildren().clear();
        for (Seance s : sessions) cardsContainer.getChildren().add(createSessionCard(s));
    }

    private HBox createSessionCard(Seance s) {
        HBox card = new HBox();
        card.setSpacing(12);
        card.setStyle("-fx-background-color: #0f172a; -fx-background-radius: 12; -fx-padding: 12;");

        VBox info = new VBox(6);
        Label lblType = new Label((s instanceof SeanceIndividuelle) ? "Individual Session" : "Collective Session");
        lblType.setStyle("-fx-text-fill: #3b82f6; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label lblDate = new Label("Date: " + s.getDate());
        lblDate.setStyle("-fx-text-fill: white;");
        Label lblSalle = new Label("Salle: " + (s.getSalle() != null ? s.getSalle().getNomSalle() : "—"));
        lblSalle.setStyle("-fx-text-fill: #94a3b8;");
        info.getChildren().addAll(lblType, lblDate, lblSalle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox actions = new VBox(8);
        Button btnEdit = new Button("Edit");
        btnEdit.setStyle("-fx-background-color: #0ea5e9; -fx-text-fill: white; -fx-background-radius: 6;");
        btnEdit.setOnAction(e -> openEditSession(s));

        Button btnDelete = new Button("Delete");
        btnDelete.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-background-radius: 6;");
        btnDelete.setOnAction(e -> { sessions.remove(s); refreshCards(); });

        actions.getChildren().addAll(btnEdit, btnDelete);

        card.getChildren().addAll(info, spacer, actions);
        return card;
    }

    // Called by AddSessionController when saving
    public void addSession(Seance s) {
        // set id
        s.idSeance = generateNewId();
        sessions.add(s);
        refreshCards();
    }

    // Called by EditSessionController when updating
    public void updateSession(Seance oldS, Seance updated) {
        int idx = sessions.indexOf(oldS);
        if (idx != -1) {
            sessions.set(idx, updated);
            refreshCards();
        }
    }

    private int generateNewId() {
        return sessions.stream().mapToInt(Seance::getIdSeance).max().orElse(0) + 1;
    }

    // load Add view (non-popup)
    @FXML
    private void handleAddSession() {
        // ensure Add controller can read the real members/coaches via static access
        LayoutController.getInstance().loadView("/Views/Sessions/AddSession.fxml");
    }

    // prepare editing session and open edit view
    private static Seance editing;
    private void openEditSession(Seance s) {
        editing = s;
        // EditSessionController will get editing session via SessionsController.getEditingSession()
        LayoutController.getInstance().loadView("/Views/Sessions/EditSession.fxml");
    }

    // helper for EditSessionController
    public static Seance getEditingSession() { return editing; }
}
