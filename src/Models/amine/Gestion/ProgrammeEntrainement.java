/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amine.Gestion;

import java.util.ArrayList;
import java.util.List;

import Models.amine.Personnel.Coach;

public class ProgrammeEntrainement {
    private int idProgramme;
    private String titre;
    private Coach coach;
    private List<Exercice> listeExercices;
    private double dureeTotale;

    public ProgrammeEntrainement(int idProgramme, String titre, Coach coach) {
        this.idProgramme = idProgramme;
        this.titre = titre;
        this.coach = coach;
        this.listeExercices = new ArrayList<>();
        this.dureeTotale = 0;
    }

    public int getIdProgramme() { return idProgramme; }
    public String getTitre() { return titre; }
    public Coach getCoach() { return coach; }
    public List<Exercice> getListeExercices() { return listeExercices; }
    public double getDureeTotale() { return dureeTotale; }
    public void ajouterExercice(Exercice e) {
        listeExercices.add(e);
        calculerDureeTotale(); }

    public void supprimerExercice(Exercice e) {
        listeExercices.remove(e);
        calculerDureeTotale();
    }

    public double calculerDureeTotale() {
        double total = 0;
        for (Exercice e : listeExercices) {
            total += e.duree();  // ← PAS getDuree(), mais duree() !
        }
        this.dureeTotale = total;
        return total;
    }

    public void setIdProgramme(int idProgramme) {
        this.idProgramme = idProgramme;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setListeExercices(List<Exercice> listeExercices) {
        this.listeExercices = listeExercices;
    }

    public void afficherProgramme() {
        System.out.println("Programme : " + titre + " | Coach : " + coach.getNom());
        listeExercices.forEach(Exercice::afficherExercice);
        System.out.println("Durée totale (en minutes) : " + dureeTotale);
    }
}
