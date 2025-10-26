/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amine.Personnel;

import Models.amine.Gestion.*;
import Models.amen.Infrastructure.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Membre extends Personne {

    private Abonnement abonnement;
    private List<Seance> listeSeances;
    private List<Reservation> listeReservations;
    private List<Paiement> listePaiements;
    private ProgrammeEntrainement programme;

    public Membre(int id, String nom, String prenom, String email, String telephone) {
        super(id, nom, prenom, email, telephone);
        this.listeSeances = new ArrayList<>();
        this.listeReservations = new ArrayList<>();
    }

    public List<Reservation> getListeReservations() { return listeReservations; }
    
    public void ajouterSeance(Seance s) { listeSeances.add(s); }
    public void supprimerSeance(Seance s) { listeSeances.remove(s); }

    public void ajouterReservation(Reservation r) { listeReservations.add(r); }
    public void supprimerReservation(Reservation r) { listeReservations.remove(r); }

    public void effectuerPaiement(Paiement p) {
    if (p != null) {
        p.setDatePaiement(new Date());
        listePaiements.add(p);
        System.out.println("Paiement effectué par " + getNom());
    } else {
        System.out.println("Paiement invalide.");
    }
}

    public void consulterProgramme() {
        if (programme != null) programme.afficherProgramme();
        else System.out.println("Aucun programme attribué.");
    }

    public double calculerCoutTotal() {
        double total = 0;
        for (Seance s : listeSeances) total += s.calculerCoutTotal();
        return total;
    }

    public String getEtatAbonnement() {
        return (abonnement != null) ? abonnement.getEtat() : "Aucun abonnement";
    }

    @Override
    public void afficherInfo() {
        System.out.println("Membre: " + nom + " " + prenom + " | Email: " + email);
    }
}
