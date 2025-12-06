package DAO;

import Database.DatabaseConnection;
import Models.amen.Infrastructure.*;
import Models.amine.Personnel.Coach;
import Models.amine.Personnel.Membre;
import Models.amine.Gestion.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAO {

    private final SalleDAO salleDAO = new SalleDAO();
    private final CoachDAO coachDAO = new CoachDAO();
    private final MembreDAO membreDAO = new MembreDAO();

    // SÉANCE COLLECTIVE
    public boolean ajouterSeanceCollective(SeanceCollective seance) {
        String sql = "INSERT INTO seance (type_seance, duree, date_seance, heure_debut, cout_horaire, salle_id, coach_id, capacite) " +
                "VALUES ('Collective', ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, seance.getDuree());
            pstmt.setDate(2, new java.sql.Date(seance.getDate().getTime()));
            pstmt.setTime(3, null); // ou une heure si tu veux
            pstmt.setDouble(4, seance.getCoutHoraire());
            pstmt.setInt(5, seance.getSalle().getIdSalle());
            pstmt.setInt(6, seance.getCoach().getId());
            pstmt.setInt(7, seance.getCapacite());

            int rows = pstmt.executeUpdate();                    // ← OBLIGATOIRE AVANT getGeneratedKeys
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        seance.setIdSeance(rs.getInt(1));
                        System.out.println("Séance collective ajoutée → ID: " + seance.getIdSeance());
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur ajout séance collective : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // SÉANCE INDIVIDUELLE
    public boolean ajouterSeanceIndividuelle(SeanceIndividuelle seance) {
        String sql = "INSERT INTO seance (type_seance, duree, date_seance, heure_debut, cout_horaire, salle_id, membre_id) " +
                "VALUES ('Individuelle', ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, seance.getDuree());
            pstmt.setDate(2, new java.sql.Date(seance.getDate().getTime()));
            pstmt.setTime(3, null);
            pstmt.setDouble(4, seance.getCoutHoraire());
            pstmt.setInt(5, seance.getSalle().getIdSalle());
            pstmt.setInt(6, seance.getMembre().getId());

            int rows = pstmt.executeUpdate();                    // ← CRUCIAL ICI AUSSI
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        seance.setIdSeance(rs.getInt(1));
                        System.out.println("Séance individuelle ajoutée → ID: " + seance.getIdSeance());
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur ajout séance individuelle : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Seance> getAllSeances() {
        List<Seance> seances = new ArrayList<>();

        // Requête corrigée
        String sql = """
        SELECT 
            s.id_seance, s.type_seance, s.duree, s.date_seance, 
            s.cout_horaire, s.capacite,
            sa.id_salle, sa.nom_salle, sa.capacite_salle,
            c.id AS coach_id, c.nom AS coach_nom, c.prenom AS coach_prenom,
            c.email AS coach_email, c.telephone AS coach_tel,
            m.id AS membre_id, m.nom AS membre_nom, m.prenom AS membre_prenom,
            m.email AS membre_email, m.telephone AS membre_tel
        FROM seance s
        LEFT JOIN salles sa ON s.salle_id = sa.id_salle
        LEFT JOIN personne c ON s.coach_id = c.id
        LEFT JOIN personne m ON s.membre_id = m.id
        ORDER BY s.date_seance DESC, s.heure_debut DESC
        """;

        System.out.println("\n🔍 Chargement des séances...");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    int id = rs.getInt("id_seance");
                    String type = rs.getString("type_seance");
                    double duree = rs.getDouble("duree");
                    Date date = rs.getDate("date_seance");
                    double cout = rs.getDouble("cout_horaire");

                    // Créer la salle directement depuis le ResultSet
                    int salleId = rs.getInt("id_salle");
                    if (rs.wasNull()) {
                        System.err.println("⚠️ Séance ID " + id + " sans salle");
                        continue;
                    }

                    Salle salle = new Salle(
                            salleId,
                            rs.getString("nom_salle"),
                            rs.getInt("capacite_salle")  // ← Changé ici
                    );

                    Seance seance = null;

                    if ("Collective".equals(type)) {
                        int coachId = rs.getInt("coach_id");
                        if (!rs.wasNull()) {
                            // Créer le coach directement depuis le ResultSet
                            Coach coach = new Coach(
                                    coachId,
                                    rs.getString("coach_nom"),
                                    rs.getString("coach_prenom"),
                                    rs.getString("coach_email"),
                                    rs.getString("coach_tel"),
                                    "Non spécifié",  // Spécialité par défaut
                                    0.0              // Tarif par défaut
                            );

                            int capacite = rs.getInt("capacite");
                            seance = new SeanceCollective(id, duree, date, salle, cout, coach, capacite);

                            System.out.println("  📄 Séance Collective ID:" + id +
                                    " - Coach:" + coach.getNom() +
                                    " - Date:" + date);
                        } else {
                            System.err.println("⚠️ Séance collective ID " + id + " sans coach");
                        }

                    } else if ("Individuelle".equals(type)) {
                        int membreId = rs.getInt("membre_id");
                        if (!rs.wasNull()) {
                            // Créer le membre directement depuis le ResultSet
                            Membre membre = new Membre(
                                    membreId,
                                    rs.getString("membre_nom"),
                                    rs.getString("membre_prenom"),
                                    rs.getString("membre_email"),
                                    rs.getString("membre_tel")
                            );

                            seance = new SeanceIndividuelle(id, duree, date, salle, cout, membre);

                            System.out.println("  📄 Séance Individuelle ID:" + id +
                                    " - Membre:" + membre.getNom() +
                                    " - Date:" + date);
                        } else {
                            System.err.println("⚠️ Séance individuelle ID " + id + " sans membre");
                        }
                    }

                    if (seance != null) {
                        seances.add(seance);
                    }

                } catch (Exception e) {
                    System.err.println("❌ Erreur lors du traitement d'une séance: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println("✅ Total séances chargées: " + seances.size());

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return seances;
    }

    public ObservableList<Seance> getAllSeancesObservable() {
        return FXCollections.observableArrayList(getAllSeances());
    }

    public boolean supprimerSeance(int id) {
        String sql = "DELETE FROM seance WHERE id_seance = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Seance getSeanceById(int id) {
        for (Seance s : getAllSeances()) {
            if (s.getIdSeance() == id) {
                return s;
            }
        }
        return null;
    }
}