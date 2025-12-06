package DAO;

import Models.amine.Personnel.Manager;
import Models.amine.Personnel.Membre;
import Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {

    // Créer un nouveau membre
    public boolean ajouterMembre(Membre membre) {
        String sqlPersonne = "INSERT INTO personne (nom, prenom, email, telephone, type_personne) VALUES (?, ?, ?, ?, 'Membre')";
        String sqlMembre = "INSERT INTO membre (id) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insérer dans personne
            PreparedStatement psPersonne = conn.prepareStatement(sqlPersonne, Statement.RETURN_GENERATED_KEYS);
            psPersonne.setString(1, membre.getNom());
            psPersonne.setString(2, membre.getPrenom());
            psPersonne.setString(3, membre.getEmail());
            psPersonne.setString(4, membre.getTelephone());
            psPersonne.executeUpdate();

            // Récupérer l'ID généré
            ResultSet rs = psPersonne.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                membre.setId(id);

                // Insérer dans membre
                PreparedStatement psMembre = conn.prepareStatement(sqlMembre);
                psMembre.setInt(1, id);
                psMembre.executeUpdate();
            }

            conn.commit();
            System.out.println("Membre ajouté avec succès !");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer tous les membres
    public List<Membre> getAllMembres() {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT p.* FROM personne p INNER JOIN membre m ON p.id = m.id WHERE p.type_personne = 'Membre'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Membre membre = new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );
                membres.add(membre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return membres;
    }

    // Récupérer un membre par ID
    public Membre getMembreById(int id) {
        String sql = "SELECT p.* FROM personne p INNER JOIN membre m ON p.id = m.id WHERE p.id = ? AND p.type_personne = 'Membre'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Modifier un membre
    public boolean modifierMembre(Membre membre) {
        String sql = "UPDATE personne SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, membre.getNom());
            ps.setString(2, membre.getPrenom());
            ps.setString(3, membre.getEmail());
            ps.setString(4, membre.getTelephone());
            ps.setInt(5, membre.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all membres as ObservableList
    public ObservableList<Membre> getAllMembresObservable() {
        return FXCollections.observableArrayList(getAllMembres());
    }

    // Supprimer un membre
    public boolean supprimerMembre(int id) {
        String sql = "DELETE FROM personne WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
