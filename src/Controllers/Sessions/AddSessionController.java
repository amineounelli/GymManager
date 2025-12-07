package Controllers.Sessions;

import Controllers.LayoutController;
import DAO.*;
import Models.amine.Gestion.*;
import Models.amen.Infrastructure.*;
import Models.amine.Personnel.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AddSessionController {

    @FXML private ComboBox<String> cbType;
    @FXML private TextField tfDuree;
    @FXML private DatePicker dpDate;
    @FXML private ComboBox<Salle> cbSalle;
    @FXML private TextField tfCout;

    @FXML private ComboBox<Membre> cbSingleMember;
    @FXML private ComboBox<Coach> cbCoach;
    @FXML private ListView<Membre> lvMembers;
    @FXML private TextField tfCapacite;

    @FXML private VBox panelIndividual;
    @FXML private VBox panelCollective;

    private final MembreDAO membreDAO = new MembreDAO();
    private final CoachDAO coachDAO = new CoachDAO();
    private final SalleDAO salleDAO = new SalleDAO();
    private final SeanceDAO seanceDAO = new SeanceDAO();

    @FXML
    public void initialize() {
        cbType.getItems().addAll("Individuelle", "Collective");

        cbSingleMember.getItems().setAll(membreDAO.getAllMembres());
        lvMembers.getItems().setAll(membreDAO.getAllMembres());
        cbCoach.getItems().setAll(coachDAO.getAllCoachs());
        cbSalle.getItems().setAll(salleDAO.getAllSalles());

        lvMembers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void onTypeChanged() {
        boolean indiv = "Individuelle".equals(cbType.getValue());
        panelIndividual.setVisible(indiv);
        panelIndividual.setManaged(indiv);

        panelCollective.setVisible(!indiv);
        panelCollective.setManaged(!indiv);
    }

    @FXML
    private void saveSession() {
        try {
            double duree = Double.parseDouble(tfDuree.getText());
            double cout = Double.parseDouble(tfCout.getText());
            Date date = Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Salle salle = cbSalle.getValue();

            if (salle == null) throw new Exception("Salle required");

            if ("Individuelle".equals(cbType.getValue())) {
                Membre m = cbSingleMember.getValue();
                SeanceIndividuelle s = new SeanceIndividuelle(0, duree, date, salle, cout, m);
                seanceDAO.ajouterSeanceIndividuelle(s);

            } else {
                Coach c = cbCoach.getValue();
                int cap = Integer.parseInt(tfCapacite.getText());
                SeanceCollective s = new SeanceCollective(0, duree, date, salle, cout, c, cap);
                seanceDAO.ajouterSeanceCollective(s);
            }

            LayoutController.getInstance().loadView("/Views/Sessions/Sessions.fxml");

        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void cancel() {
        LayoutController.getInstance().loadView("/Views/Sessions/Sessions.fxml");
    }
}
