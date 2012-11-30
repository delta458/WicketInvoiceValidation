package model;

import java.io.Serializable;

public class Invoice implements Serializable {

    private Adresse adresse;
    private Double betrag;
    private Double ust;
    private String zweck;

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getZweck() {
        return zweck;
    }

    public void setZweck(String zweck) {
        this.zweck = zweck;
    }

    public Double getBetrag() {
        return betrag;
    }

    public void setBetrag(Double betrag) {
        this.betrag = betrag;
    }

    public Double getUst() {
        return ust;
    }

    public void setUst(Double ust) {
        this.ust = ust;
    }
}
