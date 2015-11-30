package com.depvin.pps.model;

import java.util.List;

/**
 * Created by Michele De Pascalis on 30/11/15.
 *
 */

public class Ordine {
    private long id;
    private String nome;
    private boolean evaso = false;
    private Progetto progetto;
    private Dipendente dipendente;

    private List<ArticoloOrdine> articoliOrdine;

    public Ordine(String nome, Progetto progetto, Dipendente dipendente) {
        this.nome = nome;
        this.progetto = progetto;
        this.dipendente = dipendente;
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

    public boolean isEvaso() {
        return evaso;
    }

    public void setEvaso(boolean evaso) {
        this.evaso = evaso;
    }

    public List<ArticoloOrdine> getArticoliOrdine() {
        return articoliOrdine;
    }

    public Progetto getProgetto() {
        return progetto;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public float getTotale() {
        // return articoliOrdine reduce (_ + _)
        // Java -- Y U NO REDUCE!
        float t = 0.0f;
        for (ArticoloOrdine ao: articoliOrdine) {
            t += ao.getParziale() /* + progetto.getMagazzino().getSpedizioneTo(ao.getMagazzino()) */;
        }
        return t;
    }
}
