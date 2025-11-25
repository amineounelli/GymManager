/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amen.Infrastructure;

/**
 *
 * @author DELL
 */


public record Equipement(int idEquipement, String nom, String etat) {

    public boolean verifierEtat() {
        return etat != null && etat.equalsIgnoreCase("fonctionnel");
    }

    public void afficherDetailsEquipement() {
        System.out.println("Équipement #" + idEquipement + " : " + nom + " (État : " + etat + ")");
    }


}

