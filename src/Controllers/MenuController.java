package Contollers;

import Models.amine.Personnel.*;
import Models.amen.Infrastructure.*;
import Models.amine.Gestion.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import java.text.SimpleDateFormat;
import java.util.*;
import Views.MainApp;


/**
 * FXML Controller class - Menu Principal du Système de Gestion de Gym
 * @author DELL
 */
public class MenuController {

    // ==================== TABS ====================
    @FXML private TabPane mainTabPane;

    // ==================== MEMBRES ====================
    @FXML private TextField membreNomField;
    @FXML private TextField membrePrenomField;
    @FXML private TextField membreEmailField;
    @FXML private TextField membreTelField;
    @FXML private TableView<Membre> membresTable;
    @FXML private TableColumn<Membre, Integer> membreIdCol;
    @FXML private TableColumn<Membre, String> membreNomCol;
    @FXML private TableColumn<Membre, String> membreEmailCol;

    // ==================== COACHS ====================
    @FXML private TextField coachNomField;
    @FXML private TextField coachPrenomField;
    @FXML private TextField coachEmailField;
    @FXML private TextField coachTelField;
    @FXML private TextField coachSpecialiteField;
    @FXML private TextField coachTarifField;
    @FXML private TableView<Coach> coachsTable;
    @FXML private TableColumn<Coach, Integer> coachIdCol;
    @FXML private TableColumn<Coach, String> coachNomCol;

    // ==================== MANAGERS ====================
    @FXML private TextField managerNomField;
    @FXML private TextField managerPrenomField;
    @FXML private TextField managerEmailField;
    @FXML private TextField managerTelField;
    @FXML private TextField managerLoginField;
    @FXML private PasswordField managerPasswordField;
    @FXML private TableView<Manager> managersTable;
    @FXML private TableColumn<Manager, Integer> managerIdCol;
    @FXML private TableColumn<Manager, String> managerNomCol;

    // ==================== SALLES ====================
    @FXML private TextField nomSalleField;
    @FXML private TextField capaciteSalleField;
    @FXML private TableView<Salle> sallesTable;
    @FXML private TableColumn<Salle, Integer> salleIdCol;
    @FXML private TableColumn<Salle, String> salleNomCol;
    @FXML private TableColumn<Salle, Integer> salleCapaciteCol;

    // ==================== ÉQUIPEMENTS ====================
    @FXML private TextField nomEquipField;
    @FXML private ComboBox<String> etatEquipComboBox;
    @FXML private ComboBox<Integer> equipSalleComboBox;
    @FXML private TableView<Equipement> equipementsTable;
    @FXML private TableColumn<Equipement, Integer> equipIdCol;
    @FXML private TableColumn<Equipement, String> equipNomCol;
    @FXML private TableColumn<Equipement, String> equipEtatCol;

    // ==================== ABONNEMENTS ====================
    @FXML private ComboBox<String> membreAbonnementComboBox;
    @FXML private ComboBox<String> typeAbonnementComboBox;
    @FXML private Label prixAbonnementLabel;
    @FXML private TableView<AbonnementDisplay> abonnementsTable;
    @FXML private TableColumn<AbonnementDisplay, String> aboMembreCol;
    @FXML private TableColumn<AbonnementDisplay, String> aboTypeCol;
    @FXML private TableColumn<AbonnementDisplay, String> aboDebutCol;
    @FXML private TableColumn<AbonnementDisplay, String> aboFinCol;
    @FXML private TableColumn<AbonnementDisplay, String> aboValideCol;

    // ==================== PAIEMENTS ====================
    @FXML private ComboBox<String> membrePaiementComboBox;
    @FXML private ComboBox<String> typePaiementComboBox;
    @FXML private TextField montantPaiementField;
    @FXML private TableView<Paiement> paiementsTable;
    @FXML private TableColumn<Paiement, Integer> paiementIdCol;
    @FXML private TableColumn<Paiement, String> paiementMembreCol;
    @FXML private TableColumn<Paiement, String> paiementTypeCol;
    @FXML private TableColumn<Paiement, Double> paiementMontantCol;

    // ==================== SÉANCES ====================
    @FXML private ComboBox<String> typeSeanceComboBox;
    @FXML private ComboBox<String> coachSeanceComboBox;
    @FXML private ComboBox<String> membreSeanceComboBox;
    @FXML private ComboBox<String> salleSeanceComboBox;
    @FXML private TextField dateSeanceField;
    @FXML private TextField dureeSeanceField;
    @FXML private TextField coutSeanceField;
    @FXML private TextField capaciteSeanceField;
    @FXML private TableView<SeanceDisplay> seancesTable;
    @FXML private TableColumn<SeanceDisplay, Integer> seanceIdCol;
    @FXML private TableColumn<SeanceDisplay, String> seanceTypeCol;
    @FXML private TableColumn<SeanceDisplay, String> seanceDateCol;

