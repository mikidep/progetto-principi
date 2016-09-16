package com.depvin.pps.model;

import java.util.List;


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
