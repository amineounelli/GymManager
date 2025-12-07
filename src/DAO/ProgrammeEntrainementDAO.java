package DAO;

import Database.DatabaseConnection;
import Models.amine.Gestion.Exercice;
import Models.amine.Gestion.ProgrammeEntrainement;
import Models.amine.Personnel.Coach;
import Models.amen.Infrastructure.Equipement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgrammeEntrainementDAO {

    private final CoachDAO coachDAO = new CoachDAO();
    private final EquipementDAO equipementDAO = new EquipementDAO();

    // AJOUTER UN PROGRAMME + SES EXERCICES
    public boolean ajouterProgramme(ProgrammeEntrainement prog) {
        String sqlProg = "INSERT INTO programmeentrainement (titre, coach_id, duree_totale) VALUES (?, ?, ?)";
        String sqlEx = "INSERT INTO exercice (programme_id, nom, repetitions, poids, duree, equipement_id) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pProg = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            pProg = conn.prepareStatement(sqlProg, Statement.RETURN_GENERATED_KEYS);
            pProg.setString(1, prog.getTitre());
            pProg.setInt(2, prog.getCoach().getId());
            pProg.setDouble(3, prog.getDureeTotale());
            pProg.executeUpdate();

            try (ResultSet rs = pProg.getGeneratedKeys()) {
                if (rs.next()) {
                    int progId = rs.getInt(1);
                    prog.setIdProgramme(progId);

                    if (!prog.getListeExercices().isEmpty()) {
                        try (PreparedStatement pEx = conn.prepareStatement(sqlEx)) {
                            for (Exercice ex : prog.getListeExercices()) {
                                pEx.setInt(1, progId);
                                pEx.setString(2, ex.nom());
                                pEx.setInt(3, ex.repetitions());
                                pEx.setDouble(4, ex.poids());
                                pEx.setDouble(5, ex.duree());
                                if (ex.equipement() != null) {
                                    pEx.setInt(6, ex.equipement().idEquipement());
                                } else {
                                    pEx.setNull(6, Types.INTEGER);
                                }
                                pEx.addBatch();
                            }
                            pEx.executeBatch();
                        }
                    }

                    conn.commit();
                    System.out.println("✅ Programme ajouté → ID: " + progId);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (pProg != null) pProg.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<ProgrammeEntrainement> getAllProgrammes() {
        Map<Integer, ProgrammeEntrainement> programmesMap = new HashMap<>();

        String sql = """
    SELECT 
        p.id_programme, p.titre, p.duree_totale,
        p.coach_id, c.nom AS coach_nom, c.prenom AS coach_prenom,
        c.email AS coach_email, c.telephone AS coach_tel,
        e.id_exercice, e.nom AS ex_nom, e.repetitions, e.poids, 
        e.duree AS ex_duree, e.equipement_id,
        eq.nom AS eq_nom, eq.etat AS eq_etat
    FROM programmeentrainement p
    JOIN personne c ON p.coach_id = c.id
    LEFT JOIN exercice e ON p.id_programme = e.programme_id
    LEFT JOIN equipement eq ON e.equipement_id = eq.id_equipement
    ORDER BY p.id_programme DESC, e.id_exercice
    """;

        System.out.println("\n🔍 Chargement des programmes (version optimisée)...");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int progId = rs.getInt("id_programme");

                if (!programmesMap.containsKey(progId)) {
                    String titre = rs.getString("titre");

                    Coach coach = new Coach(
                            rs.getInt("coach_id"),
                            rs.getString("coach_nom"),
                            rs.getString("coach_prenom"),
                            rs.getString("coach_email"),
                            rs.getString("coach_tel"),
                            "Non spécifié",  // Spécialité par défaut
                            0.0              // Tarif par défaut
                    );

                    ProgrammeEntrainement prog = new ProgrammeEntrainement(progId, titre, coach);
                    programmesMap.put(progId, prog);

                    System.out.println("  📄 Programme ID:" + progId + " - Titre:" + titre + " - Coach:" + coach.getNom());
                }

                int exId = rs.getInt("id_exercice");
                if (!rs.wasNull()) {
                    Equipement equipement = null;
                    int eqId = rs.getInt("equipement_id");
                    if (!rs.wasNull()) {
                        equipement = new Equipement(
                                eqId,
                                rs.getString("eq_nom"),
                                rs.getString("eq_etat")
                        );
                    }

                    Exercice exercice = new Exercice(
                            rs.getString("ex_nom"),
                            rs.getInt("repetitions"),
                            rs.getDouble("poids"),
                            rs.getDouble("ex_duree"),
                            equipement
                    );

                    programmesMap.get(progId).ajouterExercice(exercice);
                    System.out.println("    ↳ Exercice: " + exercice.nom());
                }
            }

            System.out.println("✅ Total programmes chargés: " + programmesMap.size());

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        }

        return new ArrayList<>(programmesMap.values());
    }
            // SUPPRIMER UN PROGRAMME
            public boolean supprimerProgramme(int id) {
                String sql = "DELETE FROM programmeentrainement WHERE id_programme = ?";
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement p = conn.prepareStatement(sql)) {
                    p.setInt(1, id);
                    return p.executeUpdate() > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }