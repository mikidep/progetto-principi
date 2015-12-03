package com.depvin.pps.model;

/**
 * Created by Michele De Pascalis on 30/11/15.
 *
 */
public class ArticoloOrdine {
    Articolo articolo;
    int quantita;
    Magazzino magazzino;

    private boolean richiesto;

    protected ArticoloOrdine() {}

    public ArticoloOrdine(Articolo articolo, int quantita, Magazzino magazzino) {
        this.articolo = articolo;
        this.quantita = quantita;
        this.magazzino = magazzino;
    }

    public Articolo getArticolo() {
        return articolo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Magazzino getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
    }

    public boolean isRichiesto() {
        return richiesto;
    }

    public void setRichiesto(boolean richiesto) {
        this.richiesto = richiesto;
    }

    public boolean isDisponibile() {
        return magazzino.hasArticoloWithDisponibilita(articolo, quantita);
    }

    public float getParziale() {
        return quantita*articolo.getPrezzo();
    }
}