package Controllers.Sessions;

import Controllers.LayoutController;
import DAO.SeanceDAO;
import Models.amine.Gestion.*;
import Models.amen.Infrastructure.*;
import Models.amine.Personnel.*;
import Utils.SessionHolder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class SessionsController {

    @FXML private VBox cardsContainer;

    private final SeanceDAO dao = new SeanceDAO();
    private List<Seance> sessions;
    private List<Membre> membres;
    private List<Coach> coaches;
    private List<Salle> salles;

    @FXML
    public void initialize() {
        loadData();
        refreshCards();
    }

    private void loadData() {
        sessions = dao.getAllSeances();
        membres = new DAO.MembreDAO().getAllMembres();
        coaches = new DAO.CoachDAO().getAllCoachs();
        salles = new DAO.SalleDAO().getAllSalles();
    }

    private void refreshCards() {
        cardsContainer.getChildren().clear();
        for (Seance s : sessions)
            cardsContainer.getChildren().add(makeCard(s));
    }

    private HBox makeCard(Seance s) {

        HBox card = new HBox();
        card.setSpacing(20);
        card.setStyle("""
            -fx-background-color:#0f172a;
            -fx-background-radius:14;
            -fx-padding:20;
            -fx-border-color:#1e293b;
            -fx-border-radius:14;
        """);

        VBox info = new VBox(6);
        boolean isInd = s instanceof SeanceIndividuelle;

        Label type = new Label(isInd ? "Individual Session" : "Collective Session");
        type.setStyle("-fx-text-fill:#3b82f6; -fx-font-size:18px; -fx-font-weight:bold;");

        Label date = new Label("Date: " + s.getDate());
        date.setStyle("-fx-text-fill:white;");

        Label salle = new Label("Salle: " + s.getSalle().getNomSalle());
        salle.setStyle("-fx-text-fill:#94a3b8;");

        info.getChildren().addAll(type, date, salle);

        if (isInd) {
            SeanceIndividuelle si = (SeanceIndividuelle) s;
            Label m = new Label("Member: " + si.getMembre().getNom());
            m.setStyle("-fx-text-fill:#22c55e;");
            info.getChildren().add(m);
        } else {
            SeanceCollective sc = (SeanceCollective) s;
            Label c = new Label("Coach: " + sc.getCoach().getNom());
            c.setStyle("-fx-text-fill:#22c55e;");
            info.getChildren().add(c);
        }

        // ACTIONS
        Button edit = new Button("Edit");
        edit.setStyle("-fx-background-color:#0ea5e9; -fx-text-fill:white;");
        edit.setOnAction(e -> {
            SessionHolder.set(s);
            LayoutController.getInstance().loadView("/Views/Sessions/EditSession.fxml");
        });

        Button delete = new Button("Delete");
        delete.setStyle("-fx-background-color:#dc2626; -fx-text-fill:white;");
        delete.setOnAction(e -> {
            dao.supprimerSeance(s.getIdSeance());
            loadData();
            refreshCards();
        });

        VBox actions = new VBox(10, edit, delete);
        card.getChildren().addAll(info, actions);

        return card;
    }

    @FXML
    private void openAdd() {
        LayoutController.getInstance().loadView("/Views/Sessions/AddSession.fxml");
    }
}
