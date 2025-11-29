package Controllers.Coaches;

import Controllers.LayoutController;
import Models.amine.Personnel.Coach;
import javafx.collections.FXCollections;
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

    private static final ObservableList<Coach> coaches = FXCollections.observableArrayList();

    public static ObservableList<Coach> getCoachesList() {
        return coaches;
    }

    public static void addCoachToList(Coach c) {
        coaches.add(c);
    }

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

        if (coaches.isEmpty()) loadDummy();

        coachesTable.setItems(coaches);
    }

    private void loadDummy() {
        coaches.add(new Coach(1, "Leila", "Trabelsi", "leila@gmail.com", "99999999", "Yoga", 40));
        coaches.add(new Coach(2, "Omar", "Gharbi", "omar@gmail.com", "50505050", "Boxing", 60));
    }

    // LOAD ADD VIEW
    @FXML
    private void addCoach() {
        LayoutController.getInstance().loadView("/Views/Coaches/AddCoach.fxml");
    }

    // LOAD EDIT VIEW
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
            coaches.remove(selected);
        }
    }
}
