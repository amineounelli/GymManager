/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amine.Personnel;

import Models.amine.Gestion.*;
import Models.amen.Infrastructure.*;
import java.util.ArrayList;
import java.util.List;

public final class Manager extends Personne {

    private String login;
    private String motDePasse;
    private List<Membre> listeMembres;
    private List<Coach> listeCoachs;
    private List<Seance> listeSeances;
    private List<Paiement> listePaiements;

    public Manager(int id, String nom, String prenom, String email, String telephone,
                   String login, String motDePasse) {
        super(id, nom, prenom, email, telephone);
        this.login = login;
        this.motDePasse = motDePasse;
        this.listeMembres = new ArrayList<>();
        this.listeCoachs = new ArrayList<>();
        this.listeSeances = new ArrayList<>();
        this.listePaiements = new ArrayList<>();
    }

    public List<Paiement> getListePaiements() { return listePaiements; }

    public void ajouterCoach(Coach c) { listeCoachs.add(c); }
    public void supprimerCoach(Coach c) { listeCoachs.remove(c); }

    public void ajouterMembre(Membre m) { listeMembres.add(m); }
    public void supprimerMembre(Membre m) { listeMembres.remove(m); }

    public void ajouterSeance(Seance s) { listeSeances.add(s); }
    public void supprimerSeance(Seance s) { listeSeances.remove(s); }

    public void validerPaiement(Paiement p) {
    if (p != null && !listePaiements.contains(p)) {
        System.out.println("Paiement validé et ajouté.");
    } else {
        System.out.println("Paiement non valide.");
    }    }

    public double calculerRevenuTotal() {
        double total = 0;
        for (Paiement p : listePaiements) total += p.getMontant();
        return total;
    }

    public double calculerDepenseTotale() {
        double total = 0;
        for (Coach c : listeCoachs) total += c.calculerRevenu();
        return total;
    }

    @Override
    public void afficherInfo() {
        System.out.println("Manager: " + nom + " " + prenom + " | Login: " + login +
                " " + motDePasse);
    }
}
