package Models.amen.Infrastructure;

import Models.amine.Gestion.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract sealed class Seance permits SeanceIndividuelle, SeanceCollective {
    protected int idSeance;
    protected double duree;
    protected Date date;
    protected Salle salle;
    protected List<Reservation> listeReservations;
    protected double coutHoraire;

    public Seance(int idSeance, double duree, Date date, Salle salle, double coutHoraire) {
        this.idSeance = idSeance;
        this.duree = duree;
        this.date = date;
        this.salle = salle;
        this.coutHoraire = coutHoraire;
        this.listeReservations = new ArrayList<>();
    }

    public double calculerCoutTotal() {
        return duree * coutHoraire;
    }

    public abstract void afficherDetail();

    public int getIdSeance() { return idSeance; }
    public double getDuree() { return duree; }
    public Date getDate() { return date; }
    public Salle getSalle() { return salle; }
    public List<Reservation> getListeReservations() { return listeReservations; }
    public double getCoutHoraire() { return coutHoraire; }
}

