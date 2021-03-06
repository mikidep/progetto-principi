package com.depvin.pps.model;


import java.util.ArrayList;
import java.util.List;

public class Prodotto {
    private long id;
    private String nome;
    private List<Categoria> categorie;

    public Prodotto(String nome, List<Categoria> categorie) {
        this.nome = nome;
        this.categorie = categorie;
    }

    public Prodotto(String nome) {
        this(nome, new ArrayList<Categoria>());
    }

    protected Prodotto() {

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

    public List<Categoria> getCategorie() {
        return categorie;
    }
}
