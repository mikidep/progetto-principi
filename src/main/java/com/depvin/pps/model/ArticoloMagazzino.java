package com.depvin.pps.model;

/**
 * Created by Michele De Pascalis on 30/11/15.
 *
 */

public class ArticoloMagazzino {
    private Articolo articolo;
    private int disponibilita;

    public ArticoloMagazzino(Articolo articolo, int disponibilita) {
        this.articolo = articolo;
        this.disponibilita = disponibilita;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }
}
