/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amine.Gestion;

import java.util.Date;
import Models.amine.Personnel.Membre;

public class Paiement {
    private int idPaiement;
    private String typePaiement;
    private double montant;
    private Date datePaiement;
    private Membre membre;

    public Paiement(int idPaiement, String typePaiement, double montant, Date datePaiement, Membre membre) {
        this.idPaiement = idPaiement;
        this.typePaiement = typePaiement;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.membre = membre;
    }

    public void setDatePaiement(Date datePaiement) { this.datePaiement = datePaiement; }

    public int getIdPaiement() { return idPaiement; }
    public String getTypePaiement() { return typePaiement; }
    public double getMontant() { return montant; }
    public Date getDatePaiement() { return datePaiement; }
    public Membre getMembre() { return membre; }

    public void afficherDetailsPaiement() {
        System.out.println("Paiement #" + idPaiement + " | Pae: " + membre + " | Type: " + typePaiement +
                " | Montant: " + montant + " DT | Date: " + datePaiement);
    }
}
