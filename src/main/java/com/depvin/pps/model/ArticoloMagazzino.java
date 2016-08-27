package com.depvin.pps.model;

/**
 * Created by Michele De Pascalis on 30/11/15.
 */

public class ArticoloMagazzino {
    private long id;
    private Magazzino magazzino;
    private Articolo articolo;
    private int disponibilita;

    protected ArticoloMagazzino() {
    }

    public ArticoloMagazzino(Magazzino magazzino, Articolo articolo, int disponibilita) {
        this.magazzino = magazzino;
        this.articolo = articolo;
        this.disponibilita = disponibilita;
    }

    public long getId() {
        return id;
    }

    public Magazzino getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
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
