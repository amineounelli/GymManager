package Controllers.Members;

import Controllers.LayoutController;
import DAO.MembreDAO;
import Models.amine.Personnel.Membre;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditMemberController {

    private static Membre memberToEdit;

    public static void setMember(Membre m) {
        memberToEdit = m;
    }

    private final MembreDAO membreDAO = new MembreDAO();

    @FXML private TextField tfNom;
    @FXML private TextField tfPrenom;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhone;

    @FXML
    public void initialize() {
        if (memberToEdit != null) {
            tfNom.setText(memberToEdit.getNom());
            tfPrenom.setText(memberToEdit.getPrenom());
            tfEmail.setText(memberToEdit.getEmail());
            tfPhone.setText(memberToEdit.getTelephone());
        }
    }

    @FXML
    public void updateMember() {

        memberToEdit.setNom(tfNom.getText());
        memberToEdit.setPrenom(tfPrenom.getText());
        memberToEdit.setEmail(tfEmail.getText());
        memberToEdit.setTelephone(tfPhone.getText());

        membreDAO.modifierMembre(memberToEdit);

        LayoutController.getInstance().loadView("/Views/Members/Members.fxml");
    }

    @FXML
    public void cancel() {
        LayoutController.getInstance().loadView("/Views/Members/Members.fxml");
    }
}
