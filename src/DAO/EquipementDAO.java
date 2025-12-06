package DAO;

import Database.DatabaseConnection;
import Models.amen.Infrastructure.Equipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipementDAO {

    /**
     * Add a new equipement to the database
     * @param equipement The equipement to add
     * @return true if successful, false otherwise
     */
    public boolean ajouterEquipement(Equipement equipement) {
        String sql = "INSERT INTO equipement (nom, etat) VALUES (?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, equipement.nom());
            pst.setString(2, equipement.etat());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                System.out.println("✅ Équipement ajouté avec ID: " + generatedId);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout de l'équipement");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get all equipements from database
     * @return List of all equipements
     */
    public List<Equipement> getAllEquipements() {
        List<Equipement> equipements = new ArrayList<>();
        String sql = "SELECT * FROM equipement ORDER BY nom";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Equipement equip = new Equipement(
                        rs.getInt("id_equipement"),
                        rs.getString("nom"),
                        rs.getString("etat")
                );
                equipements.add(equip);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des équipements");
            e.printStackTrace();
        }
        return equipements;
    }

    /**
     * Get all equipements as ObservableList for JavaFX TableView
     * @return ObservableList of equipements
     */
    public ObservableList<Equipement> getAllEquipementsObservable() {
        return FXCollections.observableArrayList(getAllEquipements());
    }

    /**
     * Get equipement by ID
     * @param id The equipement ID
     * @return Equipement object or null if not found
     */
    public Equipement getEquipementById(int id) {
        String sql = "SELECT * FROM equipement WHERE id_equipement = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Equipement(
                        rs.getInt("id_equipement"),
                        rs.getString("nom"),
                        rs.getString("etat")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération de l'équipement");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update equipement information
     * @param id The equipement ID
     * @param nom New name
     * @param etat New state
     * @return true if successful, false otherwise
     */
    public boolean modifierEquipement(int id, String nom, String etat) {
        String sql = "UPDATE equipement SET nom = ?, etat = ? WHERE id_equipement = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setString(2, etat);
            pst.setInt(3, id);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Équipement modifié avec succès");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification de l'équipement");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete equipement from database
     * @param id The equipement ID to delete
     * @return true if successful, false otherwise
     */
    public boolean supprimerEquipement(int id) {
        String sql = "DELETE FROM equipement WHERE id_equipement = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Équipement supprimé avec succès");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression de l'équipement");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get only functional equipements (état = 'fonctionnel')
     * @return List of functional equipements
     */
    public List<Equipement> getEquipementsFonctionnels() {
        List<Equipement> equipements = new ArrayList<>();
        String sql = "SELECT * FROM equipement WHERE etat = 'fonctionnel' ORDER BY nom";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Equipement equip = new Equipement(
                        rs.getInt("id_equipement"),
                        rs.getString("nom"),
                        rs.getString("etat")
                );
                equipements.add(equip);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des équipements fonctionnels");
            e.printStackTrace();
        }
        return equipements;
    }

    /**
     * Get equipements by state
     * @param etat The state to filter by (fonctionnel, en réparation, hors service)
     * @return List of equipements with specified state
     */
    public List<Equipement> getEquipementsByEtat(String etat) {
        List<Equipement> equipements = new ArrayList<>();
        String sql = "SELECT * FROM equipement WHERE etat = ? ORDER BY nom";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, etat);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Equipement equip = new Equipement(
                        rs.getInt("id_equipement"),
                        rs.getString("nom"),
                        rs.getString("etat")
                );
                equipements.add(equip);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des équipements par état");
            e.printStackTrace();
        }
        return equipements;
    }

    /**
     * Search equipements by name
     * @param searchText Text to search in equipment name
     * @return List of matching equipements
     */
    public List<Equipement> rechercherEquipements(String searchText) {
        List<Equipement> equipements = new ArrayList<>();
        String sql = "SELECT * FROM equipement WHERE nom LIKE ? ORDER BY nom";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + searchText + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Equipement equip = new Equipement(
                        rs.getInt("id_equipement"),
                        rs.getString("nom"),
                        rs.getString("etat")
                );
                equipements.add(equip);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la recherche d'équipements");
            e.printStackTrace();
        }
        return equipements;
    }

    /**
     * Get total count of equipements
     * @return Total number of equipements
     */
    public int getTotalEquipements() {
        String sql = "SELECT COUNT(*) as total FROM equipement";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du comptage des équipements");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get count of equipements by state
     * @param etat The state to count
     * @return Count of equipements with specified state
     */
    public int getCountByEtat(String etat) {
        String sql = "SELECT COUNT(*) as total FROM equipement WHERE etat = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, etat);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du comptage des équipements par état");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Update equipment state only
     * @param id Equipment ID
     * @param nouvelEtat New state
     * @return true if successful
     */
    public boolean changerEtat(int id, String nouvelEtat) {
        String sql = "UPDATE equipement SET etat = ? WHERE id_equipement = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nouvelEtat);
            pst.setInt(2, id);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ État de l'équipement changé à: " + nouvelEtat);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du changement d'état");
            e.printStackTrace();
        }
        return false;
    }
}