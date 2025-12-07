package Controllers.Coaches;

import Controllers.LayoutController;
import DAO.CoachDAO;
import Models.amine.Personnel.Coach;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CoachesController {

    @FXML private TableView<Coach> coachesTable;
    @FXML private TableColumn<Coach, Integer> colId;
    @FXML private TableColumn<Coach, String> colNom;
    @FXML private TableColumn<Coach, String> colPrenom;
    @FXML private TableColumn<Coach, String> colEmail;
    @FXML private TableColumn<Coach, String> colPhone;
    @FXML private TableColumn<Coach, String> colSpecialite;
    @FXML private TableColumn<Coach, Double> colTarif;
    @FXML private TableColumn<Coach, Integer> colNbSeances;
    @FXML private TableColumn<Coach, Double> colRevenu;

    private final CoachDAO coachDAO = new CoachDAO();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colSpecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        colTarif.setCellValueFactory(new PropertyValueFactory<>("tarifHeure"));

        colNbSeances.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getListeSeances().size()
                ).asObject()
        );

        colRevenu.setCellValueFactory(c ->
                new javafx.beans.property.SimpleDoubleProperty(
                        c.getValue().calculerRevenu()
                ).asObject()
        );

        loadCoachesFromDB();
    }

    private void loadCoachesFromDB() {
        ObservableList<Coach> list = coachDAO.getAllCoachsObservable();
        coachesTable.setItems(list);
    }

    @FXML
    private void addCoach() {
        LayoutController.getInstance().loadView("/Views/Coaches/AddCoach.fxml");
    }

    @FXML
    private void editCoach() {
        Coach selected = coachesTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        EditCoachController.setCoach(selected);
        LayoutController.getInstance().loadView("/Views/Coaches/EditCoach.fxml");
    }

    @FXML
    private void deleteCoach() {
        Coach selected = coachesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            coachDAO.supprimerCoach(selected.getId());
            loadCoachesFromDB();
        }
    }
}
