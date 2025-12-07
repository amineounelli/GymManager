package Controllers.Coaches;

import Controllers.LayoutController;
import DAO.CoachDAO;
import Models.amine.Personnel.Coach;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditCoachController {

    private static Coach coachToEdit;

    public static void setCoach(Coach c) {
        coachToEdit = c;
    }

    private final CoachDAO coachDAO = new CoachDAO();

    @FXML private TextField tfNom;
    @FXML private TextField tfPrenom;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfSpecialite;
    @FXML private TextField tfTarif;

    @FXML
    public void initialize() {
        if (coachToEdit != null) {
            tfNom.setText(coachToEdit.getNom());
            tfPrenom.setText(coachToEdit.getPrenom());
            tfEmail.setText(coachToEdit.getEmail());
            tfPhone.setText(coachToEdit.getTelephone());
            tfSpecialite.setText(coachToEdit.getSpecialite());
            tfTarif.setText(String.valueOf(coachToEdit.getTarifHeure()));
        }
    }

    @FXML
    public void updateCoach() {

        coachToEdit.setNom(tfNom.getText());
        coachToEdit.setPrenom(tfPrenom.getText());
        coachToEdit.setEmail(tfEmail.getText());
        coachToEdit.setTelephone(tfPhone.getText());
        coachToEdit.setSpecialite(tfSpecialite.getText());
        coachToEdit.setTarifHeure(Double.parseDouble(tfTarif.getText()));

        coachDAO.modifierCoach(coachToEdit);

        LayoutController.getInstance().loadView("/Views/Coaches/Coaches.fxml");
    }

    @FXML
    public void cancel() {
        LayoutController.getInstance().loadView("/Views/Coaches/Coaches.fxml");
    }
}
