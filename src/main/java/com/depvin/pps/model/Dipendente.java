package com.depvin.pps.model;

import java.util.List;

/**
 * Created by Michele De Pascalis on 27/11/15.
 *
 */

public class Dipendente extends Utente {
    private List<Progetto> progetti;
    private List<Ordine> ordini;

    protected Dipendente() {}

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
