package Controllers.Coaches;

import Controllers.LayoutController;
import Models.amine.Personnel.Coach;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddCoachController {

    @FXML private TextField tfNom;
    @FXML private TextField tfPrenom;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhone;
    @FXML private TextField tfSpecialite;
    @FXML private TextField tfTarif;

    @FXML
    public void saveCoach() {

        Coach c = new Coach(
                CoachesController.getCoachesList().size() + 1, // AUTO ID
                tfNom.getText(),
                tfPrenom.getText(),
                tfEmail.getText(),
                tfPhone.getText(),
                tfSpecialite.getText(),
                Double.parseDouble(tfTarif.getText())
        );

        CoachesController.addCoachToList(c);

        LayoutController.getInstance().loadView("/Views/Coaches/Coaches.fxml");
    }

    @FXML
    public void cancel() {
        LayoutController.getInstance().loadView("/Views/Coaches/Coaches.fxml");
    }
}
