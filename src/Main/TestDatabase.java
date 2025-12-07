package Main;

import DAO.*;
import Models.amine.Personnel.*;
import Models.amen.Infrastructure.*;
import Database.DatabaseConnection;
import java.util.List;

public class TestDatabase {

    public static void main(String[] args) {
        // Test de connexion
        DatabaseConnection.getConnection();

        // Test MembreDAO
        System.out.println("\n=== TEST MEMBRE DAO ===");
        MembreDAO membreDAO = new MembreDAO();

        // Ajouter un membre
        Membre nouveauMembre = new Membre(0, "Test", "User", "test@example.com", "22999999");
        membreDAO.ajouterMembre(nouveauMembre);

        // Récupérer tous les membres
        List<Membre> membres = membreDAO.getAllMembres();
        System.out.println("\nListe des membres:");
        for (Membre m : membres) {
            m.afficherInfo();
        }

        // Test CoachDAO
        System.out.println("\n=== TEST COACH DAO ===");
        CoachDAO coachDAO = new CoachDAO();

        List<Coach> coachs = coachDAO.getAllCoachs();
        System.out.println("\nListe des coachs:");
        for (Coach c : coachs) {
            c.afficherInfo();
        }

        // Test SalleDAO
        System.out.println("\n=== TEST SALLE DAO ===");
        SalleDAO salleDAO = new SalleDAO();

        List<Salle> salles = salleDAO.getAllSalles();
        System.out.println("\nListe des salles:");
        for (Salle s : salles) {
            s.afficherDetailsSalle();
        }

        // Fermer la connexion
        DatabaseConnection.closeConnection();
    }
}