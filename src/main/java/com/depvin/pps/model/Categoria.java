package com.depvin.pps.model;

import java.util.ArrayList;
import java.util.List;


public class Categoria {
    private long id;
    private String nome;
    private List<Prodotto> prodotti;

    protected Categoria() {

    }

    public Categoria(String nome) {
        this.nome = nome;
        prodotti = new ArrayList<Prodotto>();
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

    public List<Prodotto> getProdotti() {
        return prodotti;
    }
}
