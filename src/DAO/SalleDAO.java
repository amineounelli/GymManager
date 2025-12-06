package DAO;

import Database.DatabaseConnection;
import Models.amen.Infrastructure.Salle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO {

    // ===================== AJOUTER UNE SALLE =====================
    public boolean ajouterSalle(Salle salle) {
        String sql = "INSERT INTO salles (nom_salle, capacite_salle, disponible) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, salle.getNomSalle());
            pstmt.setInt(2, salle.getCapaciteSalle());
            pstmt.setBoolean(3, true); // disponible par défaut

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGenere = generatedKeys.getInt(1);
                        salle.setIdSalle(idGenere); // On met à jour l'objet avec l'ID réel
                        System.out.println("Salle ajoutée avec succès ! ID: " + idGenere);
                        return true;
                    }
                }
            }
            System.err.println("Aucune salle insérée ou aucun ID généré");
            return false;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la salle : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ===================== RÉCUPÉRER TOUTES LES SALLES =====================
    public List<Salle> getAllSalles() {
        List<Salle> salles = new ArrayList<>();
        String sql = "SELECT * FROM salles ORDER BY id_salle";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Salle salle = new Salle(
                        rs.getInt("id_salle"),
                        rs.getString("nom_salle"),
                        rs.getInt("capacite_salle")
                );
                salles.add(salle);
                System.out.println("Chargée : " + salle.getNomSalle() + " (Capacité: " + salle.getCapaciteSalle() + ")");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des salles : " + e.getMessage());
            e.printStackTrace();
        }
        return salles;
    }

    // Version ObservableList pour JavaFX (comme ManagerDAO)
    public ObservableList<Salle> getAllSallesObservable() {
        return FXCollections.observableArrayList(getAllSalles());
    }

    // ===================== RÉCUPÉRER UNE SALLE PAR ID =====================
    public Salle getSalleById(int id) {
        String sql = "SELECT * FROM salles WHERE id_salle = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Salle(
                            rs.getInt("id_salle"),
                            rs.getString("nom_salle"),
                            rs.getInt("capacite_salle")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la salle ID " + id + " : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // ===================== RÉCUPÉRER UNE SALLE PAR NOM =====================
    public Salle getSalleByNom(String nom) {
        String sql = "SELECT * FROM salles WHERE nom_salle = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Salle(
                            rs.getInt("id_salle"),
                            rs.getString("nom_salle"),
                            rs.getInt("capacite_salle")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche salle par nom '" + nom + "' : " + e.getMessage());
        }
        return null;
    }

    // ===================== METTRE À JOUR UNE SALLE =====================
    public boolean updateSalle(Salle salle) {
        String sql = "UPDATE salles SET nom_salle = ?, capacite_salle = ? WHERE id_salle = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, salle.getNomSalle());
            pstmt.setInt(2, salle.getCapaciteSalle());
            pstmt.setInt(3, salle.getIdSalle());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Salle mise à jour : " + salle.getNomSalle());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour salle : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // ===================== SUPPRIMER UNE SALLE =====================
    public boolean supprimerSalle(int idSalle) {
        String sql = "DELETE FROM salles WHERE id_salle = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSalle);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Salle supprimée avec succès (ID: " + idSalle + ")");
                return true;
            } else {
                System.out.println("Aucune salle trouvée avec l'ID : " + idSalle);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la salle ID " + idSalle + " : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // ===================== COMPTER LES SALLES =====================
    public int compterSalles() {
        String sql = "SELECT COUNT(*) AS total FROM salles";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Erreur comptage salles : " + e.getMessage());
        }
        return 0;
    }

    // ===================== VÉRIFIER SI UNE SALLE EXISTE (par nom) =====================
    public boolean salleExiste(String nomSalle) {
        String sql = "SELECT 1 FROM salles WHERE nom_salle = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomSalle);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // true si au moins une ligne
            }
        } catch (SQLException e) {
            System.err.println("Erreur vérification existence salle : " + e.getMessage());
        }
        return false;
    }


}