package com.depvin.pps.model;

import java.util.List;

/**
 * Created by Michele De Pascalis on 30/11/15.
 */

public class Magazzino {
    private long id;
    private String nome;

    private List<ArticoloMagazzino> articoliMagazzino;
    private Sede sede;

    protected Magazzino() {
    }

    public Magazzino(String nome, Sede sede) {
        this.nome = nome;
        this.sede = sede;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<ArticoloMagazzino> getArticoliMagazzino() {
        return articoliMagazzino;
    }

    public Sede getSede() {
        return sede;
    }

    public boolean hasArticoloWithDisponibilita(Articolo a, int d) {
        for (ArticoloMagazzino am : articoliMagazzino) {
            if (am.getArticolo().getId() == a.getId() && am.getDisponibilita() >= d) {
                return true;
            }
        }
        return false;
    }
}
