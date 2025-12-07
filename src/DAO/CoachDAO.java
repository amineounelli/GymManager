package DAO;

import Models.amine.Personnel.Coach;
import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoachDAO {

    public boolean ajouterCoach(Coach coach) {
        String sqlPersonne = "INSERT INTO personne (nom, prenom, email, telephone, type_personne) VALUES (?, ?, ?, ?, 'Coach')";
        String sqlCoach = "INSERT INTO coach (id, specialite, tarif_heure) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.err.println("❌ Connexion NULL dans ajouterCoach");
                return false;
            }

            conn.setAutoCommit(false);

            // Insérer dans la table personne
            PreparedStatement psPersonne = conn.prepareStatement(sqlPersonne, Statement.RETURN_GENERATED_KEYS);
            psPersonne.setString(1, coach.getNom());
            psPersonne.setString(2, coach.getPrenom());
            psPersonne.setString(3, coach.getEmail());
            psPersonne.setString(4, coach.getTelephone());

            int rowsAffected = psPersonne.executeUpdate();
            System.out.println("📊 Personne insérée, lignes affectées: " + rowsAffected);

            ResultSet rs = psPersonne.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                coach.setId(id);
                System.out.println("✅ ID généré pour le coach: " + id);

                // Insérer dans la table coach
                PreparedStatement psCoach = conn.prepareStatement(sqlCoach);
                psCoach.setInt(1, id);
                psCoach.setString(2, coach.getSpecialite()); // CORRECTION: Utiliser getSpecialite()
                psCoach.setDouble(3, coach.getTarifHeure()); // CORRECTION: Utiliser le vrai tarif

                int coachRows = psCoach.executeUpdate();
                System.out.println("📊 Coach inséré, lignes affectées: " + coachRows);

                psCoach.close();
            }

            conn.commit();
            System.out.println("✅ Transaction commitée avec succès");

            rs.close();
            psPersonne.close();
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL lors de l'ajout du coach:");
            System.err.println("   Message: " + e.getMessage());
            System.err.println("   Code: " + e.getErrorCode());
            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("⚠️ Transaction rollback effectué");
                } catch (SQLException ex) {
                    System.err.println("❌ Erreur lors du rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("❌ Erreur lors de la réactivation de autoCommit: " + e.getMessage());
                }
            }
        }
    }

    public List<Coach> getAllCoachs() {
        List<Coach> coachs = new ArrayList<>();
        String sql = "SELECT p.*, c.specialite, c.tarif_heure FROM personne p " +
                "INNER JOIN coach c ON p.id = c.id WHERE p.type_personne = 'Coach'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Coach coach = new Coach(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("specialite"),
                        rs.getDouble("tarif_heure")
                );
                coachs.add(coach);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du chargement des coachs: " + e.getMessage());
            e.printStackTrace();
        }

        return coachs;
    }

    public ObservableList<Coach> getAllCoachsObservable() {
        return FXCollections.observableArrayList(getAllCoachs());
    }

    public Coach getCoachById(int id) {
        String sql = "SELECT p.*, c.specialite, c.tarif_heure FROM personne p " +
                "INNER JOIN coach c ON p.id = c.id WHERE p.id = ? AND p.type_personne = 'Coach'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Coach(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("specialite"),
                        rs.getDouble("tarif_heure")
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération du coach: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean supprimerCoach(int id) {
        String sql = "DELETE FROM personne WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Coach supprimé avec succès");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du coach: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean modifierCoach(Coach coach) {
    String sqlPersonne = "UPDATE personne SET nom=?, prenom=?, email=?, telephone=? WHERE id=?";
    String sqlCoach = "UPDATE coach SET specialite=?, tarif_heure=? WHERE id=?";

    try (Connection conn = DatabaseConnection.getConnection()) {

        PreparedStatement ps1 = conn.prepareStatement(sqlPersonne);
        ps1.setString(1, coach.getNom());
        ps1.setString(2, coach.getPrenom());
        ps1.setString(3, coach.getEmail());
        ps1.setString(4, coach.getTelephone());
        ps1.setInt(5, coach.getId());
        ps1.executeUpdate();

        PreparedStatement ps2 = conn.prepareStatement(sqlCoach);
        ps2.setString(1, coach.getSpecialite());
        ps2.setDouble(2, coach.getTarifHeure());
        ps2.setInt(3, coach.getId());
        ps2.executeUpdate();

        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}