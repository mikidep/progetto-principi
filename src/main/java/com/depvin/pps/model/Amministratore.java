package com.depvin.pps.model;

/**
 * Created by Michele De Pascalis on 27/11/15.
 *
 */

public class Amministratore extends Utente {
    protected Amministratore() {}

    public Amministratore(String username, byte[] passwordHash) {
        super(username, passwordHash);
    }
}

