package DAO;

import Database.DatabaseConnection;
import Models.amine.Gestion.Abonnement;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AbonnementDAO {

    // Add abonnement for a member
    public boolean ajouterAbonnement(int membreId, Abonnement abonnement) {
        String sql = "INSERT INTO abonnement (membre_id, type, date_debut, date_fin, etat) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, membreId);
            pst.setString(2, abonnement.getType());
            pst.setDate(3, new java.sql.Date(abonnement.getDateDebut().getTime()));
            pst.setDate(4, new java.sql.Date(abonnement.getDateFin().getTime()));
            pst.setString(5, abonnement.getEtat());

            pst.executeUpdate();
            System.out.println("✅ Abonnement ajouté pour le membre ID: " + membreId);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all abonnements as a map (membreId -> Abonnement)
    public Map<Integer, Abonnement> getAllAbonnements() {
        Map<Integer, Abonnement> abonnements = new HashMap<>();
        String sql = "SELECT * FROM abonnement";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int membreId = rs.getInt("membre_id");
                Abonnement abo = new Abonnement(
                        rs.getString("type"),
                        new java.util.Date(rs.getDate("date_debut").getTime()),
                        new java.util.Date(rs.getDate("date_fin").getTime())
                );

                // Set the state
                if (rs.getString("etat").equals("Actif")) {
                    abo.activer();
                } else {
                    abo.desactiver();
                }

                abonnements.put(membreId, abo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abonnements;
    }

    // Get abonnement by member ID
    public Abonnement getAbonnementByMembreId(int membreId) {
        String sql = "SELECT * FROM abonnement WHERE membre_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, membreId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Abonnement abo = new Abonnement(
                        rs.getString("type"),
                        new java.util.Date(rs.getDate("date_debut").getTime()),
                        new java.util.Date(rs.getDate("date_fin").getTime())
                );

                if (rs.getString("etat").equals("Actif")) {
                    abo.activer();
                }

                return abo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update abonnement
    public boolean modifierAbonnement(int membreId, Abonnement abonnement) {
        String sql = "UPDATE abonnement SET type = ?, date_debut = ?, date_fin = ?, etat = ? WHERE membre_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, abonnement.getType());
            pst.setDate(2, new java.sql.Date(abonnement.getDateDebut().getTime()));
            pst.setDate(3, new java.sql.Date(abonnement.getDateFin().getTime()));
            pst.setString(4, abonnement.getEtat());
            pst.setInt(5, membreId);

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete abonnement
    public boolean supprimerAbonnement(int membreId) {
        String sql = "DELETE FROM abonnement WHERE membre_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, membreId);

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}