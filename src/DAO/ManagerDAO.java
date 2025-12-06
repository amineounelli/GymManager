package DAO;

import Database.DatabaseConnection;
import Models.amine.Personnel.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {

    // Add manager
    public boolean ajouterManager(Manager manager) {
        Connection conn = DatabaseConnection.getConnection();
        String sqlPersonne = "INSERT INTO personne (nom, prenom, email, telephone, type_personne) VALUES (?, ?, ?, ?, 'MANAGER')";
        String sqlManager = "INSERT INTO manager (id, login, mot_de_passe) VALUES (?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            // Insert into personne
            PreparedStatement pstPersonne = conn.prepareStatement(sqlPersonne, Statement.RETURN_GENERATED_KEYS);
            pstPersonne.setString(1, manager.getNom());
            pstPersonne.setString(2, manager.getPrenom());
            pstPersonne.setString(3, manager.getEmail());
            pstPersonne.setString(4, manager.getTelephone());
            pstPersonne.executeUpdate();

            // Get generated ID
            ResultSet rs = pstPersonne.getGeneratedKeys();
            int generatedId = 0;
            if (rs.next()) {
                generatedId = rs.getInt(1);
                manager.setId(generatedId);
            }

            // Insert into manager - FIX: Added login and password
            PreparedStatement pstManager = conn.prepareStatement(sqlManager);
            pstManager.setInt(1, generatedId);
            pstManager.setString(2, manager.getLogin());
            pstManager.setString(3, manager. getMotDePasse());
            pstManager.executeUpdate();

            conn.commit();
            System.out.println("✅ Manager ajouté avec succès: " + manager.getNom());
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("❌ Erreur lors de l'ajout du manager: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Get all managers
    public List<Manager> getAllManagers() {
        List<Manager> managers = new ArrayList<>();
        String sql = "SELECT p.*, m.login, m.mot_de_passe " +
                "FROM personne p " +
                "INNER JOIN manager m ON p.id = m.id " +
                "WHERE p.type_personne = 'MANAGER'";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Manager manager = new Manager(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("login"),
                        rs.getString("mot_de_passe")
                );
                managers.add(manager);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des managers: " + e.getMessage());
            e.printStackTrace();
        }
        return managers;
    }

    // Get all managers as ObservableList
    public ObservableList<Manager> getAllManagersObservable() {
        return FXCollections.observableArrayList(getAllManagers());
    }

    // Get manager by ID
    public Manager getManagerById(int id) {
        String sql = "SELECT p.*, m.login, m.mot_de_passe " +
                "FROM personne p " +
                "INNER JOIN manager m ON p.id = m.id " +
                "WHERE p.id = ? AND p.type_personne = 'MANAGER'";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Manager(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("login"),
                        rs.getString("mot_de_passe")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération du manager: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Authenticate manager (login)
    public Manager authenticate(String login, String password) {
        String sql = "SELECT p.*, m.login, m.mot_de_passe " +
                "FROM personne p " +
                "INNER JOIN manager m ON p.id = m.id " +
                "WHERE m.login = ? AND m.mot_de_passe = ? AND p.type_personne = 'MANAGER'";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, login);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Manager authentifié: " + rs.getString("nom"));
                return new Manager(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("login"),
                        rs.getString("mot_de_passe")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'authentification: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Delete manager
    public boolean supprimerManager(int id) {
        String sql = "DELETE FROM personne WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Manager supprimé avec succès");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du manager: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}