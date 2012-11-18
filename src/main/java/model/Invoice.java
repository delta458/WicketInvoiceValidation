package model;

import java.io.Serializable;

public class Invoice implements Serializable{

    private Adresse adresse;
    private Double betrag;
    private Double Ust;
    private String zweck;

    public Adresse getAdresse() {
        return adresse;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    public Double getBetrag() {
        return betrag;
    }
    public void setBetrag(Double betrag) {
        this.betrag = betrag;
    }
    public Double getUst() {
        return Ust;
    }
    public void setUst(Double ust) {
        Ust = ust;
    }
    public String getZweck() {
        return zweck;
    }
    public void setZweck(String zweck) {
        this.zweck = zweck;
    }
}
