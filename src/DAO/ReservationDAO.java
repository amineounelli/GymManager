package DAO;

import Database.DatabaseConnection;
import Models.amen.Infrastructure.Salle;
import Models.amen.Infrastructure.SeanceCollective;
import Models.amen.Infrastructure.SeanceIndividuelle;
import Models.amine.Gestion.Reservation;
import Models.amine.Personnel.Coach;
import Models.amine.Personnel.Membre;
import Models.amen.Infrastructure.Seance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private final MembreDAO membreDAO = new MembreDAO();
    private final SeanceDAO seanceDAO = new SeanceDAO();

    public boolean ajouterReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (membre_id, seance_id, date_reservation, statut) VALUES (?, ?, ?, 'confirmee')";

        System.out.println("\n💾 === AJOUT RÉSERVATION EN BASE ===");
        System.out.println("Membre ID: " + reservation.getMembre().getId());
        System.out.println("Séance ID: " + reservation.getSeance().getIdSeance());

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, reservation.getMembre().getId());
            pstmt.setInt(2, reservation.getSeance().getIdSeance());
            pstmt.setDate(3, new java.sql.Date(reservation.getDateReservation().getTime()));

            System.out.println("📝 Exécution de la requête...");
            int rows = pstmt.executeUpdate();
            System.out.println("Lignes affectées: " + rows);

            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    System.out.println("✅ Réservation ajoutée avec ID: " + newId);
                    rs.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();

        // Charger TOUT en une seule requête avec des JOINs
        String sql = """
        SELECT 
            r.id_reservation, r.date_reservation, r.statut,
            m.id AS membre_id, m.nom AS membre_nom, m.prenom AS membre_prenom,
            m.email AS membre_email, m.telephone AS membre_tel,
            s.id_seance, s.type_seance, s.duree, s.date_seance, s.cout_horaire,
            s.capacite AS seance_capacite,
            sa.id_salle, sa.nom_salle, sa.capacite_salle,
            c.id AS coach_id, c.nom AS coach_nom, c.prenom AS coach_prenom,
            c.email AS coach_email, c.telephone AS coach_tel,
            me.id AS membre_seance_id, me.nom AS membre_seance_nom, 
            me.prenom AS membre_seance_prenom,
            me.email AS membre_seance_email, me.telephone AS membre_seance_tel
        FROM reservation r
        JOIN personne m ON r.membre_id = m.id
        JOIN seance s ON r.seance_id = s.id_seance
        LEFT JOIN salles sa ON s.salle_id = sa.id_salle
        LEFT JOIN personne c ON s.coach_id = c.id
        LEFT JOIN personne me ON s.membre_id = me.id
        WHERE r.statut != 'annulee'
        ORDER BY r.date_reservation DESC
        """;

        System.out.println("\n🔍 Chargement des réservations...");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    int id = rs.getInt("id_reservation");
                    Date dateReservation = rs.getDate("date_reservation");

                    // Créer le membre de la réservation
                    Membre membre = new Membre(
                            rs.getInt("membre_id"),
                            rs.getString("membre_nom"),
                            rs.getString("membre_prenom"),
                            rs.getString("membre_email"),
                            rs.getString("membre_tel")
                    );

                    // Créer la salle
                    Salle salle = null;
                    int salleId = rs.getInt("id_salle");
                    if (!rs.wasNull()) {
                        salle = new Salle(
                                salleId,
                                rs.getString("nom_salle"),
                                rs.getInt("capacite_salle")
                        );
                    }

                    // Créer la séance
                    Seance seance = null;
                    String typeSeance = rs.getString("type_seance");
                    int seanceId = rs.getInt("id_seance");
                    double duree = rs.getDouble("duree");
                    Date dateSeance = rs.getDate("date_seance");
                    double cout = rs.getDouble("cout_horaire");

                    if ("Collective".equals(typeSeance)) {
                        int coachId = rs.getInt("coach_id");
                        if (!rs.wasNull() && salle != null) {
                            Coach coach = new Coach(
                                    coachId,
                                    rs.getString("coach_nom"),
                                    rs.getString("coach_prenom"),
                                    rs.getString("coach_email"),
                                    rs.getString("coach_tel"),
                                    "Non spécifié",
                                    0.0
                            );
                            int capacite = rs.getInt("seance_capacite");
                            seance = new SeanceCollective(seanceId, duree, dateSeance, salle, cout, coach, capacite);
                        }
                    } else if ("Individuelle".equals(typeSeance)) {
                        int membreSeanceId = rs.getInt("membre_seance_id");
                        if (!rs.wasNull() && salle != null) {
                            Membre membreSeance = new Membre(
                                    membreSeanceId,
                                    rs.getString("membre_seance_nom"),
                                    rs.getString("membre_seance_prenom"),
                                    rs.getString("membre_seance_email"),
                                    rs.getString("membre_seance_tel")
                            );
                            seance = new SeanceIndividuelle(seanceId, duree, dateSeance, salle, cout, membreSeance);
                        }
                    }

                    if (seance != null) {
                        Reservation reservation = new Reservation(id, membre, seance, dateReservation);
                        reservations.add(reservation);

                        System.out.println("  📄 Réservation ID:" + id +
                                " - Membre:" + membre.getNom() +
                                " - Séance:" + seanceId);
                    }

                } catch (Exception e) {
                    System.err.println("❌ Erreur traitement réservation: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println("✅ Total réservations chargées: " + reservations.size());

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return reservations;
    }

    // SUPPRIMER UNE RÉSERVATION (marquer comme annulée)
    public boolean supprimerReservation(int id) {
        String sql = "UPDATE reservation SET statut = 'annulee' WHERE id_reservation = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Réservation " + id + " annulée");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur suppression réservation: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // SUPPRIMER DÉFINITIVEMENT
    public boolean supprimerReservationDefinitif(int id) {
        String sql = "DELETE FROM reservation WHERE id_reservation = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // RÉCUPÉRER UNE RÉSERVATION PAR ID
    public Reservation getReservationById(int id) {
        String sql = """
            SELECT r.*, m.nom, m.prenom, m.email, m.telephone
            FROM reservation r
            JOIN personne m ON r.membre_id = m.id
            WHERE r.id_reservation = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Membre membre = new Membre(
                        rs.getInt("membre_id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );

                Seance seance = seanceDAO.getSeanceById(rs.getInt("seance_id"));

                return new Reservation(
                        rs.getInt("id_reservation"),
                        membre,
                        seance,
                        rs.getDate("date_reservation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // RÉCUPÉRER LES RÉSERVATIONS D'UN MEMBRE
    public List<Reservation> getReservationsByMembre(int membreId) {
        List<Reservation> reservations = new ArrayList<>();

        String sql = """
            SELECT * FROM reservation 
            WHERE membre_id = ? AND statut != 'annulee'
            ORDER BY date_reservation DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, membreId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Membre membre = membreDAO.getMembreById(membreId);
                Seance seance = seanceDAO.getSeanceById(rs.getInt("seance_id"));

                if (membre != null && seance != null) {
                    reservations.add(new Reservation(
                            rs.getInt("id_reservation"),
                            membre,
                            seance,
                            rs.getDate("date_reservation")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // RÉCUPÉRER LES RÉSERVATIONS D'UNE SÉANCE
    public List<Reservation> getReservationsBySeance(int seanceId) {
        List<Reservation> reservations = new ArrayList<>();

        String sql = """
            SELECT * FROM reservation 
            WHERE seance_id = ? AND statut != 'annulee'
            ORDER BY date_reservation DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seanceId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Membre membre = membreDAO.getMembreById(rs.getInt("membre_id"));
                Seance seance = seanceDAO.getSeanceById(seanceId);

                if (membre != null && seance != null) {
                    reservations.add(new Reservation(
                            rs.getInt("id_reservation"),
                            membre,
                            seance,
                            rs.getDate("date_reservation")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}