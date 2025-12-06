package DAO;

import Database.DatabaseConnection;
import Models.amine.Gestion.Paiement;
import Models.amine.Personnel.Membre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaiementDAO {

    private MembreDAO membreDAO = new MembreDAO();

    /**
     * Add a new payment to the database
     */
    public boolean ajouterPaiement(Paiement paiement) {
        String sql = "INSERT INTO paiement (type_paiement, montant, date_paiement, membre_id) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.err.println("❌ Connexion NULL dans ajouterPaiement");
                return false;
            }

            // CORRECTION: Désactiver autocommit
            conn.setAutoCommit(false);

            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, paiement.getTypePaiement());
            pst.setDouble(2, paiement.getMontant());
            pst.setTimestamp(3, new Timestamp(paiement.getDatePaiement().getTime()));
            pst.setInt(4, paiement.getMembre().getId());

            int rowsAffected = pst.executeUpdate();
            System.out.println("📊 Lignes affectées: " + rowsAffected);

            if (rowsAffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    paiement.setIdPaiement(generatedId);

                    // CORRECTION: Commiter la transaction
                    conn.commit();
                    System.out.println("✅ Paiement ajouté avec ID: " + generatedId);

                    rs.close();
                    pst.close();
                    return true;
                } else {
                    conn.rollback();
                    System.err.println("❌ Aucun ID généré");
                }
            } else {
                conn.rollback();
                System.err.println("❌ Aucune ligne affectée");
            }

            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout du paiement");
            System.err.println("   Message: " + e.getMessage());
            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("⚠️ Rollback effectué");
                } catch (SQLException ex) {
                    System.err.println("❌ Erreur lors du rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("❌ Erreur lors de la réactivation de autoCommit: " + e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * Get all payments from database
     */
    public List<Paiement> getAllPaiements() {
        List<Paiement> paiements = new ArrayList<>();

        String sql = """
        SELECT 
            p.id_paiement,
            p.type_paiement,
            p.montant,
            p.date_paiement,
            pers.id,
            pers.nom,
            pers.prenom,
            pers.email,
            pers.telephone
        FROM paiement p
        INNER JOIN personne pers ON p.membre_id = pers.id
        WHERE pers.type_personne = 'MEMBRE'
        ORDER BY p.date_paiement DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Membre membre = new Membre(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email") != null ? rs.getString("email") : "",
                        rs.getString("telephone") != null ? rs.getString("telephone") : ""
                );

                Paiement paiement = new Paiement(
                        rs.getInt("id_paiement"),
                        rs.getString("type_paiement"),
                        rs.getDouble("montant"),
                        rs.getTimestamp("date_paiement") != null
                                ? new java.util.Date(rs.getTimestamp("date_paiement").getTime())
                                : new java.util.Date(),  // ← ici, plus d'erreur
                        membre
                );

                paiements.add(paiement);
            }

            System.out.println("Chargés " + paiements.size() + " paiement(s) depuis la base");

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des paiements : " + e.getMessage());
            e.printStackTrace();
        }

        return paiements;
    }

    /**
     * Get all payments as ObservableList for JavaFX TableView
     */
    public ObservableList<Paiement> getAllPaiementsObservable() {
        return FXCollections.observableArrayList(getAllPaiements());
    }

    /**
     * Get payment by ID
     */
    public Paiement getPaiementById(int id) {
        String sql = "SELECT * FROM paiement WHERE id_paiement = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int membreId = rs.getInt("membre_id");
                Membre membre = membreDAO.getMembreById(membreId);

                if (membre != null) {
                    Paiement paiement = new Paiement(
                            rs.getInt("id_paiement"),
                            rs.getString("type_paiement"),
                            rs.getDouble("montant"),
                            new java.util.Date(rs.getTimestamp("date_paiement").getTime()),
                            membre
                    );

                    rs.close();
                    pst.close();
                    return paiement;
                }
            }

            rs.close();
            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération du paiement");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get payments by member
     */
    public List<Paiement> getPaiementsByMembre(int membreId) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE membre_id = ? ORDER BY date_paiement DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, membreId);
            ResultSet rs = pst.executeQuery();

            Membre membre = membreDAO.getMembreById(membreId);

            while (rs.next() && membre != null) {
                Paiement paiement = new Paiement(
                        rs.getInt("id_paiement"),
                        rs.getString("type_paiement"),
                        rs.getDouble("montant"),
                        new java.util.Date(rs.getTimestamp("date_paiement").getTime()),
                        membre
                );
                paiements.add(paiement);
            }

            rs.close();
            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des paiements du membre");
            e.printStackTrace();
        }
        return paiements;
    }

    /**
     * Get payments by type
     */
    public List<Paiement> getPaiementsByType(String typePaiement) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE type_paiement = ? ORDER BY date_paiement DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, typePaiement);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int membreId = rs.getInt("membre_id");
                Membre membre = membreDAO.getMembreById(membreId);

                if (membre != null) {
                    Paiement paiement = new Paiement(
                            rs.getInt("id_paiement"),
                            rs.getString("type_paiement"),
                            rs.getDouble("montant"),
                            new java.util.Date(rs.getTimestamp("date_paiement").getTime()),
                            membre
                    );
                    paiements.add(paiement);
                }
            }

            rs.close();
            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des paiements par type");
            e.printStackTrace();
        }
        return paiements;
    }

    /**
     * Get payments within a date range
     */
    public List<Paiement> getPaiementsByDateRange(java.util.Date dateDebut, java.util.Date dateFin) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE date_paiement BETWEEN ? AND ? ORDER BY date_paiement DESC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new Timestamp(dateDebut.getTime()));
            pst.setTimestamp(2, new Timestamp(dateFin.getTime()));
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int membreId = rs.getInt("membre_id");
                Membre membre = membreDAO.getMembreById(membreId);

                if (membre != null) {
                    Paiement paiement = new Paiement(
                            rs.getInt("id_paiement"),
                            rs.getString("type_paiement"),
                            rs.getDouble("montant"),
                            new java.util.Date(rs.getTimestamp("date_paiement").getTime()),
                            membre
                    );
                    paiements.add(paiement);
                }
            }

            rs.close();
            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des paiements par période");
            e.printStackTrace();
        }
        return paiements;
    }

    /**
     * Delete payment from database
     */
    public boolean supprimerPaiement(int id) {
        String sql = "DELETE FROM paiement WHERE id_paiement = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Paiement supprimé avec succès");
                pst.close();
                return true;
            } else {
                conn.rollback();
            }

            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du paiement");
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("❌ Erreur rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("❌ Erreur autoCommit: " + e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * Calculate total revenue from all payments
     */
    public double calculerRevenuTotal() {
        String sql = "SELECT SUM(montant) as total FROM paiement";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                double total = rs.getDouble("total");
                rs.close();
                stmt.close();
                return total;
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du calcul du revenu total");
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Calculate total revenue for a specific member
     */
    public double calculerRevenuParMembre(int membreId) {
        String sql = "SELECT SUM(montant) as total FROM paiement WHERE membre_id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, membreId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double total = rs.getDouble("total");
                rs.close();
                pst.close();
                return total;
            }

            rs.close();
            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du calcul du revenu par membre");
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Calculate revenue by payment type
     */
    public Map<String, Double> calculerRevenuParType() {
        Map<String, Double> revenuParType = new HashMap<>();
        String sql = "SELECT type_paiement, SUM(montant) as total FROM paiement GROUP BY type_paiement";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                revenuParType.put(rs.getString("type_paiement"), rs.getDouble("total"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du calcul du revenu par type");
            e.printStackTrace();
        }
        return revenuParType;
    }

    /**
     * Get total count of payments
     */
    public int getTotalPaiements() {
        String sql = "SELECT COUNT(*) as total FROM paiement";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int total = rs.getInt("total");
                rs.close();
                stmt.close();
                return total;
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du comptage des paiements");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get count of payments by type
     */
    public int getCountByType(String typePaiement) {
        String sql = "SELECT COUNT(*) as total FROM paiement WHERE type_paiement = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, typePaiement);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                rs.close();
                pst.close();
                return total;
            }

            rs.close();
            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors du comptage des paiements par type");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get recent payments (last N payments)
     */
    public List<Paiement> getRecentPaiements(int limit) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement ORDER BY date_paiement DESC LIMIT ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, limit);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int membreId = rs.getInt("membre_id");
                Membre membre = membreDAO.getMembreById(membreId);

                if (membre != null) {
                    Paiement paiement = new Paiement(
                            rs.getInt("id_paiement"),
                            rs.getString("type_paiement"),
                            rs.getDouble("montant"),
                            new java.util.Date(rs.getTimestamp("date_paiement").getTime()),
                            membre
                    );
                    paiements.add(paiement);
                }
            }

            rs.close();
            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des paiements récents");
            e.printStackTrace();
        }
        return paiements;
    }

    /**
     * Update payment information
     */
    public boolean modifierPaiement(Paiement paiement) {
        String sql = "UPDATE paiement SET type_paiement = ?, montant = ?, date_paiement = ? WHERE id_paiement = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, paiement.getTypePaiement());
            pst.setDouble(2, paiement.getMontant());
            pst.setTimestamp(3, new Timestamp(paiement.getDatePaiement().getTime()));
            pst.setInt(4, paiement.getIdPaiement());

            int rows = pst.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Paiement modifié avec succès");
                pst.close();
                return true;
            } else {
                conn.rollback();
            }

            pst.close();

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification du paiement");
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("❌ Erreur rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("❌ Erreur autoCommit: " + e.getMessage());
                }
            }
        }
        return false;
    }
}