package com.depvin.pps.model;

import java.util.List;

/**
 * Created by Michele De Pascalis on 27/11/15.
 *
 */

public class Dipendente extends Utente {
    List<Progetto> progetti;
    List<Ordine> ordini;

    public Dipendente(String username) {
        super(username);
    }

    public List<Progetto> getProgetti() {
        return progetti;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }
}