    // ==================== PROGRAMMES ====================
    @FXML private ComboBox<String> coachProgrammeComboBox;
    @FXML private TextField titreProgrammeField;
    @FXML private ComboBox<String> programmeExerciceComboBox;
    @FXML private TextField nomExerciceField;
    @FXML private TextField repetitionsField;
    @FXML private TextField poidsField;
    @FXML private TextField dureeExerciceField;
    @FXML private TableView<ProgrammeEntrainement> programmesTable;
    @FXML private TableColumn<ProgrammeEntrainement, Integer> progIdCol;
    @FXML private TableColumn<ProgrammeEntrainement, String> progTitreCol;

    // ==================== RÉSERVATIONS ====================
    @FXML private ComboBox<String> membreReservationComboBox;
    @FXML private ComboBox<String> seanceReservationComboBox;
    @FXML private TableView<ReservationDisplay> reservationsTable;
    @FXML private TableColumn<ReservationDisplay, Integer> resIdCol;
    @FXML private TableColumn<ReservationDisplay, String> resMembreCol;
    @FXML private TableColumn<ReservationDisplay, String> resSeanceCol;

    // ==================== STATISTIQUES ====================
    @FXML private Label revenuTotalLabel;
    @FXML private Label depenseTotalLabel;
    @FXML private Label beneficeNetLabel;
    @FXML private Label nombreMembresLabel;
    @FXML private Label nombreSeancesLabel;
    @FXML private TextArea statsDetailsArea;

    // ==================== MESSAGES ====================
    @FXML private Label messageLabel;
    @FXML private TextArea detailsArea;

    // ==================== DONNÉES ====================
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Personnel
    private ObservableList<Membre> membres = FXCollections.observableArrayList();
    private ObservableList<Coach> coachs = FXCollections.observableArrayList();
    private ObservableList<Manager> managers = FXCollections.observableArrayList();

    // Infrastructure
    private ObservableList<Salle> salles = FXCollections.observableArrayList();
    private ObservableList<Equipement> equipements = FXCollections.observableArrayList();

    // Gestion
    private Map<Integer, Abonnement> abonnements = new HashMap<>();
    private ObservableList<Paiement> paiements = FXCollections.observableArrayList();
    private ObservableList<Seance> seances = FXCollections.observableArrayList();
    private ObservableList<ProgrammeEntrainement> programmes = FXCollections.observableArrayList();
    private ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    // IDs
    private int nextPersonneId = 1;
    private int nextSalleId = 1;
    private int nextEquipId = 1;
    private int nextPaiementId = 1;
    private int nextSeanceId = 1;
    private int nextProgrammeId = 1;
    private int nextReservationId = 1;

    @FXML
    public void initialize() {
        initializeMembresTab();
        initializeCoachsTab();
        initializeManagersTab();
        initializeSallesTab();
        initializeEquipementsTab();
        initializeAbonnementsTab();
        initializePaiementsTab();
        initializeSeancesTab();
        initializeProgrammesTab();
        initializeReservationsTab();
        initializeStatistiquesTab();
    }

    // ==================== INITIALISATION DES ONGLETS ====================

