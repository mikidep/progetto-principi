package com.depvin.pps.model;


public class Produttore {
    private long id;
    private String nome;

    public Produttore(String nome) {
        this.nome = nome;
    }

    protected Produttore() {

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
