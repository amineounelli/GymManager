/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amine.Gestion;

import Models.amen.Infrastructure.Equipement;

public record Exercice(String nom, int repetitions, double poids, double duree, Equipement equipement) {

    public void afficherExercice() {
        System.out.println(nom + " | Reps: " + repetitions + " | Poids (en kg): " + poids +
                " | Durée (en minute): " + duree + " | Équipement: " + equipement.nom());
    }

    public double getDuree() {
        return duree;
    }
}
