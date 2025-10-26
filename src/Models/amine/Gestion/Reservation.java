/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amine.Gestion;

import java.util.Date;
import Models.amine.Personnel.Membre;
import Models.amen.Infrastructure.Seance;

public class Reservation {
    private int idReservation;
    private Membre membre;
    private Seance seance;
    private Date dateReservation;

    public Reservation(int idReservation, Membre membre, Seance seance, Date dateReservation) {
        this.idReservation = idReservation;
        this.membre = membre;
        this.seance = seance;
        this.dateReservation = dateReservation;
    }

    public int getIdReservation() { return idReservation; }
    public Membre getMembre() { return membre; }
    public Seance getSeance() { return seance; }
    public Date getDateReservation() { return dateReservation; }

    public void confirmerReservation() {
    if (membre != null && seance != null) {
        membre.ajouterReservation(this);
        seance.getListeReservations().add(this);
        System.out.println("Réservation confirmée pour " + membre.getNom() + " à la séance " + seance.getIdSeance());
    } else {
        System.out.println("Impossible de confirmer : membre ou séance manquant.");
    }    }

    public void annulerReservation() {
    if (membre != null && seance != null) {
        membre.supprimerReservation(this);
        seance.getListeReservations().remove(this);
        System.out.println("Réservation annulée pour " + membre.getNom());
    } else {
        System.out.println("Impossible d'annuler : membre ou séance manquant.");
    }    }

    public void afficherDetailsReservation() {
        System.out.println("Reservation #" + idReservation + " | Membre: " + membre.getNom() +
                " | Séance ID: " + seance.getIdSeance() + " | Date: " + dateReservation);
    }
}
