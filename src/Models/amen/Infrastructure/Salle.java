/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amen.Infrastructure;

/**
 *
 * @author DELL
 */

import java.util.ArrayList;
import java.util.List;

public class Salle {
    private int idSalle;
    private String nomSalle;
    private int capaciteSalle;
    private List<Equipement> listeEquipements;

    public Salle(int idSalle, String nomSalle, int capaciteSalle) {
        this.idSalle = idSalle;
        this.nomSalle = nomSalle;
        this.capaciteSalle = capaciteSalle;
        this.listeEquipements = new ArrayList<>();
    }

    public void ajouterEquipement(Equipement e) {
        listeEquipements.add(e);
    }

    public void supprimerEquipement(Equipement e) {
        listeEquipements.remove(e);
    }

    public boolean verifierDisponibilite() {
        return true; // pour simplifier
    }

    public void afficherDetailsSalle() {
        System.out.println("=== Détails Salle ===");
        System.out.println("ID : " + idSalle);
        System.out.println("Nom : " + nomSalle);
        System.out.println("Capacité : " + capaciteSalle);
        System.out.println("Équipements : ");
        for (Equipement e : listeEquipements) {
            e.afficherDetailsEquipement();
        }
    }

    public int getIdSalle() { return idSalle; }
    public String getNomSalle() { return nomSalle; }
    public int getCapaciteSalle() { return capaciteSalle; }
    public List<Equipement> getListeEquipements() { return listeEquipements; }
}

