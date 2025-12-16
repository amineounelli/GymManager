package Main;

import DAO.*;
import Models.amine.Personnel.*;
import Models.amen.Infrastructure.*;
import Database.DatabaseConnection;
import java.util.List;

public class TestDatabase {

    public static void main(String[] args) {
        DatabaseConnection.getConnection();

        System.out.println("\n=== TEST MEMBRE DAO ===");
        MembreDAO membreDAO = new MembreDAO();

        Membre nouveauMembre = new Membre(0, "Test", "User", "test@example.com", "22999999");
        membreDAO.ajouterMembre(nouveauMembre);

        List<Membre> membres = membreDAO.getAllMembres();
        System.out.println("\nListe des membres:");
        for (Membre m : membres) {
            m.afficherInfo();
        }

        System.out.println("\n=== TEST COACH DAO ===");
        CoachDAO coachDAO = new CoachDAO();

        List<Coach> coachs = coachDAO.getAllCoachs();
        System.out.println("\nListe des coachs:");
        for (Coach c : coachs) {
            c.afficherInfo();
        }

        System.out.println("\n=== TEST SALLE DAO ===");
        SalleDAO salleDAO = new SalleDAO();

        List<Salle> salles = salleDAO.getAllSalles();
        System.out.println("\nListe des salles:");
        for (Salle s : salles) {
            s.afficherDetailsSalle();
        }

        DatabaseConnection.closeConnection();
    }
}