package com.depvin.pps.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Ordine {
    private long id;
    private String nome;
    private boolean inviato = false;
    private Progetto progetto;
    private Dipendente dipendente;

    private List<ArticoloOrdine> articoliOrdine;

    protected Ordine() {
    }

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

    public boolean isInviato() {
        return inviato;
    }

    public void setInviato(boolean inviato) {
        this.inviato = inviato;
    }

    public boolean isEvaso() {
        if (articoliOrdine.size() == 0) {
            return false;
        }

        boolean evaso = true;
        for (ArticoloOrdine ao: articoliOrdine) {
            evaso = evaso && ao.isEvaso();
        }

        return evaso;
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
        float t = 0.0f;
        Set<Sede> sedi = new HashSet<Sede>();
        for (ArticoloOrdine ao : articoliOrdine) {
            t += ao.getParziale();
            sedi.add(ao.getMagazzino().getSede());
        }
        for (Sede s : sedi) {
            t += s.calcolaSpedizionePer(progetto.getSede());
        }
        return t;
    }
}