    private void initializeMembresTab() {
        membreIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        membreNomCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNom() + " " + cellData.getValue().getPrenom()));
        membreEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        membresTable.setItems(membres);
    }

    private void initializeCoachsTab() {
        coachIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        coachNomCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNom() + " " + cellData.getValue().getPrenom()));
        coachsTable.setItems(coachs);
    }

    private void initializeManagersTab() {
        managerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        managerNomCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNom() + " " + cellData.getValue().getPrenom()));
        managersTable.setItems(managers);
    }

    private void initializeSallesTab() {
        salleIdCol.setCellValueFactory(new PropertyValueFactory<>("idSalle"));
        salleNomCol.setCellValueFactory(new PropertyValueFactory<>("nomSalle"));
        salleCapaciteCol.setCellValueFactory(new PropertyValueFactory<>("capaciteSalle"));
        sallesTable.setItems(salles);
    }

    private void initializeEquipementsTab() {
        etatEquipComboBox.setItems(FXCollections.observableArrayList(
                "fonctionnel", "en réparation", "hors service"));
        etatEquipComboBox.setValue("fonctionnel");

        equipIdCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().idEquipement()).asObject());
        equipNomCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().nom()));
        equipEtatCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().etat()));
        equipementsTable.setItems(equipements);
    }

    private void initializeAbonnementsTab() {
        typeAbonnementComboBox.setItems(FXCollections.observableArrayList(
                "Mensuel (50 DT)", "Trimestriel (120 DT)", "Annuel (400 DT)"));

        typeAbonnementComboBox.setOnAction(e -> {
            String type = typeAbonnementComboBox.getValue();
            if (type != null) {
                if (type.contains("Mensuel")) prixAbonnementLabel.setText("50 DT");
                else if (type.contains("Trimestriel")) prixAbonnementLabel.setText("120 DT");
                else prixAbonnementLabel.setText("400 DT");
            }
        });

        aboMembreCol.setCellValueFactory(new PropertyValueFactory<>("membreNom"));
        aboTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aboDebutCol.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        aboFinCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        aboValideCol.setCellValueFactory(new PropertyValueFactory<>("valide"));
    }

    private void initializePaiementsTab() {
        typePaiementComboBox.setItems(FXCollections.observableArrayList(
                "Espèces", "Carte bancaire", "Virement"));

        paiementIdCol.setCellValueFactory(new PropertyValueFactory<>("idPaiement"));
        paiementMembreCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMembre().getNom() + " " +
                        cellData.getValue().getMembre().getPrenom()));
        paiementTypeCol.setCellValueFactory(new PropertyValueFactory<>("typePaiement"));
        paiementMontantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
        paiementsTable.setItems(paiements);
    }

    private void initializeSeancesTab() {
        typeSeanceComboBox.setItems(FXCollections.observableArrayList(
                "Collective", "Individuelle"));

        seanceIdCol.setCellValueFactory(new PropertyValueFactory<>("idSeance"));
        seanceTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        seanceDateCol.setCellValueFactory(new PropertyValueFactory<>("dateStr"));
    }

    private void initializeProgrammesTab() {
        progIdCol.setCellValueFactory(new PropertyValueFactory<>("idProgramme"));
        progTitreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        programmesTable.setItems(programmes);
    }

    private void initializeReservationsTab() {
        resIdCol.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        resMembreCol.setCellValueFactory(new PropertyValueFactory<>("membreNom"));
        resSeanceCol.setCellValueFactory(new PropertyValueFactory<>("seanceInfo"));
    }

    private void initializeStatistiquesTab() {
        // Les statistiques seront calculées dynamiquement
    }

    // ==================== MEMBRES ====================

    @FXML
    private void handleAjouterMembre() {
        try {
            String nom = membreNomField.getText().trim();
            String prenom = membrePrenomField.getText().trim();
            String email = membreEmailField.getText().trim();
            String tel = membreTelField.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || tel.isEmpty()) {
                showMessage("❌ Veuillez remplir tous les champs");
                return;
            }

            Membre membre = new Membre(nextPersonneId++, nom, prenom, email, tel);
            membres.add(membre);

            showMessage("✅ Membre ajouté avec succès! ID: " + membre.getId());
            clearMembreFields();
            rafraichirComboBoxes();

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleSupprimerMembre() {
        Membre selected = membresTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez un membre à supprimer");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le membre");
        alert.setContentText("Voulez-vous vraiment supprimer " + selected.getNom() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            membres.remove(selected);
            showMessage("✅ Membre supprimé");
            rafraichirComboBoxes();
        }
    }

    @FXML
    private void handleAfficherMembres() {
        membresTable.setItems(membres);
        showMessage("📋 Liste des membres affichée");
    }


    // stream1
    private List<Membre> getMembresAvecAbonnementActif() {
        return membres.stream()
                .filter(m -> abonnements.containsKey(m.getId()))
                .filter(m -> abonnements.get(m.getId()).verifierValidite())
                .toList();
    }

    @FXML
    private void handleAfficherMembresActifs() {
        List<Membre> list = getMembresAvecAbonnementActif();
        membresTable.setItems(FXCollections.observableArrayList(list));
        showMessage("Liste des membres avec abonnement actif chargée");
    }

    // ==================== COACHS ====================

    @FXML
    private void handleAjouterCoach() {
        try {
            String nom = coachNomField.getText().trim();
            String prenom = coachPrenomField.getText().trim();
            String email = coachEmailField.getText().trim();
            String tel = coachTelField.getText().trim();
            String specialite = coachSpecialiteField.getText().trim();
            String tarifStr = coachTarifField.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() ||
                    tel.isEmpty() || specialite.isEmpty() || tarifStr.isEmpty()) {
                showMessage("❌ Veuillez remplir tous les champs");
                return;
            }

            double tarif = Double.parseDouble(tarifStr);
            Coach coach = new Coach(nextPersonneId++, nom, prenom, email, tel, specialite, tarif);
            coachs.add(coach);

            showMessage("✅ Coach ajouté avec succès! ID: " + coach.getId());
            clearCoachFields();
            rafraichirComboBoxes();

        } catch (NumberFormatException e) {
            showMessage("❌ Tarif invalide");
        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleSupprimerCoach() {
        Coach selected = coachsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez un coach à supprimer");
            return;
        }

        coachs.remove(selected);
        showMessage("✅ Coach supprimé");
        rafraichirComboBoxes();
    }

    // ==================== MANAGERS ====================

    @FXML
    private void handleAjouterManager() {
        try {
            String nom = managerNomField.getText().trim();
            String prenom = managerPrenomField.getText().trim();
            String email = managerEmailField.getText().trim();
            String tel = managerTelField.getText().trim();
            String login = managerLoginField.getText().trim();
            String password = managerPasswordField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() ||
                    tel.isEmpty() || login.isEmpty() || password.isEmpty()) {
                showMessage("❌ Veuillez remplir tous les champs");
                return;
            }

            Manager manager = new Manager(nextPersonneId++, nom, prenom, email, tel, login, password);
            managers.add(manager);

            showMessage("✅ Manager ajouté avec succès! ID: " + manager.getId());
            clearManagerFields();

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleSupprimerManager() {
        Manager selected = managersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez un manager à supprimer");
            return;
        }

        managers.remove(selected);
        showMessage("✅ Manager supprimé");
    }

    // ==================== SALLES ====================

    @FXML
    private void handleCreerSalle() {
        try {
            String nom = nomSalleField.getText().trim();
            String capaciteStr = capaciteSalleField.getText().trim();

            if (nom.isEmpty() || capaciteStr.isEmpty()) {
                showMessage("❌ Veuillez remplir tous les champs");
                return;
            }

            int capacite = Integer.parseInt(capaciteStr);
            Salle salle = new Salle(nextSalleId++, nom, capacite);
            salles.add(salle);

            showMessage("✅ Salle créée avec succès! ID: " + salle.getIdSalle());
            nomSalleField.clear();
            capaciteSalleField.clear();
            rafraichirComboBoxes();

        } catch (NumberFormatException e) {
            showMessage("❌ Capacité invalide");
        }
    }

    @FXML
    private void handleSupprimerSalle() {
        Salle selected = sallesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez une salle à supprimer");
            return;
        }

        salles.remove(selected);
        showMessage("✅ Salle supprimée");
        rafraichirComboBoxes();
    }

    // ==================== ÉQUIPEMENTS ====================

    @FXML
    private void handleAjouterEquipement() {
        try {
            String nom = nomEquipField.getText().trim();
            String etat = etatEquipComboBox.getValue();

            if (nom.isEmpty() || etat == null) {
                showMessage("❌ Veuillez remplir tous les champs");
                return;
            }

            Equipement equip = new Equipement(nextEquipId++, nom, etat);
            equipements.add(equip);

            showMessage("✅ Équipement ajouté! ID: " + equip.idEquipement());
            nomEquipField.clear();
            rafraichirComboBoxes();

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleAffecterEquipementSalle() {
        Equipement equip = equipementsTable.getSelectionModel().getSelectedItem();
        Integer salleId = equipSalleComboBox.getValue();

        if (equip == null || salleId == null) {
            showMessage("❌ Sélectionnez un équipement et une salle");
            return;
        }

        Salle salle = trouverSalleParId(salleId);
        if (salle != null) {
            salle.ajouterEquipement(equip);
            showMessage("✅ Équipement affecté à " + salle.getNomSalle());
        }
    }

    @FXML
    private void handleSupprimerEquipement() {
        Equipement selected = equipementsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez un équipement à supprimer");
            return;
        }

        equipements.remove(selected);
        showMessage("✅ Équipement supprimé");
    }

    @FXML
    private void handleAfficherEquipements() {
        equipementsTable.setItems(equipements);
        showMessage("📋 Liste des équipements affichée");
    }


    // stream2
    private List<Equipement> getEquipementsFonctionnels() {
        return equipements.stream()
                .filter(Equipement::verifierEtat)
                .sorted(Comparator.comparing(Equipement::nom))
                .toList();
    }

    @FXML
    private void handleAfficherEquipementsFonctionnels() {
        List<Equipement> list = getEquipementsFonctionnels();
        equipementsTable.setItems(FXCollections.observableArrayList(list));
        showMessage("Liste des équipements fonctionnels chargée");
    }

    // ==================== ABONNEMENTS ====================

    @FXML
    private void handleCreerAbonnement() {
        try {
            String membreNom = membreAbonnementComboBox.getValue();
            String typeChoix = typeAbonnementComboBox.getValue();

            if (membreNom == null || typeChoix == null) {
                showMessage("❌ Sélectionnez un membre et un type");
                return;
            }

            Membre membre = trouverMembreParNom(membreNom);
            if (membre == null) {
                showMessage("❌ Membre introuvable");
                return;
            }

            String type;
            Date debut = new Date();
            Date fin = new Date();

            if (typeChoix.contains("Mensuel")) {
                type = "Mensuel";
                fin.setTime(debut.getTime() + 30L * 24 * 60 * 60 * 1000);
            } else if (typeChoix.contains("Trimestriel")) {
                type = "Trimestriel";
                fin.setTime(debut.getTime() + 90L * 24 * 60 * 60 * 1000);
            } else {
                type = "Annuel";
                fin.setTime(debut.getTime() + 365L * 24 * 60 * 60 * 1000);
            }

            Abonnement abo = new Abonnement(type, debut, fin);
            abonnements.put(membre.getId(), abo);

            showMessage("✅ Abonnement créé pour " + membre.getNom());
            rafraichirTableauAbonnements();

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleAfficherAbonnements() {
        rafraichirTableauAbonnements();
        showMessage("✅ Liste des abonnements chargée");
    }

    // ==================== PAIEMENTS ====================

    @FXML
    private void handleEffectuerPaiement() {
        try {
            String membreNom = membrePaiementComboBox.getValue();
            String type = typePaiementComboBox.getValue();
            String montantStr = montantPaiementField.getText().trim();

            if (membreNom == null || type == null || montantStr.isEmpty()) {
                showMessage("❌ Veuillez remplir tous les champs");
                return;
            }

            double montant = Double.parseDouble(montantStr);
            Membre membre = trouverMembreParNom(membreNom);

            if (membre == null) {
                showMessage("❌ Membre introuvable");
                return;
            }

            Paiement paiement = new Paiement(nextPaiementId++, type, montant, new Date(), membre);
            paiements.add(paiement);
            membre.effectuerPaiement(paiement);

            showMessage("✅ Paiement effectué! ID: " + paiement.getIdPaiement());
            montantPaiementField.clear();
            calculerStatistiques();

        } catch (NumberFormatException e) {
            showMessage("❌ Montant invalide");
        }
    }

    // ==================== SÉANCES ====================

    @FXML
    private void handleCreerSeance() {
        try {
            String type = typeSeanceComboBox.getValue();
            String dateStr = dateSeanceField.getText().trim();
            String dureeStr = dureeSeanceField.getText().trim();
            String coutStr = coutSeanceField.getText().trim();

            if (type == null || dateStr.isEmpty() || dureeStr.isEmpty() || coutStr.isEmpty()) {
                showMessage("❌ Veuillez remplir tous les champs");
                return;
            }

            Date date = sdf.parse(dateStr);
            double duree = Double.parseDouble(dureeStr);
            double cout = Double.parseDouble(coutStr);

            String salleNom = salleSeanceComboBox.getValue();
            Salle salle = trouverSalleParNom(salleNom);

            if (salle == null) {
                showMessage("❌ Sélectionnez une salle valide");
                return;
            }

            Seance seance;
            if (type.equals("Collective")) {
                String coachNom = coachSeanceComboBox.getValue();
                Coach coach = trouverCoachParNom(coachNom);
                String capaciteStr = capaciteSeanceField.getText().trim();
                int capacite = Integer.parseInt(capaciteStr);

                seance = new SeanceCollective(nextSeanceId++, duree, date, salle, cout, coach, capacite);
            } else {
                String membreNom = membreSeanceComboBox.getValue();
                Membre membre = trouverMembreParNom(membreNom);

                seance = new SeanceIndividuelle(nextSeanceId++, duree, date, salle, cout, membre);
            }

            seances.add(seance);
            showMessage("✅ Séance créée! ID: " + seance.getIdSeance());
            calculerStatistiques();

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    // ==================== PROGRAMMES ====================

    @FXML
    private void handleCreerProgramme() {
        try {
            String coachNom = coachProgrammeComboBox.getValue();
            String titre = titreProgrammeField.getText().trim();

            if (coachNom == null || titre.isEmpty()) {
                showMessage("❌ Sélectionnez un coach et entrez un titre");
                return;
            }

            Coach coach = trouverCoachParNom(coachNom);
            if (coach == null) {
                showMessage("❌ Coach introuvable");
                return;
            }

            ProgrammeEntrainement prog = new ProgrammeEntrainement(nextProgrammeId++, titre, coach);
            programmes.add(prog);
            coach.creerProgramme(prog);

            showMessage("✅ Programme créé! ID: " + prog.getIdProgramme());
            titreProgrammeField.clear();
            rafraichirComboBoxes();

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleAjouterExercice() {
        try {
            ProgrammeEntrainement prog = programmesTable.getSelectionModel().getSelectedItem();
            if (prog == null) {
                showMessage("❌ Sélectionnez un programme");
                return;
            }

            String nom = nomExerciceField.getText().trim();
            int rep = Integer.parseInt(repetitionsField.getText().trim());
            double poids = Double.parseDouble(poidsField.getText().trim());
            double duree = Double.parseDouble(dureeExerciceField.getText().trim());

            Exercice ex = new Exercice(nom, rep, poids, duree, null);
            prog.ajouterExercice(ex);

            showMessage("✅ Exercice ajouté au programme!");
            nomExerciceField.clear();
            repetitionsField.clear();
            poidsField.clear();
            dureeExerciceField.clear();

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    // ==================== RÉSERVATIONS ====================

    @FXML
    private void handleCreerReservation() {
        try {
            String membreNom = membreReservationComboBox.getValue();
            String seanceInfo = seanceReservationComboBox.getValue();

            if (membreNom == null || seanceInfo == null) {
                showMessage("❌ Sélectionnez un membre et une séance");
                return;
            }

            Membre membre = trouverMembreParNom(membreNom);
            // Extraire l'ID de la séance depuis seanceInfo
            int seanceId = Integer.parseInt(seanceInfo.split(" - ")[0].replace("ID:", "").trim());
            Seance seance = trouverSeanceParId(seanceId);

            if (membre == null || seance == null) {
                showMessage("❌ Membre ou séance introuvable");
                return;
            }

            Reservation res = new Reservation(nextReservationId++, membre, seance, new Date());
            reservations.add(res);

            showMessage("✅ Réservation créée! ID: " + res.getIdReservation());
            rafraichirTableauReservations();

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleConfirmerReservation() {
        Reservation selected = reservationsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez une réservation");
            return;
        }

        Reservation res = trouverReservationParId(selected.getIdReservation());
        if (res != null) {
            res.confirmerReservation();
            showMessage("✅ Réservation confirmée!");
            reservationsTable.refresh();
        }
    }

    @FXML
    private void handleAnnulerReservation() {
        Reservation selected = reservationsTable.getSelectionModel().getSelectedItem() ;
        if (selected == null) {
            showMessage("❌ Sélectionnez une réservation");
            return;
        }

        Reservation res = trouverReservationParId(selected.getIdReservation());
        if (res != null) {
            res.annulerReservation();
            reservations.remove(res);
            showMessage("✅ Réservation annulée!");
            rafraichirTableauReservations();
        }
    }

    // ==================== STATISTIQUES ====================

    @FXML
    private void handleCalculerStatistiques() {
        calculerStatistiques();
    }

    private void calculerStatistiques() {
        // Revenu total
        double revenu = paiements.stream().mapToDouble(Paiement::getMontant).sum();
        revenuTotalLabel.setText(String.format("%.2f DT", revenu));

        // Dépense totale (salaires coachs)
        double depense = coachs.stream().mapToDouble(Coach::calculerRevenu).sum();
        depenseTotalLabel.setText(String.format("%.2f DT", depense));

        // Bénéfice net
        double benefice = revenu - depense;
        beneficeNetLabel.setText(String.format("%.2f DT", benefice));
        if (benefice > 0) {
            beneficeNetLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else if (benefice < 0) {
            beneficeNetLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }

        // Nombre de membres
        nombreMembresLabel.setText(String.valueOf(membres.size()));

        // Nombre de séances
        nombreSeancesLabel.setText(String.valueOf(seances.size()));

        // Détails statistiques
        StringBuilder details = new StringBuilder("═══ STATISTIQUES DÉTAILLÉES ═══\n\n");

        details.append("📊 FINANCES\n");
        details.append("────────────────────────────\n");
        details.append(String.format("Revenu total: %.2f DT\n", revenu));
        details.append(String.format("Dépenses totales: %.2f DT\n", depense));
        details.append(String.format("Bénéfice net: %.2f DT\n\n", benefice));

        details.append("👥 MEMBRES\n");
        details.append("────────────────────────────\n");
        details.append("Total membres: " + membres.size() + "\n");
        long abonnementsActifs = abonnements.values().stream()
                .filter(a -> a.getEtat().equals("actif")).count();
        details.append("Abonnements actifs: " + abonnementsActifs + "\n\n");

        details.append("🏋️ SÉANCES\n");
        details.append("────────────────────────────\n");
        details.append("Total séances: " + seances.size() + "\n");
        long collectives = seances.stream()
                .filter(s -> s instanceof SeanceCollective).count();
        long individuelles = seances.size() - collectives;
        details.append("Collectives: " + collectives + "\n");
        details.append("Individuelles: " + individuelles + "\n\n");

        details.append("💰 PAIEMENTS\n");
        details.append("────────────────────────────\n");
        details.append("Total paiements: " + paiements.size() + "\n");
        Map<String, Long> parType = new HashMap<>();
        for (Paiement p : paiements) {
            parType.put(p.getTypePaiement(), parType.getOrDefault(p.getTypePaiement(), 0L) + 1);
        }
        parType.forEach((type, count) ->
                details.append("  " + type + ": " + count + "\n"));

        details.append("\n🏢 INFRASTRUCTURE\n");
        details.append("────────────────────────────\n");
        details.append("Salles: " + salles.size() + "\n");
        details.append("Équipements: " + equipements.size() + "\n");
        long equipFonctionnels = equipements.stream()
                .filter(Equipement::verifierEtat).count();
        details.append("Équipements fonctionnels: " + equipFonctionnels + "\n");

        details.append("\n💼 PERSONNEL\n");
        details.append("────────────────────────────\n");
        details.append("Coachs: " + coachs.size() + "\n");
        details.append("Managers: " + managers.size() + "\n");
        details.append("Programmes créés: " + programmes.size());

        statsDetailsArea.setText(details.toString());
    }

    // ==================== MÉTHODES UTILITAIRES ====================

    private void rafraichirComboBoxes() {
        // Membres
        ObservableList<String> membresNoms = FXCollections.observableArrayList();
        for (Membre m : membres) {
            membresNoms.add(m.getNom() + " " + m.getPrenom());
        }
        if (membreAbonnementComboBox != null) membreAbonnementComboBox.setItems(membresNoms);
        if (membrePaiementComboBox != null) membrePaiementComboBox.setItems(membresNoms);
        if (membreSeanceComboBox != null) membreSeanceComboBox.setItems(membresNoms);
        if (membreReservationComboBox != null) membreReservationComboBox.setItems(membresNoms);

        // Coachs
        ObservableList<String> coachsNoms = FXCollections.observableArrayList();
        for (Coach c : coachs) {
            coachsNoms.add(c.getNom() + " " + c.getPrenom());
        }
        if (coachSeanceComboBox != null) coachSeanceComboBox.setItems(coachsNoms);
        if (coachProgrammeComboBox != null) coachProgrammeComboBox.setItems(coachsNoms);

        // Salles
        ObservableList<String> sallesNoms = FXCollections.observableArrayList();
        ObservableList<Integer> sallesIds = FXCollections.observableArrayList();
        for (Salle s : salles) {
            sallesNoms.add(s.getNomSalle());
            sallesIds.add(s.getIdSalle());
        }
        if (salleSeanceComboBox != null) salleSeanceComboBox.setItems(sallesNoms);
        if (equipSalleComboBox != null) equipSalleComboBox.setItems(sallesIds);

        // Programmes
        ObservableList<String> programmesInfo = FXCollections.observableArrayList();
        for (ProgrammeEntrainement p : programmes) {
            programmesInfo.add("ID:" + p.getIdProgramme() + " - " + p.getTitre());
        }
        if (programmeExerciceComboBox != null) programmeExerciceComboBox.setItems(programmesInfo);

        // Séances
        ObservableList<String> seancesInfo = FXCollections.observableArrayList();
        for (Seance s : seances) {
            String type = (s instanceof SeanceCollective) ? "Collective" : "Individuelle";
            seancesInfo.add("ID:" + s.getIdSeance() + " - " + type + " - " + sdf.format(s.getDate()));
        }
        if (seanceReservationComboBox != null) seanceReservationComboBox.setItems(seancesInfo);
    }

    private void rafraichirTableauAbonnements() {
        ObservableList<AbonnementDisplay> displayList = FXCollections.observableArrayList();

        for (Map.Entry<Integer, Abonnement> entry : abonnements.entrySet()) {
            Membre membre = trouverMembreParId(entry.getKey());
            Abonnement abo = entry.getValue();

            if (membre != null) {
                displayList.add(new AbonnementDisplay(
                        membre.getNom() + " " + membre.getPrenom(),
                        abo.getType(),
                        sdf.format(abo.getDateDebut()),
                        sdf.format(abo.getDateFin()),
                        abo.verifierValidite() ? "✅ Oui" : "❌ Non"
                ));
            }
        }

        abonnementsTable.setItems(displayList);
    }

    private void rafraichirTableauReservations() {
        ObservableList<ReservationDisplay> displayList = FXCollections.observableArrayList();

        for (Reservation r : reservations) {
            String type = (r.getSeance() instanceof SeanceCollective) ? "Collective" : "Individuelle";
            displayList.add(new ReservationDisplay(
                    r.getIdReservation(),
                    r.getMembre().getNom() + " " + r.getMembre().getPrenom(),
                    "Séance " + type + " - " + sdf.format(r.getSeance().getDate())
            ));
        }

        reservationsTable.setItems(displayList);
    }

    // Méthodes de recherche
    private Membre trouverMembreParId(int id) {
        return membres.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    private Membre trouverMembreParNom(String nomComplet) {
        return membres.stream()
                .filter(m -> (m.getNom() + " " + m.getPrenom()).equals(nomComplet))
                .findFirst().orElse(null);
    }

    private Coach trouverCoachParNom(String nomComplet) {
        return coachs.stream()
                .filter(c -> (c.getNom() + " " + c.getPrenom()).equals(nomComplet))
                .findFirst().orElse(null);
    }

    private Salle trouverSalleParId(int id) {
        return salles.stream().filter(s -> s.getIdSalle() == id).findFirst().orElse(null);
    }

    private Salle trouverSalleParNom(String nom) {
        return salles.stream().filter(s -> s.getNomSalle().equals(nom)).findFirst().orElse(null);
    }

    private Seance trouverSeanceParId(int id) {
        return seances.stream().filter(s -> s.getIdSeance() == id).findFirst().orElse(null);
    }

    private Reservation trouverReservationParId(int id) {
        return reservations.stream().filter(r -> r.getIdReservation() == id).findFirst().orElse(null);
    }

    // Clear fields
    private void clearMembreFields() {
        membreNomField.clear();
        membrePrenomField.clear();
        membreEmailField.clear();
        membreTelField.clear();
    }

    private void clearCoachFields() {
        coachNomField.clear();
        coachPrenomField.clear();
        coachEmailField.clear();
        coachTelField.clear();
        coachSpecialiteField.clear();
        coachTarifField.clear();
    }

    private void clearManagerFields() {
        managerNomField.clear();
        managerPrenomField.clear();
        managerEmailField.clear();
        managerTelField.clear();
        managerLoginField.clear();
        managerPasswordField.clear();
    }

    private void showMessage(String message) {
        if (messageLabel != null) {
            messageLabel.setText(message);

            // Déterminer la couleur basée sur le message
            if (message.startsWith("✅")) {
                messageLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else if (message.startsWith("❌")) {
                messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                messageLabel.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
            }
        }
    }

    // ==================== DÉCONNEXION ====================

    /**
     * Gère la déconnexion de l'utilisateur
     */
    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Déconnexion");
        alert.setContentText("Voulez-vous vraiment vous déconnecter?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            MainApp.logout();
        }
    }

    // ==================== CLASSES INTERNES POUR AFFICHAGE ====================

    public static class AbonnementDisplay {
        private final String membreNom;
        private final String type;
        private final String dateDebut;
        private final String dateFin;
        private final String valide;

        public AbonnementDisplay(String membreNom, String type, String dateDebut,
                                 String dateFin, String valide) {
            this.membreNom = membreNom;
            this.type = type;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.valide = valide;
        }

        public String getMembreNom() { return membreNom; }
        public String getType() { return type; }
        public String getDateDebut() { return dateDebut; }
        public String getDateFin() { return dateFin; }
        public String getValide() { return valide; }
    }

    public static class SeanceDisplay {
        private final int idSeance;
        private final String type;
        private final String dateStr;

        public SeanceDisplay(int idSeance, String type, String dateStr) {
            this.idSeance = idSeance;
            this.type = type;
            this.dateStr = dateStr;
        }

        public int getIdSeance() { return idSeance; }
        public String getType() { return type; }
        public String getDateStr() { return dateStr; }
    }

    public static class ReservationDisplay extends Reservation {
        private final int idReservation;
        private final String membreNom;
        private final String seanceInfo;

        public ReservationDisplay(int idReservation, String membreNom, String seanceInfo) {
            super();
            this.idReservation = idReservation;
            this.membreNom = membreNom;
            this.seanceInfo = seanceInfo;
        }

        public int getIdReservation() { return idReservation; }
        public String getMembreNom() { return membreNom; }
        public String getSeanceInfo() { return seanceInfo; }
    }


}