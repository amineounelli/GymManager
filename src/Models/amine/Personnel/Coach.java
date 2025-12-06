/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amine.Personnel;

import Models.amine.Gestion.*;
import Models.amen.Infrastructure.*;
import java.util.ArrayList;
import java.util.List;

public final class Coach extends Personne {

    private String specialite;
    private double tarifHeure;
    private List<SeanceCollective> listeSeances;
    private List<ProgrammeEntrainement> listeProgrammes;

    public Coach(int id, String nom, String prenom, String email, String telephone,
                 String specialite, double tarifHeure) {
        super(id, nom, prenom, email, telephone);
        this.specialite = specialite;
        this.tarifHeure = tarifHeure;
        this.listeSeances = new ArrayList<>();
        this.listeProgrammes = new ArrayList<>();
    }

    
    public void ajouterSeance(SeanceCollective s) { listeSeances.add(s); }
    public void supprimerSeance(SeanceCollective s) { listeSeances.remove(s); }

    public void creerProgramme(ProgrammeEntrainement p) { listeProgrammes.add(p); }
    public void supprimerProgramme(ProgrammeEntrainement p) { listeProgrammes.remove(p); }

    public double calculerRevenu() {
        double heuresTotales = 0;
        for (SeanceCollective s : listeSeances) heuresTotales += s.getDuree();
        return heuresTotales * tarifHeure;
    }

    @Override
    public void afficherInfo() {
        System.out.println("Coach: " + nom + " " + prenom + " | Spécialité: " + specialite);
    }

    public String getSpecialite() {
        return specialite;
    }

    public double getTarifHeure() {
        return tarifHeure;
    }
}
