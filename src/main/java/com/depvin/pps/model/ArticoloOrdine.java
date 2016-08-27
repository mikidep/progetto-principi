package com.depvin.pps.model;

/**
 * Created by Michele De Pascalis on 30/11/15.
 */
public class ArticoloOrdine {
    private long id;
    private Articolo articolo;
    private int quantita;
    private Magazzino magazzino;
    private Ordine ordine;
    private boolean richiesto = false;

    protected ArticoloOrdine() {
    }

    public ArticoloOrdine(Ordine ordine, Articolo articolo, int quantita, Magazzino magazzino) {
        this.ordine = ordine;
        this.articolo = articolo;
        this.quantita = quantita;
        this.magazzino = magazzino;
    }

    public long getId() {
        return id;
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

    public Ordine getOrdine() {
        return ordine;
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
        return quantita * articolo.getPrezzo();
    }

}