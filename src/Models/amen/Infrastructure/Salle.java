package Models.amen.Infrastructure;

import java.util.ArrayList;
import java.util.List;

public class Salle {
    private int idSalle;
    private String nomSalle;
    private int capaciteSalle;
    private List<Equipement> listeEquipements;

    // Constructeur
    public Salle(int idSalle, String nomSalle, int capaciteSalle) {
        this.idSalle = idSalle;
        this.nomSalle = nomSalle;
        this.capaciteSalle = capaciteSalle;
        this.listeEquipements = new ArrayList<>();
    }

    // Méthodes d'équipement
    public void ajouterEquipement(Equipement e) {
        listeEquipements.add(e);
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

    // ==================== GETTERS ====================
    public int getIdSalle() {
        return idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public int getCapaciteSalle() {
        return capaciteSalle;
    }

    public List<Equipement> getListeEquipements() {
        return listeEquipements;
    }

    // ==================== SETTERS (AJOUTÉS) ====================
    // CRITIQUE: Ce setter est nécessaire pour mettre à jour l'ID après insertion en BDD
    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public void setCapaciteSalle(int capaciteSalle) {
        this.capaciteSalle = capaciteSalle;
    }

    // ==================== TOSTRING (UTILE POUR DEBUG) ====================
    @Override
    public String toString() {
        return "Salle{" +
                "idSalle=" + idSalle +
                ", nomSalle='" + nomSalle + '\'' +
                ", capaciteSalle=" + capaciteSalle +
                ", nbEquipements=" + listeEquipements.size() +
                '}';
    }
}