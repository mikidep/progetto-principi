package com.depvin.pps.model;


public class Amministratore extends Utente {
    protected Amministratore() {
    }

    public Amministratore(String username, byte[] passwordHash) {
        super(username, passwordHash);
    }
}

