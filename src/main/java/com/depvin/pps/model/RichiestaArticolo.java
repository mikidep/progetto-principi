package com.depvin.pps.model;

public class RichiestaArticolo {
    private long id;
    private Articolo articolo;
    private Progetto progetto;
    private int quantita;

    public RichiestaArticolo(Articolo articolo, Progetto progetto, int quantita) {
        this.articolo = articolo;
        this.progetto = progetto;
        this.quantita = quantita;
    }

    protected RichiestaArticolo() {

    }

    public Articolo getArticolo() {
        return articolo;
    }

    public Progetto getProgetto() {
        return progetto;
    }

    public int getQuantita() {
        return quantita;
    }
}
