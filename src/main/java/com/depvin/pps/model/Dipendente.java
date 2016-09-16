package com.depvin.pps.model;

import java.util.List;
import java.util.Set;


public class Dipendente extends Utente {
    private List<Progetto> progetti;
    private List<Ordine> ordini;

    protected Dipendente() {
    }

    public Dipendente(String username, byte[] passwordHash) {
        super(username, passwordHash);
    }

    public List<Progetto> getProgetti() {
        return progetti;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }
}
