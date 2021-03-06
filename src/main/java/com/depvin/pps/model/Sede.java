package com.depvin.pps.model;


public class Sede {
    private long id;
    private String nome;
    private Nazione nazione;

    protected Sede() {
    }

    public Sede(String nome, Nazione nazione) {
        this.nome = nome;
        this.nazione = nazione;
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

    public Nazione getNazione() {
        return nazione;
    }

    public float calcolaSpedizionePer(Sede s) {
        if (s.getId() == id) {
            return 0.0f;
        } else {
            return nazione.calcolaSpedizionePer(s.getNazione());
        }
    }
}
