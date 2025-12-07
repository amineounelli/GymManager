package Models.amine.Gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Models.amine.Personnel.Membre;

public class Paiement {

    private static final List<Paiement> allPayments = new ArrayList<>();

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
        allPayments.add(this);
    }

    public void setDatePaiement(Date datePaiement) { this.datePaiement = datePaiement; }

    public int getIdPaiement() { return idPaiement; }
    public String getTypePaiement() { return typePaiement; }
    public double getMontant() { return montant; }
    public Date getDatePaiement() { return datePaiement; }
    public Membre getMembre() { return membre; }

    public void afficherDetailsPaiement() {
        System.out.println("Paiement #" + idPaiement + " | Par: " + membre.getNom() +
                " | Type: " + typePaiement + " | Montant: " + montant +
                " DT | Date: " + datePaiement);
    }

    public static double calculerRevenuTotal() {
        return allPayments.stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }

    public void setIdPaiement(int  generatedId) {
        this.idPaiement = generatedId;
    }

}
