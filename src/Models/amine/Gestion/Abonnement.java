/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.amine.Gestion;

import java.util.Date;

public class Abonnement {
    private String type;
    private Date dateDebut;
    private Date dateFin;
    private String etat;

    public Abonnement(String type, Date dateDebut, Date dateFin) {
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = "Inactif";
    }
    
    public String getType() { return type; }
    public Date getDateDebut() { return dateDebut; }
    public Date getDateFin() { return dateFin; }
    public String getEtat() { return etat; }

    public boolean verifierValidite() {
        Date today = new Date();
        return today.after(dateDebut) && today.before(dateFin);
    }

    public void activer() {
        etat = "Actif";
    }

    public void desactiver() {
        etat = "Inactif";
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
