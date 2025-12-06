/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amen.Infrastructure;

/**
 *
 * @author DELL
 */


import Models.amine.Personnel.Membre;

public final class SeanceIndividuelle extends Seance {
    private Membre membre;

    public SeanceIndividuelle(int idSeance, double duree, java.util.Date date, Salle salle, double coutHoraire, Membre membre) {
        super(idSeance, duree, date, salle, coutHoraire);
        this.membre = membre;
    }

    public Membre getMembre() {
        return membre;
    }

    // ==================== SETTER ====================
    public void setMembre(Membre membre) {
        this.membre = membre;
    }


    @Override
    public void afficherDetail() {
        System.out.println("=== Séance Individuelle ===");
        System.out.println("ID : " + idSeance);
        System.out.println("Date : " + date);
        System.out.println("Durée : " + duree + "h");
        System.out.println("Coût total : " + calculerCoutTotal() + " DT");
        System.out.println("Salle : " + salle.getNomSalle());
        System.out.println("Membre : " + membre.getNom() + " " + membre.getPrenom());
    }

}


