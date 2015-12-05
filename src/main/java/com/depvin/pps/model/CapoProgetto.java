package com.depvin.pps.model;

import java.util.List;

/**
 * Created by Michele De Pascalis on 30/11/15.
 */

public class CapoProgetto extends Utente {
    List<Progetto> progetti;

    protected CapoProgetto() {
    }

    public CapoProgetto(String username, byte[] passwordHash) {
        super(username, passwordHash);
    }

    public List<Progetto> getProgetti() {
        return progetti;
    }
}
