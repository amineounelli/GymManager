package Controllers.Members;

import Controllers.LayoutController;
import DAO.MembreDAO;
import Models.amine.Personnel.Membre;
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

    private final MembreDAO membreDAO = new MembreDAO();

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

        loadMembersFromDB();
    }

    private void loadMembersFromDB() {
        ObservableList<Membre> list = membreDAO.getAllMembresObservable();
        membersTable.setItems(list);
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
            membreDAO.supprimerMembre(selected.getId());
            loadMembersFromDB();
        }
    }
}
