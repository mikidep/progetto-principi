package com.depvin.pps.model;

/**
 * Created by Michele De Pascalis on 27/11/15.
 */

import java.util.List;

public class Prodotto {
    private long id;
    private String nome;
    private List<Categoria> categorie;

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
