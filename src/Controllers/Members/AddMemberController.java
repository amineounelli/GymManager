package Controllers.Members;

import Controllers.LayoutController;
import Models.amine.Personnel.Membre;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddMemberController {

    @FXML private TextField tfNom;
    @FXML private TextField tfPrenom;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhone;

    @FXML
    public void saveMember() {

        Membre m = new Membre(
                MembersController.getMembersList().size() + 1,
                tfNom.getText(),
                tfPrenom.getText(),
                tfEmail.getText(),
                tfPhone.getText()
        );

        MembersController.addMemberToList(m);

        LayoutController.getInstance().loadView("/Views/Members/Members.fxml");
    }

    @FXML
    public void cancel() {
        LayoutController.getInstance().loadView("/Views/Members/Members.fxml");
    }
}
