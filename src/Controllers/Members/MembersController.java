package Controllers.Members;

import Controllers.LayoutController;
import Models.amine.Personnel.Membre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MembersController {

    @FXML private TableView<Membre> membersTable;

    @FXML private TableColumn<Membre, Integer> colId;
    @FXML private TableColumn<Membre, String> colNom;
    @FXML private TableColumn<Membre, String> colPrenom;
    @FXML private TableColumn<Membre, String> colEmail;
    @FXML private TableColumn<Membre, String> colPhone;
    @FXML private TableColumn<Membre, Integer> colNbSeances;
    @FXML private TableColumn<Membre, String> colAbonnement;

    private static final ObservableList<Membre> members = FXCollections.observableArrayList();

    public static ObservableList<Membre> getMembersList() {
        return members;
    }

    public static void addMemberToList(Membre m) {
        members.add(m);
    }

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        colNbSeances.setCellValueFactory(m ->
                new javafx.beans.property.SimpleIntegerProperty(
                        m.getValue().getListeSeances().size()
                ).asObject()
        );

        colAbonnement.setCellValueFactory(m ->
                new javafx.beans.property.SimpleStringProperty(
                        m.getValue().getEtatAbonnement()
                )
        );

        if (members.isEmpty()) loadDummy();

        membersTable.setItems(members);
    }

    private void loadDummy() {
        members.add(new Membre(1, "Ahmed", "Ben Ali", "ahmed@gmail.com", "22222222"));
        members.add(new Membre(2, "Sarra", "Kefi", "sarra@gmail.com", "90909090"));
    }

    // LOAD ADD VIEW
    @FXML
    private void addMember() {
        LayoutController.getInstance().loadView("/Views/Members/AddMember.fxml");
    }

    // LOAD EDIT VIEW
    @FXML
    private void editMember() {
        Membre selected = membersTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        EditMemberController.setMember(selected);
        LayoutController.getInstance().loadView("/Views/Members/EditMember.fxml");
    }

    @FXML
    private void deleteMember() {
        Membre selected = membersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            members.remove(selected);
        }
    }
}
