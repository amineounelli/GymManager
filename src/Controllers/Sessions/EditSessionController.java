package Controllers.Sessions;

import Controllers.LayoutController;
import DAO.*;
import Utils.SessionHolder;
import Models.amine.Gestion.*;
import Models.amen.Infrastructure.*;
import Models.amine.Personnel.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.ZoneId;
import java.util.Date;

public class EditSessionController {

    @FXML private ComboBox<String> cbType;
    @FXML private TextField tfDuree;
    @FXML private DatePicker dpDate;
    @FXML private ComboBox<Salle> cbSalle;
    @FXML private TextField tfCout;

    @FXML private VBox panelIndividual;
    @FXML private VBox panelCollective;

    @FXML private ComboBox<Membre> cbSingleMember;
    @FXML private ComboBox<Coach> cbCoach;
    @FXML private ListView<Membre> lvMembers;
    @FXML private TextField tfCapacite;

    private final MembreDAO membreDAO = new MembreDAO();
    private final CoachDAO coachDAO = new CoachDAO();
    private final SalleDAO salleDAO = new SalleDAO();
    private final SeanceDAO dao = new SeanceDAO();

    private Seance original;

    @FXML
    public void initialize() {
        original = SessionHolder.get();

        cbSingleMember.getItems().setAll(membreDAO.getAllMembres());
        lvMembers.getItems().setAll(membreDAO.getAllMembres());
        cbCoach.getItems().setAll(coachDAO.getAllCoachs());
        cbSalle.getItems().setAll(salleDAO.getAllSalles());
        lvMembers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cbType.setItems(FXCollections.observableArrayList("Individuelle", "Collective"));
        fillForm();
    }

    private void fillForm() {
        tfDuree.setText(String.valueOf(original.getDuree()));
        tfCout.setText(String.valueOf(original.getCoutHoraire()));
        dpDate.setValue(original.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        cbSalle.getSelectionModel().select(original.getSalle());

        if (original instanceof SeanceIndividuelle si) {
            cbType.getSelectionModel().select("Individuelle");
            panelIndividual.setVisible(true);
            cbSingleMember.getSelectionModel().select(si.getMembre());

        } else {
            SeanceCollective sc = (SeanceCollective) original;
            cbType.getSelectionModel().select("Collective");
            panelCollective.setVisible(true);

            cbCoach.getSelectionModel().select(sc.getCoach());
            tfCapacite.setText(String.valueOf(sc.getCapacite()));
        }
    }

    @FXML
    private void updateSession() {
        try {
            double duree = Double.parseDouble(tfDuree.getText());
            double cout = Double.parseDouble(tfCout.getText());
            Salle salle = cbSalle.getValue();
            Date date = Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            if ("Individuelle".equals(cbType.getValue())) {
                Membre m = cbSingleMember.getValue();
                SeanceIndividuelle s = new SeanceIndividuelle(original.getIdSeance(), duree, date, salle, cout, m);
                dao.modifierSeanceIndividuelle(s);

            } else {
                Coach c = cbCoach.getValue();
                int cap = Integer.parseInt(tfCapacite.getText());

                SeanceCollective s = new SeanceCollective(original.getIdSeance(), duree, date, salle, cout, c, cap);
                dao.modifierSeanceCollective(s);
            }

            LayoutController.getInstance().loadView("/Views/Sessions/Sessions.fxml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() {
        LayoutController.getInstance().loadView("/Views/Sessions/Sessions.fxml");
    }
}
