/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amen.Infrastructure;

/**
 *
 * @author DELL
 */


import Models.amine.Personnel.Coach;
import Models.amine.Personnel.Membre;

import java.util.ArrayList;
import java.util.List;

public final class SeanceCollective extends Seance {
    private Coach coach;
    private List<Membre> listeParticipants;
    private int capacite;

    public SeanceCollective(int idSeance, double duree, java.util.Date date, Salle salle, double coutHoraire,
                            Coach coach, int capacite) {
        super(idSeance, duree, date, salle, coutHoraire);
        this.coach = coach;
        this.capacite = capacite;
        this.listeParticipants = new ArrayList<>();
    }

    public void ajouterParticipant(Membre m) throws SallePleineException {
        if (listeParticipants.size() >= capacite) {
            throw new SallePleineException("La séance collective est complète !");
        }
        listeParticipants.add(m);
    }

    public boolean verifierDisponibilite() {
        return listeParticipants.size() < capacite;
    }

    public void afficherParticipants() {
        System.out.println("Participants : ");
        for (Membre m : listeParticipants) {
            System.out.println(" - " + m.getNom() + " " + m.getPrenom());
        }
    }

    @Override
    public void afficherDetail() {
        System.out.println("=== Séance Collective ===");
        System.out.println("Coach : " + coach.getNom() + " " + coach.getPrenom());
        System.out.println("Capacité : " + capacite);
        System.out.println("Nombre de participants : " + listeParticipants.size());
        System.out.println("Coût total : " + calculerCoutTotal() + " DT");
        afficherParticipants();
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCapacite() {
        return capacite;
    }

    public List<Membre> getListeParticipants() {
        return listeParticipants;
    }

}

