package com.depvin.pps.model;

/**
 * Created by Michele De Pascalis on 30/11/15.
 *
 */

public class Nazione {
    public static final float PREZZO_SPEDIZIONE_INTERNAZIONALE = 10.00f;

    private long id;
    private String nome;
    private float prezzoSpedizione;

    protected Nazione() {}

    public Nazione(String nome, float prezzoSpedizione) {
        this.nome = nome;
        this.prezzoSpedizione = prezzoSpedizione;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPrezzoSpedizione() {
        return prezzoSpedizione;
    }

    public void setPrezzoSpedizione(float prezzoSpedizione) {
        this.prezzoSpedizione = prezzoSpedizione;
    }

    public float calcolaSpedizionePer(Nazione n) {
        if (n.getId() == id) {
            return prezzoSpedizione;
        }
        else {
            return  PREZZO_SPEDIZIONE_INTERNAZIONALE;
        }
    }
}
