package com.depvin.pps.model;

import java.util.List;
import java.util.Set;


public class Progetto {
    private long id;
    private String nome;
    private float budget;

    private Sede sede;
    private List<Ordine> ordini;
    private List<Dipendente> dipendenti;
    private Set<RichiestaArticolo> richieste;

    protected Progetto() {
    }

    public Progetto(String nome, float budget, Sede sede) {
        this.nome = nome;
        this.budget = budget;
        this.sede = sede;
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

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public Sede getSede() {
        return sede;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public List<Dipendente> getDipendenti() {
        return dipendenti;
    }

    public Set<RichiestaArticolo> getRichieste() {
        return richieste;
    }
}
