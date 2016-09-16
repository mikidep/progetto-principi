package com.depvin.pps.model;


public class Fornitore {
    private long id;

    private String nome;

    public Fornitore(String nome) {
        this.nome = nome;
    }

    protected Fornitore() {

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
}
