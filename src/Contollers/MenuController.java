package Contollers;

import DAO.*;
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
import Database.DatabaseConnection;



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
    @FXML private TableView<Seance> seancesTable;
    @FXML private TableColumn<Seance, Integer> seanceIdCol;
    @FXML private TableColumn<Seance, String> seanceTypeCol;
    @FXML private TableColumn<Seance, String> seanceDateCol;

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

    // ==================== ADD THESE DAO INSTANCES ====================
    private MembreDAO membreDAO;
    private CoachDAO coachDAO;
    private ManagerDAO managerDAO;
    private SalleDAO salleDAO;
    private EquipementDAO equipementDAO;
    private AbonnementDAO abonnementDAO;
    private PaiementDAO paiementDAO;
    private SeanceDAO seanceDAO;
    private ReservationDAO reservationDAO;

    // ==================== DONNÉES ====================
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Personnel
    private ObservableList<Membre> membres ;
    private ObservableList<Coach> coachs ;
    private ObservableList<Manager> managers ;

    // Infrastructure
    private ObservableList<Salle> salles ;
    private ObservableList<Equipement> equipements ;

    // Gestion
    private Map<Integer, Abonnement> abonnements ;
    private final ObservableList<Paiement> paiements = FXCollections.observableArrayList();
    private ObservableList<Seance> seances = FXCollections.observableArrayList();
    private final ObservableList<ProgrammeEntrainement> programmes = FXCollections.observableArrayList();
    private final ProgrammeEntrainementDAO programmeDAO = new ProgrammeEntrainementDAO();
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
        // ==================== STEP 1: TEST DATABASE CONNECTION ====================
        System.out.println("=== Initialisation du système ===");
        DatabaseConnection.testConnection();

        // ==================== STEP 2: INITIALIZE ALL DAOs ====================
        System.out.println("Initialisation des DAOs...");
        membreDAO = new MembreDAO();
        coachDAO = new CoachDAO();
        managerDAO = new ManagerDAO();
        salleDAO = new SalleDAO();
        equipementDAO = new EquipementDAO();
        abonnementDAO = new AbonnementDAO();
        paiementDAO = new PaiementDAO();
        seanceDAO = new SeanceDAO();
        reservationDAO = new ReservationDAO();
        // ==================== STEP 3: INITIALIZE ALL TABS FIRST ====================
        System.out.println("Initialisation des onglets...");
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

        // ==================== STEP 4: LOAD ALL DATA FROM DATABASE ====================
        System.out.println("Chargement des données depuis la base de données...");
        loadAllDataFromDatabase();

        // ==================== STEP 5: DISPLAY ALL DATA IN TABLES ====================
        System.out.println("Affichage des données dans les tableaux...");
        afficherToutesLesDonnees();

        // ==================== STEP 6: REFRESH COMBO BOXES ====================
        rafraichirComboBoxes();

        // ==================== STEP 7: SHOW SUCCESS MESSAGE ====================
        showMessage("✅ Système chargé avec succès depuis la base de données!");
        System.out.println("=== Initialisation terminée ===");
    }

    private void loadAllDataFromDatabase() {
        try {
            System.out.println("📥 Chargement des membres...");
            List<Membre> membresList = membreDAO.getAllMembres();
            membres = FXCollections.observableArrayList(membresList);
            System.out.println("✅ " + membres.size() + " membres chargés");

            System.out.println("📥 Chargement des coachs...");
            List<Coach> coachsList = coachDAO.getAllCoachs();
            coachs = FXCollections.observableArrayList(coachsList);
            System.out.println("✅ " + coachs.size() + " coachs chargés");

            System.out.println("📥 Chargement des managers...");
            List<Manager> managersList = managerDAO.getAllManagers();
            managers = FXCollections.observableArrayList(managersList);
            System.out.println("✅ " + managers.size() + " managers chargés");

            System.out.println("📥 Chargement des salles...");
            List<Salle> sallesList = salleDAO.getAllSalles();
            salles = FXCollections.observableArrayList(sallesList);
            System.out.println("✅ " + salles.size() + " salles chargées");

            System.out.println("📥 Chargement des équipements...");
            List<Equipement> equipementsList = equipementDAO.getAllEquipements();
            equipements = FXCollections.observableArrayList(equipementsList);
            System.out.println("✅ " + equipements.size() + " équipements chargés");

            System.out.println("Chargement des paiements...");
            List<Paiement> paiementsList = paiementDAO.getAllPaiements();
            paiements.clear();                    // On vide d'abord
            paiements.addAll(paiementsList);      // On remplit avec les nouveaux
            System.out.println("Paiements chargés : " + paiements.size());

            for (Paiement p : paiements) {
                System.out.println("   📄 Paiement ID:" + p.getIdPaiement() +
                        " - Montant:" + p.getMontant() +
                        " - Membre:" + p.getMembre().getNom());
            }

            System.out.println("Chargement des séances...");
            seances.setAll(new SeanceDAO().getAllSeances());
            System.out.println(seances.size() + " séances chargées depuis la base");

            System.out.println("📥 Chargement des abonnements...");
            abonnements = abonnementDAO.getAllAbonnements();
            System.out.println("✅ " + abonnements.size() + " abonnements chargés");

            System.out.println("Chargement des programmes d'entraînement...");
            programmes.setAll(programmeDAO.getAllProgrammes());
            System.out.println("Programmes chargés : " + programmes.size());

            System.out.println("📥 Chargement des réservations...");
            List<Reservation> reservationsList = reservationDAO.getAllReservations();
            reservations.clear();
            reservations.addAll(reservationsList);
            System.out.println("✅ " + reservations.size() + " réservations chargées");

            System.out.println("Toutes les données chargées avec succès !");

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement des données");
            e.printStackTrace();
            showMessage("❌ Erreur lors du chargement des données: " + e.getMessage());
        }
    }

    private void afficherToutesLesDonnees() {
        try {
            System.out.println("🖥️ Affichage des données dans les tableaux...");

            if (membresTable != null && membres != null) {
                membresTable.setItems(membres);
                membresTable.refresh();
                System.out.println("✅ Tableau membres mis à jour (" + membres.size() + " entrées)");
            }

            if (coachsTable != null && coachs != null) {
                coachsTable.setItems(coachs);
                coachsTable.refresh();
                System.out.println("✅ Tableau coachs mis à jour (" + coachs.size() + " entrées)");
            }

            if (managersTable != null && managers != null) {
                managersTable.setItems(managers);
                managersTable.refresh();
                System.out.println("✅ Tableau managers mis à jour (" + managers.size() + " entrées)");
            }

            if (sallesTable != null && salles != null) {
                sallesTable.setItems(salles);
                sallesTable.refresh();
                System.out.println("✅ Tableau salles mis à jour (" + salles.size() + " entrées)");
            }

            if (equipementsTable != null && equipements != null) {
                equipementsTable.setItems(equipements);
                equipementsTable.refresh();
                System.out.println("✅ Tableau équipements mis à jour (" + equipements.size() + " entrées)");
            }

            if (seancesTable != null && seances != null) {
                System.out.println("📊 Affichage des séances...");
                seancesTable.setItems(seances);
                seancesTable.refresh();
                System.out.println("✅ Tableau séances mis à jour (" + seances.size() + " entrées)");
            }

            if (paiementsTable != null && paiements != null) {
                System.out.println("📊 Affichage des paiements...");
                paiementsTable.setItems(paiements);
                paiementsTable.refresh();
                System.out.println("✅ Tableau paiements mis à jour (" + paiements.size() + " entrées)");
            }

            if (programmesTable != null && programmes != null) {
                System.out.println("\n📊 === AFFICHAGE PROGRAMMES ===");
                System.out.println("Nombre de programmes: " + programmes.size());

                for (ProgrammeEntrainement p : programmes) {
                    System.out.println("  📄 ID:" + p.getIdProgramme() +
                            " - Titre:" + p.getTitre() +
                            " - Coach:" + (p.getCoach() != null ? p.getCoach().getNom() : "NULL"));
                }

                programmesTable.setItems(programmes);
                programmesTable.refresh();
                System.out.println("✅ Tableau programmes mis à jour");
            }


            rafraichirTableauAbonnements();
            System.out.println("✅ Tableau abonnements mis à jour (" + abonnements.size() + " entrées)");

            calculerStatistiques();
            System.out.println("✅ Statistiques calculées");

            System.out.println("✅ Tous les tableaux mis à jour avec succès!");

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'affichage des données: " + e.getMessage());
            e.printStackTrace();
        }
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
        typeSeanceComboBox.setItems(FXCollections.observableArrayList("Collective", "Individuelle"));

        seanceIdCol.setCellValueFactory(new PropertyValueFactory<>("idSeance"));
        seanceTypeCol.setCellValueFactory(cellData -> {
            Seance s = cellData.getValue();
            String type = (s instanceof SeanceCollective) ? "Collective" : "Individuelle";
            return new SimpleStringProperty(type);
        });
        seanceDateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(sdf.format(cellData.getValue().getDate()))
        );

        seancesTable.setItems(seances);}


    private void initializeProgrammesTab() {
        System.out.println("\n🔧 === INITIALISATION TAB PROGRAMMES ===");

        if (programmesTable == null) {
            System.err.println("❌ programmesTable est NULL !");
            return;
        }

        System.out.println("✅ programmesTable existe");

        progIdCol.setCellValueFactory(new PropertyValueFactory<>("idProgramme"));
        progTitreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));

        System.out.println("✅ Colonnes configurées");

        programmesTable.setItems(programmes);

        System.out.println("✅ Table liée (taille actuelle: " + programmes.size() + ")");
    }

    private void initializeReservationsTab() {
        resIdCol.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        resMembreCol.setCellValueFactory(new PropertyValueFactory<>("membreNom"));
        resSeanceCol.setCellValueFactory(new PropertyValueFactory<>("seanceInfo"));
    }

    private void initializeStatistiquesTab() {
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

            Membre membre = new Membre(0, nom, prenom, email, tel);

            if (membreDAO.ajouterMembre(membre)) {
                membres.setAll(membreDAO.getAllMembres());
                membresTable.refresh();  // <-- AJOUTEZ CETTE LIGNE
                showMessage("✅ Membre ajouté avec succès! ID: " + membre.getId());
                clearMembreFields();
                rafraichirComboBoxes();
            } else {
                showMessage("❌ Erreur lors de l'ajout du membre à la base de données");
            }

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
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
        alert.setContentText("Voulez-vous vraiment supprimer " + selected.getNom() + " " + selected.getPrenom() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (membreDAO.supprimerMembre(selected.getId())) {
                membres.remove(selected);
                showMessage("✅ Membre supprimé de la base de données");
                rafraichirComboBoxes();
            } else {
                showMessage("❌ Erreur lors de la suppression du membre");
            }
        }
    }

    @FXML
    private void handleAfficherMembres() {
        membres.setAll(membreDAO.getAllMembres());
        membresTable.setItems(membres);
        showMessage("📋 Liste des membres rechargée depuis la base de données (" + membres.size() + " membres)");
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
            Coach coach = new Coach(0, nom, prenom, email, tel, specialite, tarif);

            if (coachDAO.ajouterCoach(coach)) {
                coachs.setAll(coachDAO.getAllCoachs());
                coachsTable.refresh();  // <-- AJOUTEZ CETTE LIGNE
                showMessage("✅ Coach ajouté avec succès! ID: " + coach.getId());
                clearCoachFields();
                rafraichirComboBoxes();
            } else {
                showMessage("❌ Erreur lors de l'ajout du coach");
            }

        } catch (NumberFormatException e) {
            showMessage("❌ Tarif invalide");
        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSupprimerCoach() {
        Coach selected = coachsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez un coach à supprimer");
            return;
        }

        if (coachDAO.supprimerCoach(selected.getId())) {
            coachs.remove(selected);
            showMessage("✅ Coach supprimé de la base de données");
            rafraichirComboBoxes();
        } else {
            showMessage("❌ Erreur lors de la suppression du coach");
        }
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

            Manager manager = new Manager(0, nom, prenom, email, tel, login, password);

            if (managerDAO.ajouterManager(manager)) {
                managers.setAll(managerDAO.getAllManagers());
                managersTable.refresh();  // <-- AJOUTEZ CETTE LIGNE
                showMessage("✅ Manager ajouté avec succès! ID: " + manager.getId());
                clearManagerFields();
            } else {
                showMessage("❌ Erreur lors de l'ajout du manager");
            }

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSupprimerManager() {
        Manager selected = managersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez un manager à supprimer");
            return;
        }

        if (managerDAO.supprimerManager(selected.getId())) {
            managers.remove(selected);
            showMessage("✅ Manager supprimé de la base de données");
        } else {
            showMessage("❌ Erreur lors de la suppression du manager");
        }
    }

    // ==================== SALLES ====================

    @FXML
    private void handleCreerSalle() {
        System.out.println("\n========== CRÉATION SALLE ==========");

        try {
            String nom = nomSalleField.getText().trim();
            String capaciteStr = capaciteSalleField.getText().trim();

            if (nom.isEmpty() || capaciteStr.isEmpty()) {
                showMessage("❌ Veuillez remplir tous les champs");
                System.err.println("❌ Champs vides");
                return;
            }

            int capacite = Integer.parseInt(capaciteStr);
            System.out.println("📝 Création salle: " + nom + " (capacité: " + capacite + ")");

            Salle salle = new Salle(0, nom, capacite);

            System.out.println("💾 Tentative d'ajout en BDD...");
            boolean ajoutReussi = salleDAO.ajouterSalle(salle);

            if (ajoutReussi) {
                System.out.println("✅ Ajout réussi! ID généré: " + salle.getIdSalle());

                // Recharger TOUTES les salles depuis la base de données
                System.out.println("🔄 Rechargement de toutes les salles...");
                List<Salle> sallesList = salleDAO.getAllSalles();

                // Vider et remplir la liste observable
                salles.clear();
                salles.addAll(sallesList);

                // Forcer le rafraîchissement de la table
                sallesTable.setItems(salles);
                sallesTable.refresh();

                System.out.println("✅ Table mise à jour avec " + salles.size() + " salle(s)");

                showMessage("✅ Salle créée avec succès! ID: " + salle.getIdSalle());

                nomSalleField.clear();
                capaciteSalleField.clear();

                rafraichirComboBoxes();

            } else {
                System.err.println("❌ Échec de l'ajout en BDD");
                showMessage("❌ Erreur lors de la création de la salle");
            }

        } catch (NumberFormatException e) {
            System.err.println("❌ Capacité invalide: " + e.getMessage());
            showMessage("❌ Capacité invalide");
        } catch (Exception e) {
            System.err.println("❌ Erreur inattendue: " + e.getMessage());
            showMessage("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("========== FIN CRÉATION SALLE ==========\n");
    }

    @FXML
    private void handleSupprimerSalle() {
        Salle selected = sallesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez une salle à supprimer");
            return;
        }

        // OLD: salles.remove(selected);
        // NEW: Delete from database
        if (salleDAO.supprimerSalle(selected.getIdSalle())) {
            salles.remove(selected);
            showMessage("✅ Salle supprimée de la base de données");
            rafraichirComboBoxes();
        } else {
            showMessage("❌ Erreur lors de la suppression de la salle");
        }
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

            Equipement equip = new Equipement(0, nom, etat);

            // OLD: equipements.add(equip);
            // NEW: Save to database
            if (equipementDAO.ajouterEquipement(equip)) {
                equipements.setAll(equipementDAO.getAllEquipements());
                equipementsTable.refresh();  // <-- AJOUTEZ CETTE LIGNE
                showMessage("✅ Équipement ajouté avec succès!");
                nomEquipField.clear();
                rafraichirComboBoxes();
            } else {
                showMessage("❌ Erreur lors de l'ajout de l'équipement");
            }

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

        if (equipementDAO.supprimerEquipement(selected.idEquipement())) {
            equipements.remove(selected);
            showMessage("✅ Équipement supprimé de la base de données");
        } else {
            showMessage("❌ Erreur lors de la suppression de l'équipement");
        }
    }

    @FXML
    private void handleAfficherEquipements() {
        // Reload from database
        equipements.setAll(equipementDAO.getAllEquipements());
        equipementsTable.setItems(equipements);
        showMessage("📋 Liste des équipements rechargée (" + equipements.size() + " équipements)");
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
            abo.activer(); // Activate by default

            // OLD: abonnements.put(membre.getId(), abo);
            // NEW: Save to database
            if (abonnementDAO.ajouterAbonnement(membre.getId(), abo)) {
                abonnements.put(membre.getId(), abo);
                showMessage("✅ Abonnement créé pour " + membre.getNom());
                rafraichirTableauAbonnements();
            } else {
                showMessage("❌ Erreur lors de la création de l'abonnement");
            }

        } catch (Exception e) {
            showMessage("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAfficherAbonnements() {
        // Reload from database
        abonnements = abonnementDAO.getAllAbonnements();
        rafraichirTableauAbonnements();
        showMessage("✅ Liste des abonnements rechargée (" + abonnements.size() + " abonnements)");
    }

    // ==================== PAIEMENTS ====================

    // ==================== MÉTHODE CORRIGÉE POUR LES PAIEMENTS ====================

    @FXML
    private void handleEffectuerPaiement() {
        try {
            System.out.println("\n💰 === AJOUT PAIEMENT ===");

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

            System.out.println("📝 Création paiement pour : " + membre.getNom());
            System.out.println("   Type: " + type + ", Montant: " + montant);

            Paiement paiement = new Paiement(0, type, montant, new Date(), membre);

            // Save to database
            System.out.println("💾 Tentative d'ajout en base...");
            if (paiementDAO.ajouterPaiement(paiement)) {
                System.out.println("✅ Paiement ajouté à la BD avec ID: " + paiement.getIdPaiement());

                // MÉTHODE 1 : Recharger TOUS les paiements (recommandé)
                List<Paiement> paiementsList = paiementDAO.getAllPaiements();
                paiements.clear();
                paiements.addAll(paiementsList);

                // MÉTHODE 2 ALTERNATIVE : Ajouter directement (plus rapide mais moins sûr)
                // paiements.add(paiement);

                // Forcer le rafraîchissement complet
                paiementsTable.setItems(paiements);
                paiementsTable.refresh();

                System.out.println("✅ Table paiements mise à jour avec " + paiements.size() + " paiement(s)");
                System.out.println("📊 Contenu table : " + paiementsTable.getItems().size() + " éléments");

                // Update membre's payment history
                membre.effectuerPaiement(paiement);

                showMessage("✅ Paiement effectué avec succès! ID: " + paiement.getIdPaiement());

                // Clear fields
                montantPaiementField.clear();
                typePaiementComboBox.setValue(null);
                membrePaiementComboBox.setValue(null);

                // Recalculate statistics
                calculerStatistiques();

            } else {
                System.err.println("❌ Échec de l'ajout en base");
                showMessage("❌ Erreur lors de l'enregistrement du paiement");
            }

        } catch (NumberFormatException e) {
            System.err.println("❌ Montant invalide: " + e.getMessage());
            showMessage("❌ Montant invalide");
        } catch (Exception e) {
            System.err.println("❌ Erreur inattendue: " + e.getMessage());
            showMessage("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== FIN AJOUT PAIEMENT ===\n");
    }

// ==================== MÉTHODE POUR AFFICHER LES PAIEMENTS ====================

    @FXML
    private void handleAfficherPaiements() {
        System.out.println("\n📥 === RECHARGEMENT PAIEMENTS ===");

        try {
            // Reload from database
            List<Paiement> paiementsList = paiementDAO.getAllPaiements();

            // Clear and refill observable list
            paiements.clear();
            paiements.addAll(paiementsList);

            // Refresh table
            paiementsTable.setItems(paiements);
            paiementsTable.refresh();

            System.out.println("✅ " + paiements.size() + " paiement(s) chargé(s)");
            showMessage("📋 Liste des paiements rechargée (" + paiements.size() + " paiements)");

            // Optionally recalculate statistics
            calculerStatistiques();

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du rechargement des paiements");
            e.printStackTrace();
            showMessage("❌ Erreur lors du rechargement: " + e.getMessage());
        }
    }

// ==================== MÉTHODE POUR SUPPRIMER UN PAIEMENT ====================

    @FXML
    private void handleSupprimerPaiement() {
        Paiement selected = paiementsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez un paiement à supprimer");
            return;
        }

        // Confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le paiement");
        alert.setContentText("Voulez-vous vraiment supprimer ce paiement de " +
                selected.getMontant() + " DT pour " +
                selected.getMembre().getNom() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (paiementDAO.supprimerPaiement(selected.getIdPaiement())) {
                // Reload all payments
                List<Paiement> paiementsList = paiementDAO.getAllPaiements();
                paiements.clear();
                paiements.addAll(paiementsList);

                // Refresh table
                paiementsTable.setItems(paiements);
                paiementsTable.refresh();

                showMessage("✅ Paiement supprimé de la base de données");
                calculerStatistiques();
            } else {
                showMessage("❌ Erreur lors de la suppression du paiement");
            }
        }
    }
    // ==================== SÉANCES ====================

    @FXML
    private void handleCreerSeance() {
        System.out.println("\n🏋️ === CRÉATION SÉANCE ===");

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

            Salle salle = trouverSalleParNom(salleSeanceComboBox.getValue());
            if (salle == null) {
                showMessage("❌ Sélectionnez une salle");
                return;
            }

            System.out.println("📝 Type: " + type + ", Durée: " + duree + ", Coût: " + cout);
            System.out.println("🏢 Salle: " + salle.getNomSalle());

            boolean success = false;

            if ("Collective".equals(type)) {
                Coach coach = trouverCoachParNom(coachSeanceComboBox.getValue());
                String capaciteStr = capaciteSeanceField.getText().trim();

                if (coach == null) {
                    showMessage("❌ Sélectionnez un coach");
                    return;
                }

                if (capaciteStr.isEmpty()) {
                    showMessage("❌ Indiquez la capacité");
                    return;
                }

                int capacite = Integer.parseInt(capaciteStr);
                System.out.println("👤 Coach: " + coach.getNom() + ", Capacité: " + capacite);

                SeanceCollective sc = new SeanceCollective(0, duree, date, salle, cout, coach, capacite);
                success = seanceDAO.ajouterSeanceCollective(sc);

            } else {
                Membre membre = trouverMembreParNom(membreSeanceComboBox.getValue());

                if (membre == null) {
                    showMessage("❌ Sélectionnez un membre");
                    return;
                }

                System.out.println("👤 Membre: " + membre.getNom());

                SeanceIndividuelle si = new SeanceIndividuelle(0, duree, date, salle, cout, membre);
                success = seanceDAO.ajouterSeanceIndividuelle(si);
            }

            if (success) {
                System.out.println("✅ Séance ajoutée en base");

                // RECHARGER TOUTES LES SÉANCES DEPUIS LA BASE
                List<Seance> seancesList = seanceDAO.getAllSeances();
                seances.clear();
                seances.addAll(seancesList);

                // Forcer le rafraîchissement
                seancesTable.setItems(seances);
                seancesTable.refresh();

                System.out.println("✅ Table mise à jour avec " + seances.size() + " séance(s)");

                showMessage("✅ Séance ajoutée avec succès!");

                // Clear fields
                dateSeanceField.clear();
                dureeSeanceField.clear();
                coutSeanceField.clear();
                capaciteSeanceField.clear();
                typeSeanceComboBox.setValue(null);
                coachSeanceComboBox.setValue(null);
                membreSeanceComboBox.setValue(null);
                salleSeanceComboBox.setValue(null);

                rafraichirComboBoxes();

            } else {
                System.err.println("❌ Échec de l'ajout en base");
                showMessage("❌ Échec de l'ajout en base");
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            showMessage("❌ Erreur : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== FIN CRÉATION SÉANCE ===\n");
    }

    @FXML
    private void handleAfficherSeances() {
        System.out.println("\n🔄 === RECHARGEMENT MANUEL SÉANCES ===");

        try {
            // Recharger depuis la base
            List<Seance> seancesList = seanceDAO.getAllSeances();
            System.out.println("📥 " + seancesList.size() + " séances chargées depuis la BD");

            // Vider et remplir
            seances.clear();
            seances.addAll(seancesList);

            // Forcer le refresh
            seancesTable.setItems(seances);
            seancesTable.refresh();

            System.out.println("✅ Table mise à jour");
            showMessage("📋 " + seances.size() + " séances affichées");

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleSupprimerSeance() {
        Seance selected = seancesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showMessage("❌ Sélectionnez une séance à supprimer");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer la séance");
        alert.setContentText("Voulez-vous vraiment supprimer cette séance?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (seanceDAO.supprimerSeance(selected.getIdSeance())) {
                // Recharger toutes les séances
                List<Seance> seancesList = seanceDAO.getAllSeances();
                seances.clear();
                seances.addAll(seancesList);

                seancesTable.setItems(seances);
                seancesTable.refresh();

                showMessage("✅ Séance supprimée de la base de données");
                rafraichirComboBoxes();
            } else {
                showMessage("❌ Erreur lors de la suppression de la séance");
            }
        }
    }
    // ==================== PROGRAMMES ====================

    @FXML
    private void handleCreerProgramme() {
        System.out.println("\n📋 === CRÉATION PROGRAMME ===");

        try {
            String titre = titreProgrammeField.getText().trim();
            Coach coach = trouverCoachParNom(coachProgrammeComboBox.getValue());

            if (titre.isEmpty() || coach == null) {
                showMessage("❌ Titre et coach requis");
                return;
            }

            System.out.println("📝 Titre: " + titre);
            System.out.println("👤 Coach: " + coach.getNom());

            ProgrammeEntrainement prog = new ProgrammeEntrainement(0, titre, coach);

            // Tenter l'ajout
            System.out.println("💾 Tentative d'ajout en base...");
            if (programmeDAO.ajouterProgramme(prog)) {
                System.out.println("✅ Programme ajouté avec ID: " + prog.getIdProgramme());

                // RECHARGER TOUS LES PROGRAMMES DEPUIS LA BASE
                List<ProgrammeEntrainement> programmesList = programmeDAO.getAllProgrammes();
                programmes.clear();
                programmes.addAll(programmesList);

                // Forcer le rafraîchissement
                programmesTable.setItems(programmes);
                programmesTable.refresh();

                System.out.println("✅ Table mise à jour avec " + programmes.size() + " programme(s)");

                showMessage("✅ Programme ajouté avec succès ! ID: " + prog.getIdProgramme());
                titreProgrammeField.clear();
                coachProgrammeComboBox.setValue(null);
                rafraichirComboBoxes();
            } else {
                System.err.println("❌ Échec de l'ajout en base");
                showMessage("❌ Échec de l'ajout en base");
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            showMessage("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== FIN CRÉATION PROGRAMME ===\n");
    }
    @FXML
    private void handleAfficherProgrammes() {
        System.out.println("\n🔄 === RECHARGEMENT MANUEL PROGRAMMES ===");

        try {
            // Recharger depuis la base
            List<ProgrammeEntrainement> programmesList = programmeDAO.getAllProgrammes();
            System.out.println("📥 " + programmesList.size() + " programmes chargés depuis la BD");

            // Vider et remplir
            programmes.clear();
            programmes.addAll(programmesList);

            // Forcer le refresh
            programmesTable.setItems(programmes);
            programmesTable.refresh();

            System.out.println("✅ Table mise à jour");
            showMessage("📋 " + programmes.size() + " programmes affichés");

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
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
        System.out.println("\n📝 === CRÉATION RÉSERVATION ===");

        try {
            String membreNom = membreReservationComboBox.getValue();
            String seanceInfo = seanceReservationComboBox.getValue();

            if (membreNom == null || seanceInfo == null) {
                showMessage("❌ Sélectionnez un membre et une séance");
                return;
            }

            Membre membre = trouverMembreParNom(membreNom);
            int seanceId = Integer.parseInt(seanceInfo.split(" - ")[0].replace("ID:", "").trim());
            Seance seance = trouverSeanceParId(seanceId);

            if (membre == null || seance == null) {
                showMessage("❌ Membre ou séance introuvable");
                return;
            }

            System.out.println("✅ Membre: " + membre.getNom() + " (ID:" + membre.getId() + ")");
            System.out.println("✅ Séance: ID " + seance.getIdSeance());

            Reservation res = new Reservation(0, membre, seance, new Date());

            System.out.println("💾 Ajout en base...");
            if (reservationDAO.ajouterReservation(res)) {
                System.out.println("✅ Ajout réussi!");

                // Attendre un peu pour être sûr que l'insertion est terminée
                Thread.sleep(100);

                // Recharger
                System.out.println("🔄 Rechargement des réservations...");
                List<Reservation> reservationsList = reservationDAO.getAllReservations();
                reservations.clear();
                reservations.addAll(reservationsList);

                rafraichirTableauReservations();

                showMessage("✅ Réservation créée! Total: " + reservations.size());

                membreReservationComboBox.setValue(null);
                seanceReservationComboBox.setValue(null);

            } else {
                showMessage("❌ Échec de l'ajout");
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
            showMessage("❌ Erreur: " + e.getMessage());
        }
    }

    @FXML
    private void handleAfficherReservations() {
        System.out.println("\n🔄 === RECHARGEMENT RÉSERVATIONS ===");

        try {
            // Recharger depuis la base
            List<Reservation> reservationsList = reservationDAO.getAllReservations();
            System.out.println("📥 " + reservationsList.size() + " réservations chargées depuis la BD");

            // Vider et remplir
            reservations.clear();
            reservations.addAll(reservationsList);

            // Forcer le refresh
            rafraichirTableauReservations();

            System.out.println("✅ Table mise à jour");
            showMessage("📋 " + reservations.size() + " réservations affichées");

        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
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
            // Close database connection before logout
            DatabaseConnection.closeConnection();
            System.out.println("=== Déconnexion et fermeture de la base de données ===");
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